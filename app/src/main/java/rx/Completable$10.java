package rx;

final class Completable$10 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Observable val$flowable;

    Completable$10(Observable observable) {
        this.val$flowable = observable;
    }

    public final void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        AnonymousClass1 anonymousClass1 = new Subscriber<Object>() {
            public void onCompleted() {
                completable$CompletableSubscriber.onCompleted();
            }

            public void onError(Throwable th) {
                completable$CompletableSubscriber.onError(th);
            }

            public void onNext(Object obj) {
            }
        };
        completable$CompletableSubscriber.onSubscribe(anonymousClass1);
        this.val$flowable.unsafeSubscribe(anonymousClass1);
    }
}
