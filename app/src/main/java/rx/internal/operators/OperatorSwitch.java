package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observable$Operator;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.functions.Action0;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.atomic.SpscLinkedArrayQueue;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

public final class OperatorSwitch<T> implements Observable$Operator<T, Observable<? extends T>> {
    final boolean delayError;

    static final class Holder {
        static final OperatorSwitch<Object> INSTANCE = new OperatorSwitch(false);

        private Holder() {
        }
    }

    static final class HolderDelayError {
        static final OperatorSwitch<Object> INSTANCE = new OperatorSwitch(true);

        private HolderDelayError() {
        }
    }

    static final class InnerSubscriber<T> extends Subscriber<T> {
        private final long id;
        private final SwitchSubscriber<T> parent;

        InnerSubscriber(long j, SwitchSubscriber<T> switchSubscriber) {
            this.id = j;
            this.parent = switchSubscriber;
        }

        public final void onCompleted() {
            this.parent.complete(this.id);
        }

        public final void onError(Throwable th) {
            this.parent.error(th, this.id);
        }

        public final void onNext(T t) {
            this.parent.emit(t, this);
        }

        public final void setProducer(Producer producer) {
            this.parent.innerProducer(producer, this.id);
        }
    }

    static final class SwitchSubscriber<T> extends Subscriber<Observable<? extends T>> {
        static final Throwable TERMINAL_ERROR = new Throwable("Terminal error");
        final Subscriber<? super T> child;
        final boolean delayError;
        boolean emitting;
        Throwable error;
        final AtomicLong index;
        boolean innerActive;
        volatile boolean mainDone;
        boolean missed;
        final NotificationLite<T> nl;
        Producer producer;
        final SpscLinkedArrayQueue<Object> queue;
        long requested;
        final SerialSubscription ssub = new SerialSubscription();

        SwitchSubscriber(Subscriber<? super T> subscriber, boolean z) {
            this.child = subscriber;
            this.delayError = z;
            this.index = new AtomicLong();
            this.queue = new SpscLinkedArrayQueue(RxRingBuffer.SIZE);
            this.nl = NotificationLite.instance();
        }

        protected final boolean checkTerminated(boolean z, boolean z2, Throwable th, SpscLinkedArrayQueue<Object> spscLinkedArrayQueue, Subscriber<? super T> subscriber, boolean z3) {
            if (this.delayError) {
                if (z && !z2 && z3) {
                    if (th != null) {
                        subscriber.onError(th);
                        return true;
                    }
                    subscriber.onCompleted();
                    return true;
                }
            } else if (th != null) {
                spscLinkedArrayQueue.clear();
                subscriber.onError(th);
                return true;
            } else if (z && !z2 && z3) {
                subscriber.onCompleted();
                return true;
            }
            return false;
        }

        final void childRequested(long j) {
            synchronized (this) {
                Producer producer = this.producer;
                this.requested = BackpressureUtils.addCap(this.requested, j);
            }
            if (producer != null) {
                producer.request(j);
            }
            drain();
        }

        final void clearProducer() {
            synchronized (this) {
                this.producer = null;
            }
        }

        final void complete(long j) {
            synchronized (this) {
                if (this.index.get() != j) {
                    return;
                }
                this.innerActive = false;
                this.producer = null;
                drain();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void drain() {
            /*
            r20 = this;
            r0 = r20;
            r8 = r0.mainDone;
            monitor-enter(r20);
            r0 = r20;
            r2 = r0.emitting;	 Catch:{ all -> 0x0084 }
            if (r2 == 0) goto L_0x0012;
        L_0x000b:
            r2 = 1;
            r0 = r20;
            r0.missed = r2;	 Catch:{ all -> 0x0084 }
            monitor-exit(r20);	 Catch:{ all -> 0x0084 }
        L_0x0011:
            return;
        L_0x0012:
            r2 = 1;
            r0 = r20;
            r0.emitting = r2;	 Catch:{ all -> 0x0084 }
            r0 = r20;
            r4 = r0.innerActive;	 Catch:{ all -> 0x0084 }
            r0 = r20;
            r2 = r0.requested;	 Catch:{ all -> 0x0084 }
            r0 = r20;
            r5 = r0.error;	 Catch:{ all -> 0x0084 }
            if (r5 == 0) goto L_0x0035;
        L_0x0025:
            r6 = TERMINAL_ERROR;	 Catch:{ all -> 0x0084 }
            if (r5 == r6) goto L_0x0035;
        L_0x0029:
            r0 = r20;
            r6 = r0.delayError;	 Catch:{ all -> 0x0084 }
            if (r6 != 0) goto L_0x0035;
        L_0x002f:
            r6 = TERMINAL_ERROR;	 Catch:{ all -> 0x0084 }
            r0 = r20;
            r0.error = r6;	 Catch:{ all -> 0x0084 }
        L_0x0035:
            monitor-exit(r20);	 Catch:{ all -> 0x0084 }
            r0 = r20;
            r6 = r0.queue;
            r0 = r20;
            r14 = r0.index;
            r0 = r20;
            r7 = r0.child;
            r10 = r2;
            r3 = r8;
        L_0x0044:
            r12 = 0;
        L_0x0046:
            r2 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
            if (r2 == 0) goto L_0x0087;
        L_0x004a:
            r2 = r7.isUnsubscribed();
            if (r2 != 0) goto L_0x0011;
        L_0x0050:
            r8 = r6.isEmpty();
            r2 = r20;
            r2 = r2.checkTerminated(r3, r4, r5, r6, r7, r8);
            if (r2 != 0) goto L_0x0011;
        L_0x005c:
            if (r8 != 0) goto L_0x0087;
        L_0x005e:
            r2 = r6.poll();
            r2 = (rx.internal.operators.OperatorSwitch.InnerSubscriber) r2;
            r0 = r20;
            r8 = r0.nl;
            r9 = r6.poll();
            r8 = r8.getValue(r9);
            r16 = r14.get();
            r18 = r2.id;
            r2 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
            if (r2 != 0) goto L_0x00ed;
        L_0x007c:
            r7.onNext(r8);
            r8 = 1;
            r8 = r8 + r12;
        L_0x0082:
            r12 = r8;
            goto L_0x0046;
        L_0x0084:
            r2 = move-exception;
            monitor-exit(r20);	 Catch:{ all -> 0x0084 }
            throw r2;
        L_0x0087:
            r2 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
            if (r2 != 0) goto L_0x00a1;
        L_0x008b:
            r2 = r7.isUnsubscribed();
            if (r2 != 0) goto L_0x0011;
        L_0x0091:
            r0 = r20;
            r3 = r0.mainDone;
            r8 = r6.isEmpty();
            r2 = r20;
            r2 = r2.checkTerminated(r3, r4, r5, r6, r7, r8);
            if (r2 != 0) goto L_0x0011;
        L_0x00a1:
            monitor-enter(r20);
            r0 = r20;
            r2 = r0.requested;	 Catch:{ all -> 0x00c2 }
            r4 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
            r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r4 == 0) goto L_0x00b4;
        L_0x00af:
            r2 = r2 - r12;
            r0 = r20;
            r0.requested = r2;	 Catch:{ all -> 0x00c2 }
        L_0x00b4:
            r0 = r20;
            r4 = r0.missed;	 Catch:{ all -> 0x00c2 }
            if (r4 != 0) goto L_0x00c5;
        L_0x00ba:
            r2 = 0;
            r0 = r20;
            r0.emitting = r2;	 Catch:{ all -> 0x00c2 }
            monitor-exit(r20);	 Catch:{ all -> 0x00c2 }
            goto L_0x0011;
        L_0x00c2:
            r2 = move-exception;
            monitor-exit(r20);	 Catch:{ all -> 0x00c2 }
            throw r2;
        L_0x00c5:
            r4 = 0;
            r0 = r20;
            r0.missed = r4;	 Catch:{ all -> 0x00c2 }
            r0 = r20;
            r8 = r0.mainDone;	 Catch:{ all -> 0x00c2 }
            r0 = r20;
            r4 = r0.innerActive;	 Catch:{ all -> 0x00c2 }
            r0 = r20;
            r5 = r0.error;	 Catch:{ all -> 0x00c2 }
            if (r5 == 0) goto L_0x00e8;
        L_0x00d8:
            r9 = TERMINAL_ERROR;	 Catch:{ all -> 0x00c2 }
            if (r5 == r9) goto L_0x00e8;
        L_0x00dc:
            r0 = r20;
            r9 = r0.delayError;	 Catch:{ all -> 0x00c2 }
            if (r9 != 0) goto L_0x00e8;
        L_0x00e2:
            r9 = TERMINAL_ERROR;	 Catch:{ all -> 0x00c2 }
            r0 = r20;
            r0.error = r9;	 Catch:{ all -> 0x00c2 }
        L_0x00e8:
            monitor-exit(r20);	 Catch:{ all -> 0x00c2 }
            r10 = r2;
            r3 = r8;
            goto L_0x0044;
        L_0x00ed:
            r8 = r12;
            goto L_0x0082;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorSwitch.SwitchSubscriber.drain():void");
        }

        final void emit(T t, InnerSubscriber<T> innerSubscriber) {
            synchronized (this) {
                if (this.index.get() != innerSubscriber.id) {
                    return;
                }
                this.queue.offer(innerSubscriber, this.nl.next(t));
                drain();
            }
        }

        final void error(Throwable th, long j) {
            boolean updateError;
            synchronized (this) {
                if (this.index.get() == j) {
                    updateError = updateError(th);
                    this.innerActive = false;
                    this.producer = null;
                } else {
                    updateError = true;
                }
            }
            if (updateError) {
                drain();
            } else {
                pluginError(th);
            }
        }

        final void init() {
            this.child.add(this.ssub);
            this.child.add(Subscriptions.create(new Action0() {
                public void call() {
                    SwitchSubscriber.this.clearProducer();
                }
            }));
            this.child.setProducer(new Producer() {
                public void request(long j) {
                    if (j > 0) {
                        SwitchSubscriber.this.childRequested(j);
                    } else if (j < 0) {
                        throw new IllegalArgumentException("n >= 0 expected but it was " + j);
                    }
                }
            });
        }

        final void innerProducer(Producer producer, long j) {
            synchronized (this) {
                if (this.index.get() != j) {
                    return;
                }
                long j2 = this.requested;
                this.producer = producer;
                producer.request(j2);
            }
        }

        public final void onCompleted() {
            this.mainDone = true;
            drain();
        }

        public final void onError(Throwable th) {
            synchronized (this) {
                boolean updateError = updateError(th);
            }
            if (updateError) {
                this.mainDone = true;
                drain();
                return;
            }
            pluginError(th);
        }

        public final void onNext(Observable<? extends T> observable) {
            InnerSubscriber innerSubscriber;
            long incrementAndGet = this.index.incrementAndGet();
            Subscription subscription = this.ssub.get();
            if (subscription != null) {
                subscription.unsubscribe();
            }
            synchronized (this) {
                innerSubscriber = new InnerSubscriber(incrementAndGet, this);
                this.innerActive = true;
                this.producer = null;
            }
            this.ssub.set(innerSubscriber);
            observable.unsafeSubscribe(innerSubscriber);
        }

        final void pluginError(Throwable th) {
            RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
        }

        final boolean updateError(Throwable th) {
            Throwable th2 = this.error;
            if (th2 == TERMINAL_ERROR) {
                return false;
            }
            if (th2 == null) {
                this.error = th;
            } else if (th2 instanceof CompositeException) {
                Collection arrayList = new ArrayList(((CompositeException) th2).getExceptions());
                arrayList.add(th);
                this.error = new CompositeException(arrayList);
            } else {
                this.error = new CompositeException(new Throwable[]{th2, th});
            }
            return true;
        }
    }

    OperatorSwitch(boolean z) {
        this.delayError = z;
    }

    public static <T> OperatorSwitch<T> instance(boolean z) {
        return z ? HolderDelayError.INSTANCE : Holder.INSTANCE;
    }

    public final Subscriber<? super Observable<? extends T>> call(Subscriber<? super T> subscriber) {
        SwitchSubscriber switchSubscriber = new SwitchSubscriber(subscriber, this.delayError);
        subscriber.add(switchSubscriber);
        switchSubscriber.init();
        return switchSubscriber;
    }
}
