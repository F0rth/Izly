package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.NTRUEncryptionParameters;
import org.spongycastle.crypto.params.NTRUEncryptionPrivateKeyParameters;
import org.spongycastle.crypto.params.NTRUEncryptionPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.polynomial.SparseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.TernaryPolynomial;
import org.spongycastle.util.Arrays;

public class NTRUEngine implements AsymmetricBlockCipher {
    private boolean forEncryption;
    private NTRUEncryptionParameters params;
    private NTRUEncryptionPrivateKeyParameters privKey;
    private NTRUEncryptionPublicKeyParameters pubKey;
    private SecureRandom random;

    private IntegerPolynomial MGF(byte[] bArr, int i, int i2, boolean z) {
        Digest digest = this.params.hashAlg;
        int digestSize = digest.getDigestSize();
        Object obj = new byte[(i2 * digestSize)];
        if (z) {
            bArr = calcHash(digest, bArr);
        }
        int i3 = 0;
        while (i3 < i2) {
            digest.update(bArr, 0, bArr.length);
            putInt(digest, i3);
            System.arraycopy(calcHash(digest), 0, obj, i3 * digestSize, digestSize);
            i3++;
        }
        IntegerPolynomial integerPolynomial = new IntegerPolynomial(i);
        while (true) {
            int i4 = 0;
            for (digestSize = 0; digestSize != obj.length; digestSize++) {
                int i5 = obj[digestSize] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                if (i5 < 243) {
                    for (int i6 = 0; i6 < 4; i6++) {
                        int i7 = i5 % 3;
                        integerPolynomial.coeffs[i4] = i7 - 1;
                        i4++;
                        if (i4 == i) {
                            return integerPolynomial;
                        }
                        i5 = (i5 - i7) / 3;
                    }
                    integerPolynomial.coeffs[i4] = i5 - 1;
                    i4++;
                    if (i4 == i) {
                        return integerPolynomial;
                    }
                }
            }
            if (i4 >= i) {
                return integerPolynomial;
            }
            digest.update(bArr, 0, bArr.length);
            putInt(digest, i3);
            obj = calcHash(digest);
            i3++;
        }
    }

    private byte[] buildSData(byte[] bArr, byte[] bArr2, int i, byte[] bArr3, byte[] bArr4) {
        Object obj = new byte[(((bArr.length + i) + bArr3.length) + bArr4.length)];
        System.arraycopy(bArr, 0, obj, 0, bArr.length);
        System.arraycopy(bArr2, 0, obj, bArr.length, bArr2.length);
        System.arraycopy(bArr3, 0, obj, bArr.length + bArr2.length, bArr3.length);
        System.arraycopy(bArr4, 0, obj, (bArr.length + bArr2.length) + bArr3.length, bArr4.length);
        return obj;
    }

    private byte[] calcHash(Digest digest) {
        byte[] bArr = new byte[digest.getDigestSize()];
        digest.doFinal(bArr, 0);
        return bArr;
    }

    private byte[] calcHash(Digest digest, byte[] bArr) {
        byte[] bArr2 = new byte[digest.getDigestSize()];
        digest.update(bArr, 0, bArr.length);
        digest.doFinal(bArr2, 0);
        return bArr2;
    }

    private byte[] copyOf(byte[] bArr, int i) {
        Object obj = new byte[i];
        if (i >= bArr.length) {
            i = bArr.length;
        }
        System.arraycopy(bArr, 0, obj, 0, i);
        return obj;
    }

    private byte[] decrypt(byte[] bArr, NTRUEncryptionPrivateKeyParameters nTRUEncryptionPrivateKeyParameters) throws InvalidCipherTextException {
        Polynomial polynomial = nTRUEncryptionPrivateKeyParameters.t;
        IntegerPolynomial integerPolynomial = nTRUEncryptionPrivateKeyParameters.fp;
        IntegerPolynomial integerPolynomial2 = nTRUEncryptionPrivateKeyParameters.h;
        int i = this.params.N;
        int i2 = this.params.q;
        int i3 = this.params.db;
        int i4 = this.params.maxMsgLenBytes;
        int i5 = this.params.dm0;
        int i6 = this.params.pkLen;
        int i7 = this.params.minCallsMask;
        boolean z = this.params.hashSeed;
        byte[] bArr2 = this.params.oid;
        if (i4 > CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) {
            throw new DataLengthException("maxMsgLenBytes values bigger than 255 are not supported");
        }
        int i8 = i3 / 8;
        IntegerPolynomial fromBinary = IntegerPolynomial.fromBinary(bArr, i, i2);
        integerPolynomial = decrypt(fromBinary, polynomial, integerPolynomial);
        if (integerPolynomial.count(-1) < i5) {
            throw new InvalidCipherTextException("Less than dm0 coefficients equal -1");
        } else if (integerPolynomial.count(0) < i5) {
            throw new InvalidCipherTextException("Less than dm0 coefficients equal 0");
        } else if (integerPolynomial.count(1) < i5) {
            throw new InvalidCipherTextException("Less than dm0 coefficients equal 1");
        } else {
            IntegerPolynomial integerPolynomial3 = (IntegerPolynomial) fromBinary.clone();
            integerPolynomial3.sub(integerPolynomial);
            integerPolynomial3.modPositive(i2);
            IntegerPolynomial integerPolynomial4 = (IntegerPolynomial) integerPolynomial3.clone();
            integerPolynomial4.modPositive(4);
            integerPolynomial.sub(MGF(integerPolynomial4.toBinary(4), i, i7, z));
            integerPolynomial.mod3();
            Object toBinary3Sves = integerPolynomial.toBinary3Sves();
            Object obj = new byte[i8];
            System.arraycopy(toBinary3Sves, 0, obj, 0, i8);
            i = toBinary3Sves[i8] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            if (i > i4) {
                throw new InvalidCipherTextException("Message too long: " + i + ">" + i4);
            }
            Object obj2 = new byte[i];
            System.arraycopy(toBinary3Sves, i8 + 1, obj2, 0, i);
            byte[] bArr3 = new byte[(toBinary3Sves.length - ((i8 + 1) + i))];
            System.arraycopy(toBinary3Sves, (i8 + 1) + i, bArr3, 0, bArr3.length);
            if (Arrays.areEqual(bArr3, new byte[bArr3.length])) {
                integerPolynomial4 = generateBlindingPoly(buildSData(bArr2, obj2, i, obj, copyOf(integerPolynomial2.toBinary(i2), i6 / 8)), obj2).mult(integerPolynomial2);
                integerPolynomial4.modPositive(i2);
                if (integerPolynomial4.equals(integerPolynomial3)) {
                    return obj2;
                }
                throw new InvalidCipherTextException("Invalid message encoding");
            }
            throw new InvalidCipherTextException("The message is not followed by zeroes");
        }
    }

    private byte[] encrypt(byte[] bArr, NTRUEncryptionPublicKeyParameters nTRUEncryptionPublicKeyParameters) {
        IntegerPolynomial integerPolynomial = nTRUEncryptionPublicKeyParameters.h;
        int i = this.params.N;
        int i2 = this.params.q;
        int i3 = this.params.maxMsgLenBytes;
        int i4 = this.params.db;
        int i5 = this.params.bufferLenBits;
        int i6 = this.params.dm0;
        int i7 = this.params.pkLen;
        int i8 = this.params.minCallsMask;
        boolean z = this.params.hashSeed;
        byte[] bArr2 = this.params.oid;
        int length = bArr.length;
        if (i3 > CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) {
            throw new IllegalArgumentException("llen values bigger than 1 are not supported");
        } else if (length > i3) {
            throw new DataLengthException("Message too long: " + length + ">" + i3);
        } else {
            while (true) {
                Object obj = new byte[(i4 / 8)];
                this.random.nextBytes(obj);
                Object obj2 = new byte[((i3 + 1) - length)];
                Object obj3 = new byte[(i5 / 8)];
                System.arraycopy(obj, 0, obj3, 0, obj.length);
                obj3[obj.length] = (byte) length;
                System.arraycopy(bArr, 0, obj3, obj.length + 1, bArr.length);
                System.arraycopy(obj2, 0, obj3, (obj.length + 1) + bArr.length, obj2.length);
                IntegerPolynomial fromBinary3Sves = IntegerPolynomial.fromBinary3Sves(obj3, i);
                IntegerPolynomial mult = generateBlindingPoly(buildSData(bArr2, bArr, length, obj, copyOf(integerPolynomial.toBinary(i2), i7 / 8)), obj3).mult(integerPolynomial, i2);
                IntegerPolynomial integerPolynomial2 = (IntegerPolynomial) mult.clone();
                integerPolynomial2.modPositive(4);
                fromBinary3Sves.add(MGF(integerPolynomial2.toBinary(4), i, i8, z));
                fromBinary3Sves.mod3();
                if (fromBinary3Sves.count(-1) >= i6 && fromBinary3Sves.count(0) >= i6 && fromBinary3Sves.count(1) >= i6) {
                    mult.add(fromBinary3Sves, i2);
                    mult.ensurePositive(i2);
                    return mult.toBinary(i2);
                }
            }
        }
    }

    private int[] generateBlindingCoeffs(IndexGenerator indexGenerator, int i) {
        int[] iArr = new int[this.params.N];
        for (int i2 = -1; i2 <= 1; i2 += 2) {
            int i3 = 0;
            while (i3 < i) {
                int nextIndex = indexGenerator.nextIndex();
                if (iArr[nextIndex] == 0) {
                    iArr[nextIndex] = i2;
                    i3++;
                }
            }
        }
        return iArr;
    }

    private Polynomial generateBlindingPoly(byte[] bArr, byte[] bArr2) {
        IndexGenerator indexGenerator = new IndexGenerator(bArr, this.params);
        if (this.params.polyType == 1) {
            return new ProductFormPolynomial(new SparseTernaryPolynomial(generateBlindingCoeffs(indexGenerator, this.params.dr1)), new SparseTernaryPolynomial(generateBlindingCoeffs(indexGenerator, this.params.dr2)), new SparseTernaryPolynomial(generateBlindingCoeffs(indexGenerator, this.params.dr3)));
        }
        int i = this.params.dr;
        boolean z = this.params.sparse;
        int[] generateBlindingCoeffs = generateBlindingCoeffs(indexGenerator, i);
        return z ? new SparseTernaryPolynomial(generateBlindingCoeffs) : new DenseTernaryPolynomial(generateBlindingCoeffs);
    }

    private int log2(int i) {
        if (i == PKIFailureInfo.wrongIntegrity) {
            return 11;
        }
        throw new IllegalStateException("log2 not fully implemented");
    }

    private void putInt(Digest digest, int i) {
        digest.update((byte) (i >> 24));
        digest.update((byte) (i >> 16));
        digest.update((byte) (i >> 8));
        digest.update((byte) i);
    }

    protected IntegerPolynomial decrypt(IntegerPolynomial integerPolynomial, Polynomial polynomial, IntegerPolynomial integerPolynomial2) {
        IntegerPolynomial mult;
        if (this.params.fastFp) {
            mult = polynomial.mult(integerPolynomial, this.params.q);
            mult.mult(3);
            mult.add(integerPolynomial);
        } else {
            mult = polynomial.mult(integerPolynomial, this.params.q);
        }
        mult.center0(this.params.q);
        mult.mod3();
        if (!this.params.fastFp) {
            mult = new DenseTernaryPolynomial(mult).mult(integerPolynomial2, 3);
        }
        mult.center0(3);
        return mult;
    }

    protected IntegerPolynomial encrypt(IntegerPolynomial integerPolynomial, TernaryPolynomial ternaryPolynomial, IntegerPolynomial integerPolynomial2) {
        IntegerPolynomial mult = ternaryPolynomial.mult(integerPolynomial2, this.params.q);
        mult.add(integerPolynomial, this.params.q);
        mult.ensurePositive(this.params.q);
        return mult;
    }

    public int getInputBlockSize() {
        return this.params.maxMsgLenBytes;
    }

    public int getOutputBlockSize() {
        return ((this.params.N * log2(this.params.q)) + 7) / 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.forEncryption = z;
        if (z) {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.random = parametersWithRandom.getRandom();
                this.pubKey = (NTRUEncryptionPublicKeyParameters) parametersWithRandom.getParameters();
            } else {
                this.random = new SecureRandom();
                this.pubKey = (NTRUEncryptionPublicKeyParameters) cipherParameters;
            }
            this.params = this.pubKey.getParameters();
            return;
        }
        this.privKey = (NTRUEncryptionPrivateKeyParameters) cipherParameters;
        this.params = this.privKey.getParameters();
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        Object obj = new byte[i2];
        System.arraycopy(bArr, i, obj, 0, i2);
        return this.forEncryption ? encrypt(obj, this.pubKey) : decrypt(obj, this.privKey);
    }
}
