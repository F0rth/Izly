package rx;

import java.util.concurrent.Callable;
import rx.subscriptions.BooleanSubscription;

final class Completable$9 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Callable val$callable;

    Completable$9(Callable callable) {
        this.val$callable = callable;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        BooleanSubscription booleanSubscription = new BooleanSubscription();
        completable$CompletableSubscriber.onSubscribe(booleanSubscription);
        try {
            this.val$callable.call();
            if (!booleanSubscription.isUnsubscribed()) {
                completable$CompletableSubscriber.onCompleted();
            }
        } catch (Throwable th) {
            if (!booleanSubscription.isUnsubscribed()) {
                completable$CompletableSubscriber.onError(th);
            }
        }
    }
}
