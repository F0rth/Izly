package org.spongycastle.math.ntru.polynomial;

public interface TernaryPolynomial extends Polynomial {
    void clear();

    int[] getNegOnes();

    int[] getOnes();

    IntegerPolynomial mult(IntegerPolynomial integerPolynomial);

    int size();
}
