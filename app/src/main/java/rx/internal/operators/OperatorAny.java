package rx.internal.operators;

import rx.Observable$Operator;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.internal.producers.SingleDelayedProducer;

public final class OperatorAny<T> implements Observable$Operator<Boolean, T> {
    final Func1<? super T, Boolean> predicate;
    final boolean returnOnEmpty;

    public OperatorAny(Func1<? super T, Boolean> func1, boolean z) {
        this.predicate = func1;
        this.returnOnEmpty = z;
    }

    public final Subscriber<? super T> call(final Subscriber<? super Boolean> subscriber) {
        final Producer singleDelayedProducer = new SingleDelayedProducer(subscriber);
        AnonymousClass1 anonymousClass1 = new Subscriber<T>() {
            boolean done;
            boolean hasElements;

            public void onCompleted() {
                if (!this.done) {
                    this.done = true;
                    if (this.hasElements) {
                        singleDelayedProducer.setValue(Boolean.valueOf(false));
                    } else {
                        singleDelayedProducer.setValue(Boolean.valueOf(OperatorAny.this.returnOnEmpty));
                    }
                }
            }

            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            public void onNext(T t) {
                this.hasElements = true;
                try {
                    if (((Boolean) OperatorAny.this.predicate.call(t)).booleanValue() && !this.done) {
                        this.done = true;
                        singleDelayedProducer.setValue(Boolean.valueOf(!OperatorAny.this.returnOnEmpty));
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
