package rx.internal.operators;

import java.util.HashSet;
import java.util.Set;
import rx.Observable$Operator;
import rx.Subscriber;
import rx.functions.Func1;
import rx.internal.util.UtilityFunctions;

public final class OperatorDistinct<T, U> implements Observable$Operator<T, T> {
    final Func1<? super T, ? extends U> keySelector;

    static class Holder {
        static final OperatorDistinct<?, ?> INSTANCE = new OperatorDistinct(UtilityFunctions.identity());

        private Holder() {
        }
    }

    public OperatorDistinct(Func1<? super T, ? extends U> func1) {
        this.keySelector = func1;
    }

    public static <T> OperatorDistinct<T, T> instance() {
        return Holder.INSTANCE;
    }

    public final Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) {
            Set<U> keyMemory = new HashSet();

            public void onCompleted() {
                this.keyMemory = null;
                subscriber.onCompleted();
            }

            public void onError(Throwable th) {
                this.keyMemory = null;
                subscriber.onError(th);
            }

            public void onNext(T t) {
                if (this.keyMemory.add(OperatorDistinct.this.keySelector.call(t))) {
                    subscriber.onNext(t);
                } else {
                    request(1);
                }
            }
        };
    }
}
