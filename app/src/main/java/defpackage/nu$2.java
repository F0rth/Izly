package defpackage;

import java.io.IOException;

final class nu$2 implements on {
    final /* synthetic */ on a;
    final /* synthetic */ nu b;

    nu$2(nu nuVar, on onVar) {
        this.b = nuVar;
        this.a = onVar;
    }

    public final void close() throws IOException {
        try {
            this.a.close();
            this.b.exit(true);
        } catch (IOException e) {
            throw this.b.exit(e);
        } catch (Throwable th) {
            this.b.exit(false);
        }
    }

    public final long read(nw nwVar, long j) throws IOException {
        this.b.enter();
        try {
            long read = this.a.read(nwVar, j);
            this.b.exit(true);
            return read;
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
        return "AsyncTimeout.source(" + this.a + ")";
    }
}
