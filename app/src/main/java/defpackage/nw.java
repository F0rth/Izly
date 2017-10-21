package defpackage;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.tls.CipherSuite;

public final class nw implements Cloneable, nx, ny {
    private static final byte[] c = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102};
    oj a;
    public long b;

    private String a(long j, Charset charset) throws EOFException {
        op.a(this.b, 0, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        } else if (j == 0) {
            return "";
        } else {
            oj ojVar = this.a;
            if (((long) ojVar.b) + j > ((long) ojVar.c)) {
                return new String(f(j), charset);
            }
            String str = new String(ojVar.a, ojVar.b, (int) j, charset);
            ojVar.b = (int) (((long) ojVar.b) + j);
            this.b -= j;
            if (ojVar.b != ojVar.c) {
                return str;
            }
            this.a = ojVar.a();
            ok.a(ojVar);
            return str;
        }
    }

    private void c(byte[] bArr) throws EOFException {
        int i = 0;
        while (i < bArr.length) {
            int a = a(bArr, i, bArr.length - i);
            if (a == -1) {
                throw new EOFException();
            }
            i += a;
        }
    }

    private String l(long j) throws EOFException {
        return a(j, op.a);
    }

    public final int a(byte[] bArr, int i, int i2) {
        op.a((long) bArr.length, (long) i, (long) i2);
        oj ojVar = this.a;
        if (ojVar == null) {
            return -1;
        }
        int min = Math.min(i2, ojVar.c - ojVar.b);
        System.arraycopy(ojVar.a, ojVar.b, bArr, i, min);
        ojVar.b += min;
        this.b -= (long) min;
        if (ojVar.b != ojVar.c) {
            return min;
        }
        this.a = ojVar.a();
        ok.a(ojVar);
        return min;
    }

    public final long a(byte b) {
        return a(b, 0);
    }

    public final long a(byte b, long j) {
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        oj ojVar = this.a;
        if (ojVar == null) {
            return -1;
        }
        if (this.b - j >= j) {
            while (true) {
                long j3 = ((long) (ojVar.c - ojVar.b)) + j2;
                if (j3 >= j) {
                    break;
                }
                ojVar = ojVar.f;
                j2 = j3;
            }
        } else {
            j2 = this.b;
            while (j2 > j) {
                ojVar = ojVar.g;
                j2 -= (long) (ojVar.c - ojVar.b);
            }
        }
        while (j2 < this.b) {
            byte[] bArr = ojVar.a;
            int i = ojVar.c;
            for (int i2 = (int) ((((long) ojVar.b) + j) - j2); i2 < i; i2++) {
                if (bArr[i2] == b) {
                    return ((long) (i2 - ojVar.b)) + j2;
                }
            }
            j = j2 + ((long) (ojVar.c - ojVar.b));
            ojVar = ojVar.f;
            j2 = j;
        }
        return -1;
    }

    public final long a(om omVar) throws IOException {
        long j = this.b;
        if (j > 0) {
            omVar.write(this, j);
        }
        return j;
    }

    public final long a(on onVar) throws IOException {
        if (onVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long read = onVar.read(this, 8192);
            if (read == -1) {
                return j;
            }
            j += read;
        }
    }

    public final String a(Charset charset) {
        try {
            return a(this.b, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final nw a() {
        return this;
    }

    public final nw a(int i) {
        if (i < 128) {
            b(i);
        } else if (i < PKIFailureInfo.wrongIntegrity) {
            b((i >> 6) | 192);
            b((i & 63) | 128);
        } else if (i < PKIFailureInfo.notAuthorized) {
            if (i < 55296 || i > 57343) {
                b((i >> 12) | 224);
                b(((i >> 6) & 63) | 128);
                b((i & 63) | 128);
            } else {
                throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i));
            }
        } else if (i <= 1114111) {
            b((i >> 18) | 240);
            b(((i >> 12) & 63) | 128);
            b(((i >> 6) & 63) | 128);
            b((i & 63) | 128);
        } else {
            throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i));
        }
        return this;
    }

    public final nw a(String str) {
        return a(str, 0, str.length());
    }

    public final nw a(String str, int i, int i2) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + i);
        } else if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        } else if (i2 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i2 + " > " + str.length());
        } else {
            while (i < i2) {
                char charAt = str.charAt(i);
                int i3;
                if (charAt < '') {
                    oj e = e(1);
                    byte[] bArr = e.a;
                    int i4 = e.c - i;
                    int min = Math.min(i2, 8192 - i4);
                    i3 = i + 1;
                    bArr[i4 + i] = (byte) charAt;
                    while (i3 < min) {
                        charAt = str.charAt(i3);
                        if (charAt >= '') {
                            break;
                        }
                        bArr[i3 + i4] = (byte) charAt;
                        i3++;
                    }
                    int i5 = (i3 + i4) - e.c;
                    e.c += i5;
                    this.b += (long) i5;
                    i = i3;
                } else if (charAt < 'ࠀ') {
                    b((charAt >> 6) | 192);
                    b((charAt & 63) | 128);
                    i++;
                } else if (charAt < '?' || charAt > '?') {
                    b((charAt >> 12) | 224);
                    b(((charAt >> 6) & 63) | 128);
                    b((charAt & 63) | 128);
                    i++;
                } else {
                    i3 = i + 1 < i2 ? str.charAt(i + 1) : 0;
                    if (charAt > '?' || i3 < 56320 || i3 > 57343) {
                        b(63);
                        i++;
                    } else {
                        i3 = ((i3 & -56321) | ((charAt & -55297) << 10)) + PKIFailureInfo.notAuthorized;
                        b((i3 >> 18) | 240);
                        b(((i3 >> 12) & 63) | 128);
                        b(((i3 >> 6) & 63) | 128);
                        b((i3 & 63) | 128);
                        i += 2;
                    }
                }
            }
            return this;
        }
    }

    public final nw a(nw nwVar, long j, long j2) {
        if (nwVar == null) {
            throw new IllegalArgumentException("out == null");
        }
        op.a(this.b, j, j2);
        if (j2 != 0) {
            nwVar.b += j2;
            oj ojVar = this.a;
            while (j >= ((long) (ojVar.c - ojVar.b))) {
                j -= (long) (ojVar.c - ojVar.b);
                ojVar = ojVar.f;
            }
            while (j2 > 0) {
                oj ojVar2 = new oj(ojVar);
                ojVar2.b = (int) (((long) ojVar2.b) + j);
                ojVar2.c = Math.min(ojVar2.b + ((int) j2), ojVar2.c);
                if (nwVar.a == null) {
                    ojVar2.g = ojVar2;
                    ojVar2.f = ojVar2;
                    nwVar.a = ojVar2;
                } else {
                    nwVar.a.g.a(ojVar2);
                }
                j2 -= (long) (ojVar2.c - ojVar2.b);
                ojVar = ojVar.f;
                j = 0;
            }
        }
        return this;
    }

    public final nw a(nz nzVar) {
        if (nzVar == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        nzVar.a(this);
        return this;
    }

    public final nw a(byte[] bArr) {
        if (bArr != null) {
            return b(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    public final void a(long j) throws EOFException {
        if (this.b < j) {
            throw new EOFException();
        }
    }

    public final nw b(int i) {
        oj e = e(1);
        byte[] bArr = e.a;
        int i2 = e.c;
        e.c = i2 + 1;
        bArr[i2] = (byte) i;
        this.b++;
        return this;
    }

    public final nw b(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        op.a((long) bArr.length, (long) i, (long) i2);
        int i3 = i + i2;
        while (i < i3) {
            oj e = e(1);
            int min = Math.min(i3 - i, 8192 - e.c);
            System.arraycopy(bArr, i, e.a, e.c, min);
            i += min;
            e.c = min + e.c;
        }
        this.b += (long) i2;
        return this;
    }

    public final nx b() {
        return this;
    }

    public final /* synthetic */ nx b(String str) throws IOException {
        return a(str);
    }

    public final /* synthetic */ nx b(nz nzVar) throws IOException {
        return a(nzVar);
    }

    public final /* synthetic */ nx b(byte[] bArr) throws IOException {
        return a(bArr);
    }

    public final boolean b(long j) {
        return this.b >= j;
    }

    public final byte c(long j) {
        op.a(this.b, j, 1);
        oj ojVar = this.a;
        while (true) {
            int i = ojVar.c - ojVar.b;
            if (j < ((long) i)) {
                return ojVar.a[ojVar.b + ((int) j)];
            }
            j -= (long) i;
            ojVar = ojVar.f;
        }
    }

    public final nw c(int i) {
        oj e = e(2);
        byte[] bArr = e.a;
        int i2 = e.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr[i3] = (byte) (i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        e.c = i3 + 1;
        this.b += 2;
        return this;
    }

    public final /* synthetic */ nx c(byte[] bArr, int i, int i2) throws IOException {
        return b(bArr, i, i2);
    }

    public final boolean c() {
        return this.b == 0;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return r();
    }

    public final void close() {
    }

    public final InputStream d() {
        return new nw$2(this);
    }

    public final nw d(int i) {
        oj e = e(4);
        byte[] bArr = e.a;
        int i2 = e.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        i2 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr[i3] = (byte) (i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        e.c = i3 + 1;
        this.b += 4;
        return this;
    }

    public final nz d(long j) throws EOFException {
        return new nz(f(j));
    }

    public final long e() {
        long j = this.b;
        if (j == 0) {
            return 0;
        }
        oj ojVar = this.a.g;
        return (ojVar.c >= PKIFailureInfo.certRevoked || !ojVar.e) ? j : j - ((long) (ojVar.c - ojVar.b));
    }

    final String e(long j) throws EOFException {
        if (j <= 0 || c(j - 1) != (byte) 13) {
            String l = l(j);
            g(1);
            return l;
        }
        l = l(j - 1);
        g(2);
        return l;
    }

    final oj e(int i) {
        if (i <= 0 || i > PKIFailureInfo.certRevoked) {
            throw new IllegalArgumentException();
        } else if (this.a == null) {
            this.a = ok.a();
            oj ojVar = this.a;
            oj ojVar2 = this.a;
            r0 = this.a;
            ojVar2.g = r0;
            ojVar.f = r0;
            return r0;
        } else {
            r0 = this.a.g;
            return (r0.c + i > PKIFailureInfo.certRevoked || !r0.e) ? r0.a(ok.a()) : r0;
        }
    }

    public final boolean equals(Object obj) {
        long j = 0;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof nw)) {
            return false;
        }
        nw nwVar = (nw) obj;
        if (this.b != nwVar.b) {
            return false;
        }
        if (this.b == 0) {
            return true;
        }
        oj ojVar = this.a;
        oj ojVar2 = nwVar.a;
        int i = ojVar.b;
        int i2 = ojVar2.b;
        while (j < this.b) {
            long min = (long) Math.min(ojVar.c - i, ojVar2.c - i2);
            int i3 = 0;
            while (((long) i3) < min) {
                if (ojVar.a[i] != ojVar2.a[i2]) {
                    return false;
                }
                i3++;
                i2++;
                i++;
            }
            if (i == ojVar.c) {
                ojVar = ojVar.f;
                i = ojVar.b;
            }
            if (i2 == ojVar2.c) {
                ojVar2 = ojVar2.f;
                i2 = ojVar2.b;
            }
            j += min;
        }
        return true;
    }

    public final byte f() {
        if (this.b == 0) {
            throw new IllegalStateException("size == 0");
        }
        oj ojVar = this.a;
        int i = ojVar.b;
        int i2 = ojVar.c;
        int i3 = i + 1;
        byte b = ojVar.a[i];
        this.b--;
        if (i3 == i2) {
            this.a = ojVar.a();
            ok.a(ojVar);
        } else {
            ojVar.b = i3;
        }
        return b;
    }

    public final /* synthetic */ nx f(int i) throws IOException {
        return d(i);
    }

    public final byte[] f(long j) throws EOFException {
        op.a(this.b, 0, j);
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        }
        byte[] bArr = new byte[((int) j)];
        c(bArr);
        return bArr;
    }

    public final void flush() {
    }

    public final /* synthetic */ nx g(int i) throws IOException {
        return c(i);
    }

    public final short g() {
        if (this.b < 2) {
            throw new IllegalStateException("size < 2: " + this.b);
        }
        oj ojVar = this.a;
        int i = ojVar.b;
        int i2 = ojVar.c;
        if (i2 - i < 2) {
            return (short) (((f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) | (f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV));
        }
        byte[] bArr = ojVar.a;
        int i3 = i + 1;
        byte b = bArr[i];
        int i4 = i3 + 1;
        byte b2 = bArr[i3];
        this.b -= 2;
        if (i4 == i2) {
            this.a = ojVar.a();
            ok.a(ojVar);
        } else {
            ojVar.b = i4;
        }
        return (short) (((b & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) | (b2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV));
    }

    public final void g(long j) throws EOFException {
        while (j > 0) {
            if (this.a == null) {
                throw new EOFException();
            }
            int min = (int) Math.min(j, (long) (this.a.c - this.a.b));
            this.b -= (long) min;
            j -= (long) min;
            oj ojVar = this.a;
            ojVar.b = min + ojVar.b;
            if (this.a.b == this.a.c) {
                oj ojVar2 = this.a;
                this.a = ojVar2.a();
                ok.a(ojVar2);
            }
        }
    }

    public final int h() {
        if (this.b < 4) {
            throw new IllegalStateException("size < 4: " + this.b);
        }
        oj ojVar = this.a;
        int i = ojVar.b;
        int i2 = ojVar.c;
        if (i2 - i < 4) {
            return ((((f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) | ((f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | ((f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | (f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        }
        byte[] bArr = ojVar.a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = i4 + 1;
        int i6 = i5 + 1;
        i = ((((bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) | ((bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | ((bArr[i4] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | (bArr[i5] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        this.b -= 4;
        if (i6 == i2) {
            this.a = ojVar.a();
            ok.a(ojVar);
            return i;
        }
        ojVar.b = i6;
        return i;
    }

    public final nw h(long j) {
        if (j == 0) {
            return b(48);
        }
        long j2;
        Object obj;
        if (j < 0) {
            j2 = -j;
            if (j2 < 0) {
                return a("-9223372036854775808");
            }
            obj = 1;
        } else {
            obj = null;
            j2 = j;
        }
        int i = j2 < 100000000 ? j2 < 10000 ? j2 < 100 ? j2 < 10 ? 1 : 2 : j2 < 1000 ? 3 : 4 : j2 < 1000000 ? j2 < 100000 ? 5 : 6 : j2 < 10000000 ? 7 : 8 : j2 < 1000000000000L ? j2 < 10000000000L ? j2 < 1000000000 ? 9 : 10 : j2 < 100000000000L ? 11 : 12 : j2 < 1000000000000000L ? j2 < 10000000000000L ? 13 : j2 < 100000000000000L ? 14 : 15 : j2 < 100000000000000000L ? j2 < 10000000000000000L ? 16 : 17 : j2 < 1000000000000000000L ? 18 : 19;
        if (obj != null) {
            i++;
        }
        oj e = e(i);
        byte[] bArr = e.a;
        int i2 = e.c + i;
        while (j2 != 0) {
            i2--;
            bArr[i2] = c[(int) (j2 % 10)];
            j2 /= 10;
        }
        if (obj != null) {
            bArr[i2 - 1] = (byte) 45;
        }
        e.c += i;
        this.b = ((long) i) + this.b;
        return this;
    }

    public final /* synthetic */ nx h(int i) throws IOException {
        return b(i);
    }

    public final int hashCode() {
        oj ojVar = this.a;
        if (ojVar == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = ojVar.b;
            int i3 = ojVar.c;
            while (i2 < i3) {
                byte b = ojVar.a[i2];
                i2++;
                i = (i * 31) + b;
            }
            ojVar = ojVar.f;
        } while (ojVar != this.a);
        return i;
    }

    public final nw i(long j) {
        if (j == 0) {
            return b(48);
        }
        int numberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        oj e = e(numberOfTrailingZeros);
        byte[] bArr = e.a;
        int i = e.c;
        for (int i2 = (e.c + numberOfTrailingZeros) - 1; i2 >= i; i2--) {
            bArr[i2] = c[(int) (15 & j)];
            j >>>= 4;
        }
        e.c += numberOfTrailingZeros;
        this.b = ((long) numberOfTrailingZeros) + this.b;
        return this;
    }

    public final short i() {
        return op.a(g());
    }

    public final int j() {
        return op.a(h());
    }

    public final /* synthetic */ nx j(long j) throws IOException {
        return i(j);
    }

    public final long k() {
        if (this.b == 0) {
            throw new IllegalStateException("size == 0");
        }
        long j = 0;
        int i = 0;
        Object obj = null;
        Object obj2 = null;
        long j2 = -7;
        do {
            oj ojVar = this.a;
            byte[] bArr = ojVar.a;
            int i2 = ojVar.b;
            int i3 = ojVar.c;
            while (i2 < i3) {
                byte b = bArr[i2];
                if (b >= (byte) 48 && b <= (byte) 57) {
                    int i4 = 48 - b;
                    if (j < -922337203685477580L || (j == -922337203685477580L && ((long) i4) < j2)) {
                        nw b2 = new nw().h(j).b((int) b);
                        if (obj == null) {
                            b2.f();
                        }
                        throw new NumberFormatException("Number too large: " + b2.n());
                    }
                    j = (j * 10) + ((long) i4);
                } else if (b != (byte) 45 || i != 0) {
                    if (i != 0) {
                        obj2 = 1;
                        if (i2 != i3) {
                            this.a = ojVar.a();
                            ok.a(ojVar);
                        } else {
                            ojVar.b = i2;
                        }
                        if (obj2 == null) {
                            break;
                        }
                    } else {
                        throw new NumberFormatException("Expected leading [0-9] or '-' character but was 0x" + Integer.toHexString(b));
                    }
                } else {
                    obj = 1;
                    j2--;
                }
                i2++;
                i++;
            }
            if (i2 != i3) {
                ojVar.b = i2;
            } else {
                this.a = ojVar.a();
                ok.a(ojVar);
            }
            if (obj2 == null) {
                break;
            }
        } while (this.a != null);
        this.b -= (long) i;
        return obj != null ? j : -j;
    }

    public final /* synthetic */ nx k(long j) throws IOException {
        return h(j);
    }

    public final long l() {
        if (this.b == 0) {
            throw new IllegalStateException("size == 0");
        }
        Object obj = null;
        int i = 0;
        long j = 0;
        do {
            oj ojVar = this.a;
            byte[] bArr = ojVar.a;
            int i2 = ojVar.b;
            int i3 = ojVar.c;
            int i4 = i2;
            while (i4 < i3) {
                byte b = bArr[i4];
                if (b >= (byte) 48 && b <= (byte) 57) {
                    i2 = b - 48;
                } else if (b >= (byte) 97 && b <= (byte) 102) {
                    i2 = (b - 97) + 10;
                } else if (b < (byte) 65 || b > (byte) 70) {
                    if (i != 0) {
                        obj = 1;
                        if (i4 != i3) {
                            this.a = ojVar.a();
                            ok.a(ojVar);
                        } else {
                            ojVar.b = i4;
                        }
                        if (obj == null) {
                            break;
                        }
                    } else {
                        throw new NumberFormatException("Expected leading [0-9a-fA-F] character but was 0x" + Integer.toHexString(b));
                    }
                } else {
                    i2 = (b - 65) + 10;
                }
                if ((-1152921504606846976L & j) != 0) {
                    throw new NumberFormatException("Number too large: " + new nw().i(j).b((int) b).n());
                }
                long j2 = (long) i2;
                j = (j << 4) | j2;
                i4++;
                i++;
            }
            if (i4 != i3) {
                ojVar.b = i4;
            } else {
                this.a = ojVar.a();
                ok.a(ojVar);
            }
            if (obj == null) {
                break;
            }
        } while (this.a != null);
        this.b -= (long) i;
        return j;
    }

    public final nz m() {
        return new nz(p());
    }

    public final String n() {
        try {
            return a(this.b, op.a);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final String o() throws EOFException {
        long a = a((byte) 10, 0);
        if (a != -1) {
            return e(a);
        }
        nw nwVar = new nw();
        a(nwVar, 0, Math.min(32, this.b));
        throw new EOFException("\\n not found: size=" + this.b + " content=" + nwVar.m().c() + "…");
    }

    public final byte[] p() {
        try {
            return f(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final void q() {
        try {
            g(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final nw r() {
        nw nwVar = new nw();
        if (this.b == 0) {
            return nwVar;
        }
        nwVar.a = new oj(this.a);
        oj ojVar = nwVar.a;
        oj ojVar2 = nwVar.a;
        oj ojVar3 = nwVar.a;
        ojVar2.g = ojVar3;
        ojVar.f = ojVar3;
        for (ojVar = this.a.f; ojVar != this.a; ojVar = ojVar.f) {
            nwVar.a.g.a(new oj(ojVar));
        }
        nwVar.b = this.b;
        return nwVar;
    }

    public final long read(nw nwVar, long j) {
        if (nwVar == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.b == 0) {
            return -1;
        } else {
            if (j > this.b) {
                j = this.b;
            }
            nwVar.write(this, j);
            return j;
        }
    }

    public final /* bridge */ /* synthetic */ nx s() throws IOException {
        return this;
    }

    public final oo timeout() {
        return oo.NONE;
    }

    public final String toString() {
        if (this.b > 2147483647L) {
            throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.b);
        }
        int i = (int) this.b;
        return (i == 0 ? nz.b : new ol(this, i)).toString();
    }

    public final void write(nw nwVar, long j) {
        if (nwVar == null) {
            throw new IllegalArgumentException("source == null");
        } else if (nwVar == this) {
            throw new IllegalArgumentException("source == this");
        } else {
            op.a(nwVar.b, 0, j);
            while (j > 0) {
                oj ojVar;
                oj ojVar2;
                if (j < ((long) (nwVar.a.c - nwVar.a.b))) {
                    ojVar = this.a != null ? this.a.g : null;
                    if (ojVar != null && ojVar.e) {
                        if ((((long) ojVar.c) + j) - ((long) (ojVar.d ? 0 : ojVar.b)) <= 8192) {
                            nwVar.a.a(ojVar, (int) j);
                            nwVar.b -= j;
                            this.b += j;
                            return;
                        }
                    }
                    ojVar = nwVar.a;
                    int i = (int) j;
                    if (i <= 0 || i > ojVar.c - ojVar.b) {
                        throw new IllegalArgumentException();
                    }
                    if (i >= PKIFailureInfo.badRecipientNonce) {
                        ojVar2 = new oj(ojVar);
                    } else {
                        ojVar2 = ok.a();
                        System.arraycopy(ojVar.a, ojVar.b, ojVar2.a, 0, i);
                    }
                    ojVar2.c = ojVar2.b + i;
                    ojVar.b = i + ojVar.b;
                    ojVar.g.a(ojVar2);
                    nwVar.a = ojVar2;
                }
                ojVar2 = nwVar.a;
                long j2 = (long) (ojVar2.c - ojVar2.b);
                nwVar.a = ojVar2.a();
                if (this.a == null) {
                    this.a = ojVar2;
                    ojVar2 = this.a;
                    ojVar = this.a;
                    oj ojVar3 = this.a;
                    ojVar.g = ojVar3;
                    ojVar2.f = ojVar3;
                } else {
                    ojVar = this.a.g.a(ojVar2);
                    if (ojVar.g == ojVar) {
                        throw new IllegalStateException();
                    } else if (ojVar.g.e) {
                        int i2 = ojVar.c - ojVar.b;
                        if (i2 <= (ojVar.g.d ? 0 : ojVar.g.b) + (8192 - ojVar.g.c)) {
                            ojVar.a(ojVar.g, i2);
                            ojVar.a();
                            ok.a(ojVar);
                        }
                    }
                }
                nwVar.b -= j2;
                this.b += j2;
                j -= j2;
            }
        }
    }
}
