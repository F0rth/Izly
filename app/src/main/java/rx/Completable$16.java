package rx;

import java.util.concurrent.TimeUnit;
import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.subscriptions.CompositeSubscription;

class Completable$16 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ long val$delay;
    final /* synthetic */ boolean val$delayError;
    final /* synthetic */ Scheduler val$scheduler;
    final /* synthetic */ TimeUnit val$unit;

    Completable$16(Completable completable, Scheduler scheduler, long j, TimeUnit timeUnit, boolean z) {
        this.this$0 = completable;
        this.val$scheduler = scheduler;
        this.val$delay = j;
        this.val$unit = timeUnit;
        this.val$delayError = z;
    }

    public void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        final Worker createWorker = this.val$scheduler.createWorker();
        compositeSubscription.add(createWorker);
        this.this$0.subscribe(new Completable$CompletableSubscriber() {
            public void onCompleted() {
                compositeSubscription.add(createWorker.schedule(new Action0() {
                    public void call() {
                        try {
                            completable$CompletableSubscriber.onCompleted();
                        } finally {
                            createWorker.unsubscribe();
                        }
                    }
                }, Completable$16.this.val$delay, Completable$16.this.val$unit));
            }

            public void onError(final Throwable th) {
                if (Completable$16.this.val$delayError) {
                    compositeSubscription.add(createWorker.schedule(new Action0() {
                        public void call() {
                            try {
                                completable$CompletableSubscriber.onError(th);
                            } finally {
                                createWorker.unsubscribe();
                            }
                        }
                    }, Completable$16.this.val$delay, Completable$16.this.val$unit));
                } else {
                    completable$CompletableSubscriber.onError(th);
                }
            }

            public void onSubscribe(Subscription subscription) {
                compositeSubscription.add(subscription);
                completable$CompletableSubscriber.onSubscribe(compositeSubscription);
            }
        });
    }
}
