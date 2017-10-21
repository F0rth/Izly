package rx;

import java.util.Arrays;
import rx.exceptions.CompositeException;
import rx.functions.Func1;

class Completable$23 implements Completable$CompletableOnSubscribe {
    final /* synthetic */ Completable this$0;
    final /* synthetic */ Func1 val$predicate;

    Completable$23(Completable completable, Func1 func1) {
        this.this$0 = completable;
        this.val$predicate = func1;
    }

    public void call(final Completable$CompletableSubscriber completable$CompletableSubscriber) {
        this.this$0.subscribe(new Completable$CompletableSubscriber() {
            public void onCompleted() {
                completable$CompletableSubscriber.onCompleted();
            }

            public void onError(Throwable th) {
                try {
                    if (((Boolean) Completable$23.this.val$predicate.call(th)).booleanValue()) {
                        completable$CompletableSubscriber.onCompleted();
                    } else {
                        completable$CompletableSubscriber.onError(th);
                    }
                } catch (Throwable th2) {
                    CompositeException compositeException = new CompositeException(Arrays.asList(new Throwable[]{th, th2}));
                }
            }

            public void onSubscribe(Subscription subscription) {
                completable$CompletableSubscriber.onSubscribe(subscription);
            }
        });
    }
}
