package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.tls.CipherSuite;

public class PKCS7Padding implements BlockCipherPadding {
    public int addPadding(byte[] bArr, int i) {
        byte length = (byte) (bArr.length - i);
        while (i < bArr.length) {
            bArr[i] = length;
            i++;
        }
        return length;
    }

    public String getPaddingName() {
        return "PKCS7";
    }

    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    public int padCount(byte[] bArr) throws InvalidCipherTextException {
        byte b = bArr[bArr.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        if (b > bArr.length || b == (byte) 0) {
            throw new InvalidCipherTextException("pad block corrupted");
        }
        for (byte b2 = (byte) 1; b2 <= b; b2++) {
            if (bArr[bArr.length - b2] != b) {
                throw new InvalidCipherTextException("pad block corrupted");
            }
        }
        return b;
    }
}
