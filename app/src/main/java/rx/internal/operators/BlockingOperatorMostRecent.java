package rx.internal.operators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;

public final class BlockingOperatorMostRecent {

    static final class MostRecentObserver<T> extends Subscriber<T> {
        final NotificationLite<T> nl = NotificationLite.instance();
        volatile Object value;

        MostRecentObserver(T t) {
            this.value = this.nl.next(t);
        }

        public final Iterator<T> getIterable() {
            return new Iterator<T>() {
                private Object buf = null;

                public boolean hasNext() {
                    this.buf = MostRecentObserver.this.value;
                    return !MostRecentObserver.this.nl.isCompleted(this.buf);
                }

                public T next() {
                    try {
                        if (this.buf == null) {
                            Object obj = MostRecentObserver.this.value;
                        }
                        if (MostRecentObserver.this.nl.isCompleted(this.buf)) {
                            throw new NoSuchElementException();
                        } else if (MostRecentObserver.this.nl.isError(this.buf)) {
                            throw Exceptions.propagate(MostRecentObserver.this.nl.getError(this.buf));
                        } else {
                            T value = MostRecentObserver.this.nl.getValue(this.buf);
                            this.buf = null;
                            return value;
                        }
                    } finally {
                        this.buf = null;
                    }
                }

                public void remove() {
                    throw new UnsupportedOperationException("Read only iterator");
                }
            };
        }

        public final void onCompleted() {
            this.value = this.nl.completed();
        }

        public final void onError(Throwable th) {
            this.value = this.nl.error(th);
        }

        public final void onNext(T t) {
            this.value = this.nl.next(t);
        }
    }

    private BlockingOperatorMostRecent() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Iterable<T> mostRecent(final Observable<? extends T> observable, final T t) {
        return new Iterable<T>() {
            public final Iterator<T> iterator() {
                MostRecentObserver mostRecentObserver = new MostRecentObserver(t);
                observable.subscribe(mostRecentObserver);
                return mostRecentObserver.getIterable();
            }
        };
    }
}
