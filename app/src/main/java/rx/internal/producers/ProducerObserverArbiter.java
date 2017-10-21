package rx.internal.producers;

import java.util.List;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.internal.operators.BackpressureUtils;

public final class ProducerObserverArbiter<T> implements Observer<T>, Producer {
    static final Producer NULL_PRODUCER = new Producer() {
        public final void request(long j) {
        }
    };
    final Subscriber<? super T> child;
    Producer currentProducer;
    boolean emitting;
    volatile boolean hasError;
    Producer missedProducer;
    long missedRequested;
    Object missedTerminal;
    List<T> queue;
    long requested;

    public ProducerObserverArbiter(Subscriber<? super T> subscriber) {
        this.child = subscriber;
    }

    final void emitLoop() {
        Subscriber subscriber = this.child;
        Producer producer = null;
        long j = 0;
        while (true) {
            Object obj = null;
            synchronized (this) {
                long j2 = this.missedRequested;
                Producer producer2 = this.missedProducer;
                Boolean bool = this.missedTerminal;
                List list = this.queue;
                if (j2 == 0 && producer2 == null && list == null && bool == null) {
                    this.emitting = false;
                    obj = 1;
                } else {
                    this.missedRequested = 0;
                    this.missedProducer = null;
                    this.queue = null;
                    this.missedTerminal = null;
                }
            }
            if (obj != null) {
                break;
            }
            Producer producer3;
            long addCap;
            obj = (list == null || list.isEmpty()) ? 1 : null;
            if (bool != null) {
                if (bool != Boolean.TRUE) {
                    subscriber.onError((Throwable) bool);
                    return;
                } else if (obj != null) {
                    subscriber.onCompleted();
                    return;
                }
            }
            long j3 = 0;
            if (list != null) {
                for (Object obj2 : list) {
                    if (!subscriber.isUnsubscribed()) {
                        if (this.hasError) {
                            continue;
                            break;
                        }
                        try {
                            subscriber.onNext(obj2);
                        } catch (Throwable th) {
                            Exceptions.throwOrReport(th, subscriber, obj2);
                            return;
                        }
                    }
                    return;
                }
                j3 = 0 + ((long) list.size());
            }
            long j4 = this.requested;
            if (j4 != Long.MAX_VALUE) {
                if (j2 != 0) {
                    j4 += j2;
                    if (j4 < 0) {
                        j4 = Long.MAX_VALUE;
                    }
                }
                if (j3 == 0 || j4 == Long.MAX_VALUE) {
                    j3 = j4;
                } else {
                    j3 = j4 - j3;
                    if (j3 < 0) {
                        throw new IllegalStateException("More produced than requested");
                    }
                }
                this.requested = j3;
            } else {
                j3 = j4;
            }
            if (producer2 == null) {
                producer3 = this.currentProducer;
                if (!(producer3 == null || j2 == 0)) {
                    addCap = BackpressureUtils.addCap(j, j2);
                }
                producer3 = producer;
                addCap = j;
            } else if (producer2 == NULL_PRODUCER) {
                this.currentProducer = null;
            } else {
                this.currentProducer = producer2;
                if (j3 != 0) {
                    j = BackpressureUtils.addCap(j, j3);
                    producer = producer2;
                }
                producer3 = producer;
                addCap = j;
            }
            producer = producer3;
            j = addCap;
        }
        if (j != 0 && producer != null) {
            producer.request(j);
        }
    }

    public final void onCompleted() {
        synchronized (this) {
            if (this.emitting) {
                this.missedTerminal = Boolean.valueOf(true);
                return;
            }
            this.emitting = true;
            this.child.onCompleted();
        }
    }

    public final void onError(Throwable th) {
        boolean z;
        synchronized (this) {
            if (this.emitting) {
                this.missedTerminal = th;
                z = false;
            } else {
                this.emitting = true;
                z = true;
            }
        }
        if (z) {
            this.child.onError(th);
        } else {
            this.hasError = true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onNext(T r5) {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.emitting;	 Catch:{ all -> 0x0037 }
        if (r0 == 0) goto L_0x0016;
    L_0x0005:
        r0 = r4.queue;	 Catch:{ all -> 0x0037 }
        if (r0 != 0) goto L_0x0011;
    L_0x0009:
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0037 }
        r1 = 4;
        r0.<init>(r1);	 Catch:{ all -> 0x0037 }
        r4.queue = r0;	 Catch:{ all -> 0x0037 }
    L_0x0011:
        r0.add(r5);	 Catch:{ all -> 0x0037 }
        monitor-exit(r4);	 Catch:{ all -> 0x0037 }
    L_0x0015:
        return;
    L_0x0016:
        monitor-exit(r4);	 Catch:{ all -> 0x0037 }
        r0 = r4.child;	 Catch:{ all -> 0x0030 }
        r0.onNext(r5);	 Catch:{ all -> 0x0030 }
        r0 = r4.requested;	 Catch:{ all -> 0x0030 }
        r2 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x002c;
    L_0x0027:
        r2 = 1;
        r0 = r0 - r2;
        r4.requested = r0;	 Catch:{ all -> 0x0030 }
    L_0x002c:
        r4.emitLoop();	 Catch:{ all -> 0x0030 }
        goto L_0x0015;
    L_0x0030:
        r0 = move-exception;
        monitor-enter(r4);
        r1 = 0;
        r4.emitting = r1;	 Catch:{ all -> 0x003a }
        monitor-exit(r4);	 Catch:{ all -> 0x003a }
        throw r0;
    L_0x0037:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0037 }
        throw r0;
    L_0x003a:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x003a }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.producers.ProducerObserverArbiter.onNext(java.lang.Object):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void request(long r8) {
        /*
        r7 = this;
        r4 = 0;
        r0 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1));
        if (r0 >= 0) goto L_0x000e;
    L_0x0006:
        r0 = new java.lang.IllegalArgumentException;
        r1 = "n >= 0 required";
        r0.<init>(r1);
        throw r0;
    L_0x000e:
        r0 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1));
        if (r0 != 0) goto L_0x0013;
    L_0x0012:
        return;
    L_0x0013:
        monitor-enter(r7);
        r0 = r7.emitting;	 Catch:{ all -> 0x001f }
        if (r0 == 0) goto L_0x0022;
    L_0x0018:
        r0 = r7.missedRequested;	 Catch:{ all -> 0x001f }
        r0 = r0 + r8;
        r7.missedRequested = r0;	 Catch:{ all -> 0x001f }
        monitor-exit(r7);	 Catch:{ all -> 0x001f }
        goto L_0x0012;
    L_0x001f:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x001f }
        throw r0;
    L_0x0022:
        r0 = 1;
        r7.emitting = r0;	 Catch:{ all -> 0x001f }
        monitor-exit(r7);	 Catch:{ all -> 0x001f }
        r2 = r7.currentProducer;
        r0 = r7.requested;	 Catch:{ all -> 0x003f }
        r0 = r0 + r8;
        r3 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
        if (r3 >= 0) goto L_0x0034;
    L_0x002f:
        r0 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
    L_0x0034:
        r7.requested = r0;	 Catch:{ all -> 0x003f }
        r7.emitLoop();	 Catch:{ all -> 0x003f }
        if (r2 == 0) goto L_0x0012;
    L_0x003b:
        r2.request(r8);
        goto L_0x0012;
    L_0x003f:
        r0 = move-exception;
        monitor-enter(r7);
        r1 = 0;
        r7.emitting = r1;	 Catch:{ all -> 0x0046 }
        monitor-exit(r7);	 Catch:{ all -> 0x0046 }
        throw r0;
    L_0x0046:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x0046 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.producers.ProducerObserverArbiter.request(long):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setProducer(rx.Producer r5) {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.emitting;	 Catch:{ all -> 0x0025 }
        if (r0 == 0) goto L_0x000e;
    L_0x0005:
        if (r5 == 0) goto L_0x000b;
    L_0x0007:
        r4.missedProducer = r5;	 Catch:{ all -> 0x0025 }
        monitor-exit(r4);	 Catch:{ all -> 0x0025 }
    L_0x000a:
        return;
    L_0x000b:
        r5 = NULL_PRODUCER;	 Catch:{ all -> 0x0025 }
        goto L_0x0007;
    L_0x000e:
        r0 = 1;
        r4.emitting = r0;	 Catch:{ all -> 0x0025 }
        monitor-exit(r4);	 Catch:{ all -> 0x0025 }
        r4.currentProducer = r5;
        r0 = r4.requested;
        r4.emitLoop();	 Catch:{ all -> 0x0028 }
        if (r5 == 0) goto L_0x000a;
    L_0x001b:
        r2 = 0;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x000a;
    L_0x0021:
        r5.request(r0);
        goto L_0x000a;
    L_0x0025:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0025 }
        throw r0;
    L_0x0028:
        r0 = move-exception;
        monitor-enter(r4);
        r1 = 0;
        r4.emitting = r1;	 Catch:{ all -> 0x002f }
        monitor-exit(r4);	 Catch:{ all -> 0x002f }
        throw r0;
    L_0x002f:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x002f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.producers.ProducerObserverArbiter.setProducer(rx.Producer):void");
    }
}
