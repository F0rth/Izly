package defpackage;

import org.spongycastle.crypto.tls.CipherSuite;

public final class ix {
    public static final int a(byte[] bArr) {
        return (((bArr[0] << 24) + ((bArr[1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) + ((bArr[2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) + (bArr[3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    public static final byte[] a(int i) {
        return new byte[]{(byte) (i >>> 24), (byte) (i >>> 16), (byte) (i >>> 8), (byte) i};
    }

    public static final byte[] b(int i) {
        byte[] bArr = new byte[8];
        for (int i2 = 7; i2 >= 0; i2--) {
            bArr[i2] = (byte) (i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            i >>= 8;
        }
        return bArr;
    }
}
