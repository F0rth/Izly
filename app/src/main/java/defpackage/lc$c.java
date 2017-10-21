package defpackage;

import java.util.LinkedList;
import java.util.concurrent.Executor;

final class lc$c implements Executor {
    final LinkedList<Runnable> a;
    Runnable b;

    private lc$c() {
        this.a = new LinkedList();
    }

    protected final void a() {
        synchronized (this) {
            Runnable runnable = (Runnable) this.a.poll();
            this.b = runnable;
            if (runnable != null) {
                lc.b.execute(this.b);
            }
        }
    }

    public final void execute(Runnable runnable) {
        synchronized (this) {
            this.a.offer(new lc$c$1(this, runnable));
            if (this.b == null) {
                a();
            }
        }
    }
}
