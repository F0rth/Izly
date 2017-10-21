package rx.observables;

import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Subscription;
import rx.annotations.Beta;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.internal.operators.OnSubscribeAutoConnect;
import rx.internal.operators.OnSubscribeRefCount;

public abstract class ConnectableObservable<T> extends Observable<T> {
    protected ConnectableObservable(Observable$OnSubscribe<T> observable$OnSubscribe) {
        super(observable$OnSubscribe);
    }

    @Beta
    public Observable<T> autoConnect() {
        return autoConnect(1);
    }

    @Beta
    public Observable<T> autoConnect(int i) {
        return autoConnect(i, Actions.empty());
    }

    @Beta
    public Observable<T> autoConnect(int i, Action1<? super Subscription> action1) {
        if (i > 0) {
            return create(new OnSubscribeAutoConnect(this, i, action1));
        }
        connect(action1);
        return this;
    }

    public final Subscription connect() {
        final Subscription[] subscriptionArr = new Subscription[1];
        connect(new Action1<Subscription>() {
            public void call(Subscription subscription) {
                subscriptionArr[0] = subscription;
            }
        });
        return subscriptionArr[0];
    }

    public abstract void connect(Action1<? super Subscription> action1);

    public Observable<T> refCount() {
        return create(new OnSubscribeRefCount(this));
    }
}
