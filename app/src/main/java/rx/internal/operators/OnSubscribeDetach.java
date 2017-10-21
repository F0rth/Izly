package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.internal.util.RxJavaPluginUtils;

public final class OnSubscribeDetach<T> implements Observable$OnSubscribe<T> {
    final Observable<T> source;

    static final class DetachProducer<T> implements Producer, Subscription {
        final DetachSubscriber<T> parent;

        public DetachProducer(DetachSubscriber<T> detachSubscriber) {
            this.parent = detachSubscriber;
        }

        public final boolean isUnsubscribed() {
            return this.parent.isUnsubscribed();
        }

        public final void request(long j) {
            this.parent.innerRequest(j);
        }

        public final void unsubscribe() {
            this.parent.innerUnsubscribe();
        }
    }

    static final class DetachSubscriber<T> extends Subscriber<T> {
        final AtomicReference<Subscriber<? super T>> actual;
        final AtomicReference<Producer> producer = new AtomicReference();
        final AtomicLong requested = new AtomicLong();

        public DetachSubscriber(Subscriber<? super T> subscriber) {
            this.actual = new AtomicReference(subscriber);
        }

        final void innerRequest(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("n >= 0 required but it was " + j);
            }
            Producer producer = (Producer) this.producer.get();
            if (producer != null) {
                producer.request(j);
                return;
            }
            BackpressureUtils.getAndAddRequest(this.requested, j);
            producer = (Producer) this.producer.get();
            if (producer != null && producer != TerminatedProducer.INSTANCE) {
                producer.request(this.requested.getAndSet(0));
            }
        }

        final void innerUnsubscribe() {
            this.producer.lazySet(TerminatedProducer.INSTANCE);
            this.actual.lazySet(null);
            unsubscribe();
        }

        public final void onCompleted() {
            this.producer.lazySet(TerminatedProducer.INSTANCE);
            Subscriber subscriber = (Subscriber) this.actual.getAndSet(null);
            if (subscriber != null) {
                subscriber.onCompleted();
            }
        }

        public final void onError(Throwable th) {
            this.producer.lazySet(TerminatedProducer.INSTANCE);
            Subscriber subscriber = (Subscriber) this.actual.getAndSet(null);
            if (subscriber != null) {
                subscriber.onError(th);
            } else {
                RxJavaPluginUtils.handleException(th);
            }
        }

        public final void onNext(T t) {
            Subscriber subscriber = (Subscriber) this.actual.get();
            if (subscriber != null) {
                subscriber.onNext(t);
            }
        }

        public final void setProducer(Producer producer) {
            if (this.producer.compareAndSet(null, producer)) {
                producer.request(this.requested.getAndSet(0));
            } else if (this.producer.get() != TerminatedProducer.INSTANCE) {
                throw new IllegalStateException("Producer already set!");
            }
        }
    }

    enum TerminatedProducer implements Producer {
        INSTANCE;

        public final void request(long j) {
        }
    }

    public OnSubscribeDetach(Observable<T> observable) {
        this.source = observable;
    }

    public final void call(Subscriber<? super T> subscriber) {
        DetachSubscriber detachSubscriber = new DetachSubscriber(subscriber);
        Producer detachProducer = new DetachProducer(detachSubscriber);
        subscriber.add(detachProducer);
        subscriber.setProducer(detachProducer);
        this.source.unsafeSubscribe(detachSubscriber);
    }
}
