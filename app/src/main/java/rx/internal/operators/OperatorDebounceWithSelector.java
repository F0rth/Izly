package rx.internal.operators;

import rx.Observable;
import rx.Observable$Operator;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.SerialSubscription;

public final class OperatorDebounceWithSelector<T, U> implements Observable$Operator<T, T> {
    final Func1<? super T, ? extends Observable<U>> selector;

    public OperatorDebounceWithSelector(Func1<? super T, ? extends Observable<U>> func1) {
        this.selector = func1;
    }

    public final Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        final SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        final SerialSubscription serialSubscription = new SerialSubscription();
        subscriber.add(serialSubscription);
        return new Subscriber<T>(subscriber) {
            final Subscriber<?> self = this;
            final DebounceState<T> state = new DebounceState();

            public void onCompleted() {
                this.state.emitAndComplete(serializedSubscriber, this);
            }

            public void onError(Throwable th) {
                serializedSubscriber.onError(th);
                unsubscribe();
                this.state.clear();
            }

            public void onNext(T t) {
                try {
                    Observable observable = (Observable) OperatorDebounceWithSelector.this.selector.call(t);
                    final int next = this.state.next(t);
                    AnonymousClass1 anonymousClass1 = new Subscriber<U>() {
                        public void onCompleted() {
                            AnonymousClass1.this.state.emit(next, serializedSubscriber, AnonymousClass1.this.self);
                            unsubscribe();
                        }

                        public void onError(Throwable th) {
                            AnonymousClass1.this.self.onError(th);
                        }

                        public void onNext(U u) {
                            onCompleted();
                        }
                    };
                    serialSubscription.set(anonymousClass1);
                    observable.unsafeSubscribe(anonymousClass1);
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, (Observer) this);
                }
            }

            public void onStart() {
                request(Long.MAX_VALUE);
            }
        };
    }
}
