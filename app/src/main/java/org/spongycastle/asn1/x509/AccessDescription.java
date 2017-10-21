package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.ocsp.OCSPObjectIdentifiers;

public class AccessDescription extends ASN1Object {
    public static final ASN1ObjectIdentifier id_ad_caIssuers = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.2");
    public static final ASN1ObjectIdentifier id_ad_ocsp = new ASN1ObjectIdentifier(OCSPObjectIdentifiers.pkix_ocsp);
    GeneralName accessLocation = null;
    ASN1ObjectIdentifier accessMethod = null;

    public AccessDescription(ASN1ObjectIdentifier aSN1ObjectIdentifier, GeneralName generalName) {
        this.accessMethod = aSN1ObjectIdentifier;
        this.accessLocation = generalName;
    }

    private AccessDescription(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("wrong number of elements in sequence");
        }
        this.accessMethod = DERObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.accessLocation = GeneralName.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static AccessDescription getInstance(Object obj) {
        return obj instanceof AccessDescription ? (AccessDescription) obj : obj != null ? new AccessDescription(ASN1Sequence.getInstance(obj)) : null;
    }

    public GeneralName getAccessLocation() {
        return this.accessLocation;
    }

    public ASN1ObjectIdentifier getAccessMethod() {
        return this.accessMethod;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.accessMethod);
        aSN1EncodableVector.add(this.accessLocation);
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        return "AccessDescription: Oid(" + this.accessMethod.getId() + ")";
    }
}
