package rx;

import rx.functions.Action0;
import rx.subscriptions.MultipleAssignmentSubscription;

class Completable$26 implements Completable$CompletableSubscriber {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ MultipleAssignmentSubscription val$mad;
    final /* synthetic */ Action0 val$onComplete;

    Completable$26(Completable completable, Action0 action0, MultipleAssignmentSubscription multipleAssignmentSubscription) {
        this.this$0 = completable;
        this.val$onComplete = action0;
        this.val$mad = multipleAssignmentSubscription;
    }

    public void onCompleted() {
        try {
            this.val$onComplete.call();
        } catch (Throwable th) {
            Completable.ERROR_HANDLER.handleError(th);
            Completable.access$000(th);
        } finally {
            this.val$mad.unsubscribe();
        }
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
