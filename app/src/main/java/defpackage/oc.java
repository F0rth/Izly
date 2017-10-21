package defpackage;

import java.io.IOException;

public abstract class oc implements on {
    private final on delegate;

    public oc(on onVar) {
        if (onVar == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.delegate = onVar;
    }

    public void close() throws IOException {
        this.delegate.close();
    }

    public final on delegate() {
        return this.delegate;
    }

    public long read(nw nwVar, long j) throws IOException {
        return this.delegate.read(nwVar, j);
    }

    public oo timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }
}
