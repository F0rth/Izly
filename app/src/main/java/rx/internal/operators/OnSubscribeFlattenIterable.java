package rx.internal.operators;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.exceptions.MissingBackpressureException;
import rx.functions.Func1;
import rx.internal.util.ExceptionsUtils;
import rx.internal.util.RxJavaPluginUtils;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.ScalarSynchronousObservable;
import rx.internal.util.atomic.SpscAtomicArrayQueue;
import rx.internal.util.atomic.SpscLinkedArrayQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;

public final class OnSubscribeFlattenIterable<T, R> implements Observable$OnSubscribe<R> {
    final Func1<? super T, ? extends Iterable<? extends R>> mapper;
    final int prefetch;
    final Observable<? extends T> source;

    static final class FlattenIterableSubscriber<T, R> extends Subscriber<T> {
        Iterator<? extends R> active;
        final Subscriber<? super R> actual;
        volatile boolean done;
        final AtomicReference<Throwable> error = new AtomicReference();
        final long limit;
        final Func1<? super T, ? extends Iterable<? extends R>> mapper;
        final NotificationLite<T> nl = NotificationLite.instance();
        long produced;
        final Queue<Object> queue;
        final AtomicLong requested = new AtomicLong();
        final AtomicInteger wip = new AtomicInteger();

        public FlattenIterableSubscriber(Subscriber<? super R> subscriber, Func1<? super T, ? extends Iterable<? extends R>> func1, int i) {
            this.actual = subscriber;
            this.mapper = func1;
            if (i == Integer.MAX_VALUE) {
                this.limit = Long.MAX_VALUE;
                this.queue = new SpscLinkedArrayQueue(RxRingBuffer.SIZE);
            } else {
                this.limit = (long) (i - (i >> 2));
                if (UnsafeAccess.isUnsafeAvailable()) {
                    this.queue = new SpscArrayQueue(i);
                } else {
                    this.queue = new SpscAtomicArrayQueue(i);
                }
            }
            request((long) i);
        }

        final boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, Queue<?> queue) {
            if (subscriber.isUnsubscribed()) {
                queue.clear();
                this.active = null;
                return true;
            }
            if (z) {
                if (((Throwable) this.error.get()) != null) {
                    Throwable terminate = ExceptionsUtils.terminate(this.error);
                    unsubscribe();
                    queue.clear();
                    this.active = null;
                    subscriber.onError(terminate);
                    return true;
                } else if (z2) {
                    subscriber.onCompleted();
                    return true;
                }
            }
            return false;
        }

        final void drain() {
            long j;
            if (this.wip.getAndIncrement() == 0) {
                Subscriber subscriber = this.actual;
                Queue queue = this.queue;
                int i = 1;
                while (true) {
                    boolean z;
                    boolean z2;
                    Iterator it;
                    long j2;
                    long j3;
                    int addAndGet;
                    Iterator it2 = this.active;
                    if (it2 == null) {
                        z = this.done;
                        Object poll = queue.poll();
                        z2 = poll == null;
                        if (!checkTerminated(z, z2, subscriber, queue)) {
                            if (!z2) {
                                j = this.produced + 1;
                                if (j == this.limit) {
                                    this.produced = 0;
                                    request(j);
                                } else {
                                    this.produced = j;
                                }
                                try {
                                    it = ((Iterable) this.mapper.call(this.nl.getValue(poll))).iterator();
                                    if (it.hasNext()) {
                                        this.active = it;
                                        if (it != null) {
                                            j2 = this.requested.get();
                                            j = 0;
                                            while (j != j2) {
                                                if (!checkTerminated(this.done, false, subscriber, queue)) {
                                                    try {
                                                        subscriber.onNext(it.next());
                                                        if (!checkTerminated(this.done, false, subscriber, queue)) {
                                                            j++;
                                                            try {
                                                                if (!it.hasNext()) {
                                                                    this.active = null;
                                                                    j3 = j;
                                                                    it2 = null;
                                                                    break;
                                                                }
                                                            } catch (Throwable th) {
                                                                Exceptions.throwIfFatal(th);
                                                                this.active = null;
                                                                onError(th);
                                                                j3 = j;
                                                                it2 = null;
                                                            }
                                                        } else {
                                                            return;
                                                        }
                                                    } catch (Throwable th2) {
                                                        Exceptions.throwIfFatal(th2);
                                                        this.active = null;
                                                        onError(th2);
                                                        j3 = j;
                                                        it2 = null;
                                                    }
                                                } else {
                                                    return;
                                                }
                                            }
                                            j3 = j;
                                            it2 = it;
                                            if (j3 == j2) {
                                                z = this.done;
                                                z2 = queue.isEmpty() && it2 == null;
                                                if (checkTerminated(z, z2, subscriber, queue)) {
                                                    return;
                                                }
                                            }
                                            if (j3 != 0) {
                                                BackpressureUtils.produced(this.requested, j3);
                                            }
                                            if (it2 == null) {
                                                continue;
                                            }
                                        }
                                        addAndGet = this.wip.addAndGet(-i);
                                        if (addAndGet != 0) {
                                            i = addAndGet;
                                        } else {
                                            return;
                                        }
                                    }
                                    continue;
                                } catch (Throwable th22) {
                                    Exceptions.throwIfFatal(th22);
                                    onError(th22);
                                }
                            }
                        } else {
                            return;
                        }
                    }
                    it = it2;
                    if (it != null) {
                        j2 = this.requested.get();
                        j = 0;
                        while (j != j2) {
                            if (!checkTerminated(this.done, false, subscriber, queue)) {
                                subscriber.onNext(it.next());
                                if (!checkTerminated(this.done, false, subscriber, queue)) {
                                    j++;
                                    if (it.hasNext()) {
                                        this.active = null;
                                        j3 = j;
                                        it2 = null;
                                        break;
                                    }
                                }
                                return;
                            }
                            return;
                        }
                        j3 = j;
                        it2 = it;
                        if (j3 == j2) {
                            z = this.done;
                            if (!queue.isEmpty()) {
                            }
                            if (checkTerminated(z, z2, subscriber, queue)) {
                                return;
                            }
                        }
                        if (j3 != 0) {
                            BackpressureUtils.produced(this.requested, j3);
                        }
                        if (it2 == null) {
                            continue;
                        }
                    }
                    addAndGet = this.wip.addAndGet(-i);
                    if (addAndGet != 0) {
                        i = addAndGet;
                    } else {
                        return;
                    }
                }
            }
        }

        public final void onCompleted() {
            this.done = true;
            drain();
        }

        public final void onError(Throwable th) {
            if (ExceptionsUtils.addThrowable(this.error, th)) {
                this.done = true;
                drain();
                return;
            }
            RxJavaPluginUtils.handleException(th);
        }

        public final void onNext(T t) {
            if (this.queue.offer(this.nl.next(t))) {
                drain();
                return;
            }
            unsubscribe();
            onError(new MissingBackpressureException());
        }

        final void requestMore(long j) {
            if (j > 0) {
                BackpressureUtils.getAndAddRequest(this.requested, j);
                drain();
            } else if (j < 0) {
                throw new IllegalStateException("n >= 0 required but it was " + j);
            }
        }
    }

    static final class OnSubscribeScalarFlattenIterable<T, R> implements Observable$OnSubscribe<R> {
        final Func1<? super T, ? extends Iterable<? extends R>> mapper;
        final T value;

        public OnSubscribeScalarFlattenIterable(T t, Func1<? super T, ? extends Iterable<? extends R>> func1) {
            this.value = t;
            this.mapper = func1;
        }

        public final void call(Subscriber<? super R> subscriber) {
            try {
                Iterator it = ((Iterable) this.mapper.call(this.value)).iterator();
                if (it.hasNext()) {
                    subscriber.setProducer(new IterableProducer(subscriber, it));
                } else {
                    subscriber.onCompleted();
                }
            } catch (Throwable th) {
                Exceptions.throwOrReport(th, subscriber, this.value);
            }
        }
    }

    protected OnSubscribeFlattenIterable(Observable<? extends T> observable, Func1<? super T, ? extends Iterable<? extends R>> func1, int i) {
        this.source = observable;
        this.mapper = func1;
        this.prefetch = i;
    }

    public static <T, R> Observable<R> createFrom(Observable<? extends T> observable, Func1<? super T, ? extends Iterable<? extends R>> func1, int i) {
        return observable instanceof ScalarSynchronousObservable ? Observable.create(new OnSubscribeScalarFlattenIterable(((ScalarSynchronousObservable) observable).get(), func1)) : Observable.create(new OnSubscribeFlattenIterable(observable, func1, i));
    }

    public final void call(Subscriber<? super R> subscriber) {
        final FlattenIterableSubscriber flattenIterableSubscriber = new FlattenIterableSubscriber(subscriber, this.mapper, this.prefetch);
        subscriber.add(flattenIterableSubscriber);
        subscriber.setProducer(new Producer() {
            public void request(long j) {
                flattenIterableSubscriber.requestMore(j);
            }
        });
        this.source.unsafeSubscribe(flattenIterableSubscriber);
    }
}
