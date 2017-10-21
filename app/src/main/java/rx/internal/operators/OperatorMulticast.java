package rx.internal.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func0;
import rx.observables.ConnectableObservable;
import rx.subjects.Subject;

public final class OperatorMulticast<T, R> extends ConnectableObservable<R> {
    final AtomicReference<Subject<? super T, ? extends R>> connectedSubject;
    final Object guard;
    Subscription guardedSubscription;
    final Observable<? extends T> source;
    final Func0<? extends Subject<? super T, ? extends R>> subjectFactory;
    Subscriber<T> subscription;
    final List<Subscriber<? super R>> waitingForConnect;

    class AnonymousClass1 implements Observable$OnSubscribe<R> {
        final /* synthetic */ AtomicReference val$connectedSubject;
        final /* synthetic */ Object val$guard;
        final /* synthetic */ List val$waitingForConnect;

        AnonymousClass1(Object obj, AtomicReference atomicReference, List list) {
            this.val$guard = obj;
            this.val$connectedSubject = atomicReference;
            this.val$waitingForConnect = list;
        }

        public void call(Subscriber<? super R> subscriber) {
            synchronized (this.val$guard) {
                if (this.val$connectedSubject.get() == null) {
                    this.val$waitingForConnect.add(subscriber);
                } else {
                    ((Subject) this.val$connectedSubject.get()).unsafeSubscribe(subscriber);
                }
            }
        }
    }

    private OperatorMulticast(Object obj, AtomicReference<Subject<? super T, ? extends R>> atomicReference, List<Subscriber<? super R>> list, Observable<? extends T> observable, Func0<? extends Subject<? super T, ? extends R>> func0) {
        super(new AnonymousClass1(obj, atomicReference, list));
        this.guard = obj;
        this.connectedSubject = atomicReference;
        this.waitingForConnect = list;
        this.source = observable;
        this.subjectFactory = func0;
    }

    public OperatorMulticast(Observable<? extends T> observable, Func0<? extends Subject<? super T, ? extends R>> func0) {
        this(new Object(), new AtomicReference(), new ArrayList(), observable, func0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void connect(rx.functions.Action1<? super rx.Subscription> r6) {
        /*
        r5 = this;
        r2 = r5.guard;
        monitor-enter(r2);
        r0 = r5.subscription;	 Catch:{ all -> 0x0050 }
        if (r0 == 0) goto L_0x000e;
    L_0x0007:
        r0 = r5.guardedSubscription;	 Catch:{ all -> 0x0050 }
        r6.call(r0);	 Catch:{ all -> 0x0050 }
        monitor-exit(r2);	 Catch:{ all -> 0x0050 }
    L_0x000d:
        return;
    L_0x000e:
        r0 = r5.subjectFactory;	 Catch:{ all -> 0x0050 }
        r0 = r0.call();	 Catch:{ all -> 0x0050 }
        r0 = (rx.subjects.Subject) r0;	 Catch:{ all -> 0x0050 }
        r1 = rx.observers.Subscribers.from(r0);	 Catch:{ all -> 0x0050 }
        r5.subscription = r1;	 Catch:{ all -> 0x0050 }
        r1 = new java.util.concurrent.atomic.AtomicReference;	 Catch:{ all -> 0x0050 }
        r1.<init>();	 Catch:{ all -> 0x0050 }
        r3 = new rx.internal.operators.OperatorMulticast$2;	 Catch:{ all -> 0x0050 }
        r3.<init>(r1);	 Catch:{ all -> 0x0050 }
        r3 = rx.subscriptions.Subscriptions.create(r3);	 Catch:{ all -> 0x0050 }
        r1.set(r3);	 Catch:{ all -> 0x0050 }
        r1 = r1.get();	 Catch:{ all -> 0x0050 }
        r1 = (rx.Subscription) r1;	 Catch:{ all -> 0x0050 }
        r5.guardedSubscription = r1;	 Catch:{ all -> 0x0050 }
        r1 = r5.waitingForConnect;	 Catch:{ all -> 0x0050 }
        r3 = r1.iterator();	 Catch:{ all -> 0x0050 }
    L_0x003b:
        r1 = r3.hasNext();	 Catch:{ all -> 0x0050 }
        if (r1 == 0) goto L_0x0053;
    L_0x0041:
        r1 = r3.next();	 Catch:{ all -> 0x0050 }
        r1 = (rx.Subscriber) r1;	 Catch:{ all -> 0x0050 }
        r4 = new rx.internal.operators.OperatorMulticast$3;	 Catch:{ all -> 0x0050 }
        r4.<init>(r1, r1);	 Catch:{ all -> 0x0050 }
        r0.unsafeSubscribe(r4);	 Catch:{ all -> 0x0050 }
        goto L_0x003b;
    L_0x0050:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0050 }
        throw r0;
    L_0x0053:
        r1 = r5.waitingForConnect;	 Catch:{ all -> 0x0050 }
        r1.clear();	 Catch:{ all -> 0x0050 }
        r1 = r5.connectedSubject;	 Catch:{ all -> 0x0050 }
        r1.set(r0);	 Catch:{ all -> 0x0050 }
        monitor-exit(r2);	 Catch:{ all -> 0x0050 }
        r0 = r5.guardedSubscription;
        r6.call(r0);
        r1 = r5.guard;
        monitor-enter(r1);
        r0 = r5.subscription;	 Catch:{ all -> 0x0071 }
        monitor-exit(r1);	 Catch:{ all -> 0x0071 }
        if (r0 == 0) goto L_0x000d;
    L_0x006b:
        r1 = r5.source;
        r1.subscribe(r0);
        goto L_0x000d;
    L_0x0071:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0071 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.operators.OperatorMulticast.connect(rx.functions.Action1):void");
    }
}
