package org.spongycastle.math.ntru.polynomial;

import java.lang.reflect.Array;
import org.spongycastle.util.Arrays;

public class LongPolynomial5 {
    private long[] coeffs;
    private int numCoeffs;

    public LongPolynomial5(IntegerPolynomial integerPolynomial) {
        this.numCoeffs = integerPolynomial.coeffs.length;
        this.coeffs = new long[((this.numCoeffs + 4) / 5)];
        int i = 0;
        long j = 0;
        for (int i2 = 0; i2 < this.numCoeffs; i2++) {
            long[] jArr = this.coeffs;
            jArr[i] = jArr[i] | (((long) integerPolynomial.coeffs[i2]) << j);
            j += 12;
            if (j >= 60) {
                i++;
                j = 0;
            }
        }
    }

    private LongPolynomial5(long[] jArr, int i) {
        this.coeffs = jArr;
        this.numCoeffs = i;
    }

    public LongPolynomial5 mult(TernaryPolynomial ternaryPolynomial) {
        int i;
        int length = this.coeffs.length;
        int size = (ternaryPolynomial.size() + 4) / 5;
        long[][] jArr = (long[][]) Array.newInstance(Long.TYPE, new int[]{5, (length + size) - 1});
        int[] ones = ternaryPolynomial.getOnes();
        for (size = 0; size != ones.length; size++) {
            int i2 = ones[size];
            int i3 = i2 / 5;
            i = i2 - (i3 * 5);
            for (long j : this.coeffs) {
                jArr[i][i3] = (jArr[i][i3] + j) & 576319980446939135L;
                i3++;
            }
        }
        ones = ternaryPolynomial.getNegOnes();
        for (size = 0; size != ones.length; size++) {
            i2 = ones[size];
            i3 = i2 / 5;
            i = i2 - (i3 * 5);
            for (long j2 : this.coeffs) {
                jArr[i][i3] = ((576601524159907840L + jArr[i][i3]) - j2) & 576319980446939135L;
                i3++;
            }
        }
        long[] copyOf = Arrays.copyOf(jArr[0], jArr[0].length + 1);
        for (i3 = 1; i3 <= 4; i3++) {
            i2 = i3 * 12;
            i = 60 - i2;
            int length2 = jArr[i3].length;
            for (size = 0; size < length2; size++) {
                long j3 = jArr[i3][size];
                copyOf[size] = (((jArr[i3][size] & ((1 << i) - 1)) << i2) + copyOf[size]) & 576319980446939135L;
                int i4 = size + 1;
                copyOf[i4] = ((j3 >> i) + copyOf[i4]) & 576319980446939135L;
            }
        }
        i = this.numCoeffs;
        for (length = this.coeffs.length - 1; length < copyOf.length; length++) {
            long j4;
            if (length == this.coeffs.length - 1) {
                j4 = this.numCoeffs == 5 ? 0 : copyOf[length] >> ((i % 5) * 12);
                size = 0;
            } else {
                j4 = copyOf[length];
                size = (length * 5) - this.numCoeffs;
            }
            length2 = size / 5;
            size -= length2 * 5;
            copyOf[length2] = ((j4 << (size * 12)) + copyOf[length2]) & 576319980446939135L;
            length2++;
            if (length2 < this.coeffs.length) {
                copyOf[length2] = ((j4 >> ((5 - size) * 12)) + copyOf[length2]) & 576319980446939135L;
            }
        }
        return new LongPolynomial5(copyOf, this.numCoeffs);
    }

    public IntegerPolynomial toIntegerPolynomial() {
        int[] iArr = new int[this.numCoeffs];
        int i = 0;
        long j = 0;
        for (int i2 = 0; i2 < this.numCoeffs; i2++) {
            iArr[i2] = (int) ((this.coeffs[i] >> j) & 2047);
            j += 12;
            if (j >= 60) {
                i++;
                j = 0;
            }
        }
        return new IntegerPolynomial(iArr);
    }
}
