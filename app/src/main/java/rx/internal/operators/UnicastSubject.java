package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.internal.util.atomic.SpscLinkedAtomicQueue;
import rx.internal.util.atomic.SpscUnboundedAtomicArrayQueue;
import rx.internal.util.unsafe.SpscLinkedQueue;
import rx.internal.util.unsafe.SpscUnboundedArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

public final class UnicastSubject<T> extends Subject<T, T> {
    final State<T> state;

    static final class State<T> extends AtomicLong implements Observable$OnSubscribe<T>, Observer<T>, Producer, Action0 {
        private static final long serialVersionUID = -9044104859202255786L;
        volatile boolean caughtUp;
        volatile boolean done;
        boolean emitting;
        Throwable error;
        boolean missed;
        final NotificationLite<T> nl = NotificationLite.instance();
        final Queue<Object> queue;
        final AtomicReference<Subscriber<? super T>> subscriber = new AtomicReference();
        final AtomicReference<Action0> terminateOnce;

        public State(int i, Action0 action0) {
            this.terminateOnce = action0 != null ? new AtomicReference(action0) : null;
            Queue spscUnboundedArrayQueue = i > 1 ? UnsafeAccess.isUnsafeAvailable() ? new SpscUnboundedArrayQueue(i) : new SpscUnboundedAtomicArrayQueue(i) : UnsafeAccess.isUnsafeAvailable() ? new SpscLinkedQueue() : new SpscLinkedAtomicQueue();
            this.queue = spscUnboundedArrayQueue;
        }

        public final void call() {
            doTerminate();
            this.done = true;
            synchronized (this) {
                if (this.emitting) {
                    return;
                }
                this.emitting = true;
                this.queue.clear();
            }
        }

        public final void call(Subscriber<? super T> subscriber) {
            if (this.subscriber.compareAndSet(null, subscriber)) {
                subscriber.add(Subscriptions.create(this));
                subscriber.setProducer(this);
                return;
            }
            subscriber.onError(new IllegalStateException("Only a single subscriber is allowed"));
        }

        final boolean checkTerminated(boolean z, boolean z2, Subscriber<? super T> subscriber) {
            if (subscriber.isUnsubscribed()) {
                this.queue.clear();
                return true;
            }
            if (z) {
                Throwable th = this.error;
                if (th != null) {
                    this.queue.clear();
                    subscriber.onError(th);
                    return true;
                } else if (z2) {
                    subscriber.onCompleted();
                    return true;
                }
            }
            return false;
        }

        final void doTerminate() {
            AtomicReference atomicReference = this.terminateOnce;
            if (atomicReference != null) {
                Action0 action0 = (Action0) atomicReference.get();
                if (action0 != null && atomicReference.compareAndSet(action0, null)) {
                    action0.call();
                }
            }
        }

        public final void onCompleted() {
            boolean z = true;
            if (!this.done) {
                doTerminate();
                this.done = true;
                if (!this.caughtUp) {
                    synchronized (this) {
                        if (this.caughtUp) {
                            z = false;
                        }
                    }
                    if (z) {
                        replay();
                        return;
                    }
                }
                ((Subscriber) this.subscriber.get()).onCompleted();
            }
        }

        public final void onError(Throwable th) {
            boolean z = true;
            if (!this.done) {
                doTerminate();
                this.error = th;
                this.done = true;
                if (!this.caughtUp) {
                    synchronized (this) {
                        if (this.caughtUp) {
                            z = false;
                        }
                    }
                    if (z) {
                        replay();
                        return;
                    }
                }
                ((Subscriber) this.subscriber.get()).onError(th);
            }
        }

        public final void onNext(T t) {
            if (!this.done) {
                if (!this.caughtUp) {
                    Object obj = null;
                    synchronized (this) {
                        if (!this.caughtUp) {
                            this.queue.offer(this.nl.next(t));
                            obj = 1;
                        }
                    }
                    if (obj != null) {
                        replay();
                        return;
                    }
                }
                Subscriber subscriber = (Subscriber) this.subscriber.get();
                try {
                    subscriber.onNext(t);
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, subscriber, t);
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void replay() {
            /*
            r10 = this;
            monitor-enter(r10);
            r0 = r10.emitting;	 Catch:{ all -> 0x0063 }
            if (r0 == 0) goto L_0x000a;
        L_0x0005:
            r0 = 1;
            r10.missed = r0;	 Catch:{ all -> 0x0063 }
            monitor-exit(r10);	 Catch:{ all -> 0x0063 }
        L_0x0009:
            return;
        L_0x000a:
            r0 = 1;
            r10.emitting = r0;	 Catch:{ all -> 0x0063 }
            monitor-exit(r10);	 Catch:{ all -> 0x0063 }
            r8 = r10.queue;
        L_0x0010:
            r0 = r10.subscriber;
            r0 = r0.get();
            r0 = (rx.Subscriber) r0;
            r1 = 0;
            if (r0 == 0) goto L_0x0085;
        L_0x001b:
            r1 = r10.done;
            r2 = r8.isEmpty();
            r1 = r10.checkTerminated(r1, r2, r0);
            if (r1 != 0) goto L_0x0009;
        L_0x0027:
            r2 = r10.get();
            r4 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
            r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r1 != 0) goto L_0x0066;
        L_0x0034:
            r1 = 1;
        L_0x0035:
            r4 = 0;
            r6 = r4;
            r4 = r2;
        L_0x0039:
            r2 = 0;
            r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
            if (r2 == 0) goto L_0x0079;
        L_0x003f:
            r3 = r10.done;
            r9 = r8.poll();
            if (r9 != 0) goto L_0x0068;
        L_0x0047:
            r2 = 1;
        L_0x0048:
            r3 = r10.checkTerminated(r3, r2, r0);
            if (r3 != 0) goto L_0x0009;
        L_0x004e:
            if (r2 != 0) goto L_0x0079;
        L_0x0050:
            r2 = r10.nl;
            r2 = r2.getValue(r9);
            r0.onNext(r2);	 Catch:{ Throwable -> 0x006a }
            r2 = 1;
            r2 = r4 - r2;
            r4 = 1;
            r4 = r4 + r6;
            r6 = r4;
            r4 = r2;
            goto L_0x0039;
        L_0x0063:
            r0 = move-exception;
            monitor-exit(r10);	 Catch:{ all -> 0x0063 }
            throw r0;
        L_0x0066:
            r1 = 0;
            goto L_0x0035;
        L_0x0068:
            r2 = 0;
            goto L_0x0048;
        L_0x006a:
            r1 = move-exception;
            r8.clear();
            rx.exceptions.Exceptions.throwIfFatal(r1);
            r1 = rx.exceptions.OnErrorThrowable.addValueAsLastCause(r1, r2);
            r0.onError(r1);
            goto L_0x0009;
        L_0x0079:
            if (r1 != 0) goto L_0x0085;
        L_0x007b:
            r2 = 0;
            r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
            if (r0 == 0) goto L_0x0085;
        L_0x0081:
            r2 = -r6;
            r10.addAndGet(r2);
        L_0x0085:
            monitor-enter(r10);
            r0 = r10.missed;	 Catch:{ all -> 0x009b }
            if (r0 != 0) goto L_0x009e;
        L_0x008a:
            if (r1 == 0) goto L_0x0095;
        L_0x008c:
            r0 = r8.isEmpty();	 Catch:{ all -> 0x009b }
            if (r0 == 0) goto L_0x0095;
        L_0x0092:
            r0 = 1;
            r10.caughtUp = r0;	 Catch:{ all -> 0x009b }
        L_0x0095:
            r0 = 0;
            r10.emitting = r0;	 Catch:{ all -> 0x009b }
            monitor-exit(r10);	 Catch:{ all -> 0x009b }
            goto L_0x0009;
        L_0x009b:
            r0 = move-exception;
            monitor-exit(r10);	 Catch:{ all -> 0x009b }
            throw r0;
        L_0x009e:
            r0 = 0;
            r10.missed = r0;	 Catch:{ all -> 0x009b }
            monitor-exit(r10);	 Catch:{ all -> 0x009b }
            goto L_0x0010;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.UnicastSubject.State.replay():void");
        }

        public final void request(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("n >= 0 required");
            } else if (j > 0) {
                BackpressureUtils.getAndAddRequest(this, j);
                replay();
            } else if (this.done) {
                replay();
            }
        }
    }

    private UnicastSubject(State<T> state) {
        super(state);
        this.state = state;
    }

    public static <T> UnicastSubject<T> create() {
        return create(16);
    }

    public static <T> UnicastSubject<T> create(int i) {
        return new UnicastSubject(new State(i, null));
    }

    public static <T> UnicastSubject<T> create(int i, Action0 action0) {
        return new UnicastSubject(new State(i, action0));
    }

    public final boolean hasObservers() {
        return this.state.subscriber.get() != null;
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
}
