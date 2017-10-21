package rx.internal.operators;

import java.util.Arrays;
import rx.Observable$Operator;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;

public class OperatorDoOnEach<T> implements Observable$Operator<T, T> {
    final Observer<? super T> doOnEachObserver;

    public OperatorDoOnEach(Observer<? super T> observer) {
        this.doOnEachObserver = observer;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) {
            private boolean done = false;

            public void onCompleted() {
                if (!this.done) {
                    try {
                        OperatorDoOnEach.this.doOnEachObserver.onCompleted();
                        this.done = true;
                        subscriber.onCompleted();
                    } catch (Throwable th) {
                        Exceptions.throwOrReport(th, (Observer) this);
                    }
                }
            }

            public void onError(Throwable th) {
                Exceptions.throwIfFatal(th);
                if (!this.done) {
                    this.done = true;
                    try {
                        OperatorDoOnEach.this.doOnEachObserver.onError(th);
                        subscriber.onError(th);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        subscriber.onError(new CompositeException(Arrays.asList(new Throwable[]{th, th2})));
                    }
                }
            }

            public void onNext(T t) {
                if (!this.done) {
                    try {
                        OperatorDoOnEach.this.doOnEachObserver.onNext(t);
                        subscriber.onNext(t);
                    } catch (Throwable th) {
                        Exceptions.throwOrReport(th, this, t);
                    }
                }
            }
        };
    }
}
