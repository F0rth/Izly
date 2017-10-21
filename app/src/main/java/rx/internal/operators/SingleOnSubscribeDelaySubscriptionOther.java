package rx.internal.operators;

import rx.Observable;
import rx.Single;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.SerialSubscription;

public final class SingleOnSubscribeDelaySubscriptionOther<T> implements OnSubscribe<T> {
    final Single<? extends T> main;
    final Observable<?> other;

    public SingleOnSubscribeDelaySubscriptionOther(Single<? extends T> single, Observable<?> observable) {
        this.main = single;
        this.other = observable;
    }

    public final void call(final SingleSubscriber<? super T> singleSubscriber) {
        final SingleSubscriber anonymousClass1 = new SingleSubscriber<T>() {
            public void onError(Throwable th) {
                singleSubscriber.onError(th);
            }

            public void onSuccess(T t) {
                singleSubscriber.onSuccess(t);
            }
        };
        final SerialSubscription serialSubscription = new SerialSubscription();
        singleSubscriber.add(serialSubscription);
        AnonymousClass2 anonymousClass2 = new Subscriber<Object>() {
            boolean done;

            public void onCompleted() {
                if (!this.done) {
                    this.done = true;
                    serialSubscription.set(anonymousClass1);
                    SingleOnSubscribeDelaySubscriptionOther.this.main.subscribe(anonymousClass1);
                }
            }

            public void onError(Throwable th) {
                if (this.done) {
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                    return;
                }
                this.done = true;
                anonymousClass1.onError(th);
            }

            public void onNext(Object obj) {
                onCompleted();
            }
        };
        serialSubscription.set(anonymousClass2);
        this.other.subscribe(anonymousClass2);
    }
}
