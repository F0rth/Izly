package org.spongycastle.crypto;

public interface DerivationFunction {
    int generateBytes(byte[] bArr, int i, int i2) throws DataLengthException, IllegalArgumentException;

    Digest getDigest();

    void init(DerivationParameters derivationParameters);
}
