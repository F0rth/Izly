package org.spongycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;

public abstract class ECFieldElement implements ECConstants {

    public static class F2m extends ECFieldElement {
        public static final int GNB = 1;
        public static final int PPB = 3;
        public static final int TPB = 2;
        private int k1;
        private int k2;
        private int k3;
        private int m;
        private int representation;
        private int t;
        private IntArray x;

        public F2m(int i, int i2, int i3, int i4, BigInteger bigInteger) {
            this.t = (i + 31) >> 5;
            this.x = new IntArray(bigInteger, this.t);
            if (i3 == 0 && i4 == 0) {
                this.representation = 2;
            } else if (i3 >= i4) {
                throw new IllegalArgumentException("k2 must be smaller than k3");
            } else if (i3 <= 0) {
                throw new IllegalArgumentException("k2 must be larger than 0");
            } else {
                this.representation = 3;
            }
            if (bigInteger.signum() < 0) {
                throw new IllegalArgumentException("x value cannot be negative");
            }
            this.m = i;
            this.k1 = i2;
            this.k2 = i3;
            this.k3 = i4;
        }

        private F2m(int i, int i2, int i3, int i4, IntArray intArray) {
            this.t = (i + 31) >> 5;
            this.x = intArray;
            this.m = i;
            this.k1 = i2;
            this.k2 = i3;
            this.k3 = i4;
            if (i3 == 0 && i4 == 0) {
                this.representation = 2;
            } else {
                this.representation = 3;
            }
        }

        public F2m(int i, int i2, BigInteger bigInteger) {
            this(i, i2, 0, 0, bigInteger);
        }

        public static void checkFieldElements(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            if ((eCFieldElement instanceof F2m) && (eCFieldElement2 instanceof F2m)) {
                F2m f2m = (F2m) eCFieldElement;
                F2m f2m2 = (F2m) eCFieldElement2;
                if (f2m.m != f2m2.m || f2m.k1 != f2m2.k1 || f2m.k2 != f2m2.k2 || f2m.k3 != f2m2.k3) {
                    throw new IllegalArgumentException("Field elements are not elements of the same field F2m");
                } else if (f2m.representation != f2m2.representation) {
                    throw new IllegalArgumentException("One of the field elements are not elements has incorrect representation");
                } else {
                    return;
                }
            }
            throw new IllegalArgumentException("Field elements are not both instances of ECFieldElement.F2m");
        }

        public ECFieldElement add(ECFieldElement eCFieldElement) {
            IntArray intArray = (IntArray) this.x.clone();
            intArray.addShifted(((F2m) eCFieldElement).x, 0);
            return new F2m(this.m, this.k1, this.k2, this.k3, intArray);
        }

        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return multiply(eCFieldElement.invert());
        }

        public boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof F2m)) {
                    return false;
                }
                F2m f2m = (F2m) obj;
                if (this.m != f2m.m || this.k1 != f2m.k1 || this.k2 != f2m.k2 || this.k3 != f2m.k3 || this.representation != f2m.representation) {
                    return false;
                }
                if (!this.x.equals(f2m.x)) {
                    return false;
                }
            }
            return true;
        }

        public String getFieldName() {
            return "F2m";
        }

        public int getFieldSize() {
            return this.m;
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

        public int getRepresentation() {
            return this.representation;
        }

        public int hashCode() {
            return (((this.x.hashCode() ^ this.m) ^ this.k1) ^ this.k2) ^ this.k3;
        }

        public ECFieldElement invert() {
            IntArray intArray = (IntArray) this.x.clone();
            IntArray intArray2 = new IntArray(this.t);
            intArray2.setBit(this.m);
            intArray2.setBit(0);
            intArray2.setBit(this.k1);
            if (this.representation == 3) {
                intArray2.setBit(this.k2);
                intArray2.setBit(this.k3);
            }
            IntArray intArray3 = new IntArray(this.t);
            intArray3.setBit(0);
            IntArray intArray4 = new IntArray(this.t);
            IntArray intArray5 = intArray3;
            intArray3 = intArray2;
            intArray2 = intArray;
            intArray = intArray5;
            while (!intArray2.isZero()) {
                int bitLength = intArray2.bitLength() - intArray3.bitLength();
                if (bitLength < 0) {
                    bitLength = -bitLength;
                    intArray5 = intArray4;
                    intArray4 = intArray;
                    intArray = intArray5;
                    IntArray intArray6 = intArray3;
                    intArray3 = intArray2;
                    intArray2 = intArray6;
                }
                int i = bitLength >> 5;
                bitLength &= 31;
                intArray2.addShifted(intArray3.shiftLeft(bitLength), i);
                intArray.addShifted(intArray4.shiftLeft(bitLength), i);
            }
            return new F2m(this.m, this.k1, this.k2, this.k3, intArray4);
        }

        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            IntArray multiply = this.x.multiply(((F2m) eCFieldElement).x, this.m);
            multiply.reduce(this.m, new int[]{this.k1, this.k2, this.k3});
            return new F2m(this.m, this.k1, this.k2, this.k3, multiply);
        }

        public ECFieldElement negate() {
            return this;
        }

        public ECFieldElement sqrt() {
            throw new RuntimeException("Not implemented");
        }

        public ECFieldElement square() {
            IntArray square = this.x.square(this.m);
            square.reduce(this.m, new int[]{this.k1, this.k2, this.k3});
            return new F2m(this.m, this.k1, this.k2, this.k3, square);
        }

        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return add(eCFieldElement);
        }

        public BigInteger toBigInteger() {
            return this.x.toBigInteger();
        }
    }

    public static class Fp extends ECFieldElement {
        BigInteger q;
        BigInteger x;

        public Fp(BigInteger bigInteger, BigInteger bigInteger2) {
            this.x = bigInteger2;
            if (bigInteger2.compareTo(bigInteger) >= 0) {
                throw new IllegalArgumentException("x value too large in field element");
            }
            this.q = bigInteger;
        }

        private static BigInteger[] lucasSequence(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            int bitLength = bigInteger4.bitLength();
            int lowestSetBit = bigInteger4.getLowestSetBit();
            BigInteger bigInteger5 = ECConstants.ONE;
            BigInteger bigInteger6 = ECConstants.TWO;
            BigInteger bigInteger7 = ECConstants.ONE;
            BigInteger bigInteger8 = ECConstants.ONE;
            BigInteger bigInteger9 = bigInteger6;
            int i = bitLength - 1;
            BigInteger bigInteger10 = bigInteger2;
            while (i >= lowestSetBit + 1) {
                bigInteger7 = bigInteger7.multiply(bigInteger8).mod(bigInteger);
                if (bigInteger4.testBit(i)) {
                    bigInteger8 = bigInteger7.multiply(bigInteger3).mod(bigInteger);
                    bigInteger5 = bigInteger5.multiply(bigInteger10).mod(bigInteger);
                    bigInteger6 = bigInteger10.multiply(bigInteger9).subtract(bigInteger2.multiply(bigInteger7)).mod(bigInteger);
                    bigInteger10 = bigInteger10.multiply(bigInteger10).subtract(bigInteger8.shiftLeft(1)).mod(bigInteger);
                } else {
                    bigInteger8 = bigInteger5.multiply(bigInteger9).subtract(bigInteger7).mod(bigInteger);
                    bigInteger10 = bigInteger10.multiply(bigInteger9).subtract(bigInteger2.multiply(bigInteger7)).mod(bigInteger);
                    bigInteger6 = bigInteger9.multiply(bigInteger9).subtract(bigInteger7.shiftLeft(1)).mod(bigInteger);
                    bigInteger5 = bigInteger8;
                    bigInteger8 = bigInteger7;
                }
                i--;
                bigInteger9 = bigInteger6;
            }
            bigInteger8 = bigInteger7.multiply(bigInteger8).mod(bigInteger);
            bigInteger7 = bigInteger8.multiply(bigInteger3).mod(bigInteger);
            bigInteger6 = bigInteger5.multiply(bigInteger9).subtract(bigInteger8).mod(bigInteger);
            bigInteger10 = bigInteger10.multiply(bigInteger9).subtract(bigInteger2.multiply(bigInteger8)).mod(bigInteger);
            bigInteger7 = bigInteger8.multiply(bigInteger7).mod(bigInteger);
            bigInteger8 = bigInteger10;
            bigInteger10 = bigInteger6;
            for (int i2 = 1; i2 <= lowestSetBit; i2++) {
                bigInteger10 = bigInteger10.multiply(bigInteger8).mod(bigInteger);
                bigInteger8 = bigInteger8.multiply(bigInteger8).subtract(bigInteger7.shiftLeft(1)).mod(bigInteger);
                bigInteger7 = bigInteger7.multiply(bigInteger7).mod(bigInteger);
            }
            return new BigInteger[]{bigInteger10, bigInteger8};
        }

        public ECFieldElement add(ECFieldElement eCFieldElement) {
            return new Fp(this.q, this.x.add(eCFieldElement.toBigInteger()).mod(this.q));
        }

        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return new Fp(this.q, this.x.multiply(eCFieldElement.toBigInteger().modInverse(this.q)).mod(this.q));
        }

        public boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof Fp)) {
                    return false;
                }
                Fp fp = (Fp) obj;
                if (!this.q.equals(fp.q)) {
                    return false;
                }
                if (!this.x.equals(fp.x)) {
                    return false;
                }
            }
            return true;
        }

        public String getFieldName() {
            return "Fp";
        }

        public int getFieldSize() {
            return this.q.bitLength();
        }

        public BigInteger getQ() {
            return this.q;
        }

        public int hashCode() {
            return this.q.hashCode() ^ this.x.hashCode();
        }

        public ECFieldElement invert() {
            return new Fp(this.q, this.x.modInverse(this.q));
        }

        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            return new Fp(this.q, this.x.multiply(eCFieldElement.toBigInteger()).mod(this.q));
        }

        public ECFieldElement negate() {
            return new Fp(this.q, this.x.negate().mod(this.q));
        }

        public ECFieldElement sqrt() {
            if (!this.q.testBit(0)) {
                throw new RuntimeException("not done yet");
            } else if (this.q.testBit(1)) {
                ECFieldElement fp = new Fp(this.q, this.x.modPow(this.q.shiftRight(2).add(ECConstants.ONE), this.q));
                return fp.square().equals(this) ? fp : null;
            } else {
                BigInteger subtract = this.q.subtract(ECConstants.ONE);
                BigInteger shiftRight = subtract.shiftRight(1);
                if (!this.x.modPow(shiftRight, this.q).equals(ECConstants.ONE)) {
                    return null;
                }
                BigInteger bigInteger;
                BigInteger add = subtract.shiftRight(2).shiftLeft(1).add(ECConstants.ONE);
                BigInteger bigInteger2 = this.x;
                BigInteger mod = bigInteger2.shiftLeft(2).mod(this.q);
                Random random = new Random();
                while (true) {
                    bigInteger = new BigInteger(this.q.bitLength(), random);
                    if (bigInteger.compareTo(this.q) < 0 && bigInteger.multiply(bigInteger).subtract(mod).modPow(shiftRight, this.q).equals(subtract)) {
                        BigInteger[] lucasSequence = lucasSequence(this.q, bigInteger, bigInteger2, add);
                        BigInteger bigInteger3 = lucasSequence[0];
                        bigInteger = lucasSequence[1];
                        if (bigInteger.multiply(bigInteger).mod(this.q).equals(mod)) {
                            break;
                        } else if (!(bigInteger3.equals(ECConstants.ONE) || bigInteger3.equals(subtract))) {
                            return null;
                        }
                    }
                }
                if (bigInteger.testBit(0)) {
                    bigInteger = bigInteger.add(this.q);
                }
                return new Fp(this.q, bigInteger.shiftRight(1));
            }
        }

        public ECFieldElement square() {
            return new Fp(this.q, this.x.multiply(this.x).mod(this.q));
        }

        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return new Fp(this.q, this.x.subtract(eCFieldElement.toBigInteger()).mod(this.q));
        }

        public BigInteger toBigInteger() {
            return this.x;
        }
    }

    public abstract ECFieldElement add(ECFieldElement eCFieldElement);

    public abstract ECFieldElement divide(ECFieldElement eCFieldElement);

    public abstract String getFieldName();

    public abstract int getFieldSize();

    public abstract ECFieldElement invert();

    public abstract ECFieldElement multiply(ECFieldElement eCFieldElement);

    public abstract ECFieldElement negate();

    public abstract ECFieldElement sqrt();

    public abstract ECFieldElement square();

    public abstract ECFieldElement subtract(ECFieldElement eCFieldElement);

    public abstract BigInteger toBigInteger();

    public String toString() {
        return toBigInteger().toString(2);
    }
}
