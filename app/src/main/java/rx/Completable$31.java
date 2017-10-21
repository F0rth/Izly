package rx;

import rx.Single.OnSubscribe;
import rx.functions.Func0;

class Completable$31 implements OnSubscribe<T> {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Func0 val$completionValueFunc0;

    Completable$31(Completable completable, Func0 func0) {
        this.this$0 = completable;
        this.val$completionValueFunc0 = func0;
    }

    public void call(final SingleSubscriber<? super T> singleSubscriber) {
        this.this$0.subscribe(new Completable$CompletableSubscriber() {
            public void onCompleted() {
                try {
                    Object call = Completable$31.this.val$completionValueFunc0.call();
                    if (call == null) {
                        singleSubscriber.onError(new NullPointerException("The value supplied is null"));
                    } else {
                        singleSubscriber.onSuccess(call);
                    }
                } catch (Throwable th) {
                    singleSubscriber.onError(th);
                }
            }

            public void onError(Throwable th) {
                singleSubscriber.onError(th);
            }

            public void onSubscribe(Subscription subscription) {
                singleSubscriber.add(subscription);
            }
        });
    }
}
