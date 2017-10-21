package rx;

import java.util.Arrays;
import rx.exceptions.CompositeException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.MultipleAssignmentSubscription;

class Completable$27 implements Completable$CompletableSubscriber {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ MultipleAssignmentSubscription val$mad;
    final /* synthetic */ Action0 val$onComplete;
    final /* synthetic */ Action1 val$onError;

    Completable$27(Completable completable, Action0 action0, MultipleAssignmentSubscription multipleAssignmentSubscription, Action1 action1) {
        this.this$0 = completable;
        this.val$onComplete = action0;
        this.val$mad = multipleAssignmentSubscription;
        this.val$onError = action1;
    }

    public void onCompleted() {
        try {
            this.val$onComplete.call();
            this.val$mad.unsubscribe();
        } catch (Throwable th) {
            onError(th);
        }
    }

    public void onError(Throwable th) {
        try {
            this.val$onError.call(th);
        } catch (Throwable th2) {
            CompositeException compositeException = new CompositeException(Arrays.asList(new Throwable[]{th, th2}));
            Completable.ERROR_HANDLER.handleError(compositeException);
            Completable.access$000(compositeException);
        } finally {
            this.val$mad.unsubscribe();
        }
    }

    public void onSubscribe(Subscription subscription) {
        this.val$mad.set(subscription);
    }
}
