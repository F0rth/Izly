package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;

public class DERBMPString extends ASN1Primitive implements ASN1String {
    private char[] string;

    public DERBMPString(String str) {
        this.string = str.toCharArray();
    }

    DERBMPString(byte[] bArr) {
        char[] cArr = new char[(bArr.length / 2)];
        for (int i = 0; i != cArr.length; i++) {
            cArr[i] = (char) ((bArr[i * 2] << 8) | (bArr[(i * 2) + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV));
        }
        this.string = cArr;
    }

    DERBMPString(char[] cArr) {
        this.string = cArr;
    }

    public static DERBMPString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERBMPString)) {
            return (DERBMPString) obj;
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static DERBMPString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof DERBMPString)) ? getInstance(object) : new DERBMPString(ASN1OctetString.getInstance(object).getOctets());
    }

    protected boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERBMPString)) {
            return false;
        }
        return Arrays.areEqual(this.string, ((DERBMPString) aSN1Primitive).string);
    }

    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.write(30);
        aSN1OutputStream.writeLength(this.string.length * 2);
        for (int i = 0; i != this.string.length; i++) {
            char c = this.string[i];
            aSN1OutputStream.write((byte) (c >> 8));
            aSN1OutputStream.write((byte) c);
        }
    }

    int encodedLength() {
        return (StreamUtil.calculateBodyLength(this.string.length * 2) + 1) + (this.string.length * 2);
    }

    public String getString() {
        return new String(this.string);
    }

    public int hashCode() {
        return Arrays.hashCode(this.string);
    }

    boolean isConstructed() {
        return false;
    }

    public String toString() {
        return getString();
    }
}
