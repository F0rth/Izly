package defpackage;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class lc$1 implements ThreadFactory {
    private final AtomicInteger a = new AtomicInteger(1);

    lc$1() {
    }

    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, "AsyncTask #" + this.a.getAndIncrement());
    }
}
