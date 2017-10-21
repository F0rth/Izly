package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

class LazyEncodedSequence extends ASN1Sequence {
    private byte[] encoded;

    LazyEncodedSequence(byte[] bArr) throws IOException {
        this.encoded = bArr;
    }

    private void parse() {
        Enumeration lazyConstructionEnumeration = new LazyConstructionEnumeration(this.encoded);
        while (lazyConstructionEnumeration.hasMoreElements()) {
            this.seq.addElement(lazyConstructionEnumeration.nextElement());
        }
        this.encoded = null;
    }

    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        if (this.encoded != null) {
            aSN1OutputStream.writeEncoded(48, this.encoded);
        } else {
            super.toDLObject().encode(aSN1OutputStream);
        }
    }

    int encodedLength() throws IOException {
        return this.encoded != null ? (StreamUtil.calculateBodyLength(this.encoded.length) + 1) + this.encoded.length : super.toDLObject().encodedLength();
    }

    public ASN1Encodable getObjectAt(int i) {
        ASN1Encodable objectAt;
        synchronized (this) {
            if (this.encoded != null) {
                parse();
            }
            objectAt = super.getObjectAt(i);
        }
        return objectAt;
    }

    public Enumeration getObjects() {
        Enumeration objects;
        synchronized (this) {
            objects = this.encoded == null ? super.getObjects() : new LazyConstructionEnumeration(this.encoded);
        }
        return objects;
    }

    public int size() {
        int size;
        synchronized (this) {
            if (this.encoded != null) {
                parse();
            }
            size = super.size();
        }
        return size;
    }

    ASN1Primitive toDERObject() {
        if (this.encoded != null) {
            parse();
        }
        return super.toDERObject();
    }

    ASN1Primitive toDLObject() {
        if (this.encoded != null) {
            parse();
        }
        return super.toDLObject();
    }
}
