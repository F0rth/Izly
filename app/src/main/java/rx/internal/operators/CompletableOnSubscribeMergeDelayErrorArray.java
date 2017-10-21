package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable$CompletableOnSubscribe;
import rx.Completable$CompletableSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMergeDelayErrorArray implements Completable$CompletableOnSubscribe {
    final Completable[] sources;

    public CompletableOnSubscribeMergeDelayErrorArray(Completable[] completableArr) {
        this.sources = completableArr;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        final AtomicInteger atomicInteger = new AtomicInteger(this.sources.length + 1);
        final Queue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        completable$CompletableSubscriber.onSubscribe(compositeSubscription);
        Completable[] completableArr = this.sources;
        int length = completableArr.length;
        int i = 0;
        while (i < length) {
            Completable completable = completableArr[i];
            if (!compositeSubscription.isUnsubscribed()) {
                if (completable == null) {
                    concurrentLinkedQueue.offer(new NullPointerException("A completable source is null"));
                    atomicInteger.decrementAndGet();
                } else {
                    final Completable$CompletableSubscriber completable$CompletableSubscriber2 = completable$CompletableSubscriber;
                    completable.subscribe(new Completable$CompletableSubscriber() {
                        public void onCompleted() {
                            tryTerminate();
                        }

                        public void onError(Throwable th) {
                            concurrentLinkedQueue.offer(th);
                            tryTerminate();
                        }

                        public void onSubscribe(Subscription subscription) {
                            compositeSubscription.add(subscription);
                        }

                        void tryTerminate() {
                            if (atomicInteger.decrementAndGet() != 0) {
                                return;
                            }
                            if (concurrentLinkedQueue.isEmpty()) {
                                completable$CompletableSubscriber2.onCompleted();
                            } else {
                                completable$CompletableSubscriber2.onError(CompletableOnSubscribeMerge.collectErrors(concurrentLinkedQueue));
                            }
                        }
                    });
                }
                i++;
            } else {
                return;
            }
        }
        if (atomicInteger.decrementAndGet() != 0) {
            return;
        }
        if (concurrentLinkedQueue.isEmpty()) {
            completable$CompletableSubscriber.onCompleted();
        } else {
            completable$CompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(concurrentLinkedQueue));
        }
    }
}
