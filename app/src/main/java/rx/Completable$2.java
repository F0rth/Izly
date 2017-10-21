package rx;

import rx.subscriptions.Subscriptions;

final class Completable$2 implements Completable$CompletableOnSubscribe {
    Completable$2() {
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
    }
}
