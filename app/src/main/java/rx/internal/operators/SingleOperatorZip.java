package rx.internal.operators;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Single;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.exceptions.Exceptions;
import rx.functions.FuncN;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public class SingleOperatorZip {
    public static <T, R> Single<R> zip(final Single<? extends T>[] singleArr, final FuncN<? extends R> funcN) {
        return Single.create(new OnSubscribe<R>() {
            public final void call(SingleSubscriber<? super R> singleSubscriber) {
                if (singleArr.length == 0) {
                    singleSubscriber.onError(new NoSuchElementException("Can't zip 0 Singles."));
                    return;
                }
                final AtomicInteger atomicInteger = new AtomicInteger(singleArr.length);
                final AtomicBoolean atomicBoolean = new AtomicBoolean();
                final Object[] objArr = new Object[singleArr.length];
                CompositeSubscription compositeSubscription = new CompositeSubscription();
                singleSubscriber.add(compositeSubscription);
                int i = 0;
                while (i < singleArr.length && !compositeSubscription.isUnsubscribed() && !atomicBoolean.get()) {
                    final SingleSubscriber<? super R> singleSubscriber2 = singleSubscriber;
                    SingleSubscriber anonymousClass1 = new SingleSubscriber<T>() {
                        public void onError(Throwable th) {
                            if (atomicBoolean.compareAndSet(false, true)) {
                                singleSubscriber2.onError(th);
                            } else {
                                RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                            }
                        }

                        public void onSuccess(T t) {
                            objArr[i] = t;
                            if (atomicInteger.decrementAndGet() == 0) {
                                try {
                                    singleSubscriber2.onSuccess(funcN.call(objArr));
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    onError(th);
                                }
                            }
                        }
                    };
                    compositeSubscription.add(anonymousClass1);
                    if (!compositeSubscription.isUnsubscribed() && !atomicBoolean.get()) {
                        singleArr[i].subscribe(anonymousClass1);
                        i++;
                    } else {
                        return;
                    }
                }
            }
        });
    }
}
