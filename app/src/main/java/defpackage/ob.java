package defpackage;

import java.io.IOException;

public abstract class ob implements om {
    private final om delegate;

    public ob(om omVar) {
        if (omVar == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.delegate = omVar;
    }

    public void close() throws IOException {
        this.delegate.close();
    }

    public final om delegate() {
        return this.delegate;
    }

    public void flush() throws IOException {
        this.delegate.flush();
    }

    public oo timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }

    public void write(nw nwVar, long j) throws IOException {
        this.delegate.write(nwVar, j);
    }
}
