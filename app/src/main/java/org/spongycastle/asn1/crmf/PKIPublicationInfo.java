package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;

public class PKIPublicationInfo extends ASN1Object {
    private ASN1Integer action;
    private ASN1Sequence pubInfos;

    private PKIPublicationInfo(ASN1Sequence aSN1Sequence) {
        this.action = DERInteger.getInstance(aSN1Sequence.getObjectAt(0));
        this.pubInfos = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static PKIPublicationInfo getInstance(Object obj) {
        return obj instanceof PKIPublicationInfo ? (PKIPublicationInfo) obj : obj != null ? new PKIPublicationInfo(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Integer getAction() {
        return this.action;
    }

    public SinglePubInfo[] getPubInfos() {
        if (this.pubInfos == null) {
            return null;
        }
        SinglePubInfo[] singlePubInfoArr = new SinglePubInfo[this.pubInfos.size()];
        for (int i = 0; i != singlePubInfoArr.length; i++) {
            singlePubInfoArr[i] = SinglePubInfo.getInstance(this.pubInfos.getObjectAt(i));
        }
        return singlePubInfoArr;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.action);
        aSN1EncodableVector.add(this.pubInfos);
        return new DERSequence(aSN1EncodableVector);
    }
}
