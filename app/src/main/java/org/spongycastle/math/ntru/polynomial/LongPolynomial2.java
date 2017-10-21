package org.spongycastle.math.ntru.polynomial;

import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.util.Arrays;

public class LongPolynomial2 {
    private long[] coeffs;
    private int numCoeffs;

    private LongPolynomial2(int i) {
        this.coeffs = new long[i];
    }

    public LongPolynomial2(IntegerPolynomial integerPolynomial) {
        int i = 0;
        this.numCoeffs = integerPolynomial.coeffs.length;
        this.coeffs = new long[((this.numCoeffs + 1) / 2)];
        int i2 = 0;
        while (i2 < this.numCoeffs) {
            long j;
            int i3 = i2 + 1;
            int i4 = integerPolynomial.coeffs[i2];
            while (i4 < 0) {
                i4 += PKIFailureInfo.wrongIntegrity;
            }
            if (i3 < this.numCoeffs) {
                i2 = i3 + 1;
                j = (long) integerPolynomial.coeffs[i3];
            } else {
                i2 = i3;
                j = 0;
            }
            while (j < 0) {
                j += 2048;
            }
            this.coeffs[i] = (j << 24) + ((long) i4);
            i++;
        }
    }

    private LongPolynomial2(long[] jArr) {
        this.coeffs = jArr;
    }

    private void add(LongPolynomial2 longPolynomial2) {
        if (longPolynomial2.coeffs.length > this.coeffs.length) {
            this.coeffs = Arrays.copyOf(this.coeffs, longPolynomial2.coeffs.length);
        }
        for (int i = 0; i < longPolynomial2.coeffs.length; i++) {
            this.coeffs[i] = (this.coeffs[i] + longPolynomial2.coeffs[i]) & 34342963199L;
        }
    }

    private LongPolynomial2 multRecursive(LongPolynomial2 longPolynomial2) {
        long[] jArr = this.coeffs;
        long[] jArr2 = longPolynomial2.coeffs;
        int length = longPolynomial2.coeffs.length;
        LongPolynomial2 longPolynomial22;
        int i;
        int max;
        if (length <= 32) {
            int i2 = length * 2;
            longPolynomial22 = new LongPolynomial2(new long[i2]);
            for (i = 0; i < i2; i++) {
                for (max = Math.max(0, (i - length) + 1); max <= Math.min(i, length - 1); max++) {
                    long j = jArr[i - max] * jArr2[max];
                    longPolynomial22.coeffs[i] = (((34342961152L + (2047 & j)) & j) + longPolynomial22.coeffs[i]) & 34342963199L;
                    longPolynomial22.coeffs[i + 1] = (((j >>> 48) & 2047) + longPolynomial22.coeffs[i + 1]) & 34342963199L;
                }
            }
            return longPolynomial22;
        }
        i = length / 2;
        LongPolynomial2 longPolynomial23 = new LongPolynomial2(Arrays.copyOf(jArr, i));
        LongPolynomial2 longPolynomial24 = new LongPolynomial2(Arrays.copyOfRange(jArr, i, length));
        LongPolynomial2 longPolynomial25 = new LongPolynomial2(Arrays.copyOf(jArr2, i));
        LongPolynomial2 longPolynomial26 = new LongPolynomial2(Arrays.copyOfRange(jArr2, i, length));
        LongPolynomial2 longPolynomial27 = (LongPolynomial2) longPolynomial23.clone();
        longPolynomial27.add(longPolynomial24);
        longPolynomial22 = (LongPolynomial2) longPolynomial25.clone();
        longPolynomial22.add(longPolynomial26);
        longPolynomial25 = longPolynomial23.multRecursive(longPolynomial25);
        LongPolynomial2 multRecursive = longPolynomial24.multRecursive(longPolynomial26);
        longPolynomial23 = longPolynomial27.multRecursive(longPolynomial22);
        longPolynomial23.sub(longPolynomial25);
        longPolynomial23.sub(multRecursive);
        longPolynomial22 = new LongPolynomial2(length * 2);
        for (max = 0; max < longPolynomial25.coeffs.length; max++) {
            longPolynomial22.coeffs[max] = longPolynomial25.coeffs[max] & 34342963199L;
        }
        for (max = 0; max < longPolynomial23.coeffs.length; max++) {
            longPolynomial22.coeffs[i + max] = (longPolynomial22.coeffs[i + max] + longPolynomial23.coeffs[max]) & 34342963199L;
        }
        for (max = 0; max < multRecursive.coeffs.length; max++) {
            longPolynomial22.coeffs[(i * 2) + max] = (longPolynomial22.coeffs[(i * 2) + max] + multRecursive.coeffs[max]) & 34342963199L;
        }
        return longPolynomial22;
    }

    private void sub(LongPolynomial2 longPolynomial2) {
        if (longPolynomial2.coeffs.length > this.coeffs.length) {
            this.coeffs = Arrays.copyOf(this.coeffs, longPolynomial2.coeffs.length);
        }
        for (int i = 0; i < longPolynomial2.coeffs.length; i++) {
            this.coeffs[i] = ((140737496743936L + this.coeffs[i]) - longPolynomial2.coeffs[i]) & 34342963199L;
        }
    }

    public Object clone() {
        LongPolynomial2 longPolynomial2 = new LongPolynomial2((long[]) this.coeffs.clone());
        longPolynomial2.numCoeffs = this.numCoeffs;
        return longPolynomial2;
    }

    public boolean equals(Object obj) {
        return obj instanceof LongPolynomial2 ? Arrays.areEqual(this.coeffs, ((LongPolynomial2) obj).coeffs) : false;
    }

    public LongPolynomial2 mult(LongPolynomial2 longPolynomial2) {
        int length = this.coeffs.length;
        if (longPolynomial2.coeffs.length == length && this.numCoeffs == longPolynomial2.numCoeffs) {
            LongPolynomial2 multRecursive = multRecursive(longPolynomial2);
            if (multRecursive.coeffs.length > length) {
                int i;
                if (this.numCoeffs % 2 == 0) {
                    for (i = length; i < multRecursive.coeffs.length; i++) {
                        multRecursive.coeffs[i - length] = (multRecursive.coeffs[i - length] + multRecursive.coeffs[i]) & 34342963199L;
                    }
                    multRecursive.coeffs = Arrays.copyOf(multRecursive.coeffs, length);
                } else {
                    for (i = length; i < multRecursive.coeffs.length; i++) {
                        multRecursive.coeffs[i - length] = multRecursive.coeffs[i - length] + (multRecursive.coeffs[i - 1] >> 24);
                        multRecursive.coeffs[i - length] = multRecursive.coeffs[i - length] + ((multRecursive.coeffs[i] & 2047) << 24);
                        long[] jArr = multRecursive.coeffs;
                        int i2 = i - length;
                        jArr[i2] = jArr[i2] & 34342963199L;
                    }
                    multRecursive.coeffs = Arrays.copyOf(multRecursive.coeffs, length);
                    long[] jArr2 = multRecursive.coeffs;
                    length = multRecursive.coeffs.length - 1;
                    jArr2[length] = jArr2[length] & 2047;
                }
            }
            LongPolynomial2 longPolynomial22 = new LongPolynomial2(multRecursive.coeffs);
            longPolynomial22.numCoeffs = this.numCoeffs;
            return longPolynomial22;
        }
        throw new IllegalArgumentException("Number of coefficients must be the same");
    }

    public void mult2And(int i) {
        long j = (long) i;
        long j2 = (long) i;
        for (int i2 = 0; i2 < this.coeffs.length; i2++) {
            this.coeffs[i2] = (this.coeffs[i2] << 1) & ((j << 24) + j2);
        }
    }

    public void subAnd(LongPolynomial2 longPolynomial2, int i) {
        long j = (long) i;
        long j2 = (long) i;
        for (int i2 = 0; i2 < longPolynomial2.coeffs.length; i2++) {
            this.coeffs[i2] = ((140737496743936L + this.coeffs[i2]) - longPolynomial2.coeffs[i2]) & ((j << 24) + j2);
        }
    }

    public IntegerPolynomial toIntegerPolynomial() {
        int i = 0;
        int[] iArr = new int[this.numCoeffs];
        int i2 = 0;
        while (i < this.coeffs.length) {
            int i3 = i2 + 1;
            iArr[i2] = (int) (this.coeffs[i] & 2047);
            if (i3 < this.numCoeffs) {
                i2 = i3 + 1;
                iArr[i3] = (int) ((this.coeffs[i] >> 24) & 2047);
            } else {
                i2 = i3;
            }
            i++;
        }
        return new IntegerPolynomial(iArr);
    }
}
