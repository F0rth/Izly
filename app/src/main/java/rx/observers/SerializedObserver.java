package rx.observers;

import rx.Observer;
import rx.exceptions.Exceptions;
import rx.internal.operators.NotificationLite;

public class SerializedObserver<T> implements Observer<T> {
    private static final int MAX_DRAIN_ITERATION = 1024;
    private final Observer<? super T> actual;
    private boolean emitting;
    private final NotificationLite<T> nl = NotificationLite.instance();
    private FastList queue;
    private volatile boolean terminated;

    static final class FastList {
        Object[] array;
        int size;

        FastList() {
        }

        public final void add(Object obj) {
            Object[] objArr;
            int i = this.size;
            Object obj2 = this.array;
            if (obj2 == null) {
                objArr = new Object[16];
                this.array = objArr;
            } else if (i == obj2.length) {
                objArr = new Object[((i >> 2) + i)];
                System.arraycopy(obj2, 0, objArr, 0, i);
                this.array = objArr;
            } else {
                Object obj3 = obj2;
            }
            objArr[i] = obj;
            this.size = i + 1;
        }
    }

    public SerializedObserver(Observer<? super T> observer) {
        this.actual = observer;
    }

    public void onCompleted() {
        if (!this.terminated) {
            synchronized (this) {
                if (this.terminated) {
                    return;
                }
                this.terminated = true;
                if (this.emitting) {
                    FastList fastList = this.queue;
                    if (fastList == null) {
                        fastList = new FastList();
                        this.queue = fastList;
                    }
                    fastList.add(this.nl.completed());
                    return;
                }
                this.emitting = true;
                this.actual.onCompleted();
            }
        }
    }

    public void onError(Throwable th) {
        Exceptions.throwIfFatal(th);
        if (!this.terminated) {
            synchronized (this) {
                if (this.terminated) {
                    return;
                }
                this.terminated = true;
                if (this.emitting) {
                    FastList fastList = this.queue;
                    if (fastList == null) {
                        fastList = new FastList();
                        this.queue = fastList;
                    }
                    fastList.add(this.nl.error(th));
                    return;
                }
                this.emitting = true;
                this.actual.onError(th);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onNext(T r10) {
        /*
        r9 = this;
        r1 = 0;
        r8 = 1;
        r0 = r9.terminated;
        if (r0 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        monitor-enter(r9);
        r0 = r9.terminated;	 Catch:{ all -> 0x000e }
        if (r0 == 0) goto L_0x0011;
    L_0x000c:
        monitor-exit(r9);	 Catch:{ all -> 0x000e }
        goto L_0x0006;
    L_0x000e:
        r0 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x000e }
        throw r0;
    L_0x0011:
        r0 = r9.emitting;	 Catch:{ all -> 0x000e }
        if (r0 == 0) goto L_0x002b;
    L_0x0015:
        r0 = r9.queue;	 Catch:{ all -> 0x000e }
        if (r0 != 0) goto L_0x0020;
    L_0x0019:
        r0 = new rx.observers.SerializedObserver$FastList;	 Catch:{ all -> 0x000e }
        r0.<init>();	 Catch:{ all -> 0x000e }
        r9.queue = r0;	 Catch:{ all -> 0x000e }
    L_0x0020:
        r1 = r9.nl;	 Catch:{ all -> 0x000e }
        r1 = r1.next(r10);	 Catch:{ all -> 0x000e }
        r0.add(r1);	 Catch:{ all -> 0x000e }
        monitor-exit(r9);	 Catch:{ all -> 0x000e }
        goto L_0x0006;
    L_0x002b:
        r0 = 1;
        r9.emitting = r0;	 Catch:{ all -> 0x000e }
        monitor-exit(r9);	 Catch:{ all -> 0x000e }
        r0 = r9.actual;	 Catch:{ Throwable -> 0x0046 }
        r0.onNext(r10);	 Catch:{ Throwable -> 0x0046 }
    L_0x0034:
        r2 = r1;
    L_0x0035:
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        if (r2 >= r0) goto L_0x0034;
    L_0x0039:
        monitor-enter(r9);
        r0 = r9.queue;	 Catch:{ all -> 0x0043 }
        if (r0 != 0) goto L_0x004f;
    L_0x003e:
        r0 = 0;
        r9.emitting = r0;	 Catch:{ all -> 0x0043 }
        monitor-exit(r9);	 Catch:{ all -> 0x0043 }
        goto L_0x0006;
    L_0x0043:
        r0 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x0043 }
        throw r0;
    L_0x0046:
        r0 = move-exception;
        r9.terminated = r8;
        r1 = r9.actual;
        rx.exceptions.Exceptions.throwOrReport(r0, r1, r10);
        goto L_0x0006;
    L_0x004f:
        r3 = 0;
        r9.queue = r3;	 Catch:{ all -> 0x0043 }
        monitor-exit(r9);	 Catch:{ all -> 0x0043 }
        r3 = r0.array;
        r4 = r3.length;
        r0 = r1;
    L_0x0057:
        if (r0 >= r4) goto L_0x007e;
    L_0x0059:
        r5 = r3[r0];
        if (r5 == 0) goto L_0x007e;
    L_0x005d:
        r6 = r9.nl;	 Catch:{ Throwable -> 0x006b }
        r7 = r9.actual;	 Catch:{ Throwable -> 0x006b }
        r5 = r6.accept(r7, r5);	 Catch:{ Throwable -> 0x006b }
        if (r5 == 0) goto L_0x007b;
    L_0x0067:
        r0 = 1;
        r9.terminated = r0;	 Catch:{ Throwable -> 0x006b }
        goto L_0x0006;
    L_0x006b:
        r0 = move-exception;
        r9.terminated = r8;
        rx.exceptions.Exceptions.throwIfFatal(r0);
        r1 = r9.actual;
        r0 = rx.exceptions.OnErrorThrowable.addValueAsLastCause(r0, r10);
        r1.onError(r0);
        goto L_0x0006;
    L_0x007b:
        r0 = r0 + 1;
        goto L_0x0057;
    L_0x007e:
        r0 = r2 + 1;
        r2 = r0;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.observers.SerializedObserver.onNext(java.lang.Object):void");
    }
}
