package rx.internal.operators;

import rx.Observable;
import rx.Observable$Operator;
import rx.Subscriber;
import rx.observers.SerializedSubscriber;

public final class OperatorTakeUntil<T, E> implements Observable$Operator<T, T> {
    private final Observable<? extends E> other;

    public OperatorTakeUntil(Observable<? extends E> observable) {
        this.other = observable;
    }

    public final Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        final SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber, false);
        final AnonymousClass1 anonymousClass1 = new Subscriber<T>(false, serializedSubscriber) {
            public void onCompleted() {
                try {
                    serializedSubscriber.onCompleted();
                } finally {
                    serializedSubscriber.unsubscribe();
                }
            }

            public void onError(Throwable th) {
                try {
                    serializedSubscriber.onError(th);
                } finally {
                    serializedSubscriber.unsubscribe();
                }
            }

            public void onNext(T t) {
                serializedSubscriber.onNext(t);
            }
        };
        AnonymousClass2 anonymousClass2 = new Subscriber<E>() {
            public void onCompleted() {
                anonymousClass1.onCompleted();
            }

            public void onError(Throwable th) {
                anonymousClass1.onError(th);
            }

            public void onNext(E e) {
                onCompleted();
            }

            public void onStart() {
                request(Long.MAX_VALUE);
            }
        };
        serializedSubscriber.add(anonymousClass1);
        serializedSubscriber.add(anonymousClass2);
        subscriber.add(serializedSubscriber);
        this.other.unsafeSubscribe(anonymousClass2);
        return anonymousClass1;
    }
}
