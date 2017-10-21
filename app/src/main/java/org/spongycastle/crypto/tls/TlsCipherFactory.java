package org.spongycastle.crypto.tls;

import java.io.IOException;

public interface TlsCipherFactory {
    TlsCipher createCipher(TlsClientContext tlsClientContext, int i, int i2) throws IOException;
}
