package defpackage;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public abstract class mg$a<V> extends mg$d<V> {
    private final Closeable a;
    private final boolean b;

    protected mg$a(Closeable closeable, boolean z) {
        this.a = closeable;
        this.b = z;
    }

    protected final void b() throws IOException {
        if (this.a instanceof Flushable) {
            ((Flushable) this.a).flush();
        }
        if (this.b) {
            try {
                this.a.close();
                return;
            } catch (IOException e) {
                return;
            }
        }
        this.a.close();
    }
}
