package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.internal.util.LinkedArrayList;
import rx.subscriptions.SerialSubscription;

public final class CachedObservable<T> extends Observable<T> {
    private final CacheState<T> state;

    static final class CacheState<T> extends LinkedArrayList implements Observer<T> {
        static final ReplayProducer<?>[] EMPTY = new ReplayProducer[0];
        final SerialSubscription connection = new SerialSubscription();
        volatile boolean isConnected;
        final NotificationLite<T> nl = NotificationLite.instance();
        volatile ReplayProducer<?>[] producers = EMPTY;
        final Observable<? extends T> source;
        boolean sourceDone;

        public CacheState(Observable<? extends T> observable, int i) {
            super(i);
            this.source = observable;
        }

        public final void addProducer(ReplayProducer<T> replayProducer) {
            synchronized (this.connection) {
                Object obj = this.producers;
                int length = obj.length;
                Object obj2 = new ReplayProducer[(length + 1)];
                System.arraycopy(obj, 0, obj2, 0, length);
                obj2[length] = replayProducer;
                this.producers = obj2;
            }
        }

        public final void connect() {
            AnonymousClass1 anonymousClass1 = new Subscriber<T>() {
                public void onCompleted() {
                    CacheState.this.onCompleted();
                }

                public void onError(Throwable th) {
                    CacheState.this.onError(th);
                }

                public void onNext(T t) {
                    CacheState.this.onNext(t);
                }
            };
            this.connection.set(anonymousClass1);
            this.source.unsafeSubscribe(anonymousClass1);
            this.isConnected = true;
        }

        final void dispatch() {
            for (ReplayProducer replay : this.producers) {
                replay.replay();
            }
        }

        public final void onCompleted() {
            if (!this.sourceDone) {
                this.sourceDone = true;
                add(this.nl.completed());
                this.connection.unsubscribe();
                dispatch();
            }
        }

        public final void onError(Throwable th) {
            if (!this.sourceDone) {
                this.sourceDone = true;
                add(this.nl.error(th));
                this.connection.unsubscribe();
                dispatch();
            }
        }

        public final void onNext(T t) {
            if (!this.sourceDone) {
                add(this.nl.next(t));
                dispatch();
            }
        }

        public final void removeProducer(ReplayProducer<T> replayProducer) {
            int i = 0;
            synchronized (this.connection) {
                Object obj = this.producers;
                int length = obj.length;
                while (i < length) {
                    if (obj[i].equals(replayProducer)) {
                        break;
                    }
                    i++;
                }
                i = -1;
                if (i < 0) {
                } else if (length == 1) {
                    this.producers = EMPTY;
                } else {
                    Object obj2 = new ReplayProducer[(length - 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(obj, i + 1, obj2, i, (length - i) - 1);
                    this.producers = obj2;
                }
            }
        }
    }

    static final class CachedSubscribe<T> extends AtomicBoolean implements Observable$OnSubscribe<T> {
        private static final long serialVersionUID = -2817751667698696782L;
        final CacheState<T> state;

        public CachedSubscribe(CacheState<T> cacheState) {
            this.state = cacheState;
        }

        public final void call(Subscriber<? super T> subscriber) {
            Producer replayProducer = new ReplayProducer(subscriber, this.state);
            this.state.addProducer(replayProducer);
            subscriber.add(replayProducer);
            subscriber.setProducer(replayProducer);
            if (!get() && compareAndSet(false, true)) {
                this.state.connect();
            }
        }
    }

    static final class ReplayProducer<T> extends AtomicLong implements Producer, Subscription {
        private static final long serialVersionUID = -2557562030197141021L;
        final Subscriber<? super T> child;
        Object[] currentBuffer;
        int currentIndexInBuffer;
        boolean emitting;
        int index;
        boolean missed;
        final CacheState<T> state;

        public ReplayProducer(Subscriber<? super T> subscriber, CacheState<T> cacheState) {
            this.child = subscriber;
            this.state = cacheState;
        }

        public final boolean isUnsubscribed() {
            return get() < 0;
        }

        public final long produced(long j) {
            return addAndGet(-j);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void replay() {
            /*
            r19 = this;
            monitor-enter(r19);
            r0 = r19;
            r2 = r0.emitting;	 Catch:{ all -> 0x0073 }
            if (r2 == 0) goto L_0x000e;
        L_0x0007:
            r2 = 1;
            r0 = r19;
            r0.missed = r2;	 Catch:{ all -> 0x0073 }
            monitor-exit(r19);	 Catch:{ all -> 0x0073 }
        L_0x000d:
            return;
        L_0x000e:
            r2 = 1;
            r0 = r19;
            r0.emitting = r2;	 Catch:{ all -> 0x0073 }
            monitor-exit(r19);	 Catch:{ all -> 0x0073 }
            r5 = 0;
            r6 = 0;
            r4 = 0;
            r0 = r19;
            r2 = r0.state;	 Catch:{ all -> 0x0123 }
            r11 = r2.nl;	 Catch:{ all -> 0x0123 }
            r0 = r19;
            r12 = r0.child;	 Catch:{ all -> 0x0123 }
        L_0x0021:
            r8 = r19.get();	 Catch:{ all -> 0x0123 }
            r2 = 0;
            r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
            if (r2 < 0) goto L_0x000d;
        L_0x002b:
            r0 = r19;
            r2 = r0.state;	 Catch:{ all -> 0x0123 }
            r13 = r2.size();	 Catch:{ all -> 0x0123 }
            if (r13 == 0) goto L_0x00f3;
        L_0x0035:
            r0 = r19;
            r2 = r0.currentBuffer;	 Catch:{ all -> 0x0123 }
            if (r2 != 0) goto L_0x0047;
        L_0x003b:
            r0 = r19;
            r2 = r0.state;	 Catch:{ all -> 0x0123 }
            r2 = r2.head();	 Catch:{ all -> 0x0123 }
            r0 = r19;
            r0.currentBuffer = r2;	 Catch:{ all -> 0x0123 }
        L_0x0047:
            r3 = r2.length;	 Catch:{ all -> 0x0123 }
            r14 = r3 + -1;
            r0 = r19;
            r3 = r0.index;	 Catch:{ all -> 0x0123 }
            r0 = r19;
            r10 = r0.currentIndexInBuffer;	 Catch:{ all -> 0x0123 }
            r16 = 0;
            r7 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1));
            if (r7 != 0) goto L_0x0088;
        L_0x0058:
            r2 = r2[r10];
            r3 = r11.isCompleted(r2);	 Catch:{ all -> 0x0123 }
            if (r3 == 0) goto L_0x0076;
        L_0x0060:
            r12.onCompleted();	 Catch:{ all -> 0x0123 }
            r3 = 1;
            r19.unsubscribe();	 Catch:{ all -> 0x0068 }
            goto L_0x000d;
        L_0x0068:
            r2 = move-exception;
        L_0x0069:
            if (r3 != 0) goto L_0x0072;
        L_0x006b:
            monitor-enter(r19);
            r3 = 0;
            r0 = r19;
            r0.emitting = r3;	 Catch:{ all -> 0x0111 }
            monitor-exit(r19);	 Catch:{ all -> 0x0111 }
        L_0x0072:
            throw r2;
        L_0x0073:
            r2 = move-exception;
            monitor-exit(r19);	 Catch:{ all -> 0x0073 }
            throw r2;
        L_0x0076:
            r3 = r11.isError(r2);	 Catch:{ all -> 0x0123 }
            if (r3 == 0) goto L_0x00f3;
        L_0x007c:
            r2 = r11.getError(r2);	 Catch:{ all -> 0x0123 }
            r12.onError(r2);	 Catch:{ all -> 0x0123 }
            r3 = 1;
            r19.unsubscribe();	 Catch:{ all -> 0x0068 }
            goto L_0x000d;
        L_0x0088:
            r16 = 0;
            r7 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1));
            if (r7 <= 0) goto L_0x00f3;
        L_0x008e:
            r7 = 0;
            r18 = r3;
            r3 = r10;
            r10 = r7;
            r7 = r18;
        L_0x0095:
            if (r7 >= r13) goto L_0x00db;
        L_0x0097:
            r16 = 0;
            r15 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1));
            if (r15 <= 0) goto L_0x00db;
        L_0x009d:
            r15 = r12.isUnsubscribed();	 Catch:{ all -> 0x0123 }
            if (r15 != 0) goto L_0x000d;
        L_0x00a3:
            if (r3 != r14) goto L_0x00aa;
        L_0x00a5:
            r2 = r2[r14];	 Catch:{ all -> 0x0123 }
            r2 = (java.lang.Object[]) r2;	 Catch:{ all -> 0x0123 }
            r3 = 0;
        L_0x00aa:
            r15 = r2[r3];
            r16 = r11.accept(r12, r15);	 Catch:{ Throwable -> 0x0120 }
            if (r16 == 0) goto L_0x0114;
        L_0x00b2:
            r4 = 1;
            r3 = 1;
            r19.unsubscribe();	 Catch:{ Throwable -> 0x00b9 }
            goto L_0x000d;
        L_0x00b9:
            r2 = move-exception;
            r3 = r4;
        L_0x00bb:
            rx.exceptions.Exceptions.throwIfFatal(r2);	 Catch:{ all -> 0x0068 }
            r3 = 1;
            r19.unsubscribe();	 Catch:{ all -> 0x0068 }
            r4 = r11.isError(r15);	 Catch:{ all -> 0x0068 }
            if (r4 != 0) goto L_0x000d;
        L_0x00c8:
            r4 = r11.isCompleted(r15);	 Catch:{ all -> 0x0068 }
            if (r4 != 0) goto L_0x000d;
        L_0x00ce:
            r4 = r11.getValue(r15);	 Catch:{ all -> 0x0068 }
            r2 = rx.exceptions.OnErrorThrowable.addValueAsLastCause(r2, r4);	 Catch:{ all -> 0x0068 }
            r12.onError(r2);	 Catch:{ all -> 0x0068 }
            goto L_0x000d;
        L_0x00db:
            r8 = r12.isUnsubscribed();	 Catch:{ all -> 0x0123 }
            if (r8 != 0) goto L_0x000d;
        L_0x00e1:
            r0 = r19;
            r0.index = r7;	 Catch:{ all -> 0x0123 }
            r0 = r19;
            r0.currentIndexInBuffer = r3;	 Catch:{ all -> 0x0123 }
            r0 = r19;
            r0.currentBuffer = r2;	 Catch:{ all -> 0x0123 }
            r2 = (long) r10;	 Catch:{ all -> 0x0123 }
            r0 = r19;
            r0.produced(r2);	 Catch:{ all -> 0x0123 }
        L_0x00f3:
            monitor-enter(r19);	 Catch:{ all -> 0x0123 }
            r0 = r19;
            r2 = r0.missed;	 Catch:{ all -> 0x010e }
            if (r2 != 0) goto L_0x0106;
        L_0x00fa:
            r2 = 0;
            r0 = r19;
            r0.emitting = r2;	 Catch:{ all -> 0x010e }
            r3 = 1;
            monitor-exit(r19);	 Catch:{ all -> 0x0103 }
            goto L_0x000d;
        L_0x0103:
            r2 = move-exception;
        L_0x0104:
            monitor-exit(r19);	 Catch:{ all -> 0x0103 }
            throw r2;	 Catch:{ all -> 0x0068 }
        L_0x0106:
            r2 = 0;
            r0 = r19;
            r0.missed = r2;	 Catch:{ all -> 0x010e }
            monitor-exit(r19);	 Catch:{ all -> 0x010e }
            goto L_0x0021;
        L_0x010e:
            r2 = move-exception;
            r3 = r6;
            goto L_0x0104;
        L_0x0111:
            r2 = move-exception;
            monitor-exit(r19);	 Catch:{ all -> 0x0111 }
            throw r2;
        L_0x0114:
            r7 = r7 + 1;
            r16 = 1;
            r8 = r8 - r16;
            r10 = r10 + 1;
            r3 = r3 + 1;
            goto L_0x0095;
        L_0x0120:
            r2 = move-exception;
            r3 = r5;
            goto L_0x00bb;
        L_0x0123:
            r2 = move-exception;
            r3 = r4;
            goto L_0x0069;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.CachedObservable.ReplayProducer.replay():void");
        }

        public final void request(long j) {
            long j2;
            long j3;
            do {
                j2 = get();
                if (j2 >= 0) {
                    j3 = j2 + j;
                    if (j3 < 0) {
                        j3 = Long.MAX_VALUE;
                    }
                } else {
                    return;
                }
            } while (!compareAndSet(j2, j3));
            replay();
        }

        public final void unsubscribe() {
            if (get() >= 0 && getAndSet(-1) >= 0) {
                this.state.removeProducer(this);
            }
        }
    }

    private CachedObservable(Observable$OnSubscribe<T> observable$OnSubscribe, CacheState<T> cacheState) {
        super(observable$OnSubscribe);
        this.state = cacheState;
    }

    public static <T> CachedObservable<T> from(Observable<? extends T> observable) {
        return from(observable, 16);
    }

    public static <T> CachedObservable<T> from(Observable<? extends T> observable, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("capacityHint > 0 required");
        }
        CacheState cacheState = new CacheState(observable, i);
        return new CachedObservable(new CachedSubscribe(cacheState), cacheState);
    }

    final boolean hasObservers() {
        return this.state.producers.length != 0;
    }

    final boolean isConnected() {
        return this.state.isConnected;
    }
}
