package rx.subjects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Scheduler;
import rx.annotations.Beta;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.operators.NotificationLite;
import rx.internal.util.UtilityFunctions;
import rx.schedulers.Timestamped;
import rx.subjects.SubjectSubscriptionManager.State;
import rx.subjects.SubjectSubscriptionManager.SubjectObserver;

public final class ReplaySubject<T> extends Subject<T, T> {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    final SubjectSubscriptionManager<T> ssm;
    final ReplayState<T, ?> state;

    static final class AddTimestamped implements Func1<Object, Object> {
        final Scheduler scheduler;

        public AddTimestamped(Scheduler scheduler) {
            this.scheduler = scheduler;
        }

        public final Object call(Object obj) {
            return new Timestamped(this.scheduler.now(), obj);
        }
    }

    interface ReplayState<T, I> {
        void complete();

        void error(Throwable th);

        boolean isEmpty();

        T latest();

        void next(T t);

        boolean replayObserver(SubjectObserver<? super T> subjectObserver);

        I replayObserverFromIndex(I i, SubjectObserver<? super T> subjectObserver);

        I replayObserverFromIndexTest(I i, SubjectObserver<? super T> subjectObserver, long j);

        int size();

        boolean terminated();

        T[] toArray(T[] tArr);
    }

    static final class BoundedState<T> implements ReplayState<T, Node<Object>> {
        final Func1<Object, Object> enterTransform;
        final EvictionPolicy evictionPolicy;
        final Func1<Object, Object> leaveTransform;
        final NodeList<Object> list = new NodeList();
        final NotificationLite<T> nl = NotificationLite.instance();
        volatile Node<Object> tail = this.list.tail;
        volatile boolean terminated;

        public BoundedState(EvictionPolicy evictionPolicy, Func1<Object, Object> func1, Func1<Object, Object> func12) {
            this.evictionPolicy = evictionPolicy;
            this.enterTransform = func1;
            this.leaveTransform = func12;
        }

        public final void accept(Observer<? super T> observer, Node<Object> node) {
            this.nl.accept(observer, this.leaveTransform.call(node.value));
        }

        public final void acceptTest(Observer<? super T> observer, Node<Object> node, long j) {
            Object obj = node.value;
            if (!this.evictionPolicy.test(obj, j)) {
                this.nl.accept(observer, this.leaveTransform.call(obj));
            }
        }

        public final void complete() {
            if (!this.terminated) {
                this.terminated = true;
                this.list.addLast(this.enterTransform.call(this.nl.completed()));
                this.evictionPolicy.evictFinal(this.list);
                this.tail = this.list.tail;
            }
        }

        public final void error(Throwable th) {
            if (!this.terminated) {
                this.terminated = true;
                this.list.addLast(this.enterTransform.call(this.nl.error(th)));
                this.evictionPolicy.evictFinal(this.list);
                this.tail = this.list.tail;
            }
        }

        public final Node<Object> head() {
            return this.list.head;
        }

        public final boolean isEmpty() {
            Node node = head().next;
            if (node != null) {
                Object call = this.leaveTransform.call(node.value);
                if (!(this.nl.isError(call) || this.nl.isCompleted(call))) {
                    return false;
                }
            }
            return true;
        }

        public final T latest() {
            Node node = head().next;
            if (node == null) {
                return null;
            }
            Node node2 = null;
            while (node != tail()) {
                Node node3 = node;
                node = node.next;
                node2 = node3;
            }
            Object call = this.leaveTransform.call(node.value);
            if (!this.nl.isError(call) && !this.nl.isCompleted(call)) {
                return this.nl.getValue(call);
            }
            if (node2 == null) {
                return null;
            }
            return this.nl.getValue(this.leaveTransform.call(node2.value));
        }

        public final void next(T t) {
            if (!this.terminated) {
                this.list.addLast(this.enterTransform.call(this.nl.next(t)));
                this.evictionPolicy.evict(this.list);
                this.tail = this.list.tail;
            }
        }

        public final boolean replayObserver(SubjectObserver<? super T> subjectObserver) {
            synchronized (subjectObserver) {
                subjectObserver.first = false;
                if (subjectObserver.emitting) {
                    return false;
                }
                subjectObserver.index(replayObserverFromIndex((Node) subjectObserver.index(), (SubjectObserver) subjectObserver));
                return true;
            }
        }

        public final Node<Object> replayObserverFromIndex(Node<Object> node, SubjectObserver<? super T> subjectObserver) {
            while (node != tail()) {
                accept(subjectObserver, node.next);
                node = node.next;
            }
            return node;
        }

        public final Node<Object> replayObserverFromIndexTest(Node<Object> node, SubjectObserver<? super T> subjectObserver, long j) {
            while (node != tail()) {
                acceptTest(subjectObserver, node.next, j);
                node = node.next;
            }
            return node;
        }

        public final int size() {
            Node head = head();
            Node node = head.next;
            Node node2 = head;
            int i = 0;
            Node node3 = node2;
            while (node != null) {
                i++;
                node2 = node;
                node = node.next;
                node3 = node2;
            }
            if (node3.value == null) {
                return i;
            }
            Object call = this.leaveTransform.call(node3.value);
            return call != null ? (this.nl.isError(call) || this.nl.isCompleted(call)) ? i - 1 : i : i;
        }

        public final Node<Object> tail() {
            return this.tail;
        }

        public final boolean terminated() {
            return this.terminated;
        }

        public final T[] toArray(T[] tArr) {
            List arrayList = new ArrayList();
            for (Node node = head().next; node != null; node = node.next) {
                Object call = this.leaveTransform.call(node.value);
                if (node.next == null && (this.nl.isError(call) || this.nl.isCompleted(call))) {
                    break;
                }
                arrayList.add(call);
            }
            return arrayList.toArray(tArr);
        }
    }

    static final class DefaultOnAdd<T> implements Action1<SubjectObserver<T>> {
        final BoundedState<T> state;

        public DefaultOnAdd(BoundedState<T> boundedState) {
            this.state = boundedState;
        }

        public final void call(SubjectObserver<T> subjectObserver) {
            subjectObserver.index(this.state.replayObserverFromIndex(this.state.head(), (SubjectObserver) subjectObserver));
        }
    }

    interface EvictionPolicy {
        void evict(NodeList<Object> nodeList);

        void evictFinal(NodeList<Object> nodeList);

        boolean test(Object obj, long j);
    }

    static final class EmptyEvictionPolicy implements EvictionPolicy {
        EmptyEvictionPolicy() {
        }

        public final void evict(NodeList<Object> nodeList) {
        }

        public final void evictFinal(NodeList<Object> nodeList) {
        }

        public final boolean test(Object obj, long j) {
            return true;
        }
    }

    static final class NodeList<T> {
        final Node<T> head = new Node(null);
        int size;
        Node<T> tail = this.head;

        static final class Node<T> {
            volatile Node<T> next;
            final T value;

            Node(T t) {
                this.value = t;
            }
        }

        NodeList() {
        }

        public final void addLast(T t) {
            Node node = this.tail;
            Node node2 = new Node(t);
            node.next = node2;
            this.tail = node2;
            this.size++;
        }

        public final void clear() {
            this.tail = this.head;
            this.size = 0;
        }

        public final boolean isEmpty() {
            return this.size == 0;
        }

        public final T removeFirst() {
            if (this.head.next == null) {
                throw new IllegalStateException("Empty!");
            }
            Node node = this.head.next;
            this.head.next = node.next;
            if (this.head.next == null) {
                this.tail = this.head;
            }
            this.size--;
            return node.value;
        }

        public final int size() {
            return this.size;
        }
    }

    static final class PairEvictionPolicy implements EvictionPolicy {
        final EvictionPolicy first;
        final EvictionPolicy second;

        public PairEvictionPolicy(EvictionPolicy evictionPolicy, EvictionPolicy evictionPolicy2) {
            this.first = evictionPolicy;
            this.second = evictionPolicy2;
        }

        public final void evict(NodeList<Object> nodeList) {
            this.first.evict(nodeList);
            this.second.evict(nodeList);
        }

        public final void evictFinal(NodeList<Object> nodeList) {
            this.first.evictFinal(nodeList);
            this.second.evictFinal(nodeList);
        }

        public final boolean test(Object obj, long j) {
            return this.first.test(obj, j) || this.second.test(obj, j);
        }
    }

    static final class RemoveTimestamped implements Func1<Object, Object> {
        RemoveTimestamped() {
        }

        public final Object call(Object obj) {
            return ((Timestamped) obj).getValue();
        }
    }

    static final class SizeEvictionPolicy implements EvictionPolicy {
        final int maxSize;

        public SizeEvictionPolicy(int i) {
            this.maxSize = i;
        }

        public final void evict(NodeList<Object> nodeList) {
            while (nodeList.size() > this.maxSize) {
                nodeList.removeFirst();
            }
        }

        public final void evictFinal(NodeList<Object> nodeList) {
            while (nodeList.size() > this.maxSize + 1) {
                nodeList.removeFirst();
            }
        }

        public final boolean test(Object obj, long j) {
            return false;
        }
    }

    static final class TimeEvictionPolicy implements EvictionPolicy {
        final long maxAgeMillis;
        final Scheduler scheduler;

        public TimeEvictionPolicy(long j, Scheduler scheduler) {
            this.maxAgeMillis = j;
            this.scheduler = scheduler;
        }

        public final void evict(NodeList<Object> nodeList) {
            long now = this.scheduler.now();
            while (!nodeList.isEmpty() && test(nodeList.head.next.value, now)) {
                nodeList.removeFirst();
            }
        }

        public final void evictFinal(NodeList<Object> nodeList) {
            long now = this.scheduler.now();
            while (nodeList.size > 1 && test(nodeList.head.next.value, now)) {
                nodeList.removeFirst();
            }
        }

        public final boolean test(Object obj, long j) {
            return ((Timestamped) obj).getTimestampMillis() <= j - this.maxAgeMillis;
        }
    }

    static final class TimedOnAdd<T> implements Action1<SubjectObserver<T>> {
        final Scheduler scheduler;
        final BoundedState<T> state;

        public TimedOnAdd(BoundedState<T> boundedState, Scheduler scheduler) {
            this.state = boundedState;
            this.scheduler = scheduler;
        }

        public final void call(SubjectObserver<T> subjectObserver) {
            subjectObserver.index(!this.state.terminated ? this.state.replayObserverFromIndexTest(this.state.head(), (SubjectObserver) subjectObserver, this.scheduler.now()) : this.state.replayObserverFromIndex(this.state.head(), (SubjectObserver) subjectObserver));
        }
    }

    static final class UnboundedReplayState<T> extends AtomicInteger implements ReplayState<T, Integer> {
        private final ArrayList<Object> list;
        private final NotificationLite<T> nl = NotificationLite.instance();
        private volatile boolean terminated;

        public UnboundedReplayState(int i) {
            this.list = new ArrayList(i);
        }

        public final void accept(Observer<? super T> observer, int i) {
            this.nl.accept(observer, this.list.get(i));
        }

        public final void complete() {
            if (!this.terminated) {
                this.terminated = true;
                this.list.add(this.nl.completed());
                getAndIncrement();
            }
        }

        public final void error(Throwable th) {
            if (!this.terminated) {
                this.terminated = true;
                this.list.add(this.nl.error(th));
                getAndIncrement();
            }
        }

        public final boolean isEmpty() {
            return size() == 0;
        }

        public final T latest() {
            int i = get();
            if (i <= 0) {
                return null;
            }
            Object obj = this.list.get(i - 1);
            return (this.nl.isCompleted(obj) || this.nl.isError(obj)) ? i > 1 ? this.nl.getValue(this.list.get(i - 2)) : null : this.nl.getValue(obj);
        }

        public final void next(T t) {
            if (!this.terminated) {
                this.list.add(this.nl.next(t));
                getAndIncrement();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean replayObserver(rx.subjects.SubjectSubscriptionManager.SubjectObserver<? super T> r4) {
            /*
            r3 = this;
            r0 = 0;
            monitor-enter(r4);
            r1 = 0;
            r4.first = r1;	 Catch:{ all -> 0x0025 }
            r1 = r4.emitting;	 Catch:{ all -> 0x0025 }
            if (r1 == 0) goto L_0x000b;
        L_0x0009:
            monitor-exit(r4);	 Catch:{ all -> 0x0025 }
        L_0x000a:
            return r0;
        L_0x000b:
            monitor-exit(r4);	 Catch:{ all -> 0x0025 }
            r0 = r4.index();
            r0 = (java.lang.Integer) r0;
            if (r0 == 0) goto L_0x0028;
        L_0x0014:
            r0 = r3.replayObserverFromIndex(r0, r4);
            r0 = r0.intValue();
            r0 = java.lang.Integer.valueOf(r0);
            r4.index(r0);
            r0 = 1;
            goto L_0x000a;
        L_0x0025:
            r0 = move-exception;
            monitor-exit(r4);	 Catch:{ all -> 0x0025 }
            throw r0;
        L_0x0028:
            r0 = new java.lang.IllegalStateException;
            r1 = new java.lang.StringBuilder;
            r2 = "failed to find lastEmittedLink for: ";
            r1.<init>(r2);
            r1 = r1.append(r4);
            r1 = r1.toString();
            r0.<init>(r1);
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.subjects.ReplaySubject.UnboundedReplayState.replayObserver(rx.subjects.SubjectSubscriptionManager$SubjectObserver):boolean");
        }

        public final Integer replayObserverFromIndex(Integer num, SubjectObserver<? super T> subjectObserver) {
            int intValue = num.intValue();
            while (intValue < get()) {
                accept(subjectObserver, intValue);
                intValue++;
            }
            return Integer.valueOf(intValue);
        }

        public final Integer replayObserverFromIndexTest(Integer num, SubjectObserver<? super T> subjectObserver, long j) {
            return replayObserverFromIndex(num, (SubjectObserver) subjectObserver);
        }

        public final int size() {
            int i = get();
            if (i <= 0) {
                return i;
            }
            Object obj = this.list.get(i - 1);
            return (this.nl.isCompleted(obj) || this.nl.isError(obj)) ? i - 1 : i;
        }

        public final boolean terminated() {
            return this.terminated;
        }

        public final T[] toArray(T[] tArr) {
            int size = size();
            if (size > 0) {
                int i;
                if (size > tArr.length) {
                    tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size);
                    i = 0;
                } else {
                    i = 0;
                }
                while (i < size) {
                    tArr[i] = this.list.get(i);
                    i++;
                }
                if (tArr.length > size) {
                    tArr[size] = null;
                }
            } else if (tArr.length > 0) {
                tArr[0] = null;
            }
            return tArr;
        }
    }

    ReplaySubject(Observable$OnSubscribe<T> observable$OnSubscribe, SubjectSubscriptionManager<T> subjectSubscriptionManager, ReplayState<T, ?> replayState) {
        super(observable$OnSubscribe);
        this.ssm = subjectSubscriptionManager;
        this.state = replayState;
    }

    private boolean caughtUp(SubjectObserver<? super T> subjectObserver) {
        if (subjectObserver.caughtUp) {
            return true;
        }
        if (this.state.replayObserver(subjectObserver)) {
            subjectObserver.caughtUp = true;
            subjectObserver.index(null);
        }
        return false;
    }

    public static <T> ReplaySubject<T> create() {
        return create(16);
    }

    public static <T> ReplaySubject<T> create(int i) {
        final ReplayState unboundedReplayState = new UnboundedReplayState(i);
        Object subjectSubscriptionManager = new SubjectSubscriptionManager();
        subjectSubscriptionManager.onStart = new Action1<SubjectObserver<T>>() {
            public final void call(SubjectObserver<T> subjectObserver) {
                subjectObserver.index(Integer.valueOf(unboundedReplayState.replayObserverFromIndex(Integer.valueOf(0), (SubjectObserver) subjectObserver).intValue()));
            }
        };
        subjectSubscriptionManager.onAdded = new Action1<SubjectObserver<T>>() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void call(rx.subjects.SubjectSubscriptionManager.SubjectObserver<T> r6) {
                /*
                r5 = this;
                r2 = 1;
                r1 = 0;
                monitor-enter(r6);
                r0 = r6.first;	 Catch:{ all -> 0x004a }
                if (r0 == 0) goto L_0x000b;
            L_0x0007:
                r0 = r6.emitting;	 Catch:{ all -> 0x004a }
                if (r0 == 0) goto L_0x000d;
            L_0x000b:
                monitor-exit(r6);	 Catch:{ all -> 0x004a }
            L_0x000c:
                return;
            L_0x000d:
                r0 = 0;
                r6.first = r0;	 Catch:{ all -> 0x004a }
                r0 = 1;
                r6.emitting = r0;	 Catch:{ all -> 0x004a }
                monitor-exit(r6);	 Catch:{ all -> 0x004a }
                r3 = r0;	 Catch:{ all -> 0x0056 }
            L_0x0016:
                r0 = r6.index();	 Catch:{ all -> 0x0056 }
                r0 = (java.lang.Integer) r0;	 Catch:{ all -> 0x0056 }
                r0 = r0.intValue();	 Catch:{ all -> 0x0056 }
                r4 = r3.get();	 Catch:{ all -> 0x0056 }
                if (r0 == r4) goto L_0x0031;
            L_0x0026:
                r0 = java.lang.Integer.valueOf(r0);	 Catch:{ all -> 0x0056 }
                r0 = r3.replayObserverFromIndex(r0, r6);	 Catch:{ all -> 0x0056 }
                r6.index(r0);	 Catch:{ all -> 0x0056 }
            L_0x0031:
                monitor-enter(r6);	 Catch:{ all -> 0x0056 }
                r0 = r3.get();	 Catch:{ all -> 0x004f }
                if (r4 != r0) goto L_0x004d;
            L_0x0038:
                r0 = 0;
                r6.emitting = r0;	 Catch:{ all -> 0x004f }
                monitor-exit(r6);	 Catch:{ all -> 0x003d }
                goto L_0x000c;
            L_0x003d:
                r0 = move-exception;
                r1 = r2;
            L_0x003f:
                monitor-exit(r6);	 Catch:{ all -> 0x0054 }
                throw r0;	 Catch:{ all -> 0x0041 }
            L_0x0041:
                r0 = move-exception;
            L_0x0042:
                if (r1 != 0) goto L_0x0049;
            L_0x0044:
                monitor-enter(r6);
                r1 = 0;
                r6.emitting = r1;	 Catch:{ all -> 0x0051 }
                monitor-exit(r6);	 Catch:{ all -> 0x0051 }
            L_0x0049:
                throw r0;
            L_0x004a:
                r0 = move-exception;
                monitor-exit(r6);	 Catch:{ all -> 0x004a }
                throw r0;
            L_0x004d:
                monitor-exit(r6);	 Catch:{ all -> 0x004f }
                goto L_0x0016;
            L_0x004f:
                r0 = move-exception;
                goto L_0x003f;
            L_0x0051:
                r0 = move-exception;
                monitor-exit(r6);	 Catch:{ all -> 0x0051 }
                throw r0;
            L_0x0054:
                r0 = move-exception;
                goto L_0x003f;
            L_0x0056:
                r0 = move-exception;
                goto L_0x0042;
                */
                throw new UnsupportedOperationException("Method not decompiled: rx.subjects.ReplaySubject.2.call(rx.subjects.SubjectSubscriptionManager$SubjectObserver):void");
            }
        };
        subjectSubscriptionManager.onTerminated = new Action1<SubjectObserver<T>>() {
            public final void call(SubjectObserver<T> subjectObserver) {
                Integer num = (Integer) subjectObserver.index();
                if (num == null) {
                    num = Integer.valueOf(0);
                }
                unboundedReplayState.replayObserverFromIndex(num, (SubjectObserver) subjectObserver);
            }
        };
        return new ReplaySubject(subjectSubscriptionManager, subjectSubscriptionManager, unboundedReplayState);
    }

    static <T> ReplaySubject<T> createUnbounded() {
        BoundedState boundedState = new BoundedState(new EmptyEvictionPolicy(), UtilityFunctions.identity(), UtilityFunctions.identity());
        return createWithState(boundedState, new DefaultOnAdd(boundedState));
    }

    public static <T> ReplaySubject<T> createWithSize(int i) {
        BoundedState boundedState = new BoundedState(new SizeEvictionPolicy(i), UtilityFunctions.identity(), UtilityFunctions.identity());
        return createWithState(boundedState, new DefaultOnAdd(boundedState));
    }

    static <T> ReplaySubject<T> createWithState(final BoundedState<T> boundedState, Action1<SubjectObserver<T>> action1) {
        Object subjectSubscriptionManager = new SubjectSubscriptionManager();
        subjectSubscriptionManager.onStart = action1;
        subjectSubscriptionManager.onAdded = new Action1<SubjectObserver<T>>() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void call(rx.subjects.SubjectSubscriptionManager.SubjectObserver<T> r6) {
                /*
                r5 = this;
                r2 = 1;
                r1 = 0;
                monitor-enter(r6);
                r0 = r6.first;	 Catch:{ all -> 0x0046 }
                if (r0 == 0) goto L_0x000b;
            L_0x0007:
                r0 = r6.emitting;	 Catch:{ all -> 0x0046 }
                if (r0 == 0) goto L_0x000d;
            L_0x000b:
                monitor-exit(r6);	 Catch:{ all -> 0x0046 }
            L_0x000c:
                return;
            L_0x000d:
                r0 = 0;
                r6.first = r0;	 Catch:{ all -> 0x0046 }
                r0 = 1;
                r6.emitting = r0;	 Catch:{ all -> 0x0046 }
                monitor-exit(r6);	 Catch:{ all -> 0x0046 }
            L_0x0014:
                r0 = r6.index();	 Catch:{ all -> 0x0052 }
                r0 = (rx.subjects.ReplaySubject.NodeList.Node) r0;	 Catch:{ all -> 0x0052 }
                r3 = r2;	 Catch:{ all -> 0x0052 }
                r3 = r3.tail();	 Catch:{ all -> 0x0052 }
                if (r0 == r3) goto L_0x002b;
            L_0x0022:
                r4 = r2;	 Catch:{ all -> 0x0052 }
                r0 = r4.replayObserverFromIndex(r0, r6);	 Catch:{ all -> 0x0052 }
                r6.index(r0);	 Catch:{ all -> 0x0052 }
            L_0x002b:
                monitor-enter(r6);	 Catch:{ all -> 0x0052 }
                r0 = r2;	 Catch:{ all -> 0x004b }
                r0 = r0.tail();	 Catch:{ all -> 0x004b }
                if (r3 != r0) goto L_0x0049;
            L_0x0034:
                r0 = 0;
                r6.emitting = r0;	 Catch:{ all -> 0x004b }
                monitor-exit(r6);	 Catch:{ all -> 0x0039 }
                goto L_0x000c;
            L_0x0039:
                r0 = move-exception;
                r1 = r2;
            L_0x003b:
                monitor-exit(r6);	 Catch:{ all -> 0x0050 }
                throw r0;	 Catch:{ all -> 0x003d }
            L_0x003d:
                r0 = move-exception;
            L_0x003e:
                if (r1 != 0) goto L_0x0045;
            L_0x0040:
                monitor-enter(r6);
                r1 = 0;
                r6.emitting = r1;	 Catch:{ all -> 0x004d }
                monitor-exit(r6);	 Catch:{ all -> 0x004d }
            L_0x0045:
                throw r0;
            L_0x0046:
                r0 = move-exception;
                monitor-exit(r6);	 Catch:{ all -> 0x0046 }
                throw r0;
            L_0x0049:
                monitor-exit(r6);	 Catch:{ all -> 0x004b }
                goto L_0x0014;
            L_0x004b:
                r0 = move-exception;
                goto L_0x003b;
            L_0x004d:
                r0 = move-exception;
                monitor-exit(r6);	 Catch:{ all -> 0x004d }
                throw r0;
            L_0x0050:
                r0 = move-exception;
                goto L_0x003b;
            L_0x0052:
                r0 = move-exception;
                goto L_0x003e;
                */
                throw new UnsupportedOperationException("Method not decompiled: rx.subjects.ReplaySubject.4.call(rx.subjects.SubjectSubscriptionManager$SubjectObserver):void");
            }
        };
        subjectSubscriptionManager.onTerminated = new Action1<SubjectObserver<T>>() {
            public final void call(SubjectObserver<T> subjectObserver) {
                Node node = (Node) subjectObserver.index();
                if (node == null) {
                    node = boundedState.head();
                }
                boundedState.replayObserverFromIndex(node, (SubjectObserver) subjectObserver);
            }
        };
        return new ReplaySubject(subjectSubscriptionManager, subjectSubscriptionManager, boundedState);
    }

    public static <T> ReplaySubject<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        BoundedState boundedState = new BoundedState(new TimeEvictionPolicy(timeUnit.toMillis(j), scheduler), new AddTimestamped(scheduler), new RemoveTimestamped());
        return createWithState(boundedState, new TimedOnAdd(boundedState, scheduler));
    }

    public static <T> ReplaySubject<T> createWithTimeAndSize(long j, TimeUnit timeUnit, int i, Scheduler scheduler) {
        BoundedState boundedState = new BoundedState(new PairEvictionPolicy(new SizeEvictionPolicy(i), new TimeEvictionPolicy(timeUnit.toMillis(j), scheduler)), new AddTimestamped(scheduler), new RemoveTimestamped());
        return createWithState(boundedState, new TimedOnAdd(boundedState, scheduler));
    }

    @Beta
    public final Throwable getThrowable() {
        NotificationLite notificationLite = this.ssm.nl;
        Object latest = this.ssm.getLatest();
        return notificationLite.isError(latest) ? notificationLite.getError(latest) : null;
    }

    @Beta
    public final T getValue() {
        return this.state.latest();
    }

    @Beta
    public final Object[] getValues() {
        Object[] values = getValues(EMPTY_ARRAY);
        return values == EMPTY_ARRAY ? new Object[0] : values;
    }

    @Beta
    public final T[] getValues(T[] tArr) {
        return this.state.toArray(tArr);
    }

    @Beta
    public final boolean hasAnyValue() {
        return !this.state.isEmpty();
    }

    @Beta
    public final boolean hasCompleted() {
        NotificationLite notificationLite = this.ssm.nl;
        Object latest = this.ssm.getLatest();
        return (latest == null || notificationLite.isError(latest)) ? false : true;
    }

    public final boolean hasObservers() {
        return this.ssm.observers().length > 0;
    }

    @Beta
    public final boolean hasThrowable() {
        return this.ssm.nl.isError(this.ssm.getLatest());
    }

    @Beta
    public final boolean hasValue() {
        return hasAnyValue();
    }

    public final void onCompleted() {
        if (this.ssm.active) {
            this.state.complete();
            for (SubjectObserver subjectObserver : this.ssm.terminate(NotificationLite.instance().completed())) {
                if (caughtUp(subjectObserver)) {
                    subjectObserver.onCompleted();
                }
            }
        }
    }

    public final void onError(Throwable th) {
        if (this.ssm.active) {
            this.state.error(th);
            List list = null;
            for (SubjectObserver subjectObserver : this.ssm.terminate(NotificationLite.instance().error(th))) {
                try {
                    if (caughtUp(subjectObserver)) {
                        subjectObserver.onError(th);
                    }
                } catch (Throwable th2) {
                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(th2);
                }
            }
            Exceptions.throwIfAny(list);
        }
    }

    public final void onNext(T t) {
        if (this.ssm.active) {
            this.state.next(t);
            for (SubjectObserver subjectObserver : this.ssm.observers()) {
                if (caughtUp(subjectObserver)) {
                    subjectObserver.onNext(t);
                }
            }
        }
    }

    @Beta
    public final int size() {
        return this.state.size();
    }

    final int subscriberCount() {
        return ((State) this.ssm.get()).observers.length;
    }
}
