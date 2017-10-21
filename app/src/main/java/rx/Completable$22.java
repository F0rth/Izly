package rx;

import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.internal.util.SubscriptionList;

class Completable$22 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Scheduler val$scheduler;

    Completable$22(Completable completable, Scheduler scheduler) {
        this.this$0 = completable;
        this.val$scheduler = scheduler;
    }

    public void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        final SubscriptionList subscriptionList = new SubscriptionList();
        final Worker createWorker = this.val$scheduler.createWorker();
        subscriptionList.add(createWorker);
        completable$CompletableSubscriber.onSubscribe(subscriptionList);
        this.this$0.subscribe(new Completable$CompletableSubscriber() {
            public void onCompleted() {
                createWorker.schedule(new Action0() {
                    public void call() {
                        try {
                            completable$CompletableSubscriber.onCompleted();
                        } finally {
                            subscriptionList.unsubscribe();
                        }
                    }
                });
            }

            public void onError(final Throwable th) {
                createWorker.schedule(new Action0() {
                    public void call() {
                        try {
                            completable$CompletableSubscriber.onError(th);
                        } finally {
                            subscriptionList.unsubscribe();
                        }
                    }
                });
            }

            public void onSubscribe(Subscription subscription) {
                subscriptionList.add(subscription);
            }
        });
    }
}
