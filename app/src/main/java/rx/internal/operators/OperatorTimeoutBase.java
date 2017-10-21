package rx.internal.operators;

import java.util.concurrent.TimeoutException;
import rx.Observable;
import rx.Observable$Operator;
import rx.Producer;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func3;
import rx.functions.Func4;
import rx.internal.producers.ProducerArbiter;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.SerialSubscription;

class OperatorTimeoutBase<T> implements Observable$Operator<T, T> {
    final FirstTimeoutStub<T> firstTimeoutStub;
    final Observable<? extends T> other;
    final Scheduler scheduler;
    final TimeoutStub<T> timeoutStub;

    interface FirstTimeoutStub<T> extends Func3<TimeoutSubscriber<T>, Long, Worker, Subscription> {
    }

    interface TimeoutStub<T> extends Func4<TimeoutSubscriber<T>, Long, T, Worker, Subscription> {
    }

    static final class TimeoutSubscriber<T> extends Subscriber<T> {
        long actual;
        final ProducerArbiter arbiter = new ProducerArbiter();
        final Worker inner;
        final Observable<? extends T> other;
        final SerialSubscription serial;
        final SerializedSubscriber<T> serializedSubscriber;
        boolean terminated;
        final TimeoutStub<T> timeoutStub;

        TimeoutSubscriber(SerializedSubscriber<T> serializedSubscriber, TimeoutStub<T> timeoutStub, SerialSubscription serialSubscription, Observable<? extends T> observable, Worker worker) {
            this.serializedSubscriber = serializedSubscriber;
            this.timeoutStub = timeoutStub;
            this.serial = serialSubscription;
            this.other = observable;
            this.inner = worker;
        }

        public final void onCompleted() {
            Object obj = 1;
            synchronized (this) {
                if (this.terminated) {
                    obj = null;
                } else {
                    this.terminated = true;
                }
            }
            if (obj != null) {
                this.serial.unsubscribe();
                this.serializedSubscriber.onCompleted();
            }
        }

        public final void onError(Throwable th) {
            Object obj = 1;
            synchronized (this) {
                if (this.terminated) {
                    obj = null;
                } else {
                    this.terminated = true;
                }
            }
            if (obj != null) {
                this.serial.unsubscribe();
                this.serializedSubscriber.onError(th);
            }
        }

        public final void onNext(T t) {
            Object obj = null;
            synchronized (this) {
                if (!this.terminated) {
                    this.actual++;
                    obj = 1;
                }
            }
            if (obj != null) {
                this.serializedSubscriber.onNext(t);
                this.serial.set((Subscription) this.timeoutStub.call(this, Long.valueOf(0), t, this.inner));
            }
        }

        public final void onTimeout(long j) {
            Object obj = 1;
            synchronized (this) {
                if (j != this.actual || this.terminated) {
                    obj = null;
                } else {
                    this.terminated = true;
                }
            }
            if (obj == null) {
                return;
            }
            if (this.other == null) {
                this.serializedSubscriber.onError(new TimeoutException());
                return;
            }
            AnonymousClass1 anonymousClass1 = new Subscriber<T>() {
                public void onCompleted() {
                    TimeoutSubscriber.this.serializedSubscriber.onCompleted();
                }

                public void onError(Throwable th) {
                    TimeoutSubscriber.this.serializedSubscriber.onError(th);
                }

                public void onNext(T t) {
                    TimeoutSubscriber.this.serializedSubscriber.onNext(t);
                }

                public void setProducer(Producer producer) {
                    TimeoutSubscriber.this.arbiter.setProducer(producer);
                }
            };
            this.other.unsafeSubscribe(anonymousClass1);
            this.serial.set(anonymousClass1);
        }

        public final void setProducer(Producer producer) {
            this.arbiter.setProducer(producer);
        }
    }

    OperatorTimeoutBase(FirstTimeoutStub<T> firstTimeoutStub, TimeoutStub<T> timeoutStub, Observable<? extends T> observable, Scheduler scheduler) {
        this.firstTimeoutStub = firstTimeoutStub;
        this.timeoutStub = timeoutStub;
        this.other = observable;
        this.scheduler = scheduler;
    }

    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        Worker createWorker = this.scheduler.createWorker();
        subscriber.add(createWorker);
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        SerialSubscription serialSubscription = new SerialSubscription();
        serializedSubscriber.add(serialSubscription);
        TimeoutSubscriber timeoutSubscriber = new TimeoutSubscriber(serializedSubscriber, this.timeoutStub, serialSubscription, this.other, createWorker);
        serializedSubscriber.add(timeoutSubscriber);
        serializedSubscriber.setProducer(timeoutSubscriber.arbiter);
        serialSubscription.set((Subscription) this.firstTimeoutStub.call(timeoutSubscriber, Long.valueOf(0), createWorker));
        return timeoutSubscriber;
    }
}
