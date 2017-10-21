package defpackage;

import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

final class lc$3 extends FutureTask<Result> {
    final /* synthetic */ lc a;

    lc$3(lc lcVar, Callable callable) {
        this.a = lcVar;
        super(callable);
    }

    protected final void done() {
        try {
            lc.b(this.a, get());
        } catch (Throwable e) {
            Log.w("AsyncTask", e);
        } catch (ExecutionException e2) {
            throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
        } catch (CancellationException e3) {
            lc.b(this.a, null);
        }
    }
}
