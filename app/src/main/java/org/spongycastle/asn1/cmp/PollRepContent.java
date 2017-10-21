package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;

public class PollRepContent extends ASN1Object {
    private ASN1Integer certReqId;
    private ASN1Integer checkAfter;
    private PKIFreeText reason;

    private PollRepContent(ASN1Sequence aSN1Sequence) {
        this.certReqId = DERInteger.getInstance(aSN1Sequence.getObjectAt(0));
        this.checkAfter = DERInteger.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() > 2) {
            this.reason = PKIFreeText.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public static PollRepContent getInstance(Object obj) {
        return obj instanceof PollRepContent ? (PollRepContent) obj : obj != null ? new PollRepContent(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Integer getCertReqId() {
        return this.certReqId;
    }

    public ASN1Integer getCheckAfter() {
        return this.checkAfter;
    }

    public PKIFreeText getReason() {
        return this.reason;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.certReqId);
        aSN1EncodableVector.add(this.checkAfter);
        if (this.reason != null) {
            aSN1EncodableVector.add(this.reason);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
