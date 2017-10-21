package defpackage;

import java.io.IOException;
import java.util.zip.Deflater;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

public final class oa implements om {
    private final nx a;
    private final Deflater b;
    private boolean c;

    private oa(nx nxVar, Deflater deflater) {
        if (nxVar == null) {
            throw new IllegalArgumentException("source == null");
        } else if (deflater == null) {
            throw new IllegalArgumentException("inflater == null");
        } else {
            this.a = nxVar;
            this.b = deflater;
        }
    }

    public oa(om omVar, Deflater deflater) {
        this(og.a(omVar), deflater);
    }

    @IgnoreJRERequirement
    private void a(boolean z) throws IOException {
        nw a = this.a.a();
        while (true) {
            oj e = a.e(1);
            int deflate = z ? this.b.deflate(e.a, e.c, 8192 - e.c, 2) : this.b.deflate(e.a, e.c, 8192 - e.c);
            if (deflate > 0) {
                e.c += deflate;
                a.b += (long) deflate;
                this.a.s();
            } else if (this.b.needsInput()) {
                break;
            }
        }
        if (e.b == e.c) {
            a.a = e.a();
            ok.a(e);
        }
    }

    public final void close() throws IOException {
        Throwable th;
        if (!this.c) {
            Throwable th2 = null;
            try {
                this.b.finish();
                a(false);
            } catch (Throwable th3) {
                th2 = th3;
            }
            try {
                this.b.end();
                th3 = th2;
            } catch (Throwable th4) {
                th3 = th4;
                if (th2 != null) {
                    th3 = th2;
                }
            }
            try {
                this.a.close();
            } catch (Throwable th22) {
                if (th3 == null) {
                    th3 = th22;
                }
            }
            this.c = true;
            if (th3 != null) {
                op.a(th3);
            }
        }
    }

    public final void flush() throws IOException {
        a(true);
        this.a.flush();
    }

    public final oo timeout() {
        return this.a.timeout();
    }

    public final String toString() {
        return "DeflaterSink(" + this.a + ")";
    }

    public final void write(nw nwVar, long j) throws IOException {
        op.a(nwVar.b, 0, j);
        while (j > 0) {
            oj ojVar = nwVar.a;
            int min = (int) Math.min(j, (long) (ojVar.c - ojVar.b));
            this.b.setInput(ojVar.a, ojVar.b, min);
            a(false);
            nwVar.b -= (long) min;
            ojVar.b += min;
            if (ojVar.b == ojVar.c) {
                nwVar.a = ojVar.a();
                ok.a(ojVar);
            }
            j -= (long) min;
        }
    }
}
