package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Timestamped;
import rx.subscriptions.Subscriptions;

public final class OperatorReplay<T> extends ConnectableObservable<T> {
    static final Func0 DEFAULT_UNBOUNDED_FACTORY = new Func0() {
        public final Object call() {
            return new UnboundedReplayBuffer(16);
        }
    };
    final Func0<? extends ReplayBuffer<T>> bufferFactory;
    final AtomicReference<ReplaySubscriber<T>> current;
    final Observable<? extends T> source;

    interface ReplayBuffer<T> {
        void complete();

        void error(Throwable th);

        void next(T t);

        void replay(InnerProducer<T> innerProducer);
    }

    static class BoundedReplayBuffer<T> extends AtomicReference<Node> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        long index;
        final NotificationLite<T> nl = NotificationLite.instance();
        int size;
        Node tail;

        public BoundedReplayBuffer() {
            Node node = new Node(null, 0);
            this.tail = node;
            set(node);
        }

        final void addLast(Node node) {
            this.tail.set(node);
            this.tail = node;
            this.size++;
        }

        final void collect(Collection<? super T> collection) {
            Node node = (Node) get();
            while (true) {
                node = (Node) node.get();
                if (node != null) {
                    Object leaveTransform = leaveTransform(node.value);
                    if (!this.nl.isCompleted(leaveTransform) && !this.nl.isError(leaveTransform)) {
                        collection.add(this.nl.getValue(leaveTransform));
                    } else {
                        return;
                    }
                }
                return;
            }
        }

        public final void complete() {
            Object enterTransform = enterTransform(this.nl.completed());
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(enterTransform, j));
            truncateFinal();
        }

        Object enterTransform(Object obj) {
            return obj;
        }

        public final void error(Throwable th) {
            Object enterTransform = enterTransform(this.nl.error(th));
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(enterTransform, j));
            truncateFinal();
        }

        boolean hasCompleted() {
            return this.tail.value != null && this.nl.isCompleted(leaveTransform(this.tail.value));
        }

        boolean hasError() {
            return this.tail.value != null && this.nl.isError(leaveTransform(this.tail.value));
        }

        Object leaveTransform(Object obj) {
            return obj;
        }

        public final void next(T t) {
            Object enterTransform = enterTransform(this.nl.next(t));
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(enterTransform, j));
            truncate();
        }

        final void removeFirst() {
            Node node = (Node) ((Node) get()).get();
            if (node == null) {
                throw new IllegalStateException("Empty list!");
            }
            this.size--;
            setFirst(node);
        }

        final void removeSome(int i) {
            Node node = (Node) get();
            while (i > 0) {
                node = (Node) node.get();
                i--;
                this.size--;
            }
            setFirst(node);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void replay(rx.internal.operators.OperatorReplay.InnerProducer<T> r11) {
            /*
            r10 = this;
            monitor-enter(r11);
            r0 = r11.emitting;	 Catch:{ all -> 0x0090 }
            if (r0 == 0) goto L_0x000a;
        L_0x0005:
            r0 = 1;
            r11.missed = r0;	 Catch:{ all -> 0x0090 }
            monitor-exit(r11);	 Catch:{ all -> 0x0090 }
        L_0x0009:
            return;
        L_0x000a:
            r0 = 1;
            r11.emitting = r0;	 Catch:{ all -> 0x0090 }
            monitor-exit(r11);	 Catch:{ all -> 0x0090 }
        L_0x000e:
            r0 = r11.isUnsubscribed();
            if (r0 != 0) goto L_0x0009;
        L_0x0014:
            r2 = r11.get();
            r0 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
            r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
            if (r0 != 0) goto L_0x0093;
        L_0x0021:
            r0 = 1;
            r1 = r0;
        L_0x0023:
            r0 = r11.index();
            r0 = (rx.internal.operators.OperatorReplay.Node) r0;
            if (r0 != 0) goto L_0x0038;
        L_0x002b:
            r0 = r10.get();
            r0 = (rx.internal.operators.OperatorReplay.Node) r0;
            r11.index = r0;
            r4 = r0.index;
            r11.addTotalRequested(r4);
        L_0x0038:
            r4 = r11.isUnsubscribed();
            if (r4 != 0) goto L_0x0009;
        L_0x003e:
            r4 = 0;
            r6 = r4;
            r4 = r2;
            r2 = r0;
        L_0x0043:
            r8 = 0;
            r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
            if (r0 == 0) goto L_0x00a7;
        L_0x0049:
            r0 = r2.get();
            r0 = (rx.internal.operators.OperatorReplay.Node) r0;
            if (r0 == 0) goto L_0x00a7;
        L_0x0051:
            r2 = r0.value;
            r2 = r10.leaveTransform(r2);
            r3 = r10.nl;	 Catch:{ Throwable -> 0x0065 }
            r8 = r11.child;	 Catch:{ Throwable -> 0x0065 }
            r3 = r3.accept(r8, r2);	 Catch:{ Throwable -> 0x0065 }
            if (r3 == 0) goto L_0x0096;
        L_0x0061:
            r0 = 0;
            r11.index = r0;	 Catch:{ Throwable -> 0x0065 }
            goto L_0x0009;
        L_0x0065:
            r0 = move-exception;
            r1 = 0;
            r11.index = r1;
            rx.exceptions.Exceptions.throwIfFatal(r0);
            r11.unsubscribe();
            r1 = r10.nl;
            r1 = r1.isError(r2);
            if (r1 != 0) goto L_0x0009;
        L_0x0077:
            r1 = r10.nl;
            r1 = r1.isCompleted(r2);
            if (r1 != 0) goto L_0x0009;
        L_0x007f:
            r1 = r11.child;
            r3 = r10.nl;
            r2 = r3.getValue(r2);
            r0 = rx.exceptions.OnErrorThrowable.addValueAsLastCause(r0, r2);
            r1.onError(r0);
            goto L_0x0009;
        L_0x0090:
            r0 = move-exception;
            monitor-exit(r11);	 Catch:{ all -> 0x0090 }
            throw r0;
        L_0x0093:
            r0 = 0;
            r1 = r0;
            goto L_0x0023;
        L_0x0096:
            r2 = r11.isUnsubscribed();
            if (r2 != 0) goto L_0x0009;
        L_0x009c:
            r2 = 1;
            r2 = r4 - r2;
            r4 = 1;
            r4 = r4 + r6;
            r6 = r4;
            r4 = r2;
            r2 = r0;
            goto L_0x0043;
        L_0x00a7:
            r4 = 0;
            r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
            if (r0 == 0) goto L_0x00b4;
        L_0x00ad:
            r11.index = r2;
            if (r1 != 0) goto L_0x00b4;
        L_0x00b1:
            r11.produced(r6);
        L_0x00b4:
            monitor-enter(r11);
            r0 = r11.missed;	 Catch:{ all -> 0x00bf }
            if (r0 != 0) goto L_0x00c2;
        L_0x00b9:
            r0 = 0;
            r11.emitting = r0;	 Catch:{ all -> 0x00bf }
            monitor-exit(r11);	 Catch:{ all -> 0x00bf }
            goto L_0x0009;
        L_0x00bf:
            r0 = move-exception;
            monitor-exit(r11);	 Catch:{ all -> 0x00bf }
            throw r0;
        L_0x00c2:
            r0 = 0;
            r11.missed = r0;	 Catch:{ all -> 0x00bf }
            monitor-exit(r11);	 Catch:{ all -> 0x00bf }
            goto L_0x000e;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorReplay.BoundedReplayBuffer.replay(rx.internal.operators.OperatorReplay$InnerProducer):void");
        }

        final void setFirst(Node node) {
            set(node);
        }

        void truncate() {
        }

        void truncateFinal() {
        }
    }

    static final class InnerProducer<T> extends AtomicLong implements Producer, Subscription {
        static final long UNSUBSCRIBED = Long.MIN_VALUE;
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        boolean emitting;
        Object index;
        boolean missed;
        final ReplaySubscriber<T> parent;
        final AtomicLong totalRequested = new AtomicLong();

        public InnerProducer(ReplaySubscriber<T> replaySubscriber, Subscriber<? super T> subscriber) {
            this.parent = replaySubscriber;
            this.child = subscriber;
        }

        final void addTotalRequested(long j) {
            long j2;
            long j3;
            do {
                j2 = this.totalRequested.get();
                j3 = j2 + j;
                if (j3 < 0) {
                    j3 = Long.MAX_VALUE;
                }
            } while (!this.totalRequested.compareAndSet(j2, j3));
        }

        final <U> U index() {
            return this.index;
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
                if (j3 == UNSUBSCRIBED) {
                    return UNSUBSCRIBED;
                }
                j2 = j3 - j;
                if (j2 < 0) {
                    throw new IllegalStateException("More produced (" + j + ") than requested (" + j3 + ")");
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
                    if (j2 < 0 || j != 0) {
                        j3 = j2 + j;
                        if (j3 < 0) {
                            j3 = Long.MAX_VALUE;
                        }
                    } else {
                        return;
                    }
                } while (!compareAndSet(j2, j3));
                addTotalRequested(j);
                this.parent.manageRequests();
                this.parent.buffer.replay(this);
            }
        }

        public final void unsubscribe() {
            if (get() != UNSUBSCRIBED && getAndSet(UNSUBSCRIBED) != UNSUBSCRIBED) {
                this.parent.remove(this);
                this.parent.manageRequests();
            }
        }
    }

    static final class Node extends AtomicReference<Node> {
        private static final long serialVersionUID = 245354315435971818L;
        final long index;
        final Object value;

        public Node(Object obj, long j) {
            this.value = obj;
            this.index = j;
        }
    }

    static final class ReplaySubscriber<T> extends Subscriber<T> implements Subscription {
        static final InnerProducer[] EMPTY = new InnerProducer[0];
        static final InnerProducer[] TERMINATED = new InnerProducer[0];
        final ReplayBuffer<T> buffer;
        boolean done;
        boolean emitting;
        long maxChildRequested;
        long maxUpstreamRequested;
        boolean missed;
        final NotificationLite<T> nl = NotificationLite.instance();
        volatile Producer producer;
        final AtomicReference<InnerProducer[]> producers = new AtomicReference(EMPTY);
        final AtomicBoolean shouldConnect = new AtomicBoolean();

        public ReplaySubscriber(AtomicReference<ReplaySubscriber<T>> atomicReference, ReplayBuffer<T> replayBuffer) {
            this.buffer = replayBuffer;
            request(0);
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

        final void init() {
            add(Subscriptions.create(new Action0() {
                public void call() {
                    ReplaySubscriber.this.producers.getAndSet(ReplaySubscriber.TERMINATED);
                }
            }));
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void manageRequests() {
            /*
            r12 = this;
            r6 = 0;
            r10 = 0;
            r0 = r12.isUnsubscribed();
            if (r0 == 0) goto L_0x000a;
        L_0x0009:
            return;
        L_0x000a:
            monitor-enter(r12);
            r0 = r12.emitting;	 Catch:{ all -> 0x0014 }
            if (r0 == 0) goto L_0x0017;
        L_0x000f:
            r0 = 1;
            r12.missed = r0;	 Catch:{ all -> 0x0014 }
            monitor-exit(r12);	 Catch:{ all -> 0x0014 }
            goto L_0x0009;
        L_0x0014:
            r0 = move-exception;
            monitor-exit(r12);	 Catch:{ all -> 0x0014 }
            throw r0;
        L_0x0017:
            r0 = 1;
            r12.emitting = r0;	 Catch:{ all -> 0x0014 }
            monitor-exit(r12);	 Catch:{ all -> 0x0014 }
        L_0x001b:
            r0 = r12.isUnsubscribed();
            if (r0 != 0) goto L_0x0009;
        L_0x0021:
            r0 = r12.producers;
            r0 = r0.get();
            r0 = (rx.internal.operators.OperatorReplay.InnerProducer[]) r0;
            r4 = r12.maxChildRequested;
            r7 = r0.length;
            r2 = r4;
            r1 = r6;
        L_0x002e:
            if (r1 >= r7) goto L_0x003f;
        L_0x0030:
            r8 = r0[r1];
            r8 = r8.totalRequested;
            r8 = r8.get();
            r2 = java.lang.Math.max(r2, r8);
            r1 = r1 + 1;
            goto L_0x002e;
        L_0x003f:
            r0 = r12.maxUpstreamRequested;
            r7 = r12.producer;
            r4 = r2 - r4;
            r8 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
            if (r8 == 0) goto L_0x0075;
        L_0x0049:
            r12.maxChildRequested = r2;
            if (r7 == 0) goto L_0x0068;
        L_0x004d:
            r2 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
            if (r2 == 0) goto L_0x0064;
        L_0x0051:
            r12.maxUpstreamRequested = r10;
            r0 = r0 + r4;
            r7.request(r0);
        L_0x0057:
            monitor-enter(r12);
            r0 = r12.missed;	 Catch:{ all -> 0x0061 }
            if (r0 != 0) goto L_0x0081;
        L_0x005c:
            r0 = 0;
            r12.emitting = r0;	 Catch:{ all -> 0x0061 }
            monitor-exit(r12);	 Catch:{ all -> 0x0061 }
            goto L_0x0009;
        L_0x0061:
            r0 = move-exception;
            monitor-exit(r12);	 Catch:{ all -> 0x0061 }
            throw r0;
        L_0x0064:
            r7.request(r4);
            goto L_0x0057;
        L_0x0068:
            r0 = r0 + r4;
            r2 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
            if (r2 >= 0) goto L_0x0072;
        L_0x006d:
            r0 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        L_0x0072:
            r12.maxUpstreamRequested = r0;
            goto L_0x0057;
        L_0x0075:
            r2 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
            if (r2 == 0) goto L_0x0057;
        L_0x0079:
            if (r7 == 0) goto L_0x0057;
        L_0x007b:
            r12.maxUpstreamRequested = r10;
            r7.request(r0);
            goto L_0x0057;
        L_0x0081:
            r0 = 0;
            r12.missed = r0;	 Catch:{ all -> 0x0061 }
            monitor-exit(r12);	 Catch:{ all -> 0x0061 }
            goto L_0x001b;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorReplay.ReplaySubscriber.manageRequests():void");
        }

        public final void onCompleted() {
            if (!this.done) {
                this.done = true;
                try {
                    this.buffer.complete();
                    replay();
                } finally {
                    unsubscribe();
                }
            }
        }

        public final void onError(Throwable th) {
            if (!this.done) {
                this.done = true;
                try {
                    this.buffer.error(th);
                    replay();
                } finally {
                    unsubscribe();
                }
            }
        }

        public final void onNext(T t) {
            if (!this.done) {
                this.buffer.next(t);
                replay();
            }
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

        final void replay() {
            for (InnerProducer replay : (InnerProducer[]) this.producers.get()) {
                this.buffer.replay(replay);
            }
        }

        public final void setProducer(Producer producer) {
            if (this.producer != null) {
                throw new IllegalStateException("Only a single producer can be set on a Subscriber.");
            }
            this.producer = producer;
            manageRequests();
            replay();
        }
    }

    static final class SizeAndTimeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final int limit;
        final long maxAgeInMillis;
        final Scheduler scheduler;

        public SizeAndTimeBoundReplayBuffer(int i, long j, Scheduler scheduler) {
            this.scheduler = scheduler;
            this.limit = i;
            this.maxAgeInMillis = j;
        }

        final Object enterTransform(Object obj) {
            return new Timestamped(this.scheduler.now(), obj);
        }

        final Object leaveTransform(Object obj) {
            return ((Timestamped) obj).getValue();
        }

        final void truncate() {
            long now = this.scheduler.now();
            long j = this.maxAgeInMillis;
            Node node = (Node) get();
            int i = 0;
            Node node2 = (Node) node.get();
            Node node3 = node;
            while (node2 != null) {
                int i2;
                if (this.size <= this.limit) {
                    if (((Timestamped) node2.value).getTimestampMillis() > now - j) {
                        break;
                    }
                    i2 = i + 1;
                    this.size--;
                    i = i2;
                    node3 = node2;
                    node2 = (Node) node2.get();
                } else {
                    i2 = i + 1;
                    this.size--;
                    i = i2;
                    node3 = node2;
                    node2 = (Node) node2.get();
                }
            }
            if (i != 0) {
                setFirst(node3);
            }
        }

        final void truncateFinal() {
            long now = this.scheduler.now();
            long j = this.maxAgeInMillis;
            Node node = (Node) get();
            int i = 0;
            Node node2 = (Node) node.get();
            Node node3 = node;
            while (node2 != null && this.size > 1 && ((Timestamped) node2.value).getTimestampMillis() <= now - j) {
                int i2 = i + 1;
                this.size--;
                i = i2;
                node3 = node2;
                node2 = (Node) node2.get();
            }
            if (i != 0) {
                setFirst(node3);
            }
        }
    }

    static final class SizeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = -5898283885385201806L;
        final int limit;

        public SizeBoundReplayBuffer(int i) {
            this.limit = i;
        }

        final void truncate() {
            if (this.size > this.limit) {
                removeFirst();
            }
        }
    }

    static final class UnboundedReplayBuffer<T> extends ArrayList<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        final NotificationLite<T> nl = NotificationLite.instance();
        volatile int size;

        public UnboundedReplayBuffer(int i) {
            super(i);
        }

        public final void complete() {
            add(this.nl.completed());
            this.size++;
        }

        public final void error(Throwable th) {
            add(this.nl.error(th));
            this.size++;
        }

        public final void next(T t) {
            add(this.nl.next(t));
            this.size++;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void replay(rx.internal.operators.OperatorReplay.InnerProducer<T> r12) {
            /*
            r11 = this;
            monitor-enter(r12);
            r0 = r12.emitting;	 Catch:{ all -> 0x004f }
            if (r0 == 0) goto L_0x000a;
        L_0x0005:
            r0 = 1;
            r12.missed = r0;	 Catch:{ all -> 0x004f }
            monitor-exit(r12);	 Catch:{ all -> 0x004f }
        L_0x0009:
            return;
        L_0x000a:
            r0 = 1;
            r12.emitting = r0;	 Catch:{ all -> 0x004f }
            monitor-exit(r12);	 Catch:{ all -> 0x004f }
        L_0x000e:
            r0 = r12.isUnsubscribed();
            if (r0 != 0) goto L_0x0009;
        L_0x0014:
            r7 = r11.size;
            r0 = r12.index();
            r0 = (java.lang.Integer) r0;
            if (r0 == 0) goto L_0x0052;
        L_0x001e:
            r0 = r0.intValue();
        L_0x0022:
            r2 = r12.get();
            r4 = 0;
            r6 = r0;
            r0 = r2;
        L_0x002a:
            r8 = 0;
            r8 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
            if (r8 == 0) goto L_0x007b;
        L_0x0030:
            if (r6 >= r7) goto L_0x007b;
        L_0x0032:
            r8 = r11.get(r6);
            r9 = r11.nl;	 Catch:{ Throwable -> 0x0054 }
            r10 = r12.child;	 Catch:{ Throwable -> 0x0054 }
            r8 = r9.accept(r10, r8);	 Catch:{ Throwable -> 0x0054 }
            if (r8 != 0) goto L_0x0009;
        L_0x0040:
            r8 = r12.isUnsubscribed();
            if (r8 != 0) goto L_0x0009;
        L_0x0046:
            r6 = r6 + 1;
            r8 = 1;
            r0 = r0 - r8;
            r8 = 1;
            r4 = r4 + r8;
            goto L_0x002a;
        L_0x004f:
            r0 = move-exception;
            monitor-exit(r12);	 Catch:{ all -> 0x004f }
            throw r0;
        L_0x0052:
            r0 = 0;
            goto L_0x0022;
        L_0x0054:
            r0 = move-exception;
            rx.exceptions.Exceptions.throwIfFatal(r0);
            r12.unsubscribe();
            r1 = r11.nl;
            r1 = r1.isError(r8);
            if (r1 != 0) goto L_0x0009;
        L_0x0063:
            r1 = r11.nl;
            r1 = r1.isCompleted(r8);
            if (r1 != 0) goto L_0x0009;
        L_0x006b:
            r1 = r12.child;
            r2 = r11.nl;
            r2 = r2.getValue(r8);
            r0 = rx.exceptions.OnErrorThrowable.addValueAsLastCause(r0, r2);
            r1.onError(r0);
            goto L_0x0009;
        L_0x007b:
            r0 = 0;
            r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
            if (r0 == 0) goto L_0x0093;
        L_0x0081:
            r0 = java.lang.Integer.valueOf(r6);
            r12.index = r0;
            r0 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
            r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
            if (r0 == 0) goto L_0x0093;
        L_0x0090:
            r12.produced(r4);
        L_0x0093:
            monitor-enter(r12);
            r0 = r12.missed;	 Catch:{ all -> 0x009e }
            if (r0 != 0) goto L_0x00a1;
        L_0x0098:
            r0 = 0;
            r12.emitting = r0;	 Catch:{ all -> 0x009e }
            monitor-exit(r12);	 Catch:{ all -> 0x009e }
            goto L_0x0009;
        L_0x009e:
            r0 = move-exception;
            monitor-exit(r12);	 Catch:{ all -> 0x009e }
            throw r0;
        L_0x00a1:
            r0 = 0;
            r12.missed = r0;	 Catch:{ all -> 0x009e }
            monitor-exit(r12);	 Catch:{ all -> 0x009e }
            goto L_0x000e;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorReplay.UnboundedReplayBuffer.replay(rx.internal.operators.OperatorReplay$InnerProducer):void");
        }
    }

    private OperatorReplay(Observable$OnSubscribe<T> observable$OnSubscribe, Observable<? extends T> observable, AtomicReference<ReplaySubscriber<T>> atomicReference, Func0<? extends ReplayBuffer<T>> func0) {
        super(observable$OnSubscribe);
        this.source = observable;
        this.current = atomicReference;
        this.bufferFactory = func0;
    }

    public static <T> ConnectableObservable<T> create(Observable<? extends T> observable) {
        return create((Observable) observable, DEFAULT_UNBOUNDED_FACTORY);
    }

    public static <T> ConnectableObservable<T> create(Observable<? extends T> observable, final int i) {
        return i == Integer.MAX_VALUE ? create(observable) : create((Observable) observable, new Func0<ReplayBuffer<T>>() {
            public final ReplayBuffer<T> call() {
                return new SizeBoundReplayBuffer(i);
            }
        });
    }

    public static <T> ConnectableObservable<T> create(Observable<? extends T> observable, long j, TimeUnit timeUnit, Scheduler scheduler) {
        return create(observable, j, timeUnit, scheduler, Integer.MAX_VALUE);
    }

    public static <T> ConnectableObservable<T> create(Observable<? extends T> observable, long j, TimeUnit timeUnit, final Scheduler scheduler, final int i) {
        final long toMillis = timeUnit.toMillis(j);
        return create((Observable) observable, new Func0<ReplayBuffer<T>>() {
            public final ReplayBuffer<T> call() {
                return new SizeAndTimeBoundReplayBuffer(i, toMillis, scheduler);
            }
        });
    }

    static <T> ConnectableObservable<T> create(Observable<? extends T> observable, final Func0<? extends ReplayBuffer<T>> func0) {
        final AtomicReference atomicReference = new AtomicReference();
        return new OperatorReplay(new Observable$OnSubscribe<T>() {
            public final void call(Subscriber<? super T> subscriber) {
                ReplaySubscriber replaySubscriber;
                ReplaySubscriber replaySubscriber2;
                do {
                    replaySubscriber = (ReplaySubscriber) atomicReference.get();
                    if (replaySubscriber != null) {
                        break;
                    }
                    replaySubscriber2 = new ReplaySubscriber(atomicReference, (ReplayBuffer) func0.call());
                    replaySubscriber2.init();
                } while (!atomicReference.compareAndSet(replaySubscriber, replaySubscriber2));
                replaySubscriber = replaySubscriber2;
                Producer innerProducer = new InnerProducer(replaySubscriber, subscriber);
                replaySubscriber.add(innerProducer);
                subscriber.add(innerProducer);
                replaySubscriber.buffer.replay(innerProducer);
                subscriber.setProducer(innerProducer);
            }
        }, observable, atomicReference, func0);
    }

    public static <T, U, R> Observable<R> multicastSelector(final Func0<? extends ConnectableObservable<U>> func0, final Func1<? super Observable<U>, ? extends Observable<R>> func1) {
        return Observable.create(new Observable$OnSubscribe<R>() {
            public final void call(final Subscriber<? super R> subscriber) {
                try {
                    ConnectableObservable connectableObservable = (ConnectableObservable) func0.call();
                    ((Observable) func1.call(connectableObservable)).subscribe(subscriber);
                    connectableObservable.connect(new Action1<Subscription>() {
                        public void call(Subscription subscription) {
                            subscriber.add(subscription);
                        }
                    });
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, (Observer) subscriber);
                }
            }
        });
    }

    public static <T> ConnectableObservable<T> observeOn(final ConnectableObservable<T> connectableObservable, Scheduler scheduler) {
        final Observable observeOn = connectableObservable.observeOn(scheduler);
        return new ConnectableObservable<T>(new Observable$OnSubscribe<T>() {
            public final void call(final Subscriber<? super T> subscriber) {
                observeOn.unsafeSubscribe(new Subscriber<T>(subscriber) {
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    public void onError(Throwable th) {
                        subscriber.onError(th);
                    }

                    public void onNext(T t) {
                        subscriber.onNext(t);
                    }
                });
            }
        }) {
            public final void connect(Action1<? super Subscription> action1) {
                connectableObservable.connect(action1);
            }
        };
    }

    public final void connect(Action1<? super Subscription> action1) {
        ReplaySubscriber replaySubscriber;
        ReplaySubscriber replaySubscriber2;
        do {
            replaySubscriber = (ReplaySubscriber) this.current.get();
            if (replaySubscriber != null && !replaySubscriber.isUnsubscribed()) {
                break;
            }
            replaySubscriber2 = new ReplaySubscriber(this.current, (ReplayBuffer) this.bufferFactory.call());
            replaySubscriber2.init();
        } while (!this.current.compareAndSet(replaySubscriber, replaySubscriber2));
        replaySubscriber = replaySubscriber2;
        boolean z = !replaySubscriber.shouldConnect.get() && replaySubscriber.shouldConnect.compareAndSet(false, true);
        action1.call(replaySubscriber);
        if (z) {
            this.source.unsafeSubscribe(replaySubscriber);
        }
    }
}
