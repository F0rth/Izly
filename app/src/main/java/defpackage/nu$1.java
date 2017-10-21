package defpackage;

import java.io.IOException;

final class nu$1 implements om {
    final /* synthetic */ om a;
    final /* synthetic */ nu b;

    nu$1(nu nuVar, om omVar) {
        this.b = nuVar;
        this.a = omVar;
    }

    public final void close() throws IOException {
        this.b.enter();
        try {
            this.a.close();
            this.b.exit(true);
        } catch (IOException e) {
            throw this.b.exit(e);
        } catch (Throwable th) {
            this.b.exit(false);
        }
    }

    public final void flush() throws IOException {
        this.b.enter();
        try {
            this.a.flush();
            this.b.exit(true);
        } catch (IOException e) {
            throw this.b.exit(e);
        } catch (Throwable th) {
            this.b.exit(false);
        }
    }

    public final oo timeout() {
        return this.b;
    }

    public final String toString() {
        return "AsyncTimeout.sink(" + this.a + ")";
    }

    public final void write(nw nwVar, long j) throws IOException {
        op.a(nwVar.b, 0, j);
        long j2 = j;
        while (j2 > 0) {
            oj ojVar = nwVar.a;
            long j3 = 0;
            while (j3 < 65536) {
                j3 += (long) (nwVar.a.c - nwVar.a.b);
                if (j3 >= j2) {
                    j3 = j2;
                    break;
                }
            }
            this.b.enter();
            try {
                this.a.write(nwVar, j3);
                j2 -= j3;
                this.b.exit(true);
            } catch (IOException e) {
                throw this.b.exit(e);
            } catch (Throwable th) {
                this.b.exit(false);
            }
        }
    }
}
