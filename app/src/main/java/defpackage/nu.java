package defpackage;

import java.io.IOException;
import java.io.InterruptedIOException;

public class nu extends oo {
    private static final int TIMEOUT_WRITE_SIZE = 65536;
    private static nu head;
    private boolean inQueue;
    private nu next;
    private long timeoutAt;

    static nu awaitTimeout() throws InterruptedException {
        nu nuVar = null;
        synchronized (nu.class) {
            Object obj;
            try {
                obj = head.next;
                if (obj == null) {
                    obj.wait();
                } else {
                    long remainingNanos = obj.remainingNanos(System.nanoTime());
                    if (remainingNanos > 0) {
                        long j = remainingNanos / 1000000;
                        nu.class.wait(j, (int) (remainingNanos - (1000000 * j)));
                    } else {
                        head.next = obj.next;
                        obj.next = null;
                        nuVar = obj;
                    }
                }
            } finally {
                obj = nu.class;
            }
        }
        return nuVar;
    }

    private static boolean cancelScheduledTimeout(nu nuVar) {
        boolean z;
        synchronized (nu.class) {
            try {
                for (nu nuVar2 = head; nuVar2 != null; nuVar2 = nuVar2.next) {
                    if (nuVar2.next == nuVar) {
                        nuVar2.next = nuVar.next;
                        nuVar.next = null;
                        z = false;
                        break;
                    }
                }
                z = true;
            } catch (Throwable th) {
                Class cls = nu.class;
            }
        }
        return z;
    }

    private long remainingNanos(long j) {
        return this.timeoutAt - j;
    }

    private static void scheduleTimeout(nu nuVar, long j, boolean z) {
        synchronized (nu.class) {
            try {
                if (head == null) {
                    head = new nu();
                    new nu$a().start();
                }
                long nanoTime = System.nanoTime();
                if (j != 0 && z) {
                    nuVar.timeoutAt = Math.min(j, nuVar.deadlineNanoTime() - nanoTime) + nanoTime;
                } else if (j != 0) {
                    nuVar.timeoutAt = nanoTime + j;
                } else if (z) {
                    nuVar.timeoutAt = nuVar.deadlineNanoTime();
                } else {
                    throw new AssertionError();
                }
                long remainingNanos = nuVar.remainingNanos(nanoTime);
                nu nuVar2 = head;
                while (nuVar2.next != null && remainingNanos >= nuVar2.next.remainingNanos(nanoTime)) {
                    nuVar2 = nuVar2.next;
                }
                nuVar.next = nuVar2.next;
                nuVar2.next = nuVar;
                if (nuVar2 == head) {
                    nuVar2.notify();
                }
            } finally {
                Class cls = nu.class;
            }
        }
    }

    public final void enter() {
        if (this.inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long timeoutNanos = timeoutNanos();
        boolean hasDeadline = hasDeadline();
        if (timeoutNanos != 0 || hasDeadline) {
            this.inQueue = true;
            nu.scheduleTimeout(this, timeoutNanos, hasDeadline);
        }
    }

    final IOException exit(IOException iOException) throws IOException {
        return !exit() ? iOException : newTimeoutException(iOException);
    }

    final void exit(boolean z) throws IOException {
        if (exit() && z) {
            throw newTimeoutException(null);
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return nu.cancelScheduledTimeout(this);
    }

    protected IOException newTimeoutException(IOException iOException) {
        IOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    public final om sink(om omVar) {
        return new nu$1(this, omVar);
    }

    public final on source(on onVar) {
        return new nu$2(this, onVar);
    }

    protected void timedOut() {
    }
}
