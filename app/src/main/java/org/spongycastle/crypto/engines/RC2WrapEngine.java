package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;

public class RC2WrapEngine implements Wrapper {
    private static final byte[] IV2 = new byte[]{(byte) 74, (byte) -35, (byte) -94, (byte) 44, (byte) 121, (byte) -24, (byte) 33, (byte) 5};
    byte[] digest = new byte[20];
    private CBCBlockCipher engine;
    private boolean forWrapping;
    private byte[] iv;
    private CipherParameters param;
    private ParametersWithIV paramPlusIV;
    Digest sha1 = new SHA1Digest();
    private SecureRandom sr;

    private byte[] calculateCMSKeyChecksum(byte[] bArr) {
        Object obj = new byte[8];
        this.sha1.update(bArr, 0, bArr.length);
        this.sha1.doFinal(this.digest, 0);
        System.arraycopy(this.digest, 0, obj, 0, 8);
        return obj;
    }

    private boolean checkCMSKeyChecksum(byte[] bArr, byte[] bArr2) {
        return Arrays.constantTimeAreEqual(calculateCMSKeyChecksum(bArr), bArr2);
    }

    public String getAlgorithmName() {
        return "RC2";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        CipherParameters parameters;
        this.forWrapping = z;
        this.engine = new CBCBlockCipher(new RC2Engine());
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.sr = parametersWithRandom.getRandom();
            parameters = parametersWithRandom.getParameters();
        } else {
            this.sr = new SecureRandom();
            parameters = cipherParameters;
        }
        if (parameters instanceof ParametersWithIV) {
            this.paramPlusIV = (ParametersWithIV) parameters;
            this.iv = this.paramPlusIV.getIV();
            this.param = this.paramPlusIV.getParameters();
            if (!this.forWrapping) {
                throw new IllegalArgumentException("You should not supply an IV for unwrapping");
            } else if (this.iv == null || this.iv.length != 8) {
                throw new IllegalArgumentException("IV is not 8 octets");
            } else {
                return;
            }
        }
        this.param = parameters;
        if (this.forWrapping) {
            this.iv = new byte[8];
            this.sr.nextBytes(this.iv);
            this.paramPlusIV = new ParametersWithIV(this.param, this.iv);
        }
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        if (this.forWrapping) {
            throw new IllegalStateException("Not set for unwrapping");
        } else if (bArr == null) {
            throw new InvalidCipherTextException("Null pointer as ciphertext");
        } else if (i2 % this.engine.getBlockSize() != 0) {
            throw new InvalidCipherTextException("Ciphertext not multiple of " + this.engine.getBlockSize());
        } else {
            int i3;
            int blockSize;
            this.engine.init(false, new ParametersWithIV(this.param, IV2));
            Object obj = new byte[i2];
            System.arraycopy(bArr, i, obj, 0, i2);
            for (i3 = 0; i3 < i2 / this.engine.getBlockSize(); i3++) {
                blockSize = this.engine.getBlockSize() * i3;
                this.engine.processBlock(obj, blockSize, obj, blockSize);
            }
            Object obj2 = new byte[i2];
            for (i3 = 0; i3 < i2; i3++) {
                obj2[i3] = obj[i2 - (i3 + 1)];
            }
            this.iv = new byte[8];
            Object obj3 = new byte[(i2 - 8)];
            System.arraycopy(obj2, 0, this.iv, 0, 8);
            System.arraycopy(obj2, 8, obj3, 0, i2 - 8);
            this.paramPlusIV = new ParametersWithIV(this.param, this.iv);
            this.engine.init(false, this.paramPlusIV);
            obj = new byte[obj3.length];
            System.arraycopy(obj3, 0, obj, 0, obj3.length);
            for (i3 = 0; i3 < obj.length / this.engine.getBlockSize(); i3++) {
                blockSize = this.engine.getBlockSize() * i3;
                this.engine.processBlock(obj, blockSize, obj, blockSize);
            }
            obj3 = new byte[(obj.length - 8)];
            obj2 = new byte[8];
            System.arraycopy(obj, 0, obj3, 0, obj.length - 8);
            System.arraycopy(obj, obj.length - 8, obj2, 0, 8);
            if (!checkCMSKeyChecksum(obj3, obj2)) {
                throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
            } else if (obj3.length - ((obj3[0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 1) > 7) {
                throw new InvalidCipherTextException("too many pad bytes (" + (obj3.length - ((obj3[0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 1)) + ")");
            } else {
                obj = new byte[obj3[0]];
                System.arraycopy(obj3, 1, obj, 0, obj.length);
                return obj;
            }
        }
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        int i3 = 0;
        if (this.forWrapping) {
            int i4 = i2 + 1;
            if (i4 % 8 != 0) {
                i4 += 8 - (i4 % 8);
            }
            Object obj = new byte[i4];
            obj[0] = (byte) i2;
            System.arraycopy(bArr, i, obj, 1, i2);
            Object obj2 = new byte[((obj.length - i2) - 1)];
            if (obj2.length > 0) {
                this.sr.nextBytes(obj2);
                System.arraycopy(obj2, 0, obj, i2 + 1, obj2.length);
            }
            obj2 = calculateCMSKeyChecksum(obj);
            Object obj3 = new byte[(obj.length + obj2.length)];
            System.arraycopy(obj, 0, obj3, 0, obj.length);
            System.arraycopy(obj2, 0, obj3, obj.length, obj2.length);
            obj2 = new byte[obj3.length];
            System.arraycopy(obj3, 0, obj2, 0, obj3.length);
            int length = obj3.length / this.engine.getBlockSize();
            if (obj3.length % this.engine.getBlockSize() != 0) {
                throw new IllegalStateException("Not multiple of block length");
            }
            this.engine.init(true, this.paramPlusIV);
            for (i4 = 0; i4 < length; i4++) {
                int blockSize = this.engine.getBlockSize() * i4;
                this.engine.processBlock(obj2, blockSize, obj2, blockSize);
            }
            obj3 = new byte[(this.iv.length + obj2.length)];
            System.arraycopy(this.iv, 0, obj3, 0, this.iv.length);
            System.arraycopy(obj2, 0, obj3, this.iv.length, obj2.length);
            byte[] bArr2 = new byte[obj3.length];
            for (i4 = 0; i4 < obj3.length; i4++) {
                bArr2[i4] = obj3[obj3.length - (i4 + 1)];
            }
            this.engine.init(true, new ParametersWithIV(this.param, IV2));
            while (i3 < length + 1) {
                i4 = this.engine.getBlockSize() * i3;
                this.engine.processBlock(bArr2, i4, bArr2, i4);
                i3++;
            }
            return bArr2;
        }
        throw new IllegalStateException("Not initialized for wrapping");
    }
}
