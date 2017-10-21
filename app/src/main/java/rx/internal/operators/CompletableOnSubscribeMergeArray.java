package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable$CompletableOnSubscribe;
import rx.Completable$CompletableSubscriber;
import rx.Subscription;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMergeArray implements Completable$CompletableOnSubscribe {
    final Completable[] sources;

    public CompletableOnSubscribeMergeArray(Completable[] completableArr) {
        this.sources = completableArr;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        final AtomicInteger atomicInteger = new AtomicInteger(this.sources.length + 1);
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        completable$CompletableSubscriber.onSubscribe(compositeSubscription);
        Completable[] completableArr = this.sources;
        int length = completableArr.length;
        int i = 0;
        while (i < length) {
            Completable completable = completableArr[i];
            if (!compositeSubscription.isUnsubscribed()) {
                if (completable == null) {
                    compositeSubscription.unsubscribe();
                    Throwable nullPointerException = new NullPointerException("A completable source is null");
                    if (atomicBoolean.compareAndSet(false, true)) {
                        completable$CompletableSubscriber.onError(nullPointerException);
                        return;
                    }
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(nullPointerException);
                }
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
                i++;
            } else {
                return;
            }
        }
        if (atomicInteger.decrementAndGet() == 0 && atomicBoolean.compareAndSet(false, true)) {
            completable$CompletableSubscriber.onCompleted();
        }
    }
}
