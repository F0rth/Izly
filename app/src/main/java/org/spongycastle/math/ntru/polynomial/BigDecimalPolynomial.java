package org.spongycastle.math.ntru.polynomial;

import java.math.BigDecimal;

public class BigDecimalPolynomial {
    private static final BigDecimal ONE_HALF = new BigDecimal("0.5");
    private static final BigDecimal ZERO = new BigDecimal("0");
    BigDecimal[] coeffs;

    BigDecimalPolynomial(int i) {
        this.coeffs = new BigDecimal[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.coeffs[i2] = ZERO;
        }
    }

    public BigDecimalPolynomial(BigIntPolynomial bigIntPolynomial) {
        int length = bigIntPolynomial.coeffs.length;
        this.coeffs = new BigDecimal[length];
        for (int i = 0; i < length; i++) {
            this.coeffs[i] = new BigDecimal(bigIntPolynomial.coeffs[i]);
        }
    }

    BigDecimalPolynomial(BigDecimal[] bigDecimalArr) {
        this.coeffs = bigDecimalArr;
    }

    private BigDecimal[] copyOf(BigDecimal[] bigDecimalArr, int i) {
        Object obj = new BigDecimal[i];
        if (bigDecimalArr.length < i) {
            i = bigDecimalArr.length;
        }
        System.arraycopy(bigDecimalArr, 0, obj, 0, i);
        return obj;
    }

    private BigDecimal[] copyOfRange(BigDecimal[] bigDecimalArr, int i, int i2) {
        int i3 = i2 - i;
        Object obj = new BigDecimal[i3];
        if (bigDecimalArr.length - i < i3) {
            i3 = bigDecimalArr.length - i;
        }
        System.arraycopy(bigDecimalArr, i, obj, 0, i3);
        return obj;
    }

    private BigDecimalPolynomial multRecursive(BigDecimalPolynomial bigDecimalPolynomial) {
        int i = 0;
        BigDecimal[] bigDecimalArr = this.coeffs;
        BigDecimal[] bigDecimalArr2 = bigDecimalPolynomial.coeffs;
        int length = bigDecimalPolynomial.coeffs.length;
        if (length <= 1) {
            bigDecimalArr = (BigDecimal[]) this.coeffs.clone();
            for (int i2 = 0; i2 < this.coeffs.length; i2++) {
                bigDecimalArr[i2] = bigDecimalArr[i2].multiply(bigDecimalPolynomial.coeffs[0]);
            }
            return new BigDecimalPolynomial(bigDecimalArr);
        }
        int i3;
        int i4 = length / 2;
        BigDecimalPolynomial bigDecimalPolynomial2 = new BigDecimalPolynomial(copyOf(bigDecimalArr, i4));
        BigDecimalPolynomial bigDecimalPolynomial3 = new BigDecimalPolynomial(copyOfRange(bigDecimalArr, i4, length));
        BigDecimalPolynomial bigDecimalPolynomial4 = new BigDecimalPolynomial(copyOf(bigDecimalArr2, i4));
        BigDecimalPolynomial bigDecimalPolynomial5 = new BigDecimalPolynomial(copyOfRange(bigDecimalArr2, i4, length));
        BigDecimalPolynomial bigDecimalPolynomial6 = (BigDecimalPolynomial) bigDecimalPolynomial2.clone();
        bigDecimalPolynomial6.add(bigDecimalPolynomial3);
        BigDecimalPolynomial bigDecimalPolynomial7 = (BigDecimalPolynomial) bigDecimalPolynomial4.clone();
        bigDecimalPolynomial7.add(bigDecimalPolynomial5);
        bigDecimalPolynomial2 = bigDecimalPolynomial2.multRecursive(bigDecimalPolynomial4);
        bigDecimalPolynomial3 = bigDecimalPolynomial3.multRecursive(bigDecimalPolynomial5);
        bigDecimalPolynomial4 = bigDecimalPolynomial6.multRecursive(bigDecimalPolynomial7);
        bigDecimalPolynomial4.sub(bigDecimalPolynomial2);
        bigDecimalPolynomial4.sub(bigDecimalPolynomial3);
        bigDecimalPolynomial7 = new BigDecimalPolynomial((length * 2) - 1);
        for (i3 = 0; i3 < bigDecimalPolynomial2.coeffs.length; i3++) {
            bigDecimalPolynomial7.coeffs[i3] = bigDecimalPolynomial2.coeffs[i3];
        }
        for (i3 = 0; i3 < bigDecimalPolynomial4.coeffs.length; i3++) {
            bigDecimalPolynomial7.coeffs[i4 + i3] = bigDecimalPolynomial7.coeffs[i4 + i3].add(bigDecimalPolynomial4.coeffs[i3]);
        }
        while (i < bigDecimalPolynomial3.coeffs.length) {
            bigDecimalPolynomial7.coeffs[(i4 * 2) + i] = bigDecimalPolynomial7.coeffs[(i4 * 2) + i].add(bigDecimalPolynomial3.coeffs[i]);
            i++;
        }
        return bigDecimalPolynomial7;
    }

    public void add(BigDecimalPolynomial bigDecimalPolynomial) {
        int length;
        if (bigDecimalPolynomial.coeffs.length > this.coeffs.length) {
            this.coeffs = copyOf(this.coeffs, bigDecimalPolynomial.coeffs.length);
            for (length = this.coeffs.length; length < this.coeffs.length; length++) {
                this.coeffs[length] = ZERO;
            }
        }
        for (length = 0; length < bigDecimalPolynomial.coeffs.length; length++) {
            this.coeffs[length] = this.coeffs[length].add(bigDecimalPolynomial.coeffs[length]);
        }
    }

    public Object clone() {
        return new BigDecimalPolynomial((BigDecimal[]) this.coeffs.clone());
    }

    public BigDecimal[] getCoeffs() {
        Object obj = new BigDecimal[this.coeffs.length];
        System.arraycopy(this.coeffs, 0, obj, 0, this.coeffs.length);
        return obj;
    }

    public void halve() {
        for (int i = 0; i < this.coeffs.length; i++) {
            this.coeffs[i] = this.coeffs[i].multiply(ONE_HALF);
        }
    }

    public BigDecimalPolynomial mult(BigDecimalPolynomial bigDecimalPolynomial) {
        int length = this.coeffs.length;
        if (bigDecimalPolynomial.coeffs.length != length) {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
        BigDecimalPolynomial multRecursive = multRecursive(bigDecimalPolynomial);
        if (multRecursive.coeffs.length > length) {
            for (int i = length; i < multRecursive.coeffs.length; i++) {
                multRecursive.coeffs[i - length] = multRecursive.coeffs[i - length].add(multRecursive.coeffs[i]);
            }
            multRecursive.coeffs = copyOf(multRecursive.coeffs, length);
        }
        return multRecursive;
    }

    public BigDecimalPolynomial mult(BigIntPolynomial bigIntPolynomial) {
        return mult(new BigDecimalPolynomial(bigIntPolynomial));
    }

    public BigIntPolynomial round() {
        int length = this.coeffs.length;
        BigIntPolynomial bigIntPolynomial = new BigIntPolynomial(length);
        for (int i = 0; i < length; i++) {
            bigIntPolynomial.coeffs[i] = this.coeffs[i].setScale(0, 6).toBigInteger();
        }
        return bigIntPolynomial;
    }

    void sub(BigDecimalPolynomial bigDecimalPolynomial) {
        int length;
        if (bigDecimalPolynomial.coeffs.length > this.coeffs.length) {
            this.coeffs = copyOf(this.coeffs, bigDecimalPolynomial.coeffs.length);
            for (length = this.coeffs.length; length < this.coeffs.length; length++) {
                this.coeffs[length] = ZERO;
            }
        }
        for (length = 0; length < bigDecimalPolynomial.coeffs.length; length++) {
            this.coeffs[length] = this.coeffs[length].subtract(bigDecimalPolynomial.coeffs[length]);
        }
    }
}
