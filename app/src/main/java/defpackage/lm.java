package defpackage;

import android.annotation.TargetApi;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class lm extends ThreadPoolExecutor {
    private static final int a;
    private static final int b;
    private static final int c = ((a * 2) + 1);

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        a = availableProcessors;
        b = availableProcessors + 1;
    }

    private <T extends Runnable & ld & ln & lk> lm(int i, int i2, long j, TimeUnit timeUnit, le<T> leVar, ThreadFactory threadFactory) {
        super(i, i2, 1, timeUnit, leVar, threadFactory);
        prestartAllCoreThreads();
    }

    public static lm a() {
        return new lm(b, c, 1, TimeUnit.SECONDS, new le(), new lm$a(10));
    }

    protected final void afterExecute(Runnable runnable, Throwable th) {
        ln lnVar = (ln) runnable;
        lnVar.setFinished(true);
        lnVar.setError(th);
        ((le) super.getQueue()).a();
        super.afterExecute(runnable, th);
    }

    @TargetApi(9)
    public final void execute(Runnable runnable) {
        if (ll.isProperDelegate(runnable)) {
            super.execute(runnable);
        } else {
            super.execute(newTaskFor(runnable, null));
        }
    }

    public final /* bridge */ /* synthetic */ BlockingQueue getQueue() {
        return (le) super.getQueue();
    }

    protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new lj(runnable, t);
    }

    protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new lj(callable);
    }
}
