package defpackage;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

final class ku$1 implements ThreadFactory {
    final /* synthetic */ String a;
    final /* synthetic */ AtomicLong b;

    ku$1(String str, AtomicLong atomicLong) {
        this.a = str;
        this.b = atomicLong;
    }

    public final Thread newThread(Runnable runnable) {
        Thread newThread = Executors.defaultThreadFactory().newThread(new ku$1$1(this, runnable));
        newThread.setName(this.a + this.b.getAndIncrement());
        return newThread;
    }
}
