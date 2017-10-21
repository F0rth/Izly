package rx;

import java.util.concurrent.atomic.AtomicBoolean;
import rx.subscriptions.CompositeSubscription;

final class Completable$3 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Completable[] val$sources;

    Completable$3(Completable[] completableArr) {
        this.val$sources = completableArr;
    }

    public final void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
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
        Completable[] completableArr = this.val$sources;
        int length = completableArr.length;
        int i = 0;
        while (i < length) {
            Completable completable = completableArr[i];
            if (!compositeSubscription.isUnsubscribed()) {
                if (completable == null) {
                    Throwable nullPointerException = new NullPointerException("One of the sources is null");
                    if (atomicBoolean.compareAndSet(false, true)) {
                        compositeSubscription.unsubscribe();
                        completable$CompletableSubscriber.onError(nullPointerException);
                        return;
                    }
                    Completable.ERROR_HANDLER.handleError(nullPointerException);
                    return;
                } else if (!atomicBoolean.get() && !compositeSubscription.isUnsubscribed()) {
                    completable.subscribe(anonymousClass1);
                    i++;
                } else {
                    return;
                }
            }
            return;
        }
    }
}
