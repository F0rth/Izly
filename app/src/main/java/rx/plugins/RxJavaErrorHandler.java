package rx.plugins;

import rx.annotations.Beta;
import rx.exceptions.Exceptions;

public abstract class RxJavaErrorHandler {
    protected static final String ERROR_IN_RENDERING_SUFFIX = ".errorRendering";

    public void handleError(Throwable th) {
    }

    @Beta
    public final String handleOnNextValueRendering(Object obj) {
        try {
            return render(obj);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return obj.getClass().getName() + ERROR_IN_RENDERING_SUFFIX;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            return obj.getClass().getName() + ERROR_IN_RENDERING_SUFFIX;
        }
    }

    @Beta
    protected String render(Object obj) throws InterruptedException {
        return null;
    }
}
