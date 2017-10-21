package defpackage;

import java.io.IOException;
import java.io.InputStream;

final class ky$b extends InputStream {
    final /* synthetic */ ky a;
    private int b;
    private int c;

    private ky$b(ky kyVar, ky$a ky_a) {
        this.a = kyVar;
        this.b = kyVar.b(ky_a.b + 4);
        this.c = ky_a.c;
    }

    public final int read() throws IOException {
        if (this.c == 0) {
            return -1;
        }
        this.a.c.seek((long) this.b);
        int read = this.a.c.read();
        this.b = this.a.b(this.b + 1);
        this.c--;
        return read;
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        ky.b(bArr, "buffer");
        if ((i | i2) < 0 || i2 > bArr.length - i) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (this.c <= 0) {
            return -1;
        } else {
            if (i2 > this.c) {
                i2 = this.c;
            }
            this.a.b(this.b, bArr, i, i2);
            this.b = this.a.b(this.b + i2);
            this.c -= i2;
            return i2;
        }
    }
}
