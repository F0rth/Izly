package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.Observable$Operator;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func0;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.SerialSubscription;

public final class OperatorWindowWithObservableFactory<T, U> implements Observable$Operator<Observable<T>, T> {
    static final Object NEXT_SUBJECT = new Object();
    static final NotificationLite<Object> nl = NotificationLite.instance();
    final Func0<? extends Observable<? extends U>> otherFactory;

    static final class BoundarySubscriber<T, U> extends Subscriber<U> {
        boolean done;
        final SourceSubscriber<T, U> sub;

        public BoundarySubscriber(Subscriber<?> subscriber, SourceSubscriber<T, U> sourceSubscriber) {
            this.sub = sourceSubscriber;
        }

        public final void onCompleted() {
            if (!this.done) {
                this.done = true;
                this.sub.onCompleted();
            }
        }

        public final void onError(Throwable th) {
            this.sub.onError(th);
        }

        public final void onNext(U u) {
            if (!this.done) {
                this.done = true;
                this.sub.replaceWindow();
            }
        }

        public final void onStart() {
            request(Long.MAX_VALUE);
        }
    }

    static final class SourceSubscriber<T, U> extends Subscriber<T> {
        final Subscriber<? super Observable<T>> child;
        Observer<T> consumer;
        boolean emitting;
        final Object guard = new Object();
        final Func0<? extends Observable<? extends U>> otherFactory;
        Observable<T> producer;
        List<Object> queue;
        final SerialSubscription ssub = new SerialSubscription();

        public SourceSubscriber(Subscriber<? super Observable<T>> subscriber, Func0<? extends Observable<? extends U>> func0) {
            this.child = new SerializedSubscriber(subscriber);
            this.otherFactory = func0;
            add(this.ssub);
        }

        final void complete() {
            Observer observer = this.consumer;
            this.consumer = null;
            this.producer = null;
            if (observer != null) {
                observer.onCompleted();
            }
            this.child.onCompleted();
            unsubscribe();
        }

        final void createNewWindow() {
            UnicastSubject create = UnicastSubject.create();
            this.consumer = create;
            this.producer = create;
            try {
                Observable observable = (Observable) this.otherFactory.call();
                BoundarySubscriber boundarySubscriber = new BoundarySubscriber(this.child, this);
                this.ssub.set(boundarySubscriber);
                observable.unsafeSubscribe(boundarySubscriber);
            } catch (Throwable th) {
                this.child.onError(th);
                unsubscribe();
            }
        }

        final void drain(List<Object> list) {
            if (list != null) {
                for (Object next : list) {
                    if (next == OperatorWindowWithObservableFactory.NEXT_SUBJECT) {
                        replaceSubject();
                    } else if (OperatorWindowWithObservableFactory.nl.isError(next)) {
                        error(OperatorWindowWithObservableFactory.nl.getError(next));
                        return;
                    } else if (OperatorWindowWithObservableFactory.nl.isCompleted(next)) {
                        complete();
                        return;
                    } else {
                        emitValue(next);
                    }
                }
            }
        }

        final void emitValue(T t) {
            Observer observer = this.consumer;
            if (observer != null) {
                observer.onNext(t);
            }
        }

        final void error(Throwable th) {
            Observer observer = this.consumer;
            this.consumer = null;
            this.producer = null;
            if (observer != null) {
                observer.onError(th);
            }
            this.child.onError(th);
            unsubscribe();
        }

        public final void onCompleted() {
            synchronized (this.guard) {
                if (this.emitting) {
                    if (this.queue == null) {
                        this.queue = new ArrayList();
                    }
                    this.queue.add(OperatorWindowWithObservableFactory.nl.completed());
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
                    this.queue = Collections.singletonList(OperatorWindowWithObservableFactory.nl.error(th));
                    return;
                }
                this.queue = null;
                this.emitting = true;
                error(th);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onNext(T r7) {
            /*
            r6 = this;
            r2 = 1;
            r0 = 0;
            r3 = r6.guard;
            monitor-enter(r3);
            r1 = r6.emitting;	 Catch:{ all -> 0x004d }
            if (r1 == 0) goto L_0x001b;
        L_0x0009:
            r0 = r6.queue;	 Catch:{ all -> 0x004d }
            if (r0 != 0) goto L_0x0014;
        L_0x000d:
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x004d }
            r0.<init>();	 Catch:{ all -> 0x004d }
            r6.queue = r0;	 Catch:{ all -> 0x004d }
        L_0x0014:
            r0 = r6.queue;	 Catch:{ all -> 0x004d }
            r0.add(r7);	 Catch:{ all -> 0x004d }
            monitor-exit(r3);	 Catch:{ all -> 0x004d }
        L_0x001a:
            return;
        L_0x001b:
            r1 = r6.queue;	 Catch:{ all -> 0x004d }
            r4 = 0;
            r6.queue = r4;	 Catch:{ all -> 0x004d }
            r4 = 1;
            r6.emitting = r4;	 Catch:{ all -> 0x004d }
            monitor-exit(r3);	 Catch:{ all -> 0x004d }
            r3 = r1;
            r1 = r2;
        L_0x0026:
            r6.drain(r3);	 Catch:{ all -> 0x006b }
            if (r1 == 0) goto L_0x002f;
        L_0x002b:
            r6.emitValue(r7);	 Catch:{ all -> 0x006b }
            r1 = r0;
        L_0x002f:
            r4 = r6.guard;	 Catch:{ all -> 0x006b }
            monitor-enter(r4);	 Catch:{ all -> 0x006b }
            r3 = r6.queue;	 Catch:{ all -> 0x0069 }
            r5 = 0;
            r6.queue = r5;	 Catch:{ all -> 0x0069 }
            if (r3 != 0) goto L_0x0050;
        L_0x0039:
            r1 = 0;
            r6.emitting = r1;	 Catch:{ all -> 0x0069 }
            monitor-exit(r4);	 Catch:{ all -> 0x003e }
            goto L_0x001a;
        L_0x003e:
            r1 = move-exception;
            r0 = r2;
        L_0x0040:
            monitor-exit(r4);	 Catch:{ all -> 0x0067 }
            throw r1;	 Catch:{ all -> 0x0042 }
        L_0x0042:
            r1 = move-exception;
        L_0x0043:
            if (r0 != 0) goto L_0x004c;
        L_0x0045:
            r2 = r6.guard;
            monitor-enter(r2);
            r0 = 0;
            r6.emitting = r0;	 Catch:{ all -> 0x0064 }
            monitor-exit(r2);	 Catch:{ all -> 0x0064 }
        L_0x004c:
            throw r1;
        L_0x004d:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x004d }
            throw r0;
        L_0x0050:
            monitor-exit(r4);	 Catch:{ all -> 0x0069 }
            r4 = r6.child;	 Catch:{ all -> 0x006b }
            r4 = r4.isUnsubscribed();	 Catch:{ all -> 0x006b }
            if (r4 == 0) goto L_0x0026;
        L_0x0059:
            r1 = r6.guard;
            monitor-enter(r1);
            r0 = 0;
            r6.emitting = r0;	 Catch:{ all -> 0x0061 }
            monitor-exit(r1);	 Catch:{ all -> 0x0061 }
            goto L_0x001a;
        L_0x0061:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0061 }
            throw r0;
        L_0x0064:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0064 }
            throw r0;
        L_0x0067:
            r1 = move-exception;
            goto L_0x0040;
        L_0x0069:
            r1 = move-exception;
            goto L_0x0040;
        L_0x006b:
            r1 = move-exception;
            goto L_0x0043;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithObservableFactory.SourceSubscriber.onNext(java.lang.Object):void");
        }

        public final void onStart() {
            request(Long.MAX_VALUE);
        }

        final void replaceSubject() {
            Observer observer = this.consumer;
            if (observer != null) {
                observer.onCompleted();
            }
            createNewWindow();
            this.child.onNext(this.producer);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void replaceWindow() {
            /*
            r6 = this;
            r2 = 1;
            r1 = 0;
            r3 = r6.guard;
            monitor-enter(r3);
            r0 = r6.emitting;	 Catch:{ all -> 0x004f }
            if (r0 == 0) goto L_0x001d;
        L_0x0009:
            r0 = r6.queue;	 Catch:{ all -> 0x004f }
            if (r0 != 0) goto L_0x0014;
        L_0x000d:
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x004f }
            r0.<init>();	 Catch:{ all -> 0x004f }
            r6.queue = r0;	 Catch:{ all -> 0x004f }
        L_0x0014:
            r0 = r6.queue;	 Catch:{ all -> 0x004f }
            r1 = rx.internal.operators.OperatorWindowWithObservableFactory.NEXT_SUBJECT;	 Catch:{ all -> 0x004f }
            r0.add(r1);	 Catch:{ all -> 0x004f }
            monitor-exit(r3);	 Catch:{ all -> 0x004f }
        L_0x001c:
            return;
        L_0x001d:
            r0 = r6.queue;	 Catch:{ all -> 0x004f }
            r4 = 0;
            r6.queue = r4;	 Catch:{ all -> 0x004f }
            r4 = 1;
            r6.emitting = r4;	 Catch:{ all -> 0x004f }
            monitor-exit(r3);	 Catch:{ all -> 0x004f }
            r3 = r0;
            r0 = r2;
        L_0x0028:
            r6.drain(r3);	 Catch:{ all -> 0x006d }
            if (r0 == 0) goto L_0x0031;
        L_0x002d:
            r6.replaceSubject();	 Catch:{ all -> 0x006d }
            r0 = r1;
        L_0x0031:
            r4 = r6.guard;	 Catch:{ all -> 0x006d }
            monitor-enter(r4);	 Catch:{ all -> 0x006d }
            r3 = r6.queue;	 Catch:{ all -> 0x006b }
            r5 = 0;
            r6.queue = r5;	 Catch:{ all -> 0x006b }
            if (r3 != 0) goto L_0x0052;
        L_0x003b:
            r0 = 0;
            r6.emitting = r0;	 Catch:{ all -> 0x006b }
            monitor-exit(r4);	 Catch:{ all -> 0x0040 }
            goto L_0x001c;
        L_0x0040:
            r0 = move-exception;
            r1 = r2;
        L_0x0042:
            monitor-exit(r4);	 Catch:{ all -> 0x0069 }
            throw r0;	 Catch:{ all -> 0x0044 }
        L_0x0044:
            r0 = move-exception;
        L_0x0045:
            if (r1 != 0) goto L_0x004e;
        L_0x0047:
            r1 = r6.guard;
            monitor-enter(r1);
            r2 = 0;
            r6.emitting = r2;	 Catch:{ all -> 0x0066 }
            monitor-exit(r1);	 Catch:{ all -> 0x0066 }
        L_0x004e:
            throw r0;
        L_0x004f:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x004f }
            throw r0;
        L_0x0052:
            monitor-exit(r4);	 Catch:{ all -> 0x006b }
            r4 = r6.child;	 Catch:{ all -> 0x006d }
            r4 = r4.isUnsubscribed();	 Catch:{ all -> 0x006d }
            if (r4 == 0) goto L_0x0028;
        L_0x005b:
            r1 = r6.guard;
            monitor-enter(r1);
            r0 = 0;
            r6.emitting = r0;	 Catch:{ all -> 0x0063 }
            monitor-exit(r1);	 Catch:{ all -> 0x0063 }
            goto L_0x001c;
        L_0x0063:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0063 }
            throw r0;
        L_0x0066:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0066 }
            throw r0;
        L_0x0069:
            r0 = move-exception;
            goto L_0x0042;
        L_0x006b:
            r0 = move-exception;
            goto L_0x0042;
        L_0x006d:
            r0 = move-exception;
            goto L_0x0045;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithObservableFactory.SourceSubscriber.replaceWindow():void");
        }
    }

    public OperatorWindowWithObservableFactory(Func0<? extends Observable<? extends U>> func0) {
        this.otherFactory = func0;
    }

    public final Subscriber<? super T> call(Subscriber<? super Observable<T>> subscriber) {
        SourceSubscriber sourceSubscriber = new SourceSubscriber(subscriber, this.otherFactory);
        subscriber.add(sourceSubscriber);
        sourceSubscriber.replaceWindow();
        return sourceSubscriber;
    }
}
