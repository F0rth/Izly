package defpackage;

final class nu$a extends Thread {
    public nu$a() {
        super("Okio Watchdog");
        setDaemon(true);
    }

    public final void run() {
        while (true) {
            try {
                nu awaitTimeout = nu.awaitTimeout();
                if (awaitTimeout != null) {
                    awaitTimeout.timedOut();
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
