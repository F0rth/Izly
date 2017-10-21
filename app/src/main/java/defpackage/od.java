package defpackage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class od extends oo {
    public oo a;

    public od(oo ooVar) {
        if (ooVar == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.a = ooVar;
    }

    public final oo clearDeadline() {
        return this.a.clearDeadline();
    }

    public final oo clearTimeout() {
        return this.a.clearTimeout();
    }

    public final long deadlineNanoTime() {
        return this.a.deadlineNanoTime();
    }

    public final oo deadlineNanoTime(long j) {
        return this.a.deadlineNanoTime(j);
    }

    public final boolean hasDeadline() {
        return this.a.hasDeadline();
    }

    public final void throwIfReached() throws IOException {
        this.a.throwIfReached();
    }

    public final oo timeout(long j, TimeUnit timeUnit) {
        return this.a.timeout(j, timeUnit);
    }

    public final long timeoutNanos() {
        return this.a.timeoutNanos();
    }
}
