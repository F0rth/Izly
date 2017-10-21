package defpackage;

import java.io.InputStream;
import org.spongycastle.crypto.tls.CipherSuite;

final class nw$2 extends InputStream {
    final /* synthetic */ nw a;

    nw$2(nw nwVar) {
        this.a = nwVar;
    }

    public final int available() {
        return (int) Math.min(this.a.b, 2147483647L);
    }

    public final void close() {
    }

    public final int read() {
        return this.a.b > 0 ? this.a.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV : -1;
    }

    public final int read(byte[] bArr, int i, int i2) {
        return this.a.a(bArr, i, i2);
    }

    public final String toString() {
        return this.a + ".inputStream()";
    }
}
