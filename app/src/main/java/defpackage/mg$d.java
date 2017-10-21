package defpackage;

import java.io.IOException;
import java.util.concurrent.Callable;

public abstract class mg$d<V> implements Callable<V> {
    protected mg$d() {
    }

    protected abstract V a() throws mg$c, IOException;

    protected abstract void b() throws IOException;

    public V call() throws mg$c {
        Throwable th;
        Object obj = 1;
        try {
            V a = a();
            try {
                b();
                return a;
            } catch (IOException e) {
                throw new mg$c(e);
            }
        } catch (mg$c e2) {
            throw e2;
        } catch (IOException e3) {
            throw new mg$c(e3);
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            b();
        } catch (IOException e4) {
            if (obj == null) {
                throw new mg$c(e4);
            }
        }
        throw th;
    }
}
