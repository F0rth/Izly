package defpackage;

public final class i implements Runnable {
    private final nr a;

    public i(nr nrVar) {
        this.a = nrVar;
    }

    public final void run() {
        ov.d();
        ov.e();
        synchronized (this.a) {
            this.a.g = null;
        }
    }
}
