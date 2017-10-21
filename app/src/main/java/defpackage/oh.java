package defpackage;

import java.io.IOException;

final class oh implements nx {
    public final nw a = new nw();
    public final om b;
    boolean c;

    oh(om omVar) {
        if (omVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        this.b = omVar;
    }

    public final long a(on onVar) throws IOException {
        if (onVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long read = onVar.read(this.a, 8192);
            if (read == -1) {
                return j;
            }
            j += read;
            s();
        }
    }

    public final nw a() {
        return this.a;
    }

    public final nx b() throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long j = this.a.b;
        if (j > 0) {
            this.b.write(this.a, j);
        }
        return this;
    }

    public final nx b(String str) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.a(str);
        return s();
    }

    public final nx b(nz nzVar) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.a(nzVar);
        return s();
    }

    public final nx b(byte[] bArr) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.a(bArr);
        return s();
    }

    public final nx c(byte[] bArr, int i, int i2) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.b(bArr, i, i2);
        return s();
    }

    public final void close() throws IOException {
        if (!this.c) {
            Throwable th = null;
            try {
                if (this.a.b > 0) {
                    this.b.write(this.a, this.a.b);
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                this.b.close();
            } catch (Throwable th3) {
                if (th == null) {
                    th = th3;
                }
            }
            this.c = true;
            if (th != null) {
                op.a(th);
            }
        }
    }

    public final nx f(int i) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.d(i);
        return s();
    }

    public final void flush() throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        if (this.a.b > 0) {
            this.b.write(this.a, this.a.b);
        }
        this.b.flush();
    }

    public final nx g(int i) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.c(i);
        return s();
    }

    public final nx h(int i) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.b(i);
        return s();
    }

    public final nx j(long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.i(j);
        return s();
    }

    public final nx k(long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.h(j);
        return s();
    }

    public final nx s() throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long e = this.a.e();
        if (e > 0) {
            this.b.write(this.a, e);
        }
        return this;
    }

    public final oo timeout() {
        return this.b.timeout();
    }

    public final String toString() {
        return "buffer(" + this.b + ")";
    }

    public final void write(nw nwVar, long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.a.write(nwVar, j);
        s();
    }
}
