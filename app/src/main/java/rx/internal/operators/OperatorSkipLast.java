package rx.internal.operators;

import java.util.ArrayDeque;
import java.util.Deque;
import rx.Observable$Operator;
import rx.Subscriber;

public class OperatorSkipLast<T> implements Observable$Operator<T, T> {
    final int count;

    public OperatorSkipLast(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("count could not be negative");
        }
        this.count = i;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) {
            private final Deque<Object> deque = new ArrayDeque();
            private final NotificationLite<T> on = NotificationLite.instance();

            public void onCompleted() {
                subscriber.onCompleted();
            }

            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            public void onNext(T t) {
                if (OperatorSkipLast.this.count == 0) {
                    subscriber.onNext(t);
                    return;
                }
                if (this.deque.size() == OperatorSkipLast.this.count) {
                    subscriber.onNext(this.on.getValue(this.deque.removeFirst()));
                } else {
                    request(1);
                }
                this.deque.offerLast(this.on.next(t));
            }
        };
    }
}
