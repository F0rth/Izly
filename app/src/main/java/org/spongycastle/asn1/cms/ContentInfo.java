package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;

public class ContentInfo extends ASN1Object implements CMSObjectIdentifiers {
    private ASN1Encodable content;
    private ASN1ObjectIdentifier contentType;

    public ContentInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.contentType = aSN1ObjectIdentifier;
        this.content = aSN1Encodable;
    }

    public ContentInfo(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() <= 0 || aSN1Sequence.size() > 2) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.contentType = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        if (aSN1Sequence.size() > 1) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(1);
            if (aSN1TaggedObject.isExplicit() && aSN1TaggedObject.getTagNo() == 0) {
                this.content = aSN1TaggedObject.getObject();
                return;
            }
            throw new IllegalArgumentException("Bad tag for 'content'");
        }
    }

    public static ContentInfo getInstance(Object obj) {
        return obj instanceof ContentInfo ? (ContentInfo) obj : obj != null ? new ContentInfo(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Encodable getContent() {
        return this.content;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.contentType;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.contentType);
        if (this.content != null) {
            aSN1EncodableVector.add(new BERTaggedObject(0, this.content));
        }
        return new BERSequence(aSN1EncodableVector);
    }
}
