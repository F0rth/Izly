package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import rx.Notification;
import rx.Observable$Operator;
import rx.Producer;
import rx.Subscriber;
import rx.plugins.RxJavaPlugins;

public final class OperatorMaterialize<T> implements Observable$Operator<Notification<T>, T> {

    static final class Holder {
        static final OperatorMaterialize<Object> INSTANCE = new OperatorMaterialize();

        private Holder() {
        }
    }

    static class ParentSubscriber<T> extends Subscriber<T> {
        private boolean busy = false;
        private final Subscriber<? super Notification<T>> child;
        private boolean missed = false;
        private final AtomicLong requested = new AtomicLong();
        private volatile Notification<T> terminalNotification;

        ParentSubscriber(Subscriber<? super Notification<T>> subscriber) {
            this.child = subscriber;
        }

        private void decrementRequested() {
            AtomicLong atomicLong = this.requested;
            long j;
            do {
                j = atomicLong.get();
                if (j == Long.MAX_VALUE) {
                    return;
                }
            } while (!atomicLong.compareAndSet(j, j - 1));
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void drain() {
            /*
            r6 = this;
            monitor-enter(r6);
            r0 = r6.busy;	 Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x000a;
        L_0x0005:
            r0 = 1;
            r6.missed = r0;	 Catch:{ all -> 0x0039 }
            monitor-exit(r6);	 Catch:{ all -> 0x0039 }
        L_0x0009:
            return;
        L_0x000a:
            monitor-exit(r6);	 Catch:{ all -> 0x0039 }
            r0 = r6.requested;
        L_0x000d:
            r1 = r6.child;
            r1 = r1.isUnsubscribed();
            if (r1 != 0) goto L_0x0009;
        L_0x0015:
            r1 = r6.terminalNotification;
            if (r1 == 0) goto L_0x003c;
        L_0x0019:
            r2 = r0.get();
            r4 = 0;
            r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r2 <= 0) goto L_0x003c;
        L_0x0023:
            r0 = 0;
            r6.terminalNotification = r0;
            r0 = r6.child;
            r0.onNext(r1);
            r0 = r6.child;
            r0 = r0.isUnsubscribed();
            if (r0 != 0) goto L_0x0009;
        L_0x0033:
            r0 = r6.child;
            r0.onCompleted();
            goto L_0x0009;
        L_0x0039:
            r0 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x0039 }
            throw r0;
        L_0x003c:
            monitor-enter(r6);
            r1 = r6.missed;	 Catch:{ all -> 0x0046 }
            if (r1 != 0) goto L_0x0049;
        L_0x0041:
            r0 = 0;
            r6.busy = r0;	 Catch:{ all -> 0x0046 }
            monitor-exit(r6);	 Catch:{ all -> 0x0046 }
            goto L_0x0009;
        L_0x0046:
            r0 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x0046 }
            throw r0;
        L_0x0049:
            monitor-exit(r6);	 Catch:{ all -> 0x0046 }
            goto L_0x000d;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorMaterialize.ParentSubscriber.drain():void");
        }

        public void onCompleted() {
            this.terminalNotification = Notification.createOnCompleted();
            drain();
        }

        public void onError(Throwable th) {
            this.terminalNotification = Notification.createOnError(th);
            RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
            drain();
        }

        public void onNext(T t) {
            this.child.onNext(Notification.createOnNext(t));
            decrementRequested();
        }

        public void onStart() {
            request(0);
        }

        void requestMore(long j) {
            BackpressureUtils.getAndAddRequest(this.requested, j);
            request(j);
            drain();
        }
    }

    OperatorMaterialize() {
    }

    public static <T> OperatorMaterialize<T> instance() {
        return Holder.INSTANCE;
    }

    public final Subscriber<? super T> call(Subscriber<? super Notification<T>> subscriber) {
        final ParentSubscriber parentSubscriber = new ParentSubscriber(subscriber);
        subscriber.add(parentSubscriber);
        subscriber.setProducer(new Producer() {
            public void request(long j) {
                if (j > 0) {
                    parentSubscriber.requestMore(j);
                }
            }
        });
        return parentSubscriber;
    }
}
