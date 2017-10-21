package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.MissingBackpressureException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.SynchronizedQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;

public final class OperatorPublish<T> extends ConnectableObservable<T> {
    final AtomicReference<PublishSubscriber<T>> current;
    final Observable<? extends T> source;

    static final class InnerProducer<T> extends AtomicLong implements Producer, Subscription {
        static final long NOT_REQUESTED = -4611686018427387904L;
        static final long UNSUBSCRIBED = Long.MIN_VALUE;
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        final PublishSubscriber<T> parent;

        public InnerProducer(PublishSubscriber<T> publishSubscriber, Subscriber<? super T> subscriber) {
            this.parent = publishSubscriber;
            this.child = subscriber;
            lazySet(NOT_REQUESTED);
        }

        public final boolean isUnsubscribed() {
            return get() == UNSUBSCRIBED;
        }

        public final long produced(long j) {
            if (j <= 0) {
                throw new IllegalArgumentException("Cant produce zero or less");
            }
            long j2;
            long j3;
            do {
                j3 = get();
                if (j3 == NOT_REQUESTED) {
                    throw new IllegalStateException("Produced without request");
                } else if (j3 == UNSUBSCRIBED) {
                    return UNSUBSCRIBED;
                } else {
                    j2 = j3 - j;
                    if (j2 < 0) {
                        throw new IllegalStateException("More produced (" + j + ") than requested (" + j3 + ")");
                    }
                }
            } while (!compareAndSet(j3, j2));
            return j2;
        }

        public final void request(long j) {
            if (j >= 0) {
                long j2;
                long j3;
                do {
                    j2 = get();
                    if (j2 == UNSUBSCRIBED) {
                        return;
                    }
                    if (j2 >= 0 && j == 0) {
                        return;
                    }
                    if (j2 == NOT_REQUESTED) {
                        j3 = j;
                    } else {
                        j3 = j2 + j;
                        if (j3 < 0) {
                            j3 = Long.MAX_VALUE;
                        }
                    }
                } while (!compareAndSet(j2, j3));
                this.parent.dispatch();
            }
        }

        public final void unsubscribe() {
            if (get() != UNSUBSCRIBED && getAndSet(UNSUBSCRIBED) != UNSUBSCRIBED) {
                this.parent.remove(this);
                this.parent.dispatch();
            }
        }
    }

    static final class PublishSubscriber<T> extends Subscriber<T> implements Subscription {
        static final InnerProducer[] EMPTY = new InnerProducer[0];
        static final InnerProducer[] TERMINATED = new InnerProducer[0];
        final AtomicReference<PublishSubscriber<T>> current;
        boolean emitting;
        boolean missed;
        final NotificationLite<T> nl;
        final AtomicReference<InnerProducer[]> producers;
        final Queue<Object> queue;
        final AtomicBoolean shouldConnect;
        volatile Object terminalEvent;

        public PublishSubscriber(AtomicReference<PublishSubscriber<T>> atomicReference) {
            this.queue = UnsafeAccess.isUnsafeAvailable() ? new SpscArrayQueue(RxRingBuffer.SIZE) : new SynchronizedQueue(RxRingBuffer.SIZE);
            this.nl = NotificationLite.instance();
            this.producers = new AtomicReference(EMPTY);
            this.current = atomicReference;
            this.shouldConnect = new AtomicBoolean();
        }

        final boolean add(InnerProducer<T> innerProducer) {
            if (innerProducer == null) {
                throw new NullPointerException();
            }
            InnerProducer[] innerProducerArr;
            Object obj;
            do {
                innerProducerArr = (InnerProducer[]) this.producers.get();
                if (innerProducerArr == TERMINATED) {
                    return false;
                }
                int length = innerProducerArr.length;
                obj = new InnerProducer[(length + 1)];
                System.arraycopy(innerProducerArr, 0, obj, 0, length);
                obj[length] = innerProducer;
            } while (!this.producers.compareAndSet(innerProducerArr, obj));
            return true;
        }

        final boolean checkTerminated(Object obj, boolean z) {
            int i = 0;
            if (obj != null) {
                InnerProducer[] innerProducerArr;
                if (!this.nl.isCompleted(obj)) {
                    Throwable error = this.nl.getError(obj);
                    this.current.compareAndSet(this, null);
                    try {
                        innerProducerArr = (InnerProducer[]) this.producers.getAndSet(TERMINATED);
                        int length = innerProducerArr.length;
                        while (i < length) {
                            innerProducerArr[i].child.onError(error);
                            i++;
                        }
                        return true;
                    } finally {
                        unsubscribe();
                    }
                } else if (z) {
                    this.current.compareAndSet(this, null);
                    try {
                        innerProducerArr = (InnerProducer[]) this.producers.getAndSet(TERMINATED);
                        int length2 = innerProducerArr.length;
                        while (i < length2) {
                            innerProducerArr[i].child.onCompleted();
                            i++;
                        }
                        return true;
                    } finally {
                        unsubscribe();
                    }
                }
            }
            return false;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void dispatch() {
            /*
            r18 = this;
            monitor-enter(r18);
            r0 = r18;
            r2 = r0.emitting;	 Catch:{ all -> 0x0058 }
            if (r2 == 0) goto L_0x000e;
        L_0x0007:
            r2 = 1;
            r0 = r18;
            r0.missed = r2;	 Catch:{ all -> 0x0058 }
            monitor-exit(r18);	 Catch:{ all -> 0x0058 }
        L_0x000d:
            return;
        L_0x000e:
            r2 = 1;
            r0 = r18;
            r0.emitting = r2;	 Catch:{ all -> 0x0058 }
            r2 = 0;
            r0 = r18;
            r0.missed = r2;	 Catch:{ all -> 0x0058 }
            monitor-exit(r18);	 Catch:{ all -> 0x0058 }
            r9 = 0;
        L_0x001a:
            r0 = r18;
            r2 = r0.terminalEvent;	 Catch:{ all -> 0x0085 }
            r0 = r18;
            r3 = r0.queue;	 Catch:{ all -> 0x0085 }
            r6 = r3.isEmpty();	 Catch:{ all -> 0x0085 }
            r0 = r18;
            r2 = r0.checkTerminated(r2, r6);	 Catch:{ all -> 0x0085 }
            if (r2 != 0) goto L_0x000d;
        L_0x002e:
            if (r6 != 0) goto L_0x00f6;
        L_0x0030:
            r0 = r18;
            r2 = r0.producers;	 Catch:{ all -> 0x0085 }
            r2 = r2.get();	 Catch:{ all -> 0x0085 }
            r2 = (rx.internal.operators.OperatorPublish.InnerProducer[]) r2;	 Catch:{ all -> 0x0085 }
            r8 = r2.length;	 Catch:{ all -> 0x0085 }
            r4 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
            r10 = r2.length;	 Catch:{ all -> 0x0085 }
            r3 = 0;
            r7 = 0;
        L_0x0043:
            if (r7 >= r10) goto L_0x0064;
        L_0x0045:
            r11 = r2[r7];	 Catch:{ all -> 0x0085 }
            r12 = r11.get();	 Catch:{ all -> 0x0085 }
            r14 = 0;
            r11 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
            if (r11 < 0) goto L_0x005b;
        L_0x0051:
            r4 = java.lang.Math.min(r4, r12);	 Catch:{ all -> 0x0085 }
        L_0x0055:
            r7 = r7 + 1;
            goto L_0x0043;
        L_0x0058:
            r2 = move-exception;
            monitor-exit(r18);	 Catch:{ all -> 0x0058 }
            throw r2;
        L_0x005b:
            r14 = -9223372036854775808;
            r11 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
            if (r11 != 0) goto L_0x0055;
        L_0x0061:
            r3 = r3 + 1;
            goto L_0x0055;
        L_0x0064:
            if (r8 != r3) goto L_0x0093;
        L_0x0066:
            r0 = r18;
            r3 = r0.terminalEvent;	 Catch:{ all -> 0x0085 }
            r0 = r18;
            r2 = r0.queue;	 Catch:{ all -> 0x0085 }
            r2 = r2.poll();	 Catch:{ all -> 0x0085 }
            if (r2 != 0) goto L_0x0091;
        L_0x0074:
            r2 = 1;
        L_0x0075:
            r0 = r18;
            r2 = r0.checkTerminated(r3, r2);	 Catch:{ all -> 0x0085 }
            if (r2 != 0) goto L_0x000d;
        L_0x007d:
            r2 = 1;
            r0 = r18;
            r0.request(r2);	 Catch:{ all -> 0x0085 }
            goto L_0x001a;
        L_0x0085:
            r2 = move-exception;
            r3 = r9;
        L_0x0087:
            if (r3 != 0) goto L_0x0090;
        L_0x0089:
            monitor-enter(r18);
            r3 = 0;
            r0 = r18;
            r0.emitting = r3;	 Catch:{ all -> 0x0117 }
            monitor-exit(r18);	 Catch:{ all -> 0x0117 }
        L_0x0090:
            throw r2;
        L_0x0091:
            r2 = 0;
            goto L_0x0075;
        L_0x0093:
            r3 = 0;
            r8 = r3;
            r3 = r6;
        L_0x0096:
            r6 = (long) r8;
            r6 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
            if (r6 >= 0) goto L_0x00e5;
        L_0x009b:
            r0 = r18;
            r3 = r0.terminalEvent;	 Catch:{ all -> 0x0085 }
            r0 = r18;
            r6 = r0.queue;	 Catch:{ all -> 0x0085 }
            r7 = r6.poll();	 Catch:{ all -> 0x0085 }
            if (r7 != 0) goto L_0x011a;
        L_0x00a9:
            r6 = 1;
        L_0x00aa:
            r0 = r18;
            r3 = r0.checkTerminated(r3, r6);	 Catch:{ all -> 0x0085 }
            if (r3 != 0) goto L_0x000d;
        L_0x00b2:
            if (r6 != 0) goto L_0x0122;
        L_0x00b4:
            r0 = r18;
            r3 = r0.nl;	 Catch:{ all -> 0x0085 }
            r10 = r3.getValue(r7);	 Catch:{ all -> 0x0085 }
            r11 = r2.length;	 Catch:{ all -> 0x0085 }
            r3 = 0;
            r7 = r3;
        L_0x00bf:
            if (r7 >= r11) goto L_0x011c;
        L_0x00c1:
            r12 = r2[r7];
            r14 = r12.get();	 Catch:{ all -> 0x0085 }
            r16 = 0;
            r3 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
            if (r3 <= 0) goto L_0x00d7;
        L_0x00cd:
            r3 = r12.child;	 Catch:{ Throwable -> 0x00db }
            r3.onNext(r10);	 Catch:{ Throwable -> 0x00db }
            r14 = 1;
            r12.produced(r14);	 Catch:{ all -> 0x0085 }
        L_0x00d7:
            r3 = r7 + 1;
            r7 = r3;
            goto L_0x00bf;
        L_0x00db:
            r3 = move-exception;
            r12.unsubscribe();	 Catch:{ all -> 0x0085 }
            r12 = r12.child;	 Catch:{ all -> 0x0085 }
            rx.exceptions.Exceptions.throwOrReport(r3, r12, r10);	 Catch:{ all -> 0x0085 }
            goto L_0x00d7;
        L_0x00e5:
            r2 = r3;
        L_0x00e6:
            if (r8 <= 0) goto L_0x00ee;
        L_0x00e8:
            r6 = (long) r8;	 Catch:{ all -> 0x0085 }
            r0 = r18;
            r0.request(r6);	 Catch:{ all -> 0x0085 }
        L_0x00ee:
            r6 = 0;
            r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r3 == 0) goto L_0x00f6;
        L_0x00f4:
            if (r2 == 0) goto L_0x001a;
        L_0x00f6:
            monitor-enter(r18);	 Catch:{ all -> 0x0085 }
            r0 = r18;
            r2 = r0.missed;	 Catch:{ all -> 0x0114 }
            if (r2 != 0) goto L_0x010c;
        L_0x00fd:
            r2 = 0;
            r0 = r18;
            r0.emitting = r2;	 Catch:{ all -> 0x0114 }
            r3 = 1;
            monitor-exit(r18);	 Catch:{ all -> 0x0106 }
            goto L_0x000d;
        L_0x0106:
            r2 = move-exception;
        L_0x0107:
            monitor-exit(r18);	 Catch:{ all -> 0x0106 }
            throw r2;	 Catch:{ all -> 0x0109 }
        L_0x0109:
            r2 = move-exception;
            goto L_0x0087;
        L_0x010c:
            r2 = 0;
            r0 = r18;
            r0.missed = r2;	 Catch:{ all -> 0x0114 }
            monitor-exit(r18);	 Catch:{ all -> 0x0114 }
            goto L_0x001a;
        L_0x0114:
            r2 = move-exception;
            r3 = 0;
            goto L_0x0107;
        L_0x0117:
            r2 = move-exception;
            monitor-exit(r18);	 Catch:{ all -> 0x0117 }
            throw r2;
        L_0x011a:
            r6 = 0;
            goto L_0x00aa;
        L_0x011c:
            r3 = r8 + 1;
            r8 = r3;
            r3 = r6;
            goto L_0x0096;
        L_0x0122:
            r2 = r6;
            goto L_0x00e6;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorPublish.PublishSubscriber.dispatch():void");
        }

        final void init() {
            add(Subscriptions.create(new Action0() {
                public void call() {
                    PublishSubscriber.this.producers.getAndSet(PublishSubscriber.TERMINATED);
                    PublishSubscriber.this.current.compareAndSet(PublishSubscriber.this, null);
                }
            }));
        }

        public final void onCompleted() {
            if (this.terminalEvent == null) {
                this.terminalEvent = this.nl.completed();
                dispatch();
            }
        }

        public final void onError(Throwable th) {
            if (this.terminalEvent == null) {
                this.terminalEvent = this.nl.error(th);
                dispatch();
            }
        }

        public final void onNext(T t) {
            if (this.queue.offer(this.nl.next(t))) {
                dispatch();
            } else {
                onError(new MissingBackpressureException());
            }
        }

        public final void onStart() {
            request((long) RxRingBuffer.SIZE);
        }

        final void remove(InnerProducer<T> innerProducer) {
            InnerProducer[] innerProducerArr;
            Object obj;
            do {
                innerProducerArr = (InnerProducer[]) this.producers.get();
                if (innerProducerArr != EMPTY && innerProducerArr != TERMINATED) {
                    int i = -1;
                    int length = innerProducerArr.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        if (innerProducerArr[i2].equals(innerProducer)) {
                            i = i2;
                            break;
                        }
                    }
                    if (i < 0) {
                        return;
                    }
                    if (length == 1) {
                        obj = EMPTY;
                    } else {
                        obj = new InnerProducer[(length - 1)];
                        System.arraycopy(innerProducerArr, 0, obj, 0, i);
                        System.arraycopy(innerProducerArr, i + 1, obj, i, (length - i) - 1);
                    }
                } else {
                    return;
                }
            } while (!this.producers.compareAndSet(innerProducerArr, obj));
        }
    }

    private OperatorPublish(Observable$OnSubscribe<T> observable$OnSubscribe, Observable<? extends T> observable, AtomicReference<PublishSubscriber<T>> atomicReference) {
        super(observable$OnSubscribe);
        this.source = observable;
        this.current = atomicReference;
    }

    public static <T, R> Observable<R> create(Observable<? extends T> observable, Func1<? super Observable<T>, ? extends Observable<R>> func1) {
        return create(observable, func1, false);
    }

    public static <T, R> Observable<R> create(final Observable<? extends T> observable, final Func1<? super Observable<T>, ? extends Observable<R>> func1, final boolean z) {
        return create(new Observable$OnSubscribe<R>() {
            public final void call(final Subscriber<? super R> subscriber) {
                final Object onSubscribePublishMulticast = new OnSubscribePublishMulticast(RxRingBuffer.SIZE, z);
                AnonymousClass1 anonymousClass1 = new Subscriber<R>() {
                    public void onCompleted() {
                        onSubscribePublishMulticast.unsubscribe();
                        subscriber.onCompleted();
                    }

                    public void onError(Throwable th) {
                        onSubscribePublishMulticast.unsubscribe();
                        subscriber.onError(th);
                    }

                    public void onNext(R r) {
                        subscriber.onNext(r);
                    }

                    public void setProducer(Producer producer) {
                        subscriber.setProducer(producer);
                    }
                };
                subscriber.add(onSubscribePublishMulticast);
                subscriber.add(anonymousClass1);
                ((Observable) func1.call(Observable.create(onSubscribePublishMulticast))).unsafeSubscribe(anonymousClass1);
                observable.unsafeSubscribe(onSubscribePublishMulticast.subscriber());
            }
        });
    }

    public static <T> ConnectableObservable<T> create(Observable<? extends T> observable) {
        final AtomicReference atomicReference = new AtomicReference();
        return new OperatorPublish(new Observable$OnSubscribe<T>() {
            public final void call(Subscriber<? super T> subscriber) {
                while (true) {
                    PublishSubscriber publishSubscriber = (PublishSubscriber) atomicReference.get();
                    if (publishSubscriber == null || publishSubscriber.isUnsubscribed()) {
                        PublishSubscriber publishSubscriber2 = new PublishSubscriber(atomicReference);
                        publishSubscriber2.init();
                        if (atomicReference.compareAndSet(publishSubscriber, publishSubscriber2)) {
                            publishSubscriber = publishSubscriber2;
                        } else {
                            continue;
                        }
                    }
                    Producer innerProducer = new InnerProducer(publishSubscriber, subscriber);
                    if (publishSubscriber.add(innerProducer)) {
                        subscriber.add(innerProducer);
                        subscriber.setProducer(innerProducer);
                        return;
                    }
                }
            }
        }, observable, atomicReference);
    }

    public final void connect(Action1<? super Subscription> action1) {
        PublishSubscriber publishSubscriber;
        PublishSubscriber publishSubscriber2;
        do {
            publishSubscriber = (PublishSubscriber) this.current.get();
            if (publishSubscriber != null && !publishSubscriber.isUnsubscribed()) {
                break;
            }
            publishSubscriber2 = new PublishSubscriber(this.current);
            publishSubscriber2.init();
        } while (!this.current.compareAndSet(publishSubscriber, publishSubscriber2));
        publishSubscriber = publishSubscriber2;
        boolean z = !publishSubscriber.shouldConnect.get() && publishSubscriber.shouldConnect.compareAndSet(false, true);
        action1.call(publishSubscriber);
        if (z) {
            this.source.unsafeSubscribe(publishSubscriber);
        }
    }
}
