package rx.internal.operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.functions.FuncN;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.atomic.SpscLinkedArrayQueue;
import rx.plugins.RxJavaPlugins;

public final class OnSubscribeCombineLatest<T, R> implements Observable$OnSubscribe<R> {
    final int bufferSize;
    final FuncN<? extends R> combiner;
    final boolean delayError;
    final Observable<? extends T>[] sources;
    final Iterable<? extends Observable<? extends T>> sourcesIterable;

    static final class CombinerSubscriber<T, R> extends Subscriber<T> {
        boolean done;
        final int index;
        final NotificationLite<T> nl = NotificationLite.instance();
        final LatestCoordinator<T, R> parent;

        public CombinerSubscriber(LatestCoordinator<T, R> latestCoordinator, int i) {
            this.parent = latestCoordinator;
            this.index = i;
            request((long) latestCoordinator.bufferSize);
        }

        public final void onCompleted() {
            if (!this.done) {
                this.done = true;
                this.parent.combine(null, this.index);
            }
        }

        public final void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                return;
            }
            this.parent.onError(th);
            this.done = true;
            this.parent.combine(null, this.index);
        }

        public final void onNext(T t) {
            if (!this.done) {
                this.parent.combine(this.nl.next(t), this.index);
            }
        }

        public final void requestMore(long j) {
            request(j);
        }
    }

    static final class LatestCoordinator<T, R> extends AtomicInteger implements Producer, Subscription {
        static final Object MISSING = new Object();
        private static final long serialVersionUID = 8567835998786448817L;
        int active;
        final Subscriber<? super R> actual;
        final int bufferSize;
        volatile boolean cancelled;
        final FuncN<? extends R> combiner;
        int complete;
        final int count;
        final boolean delayError;
        volatile boolean done;
        final AtomicReference<Throwable> error = new AtomicReference();
        final Object[] latest;
        final SpscLinkedArrayQueue<Object> queue;
        final AtomicLong requested = new AtomicLong();
        final CombinerSubscriber<T, R>[] subscribers;

        public LatestCoordinator(Subscriber<? super R> subscriber, FuncN<? extends R> funcN, int i, int i2, boolean z) {
            this.actual = subscriber;
            this.combiner = funcN;
            this.count = i;
            this.bufferSize = i2;
            this.delayError = z;
            this.latest = new Object[i];
            Arrays.fill(this.latest, MISSING);
            this.subscribers = new CombinerSubscriber[i];
            this.queue = new SpscLinkedArrayQueue(i2);
        }

        final void cancel(Queue<?> queue) {
            queue.clear();
            for (CombinerSubscriber unsubscribe : this.subscribers) {
                unsubscribe.unsubscribe();
            }
        }

        final boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, Queue<?> queue, boolean z3) {
            if (this.cancelled) {
                cancel(queue);
                return true;
            }
            if (z) {
                Throwable th;
                if (!z3) {
                    th = (Throwable) this.error.get();
                    if (th != null) {
                        cancel(queue);
                        subscriber.onError(th);
                        return true;
                    } else if (z2) {
                        subscriber.onCompleted();
                        return true;
                    }
                } else if (z2) {
                    th = (Throwable) this.error.get();
                    if (th != null) {
                        subscriber.onError(th);
                    } else {
                        subscriber.onCompleted();
                    }
                    return true;
                }
            }
            return false;
        }

        final void combine(Object obj, int i) {
            Object obj2 = null;
            CombinerSubscriber combinerSubscriber = this.subscribers[i];
            synchronized (this) {
                int i2;
                int length = this.latest.length;
                Object obj3 = this.latest[i];
                int i3 = this.active;
                if (obj3 == MISSING) {
                    i3++;
                    this.active = i3;
                }
                int i4 = i3;
                i3 = this.complete;
                if (obj == null) {
                    i3++;
                    this.complete = i3;
                    i2 = i3;
                } else {
                    this.latest[i] = combinerSubscriber.nl.getValue(obj);
                    i2 = i3;
                }
                Object obj4 = i4 == length ? 1 : null;
                if (i2 == length || (obj == null && obj3 == MISSING)) {
                    obj2 = 1;
                }
                if (obj2 != null) {
                    this.done = true;
                } else if (obj != null && obj4 != null) {
                    this.queue.offer(combinerSubscriber, this.latest.clone());
                } else if (obj == null && this.error.get() != null && (obj3 == MISSING || !this.delayError)) {
                    this.done = true;
                }
            }
            if (obj4 != null || obj == null) {
                drain();
            } else {
                combinerSubscriber.requestMore(1);
            }
        }

        final void drain() {
            if (getAndIncrement() == 0) {
                Queue queue = this.queue;
                Subscriber subscriber = this.actual;
                boolean z = this.delayError;
                AtomicLong atomicLong = this.requested;
                int i = 1;
                while (!checkTerminated(this.done, queue.isEmpty(), subscriber, queue, z)) {
                    long j = atomicLong.get();
                    Object obj = j == Long.MAX_VALUE ? 1 : null;
                    long j2 = 0;
                    while (j != 0) {
                        boolean z2 = this.done;
                        CombinerSubscriber combinerSubscriber = (CombinerSubscriber) queue.peek();
                        boolean z3 = combinerSubscriber == null;
                        if (!checkTerminated(z2, z3, subscriber, queue, z)) {
                            if (z3) {
                                break;
                            }
                            queue.poll();
                            Object[] objArr = (Object[]) queue.poll();
                            if (objArr == null) {
                                this.cancelled = true;
                                cancel(queue);
                                subscriber.onError(new IllegalStateException("Broken queue?! Sender received but not the array."));
                                return;
                            }
                            try {
                                subscriber.onNext(this.combiner.call(objArr));
                                combinerSubscriber.requestMore(1);
                                j2--;
                                j--;
                            } catch (Throwable th) {
                                this.cancelled = true;
                                cancel(queue);
                                subscriber.onError(th);
                                return;
                            }
                        }
                        return;
                    }
                    if (j2 != 0 && obj == null) {
                        atomicLong.addAndGet(j2);
                    }
                    int addAndGet = addAndGet(-i);
                    if (addAndGet != 0) {
                        i = addAndGet;
                    } else {
                        return;
                    }
                }
            }
        }

        public final boolean isUnsubscribed() {
            return this.cancelled;
        }

        final void onError(Throwable th) {
            AtomicReference atomicReference = this.error;
            Throwable th2;
            Object compositeException;
            do {
                th2 = (Throwable) atomicReference.get();
                if (th2 == null) {
                    Throwable th3 = th;
                } else if (th2 instanceof CompositeException) {
                    Collection arrayList = new ArrayList(((CompositeException) th2).getExceptions());
                    arrayList.add(th);
                    compositeException = new CompositeException(arrayList);
                } else {
                    compositeException = new CompositeException(Arrays.asList(new Throwable[]{th2, th}));
                }
            } while (!atomicReference.compareAndSet(th2, compositeException));
        }

        public final void request(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("n >= required but it was " + j);
            } else if (j != 0) {
                BackpressureUtils.getAndAddRequest(this.requested, j);
                drain();
            }
        }

        public final void subscribe(Observable<? extends T>[] observableArr) {
            int i = 0;
            CombinerSubscriber[] combinerSubscriberArr = this.subscribers;
            int length = combinerSubscriberArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                combinerSubscriberArr[i2] = new CombinerSubscriber(this, i2);
            }
            lazySet(0);
            this.actual.add(this);
            this.actual.setProducer(this);
            while (i < length && !this.cancelled) {
                observableArr[i].subscribe(combinerSubscriberArr[i]);
                i++;
            }
        }

        public final void unsubscribe() {
            if (!this.cancelled) {
                this.cancelled = true;
                if (getAndIncrement() == 0) {
                    cancel(this.queue);
                }
            }
        }
    }

    public OnSubscribeCombineLatest(Iterable<? extends Observable<? extends T>> iterable, FuncN<? extends R> funcN) {
        this(null, iterable, funcN, RxRingBuffer.SIZE, false);
    }

    public OnSubscribeCombineLatest(Observable<? extends T>[] observableArr, Iterable<? extends Observable<? extends T>> iterable, FuncN<? extends R> funcN, int i, boolean z) {
        this.sources = observableArr;
        this.sourcesIterable = iterable;
        this.combiner = funcN;
        this.bufferSize = i;
        this.delayError = z;
    }

    public final void call(Subscriber<? super R> subscriber) {
        int length;
        Observable[] observableArr;
        Observable[] observableArr2 = this.sources;
        if (observableArr2 != null) {
            length = observableArr2.length;
            observableArr = observableArr2;
        } else if (this.sourcesIterable instanceof List) {
            List list = (List) this.sourcesIterable;
            observableArr2 = (Observable[]) list.toArray(new Observable[list.size()]);
            length = observableArr2.length;
            observableArr = observableArr2;
        } else {
            Object obj = new Observable[8];
            Object obj2 = obj;
            int i = 0;
            for (Observable observable : this.sourcesIterable) {
                if (i == obj2.length) {
                    Object obj3 = new Observable[((i >> 2) + i)];
                    System.arraycopy(obj2, 0, obj3, 0, i);
                    obj2 = obj3;
                }
                obj2[i] = observable;
                i++;
            }
            Object obj4 = obj2;
            length = i;
        }
        if (length == 0) {
            subscriber.onCompleted();
            return;
        }
        new LatestCoordinator(subscriber, this.combiner, length, this.bufferSize, this.delayError).subscribe(observableArr);
    }
}
