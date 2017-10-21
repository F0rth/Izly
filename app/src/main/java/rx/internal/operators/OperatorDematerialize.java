package rx.internal.operators;

import rx.Notification;
import rx.Observable$Operator;
import rx.Subscriber;

public final class OperatorDematerialize<T> implements Observable$Operator<T, Notification<T>> {

    static final class Holder {
        static final OperatorDematerialize<Object> INSTANCE = new OperatorDematerialize();

        private Holder() {
        }
    }

    OperatorDematerialize() {
    }

    public static OperatorDematerialize instance() {
        return Holder.INSTANCE;
    }

    public final Subscriber<? super Notification<T>> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<Notification<T>>(subscriber) {
            boolean terminated;

            public void onCompleted() {
                if (!this.terminated) {
                    this.terminated = true;
                    subscriber.onCompleted();
                }
            }

            public void onError(Throwable th) {
                if (!this.terminated) {
                    this.terminated = true;
                    subscriber.onError(th);
                }
            }

            public void onNext(Notification<T> notification) {
                switch (notification.getKind()) {
                    case OnNext:
                        if (!this.terminated) {
                            subscriber.onNext(notification.getValue());
                            return;
                        }
                        return;
                    case OnError:
                        onError(notification.getThrowable());
                        return;
                    case OnCompleted:
                        onCompleted();
                        return;
                    default:
                        return;
                }
            }
        };
    }
}
