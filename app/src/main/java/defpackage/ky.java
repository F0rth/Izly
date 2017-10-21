package defpackage;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.tls.CipherSuite;

public class ky implements Closeable {
    private static final Logger b = Logger.getLogger(ky.class.getName());
    int a;
    private final RandomAccessFile c;
    private int d;
    private ky$a e;
    private ky$a f;
    private final byte[] g = new byte[16];

    public ky(File file) throws IOException {
        if (!file.exists()) {
            File file2 = new File(file.getPath() + ".tmp");
            RandomAccessFile a = ky.a(file2);
            try {
                a.setLength(4096);
                a.seek(0);
                byte[] bArr = new byte[16];
                ky.a(bArr, PKIFailureInfo.certConfirmed, 0, 0, 0);
                a.write(bArr);
                if (!file2.renameTo(file)) {
                    throw new IOException("Rename failed!");
                }
            } finally {
                a.close();
            }
        }
        this.c = ky.a(file);
        this.c.seek(0);
        this.c.readFully(this.g);
        this.a = ky.a(this.g, 0);
        if (((long) this.a) > this.c.length()) {
            throw new IOException("File is truncated. Expected length: " + this.a + ", Actual length: " + this.c.length());
        }
        this.d = ky.a(this.g, 4);
        int a2 = ky.a(this.g, 8);
        int a3 = ky.a(this.g, 12);
        this.e = a(a2);
        this.f = a(a3);
    }

    private static int a(byte[] bArr, int i) {
        return ((((bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) + ((bArr[i + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) + ((bArr[i + 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) + (bArr[i + 3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    private static RandomAccessFile a(File file) throws FileNotFoundException {
        return new RandomAccessFile(file, "rwd");
    }

    private ky$a a(int i) throws IOException {
        if (i == 0) {
            return ky$a.a;
        }
        this.c.seek((long) i);
        return new ky$a(i, this.c.readInt());
    }

    private void a(int i, int i2, int i3, int i4) throws IOException {
        ky.a(this.g, i, i2, i3, i4);
        this.c.seek(0);
        this.c.write(this.g);
    }

    private void a(int i, byte[] bArr, int i2, int i3) throws IOException {
        int b = b(i);
        if (b + i3 <= this.a) {
            this.c.seek((long) b);
            this.c.write(bArr, i2, i3);
            return;
        }
        int i4 = this.a - b;
        this.c.seek((long) b);
        this.c.write(bArr, i2, i4);
        this.c.seek(16);
        this.c.write(bArr, i2 + i4, i3 - i4);
    }

    private static void a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >> 24);
        bArr[i + 1] = (byte) (i2 >> 16);
        bArr[i + 2] = (byte) (i2 >> 8);
        bArr[i + 3] = (byte) i2;
    }

    private static void a(byte[] bArr, int... iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            ky.a(bArr, i, iArr[i2]);
            i += 4;
        }
    }

    private int b(int i) {
        return i < this.a ? i : (i + 16) - this.a;
    }

    private static <T> T b(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    private void b(int i, byte[] bArr, int i2, int i3) throws IOException {
        int b = b(i);
        if (b + i3 <= this.a) {
            this.c.seek((long) b);
            this.c.readFully(bArr, i2, i3);
            return;
        }
        int i4 = this.a - b;
        this.c.seek((long) b);
        this.c.readFully(bArr, i2, i4);
        this.c.seek(16);
        this.c.readFully(bArr, i2 + i4, i3 - i4);
    }

    private void b(byte[] bArr, int i, int i2) throws IOException {
        synchronized (this) {
            ky.b(bArr, "buffer");
            if ((i2 | 0) < 0 || i2 > bArr.length + 0) {
                throw new IndexOutOfBoundsException();
            }
            c(i2);
            boolean b = b();
            ky$a ky_a = new ky$a(b ? 16 : b((this.f.b + 4) + this.f.c), i2);
            ky.a(this.g, 0, i2);
            a(ky_a.b, this.g, 0, 4);
            a(ky_a.b + 4, bArr, 0, i2);
            a(this.a, this.d + 1, b ? ky_a.b : this.e.b, ky_a.b);
            this.f = ky_a;
            this.d++;
            if (b) {
                this.e = this.f;
            }
        }
    }

    private void c(int i) throws IOException {
        int i2 = i + 4;
        int a = this.a - a();
        if (a < i2) {
            int i3 = this.a;
            do {
                a += i3;
                i3 <<= 1;
            } while (a < i2);
            d(i3);
            i2 = b((this.f.b + 4) + this.f.c);
            if (i2 < this.e.b) {
                FileChannel channel = this.c.getChannel();
                channel.position((long) this.a);
                int i4 = i2 - 4;
                if (channel.transferTo(16, (long) i4, channel) != ((long) i4)) {
                    throw new AssertionError("Copied insufficient number of bytes!");
                }
            }
            if (this.f.b < this.e.b) {
                a = (this.a + this.f.b) - 16;
                a(i3, this.d, this.e.b, a);
                this.f = new ky$a(a, this.f.c);
            } else {
                a(i3, this.d, this.e.b, this.f.b);
            }
            this.a = i3;
        }
    }

    private void d() throws IOException {
        synchronized (this) {
            a((int) PKIFailureInfo.certConfirmed, 0, 0, 0);
            this.d = 0;
            this.e = ky$a.a;
            this.f = ky$a.a;
            if (this.a > PKIFailureInfo.certConfirmed) {
                d(PKIFailureInfo.certConfirmed);
            }
            this.a = PKIFailureInfo.certConfirmed;
        }
    }

    private void d(int i) throws IOException {
        this.c.setLength((long) i);
        this.c.getChannel().force(true);
    }

    public final int a() {
        return this.d == 0 ? 16 : this.f.b >= this.e.b ? (((this.f.b - this.e.b) + 4) + this.f.c) + 16 : (((this.f.b + 4) + this.f.c) + this.a) - this.e.b;
    }

    public final void a(ky$c ky_c) throws IOException {
        synchronized (this) {
            int i = this.e.b;
            for (int i2 = 0; i2 < this.d; i2++) {
                ky$a a = a(i);
                ky_c.read(new ky$b(this, a), a.c);
                i = b(a.c + (a.b + 4));
            }
        }
    }

    public final void a(byte[] bArr) throws IOException {
        b(bArr, 0, bArr.length);
    }

    public final boolean b() {
        boolean z;
        synchronized (this) {
            z = this.d == 0;
        }
        return z;
    }

    public final void c() throws IOException {
        synchronized (this) {
            if (b()) {
                throw new NoSuchElementException();
            }
            if (this.d == 1) {
                d();
            } else {
                int b = b((this.e.b + 4) + this.e.c);
                b(b, this.g, 0, 4);
                int a = ky.a(this.g, 0);
                a(this.a, this.d - 1, b, this.f.b);
                this.d--;
                this.e = new ky$a(b, a);
            }
        }
    }

    public void close() throws IOException {
        synchronized (this) {
            this.c.close();
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName()).append('[');
        stringBuilder.append("fileLength=").append(this.a);
        stringBuilder.append(", size=").append(this.d);
        stringBuilder.append(", first=").append(this.e);
        stringBuilder.append(", last=").append(this.f);
        stringBuilder.append(", element lengths=[");
        try {
            a(new ky$1(this, stringBuilder));
        } catch (Throwable e) {
            b.log(Level.WARNING, "read error", e);
        }
        stringBuilder.append("]]");
        return stringBuilder.toString();
    }
}
