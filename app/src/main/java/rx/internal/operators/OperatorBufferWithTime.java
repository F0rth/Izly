package rx.internal.operators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable$Operator;
import rx.Observer;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.observers.SerializedSubscriber;

public final class OperatorBufferWithTime<T> implements Observable$Operator<List<T>, T> {
    final int count;
    final Scheduler scheduler;
    final long timeshift;
    final long timespan;
    final TimeUnit unit;

    final class ExactSubscriber extends Subscriber<T> {
        final Subscriber<? super List<T>> child;
        List<T> chunk = new ArrayList();
        boolean done;
        final Worker inner;

        public ExactSubscriber(Subscriber<? super List<T>> subscriber, Worker worker) {
            this.child = subscriber;
            this.inner = worker;
        }

        final void emit() {
            synchronized (this) {
                if (this.done) {
                    return;
                }
                List list = this.chunk;
                this.chunk = new ArrayList();
                try {
                    this.child.onNext(list);
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, (Observer) this);
                }
            }
        }

        public final void onCompleted() {
            try {
                this.inner.unsubscribe();
                synchronized (this) {
                    if (this.done) {
                        return;
                    }
                    this.done = true;
                    List list = this.chunk;
                    this.chunk = null;
                    this.child.onNext(list);
                    this.child.onCompleted();
                    unsubscribe();
                }
            } catch (Throwable th) {
                Exceptions.throwOrReport(th, this.child);
            }
        }

        public final void onError(Throwable th) {
            synchronized (this) {
                if (this.done) {
                    return;
                }
                this.done = true;
                this.chunk = null;
                this.child.onError(th);
                unsubscribe();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onNext(T r4) {
            /*
            r3 = this;
            r0 = 0;
            monitor-enter(r3);
            r1 = r3.done;	 Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x0008;
        L_0x0006:
            monitor-exit(r3);	 Catch:{ all -> 0x002b }
        L_0x0007:
            return;
        L_0x0008:
            r1 = r3.chunk;	 Catch:{ all -> 0x002b }
            r1.add(r4);	 Catch:{ all -> 0x002b }
            r1 = r3.chunk;	 Catch:{ all -> 0x002b }
            r1 = r1.size();	 Catch:{ all -> 0x002b }
            r2 = rx.internal.operators.OperatorBufferWithTime.this;	 Catch:{ all -> 0x002b }
            r2 = r2.count;	 Catch:{ all -> 0x002b }
            if (r1 != r2) goto L_0x0022;
        L_0x0019:
            r0 = r3.chunk;	 Catch:{ all -> 0x002b }
            r1 = new java.util.ArrayList;	 Catch:{ all -> 0x002b }
            r1.<init>();	 Catch:{ all -> 0x002b }
            r3.chunk = r1;	 Catch:{ all -> 0x002b }
        L_0x0022:
            monitor-exit(r3);	 Catch:{ all -> 0x002b }
            if (r0 == 0) goto L_0x0007;
        L_0x0025:
            r1 = r3.child;
            r1.onNext(r0);
            goto L_0x0007;
        L_0x002b:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x002b }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorBufferWithTime.ExactSubscriber.onNext(java.lang.Object):void");
        }

        final void scheduleExact() {
            this.inner.schedulePeriodically(new Action0() {
                public void call() {
                    ExactSubscriber.this.emit();
                }
            }, OperatorBufferWithTime.this.timespan, OperatorBufferWithTime.this.timespan, OperatorBufferWithTime.this.unit);
        }
    }

    final class InexactSubscriber extends Subscriber<T> {
        final Subscriber<? super List<T>> child;
        final List<List<T>> chunks = new LinkedList();
        boolean done;
        final Worker inner;

        public InexactSubscriber(Subscriber<? super List<T>> subscriber, Worker worker) {
            this.child = subscriber;
            this.inner = worker;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void emitChunk(java.util.List<T> r3) {
            /*
            r2 = this;
            monitor-enter(r2);
            r0 = r2.done;	 Catch:{ all -> 0x002d }
            if (r0 == 0) goto L_0x0007;
        L_0x0005:
            monitor-exit(r2);	 Catch:{ all -> 0x002d }
        L_0x0006:
            return;
        L_0x0007:
            r0 = r2.chunks;	 Catch:{ all -> 0x002d }
            r1 = r0.iterator();	 Catch:{ all -> 0x002d }
        L_0x000d:
            r0 = r1.hasNext();	 Catch:{ all -> 0x002d }
            if (r0 == 0) goto L_0x0030;
        L_0x0013:
            r0 = r1.next();	 Catch:{ all -> 0x002d }
            r0 = (java.util.List) r0;	 Catch:{ all -> 0x002d }
            if (r0 != r3) goto L_0x000d;
        L_0x001b:
            r1.remove();	 Catch:{ all -> 0x002d }
            r0 = 1;
        L_0x001f:
            monitor-exit(r2);	 Catch:{ all -> 0x002d }
            if (r0 == 0) goto L_0x0006;
        L_0x0022:
            r0 = r2.child;	 Catch:{ Throwable -> 0x0028 }
            r0.onNext(r3);	 Catch:{ Throwable -> 0x0028 }
            goto L_0x0006;
        L_0x0028:
            r0 = move-exception;
            rx.exceptions.Exceptions.throwOrReport(r0, r2);
            goto L_0x0006;
        L_0x002d:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x002d }
            throw r0;
        L_0x0030:
            r0 = 0;
            goto L_0x001f;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorBufferWithTime.InexactSubscriber.emitChunk(java.util.List):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onCompleted() {
            /*
            r3 = this;
            monitor-enter(r3);	 Catch:{ Throwable -> 0x002d }
            r0 = r3.done;	 Catch:{ all -> 0x0034 }
            if (r0 == 0) goto L_0x0007;
        L_0x0005:
            monitor-exit(r3);	 Catch:{ all -> 0x0034 }
        L_0x0006:
            return;
        L_0x0007:
            r0 = 1;
            r3.done = r0;	 Catch:{ all -> 0x0034 }
            r0 = new java.util.LinkedList;	 Catch:{ all -> 0x0034 }
            r1 = r3.chunks;	 Catch:{ all -> 0x0034 }
            r0.<init>(r1);	 Catch:{ all -> 0x0034 }
            r1 = r3.chunks;	 Catch:{ all -> 0x0034 }
            r1.clear();	 Catch:{ all -> 0x0034 }
            monitor-exit(r3);	 Catch:{ all -> 0x0034 }
            r1 = r0.iterator();	 Catch:{ Throwable -> 0x002d }
        L_0x001b:
            r0 = r1.hasNext();	 Catch:{ Throwable -> 0x002d }
            if (r0 == 0) goto L_0x0037;
        L_0x0021:
            r0 = r1.next();	 Catch:{ Throwable -> 0x002d }
            r0 = (java.util.List) r0;	 Catch:{ Throwable -> 0x002d }
            r2 = r3.child;	 Catch:{ Throwable -> 0x002d }
            r2.onNext(r0);	 Catch:{ Throwable -> 0x002d }
            goto L_0x001b;
        L_0x002d:
            r0 = move-exception;
            r1 = r3.child;
            rx.exceptions.Exceptions.throwOrReport(r0, r1);
            goto L_0x0006;
        L_0x0034:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0034 }
            throw r0;	 Catch:{ Throwable -> 0x002d }
        L_0x0037:
            r0 = r3.child;
            r0.onCompleted();
            r3.unsubscribe();
            goto L_0x0006;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorBufferWithTime.InexactSubscriber.onCompleted():void");
        }

        public final void onError(Throwable th) {
            synchronized (this) {
                if (this.done) {
                    return;
                }
                this.done = true;
                this.chunks.clear();
                this.child.onError(th);
                unsubscribe();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onNext(T r6) {
            /*
            r5 = this;
            r1 = 0;
            monitor-enter(r5);
            r0 = r5.done;	 Catch:{ all -> 0x0050 }
            if (r0 == 0) goto L_0x0008;
        L_0x0006:
            monitor-exit(r5);	 Catch:{ all -> 0x0050 }
        L_0x0007:
            return;
        L_0x0008:
            r0 = r5.chunks;	 Catch:{ all -> 0x0050 }
            r2 = r0.iterator();	 Catch:{ all -> 0x0050 }
        L_0x000e:
            r0 = r2.hasNext();	 Catch:{ all -> 0x0050 }
            if (r0 == 0) goto L_0x0037;
        L_0x0014:
            r0 = r2.next();	 Catch:{ all -> 0x0050 }
            r0 = (java.util.List) r0;	 Catch:{ all -> 0x0050 }
            r0.add(r6);	 Catch:{ all -> 0x0050 }
            r3 = r0.size();	 Catch:{ all -> 0x0050 }
            r4 = rx.internal.operators.OperatorBufferWithTime.this;	 Catch:{ all -> 0x0050 }
            r4 = r4.count;	 Catch:{ all -> 0x0050 }
            if (r3 != r4) goto L_0x0053;
        L_0x0027:
            r2.remove();	 Catch:{ all -> 0x0050 }
            if (r1 != 0) goto L_0x0031;
        L_0x002c:
            r1 = new java.util.LinkedList;	 Catch:{ all -> 0x0050 }
            r1.<init>();	 Catch:{ all -> 0x0050 }
        L_0x0031:
            r1.add(r0);	 Catch:{ all -> 0x0050 }
            r0 = r1;
        L_0x0035:
            r1 = r0;
            goto L_0x000e;
        L_0x0037:
            monitor-exit(r5);	 Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x0007;
        L_0x003a:
            r1 = r1.iterator();
        L_0x003e:
            r0 = r1.hasNext();
            if (r0 == 0) goto L_0x0007;
        L_0x0044:
            r0 = r1.next();
            r0 = (java.util.List) r0;
            r2 = r5.child;
            r2.onNext(r0);
            goto L_0x003e;
        L_0x0050:
            r0 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x0050 }
            throw r0;
        L_0x0053:
            r0 = r1;
            goto L_0x0035;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorBufferWithTime.InexactSubscriber.onNext(java.lang.Object):void");
        }

        final void scheduleChunk() {
            this.inner.schedulePeriodically(new Action0() {
                public void call() {
                    InexactSubscriber.this.startNewChunk();
                }
            }, OperatorBufferWithTime.this.timeshift, OperatorBufferWithTime.this.timeshift, OperatorBufferWithTime.this.unit);
        }

        final void startNewChunk() {
            final List arrayList = new ArrayList();
            synchronized (this) {
                if (this.done) {
                    return;
                }
                this.chunks.add(arrayList);
                this.inner.schedule(new Action0() {
                    public void call() {
                        InexactSubscriber.this.emitChunk(arrayList);
                    }
                }, OperatorBufferWithTime.this.timespan, OperatorBufferWithTime.this.unit);
            }
        }
    }

    public OperatorBufferWithTime(long j, long j2, TimeUnit timeUnit, int i, Scheduler scheduler) {
        this.timespan = j;
        this.timeshift = j2;
        this.unit = timeUnit;
        this.count = i;
        this.scheduler = scheduler;
    }

    public final Subscriber<? super T> call(Subscriber<? super List<T>> subscriber) {
        Worker createWorker = this.scheduler.createWorker();
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        if (this.timespan == this.timeshift) {
            ExactSubscriber exactSubscriber = new ExactSubscriber(serializedSubscriber, createWorker);
            exactSubscriber.add(createWorker);
            subscriber.add(exactSubscriber);
            exactSubscriber.scheduleExact();
            return exactSubscriber;
        }
        InexactSubscriber inexactSubscriber = new InexactSubscriber(serializedSubscriber, createWorker);
        inexactSubscriber.add(createWorker);
        subscriber.add(inexactSubscriber);
        inexactSubscriber.startNewChunk();
        inexactSubscriber.scheduleChunk();
        return inexactSubscriber;
    }
}
