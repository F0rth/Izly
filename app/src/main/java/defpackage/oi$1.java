package defpackage;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.tls.CipherSuite;

final class oi$1 extends InputStream {
    final /* synthetic */ oi a;

    oi$1(oi oiVar) {
        this.a = oiVar;
    }

    public final int available() throws IOException {
        if (!this.a.c) {
            return (int) Math.min(this.a.a.b, 2147483647L);
        }
        throw new IOException("closed");
    }

    public final void close() throws IOException {
        this.a.close();
    }

    public final int read() throws IOException {
        if (!this.a.c) {
            return (this.a.a.b == 0 && this.a.b.read(this.a.a, 8192) == -1) ? -1 : this.a.a.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        } else {
            throw new IOException("closed");
        }
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.a.c) {
            throw new IOException("closed");
        }
        op.a((long) bArr.length, (long) i, (long) i2);
        return (this.a.a.b == 0 && this.a.b.read(this.a.a, 8192) == -1) ? -1 : this.a.a.a(bArr, i, i2);
    }

    public final String toString() {
        return this.a + ".inputStream()";
    }
}
