package rx;

import rx.functions.Func0;
import rx.subscriptions.Subscriptions;

final class Completable$5 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Func0 val$completableFunc0;

    Completable$5(Func0 func0) {
        this.val$completableFunc0 = func0;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        try {
            Completable completable = (Completable) this.val$completableFunc0.call();
            if (completable == null) {
                completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
                completable$CompletableSubscriber.onError(new NullPointerException("The completable returned is null"));
                return;
            }
            completable.subscribe(completable$CompletableSubscriber);
        } catch (Throwable th) {
            completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
            completable$CompletableSubscriber.onError(th);
        }
    }
}
