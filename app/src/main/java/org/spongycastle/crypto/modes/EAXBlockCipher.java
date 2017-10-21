package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class EAXBlockCipher implements AEADBlockCipher {
    private static final byte cTAG = (byte) 2;
    private static final byte hTAG = (byte) 1;
    private static final byte nTAG = (byte) 0;
    private byte[] associatedTextMac = new byte[this.mac.getMacSize()];
    private int blockSize;
    private byte[] bufBlock = new byte[(this.blockSize * 2)];
    private int bufOff;
    private SICBlockCipher cipher;
    private boolean forEncryption;
    private Mac mac;
    private byte[] macBlock = new byte[this.blockSize];
    private int macSize;
    private byte[] nonceMac = new byte[this.mac.getMacSize()];

    public EAXBlockCipher(BlockCipher blockCipher) {
        this.blockSize = blockCipher.getBlockSize();
        this.mac = new CMac(blockCipher);
        this.cipher = new SICBlockCipher(blockCipher);
    }

    private void calculateMac() {
        int i = 0;
        byte[] bArr = new byte[this.blockSize];
        this.mac.doFinal(bArr, 0);
        while (i < this.macBlock.length) {
            this.macBlock[i] = (byte) ((this.nonceMac[i] ^ this.associatedTextMac[i]) ^ bArr[i]);
            i++;
        }
    }

    private int process(byte b, byte[] bArr, int i) {
        byte[] bArr2 = this.bufBlock;
        int i2 = this.bufOff;
        this.bufOff = i2 + 1;
        bArr2[i2] = b;
        if (this.bufOff != this.bufBlock.length) {
            return 0;
        }
        int processBlock;
        if (this.forEncryption) {
            processBlock = this.cipher.processBlock(this.bufBlock, 0, bArr, i);
            this.mac.update(bArr, i, this.blockSize);
        } else {
            this.mac.update(this.bufBlock, 0, this.blockSize);
            processBlock = this.cipher.processBlock(this.bufBlock, 0, bArr, i);
        }
        this.bufOff = this.blockSize;
        System.arraycopy(this.bufBlock, this.blockSize, this.bufBlock, 0, this.blockSize);
        return processBlock;
    }

    private void reset(boolean z) {
        this.cipher.reset();
        this.mac.reset();
        this.bufOff = 0;
        Arrays.fill(this.bufBlock, (byte) 0);
        if (z) {
            Arrays.fill(this.macBlock, (byte) 0);
        }
        byte[] bArr = new byte[this.blockSize];
        bArr[this.blockSize - 1] = cTAG;
        this.mac.update(bArr, 0, this.blockSize);
    }

    private boolean verifyMac(byte[] bArr, int i) {
        for (int i2 = 0; i2 < this.macSize; i2++) {
            if (this.macBlock[i2] != bArr[i + i2]) {
                return false;
            }
        }
        return true;
    }

    public int doFinal(byte[] bArr, int i) throws IllegalStateException, InvalidCipherTextException {
        int i2 = this.bufOff;
        Object obj = new byte[this.bufBlock.length];
        this.bufOff = 0;
        if (this.forEncryption) {
            this.cipher.processBlock(this.bufBlock, 0, obj, 0);
            this.cipher.processBlock(this.bufBlock, this.blockSize, obj, this.blockSize);
            System.arraycopy(obj, 0, bArr, i, i2);
            this.mac.update(obj, 0, i2);
            calculateMac();
            System.arraycopy(this.macBlock, 0, bArr, i + i2, this.macSize);
            reset(false);
            return i2 + this.macSize;
        }
        if (i2 > this.macSize) {
            this.mac.update(this.bufBlock, 0, i2 - this.macSize);
            this.cipher.processBlock(this.bufBlock, 0, obj, 0);
            this.cipher.processBlock(this.bufBlock, this.blockSize, obj, this.blockSize);
            System.arraycopy(obj, 0, bArr, i, i2 - this.macSize);
        }
        calculateMac();
        if (verifyMac(this.bufBlock, i2 - this.macSize)) {
            reset(false);
            return i2 - this.macSize;
        }
        throw new InvalidCipherTextException("mac check in EAX failed");
    }

    public String getAlgorithmName() {
        return this.cipher.getUnderlyingCipher().getAlgorithmName() + "/EAX";
    }

    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public byte[] getMac() {
        Object obj = new byte[this.macSize];
        System.arraycopy(this.macBlock, 0, obj, 0, this.macSize);
        return obj;
    }

    public int getOutputSize(int i) {
        return this.forEncryption ? (this.bufOff + i) + this.macSize : (this.bufOff + i) - this.macSize;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher.getUnderlyingCipher();
    }

    public int getUpdateOutputSize(int i) {
        return ((this.bufOff + i) / this.blockSize) * this.blockSize;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        byte[] nonce;
        byte[] associatedText;
        CipherParameters key;
        this.forEncryption = z;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            nonce = aEADParameters.getNonce();
            associatedText = aEADParameters.getAssociatedText();
            this.macSize = aEADParameters.getMacSize() / 8;
            key = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            nonce = parametersWithIV.getIV();
            associatedText = new byte[0];
            this.macSize = this.mac.getMacSize() / 2;
            key = parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to EAX");
        }
        byte[] bArr = new byte[this.blockSize];
        this.mac.init(key);
        bArr[this.blockSize - 1] = hTAG;
        this.mac.update(bArr, 0, this.blockSize);
        this.mac.update(associatedText, 0, associatedText.length);
        this.mac.doFinal(this.associatedTextMac, 0);
        bArr[this.blockSize - 1] = (byte) 0;
        this.mac.update(bArr, 0, this.blockSize);
        this.mac.update(nonce, 0, nonce.length);
        this.mac.doFinal(this.nonceMac, 0);
        bArr[this.blockSize - 1] = cTAG;
        this.mac.update(bArr, 0, this.blockSize);
        this.cipher.init(true, new ParametersWithIV(key, this.nonceMac));
    }

    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException {
        return process(b, bArr, i);
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
        int i4 = 0;
        for (int i5 = 0; i5 != i2; i5++) {
            i4 += process(bArr[i + i5], bArr2, i3 + i4);
        }
        return i4;
    }

    public void reset() {
        reset(true);
    }
}
