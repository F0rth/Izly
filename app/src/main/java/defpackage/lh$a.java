package defpackage;

import java.util.concurrent.Executor;

final class lh$a<Result> implements Executor {
    private final Executor a;
    private final lh b;

    public lh$a(Executor executor, lh lhVar) {
        this.a = executor;
        this.b = lhVar;
    }

    public final void execute(Runnable runnable) {
        this.a.execute(new lh$a$1(this, runnable, null));
    }
}
