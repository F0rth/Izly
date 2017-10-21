package rx;

class Completable$28 implements Completable$CompletableSubscriber {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Subscriber val$sw;

    Completable$28(Completable completable, Subscriber subscriber) {
        this.this$0 = completable;
        this.val$sw = subscriber;
    }

    public void onCompleted() {
        this.val$sw.onCompleted();
    }

    public void onError(Throwable th) {
        this.val$sw.onError(th);
    }

    public void onSubscribe(Subscription subscription) {
        this.val$sw.add(subscription);
    }
}
