package rx.internal.util;

import rx.Subscription;

public class SynchronizedSubscription implements Subscription {
    private final Subscription s;

    public SynchronizedSubscription(Subscription subscription) {
        this.s = subscription;
    }

    public boolean isUnsubscribed() {
        boolean isUnsubscribed;
        synchronized (this) {
            isUnsubscribed = this.s.isUnsubscribed();
        }
        return isUnsubscribed;
    }

    public void unsubscribe() {
        synchronized (this) {
            this.s.unsubscribe();
        }
    }
}
