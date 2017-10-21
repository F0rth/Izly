package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.DERBitString;
import org.spongycastle.crypto.tls.CipherSuite;

public class KeyUsage extends DERBitString {
    public static final int cRLSign = 2;
    public static final int dataEncipherment = 16;
    public static final int decipherOnly = 32768;
    public static final int digitalSignature = 128;
    public static final int encipherOnly = 1;
    public static final int keyAgreement = 8;
    public static final int keyCertSign = 4;
    public static final int keyEncipherment = 32;
    public static final int nonRepudiation = 64;

    public KeyUsage(int i) {
        super(DERBitString.getBytes(i), DERBitString.getPadBits(i));
    }

    public KeyUsage(DERBitString dERBitString) {
        super(dERBitString.getBytes(), dERBitString.getPadBits());
    }

    public static DERBitString getInstance(Object obj) {
        return obj instanceof KeyUsage ? (KeyUsage) obj : obj instanceof X509Extension ? new KeyUsage(DERBitString.getInstance(X509Extension.convertValueToObject((X509Extension) obj))) : new KeyUsage(DERBitString.getInstance(obj));
    }

    public String toString() {
        return this.data.length == 1 ? "KeyUsage: 0x" + Integer.toHexString(this.data[0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) : "KeyUsage: 0x" + Integer.toHexString(((this.data[1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) | (this.data[0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV));
    }
}
