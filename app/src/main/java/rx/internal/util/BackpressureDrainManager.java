package rx.internal.util;

import java.util.concurrent.atomic.AtomicLong;
import rx.Producer;
import rx.annotations.Experimental;

@Experimental
public final class BackpressureDrainManager extends AtomicLong implements Producer {
    protected final BackpressureQueueCallback actual;
    protected boolean emitting;
    protected Throwable exception;
    protected volatile boolean terminated;

    public interface BackpressureQueueCallback {
        boolean accept(Object obj);

        void complete(Throwable th);

        Object peek();

        Object poll();
    }

    public BackpressureDrainManager(BackpressureQueueCallback backpressureQueueCallback) {
        this.actual = backpressureQueueCallback;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void drain() {
        /*
        r14 = this;
        r6 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r12 = 0;
        r3 = 1;
        r4 = 0;
        monitor-enter(r14);
        r0 = r14.emitting;	 Catch:{ all -> 0x003b }
        if (r0 == 0) goto L_0x0010;
    L_0x000e:
        monitor-exit(r14);	 Catch:{ all -> 0x003b }
    L_0x000f:
        return;
    L_0x0010:
        r0 = 1;
        r14.emitting = r0;	 Catch:{ all -> 0x003b }
        r2 = r14.terminated;	 Catch:{ all -> 0x003b }
        monitor-exit(r14);	 Catch:{ all -> 0x003b }
        r0 = r14.get();
        r10 = r14.actual;	 Catch:{ all -> 0x0073 }
    L_0x001c:
        r5 = r4;
    L_0x001d:
        r8 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1));
        if (r8 > 0) goto L_0x0023;
    L_0x0021:
        if (r2 == 0) goto L_0x0054;
    L_0x0023:
        if (r2 == 0) goto L_0x0042;
    L_0x0025:
        r8 = r10.peek();	 Catch:{ all -> 0x0073 }
        if (r8 != 0) goto L_0x003e;
    L_0x002b:
        r0 = r14.exception;	 Catch:{ all -> 0x0031 }
        r10.complete(r0);	 Catch:{ all -> 0x0031 }
        goto L_0x000f;
    L_0x0031:
        r0 = move-exception;
        r4 = r3;
    L_0x0033:
        if (r4 != 0) goto L_0x003a;
    L_0x0035:
        monitor-enter(r14);
        r1 = 0;
        r14.emitting = r1;	 Catch:{ all -> 0x0092 }
        monitor-exit(r14);	 Catch:{ all -> 0x0092 }
    L_0x003a:
        throw r0;
    L_0x003b:
        r0 = move-exception;
        monitor-exit(r14);	 Catch:{ all -> 0x003b }
        throw r0;
    L_0x003e:
        r8 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1));
        if (r8 == 0) goto L_0x0054;
    L_0x0042:
        r8 = r10.poll();	 Catch:{ all -> 0x0073 }
        if (r8 == 0) goto L_0x0054;
    L_0x0048:
        r8 = r10.accept(r8);	 Catch:{ all -> 0x0073 }
        if (r8 != 0) goto L_0x000f;
    L_0x004e:
        r8 = 1;
        r0 = r0 - r8;
        r5 = r5 + 1;
        goto L_0x001d;
    L_0x0054:
        monitor-enter(r14);	 Catch:{ all -> 0x0073 }
        r2 = r14.terminated;	 Catch:{ all -> 0x007a }
        r0 = r10.peek();	 Catch:{ all -> 0x007a }
        if (r0 == 0) goto L_0x0075;
    L_0x005d:
        r0 = r3;
    L_0x005e:
        r8 = r14.get();	 Catch:{ all -> 0x007a }
        r1 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r1 != 0) goto L_0x007c;
    L_0x0066:
        if (r0 != 0) goto L_0x0077;
    L_0x0068:
        if (r2 != 0) goto L_0x0077;
    L_0x006a:
        r0 = 0;
        r14.emitting = r0;	 Catch:{ all -> 0x006f }
        monitor-exit(r14);	 Catch:{ all -> 0x006f }
        goto L_0x000f;
    L_0x006f:
        r0 = move-exception;
        r4 = r3;
    L_0x0071:
        monitor-exit(r14);	 Catch:{ all -> 0x007a }
        throw r0;	 Catch:{ all -> 0x0073 }
    L_0x0073:
        r0 = move-exception;
        goto L_0x0033;
    L_0x0075:
        r0 = r4;
        goto L_0x005e;
    L_0x0077:
        r0 = r6;
    L_0x0078:
        monitor-exit(r14);	 Catch:{ all -> 0x007a }
        goto L_0x001c;
    L_0x007a:
        r0 = move-exception;
        goto L_0x0071;
    L_0x007c:
        r1 = -r5;
        r8 = (long) r1;	 Catch:{ all -> 0x007a }
        r8 = r14.addAndGet(r8);	 Catch:{ all -> 0x007a }
        r1 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1));
        if (r1 == 0) goto L_0x0088;
    L_0x0086:
        if (r0 != 0) goto L_0x0095;
    L_0x0088:
        if (r2 == 0) goto L_0x008c;
    L_0x008a:
        if (r0 == 0) goto L_0x0095;
    L_0x008c:
        r0 = 0;
        r14.emitting = r0;	 Catch:{ all -> 0x006f }
        monitor-exit(r14);	 Catch:{ all -> 0x006f }
        goto L_0x000f;
    L_0x0092:
        r0 = move-exception;
        monitor-exit(r14);	 Catch:{ all -> 0x0092 }
        throw r0;
    L_0x0095:
        r0 = r8;
        goto L_0x0078;
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.util.BackpressureDrainManager.drain():void");
    }

    public final boolean isTerminated() {
        return this.terminated;
    }

    public final void request(long j) {
        if (j != 0) {
            Object obj;
            long j2;
            long j3;
            do {
                j2 = get();
                obj = j2 == 0 ? 1 : null;
                if (j2 == Long.MAX_VALUE) {
                    break;
                } else if (j == Long.MAX_VALUE) {
                    j3 = j;
                    obj = 1;
                } else {
                    j3 = j2 > Long.MAX_VALUE - j ? Long.MAX_VALUE : j2 + j;
                }
            } while (!compareAndSet(j2, j3));
            if (obj != null) {
                drain();
            }
        }
    }

    public final void terminate() {
        this.terminated = true;
    }

    public final void terminate(Throwable th) {
        if (!this.terminated) {
            this.exception = th;
            this.terminated = true;
        }
    }

    public final void terminateAndDrain() {
        this.terminated = true;
        drain();
    }

    public final void terminateAndDrain(Throwable th) {
        if (!this.terminated) {
            this.exception = th;
            this.terminated = true;
            drain();
        }
    }
}
