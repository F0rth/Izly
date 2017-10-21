package rx.internal.operators;

import java.util.LinkedList;
import java.util.List;
import rx.Observable;
import rx.Observable$Operator;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observers.SerializedObserver;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.CompositeSubscription;

public final class OperatorWindowWithStartEndObservable<T, U, V> implements Observable$Operator<Observable<T>, T> {
    final Func1<? super U, ? extends Observable<? extends V>> windowClosingSelector;
    final Observable<? extends U> windowOpenings;

    static final class SerializedSubject<T> {
        final Observer<T> consumer;
        final Observable<T> producer;

        public SerializedSubject(Observer<T> observer, Observable<T> observable) {
            this.consumer = new SerializedObserver(observer);
            this.producer = observable;
        }
    }

    final class SourceSubscriber extends Subscriber<T> {
        final Subscriber<? super Observable<T>> child;
        final List<SerializedSubject<T>> chunks = new LinkedList();
        final CompositeSubscription csub;
        boolean done;
        final Object guard = new Object();

        public SourceSubscriber(Subscriber<? super Observable<T>> subscriber, CompositeSubscription compositeSubscription) {
            this.child = new SerializedSubscriber(subscriber);
            this.csub = compositeSubscription;
        }

        final void beginWindow(U u) {
            final SerializedSubject createSerializedSubject = createSerializedSubject();
            synchronized (this.guard) {
                if (this.done) {
                    return;
                }
                this.chunks.add(createSerializedSubject);
                this.child.onNext(createSerializedSubject.producer);
                try {
                    Observable observable = (Observable) OperatorWindowWithStartEndObservable.this.windowClosingSelector.call(u);
                    AnonymousClass1 anonymousClass1 = new Subscriber<V>() {
                        boolean once = true;

                        public void onCompleted() {
                            if (this.once) {
                                this.once = false;
                                SourceSubscriber.this.endWindow(createSerializedSubject);
                                SourceSubscriber.this.csub.remove(this);
                            }
                        }

                        public void onError(Throwable th) {
                        }

                        public void onNext(V v) {
                            onCompleted();
                        }
                    };
                    this.csub.add(anonymousClass1);
                    observable.unsafeSubscribe(anonymousClass1);
                } catch (Throwable th) {
                    onError(th);
                }
            }
        }

        final SerializedSubject<T> createSerializedSubject() {
            UnicastSubject create = UnicastSubject.create();
            return new SerializedSubject(create, create);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void endWindow(rx.internal.operators.OperatorWindowWithStartEndObservable.SerializedSubject<T> r4) {
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
            r0 = (rx.internal.operators.OperatorWindowWithStartEndObservable.SerializedSubject) r0;	 Catch:{ all -> 0x002a }
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
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithStartEndObservable.SourceSubscriber.endWindow(rx.internal.operators.OperatorWindowWithStartEndObservable$SerializedSubject):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onCompleted() {
            /*
            r3 = this;
            r1 = r3.guard;	 Catch:{ all -> 0x0034 }
            monitor-enter(r1);	 Catch:{ all -> 0x0034 }
            r0 = r3.done;	 Catch:{ all -> 0x003b }
            if (r0 == 0) goto L_0x000e;
        L_0x0007:
            monitor-exit(r1);	 Catch:{ all -> 0x003b }
            r0 = r3.csub;
            r0.unsubscribe();
        L_0x000d:
            return;
        L_0x000e:
            r0 = 1;
            r3.done = r0;	 Catch:{ all -> 0x003b }
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x003b }
            r2 = r3.chunks;	 Catch:{ all -> 0x003b }
            r0.<init>(r2);	 Catch:{ all -> 0x003b }
            r2 = r3.chunks;	 Catch:{ all -> 0x003b }
            r2.clear();	 Catch:{ all -> 0x003b }
            monitor-exit(r1);	 Catch:{ all -> 0x003b }
            r1 = r0.iterator();	 Catch:{ all -> 0x0034 }
        L_0x0022:
            r0 = r1.hasNext();	 Catch:{ all -> 0x0034 }
            if (r0 == 0) goto L_0x003e;
        L_0x0028:
            r0 = r1.next();	 Catch:{ all -> 0x0034 }
            r0 = (rx.internal.operators.OperatorWindowWithStartEndObservable.SerializedSubject) r0;	 Catch:{ all -> 0x0034 }
            r0 = r0.consumer;	 Catch:{ all -> 0x0034 }
            r0.onCompleted();	 Catch:{ all -> 0x0034 }
            goto L_0x0022;
        L_0x0034:
            r0 = move-exception;
            r1 = r3.csub;
            r1.unsubscribe();
            throw r0;
        L_0x003b:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x003b }
            throw r0;	 Catch:{ all -> 0x0034 }
        L_0x003e:
            r0 = r3.child;	 Catch:{ all -> 0x0034 }
            r0.onCompleted();	 Catch:{ all -> 0x0034 }
            r0 = r3.csub;
            r0.unsubscribe();
            goto L_0x000d;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithStartEndObservable.SourceSubscriber.onCompleted():void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onError(java.lang.Throwable r4) {
            /*
            r3 = this;
            r1 = r3.guard;	 Catch:{ all -> 0x0034 }
            monitor-enter(r1);	 Catch:{ all -> 0x0034 }
            r0 = r3.done;	 Catch:{ all -> 0x003b }
            if (r0 == 0) goto L_0x000e;
        L_0x0007:
            monitor-exit(r1);	 Catch:{ all -> 0x003b }
            r0 = r3.csub;
            r0.unsubscribe();
        L_0x000d:
            return;
        L_0x000e:
            r0 = 1;
            r3.done = r0;	 Catch:{ all -> 0x003b }
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x003b }
            r2 = r3.chunks;	 Catch:{ all -> 0x003b }
            r0.<init>(r2);	 Catch:{ all -> 0x003b }
            r2 = r3.chunks;	 Catch:{ all -> 0x003b }
            r2.clear();	 Catch:{ all -> 0x003b }
            monitor-exit(r1);	 Catch:{ all -> 0x003b }
            r1 = r0.iterator();	 Catch:{ all -> 0x0034 }
        L_0x0022:
            r0 = r1.hasNext();	 Catch:{ all -> 0x0034 }
            if (r0 == 0) goto L_0x003e;
        L_0x0028:
            r0 = r1.next();	 Catch:{ all -> 0x0034 }
            r0 = (rx.internal.operators.OperatorWindowWithStartEndObservable.SerializedSubject) r0;	 Catch:{ all -> 0x0034 }
            r0 = r0.consumer;	 Catch:{ all -> 0x0034 }
            r0.onError(r4);	 Catch:{ all -> 0x0034 }
            goto L_0x0022;
        L_0x0034:
            r0 = move-exception;
            r1 = r3.csub;
            r1.unsubscribe();
            throw r0;
        L_0x003b:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x003b }
            throw r0;	 Catch:{ all -> 0x0034 }
        L_0x003e:
            r0 = r3.child;	 Catch:{ all -> 0x0034 }
            r0.onError(r4);	 Catch:{ all -> 0x0034 }
            r0 = r3.csub;
            r0.unsubscribe();
            goto L_0x000d;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithStartEndObservable.SourceSubscriber.onError(java.lang.Throwable):void");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onNext(T r4) {
            /*
            r3 = this;
            r1 = r3.guard;
            monitor-enter(r1);
            r0 = r3.done;	 Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r1);	 Catch:{ all -> 0x0027 }
        L_0x0008:
            return;
        L_0x0009:
            r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0027 }
            r2 = r3.chunks;	 Catch:{ all -> 0x0027 }
            r0.<init>(r2);	 Catch:{ all -> 0x0027 }
            monitor-exit(r1);	 Catch:{ all -> 0x0027 }
            r1 = r0.iterator();
        L_0x0015:
            r0 = r1.hasNext();
            if (r0 == 0) goto L_0x0008;
        L_0x001b:
            r0 = r1.next();
            r0 = (rx.internal.operators.OperatorWindowWithStartEndObservable.SerializedSubject) r0;
            r0 = r0.consumer;
            r0.onNext(r4);
            goto L_0x0015;
        L_0x0027:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0027 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorWindowWithStartEndObservable.SourceSubscriber.onNext(java.lang.Object):void");
        }

        public final void onStart() {
            request(Long.MAX_VALUE);
        }
    }

    public OperatorWindowWithStartEndObservable(Observable<? extends U> observable, Func1<? super U, ? extends Observable<? extends V>> func1) {
        this.windowOpenings = observable;
        this.windowClosingSelector = func1;
    }

    public final Subscriber<? super T> call(Subscriber<? super Observable<T>> subscriber) {
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        subscriber.add(compositeSubscription);
        final SourceSubscriber sourceSubscriber = new SourceSubscriber(subscriber, compositeSubscription);
        AnonymousClass1 anonymousClass1 = new Subscriber<U>() {
            public void onCompleted() {
                sourceSubscriber.onCompleted();
            }

            public void onError(Throwable th) {
                sourceSubscriber.onError(th);
            }

            public void onNext(U u) {
                sourceSubscriber.beginWindow(u);
            }

            public void onStart() {
                request(Long.MAX_VALUE);
            }
        };
        compositeSubscription.add(sourceSubscriber);
        compositeSubscription.add(anonymousClass1);
        this.windowOpenings.unsafeSubscribe(anonymousClass1);
        return sourceSubscriber;
    }
}
