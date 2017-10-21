package rx.internal.operators;

import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

public final class OperatorTimeout<T> extends OperatorTimeoutBase<T> {

    class AnonymousClass1 implements FirstTimeoutStub<T> {
        final /* synthetic */ TimeUnit val$timeUnit;
        final /* synthetic */ long val$timeout;

        AnonymousClass1(long j, TimeUnit timeUnit) {
            this.val$timeout = j;
            this.val$timeUnit = timeUnit;
        }

        public Subscription call(final TimeoutSubscriber<T> timeoutSubscriber, final Long l, Worker worker) {
            return worker.schedule(new Action0() {
                public void call() {
                    timeoutSubscriber.onTimeout(l.longValue());
                }
            }, this.val$timeout, this.val$timeUnit);
        }
    }

    class AnonymousClass2 implements TimeoutStub<T> {
        final /* synthetic */ TimeUnit val$timeUnit;
        final /* synthetic */ long val$timeout;

        AnonymousClass2(long j, TimeUnit timeUnit) {
            this.val$timeout = j;
            this.val$timeUnit = timeUnit;
        }

        public Subscription call(final TimeoutSubscriber<T> timeoutSubscriber, final Long l, T t, Worker worker) {
            return worker.schedule(new Action0() {
                public void call() {
                    timeoutSubscriber.onTimeout(l.longValue());
                }
            }, this.val$timeout, this.val$timeUnit);
        }
    }

    public OperatorTimeout(long j, TimeUnit timeUnit, Observable<? extends T> observable, Scheduler scheduler) {
        super(new AnonymousClass1(j, timeUnit), new AnonymousClass2(j, timeUnit), observable, scheduler);
    }

    public final /* bridge */ /* synthetic */ Subscriber call(Subscriber subscriber) {
        return super.call(subscriber);
    }
}
