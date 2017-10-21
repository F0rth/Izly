package rx.plugins;

import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Observable$Operator;
import rx.Subscription;

public abstract class RxJavaObservableExecutionHook {
    public <T> Observable$OnSubscribe<T> onCreate(Observable$OnSubscribe<T> observable$OnSubscribe) {
        return observable$OnSubscribe;
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

    public <T> Observable$OnSubscribe<T> onSubscribeStart(Observable<? extends T> observable, Observable$OnSubscribe<T> observable$OnSubscribe) {
        return observable$OnSubscribe;
    }
}
