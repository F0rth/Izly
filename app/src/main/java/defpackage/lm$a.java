package defpackage;

import java.util.concurrent.ThreadFactory;

public final class lm$a implements ThreadFactory {
    private final int a = 10;

    public lm$a(int i) {
    }

    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setPriority(this.a);
        thread.setName("Queue");
        return thread;
    }
}
