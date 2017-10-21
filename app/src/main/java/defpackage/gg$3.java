package defpackage;

public final class gg$3 implements Runnable {
    final /* synthetic */ gg a;

    public gg$3(gg ggVar) {
        this.a = ggVar;
    }

    public final void run() {
        if (gg.e(this.a) != null) {
            gg.e(this.a).remove(this);
            this.a.a();
            this.a.onLocationChanged(null);
        }
    }
}
