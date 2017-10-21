package rx;

final class Completable$11 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Single val$single;

    Completable$11(Single single) {
        this.val$single = single;
    }

    public final void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        SingleSubscriber anonymousClass1 = new SingleSubscriber<Object>() {
            public void onError(Throwable th) {
                completable$CompletableSubscriber.onError(th);
            }

            public void onSuccess(Object obj) {
                completable$CompletableSubscriber.onCompleted();
            }
        };
        completable$CompletableSubscriber.onSubscribe(anonymousClass1);
        this.val$single.subscribe(anonymousClass1);
    }
}
