package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;

public class PolicyInformation extends ASN1Object {
    private ASN1ObjectIdentifier policyIdentifier;
    private ASN1Sequence policyQualifiers;

    public PolicyInformation(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.policyIdentifier = aSN1ObjectIdentifier;
    }

    public PolicyInformation(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Sequence aSN1Sequence) {
        this.policyIdentifier = aSN1ObjectIdentifier;
        this.policyQualifiers = aSN1Sequence;
    }

    private PolicyInformation(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() <= 0 || aSN1Sequence.size() > 2) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.policyIdentifier = DERObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() > 1) {
            this.policyQualifiers = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public static PolicyInformation getInstance(Object obj) {
        return (obj == null || (obj instanceof PolicyInformation)) ? (PolicyInformation) obj : new PolicyInformation(ASN1Sequence.getInstance(obj));
    }

    public ASN1ObjectIdentifier getPolicyIdentifier() {
        return this.policyIdentifier;
    }

    public ASN1Sequence getPolicyQualifiers() {
        return this.policyQualifiers;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.policyIdentifier);
        if (this.policyQualifiers != null) {
            aSN1EncodableVector.add(this.policyQualifiers);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
