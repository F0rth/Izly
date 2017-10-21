package defpackage;

import org.spongycastle.asn1.cmp.PKIFailureInfo;

final class oj {
    final byte[] a;
    int b;
    int c;
    boolean d;
    boolean e;
    oj f;
    oj g;

    oj() {
        this.a = new byte[PKIFailureInfo.certRevoked];
        this.e = true;
        this.d = false;
    }

    oj(oj ojVar) {
        this(ojVar.a, ojVar.b, ojVar.c);
        ojVar.d = true;
    }

    oj(byte[] bArr, int i, int i2) {
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.e = false;
        this.d = true;
    }

    public final oj a() {
        oj ojVar = this.f != this ? this.f : null;
        this.g.f = this.f;
        this.f.g = this.g;
        this.f = null;
        this.g = null;
        return ojVar;
    }

    public final oj a(oj ojVar) {
        ojVar.g = this;
        ojVar.f = this.f;
        this.f.g = ojVar;
        this.f = ojVar;
        return ojVar;
    }

    public final void a(oj ojVar, int i) {
        if (ojVar.e) {
            if (ojVar.c + i > PKIFailureInfo.certRevoked) {
                if (ojVar.d) {
                    throw new IllegalArgumentException();
                } else if ((ojVar.c + i) - ojVar.b > PKIFailureInfo.certRevoked) {
                    throw new IllegalArgumentException();
                } else {
                    System.arraycopy(ojVar.a, ojVar.b, ojVar.a, 0, ojVar.c - ojVar.b);
                    ojVar.c -= ojVar.b;
                    ojVar.b = 0;
                }
            }
            System.arraycopy(this.a, this.b, ojVar.a, ojVar.c, i);
            ojVar.c += i;
            this.b += i;
            return;
        }
        throw new IllegalArgumentException();
    }
}
