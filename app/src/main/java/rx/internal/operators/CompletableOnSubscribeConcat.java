package rx.internal.operators;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import rx.Completable;
import rx.Completable$CompletableOnSubscribe;
import rx.Completable$CompletableSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.MissingBackpressureException;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.SerialSubscription;

public final class CompletableOnSubscribeConcat implements Completable$CompletableOnSubscribe {
    final int prefetch;
    final Observable<Completable> sources;

    static final class CompletableConcatSubscriber extends Subscriber<Completable> {
        static final AtomicIntegerFieldUpdater<CompletableConcatSubscriber> ONCE = AtomicIntegerFieldUpdater.newUpdater(CompletableConcatSubscriber.class, "once");
        final Completable$CompletableSubscriber actual;
        volatile boolean done;
        final ConcatInnerSubscriber inner = new ConcatInnerSubscriber();
        volatile int once;
        final int prefetch;
        final SpscArrayQueue<Completable> queue;
        final SerialSubscription sr = new SerialSubscription();
        final AtomicInteger wip = new AtomicInteger();

        final class ConcatInnerSubscriber implements Completable$CompletableSubscriber {
            ConcatInnerSubscriber() {
            }

            public final void onCompleted() {
                CompletableConcatSubscriber.this.innerComplete();
            }

            public final void onError(Throwable th) {
                CompletableConcatSubscriber.this.innerError(th);
            }

            public final void onSubscribe(Subscription subscription) {
                CompletableConcatSubscriber.this.sr.set(subscription);
            }
        }

        public CompletableConcatSubscriber(Completable$CompletableSubscriber completable$CompletableSubscriber, int i) {
            this.actual = completable$CompletableSubscriber;
            this.prefetch = i;
            this.queue = new SpscArrayQueue(i);
            add(this.sr);
            request((long) i);
        }

        final void innerComplete() {
            if (this.wip.decrementAndGet() != 0) {
                next();
            }
            if (!this.done) {
                request(1);
            }
        }

        final void innerError(Throwable th) {
            unsubscribe();
            onError(th);
        }

        final void next() {
            boolean z = this.done;
            Completable completable = (Completable) this.queue.poll();
            if (completable != null) {
                completable.subscribe(this.inner);
            } else if (!z) {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(new IllegalStateException("Queue is empty?!"));
            } else if (ONCE.compareAndSet(this, 0, 1)) {
                this.actual.onCompleted();
            }
        }

        public final void onCompleted() {
            if (!this.done) {
                this.done = true;
                if (this.wip.getAndIncrement() == 0) {
                    next();
                }
            }
        }

        public final void onError(Throwable th) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.actual.onError(th);
            } else {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
            }
        }

        public final void onNext(Completable completable) {
            if (!this.queue.offer(completable)) {
                onError(new MissingBackpressureException());
            } else if (this.wip.getAndIncrement() == 0) {
                next();
            }
        }
    }

    public CompletableOnSubscribeConcat(Observable<? extends Completable> observable, int i) {
        this.sources = observable;
        this.prefetch = i;
    }

    public final void call(Completable$CompletableSubscriber completable$CompletableSubscriber) {
        CompletableConcatSubscriber completableConcatSubscriber = new CompletableConcatSubscriber(completable$CompletableSubscriber, this.prefetch);
        completable$CompletableSubscriber.onSubscribe(completableConcatSubscriber);
        this.sources.subscribe(completableConcatSubscriber);
    }
}
