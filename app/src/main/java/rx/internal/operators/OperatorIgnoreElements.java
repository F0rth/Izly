package rx.internal.operators;

import rx.Observable$Operator;
import rx.Subscriber;

public class OperatorIgnoreElements<T> implements Observable$Operator<T, T> {

    static class Holder {
        static final OperatorIgnoreElements<?> INSTANCE = new OperatorIgnoreElements();

        private Holder() {
        }
    }

    OperatorIgnoreElements() {
    }

    public static <T> OperatorIgnoreElements<T> instance() {
        return Holder.INSTANCE;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        AnonymousClass1 anonymousClass1 = new Subscriber<T>() {
            public void onCompleted() {
                subscriber.onCompleted();
            }

            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            public void onNext(T t) {
            }
        };
        subscriber.add(anonymousClass1);
        return anonymousClass1;
    }
}
