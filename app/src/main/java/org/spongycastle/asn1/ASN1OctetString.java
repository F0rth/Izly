package org.spongycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.encoders.Hex;

public abstract class ASN1OctetString extends ASN1Primitive implements ASN1OctetStringParser {
    byte[] string;

    public ASN1OctetString(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("string cannot be null");
        }
        this.string = bArr;
    }

    public static ASN1OctetString getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1OctetString)) {
            return (ASN1OctetString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + e.getMessage());
            }
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive toASN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (toASN1Primitive instanceof ASN1OctetString) {
                return (ASN1OctetString) toASN1Primitive;
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static ASN1OctetString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof ASN1OctetString)) ? getInstance(object) : BEROctetString.fromSequence(ASN1Sequence.getInstance(object));
    }

    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1OctetString)) {
            return false;
        }
        return Arrays.areEqual(this.string, ((ASN1OctetString) aSN1Primitive).string);
    }

    abstract void encode(ASN1OutputStream aSN1OutputStream) throws IOException;

    public ASN1Primitive getLoadedObject() {
        return toASN1Primitive();
    }

    public InputStream getOctetStream() {
        return new ByteArrayInputStream(this.string);
    }

    public byte[] getOctets() {
        return this.string;
    }

    public int hashCode() {
        return Arrays.hashCode(getOctets());
    }

    public ASN1OctetStringParser parser() {
        return this;
    }

    ASN1Primitive toDERObject() {
        return new DEROctetString(this.string);
    }

    ASN1Primitive toDLObject() {
        return new DEROctetString(this.string);
    }

    public String toString() {
        return "#" + new String(Hex.encode(this.string));
    }
}
