package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable$Operator;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.internal.util.atomic.SpscLinkedAtomicQueue;
import rx.internal.util.unsafe.SpscLinkedQueue;
import rx.internal.util.unsafe.UnsafeAccess;

public final class OperatorScan<R, T> implements Observable$Operator<R, T> {
    private static final Object NO_INITIAL_VALUE = new Object();
    final Func2<R, ? super T, R> accumulator;
    private final Func0<R> initialValueFactory;

    class AnonymousClass1 implements Func0<R> {
        final /* synthetic */ Object val$initialValue;

        AnonymousClass1(Object obj) {
            this.val$initialValue = obj;
        }

        public R call() {
            return this.val$initialValue;
        }
    }

    static final class InitialProducer<R> implements Observer<R>, Producer {
        final Subscriber<? super R> child;
        volatile boolean done;
        boolean emitting;
        Throwable error;
        boolean missed;
        long missedRequested;
        volatile Producer producer;
        final Queue<Object> queue;
        final AtomicLong requested;

        public InitialProducer(R r, Subscriber<? super R> subscriber) {
            this.child = subscriber;
            Queue spscLinkedQueue = UnsafeAccess.isUnsafeAvailable() ? new SpscLinkedQueue() : new SpscLinkedAtomicQueue();
            this.queue = spscLinkedQueue;
            spscLinkedQueue.offer(NotificationLite.instance().next(r));
            this.requested = new AtomicLong();
        }

        final boolean checkTerminated(boolean z, boolean z2, Subscriber<? super R> subscriber) {
            if (subscriber.isUnsubscribed()) {
                return true;
            }
            if (z) {
                Throwable th = this.error;
                if (th != null) {
                    subscriber.onError(th);
                    return true;
                } else if (z2) {
                    subscriber.onCompleted();
                    return true;
                }
            }
            return false;
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

        final void emitLoop() {
            Subscriber subscriber = this.child;
            Queue queue = this.queue;
            NotificationLite instance = NotificationLite.instance();
            AtomicLong atomicLong = this.requested;
            long j = atomicLong.get();
            while (true) {
                long addAndGet;
                Object obj = j == Long.MAX_VALUE ? 1 : null;
                if (!checkTerminated(this.done, queue.isEmpty(), subscriber)) {
                    long j2 = j;
                    j = 0;
                    while (j2 != 0) {
                        boolean z = this.done;
                        Object poll = queue.poll();
                        boolean z2 = poll == null;
                        if (!checkTerminated(z, z2, subscriber)) {
                            if (z2) {
                                break;
                            }
                            Object value = instance.getValue(poll);
                            try {
                                subscriber.onNext(value);
                                j--;
                                j2--;
                            } catch (Throwable th) {
                                Exceptions.throwOrReport(th, subscriber, value);
                                return;
                            }
                        }
                        return;
                    }
                    addAndGet = (j == 0 || obj != null) ? j2 : atomicLong.addAndGet(j);
                    synchronized (this) {
                        if (this.missed) {
                            this.missed = false;
                        } else {
                            this.emitting = false;
                            return;
                        }
                    }
                }
                return;
                j = addAndGet;
            }
        }

        public final void onCompleted() {
            this.done = true;
            emit();
        }

        public final void onError(Throwable th) {
            this.error = th;
            this.done = true;
            emit();
        }

        public final void onNext(R r) {
            this.queue.offer(NotificationLite.instance().next(r));
            emit();
        }

        public final void request(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("n >= required but it was " + j);
            } else if (j != 0) {
                BackpressureUtils.getAndAddRequest(this.requested, j);
                Producer producer = this.producer;
                if (producer == null) {
                    synchronized (this.requested) {
                        producer = this.producer;
                        if (producer == null) {
                            this.missedRequested = BackpressureUtils.addCap(this.missedRequested, j);
                        }
                    }
                }
                if (producer != null) {
                    producer.request(j);
                }
                emit();
            }
        }

        public final void setProducer(Producer producer) {
            if (producer == null) {
                throw new NullPointerException();
            }
            synchronized (this.requested) {
                if (this.producer != null) {
                    throw new IllegalStateException("Can't set more than one Producer!");
                }
                long j = this.missedRequested;
                if (j != Long.MAX_VALUE) {
                    j--;
                }
                this.missedRequested = 0;
                this.producer = producer;
            }
            if (j > 0) {
                producer.request(j);
            }
            emit();
        }
    }

    public OperatorScan(R r, Func2<R, ? super T, R> func2) {
        this(new AnonymousClass1(r), (Func2) func2);
    }

    public OperatorScan(Func0<R> func0, Func2<R, ? super T, R> func2) {
        this.initialValueFactory = func0;
        this.accumulator = func2;
    }

    public OperatorScan(Func2<R, ? super T, R> func2) {
        this(NO_INITIAL_VALUE, (Func2) func2);
    }

    public final Subscriber<? super T> call(final Subscriber<? super R> subscriber) {
        final Object call = this.initialValueFactory.call();
        if (call == NO_INITIAL_VALUE) {
            return new Subscriber<T>(subscriber) {
                boolean once;
                R value;

                public void onCompleted() {
                    subscriber.onCompleted();
                }

                public void onError(Throwable th) {
                    subscriber.onError(th);
                }

                public void onNext(T t) {
                    Object call;
                    if (this.once) {
                        try {
                            call = OperatorScan.this.accumulator.call(this.value, t);
                        } catch (Throwable th) {
                            Exceptions.throwOrReport(th, subscriber, t);
                            return;
                        }
                    }
                    this.once = true;
                    this.value = call;
                    subscriber.onNext(call);
                }
            };
        }
        final Producer initialProducer = new InitialProducer(call, subscriber);
        AnonymousClass3 anonymousClass3 = new Subscriber<T>() {
            private R value = call;

            public void onCompleted() {
                initialProducer.onCompleted();
            }

            public void onError(Throwable th) {
                initialProducer.onError(th);
            }

            public void onNext(T t) {
                try {
                    Object call = OperatorScan.this.accumulator.call(this.value, t);
                    this.value = call;
                    initialProducer.onNext(call);
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, this, t);
                }
            }

            public void setProducer(Producer producer) {
                initialProducer.setProducer(producer);
            }
        };
        subscriber.add(anonymousClass3);
        subscriber.setProducer(initialProducer);
        return anonymousClass3;
    }
}
