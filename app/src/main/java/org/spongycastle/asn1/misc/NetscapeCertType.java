package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.DERBitString;
import org.spongycastle.crypto.tls.CipherSuite;

public class NetscapeCertType extends DERBitString {
    public static final int objectSigning = 16;
    public static final int objectSigningCA = 1;
    public static final int reserved = 8;
    public static final int smime = 32;
    public static final int smimeCA = 2;
    public static final int sslCA = 4;
    public static final int sslClient = 128;
    public static final int sslServer = 64;

    public NetscapeCertType(int i) {
        super(DERBitString.getBytes(i), DERBitString.getPadBits(i));
    }

    public NetscapeCertType(DERBitString dERBitString) {
        super(dERBitString.getBytes(), dERBitString.getPadBits());
    }

    public String toString() {
        return "NetscapeCertType: 0x" + Integer.toHexString(this.data[0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }
}
