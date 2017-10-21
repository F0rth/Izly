package defpackage;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.Inflater;

public final class of implements on {
    private final ny a;
    private final Inflater b;
    private int c;
    private boolean d;

    of(ny nyVar, Inflater inflater) {
        if (nyVar == null) {
            throw new IllegalArgumentException("source == null");
        } else if (inflater == null) {
            throw new IllegalArgumentException("inflater == null");
        } else {
            this.a = nyVar;
            this.b = inflater;
        }
    }

    public of(on onVar, Inflater inflater) {
        this(og.a(onVar), inflater);
    }

    private void b() throws IOException {
        if (this.c != 0) {
            int remaining = this.c - this.b.getRemaining();
            this.c -= remaining;
            this.a.g((long) remaining);
        }
    }

    public final boolean a() throws IOException {
        if (!this.b.needsInput()) {
            return false;
        }
        b();
        if (this.b.getRemaining() != 0) {
            throw new IllegalStateException("?");
        } else if (this.a.c()) {
            return true;
        } else {
            oj ojVar = this.a.a().a;
            this.c = ojVar.c - ojVar.b;
            this.b.setInput(ojVar.a, ojVar.b, this.c);
            return false;
        }
    }

    public final void close() throws IOException {
        if (!this.d) {
            this.b.end();
            this.d = true;
            this.a.close();
        }
    }

    public final long read(nw nwVar, long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.d) {
            throw new IllegalStateException("closed");
        } else if (j == 0) {
            return 0;
        } else {
            boolean a;
            do {
                a = a();
                try {
                    oj e = nwVar.e(1);
                    int inflate = this.b.inflate(e.a, e.c, 8192 - e.c);
                    if (inflate > 0) {
                        e.c += inflate;
                        nwVar.b += (long) inflate;
                        return (long) inflate;
                    } else if (this.b.finished() || this.b.needsDictionary()) {
                        b();
                        if (e.b == e.c) {
                            nwVar.a = e.a();
                            ok.a(e);
                        }
                        return -1;
                    }
                } catch (Throwable e2) {
                    throw new IOException(e2);
                }
            } while (!a);
            throw new EOFException("source exhausted prematurely");
        }
    }

    public final oo timeout() {
        return this.a.timeout();
    }
}
