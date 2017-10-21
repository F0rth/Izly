package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observable$Operator;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.exceptions.MissingBackpressureException;
import rx.exceptions.OnErrorThrowable;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.ScalarSynchronousObservable;
import rx.internal.util.atomic.SpscAtomicArrayQueue;
import rx.internal.util.atomic.SpscExactAtomicArrayQueue;
import rx.internal.util.atomic.SpscUnboundedAtomicArrayQueue;
import rx.internal.util.unsafe.Pow2;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.subscriptions.CompositeSubscription;

public final class OperatorMerge<T> implements Observable$Operator<T, Observable<? extends T>> {
    final boolean delayErrors;
    final int maxConcurrent;

    static final class HolderDelayErrors {
        static final OperatorMerge<Object> INSTANCE = new OperatorMerge(true, Integer.MAX_VALUE);

        private HolderDelayErrors() {
        }
    }

    static final class HolderNoDelay {
        static final OperatorMerge<Object> INSTANCE = new OperatorMerge(false, Integer.MAX_VALUE);

        private HolderNoDelay() {
        }
    }

    static final class InnerSubscriber<T> extends Subscriber<T> {
        static final int limit = (RxRingBuffer.SIZE / 4);
        volatile boolean done;
        final long id;
        int outstanding;
        final MergeSubscriber<T> parent;
        volatile RxRingBuffer queue;

        public InnerSubscriber(MergeSubscriber<T> mergeSubscriber, long j) {
            this.parent = mergeSubscriber;
            this.id = j;
        }

        public final void onCompleted() {
            this.done = true;
            this.parent.emit();
        }

        public final void onError(Throwable th) {
            this.done = true;
            this.parent.getOrCreateErrorQueue().offer(th);
            this.parent.emit();
        }

        public final void onNext(T t) {
            this.parent.tryEmit(this, t);
        }

        public final void onStart() {
            this.outstanding = RxRingBuffer.SIZE;
            request((long) RxRingBuffer.SIZE);
        }

        public final void requestMore(long j) {
            int i = this.outstanding - ((int) j);
            if (i > limit) {
                this.outstanding = i;
                return;
            }
            this.outstanding = RxRingBuffer.SIZE;
            i = RxRingBuffer.SIZE - i;
            if (i > 0) {
                request((long) i);
            }
        }
    }

    static final class MergeProducer<T> extends AtomicLong implements Producer {
        private static final long serialVersionUID = -1214379189873595503L;
        final MergeSubscriber<T> subscriber;

        public MergeProducer(MergeSubscriber<T> mergeSubscriber) {
            this.subscriber = mergeSubscriber;
        }

        public final long produced(int i) {
            return addAndGet((long) (-i));
        }

        public final void request(long j) {
            if (j > 0) {
                if (get() != Long.MAX_VALUE) {
                    BackpressureUtils.getAndAddRequest(this, j);
                    this.subscriber.emit();
                }
            } else if (j < 0) {
                throw new IllegalArgumentException("n >= 0 required");
            }
        }
    }

    static final class MergeSubscriber<T> extends Subscriber<Observable<? extends T>> {
        static final InnerSubscriber<?>[] EMPTY = new InnerSubscriber[0];
        final Subscriber<? super T> child;
        final boolean delayErrors;
        volatile boolean done;
        boolean emitting;
        volatile ConcurrentLinkedQueue<Throwable> errors;
        final Object innerGuard = new Object();
        volatile InnerSubscriber<?>[] innerSubscribers = EMPTY;
        long lastId;
        int lastIndex;
        final int maxConcurrent;
        boolean missed;
        final NotificationLite<T> nl = NotificationLite.instance();
        MergeProducer<T> producer;
        volatile Queue<Object> queue;
        int scalarEmissionCount;
        final int scalarEmissionLimit;
        volatile CompositeSubscription subscriptions;
        long uniqueId;

        public MergeSubscriber(Subscriber<? super T> subscriber, boolean z, int i) {
            this.child = subscriber;
            this.delayErrors = z;
            this.maxConcurrent = i;
            if (i == Integer.MAX_VALUE) {
                this.scalarEmissionLimit = Integer.MAX_VALUE;
                request(Long.MAX_VALUE);
                return;
            }
            this.scalarEmissionLimit = Math.max(1, i >> 1);
            request((long) i);
        }

        private void reportError() {
            Collection arrayList = new ArrayList(this.errors);
            if (arrayList.size() == 1) {
                this.child.onError((Throwable) arrayList.get(0));
            } else {
                this.child.onError(new CompositeException(arrayList));
            }
        }

        final void addInner(InnerSubscriber<T> innerSubscriber) {
            getOrCreateComposite().add(innerSubscriber);
            synchronized (this.innerGuard) {
                Object obj = this.innerSubscribers;
                int length = obj.length;
                Object obj2 = new InnerSubscriber[(length + 1)];
                System.arraycopy(obj, 0, obj2, 0, length);
                obj2[length] = innerSubscriber;
                this.innerSubscribers = obj2;
            }
        }

        final boolean checkTerminate() {
            if (this.child.isUnsubscribed()) {
                return true;
            }
            Queue queue = this.errors;
            if (this.delayErrors || queue == null || queue.isEmpty()) {
                return false;
            }
            try {
                reportError();
                return true;
            } finally {
                unsubscribe();
            }
        }

        final void emit() {
            synchronized (this) {
                if (this.emitting) {
                    this.missed = true;
                    return;
                }
                this.emitting = true;
                emitLoop();
            }
        }

        final void emitEmpty() {
            int i = this.scalarEmissionCount + 1;
            if (i == this.scalarEmissionLimit) {
                this.scalarEmissionCount = 0;
                requestMore((long) i);
                return;
            }
            this.scalarEmissionCount = i;
        }

        final void emitLoop() {
            Throwable th;
            Subscriber subscriber = this.child;
            while (!checkTerminate()) {
                int i;
                Queue queue = this.queue;
                long j = this.producer.get();
                Object obj = j == Long.MAX_VALUE ? 1 : null;
                int i2 = 0;
                if (queue != null) {
                    i2 = 0;
                    Object obj2;
                    do {
                        i = 0;
                        obj2 = null;
                        while (j > 0) {
                            obj2 = queue.poll();
                            if (!checkTerminate()) {
                                if (obj2 == null) {
                                    break;
                                }
                                try {
                                    subscriber.onNext(this.nl.getValue(obj2));
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                                j--;
                                i++;
                                i2++;
                            } else {
                                return;
                            }
                        }
                        if (i > 0) {
                            j = obj != null ? Long.MAX_VALUE : this.producer.produced(i);
                        }
                        if (j == 0) {
                            break;
                        }
                    } while (obj2 != null);
                }
                boolean z = this.done;
                Queue queue2 = this.queue;
                InnerSubscriber[] innerSubscriberArr = this.innerSubscribers;
                int length = innerSubscriberArr.length;
                if (z && ((queue2 == null || queue2.isEmpty()) && length == 0)) {
                    Queue queue3 = this.errors;
                    if (queue3 == null || queue3.isEmpty()) {
                        subscriber.onCompleted();
                        return;
                    } else {
                        reportError();
                        return;
                    }
                }
                Object obj3;
                int i3;
                if (length > 0) {
                    long j2 = this.lastId;
                    int i4 = this.lastIndex;
                    if (length <= i4 || innerSubscriberArr[i4].id != j2) {
                        if (length <= i4) {
                            i4 = 0;
                        }
                        for (i = 0; i < length && innerSubscriberArr[i4].id != j2; i++) {
                            i4++;
                            if (i4 == length) {
                                i4 = 0;
                            }
                        }
                        this.lastIndex = i4;
                        this.lastId = innerSubscriberArr[i4].id;
                    }
                    int i5 = 0;
                    int i6 = i4;
                    Object obj4 = null;
                    int i7 = i2;
                    long j3 = j;
                    while (i5 < length) {
                        if (!checkTerminate()) {
                            Object obj5;
                            boolean z2;
                            RxRingBuffer rxRingBuffer;
                            InnerSubscriber innerSubscriber = innerSubscriberArr[i6];
                            obj3 = null;
                            while (true) {
                                long j4 = j3;
                                obj5 = obj3;
                                i4 = 0;
                                j = j4;
                                while (j > 0) {
                                    if (!checkTerminate()) {
                                        RxRingBuffer rxRingBuffer2 = innerSubscriber.queue;
                                        if (rxRingBuffer2 == null) {
                                            break;
                                        }
                                        obj5 = rxRingBuffer2.poll();
                                        if (obj5 == null) {
                                            break;
                                        }
                                        try {
                                            subscriber.onNext(this.nl.getValue(obj5));
                                            i4++;
                                            j--;
                                        } catch (Throwable th3) {
                                            r5 = 1;
                                            Exceptions.throwIfFatal(th3);
                                            subscriber.onError(th3);
                                            return;
                                        } finally {
                                            unsubscribe();
                                        }
                                    } else {
                                        return;
                                    }
                                }
                                if (i4 > 0) {
                                    j = obj == null ? this.producer.produced(i4) : Long.MAX_VALUE;
                                    innerSubscriber.requestMore((long) i4);
                                }
                                if (j == 0 || obj5 == null) {
                                    z2 = innerSubscriber.done;
                                    rxRingBuffer = innerSubscriber.queue;
                                } else {
                                    Object obj6 = obj5;
                                    j3 = j;
                                    obj3 = obj6;
                                }
                            }
                            z2 = innerSubscriber.done;
                            rxRingBuffer = innerSubscriber.queue;
                            if (z2 && (rxRingBuffer == null || rxRingBuffer.isEmpty())) {
                                removeInner(innerSubscriber);
                                if (!checkTerminate()) {
                                    i4 = i7 + 1;
                                    obj5 = 1;
                                } else {
                                    return;
                                }
                            }
                            i4 = i7;
                            obj5 = obj4;
                            if (j == 0) {
                                obj3 = obj5;
                                i3 = i4;
                                break;
                            }
                            i = i6 + 1;
                            if (i == length) {
                                i = 0;
                            }
                            i5++;
                            i6 = i;
                            i7 = i4;
                            obj4 = obj5;
                            j3 = j;
                        } else {
                            return;
                        }
                    }
                    obj3 = obj4;
                    i3 = i7;
                    this.lastIndex = i6;
                    this.lastId = innerSubscriberArr[i6].id;
                } else {
                    obj3 = null;
                    i3 = i2;
                }
                if (i3 > 0) {
                    request((long) i3);
                }
                if (obj3 == null) {
                    synchronized (this) {
                        if (this.missed) {
                            try {
                                this.missed = false;
                            } catch (Throwable th4) {
                                th3 = th4;
                                r5 = null;
                            }
                        } else {
                            r5 = 1;
                            try {
                                this.emitting = false;
                                return;
                            } catch (Throwable th5) {
                                th3 = th5;
                                throw th3;
                            }
                        }
                    }
                }
            }
            return;
            if (r5 == null) {
                synchronized (this) {
                    this.emitting = false;
                }
            }
            throw th3;
        }

        protected final void emitScalar(T t, long j) {
            try {
                this.child.onNext(t);
            } catch (Throwable th) {
                r1 = th;
                r0 = 1;
            }
            if (j != Long.MAX_VALUE) {
                this.producer.produced(1);
            }
            int i = this.scalarEmissionCount + 1;
            if (i == this.scalarEmissionLimit) {
                this.scalarEmissionCount = 0;
                requestMore((long) i);
            } else {
                this.scalarEmissionCount = i;
            }
            synchronized (this) {
                if (this.missed) {
                    this.missed = false;
                    emitLoop();
                    return;
                }
                this.emitting = false;
                return;
            }
            if (r0 == null) {
                synchronized (this) {
                    this.emitting = false;
                }
            }
            throw r1;
        }

        protected final void emitScalar(InnerSubscriber<T> innerSubscriber, T t, long j) {
            try {
                this.child.onNext(t);
            } catch (Throwable th) {
                r1 = th;
                r0 = 1;
            }
            if (j != Long.MAX_VALUE) {
                this.producer.produced(1);
            }
            innerSubscriber.requestMore(1);
            synchronized (this) {
                if (this.missed) {
                    this.missed = false;
                    emitLoop();
                    return;
                }
                this.emitting = false;
                return;
            }
            if (r0 == null) {
                synchronized (this) {
                    this.emitting = false;
                }
            }
            throw r1;
        }

        final CompositeSubscription getOrCreateComposite() {
            CompositeSubscription compositeSubscription = this.subscriptions;
            if (compositeSubscription == null) {
                Object obj;
                synchronized (this) {
                    compositeSubscription = this.subscriptions;
                    if (compositeSubscription == null) {
                        compositeSubscription = new CompositeSubscription();
                        this.subscriptions = compositeSubscription;
                        obj = 1;
                    } else {
                        obj = null;
                    }
                }
                if (obj != null) {
                    add(compositeSubscription);
                }
            }
            return compositeSubscription;
        }

        final Queue<Throwable> getOrCreateErrorQueue() {
            Queue<Throwable> queue = this.errors;
            if (queue == null) {
                synchronized (this) {
                    queue = this.errors;
                    if (queue == null) {
                        queue = new ConcurrentLinkedQueue();
                        this.errors = queue;
                    }
                }
            }
            return queue;
        }

        public final void onCompleted() {
            this.done = true;
            emit();
        }

        public final void onError(Throwable th) {
            getOrCreateErrorQueue().offer(th);
            this.done = true;
            emit();
        }

        public final void onNext(Observable<? extends T> observable) {
            if (observable != null) {
                if (observable == Observable.empty()) {
                    emitEmpty();
                } else if (observable instanceof ScalarSynchronousObservable) {
                    tryEmit(((ScalarSynchronousObservable) observable).get());
                } else {
                    long j = this.uniqueId;
                    this.uniqueId = 1 + j;
                    InnerSubscriber innerSubscriber = new InnerSubscriber(this, j);
                    addInner(innerSubscriber);
                    observable.unsafeSubscribe(innerSubscriber);
                    emit();
                }
            }
        }

        protected final void queueScalar(T t) {
            Queue queue = this.queue;
            if (queue == null) {
                int i = this.maxConcurrent;
                queue = i == Integer.MAX_VALUE ? new SpscUnboundedAtomicArrayQueue(RxRingBuffer.SIZE) : Pow2.isPowerOfTwo(i) ? UnsafeAccess.isUnsafeAvailable() ? new SpscArrayQueue(i) : new SpscAtomicArrayQueue(i) : new SpscExactAtomicArrayQueue(i);
                this.queue = queue;
            }
            if (queue.offer(this.nl.next(t))) {
                emit();
                return;
            }
            unsubscribe();
            onError(OnErrorThrowable.addValueAsLastCause(new MissingBackpressureException(), t));
        }

        protected final void queueScalar(InnerSubscriber<T> innerSubscriber, T t) {
            RxRingBuffer rxRingBuffer = innerSubscriber.queue;
            if (rxRingBuffer == null) {
                rxRingBuffer = RxRingBuffer.getSpscInstance();
                innerSubscriber.add(rxRingBuffer);
                innerSubscriber.queue = rxRingBuffer;
            }
            try {
                rxRingBuffer.onNext(this.nl.next(t));
                emit();
            } catch (Throwable e) {
                innerSubscriber.unsubscribe();
                innerSubscriber.onError(e);
            } catch (Throwable e2) {
                if (!innerSubscriber.isUnsubscribed()) {
                    innerSubscriber.unsubscribe();
                    innerSubscriber.onError(e2);
                }
            }
        }

        final void removeInner(InnerSubscriber<T> innerSubscriber) {
            int i = 0;
            RxRingBuffer rxRingBuffer = innerSubscriber.queue;
            if (rxRingBuffer != null) {
                rxRingBuffer.release();
            }
            this.subscriptions.remove(innerSubscriber);
            synchronized (this.innerGuard) {
                Object obj = this.innerSubscribers;
                int length = obj.length;
                while (i < length) {
                    if (innerSubscriber.equals(obj[i])) {
                        break;
                    }
                    i++;
                }
                i = -1;
                if (i < 0) {
                } else if (length == 1) {
                    this.innerSubscribers = EMPTY;
                } else {
                    Object obj2 = new InnerSubscriber[(length - 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(obj, i + 1, obj2, i, (length - i) - 1);
                    this.innerSubscribers = obj2;
                }
            }
        }

        public final void requestMore(long j) {
            request(j);
        }

        final void tryEmit(T t) {
            Object obj = null;
            long j = this.producer.get();
            if (j != 0) {
                synchronized (this) {
                    j = this.producer.get();
                    if (!(this.emitting || j == 0)) {
                        this.emitting = true;
                        obj = 1;
                    }
                }
            }
            if (obj != null) {
                emitScalar(t, j);
            } else {
                queueScalar(t);
            }
        }

        final void tryEmit(InnerSubscriber<T> innerSubscriber, T t) {
            Object obj = null;
            long j = this.producer.get();
            if (j != 0) {
                synchronized (this) {
                    j = this.producer.get();
                    if (!(this.emitting || j == 0)) {
                        this.emitting = true;
                        obj = 1;
                    }
                }
            }
            if (obj != null) {
                emitScalar(innerSubscriber, t, j);
            } else {
                queueScalar(innerSubscriber, t);
            }
        }
    }

    OperatorMerge(boolean z, int i) {
        this.delayErrors = z;
        this.maxConcurrent = i;
    }

    public static <T> OperatorMerge<T> instance(boolean z) {
        return z ? HolderDelayErrors.INSTANCE : HolderNoDelay.INSTANCE;
    }

    public static <T> OperatorMerge<T> instance(boolean z, int i) {
        if (i > 0) {
            return i == Integer.MAX_VALUE ? instance(z) : new OperatorMerge(z, i);
        } else {
            throw new IllegalArgumentException("maxConcurrent > 0 required but it was " + i);
        }
    }

    public final Subscriber<Observable<? extends T>> call(Subscriber<? super T> subscriber) {
        MergeSubscriber mergeSubscriber = new MergeSubscriber(subscriber, this.delayErrors, this.maxConcurrent);
        Producer mergeProducer = new MergeProducer(mergeSubscriber);
        mergeSubscriber.producer = mergeProducer;
        subscriber.add(mergeSubscriber);
        subscriber.setProducer(mergeProducer);
        return mergeSubscriber;
    }
}
