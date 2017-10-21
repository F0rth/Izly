package rx.subjects;

import java.util.ArrayList;
import java.util.List;
import rx.Observable$OnSubscribe;
import rx.annotations.Beta;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.internal.operators.NotificationLite;
import rx.subjects.SubjectSubscriptionManager.SubjectObserver;

public final class PublishSubject<T> extends Subject<T, T> {
    private final NotificationLite<T> nl = NotificationLite.instance();
    final SubjectSubscriptionManager<T> state;

    protected PublishSubject(Observable$OnSubscribe<T> observable$OnSubscribe, SubjectSubscriptionManager<T> subjectSubscriptionManager) {
        super(observable$OnSubscribe);
        this.state = subjectSubscriptionManager;
    }

    public static <T> PublishSubject<T> create() {
        final Object subjectSubscriptionManager = new SubjectSubscriptionManager();
        subjectSubscriptionManager.onTerminated = new Action1<SubjectObserver<T>>() {
            public final void call(SubjectObserver<T> subjectObserver) {
                subjectObserver.emitFirst(subjectSubscriptionManager.getLatest(), subjectSubscriptionManager.nl);
            }
        };
        return new PublishSubject(subjectSubscriptionManager, subjectSubscriptionManager);
    }

    @Beta
    public final Throwable getThrowable() {
        Object latest = this.state.getLatest();
        return this.nl.isError(latest) ? this.nl.getError(latest) : null;
    }

    @Beta
    public final boolean hasCompleted() {
        Object latest = this.state.getLatest();
        return (latest == null || this.nl.isError(latest)) ? false : true;
    }

    public final boolean hasObservers() {
        return this.state.observers().length > 0;
    }

    @Beta
    public final boolean hasThrowable() {
        return this.nl.isError(this.state.getLatest());
    }

    public final void onCompleted() {
        if (this.state.active) {
            Object completed = this.nl.completed();
            for (SubjectObserver emitNext : this.state.terminate(completed)) {
                emitNext.emitNext(completed, this.state.nl);
            }
        }
    }

    public final void onError(Throwable th) {
        if (this.state.active) {
            Object error = this.nl.error(th);
            List list = null;
            for (SubjectObserver emitNext : this.state.terminate(error)) {
                try {
                    emitNext.emitNext(error, this.state.nl);
                } catch (Throwable th2) {
                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(th2);
                }
            }
            Exceptions.throwIfAny(list);
        }
    }

    public final void onNext(T t) {
        for (SubjectObserver onNext : this.state.observers()) {
            onNext.onNext(t);
        }
    }
}
