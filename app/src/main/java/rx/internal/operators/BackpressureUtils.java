package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import rx.Subscriber;
import rx.functions.Func1;
import rx.internal.util.UtilityFunctions;

public final class BackpressureUtils {
    static final long COMPLETED_MASK = Long.MIN_VALUE;
    static final long REQUESTED_MASK = Long.MAX_VALUE;

    private BackpressureUtils() {
        throw new IllegalStateException("No instances!");
    }

    public static long addCap(long j, long j2) {
        long j3 = j + j2;
        return j3 < 0 ? REQUESTED_MASK : j3;
    }

    public static long getAndAddRequest(AtomicLong atomicLong, long j) {
        long j2;
        do {
            j2 = atomicLong.get();
        } while (!atomicLong.compareAndSet(j2, addCap(j2, j)));
        return j2;
    }

    public static <T> long getAndAddRequest(AtomicLongFieldUpdater<T> atomicLongFieldUpdater, T t, long j) {
        long j2;
        do {
            j2 = atomicLongFieldUpdater.get(t);
        } while (!atomicLongFieldUpdater.compareAndSet(t, j2, addCap(j2, j)));
        return j2;
    }

    public static long multiplyCap(long j, long j2) {
        long j3 = j * j2;
        return (((j | j2) >>> 31) == 0 || j2 == 0 || j3 / j2 == j) ? j3 : REQUESTED_MASK;
    }

    public static <T> void postCompleteDone(AtomicLong atomicLong, Queue<T> queue, Subscriber<? super T> subscriber) {
        postCompleteDone(atomicLong, queue, subscriber, UtilityFunctions.identity());
    }

    public static <T, R> void postCompleteDone(AtomicLong atomicLong, Queue<T> queue, Subscriber<? super R> subscriber, Func1<? super T, ? extends R> func1) {
        long j;
        do {
            j = atomicLong.get();
            if ((j & COMPLETED_MASK) != 0) {
                return;
            }
        } while (!atomicLong.compareAndSet(j, j | COMPLETED_MASK));
        if (j != 0) {
            postCompleteDrain(atomicLong, queue, subscriber, func1);
        }
    }

    static <T, R> void postCompleteDrain(AtomicLong atomicLong, Queue<T> queue, Subscriber<? super R> subscriber, Func1<? super T, ? extends R> func1) {
        long j = atomicLong.get();
        if (j == REQUESTED_MASK) {
            while (!subscriber.isUnsubscribed()) {
                Object poll = queue.poll();
                if (poll == null) {
                    subscriber.onCompleted();
                    return;
                }
                subscriber.onNext(func1.call(poll));
            }
            return;
        }
        long j2 = j;
        j = COMPLETED_MASK;
        while (true) {
            if (j == j2) {
                if (j == j2) {
                    if (!subscriber.isUnsubscribed()) {
                        if (queue.isEmpty()) {
                            subscriber.onCompleted();
                            return;
                        }
                    }
                    return;
                }
                j2 = atomicLong.get();
                if (j2 == j) {
                    j = atomicLong.addAndGet(-(j & REQUESTED_MASK));
                    if (j != COMPLETED_MASK) {
                        j2 = j;
                        j = COMPLETED_MASK;
                    } else {
                        return;
                    }
                }
                continue;
            } else if (!subscriber.isUnsubscribed()) {
                Object poll2 = queue.poll();
                if (poll2 == null) {
                    subscriber.onCompleted();
                    return;
                } else {
                    subscriber.onNext(func1.call(poll2));
                    j++;
                }
            } else {
                return;
            }
        }
    }

    public static <T> boolean postCompleteRequest(AtomicLong atomicLong, long j, Queue<T> queue, Subscriber<? super T> subscriber) {
        return postCompleteRequest(atomicLong, j, queue, subscriber, UtilityFunctions.identity());
    }

    public static <T, R> boolean postCompleteRequest(AtomicLong atomicLong, long j, Queue<T> queue, Subscriber<? super R> subscriber, Func1<? super T, ? extends R> func1) {
        if (j < 0) {
            throw new IllegalArgumentException("n >= 0 required but it was " + j);
        } else if (j == 0) {
            return (atomicLong.get() & COMPLETED_MASK) == 0;
        } else {
            long j2;
            long j3;
            do {
                j2 = atomicLong.get();
                j3 = COMPLETED_MASK & j2;
            } while (!atomicLong.compareAndSet(j2, addCap(REQUESTED_MASK & j2, j) | j3));
            if (j2 != COMPLETED_MASK) {
                return j3 == 0;
            } else {
                postCompleteDrain(atomicLong, queue, subscriber, func1);
                return false;
            }
        }
    }

    public static long produced(AtomicLong atomicLong, long j) {
        long j2;
        long j3;
        do {
            j3 = atomicLong.get();
            if (j3 == REQUESTED_MASK) {
                return REQUESTED_MASK;
            }
            j2 = j3 - j;
            if (j2 < 0) {
                throw new IllegalStateException("More produced than requested: " + j2);
            }
        } while (!atomicLong.compareAndSet(j3, j2));
        return j2;
    }
}
