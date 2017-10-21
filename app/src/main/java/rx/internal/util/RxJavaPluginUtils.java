package rx.internal.util;

import rx.plugins.RxJavaPlugins;

public final class RxJavaPluginUtils {
    public static void handleException(Throwable th) {
        try {
            RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
        } catch (Throwable th2) {
            handlePluginException(th2);
        }
    }

    private static void handlePluginException(Throwable th) {
        System.err.println("RxJavaErrorHandler threw an Exception. It shouldn't. => " + th.getMessage());
        th.printStackTrace();
    }
}
