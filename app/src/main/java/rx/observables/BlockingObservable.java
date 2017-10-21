package rx.observables;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.annotations.Experimental;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func1;
import rx.internal.operators.BlockingOperatorLatest;
import rx.internal.operators.BlockingOperatorMostRecent;
import rx.internal.operators.BlockingOperatorNext;
import rx.internal.operators.BlockingOperatorToFuture;
import rx.internal.operators.BlockingOperatorToIterator;
import rx.internal.operators.NotificationLite;
import rx.internal.util.BlockingUtils;
import rx.internal.util.UtilityFunctions;
import rx.subscriptions.Subscriptions;

public final class BlockingObservable<T> {
    static final Object ON_START = new Object();
    static final Object SET_PRODUCER = new Object();
    static final Object UNSUBSCRIBE = new Object();
    private final Observable<? extends T> o;

    private BlockingObservable(Observable<? extends T> observable) {
        this.o = observable;
    }

    private T blockForSingle(Observable<? extends T> observable) {
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        BlockingUtils.awaitForComplete(countDownLatch, observable.subscribe(new Subscriber<T>() {
            public void onCompleted() {
                countDownLatch.countDown();
            }

            public void onError(Throwable th) {
                atomicReference2.set(th);
                countDownLatch.countDown();
            }

            public void onNext(T t) {
                atomicReference.set(t);
            }
        }));
        if (atomicReference2.get() == null) {
            return atomicReference.get();
        }
        if (atomicReference2.get() instanceof RuntimeException) {
            throw ((RuntimeException) atomicReference2.get());
        }
        throw new RuntimeException((Throwable) atomicReference2.get());
    }

    public static <T> BlockingObservable<T> from(Observable<? extends T> observable) {
        return new BlockingObservable(observable);
    }

    public final T first() {
        return blockForSingle(this.o.first());
    }

    public final T first(Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.first(func1));
    }

    public final T firstOrDefault(T t) {
        return blockForSingle(this.o.map(UtilityFunctions.identity()).firstOrDefault(t));
    }

    public final T firstOrDefault(T t, Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.filter(func1).map(UtilityFunctions.identity()).firstOrDefault(t));
    }

    public final void forEach(final Action1<? super T> action1) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference atomicReference = new AtomicReference();
        BlockingUtils.awaitForComplete(countDownLatch, this.o.subscribe(new Subscriber<T>() {
            public void onCompleted() {
                countDownLatch.countDown();
            }

            public void onError(Throwable th) {
                atomicReference.set(th);
                countDownLatch.countDown();
            }

            public void onNext(T t) {
                action1.call(t);
            }
        }));
        if (atomicReference.get() == null) {
            return;
        }
        if (atomicReference.get() instanceof RuntimeException) {
            throw ((RuntimeException) atomicReference.get());
        }
        throw new RuntimeException((Throwable) atomicReference.get());
    }

    public final Iterator<T> getIterator() {
        return BlockingOperatorToIterator.toIterator(this.o);
    }

    public final T last() {
        return blockForSingle(this.o.last());
    }

    public final T last(Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.last(func1));
    }

    public final T lastOrDefault(T t) {
        return blockForSingle(this.o.map(UtilityFunctions.identity()).lastOrDefault(t));
    }

    public final T lastOrDefault(T t, Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.filter(func1).map(UtilityFunctions.identity()).lastOrDefault(t));
    }

    public final Iterable<T> latest() {
        return BlockingOperatorLatest.latest(this.o);
    }

    public final Iterable<T> mostRecent(T t) {
        return BlockingOperatorMostRecent.mostRecent(this.o, t);
    }

    public final Iterable<T> next() {
        return BlockingOperatorNext.next(this.o);
    }

    public final T single() {
        return blockForSingle(this.o.single());
    }

    public final T single(Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.single(func1));
    }

    public final T singleOrDefault(T t) {
        return blockForSingle(this.o.map(UtilityFunctions.identity()).singleOrDefault(t));
    }

    public final T singleOrDefault(T t, Func1<? super T, Boolean> func1) {
        return blockForSingle(this.o.filter(func1).map(UtilityFunctions.identity()).singleOrDefault(t));
    }

    @Experimental
    public final void subscribe() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Throwable[] thArr = new Throwable[]{null};
        BlockingUtils.awaitForComplete(countDownLatch, this.o.subscribe(new Subscriber<T>() {
            public void onCompleted() {
                countDownLatch.countDown();
            }

            public void onError(Throwable th) {
                thArr[0] = th;
                countDownLatch.countDown();
            }

            public void onNext(T t) {
            }
        }));
        Throwable th = thArr[0];
        if (th == null) {
            return;
        }
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        }
        throw new RuntimeException(th);
    }

    @Experimental
    public final void subscribe(Observer<? super T> observer) {
        final NotificationLite instance = NotificationLite.instance();
        final BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        Subscription subscribe = this.o.subscribe(new Subscriber<T>() {
            public void onCompleted() {
                linkedBlockingQueue.offer(instance.completed());
            }

            public void onError(Throwable th) {
                linkedBlockingQueue.offer(instance.error(th));
            }

            public void onNext(T t) {
                linkedBlockingQueue.offer(instance.next(t));
            }
        });
        while (true) {
            try {
                Object poll = linkedBlockingQueue.poll();
                if (poll == null) {
                    poll = linkedBlockingQueue.take();
                }
                if (instance.accept(observer, poll)) {
                    break;
                }
            } catch (Throwable e) {
                Thread.currentThread().interrupt();
                observer.onError(e);
            } finally {
                subscribe.unsubscribe();
            }
        }
    }

    @Experimental
    public final void subscribe(Subscriber<? super T> subscriber) {
        final NotificationLite instance = NotificationLite.instance();
        final BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        final Producer[] producerArr = new Producer[]{null};
        AnonymousClass6 anonymousClass6 = new Subscriber<T>() {
            public void onCompleted() {
                linkedBlockingQueue.offer(instance.completed());
            }

            public void onError(Throwable th) {
                linkedBlockingQueue.offer(instance.error(th));
            }

            public void onNext(T t) {
                linkedBlockingQueue.offer(instance.next(t));
            }

            public void onStart() {
                linkedBlockingQueue.offer(BlockingObservable.ON_START);
            }

            public void setProducer(Producer producer) {
                producerArr[0] = producer;
                linkedBlockingQueue.offer(BlockingObservable.SET_PRODUCER);
            }
        };
        subscriber.add(anonymousClass6);
        subscriber.add(Subscriptions.create(new Action0() {
            public void call() {
                linkedBlockingQueue.offer(BlockingObservable.UNSUBSCRIBE);
            }
        }));
        this.o.subscribe(anonymousClass6);
        while (!subscriber.isUnsubscribed()) {
            try {
                Object poll = linkedBlockingQueue.poll();
                if (poll == null) {
                    poll = linkedBlockingQueue.take();
                }
                if (subscriber.isUnsubscribed() || poll == UNSUBSCRIBE) {
                    break;
                } else if (poll == ON_START) {
                    subscriber.onStart();
                } else if (poll == SET_PRODUCER) {
                    subscriber.setProducer(producerArr[0]);
                } else if (instance.accept(subscriber, poll)) {
                    return;
                }
            } catch (Throwable e) {
                Thread.currentThread().interrupt();
                subscriber.onError(e);
            } finally {
                anonymousClass6.unsubscribe();
            }
        }
        anonymousClass6.unsubscribe();
    }

    @Experimental
    public final void subscribe(Action1<? super T> action1) {
        subscribe(action1, new Action1<Throwable>() {
            public void call(Throwable th) {
                throw new OnErrorNotImplementedException(th);
            }
        }, Actions.empty());
    }

    @Experimental
    public final void subscribe(Action1<? super T> action1, Action1<? super Throwable> action12) {
        subscribe(action1, action12, Actions.empty());
    }

    @Experimental
    public final void subscribe(final Action1<? super T> action1, final Action1<? super Throwable> action12, final Action0 action0) {
        subscribe(new Observer<T>() {
            public void onCompleted() {
                action0.call();
            }

            public void onError(Throwable th) {
                action12.call(th);
            }

            public void onNext(T t) {
                action1.call(t);
            }
        });
    }

    public final Future<T> toFuture() {
        return BlockingOperatorToFuture.toFuture(this.o);
    }

    public final Iterable<T> toIterable() {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return BlockingObservable.this.getIterator();
            }
        };
    }
}
