package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.tls.CipherSuite;

public class X923Padding implements BlockCipherPadding {
    SecureRandom random = null;

    public int addPadding(byte[] bArr, int i) {
        byte length = (byte) (bArr.length - i);
        while (i < bArr.length - 1) {
            if (this.random == null) {
                bArr[i] = (byte) 0;
            } else {
                bArr[i] = (byte) this.random.nextInt();
            }
            i++;
        }
        bArr[i] = length;
        return length;
    }

    public String getPaddingName() {
        return "X9.23";
    }

    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
        this.random = secureRandom;
    }

    public int padCount(byte[] bArr) throws InvalidCipherTextException {
        int i = bArr[bArr.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        if (i <= bArr.length) {
            return i;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}
