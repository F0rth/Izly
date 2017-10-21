package org.spongycastle.crypto.signers;

import java.nio.ByteBuffer;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.NTRUSigningParameters;
import org.spongycastle.crypto.params.NTRUSigningPrivateKeyParameters;
import org.spongycastle.crypto.params.NTRUSigningPublicKeyParameters;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;

public class NTRUSigner {
    private Digest hashAlg;
    private NTRUSigningParameters params;
    private NTRUSigningPrivateKeyParameters signingKeyPair;
    private NTRUSigningPublicKeyParameters verificationKey;

    public NTRUSigner(NTRUSigningParameters nTRUSigningParameters) {
        this.params = nTRUSigningParameters;
    }

    private IntegerPolynomial sign(IntegerPolynomial integerPolynomial, NTRUSigningPrivateKeyParameters nTRUSigningPrivateKeyParameters) {
        Polynomial polynomial;
        int i = this.params.N;
        int i2 = this.params.q;
        int i3 = this.params.B;
        NTRUSigningPublicKeyParameters publicKey = nTRUSigningPrivateKeyParameters.getPublicKey();
        IntegerPolynomial integerPolynomial2 = new IntegerPolynomial(i);
        for (i = i3; i > 0; i--) {
            polynomial = nTRUSigningPrivateKeyParameters.getBasis(i).f;
            Polynomial polynomial2 = nTRUSigningPrivateKeyParameters.getBasis(i).fPrime;
            IntegerPolynomial mult = polynomial.mult(integerPolynomial);
            mult.div(i2);
            mult = polynomial2.mult(mult);
            IntegerPolynomial mult2 = polynomial2.mult(integerPolynomial);
            mult2.div(i2);
            mult.sub(polynomial.mult(mult2));
            integerPolynomial2.add(mult);
            IntegerPolynomial integerPolynomial3 = (IntegerPolynomial) nTRUSigningPrivateKeyParameters.getBasis(i).h.clone();
            if (i > 1) {
                integerPolynomial3.sub(nTRUSigningPrivateKeyParameters.getBasis(i - 1).h);
            } else {
                integerPolynomial3.sub(publicKey.h);
            }
            integerPolynomial = mult.mult(integerPolynomial3, i2);
        }
        polynomial = nTRUSigningPrivateKeyParameters.getBasis(0).f;
        Polynomial polynomial3 = nTRUSigningPrivateKeyParameters.getBasis(0).fPrime;
        IntegerPolynomial mult3 = polynomial.mult(integerPolynomial);
        mult3.div(i2);
        mult3 = polynomial3.mult(mult3);
        IntegerPolynomial mult4 = polynomial3.mult(integerPolynomial);
        mult4.div(i2);
        mult3.sub(polynomial.mult(mult4));
        integerPolynomial2.add(mult3);
        integerPolynomial2.modPositive(i2);
        return integerPolynomial2;
    }

    private byte[] signHash(byte[] bArr, NTRUSigningPrivateKeyParameters nTRUSigningPrivateKeyParameters) {
        IntegerPolynomial sign;
        int i = 0;
        NTRUSigningPublicKeyParameters publicKey = nTRUSigningPrivateKeyParameters.getPublicKey();
        IntegerPolynomial createMsgRep;
        do {
            i++;
            if (i > this.params.signFailTolerance) {
                throw new IllegalStateException("Signing failed: too many retries (max=" + this.params.signFailTolerance + ")");
            }
            createMsgRep = createMsgRep(bArr, i);
            sign = sign(createMsgRep, nTRUSigningPrivateKeyParameters);
        } while (!verify(createMsgRep, sign, publicKey.h));
        byte[] toBinary = sign.toBinary(this.params.q);
        ByteBuffer allocate = ByteBuffer.allocate(toBinary.length + 4);
        allocate.put(toBinary);
        allocate.putInt(i);
        return allocate.array();
    }

    private boolean verify(IntegerPolynomial integerPolynomial, IntegerPolynomial integerPolynomial2, IntegerPolynomial integerPolynomial3) {
        int i = this.params.q;
        double d = this.params.normBoundSq;
        double d2 = this.params.betaSq;
        IntegerPolynomial mult = integerPolynomial3.mult(integerPolynomial2, i);
        mult.sub(integerPolynomial);
        return ((double) ((long) ((((double) mult.centeredNormSq(i)) * d2) + ((double) integerPolynomial2.centeredNormSq(i))))) <= d;
    }

    private boolean verifyHash(byte[] bArr, byte[] bArr2, NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr2);
        byte[] bArr3 = new byte[(bArr2.length - 4)];
        wrap.get(bArr3);
        return verify(createMsgRep(bArr, wrap.getInt()), IntegerPolynomial.fromBinary(bArr3, this.params.N, this.params.q), nTRUSigningPublicKeyParameters.h);
    }

    protected IntegerPolynomial createMsgRep(byte[] bArr, int i) {
        int i2 = this.params.N;
        int numberOfLeadingZeros = 31 - Integer.numberOfLeadingZeros(this.params.q);
        int i3 = (numberOfLeadingZeros + 7) / 8;
        IntegerPolynomial integerPolynomial = new IntegerPolynomial(i2);
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length + 4);
        allocate.put(bArr);
        allocate.putInt(i);
        NTRUSignerPrng nTRUSignerPrng = new NTRUSignerPrng(allocate.array(), this.params.hashAlg);
        for (int i4 = 0; i4 < i2; i4++) {
            byte[] nextBytes = nTRUSignerPrng.nextBytes(i3);
            nextBytes[nextBytes.length - 1] = (byte) ((nextBytes[nextBytes.length - 1] >> ((i3 * 8) - numberOfLeadingZeros)) << ((i3 * 8) - numberOfLeadingZeros));
            ByteBuffer allocate2 = ByteBuffer.allocate(4);
            allocate2.put(nextBytes);
            allocate2.rewind();
            integerPolynomial.coeffs[i4] = Integer.reverseBytes(allocate2.getInt());
        }
        return integerPolynomial;
    }

    public byte[] generateSignature() {
        if (this.hashAlg == null || this.signingKeyPair == null) {
            throw new IllegalStateException("Call initSign first!");
        }
        byte[] bArr = new byte[this.hashAlg.getDigestSize()];
        this.hashAlg.doFinal(bArr, 0);
        return signHash(bArr, this.signingKeyPair);
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (z) {
            this.signingKeyPair = (NTRUSigningPrivateKeyParameters) cipherParameters;
        } else {
            this.verificationKey = (NTRUSigningPublicKeyParameters) cipherParameters;
        }
        this.hashAlg = this.params.hashAlg;
        this.hashAlg.reset();
    }

    public void update(byte b) {
        if (this.hashAlg == null) {
            throw new IllegalStateException("Call initSign or initVerify first!");
        }
        this.hashAlg.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        if (this.hashAlg == null) {
            throw new IllegalStateException("Call initSign or initVerify first!");
        }
        this.hashAlg.update(bArr, i, i2);
    }

    public boolean verifySignature(byte[] bArr) {
        if (this.hashAlg == null || this.verificationKey == null) {
            throw new IllegalStateException("Call initVerify first!");
        }
        byte[] bArr2 = new byte[this.hashAlg.getDigestSize()];
        this.hashAlg.doFinal(bArr2, 0);
        return verifyHash(bArr2, bArr, this.verificationKey);
    }
}
