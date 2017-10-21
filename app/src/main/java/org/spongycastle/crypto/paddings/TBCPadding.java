package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.tls.CipherSuite;

public class TBCPadding implements BlockCipherPadding {
    public int addPadding(byte[] bArr, int i) {
        byte b;
        int i2 = 0;
        int length = bArr.length;
        if (i > 0) {
            if ((bArr[i - 1] & 1) == 0) {
                i2 = CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            }
            b = (byte) i2;
            i2 = i;
        } else {
            if ((bArr[bArr.length - 1] & 1) == 0) {
                i2 = CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            }
            b = (byte) i2;
            i2 = i;
        }
        while (i2 < bArr.length) {
            bArr[i2] = b;
            i2++;
        }
        return length - i;
    }

    public String getPaddingName() {
        return "TBC";
    }

    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    public int padCount(byte[] bArr) throws InvalidCipherTextException {
        byte b = bArr[bArr.length - 1];
        int length = bArr.length - 1;
        while (length > 0 && bArr[length - 1] == b) {
            length--;
        }
        return bArr.length - length;
    }
}
