package rx.internal.operators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable$CompletableOnSubscribe;
import rx.Completable$CompletableSubscriber;
import rx.Subscription;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMergeIterable implements Completable$CompletableOnSubscribe {
    final Iterable<? extends Completable> sources;

    public CompletableOnSubscribeMergeIterable(Iterable<? extends Completable> iterable) {
        this.sources = iterable;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        completable$CompletableSubscriber.onSubscribe(compositeSubscription);
        Throwable nullPointerException;
        try {
            Iterator it = this.sources.iterator();
            if (it == null) {
                completable$CompletableSubscriber.onError(new NullPointerException("The source iterator returned is null"));
                return;
            }
            while (!compositeSubscription.isUnsubscribed()) {
                try {
                    if (it.hasNext()) {
                        if (!compositeSubscription.isUnsubscribed()) {
                            try {
                                Completable completable = (Completable) it.next();
                                if (!compositeSubscription.isUnsubscribed()) {
                                    if (completable == null) {
                                        compositeSubscription.unsubscribe();
                                        nullPointerException = new NullPointerException("A completable source is null");
                                        if (atomicBoolean.compareAndSet(false, true)) {
                                            completable$CompletableSubscriber.onError(nullPointerException);
                                            return;
                                        } else {
                                            RxJavaPlugins.getInstance().getErrorHandler().handleError(nullPointerException);
                                            return;
                                        }
                                    }
                                    atomicInteger.getAndIncrement();
                                    final Completable$CompletableSubscriber completable$CompletableSubscriber2 = completable$CompletableSubscriber;
                                    completable.subscribe(new Completable$CompletableSubscriber() {
                                        public void onCompleted() {
                                            if (atomicInteger.decrementAndGet() == 0 && atomicBoolean.compareAndSet(false, true)) {
                                                completable$CompletableSubscriber2.onCompleted();
                                            }
                                        }

                                        public void onError(Throwable th) {
                                            compositeSubscription.unsubscribe();
                                            if (atomicBoolean.compareAndSet(false, true)) {
                                                completable$CompletableSubscriber2.onError(th);
                                            } else {
                                                RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                                            }
                                        }

                                        public void onSubscribe(Subscription subscription) {
                                            compositeSubscription.add(subscription);
                                        }
                                    });
                                } else {
                                    return;
                                }
                            } catch (Throwable nullPointerException2) {
                                compositeSubscription.unsubscribe();
                                if (atomicBoolean.compareAndSet(false, true)) {
                                    completable$CompletableSubscriber.onError(nullPointerException2);
                                    return;
                                } else {
                                    RxJavaPlugins.getInstance().getErrorHandler().handleError(nullPointerException2);
                                    return;
                                }
                            }
                        }
                        return;
                    } else if (atomicInteger.decrementAndGet() == 0 && atomicBoolean.compareAndSet(false, true)) {
                        completable$CompletableSubscriber.onCompleted();
                        return;
                    } else {
                        return;
                    }
                } catch (Throwable nullPointerException22) {
                    compositeSubscription.unsubscribe();
                    if (atomicBoolean.compareAndSet(false, true)) {
                        completable$CompletableSubscriber.onError(nullPointerException22);
                        return;
                    } else {
                        RxJavaPlugins.getInstance().getErrorHandler().handleError(nullPointerException22);
                        return;
                    }
                }
            }
        } catch (Throwable nullPointerException222) {
            completable$CompletableSubscriber.onError(nullPointerException222);
        }
    }
}
