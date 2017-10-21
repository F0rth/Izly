package rx;

import java.util.concurrent.TimeUnit;
import rx.functions.Action0;
import rx.subscriptions.MultipleAssignmentSubscription;

public abstract class Scheduler {
    static final long CLOCK_DRIFT_TOLERANCE_NANOS = TimeUnit.MINUTES.toNanos(Long.getLong("rx.scheduler.drift-tolerance", 15).longValue());

    public static abstract class Worker implements Subscription {
        public long now() {
            return System.currentTimeMillis();
        }

        public abstract Subscription schedule(Action0 action0);

        public abstract Subscription schedule(Action0 action0, long j, TimeUnit timeUnit);

        public Subscription schedulePeriodically(Action0 action0, long j, long j2, TimeUnit timeUnit) {
            final long toNanos = timeUnit.toNanos(j2);
            final long toNanos2 = TimeUnit.MILLISECONDS.toNanos(now());
            long toNanos3 = timeUnit.toNanos(j);
            final MultipleAssignmentSubscription multipleAssignmentSubscription = new MultipleAssignmentSubscription();
            toNanos3 += toNanos2;
            final Action0 action02 = action0;
            Action0 anonymousClass1 = new Action0() {
                long count;
                long lastNowNanos = toNanos2;
                long startInNanos = toNanos3;

                public void call() {
                    if (!multipleAssignmentSubscription.isUnsubscribed()) {
                        long j;
                        action02.call();
                        long toNanos = TimeUnit.MILLISECONDS.toNanos(Worker.this.now());
                        long j2;
                        if (Scheduler.CLOCK_DRIFT_TOLERANCE_NANOS + toNanos < this.lastNowNanos || toNanos >= (this.lastNowNanos + toNanos) + Scheduler.CLOCK_DRIFT_TOLERANCE_NANOS) {
                            j = toNanos + toNanos;
                            j2 = toNanos;
                            long j3 = this.count + 1;
                            this.count = j3;
                            this.startInNanos = j - (j2 * j3);
                        } else {
                            j = this.startInNanos;
                            j2 = this.count + 1;
                            this.count = j2;
                            j += j2 * toNanos;
                        }
                        this.lastNowNanos = toNanos;
                        multipleAssignmentSubscription.set(Worker.this.schedule(this, j - toNanos, TimeUnit.NANOSECONDS));
                    }
                }
            };
            MultipleAssignmentSubscription multipleAssignmentSubscription2 = new MultipleAssignmentSubscription();
            multipleAssignmentSubscription.set(multipleAssignmentSubscription2);
            multipleAssignmentSubscription2.set(schedule(anonymousClass1, j, timeUnit));
            return multipleAssignmentSubscription;
        }
    }

    public abstract Worker createWorker();

    public long now() {
        return System.currentTimeMillis();
    }
}
