package defpackage;

import java.io.OutputStream;

public final class nw$1 extends OutputStream {
    final /* synthetic */ nw a;

    public nw$1(nw nwVar) {
        this.a = nwVar;
    }

    public final void close() {
    }

    public final void flush() {
    }

    public final String toString() {
        return this + ".outputStream()";
    }

    public final void write(int i) {
        this.a.b((byte) i);
    }

    public final void write(byte[] bArr, int i, int i2) {
        this.a.b(bArr, i, i2);
    }
}
