package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class GenRepContent extends ASN1Object {
    private ASN1Sequence content;

    private GenRepContent(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public GenRepContent(InfoTypeAndValue infoTypeAndValue) {
        this.content = new DERSequence((ASN1Encodable) infoTypeAndValue);
    }

    public GenRepContent(InfoTypeAndValue[] infoTypeAndValueArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (ASN1Encodable add : infoTypeAndValueArr) {
            aSN1EncodableVector.add(add);
        }
        this.content = new DERSequence(aSN1EncodableVector);
    }

    public static GenRepContent getInstance(Object obj) {
        return obj instanceof GenRepContent ? (GenRepContent) obj : obj != null ? new GenRepContent(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }

    public InfoTypeAndValue[] toInfoTypeAndValueArray() {
        InfoTypeAndValue[] infoTypeAndValueArr = new InfoTypeAndValue[this.content.size()];
        for (int i = 0; i != infoTypeAndValueArr.length; i++) {
            infoTypeAndValueArr[i] = InfoTypeAndValue.getInstance(this.content.getObjectAt(i));
        }
        return infoTypeAndValueArr;
    }
}
