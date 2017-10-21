package org.spongycastle.math.ntru.polynomial;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import org.spongycastle.util.Arrays;

public class ProductFormPolynomial implements Polynomial {
    private SparseTernaryPolynomial f1;
    private SparseTernaryPolynomial f2;
    private SparseTernaryPolynomial f3;

    public ProductFormPolynomial(SparseTernaryPolynomial sparseTernaryPolynomial, SparseTernaryPolynomial sparseTernaryPolynomial2, SparseTernaryPolynomial sparseTernaryPolynomial3) {
        this.f1 = sparseTernaryPolynomial;
        this.f2 = sparseTernaryPolynomial2;
        this.f3 = sparseTernaryPolynomial3;
    }

    public static ProductFormPolynomial fromBinary(InputStream inputStream, int i, int i2, int i3, int i4, int i5) throws IOException {
        return new ProductFormPolynomial(SparseTernaryPolynomial.fromBinary(inputStream, i, i2, i2), SparseTernaryPolynomial.fromBinary(inputStream, i, i3, i3), SparseTernaryPolynomial.fromBinary(inputStream, i, i4, i5));
    }

    public static ProductFormPolynomial fromBinary(byte[] bArr, int i, int i2, int i3, int i4, int i5) throws IOException {
        return fromBinary(new ByteArrayInputStream(bArr), i, i2, i3, i4, i5);
    }

    public static ProductFormPolynomial generateRandom(int i, int i2, int i3, int i4, int i5, SecureRandom secureRandom) {
        return new ProductFormPolynomial(SparseTernaryPolynomial.generateRandom(i, i2, i2, secureRandom), SparseTernaryPolynomial.generateRandom(i, i3, i3, secureRandom), SparseTernaryPolynomial.generateRandom(i, i4, i5, secureRandom));
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ProductFormPolynomial productFormPolynomial = (ProductFormPolynomial) obj;
            if (this.f1 == null) {
                if (productFormPolynomial.f1 != null) {
                    return false;
                }
            } else if (!this.f1.equals(productFormPolynomial.f1)) {
                return false;
            }
            if (this.f2 == null) {
                if (productFormPolynomial.f2 != null) {
                    return false;
                }
            } else if (!this.f2.equals(productFormPolynomial.f2)) {
                return false;
            }
            if (this.f3 == null) {
                if (productFormPolynomial.f3 != null) {
                    return false;
                }
            } else if (!this.f3.equals(productFormPolynomial.f3)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.f1 == null ? 0 : this.f1.hashCode();
        int hashCode2 = this.f2 == null ? 0 : this.f2.hashCode();
        if (this.f3 != null) {
            i = this.f3.hashCode();
        }
        return ((((hashCode + 31) * 31) + hashCode2) * 31) + i;
    }

    public BigIntPolynomial mult(BigIntPolynomial bigIntPolynomial) {
        BigIntPolynomial mult = this.f2.mult(this.f1.mult(bigIntPolynomial));
        mult.add(this.f3.mult(bigIntPolynomial));
        return mult;
    }

    public IntegerPolynomial mult(IntegerPolynomial integerPolynomial) {
        IntegerPolynomial mult = this.f2.mult(this.f1.mult(integerPolynomial));
        mult.add(this.f3.mult(integerPolynomial));
        return mult;
    }

    public IntegerPolynomial mult(IntegerPolynomial integerPolynomial, int i) {
        IntegerPolynomial mult = mult(integerPolynomial);
        mult.mod(i);
        return mult;
    }

    public byte[] toBinary() {
        byte[] toBinary = this.f1.toBinary();
        Object toBinary2 = this.f2.toBinary();
        Object toBinary3 = this.f3.toBinary();
        Object copyOf = Arrays.copyOf(toBinary, (toBinary.length + toBinary2.length) + toBinary3.length);
        System.arraycopy(toBinary2, 0, copyOf, toBinary.length, toBinary2.length);
        System.arraycopy(toBinary3, 0, copyOf, toBinary.length + toBinary2.length, toBinary3.length);
        return copyOf;
    }

    public IntegerPolynomial toIntegerPolynomial() {
        IntegerPolynomial mult = this.f1.mult(this.f2.toIntegerPolynomial());
        mult.add(this.f3.toIntegerPolynomial());
        return mult;
    }
}
