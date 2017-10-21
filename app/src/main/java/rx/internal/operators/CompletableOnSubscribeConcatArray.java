package rx.internal.operators;

import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable$CompletableOnSubscribe;
import rx.Completable$CompletableSubscriber;
import rx.Subscription;
import rx.subscriptions.SerialSubscription;

public final class CompletableOnSubscribeConcatArray implements Completable$CompletableOnSubscribe {
    final Completable[] sources;

    static final class ConcatInnerSubscriber extends AtomicInteger implements Completable$CompletableSubscriber {
        private static final long serialVersionUID = -7965400327305809232L;
        final Completable$CompletableSubscriber actual;
        int index;
        final SerialSubscription sd = new SerialSubscription();
        final Completable[] sources;

        public ConcatInnerSubscriber(Completable$CompletableSubscriber completable$CompletableSubscriber, Completable[] completableArr) {
            this.actual = completable$CompletableSubscriber;
            this.sources = completableArr;
        }

        final void next() {
            if (!this.sd.isUnsubscribed() && getAndIncrement() == 0) {
                Completable[] completableArr = this.sources;
                while (!this.sd.isUnsubscribed()) {
                    int i = this.index;
                    this.index = i + 1;
                    if (i == completableArr.length) {
                        this.actual.onCompleted();
                        return;
                    }
                    completableArr[i].subscribe(this);
                    if (decrementAndGet() == 0) {
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

    public CompletableOnSubscribeConcatArray(Completable[] completableArr) {
        this.sources = completableArr;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        ConcatInnerSubscriber concatInnerSubscriber = new ConcatInnerSubscriber(completable$CompletableSubscriber, this.sources);
        completable$CompletableSubscriber.onSubscribe(concatInnerSubscriber.sd);
        concatInnerSubscriber.next();
    }
}
