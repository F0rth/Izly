package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DLSequence;
import org.spongycastle.asn1.DLTaggedObject;

public class SafeBag extends ASN1Object {
    private ASN1Set bagAttributes;
    private ASN1ObjectIdentifier bagId;
    private ASN1Encodable bagValue;

    public SafeBag(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.bagId = aSN1ObjectIdentifier;
        this.bagValue = aSN1Encodable;
        this.bagAttributes = null;
    }

    public SafeBag(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable, ASN1Set aSN1Set) {
        this.bagId = aSN1ObjectIdentifier;
        this.bagValue = aSN1Encodable;
        this.bagAttributes = aSN1Set;
    }

    private SafeBag(ASN1Sequence aSN1Sequence) {
        this.bagId = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.bagValue = ((ASN1TaggedObject) aSN1Sequence.getObjectAt(1)).getObject();
        if (aSN1Sequence.size() == 3) {
            this.bagAttributes = (ASN1Set) aSN1Sequence.getObjectAt(2);
        }
    }

    public static SafeBag getInstance(Object obj) {
        return obj instanceof SafeBag ? (SafeBag) obj : obj != null ? new SafeBag(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Set getBagAttributes() {
        return this.bagAttributes;
    }

    public ASN1ObjectIdentifier getBagId() {
        return this.bagId;
    }

    public ASN1Encodable getBagValue() {
        return this.bagValue;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.bagId);
        aSN1EncodableVector.add(new DLTaggedObject(true, 0, this.bagValue));
        if (this.bagAttributes != null) {
            aSN1EncodableVector.add(this.bagAttributes);
        }
        return new DLSequence(aSN1EncodableVector);
    }
}
