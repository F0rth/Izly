package defpackage;

import java.util.Arrays;

final class ol extends nz {
    final transient byte[][] f;
    final transient int[] g;

    ol(nw nwVar, int i) {
        int i2 = 0;
        super(null);
        op.a(nwVar.b, 0, (long) i);
        oj ojVar = nwVar.a;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            if (ojVar.c == ojVar.b) {
                throw new AssertionError("s.limit == s.pos");
            }
            i3 += ojVar.c - ojVar.b;
            i4++;
            ojVar = ojVar.f;
        }
        this.f = new byte[i4][];
        this.g = new int[(i4 * 2)];
        i3 = 0;
        oj ojVar2 = nwVar.a;
        while (i2 < i) {
            this.f[i3] = ojVar2.a;
            int i5 = (ojVar2.c - ojVar2.b) + i2;
            if (i5 > i) {
                i5 = i;
            }
            this.g[i3] = i5;
            this.g[this.f.length + i3] = ojVar2.b;
            ojVar2.d = true;
            i3++;
            ojVar2 = ojVar2.f;
            i2 = i5;
        }
    }

    private int b(int i) {
        int binarySearch = Arrays.binarySearch(this.g, 0, this.f.length, i + 1);
        return binarySearch >= 0 ? binarySearch : binarySearch ^ -1;
    }

    private nz g() {
        return new nz(f());
    }

    private Object writeReplace() {
        return g();
    }

    public final byte a(int i) {
        op.a((long) this.g[this.f.length - 1], (long) i, 1);
        int b = b(i);
        return this.f[b][(i - (b == 0 ? 0 : this.g[b - 1])) + this.g[this.f.length + b]];
    }

    public final String a() {
        return g().a();
    }

    public final nz a(int i, int i2) {
        return g().a(i, i2);
    }

    final void a(nw nwVar) {
        int i = 0;
        int length = this.f.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = this.g[length + i2];
            int i4 = this.g[i2];
            oj ojVar = new oj(this.f[i2], i3, (i3 + i4) - i);
            if (nwVar.a == null) {
                ojVar.g = ojVar;
                ojVar.f = ojVar;
                nwVar.a = ojVar;
            } else {
                nwVar.a.g.a(ojVar);
            }
            i2++;
            i = i4;
        }
        nwVar.b = ((long) i) + nwVar.b;
    }

    public final boolean a(int i, byte[] bArr, int i2, int i3) {
        if (i < 0 || i > e() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int b = b(i);
        while (i3 > 0) {
            int i4 = b == 0 ? 0 : this.g[b - 1];
            int min = Math.min(i3, ((this.g[b] - i4) + i4) - i);
            if (!op.a(this.f[b], (i - i4) + this.g[this.f.length + b], bArr, i2, min)) {
                return false;
            }
            i += min;
            i2 += min;
            i3 -= min;
            b++;
        }
        return true;
    }

    public final String b() {
        return g().b();
    }

    public final String c() {
        return g().c();
    }

    public final nz d() {
        return g().d();
    }

    public final int e() {
        return this.g[this.f.length - 1];
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof nz) && ((nz) obj).e() == e()) {
            nz nzVar = (nz) obj;
            int e = e();
            if (e() - e < 0) {
                e = 0;
            } else {
                int i = 0;
                int i2 = e;
                int b = b(0);
                int i3 = 0;
                while (i2 > 0) {
                    e = b == 0 ? 0 : this.g[b - 1];
                    int min = Math.min(i2, ((this.g[b] - e) + e) - i3);
                    if (!nzVar.a(i, this.f[b], (i3 - e) + this.g[this.f.length + b], min)) {
                        e = 0;
                        break;
                    }
                    e = i3 + min;
                    b++;
                    i2 -= min;
                    i += min;
                    i3 = e;
                }
                e = 1;
            }
            if (e != 0) {
                return true;
            }
        }
        return false;
    }

    public final byte[] f() {
        int i = 0;
        Object obj = new byte[this.g[this.f.length - 1]];
        int length = this.f.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = this.g[length + i2];
            int i4 = this.g[i2];
            System.arraycopy(this.f[i2], i3, obj, i, i4 - i);
            i2++;
            i = i4;
        }
        return obj;
    }

    public final int hashCode() {
        int i = this.d;
        if (i == 0) {
            i = 1;
            int length = this.f.length;
            int i2 = 0;
            int i3 = 0;
            while (i3 < length) {
                byte[] bArr = this.f[i3];
                int i4 = this.g[length + i3];
                int i5 = this.g[i3];
                int i6 = i;
                for (i = i4; i < (i5 - i2) + i4; i++) {
                    i6 = (i6 * 31) + bArr[i];
                }
                i2 = i5;
                i3++;
                i = i6;
            }
            this.d = i;
        }
        return i;
    }

    public final String toString() {
        return g().toString();
    }
}
