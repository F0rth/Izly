package org.spongycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.eac.CertificateBody;

class StreamUtil {
    private static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();

    StreamUtil() {
    }

    static int calculateBodyLength(int i) {
        int i2 = 1;
        if (i > CertificateBody.profileType) {
            int i3 = 1;
            while (true) {
                i >>>= 8;
                if (i == 0) {
                    break;
                }
                i3++;
            }
            i3 = (i3 - 1) * 8;
            while (i3 >= 0) {
                i3 -= 8;
                i2++;
            }
        }
        return i2;
    }

    static int calculateTagLength(int i) throws IOException {
        int i2 = 4;
        if (i < 31) {
            return 1;
        }
        if (i < 128) {
            return 2;
        }
        byte[] bArr = new byte[5];
        bArr[4] = (byte) (i & CertificateBody.profileType);
        do {
            i >>= 7;
            i2--;
            bArr[i2] = (byte) ((i & CertificateBody.profileType) | 128);
        } while (i > CertificateBody.profileType);
        return (5 - i2) + 1;
    }

    static int findLimit(InputStream inputStream) {
        if (inputStream instanceof LimitedInputStream) {
            return ((LimitedInputStream) inputStream).getRemaining();
        }
        if (inputStream instanceof ASN1InputStream) {
            return ((ASN1InputStream) inputStream).getLimit();
        }
        if (inputStream instanceof ByteArrayInputStream) {
            return ((ByteArrayInputStream) inputStream).available();
        }
        if (inputStream instanceof FileInputStream) {
            try {
                long size = ((FileInputStream) inputStream).getChannel().size();
                if (size < 2147483647L) {
                    return (int) size;
                }
            } catch (IOException e) {
            }
        }
        return MAX_MEMORY > 2147483647L ? Integer.MAX_VALUE : (int) MAX_MEMORY;
    }
}
