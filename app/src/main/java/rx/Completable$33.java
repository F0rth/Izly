package rx;

import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

class Completable$33 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Scheduler val$scheduler;

    Completable$33(Completable completable, Scheduler scheduler) {
        this.this$0 = completable;
        this.val$scheduler = scheduler;
    }

    public void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        this.this$0.subscribe(new Completable$CompletableSubscriber() {
            public void onCompleted() {
                completable$CompletableSubscriber.onCompleted();
            }

            public void onError(Throwable th) {
                completable$CompletableSubscriber.onError(th);
            }

            public void onSubscribe(final Subscription subscription) {
                completable$CompletableSubscriber.onSubscribe(Subscriptions.create(new Action0() {
                    public void call() {
                        final Worker createWorker = Completable$33.this.val$scheduler.createWorker();
                        createWorker.schedule(new Action0() {
                            public void call() {
                                try {
                                    subscription.unsubscribe();
                                } finally {
                                    createWorker.unsubscribe();
                                }
                            }
                        });
                    }
                }));
            }
        });
    }
}
