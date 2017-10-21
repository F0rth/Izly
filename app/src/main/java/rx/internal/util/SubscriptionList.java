package rx.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import rx.Subscription;
import rx.exceptions.Exceptions;

public final class SubscriptionList implements Subscription {
    private LinkedList<Subscription> subscriptions;
    private volatile boolean unsubscribed;

    public SubscriptionList(Subscription subscription) {
        this.subscriptions = new LinkedList();
        this.subscriptions.add(subscription);
    }

    public SubscriptionList(Subscription... subscriptionArr) {
        this.subscriptions = new LinkedList(Arrays.asList(subscriptionArr));
    }

    private static void unsubscribeFromAll(Collection<Subscription> collection) {
        if (collection != null) {
            List list = null;
            for (Subscription unsubscribe : collection) {
                try {
                    unsubscribe.unsubscribe();
                } catch (Throwable th) {
                    List arrayList = list == null ? new ArrayList() : list;
                    arrayList.add(th);
                    list = arrayList;
                }
            }
            Exceptions.throwIfAny(list);
        }
    }

    public final void add(Subscription subscription) {
        if (!subscription.isUnsubscribed()) {
            if (!this.unsubscribed) {
                synchronized (this) {
                    if (!this.unsubscribed) {
                        LinkedList linkedList = this.subscriptions;
                        if (linkedList == null) {
                            linkedList = new LinkedList();
                            this.subscriptions = linkedList;
                        }
                        linkedList.add(subscription);
                        return;
                    }
                }
            }
            subscription.unsubscribe();
        }
    }

    public final void clear() {
        if (!this.unsubscribed) {
            Collection collection;
            synchronized (this) {
                collection = this.subscriptions;
                this.subscriptions = null;
            }
            unsubscribeFromAll(collection);
        }
    }

    public final boolean hasSubscriptions() {
        boolean z = false;
        if (!this.unsubscribed) {
            synchronized (this) {
                if (!(this.unsubscribed || this.subscriptions == null || this.subscriptions.isEmpty())) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final boolean isUnsubscribed() {
        return this.unsubscribed;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void remove(rx.Subscription r3) {
        /*
        r2 = this;
        r0 = r2.unsubscribed;
        if (r0 != 0) goto L_0x000e;
    L_0x0004:
        monitor-enter(r2);
        r0 = r2.subscriptions;	 Catch:{ all -> 0x001a }
        r1 = r2.unsubscribed;	 Catch:{ all -> 0x001a }
        if (r1 != 0) goto L_0x000d;
    L_0x000b:
        if (r0 != 0) goto L_0x000f;
    L_0x000d:
        monitor-exit(r2);	 Catch:{ all -> 0x001a }
    L_0x000e:
        return;
    L_0x000f:
        r0 = r0.remove(r3);	 Catch:{ all -> 0x001a }
        monitor-exit(r2);	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x000e;
    L_0x0016:
        r3.unsubscribe();
        goto L_0x000e;
    L_0x001a:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x001a }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rx.internal.util.SubscriptionList.remove(rx.Subscription):void");
    }

    public final void unsubscribe() {
        if (!this.unsubscribed) {
            synchronized (this) {
                if (this.unsubscribed) {
                    return;
                }
                this.unsubscribed = true;
                Collection collection = this.subscriptions;
                this.subscriptions = null;
                unsubscribeFromAll(collection);
            }
        }
    }
}
