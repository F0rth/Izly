package rx;

import rx.subscriptions.Subscriptions;

final class Completable$7 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Throwable val$error;

    Completable$7(Throwable th) {
        this.val$error = th;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
        completable$CompletableSubscriber.onError(this.val$error);
    }
}
