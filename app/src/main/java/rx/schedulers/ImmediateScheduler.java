package rx.schedulers;

import rx.Scheduler;
import rx.Scheduler.Worker;

@Deprecated
public final class ImmediateScheduler extends Scheduler {
    private ImmediateScheduler() {
        throw new AssertionError();
    }

    public final Worker createWorker() {
        return null;
    }
}
