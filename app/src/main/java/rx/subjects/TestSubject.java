package rx.subjects;

import java.util.concurrent.TimeUnit;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.internal.operators.NotificationLite;
import rx.schedulers.TestScheduler;
import rx.subjects.SubjectSubscriptionManager.SubjectObserver;

public final class TestSubject<T> extends Subject<T, T> {
    private final Worker innerScheduler;
    private final SubjectSubscriptionManager<T> state;

    protected TestSubject(Observable$OnSubscribe<T> observable$OnSubscribe, SubjectSubscriptionManager<T> subjectSubscriptionManager, TestScheduler testScheduler) {
        super(observable$OnSubscribe);
        this.state = subjectSubscriptionManager;
        this.innerScheduler = testScheduler.createWorker();
    }

    public static <T> TestSubject<T> create(TestScheduler testScheduler) {
        final Object subjectSubscriptionManager = new SubjectSubscriptionManager();
        subjectSubscriptionManager.onAdded = new Action1<SubjectObserver<T>>() {
            public final void call(SubjectObserver<T> subjectObserver) {
                subjectObserver.emitFirst(subjectSubscriptionManager.getLatest(), subjectSubscriptionManager.nl);
            }
        };
        subjectSubscriptionManager.onTerminated = subjectSubscriptionManager.onAdded;
        return new TestSubject(subjectSubscriptionManager, subjectSubscriptionManager, testScheduler);
    }

    final void _onCompleted() {
        if (this.state.active) {
            for (SubjectObserver onCompleted : this.state.terminate(NotificationLite.instance().completed())) {
                onCompleted.onCompleted();
            }
        }
    }

    final void _onError(Throwable th) {
        if (this.state.active) {
            for (SubjectObserver onError : this.state.terminate(NotificationLite.instance().error(th))) {
                onError.onError(th);
            }
        }
    }

    final void _onNext(T t) {
        for (Observer onNext : this.state.observers()) {
            onNext.onNext(t);
        }
    }

    public final boolean hasObservers() {
        return this.state.observers().length > 0;
    }

    public final void onCompleted() {
        onCompleted(0);
    }

    public final void onCompleted(long j) {
        this.innerScheduler.schedule(new Action0() {
            public void call() {
                TestSubject.this._onCompleted();
            }
        }, j, TimeUnit.MILLISECONDS);
    }

    public final void onError(Throwable th) {
        onError(th, 0);
    }

    public final void onError(final Throwable th, long j) {
        this.innerScheduler.schedule(new Action0() {
            public void call() {
                TestSubject.this._onError(th);
            }
        }, j, TimeUnit.MILLISECONDS);
    }

    public final void onNext(T t) {
        onNext(t, 0);
    }

    public final void onNext(final T t, long j) {
        this.innerScheduler.schedule(new Action0() {
            public void call() {
                TestSubject.this._onNext(t);
            }
        }, j, TimeUnit.MILLISECONDS);
    }
}
