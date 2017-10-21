package rx;

import rx.functions.Action0;
import rx.subscriptions.BooleanSubscription;

final class Completable$8 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Action0 val$action;

    Completable$8(Action0 action0) {
        this.val$action = action0;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        BooleanSubscription booleanSubscription = new BooleanSubscription();
        completable$CompletableSubscriber.onSubscribe(booleanSubscription);
        try {
            this.val$action.call();
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
