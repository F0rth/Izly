package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.io.Streams;

public class DERBitString extends ASN1Primitive implements ASN1String {
    private static final char[] table = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    protected byte[] data;
    protected int padBits;

    protected DERBitString(byte b, int i) {
        this.data = new byte[1];
        this.data[0] = b;
        this.padBits = i;
    }

    public DERBitString(ASN1Encodable aSN1Encodable) {
        try {
            this.data = aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER);
            this.padBits = 0;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error processing object : " + e.toString());
        }
    }

    public DERBitString(byte[] bArr) {
        this(bArr, 0);
    }

    public DERBitString(byte[] bArr, int i) {
        this.data = bArr;
        this.padBits = i;
    }

    static DERBitString fromInputStream(int i, InputStream inputStream) throws IOException {
        if (i <= 0) {
            throw new IllegalArgumentException("truncated BIT STRING detected");
        }
        int read = inputStream.read();
        byte[] bArr = new byte[(i - 1)];
        if (bArr.length == 0 || Streams.readFully(inputStream, bArr) == bArr.length) {
            return new DERBitString(bArr, read);
        }
        throw new EOFException("EOF encountered in middle of BIT STRING");
    }

    static DERBitString fromOctetString(byte[] bArr) {
        if (bArr.length <= 0) {
            throw new IllegalArgumentException("truncated BIT STRING detected");
        }
        int i = bArr[0];
        byte[] bArr2 = new byte[(bArr.length - 1)];
        if (bArr2.length != 0) {
            System.arraycopy(bArr, 1, bArr2, 0, bArr.length - 1);
        }
        return new DERBitString(bArr2, i);
    }

    protected static byte[] getBytes(int i) {
        int i2 = 4;
        int i3 = 3;
        while (i3 > 0 && ((CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV << (i3 * 8)) & i) == 0) {
            i2--;
            i3--;
        }
        byte[] bArr = new byte[i2];
        for (i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) ((i >> (i3 * 8)) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        }
        return bArr;
    }

    public static DERBitString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERBitString)) {
            return (DERBitString) obj;
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static DERBitString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof DERBitString)) ? getInstance(object) : fromOctetString(((ASN1OctetString) object).getOctets());
    }

    protected static int getPadBits(int i) {
        int i2;
        int i3 = 0;
        for (i2 = 3; i2 >= 0; i2--) {
            if (i2 != 0) {
                if ((i >> (i2 * 8)) != 0) {
                    i3 = (i >> (i2 * 8)) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                    break;
                }
            } else if (i != 0) {
                i3 = i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                break;
            }
        }
        if (i3 == 0) {
            return 7;
        }
        i2 = 1;
        while (true) {
            i3 <<= 1;
            if ((i3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) == 0) {
                return 8 - i2;
            }
            i2++;
        }
    }

    protected boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof DERBitString) {
            DERBitString dERBitString = (DERBitString) aSN1Primitive;
            if (this.padBits == dERBitString.padBits && Arrays.areEqual(this.data, dERBitString.data)) {
                return true;
            }
        }
        return false;
    }

    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        Object obj = new byte[(getBytes().length + 1)];
        obj[0] = (byte) getPadBits();
        System.arraycopy(getBytes(), 0, obj, 1, obj.length - 1);
        aSN1OutputStream.writeEncoded(3, obj);
    }

    int encodedLength() {
        return ((StreamUtil.calculateBodyLength(this.data.length + 1) + 1) + this.data.length) + 1;
    }

    public byte[] getBytes() {
        return this.data;
    }

    public int getPadBits() {
        return this.padBits;
    }

    public String getString() {
        StringBuffer stringBuffer = new StringBuffer("#");
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ASN1OutputStream(byteArrayOutputStream).writeObject(this);
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            for (int i = 0; i != toByteArray.length; i++) {
                stringBuffer.append(table[(toByteArray[i] >>> 4) & 15]);
                stringBuffer.append(table[toByteArray[i] & 15]);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            throw new RuntimeException("internal error encoding BitString");
        }
    }

    public int hashCode() {
        return this.padBits ^ Arrays.hashCode(this.data);
    }

    public int intValue() {
        int i = 0;
        int i2 = 0;
        while (i2 != this.data.length && i2 != 4) {
            i |= (this.data[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << (i2 * 8);
            i2++;
        }
        return i;
    }

    boolean isConstructed() {
        return false;
    }

    public String toString() {
        return getString();
    }
}
