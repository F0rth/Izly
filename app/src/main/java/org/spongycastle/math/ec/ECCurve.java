package org.spongycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;

public abstract class ECCurve {
    ECFieldElement a;
    ECFieldElement b;

    public static class F2m extends ECCurve {
        private BigInteger h;
        private org.spongycastle.math.ec.ECPoint.F2m infinity;
        private int k1;
        private int k2;
        private int k3;
        private int m;
        private byte mu;
        private BigInteger n;
        private BigInteger[] si;

        public F2m(int i, int i2, int i3, int i4, BigInteger bigInteger, BigInteger bigInteger2) {
            this(i, i2, i3, i4, bigInteger, bigInteger2, null, null);
        }

        public F2m(int i, int i2, int i3, int i4, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            this.mu = (byte) 0;
            this.si = null;
            this.m = i;
            this.k1 = i2;
            this.k2 = i3;
            this.k3 = i4;
            this.n = bigInteger3;
            this.h = bigInteger4;
            if (i2 == 0) {
                throw new IllegalArgumentException("k1 must be > 0");
            }
            if (i3 == 0) {
                if (i4 != 0) {
                    throw new IllegalArgumentException("k3 must be 0 if k2 == 0");
                }
            } else if (i3 <= i2) {
                throw new IllegalArgumentException("k2 must be > k1");
            } else if (i4 <= i3) {
                throw new IllegalArgumentException("k3 must be > k2");
            }
            this.a = fromBigInteger(bigInteger);
            this.b = fromBigInteger(bigInteger2);
            this.infinity = new org.spongycastle.math.ec.ECPoint.F2m(this, null, null);
        }

        public F2m(int i, int i2, BigInteger bigInteger, BigInteger bigInteger2) {
            this(i, i2, 0, 0, bigInteger, bigInteger2, null, null);
        }

        public F2m(int i, int i2, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            this(i, i2, 0, 0, bigInteger, bigInteger2, bigInteger3, bigInteger4);
        }

        private ECPoint decompressPoint(byte[] bArr, int i) {
            ECFieldElement eCFieldElement;
            int i2 = 0;
            ECFieldElement f2m = new org.spongycastle.math.ec.ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, new BigInteger(1, bArr));
            if (f2m.toBigInteger().equals(ECConstants.ZERO)) {
                eCFieldElement = (org.spongycastle.math.ec.ECFieldElement.F2m) this.b;
                for (int i3 = 0; i3 < this.m - 1; i3++) {
                    eCFieldElement = eCFieldElement.square();
                }
            } else {
                ECFieldElement solveQuadradicEquation = solveQuadradicEquation(f2m.add(this.a).add(this.b.multiply(f2m.square().invert())));
                if (solveQuadradicEquation == null) {
                    throw new RuntimeException("Invalid point compression");
                }
                if (solveQuadradicEquation.toBigInteger().testBit(0)) {
                    i2 = 1;
                }
                eCFieldElement = f2m.multiply(i2 != i ? solveQuadradicEquation.add(new org.spongycastle.math.ec.ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, ECConstants.ONE)) : solveQuadradicEquation);
            }
            return new org.spongycastle.math.ec.ECPoint.F2m(this, f2m, eCFieldElement);
        }

        private ECFieldElement solveQuadradicEquation(ECFieldElement eCFieldElement) {
            ECFieldElement f2m = new org.spongycastle.math.ec.ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, ECConstants.ZERO);
            if (eCFieldElement.toBigInteger().equals(ECConstants.ZERO)) {
                return f2m;
            }
            ECFieldElement eCFieldElement2;
            Random random = new Random();
            do {
                ECFieldElement f2m2 = new org.spongycastle.math.ec.ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, new BigInteger(this.m, random));
                eCFieldElement2 = f2m;
                ECFieldElement eCFieldElement3 = eCFieldElement;
                for (int i = 1; i <= this.m - 1; i++) {
                    eCFieldElement3 = eCFieldElement3.square();
                    eCFieldElement2 = eCFieldElement2.square().add(eCFieldElement3.multiply(f2m2));
                    eCFieldElement3 = eCFieldElement3.add(eCFieldElement);
                }
                if (!eCFieldElement3.toBigInteger().equals(ECConstants.ZERO)) {
                    return null;
                }
            } while (eCFieldElement2.square().add(eCFieldElement2).toBigInteger().equals(ECConstants.ZERO));
            return eCFieldElement2;
        }

        public ECPoint createPoint(BigInteger bigInteger, BigInteger bigInteger2, boolean z) {
            return new org.spongycastle.math.ec.ECPoint.F2m(this, fromBigInteger(bigInteger), fromBigInteger(bigInteger2), z);
        }

        public ECPoint decodePoint(byte[] bArr) {
            switch (bArr[0]) {
                case (byte) 0:
                    if (bArr.length <= 1) {
                        return getInfinity();
                    }
                    throw new RuntimeException("Invalid point encoding");
                case (byte) 2:
                case (byte) 3:
                    Object obj = new byte[(bArr.length - 1)];
                    System.arraycopy(bArr, 1, obj, 0, obj.length);
                    return bArr[0] == (byte) 2 ? decompressPoint(obj, 0) : decompressPoint(obj, 1);
                case (byte) 4:
                case (byte) 6:
                case (byte) 7:
                    Object obj2 = new byte[((bArr.length - 1) / 2)];
                    Object obj3 = new byte[((bArr.length - 1) / 2)];
                    System.arraycopy(bArr, 1, obj2, 0, obj2.length);
                    System.arraycopy(bArr, obj2.length + 1, obj3, 0, obj3.length);
                    return new org.spongycastle.math.ec.ECPoint.F2m(this, new org.spongycastle.math.ec.ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, new BigInteger(1, obj2)), new org.spongycastle.math.ec.ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, new BigInteger(1, obj3)), false);
                default:
                    throw new RuntimeException("Invalid point encoding 0x" + Integer.toString(bArr[0], 16));
            }
        }

        public boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof F2m)) {
                    return false;
                }
                F2m f2m = (F2m) obj;
                if (this.m != f2m.m || this.k1 != f2m.k1 || this.k2 != f2m.k2 || this.k3 != f2m.k3 || !this.a.equals(f2m.a)) {
                    return false;
                }
                if (!this.b.equals(f2m.b)) {
                    return false;
                }
            }
            return true;
        }

        public ECFieldElement fromBigInteger(BigInteger bigInteger) {
            return new org.spongycastle.math.ec.ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, bigInteger);
        }

        public int getFieldSize() {
            return this.m;
        }

        public BigInteger getH() {
            return this.h;
        }

        public ECPoint getInfinity() {
            return this.infinity;
        }

        public int getK1() {
            return this.k1;
        }

        public int getK2() {
            return this.k2;
        }

        public int getK3() {
            return this.k3;
        }

        public int getM() {
            return this.m;
        }

        byte getMu() {
            byte b;
            synchronized (this) {
                if (this.mu == (byte) 0) {
                    this.mu = Tnaf.getMu(this);
                }
                b = this.mu;
            }
            return b;
        }

        public BigInteger getN() {
            return this.n;
        }

        BigInteger[] getSi() {
            BigInteger[] bigIntegerArr;
            synchronized (this) {
                if (this.si == null) {
                    this.si = Tnaf.getSi(this);
                }
                bigIntegerArr = this.si;
            }
            return bigIntegerArr;
        }

        public int hashCode() {
            return ((((this.a.hashCode() ^ this.b.hashCode()) ^ this.m) ^ this.k1) ^ this.k2) ^ this.k3;
        }

        public boolean isKoblitz() {
            return (this.n == null || this.h == null || ((!this.a.toBigInteger().equals(ECConstants.ZERO) && !this.a.toBigInteger().equals(ECConstants.ONE)) || !this.b.toBigInteger().equals(ECConstants.ONE))) ? false : true;
        }

        public boolean isTrinomial() {
            return this.k2 == 0 && this.k3 == 0;
        }
    }

    public static class Fp extends ECCurve {
        org.spongycastle.math.ec.ECPoint.Fp infinity = new org.spongycastle.math.ec.ECPoint.Fp(this, null, null);
        BigInteger q;

        public Fp(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            this.q = bigInteger;
            this.a = fromBigInteger(bigInteger2);
            this.b = fromBigInteger(bigInteger3);
        }

        public ECPoint createPoint(BigInteger bigInteger, BigInteger bigInteger2, boolean z) {
            return new org.spongycastle.math.ec.ECPoint.Fp(this, fromBigInteger(bigInteger), fromBigInteger(bigInteger2), z);
        }

        public ECPoint decodePoint(byte[] bArr) {
            int i = 0;
            Object obj;
            switch (bArr[0]) {
                case (byte) 0:
                    if (bArr.length <= 1) {
                        return getInfinity();
                    }
                    throw new RuntimeException("Invalid point encoding");
                case (byte) 2:
                case (byte) 3:
                    byte b = bArr[0];
                    obj = new byte[(bArr.length - 1)];
                    System.arraycopy(bArr, 1, obj, 0, obj.length);
                    ECFieldElement fp = new org.spongycastle.math.ec.ECFieldElement.Fp(this.q, new BigInteger(1, obj));
                    ECFieldElement sqrt = fp.multiply(fp.square().add(this.a)).add(this.b).sqrt();
                    if (sqrt == null) {
                        throw new RuntimeException("Invalid point compression");
                    }
                    if (sqrt.toBigInteger().testBit(0)) {
                        i = 1;
                    }
                    return i == (b & 1) ? new org.spongycastle.math.ec.ECPoint.Fp(this, fp, sqrt, true) : new org.spongycastle.math.ec.ECPoint.Fp(this, fp, new org.spongycastle.math.ec.ECFieldElement.Fp(this.q, this.q.subtract(sqrt.toBigInteger())), true);
                case (byte) 4:
                case (byte) 6:
                case (byte) 7:
                    Object obj2 = new byte[((bArr.length - 1) / 2)];
                    obj = new byte[((bArr.length - 1) / 2)];
                    System.arraycopy(bArr, 1, obj2, 0, obj2.length);
                    System.arraycopy(bArr, obj2.length + 1, obj, 0, obj.length);
                    return new org.spongycastle.math.ec.ECPoint.Fp(this, new org.spongycastle.math.ec.ECFieldElement.Fp(this.q, new BigInteger(1, obj2)), new org.spongycastle.math.ec.ECFieldElement.Fp(this.q, new BigInteger(1, obj)));
                default:
                    throw new RuntimeException("Invalid point encoding 0x" + Integer.toString(bArr[0], 16));
            }
        }

        public boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof Fp)) {
                    return false;
                }
                Fp fp = (Fp) obj;
                if (!this.q.equals(fp.q) || !this.a.equals(fp.a)) {
                    return false;
                }
                if (!this.b.equals(fp.b)) {
                    return false;
                }
            }
            return true;
        }

        public ECFieldElement fromBigInteger(BigInteger bigInteger) {
            return new org.spongycastle.math.ec.ECFieldElement.Fp(this.q, bigInteger);
        }

        public int getFieldSize() {
            return this.q.bitLength();
        }

        public ECPoint getInfinity() {
            return this.infinity;
        }

        public BigInteger getQ() {
            return this.q;
        }

        public int hashCode() {
            return (this.a.hashCode() ^ this.b.hashCode()) ^ this.q.hashCode();
        }
    }

    public abstract ECPoint createPoint(BigInteger bigInteger, BigInteger bigInteger2, boolean z);

    public abstract ECPoint decodePoint(byte[] bArr);

    public abstract ECFieldElement fromBigInteger(BigInteger bigInteger);

    public ECFieldElement getA() {
        return this.a;
    }

    public ECFieldElement getB() {
        return this.b;
    }

    public abstract int getFieldSize();

    public abstract ECPoint getInfinity();
}
