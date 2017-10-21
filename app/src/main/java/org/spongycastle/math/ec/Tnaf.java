package org.spongycastle.math.ec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve.F2m;

class Tnaf {
    private static final BigInteger MINUS_ONE = ECConstants.ONE.negate();
    private static final BigInteger MINUS_THREE = ECConstants.THREE.negate();
    private static final BigInteger MINUS_TWO = ECConstants.TWO.negate();
    public static final byte POW_2_WIDTH = (byte) 16;
    public static final byte WIDTH = (byte) 4;
    public static final ZTauElement[] alpha0 = new ZTauElement[]{null, new ZTauElement(ECConstants.ONE, ECConstants.ZERO), null, new ZTauElement(MINUS_THREE, MINUS_ONE), null, new ZTauElement(MINUS_ONE, MINUS_ONE), null, new ZTauElement(ECConstants.ONE, MINUS_ONE), null};
    public static final byte[][] alpha0Tnaf;
    public static final ZTauElement[] alpha1 = new ZTauElement[]{null, new ZTauElement(ECConstants.ONE, ECConstants.ZERO), null, new ZTauElement(MINUS_THREE, ECConstants.ONE), null, new ZTauElement(MINUS_ONE, ECConstants.ONE), null, new ZTauElement(ECConstants.ONE, ECConstants.ONE), null};
    public static final byte[][] alpha1Tnaf;

    static {
        byte[] bArr = new byte[]{(byte) 1, (byte) 0, (byte) 1};
        byte[] bArr2 = new byte[]{(byte) -1, (byte) 0, (byte) 0, (byte) 1};
        r2 = new byte[8][];
        r2[1] = new byte[]{(byte) 1};
        r2[2] = null;
        r2[3] = new byte[]{(byte) -1, (byte) 0, (byte) 1};
        r2[4] = null;
        r2[5] = bArr;
        r2[6] = null;
        r2[7] = bArr2;
        alpha0Tnaf = r2;
        bArr = new byte[]{(byte) -1, (byte) 0, (byte) 1};
        bArr2 = new byte[]{(byte) 1, (byte) 0, (byte) 1};
        byte[] bArr3 = new byte[]{(byte) -1, (byte) 0, (byte) 0, (byte) -1};
        r3 = new byte[8][];
        r3[1] = new byte[]{(byte) 1};
        r3[2] = null;
        r3[3] = bArr;
        r3[4] = null;
        r3[5] = bArr2;
        r3[6] = null;
        r3[7] = bArr3;
        alpha1Tnaf = r3;
    }

    Tnaf() {
    }

    public static SimpleBigDecimal approximateDivisionByN(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, byte b, int i, int i2) {
        int i3 = ((i + 5) / 2) + i2;
        BigInteger multiply = bigInteger2.multiply(bigInteger.shiftRight(((i - i3) - 2) + b));
        BigInteger add = multiply.add(bigInteger3.multiply(multiply.shiftRight(i)));
        multiply = add.shiftRight(i3 - i2);
        if (add.testBit((i3 - i2) - 1)) {
            multiply = multiply.add(ECConstants.ONE);
        }
        return new SimpleBigDecimal(multiply, i2);
    }

    public static BigInteger[] getLucas(byte b, int i, boolean z) {
        if (b == (byte) 1 || b == (byte) -1) {
            BigInteger bigInteger;
            BigInteger valueOf;
            if (z) {
                bigInteger = ECConstants.TWO;
                valueOf = BigInteger.valueOf((long) b);
            } else {
                bigInteger = ECConstants.ZERO;
                valueOf = ECConstants.ONE;
            }
            BigInteger bigInteger2 = bigInteger;
            int i2 = 1;
            while (i2 < i) {
                bigInteger = (b == (byte) 1 ? valueOf : valueOf.negate()).subtract(bigInteger2.shiftLeft(1));
                i2++;
                bigInteger2 = valueOf;
                valueOf = bigInteger;
            }
            return new BigInteger[]{bigInteger2, valueOf};
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static byte getMu(F2m f2m) {
        BigInteger toBigInteger = f2m.getA().toBigInteger();
        if (toBigInteger.equals(ECConstants.ZERO)) {
            return (byte) -1;
        }
        if (toBigInteger.equals(ECConstants.ONE)) {
            return (byte) 1;
        }
        throw new IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
    }

    public static ECPoint.F2m[] getPreComp(ECPoint.F2m f2m, byte b) {
        ECPoint.F2m[] f2mArr = new ECPoint.F2m[16];
        f2mArr[1] = f2m;
        byte[][] bArr = b == (byte) 0 ? alpha0Tnaf : alpha1Tnaf;
        int length = bArr.length;
        for (int i = 3; i < length; i += 2) {
            f2mArr[i] = multiplyFromTnaf(f2m, bArr[i]);
        }
        return f2mArr;
    }

    public static BigInteger[] getSi(F2m f2m) {
        if (f2m.isKoblitz()) {
            BigInteger subtract;
            BigInteger subtract2;
            int m = f2m.getM();
            int intValue = f2m.getA().toBigInteger().intValue();
            byte mu = f2m.getMu();
            int intValue2 = f2m.getH().intValue();
            BigInteger[] lucas = getLucas(mu, (m + 3) - intValue, false);
            if (mu == (byte) 1) {
                subtract = ECConstants.ONE.subtract(lucas[1]);
                subtract2 = ECConstants.ONE.subtract(lucas[0]);
            } else if (mu == (byte) -1) {
                subtract = ECConstants.ONE.add(lucas[1]);
                subtract2 = ECConstants.ONE.add(lucas[0]);
            } else {
                throw new IllegalArgumentException("mu must be 1 or -1");
            }
            BigInteger[] bigIntegerArr = new BigInteger[2];
            if (intValue2 == 2) {
                bigIntegerArr[0] = subtract.shiftRight(1);
                bigIntegerArr[1] = subtract2.shiftRight(1).negate();
                return bigIntegerArr;
            } else if (intValue2 == 4) {
                bigIntegerArr[0] = subtract.shiftRight(2);
                bigIntegerArr[1] = subtract2.shiftRight(2).negate();
                return bigIntegerArr;
            } else {
                throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
            }
        }
        throw new IllegalArgumentException("si is defined for Koblitz curves only");
    }

    public static BigInteger getTw(byte b, int i) {
        if (i == 4) {
            return b == (byte) 1 ? BigInteger.valueOf(6) : BigInteger.valueOf(10);
        } else {
            BigInteger[] lucas = getLucas(b, i, false);
            BigInteger bit = ECConstants.ZERO.setBit(i);
            return ECConstants.TWO.multiply(lucas[0]).multiply(lucas[1].modInverse(bit)).mod(bit);
        }
    }

    public static ECPoint.F2m multiplyFromTnaf(ECPoint.F2m f2m, byte[] bArr) {
        ECPoint.F2m f2m2 = (ECPoint.F2m) ((F2m) f2m.getCurve()).getInfinity();
        for (int length = bArr.length - 1; length >= 0; length--) {
            f2m2 = tau(f2m2);
            if (bArr[length] == (byte) 1) {
                f2m2 = f2m2.addSimple(f2m);
            } else if (bArr[length] == (byte) -1) {
                f2m2 = f2m2.subtractSimple(f2m);
            }
        }
        return f2m2;
    }

    public static ECPoint.F2m multiplyRTnaf(ECPoint.F2m f2m, BigInteger bigInteger) {
        F2m f2m2 = (F2m) f2m.getCurve();
        int m = f2m2.getM();
        byte intValue = (byte) f2m2.getA().toBigInteger().intValue();
        byte mu = f2m2.getMu();
        return multiplyTnaf(f2m, partModReduction(bigInteger, m, intValue, f2m2.getSi(), mu, (byte) 10));
    }

    public static ECPoint.F2m multiplyTnaf(ECPoint.F2m f2m, ZTauElement zTauElement) {
        return multiplyFromTnaf(f2m, tauAdicNaf(((F2m) f2m.getCurve()).getMu(), zTauElement));
    }

    public static BigInteger norm(byte b, ZTauElement zTauElement) {
        BigInteger multiply = zTauElement.u.multiply(zTauElement.u);
        BigInteger multiply2 = zTauElement.u.multiply(zTauElement.v);
        BigInteger shiftLeft = zTauElement.v.multiply(zTauElement.v).shiftLeft(1);
        if (b == (byte) 1) {
            return multiply.add(multiply2).add(shiftLeft);
        }
        if (b == (byte) -1) {
            return multiply.subtract(multiply2).add(shiftLeft);
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static SimpleBigDecimal norm(byte b, SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2) {
        SimpleBigDecimal multiply = simpleBigDecimal.multiply(simpleBigDecimal);
        SimpleBigDecimal multiply2 = simpleBigDecimal.multiply(simpleBigDecimal2);
        SimpleBigDecimal shiftLeft = simpleBigDecimal2.multiply(simpleBigDecimal2).shiftLeft(1);
        if (b == (byte) 1) {
            return multiply.add(multiply2).add(shiftLeft);
        }
        if (b == (byte) -1) {
            return multiply.subtract(multiply2).add(shiftLeft);
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static ZTauElement partModReduction(BigInteger bigInteger, int i, byte b, BigInteger[] bigIntegerArr, byte b2, byte b3) {
        BigInteger add = b2 == (byte) 1 ? bigIntegerArr[0].add(bigIntegerArr[1]) : bigIntegerArr[0].subtract(bigIntegerArr[1]);
        BigInteger bigInteger2 = getLucas(b2, i, true)[1];
        ZTauElement round = round(approximateDivisionByN(bigInteger, bigIntegerArr[0], bigInteger2, b, i, b3), approximateDivisionByN(bigInteger, bigIntegerArr[1], bigInteger2, b, i, b3), b2);
        return new ZTauElement(bigInteger.subtract(add.multiply(round.u)).subtract(BigInteger.valueOf(2).multiply(bigIntegerArr[1]).multiply(round.v)), bigIntegerArr[1].multiply(round.u).subtract(bigIntegerArr[0].multiply(round.v)));
    }

    public static ZTauElement round(SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2, byte b) {
        int i = 1;
        int i2 = 0;
        if (simpleBigDecimal2.getScale() != simpleBigDecimal.getScale()) {
            throw new IllegalArgumentException("lambda0 and lambda1 do not have same scale");
        } else if (b == (byte) 1 || b == (byte) -1) {
            BigInteger round = simpleBigDecimal.round();
            BigInteger round2 = simpleBigDecimal2.round();
            SimpleBigDecimal subtract = simpleBigDecimal.subtract(round);
            SimpleBigDecimal subtract2 = simpleBigDecimal2.subtract(round2);
            SimpleBigDecimal add = subtract.add(subtract);
            add = b == (byte) 1 ? add.add(subtract2) : add.subtract(subtract2);
            SimpleBigDecimal add2 = subtract2.add(subtract2).add(subtract2);
            SimpleBigDecimal add3 = add2.add(subtract2);
            if (b == (byte) 1) {
                subtract2 = subtract.subtract(add2);
                subtract = subtract.add(add3);
            } else {
                subtract2 = subtract.add(add2);
                subtract = subtract.subtract(add3);
            }
            if (add.compareTo(ECConstants.ONE) >= 0) {
                if (subtract2.compareTo(MINUS_ONE) < 0) {
                    i = 0;
                    i2 = b;
                }
            } else if (subtract.compareTo(ECConstants.TWO) >= 0) {
                i = 0;
                byte b2 = b;
            } else {
                i = 0;
            }
            if (add.compareTo(MINUS_ONE) < 0) {
                if (subtract2.compareTo(ECConstants.ONE) >= 0) {
                    i2 = (byte) (-b);
                } else {
                    i = -1;
                }
            } else if (subtract.compareTo(MINUS_TWO) < 0) {
                i2 = (byte) (-b);
            }
            return new ZTauElement(round.add(BigInteger.valueOf((long) i)), round2.add(BigInteger.valueOf((long) i2)));
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
    }

    public static ECPoint.F2m tau(ECPoint.F2m f2m) {
        if (f2m.isInfinity()) {
            return f2m;
        }
        return new ECPoint.F2m(f2m.getCurve(), f2m.getX().square(), f2m.getY().square(), f2m.isCompressed());
    }

    public static byte[] tauAdicNaf(byte b, ZTauElement zTauElement) {
        if (b == (byte) 1 || b == (byte) -1) {
            int bitLength = norm(b, zTauElement).bitLength();
            Object obj = new byte[(bitLength > 30 ? bitLength + 4 : 34)];
            BigInteger bigInteger = zTauElement.u;
            BigInteger bigInteger2 = zTauElement.v;
            int i = 0;
            BigInteger bigInteger3 = bigInteger;
            int i2 = 0;
            while (true) {
                if (bigInteger3.equals(ECConstants.ZERO) && bigInteger2.equals(ECConstants.ZERO)) {
                    bitLength = i2 + 1;
                    Object obj2 = new byte[bitLength];
                    System.arraycopy(obj, 0, obj2, 0, bitLength);
                    return obj2;
                }
                BigInteger clearBit;
                if (bigInteger3.testBit(0)) {
                    obj[i] = (byte) ECConstants.TWO.subtract(bigInteger3.subtract(bigInteger2.shiftLeft(1)).mod(ECConstants.FOUR)).intValue();
                    clearBit = obj[i] == (byte) 1 ? bigInteger3.clearBit(0) : bigInteger3.add(ECConstants.ONE);
                    i2 = i;
                } else {
                    obj[i] = null;
                    clearBit = bigInteger3;
                }
                bigInteger3 = clearBit.shiftRight(1);
                bigInteger3 = b == (byte) 1 ? bigInteger2.add(bigInteger3) : bigInteger2.subtract(bigInteger3);
                bigInteger2 = clearBit.shiftRight(1).negate();
                i++;
            }
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
    }

    public static byte[] tauAdicWNaf(byte b, ZTauElement zTauElement, byte b2, BigInteger bigInteger, BigInteger bigInteger2, ZTauElement[] zTauElementArr) {
        if (b == (byte) 1 || b == (byte) -1) {
            int bitLength = norm(b, zTauElement).bitLength();
            byte[] bArr = new byte[(bitLength > 30 ? (bitLength + 4) + b2 : b2 + 34)];
            BigInteger shiftRight = bigInteger.shiftRight(1);
            BigInteger bigInteger3 = zTauElement.u;
            BigInteger bigInteger4 = zTauElement.v;
            int i = 0;
            while (true) {
                if (bigInteger3.equals(ECConstants.ZERO) && bigInteger4.equals(ECConstants.ZERO)) {
                    return bArr;
                }
                BigInteger mod;
                if (bigInteger3.testBit(0)) {
                    byte b3;
                    mod = bigInteger3.add(bigInteger4.multiply(bigInteger2)).mod(bigInteger);
                    int intValue = mod.compareTo(shiftRight) >= 0 ? (byte) mod.subtract(bigInteger).intValue() : (byte) mod.intValue();
                    bArr[i] = intValue;
                    if (intValue < 0) {
                        intValue = (byte) (-intValue);
                        b3 = (byte) 0;
                    } else {
                        b3 = (byte) 1;
                    }
                    if (b3 != (byte) 0) {
                        bigInteger3 = bigInteger3.subtract(zTauElementArr[intValue].u);
                        bigInteger4 = bigInteger4.subtract(zTauElementArr[intValue].v);
                        mod = bigInteger3;
                    } else {
                        bigInteger3 = bigInteger3.add(zTauElementArr[intValue].u);
                        bigInteger4 = bigInteger4.add(zTauElementArr[intValue].v);
                        mod = bigInteger3;
                    }
                } else {
                    bArr[i] = (byte) 0;
                    mod = bigInteger3;
                }
                bigInteger3 = b == (byte) 1 ? bigInteger4.add(mod.shiftRight(1)) : bigInteger4.subtract(mod.shiftRight(1));
                bigInteger4 = mod.shiftRight(1).negate();
                i++;
            }
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
    }
}
