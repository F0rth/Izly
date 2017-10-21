package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Observable$Operator;
import rx.Subscriber;
import rx.observers.SerializedSubscriber;

public final class OperatorSkipUntil<T, U> implements Observable$Operator<T, T> {
    final Observable<U> other;

    public OperatorSkipUntil(Observable<U> observable) {
        this.other = observable;
    }

    public final Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        final SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        AnonymousClass1 anonymousClass1 = new Subscriber<U>() {
            public void onCompleted() {
                unsubscribe();
            }

            public void onError(Throwable th) {
                serializedSubscriber.onError(th);
                serializedSubscriber.unsubscribe();
            }

            public void onNext(U u) {
                atomicBoolean.set(true);
                unsubscribe();
            }
        };
        subscriber.add(anonymousClass1);
        this.other.unsafeSubscribe(anonymousClass1);
        return new Subscriber<T>(subscriber) {
            public void onCompleted() {
                serializedSubscriber.onCompleted();
                unsubscribe();
            }

            public void onError(Throwable th) {
                serializedSubscriber.onError(th);
                unsubscribe();
            }

            public void onNext(T t) {
                if (atomicBoolean.get()) {
                    serializedSubscriber.onNext(t);
                } else {
                    request(1);
                }
            }
        };
    }
}
