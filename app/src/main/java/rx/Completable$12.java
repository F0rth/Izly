package rx;

import java.util.concurrent.TimeUnit;
import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.subscriptions.MultipleAssignmentSubscription;

final class Completable$12 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ long val$delay;
    final /* synthetic */ Scheduler val$scheduler;
    final /* synthetic */ TimeUnit val$unit;

    Completable$12(Scheduler scheduler, long j, TimeUnit timeUnit) {
        this.val$scheduler = scheduler;
        this.val$delay = j;
        this.val$unit = timeUnit;
    }

    public final void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        MultipleAssignmentSubscription multipleAssignmentSubscription = new MultipleAssignmentSubscription();
        completable$CompletableSubscriber.onSubscribe(multipleAssignmentSubscription);
        if (!multipleAssignmentSubscription.isUnsubscribed()) {
            final Worker createWorker = this.val$scheduler.createWorker();
            multipleAssignmentSubscription.set(createWorker);
            createWorker.schedule(new Action0() {
                public void call() {
                    try {
                        completable$CompletableSubscriber.onCompleted();
                    } finally {
                        createWorker.unsubscribe();
                    }
                }
            }, this.val$delay, this.val$unit);
        }
    }
}
