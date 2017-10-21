package defpackage;

import android.os.Process;

final class lc$2 extends lc$e<Params, Result> {
    final /* synthetic */ lc a;

    lc$2(lc lcVar) {
        this.a = lcVar;
        super();
    }

    public final Result call() throws Exception {
        this.a.n.set(true);
        Process.setThreadPriority(10);
        return this.a.c(this.a.a(this.b));
    }
}
