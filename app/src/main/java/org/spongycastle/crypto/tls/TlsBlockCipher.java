package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class TlsBlockCipher implements TlsCipher {
    protected TlsClientContext context;
    protected BlockCipher decryptCipher;
    protected BlockCipher encryptCipher;
    protected TlsMac readMac;
    protected TlsMac writeMac;

    public TlsBlockCipher(TlsClientContext tlsClientContext, BlockCipher blockCipher, BlockCipher blockCipher2, Digest digest, Digest digest2, int i) {
        this.context = tlsClientContext;
        this.encryptCipher = blockCipher;
        this.decryptCipher = blockCipher2;
        byte[] calculateKeyBlock = TlsUtils.calculateKeyBlock(tlsClientContext, ((((i * 2) + digest.getDigestSize()) + digest2.getDigestSize()) + blockCipher.getBlockSize()) + blockCipher2.getBlockSize());
        this.writeMac = new TlsMac(tlsClientContext, digest, calculateKeyBlock, 0, digest.getDigestSize());
        int digestSize = digest.getDigestSize() + 0;
        this.readMac = new TlsMac(tlsClientContext, digest2, calculateKeyBlock, digestSize, digest2.getDigestSize());
        int digestSize2 = digestSize + digest2.getDigestSize();
        initCipher(true, blockCipher, calculateKeyBlock, i, digestSize2, digestSize2 + (i * 2));
        digestSize2 += i;
        initCipher(false, blockCipher2, calculateKeyBlock, i, digestSize2, (digestSize2 + i) + blockCipher.getBlockSize());
    }

    protected int chooseExtraPadBlocks(SecureRandom secureRandom, int i) {
        return Math.min(lowestBitSet(secureRandom.nextInt()), i);
    }

    public byte[] decodeCiphertext(short s, byte[] bArr, int i, int i2) throws IOException {
        int i3 = 1;
        int size = this.readMac.getSize() + 1;
        int blockSize = this.decryptCipher.getBlockSize();
        if (i2 < size) {
            throw new TlsFatalAlert((short) 50);
        } else if (i2 % blockSize != 0) {
            throw new TlsFatalAlert((short) 21);
        } else {
            int i4;
            for (i4 = 0; i4 < i2; i4 += blockSize) {
                this.decryptCipher.processBlock(bArr, i4 + i, bArr, i4 + i);
            }
            int i5 = (i + i2) - 1;
            byte b = bArr[i5];
            i4 = b & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            int i6 = this.context.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion() ? 1 : 0;
            int i7 = i2 - size;
            if (i6 == 0) {
                i7 = Math.min(i7, blockSize);
            }
            if (i4 > i7) {
                i4 = 0;
                i7 = 1;
            } else {
                if (i6 != 0) {
                    i7 = 0;
                    for (i6 = i5 - i4; i6 < i5; i6++) {
                        i7 = (byte) (i7 | (bArr[i6] ^ b));
                    }
                    if (i7 != 0) {
                        i4 = 0;
                        i7 = 1;
                    }
                }
                i7 = 0;
            }
            i4 = (i2 - size) - i4;
            byte[] calculateMac = this.readMac.calculateMac(s, bArr, i, i4);
            Object obj = new byte[calculateMac.length];
            System.arraycopy(bArr, i + i4, obj, 0, calculateMac.length);
            if (Arrays.constantTimeAreEqual(calculateMac, obj)) {
                i3 = i7;
            }
            if (i3 != 0) {
                throw new TlsFatalAlert((short) 20);
            }
            Object obj2 = new byte[i4];
            System.arraycopy(bArr, i, obj2, 0, i4);
            return obj2;
        }
    }

    public byte[] encodePlaintext(short s, byte[] bArr, int i, int i2) {
        int blockSize = this.encryptCipher.getBlockSize();
        int size = blockSize - (((this.writeMac.getSize() + i2) + 1) % blockSize);
        if ((this.context.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion() ? 1 : 0) != 0) {
            size += chooseExtraPadBlocks(this.context.getSecureRandom(), (255 - size) / blockSize) * blockSize;
        }
        int size2 = ((this.writeMac.getSize() + i2) + size) + 1;
        Object obj = new byte[size2];
        System.arraycopy(bArr, i, obj, 0, i2);
        Object calculateMac = this.writeMac.calculateMac(s, bArr, i, i2);
        System.arraycopy(calculateMac, 0, obj, i2, calculateMac.length);
        int length = calculateMac.length;
        for (int i3 = 0; i3 <= size; i3++) {
            obj[(i2 + length) + i3] = (byte) size;
        }
        for (size = 0; size < size2; size += blockSize) {
            this.encryptCipher.processBlock(obj, size, obj, size);
        }
        return obj;
    }

    public TlsMac getReadMac() {
        return this.readMac;
    }

    public TlsMac getWriteMac() {
        return this.writeMac;
    }

    protected void initCipher(boolean z, BlockCipher blockCipher, byte[] bArr, int i, int i2, int i3) {
        blockCipher.init(z, new ParametersWithIV(new KeyParameter(bArr, i2, i), bArr, i3, blockCipher.getBlockSize()));
    }

    protected int lowestBitSet(int i) {
        if (i == 0) {
            return 32;
        }
        int i2 = 0;
        while ((i & 1) == 0) {
            i2++;
            i >>= 1;
        }
        return i2;
    }
}
