package rx;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import rx.annotations.Experimental;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.internal.operators.CompletableOnSubscribeConcat;
import rx.internal.operators.CompletableOnSubscribeConcatArray;
import rx.internal.operators.CompletableOnSubscribeConcatIterable;
import rx.internal.operators.CompletableOnSubscribeMerge;
import rx.internal.operators.CompletableOnSubscribeMergeArray;
import rx.internal.operators.CompletableOnSubscribeMergeDelayErrorArray;
import rx.internal.operators.CompletableOnSubscribeMergeDelayErrorIterable;
import rx.internal.operators.CompletableOnSubscribeMergeIterable;
import rx.internal.operators.CompletableOnSubscribeTimeout;
import rx.internal.util.UtilityFunctions;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.Schedulers;
import rx.subscriptions.MultipleAssignmentSubscription;

@Experimental
public class Completable {
    static final Completable COMPLETE = create(new 1());
    static final RxJavaErrorHandler ERROR_HANDLER = RxJavaPlugins.getInstance().getErrorHandler();
    static final Completable NEVER = create(new 2());
    private final CompletableOnSubscribe onSubscribe;

    protected Completable(CompletableOnSubscribe completableOnSubscribe) {
        this.onSubscribe = completableOnSubscribe;
    }

    public static Completable amb(Iterable<? extends Completable> iterable) {
        requireNonNull(iterable);
        return create(new 4(iterable));
    }

    public static Completable amb(Completable... completableArr) {
        requireNonNull(completableArr);
        return completableArr.length == 0 ? complete() : completableArr.length == 1 ? completableArr[0] : create(new 3(completableArr));
    }

    public static Completable complete() {
        return COMPLETE;
    }

    public static Completable concat(Iterable<? extends Completable> iterable) {
        requireNonNull(iterable);
        return create(new CompletableOnSubscribeConcatIterable(iterable));
    }

    public static Completable concat(Observable<? extends Completable> observable) {
        return concat(observable, 2);
    }

    public static Completable concat(Observable<? extends Completable> observable, int i) {
        requireNonNull(observable);
        if (i > 0) {
            return create(new CompletableOnSubscribeConcat(observable, i));
        }
        throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
    }

    public static Completable concat(Completable... completableArr) {
        requireNonNull(completableArr);
        return completableArr.length == 0 ? complete() : completableArr.length == 1 ? completableArr[0] : create(new CompletableOnSubscribeConcatArray(completableArr));
    }

    public static Completable create(CompletableOnSubscribe completableOnSubscribe) {
        NullPointerException e;
        requireNonNull(completableOnSubscribe);
        try {
            return new Completable(completableOnSubscribe);
        } catch (NullPointerException e2) {
            throw e2;
        } catch (Throwable th) {
            ERROR_HANDLER.handleError(th);
            e2 = toNpe(th);
        }
    }

    public static Completable defer(Func0<? extends Completable> func0) {
        requireNonNull(func0);
        return create(new 5(func0));
    }

    private static void deliverUncaughtException(Throwable th) {
        Thread currentThread = Thread.currentThread();
        currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, th);
    }

    public static Completable error(Throwable th) {
        requireNonNull(th);
        return create(new 7(th));
    }

    public static Completable error(Func0<? extends Throwable> func0) {
        requireNonNull(func0);
        return create(new 6(func0));
    }

    public static Completable fromAction(Action0 action0) {
        requireNonNull(action0);
        return create(new 8(action0));
    }

    public static Completable fromCallable(Callable<?> callable) {
        requireNonNull(callable);
        return create(new 9(callable));
    }

    public static Completable fromFuture(Future<?> future) {
        requireNonNull(future);
        return fromObservable(Observable.from((Future) future));
    }

    public static Completable fromObservable(Observable<?> observable) {
        requireNonNull(observable);
        return create(new 10(observable));
    }

    public static Completable fromSingle(Single<?> single) {
        requireNonNull(single);
        return create(new 11(single));
    }

    public static Completable merge(Iterable<? extends Completable> iterable) {
        requireNonNull(iterable);
        return create(new CompletableOnSubscribeMergeIterable(iterable));
    }

    public static Completable merge(Observable<? extends Completable> observable) {
        return merge0(observable, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, false);
    }

    public static Completable merge(Observable<? extends Completable> observable, int i) {
        return merge0(observable, i, false);
    }

    public static Completable merge(Completable... completableArr) {
        requireNonNull(completableArr);
        return completableArr.length == 0 ? complete() : completableArr.length == 1 ? completableArr[0] : create(new CompletableOnSubscribeMergeArray(completableArr));
    }

    protected static Completable merge0(Observable<? extends Completable> observable, int i, boolean z) {
        requireNonNull(observable);
        if (i > 0) {
            return create(new CompletableOnSubscribeMerge(observable, i, z));
        }
        throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + i);
    }

    public static Completable mergeDelayError(Iterable<? extends Completable> iterable) {
        requireNonNull(iterable);
        return create(new CompletableOnSubscribeMergeDelayErrorIterable(iterable));
    }

    public static Completable mergeDelayError(Observable<? extends Completable> observable) {
        return merge0(observable, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, true);
    }

    public static Completable mergeDelayError(Observable<? extends Completable> observable, int i) {
        return merge0(observable, i, true);
    }

    public static Completable mergeDelayError(Completable... completableArr) {
        requireNonNull(completableArr);
        return create(new CompletableOnSubscribeMergeDelayErrorArray(completableArr));
    }

    public static Completable never() {
        return NEVER;
    }

    static <T> T requireNonNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static Completable timer(long j, TimeUnit timeUnit) {
        return timer(j, timeUnit, Schedulers.computation());
    }

    public static Completable timer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        requireNonNull(timeUnit);
        requireNonNull(scheduler);
        return create(new 12(scheduler, j, timeUnit));
    }

    static NullPointerException toNpe(Throwable th) {
        NullPointerException nullPointerException = new NullPointerException("Actually not, but can't pass out an exception otherwise...");
        nullPointerException.initCause(th);
        return nullPointerException;
    }

    public static <R> Completable using(Func0<R> func0, Func1<? super R, ? extends Completable> func1, Action1<? super R> action1) {
        return using(func0, func1, action1, true);
    }

    public static <R> Completable using(Func0<R> func0, Func1<? super R, ? extends Completable> func1, Action1<? super R> action1, boolean z) {
        requireNonNull(func0);
        requireNonNull(func1);
        requireNonNull(action1);
        return create(new 13(func0, func1, action1, z));
    }

    public final Completable ambWith(Completable completable) {
        requireNonNull(completable);
        return amb(this, completable);
    }

    public final <T> Observable<T> andThen(Observable<T> observable) {
        requireNonNull(observable);
        return observable.delaySubscription(toObservable());
    }

    public final <T> Single<T> andThen(Single<T> single) {
        requireNonNull(single);
        return single.delaySubscription(toObservable());
    }

    public final void await() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Throwable[] thArr = new Throwable[1];
        subscribe(new 14(this, countDownLatch, thArr));
        if (countDownLatch.getCount() != 0) {
            try {
                countDownLatch.await();
                if (thArr[0] != null) {
                    Exceptions.propagate(thArr[0]);
                }
            } catch (Throwable e) {
                throw Exceptions.propagate(e);
            }
        } else if (thArr[0] != null) {
            Exceptions.propagate(thArr[0]);
        }
    }

    public final boolean await(long j, TimeUnit timeUnit) {
        boolean z = true;
        requireNonNull(timeUnit);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Throwable[] thArr = new Throwable[1];
        subscribe(new 15(this, countDownLatch, thArr));
        if (countDownLatch.getCount() != 0) {
            try {
                z = countDownLatch.await(j, timeUnit);
                if (z && thArr[0] != null) {
                    Exceptions.propagate(thArr[0]);
                }
            } catch (Throwable e) {
                throw Exceptions.propagate(e);
            }
        } else if (thArr[0] != null) {
            Exceptions.propagate(thArr[0]);
        }
        return z;
    }

    public final Completable compose(CompletableTransformer completableTransformer) {
        return (Completable) to(completableTransformer);
    }

    public final Completable concatWith(Completable completable) {
        requireNonNull(completable);
        return concat(this, completable);
    }

    public final Completable delay(long j, TimeUnit timeUnit) {
        return delay(j, timeUnit, Schedulers.computation(), false);
    }

    public final Completable delay(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return delay(j, timeUnit, scheduler, false);
    }

    public final Completable delay(long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        requireNonNull(timeUnit);
        requireNonNull(scheduler);
        return create(new 16(this, scheduler, j, timeUnit, z));
    }

    public final Completable doAfterTerminate(Action0 action0) {
        return doOnLifecycle(Actions.empty(), Actions.empty(), Actions.empty(), action0, Actions.empty());
    }

    @Deprecated
    public final Completable doOnComplete(Action0 action0) {
        return doOnCompleted(action0);
    }

    public final Completable doOnCompleted(Action0 action0) {
        return doOnLifecycle(Actions.empty(), Actions.empty(), action0, Actions.empty(), Actions.empty());
    }

    public final Completable doOnError(Action1<? super Throwable> action1) {
        return doOnLifecycle(Actions.empty(), action1, Actions.empty(), Actions.empty(), Actions.empty());
    }

    protected final Completable doOnLifecycle(Action1<? super Subscription> action1, Action1<? super Throwable> action12, Action0 action0, Action0 action02, Action0 action03) {
        requireNonNull(action1);
        requireNonNull(action12);
        requireNonNull(action0);
        requireNonNull(action02);
        requireNonNull(action03);
        return create(new 17(this, action0, action02, action12, action1, action03));
    }

    public final Completable doOnSubscribe(Action1<? super Subscription> action1) {
        return doOnLifecycle(action1, Actions.empty(), Actions.empty(), Actions.empty(), Actions.empty());
    }

    public final Completable doOnTerminate(Action0 action0) {
        return doOnLifecycle(Actions.empty(), new 18(this, action0), action0, Actions.empty(), Actions.empty());
    }

    public final Completable doOnUnsubscribe(Action0 action0) {
        return doOnLifecycle(Actions.empty(), Actions.empty(), Actions.empty(), Actions.empty(), action0);
    }

    public final Completable endWith(Completable completable) {
        return concatWith(completable);
    }

    public final <T> Observable<T> endWith(Observable<T> observable) {
        return observable.startWith(toObservable());
    }

    public final Throwable get() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Throwable[] thArr = new Throwable[1];
        subscribe(new 19(this, countDownLatch, thArr));
        if (countDownLatch.getCount() == 0) {
            return thArr[0];
        }
        try {
            countDownLatch.await();
            return thArr[0];
        } catch (Throwable e) {
            throw Exceptions.propagate(e);
        }
    }

    public final Throwable get(long j, TimeUnit timeUnit) {
        requireNonNull(timeUnit);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Throwable[] thArr = new Throwable[1];
        subscribe(new 20(this, countDownLatch, thArr));
        if (countDownLatch.getCount() == 0) {
            return thArr[0];
        }
        try {
            if (countDownLatch.await(j, timeUnit)) {
                return thArr[0];
            }
            Exceptions.propagate(new TimeoutException());
            return null;
        } catch (Throwable e) {
            throw Exceptions.propagate(e);
        }
    }

    public final Completable lift(CompletableOperator completableOperator) {
        requireNonNull(completableOperator);
        return create(new 21(this, completableOperator));
    }

    public final Completable mergeWith(Completable completable) {
        requireNonNull(completable);
        return merge(this, completable);
    }

    public final Completable observeOn(Scheduler scheduler) {
        requireNonNull(scheduler);
        return create(new 22(this, scheduler));
    }

    public final Completable onErrorComplete() {
        return onErrorComplete(UtilityFunctions.alwaysTrue());
    }

    public final Completable onErrorComplete(Func1<? super Throwable, Boolean> func1) {
        requireNonNull(func1);
        return create(new 23(this, func1));
    }

    public final Completable onErrorResumeNext(Func1<? super Throwable, ? extends Completable> func1) {
        requireNonNull(func1);
        return create(new 24(this, func1));
    }

    public final Completable repeat() {
        return fromObservable(toObservable().repeat());
    }

    public final Completable repeat(long j) {
        return fromObservable(toObservable().repeat(j));
    }

    public final Completable repeatWhen(Func1<? super Observable<? extends Void>, ? extends Observable<?>> func1) {
        requireNonNull(func1);
        return fromObservable(toObservable().repeatWhen(func1));
    }

    public final Completable retry() {
        return fromObservable(toObservable().retry());
    }

    public final Completable retry(long j) {
        return fromObservable(toObservable().retry(j));
    }

    public final Completable retry(Func2<Integer, Throwable, Boolean> func2) {
        return fromObservable(toObservable().retry((Func2) func2));
    }

    public final Completable retryWhen(Func1<? super Observable<? extends Throwable>, ? extends Observable<?>> func1) {
        return fromObservable(toObservable().retryWhen(func1));
    }

    public final Completable startWith(Completable completable) {
        requireNonNull(completable);
        return concat(completable, this);
    }

    public final <T> Observable<T> startWith(Observable<T> observable) {
        requireNonNull(observable);
        return toObservable().startWith((Observable) observable);
    }

    public final Subscription subscribe() {
        MultipleAssignmentSubscription multipleAssignmentSubscription = new MultipleAssignmentSubscription();
        subscribe(new 25(this, multipleAssignmentSubscription));
        return multipleAssignmentSubscription;
    }

    public final Subscription subscribe(Action0 action0) {
        requireNonNull(action0);
        MultipleAssignmentSubscription multipleAssignmentSubscription = new MultipleAssignmentSubscription();
        subscribe(new 26(this, action0, multipleAssignmentSubscription));
        return multipleAssignmentSubscription;
    }

    public final Subscription subscribe(Action1<? super Throwable> action1, Action0 action0) {
        requireNonNull(action1);
        requireNonNull(action0);
        MultipleAssignmentSubscription multipleAssignmentSubscription = new MultipleAssignmentSubscription();
        subscribe(new 27(this, action0, multipleAssignmentSubscription, action1));
        return multipleAssignmentSubscription;
    }

    public final void subscribe(CompletableSubscriber completableSubscriber) {
        NullPointerException e;
        requireNonNull(completableSubscriber);
        try {
            this.onSubscribe.call(completableSubscriber);
        } catch (NullPointerException e2) {
            throw e2;
        } catch (Throwable th) {
            ERROR_HANDLER.handleError(th);
            Exceptions.throwIfFatal(th);
            e2 = toNpe(th);
        }
    }

    public final <T> void subscribe(Subscriber<T> subscriber) {
        NullPointerException e;
        requireNonNull(subscriber);
        if (subscriber == null) {
            try {
                throw new NullPointerException("The RxJavaPlugins.onSubscribe returned a null Subscriber");
            } catch (NullPointerException e2) {
                throw e2;
            } catch (Throwable th) {
                ERROR_HANDLER.handleError(th);
                e2 = toNpe(th);
            }
        } else {
            subscribe(new 28(this, subscriber));
        }
    }

    public final Completable subscribeOn(Scheduler scheduler) {
        requireNonNull(scheduler);
        return create(new 29(this, scheduler));
    }

    public final Completable timeout(long j, TimeUnit timeUnit) {
        return timeout0(j, timeUnit, Schedulers.computation(), null);
    }

    public final Completable timeout(long j, TimeUnit timeUnit, Completable completable) {
        requireNonNull(completable);
        return timeout0(j, timeUnit, Schedulers.computation(), completable);
    }

    public final Completable timeout(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return timeout0(j, timeUnit, scheduler, null);
    }

    public final Completable timeout(long j, TimeUnit timeUnit, Scheduler scheduler, Completable completable) {
        requireNonNull(completable);
        return timeout0(j, timeUnit, scheduler, completable);
    }

    public final Completable timeout0(long j, TimeUnit timeUnit, Scheduler scheduler, Completable completable) {
        requireNonNull(timeUnit);
        requireNonNull(scheduler);
        return create(new CompletableOnSubscribeTimeout(this, j, timeUnit, scheduler, completable));
    }

    public final <U> U to(Func1<? super Completable, U> func1) {
        return func1.call(this);
    }

    public final <T> Observable<T> toObservable() {
        return Observable.create(new 30(this));
    }

    public final <T> Single<T> toSingle(Func0<? extends T> func0) {
        requireNonNull(func0);
        return Single.create(new 31(this, func0));
    }

    public final <T> Single<T> toSingleDefault(T t) {
        requireNonNull(t);
        return toSingle(new 32(this, t));
    }

    public final Completable unsubscribeOn(Scheduler scheduler) {
        requireNonNull(scheduler);
        return create(new 33(this, scheduler));
    }
}
