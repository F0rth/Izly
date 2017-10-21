package rx.subscriptions;

import java.util.concurrent.atomic.AtomicReference;
import rx.Subscription;

public final class MultipleAssignmentSubscription implements Subscription {
    final AtomicReference<State> state = new AtomicReference(new State(false, Subscriptions.empty()));

    static final class State {
        final boolean isUnsubscribed;
        final Subscription subscription;

        State(boolean z, Subscription subscription) {
            this.isUnsubscribed = z;
            this.subscription = subscription;
        }

        final State set(Subscription subscription) {
            return new State(this.isUnsubscribed, subscription);
        }

        final State unsubscribe() {
            return new State(true, this.subscription);
        }
    }

    public final Subscription get() {
        return ((State) this.state.get()).subscription;
    }

    public final boolean isUnsubscribed() {
        return ((State) this.state.get()).isUnsubscribed;
    }

    public final void set(Subscription subscription) {
        if (subscription == null) {
            throw new IllegalArgumentException("Subscription can not be null");
        }
        AtomicReference atomicReference = this.state;
        State state;
        do {
            state = (State) atomicReference.get();
            if (state.isUnsubscribed) {
                subscription.unsubscribe();
                return;
            }
        } while (!atomicReference.compareAndSet(state, state.set(subscription)));
    }

    public final void unsubscribe() {
        State state;
        AtomicReference atomicReference = this.state;
        do {
            state = (State) atomicReference.get();
            if (state.isUnsubscribed) {
                return;
            }
        } while (!atomicReference.compareAndSet(state, state.unsubscribe()));
        state.subscription.unsubscribe();
    }
}
