package rx.observables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.annotations.Experimental;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Action3;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.internal.operators.BufferUntilSubscriber;
import rx.observers.SerializedObserver;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

@Experimental
public abstract class AsyncOnSubscribe<S, T> implements Observable$OnSubscribe<T> {

    static final class AsyncOnSubscribeImpl<S, T> extends AsyncOnSubscribe<S, T> {
        private final Func0<? extends S> generator;
        private final Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> next;
        private final Action1<? super S> onUnsubscribe;

        public AsyncOnSubscribeImpl(Func0<? extends S> func0, Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> func3) {
            this(func0, func3, null);
        }

        AsyncOnSubscribeImpl(Func0<? extends S> func0, Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> func3, Action1<? super S> action1) {
            this.generator = func0;
            this.next = func3;
            this.onUnsubscribe = action1;
        }

        public AsyncOnSubscribeImpl(Func3<S, Long, Observer<Observable<? extends T>>, S> func3) {
            this(null, func3, null);
        }

        public AsyncOnSubscribeImpl(Func3<S, Long, Observer<Observable<? extends T>>, S> func3, Action1<? super S> action1) {
            this(null, func3, action1);
        }

        public final /* bridge */ /* synthetic */ void call(Object obj) {
            super.call((Subscriber) obj);
        }

        protected final S generateState() {
            return this.generator == null ? null : this.generator.call();
        }

        protected final S next(S s, long j, Observer<Observable<? extends T>> observer) {
            return this.next.call(s, Long.valueOf(j), observer);
        }

        protected final void onUnsubscribe(S s) {
            if (this.onUnsubscribe != null) {
                this.onUnsubscribe.call(s);
            }
        }
    }

    static final class AsyncOuterManager<S, T> implements Observer<Observable<? extends T>>, Producer, Subscription {
        private static final AtomicIntegerFieldUpdater<AsyncOuterManager> IS_UNSUBSCRIBED = AtomicIntegerFieldUpdater.newUpdater(AsyncOuterManager.class, "isUnsubscribed");
        Producer concatProducer;
        boolean emitting;
        long expectedDelivery;
        private boolean hasTerminated;
        private volatile int isUnsubscribed;
        private final UnicastSubject<Observable<T>> merger;
        private boolean onNextCalled;
        private final AsyncOnSubscribe<S, T> parent;
        List<Long> requests;
        private final SerializedObserver<Observable<? extends T>> serializedSubscriber;
        private S state;
        final CompositeSubscription subscriptions = new CompositeSubscription();

        public AsyncOuterManager(AsyncOnSubscribe<S, T> asyncOnSubscribe, S s, UnicastSubject<Observable<T>> unicastSubject) {
            this.parent = asyncOnSubscribe;
            this.serializedSubscriber = new SerializedObserver(this);
            this.state = s;
            this.merger = unicastSubject;
        }

        private void handleThrownError(Throwable th) {
            if (this.hasTerminated) {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                return;
            }
            this.hasTerminated = true;
            this.merger.onError(th);
            cleanup();
        }

        private void subscribeBufferToObservable(Observable<? extends T> observable) {
            final BufferUntilSubscriber create = BufferUntilSubscriber.create();
            final long j = this.expectedDelivery;
            final AnonymousClass1 anonymousClass1 = new Subscriber<T>() {
                long remaining = j;

                public void onCompleted() {
                    create.onCompleted();
                    long j = this.remaining;
                    if (j > 0) {
                        AsyncOuterManager.this.requestRemaining(j);
                    }
                }

                public void onError(Throwable th) {
                    create.onError(th);
                }

                public void onNext(T t) {
                    this.remaining--;
                    create.onNext(t);
                }
            };
            this.subscriptions.add(anonymousClass1);
            observable.doOnTerminate(new Action0() {
                public void call() {
                    AsyncOuterManager.this.subscriptions.remove(anonymousClass1);
                }
            }).subscribe(anonymousClass1);
            this.merger.onNext(create);
        }

        final void cleanup() {
            this.subscriptions.unsubscribe();
            try {
                this.parent.onUnsubscribe(this.state);
            } catch (Throwable th) {
                handleThrownError(th);
            }
        }

        public final boolean isUnsubscribed() {
            return this.isUnsubscribed != 0;
        }

        public final void nextIteration(long j) {
            this.state = this.parent.next(this.state, j, this.serializedSubscriber);
        }

        public final void onCompleted() {
            if (this.hasTerminated) {
                throw new IllegalStateException("Terminal event already emitted.");
            }
            this.hasTerminated = true;
            this.merger.onCompleted();
        }

        public final void onError(Throwable th) {
            if (this.hasTerminated) {
                throw new IllegalStateException("Terminal event already emitted.");
            }
            this.hasTerminated = true;
            this.merger.onError(th);
        }

        public final void onNext(Observable<? extends T> observable) {
            if (this.onNextCalled) {
                throw new IllegalStateException("onNext called multiple times!");
            }
            this.onNextCalled = true;
            if (!this.hasTerminated) {
                subscribeBufferToObservable(observable);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void request(long r8) {
            /*
            r7 = this;
            r4 = 0;
            r0 = 1;
            r1 = 0;
            r2 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1));
            if (r2 != 0) goto L_0x0009;
        L_0x0008:
            return;
        L_0x0009:
            r2 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1));
            if (r2 >= 0) goto L_0x0022;
        L_0x000d:
            r0 = new java.lang.IllegalStateException;
            r1 = new java.lang.StringBuilder;
            r2 = "Request can't be negative! ";
            r1.<init>(r2);
            r1 = r1.append(r8);
            r1 = r1.toString();
            r0.<init>(r1);
            throw r0;
        L_0x0022:
            monitor-enter(r7);
            r2 = r7.emitting;	 Catch:{ all -> 0x0059 }
            if (r2 == 0) goto L_0x0054;
        L_0x0027:
            r1 = r7.requests;	 Catch:{ all -> 0x0059 }
            if (r1 != 0) goto L_0x0032;
        L_0x002b:
            r1 = new java.util.ArrayList;	 Catch:{ all -> 0x0059 }
            r1.<init>();	 Catch:{ all -> 0x0059 }
            r7.requests = r1;	 Catch:{ all -> 0x0059 }
        L_0x0032:
            r2 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x0059 }
            r1.add(r2);	 Catch:{ all -> 0x0059 }
        L_0x0039:
            monitor-exit(r7);	 Catch:{ all -> 0x0059 }
            r1 = r7.concatProducer;
            r1.request(r8);
            if (r0 != 0) goto L_0x0008;
        L_0x0041:
            r0 = r7.tryEmit(r8);
            if (r0 != 0) goto L_0x0008;
        L_0x0047:
            monitor-enter(r7);
            r0 = r7.requests;	 Catch:{ all -> 0x0051 }
            if (r0 != 0) goto L_0x005c;
        L_0x004c:
            r0 = 0;
            r7.emitting = r0;	 Catch:{ all -> 0x0051 }
            monitor-exit(r7);	 Catch:{ all -> 0x0051 }
            goto L_0x0008;
        L_0x0051:
            r0 = move-exception;
            monitor-exit(r7);	 Catch:{ all -> 0x0051 }
            throw r0;
        L_0x0054:
            r0 = 1;
            r7.emitting = r0;	 Catch:{ all -> 0x0059 }
            r0 = r1;
            goto L_0x0039;
        L_0x0059:
            r0 = move-exception;
            monitor-exit(r7);	 Catch:{ all -> 0x0059 }
            throw r0;
        L_0x005c:
            r1 = 0;
            r7.requests = r1;	 Catch:{ all -> 0x0051 }
            monitor-exit(r7);	 Catch:{ all -> 0x0051 }
            r1 = r0.iterator();
        L_0x0064:
            r0 = r1.hasNext();
            if (r0 == 0) goto L_0x0047;
        L_0x006a:
            r0 = r1.next();
            r0 = (java.lang.Long) r0;
            r2 = r0.longValue();
            r0 = r7.tryEmit(r2);
            if (r0 == 0) goto L_0x0064;
        L_0x007a:
            goto L_0x0008;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.observables.AsyncOnSubscribe.AsyncOuterManager.request(long):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void requestRemaining(long r6) {
            /*
            r5 = this;
            r2 = 0;
            r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
            if (r0 != 0) goto L_0x0007;
        L_0x0006:
            return;
        L_0x0007:
            r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
            if (r0 >= 0) goto L_0x0020;
        L_0x000b:
            r0 = new java.lang.IllegalStateException;
            r1 = new java.lang.StringBuilder;
            r2 = "Request can't be negative! ";
            r1.<init>(r2);
            r1 = r1.append(r6);
            r1 = r1.toString();
            r0.<init>(r1);
            throw r0;
        L_0x0020:
            monitor-enter(r5);
            r0 = r5.emitting;	 Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x003c;
        L_0x0025:
            r0 = r5.requests;	 Catch:{ all -> 0x0039 }
            if (r0 != 0) goto L_0x0030;
        L_0x0029:
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0039 }
            r0.<init>();	 Catch:{ all -> 0x0039 }
            r5.requests = r0;	 Catch:{ all -> 0x0039 }
        L_0x0030:
            r1 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0039 }
            r0.add(r1);	 Catch:{ all -> 0x0039 }
            monitor-exit(r5);	 Catch:{ all -> 0x0039 }
            goto L_0x0006;
        L_0x0039:
            r0 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x0039 }
            throw r0;
        L_0x003c:
            r0 = 1;
            r5.emitting = r0;	 Catch:{ all -> 0x0039 }
            monitor-exit(r5);	 Catch:{ all -> 0x0039 }
            r0 = r5.tryEmit(r6);
            if (r0 != 0) goto L_0x0006;
        L_0x0046:
            monitor-enter(r5);
            r0 = r5.requests;	 Catch:{ all -> 0x0050 }
            if (r0 != 0) goto L_0x0053;
        L_0x004b:
            r0 = 0;
            r5.emitting = r0;	 Catch:{ all -> 0x0050 }
            monitor-exit(r5);	 Catch:{ all -> 0x0050 }
            goto L_0x0006;
        L_0x0050:
            r0 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x0050 }
            throw r0;
        L_0x0053:
            r1 = 0;
            r5.requests = r1;	 Catch:{ all -> 0x0050 }
            monitor-exit(r5);	 Catch:{ all -> 0x0050 }
            r1 = r0.iterator();
        L_0x005b:
            r0 = r1.hasNext();
            if (r0 == 0) goto L_0x0046;
        L_0x0061:
            r0 = r1.next();
            r0 = (java.lang.Long) r0;
            r2 = r0.longValue();
            r0 = r5.tryEmit(r2);
            if (r0 == 0) goto L_0x005b;
        L_0x0071:
            goto L_0x0006;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.observables.AsyncOnSubscribe.AsyncOuterManager.requestRemaining(long):void");
        }

        final void setConcatProducer(Producer producer) {
            if (this.concatProducer != null) {
                throw new IllegalStateException("setConcatProducer may be called at most once!");
            }
            this.concatProducer = producer;
        }

        final boolean tryEmit(long j) {
            if (isUnsubscribed()) {
                cleanup();
                return true;
            }
            try {
                this.onNextCalled = false;
                this.expectedDelivery = j;
                nextIteration(j);
                if (this.hasTerminated || isUnsubscribed()) {
                    cleanup();
                    return true;
                } else if (this.onNextCalled) {
                    return false;
                } else {
                    handleThrownError(new IllegalStateException("No events emitted!"));
                    return true;
                }
            } catch (Throwable th) {
                handleThrownError(th);
                return true;
            }
        }

        public final void unsubscribe() {
            if (IS_UNSUBSCRIBED.compareAndSet(this, 0, 1)) {
                synchronized (this) {
                    if (this.emitting) {
                        this.requests = new ArrayList();
                        this.requests.add(Long.valueOf(0));
                        return;
                    }
                    this.emitting = true;
                    cleanup();
                }
            }
        }
    }

    static final class UnicastSubject<T> extends Observable<T> implements Observer<T> {
        private State<T> state;

        static final class State<T> implements Observable$OnSubscribe<T> {
            Subscriber<? super T> subscriber;

            State() {
            }

            public final void call(Subscriber<? super T> subscriber) {
                synchronized (this) {
                    if (this.subscriber == null) {
                        this.subscriber = subscriber;
                        return;
                    }
                    subscriber.onError(new IllegalStateException("There can be only one subscriber"));
                }
            }
        }

        protected UnicastSubject(State<T> state) {
            super(state);
            this.state = state;
        }

        public static <T> UnicastSubject<T> create() {
            return new UnicastSubject(new State());
        }

        public final void onCompleted() {
            this.state.subscriber.onCompleted();
        }

        public final void onError(Throwable th) {
            this.state.subscriber.onError(th);
        }

        public final void onNext(T t) {
            this.state.subscriber.onNext(t);
        }
    }

    @Experimental
    public static <S, T> AsyncOnSubscribe<S, T> createSingleState(Func0<? extends S> func0, final Action3<? super S, Long, ? super Observer<Observable<? extends T>>> action3) {
        return new AsyncOnSubscribeImpl((Func0) func0, new Func3<S, Long, Observer<Observable<? extends T>>, S>() {
            public final S call(S s, Long l, Observer<Observable<? extends T>> observer) {
                action3.call(s, l, observer);
                return s;
            }
        });
    }

    @Experimental
    public static <S, T> AsyncOnSubscribe<S, T> createSingleState(Func0<? extends S> func0, final Action3<? super S, Long, ? super Observer<Observable<? extends T>>> action3, Action1<? super S> action1) {
        return new AsyncOnSubscribeImpl(func0, new Func3<S, Long, Observer<Observable<? extends T>>, S>() {
            public final S call(S s, Long l, Observer<Observable<? extends T>> observer) {
                action3.call(s, l, observer);
                return s;
            }
        }, action1);
    }

    @Experimental
    public static <S, T> AsyncOnSubscribe<S, T> createStateful(Func0<? extends S> func0, Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> func3) {
        return new AsyncOnSubscribeImpl((Func0) func0, (Func3) func3);
    }

    @Experimental
    public static <S, T> AsyncOnSubscribe<S, T> createStateful(Func0<? extends S> func0, Func3<? super S, Long, ? super Observer<Observable<? extends T>>, ? extends S> func3, Action1<? super S> action1) {
        return new AsyncOnSubscribeImpl(func0, func3, action1);
    }

    @Experimental
    public static <T> AsyncOnSubscribe<Void, T> createStateless(final Action2<Long, ? super Observer<Observable<? extends T>>> action2) {
        return new AsyncOnSubscribeImpl(new Func3<Void, Long, Observer<Observable<? extends T>>, Void>() {
            public final Void call(Void voidR, Long l, Observer<Observable<? extends T>> observer) {
                action2.call(l, observer);
                return voidR;
            }
        });
    }

    @Experimental
    public static <T> AsyncOnSubscribe<Void, T> createStateless(final Action2<Long, ? super Observer<Observable<? extends T>>> action2, final Action0 action0) {
        return new AsyncOnSubscribeImpl(new Func3<Void, Long, Observer<Observable<? extends T>>, Void>() {
            public final Void call(Void voidR, Long l, Observer<Observable<? extends T>> observer) {
                action2.call(l, observer);
                return null;
            }
        }, new Action1<Void>() {
            public final void call(Void voidR) {
                action0.call();
            }
        });
    }

    public final void call(final Subscriber<? super T> subscriber) {
        try {
            Object generateState = generateState();
            UnicastSubject create = UnicastSubject.create();
            final Producer asyncOuterManager = new AsyncOuterManager(this, generateState, create);
            AnonymousClass6 anonymousClass6 = new Subscriber<T>() {
                public void onCompleted() {
                    subscriber.onCompleted();
                }

                public void onError(Throwable th) {
                    subscriber.onError(th);
                }

                public void onNext(T t) {
                    subscriber.onNext(t);
                }

                public void setProducer(Producer producer) {
                    asyncOuterManager.setConcatProducer(producer);
                }
            };
            create.onBackpressureBuffer().concatMap(new Func1<Observable<T>, Observable<T>>() {
                public Observable<T> call(Observable<T> observable) {
                    return observable.onBackpressureBuffer();
                }
            }).unsafeSubscribe(anonymousClass6);
            subscriber.add(anonymousClass6);
            subscriber.add(asyncOuterManager);
            subscriber.setProducer(asyncOuterManager);
        } catch (Throwable th) {
            subscriber.onError(th);
        }
    }

    protected abstract S generateState();

    protected abstract S next(S s, long j, Observer<Observable<? extends T>> observer);

    protected void onUnsubscribe(S s) {
    }
}
