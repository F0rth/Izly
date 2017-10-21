package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class SinglePubInfo extends ASN1Object {
    private GeneralName pubLocation;
    private ASN1Integer pubMethod;

    private SinglePubInfo(ASN1Sequence aSN1Sequence) {
        this.pubMethod = DERInteger.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.pubLocation = GeneralName.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public static SinglePubInfo getInstance(Object obj) {
        return obj instanceof SinglePubInfo ? (SinglePubInfo) obj : obj != null ? new SinglePubInfo(ASN1Sequence.getInstance(obj)) : null;
    }

    public GeneralName getPubLocation() {
        return this.pubLocation;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.pubMethod);
        if (this.pubLocation != null) {
            aSN1EncodableVector.add(this.pubLocation);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
