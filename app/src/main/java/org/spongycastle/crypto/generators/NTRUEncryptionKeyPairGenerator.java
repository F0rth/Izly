package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.NTRUEncryptionKeyGenerationParameters;
import org.spongycastle.crypto.params.NTRUEncryptionPrivateKeyParameters;
import org.spongycastle.crypto.params.NTRUEncryptionPublicKeyParameters;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.util.Util;

public class NTRUEncryptionKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private NTRUEncryptionKeyGenerationParameters params;

    public AsymmetricCipherKeyPair generateKeyPair() {
        Polynomial polynomial;
        IntegerPolynomial integerPolynomial;
        DenseTernaryPolynomial generateRandom;
        int i = this.params.N;
        int i2 = this.params.q;
        int i3 = this.params.df;
        int i4 = this.params.df1;
        int i5 = this.params.df2;
        int i6 = this.params.df3;
        int i7 = this.params.dg;
        boolean z = this.params.fastFp;
        boolean z2 = this.params.sparse;
        IntegerPolynomial integerPolynomial2 = null;
        while (true) {
            if (z) {
                Polynomial generateRandomTernary = this.params.polyType == 0 ? Util.generateRandomTernary(i, i3, i3, z2, this.params.getRandom()) : ProductFormPolynomial.generateRandom(i, i4, i5, i6, i6, this.params.getRandom());
                IntegerPolynomial toIntegerPolynomial = generateRandomTernary.toIntegerPolynomial();
                toIntegerPolynomial.mult(3);
                int[] iArr = toIntegerPolynomial.coeffs;
                iArr[0] = iArr[0] + 1;
                IntegerPolynomial integerPolynomial3 = toIntegerPolynomial;
                polynomial = generateRandomTernary;
                integerPolynomial = integerPolynomial3;
            } else {
                polynomial = this.params.polyType == 0 ? Util.generateRandomTernary(i, i3, i3 - 1, z2, this.params.getRandom()) : ProductFormPolynomial.generateRandom(i, i4, i5, i6, i6 - 1, this.params.getRandom());
                integerPolynomial = polynomial.toIntegerPolynomial();
                integerPolynomial2 = integerPolynomial.invertF3();
                if (integerPolynomial2 == null) {
                    continue;
                }
            }
            integerPolynomial = integerPolynomial.invertFq(i2);
            if (integerPolynomial != null) {
                break;
            }
        }
        if (z) {
            integerPolynomial2 = new IntegerPolynomial(i);
            integerPolynomial2.coeffs[0] = 1;
        }
        do {
            generateRandom = DenseTernaryPolynomial.generateRandom(i, i7, i7 - 1, this.params.getRandom());
        } while (generateRandom.invertFq(i2) == null);
        IntegerPolynomial mult = generateRandom.mult(integerPolynomial, i2);
        mult.mult3(i2);
        mult.ensurePositive(i2);
        generateRandom.clear();
        integerPolynomial.clear();
        return new AsymmetricCipherKeyPair(new NTRUEncryptionPublicKeyParameters(mult, this.params.getEncryptionParameters()), new NTRUEncryptionPrivateKeyParameters(mult, polynomial, integerPolynomial2, this.params.getEncryptionParameters()));
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.params = (NTRUEncryptionKeyGenerationParameters) keyGenerationParameters;
    }
}
