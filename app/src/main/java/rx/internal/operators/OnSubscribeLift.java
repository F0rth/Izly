package rx.internal.operators;

import rx.Observable$OnSubscribe;
import rx.Observable$Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.plugins.RxJavaObservableExecutionHook;
import rx.plugins.RxJavaPlugins;

public final class OnSubscribeLift<T, R> implements Observable$OnSubscribe<R> {
    static final RxJavaObservableExecutionHook hook = RxJavaPlugins.getInstance().getObservableExecutionHook();
    final Observable$Operator<? extends R, ? super T> operator;
    final Observable$OnSubscribe<T> parent;

    public OnSubscribeLift(Observable$OnSubscribe<T> observable$OnSubscribe, Observable$Operator<? extends R, ? super T> observable$Operator) {
        this.parent = observable$OnSubscribe;
        this.operator = observable$Operator;
    }

    public final void call(Subscriber<? super R> subscriber) {
        Subscriber subscriber2;
        try {
            subscriber2 = (Subscriber) hook.onLift(this.operator).call(subscriber);
            subscriber2.onStart();
            this.parent.call(subscriber2);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            subscriber.onError(th);
        }
    }
}
