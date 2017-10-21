package rx.internal.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import rx.Observable$OnSubscribe;
import rx.Observable$Operator;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.internal.producers.ProducerArbiter;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.UtilityFunctions;
import rx.observables.GroupedObservable;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.Subscriptions;

public final class OperatorGroupBy<T, K, V> implements Observable$Operator<GroupedObservable<K, V>, T> {
    final int bufferSize;
    final boolean delayError;
    final Func1<? super T, ? extends K> keySelector;
    final Func1<? super T, ? extends V> valueSelector;

    public static final class GroupByProducer implements Producer {
        final GroupBySubscriber<?, ?, ?> parent;

        public GroupByProducer(GroupBySubscriber<?, ?, ?> groupBySubscriber) {
            this.parent = groupBySubscriber;
        }

        public final void request(long j) {
            this.parent.requestMore(j);
        }
    }

    public static final class GroupBySubscriber<T, K, V> extends Subscriber<T> {
        static final AtomicIntegerFieldUpdater<GroupBySubscriber> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(GroupBySubscriber.class, "cancelled");
        static final AtomicIntegerFieldUpdater<GroupBySubscriber> GROUP_COUNT = AtomicIntegerFieldUpdater.newUpdater(GroupBySubscriber.class, "groupCount");
        static final Object NULL_KEY = new Object();
        static final AtomicLongFieldUpdater<GroupBySubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(GroupBySubscriber.class, "requested");
        static final AtomicIntegerFieldUpdater<GroupBySubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(GroupBySubscriber.class, "wip");
        final Subscriber<? super GroupedObservable<K, V>> actual;
        final int bufferSize;
        volatile int cancelled;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        volatile int groupCount;
        final Map<Object, GroupedUnicast<K, V>> groups = new ConcurrentHashMap();
        final Func1<? super T, ? extends K> keySelector;
        final GroupByProducer producer;
        final Queue<GroupedObservable<K, V>> queue = new ConcurrentLinkedQueue();
        volatile long requested;
        final ProducerArbiter s;
        final Func1<? super T, ? extends V> valueSelector;
        volatile int wip;

        public GroupBySubscriber(Subscriber<? super GroupedObservable<K, V>> subscriber, Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12, int i, boolean z) {
            this.actual = subscriber;
            this.keySelector = func1;
            this.valueSelector = func12;
            this.bufferSize = i;
            this.delayError = z;
            GROUP_COUNT.lazySet(this, 1);
            this.s = new ProducerArbiter();
            this.s.request((long) i);
            this.producer = new GroupByProducer(this);
        }

        public final void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1) && GROUP_COUNT.decrementAndGet(this) == 0) {
                unsubscribe();
            }
        }

        public final void cancel(K k) {
            if (k == null) {
                Object obj = NULL_KEY;
            }
            if (this.groups.remove(obj) != null && GROUP_COUNT.decrementAndGet(this) == 0) {
                unsubscribe();
            }
        }

        final boolean checkTerminated(boolean z, boolean z2, Subscriber<? super GroupedObservable<K, V>> subscriber, Queue<?> queue) {
            if (z) {
                Throwable th = this.error;
                if (th != null) {
                    errorAll(subscriber, queue, th);
                    return true;
                } else if (z2) {
                    this.actual.onCompleted();
                    return true;
                }
            }
            return false;
        }

        final void drain() {
            if (WIP.getAndIncrement(this) == 0) {
                Queue queue = this.queue;
                Subscriber subscriber = this.actual;
                int i = 1;
                while (!checkTerminated(this.done, queue.isEmpty(), subscriber, queue)) {
                    long j = this.requested;
                    Object obj = j == Long.MAX_VALUE ? 1 : null;
                    long j2 = 0;
                    while (j != 0) {
                        boolean z = this.done;
                        GroupedObservable groupedObservable = (GroupedObservable) queue.poll();
                        boolean z2 = groupedObservable == null;
                        if (!checkTerminated(z, z2, subscriber, queue)) {
                            if (z2) {
                                break;
                            }
                            subscriber.onNext(groupedObservable);
                            j--;
                            j2--;
                        } else {
                            return;
                        }
                    }
                    if (j2 != 0) {
                        if (obj == null) {
                            REQUESTED.addAndGet(this, j2);
                        }
                        this.s.request(-j2);
                    }
                    int addAndGet = WIP.addAndGet(this, -i);
                    if (addAndGet != 0) {
                        i = addAndGet;
                    } else {
                        return;
                    }
                }
            }
        }

        final void errorAll(Subscriber<? super GroupedObservable<K, V>> subscriber, Queue<?> queue, Throwable th) {
            queue.clear();
            List<GroupedUnicast> arrayList = new ArrayList(this.groups.values());
            this.groups.clear();
            for (GroupedUnicast onError : arrayList) {
                onError.onError(th);
            }
            subscriber.onError(th);
        }

        public final void onCompleted() {
            if (!this.done) {
                for (GroupedUnicast onComplete : this.groups.values()) {
                    onComplete.onComplete();
                }
                this.groups.clear();
                this.done = true;
                GROUP_COUNT.decrementAndGet(this);
                drain();
            }
        }

        public final void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                return;
            }
            this.error = th;
            this.done = true;
            GROUP_COUNT.decrementAndGet(this);
            drain();
        }

        public final void onNext(T t) {
            if (!this.done) {
                Queue queue = this.queue;
                Subscriber subscriber = this.actual;
                try {
                    Object call = this.keySelector.call(t);
                    Object obj = call != null ? call : NULL_KEY;
                    GroupedUnicast groupedUnicast = (GroupedUnicast) this.groups.get(obj);
                    if (groupedUnicast != null) {
                        obj = 1;
                    } else if (this.cancelled == 0) {
                        groupedUnicast = GroupedUnicast.createWith(call, this.bufferSize, this, this.delayError);
                        this.groups.put(obj, groupedUnicast);
                        GROUP_COUNT.getAndIncrement(this);
                        obj = null;
                        queue.offer(groupedUnicast);
                        drain();
                    } else {
                        return;
                    }
                    try {
                        groupedUnicast.onNext(this.valueSelector.call(t));
                        if (obj != null) {
                            this.s.request(1);
                        }
                    } catch (Throwable th) {
                        unsubscribe();
                        errorAll(subscriber, queue, th);
                    }
                } catch (Throwable th2) {
                    unsubscribe();
                    errorAll(subscriber, queue, th2);
                }
            }
        }

        public final void requestMore(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("n >= 0 required but it was " + j);
            }
            BackpressureUtils.getAndAddRequest(REQUESTED, this, j);
            drain();
        }

        public final void setProducer(Producer producer) {
            this.s.setProducer(producer);
        }
    }

    static final class GroupedUnicast<K, T> extends GroupedObservable<K, T> {
        final State<T, K> state;

        protected GroupedUnicast(K k, State<T, K> state) {
            super(k, state);
            this.state = state;
        }

        public static <T, K> GroupedUnicast<K, T> createWith(K k, int i, GroupBySubscriber<?, K, T> groupBySubscriber, boolean z) {
            return new GroupedUnicast(k, new State(i, groupBySubscriber, k, z));
        }

        public final void onComplete() {
            this.state.onComplete();
        }

        public final void onError(Throwable th) {
            this.state.onError(th);
        }

        public final void onNext(T t) {
            this.state.onNext(t);
        }
    }

    static final class State<T, K> extends AtomicInteger implements Observable$OnSubscribe<T>, Producer, Subscription {
        static final AtomicReferenceFieldUpdater<State, Subscriber> ACTUAL = AtomicReferenceFieldUpdater.newUpdater(State.class, Subscriber.class, "actual");
        static final AtomicIntegerFieldUpdater<State> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(State.class, "cancelled");
        static final AtomicIntegerFieldUpdater<State> ONCE = AtomicIntegerFieldUpdater.newUpdater(State.class, "once");
        static final AtomicLongFieldUpdater<State> REQUESTED = AtomicLongFieldUpdater.newUpdater(State.class, "requested");
        private static final long serialVersionUID = -3852313036005250360L;
        volatile Subscriber<? super T> actual;
        volatile int cancelled;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final K key;
        volatile int once;
        final GroupBySubscriber<?, K, T> parent;
        final Queue<Object> queue = new ConcurrentLinkedQueue();
        volatile long requested;

        public State(int i, GroupBySubscriber<?, K, T> groupBySubscriber, K k, boolean z) {
            this.parent = groupBySubscriber;
            this.key = k;
            this.delayError = z;
        }

        public final void call(Subscriber<? super T> subscriber) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                subscriber.add(this);
                subscriber.setProducer(this);
                ACTUAL.lazySet(this, subscriber);
                drain();
                return;
            }
            subscriber.onError(new IllegalStateException("Only one Subscriber allowed!"));
        }

        final boolean checkTerminated(boolean z, boolean z2, Subscriber<? super T> subscriber, boolean z3) {
            if (this.cancelled != 0) {
                this.queue.clear();
                this.parent.cancel(this.key);
                return true;
            }
            if (z) {
                Throwable th;
                if (!z3) {
                    th = this.error;
                    if (th != null) {
                        this.queue.clear();
                        subscriber.onError(th);
                        return true;
                    } else if (z2) {
                        subscriber.onCompleted();
                        return true;
                    }
                } else if (z2) {
                    th = this.error;
                    if (th != null) {
                        subscriber.onError(th);
                        return true;
                    }
                    subscriber.onCompleted();
                    return true;
                }
            }
            return false;
        }

        final void drain() {
            if (getAndIncrement() == 0) {
                Queue queue = this.queue;
                boolean z = this.delayError;
                Subscriber subscriber = this.actual;
                NotificationLite instance = NotificationLite.instance();
                Subscriber subscriber2 = subscriber;
                int i = 1;
                while (true) {
                    if (subscriber2 != null) {
                        if (!checkTerminated(this.done, queue.isEmpty(), subscriber2, z)) {
                            long j = this.requested;
                            Object obj = j == Long.MAX_VALUE ? 1 : null;
                            long j2 = 0;
                            while (j != 0) {
                                boolean z2 = this.done;
                                Object poll = queue.poll();
                                boolean z3 = poll == null;
                                if (!checkTerminated(z2, z3, subscriber2, z)) {
                                    if (z3) {
                                        break;
                                    }
                                    subscriber2.onNext(instance.getValue(poll));
                                    j--;
                                    j2--;
                                } else {
                                    return;
                                }
                            }
                            if (j2 != 0) {
                                if (obj == null) {
                                    REQUESTED.addAndGet(this, j2);
                                }
                                this.parent.s.request(-j2);
                            }
                        } else {
                            return;
                        }
                    }
                    int addAndGet = addAndGet(-i);
                    if (addAndGet == 0) {
                        return;
                    }
                    if (subscriber2 == null) {
                        subscriber2 = this.actual;
                        i = addAndGet;
                    } else {
                        i = addAndGet;
                    }
                }
            }
        }

        public final boolean isUnsubscribed() {
            return this.cancelled != 0;
        }

        public final void onComplete() {
            this.done = true;
            drain();
        }

        public final void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        public final void onNext(T t) {
            if (t == null) {
                this.error = new NullPointerException();
                this.done = true;
            } else {
                this.queue.offer(NotificationLite.instance().next(t));
            }
            drain();
        }

        public final void request(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("n >= required but it was " + j);
            } else if (j != 0) {
                BackpressureUtils.getAndAddRequest(REQUESTED, this, j);
                drain();
            }
        }

        public final void unsubscribe() {
            if (CANCELLED.compareAndSet(this, 0, 1) && getAndIncrement() == 0) {
                this.parent.cancel(this.key);
            }
        }
    }

    public OperatorGroupBy(Func1<? super T, ? extends K> func1) {
        this(func1, UtilityFunctions.identity(), RxRingBuffer.SIZE, false);
    }

    public OperatorGroupBy(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12) {
        this(func1, func12, RxRingBuffer.SIZE, false);
    }

    public OperatorGroupBy(Func1<? super T, ? extends K> func1, Func1<? super T, ? extends V> func12, int i, boolean z) {
        this.keySelector = func1;
        this.valueSelector = func12;
        this.bufferSize = i;
        this.delayError = z;
    }

    public final Subscriber<? super T> call(Subscriber<? super GroupedObservable<K, V>> subscriber) {
        final GroupBySubscriber groupBySubscriber = new GroupBySubscriber(subscriber, this.keySelector, this.valueSelector, this.bufferSize, this.delayError);
        subscriber.add(Subscriptions.create(new Action0() {
            public void call() {
                groupBySubscriber.cancel();
            }
        }));
        subscriber.setProducer(groupBySubscriber.producer);
        return groupBySubscriber;
    }
}
