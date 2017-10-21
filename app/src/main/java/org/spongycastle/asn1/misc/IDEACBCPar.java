package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class IDEACBCPar extends ASN1Object {
    ASN1OctetString iv;

    public IDEACBCPar(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 1) {
            this.iv = (ASN1OctetString) aSN1Sequence.getObjectAt(0);
        } else {
            this.iv = null;
        }
    }

    public IDEACBCPar(byte[] bArr) {
        this.iv = new DEROctetString(bArr);
    }

    public static IDEACBCPar getInstance(Object obj) {
        return obj instanceof IDEACBCPar ? (IDEACBCPar) obj : obj != null ? new IDEACBCPar(ASN1Sequence.getInstance(obj)) : null;
    }

    public byte[] getIV() {
        return this.iv != null ? this.iv.getOctets() : null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.iv != null) {
            aSN1EncodableVector.add(this.iv);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
