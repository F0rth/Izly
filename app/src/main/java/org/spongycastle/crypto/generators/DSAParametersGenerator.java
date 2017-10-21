package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAValidationParameters;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;

public class DSAParametersGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private int L;
    private int N;
    private int certainty;
    private SecureRandom random;

    private static BigInteger calculateGenerator_FIPS186_2(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger modPow;
        BigInteger divide = bigInteger.subtract(ONE).divide(bigInteger2);
        BigInteger subtract = bigInteger.subtract(TWO);
        do {
            modPow = BigIntegers.createRandomInRange(TWO, subtract, secureRandom).modPow(divide, bigInteger);
        } while (modPow.bitLength() <= 1);
        return modPow;
    }

    private static BigInteger calculateGenerator_FIPS186_3_Unverifiable(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        return calculateGenerator_FIPS186_2(bigInteger, bigInteger2, secureRandom);
    }

    private DSAParameters generateParameters_FIPS186_2() {
        byte[] bArr = new byte[20];
        Object obj = new byte[20];
        Object obj2 = new byte[20];
        byte[] bArr2 = new byte[20];
        Digest sHA1Digest = new SHA1Digest();
        int i = (this.L - 1) / 160;
        Object obj3 = new byte[(this.L / 8)];
        while (true) {
            int i2;
            this.random.nextBytes(bArr);
            hash(sHA1Digest, bArr, obj);
            System.arraycopy(bArr, 0, obj2, 0, 20);
            inc(obj2);
            hash(sHA1Digest, obj2, obj2);
            for (i2 = 0; i2 != 20; i2++) {
                bArr2[i2] = (byte) (obj[i2] ^ obj2[i2]);
            }
            bArr2[0] = (byte) (bArr2[0] | -128);
            bArr2[19] = (byte) (bArr2[19] | 1);
            BigInteger bigInteger = new BigInteger(1, bArr2);
            if (bigInteger.isProbablePrime(this.certainty)) {
                byte[] clone = Arrays.clone(bArr);
                inc(clone);
                for (int i3 = 0; i3 < PKIFailureInfo.certConfirmed; i3++) {
                    for (i2 = 0; i2 < i; i2++) {
                        inc(clone);
                        hash(sHA1Digest, clone, obj);
                        System.arraycopy(obj, 0, obj3, obj3.length - ((i2 + 1) * 20), 20);
                    }
                    inc(clone);
                    hash(sHA1Digest, clone, obj);
                    System.arraycopy(obj, 20 - (obj3.length - (i * 20)), obj3, 0, obj3.length - (i * 20));
                    obj3[0] = (byte) (obj3[0] | -128);
                    BigInteger bigInteger2 = new BigInteger(1, obj3);
                    bigInteger2 = bigInteger2.subtract(bigInteger2.mod(bigInteger.shiftLeft(1)).subtract(ONE));
                    if (bigInteger2.bitLength() == this.L && bigInteger2.isProbablePrime(this.certainty)) {
                        return new DSAParameters(bigInteger2, bigInteger, calculateGenerator_FIPS186_2(bigInteger2, bigInteger, this.random), new DSAValidationParameters(bArr, i3));
                    }
                }
                continue;
            }
        }
    }

    private DSAParameters generateParameters_FIPS186_3() {
        Digest sHA256Digest = new SHA256Digest();
        int digestSize = sHA256Digest.getDigestSize() * 8;
        byte[] bArr = new byte[(this.N / 8)];
        int i = (this.L - 1) / digestSize;
        int i2 = this.L;
        byte[] bArr2 = new byte[sHA256Digest.getDigestSize()];
        while (true) {
            this.random.nextBytes(bArr);
            hash(sHA256Digest, bArr, bArr2);
            BigInteger mod = new BigInteger(1, bArr2).mod(ONE.shiftLeft(this.N - 1));
            BigInteger subtract = ONE.shiftLeft(this.N - 1).add(mod).add(ONE).subtract(mod.mod(TWO));
            if (subtract.isProbablePrime(this.certainty)) {
                byte[] clone = Arrays.clone(bArr);
                int i3 = this.L;
                for (int i4 = 0; i4 < i3 * 4; i4++) {
                    int i5 = 0;
                    int i6 = 0;
                    BigInteger bigInteger = ZERO;
                    while (i6 <= i) {
                        inc(clone);
                        hash(sHA256Digest, clone, bArr2);
                        mod = new BigInteger(1, bArr2);
                        if (i6 == i) {
                            mod = mod.mod(ONE.shiftLeft((i2 - 1) % digestSize));
                        }
                        mod = bigInteger.add(mod.shiftLeft(i5));
                        i5 += digestSize;
                        i6++;
                        bigInteger = mod;
                    }
                    mod = bigInteger.add(ONE.shiftLeft(this.L - 1));
                    mod = mod.subtract(mod.mod(subtract.shiftLeft(1)).subtract(ONE));
                    if (mod.bitLength() == this.L && mod.isProbablePrime(this.certainty)) {
                        return new DSAParameters(mod, subtract, calculateGenerator_FIPS186_3_Unverifiable(mod, subtract, this.random), new DSAValidationParameters(bArr, i4));
                    }
                }
                continue;
            }
        }
    }

    private static int getDefaultN(int i) {
        return i > PKIFailureInfo.badRecipientNonce ? 256 : 160;
    }

    private static void hash(Digest digest, byte[] bArr, byte[] bArr2) {
        digest.update(bArr, 0, bArr.length);
        digest.doFinal(bArr2, 0);
    }

    private static void inc(byte[] bArr) {
        int length = bArr.length - 1;
        while (length >= 0) {
            byte b = (byte) ((bArr[length] + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            bArr[length] = b;
            if (b == (byte) 0) {
                length--;
            } else {
                return;
            }
        }
    }

    private void init(int i, int i2, int i3, SecureRandom secureRandom) {
        this.L = i;
        this.N = i2;
        this.certainty = i3;
        this.random = secureRandom;
    }

    public DSAParameters generateParameters() {
        return this.L > PKIFailureInfo.badRecipientNonce ? generateParameters_FIPS186_3() : generateParameters_FIPS186_2();
    }

    public void init(int i, int i2, SecureRandom secureRandom) {
        init(i, getDefaultN(i), i2, secureRandom);
    }
}
