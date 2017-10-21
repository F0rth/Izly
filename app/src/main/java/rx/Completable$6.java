package rx;

import rx.functions.Func0;
import rx.subscriptions.Subscriptions;

final class Completable$6 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Func0 val$errorFunc0;

    Completable$6(Func0 func0) {
        this.val$errorFunc0 = func0;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        Throwable th;
        completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
        try {
            th = (Throwable) this.val$errorFunc0.call();
        } catch (Throwable th2) {
            th = th2;
        }
        if (th == null) {
            th = new NullPointerException("The error supplied is null");
        }
        completable$CompletableSubscriber.onError(th);
    }
}
