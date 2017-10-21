package rx;

import rx.subscriptions.Subscriptions;

final class Completable$1 implements Completable$CompletableOnSubscribe {
    Completable$1() {
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
        completable$CompletableSubscriber.onCompleted();
    }
}
