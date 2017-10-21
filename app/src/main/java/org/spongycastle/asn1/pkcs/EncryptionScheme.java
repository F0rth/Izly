package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptionScheme extends AlgorithmIdentifier {
    public EncryptionScheme(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        super(aSN1ObjectIdentifier, aSN1Encodable);
    }

    EncryptionScheme(ASN1Sequence aSN1Sequence) {
        this((ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0), aSN1Sequence.getObjectAt(1));
    }

    public static final AlgorithmIdentifier getInstance(Object obj) {
        if (obj instanceof EncryptionScheme) {
            return (EncryptionScheme) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new EncryptionScheme((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public ASN1Primitive getASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(getObjectId());
        aSN1EncodableVector.add(getParameters());
        return new DERSequence(aSN1EncodableVector);
    }

    public ASN1Primitive getObject() {
        return (ASN1Primitive) getParameters();
    }
}
