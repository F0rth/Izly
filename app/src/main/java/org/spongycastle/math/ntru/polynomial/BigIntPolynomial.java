package org.spongycastle.math.ntru.polynomial;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.spongycastle.util.Arrays;

public class BigIntPolynomial {
    private static final double LOG_10_2 = Math.log10(2.0d);
    BigInteger[] coeffs;

    BigIntPolynomial(int i) {
        this.coeffs = new BigInteger[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.coeffs[i2] = Constants.BIGINT_ZERO;
        }
    }

    public BigIntPolynomial(IntegerPolynomial integerPolynomial) {
        this.coeffs = new BigInteger[integerPolynomial.coeffs.length];
        for (int i = 0; i < this.coeffs.length; i++) {
            this.coeffs[i] = BigInteger.valueOf((long) integerPolynomial.coeffs[i]);
        }
    }

    BigIntPolynomial(BigInteger[] bigIntegerArr) {
        this.coeffs = bigIntegerArr;
    }

    static BigIntPolynomial generateRandomSmall(int i, int i2, int i3) {
        int i4;
        List arrayList = new ArrayList();
        for (i4 = 0; i4 < i2; i4++) {
            arrayList.add(Constants.BIGINT_ONE);
        }
        for (i4 = 0; i4 < i3; i4++) {
            arrayList.add(BigInteger.valueOf(-1));
        }
        while (arrayList.size() < i) {
            arrayList.add(Constants.BIGINT_ZERO);
        }
        Collections.shuffle(arrayList, new SecureRandom());
        BigIntPolynomial bigIntPolynomial = new BigIntPolynomial(i);
        for (i4 = 0; i4 < arrayList.size(); i4++) {
            bigIntPolynomial.coeffs[i4] = (BigInteger) arrayList.get(i4);
        }
        return bigIntPolynomial;
    }

    private BigInteger maxCoeffAbs() {
        BigInteger abs = this.coeffs[0].abs();
        for (int i = 1; i < this.coeffs.length; i++) {
            BigInteger abs2 = this.coeffs[i].abs();
            if (abs2.compareTo(abs) > 0) {
                abs = abs2;
            }
        }
        return abs;
    }

    private BigIntPolynomial multRecursive(BigIntPolynomial bigIntPolynomial) {
        int i = 0;
        BigInteger[] bigIntegerArr = this.coeffs;
        BigInteger[] bigIntegerArr2 = bigIntPolynomial.coeffs;
        int length = bigIntPolynomial.coeffs.length;
        int i2;
        if (length <= 1) {
            bigIntegerArr2 = Arrays.clone(this.coeffs);
            for (i2 = 0; i2 < this.coeffs.length; i2++) {
                bigIntegerArr2[i2] = bigIntegerArr2[i2].multiply(bigIntPolynomial.coeffs[0]);
            }
            return new BigIntPolynomial(bigIntegerArr2);
        }
        int i3 = length / 2;
        BigIntPolynomial bigIntPolynomial2 = new BigIntPolynomial(Arrays.copyOf(bigIntegerArr, i3));
        BigIntPolynomial bigIntPolynomial3 = new BigIntPolynomial(Arrays.copyOfRange(bigIntegerArr, i3, length));
        BigIntPolynomial bigIntPolynomial4 = new BigIntPolynomial(Arrays.copyOf(bigIntegerArr2, i3));
        BigIntPolynomial bigIntPolynomial5 = new BigIntPolynomial(Arrays.copyOfRange(bigIntegerArr2, i3, length));
        BigIntPolynomial bigIntPolynomial6 = (BigIntPolynomial) bigIntPolynomial2.clone();
        bigIntPolynomial6.add(bigIntPolynomial3);
        BigIntPolynomial bigIntPolynomial7 = (BigIntPolynomial) bigIntPolynomial4.clone();
        bigIntPolynomial7.add(bigIntPolynomial5);
        bigIntPolynomial2 = bigIntPolynomial2.multRecursive(bigIntPolynomial4);
        bigIntPolynomial3 = bigIntPolynomial3.multRecursive(bigIntPolynomial5);
        bigIntPolynomial4 = bigIntPolynomial6.multRecursive(bigIntPolynomial7);
        bigIntPolynomial4.sub(bigIntPolynomial2);
        bigIntPolynomial4.sub(bigIntPolynomial3);
        bigIntPolynomial7 = new BigIntPolynomial((length * 2) - 1);
        for (i2 = 0; i2 < bigIntPolynomial2.coeffs.length; i2++) {
            bigIntPolynomial7.coeffs[i2] = bigIntPolynomial2.coeffs[i2];
        }
        for (i2 = 0; i2 < bigIntPolynomial4.coeffs.length; i2++) {
            bigIntPolynomial7.coeffs[i3 + i2] = bigIntPolynomial7.coeffs[i3 + i2].add(bigIntPolynomial4.coeffs[i2]);
        }
        while (i < bigIntPolynomial3.coeffs.length) {
            bigIntPolynomial7.coeffs[(i3 * 2) + i] = bigIntPolynomial7.coeffs[(i3 * 2) + i].add(bigIntPolynomial3.coeffs[i]);
            i++;
        }
        return bigIntPolynomial7;
    }

    public void add(BigIntPolynomial bigIntPolynomial) {
        int length;
        if (bigIntPolynomial.coeffs.length > this.coeffs.length) {
            this.coeffs = Arrays.copyOf(this.coeffs, bigIntPolynomial.coeffs.length);
            for (length = this.coeffs.length; length < this.coeffs.length; length++) {
                this.coeffs[length] = Constants.BIGINT_ZERO;
            }
        }
        for (length = 0; length < bigIntPolynomial.coeffs.length; length++) {
            this.coeffs[length] = this.coeffs[length].add(bigIntPolynomial.coeffs[length]);
        }
    }

    void add(BigIntPolynomial bigIntPolynomial, BigInteger bigInteger) {
        add(bigIntPolynomial);
        mod(bigInteger);
    }

    public Object clone() {
        return new BigIntPolynomial((BigInteger[]) this.coeffs.clone());
    }

    public BigDecimalPolynomial div(BigDecimal bigDecimal, int i) {
        BigDecimal divide = Constants.BIGDEC_ONE.divide(bigDecimal, ((((int) (((double) maxCoeffAbs().bitLength()) * LOG_10_2)) + 1) + i) + 1, 6);
        BigDecimalPolynomial bigDecimalPolynomial = new BigDecimalPolynomial(this.coeffs.length);
        for (int i2 = 0; i2 < this.coeffs.length; i2++) {
            bigDecimalPolynomial.coeffs[i2] = new BigDecimal(this.coeffs[i2]).multiply(divide).setScale(i, 6);
        }
        return bigDecimalPolynomial;
    }

    public void div(BigInteger bigInteger) {
        BigInteger divide = bigInteger.add(Constants.BIGINT_ONE).divide(BigInteger.valueOf(2));
        for (int i = 0; i < this.coeffs.length; i++) {
            this.coeffs[i] = this.coeffs[i].compareTo(Constants.BIGINT_ZERO) > 0 ? this.coeffs[i].add(divide) : this.coeffs[i].add(divide.negate());
            this.coeffs[i] = this.coeffs[i].divide(bigInteger);
        }
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            if (!Arrays.areEqual(this.coeffs, ((BigIntPolynomial) obj).coeffs)) {
                return false;
            }
        }
        return true;
    }

    public BigInteger[] getCoeffs() {
        return Arrays.clone(this.coeffs);
    }

    public int getMaxCoeffLength() {
        return ((int) (((double) maxCoeffAbs().bitLength()) * LOG_10_2)) + 1;
    }

    public int hashCode() {
        return Arrays.hashCode(this.coeffs) + 31;
    }

    public void mod(BigInteger bigInteger) {
        for (int i = 0; i < this.coeffs.length; i++) {
            this.coeffs[i] = this.coeffs[i].mod(bigInteger);
        }
    }

    public BigIntPolynomial mult(BigIntPolynomial bigIntPolynomial) {
        int length = this.coeffs.length;
        if (bigIntPolynomial.coeffs.length != length) {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
        BigIntPolynomial multRecursive = multRecursive(bigIntPolynomial);
        if (multRecursive.coeffs.length > length) {
            for (int i = length; i < multRecursive.coeffs.length; i++) {
                multRecursive.coeffs[i - length] = multRecursive.coeffs[i - length].add(multRecursive.coeffs[i]);
            }
            multRecursive.coeffs = Arrays.copyOf(multRecursive.coeffs, length);
        }
        return multRecursive;
    }

    void mult(int i) {
        mult(BigInteger.valueOf((long) i));
    }

    public void mult(BigInteger bigInteger) {
        for (int i = 0; i < this.coeffs.length; i++) {
            this.coeffs[i] = this.coeffs[i].multiply(bigInteger);
        }
    }

    public void sub(BigIntPolynomial bigIntPolynomial) {
        int length;
        if (bigIntPolynomial.coeffs.length > this.coeffs.length) {
            this.coeffs = Arrays.copyOf(this.coeffs, bigIntPolynomial.coeffs.length);
            for (length = this.coeffs.length; length < this.coeffs.length; length++) {
                this.coeffs[length] = Constants.BIGINT_ZERO;
            }
        }
        for (length = 0; length < bigIntPolynomial.coeffs.length; length++) {
            this.coeffs[length] = this.coeffs[length].subtract(bigIntPolynomial.coeffs[length]);
        }
    }

    BigInteger sumCoeffs() {
        BigInteger bigInteger = Constants.BIGINT_ZERO;
        for (BigInteger add : this.coeffs) {
            bigInteger = bigInteger.add(add);
        }
        return bigInteger;
    }
}
