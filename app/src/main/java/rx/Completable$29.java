package rx;

import rx.Scheduler.Worker;
import rx.functions.Action0;

class Completable$29 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Scheduler val$scheduler;

    Completable$29(Completable completable, Scheduler scheduler) {
        this.this$0 = completable;
        this.val$scheduler = scheduler;
    }

    public void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        final Worker createWorker = this.val$scheduler.createWorker();
        createWorker.schedule(new Action0() {
            public void call() {
                try {
                    Completable$29.this.this$0.subscribe(completable$CompletableSubscriber);
                } finally {
                    createWorker.unsubscribe();
                }
            }
        });
    }
}
