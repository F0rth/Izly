package defpackage;

import java.io.IOException;
import java.io.InputStream;

final class og$2 implements on {
    final /* synthetic */ oo a;
    final /* synthetic */ InputStream b;

    og$2(oo ooVar, InputStream inputStream) {
        this.a = ooVar;
        this.b = inputStream;
    }

    public final void close() throws IOException {
        this.b.close();
    }

    public final long read(nw nwVar, long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (j == 0) {
            return 0;
        } else {
            try {
                this.a.throwIfReached();
                oj e = nwVar.e(1);
                int read = this.b.read(e.a, e.c, (int) Math.min(j, (long) (8192 - e.c)));
                if (read == -1) {
                    return -1;
                }
                e.c += read;
                nwVar.b += (long) read;
                return (long) read;
            } catch (AssertionError e2) {
                if (og.a(e2)) {
                    throw new IOException(e2);
                }
                throw e2;
            }
        }
    }

    public final oo timeout() {
        return this.a;
    }

    public final String toString() {
        return "source(" + this.b + ")";
    }
}
