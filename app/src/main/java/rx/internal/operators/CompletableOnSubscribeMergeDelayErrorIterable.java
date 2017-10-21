package rx.internal.operators;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable$CompletableOnSubscribe;
import rx.Completable$CompletableSubscriber;
import rx.Subscription;
import rx.internal.util.unsafe.MpscLinkedQueue;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMergeDelayErrorIterable implements Completable$CompletableOnSubscribe {
    final Iterable<? extends Completable> sources;

    public CompletableOnSubscribeMergeDelayErrorIterable(Iterable<? extends Completable> iterable) {
        this.sources = iterable;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        final CompositeSubscription compositeSubscription = new CompositeSubscription();
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        final Queue mpscLinkedQueue = new MpscLinkedQueue();
        completable$CompletableSubscriber.onSubscribe(compositeSubscription);
        try {
            Iterator it = this.sources.iterator();
            if (it == null) {
                completable$CompletableSubscriber.onError(new NullPointerException("The source iterator returned is null"));
                return;
            }
            while (!compositeSubscription.isUnsubscribed()) {
                try {
                    if (it.hasNext()) {
                        if (!compositeSubscription.isUnsubscribed()) {
                            try {
                                Completable completable = (Completable) it.next();
                                if (!compositeSubscription.isUnsubscribed()) {
                                    if (completable == null) {
                                        mpscLinkedQueue.offer(new NullPointerException("A completable source is null"));
                                        if (atomicInteger.decrementAndGet() != 0) {
                                            return;
                                        }
                                        if (mpscLinkedQueue.isEmpty()) {
                                            completable$CompletableSubscriber.onCompleted();
                                            return;
                                        } else {
                                            completable$CompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(mpscLinkedQueue));
                                            return;
                                        }
                                    }
                                    atomicInteger.getAndIncrement();
                                    final Completable$CompletableSubscriber completable$CompletableSubscriber2 = completable$CompletableSubscriber;
                                    completable.subscribe(new Completable$CompletableSubscriber() {
                                        public void onCompleted() {
                                            tryTerminate();
                                        }

                                        public void onError(Throwable th) {
                                            mpscLinkedQueue.offer(th);
                                            tryTerminate();
                                        }

                                        public void onSubscribe(Subscription subscription) {
                                            compositeSubscription.add(subscription);
                                        }

                                        void tryTerminate() {
                                            if (atomicInteger.decrementAndGet() != 0) {
                                                return;
                                            }
                                            if (mpscLinkedQueue.isEmpty()) {
                                                completable$CompletableSubscriber2.onCompleted();
                                            } else {
                                                completable$CompletableSubscriber2.onError(CompletableOnSubscribeMerge.collectErrors(mpscLinkedQueue));
                                            }
                                        }
                                    });
                                } else {
                                    return;
                                }
                            } catch (Throwable th) {
                                mpscLinkedQueue.offer(th);
                                if (atomicInteger.decrementAndGet() != 0) {
                                    return;
                                }
                                if (mpscLinkedQueue.isEmpty()) {
                                    completable$CompletableSubscriber.onCompleted();
                                    return;
                                } else {
                                    completable$CompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(mpscLinkedQueue));
                                    return;
                                }
                            }
                        }
                        return;
                    } else if (atomicInteger.decrementAndGet() != 0) {
                        return;
                    } else {
                        if (mpscLinkedQueue.isEmpty()) {
                            completable$CompletableSubscriber.onCompleted();
                            return;
                        } else {
                            completable$CompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(mpscLinkedQueue));
                            return;
                        }
                    }
                } catch (Throwable th2) {
                    mpscLinkedQueue.offer(th2);
                    if (atomicInteger.decrementAndGet() != 0) {
                        return;
                    }
                    if (mpscLinkedQueue.isEmpty()) {
                        completable$CompletableSubscriber.onCompleted();
                        return;
                    } else {
                        completable$CompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(mpscLinkedQueue));
                        return;
                    }
                }
            }
        } catch (Throwable th22) {
            completable$CompletableSubscriber.onError(th22);
        }
    }
}
