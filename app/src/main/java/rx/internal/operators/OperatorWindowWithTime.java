package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observable$Operator;
import rx.Observer;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.functions.Action0;
import rx.observers.SerializedObserver;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.Subscriptions;

public final class OperatorWindowWithTime<T> implements Observable$Operator<Observable<T>, T> {
    static final Object NEXT_SUBJECT = new Object();
    static final NotificationLite<Object> nl = NotificationLite.instance();
    final Scheduler scheduler;
    final int size;
    final long timeshift;
    final long timespan;
    final TimeUnit unit;

    static final class CountedSerializedSubject<T> {
        final Observer<T> consumer;
        int count;
        final Observable<T> producer;

        public CountedSerializedSubject(Observer<T> observer, Observable<T> observable) {
            this.consumer = new SerializedObserver(observer);
            this.producer = observable;
        }
    }

    final class ExactSubscriber extends Subscriber<T> {
        final Subscriber<? super Observable<T>> child;
        boolean emitting;
        final Object guard = new Object();
        List<Object> queue;
        volatile State<T> state = State.empty();
        final Worker worker;

        public ExactSubscriber(Subscriber<? super Observable<T>> subscriber, Worker worker) {
            this.child = new SerializedSubscriber(subscriber);
            this.worker = worker;
            subscriber.add(Subscriptions.create(new Action0(OperatorWindowWithTime.this) {
                public void call() {
                    if (ExactSubscriber.this.state.consumer == null) {
                        ExactSubscriber.this.unsubscribe();
                    }
                }
            }));
        }

        final void complete() {
            Observer observer = this.state.consumer;
            this.state = this.state.clear();
            if (observer != null) {
                observer.onCompleted();
            }
            this.child.onCompleted();
            unsubscribe();
        }

        final boolean drain(List<Object> list) {
            if (list != null) {
                for (Object next : list) {
                    if (next == OperatorWindowWithTime.NEXT_SUBJECT) {
                        if (!replaceSubject()) {
                            return false;
                        }
                    } else if (OperatorWindowWithTime.nl.isError(next)) {
                        error(OperatorWindowWithTime.nl.getError(next));
                        return true;
                    } else if (OperatorWindowWithTime.nl.isCompleted(next)) {
                        complete();
                        return true;
                    } else if (!emitValue(next)) {
                        return false;
                    }
                }
            }
            return true;
        }

        final boolean emitValue(T t) {
            State state = this.state;
            if (state.consumer == null) {
                if (!replaceSubject()) {
                    return false;
                }
                state = this.state;
            }
            state.consumer.onNext(t);
            if (state.count == OperatorWindowWithTime.this.size - 1) {
                state.consumer.onCompleted();
                state = state.clear();
            } else {
                state = state.next();
            }
            this.state = state;
            return true;
        }

        final void error(Throwable th) {
            Observer observer = this.state.consumer;
            this.state = this.state.clear();
            if (observer != null) {
                observer.onError(th);
            }
            this.child.onError(th);
            unsubscribe();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void nextWindow() {
            /*
            r5 = this;
            r2 = 1;
            r1 = 0;
            r3 = r5.guard;
            monitor-enter(r3);
            r0 = r5.emitting;	 Catch:{ all -> 0x0032 }
            if (r0 == 0) goto L_0x001d;
        L_0x0009:
            r0 = r5.queue;	 Catch:{ all -> 0x0032 }
            if (r0 != 0) goto L_0x0014;
        L_0x000d:
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0032 }
            r0.<init>();	 Catch:{ all -> 0x0032 }
            r5.queue = r0;	 Catch:{ all -> 0x0032 }
        L_0x0014:
            r0 = r5.queue;	 Catch:{ all -> 0x0032 }
            r1 = rx.internal.operators.OperatorWindowWithTime.NEXT_SUBJECT;	 Catch:{ all -> 0x0032 }
            r0.add(r1);	 Catch:{ all -> 0x0032 }
            monitor-exit(r3);	 Catch:{ all -> 0x0032 }
        L_0x001c:
            return;
        L_0x001d:
            r0 = 1;
            r5.emitting = r0;	 Catch:{ all -> 0x0032 }
            monitor-exit(r3);	 Catch:{ all -> 0x0032 }
            r0 = r5.replaceSubject();	 Catch:{ all -> 0x006c }
            if (r0 != 0) goto L_0x0035;
        L_0x0027:
            r1 = r5.guard;
            monitor-enter(r1);
            r0 = 0;
            r5.emitting = r0;	 Catch:{ all -> 0x002f }
            monitor-exit(r1);	 Catch:{ all -> 0x002f }
            goto L_0x001c;
        L_0x002f:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x002f }
            throw r0;
        L_0x0032:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0032 }
            throw r0;
        L_0x0035:
            r3 = r5.guard;	 Catch:{ all -> 0x006c }
            monitor-enter(r3);	 Catch:{ all -> 0x006c }
            r0 = r5.queue;	 Catch:{ all -> 0x006a }
            if (r0 != 0) goto L_0x0050;
        L_0x003c:
            r0 = 0;
            r5.emitting = r0;	 Catch:{ all -> 0x006a }
            monitor-exit(r3);	 Catch:{ all -> 0x0041 }
            goto L_0x001c;
        L_0x0041:
            r0 = move-exception;
            r1 = r2;
        L_0x0043:
            monitor-exit(r3);	 Catch:{ all -> 0x0068 }
            throw r0;	 Catch:{ all -> 0x0045 }
        L_0x0045:
            r0 = move-exception;
        L_0x0046:
            if (r1 != 0) goto L_0x004f;
        L_0x0048:
            r1 = r5.guard;
            monitor-enter(r1);
            r2 = 0;
            r5.emitting = r2;	 Catch:{ all -> 0x0065 }
            monitor-exit(r1);	 Catch:{ all -> 0x0065 }
        L_0x004f:
            throw r0;
        L_0x0050:
            r4 = 0;
            r5.queue = r4;	 Catch:{ all -> 0x006a }
            monitor-exit(r3);	 Catch:{ all -> 0x006a }
            r0 = r5.drain(r0);	 Catch:{ all -> 0x006c }
            if (r0 != 0) goto L_0x0035;
        L_0x005a:
            r1 = r5.guard;
            monitor-enter(r1);
            r0 = 0;
            r5.emitting = r0;	 Catch:{ all -> 0x0062 }
            monitor-exit(r1);	 Catch:{ all -> 0x0062 }
            goto L_0x001c;
        L_0x0062:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0062 }
            throw r0;
        L_0x0065:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0065 }
            throw r0;
        L_0x0068:
            r0 = move-exception;
            goto L_0x0043;
        L_0x006a:
            r0 = move-exception;
            goto L_0x0043;
        L_0x006c:
            r0 = move-exception;
            goto L_0x0046;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithTime.ExactSubscriber.nextWindow():void");
        }

        public final void onCompleted() {
            synchronized (this.guard) {
                if (this.emitting) {
                    if (this.queue == null) {
                        this.queue = new ArrayList();
                    }
                    this.queue.add(OperatorWindowWithTime.nl.completed());
                    return;
                }
                List list = this.queue;
                this.queue = null;
                this.emitting = true;
                try {
                    drain(list);
                    complete();
                } catch (Throwable th) {
                    error(th);
                }
            }
        }

        public final void onError(Throwable th) {
            synchronized (this.guard) {
                if (this.emitting) {
                    this.queue = Collections.singletonList(OperatorWindowWithTime.nl.error(th));
                    return;
                }
                this.queue = null;
                this.emitting = true;
                error(th);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onNext(T r6) {
            /*
            r5 = this;
            r2 = 1;
            r0 = 0;
            r1 = r5.guard;
            monitor-enter(r1);
            r3 = r5.emitting;	 Catch:{ all -> 0x0030 }
            if (r3 == 0) goto L_0x001b;
        L_0x0009:
            r0 = r5.queue;	 Catch:{ all -> 0x0030 }
            if (r0 != 0) goto L_0x0014;
        L_0x000d:
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0030 }
            r0.<init>();	 Catch:{ all -> 0x0030 }
            r5.queue = r0;	 Catch:{ all -> 0x0030 }
        L_0x0014:
            r0 = r5.queue;	 Catch:{ all -> 0x0030 }
            r0.add(r6);	 Catch:{ all -> 0x0030 }
            monitor-exit(r1);	 Catch:{ all -> 0x0030 }
        L_0x001a:
            return;
        L_0x001b:
            r3 = 1;
            r5.emitting = r3;	 Catch:{ all -> 0x0030 }
            monitor-exit(r1);	 Catch:{ all -> 0x0030 }
            r1 = r5.emitValue(r6);	 Catch:{ all -> 0x006a }
            if (r1 != 0) goto L_0x0033;
        L_0x0025:
            r1 = r5.guard;
            monitor-enter(r1);
            r0 = 0;
            r5.emitting = r0;	 Catch:{ all -> 0x002d }
            monitor-exit(r1);	 Catch:{ all -> 0x002d }
            goto L_0x001a;
        L_0x002d:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x002d }
            throw r0;
        L_0x0030:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0030 }
            throw r0;
        L_0x0033:
            r3 = r5.guard;	 Catch:{ all -> 0x006a }
            monitor-enter(r3);	 Catch:{ all -> 0x006a }
            r1 = r5.queue;	 Catch:{ all -> 0x0068 }
            if (r1 != 0) goto L_0x004e;
        L_0x003a:
            r1 = 0;
            r5.emitting = r1;	 Catch:{ all -> 0x0068 }
            monitor-exit(r3);	 Catch:{ all -> 0x003f }
            goto L_0x001a;
        L_0x003f:
            r1 = move-exception;
            r0 = r2;
        L_0x0041:
            monitor-exit(r3);	 Catch:{ all -> 0x0066 }
            throw r1;	 Catch:{ all -> 0x0043 }
        L_0x0043:
            r1 = move-exception;
        L_0x0044:
            if (r0 != 0) goto L_0x004d;
        L_0x0046:
            r2 = r5.guard;
            monitor-enter(r2);
            r0 = 0;
            r5.emitting = r0;	 Catch:{ all -> 0x0063 }
            monitor-exit(r2);	 Catch:{ all -> 0x0063 }
        L_0x004d:
            throw r1;
        L_0x004e:
            r4 = 0;
            r5.queue = r4;	 Catch:{ all -> 0x0068 }
            monitor-exit(r3);	 Catch:{ all -> 0x0068 }
            r1 = r5.drain(r1);	 Catch:{ all -> 0x006a }
            if (r1 != 0) goto L_0x0033;
        L_0x0058:
            r1 = r5.guard;
            monitor-enter(r1);
            r0 = 0;
            r5.emitting = r0;	 Catch:{ all -> 0x0060 }
            monitor-exit(r1);	 Catch:{ all -> 0x0060 }
            goto L_0x001a;
        L_0x0060:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0060 }
            throw r0;
        L_0x0063:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0063 }
            throw r0;
        L_0x0066:
            r1 = move-exception;
            goto L_0x0041;
        L_0x0068:
            r1 = move-exception;
            goto L_0x0041;
        L_0x006a:
            r1 = move-exception;
            goto L_0x0044;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithTime.ExactSubscriber.onNext(java.lang.Object):void");
        }

        public final void onStart() {
            request(Long.MAX_VALUE);
        }

        final boolean replaceSubject() {
            Observer observer = this.state.consumer;
            if (observer != null) {
                observer.onCompleted();
            }
            if (this.child.isUnsubscribed()) {
                this.state = this.state.clear();
                unsubscribe();
                return false;
            }
            UnicastSubject create = UnicastSubject.create();
            this.state = this.state.create(create, create);
            this.child.onNext(create);
            return true;
        }

        final void scheduleExact() {
            this.worker.schedulePeriodically(new Action0() {
                public void call() {
                    ExactSubscriber.this.nextWindow();
                }
            }, 0, OperatorWindowWithTime.this.timespan, OperatorWindowWithTime.this.unit);
        }
    }

    final class InexactSubscriber extends Subscriber<T> {
        final Subscriber<? super Observable<T>> child;
        final List<CountedSerializedSubject<T>> chunks = new LinkedList();
        boolean done;
        final Object guard = new Object();
        final Worker worker;

        public InexactSubscriber(Subscriber<? super Observable<T>> subscriber, Worker worker) {
            super(subscriber);
            this.child = subscriber;
            this.worker = worker;
        }

        final CountedSerializedSubject<T> createCountedSerializedSubject() {
            UnicastSubject create = UnicastSubject.create();
            return new CountedSerializedSubject(create, create);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onCompleted() {
            /*
            r3 = this;
            r1 = r3.guard;
            monitor-enter(r1);
            r0 = r3.done;	 Catch:{ all -> 0x002f }
            if (r0 == 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r1);	 Catch:{ all -> 0x002f }
        L_0x0008:
            return;
        L_0x0009:
            r0 = 1;
            r3.done = r0;	 Catch:{ all -> 0x002f }
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x002f }
            r2 = r3.chunks;	 Catch:{ all -> 0x002f }
            r0.<init>(r2);	 Catch:{ all -> 0x002f }
            r2 = r3.chunks;	 Catch:{ all -> 0x002f }
            r2.clear();	 Catch:{ all -> 0x002f }
            monitor-exit(r1);	 Catch:{ all -> 0x002f }
            r1 = r0.iterator();
        L_0x001d:
            r0 = r1.hasNext();
            if (r0 == 0) goto L_0x0032;
        L_0x0023:
            r0 = r1.next();
            r0 = (rx.internal.operators.OperatorWindowWithTime.CountedSerializedSubject) r0;
            r0 = r0.consumer;
            r0.onCompleted();
            goto L_0x001d;
        L_0x002f:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x002f }
            throw r0;
        L_0x0032:
            r0 = r3.child;
            r0.onCompleted();
            goto L_0x0008;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithTime.InexactSubscriber.onCompleted():void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onError(java.lang.Throwable r4) {
            /*
            r3 = this;
            r1 = r3.guard;
            monitor-enter(r1);
            r0 = r3.done;	 Catch:{ all -> 0x002f }
            if (r0 == 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r1);	 Catch:{ all -> 0x002f }
        L_0x0008:
            return;
        L_0x0009:
            r0 = 1;
            r3.done = r0;	 Catch:{ all -> 0x002f }
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x002f }
            r2 = r3.chunks;	 Catch:{ all -> 0x002f }
            r0.<init>(r2);	 Catch:{ all -> 0x002f }
            r2 = r3.chunks;	 Catch:{ all -> 0x002f }
            r2.clear();	 Catch:{ all -> 0x002f }
            monitor-exit(r1);	 Catch:{ all -> 0x002f }
            r1 = r0.iterator();
        L_0x001d:
            r0 = r1.hasNext();
            if (r0 == 0) goto L_0x0032;
        L_0x0023:
            r0 = r1.next();
            r0 = (rx.internal.operators.OperatorWindowWithTime.CountedSerializedSubject) r0;
            r0 = r0.consumer;
            r0.onError(r4);
            goto L_0x001d;
        L_0x002f:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x002f }
            throw r0;
        L_0x0032:
            r0 = r3.child;
            r0.onError(r4);
            goto L_0x0008;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithTime.InexactSubscriber.onError(java.lang.Throwable):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onNext(T r6) {
            /*
            r5 = this;
            r1 = r5.guard;
            monitor-enter(r1);
            r0 = r5.done;	 Catch:{ all -> 0x0032 }
            if (r0 == 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        L_0x0008:
            return;
        L_0x0009:
            r2 = new java.util.ArrayList;	 Catch:{ all -> 0x0032 }
            r0 = r5.chunks;	 Catch:{ all -> 0x0032 }
            r2.<init>(r0);	 Catch:{ all -> 0x0032 }
            r0 = r5.chunks;	 Catch:{ all -> 0x0032 }
            r3 = r0.iterator();	 Catch:{ all -> 0x0032 }
        L_0x0016:
            r0 = r3.hasNext();	 Catch:{ all -> 0x0032 }
            if (r0 == 0) goto L_0x0035;
        L_0x001c:
            r0 = r3.next();	 Catch:{ all -> 0x0032 }
            r0 = (rx.internal.operators.OperatorWindowWithTime.CountedSerializedSubject) r0;	 Catch:{ all -> 0x0032 }
            r4 = r0.count;	 Catch:{ all -> 0x0032 }
            r4 = r4 + 1;
            r0.count = r4;	 Catch:{ all -> 0x0032 }
            r0 = rx.internal.operators.OperatorWindowWithTime.this;	 Catch:{ all -> 0x0032 }
            r0 = r0.size;	 Catch:{ all -> 0x0032 }
            if (r4 != r0) goto L_0x0016;
        L_0x002e:
            r3.remove();	 Catch:{ all -> 0x0032 }
            goto L_0x0016;
        L_0x0032:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0032 }
            throw r0;
        L_0x0035:
            monitor-exit(r1);	 Catch:{ all -> 0x0032 }
            r1 = r2.iterator();
        L_0x003a:
            r0 = r1.hasNext();
            if (r0 == 0) goto L_0x0008;
        L_0x0040:
            r0 = r1.next();
            r0 = (rx.internal.operators.OperatorWindowWithTime.CountedSerializedSubject) r0;
            r2 = r0.consumer;
            r2.onNext(r6);
            r2 = r0.count;
            r3 = rx.internal.operators.OperatorWindowWithTime.this;
            r3 = r3.size;
            if (r2 != r3) goto L_0x003a;
        L_0x0053:
            r0 = r0.consumer;
            r0.onCompleted();
            goto L_0x003a;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithTime.InexactSubscriber.onNext(java.lang.Object):void");
        }

        public final void onStart() {
            request(Long.MAX_VALUE);
        }

        final void scheduleChunk() {
            this.worker.schedulePeriodically(new Action0() {
                public void call() {
                    InexactSubscriber.this.startNewChunk();
                }
            }, OperatorWindowWithTime.this.timeshift, OperatorWindowWithTime.this.timeshift, OperatorWindowWithTime.this.unit);
        }

        final void startNewChunk() {
            final CountedSerializedSubject createCountedSerializedSubject = createCountedSerializedSubject();
            synchronized (this.guard) {
                if (this.done) {
                    return;
                }
                this.chunks.add(createCountedSerializedSubject);
                try {
                    this.child.onNext(createCountedSerializedSubject.producer);
                    this.worker.schedule(new Action0() {
                        public void call() {
                            InexactSubscriber.this.terminateChunk(createCountedSerializedSubject);
                        }
                    }, OperatorWindowWithTime.this.timespan, OperatorWindowWithTime.this.unit);
                } catch (Throwable th) {
                    onError(th);
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void terminateChunk(rx.internal.operators.OperatorWindowWithTime.CountedSerializedSubject<T> r4) {
            /*
            r3 = this;
            r1 = r3.guard;
            monitor-enter(r1);
            r0 = r3.done;	 Catch:{ all -> 0x002a }
            if (r0 == 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r1);	 Catch:{ all -> 0x002a }
        L_0x0008:
            return;
        L_0x0009:
            r0 = r3.chunks;	 Catch:{ all -> 0x002a }
            r2 = r0.iterator();	 Catch:{ all -> 0x002a }
        L_0x000f:
            r0 = r2.hasNext();	 Catch:{ all -> 0x002a }
            if (r0 == 0) goto L_0x002d;
        L_0x0015:
            r0 = r2.next();	 Catch:{ all -> 0x002a }
            r0 = (rx.internal.operators.OperatorWindowWithTime.CountedSerializedSubject) r0;	 Catch:{ all -> 0x002a }
            if (r0 != r4) goto L_0x000f;
        L_0x001d:
            r0 = 1;
            r2.remove();	 Catch:{ all -> 0x002a }
        L_0x0021:
            monitor-exit(r1);	 Catch:{ all -> 0x002a }
            if (r0 == 0) goto L_0x0008;
        L_0x0024:
            r0 = r4.consumer;
            r0.onCompleted();
            goto L_0x0008;
        L_0x002a:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x002a }
            throw r0;
        L_0x002d:
            r0 = 0;
            goto L_0x0021;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithTime.InexactSubscriber.terminateChunk(rx.internal.operators.OperatorWindowWithTime$CountedSerializedSubject):void");
        }
    }

    static final class State<T> {
        static final State<Object> EMPTY = new State(null, null, 0);
        final Observer<T> consumer;
        final int count;
        final Observable<T> producer;

        public State(Observer<T> observer, Observable<T> observable, int i) {
            this.consumer = observer;
            this.producer = observable;
            this.count = i;
        }

        public static <T> State<T> empty() {
            return EMPTY;
        }

        public final State<T> clear() {
            return empty();
        }

        public final State<T> create(Observer<T> observer, Observable<T> observable) {
            return new State(observer, observable, 0);
        }

        public final State<T> next() {
            return new State(this.consumer, this.producer, this.count + 1);
        }
    }

    public OperatorWindowWithTime(long j, long j2, TimeUnit timeUnit, int i, Scheduler scheduler) {
        this.timespan = j;
        this.timeshift = j2;
        this.unit = timeUnit;
        this.size = i;
        this.scheduler = scheduler;
    }

    public final Subscriber<? super T> call(Subscriber<? super Observable<T>> subscriber) {
        Worker createWorker = this.scheduler.createWorker();
        if (this.timespan == this.timeshift) {
            ExactSubscriber exactSubscriber = new ExactSubscriber(subscriber, createWorker);
            exactSubscriber.add(createWorker);
            exactSubscriber.scheduleExact();
            return exactSubscriber;
        }
        InexactSubscriber inexactSubscriber = new InexactSubscriber(subscriber, createWorker);
        inexactSubscriber.add(createWorker);
        inexactSubscriber.startNewChunk();
        inexactSubscriber.scheduleChunk();
        return inexactSubscriber;
    }
}
