package rx.internal.operators;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import rx.Observable$Operator;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Timestamped;

public class OperatorSkipLastTimed<T> implements Observable$Operator<T, T> {
    final Scheduler scheduler;
    final long timeInMillis;

    public OperatorSkipLastTimed(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.timeInMillis = timeUnit.toMillis(j);
        this.scheduler = scheduler;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) {
            private Deque<Timestamped<T>> buffer = new ArrayDeque();

            private void emitItemsOutOfWindow(long j) {
                long j2 = OperatorSkipLastTimed.this.timeInMillis;
                while (!this.buffer.isEmpty()) {
                    Timestamped timestamped = (Timestamped) this.buffer.getFirst();
                    if (timestamped.getTimestampMillis() < j - j2) {
                        this.buffer.removeFirst();
                        subscriber.onNext(timestamped.getValue());
                    } else {
                        return;
                    }
                }
            }

            public void onCompleted() {
                emitItemsOutOfWindow(OperatorSkipLastTimed.this.scheduler.now());
                subscriber.onCompleted();
            }

            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            public void onNext(T t) {
                long now = OperatorSkipLastTimed.this.scheduler.now();
                emitItemsOutOfWindow(now);
                this.buffer.offerLast(new Timestamped(now, t));
            }
        };
    }
}
