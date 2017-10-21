package org.spongycastle.crypto.signers;

import java.util.Hashtable;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.SignerWithRecovery;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;

public class ISO9796d2Signer implements SignerWithRecovery {
    public static final int TRAILER_IMPLICIT = 188;
    public static final int TRAILER_RIPEMD128 = 13004;
    public static final int TRAILER_RIPEMD160 = 12748;
    public static final int TRAILER_SHA1 = 13260;
    public static final int TRAILER_SHA256 = 13516;
    public static final int TRAILER_SHA384 = 14028;
    public static final int TRAILER_SHA512 = 13772;
    public static final int TRAILER_WHIRLPOOL = 14284;
    private static Hashtable trailerMap;
    private byte[] block;
    private AsymmetricBlockCipher cipher;
    private Digest digest;
    private boolean fullMessage;
    private int keyBits;
    private byte[] mBuf;
    private int messageLength;
    private byte[] preBlock;
    private byte[] preSig;
    private byte[] recoveredMessage;
    private int trailer;

    static {
        Hashtable hashtable = new Hashtable();
        trailerMap = hashtable;
        hashtable.put("RIPEMD128", new Integer(13004));
        trailerMap.put("RIPEMD160", new Integer(12748));
        trailerMap.put("SHA-1", new Integer(13260));
        trailerMap.put("SHA-256", new Integer(TRAILER_SHA256));
        trailerMap.put("SHA-384", new Integer(TRAILER_SHA384));
        trailerMap.put("SHA-512", new Integer(TRAILER_SHA512));
        trailerMap.put("Whirlpool", new Integer(TRAILER_WHIRLPOOL));
    }

    public ISO9796d2Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this(asymmetricBlockCipher, digest, false);
    }

    public ISO9796d2Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, boolean z) {
        this.cipher = asymmetricBlockCipher;
        this.digest = digest;
        if (z) {
            this.trailer = 188;
            return;
        }
        Integer num = (Integer) trailerMap.get(digest.getAlgorithmName());
        if (num != null) {
            this.trailer = num.intValue();
            return;
        }
        throw new IllegalArgumentException("no valid trailer for digest");
    }

    private void clearBlock(byte[] bArr) {
        for (int i = 0; i != bArr.length; i++) {
            bArr[i] = (byte) 0;
        }
    }

    private boolean isSameAs(byte[] bArr, byte[] bArr2) {
        boolean z = true;
        int i;
        if (this.messageLength > this.mBuf.length) {
            if (this.mBuf.length > bArr2.length) {
                z = false;
            }
            for (i = 0; i != this.mBuf.length; i++) {
                if (bArr[i] != bArr2[i]) {
                    z = false;
                }
            }
        } else {
            if (this.messageLength != bArr2.length) {
                z = false;
            }
            for (i = 0; i != bArr2.length; i++) {
                if (bArr[i] != bArr2[i]) {
                    z = false;
                }
            }
        }
        return z;
    }

    private boolean returnFalse(byte[] bArr) {
        clearBlock(this.mBuf);
        clearBlock(bArr);
        return false;
    }

    public byte[] generateSignature() throws CryptoException {
        int i;
        int length;
        byte[] bArr;
        int digestSize = this.digest.getDigestSize();
        if (this.trailer == 188) {
            i = 8;
            length = (this.block.length - digestSize) - 1;
            this.digest.doFinal(this.block, length);
            this.block[this.block.length - 1] = PSSSigner.TRAILER_IMPLICIT;
        } else {
            i = 16;
            length = (this.block.length - digestSize) - 2;
            this.digest.doFinal(this.block, length);
            this.block[this.block.length - 2] = (byte) (this.trailer >>> 8);
            this.block[this.block.length - 1] = (byte) this.trailer;
        }
        i = ((i + ((digestSize + this.messageLength) * 8)) + 4) - this.keyBits;
        if (i > 0) {
            digestSize = this.messageLength - ((i + 7) / 8);
            i = length - digestSize;
            System.arraycopy(this.mBuf, 0, this.block, i, digestSize);
            digestSize = 96;
            length = i;
        } else {
            i = length - this.messageLength;
            System.arraycopy(this.mBuf, 0, this.block, i, this.messageLength);
            digestSize = 64;
            length = i;
        }
        if (length - 1 > 0) {
            for (i = length - 1; i != 0; i--) {
                this.block[i] = (byte) -69;
            }
            bArr = this.block;
            length--;
            bArr[length] = (byte) (bArr[length] ^ 1);
            this.block[0] = (byte) 11;
            bArr = this.block;
            bArr[0] = (byte) (bArr[0] | digestSize);
        } else {
            this.block[0] = (byte) 10;
            bArr = this.block;
            bArr[0] = (byte) (bArr[0] | digestSize);
        }
        bArr = this.cipher.processBlock(this.block, 0, this.block.length);
        clearBlock(this.mBuf);
        clearBlock(this.block);
        return bArr;
    }

    public byte[] getRecoveredMessage() {
        return this.recoveredMessage;
    }

    public boolean hasFullMessage() {
        return this.fullMessage;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        RSAKeyParameters rSAKeyParameters = (RSAKeyParameters) cipherParameters;
        this.cipher.init(z, rSAKeyParameters);
        this.keyBits = rSAKeyParameters.getModulus().bitLength();
        this.block = new byte[((this.keyBits + 7) / 8)];
        if (this.trailer == 188) {
            this.mBuf = new byte[((this.block.length - this.digest.getDigestSize()) - 2)];
        } else {
            this.mBuf = new byte[((this.block.length - this.digest.getDigestSize()) - 3)];
        }
        reset();
    }

    public void reset() {
        this.digest.reset();
        this.messageLength = 0;
        clearBlock(this.mBuf);
        if (this.recoveredMessage != null) {
            clearBlock(this.recoveredMessage);
        }
        this.recoveredMessage = null;
        this.fullMessage = false;
    }

    public void update(byte b) {
        this.digest.update(b);
        if (this.preSig == null && this.messageLength < this.mBuf.length) {
            this.mBuf[this.messageLength] = b;
        }
        this.messageLength++;
    }

    public void update(byte[] bArr, int i, int i2) {
        this.digest.update(bArr, i, i2);
        if (this.preSig == null && this.messageLength < this.mBuf.length) {
            int i3 = 0;
            while (i3 < i2 && this.messageLength + i3 < this.mBuf.length) {
                this.mBuf[this.messageLength + i3] = bArr[i + i3];
                i3++;
            }
        }
        this.messageLength += i2;
    }

    public void updateWithRecoveredMessage(byte[] bArr) throws InvalidCipherTextException {
        Object processBlock = this.cipher.processBlock(bArr, 0, bArr.length);
        if (((processBlock[0] & 192) ^ 64) != 0) {
            throw new InvalidCipherTextException("malformed signature");
        } else if (((processBlock[processBlock.length - 1] & 15) ^ 12) != 0) {
            throw new InvalidCipherTextException("malformed signature");
        } else {
            int i;
            int i2;
            if (((processBlock[processBlock.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ 188) == 0) {
                i = 1;
            } else {
                i2 = (processBlock[processBlock.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) | ((processBlock[processBlock.length - 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8);
                Integer num = (Integer) trailerMap.get(this.digest.getAlgorithmName());
                if (num == null) {
                    throw new IllegalArgumentException("unrecognised hash in signature");
                } else if (i2 != num.intValue()) {
                    throw new IllegalStateException("signer initialised with wrong digest for trailer " + i2);
                } else {
                    i = 2;
                }
            }
            i2 = 0;
            while (i2 != processBlock.length && ((processBlock[i2] & 15) ^ 10) != 0) {
                i2++;
            }
            i2++;
            i = (processBlock.length - i) - this.digest.getDigestSize();
            if (i - i2 <= 0) {
                throw new InvalidCipherTextException("malformed block");
            }
            if ((processBlock[0] & 32) == 0) {
                this.fullMessage = true;
                this.recoveredMessage = new byte[(i - i2)];
                System.arraycopy(processBlock, i2, this.recoveredMessage, 0, this.recoveredMessage.length);
            } else {
                this.fullMessage = false;
                this.recoveredMessage = new byte[(i - i2)];
                System.arraycopy(processBlock, i2, this.recoveredMessage, 0, this.recoveredMessage.length);
            }
            this.preSig = bArr;
            this.preBlock = processBlock;
            this.digest.update(this.recoveredMessage, 0, this.recoveredMessage.length);
            this.messageLength = this.recoveredMessage.length;
        }
    }

    public boolean verifySignature(byte[] bArr) {
        Object processBlock;
        boolean z;
        if (this.preSig == null) {
            try {
                z = false;
                processBlock = this.cipher.processBlock(bArr, 0, bArr.length);
            } catch (Exception e) {
                return false;
            }
        } else if (Arrays.areEqual(this.preSig, bArr)) {
            Object obj = this.preBlock;
            this.preSig = null;
            this.preBlock = null;
            z = true;
            processBlock = obj;
        } else {
            throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
        }
        if (((processBlock[0] & 192) ^ 64) != 0) {
            return returnFalse(processBlock);
        }
        if (((processBlock[processBlock.length - 1] & 15) ^ 12) != 0) {
            return returnFalse(processBlock);
        }
        int i;
        int i2;
        if (((processBlock[processBlock.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ 188) == 0) {
            i = 1;
        } else {
            i2 = (processBlock[processBlock.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) | ((processBlock[processBlock.length - 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8);
            Integer num = (Integer) trailerMap.get(this.digest.getAlgorithmName());
            if (num == null) {
                throw new IllegalArgumentException("unrecognised hash in signature");
            } else if (i2 != num.intValue()) {
                throw new IllegalStateException("signer initialised with wrong digest for trailer " + i2);
            } else {
                i = 2;
            }
        }
        i2 = 0;
        while (i2 != processBlock.length && ((processBlock[i2] & 15) ^ 10) != 0) {
            i2++;
        }
        int i3 = i2 + 1;
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        int length = (processBlock.length - i) - bArr2.length;
        if (length - i3 <= 0) {
            return returnFalse(processBlock);
        }
        boolean z2;
        int i4;
        if ((processBlock[0] & 32) == 0) {
            this.fullMessage = true;
            if (this.messageLength > length - i3) {
                return returnFalse(processBlock);
            }
            this.digest.reset();
            this.digest.update(processBlock, i3, length - i3);
            this.digest.doFinal(bArr2, 0);
            z2 = true;
            for (i2 = 0; i2 != bArr2.length; i2++) {
                i4 = length + i2;
                processBlock[i4] = (byte) (processBlock[i4] ^ bArr2[i2]);
                if (processBlock[length + i2] != (byte) 0) {
                    z2 = false;
                }
            }
            if (!z2) {
                return returnFalse(processBlock);
            }
            this.recoveredMessage = new byte[(length - i3)];
            System.arraycopy(processBlock, i3, this.recoveredMessage, 0, this.recoveredMessage.length);
        } else {
            this.fullMessage = false;
            this.digest.doFinal(bArr2, 0);
            z2 = true;
            for (i2 = 0; i2 != bArr2.length; i2++) {
                i4 = length + i2;
                processBlock[i4] = (byte) (processBlock[i4] ^ bArr2[i2]);
                if (processBlock[length + i2] != (byte) 0) {
                    z2 = false;
                }
            }
            if (!z2) {
                return returnFalse(processBlock);
            }
            this.recoveredMessage = new byte[(length - i3)];
            System.arraycopy(processBlock, i3, this.recoveredMessage, 0, this.recoveredMessage.length);
        }
        if (this.messageLength != 0 && !r1 && !isSameAs(this.mBuf, this.recoveredMessage)) {
            return returnFalse(processBlock);
        }
        clearBlock(this.mBuf);
        clearBlock(processBlock);
        return true;
    }
}
