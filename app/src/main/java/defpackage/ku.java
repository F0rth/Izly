package defpackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ku {
    public static final ThreadFactory a(String str) {
        return new ku$1(str, new AtomicLong(1));
    }

    public static final void a(String str, ExecutorService executorService) {
        Runtime.getRuntime().addShutdownHook(new Thread(new ku$2(str, executorService, 2, TimeUnit.SECONDS), "Crashlytics Shutdown Hook for " + str));
    }
}
