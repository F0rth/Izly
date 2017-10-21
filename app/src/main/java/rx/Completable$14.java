package rx;

import java.util.concurrent.CountDownLatch;

class Completable$14 implements Completable$CompletableSubscriber {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ CountDownLatch val$cdl;
    final /* synthetic */ Throwable[] val$err;

    Completable$14(Completable completable, CountDownLatch countDownLatch, Throwable[] thArr) {
        this.this$0 = completable;
        this.val$cdl = countDownLatch;
        this.val$err = thArr;
    }

    public void onCompleted() {
        this.val$cdl.countDown();
    }

    public void onError(Throwable th) {
        this.val$err[0] = th;
        this.val$cdl.countDown();
    }

    public void onSubscribe(Subscription subscription) {
    }
}
