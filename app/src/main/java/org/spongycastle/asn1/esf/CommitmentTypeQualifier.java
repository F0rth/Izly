package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CommitmentTypeQualifier extends ASN1Object {
    private ASN1ObjectIdentifier commitmentTypeIdentifier;
    private ASN1Encodable qualifier;

    public CommitmentTypeQualifier(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this(aSN1ObjectIdentifier, null);
    }

    public CommitmentTypeQualifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.commitmentTypeIdentifier = aSN1ObjectIdentifier;
        this.qualifier = aSN1Encodable;
    }

    private CommitmentTypeQualifier(ASN1Sequence aSN1Sequence) {
        this.commitmentTypeIdentifier = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        if (aSN1Sequence.size() > 1) {
            this.qualifier = aSN1Sequence.getObjectAt(1);
        }
    }

    public static CommitmentTypeQualifier getInstance(Object obj) {
        return obj instanceof CommitmentTypeQualifier ? (CommitmentTypeQualifier) obj : obj != null ? new CommitmentTypeQualifier(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1ObjectIdentifier getCommitmentTypeIdentifier() {
        return this.commitmentTypeIdentifier;
    }

    public ASN1Encodable getQualifier() {
        return this.qualifier;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.commitmentTypeIdentifier);
        if (this.qualifier != null) {
            aSN1EncodableVector.add(this.qualifier);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
