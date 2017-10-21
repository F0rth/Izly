package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEREnumerated;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.CRLReason;

public class RevokedInfo extends ASN1Object {
    private CRLReason revocationReason;
    private DERGeneralizedTime revocationTime;

    private RevokedInfo(ASN1Sequence aSN1Sequence) {
        this.revocationTime = (DERGeneralizedTime) aSN1Sequence.getObjectAt(0);
        if (aSN1Sequence.size() > 1) {
            this.revocationReason = CRLReason.getInstance(DEREnumerated.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true));
        }
    }

    public RevokedInfo(DERGeneralizedTime dERGeneralizedTime, CRLReason cRLReason) {
        this.revocationTime = dERGeneralizedTime;
        this.revocationReason = cRLReason;
    }

    public static RevokedInfo getInstance(Object obj) {
        return obj instanceof RevokedInfo ? (RevokedInfo) obj : obj != null ? new RevokedInfo(ASN1Sequence.getInstance(obj)) : null;
    }

    public static RevokedInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public CRLReason getRevocationReason() {
        return this.revocationReason;
    }

    public DERGeneralizedTime getRevocationTime() {
        return this.revocationTime;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.revocationTime);
        if (this.revocationReason != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.revocationReason));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
