package rx.internal.operators;

import rx.Observable$Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.functions.Func2;

public final class OperatorTakeWhile<T> implements Observable$Operator<T, T> {
    final Func2<? super T, ? super Integer, Boolean> predicate;

    class AnonymousClass1 implements Func2<T, Integer, Boolean> {
        final /* synthetic */ Func1 val$underlying;

        AnonymousClass1(Func1 func1) {
            this.val$underlying = func1;
        }

        public Boolean call(T t, Integer num) {
            return (Boolean) this.val$underlying.call(t);
        }
    }

    public OperatorTakeWhile(Func1<? super T, Boolean> func1) {
        this(new AnonymousClass1(func1));
    }

    public OperatorTakeWhile(Func2<? super T, ? super Integer, Boolean> func2) {
        this.predicate = func2;
    }

    public final Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        AnonymousClass2 anonymousClass2 = new Subscriber<T>(false, subscriber) {
            private int counter = 0;
            private boolean done = false;

            public void onCompleted() {
                if (!this.done) {
                    subscriber.onCompleted();
                }
            }

            public void onError(Throwable th) {
                if (!this.done) {
                    subscriber.onError(th);
                }
            }

            public void onNext(T t) {
                try {
                    Func2 func2 = OperatorTakeWhile.this.predicate;
                    int i = this.counter;
                    this.counter = i + 1;
                    if (((Boolean) func2.call(t, Integer.valueOf(i))).booleanValue()) {
                        subscriber.onNext(t);
                        return;
                    }
                    this.done = true;
                    subscriber.onCompleted();
                    unsubscribe();
                } catch (Throwable th) {
                    this.done = true;
                    Exceptions.throwOrReport(th, subscriber, t);
                    unsubscribe();
                }
            }
        };
        subscriber.add(anonymousClass2);
        return anonymousClass2;
    }
}
