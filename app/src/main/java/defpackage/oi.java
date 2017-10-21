package defpackage;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

final class oi implements ny {
    public final nw a = new nw();
    public final on b;
    boolean c;

    oi(on onVar) {
        if (onVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.b = onVar;
    }

    public final long a(byte b) throws IOException {
        long j = 0;
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long a = this.a.a(b, j);
            if (a != -1) {
                return a;
            }
            a = this.a.b;
            if (this.b.read(this.a, 8192) == -1) {
                return -1;
            }
            j = Math.max(j, a);
        }
    }

    public final long a(om omVar) throws IOException {
        long j = 0;
        while (this.b.read(this.a, 8192) != -1) {
            long e = this.a.e();
            if (e > 0) {
                j += e;
                omVar.write(this.a, e);
            }
        }
        if (this.a.b <= 0) {
            return j;
        }
        j += this.a.b;
        omVar.write(this.a, this.a.b);
        return j;
    }

    public final nw a() {
        return this.a;
    }

    public final void a(long j) throws IOException {
        if (!b(j)) {
            throw new EOFException();
        }
    }

    public final boolean b(long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.c) {
            throw new IllegalStateException("closed");
        } else {
            while (this.a.b < j) {
                if (this.b.read(this.a, 8192) == -1) {
                    return false;
                }
            }
            return true;
        }
    }

    public final boolean c() throws IOException {
        if (!this.c) {
            return this.a.c() && this.b.read(this.a, 8192) == -1;
        } else {
            throw new IllegalStateException("closed");
        }
    }

    public final void close() throws IOException {
        if (!this.c) {
            this.c = true;
            this.b.close();
            this.a.q();
        }
    }

    public final InputStream d() {
        return new oi$1(this);
    }

    public final nz d(long j) throws IOException {
        a(j);
        return this.a.d(j);
    }

    public final byte f() throws IOException {
        a(1);
        return this.a.f();
    }

    public final byte[] f(long j) throws IOException {
        a(j);
        return this.a.f(j);
    }

    public final short g() throws IOException {
        a(2);
        return this.a.g();
    }

    public final void g(long j) throws IOException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (j > 0) {
            if (this.a.b == 0 && this.b.read(this.a, 8192) == -1) {
                throw new EOFException();
            }
            long min = Math.min(j, this.a.b);
            this.a.g(min);
            j -= min;
        }
    }

    public final int h() throws IOException {
        a(4);
        return this.a.h();
    }

    public final short i() throws IOException {
        a(2);
        return op.a(this.a.g());
    }

    public final int j() throws IOException {
        a(4);
        return op.a(this.a.h());
    }

    public final long k() throws IOException {
        a(1);
        int i = 0;
        while (b((long) (i + 1))) {
            byte c = this.a.c((long) i);
            if ((c < (byte) 48 || c > (byte) 57) && !(i == 0 && c == (byte) 45)) {
                if (i == 0) {
                    throw new NumberFormatException(String.format("Expected leading [0-9] or '-' character but was %#x", new Object[]{Byte.valueOf(c)}));
                }
                return this.a.k();
            }
            i++;
        }
        return this.a.k();
    }

    public final long l() throws IOException {
        a(1);
        for (int i = 0; b((long) (i + 1)); i++) {
            byte c = this.a.c((long) i);
            if ((c < (byte) 48 || c > (byte) 57) && ((c < (byte) 97 || c > (byte) 102) && (c < (byte) 65 || c > (byte) 70))) {
                if (i == 0) {
                    throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", new Object[]{Byte.valueOf(c)}));
                }
                return this.a.l();
            }
        }
        return this.a.l();
    }

    public final String o() throws IOException {
        long a = a((byte) 10);
        if (a != -1) {
            return this.a.e(a);
        }
        nw nwVar = new nw();
        this.a.a(nwVar, 0, Math.min(32, this.a.b));
        throw new EOFException("\\n not found: size=" + this.a.b + " content=" + nwVar.m().c() + "…");
    }

    public final byte[] p() throws IOException {
        this.a.a(this.b);
        return this.a.p();
    }

    public final long read(nw nwVar, long j) throws IOException {
        if (nwVar == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.c) {
            throw new IllegalStateException("closed");
        } else if (this.a.b == 0 && this.b.read(this.a, 8192) == -1) {
            return -1;
        } else {
            return this.a.read(nwVar, Math.min(j, this.a.b));
        }
    }

    public final oo timeout() {
        return this.b.timeout();
    }

    public final String toString() {
        return "buffer(" + this.b + ")";
    }
}
