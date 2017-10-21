package rx;

import java.util.Arrays;
import rx.exceptions.CompositeException;
import rx.functions.Func1;
import rx.subscriptions.SerialSubscription;

class Completable$24 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Func1 val$errorMapper;

    Completable$24(Completable completable, Func1 func1) {
        this.this$0 = completable;
        this.val$errorMapper = func1;
    }

    public void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        final SerialSubscription serialSubscription = new SerialSubscription();
        this.this$0.subscribe(new Completable$CompletableSubscriber() {
            public void onCompleted() {
                completable$CompletableSubscriber.onCompleted();
            }

            public void onError(Throwable th) {
                try {
                    Completable completable = (Completable) Completable$24.this.val$errorMapper.call(th);
                    if (completable == null) {
                        completable$CompletableSubscriber.onError(new CompositeException(Arrays.asList(new Throwable[]{th, new NullPointerException("The completable returned is null")})));
                        return;
                    }
                    completable.subscribe(new Completable$CompletableSubscriber() {
                        public void onCompleted() {
                            completable$CompletableSubscriber.onCompleted();
                        }

                        public void onError(Throwable th) {
                            completable$CompletableSubscriber.onError(th);
                        }

                        public void onSubscribe(Subscription subscription) {
                            serialSubscription.set(subscription);
                        }
                    });
                } catch (Throwable th2) {
                    completable$CompletableSubscriber.onError(new CompositeException(Arrays.asList(new Throwable[]{th, th2})));
                }
            }

            public void onSubscribe(Subscription subscription) {
                serialSubscription.set(subscription);
            }
        });
    }
}
