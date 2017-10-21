package rx;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.subscriptions.CompositeSubscription;

final class Completable$4 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Iterable val$sources;

    Completable$4(Iterable iterable) {
        this.val$sources = iterable;
    }

    public final void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        Throwable nullPointerException;
        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        completable$CompletableSubscriber.onSubscribe(compositeSubscription);
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        Completable$CompletableSubscriber anonymousClass1 = new Completable$CompletableSubscriber() {
            public void onCompleted() {
                if (atomicBoolean.compareAndSet(false, true)) {
                    compositeSubscription.unsubscribe();
                    completable$CompletableSubscriber.onCompleted();
                }
            }

            public void onError(Throwable th) {
                if (atomicBoolean.compareAndSet(false, true)) {
                    compositeSubscription.unsubscribe();
                    completable$CompletableSubscriber.onError(th);
                    return;
                }
                Completable.ERROR_HANDLER.handleError(th);
            }

            public void onSubscribe(Subscription subscription) {
                compositeSubscription.add(subscription);
            }
        };
        try {
            Iterator it = this.val$sources.iterator();
            if (it == null) {
                completable$CompletableSubscriber.onError(new NullPointerException("The iterator returned is null"));
                return;
            }
            boolean z = true;
            while (!atomicBoolean.get() && !compositeSubscription.isUnsubscribed()) {
                try {
                    if (it.hasNext()) {
                        if (!atomicBoolean.get() && !compositeSubscription.isUnsubscribed()) {
                            try {
                                Completable completable = (Completable) it.next();
                                if (completable == null) {
                                    nullPointerException = new NullPointerException("One of the sources is null");
                                    if (atomicBoolean.compareAndSet(false, true)) {
                                        compositeSubscription.unsubscribe();
                                        completable$CompletableSubscriber.onError(nullPointerException);
                                        return;
                                    }
                                    Completable.ERROR_HANDLER.handleError(nullPointerException);
                                    return;
                                } else if (!atomicBoolean.get() && !compositeSubscription.isUnsubscribed()) {
                                    completable.subscribe(anonymousClass1);
                                    z = false;
                                } else {
                                    return;
                                }
                            } catch (Throwable nullPointerException2) {
                                if (atomicBoolean.compareAndSet(false, true)) {
                                    compositeSubscription.unsubscribe();
                                    completable$CompletableSubscriber.onError(nullPointerException2);
                                    return;
                                }
                                Completable.ERROR_HANDLER.handleError(nullPointerException2);
                                return;
                            }
                        }
                        return;
                    } else if (z) {
                        completable$CompletableSubscriber.onCompleted();
                        return;
                    } else {
                        return;
                    }
                } catch (Throwable nullPointerException22) {
                    if (atomicBoolean.compareAndSet(false, true)) {
                        compositeSubscription.unsubscribe();
                        completable$CompletableSubscriber.onError(nullPointerException22);
                        return;
                    }
                    Completable.ERROR_HANDLER.handleError(nullPointerException22);
                    return;
                }
            }
        } catch (Throwable nullPointerException222) {
            completable$CompletableSubscriber.onError(nullPointerException222);
        }
    }
}
