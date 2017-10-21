package rx.internal.operators;

import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable$Operator;
import rx.Subscriber;
import rx.Subscription;
import rx.observers.SerializedSubscriber;

public final class OperatorSampleWithObservable<T, U> implements Observable$Operator<T, T> {
    static final Object EMPTY_TOKEN = new Object();
    final Observable<U> sampler;

    public OperatorSampleWithObservable(Observable<U> observable) {
        this.sampler = observable;
    }

    public final Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        final SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        final AtomicReference atomicReference = new AtomicReference(EMPTY_TOKEN);
        final AtomicReference atomicReference2 = new AtomicReference();
        final AnonymousClass1 anonymousClass1 = new Subscriber<U>() {
            public void onCompleted() {
                onNext(null);
                serializedSubscriber.onCompleted();
                ((Subscription) atomicReference2.get()).unsubscribe();
            }

            public void onError(Throwable th) {
                serializedSubscriber.onError(th);
                ((Subscription) atomicReference2.get()).unsubscribe();
            }

            public void onNext(U u) {
                Object andSet = atomicReference.getAndSet(OperatorSampleWithObservable.EMPTY_TOKEN);
                if (andSet != OperatorSampleWithObservable.EMPTY_TOKEN) {
                    serializedSubscriber.onNext(andSet);
                }
            }
        };
        AnonymousClass2 anonymousClass2 = new Subscriber<T>() {
            public void onCompleted() {
                anonymousClass1.onNext(null);
                serializedSubscriber.onCompleted();
                anonymousClass1.unsubscribe();
            }

            public void onError(Throwable th) {
                serializedSubscriber.onError(th);
                anonymousClass1.unsubscribe();
            }

            public void onNext(T t) {
                atomicReference.set(t);
            }
        };
        atomicReference2.lazySet(anonymousClass2);
        subscriber.add(anonymousClass2);
        subscriber.add(anonymousClass1);
        this.sampler.unsafeSubscribe(anonymousClass1);
        return anonymousClass2;
    }
}
