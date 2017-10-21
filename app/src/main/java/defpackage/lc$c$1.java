package defpackage;

final class lc$c$1 implements Runnable {
    final /* synthetic */ Runnable a;
    final /* synthetic */ lc$c b;

    lc$c$1(lc$c lc_c, Runnable runnable) {
        this.b = lc_c;
        this.a = runnable;
    }

    public final void run() {
        try {
            this.a.run();
        } finally {
            this.b.a();
        }
    }
}
