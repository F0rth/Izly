package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;

public class SignaturePolicyId extends ASN1Object {
    private OtherHashAlgAndValue sigPolicyHash;
    private ASN1ObjectIdentifier sigPolicyId;
    private SigPolicyQualifiers sigPolicyQualifiers;

    public SignaturePolicyId(ASN1ObjectIdentifier aSN1ObjectIdentifier, OtherHashAlgAndValue otherHashAlgAndValue) {
        this(aSN1ObjectIdentifier, otherHashAlgAndValue, null);
    }

    public SignaturePolicyId(ASN1ObjectIdentifier aSN1ObjectIdentifier, OtherHashAlgAndValue otherHashAlgAndValue, SigPolicyQualifiers sigPolicyQualifiers) {
        this.sigPolicyId = aSN1ObjectIdentifier;
        this.sigPolicyHash = otherHashAlgAndValue;
        this.sigPolicyQualifiers = sigPolicyQualifiers;
    }

    private SignaturePolicyId(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 2 || aSN1Sequence.size() == 3) {
            this.sigPolicyId = DERObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
            this.sigPolicyHash = OtherHashAlgAndValue.getInstance(aSN1Sequence.getObjectAt(1));
            if (aSN1Sequence.size() == 3) {
                this.sigPolicyQualifiers = SigPolicyQualifiers.getInstance(aSN1Sequence.getObjectAt(2));
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
    }

    public static SignaturePolicyId getInstance(Object obj) {
        return obj instanceof SignaturePolicyId ? (SignaturePolicyId) obj : obj != null ? new SignaturePolicyId(ASN1Sequence.getInstance(obj)) : null;
    }

    public OtherHashAlgAndValue getSigPolicyHash() {
        return this.sigPolicyHash;
    }

    public ASN1ObjectIdentifier getSigPolicyId() {
        return new ASN1ObjectIdentifier(this.sigPolicyId.getId());
    }

    public SigPolicyQualifiers getSigPolicyQualifiers() {
        return this.sigPolicyQualifiers;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.sigPolicyId);
        aSN1EncodableVector.add(this.sigPolicyHash);
        if (this.sigPolicyQualifiers != null) {
            aSN1EncodableVector.add(this.sigPolicyQualifiers);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
