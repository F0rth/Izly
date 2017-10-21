package defpackage;

import java.io.IOException;
import java.io.OutputStream;

final class og$1 implements om {
    final /* synthetic */ oo a;
    final /* synthetic */ OutputStream b;

    og$1(oo ooVar, OutputStream outputStream) {
        this.a = ooVar;
        this.b = outputStream;
    }

    public final void close() throws IOException {
        this.b.close();
    }

    public final void flush() throws IOException {
        this.b.flush();
    }

    public final oo timeout() {
        return this.a;
    }

    public final String toString() {
        return "sink(" + this.b + ")";
    }

    public final void write(nw nwVar, long j) throws IOException {
        op.a(nwVar.b, 0, j);
        while (j > 0) {
            this.a.throwIfReached();
            oj ojVar = nwVar.a;
            int min = (int) Math.min(j, (long) (ojVar.c - ojVar.b));
            this.b.write(ojVar.a, ojVar.b, min);
            ojVar.b += min;
            j -= (long) min;
            nwVar.b -= (long) min;
            if (ojVar.b == ojVar.c) {
                nwVar.a = ojVar.a();
                ok.a(ojVar);
            }
        }
    }
}
