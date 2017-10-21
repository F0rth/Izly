package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.asn1.eac.CertificateBody;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;

public class DERObjectIdentifier extends ASN1Primitive {
    private static ASN1ObjectIdentifier[][] cache = new ASN1ObjectIdentifier[CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV][];
    private byte[] body;
    String identifier;

    public DERObjectIdentifier(String str) {
        if (isValidIdentifier(str)) {
            this.identifier = str;
            return;
        }
        throw new IllegalArgumentException("string " + str + " not an OID");
    }

    DERObjectIdentifier(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        BigInteger bigInteger = null;
        long j = 0;
        Object obj = 1;
        for (int i = 0; i != bArr.length; i++) {
            int i2 = bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            if (j < 36028797018963968L) {
                j = (j * 128) + ((long) (i2 & CertificateBody.profileType));
                if ((i2 & 128) == 0) {
                    if (obj != null) {
                        switch (((int) j) / 40) {
                            case 0:
                                stringBuffer.append('0');
                                break;
                            case 1:
                                stringBuffer.append('1');
                                j -= 40;
                                break;
                            default:
                                stringBuffer.append('2');
                                j -= 80;
                                break;
                        }
                        obj = null;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(j);
                    j = 0;
                }
            } else {
                if (bigInteger == null) {
                    bigInteger = BigInteger.valueOf(j);
                }
                bigInteger = bigInteger.shiftLeft(7).or(BigInteger.valueOf((long) (i2 & CertificateBody.profileType)));
                if ((i2 & 128) == 0) {
                    stringBuffer.append('.');
                    stringBuffer.append(bigInteger);
                    j = 0;
                    bigInteger = null;
                }
            }
        }
        this.identifier = stringBuffer.toString();
    }

    private void doOutput(ByteArrayOutputStream byteArrayOutputStream) {
        OIDTokenizer oIDTokenizer = new OIDTokenizer(this.identifier);
        writeField(byteArrayOutputStream, (long) ((Integer.parseInt(oIDTokenizer.nextToken()) * 40) + Integer.parseInt(oIDTokenizer.nextToken())));
        while (oIDTokenizer.hasMoreTokens()) {
            String nextToken = oIDTokenizer.nextToken();
            if (nextToken.length() < 18) {
                writeField(byteArrayOutputStream, Long.parseLong(nextToken));
            } else {
                writeField(byteArrayOutputStream, new BigInteger(nextToken));
            }
        }
    }

    static ASN1ObjectIdentifier fromOctetString(byte[] bArr) {
        if (bArr.length < 3) {
            return new ASN1ObjectIdentifier(bArr);
        }
        int i = bArr[bArr.length - 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = cache[i];
        if (aSN1ObjectIdentifierArr == null) {
            aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            cache[i] = aSN1ObjectIdentifierArr;
        }
        int i2 = bArr[bArr.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = aSN1ObjectIdentifierArr[i2];
        if (aSN1ObjectIdentifier == null) {
            aSN1ObjectIdentifier = new ASN1ObjectIdentifier(bArr);
            aSN1ObjectIdentifierArr[i2] = aSN1ObjectIdentifier;
            return aSN1ObjectIdentifier;
        } else if (Arrays.areEqual(bArr, aSN1ObjectIdentifier.getBody())) {
            return aSN1ObjectIdentifier;
        } else {
            int i3 = (i + 1) % 256;
            aSN1ObjectIdentifierArr = cache[i3];
            if (aSN1ObjectIdentifierArr == null) {
                aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
                cache[i3] = aSN1ObjectIdentifierArr;
            }
            aSN1ObjectIdentifier = aSN1ObjectIdentifierArr[i2];
            if (aSN1ObjectIdentifier == null) {
                aSN1ObjectIdentifier = new ASN1ObjectIdentifier(bArr);
                aSN1ObjectIdentifierArr[i2] = aSN1ObjectIdentifier;
                return aSN1ObjectIdentifier;
            } else if (Arrays.areEqual(bArr, aSN1ObjectIdentifier.getBody())) {
                return aSN1ObjectIdentifier;
            } else {
                i = (i2 + 1) % 256;
                aSN1ObjectIdentifier = aSN1ObjectIdentifierArr[i];
                if (aSN1ObjectIdentifier != null) {
                    return Arrays.areEqual(bArr, aSN1ObjectIdentifier.getBody()) ? aSN1ObjectIdentifier : new ASN1ObjectIdentifier(bArr);
                } else {
                    aSN1ObjectIdentifier = new ASN1ObjectIdentifier(bArr);
                    aSN1ObjectIdentifierArr[i] = aSN1ObjectIdentifier;
                    return aSN1ObjectIdentifier;
                }
            }
        }
    }

    public static ASN1ObjectIdentifier getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1ObjectIdentifier)) {
            return (ASN1ObjectIdentifier) obj;
        }
        if (obj instanceof DERObjectIdentifier) {
            return new ASN1ObjectIdentifier(((DERObjectIdentifier) obj).getId());
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static ASN1ObjectIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof DERObjectIdentifier)) ? getInstance(object) : fromOctetString(ASN1OctetString.getInstance(aSN1TaggedObject.getObject()).getOctets());
    }

    private static boolean isValidIdentifier(String str) {
        if (str.length() < 3 || str.charAt(1) != '.') {
            return false;
        }
        char charAt = str.charAt(0);
        if (charAt < '0' || charAt > '2') {
            return false;
        }
        boolean z = false;
        for (int length = str.length() - 1; length >= 2; length--) {
            char charAt2 = str.charAt(length);
            if ('0' <= charAt2 && charAt2 <= '9') {
                z = true;
            } else if (charAt2 != '.' || !z) {
                return false;
            } else {
                z = false;
            }
        }
        return z;
    }

    private void writeField(ByteArrayOutputStream byteArrayOutputStream, long j) {
        int i = 8;
        byte[] bArr = new byte[9];
        bArr[8] = (byte) (((int) j) & CertificateBody.profileType);
        while (j >= 128) {
            j >>= 7;
            i--;
            bArr[i] = (byte) ((((int) j) & CertificateBody.profileType) | 128);
        }
        byteArrayOutputStream.write(bArr, i, 9 - i);
    }

    private void writeField(ByteArrayOutputStream byteArrayOutputStream, BigInteger bigInteger) {
        int bitLength = (bigInteger.bitLength() + 6) / 7;
        if (bitLength == 0) {
            byteArrayOutputStream.write(0);
            return;
        }
        int i;
        byte[] bArr = new byte[bitLength];
        for (i = bitLength - 1; i >= 0; i--) {
            bArr[i] = (byte) ((bigInteger.intValue() & CertificateBody.profileType) | 128);
            bigInteger = bigInteger.shiftRight(7);
        }
        i = bitLength - 1;
        bArr[i] = (byte) (bArr[i] & CertificateBody.profileType);
        byteArrayOutputStream.write(bArr, 0, bitLength);
    }

    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        return !(aSN1Primitive instanceof DERObjectIdentifier) ? false : this.identifier.equals(((DERObjectIdentifier) aSN1Primitive).identifier);
    }

    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        byte[] body = getBody();
        aSN1OutputStream.write(6);
        aSN1OutputStream.writeLength(body.length);
        aSN1OutputStream.write(body);
    }

    int encodedLength() throws IOException {
        int length = getBody().length;
        return length + (StreamUtil.calculateBodyLength(length) + 1);
    }

    protected byte[] getBody() {
        if (this.body == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            doOutput(byteArrayOutputStream);
            this.body = byteArrayOutputStream.toByteArray();
        }
        return this.body;
    }

    public String getId() {
        return this.identifier;
    }

    public int hashCode() {
        return this.identifier.hashCode();
    }

    boolean isConstructed() {
        return false;
    }

    public String toString() {
        return getId();
    }
}
