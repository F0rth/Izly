package org.spongycastle.crypto.signers;

import java.security.SecureRandom;
import org.spongycastle.asn1.eac.CertificateBody;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.SignerWithRecovery;
import org.spongycastle.crypto.digests.RIPEMD128Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSalt;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.tls.CipherSuite;

public class ISO9796d2PSSSigner implements SignerWithRecovery {
    public static final int TRAILER_IMPLICIT = 188;
    public static final int TRAILER_RIPEMD128 = 13004;
    public static final int TRAILER_RIPEMD160 = 12748;
    public static final int TRAILER_SHA1 = 13260;
    private byte[] block;
    private AsymmetricBlockCipher cipher;
    private Digest digest;
    private boolean fullMessage;
    private int hLen;
    private int keyBits;
    private byte[] mBuf;
    private int messageLength;
    private SecureRandom random;
    private byte[] recoveredMessage;
    private int saltLength;
    private byte[] standardSalt;
    private int trailer;

    public ISO9796d2PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i) {
        this(asymmetricBlockCipher, digest, i, false);
    }

    public ISO9796d2PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i, boolean z) {
        this.cipher = asymmetricBlockCipher;
        this.digest = digest;
        this.hLen = digest.getDigestSize();
        this.saltLength = i;
        if (z) {
            this.trailer = 188;
        } else if (digest instanceof SHA1Digest) {
            this.trailer = 13260;
        } else if (digest instanceof RIPEMD160Digest) {
            this.trailer = 12748;
        } else if (digest instanceof RIPEMD128Digest) {
            this.trailer = 13004;
        } else {
            throw new IllegalArgumentException("no valid trailer for digest");
        }
    }

    private void ItoOSP(int i, byte[] bArr) {
        bArr[0] = (byte) (i >>> 24);
        bArr[1] = (byte) (i >>> 16);
        bArr[2] = (byte) (i >>> 8);
        bArr[3] = (byte) (i >>> 0);
    }

    private void LtoOSP(long j, byte[] bArr) {
        bArr[0] = (byte) ((int) (j >>> 56));
        bArr[1] = (byte) ((int) (j >>> 48));
        bArr[2] = (byte) ((int) (j >>> 40));
        bArr[3] = (byte) ((int) (j >>> 32));
        bArr[4] = (byte) ((int) (j >>> 24));
        bArr[5] = (byte) ((int) (j >>> 16));
        bArr[6] = (byte) ((int) (j >>> 8));
        bArr[7] = (byte) ((int) (j >>> 0));
    }

    private void clearBlock(byte[] bArr) {
        for (int i = 0; i != bArr.length; i++) {
            bArr[i] = (byte) 0;
        }
    }

    private boolean isSameAs(byte[] bArr, byte[] bArr2) {
        boolean z = true;
        if (this.messageLength != bArr2.length) {
            z = false;
        }
        for (int i = 0; i != bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                z = false;
            }
        }
        return z;
    }

    private byte[] maskGeneratorFunction1(byte[] bArr, int i, int i2, int i3) {
        Object obj = new byte[i3];
        Object obj2 = new byte[this.hLen];
        byte[] bArr2 = new byte[4];
        this.digest.reset();
        int i4 = 0;
        while (i4 < i3 / this.hLen) {
            ItoOSP(i4, bArr2);
            this.digest.update(bArr, i, i2);
            this.digest.update(bArr2, 0, 4);
            this.digest.doFinal(obj2, 0);
            System.arraycopy(obj2, 0, obj, this.hLen * i4, this.hLen);
            i4++;
        }
        if (this.hLen * i4 < i3) {
            ItoOSP(i4, bArr2);
            this.digest.update(bArr, i, i2);
            this.digest.update(bArr2, 0, 4);
            this.digest.doFinal(obj2, 0);
            System.arraycopy(obj2, 0, obj, this.hLen * i4, i3 - (i4 * this.hLen));
        }
        return obj;
    }

    public byte[] generateSignature() throws CryptoException {
        Object obj;
        int digestSize = this.digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        this.digest.doFinal(bArr, 0);
        byte[] bArr2 = new byte[8];
        LtoOSP((long) (this.messageLength * 8), bArr2);
        this.digest.update(bArr2, 0, 8);
        this.digest.update(this.mBuf, 0, this.messageLength);
        this.digest.update(bArr, 0, digestSize);
        if (this.standardSalt != null) {
            obj = this.standardSalt;
        } else {
            obj = new byte[this.saltLength];
            this.random.nextBytes(obj);
        }
        this.digest.update(obj, 0, obj.length);
        Object obj2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(obj2, 0);
        int i = 2;
        if (this.trailer == 188) {
            i = 1;
        }
        int length = ((((this.block.length - this.messageLength) - obj.length) - this.hLen) - i) - 1;
        this.block[length] = (byte) 1;
        System.arraycopy(this.mBuf, 0, this.block, length + 1, this.messageLength);
        System.arraycopy(obj, 0, this.block, (length + 1) + this.messageLength, obj.length);
        byte[] maskGeneratorFunction1 = maskGeneratorFunction1(obj2, 0, obj2.length, (this.block.length - this.hLen) - i);
        for (digestSize = 0; digestSize != maskGeneratorFunction1.length; digestSize++) {
            byte[] bArr3 = this.block;
            bArr3[digestSize] = (byte) (bArr3[digestSize] ^ maskGeneratorFunction1[digestSize]);
        }
        System.arraycopy(obj2, 0, this.block, (this.block.length - this.hLen) - i, this.hLen);
        if (this.trailer == 188) {
            this.block[this.block.length - 1] = PSSSigner.TRAILER_IMPLICIT;
        } else {
            this.block[this.block.length - 2] = (byte) (this.trailer >>> 8);
            this.block[this.block.length - 1] = (byte) this.trailer;
        }
        byte[] bArr4 = this.block;
        bArr4[0] = (byte) (bArr4[0] & CertificateBody.profileType);
        bArr4 = this.cipher.processBlock(this.block, 0, this.block.length);
        clearBlock(this.mBuf);
        clearBlock(this.block);
        this.messageLength = 0;
        return bArr4;
    }

    public byte[] getRecoveredMessage() {
        return this.recoveredMessage;
    }

    public boolean hasFullMessage() {
        return this.fullMessage;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        Object obj;
        int i;
        int i2 = this.saltLength;
        RSAKeyParameters rSAKeyParameters;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            rSAKeyParameters = (RSAKeyParameters) parametersWithRandom.getParameters();
            if (z) {
                this.random = parametersWithRandom.getRandom();
            }
            obj = rSAKeyParameters;
            i = i2;
        } else if (cipherParameters instanceof ParametersWithSalt) {
            ParametersWithSalt parametersWithSalt = (ParametersWithSalt) cipherParameters;
            rSAKeyParameters = (RSAKeyParameters) parametersWithSalt.getParameters();
            this.standardSalt = parametersWithSalt.getSalt();
            i2 = this.standardSalt.length;
            if (this.standardSalt.length != this.saltLength) {
                throw new IllegalArgumentException("Fixed salt is of wrong length");
            }
            r6 = rSAKeyParameters;
            i = i2;
        } else {
            r6 = (RSAKeyParameters) cipherParameters;
            if (z) {
                this.random = new SecureRandom();
            }
            i = i2;
        }
        this.cipher.init(z, obj);
        this.keyBits = obj.getModulus().bitLength();
        this.block = new byte[((this.keyBits + 7) / 8)];
        if (this.trailer == 188) {
            this.mBuf = new byte[((((this.block.length - this.digest.getDigestSize()) - i) - 1) - 1)];
        } else {
            this.mBuf = new byte[((((this.block.length - this.digest.getDigestSize()) - i) - 1) - 2)];
        }
        reset();
    }

    public void reset() {
        this.digest.reset();
        this.messageLength = 0;
        if (this.mBuf != null) {
            clearBlock(this.mBuf);
        }
        if (this.recoveredMessage != null) {
            clearBlock(this.recoveredMessage);
            this.recoveredMessage = null;
        }
        this.fullMessage = false;
    }

    public void update(byte b) {
        if (this.messageLength < this.mBuf.length) {
            byte[] bArr = this.mBuf;
            int i = this.messageLength;
            this.messageLength = i + 1;
            bArr[i] = b;
            return;
        }
        this.digest.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        while (i2 > 0 && this.messageLength < this.mBuf.length) {
            update(bArr[i]);
            i++;
            i2--;
        }
        if (i2 > 0) {
            this.digest.update(bArr, i, i2);
        }
    }

    public void updateWithRecoveredMessage(byte[] bArr) throws InvalidCipherTextException {
        throw new RuntimeException("not implemented");
    }

    public boolean verifySignature(byte[] bArr) {
        try {
            Object obj;
            int i;
            int i2;
            Object processBlock = this.cipher.processBlock(bArr, 0, bArr.length);
            if (processBlock.length < (this.keyBits + 7) / 8) {
                Object obj2 = new byte[((this.keyBits + 7) / 8)];
                System.arraycopy(processBlock, 0, obj2, obj2.length - processBlock.length, processBlock.length);
                clearBlock(processBlock);
                obj = obj2;
            } else {
                obj = processBlock;
            }
            if (((obj[obj.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ 188) == 0) {
                i = 1;
            } else {
                switch (((obj[obj.length - 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) | (obj[obj.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV)) {
                    case 12748:
                        if (!(this.digest instanceof RIPEMD160Digest)) {
                            throw new IllegalStateException("signer should be initialised with RIPEMD160");
                        }
                        break;
                    case 13004:
                        if (!(this.digest instanceof RIPEMD128Digest)) {
                            throw new IllegalStateException("signer should be initialised with RIPEMD128");
                        }
                        break;
                    case 13260:
                        if (!(this.digest instanceof SHA1Digest)) {
                            throw new IllegalStateException("signer should be initialised with SHA1");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("unrecognised hash in signature");
                }
                i = 2;
            }
            byte[] bArr2 = new byte[this.hLen];
            this.digest.doFinal(bArr2, 0);
            byte[] maskGeneratorFunction1 = maskGeneratorFunction1(obj, (obj.length - this.hLen) - i, this.hLen, (obj.length - this.hLen) - i);
            for (i2 = 0; i2 != maskGeneratorFunction1.length; i2++) {
                obj[i2] = (byte) (obj[i2] ^ maskGeneratorFunction1[i2]);
            }
            obj[0] = (byte) (obj[0] & CertificateBody.profileType);
            i2 = 0;
            while (i2 != obj.length && obj[i2] != (byte) 1) {
                i2++;
            }
            int i3 = i2 + 1;
            if (i3 >= obj.length) {
                clearBlock(obj);
                return false;
            }
            this.fullMessage = i3 > 1;
            this.recoveredMessage = new byte[((maskGeneratorFunction1.length - i3) - this.saltLength)];
            System.arraycopy(obj, i3, this.recoveredMessage, 0, this.recoveredMessage.length);
            byte[] bArr3 = new byte[8];
            LtoOSP((long) (this.recoveredMessage.length * 8), bArr3);
            this.digest.update(bArr3, 0, 8);
            if (this.recoveredMessage.length != 0) {
                this.digest.update(this.recoveredMessage, 0, this.recoveredMessage.length);
            }
            this.digest.update(bArr2, 0, bArr2.length);
            this.digest.update(obj, this.recoveredMessage.length + i3, this.saltLength);
            maskGeneratorFunction1 = new byte[this.digest.getDigestSize()];
            this.digest.doFinal(maskGeneratorFunction1, 0);
            i3 = obj.length;
            int length = maskGeneratorFunction1.length;
            boolean z = true;
            for (int i4 = 0; i4 != maskGeneratorFunction1.length; i4++) {
                if (maskGeneratorFunction1[i4] != obj[((i3 - i) - length) + i4]) {
                    z = false;
                }
            }
            clearBlock(obj);
            clearBlock(maskGeneratorFunction1);
            if (z) {
                if (this.messageLength != 0) {
                    if (isSameAs(this.mBuf, this.recoveredMessage)) {
                        this.messageLength = 0;
                    } else {
                        clearBlock(this.mBuf);
                        return false;
                    }
                }
                clearBlock(this.mBuf);
                return true;
            }
            this.fullMessage = false;
            clearBlock(this.recoveredMessage);
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
