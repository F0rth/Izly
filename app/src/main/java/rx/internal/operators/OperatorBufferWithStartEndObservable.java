package rx.internal.operators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import rx.Observable;
import rx.Observable$Operator;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.CompositeSubscription;

public final class OperatorBufferWithStartEndObservable<T, TOpening, TClosing> implements Observable$Operator<List<T>, T> {
    final Func1<? super TOpening, ? extends Observable<? extends TClosing>> bufferClosing;
    final Observable<? extends TOpening> bufferOpening;

    final class BufferingSubscriber extends Subscriber<T> {
        final Subscriber<? super List<T>> child;
        final List<List<T>> chunks = new LinkedList();
        final CompositeSubscription closingSubscriptions = new CompositeSubscription();
        boolean done;

        public BufferingSubscriber(Subscriber<? super List<T>> subscriber) {
            this.child = subscriber;
            add(this.closingSubscriptions);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        final void endBuffer(java.util.List<T> r3) {
            /*
            r2 = this;
            monitor-enter(r2);
            r0 = r2.done;	 Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0007;
        L_0x0005:
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
        L_0x0006:
            return;
        L_0x0007:
            r0 = r2.chunks;	 Catch:{ all -> 0x0028 }
            r1 = r0.iterator();	 Catch:{ all -> 0x0028 }
        L_0x000d:
            r0 = r1.hasNext();	 Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x002b;
        L_0x0013:
            r0 = r1.next();	 Catch:{ all -> 0x0028 }
            r0 = (java.util.List) r0;	 Catch:{ all -> 0x0028 }
            if (r0 != r3) goto L_0x000d;
        L_0x001b:
            r0 = 1;
            r1.remove();	 Catch:{ all -> 0x0028 }
        L_0x001f:
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0006;
        L_0x0022:
            r0 = r2.child;
            r0.onNext(r3);
            goto L_0x0006;
        L_0x0028:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0028 }
            throw r0;
        L_0x002b:
            r0 = 0;
            goto L_0x001f;
            */
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorBufferWithStartEndObservable.BufferingSubscriber.endBuffer(java.util.List):void");
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
            throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorBufferWithStartEndObservable.BufferingSubscriber.onCompleted():void");
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

        public final void onNext(T t) {
            synchronized (this) {
                for (List add : this.chunks) {
                    add.add(t);
                }
            }
        }

        final void startBuffer(TOpening tOpening) {
            final List arrayList = new ArrayList();
            synchronized (this) {
                if (this.done) {
                    return;
                }
                this.chunks.add(arrayList);
                try {
                    Observable observable = (Observable) OperatorBufferWithStartEndObservable.this.bufferClosing.call(tOpening);
                    AnonymousClass1 anonymousClass1 = new Subscriber<TClosing>() {
                        public void onCompleted() {
                            BufferingSubscriber.this.closingSubscriptions.remove(this);
                            BufferingSubscriber.this.endBuffer(arrayList);
                        }

                        public void onError(Throwable th) {
                            BufferingSubscriber.this.onError(th);
                        }

                        public void onNext(TClosing tClosing) {
                            BufferingSubscriber.this.closingSubscriptions.remove(this);
                            BufferingSubscriber.this.endBuffer(arrayList);
                        }
                    };
                    this.closingSubscriptions.add(anonymousClass1);
                    observable.unsafeSubscribe(anonymousClass1);
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, (Observer) this);
                }
            }
        }
    }

    public OperatorBufferWithStartEndObservable(Observable<? extends TOpening> observable, Func1<? super TOpening, ? extends Observable<? extends TClosing>> func1) {
        this.bufferOpening = observable;
        this.bufferClosing = func1;
    }

    public final Subscriber<? super T> call(Subscriber<? super List<T>> subscriber) {
        final BufferingSubscriber bufferingSubscriber = new BufferingSubscriber(new SerializedSubscriber(subscriber));
        AnonymousClass1 anonymousClass1 = new Subscriber<TOpening>() {
            public void onCompleted() {
                bufferingSubscriber.onCompleted();
            }

            public void onError(Throwable th) {
                bufferingSubscriber.onError(th);
            }

            public void onNext(TOpening tOpening) {
                bufferingSubscriber.startBuffer(tOpening);
            }
        };
        subscriber.add(anonymousClass1);
        subscriber.add(bufferingSubscriber);
        this.bufferOpening.unsafeSubscribe(anonymousClass1);
        return bufferingSubscriber;
    }
}
