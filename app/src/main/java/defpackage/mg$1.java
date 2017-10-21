package defpackage;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class mg$1 extends mg$a<mg> {
    final /* synthetic */ InputStream a;
    final /* synthetic */ OutputStream b;
    final /* synthetic */ mg c;

    mg$1(mg mgVar, Closeable closeable, boolean z, InputStream inputStream, OutputStream outputStream) {
        this.c = mgVar;
        this.a = inputStream;
        this.b = outputStream;
        super(closeable, z);
    }

    public final /* synthetic */ Object a() throws mg$c, IOException {
        byte[] bArr = new byte[this.c.j];
        while (true) {
            int read = this.a.read(bArr);
            if (read == -1) {
                return this.c;
            }
            this.b.write(bArr, 0, read);
        }
    }
}
