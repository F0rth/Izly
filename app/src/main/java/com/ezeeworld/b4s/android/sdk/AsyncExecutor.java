package com.ezeeworld.b4s.android.sdk;

public final class AsyncExecutor {
    private static final de.greenrobot.event.util.AsyncExecutor a = de.greenrobot.event.util.AsyncExecutor.builder().eventBus(EventBus.get()).build();

    public interface RunnableEx extends de.greenrobot.event.util.AsyncExecutor.RunnableEx {
    }

    public static de.greenrobot.event.util.AsyncExecutor get() {
        return a;
    }
}
