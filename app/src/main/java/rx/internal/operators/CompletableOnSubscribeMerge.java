package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import rx.Completable;
import rx.Completable$CompletableOnSubscribe;
import rx.Completable$CompletableSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMerge implements Completable$CompletableOnSubscribe {
    final boolean delayErrors;
    final int maxConcurrency;
    final Observable<Completable> source;

    static final class CompletableMergeSubscriber extends Subscriber<Completable> {
        static final AtomicReferenceFieldUpdater<CompletableMergeSubscriber, Queue> ERRORS = AtomicReferenceFieldUpdater.newUpdater(CompletableMergeSubscriber.class, Queue.class, "errors");
        static final AtomicIntegerFieldUpdater<CompletableMergeSubscriber> ONCE = AtomicIntegerFieldUpdater.newUpdater(CompletableMergeSubscriber.class, "once");
        final Completable$CompletableSubscriber actual;
        final boolean delayErrors;
        volatile boolean done;
        volatile Queue<Throwable> errors;
        final int maxConcurrency;
        volatile int once;
        final CompositeSubscription set = new CompositeSubscription();
        final AtomicInteger wip = new AtomicInteger(1);

        public CompletableMergeSubscriber(Completable$CompletableSubscriber completable$CompletableSubscriber, int i, boolean z) {
            this.actual = completable$CompletableSubscriber;
            this.maxConcurrency = i;
            this.delayErrors = z;
            if (i == Integer.MAX_VALUE) {
                request(Long.MAX_VALUE);
            } else {
                request((long) i);
            }
        }

        final Queue<Throwable> getOrCreateErrors() {
            Queue<Throwable> queue = this.errors;
            if (queue != null) {
                return queue;
            }
            Queue concurrentLinkedQueue = new ConcurrentLinkedQueue();
            return !ERRORS.compareAndSet(this, null, concurrentLinkedQueue) ? this.errors : concurrentLinkedQueue;
        }

        public final void onCompleted() {
            if (!this.done) {
                this.done = true;
                terminate();
            }
        }

        public final void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                return;
            }
            getOrCreateErrors().offer(th);
            this.done = true;
            terminate();
        }

        public final void onNext(Completable completable) {
            if (!this.done) {
                this.wip.getAndIncrement();
                completable.subscribe(new Completable$CompletableSubscriber() {
                    Subscription d;
                    boolean innerDone;

                    public void onCompleted() {
                        if (!this.innerDone) {
                            this.innerDone = true;
                            CompletableMergeSubscriber.this.set.remove(this.d);
                            CompletableMergeSubscriber.this.terminate();
                            if (!CompletableMergeSubscriber.this.done) {
                                CompletableMergeSubscriber.this.request(1);
                            }
                        }
                    }

                    public void onError(Throwable th) {
                        if (this.innerDone) {
                            RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
                            return;
                        }
                        this.innerDone = true;
                        CompletableMergeSubscriber.this.set.remove(this.d);
                        CompletableMergeSubscriber.this.getOrCreateErrors().offer(th);
                        CompletableMergeSubscriber.this.terminate();
                        if (CompletableMergeSubscriber.this.delayErrors && !CompletableMergeSubscriber.this.done) {
                            CompletableMergeSubscriber.this.request(1);
                        }
                    }

                    public void onSubscribe(Subscription subscription) {
                        this.d = subscription;
                        CompletableMergeSubscriber.this.set.add(subscription);
                    }
                });
            }
        }

        final void terminate() {
            Queue queue;
            Throwable collectErrors;
            if (this.wip.decrementAndGet() == 0) {
                queue = this.errors;
                if (queue == null || queue.isEmpty()) {
                    this.actual.onCompleted();
                    return;
                }
                collectErrors = CompletableOnSubscribeMerge.collectErrors(queue);
                if (ONCE.compareAndSet(this, 0, 1)) {
                    this.actual.onError(collectErrors);
                } else {
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(collectErrors);
                }
            } else if (!this.delayErrors) {
                queue = this.errors;
                if (queue != null && !queue.isEmpty()) {
                    collectErrors = CompletableOnSubscribeMerge.collectErrors(queue);
                    if (ONCE.compareAndSet(this, 0, 1)) {
                        this.actual.onError(collectErrors);
                    } else {
                        RxJavaPlugins.getInstance().getErrorHandler().handleError(collectErrors);
                    }
                }
            }
        }
    }

    public CompletableOnSubscribeMerge(Observable<? extends Completable> observable, int i, boolean z) {
        this.source = observable;
        this.maxConcurrency = i;
        this.delayErrors = z;
    }

    public static Throwable collectErrors(Queue<Throwable> queue) {
        Collection arrayList = new ArrayList();
        while (true) {
            Throwable th = (Throwable) queue.poll();
            if (th == null) {
                break;
            }
            arrayList.add(th);
        }
        return arrayList.isEmpty() ? null : arrayList.size() == 1 ? (Throwable) arrayList.get(0) : new CompositeException(arrayList);
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        CompletableMergeSubscriber completableMergeSubscriber = new CompletableMergeSubscriber(completable$CompletableSubscriber, this.maxConcurrency, this.delayErrors);
        completable$CompletableSubscriber.onSubscribe(completableMergeSubscriber);
        this.source.subscribe(completableMergeSubscriber);
    }
}
