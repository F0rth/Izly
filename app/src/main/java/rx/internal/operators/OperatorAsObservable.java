package rx.internal.operators;

import rx.Observable$Operator;
import rx.Subscriber;

public final class OperatorAsObservable<T> implements Observable$Operator<T, T> {

    static final class Holder {
        static final OperatorAsObservable<Object> INSTANCE = new OperatorAsObservable();

        private Holder() {
        }
    }

    OperatorAsObservable() {
    }

    public static <T> OperatorAsObservable<T> instance() {
        return Holder.INSTANCE;
    }

    public final Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        return subscriber;
    }
}
