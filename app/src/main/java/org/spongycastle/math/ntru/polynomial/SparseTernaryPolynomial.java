package org.spongycastle.math.ntru.polynomial;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.math.ntru.util.ArrayEncoder;
import org.spongycastle.math.ntru.util.Util;
import org.spongycastle.util.Arrays;

public class SparseTernaryPolynomial implements TernaryPolynomial {
    private static final int BITS_PER_INDEX = 11;
    private int N;
    private int[] negOnes;
    private int[] ones;

    SparseTernaryPolynomial(int i, int[] iArr, int[] iArr2) {
        this.N = i;
        this.ones = iArr;
        this.negOnes = iArr2;
    }

    public SparseTernaryPolynomial(IntegerPolynomial integerPolynomial) {
        this(integerPolynomial.coeffs);
    }

    public SparseTernaryPolynomial(int[] iArr) {
        int i = 0;
        this.N = iArr.length;
        this.ones = new int[this.N];
        this.negOnes = new int[this.N];
        int i2 = 0;
        for (int i3 = 0; i3 < this.N; i3++) {
            int i4 = iArr[i3];
            switch (i4) {
                case -1:
                    this.negOnes[i] = i3;
                    i++;
                    break;
                case 0:
                    break;
                case 1:
                    this.ones[i2] = i3;
                    i2++;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal value: " + i4 + ", must be one of {-1, 0, 1}");
            }
        }
        this.ones = Arrays.copyOf(this.ones, i2);
        this.negOnes = Arrays.copyOf(this.negOnes, i);
    }

    public static SparseTernaryPolynomial fromBinary(InputStream inputStream, int i, int i2, int i3) throws IOException {
        int numberOfLeadingZeros = 32 - Integer.numberOfLeadingZeros(2047);
        return new SparseTernaryPolynomial(i, ArrayEncoder.decodeModQ(Util.readFullLength(inputStream, ((i2 * numberOfLeadingZeros) + 7) / 8), i2, (int) PKIFailureInfo.wrongIntegrity), ArrayEncoder.decodeModQ(Util.readFullLength(inputStream, ((numberOfLeadingZeros * i3) + 7) / 8), i3, (int) PKIFailureInfo.wrongIntegrity));
    }

    public static SparseTernaryPolynomial generateRandom(int i, int i2, int i3, SecureRandom secureRandom) {
        return new SparseTernaryPolynomial(Util.generateRandomTernary(i, i2, i3, secureRandom));
    }

    public void clear() {
        int i;
        for (i = 0; i < this.ones.length; i++) {
            this.ones[i] = 0;
        }
        for (i = 0; i < this.negOnes.length; i++) {
            this.negOnes[i] = 0;
        }
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SparseTernaryPolynomial sparseTernaryPolynomial = (SparseTernaryPolynomial) obj;
            if (this.N != sparseTernaryPolynomial.N || !Arrays.areEqual(this.negOnes, sparseTernaryPolynomial.negOnes)) {
                return false;
            }
            if (!Arrays.areEqual(this.ones, sparseTernaryPolynomial.ones)) {
                return false;
            }
        }
        return true;
    }

    public int[] getNegOnes() {
        return this.negOnes;
    }

    public int[] getOnes() {
        return this.ones;
    }

    public int hashCode() {
        return ((((this.N + 31) * 31) + Arrays.hashCode(this.negOnes)) * 31) + Arrays.hashCode(this.ones);
    }

    public BigIntPolynomial mult(BigIntPolynomial bigIntPolynomial) {
        int i = 0;
        BigInteger[] bigIntegerArr = bigIntPolynomial.coeffs;
        if (bigIntegerArr.length != this.N) {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
        int i2;
        BigInteger[] bigIntegerArr2 = new BigInteger[this.N];
        for (i2 = 0; i2 < this.N; i2++) {
            bigIntegerArr2[i2] = BigInteger.ZERO;
        }
        for (i2 = 0; i2 != this.ones.length; i2++) {
            int i3 = (this.N - 1) - this.ones[i2];
            for (int i4 = this.N - 1; i4 >= 0; i4--) {
                bigIntegerArr2[i4] = bigIntegerArr2[i4].add(bigIntegerArr[i3]);
                i3--;
                if (i3 < 0) {
                    i3 = this.N - 1;
                }
            }
        }
        while (i != this.negOnes.length) {
            i2 = (this.N - 1) - this.negOnes[i];
            for (i3 = this.N - 1; i3 >= 0; i3--) {
                bigIntegerArr2[i3] = bigIntegerArr2[i3].subtract(bigIntegerArr[i2]);
                i2--;
                if (i2 < 0) {
                    i2 = this.N - 1;
                }
            }
            i++;
        }
        return new BigIntPolynomial(bigIntegerArr2);
    }

    public IntegerPolynomial mult(IntegerPolynomial integerPolynomial) {
        int i = 0;
        int[] iArr = integerPolynomial.coeffs;
        if (iArr.length != this.N) {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
        int i2;
        int[] iArr2 = new int[this.N];
        for (i2 = 0; i2 != this.ones.length; i2++) {
            int i3 = (this.N - 1) - this.ones[i2];
            for (int i4 = this.N - 1; i4 >= 0; i4--) {
                iArr2[i4] = iArr2[i4] + iArr[i3];
                i3--;
                if (i3 < 0) {
                    i3 = this.N - 1;
                }
            }
        }
        while (i != this.negOnes.length) {
            i2 = (this.N - 1) - this.negOnes[i];
            for (i3 = this.N - 1; i3 >= 0; i3--) {
                iArr2[i3] = iArr2[i3] - iArr[i2];
                i2--;
                if (i2 < 0) {
                    i2 = this.N - 1;
                }
            }
            i++;
        }
        return new IntegerPolynomial(iArr2);
    }

    public IntegerPolynomial mult(IntegerPolynomial integerPolynomial, int i) {
        IntegerPolynomial mult = mult(integerPolynomial);
        mult.mod(i);
        return mult;
    }

    public int size() {
        return this.N;
    }

    public byte[] toBinary() {
        byte[] encodeModQ = ArrayEncoder.encodeModQ(this.ones, PKIFailureInfo.wrongIntegrity);
        Object encodeModQ2 = ArrayEncoder.encodeModQ(this.negOnes, PKIFailureInfo.wrongIntegrity);
        Object copyOf = Arrays.copyOf(encodeModQ, encodeModQ.length + encodeModQ2.length);
        System.arraycopy(encodeModQ2, 0, copyOf, encodeModQ.length, encodeModQ2.length);
        return copyOf;
    }

    public IntegerPolynomial toIntegerPolynomial() {
        int i = 0;
        int[] iArr = new int[this.N];
        for (int i2 = 0; i2 != this.ones.length; i2++) {
            iArr[this.ones[i2]] = 1;
        }
        while (i != this.negOnes.length) {
            iArr[this.negOnes[i]] = -1;
            i++;
        }
        return new IntegerPolynomial(iArr);
    }
}
