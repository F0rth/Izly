package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.IESParameters;
import org.spongycastle.crypto.params.IESWithCipherParameters;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.BigIntegers;

public class IESEngine {
    BasicAgreement agree;
    BufferedBlockCipher cipher;
    boolean forEncryption;
    DerivationFunction kdf;
    Mac mac;
    byte[] macBuf;
    IESParameters param;
    CipherParameters privParam;
    CipherParameters pubParam;

    public IESEngine(BasicAgreement basicAgreement, DerivationFunction derivationFunction, Mac mac) {
        this.agree = basicAgreement;
        this.kdf = derivationFunction;
        this.mac = mac;
        this.macBuf = new byte[mac.getMacSize()];
        this.cipher = null;
    }

    public IESEngine(BasicAgreement basicAgreement, DerivationFunction derivationFunction, Mac mac, BufferedBlockCipher bufferedBlockCipher) {
        this.agree = basicAgreement;
        this.kdf = derivationFunction;
        this.mac = mac;
        this.macBuf = new byte[mac.getMacSize()];
        this.cipher = bufferedBlockCipher;
    }

    private byte[] decryptBlock(byte[] bArr, int i, int i2, byte[] bArr2) throws InvalidCipherTextException {
        byte[] generateKdfBytes;
        byte[] bArr3;
        CipherParameters keyParameter;
        int i3 = 0;
        KDFParameters kDFParameters = new KDFParameters(bArr2, this.param.getDerivationV());
        int macKeySize = this.param.getMacKeySize();
        this.kdf.init(kDFParameters);
        int macSize = i2 - this.mac.getMacSize();
        int i4;
        if (this.cipher == null) {
            generateKdfBytes = generateKdfBytes(kDFParameters, (macKeySize / 8) + macSize);
            bArr3 = new byte[macSize];
            for (i4 = 0; i4 != macSize; i4++) {
                bArr3[i4] = (byte) (bArr[i + i4] ^ generateKdfBytes[i4]);
            }
            keyParameter = new KeyParameter(generateKdfBytes, macSize, macKeySize / 8);
        } else {
            int cipherKeySize = ((IESWithCipherParameters) this.param).getCipherKeySize();
            byte[] generateKdfBytes2 = generateKdfBytes(kDFParameters, (cipherKeySize / 8) + (macKeySize / 8));
            this.cipher.init(false, new KeyParameter(generateKdfBytes2, 0, cipherKeySize / 8));
            Object obj = new byte[this.cipher.getOutputSize(macSize)];
            int processBytes = this.cipher.processBytes(bArr, i, macSize, obj, 0);
            i4 = this.cipher.doFinal(obj, processBytes) + processBytes;
            bArr3 = new byte[i4];
            System.arraycopy(obj, 0, bArr3, 0, i4);
            keyParameter = new KeyParameter(generateKdfBytes2, cipherKeySize / 8, macKeySize / 8);
        }
        generateKdfBytes = this.param.getEncodingV();
        this.mac.init(keyParameter);
        this.mac.update(bArr, i, macSize);
        this.mac.update(generateKdfBytes, 0, generateKdfBytes.length);
        this.mac.doFinal(this.macBuf, 0);
        while (i3 < this.macBuf.length) {
            if (this.macBuf[i3] != bArr[(i + macSize) + i3]) {
                throw new InvalidCipherTextException("Mac codes failed to equal.");
            }
            i3++;
        }
        return bArr3;
    }

    private byte[] encryptBlock(byte[] bArr, int i, int i2, byte[] bArr2) throws InvalidCipherTextException {
        byte[] generateKdfBytes;
        byte[] bArr3;
        CipherParameters keyParameter;
        KDFParameters kDFParameters = new KDFParameters(bArr2, this.param.getDerivationV());
        int macKeySize = this.param.getMacKeySize();
        int i3;
        if (this.cipher == null) {
            generateKdfBytes = generateKdfBytes(kDFParameters, (macKeySize / 8) + i2);
            bArr3 = new byte[(this.mac.getMacSize() + i2)];
            for (i3 = 0; i3 != i2; i3++) {
                bArr3[i3] = (byte) (bArr[i + i3] ^ generateKdfBytes[i3]);
            }
            keyParameter = new KeyParameter(generateKdfBytes, i2, macKeySize / 8);
        } else {
            int cipherKeySize = ((IESWithCipherParameters) this.param).getCipherKeySize();
            byte[] generateKdfBytes2 = generateKdfBytes(kDFParameters, (cipherKeySize / 8) + (macKeySize / 8));
            this.cipher.init(true, new KeyParameter(generateKdfBytes2, 0, cipherKeySize / 8));
            Object obj = new byte[this.cipher.getOutputSize(i2)];
            i3 = this.cipher.processBytes(bArr, i, i2, obj, 0);
            i2 = i3 + this.cipher.doFinal(obj, i3);
            bArr3 = new byte[(this.mac.getMacSize() + i2)];
            System.arraycopy(obj, 0, bArr3, 0, i2);
            keyParameter = new KeyParameter(generateKdfBytes2, cipherKeySize / 8, macKeySize / 8);
        }
        generateKdfBytes = this.param.getEncodingV();
        this.mac.init(keyParameter);
        this.mac.update(bArr3, 0, i2);
        this.mac.update(generateKdfBytes, 0, generateKdfBytes.length);
        this.mac.doFinal(bArr3, i2);
        return bArr3;
    }

    private byte[] generateKdfBytes(KDFParameters kDFParameters, int i) {
        byte[] bArr = new byte[i];
        this.kdf.init(kDFParameters);
        this.kdf.generateBytes(bArr, 0, i);
        return bArr;
    }

    public void init(boolean z, CipherParameters cipherParameters, CipherParameters cipherParameters2, CipherParameters cipherParameters3) {
        this.forEncryption = z;
        this.privParam = cipherParameters;
        this.pubParam = cipherParameters2;
        this.param = (IESParameters) cipherParameters3;
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        this.agree.init(this.privParam);
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(this.agree.calculateAgreement(this.pubParam));
        return this.forEncryption ? encryptBlock(bArr, i, i2, asUnsignedByteArray) : decryptBlock(bArr, i, i2, asUnsignedByteArray);
    }
}
