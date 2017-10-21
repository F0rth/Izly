package rx.subjects;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.internal.operators.NotificationLite;
import rx.subscriptions.Subscriptions;

final class SubjectSubscriptionManager<T> extends AtomicReference<State<T>> implements Observable$OnSubscribe<T> {
    boolean active = true;
    volatile Object latest;
    public final NotificationLite<T> nl = NotificationLite.instance();
    Action1<SubjectObserver<T>> onAdded = Actions.empty();
    Action1<SubjectObserver<T>> onStart = Actions.empty();
    Action1<SubjectObserver<T>> onTerminated = Actions.empty();

    public static final class State<T> {
        static final State EMPTY = new State(false, NO_OBSERVERS);
        static final SubjectObserver[] NO_OBSERVERS = new SubjectObserver[0];
        static final State TERMINATED = new State(true, NO_OBSERVERS);
        final SubjectObserver[] observers;
        final boolean terminated;

        public State(boolean z, SubjectObserver[] subjectObserverArr) {
            this.terminated = z;
            this.observers = subjectObserverArr;
        }

        public final State add(SubjectObserver subjectObserver) {
            int length = this.observers.length;
            Object obj = new SubjectObserver[(length + 1)];
            System.arraycopy(this.observers, 0, obj, 0, length);
            obj[length] = subjectObserver;
            return new State(this.terminated, obj);
        }

        public final State remove(SubjectObserver subjectObserver) {
            SubjectObserver[] subjectObserverArr = this.observers;
            int length = subjectObserverArr.length;
            if (length == 1 && subjectObserverArr[0] == subjectObserver) {
                return EMPTY;
            }
            if (length == 0) {
                return this;
            }
            Object obj = new SubjectObserver[(length - 1)];
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3;
                SubjectObserver subjectObserver2 = subjectObserverArr[i];
                if (subjectObserver2 == subjectObserver) {
                    i3 = i2;
                } else if (i2 == length - 1) {
                    return this;
                } else {
                    i3 = i2 + 1;
                    obj[i2] = subjectObserver2;
                }
                i++;
                i2 = i3;
            }
            if (i2 == 0) {
                return EMPTY;
            }
            SubjectObserver[] subjectObserverArr2;
            if (i2 < length - 1) {
                subjectObserverArr2 = new SubjectObserver[i2];
                System.arraycopy(obj, 0, subjectObserverArr2, 0, i2);
            } else {
                Object obj2 = obj;
            }
            return new State(this.terminated, subjectObserverArr2);
        }
    }

    public static final class SubjectObserver<T> implements Observer<T> {
        final Subscriber<? super T> actual;
        protected volatile boolean caughtUp;
        boolean emitting;
        boolean fastPath;
        boolean first = true;
        private volatile Object index;
        List<Object> queue;

        public SubjectObserver(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        protected final void accept(Object obj, NotificationLite<T> notificationLite) {
            if (obj != null) {
                notificationLite.accept(this.actual, obj);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected final void emitFirst(java.lang.Object r3, rx.internal.operators.NotificationLite<T> r4) {
            /*
            r2 = this;
            r0 = 0;
            monitor-enter(r2);
            r1 = r2.first;	 Catch:{ all -> 0x001c }
            if (r1 == 0) goto L_0x000a;
        L_0x0006:
            r1 = r2.emitting;	 Catch:{ all -> 0x001c }
            if (r1 == 0) goto L_0x000c;
        L_0x000a:
            monitor-exit(r2);	 Catch:{ all -> 0x001c }
        L_0x000b:
            return;
        L_0x000c:
            r1 = 0;
            r2.first = r1;	 Catch:{ all -> 0x001c }
            if (r3 == 0) goto L_0x0012;
        L_0x0011:
            r0 = 1;
        L_0x0012:
            r2.emitting = r0;	 Catch:{ all -> 0x001c }
            monitor-exit(r2);	 Catch:{ all -> 0x001c }
            if (r3 == 0) goto L_0x000b;
        L_0x0017:
            r0 = 0;
            r2.emitLoop(r0, r3, r4);
            goto L_0x000b;
        L_0x001c:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x001c }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.subjects.SubjectSubscriptionManager.SubjectObserver.emitFirst(java.lang.Object, rx.internal.operators.NotificationLite):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected final void emitLoop(java.util.List<java.lang.Object> r6, java.lang.Object r7, rx.internal.operators.NotificationLite<T> r8) {
            /*
            r5 = this;
            r2 = 1;
            r0 = 0;
            r1 = r2;
        L_0x0003:
            if (r6 == 0) goto L_0x0020;
        L_0x0005:
            r3 = r6.iterator();	 Catch:{ all -> 0x0017 }
        L_0x0009:
            r4 = r3.hasNext();	 Catch:{ all -> 0x0017 }
            if (r4 == 0) goto L_0x0020;
        L_0x000f:
            r4 = r3.next();	 Catch:{ all -> 0x0017 }
            r5.accept(r4, r8);	 Catch:{ all -> 0x0017 }
            goto L_0x0009;
        L_0x0017:
            r1 = move-exception;
        L_0x0018:
            if (r0 != 0) goto L_0x001f;
        L_0x001a:
            monitor-enter(r5);
            r0 = 0;
            r5.emitting = r0;	 Catch:{ all -> 0x003a }
            monitor-exit(r5);	 Catch:{ all -> 0x003a }
        L_0x001f:
            throw r1;
        L_0x0020:
            if (r1 == 0) goto L_0x0026;
        L_0x0022:
            r5.accept(r7, r8);	 Catch:{ all -> 0x0017 }
            r1 = r0;
        L_0x0026:
            monitor-enter(r5);	 Catch:{ all -> 0x0017 }
            r6 = r5.queue;	 Catch:{ all -> 0x0035 }
            r3 = 0;
            r5.queue = r3;	 Catch:{ all -> 0x0035 }
            if (r6 != 0) goto L_0x0033;
        L_0x002e:
            r1 = 0;
            r5.emitting = r1;	 Catch:{ all -> 0x0035 }
            monitor-exit(r5);	 Catch:{ all -> 0x003d }
            return;
        L_0x0033:
            monitor-exit(r5);	 Catch:{ all -> 0x0035 }
            goto L_0x0003;
        L_0x0035:
            r1 = move-exception;
        L_0x0036:
            monitor-exit(r5);	 Catch:{ all -> 0x0040 }
            throw r1;	 Catch:{ all -> 0x0038 }
        L_0x0038:
            r1 = move-exception;
            goto L_0x0018;
        L_0x003a:
            r0 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x003a }
            throw r0;
        L_0x003d:
            r1 = move-exception;
            r0 = r2;
            goto L_0x0036;
        L_0x0040:
            r1 = move-exception;
            goto L_0x0036;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.subjects.SubjectSubscriptionManager.SubjectObserver.emitLoop(java.util.List, java.lang.Object, rx.internal.operators.NotificationLite):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected final void emitNext(java.lang.Object r2, rx.internal.operators.NotificationLite<T> r3) {
            /*
            r1 = this;
            r0 = r1.fastPath;
            if (r0 != 0) goto L_0x0022;
        L_0x0004:
            monitor-enter(r1);
            r0 = 0;
            r1.first = r0;	 Catch:{ all -> 0x0028 }
            r0 = r1.emitting;	 Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x001e;
        L_0x000c:
            r0 = r1.queue;	 Catch:{ all -> 0x0028 }
            if (r0 != 0) goto L_0x0017;
        L_0x0010:
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0028 }
            r0.<init>();	 Catch:{ all -> 0x0028 }
            r1.queue = r0;	 Catch:{ all -> 0x0028 }
        L_0x0017:
            r0 = r1.queue;	 Catch:{ all -> 0x0028 }
            r0.add(r2);	 Catch:{ all -> 0x0028 }
            monitor-exit(r1);	 Catch:{ all -> 0x0028 }
        L_0x001d:
            return;
        L_0x001e:
            monitor-exit(r1);	 Catch:{ all -> 0x0028 }
            r0 = 1;
            r1.fastPath = r0;
        L_0x0022:
            r0 = r1.actual;
            r3.accept(r0, r2);
            goto L_0x001d;
        L_0x0028:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0028 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.subjects.SubjectSubscriptionManager.SubjectObserver.emitNext(java.lang.Object, rx.internal.operators.NotificationLite):void");
        }

        protected final Observer<? super T> getActual() {
            return this.actual;
        }

        public final <I> I index() {
            return this.index;
        }

        public final void index(Object obj) {
            this.index = obj;
        }

        public final void onCompleted() {
            this.actual.onCompleted();
        }

        public final void onError(Throwable th) {
            this.actual.onError(th);
        }

        public final void onNext(T t) {
            this.actual.onNext(t);
        }
    }

    public SubjectSubscriptionManager() {
        super(State.EMPTY);
    }

    final boolean add(SubjectObserver<T> subjectObserver) {
        State state;
        do {
            state = (State) get();
            if (state.terminated) {
                this.onTerminated.call(subjectObserver);
                return false;
            }
        } while (!compareAndSet(state, state.add(subjectObserver)));
        this.onAdded.call(subjectObserver);
        return true;
    }

    final void addUnsubscriber(Subscriber<? super T> subscriber, final SubjectObserver<T> subjectObserver) {
        subscriber.add(Subscriptions.create(new Action0() {
            public void call() {
                SubjectSubscriptionManager.this.remove(subjectObserver);
            }
        }));
    }

    public final void call(Subscriber<? super T> subscriber) {
        SubjectObserver subjectObserver = new SubjectObserver(subscriber);
        addUnsubscriber(subscriber, subjectObserver);
        this.onStart.call(subjectObserver);
        if (!subscriber.isUnsubscribed() && add(subjectObserver) && subscriber.isUnsubscribed()) {
            remove(subjectObserver);
        }
    }

    final Object getLatest() {
        return this.latest;
    }

    final SubjectObserver<T>[] next(Object obj) {
        setLatest(obj);
        return ((State) get()).observers;
    }

    final SubjectObserver<T>[] observers() {
        return ((State) get()).observers;
    }

    final void remove(SubjectObserver<T> subjectObserver) {
        State state;
        State remove;
        do {
            state = (State) get();
            if (!state.terminated) {
                remove = state.remove(subjectObserver);
                if (remove == state) {
                    return;
                }
            } else {
                return;
            }
        } while (!compareAndSet(state, remove));
    }

    final void setLatest(Object obj) {
        this.latest = obj;
    }

    final SubjectObserver<T>[] terminate(Object obj) {
        setLatest(obj);
        this.active = false;
        return ((State) get()).terminated ? State.NO_OBSERVERS : ((State) getAndSet(State.TERMINATED)).observers;
    }
}
