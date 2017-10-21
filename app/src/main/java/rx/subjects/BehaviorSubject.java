package rx.subjects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import rx.Observable$OnSubscribe;
import rx.annotations.Beta;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.internal.operators.NotificationLite;
import rx.subjects.SubjectSubscriptionManager.SubjectObserver;

public final class BehaviorSubject<T> extends Subject<T, T> {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private final NotificationLite<T> nl = NotificationLite.instance();
    private final SubjectSubscriptionManager<T> state;

    protected BehaviorSubject(Observable$OnSubscribe<T> observable$OnSubscribe, SubjectSubscriptionManager<T> subjectSubscriptionManager) {
        super(observable$OnSubscribe);
        this.state = subjectSubscriptionManager;
    }

    public static <T> BehaviorSubject<T> create() {
        return create(null, false);
    }

    public static <T> BehaviorSubject<T> create(T t) {
        return create(t, true);
    }

    private static <T> BehaviorSubject<T> create(T t, boolean z) {
        final Object subjectSubscriptionManager = new SubjectSubscriptionManager();
        if (z) {
            subjectSubscriptionManager.setLatest(NotificationLite.instance().next(t));
        }
        subjectSubscriptionManager.onAdded = new Action1<SubjectObserver<T>>() {
            public final void call(SubjectObserver<T> subjectObserver) {
                subjectObserver.emitFirst(subjectSubscriptionManager.getLatest(), subjectSubscriptionManager.nl);
            }
        };
        subjectSubscriptionManager.onTerminated = subjectSubscriptionManager.onAdded;
        return new BehaviorSubject(subjectSubscriptionManager, subjectSubscriptionManager);
    }

    @Beta
    public final Throwable getThrowable() {
        Object latest = this.state.getLatest();
        return this.nl.isError(latest) ? this.nl.getError(latest) : null;
    }

    @Beta
    public final T getValue() {
        Object latest = this.state.getLatest();
        return this.nl.isNext(latest) ? this.nl.getValue(latest) : null;
    }

    @Beta
    public final Object[] getValues() {
        Object[] values = getValues(EMPTY_ARRAY);
        return values == EMPTY_ARRAY ? new Object[0] : values;
    }

    @Beta
    public final T[] getValues(T[] tArr) {
        Object latest = this.state.getLatest();
        if (this.nl.isNext(latest)) {
            T[] tArr2 = tArr.length == 0 ? (Object[]) Array.newInstance(tArr.getClass().getComponentType(), 1) : tArr;
            tArr2[0] = this.nl.getValue(latest);
            if (tArr2.length <= 1) {
                return tArr2;
            }
            tArr2[1] = null;
            return tArr2;
        }
        if (tArr.length > 0) {
            tArr[0] = null;
        }
        return tArr;
    }

    @Beta
    public final boolean hasCompleted() {
        return this.nl.isCompleted(this.state.getLatest());
    }

    public final boolean hasObservers() {
        return this.state.observers().length > 0;
    }

    @Beta
    public final boolean hasThrowable() {
        return this.nl.isError(this.state.getLatest());
    }

    @Beta
    public final boolean hasValue() {
        return this.nl.isNext(this.state.getLatest());
    }

    public final void onCompleted() {
        if (this.state.getLatest() == null || this.state.active) {
            Object completed = this.nl.completed();
            for (SubjectObserver emitNext : this.state.terminate(completed)) {
                emitNext.emitNext(completed, this.state.nl);
            }
        }
    }

    public final void onError(Throwable th) {
        if (this.state.getLatest() == null || this.state.active) {
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
        if (this.state.getLatest() == null || this.state.active) {
            Object next = this.nl.next(t);
            for (SubjectObserver emitNext : this.state.next(next)) {
                emitNext.emitNext(next, this.state.nl);
            }
        }
    }

    final int subscriberCount() {
        return this.state.observers().length;
    }
}
