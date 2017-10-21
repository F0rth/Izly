package defpackage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class lc<Params, Progress, Result> {
    private static final int a;
    public static final Executor b = new ThreadPoolExecutor(f, g, 1, TimeUnit.SECONDS, i, h);
    public static final Executor c = new lc$c();
    private static final int f;
    private static final int g = ((a * 2) + 1);
    private static final ThreadFactory h = new lc$1();
    private static final BlockingQueue<Runnable> i = new LinkedBlockingQueue(128);
    private static final lc$b j = new lc$b();
    private static volatile Executor k = c;
    volatile int d = lc$d.a;
    protected final AtomicBoolean e = new AtomicBoolean();
    private final lc$e<Params, Result> l = new lc$2(this);
    private final FutureTask<Result> m = new lc$3(this, this.l);
    private final AtomicBoolean n = new AtomicBoolean();

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        a = availableProcessors;
        f = availableProcessors + 1;
    }

    protected static void b() {
    }

    static /* synthetic */ void b(lc lcVar, Object obj) {
        if (!lcVar.n.get()) {
            lcVar.c(obj);
        }
    }

    private Result c(Result result) {
        j.obtainMessage(1, new lc$a(this, result)).sendToTarget();
        return result;
    }

    static /* synthetic */ void c(lc lcVar, Object obj) {
        if (lcVar.e.get()) {
            lcVar.b(obj);
        } else {
            lcVar.a(obj);
        }
        lcVar.d = lc$d.c;
    }

    public abstract Result a(Params... paramsArr);

    public final lc<Params, Progress, Result> a(Executor executor, Params... paramsArr) {
        if (this.d != lc$d.a) {
            switch (lc$4.a[this.d - 1]) {
                case 1:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case 2:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.d = lc$d.b;
        a();
        this.l.b = paramsArr;
        executor.execute(this.m);
        return this;
    }

    public void a() {
    }

    public void a(Result result) {
    }

    public final boolean a(boolean z) {
        this.e.set(true);
        return this.m.cancel(true);
    }

    public void b(Result result) {
    }
}
