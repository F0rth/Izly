package rx.plugins;

import rx.Observable$OnSubscribe;
import rx.Observable$Operator;
import rx.Single;
import rx.Single.OnSubscribe;
import rx.Subscription;

public abstract class RxJavaSingleExecutionHook {
    public <T> OnSubscribe<T> onCreate(OnSubscribe<T> onSubscribe) {
        return onSubscribe;
    }

    public <T, R> Observable$Operator<? extends R, ? super T> onLift(Observable$Operator<? extends R, ? super T> observable$Operator) {
        return observable$Operator;
    }

    public <T> Throwable onSubscribeError(Throwable th) {
        return th;
    }

    public <T> Subscription onSubscribeReturn(Subscription subscription) {
        return subscription;
    }

    public <T> Observable$OnSubscribe<T> onSubscribeStart(Single<? extends T> single, Observable$OnSubscribe<T> observable$OnSubscribe) {
        return observable$OnSubscribe;
    }
}
