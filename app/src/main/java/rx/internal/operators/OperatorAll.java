package rx.internal.operators;

import rx.Observable$Operator;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.internal.producers.SingleDelayedProducer;

public final class OperatorAll<T> implements Observable$Operator<Boolean, T> {
    final Func1<? super T, Boolean> predicate;

    public OperatorAll(Func1<? super T, Boolean> func1) {
        this.predicate = func1;
    }

    public final Subscriber<? super T> call(final Subscriber<? super Boolean> subscriber) {
        final Producer singleDelayedProducer = new SingleDelayedProducer(subscriber);
        AnonymousClass1 anonymousClass1 = new Subscriber<T>() {
            boolean done;

            public void onCompleted() {
                if (!this.done) {
                    this.done = true;
                    singleDelayedProducer.setValue(Boolean.valueOf(true));
                }
            }

            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            public void onNext(T t) {
                try {
                    if (!((Boolean) OperatorAll.this.predicate.call(t)).booleanValue() && !this.done) {
                        this.done = true;
                        singleDelayedProducer.setValue(Boolean.valueOf(false));
                        unsubscribe();
                    }
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, this, t);
                }
            }
        };
        subscriber.add(anonymousClass1);
        subscriber.setProducer(singleDelayedProducer);
        return anonymousClass1;
    }
}
