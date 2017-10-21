package rx;

import rx.subscriptions.MultipleAssignmentSubscription;

class Completable$25 implements Completable$CompletableSubscriber {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ MultipleAssignmentSubscription val$mad;

    Completable$25(Completable completable, MultipleAssignmentSubscription multipleAssignmentSubscription) {
        this.this$0 = completable;
        this.val$mad = multipleAssignmentSubscription;
    }

    public void onCompleted() {
        this.val$mad.unsubscribe();
    }

    public void onError(Throwable th) {
        Completable.ERROR_HANDLER.handleError(th);
        this.val$mad.unsubscribe();
        Completable.access$000(th);
    }

    public void onSubscribe(Subscription subscription) {
        this.val$mad.set(subscription);
    }
}
