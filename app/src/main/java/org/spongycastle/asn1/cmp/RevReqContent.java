package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class RevReqContent extends ASN1Object {
    private ASN1Sequence content;

    private RevReqContent(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public RevReqContent(RevDetails revDetails) {
        this.content = new DERSequence((ASN1Encodable) revDetails);
    }

    public RevReqContent(RevDetails[] revDetailsArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != revDetailsArr.length; i++) {
            aSN1EncodableVector.add(revDetailsArr[i]);
        }
        this.content = new DERSequence(aSN1EncodableVector);
    }

    public static RevReqContent getInstance(Object obj) {
        return obj instanceof RevReqContent ? (RevReqContent) obj : obj != null ? new RevReqContent(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }

    public RevDetails[] toRevDetailsArray() {
        RevDetails[] revDetailsArr = new RevDetails[this.content.size()];
        for (int i = 0; i != revDetailsArr.length; i++) {
            revDetailsArr[i] = RevDetails.getInstance(this.content.getObjectAt(i));
        }
        return revDetailsArr;
    }
}
