package rx.schedulers;

import rx.Scheduler;
import rx.Scheduler.Worker;

@Deprecated
public final class NewThreadScheduler extends Scheduler {
    private NewThreadScheduler() {
        throw new AssertionError();
    }

    public final Worker createWorker() {
        return null;
    }
}
