package org.spongycastle.crypto.generators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.NTRUSigningKeyGenerationParameters;
import org.spongycastle.crypto.params.NTRUSigningPrivateKeyParameters;
import org.spongycastle.crypto.params.NTRUSigningPrivateKeyParameters.Basis;
import org.spongycastle.crypto.params.NTRUSigningPublicKeyParameters;
import org.spongycastle.math.ntru.euclid.BigIntEuclidean;
import org.spongycastle.math.ntru.polynomial.BigDecimalPolynomial;
import org.spongycastle.math.ntru.polynomial.BigIntPolynomial;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.polynomial.Resultant;

public class NTRUSigningKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private NTRUSigningKeyGenerationParameters params;

    class BasisGenerationTask implements Callable<Basis> {
        private BasisGenerationTask() {
        }

        public Basis call() throws Exception {
            return NTRUSigningKeyPairGenerator.this.generateBoundedBasis();
        }
    }

    public class FGBasis extends Basis {
        public IntegerPolynomial F;
        public IntegerPolynomial G;

        FGBasis(Polynomial polynomial, Polynomial polynomial2, IntegerPolynomial integerPolynomial, IntegerPolynomial integerPolynomial2, IntegerPolynomial integerPolynomial3, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters) {
            super(polynomial, polynomial2, integerPolynomial, nTRUSigningKeyGenerationParameters);
            this.F = integerPolynomial2;
            this.G = integerPolynomial3;
        }

        boolean isNormOk() {
            double d = NTRUSigningKeyPairGenerator.this.params.keyNormBoundSq;
            int i = NTRUSigningKeyPairGenerator.this.params.q;
            return ((double) this.F.centeredNormSq(i)) < d && ((double) this.G.centeredNormSq(i)) < d;
        }
    }

    private FGBasis generateBasis() {
        Polynomial generateRandom;
        IntegerPolynomial toIntegerPolynomial;
        Polynomial generateRandom2;
        IntegerPolynomial toIntegerPolynomial2;
        Resultant resultant;
        BigIntEuclidean calculate;
        IntegerPolynomial integerPolynomial;
        IntegerPolynomial integerPolynomial2;
        BigIntPolynomial mult;
        IntegerPolynomial mult2;
        Polynomial polynomial;
        int i = this.params.N;
        int i2 = this.params.q;
        int i3 = this.params.d;
        int i4 = this.params.d1;
        int i5 = this.params.d2;
        int i6 = this.params.d3;
        int i7 = this.params.basisType;
        int i8 = (i * 2) + 1;
        boolean z = this.params.primeCheck;
        while (true) {
            IntegerPolynomial invertFq;
            generateRandom = this.params.polyType == 0 ? DenseTernaryPolynomial.generateRandom(i, i3 + 1, i3, new SecureRandom()) : ProductFormPolynomial.generateRandom(i, i4, i5, i6 + 1, i6, new SecureRandom());
            toIntegerPolynomial = generateRandom.toIntegerPolynomial();
            if (!z || !toIntegerPolynomial.resultant(i8).res.equals(BigInteger.ZERO)) {
                invertFq = toIntegerPolynomial.invertFq(i2);
                if (invertFq != null) {
                    break;
                }
            }
        }
        Resultant resultant2 = toIntegerPolynomial.resultant();
        while (true) {
            if (this.params.polyType == 0) {
                generateRandom2 = DenseTernaryPolynomial.generateRandom(i, i3 + 1, i3, new SecureRandom());
            } else {
                Object generateRandom3 = ProductFormPolynomial.generateRandom(i, i4, i5, i6 + 1, i6, new SecureRandom());
            }
            toIntegerPolynomial2 = generateRandom2.toIntegerPolynomial();
            if (!((z && toIntegerPolynomial2.resultant(i8).res.equals(BigInteger.ZERO)) || toIntegerPolynomial2.invertFq(i2) == null)) {
                resultant = toIntegerPolynomial2.resultant();
                calculate = BigIntEuclidean.calculate(resultant2.res, resultant.res);
                if (calculate.gcd.equals(BigInteger.ONE)) {
                    break;
                }
            }
        }
        BigIntPolynomial bigIntPolynomial = (BigIntPolynomial) resultant2.rho.clone();
        bigIntPolynomial.mult(calculate.x.multiply(BigInteger.valueOf((long) i2)));
        BigIntPolynomial bigIntPolynomial2 = (BigIntPolynomial) resultant.rho.clone();
        bigIntPolynomial2.mult(calculate.y.multiply(BigInteger.valueOf((long) (-i2))));
        if (this.params.keyGenAlg == 0) {
            int[] iArr = new int[i];
            int[] iArr2 = new int[i];
            iArr[0] = toIntegerPolynomial.coeffs[0];
            iArr2[0] = toIntegerPolynomial2.coeffs[0];
            for (i6 = 1; i6 < i; i6++) {
                iArr[i6] = toIntegerPolynomial.coeffs[i - i6];
                iArr2[i6] = toIntegerPolynomial2.coeffs[i - i6];
            }
            integerPolynomial = new IntegerPolynomial(iArr);
            integerPolynomial2 = new IntegerPolynomial(iArr2);
            IntegerPolynomial mult3 = generateRandom.mult(integerPolynomial);
            mult3.add(generateRandom2.mult(integerPolynomial2));
            Resultant resultant3 = mult3.resultant();
            mult = integerPolynomial.mult(bigIntPolynomial2);
            mult.add(integerPolynomial2.mult(bigIntPolynomial));
            mult = mult.mult(resultant3.rho);
            mult.div(resultant3.res);
        } else {
            i6 = 0;
            for (int i9 = 1; i9 < i; i9 *= 10) {
                i6++;
            }
            BigDecimalPolynomial div = resultant2.rho.div(new BigDecimal(resultant2.res), (bigIntPolynomial2.getMaxCoeffLength() + 1) + i6);
            BigDecimalPolynomial div2 = resultant.rho.div(new BigDecimal(resultant.res), i6 + (bigIntPolynomial.getMaxCoeffLength() + 1));
            div = div.mult(bigIntPolynomial2);
            div.add(div2.mult(bigIntPolynomial));
            div.halve();
            mult = div.round();
        }
        bigIntPolynomial2 = (BigIntPolynomial) bigIntPolynomial2.clone();
        bigIntPolynomial2.sub(generateRandom.mult(mult));
        bigIntPolynomial = (BigIntPolynomial) bigIntPolynomial.clone();
        bigIntPolynomial.sub(generateRandom2.mult(mult));
        integerPolynomial = new IntegerPolynomial(bigIntPolynomial2);
        integerPolynomial2 = new IntegerPolynomial(bigIntPolynomial);
        minimizeFG(toIntegerPolynomial, toIntegerPolynomial2, integerPolynomial, integerPolynomial2, i);
        if (i7 == 0) {
            mult2 = generateRandom2.mult(invertFq, i2);
            polynomial = integerPolynomial;
        } else {
            mult2 = integerPolynomial.mult(invertFq, i2);
            polynomial = generateRandom2;
        }
        mult2.modPositive(i2);
        return new FGBasis(generateRandom, polynomial, mult2, integerPolynomial, integerPolynomial2, this.params);
    }

    private void minimizeFG(IntegerPolynomial integerPolynomial, IntegerPolynomial integerPolynomial2, IntegerPolynomial integerPolynomial3, IntegerPolynomial integerPolynomial4, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += (i * 2) * ((integerPolynomial.coeffs[i3] * integerPolynomial.coeffs[i3]) + (integerPolynomial2.coeffs[i3] * integerPolynomial2.coeffs[i3]));
        }
        int i4 = i2 - 4;
        IntegerPolynomial integerPolynomial5 = (IntegerPolynomial) integerPolynomial.clone();
        IntegerPolynomial integerPolynomial6 = (IntegerPolynomial) integerPolynomial2.clone();
        int i5 = 0;
        int i6 = 0;
        while (i6 < i && r3 < i) {
            int i7 = 0;
            for (int i8 = 0; i8 < i; i8++) {
                i7 += ((integerPolynomial3.coeffs[i8] * integerPolynomial.coeffs[i8]) + (integerPolynomial4.coeffs[i8] * integerPolynomial2.coeffs[i8])) * (i * 4);
            }
            i7 -= (integerPolynomial3.sumCoeffs() + integerPolynomial4.sumCoeffs()) * 4;
            if (i7 > i4) {
                integerPolynomial3.sub(integerPolynomial5);
                integerPolynomial4.sub(integerPolynomial6);
                i6++;
                i5 = 0;
            } else if (i7 < (-i4)) {
                integerPolynomial3.add(integerPolynomial5);
                integerPolynomial4.add(integerPolynomial6);
                i6++;
                i5 = 0;
            }
            i5++;
            integerPolynomial5.rotate1();
            integerPolynomial6.rotate1();
        }
    }

    public Basis generateBoundedBasis() {
        Basis generateBasis;
        do {
            generateBasis = generateBasis();
        } while (!generateBasis.isNormOk());
        return generateBasis;
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters = null;
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        List arrayList = new ArrayList();
        for (int i = this.params.B; i >= 0; i--) {
            arrayList.add(newCachedThreadPool.submit(new BasisGenerationTask()));
        }
        newCachedThreadPool.shutdown();
        List arrayList2 = new ArrayList();
        int i2 = this.params.B;
        while (i2 >= 0) {
            Future future = (Future) arrayList.get(i2);
            try {
                arrayList2.add(future.get());
                NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters2 = i2 == this.params.B ? new NTRUSigningPublicKeyParameters(((Basis) future.get()).h, this.params.getSigningParameters()) : nTRUSigningPublicKeyParameters;
                i2--;
                nTRUSigningPublicKeyParameters = nTRUSigningPublicKeyParameters2;
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
        return new AsymmetricCipherKeyPair(nTRUSigningPublicKeyParameters, new NTRUSigningPrivateKeyParameters(arrayList2, nTRUSigningPublicKeyParameters));
    }

    public AsymmetricCipherKeyPair generateKeyPairSingleThread() {
        List arrayList = new ArrayList();
        NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters = null;
        for (int i = this.params.B; i >= 0; i--) {
            Basis generateBoundedBasis = generateBoundedBasis();
            arrayList.add(generateBoundedBasis);
            if (i == 0) {
                nTRUSigningPublicKeyParameters = new NTRUSigningPublicKeyParameters(generateBoundedBasis.h, this.params.getSigningParameters());
            }
        }
        return new AsymmetricCipherKeyPair(nTRUSigningPublicKeyParameters, new NTRUSigningPrivateKeyParameters(arrayList, nTRUSigningPublicKeyParameters));
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.params = (NTRUSigningKeyGenerationParameters) keyGenerationParameters;
    }
}
