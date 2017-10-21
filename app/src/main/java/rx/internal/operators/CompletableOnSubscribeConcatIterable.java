package rx.internal.operators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable$CompletableOnSubscribe;
import rx.Completable$CompletableSubscriber;
import rx.Subscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

public final class CompletableOnSubscribeConcatIterable implements Completable$CompletableOnSubscribe {
    final Iterable<? extends Completable> sources;

    static final class ConcatInnerSubscriber extends AtomicInteger implements Completable$CompletableSubscriber {
        private static final long serialVersionUID = -7965400327305809232L;
        final Completable$CompletableSubscriber actual;
        int index;
        final SerialSubscription sd = new SerialSubscription();
        final Iterator<? extends Completable> sources;

        public ConcatInnerSubscriber(Completable$CompletableSubscriber completable$CompletableSubscriber, Iterator<? extends Completable> it) {
            this.actual = completable$CompletableSubscriber;
            this.sources = it;
        }

        final void next() {
            if (!this.sd.isUnsubscribed() && getAndIncrement() == 0) {
                Iterator it = this.sources;
                while (!this.sd.isUnsubscribed()) {
                    try {
                        if (it.hasNext()) {
                            try {
                                Completable completable = (Completable) it.next();
                                if (completable == null) {
                                    this.actual.onError(new NullPointerException("The completable returned is null"));
                                    return;
                                }
                                completable.subscribe(this);
                                if (decrementAndGet() == 0) {
                                    return;
                                }
                            } catch (Throwable th) {
                                this.actual.onError(th);
                                return;
                            }
                        }
                        this.actual.onCompleted();
                        return;
                    } catch (Throwable th2) {
                        this.actual.onError(th2);
                        return;
                    }
                }
            }
        }

        public final void onCompleted() {
            next();
        }

        public final void onError(Throwable th) {
            this.actual.onError(th);
        }

        public final void onSubscribe(Subscription subscription) {
            this.sd.set(subscription);
        }
    }

    public CompletableOnSubscribeConcatIterable(Iterable<? extends Completable> iterable) {
        this.sources = iterable;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        try {
            Iterator it = this.sources.iterator();
            if (it == null) {
                completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
                completable$CompletableSubscriber.onError(new NullPointerException("The iterator returned is null"));
                return;
            }
            ConcatInnerSubscriber concatInnerSubscriber = new ConcatInnerSubscriber(completable$CompletableSubscriber, it);
            completable$CompletableSubscriber.onSubscribe(concatInnerSubscriber.sd);
            concatInnerSubscriber.next();
        } catch (Throwable th) {
            completable$CompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
            completable$CompletableSubscriber.onError(th);
        }
    }
}
