package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class RFC3394WrapEngine implements Wrapper {
    private BlockCipher engine;
    private boolean forWrapping;
    private byte[] iv = new byte[]{(byte) -90, (byte) -90, (byte) -90, (byte) -90, (byte) -90, (byte) -90, (byte) -90, (byte) -90};
    private KeyParameter param;

    public RFC3394WrapEngine(BlockCipher blockCipher) {
        this.engine = blockCipher;
    }

    public String getAlgorithmName() {
        return this.engine.getAlgorithmName();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.forWrapping = z;
        CipherParameters parameters = cipherParameters instanceof ParametersWithRandom ? ((ParametersWithRandom) cipherParameters).getParameters() : cipherParameters;
        if (parameters instanceof KeyParameter) {
            this.param = (KeyParameter) parameters;
        } else if (parameters instanceof ParametersWithIV) {
            this.iv = ((ParametersWithIV) parameters).getIV();
            this.param = (KeyParameter) ((ParametersWithIV) parameters).getParameters();
            if (this.iv.length != 8) {
                throw new IllegalArgumentException("IV not equal to 8");
            }
        }
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        if (this.forWrapping) {
            throw new IllegalStateException("not set for unwrapping");
        }
        int i3 = i2 / 8;
        if (i3 * 8 != i2) {
            throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
        }
        Object obj = new byte[(i2 - this.iv.length)];
        Object obj2 = new byte[this.iv.length];
        Object obj3 = new byte[(this.iv.length + 8)];
        System.arraycopy(bArr, 0, obj2, 0, this.iv.length);
        System.arraycopy(bArr, this.iv.length, obj, 0, i2 - this.iv.length);
        this.engine.init(false, this.param);
        int i4 = i3 - 1;
        for (int i5 = 5; i5 >= 0; i5--) {
            for (int i6 = i4; i6 > 0; i6--) {
                System.arraycopy(obj2, 0, obj3, 0, this.iv.length);
                System.arraycopy(obj, (i6 - 1) * 8, obj3, this.iv.length, 8);
                i3 = (i4 * i5) + i6;
                int i7 = 1;
                while (i3 != 0) {
                    int length = this.iv.length - i7;
                    obj3[length] = (byte) (((byte) i3) ^ obj3[length]);
                    i3 >>>= 8;
                    i7++;
                }
                this.engine.processBlock(obj3, 0, obj3, 0);
                System.arraycopy(obj3, 0, obj2, 0, 8);
                System.arraycopy(obj3, 8, obj, (i6 - 1) * 8, 8);
            }
        }
        if (Arrays.constantTimeAreEqual(obj2, this.iv)) {
            return obj;
        }
        throw new InvalidCipherTextException("checksum failed");
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (this.forWrapping) {
            int i3 = i2 / 8;
            if (i3 * 8 != i2) {
                throw new DataLengthException("wrap data must be a multiple of 8 bytes");
            }
            Object obj = new byte[(this.iv.length + i2)];
            Object obj2 = new byte[(this.iv.length + 8)];
            System.arraycopy(this.iv, 0, obj, 0, this.iv.length);
            System.arraycopy(bArr, 0, obj, this.iv.length, i2);
            this.engine.init(true, this.param);
            for (int i4 = 0; i4 != 6; i4++) {
                for (int i5 = 1; i5 <= i3; i5++) {
                    System.arraycopy(obj, 0, obj2, 0, this.iv.length);
                    System.arraycopy(obj, i5 * 8, obj2, this.iv.length, 8);
                    this.engine.processBlock(obj2, 0, obj2, 0);
                    int i6 = (i3 * i4) + i5;
                    int i7 = 1;
                    while (i6 != 0) {
                        int length = this.iv.length - i7;
                        obj2[length] = (byte) (((byte) i6) ^ obj2[length]);
                        i6 >>>= 8;
                        i7++;
                    }
                    System.arraycopy(obj2, 0, obj, 0, 8);
                    System.arraycopy(obj2, 8, obj, i5 * 8, 8);
                }
            }
            return obj;
        }
        throw new IllegalStateException("not set for wrapping");
    }
}
