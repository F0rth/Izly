package rx.internal.operators;

import rx.Observable$Operator;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

public final class OperatorTakeUntilPredicate<T> implements Observable$Operator<T, T> {
    final Func1<? super T, Boolean> stopPredicate;

    final class ParentSubscriber extends Subscriber<T> {
        private final Subscriber<? super T> child;
        private boolean done = false;

        ParentSubscriber(Subscriber<? super T> subscriber) {
            this.child = subscriber;
        }

        final void downstreamRequest(long j) {
            request(j);
        }

        public final void onCompleted() {
            if (!this.done) {
                this.child.onCompleted();
            }
        }

        public final void onError(Throwable th) {
            if (!this.done) {
                this.child.onError(th);
            }
        }

        public final void onNext(T t) {
            this.child.onNext(t);
            try {
                if (((Boolean) OperatorTakeUntilPredicate.this.stopPredicate.call(t)).booleanValue()) {
                    this.done = true;
                    this.child.onCompleted();
                    unsubscribe();
                }
            } catch (Throwable th) {
                this.done = true;
                Exceptions.throwOrReport(th, this.child, t);
                unsubscribe();
            }
        }
    }

    public OperatorTakeUntilPredicate(Func1<? super T, Boolean> func1) {
        this.stopPredicate = func1;
    }

    public final Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        final ParentSubscriber parentSubscriber = new ParentSubscriber(subscriber);
        subscriber.add(parentSubscriber);
        subscriber.setProducer(new Producer() {
            public void request(long j) {
                parentSubscriber.downstreamRequest(j);
            }
        });
        return parentSubscriber;
    }
}
