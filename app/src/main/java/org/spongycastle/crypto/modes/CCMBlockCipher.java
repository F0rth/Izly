package org.spongycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;

public class CCMBlockCipher implements AEADBlockCipher {
    private byte[] associatedText;
    private int blockSize;
    private BlockCipher cipher;
    private ByteArrayOutputStream data = new ByteArrayOutputStream();
    private boolean forEncryption;
    private CipherParameters keyParam;
    private byte[] macBlock;
    private int macSize;
    private byte[] nonce;

    public CCMBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        this.blockSize = blockCipher.getBlockSize();
        this.macBlock = new byte[this.blockSize];
        if (this.blockSize != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
    }

    private int calculateMac(byte[] bArr, int i, int i2, byte[] bArr2) {
        Mac cBCBlockCipherMac = new CBCBlockCipherMac(this.cipher, this.macSize * 8);
        cBCBlockCipherMac.init(this.keyParam);
        Object obj = new byte[16];
        if (hasAssociatedText()) {
            obj[0] = (byte) (obj[0] | 64);
        }
        obj[0] = (byte) (obj[0] | ((((cBCBlockCipherMac.getMacSize() - 2) / 2) & 7) << 3));
        obj[0] = (byte) (obj[0] | (((15 - this.nonce.length) - 1) & 7));
        System.arraycopy(this.nonce, 0, obj, 1, this.nonce.length);
        int i3 = 1;
        int i4 = i2;
        while (i4 > 0) {
            obj[16 - i3] = (byte) (i4 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            i4 >>>= 8;
            i3++;
        }
        cBCBlockCipherMac.update(obj, 0, 16);
        if (hasAssociatedText()) {
            if (this.associatedText.length < 65280) {
                cBCBlockCipherMac.update((byte) (this.associatedText.length >> 8));
                cBCBlockCipherMac.update((byte) this.associatedText.length);
                i4 = 2;
            } else {
                cBCBlockCipherMac.update((byte) -1);
                cBCBlockCipherMac.update((byte) -2);
                cBCBlockCipherMac.update((byte) (this.associatedText.length >> 24));
                cBCBlockCipherMac.update((byte) (this.associatedText.length >> 16));
                cBCBlockCipherMac.update((byte) (this.associatedText.length >> 8));
                cBCBlockCipherMac.update((byte) this.associatedText.length);
                i4 = 6;
            }
            cBCBlockCipherMac.update(this.associatedText, 0, this.associatedText.length);
            i3 = (i4 + this.associatedText.length) % 16;
            if (i3 != 0) {
                for (i4 = 0; i4 != 16 - i3; i4++) {
                    cBCBlockCipherMac.update((byte) 0);
                }
            }
        }
        cBCBlockCipherMac.update(bArr, i, i2);
        return cBCBlockCipherMac.doFinal(bArr2, 0);
    }

    private boolean hasAssociatedText() {
        return (this.associatedText == null || this.associatedText.length == 0) ? false : true;
    }

    public int doFinal(byte[] bArr, int i) throws IllegalStateException, InvalidCipherTextException {
        byte[] toByteArray = this.data.toByteArray();
        Object processPacket = processPacket(toByteArray, 0, toByteArray.length);
        System.arraycopy(processPacket, 0, bArr, i, processPacket.length);
        reset();
        return processPacket.length;
    }

    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CCM";
    }

    public byte[] getMac() {
        Object obj = new byte[this.macSize];
        System.arraycopy(this.macBlock, 0, obj, 0, obj.length);
        return obj;
    }

    public int getOutputSize(int i) {
        return this.forEncryption ? (this.data.size() + i) + this.macSize : (this.data.size() + i) - this.macSize;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    public int getUpdateOutputSize(int i) {
        return 0;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = z;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            this.nonce = aEADParameters.getNonce();
            this.associatedText = aEADParameters.getAssociatedText();
            this.macSize = aEADParameters.getMacSize() / 8;
            this.keyParam = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.nonce = parametersWithIV.getIV();
            this.associatedText = null;
            this.macSize = this.macBlock.length / 2;
            this.keyParam = parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to CCM");
        }
    }

    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException, IllegalStateException {
        this.data.write(b);
        return 0;
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException, IllegalStateException {
        this.data.write(bArr, i, i2);
        return 0;
    }

    public byte[] processPacket(byte[] bArr, int i, int i2) throws IllegalStateException, InvalidCipherTextException {
        if (this.keyParam == null) {
            throw new IllegalStateException("CCM cipher unitialized.");
        }
        BlockCipher sICBlockCipher = new SICBlockCipher(this.cipher);
        Object obj = new byte[this.blockSize];
        obj[0] = (byte) (((15 - this.nonce.length) - 1) & 7);
        System.arraycopy(this.nonce, 0, obj, 1, this.nonce.length);
        sICBlockCipher.init(this.forEncryption, new ParametersWithIV(this.keyParam, obj));
        Object obj2;
        int i3;
        if (this.forEncryption) {
            obj2 = new byte[(this.macSize + i2)];
            calculateMac(bArr, i, i2, this.macBlock);
            sICBlockCipher.processBlock(this.macBlock, 0, this.macBlock, 0);
            i3 = 0;
            while (i < i2 - this.blockSize) {
                sICBlockCipher.processBlock(bArr, i, obj2, i3);
                i3 += this.blockSize;
                i += this.blockSize;
            }
            Object obj3 = new byte[this.blockSize];
            System.arraycopy(bArr, i, obj3, 0, i2 - i);
            sICBlockCipher.processBlock(obj3, 0, obj3, 0);
            System.arraycopy(obj3, 0, obj2, i3, i2 - i);
            i3 += i2 - i;
            System.arraycopy(this.macBlock, 0, obj2, i3, obj2.length - i3);
            return obj2;
        }
        obj2 = new byte[(i2 - this.macSize)];
        System.arraycopy(bArr, (i + i2) - this.macSize, this.macBlock, 0, this.macSize);
        sICBlockCipher.processBlock(this.macBlock, 0, this.macBlock, 0);
        for (i3 = this.macSize; i3 != this.macBlock.length; i3++) {
            this.macBlock[i3] = (byte) 0;
        }
        i3 = 0;
        while (i3 < obj2.length - this.blockSize) {
            sICBlockCipher.processBlock(bArr, i, obj2, i3);
            i3 += this.blockSize;
            i += this.blockSize;
        }
        obj3 = new byte[this.blockSize];
        System.arraycopy(bArr, i, obj3, 0, obj2.length - i3);
        sICBlockCipher.processBlock(obj3, 0, obj3, 0);
        System.arraycopy(obj3, 0, obj2, i3, obj2.length - i3);
        byte[] bArr2 = new byte[this.blockSize];
        calculateMac(obj2, 0, obj2.length, bArr2);
        if (Arrays.constantTimeAreEqual(this.macBlock, bArr2)) {
            return obj2;
        }
        throw new InvalidCipherTextException("mac check in CCM failed");
    }

    public void reset() {
        this.cipher.reset();
        this.data.reset();
    }
}
