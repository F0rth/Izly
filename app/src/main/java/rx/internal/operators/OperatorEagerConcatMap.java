package rx.internal.operators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observable$Operator;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.internal.util.atomic.SpscAtomicArrayQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.subscriptions.Subscriptions;

public final class OperatorEagerConcatMap<T, R> implements Observable$Operator<R, T> {
    final int bufferSize;
    final Func1<? super T, ? extends Observable<? extends R>> mapper;
    private final int maxConcurrent;

    static final class EagerInnerSubscriber<T> extends Subscriber<T> {
        volatile boolean done;
        Throwable error;
        final NotificationLite<T> nl;
        final EagerOuterSubscriber<?, T> parent;
        final Queue<Object> queue;

        public EagerInnerSubscriber(EagerOuterSubscriber<?, T> eagerOuterSubscriber, int i) {
            this.parent = eagerOuterSubscriber;
            this.queue = UnsafeAccess.isUnsafeAvailable() ? new SpscArrayQueue(i) : new SpscAtomicArrayQueue(i);
            this.nl = NotificationLite.instance();
            request((long) i);
        }

        public final void onCompleted() {
            this.done = true;
            this.parent.drain();
        }

        public final void onError(Throwable th) {
            this.error = th;
            this.done = true;
            this.parent.drain();
        }

        public final void onNext(T t) {
            this.queue.offer(this.nl.next(t));
            this.parent.drain();
        }

        final void requestMore(long j) {
            request(j);
        }
    }

    static final class EagerOuterProducer extends AtomicLong implements Producer {
        private static final long serialVersionUID = -657299606803478389L;
        final EagerOuterSubscriber<?, ?> parent;

        public EagerOuterProducer(EagerOuterSubscriber<?, ?> eagerOuterSubscriber) {
            this.parent = eagerOuterSubscriber;
        }

        public final void request(long j) {
            if (j < 0) {
                throw new IllegalStateException("n >= 0 required but it was " + j);
            } else if (j > 0) {
                BackpressureUtils.getAndAddRequest(this, j);
                this.parent.drain();
            }
        }
    }

    static final class EagerOuterSubscriber<T, R> extends Subscriber<T> {
        final Subscriber<? super R> actual;
        final int bufferSize;
        volatile boolean cancelled;
        volatile boolean done;
        Throwable error;
        final Func1<? super T, ? extends Observable<? extends R>> mapper;
        private EagerOuterProducer sharedProducer;
        final LinkedList<EagerInnerSubscriber<R>> subscribers = new LinkedList();
        final AtomicInteger wip = new AtomicInteger();

        public EagerOuterSubscriber(Func1<? super T, ? extends Observable<? extends R>> func1, int i, int i2, Subscriber<? super R> subscriber) {
            this.mapper = func1;
            this.bufferSize = i;
            this.actual = subscriber;
            request(i2 == Integer.MAX_VALUE ? Long.MAX_VALUE : (long) i2);
        }

        final void cleanup() {
            synchronized (this.subscribers) {
                List<Subscription> arrayList = new ArrayList(this.subscribers);
                this.subscribers.clear();
            }
            for (Subscription unsubscribe : arrayList) {
                unsubscribe.unsubscribe();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void drain() {
            /*
            r20 = this;
            r0 = r20;
            r2 = r0.wip;
            r2 = r2.getAndIncrement();
            if (r2 == 0) goto L_0x000b;
        L_0x000a:
            return;
        L_0x000b:
            r0 = r20;
            r11 = r0.sharedProducer;
            r0 = r20;
            r12 = r0.actual;
            r13 = rx.internal.operators.NotificationLite.instance();
            r2 = 1;
            r3 = r2;
        L_0x0019:
            r0 = r20;
            r2 = r0.cancelled;
            if (r2 == 0) goto L_0x0023;
        L_0x001f:
            r20.cleanup();
            goto L_0x000a;
        L_0x0023:
            r0 = r20;
            r5 = r0.done;
            r0 = r20;
            r4 = r0.subscribers;
            monitor-enter(r4);
            r0 = r20;
            r2 = r0.subscribers;	 Catch:{ all -> 0x0049 }
            r2 = r2.peek();	 Catch:{ all -> 0x0049 }
            r2 = (rx.internal.operators.OperatorEagerConcatMap.EagerInnerSubscriber) r2;	 Catch:{ all -> 0x0049 }
            monitor-exit(r4);	 Catch:{ all -> 0x0049 }
            if (r2 != 0) goto L_0x004c;
        L_0x0039:
            r4 = 1;
        L_0x003a:
            if (r5 == 0) goto L_0x0054;
        L_0x003c:
            r0 = r20;
            r5 = r0.error;
            if (r5 == 0) goto L_0x004e;
        L_0x0042:
            r20.cleanup();
            r12.onError(r5);
            goto L_0x000a;
        L_0x0049:
            r2 = move-exception;
            monitor-exit(r4);	 Catch:{ all -> 0x0049 }
            throw r2;
        L_0x004c:
            r4 = 0;
            goto L_0x003a;
        L_0x004e:
            if (r4 == 0) goto L_0x0054;
        L_0x0050:
            r12.onCompleted();
            goto L_0x000a;
        L_0x0054:
            if (r4 != 0) goto L_0x00b0;
        L_0x0056:
            r6 = r11.get();
            r8 = 0;
            r4 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
            r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
            if (r4 != 0) goto L_0x007f;
        L_0x0065:
            r4 = 1;
        L_0x0066:
            r14 = r2.queue;
            r5 = 0;
        L_0x0069:
            r15 = r2.done;
            r16 = r14.peek();
            if (r16 != 0) goto L_0x0081;
        L_0x0071:
            r10 = 1;
        L_0x0072:
            if (r15 == 0) goto L_0x00c1;
        L_0x0074:
            r15 = r2.error;
            if (r15 == 0) goto L_0x0083;
        L_0x0078:
            r20.cleanup();
            r12.onError(r15);
            goto L_0x000a;
        L_0x007f:
            r4 = 0;
            goto L_0x0066;
        L_0x0081:
            r10 = 0;
            goto L_0x0072;
        L_0x0083:
            if (r10 == 0) goto L_0x00c1;
        L_0x0085:
            r0 = r20;
            r5 = r0.subscribers;
            monitor-enter(r5);
            r0 = r20;
            r6 = r0.subscribers;	 Catch:{ all -> 0x00be }
            r6.poll();	 Catch:{ all -> 0x00be }
            monitor-exit(r5);	 Catch:{ all -> 0x00be }
            r2.unsubscribe();
            r5 = 1;
            r6 = 1;
            r0 = r20;
            r0.request(r6);
        L_0x009d:
            r6 = 0;
            r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
            if (r6 == 0) goto L_0x00ae;
        L_0x00a3:
            if (r4 != 0) goto L_0x00a8;
        L_0x00a5:
            r11.addAndGet(r8);
        L_0x00a8:
            if (r5 != 0) goto L_0x00ae;
        L_0x00aa:
            r6 = -r8;
            r2.requestMore(r6);
        L_0x00ae:
            if (r5 != 0) goto L_0x0019;
        L_0x00b0:
            r0 = r20;
            r2 = r0.wip;
            r3 = -r3;
            r2 = r2.addAndGet(r3);
            if (r2 == 0) goto L_0x000a;
        L_0x00bb:
            r3 = r2;
            goto L_0x0019;
        L_0x00be:
            r2 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x00be }
            throw r2;
        L_0x00c1:
            if (r10 != 0) goto L_0x009d;
        L_0x00c3:
            r18 = 0;
            r10 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1));
            if (r10 == 0) goto L_0x009d;
        L_0x00c9:
            r14.poll();
            r0 = r16;
            r10 = r13.getValue(r0);	 Catch:{ Throwable -> 0x00de }
            r12.onNext(r10);	 Catch:{ Throwable -> 0x00de }
            r16 = 1;
            r6 = r6 - r16;
            r16 = 1;
            r8 = r8 - r16;
            goto L_0x0069;
        L_0x00de:
            r2 = move-exception;
            r0 = r16;
            rx.exceptions.Exceptions.throwOrReport(r2, r12, r0);
            goto L_0x000a;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorEagerConcatMap.EagerOuterSubscriber.drain():void");
        }

        final void init() {
            this.sharedProducer = new EagerOuterProducer(this);
            add(Subscriptions.create(new Action0() {
                public void call() {
                    EagerOuterSubscriber.this.cancelled = true;
                    if (EagerOuterSubscriber.this.wip.getAndIncrement() == 0) {
                        EagerOuterSubscriber.this.cleanup();
                    }
                }
            }));
            this.actual.add(this);
            this.actual.setProducer(this.sharedProducer);
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onNext(T r5) {
            /*
            r4 = this;
            r0 = r4.mapper;	 Catch:{ Throwable -> 0x0014 }
            r0 = r0.call(r5);	 Catch:{ Throwable -> 0x0014 }
            r0 = (rx.Observable) r0;	 Catch:{ Throwable -> 0x0014 }
            r1 = new rx.internal.operators.OperatorEagerConcatMap$EagerInnerSubscriber;
            r2 = r4.bufferSize;
            r1.<init>(r4, r2);
            r2 = r4.cancelled;
            if (r2 == 0) goto L_0x001b;
        L_0x0013:
            return;
        L_0x0014:
            r0 = move-exception;
            r1 = r4.actual;
            rx.exceptions.Exceptions.throwOrReport(r0, r1, r5);
            goto L_0x0013;
        L_0x001b:
            r2 = r4.subscribers;
            monitor-enter(r2);
            r3 = r4.cancelled;	 Catch:{ all -> 0x0024 }
            if (r3 == 0) goto L_0x0027;
        L_0x0022:
            monitor-exit(r2);	 Catch:{ all -> 0x0024 }
            goto L_0x0013;
        L_0x0024:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0024 }
            throw r0;
        L_0x0027:
            r3 = r4.subscribers;	 Catch:{ all -> 0x0024 }
            r3.add(r1);	 Catch:{ all -> 0x0024 }
            monitor-exit(r2);	 Catch:{ all -> 0x0024 }
            r2 = r4.cancelled;
            if (r2 != 0) goto L_0x0013;
        L_0x0031:
            r0.unsafeSubscribe(r1);
            r4.drain();
            goto L_0x0013;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorEagerConcatMap.EagerOuterSubscriber.onNext(java.lang.Object):void");
        }
    }

    public OperatorEagerConcatMap(Func1<? super T, ? extends Observable<? extends R>> func1, int i, int i2) {
        this.mapper = func1;
        this.bufferSize = i;
        this.maxConcurrent = i2;
    }

    public final Subscriber<? super T> call(Subscriber<? super R> subscriber) {
        EagerOuterSubscriber eagerOuterSubscriber = new EagerOuterSubscriber(this.mapper, this.bufferSize, this.maxConcurrent, subscriber);
        eagerOuterSubscriber.init();
        return eagerOuterSubscriber;
    }
}
