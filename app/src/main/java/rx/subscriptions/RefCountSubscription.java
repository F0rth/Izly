package rx.subscriptions;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import rx.Subscription;

public final class RefCountSubscription implements Subscription {
    static final State EMPTY_STATE = new State(false, 0);
    private final Subscription actual;
    final AtomicReference<State> state = new AtomicReference(EMPTY_STATE);

    static final class InnerSubscription extends AtomicInteger implements Subscription {
        final RefCountSubscription parent;

        public InnerSubscription(RefCountSubscription refCountSubscription) {
            this.parent = refCountSubscription;
        }

        public final boolean isUnsubscribed() {
            return get() != 0;
        }

        public final void unsubscribe() {
            if (compareAndSet(0, 1)) {
                this.parent.unsubscribeAChild();
            }
        }
    }

    static final class State {
        final int children;
        final boolean isUnsubscribed;

        State(boolean z, int i) {
            this.isUnsubscribed = z;
            this.children = i;
        }

        final State addChild() {
            return new State(this.isUnsubscribed, this.children + 1);
        }

        final State removeChild() {
            return new State(this.isUnsubscribed, this.children - 1);
        }

        final State unsubscribe() {
            return new State(true, this.children);
        }
    }

    public RefCountSubscription(Subscription subscription) {
        if (subscription == null) {
            throw new IllegalArgumentException("s");
        }
        this.actual = subscription;
    }

    private void unsubscribeActualIfApplicable(State state) {
        if (state.isUnsubscribed && state.children == 0) {
            this.actual.unsubscribe();
        }
    }

    public final Subscription get() {
        AtomicReference atomicReference = this.state;
        State state;
        do {
            state = (State) atomicReference.get();
            if (state.isUnsubscribed) {
                return Subscriptions.unsubscribed();
            }
        } while (!atomicReference.compareAndSet(state, state.addChild()));
        return new InnerSubscription(this);
    }

    public final boolean isUnsubscribed() {
        return ((State) this.state.get()).isUnsubscribed;
    }

    public final void unsubscribe() {
        State unsubscribe;
        AtomicReference atomicReference = this.state;
        State state;
        do {
            state = (State) atomicReference.get();
            if (!state.isUnsubscribed) {
                unsubscribe = state.unsubscribe();
            } else {
                return;
            }
        } while (!atomicReference.compareAndSet(state, unsubscribe));
        unsubscribeActualIfApplicable(unsubscribe);
    }

    final void unsubscribeAChild() {
        State removeChild;
        AtomicReference atomicReference = this.state;
        State state;
        do {
            state = (State) atomicReference.get();
            removeChild = state.removeChild();
        } while (!atomicReference.compareAndSet(state, removeChild));
        unsubscribeActualIfApplicable(removeChild);
    }
}
