package rx.internal.operators;

import java.util.concurrent.TimeUnit;
import rx.Observable$Operator;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.functions.Action0;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.SerialSubscription;

public final class OperatorDebounceWithTime<T> implements Observable$Operator<T, T> {
    final Scheduler scheduler;
    final long timeout;
    final TimeUnit unit;

    static final class DebounceState<T> {
        boolean emitting;
        boolean hasValue;
        int index;
        boolean terminate;
        T value;

        DebounceState() {
        }

        public final void clear() {
            synchronized (this) {
                this.index++;
                this.value = null;
                this.hasValue = false;
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void emit(int r3, rx.Subscriber<T> r4, rx.Subscriber<?> r5) {
            /*
            r2 = this;
            monitor-enter(r2);
            r0 = r2.emitting;	 Catch:{ all -> 0x002b }
            if (r0 != 0) goto L_0x000d;
        L_0x0005:
            r0 = r2.hasValue;	 Catch:{ all -> 0x002b }
            if (r0 == 0) goto L_0x000d;
        L_0x0009:
            r0 = r2.index;	 Catch:{ all -> 0x002b }
            if (r3 == r0) goto L_0x000f;
        L_0x000d:
            monitor-exit(r2);	 Catch:{ all -> 0x002b }
        L_0x000e:
            return;
        L_0x000f:
            r0 = r2.value;	 Catch:{ all -> 0x002b }
            r1 = 0;
            r2.value = r1;	 Catch:{ all -> 0x002b }
            r1 = 0;
            r2.hasValue = r1;	 Catch:{ all -> 0x002b }
            r1 = 1;
            r2.emitting = r1;	 Catch:{ all -> 0x002b }
            monitor-exit(r2);	 Catch:{ all -> 0x002b }
            r4.onNext(r0);	 Catch:{ Throwable -> 0x002e }
            monitor-enter(r2);
            r0 = r2.terminate;	 Catch:{ all -> 0x0028 }
            if (r0 != 0) goto L_0x0033;
        L_0x0023:
            r0 = 0;
            r2.emitting = r0;	 Catch:{ all -> 0x0028 }
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
            goto L_0x000e;
        L_0x0028:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
            throw r0;
        L_0x002b:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x002b }
            throw r0;
        L_0x002e:
            r1 = move-exception;
            rx.exceptions.Exceptions.throwOrReport(r1, r5, r0);
            goto L_0x000e;
        L_0x0033:
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
            r4.onCompleted();
            goto L_0x000e;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorDebounceWithTime.DebounceState.emit(int, rx.Subscriber, rx.Subscriber):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void emitAndComplete(rx.Subscriber<T> r4, rx.Subscriber<?> r5) {
            /*
            r3 = this;
            monitor-enter(r3);
            r0 = r3.emitting;	 Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x000a;
        L_0x0005:
            r0 = 1;
            r3.terminate = r0;	 Catch:{ all -> 0x0021 }
            monitor-exit(r3);	 Catch:{ all -> 0x0021 }
        L_0x0009:
            return;
        L_0x000a:
            r0 = r3.value;	 Catch:{ all -> 0x0021 }
            r1 = r3.hasValue;	 Catch:{ all -> 0x0021 }
            r2 = 0;
            r3.value = r2;	 Catch:{ all -> 0x0021 }
            r2 = 0;
            r3.hasValue = r2;	 Catch:{ all -> 0x0021 }
            r2 = 1;
            r3.emitting = r2;	 Catch:{ all -> 0x0021 }
            monitor-exit(r3);	 Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x001d;
        L_0x001a:
            r4.onNext(r0);	 Catch:{ Throwable -> 0x0024 }
        L_0x001d:
            r4.onCompleted();
            goto L_0x0009;
        L_0x0021:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0021 }
            throw r0;
        L_0x0024:
            r1 = move-exception;
            rx.exceptions.Exceptions.throwOrReport(r1, r5, r0);
            goto L_0x0009;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorDebounceWithTime.DebounceState.emitAndComplete(rx.Subscriber, rx.Subscriber):void");
        }

        public final int next(T t) {
            int i;
            synchronized (this) {
                this.value = t;
                this.hasValue = true;
                i = this.index + 1;
                this.index = i;
            }
            return i;
        }
    }

    public OperatorDebounceWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.timeout = j;
        this.unit = timeUnit;
        this.scheduler = scheduler;
    }

    public final Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        final Worker createWorker = this.scheduler.createWorker();
        final SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        final SerialSubscription serialSubscription = new SerialSubscription();
        serializedSubscriber.add(createWorker);
        serializedSubscriber.add(serialSubscription);
        return new Subscriber<T>(subscriber) {
            final Subscriber<?> self = this;
            final DebounceState<T> state = new DebounceState();

            public void onCompleted() {
                this.state.emitAndComplete(serializedSubscriber, this);
            }

            public void onError(Throwable th) {
                serializedSubscriber.onError(th);
                unsubscribe();
                this.state.clear();
            }

            public void onNext(T t) {
                final int next = this.state.next(t);
                serialSubscription.set(createWorker.schedule(new Action0() {
                    public void call() {
                        AnonymousClass1.this.state.emit(next, serializedSubscriber, AnonymousClass1.this.self);
                    }
                }, OperatorDebounceWithTime.this.timeout, OperatorDebounceWithTime.this.unit));
            }

            public void onStart() {
                request(Long.MAX_VALUE);
            }
        };
    }
}
