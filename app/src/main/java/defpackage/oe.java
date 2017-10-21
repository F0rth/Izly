package defpackage;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public final class oe implements on {
    private int a = 0;
    private final ny b;
    private final Inflater c;
    private final of d;
    private final CRC32 e = new CRC32();

    public oe(on onVar) {
        if (onVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.c = new Inflater(true);
        this.b = og.a(onVar);
        this.d = new of(this.b, this.c);
    }

    private static void a(String str, int i, int i2) throws IOException {
        if (i2 != i) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", new Object[]{str, Integer.valueOf(i2), Integer.valueOf(i)}));
        }
    }

    private void a(nw nwVar, long j, long j2) {
        oj ojVar = nwVar.a;
        while (j >= ((long) (ojVar.c - ojVar.b))) {
            j -= (long) (ojVar.c - ojVar.b);
            ojVar = ojVar.f;
        }
        while (j2 > 0) {
            int i = (int) (((long) ojVar.b) + j);
            int min = (int) Math.min((long) (ojVar.c - i), j2);
            this.e.update(ojVar.a, i, min);
            j2 -= (long) min;
            ojVar = ojVar.f;
            j = 0;
        }
    }

    public final void close() throws IOException {
        this.d.close();
    }

    public final long read(nw nwVar, long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (j == 0) {
            return 0;
        } else {
            if (this.a == 0) {
                long a;
                this.b.a(10);
                byte c = this.b.a().c(3);
                Object obj = ((c >> 1) & 1) == 1 ? 1 : null;
                if (obj != null) {
                    a(this.b.a(), 0, 10);
                }
                oe.a("ID1ID2", 8075, this.b.g());
                this.b.g(8);
                if (((c >> 2) & 1) == 1) {
                    this.b.a(2);
                    if (obj != null) {
                        a(this.b.a(), 0, 2);
                    }
                    short i = this.b.a().i();
                    this.b.a((long) i);
                    if (obj != null) {
                        a(this.b.a(), 0, (long) i);
                    }
                    this.b.g((long) i);
                }
                if (((c >> 3) & 1) == 1) {
                    a = this.b.a((byte) 0);
                    if (a == -1) {
                        throw new EOFException();
                    }
                    if (obj != null) {
                        a(this.b.a(), 0, 1 + a);
                    }
                    this.b.g(1 + a);
                }
                if (((c >> 4) & 1) == 1) {
                    a = this.b.a((byte) 0);
                    if (a == -1) {
                        throw new EOFException();
                    }
                    if (obj != null) {
                        a(this.b.a(), 0, 1 + a);
                    }
                    this.b.g(1 + a);
                }
                if (obj != null) {
                    oe.a("FHCRC", this.b.i(), (short) ((int) this.e.getValue()));
                    this.e.reset();
                }
                this.a = 1;
            }
            if (this.a == 1) {
                long j2 = nwVar.b;
                long read = this.d.read(nwVar, j);
                if (read != -1) {
                    a(nwVar, j2, read);
                    return read;
                }
                this.a = 2;
            }
            if (this.a == 2) {
                oe.a("CRC", this.b.j(), (int) this.e.getValue());
                oe.a("ISIZE", this.b.j(), this.c.getTotalOut());
                this.a = 3;
                if (!this.b.c()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1;
        }
    }

    public final oo timeout() {
        return this.b.timeout();
    }
}
