package rx.internal.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.SerializedObserver;
import rx.observers.SerializedSubscriber;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.RefCountSubscription;

public final class OnSubscribeGroupJoin<T1, T2, D1, D2, R> implements Observable$OnSubscribe<R> {
    protected final Observable<T1> left;
    protected final Func1<? super T1, ? extends Observable<D1>> leftDuration;
    protected final Func2<? super T1, ? super Observable<T2>, ? extends R> resultSelector;
    protected final Observable<T2> right;
    protected final Func1<? super T2, ? extends Observable<D2>> rightDuration;

    final class ResultManager implements Subscription {
        final RefCountSubscription cancel;
        final CompositeSubscription group;
        final Object guard = new Object();
        boolean leftDone;
        int leftIds;
        final Map<Integer, Observer<T2>> leftMap = new HashMap();
        boolean rightDone;
        int rightIds;
        final Map<Integer, T2> rightMap = new HashMap();
        final Subscriber<? super R> subscriber;

        final class LeftDurationObserver extends Subscriber<D1> {
            final int id;
            boolean once = true;

            public LeftDurationObserver(int i) {
                this.id = i;
            }

            public final void onCompleted() {
                if (this.once) {
                    Observer observer;
                    this.once = false;
                    synchronized (ResultManager.this.guard) {
                        observer = (Observer) ResultManager.this.leftMap.remove(Integer.valueOf(this.id));
                    }
                    if (observer != null) {
                        observer.onCompleted();
                    }
                    ResultManager.this.group.remove(this);
                }
            }

            public final void onError(Throwable th) {
                ResultManager.this.errorMain(th);
            }

            public final void onNext(D1 d1) {
                onCompleted();
            }
        }

        final class LeftObserver extends Subscriber<T1> {
            LeftObserver() {
            }

            public final void onCompleted() {
                List list = null;
                synchronized (ResultManager.this.guard) {
                    ResultManager.this.leftDone = true;
                    if (ResultManager.this.rightDone) {
                        list = new ArrayList(ResultManager.this.leftMap.values());
                        ResultManager.this.leftMap.clear();
                        ResultManager.this.rightMap.clear();
                    }
                }
                ResultManager.this.complete(list);
            }

            public final void onError(Throwable th) {
                ResultManager.this.errorAll(th);
            }

            public final void onNext(T1 t1) {
                try {
                    int i;
                    PublishSubject create = PublishSubject.create();
                    SerializedObserver serializedObserver = new SerializedObserver(create);
                    synchronized (ResultManager.this.guard) {
                        ResultManager resultManager = ResultManager.this;
                        i = resultManager.leftIds;
                        resultManager.leftIds = i + 1;
                        ResultManager.this.leftMap.put(Integer.valueOf(i), serializedObserver);
                    }
                    Observable create2 = Observable.create(new WindowObservableFunc(create, ResultManager.this.cancel));
                    Observable observable = (Observable) OnSubscribeGroupJoin.this.leftDuration.call(t1);
                    LeftDurationObserver leftDurationObserver = new LeftDurationObserver(i);
                    ResultManager.this.group.add(leftDurationObserver);
                    observable.unsafeSubscribe(leftDurationObserver);
                    Object call = OnSubscribeGroupJoin.this.resultSelector.call(t1, create2);
                    synchronized (ResultManager.this.guard) {
                        List<Object> arrayList = new ArrayList(ResultManager.this.rightMap.values());
                    }
                    ResultManager.this.subscriber.onNext(call);
                    for (Object onNext : arrayList) {
                        serializedObserver.onNext(onNext);
                    }
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, (Observer) this);
                }
            }
        }

        final class RightDurationObserver extends Subscriber<D2> {
            final int id;
            boolean once = true;

            public RightDurationObserver(int i) {
                this.id = i;
            }

            public final void onCompleted() {
                if (this.once) {
                    this.once = false;
                    synchronized (ResultManager.this.guard) {
                        ResultManager.this.rightMap.remove(Integer.valueOf(this.id));
                    }
                    ResultManager.this.group.remove(this);
                }
            }

            public final void onError(Throwable th) {
                ResultManager.this.errorMain(th);
            }

            public final void onNext(D2 d2) {
                onCompleted();
            }
        }

        final class RightObserver extends Subscriber<T2> {
            RightObserver() {
            }

            public final void onCompleted() {
                List list = null;
                synchronized (ResultManager.this.guard) {
                    ResultManager.this.rightDone = true;
                    if (ResultManager.this.leftDone) {
                        list = new ArrayList(ResultManager.this.leftMap.values());
                        ResultManager.this.leftMap.clear();
                        ResultManager.this.rightMap.clear();
                    }
                }
                ResultManager.this.complete(list);
            }

            public final void onError(Throwable th) {
                ResultManager.this.errorAll(th);
            }

            public final void onNext(T2 t2) {
                try {
                    int i;
                    synchronized (ResultManager.this.guard) {
                        ResultManager resultManager = ResultManager.this;
                        i = resultManager.rightIds;
                        resultManager.rightIds = i + 1;
                        ResultManager.this.rightMap.put(Integer.valueOf(i), t2);
                    }
                    Observable observable = (Observable) OnSubscribeGroupJoin.this.rightDuration.call(t2);
                    RightDurationObserver rightDurationObserver = new RightDurationObserver(i);
                    ResultManager.this.group.add(rightDurationObserver);
                    observable.unsafeSubscribe(rightDurationObserver);
                    synchronized (ResultManager.this.guard) {
                        List<Observer> arrayList = new ArrayList(ResultManager.this.leftMap.values());
                    }
                    for (Observer onNext : arrayList) {
                        onNext.onNext(t2);
                    }
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, (Observer) this);
                }
            }
        }

        public ResultManager(Subscriber<? super R> subscriber) {
            this.subscriber = subscriber;
            this.group = new CompositeSubscription();
            this.cancel = new RefCountSubscription(this.group);
        }

        final void complete(List<Observer<T2>> list) {
            if (list != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ((Observer) it.next()).onCompleted();
                }
                this.subscriber.onCompleted();
                this.cancel.unsubscribe();
            }
        }

        final void errorAll(Throwable th) {
            synchronized (this.guard) {
                List<Observer> arrayList = new ArrayList(this.leftMap.values());
                this.leftMap.clear();
                this.rightMap.clear();
            }
            for (Observer onError : arrayList) {
                onError.onError(th);
            }
            this.subscriber.onError(th);
            this.cancel.unsubscribe();
        }

        final void errorMain(Throwable th) {
            synchronized (this.guard) {
                this.leftMap.clear();
                this.rightMap.clear();
            }
            this.subscriber.onError(th);
            this.cancel.unsubscribe();
        }

        public final void init() {
            LeftObserver leftObserver = new LeftObserver();
            RightObserver rightObserver = new RightObserver();
            this.group.add(leftObserver);
            this.group.add(rightObserver);
            OnSubscribeGroupJoin.this.left.unsafeSubscribe(leftObserver);
            OnSubscribeGroupJoin.this.right.unsafeSubscribe(rightObserver);
        }

        public final boolean isUnsubscribed() {
            return this.cancel.isUnsubscribed();
        }

        public final void unsubscribe() {
            this.cancel.unsubscribe();
        }
    }

    static final class WindowObservableFunc<T> implements Observable$OnSubscribe<T> {
        final RefCountSubscription refCount;
        final Observable<T> underlying;

        final class WindowSubscriber extends Subscriber<T> {
            private final Subscription ref;
            final Subscriber<? super T> subscriber;

            public WindowSubscriber(Subscriber<? super T> subscriber, Subscription subscription) {
                super(subscriber);
                this.subscriber = subscriber;
                this.ref = subscription;
            }

            public final void onCompleted() {
                this.subscriber.onCompleted();
                this.ref.unsubscribe();
            }

            public final void onError(Throwable th) {
                this.subscriber.onError(th);
                this.ref.unsubscribe();
            }

            public final void onNext(T t) {
                this.subscriber.onNext(t);
            }
        }

        public WindowObservableFunc(Observable<T> observable, RefCountSubscription refCountSubscription) {
            this.refCount = refCountSubscription;
            this.underlying = observable;
        }

        public final void call(Subscriber<? super T> subscriber) {
            Subscription subscription = this.refCount.get();
            WindowSubscriber windowSubscriber = new WindowSubscriber(subscriber, subscription);
            windowSubscriber.add(subscription);
            this.underlying.unsafeSubscribe(windowSubscriber);
        }
    }

    public OnSubscribeGroupJoin(Observable<T1> observable, Observable<T2> observable2, Func1<? super T1, ? extends Observable<D1>> func1, Func1<? super T2, ? extends Observable<D2>> func12, Func2<? super T1, ? super Observable<T2>, ? extends R> func2) {
        this.left = observable;
        this.right = observable2;
        this.leftDuration = func1;
        this.rightDuration = func12;
        this.resultSelector = func2;
    }

    public final void call(Subscriber<? super R> subscriber) {
        ResultManager resultManager = new ResultManager(new SerializedSubscriber(subscriber));
        subscriber.add(resultManager);
        resultManager.init();
    }
}
