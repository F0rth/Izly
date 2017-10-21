package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import rx.Observable$OnSubscribe;
import rx.Producer;
import rx.Subscriber;

public final class OnSubscribeRange implements Observable$OnSubscribe<Integer> {
    private final int endIndex;
    private final int startIndex;

    static final class RangeProducer extends AtomicLong implements Producer {
        private static final long serialVersionUID = 4114392207069098388L;
        private final Subscriber<? super Integer> childSubscriber;
        private long currentIndex;
        private final int endOfRange;

        RangeProducer(Subscriber<? super Integer> subscriber, int i, int i2) {
            this.childSubscriber = subscriber;
            this.currentIndex = (long) i;
            this.endOfRange = i2;
        }

        final void fastpath() {
            long j = (long) this.endOfRange;
            Subscriber subscriber = this.childSubscriber;
            long j2 = this.currentIndex;
            while (j2 != j + 1) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(Integer.valueOf((int) j2));
                    j2++;
                } else {
                    return;
                }
            }
            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
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
            long j2 = ((long) this.endOfRange) + 1;
            long j3 = this.currentIndex;
            Subscriber subscriber = this.childSubscriber;
            long j4 = 0;
            while (true) {
                if (j4 == j || j3 == j2) {
                    if (!subscriber.isUnsubscribed()) {
                        if (j3 == j2) {
                            subscriber.onCompleted();
                            return;
                        }
                        j = get();
                        if (j == j4) {
                            this.currentIndex = j3;
                            j = addAndGet(-j4);
                            if (j != 0) {
                                j4 = 0;
                            } else {
                                return;
                            }
                        }
                        continue;
                    } else {
                        return;
                    }
                } else if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(Integer.valueOf((int) j3));
                    j3++;
                    j4++;
                } else {
                    return;
                }
            }
        }
    }

    public OnSubscribeRange(int i, int i2) {
        this.startIndex = i;
        this.endIndex = i2;
    }

    public final void call(Subscriber<? super Integer> subscriber) {
        subscriber.setProducer(new RangeProducer(subscriber, this.startIndex, this.endIndex));
    }
}
