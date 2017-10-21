package rx.internal.operators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable$OnSubscribe;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;

public final class OnSubscribeFromIterable<T> implements Observable$OnSubscribe<T> {
    final Iterable<? extends T> is;

    static final class IterableProducer<T> extends AtomicLong implements Producer {
        private static final long serialVersionUID = -8730475647105475802L;
        private final Iterator<? extends T> it;
        private final Subscriber<? super T> o;

        IterableProducer(Subscriber<? super T> subscriber, Iterator<? extends T> it) {
            this.o = subscriber;
            this.it = it;
        }

        final void fastpath() {
            Observer observer = this.o;
            Iterator it = this.it;
            while (!observer.isUnsubscribed()) {
                try {
                    observer.onNext(it.next());
                    if (!observer.isUnsubscribed()) {
                        try {
                            if (!it.hasNext()) {
                                if (!observer.isUnsubscribed()) {
                                    observer.onCompleted();
                                    return;
                                }
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwOrReport(th, observer);
                            return;
                        }
                    }
                    return;
                } catch (Throwable th2) {
                    Exceptions.throwOrReport(th2, observer);
                    return;
                }
            }
        }

        public final void request(long j) {
            if (get() != Long.MAX_VALUE) {
                if (j == Long.MAX_VALUE && compareAndSet(0, Long.MAX_VALUE)) {
                    fastpath();
                } else if (j > 0 && BackpressureUtils.getAndAddRequest(this, j) == 0) {
                    slowpath(j);
                }
            }
        }

        final void slowpath(long j) {
            Observer observer = this.o;
            Iterator it = this.it;
            long j2 = 0;
            while (true) {
                if (j2 == j) {
                    j = get();
                    if (j2 == j) {
                        j = BackpressureUtils.produced(this, j2);
                        if (j != 0) {
                            j2 = 0;
                        } else {
                            return;
                        }
                    }
                    continue;
                } else if (!observer.isUnsubscribed()) {
                    try {
                        observer.onNext(it.next());
                        if (!observer.isUnsubscribed()) {
                            try {
                                if (!it.hasNext()) {
                                    break;
                                }
                                j2++;
                            } catch (Throwable th) {
                                Exceptions.throwOrReport(th, observer);
                                return;
                            }
                        }
                        return;
                    } catch (Throwable th2) {
                        Exceptions.throwOrReport(th2, observer);
                        return;
                    }
                } else {
                    return;
                }
            }
            if (!observer.isUnsubscribed()) {
                observer.onCompleted();
            }
        }
    }

    public OnSubscribeFromIterable(Iterable<? extends T> iterable) {
        if (iterable == null) {
            throw new NullPointerException("iterable must not be null");
        }
        this.is = iterable;
    }

    public final void call(Subscriber<? super T> subscriber) {
        try {
            Iterator it = this.is.iterator();
            boolean hasNext = it.hasNext();
            if (!subscriber.isUnsubscribed()) {
                if (hasNext) {
                    subscriber.setProducer(new IterableProducer(subscriber, it));
                } else {
                    subscriber.onCompleted();
                }
            }
        } catch (Throwable th) {
            Exceptions.throwOrReport(th, (Observer) subscriber);
        }
    }
}
