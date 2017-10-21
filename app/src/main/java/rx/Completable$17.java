package rx;

import java.util.Arrays;
import rx.exceptions.CompositeException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

class Completable$17 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Action0 val$onAfterComplete;
    final /* synthetic */ Action0 val$onComplete;
    final /* synthetic */ Action1 val$onError;
    final /* synthetic */ Action1 val$onSubscribe;
    final /* synthetic */ Action0 val$onUnsubscribe;

    Completable$17(Completable completable, Action0 action0, Action0 action02, Action1 action1, Action1 action12, Action0 action03) {
        this.this$0 = completable;
        this.val$onComplete = action0;
        this.val$onAfterComplete = action02;
        this.val$onError = action1;
        this.val$onSubscribe = action12;
        this.val$onUnsubscribe = action03;
    }

    public void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        this.this$0.subscribe(new Completable$CompletableSubscriber() {
            public void onCompleted() {
                try {
                    Completable$17.this.val$onComplete.call();
                    completable$CompletableSubscriber.onCompleted();
                    try {
                        Completable$17.this.val$onAfterComplete.call();
                    } catch (Throwable th) {
                        Completable.ERROR_HANDLER.handleError(th);
                    }
                } catch (Throwable th2) {
                    completable$CompletableSubscriber.onError(th2);
                }
            }

            public void onError(Throwable th) {
                try {
                    Completable$17.this.val$onError.call(th);
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    th = new CompositeException(Arrays.asList(new Throwable[]{th, th3}));
                }
                completable$CompletableSubscriber.onError(th);
            }

            public void onSubscribe(final Subscription subscription) {
                try {
                    Completable$17.this.val$onSubscribe.call(subscription);
                    completable$CompletableSubscriber.onSubscribe(Subscriptions.create(new Action0() {
                        public void call() {
                            try {
                                Completable$17.this.val$onUnsubscribe.call();
                            } catch (Throwable th) {
                                Completable.ERROR_HANDLER.handleError(th);
                            }
                            subscription.unsubscribe();
                        }
                    }));
                } catch (Throwable th) {
                    subscription.unsubscribe();
                    completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
                    completable$CompletableSubscriber.onError(th);
                }
            }
        });
    }
}
