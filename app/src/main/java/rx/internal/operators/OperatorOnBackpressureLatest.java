package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable$Operator;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;

public final class OperatorOnBackpressureLatest<T> implements Observable$Operator<T, T> {

    static final class Holder {
        static final OperatorOnBackpressureLatest<Object> INSTANCE = new OperatorOnBackpressureLatest();

        Holder() {
        }
    }

    static final class LatestEmitter<T> extends AtomicLong implements Observer<T>, Producer, Subscription {
        static final Object EMPTY = new Object();
        static final long NOT_REQUESTED = -4611686018427387904L;
        private static final long serialVersionUID = -1364393685005146274L;
        final Subscriber<? super T> child;
        volatile boolean done;
        boolean emitting;
        boolean missed;
        LatestSubscriber<? super T> parent;
        Throwable terminal;
        final AtomicReference<Object> value = new AtomicReference(EMPTY);

        public LatestEmitter(Subscriber<? super T> subscriber) {
            this.child = subscriber;
            lazySet(NOT_REQUESTED);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void emit() {
            /*
            r8 = this;
            r2 = 1;
            r1 = 0;
            monitor-enter(r8);
            r0 = r8.emitting;	 Catch:{ all -> 0x0068 }
            if (r0 == 0) goto L_0x000c;
        L_0x0007:
            r0 = 1;
            r8.missed = r0;	 Catch:{ all -> 0x0068 }
            monitor-exit(r8);	 Catch:{ all -> 0x0068 }
        L_0x000b:
            return;
        L_0x000c:
            r0 = 1;
            r8.emitting = r0;	 Catch:{ all -> 0x0068 }
            r0 = 0;
            r8.missed = r0;	 Catch:{ all -> 0x0068 }
            monitor-exit(r8);	 Catch:{ all -> 0x0068 }
        L_0x0013:
            r4 = r8.get();	 Catch:{ all -> 0x0071 }
            r6 = -9223372036854775808;
            r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r0 == 0) goto L_0x000b;
        L_0x001d:
            r0 = r8.value;	 Catch:{ all -> 0x0071 }
            r0 = r0.get();	 Catch:{ all -> 0x0071 }
            r6 = 0;
            r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r3 <= 0) goto L_0x0040;
        L_0x0029:
            r3 = EMPTY;	 Catch:{ all -> 0x0071 }
            if (r0 == r3) goto L_0x0040;
        L_0x002d:
            r3 = r8.child;	 Catch:{ all -> 0x0071 }
            r3.onNext(r0);	 Catch:{ all -> 0x0071 }
            r3 = r8.value;	 Catch:{ all -> 0x0071 }
            r4 = EMPTY;	 Catch:{ all -> 0x0071 }
            r3.compareAndSet(r0, r4);	 Catch:{ all -> 0x0071 }
            r4 = 1;
            r8.produced(r4);	 Catch:{ all -> 0x0071 }
            r0 = EMPTY;	 Catch:{ all -> 0x0071 }
        L_0x0040:
            r3 = EMPTY;	 Catch:{ all -> 0x0071 }
            if (r0 != r3) goto L_0x0051;
        L_0x0044:
            r0 = r8.done;	 Catch:{ all -> 0x0071 }
            if (r0 == 0) goto L_0x0051;
        L_0x0048:
            r0 = r8.terminal;	 Catch:{ all -> 0x0071 }
            if (r0 == 0) goto L_0x006b;
        L_0x004c:
            r3 = r8.child;	 Catch:{ all -> 0x0071 }
            r3.onError(r0);	 Catch:{ all -> 0x0071 }
        L_0x0051:
            monitor-enter(r8);	 Catch:{ all -> 0x0071 }
            r0 = r8.missed;	 Catch:{ all -> 0x0078 }
            if (r0 != 0) goto L_0x0073;
        L_0x0056:
            r0 = 0;
            r8.emitting = r0;	 Catch:{ all -> 0x0078 }
            monitor-exit(r8);	 Catch:{ all -> 0x005b }
            goto L_0x000b;
        L_0x005b:
            r0 = move-exception;
            r1 = r2;
        L_0x005d:
            monitor-exit(r8);	 Catch:{ all -> 0x007d }
            throw r0;	 Catch:{ all -> 0x005f }
        L_0x005f:
            r0 = move-exception;
        L_0x0060:
            if (r1 != 0) goto L_0x0067;
        L_0x0062:
            monitor-enter(r8);
            r1 = 0;
            r8.emitting = r1;	 Catch:{ all -> 0x007a }
            monitor-exit(r8);	 Catch:{ all -> 0x007a }
        L_0x0067:
            throw r0;
        L_0x0068:
            r0 = move-exception;
            monitor-exit(r8);	 Catch:{ all -> 0x0068 }
            throw r0;
        L_0x006b:
            r0 = r8.child;	 Catch:{ all -> 0x0071 }
            r0.onCompleted();	 Catch:{ all -> 0x0071 }
            goto L_0x0051;
        L_0x0071:
            r0 = move-exception;
            goto L_0x0060;
        L_0x0073:
            r0 = 0;
            r8.missed = r0;	 Catch:{ all -> 0x0078 }
            monitor-exit(r8);	 Catch:{ all -> 0x0078 }
            goto L_0x0013;
        L_0x0078:
            r0 = move-exception;
            goto L_0x005d;
        L_0x007a:
            r0 = move-exception;
            monitor-exit(r8);	 Catch:{ all -> 0x007a }
            throw r0;
        L_0x007d:
            r0 = move-exception;
            goto L_0x005d;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorOnBackpressureLatest.LatestEmitter.emit():void");
        }

        public final boolean isUnsubscribed() {
            return get() == Long.MIN_VALUE;
        }

        public final void onCompleted() {
            this.done = true;
            emit();
        }

        public final void onError(Throwable th) {
            this.terminal = th;
            this.done = true;
            emit();
        }

        public final void onNext(T t) {
            this.value.lazySet(t);
            emit();
        }

        final long produced(long j) {
            long j2;
            long j3;
            do {
                j3 = get();
                if (j3 < 0) {
                    return j3;
                }
                j2 = j3 - j;
            } while (!compareAndSet(j3, j2));
            return j2;
        }

        public final void request(long j) {
            if (j >= 0) {
                long j2;
                long j3;
                do {
                    j2 = get();
                    if (j2 != Long.MIN_VALUE) {
                        if (j2 == NOT_REQUESTED) {
                            j3 = j;
                        } else {
                            j3 = j2 + j;
                            if (j3 < 0) {
                                j3 = Long.MAX_VALUE;
                            }
                        }
                    } else {
                        return;
                    }
                } while (!compareAndSet(j2, j3));
                if (j2 == NOT_REQUESTED) {
                    this.parent.requestMore(Long.MAX_VALUE);
                }
                emit();
            }
        }

        public final void unsubscribe() {
            if (get() >= 0) {
                getAndSet(Long.MIN_VALUE);
            }
        }
    }

    static final class LatestSubscriber<T> extends Subscriber<T> {
        private final LatestEmitter<T> producer;

        LatestSubscriber(LatestEmitter<T> latestEmitter) {
            this.producer = latestEmitter;
        }

        public final void onCompleted() {
            this.producer.onCompleted();
        }

        public final void onError(Throwable th) {
            this.producer.onError(th);
        }

        public final void onNext(T t) {
            this.producer.onNext(t);
        }

        public final void onStart() {
            request(0);
        }

        final void requestMore(long j) {
            request(j);
        }
    }

    public static <T> OperatorOnBackpressureLatest<T> instance() {
        return Holder.INSTANCE;
    }

    public final Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        Producer latestEmitter = new LatestEmitter(subscriber);
        LatestSubscriber latestSubscriber = new LatestSubscriber(latestEmitter);
        latestEmitter.parent = latestSubscriber;
        subscriber.add(latestSubscriber);
        subscriber.add(latestEmitter);
        subscriber.setProducer(latestEmitter);
        return latestSubscriber;
    }
}
