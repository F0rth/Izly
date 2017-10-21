package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.MissingBackpressureException;
import rx.internal.util.atomic.SpscAtomicArrayQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;

public final class OnSubscribePublishMulticast<T> extends AtomicInteger implements Observable$OnSubscribe<T>, Observer<T>, Subscription {
    static final PublishProducer<?>[] EMPTY = new PublishProducer[0];
    static final PublishProducer<?>[] TERMINATED = new PublishProducer[0];
    private static final long serialVersionUID = -3741892510772238743L;
    final boolean delayError;
    volatile boolean done;
    Throwable error;
    final ParentSubscriber<T> parent;
    final int prefetch;
    volatile Producer producer;
    final Queue<T> queue;
    volatile PublishProducer<T>[] subscribers;

    static final class ParentSubscriber<T> extends Subscriber<T> {
        final OnSubscribePublishMulticast<T> state;

        public ParentSubscriber(OnSubscribePublishMulticast<T> onSubscribePublishMulticast) {
            this.state = onSubscribePublishMulticast;
        }

        public final void onCompleted() {
            this.state.onCompleted();
        }

        public final void onError(Throwable th) {
            this.state.onError(th);
        }

        public final void onNext(T t) {
            this.state.onNext(t);
        }

        public final void setProducer(Producer producer) {
            this.state.setProducer(producer);
        }
    }

    static final class PublishProducer<T> extends AtomicLong implements Producer, Subscription {
        private static final long serialVersionUID = 960704844171597367L;
        final Subscriber<? super T> actual;
        final AtomicBoolean once = new AtomicBoolean();
        final OnSubscribePublishMulticast<T> parent;

        public PublishProducer(Subscriber<? super T> subscriber, OnSubscribePublishMulticast<T> onSubscribePublishMulticast) {
            this.actual = subscriber;
            this.parent = onSubscribePublishMulticast;
        }

        public final boolean isUnsubscribed() {
            return this.once.get();
        }

        public final void request(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("n >= 0 required but it was " + j);
            } else if (j != 0) {
                BackpressureUtils.getAndAddRequest(this, j);
                this.parent.drain();
            }
        }

        public final void unsubscribe() {
            if (this.once.compareAndSet(false, true)) {
                this.parent.remove(this);
            }
        }
    }

    public OnSubscribePublishMulticast(int i, boolean z) {
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.prefetch = i;
        this.delayError = z;
        if (UnsafeAccess.isUnsafeAvailable()) {
            this.queue = new SpscArrayQueue(i);
        } else {
            this.queue = new SpscAtomicArrayQueue(i);
        }
        this.subscribers = EMPTY;
        this.parent = new ParentSubscriber(this);
    }

    final boolean add(PublishProducer<T> publishProducer) {
        if (this.subscribers == TERMINATED) {
            return false;
        }
        synchronized (this) {
            Object obj = this.subscribers;
            if (obj == TERMINATED) {
                return false;
            }
            int length = obj.length;
            Object obj2 = new PublishProducer[(length + 1)];
            System.arraycopy(obj, 0, obj2, 0, length);
            obj2[length] = publishProducer;
            this.subscribers = obj2;
            return true;
        }
    }

    public final void call(Subscriber<? super T> subscriber) {
        PublishProducer publishProducer = new PublishProducer(subscriber, this);
        subscriber.add(publishProducer);
        subscriber.setProducer(publishProducer);
        if (!add(publishProducer)) {
            Throwable th = this.error;
            if (th != null) {
                subscriber.onError(th);
            } else {
                subscriber.onCompleted();
            }
        } else if (publishProducer.isUnsubscribed()) {
            remove(publishProducer);
        } else {
            drain();
        }
    }

    final boolean checkTerminated(boolean z, boolean z2) {
        int i = 0;
        if (z) {
            int length;
            PublishProducer[] terminate;
            int length2;
            if (!this.delayError) {
                Throwable th = this.error;
                if (th != null) {
                    this.queue.clear();
                    PublishProducer[] terminate2 = terminate();
                    length = terminate2.length;
                    while (i < length) {
                        terminate2[i].actual.onError(th);
                        i++;
                    }
                    return true;
                } else if (z2) {
                    terminate = terminate();
                    length2 = terminate.length;
                    while (i < length2) {
                        terminate[i].actual.onCompleted();
                        i++;
                    }
                    return true;
                }
            } else if (z2) {
                terminate = terminate();
                Throwable th2 = this.error;
                if (th2 != null) {
                    length = terminate.length;
                    while (i < length) {
                        terminate[i].actual.onError(th2);
                        i++;
                    }
                    return true;
                }
                length2 = terminate.length;
                while (i < length2) {
                    terminate[i].actual.onCompleted();
                    i++;
                }
                return true;
            }
        }
        return false;
    }

    final void drain() {
        if (getAndIncrement() == 0) {
            Queue queue = this.queue;
            int i = 0;
            while (true) {
                PublishProducer[] publishProducerArr = this.subscribers;
                int length = publishProducerArr.length;
                int length2 = publishProducerArr.length;
                long j = Long.MAX_VALUE;
                int i2 = 0;
                while (i2 < length2) {
                    i2++;
                    j = Math.min(j, publishProducerArr[i2].get());
                }
                if (length != 0) {
                    long j2 = 0;
                    while (j2 != j) {
                        boolean z = this.done;
                        Object poll = queue.poll();
                        boolean z2 = poll == null;
                        if (!checkTerminated(z, z2)) {
                            if (z2) {
                                break;
                            }
                            for (PublishProducer publishProducer : publishProducerArr) {
                                publishProducer.actual.onNext(poll);
                            }
                            j2 = 1 + j2;
                        } else {
                            return;
                        }
                    }
                    if (j2 != j || !checkTerminated(this.done, queue.isEmpty())) {
                        if (j2 != 0) {
                            Producer producer = this.producer;
                            if (producer != null) {
                                producer.request(j2);
                            }
                            for (AtomicLong produced : publishProducerArr) {
                                BackpressureUtils.produced(produced, j2);
                            }
                        }
                    } else {
                        return;
                    }
                }
                i = addAndGet(-i);
                if (i == 0) {
                    return;
                }
            }
        }
    }

    public final boolean isUnsubscribed() {
        return this.parent.isUnsubscribed();
    }

    public final void onCompleted() {
        this.done = true;
        drain();
    }

    public final void onError(Throwable th) {
        this.error = th;
        this.done = true;
        drain();
    }

    public final void onNext(T t) {
        if (!this.queue.offer(t)) {
            this.parent.unsubscribe();
            this.error = new MissingBackpressureException("Queue full?!");
            this.done = true;
        }
        drain();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final void remove(rx.internal.operators.OnSubscribePublishMulticast.PublishProducer<T> r7) {
        /*
        r6 = this;
        r0 = 0;
        r1 = r6.subscribers;
        r2 = TERMINATED;
        if (r1 == r2) goto L_0x000b;
    L_0x0007:
        r2 = EMPTY;
        if (r1 != r2) goto L_0x000c;
    L_0x000b:
        return;
    L_0x000c:
        monitor-enter(r6);
        r2 = r6.subscribers;	 Catch:{ all -> 0x0019 }
        r1 = TERMINATED;	 Catch:{ all -> 0x0019 }
        if (r2 == r1) goto L_0x0017;
    L_0x0013:
        r1 = EMPTY;	 Catch:{ all -> 0x0019 }
        if (r2 != r1) goto L_0x001c;
    L_0x0017:
        monitor-exit(r6);	 Catch:{ all -> 0x0019 }
        goto L_0x000b;
    L_0x0019:
        r0 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0019 }
        throw r0;
    L_0x001c:
        r1 = -1;
        r3 = r2.length;	 Catch:{ all -> 0x0019 }
    L_0x001e:
        if (r0 >= r3) goto L_0x0025;
    L_0x0020:
        r4 = r2[r0];
        if (r4 != r7) goto L_0x0046;
    L_0x0024:
        r1 = r0;
    L_0x0025:
        if (r1 >= 0) goto L_0x0029;
    L_0x0027:
        monitor-exit(r6);	 Catch:{ all -> 0x0019 }
        goto L_0x000b;
    L_0x0029:
        r0 = 1;
        if (r3 != r0) goto L_0x0034;
    L_0x002c:
        r0 = EMPTY;	 Catch:{ all -> 0x0019 }
        r0 = (rx.internal.operators.OnSubscribePublishMulticast.PublishProducer[]) r0;	 Catch:{ all -> 0x0019 }
    L_0x0030:
        r6.subscribers = r0;	 Catch:{ all -> 0x0019 }
        monitor-exit(r6);	 Catch:{ all -> 0x0019 }
        goto L_0x000b;
    L_0x0034:
        r0 = r3 + -1;
        r0 = new rx.internal.operators.OnSubscribePublishMulticast.PublishProducer[r0];	 Catch:{ all -> 0x0019 }
        r4 = 0;
        r5 = 0;
        java.lang.System.arraycopy(r2, r4, r0, r5, r1);	 Catch:{ all -> 0x0019 }
        r4 = r1 + 1;
        r3 = r3 - r1;
        r3 = r3 + -1;
        java.lang.System.arraycopy(r2, r4, r0, r1, r3);	 Catch:{ all -> 0x0019 }
        goto L_0x0030;
    L_0x0046:
        r0 = r0 + 1;
        goto L_0x001e;
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OnSubscribePublishMulticast.remove(rx.internal.operators.OnSubscribePublishMulticast$PublishProducer):void");
    }

    final void setProducer(Producer producer) {
        this.producer = producer;
        producer.request((long) this.prefetch);
    }

    public final Subscriber<T> subscriber() {
        return this.parent;
    }

    final PublishProducer<T>[] terminate() {
        PublishProducer<T>[] publishProducerArr = this.subscribers;
        if (publishProducerArr == TERMINATED) {
            return publishProducerArr;
        }
        PublishProducer<T>[] publishProducerArr2;
        synchronized (this) {
            publishProducerArr2 = this.subscribers;
            if (publishProducerArr2 != TERMINATED) {
                this.subscribers = TERMINATED;
            }
        }
        return publishProducerArr2;
    }

    public final void unsubscribe() {
        this.parent.unsubscribe();
    }
}
