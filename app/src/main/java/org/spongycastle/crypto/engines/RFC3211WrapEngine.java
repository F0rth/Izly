package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.tls.CipherSuite;

public class RFC3211WrapEngine implements Wrapper {
    private CBCBlockCipher engine;
    private boolean forWrapping;
    private ParametersWithIV param;
    private SecureRandom rand;

    public RFC3211WrapEngine(BlockCipher blockCipher) {
        this.engine = new CBCBlockCipher(blockCipher);
    }

    public String getAlgorithmName() {
        return this.engine.getUnderlyingCipher().getAlgorithmName() + "/RFC3211Wrap";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.forWrapping = z;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.rand = parametersWithRandom.getRandom();
            this.param = (ParametersWithIV) parametersWithRandom.getParameters();
            return;
        }
        if (z) {
            this.rand = new SecureRandom();
        }
        this.param = (ParametersWithIV) cipherParameters;
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        int i3 = 0;
        if (this.forWrapping) {
            throw new IllegalStateException("not set for unwrapping");
        }
        int blockSize = this.engine.getBlockSize();
        if (i2 < blockSize * 2) {
            throw new InvalidCipherTextException("input too short");
        }
        int i4;
        Object obj = new byte[i2];
        Object obj2 = new byte[blockSize];
        System.arraycopy(bArr, i, obj, 0, i2);
        System.arraycopy(bArr, i, obj2, 0, blockSize);
        this.engine.init(false, new ParametersWithIV(this.param.getParameters(), obj2));
        for (i4 = blockSize; i4 < i2; i4 += blockSize) {
            this.engine.processBlock(obj, i4, obj, i4);
        }
        System.arraycopy(obj, i2 - blockSize, obj2, 0, blockSize);
        this.engine.init(false, new ParametersWithIV(this.param.getParameters(), obj2));
        this.engine.processBlock(obj, 0, obj, 0);
        this.engine.init(false, this.param);
        for (i4 = 0; i4 < i2; i4 += blockSize) {
            this.engine.processBlock(obj, i4, obj, i4);
        }
        if ((obj[0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) > i2 - 4) {
            throw new InvalidCipherTextException("wrapped key corrupted");
        }
        Object obj3 = new byte[(obj[0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV)];
        System.arraycopy(obj, 4, obj3, 0, obj[0]);
        for (i4 = 0; i4 != 3; i4++) {
            i3 |= ((byte) (obj[i4 + 1] ^ -1)) ^ obj3[i4];
        }
        if (i3 == 0) {
            return obj3;
        }
        throw new InvalidCipherTextException("wrapped key fails checksum");
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        int i3 = 0;
        if (this.forWrapping) {
            this.engine.init(true, this.param);
            int blockSize = this.engine.getBlockSize();
            int i4 = i2 + 4 < blockSize * 2 ? blockSize * 2 : (i2 + 4) % blockSize == 0 ? i2 + 4 : (((i2 + 4) / blockSize) + 1) * blockSize;
            Object obj = new byte[i4];
            obj[0] = (byte) i2;
            obj[1] = (byte) (bArr[i] ^ -1);
            obj[2] = (byte) (bArr[i + 1] ^ -1);
            obj[3] = (byte) (bArr[i + 2] ^ -1);
            System.arraycopy(bArr, i, obj, 4, i2);
            for (i4 = i2 + 4; i4 < obj.length; i4++) {
                obj[i4] = (byte) this.rand.nextInt();
            }
            for (i4 = 0; i4 < obj.length; i4 += blockSize) {
                this.engine.processBlock(obj, i4, obj, i4);
            }
            while (i3 < obj.length) {
                this.engine.processBlock(obj, i3, obj, i3);
                i3 += blockSize;
            }
            return obj;
        }
        throw new IllegalStateException("not set for wrapping");
    }
}
