package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.modes.gcm.GCMMultiplier;
import org.spongycastle.crypto.modes.gcm.Tables8kGCMMultiplier;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.crypto.util.Pack;
import org.spongycastle.util.Arrays;

public class GCMBlockCipher implements AEADBlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final byte[] ZEROES = new byte[16];
    private byte[] A;
    private byte[] H;
    private byte[] J0;
    private byte[] S;
    private byte[] bufBlock;
    private int bufOff;
    private BlockCipher cipher;
    private byte[] counter;
    private boolean forEncryption;
    private byte[] initS;
    private byte[] macBlock;
    private int macSize;
    private GCMMultiplier multiplier;
    private byte[] nonce;
    private long totalLength;

    public GCMBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, null);
    }

    public GCMBlockCipher(BlockCipher blockCipher, GCMMultiplier gCMMultiplier) {
        if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
        if (gCMMultiplier == null) {
            gCMMultiplier = new Tables8kGCMMultiplier();
        }
        this.cipher = blockCipher;
        this.multiplier = gCMMultiplier;
    }

    private void gCTRBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        byte[] bArr3;
        for (int i3 = 15; i3 >= 12; i3--) {
            byte b = (byte) ((this.counter[i3] + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            this.counter[i3] = b;
            if (b != (byte) 0) {
                break;
            }
        }
        Object obj = new byte[16];
        this.cipher.processBlock(this.counter, 0, obj, 0);
        if (this.forEncryption) {
            System.arraycopy(ZEROES, i, obj, i, 16 - i);
            bArr3 = obj;
        } else {
            bArr3 = bArr;
        }
        for (int i4 = i - 1; i4 >= 0; i4--) {
            obj[i4] = (byte) (obj[i4] ^ bArr[i4]);
            bArr2[i2 + i4] = obj[i4];
        }
        xor(this.S, bArr3);
        this.multiplier.multiplyH(this.S);
        this.totalLength += (long) i;
    }

    private byte[] gHASH(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        for (int i = 0; i < bArr.length; i += 16) {
            Object obj = new byte[16];
            System.arraycopy(bArr, i, obj, 0, Math.min(bArr.length - i, 16));
            xor(bArr2, obj);
            this.multiplier.multiplyH(bArr2);
        }
        return bArr2;
    }

    private static void packLength(long j, byte[] bArr, int i) {
        Pack.intToBigEndian((int) (j >>> 32), bArr, i);
        Pack.intToBigEndian((int) j, bArr, i + 4);
    }

    private int process(byte b, byte[] bArr, int i) throws DataLengthException {
        byte[] bArr2 = this.bufBlock;
        int i2 = this.bufOff;
        this.bufOff = i2 + 1;
        bArr2[i2] = b;
        if (this.bufOff != this.bufBlock.length) {
            return 0;
        }
        gCTRBlock(this.bufBlock, 16, bArr, i);
        if (!this.forEncryption) {
            System.arraycopy(this.bufBlock, 16, this.bufBlock, 0, this.macSize);
        }
        this.bufOff = this.bufBlock.length - 16;
        return 16;
    }

    private void reset(boolean z) {
        this.S = Arrays.clone(this.initS);
        this.counter = Arrays.clone(this.J0);
        this.bufOff = 0;
        this.totalLength = 0;
        if (this.bufBlock != null) {
            Arrays.fill(this.bufBlock, (byte) 0);
        }
        if (z) {
            this.macBlock = null;
        }
        this.cipher.reset();
    }

    private static void xor(byte[] bArr, byte[] bArr2) {
        for (int i = 15; i >= 0; i--) {
            bArr[i] = (byte) (bArr[i] ^ bArr2[i]);
        }
    }

    public int doFinal(byte[] bArr, int i) throws IllegalStateException, InvalidCipherTextException {
        Object obj;
        int i2 = this.bufOff;
        if (!this.forEncryption) {
            if (i2 < this.macSize) {
                throw new InvalidCipherTextException("data too short");
            }
            i2 -= this.macSize;
        }
        if (i2 > 0) {
            obj = new byte[16];
            System.arraycopy(this.bufBlock, 0, obj, 0, i2);
            gCTRBlock(obj, i2, bArr, i);
        }
        byte[] bArr2 = new byte[16];
        packLength(((long) this.A.length) * 8, bArr2, 0);
        packLength(this.totalLength * 8, bArr2, 8);
        xor(this.S, bArr2);
        this.multiplier.multiplyH(this.S);
        obj = new byte[16];
        this.cipher.processBlock(this.J0, 0, obj, 0);
        xor(obj, this.S);
        this.macBlock = new byte[this.macSize];
        System.arraycopy(obj, 0, this.macBlock, 0, this.macSize);
        if (this.forEncryption) {
            System.arraycopy(this.macBlock, 0, bArr, this.bufOff + i, this.macSize);
            i2 += this.macSize;
        } else {
            obj = new byte[this.macSize];
            System.arraycopy(this.bufBlock, i2, obj, 0, this.macSize);
            if (!Arrays.constantTimeAreEqual(this.macBlock, obj)) {
                throw new InvalidCipherTextException("mac check in GCM failed");
            }
        }
        reset(false);
        return i2;
    }

    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/GCM";
    }

    public byte[] getMac() {
        return Arrays.clone(this.macBlock);
    }

    public int getOutputSize(int i) {
        return this.forEncryption ? (this.bufOff + i) + this.macSize : (this.bufOff + i) - this.macSize;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    public int getUpdateOutputSize(int i) {
        return ((this.bufOff + i) / 16) * 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = z;
        this.macBlock = null;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            this.nonce = aEADParameters.getNonce();
            this.A = aEADParameters.getAssociatedText();
            int macSize = aEADParameters.getMacSize();
            if (macSize < 96 || macSize > 128 || macSize % 8 != 0) {
                throw new IllegalArgumentException("Invalid value for MAC size: " + macSize);
            }
            this.macSize = macSize / 8;
            CipherParameters key = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.nonce = parametersWithIV.getIV();
            this.A = null;
            this.macSize = 16;
            Object obj = (KeyParameter) parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to GCM");
        }
        this.bufBlock = new byte[(z ? 16 : this.macSize + 16)];
        if (this.nonce == null || this.nonce.length <= 0) {
            throw new IllegalArgumentException("IV must be at least 1 byte");
        }
        if (this.A == null) {
            this.A = new byte[0];
        }
        if (key != null) {
            this.cipher.init(true, key);
        }
        this.H = new byte[16];
        this.cipher.processBlock(ZEROES, 0, this.H, 0);
        this.multiplier.init(this.H);
        this.initS = gHASH(this.A);
        if (this.nonce.length == 12) {
            this.J0 = new byte[16];
            System.arraycopy(this.nonce, 0, this.J0, 0, this.nonce.length);
            this.J0[15] = (byte) 1;
        } else {
            this.J0 = gHASH(this.nonce);
            byte[] bArr = new byte[16];
            packLength(((long) this.nonce.length) * 8, bArr, 8);
            xor(this.J0, bArr);
            this.multiplier.multiplyH(this.J0);
        }
        this.S = Arrays.clone(this.initS);
        this.counter = Arrays.clone(this.J0);
        this.bufOff = 0;
        this.totalLength = 0;
    }

    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException {
        return process(b, bArr, i);
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
        int i4 = 0;
        for (int i5 = 0; i5 != i2; i5++) {
            byte[] bArr3 = this.bufBlock;
            int i6 = this.bufOff;
            this.bufOff = i6 + 1;
            bArr3[i6] = bArr[i + i5];
            if (this.bufOff == this.bufBlock.length) {
                gCTRBlock(this.bufBlock, 16, bArr2, i3 + i4);
                if (!this.forEncryption) {
                    System.arraycopy(this.bufBlock, 16, this.bufBlock, 0, this.macSize);
                }
                this.bufOff = this.bufBlock.length - 16;
                i4 += 16;
            }
        }
        return i4;
    }

    public void reset() {
        reset(true);
    }
}
