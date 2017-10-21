package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.engines.AESFastEngine;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;

public class DefaultTlsCipherFactory implements TlsCipherFactory {
    protected BlockCipher createAESBlockCipher() {
        return new CBCBlockCipher(new AESFastEngine());
    }

    protected TlsCipher createAESCipher(TlsClientContext tlsClientContext, int i, int i2) throws IOException {
        return new TlsBlockCipher(tlsClientContext, createAESBlockCipher(), createAESBlockCipher(), createDigest(i2), createDigest(i2), i);
    }

    public TlsCipher createCipher(TlsClientContext tlsClientContext, int i, int i2) throws IOException {
        switch (i) {
            case 7:
                return createDESedeCipher(tlsClientContext, 24, i2);
            case 8:
                return createAESCipher(tlsClientContext, 16, i2);
            case 9:
                return createAESCipher(tlsClientContext, 32, i2);
            default:
                throw new TlsFatalAlert((short) 80);
        }
    }

    protected BlockCipher createDESedeBlockCipher() {
        return new CBCBlockCipher(new DESedeEngine());
    }

    protected TlsCipher createDESedeCipher(TlsClientContext tlsClientContext, int i, int i2) throws IOException {
        return new TlsBlockCipher(tlsClientContext, createDESedeBlockCipher(), createDESedeBlockCipher(), createDigest(i2), createDigest(i2), i);
    }

    protected Digest createDigest(int i) throws IOException {
        switch (i) {
            case 1:
                return new MD5Digest();
            case 2:
                return new SHA1Digest();
            case 3:
                return new SHA256Digest();
            case 4:
                return new SHA384Digest();
            default:
                throw new TlsFatalAlert((short) 80);
        }
    }
}
