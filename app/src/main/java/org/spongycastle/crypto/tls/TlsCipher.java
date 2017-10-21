package org.spongycastle.crypto.tls;

import java.io.IOException;

public interface TlsCipher {
    byte[] decodeCiphertext(short s, byte[] bArr, int i, int i2) throws IOException;

    byte[] encodePlaintext(short s, byte[] bArr, int i, int i2) throws IOException;
}
