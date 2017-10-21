package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class OCSPResponse extends ASN1Object {
    ResponseBytes responseBytes;
    OCSPResponseStatus responseStatus;

    private OCSPResponse(ASN1Sequence aSN1Sequence) {
        this.responseStatus = OCSPResponseStatus.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.responseBytes = ResponseBytes.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true);
        }
    }

    public OCSPResponse(OCSPResponseStatus oCSPResponseStatus, ResponseBytes responseBytes) {
        this.responseStatus = oCSPResponseStatus;
        this.responseBytes = responseBytes;
    }

    public static OCSPResponse getInstance(Object obj) {
        return obj instanceof OCSPResponse ? (OCSPResponse) obj : obj != null ? new OCSPResponse(ASN1Sequence.getInstance(obj)) : null;
    }

    public static OCSPResponse getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ResponseBytes getResponseBytes() {
        return this.responseBytes;
    }

    public OCSPResponseStatus getResponseStatus() {
        return this.responseStatus;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.responseStatus);
        if (this.responseBytes != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.responseBytes));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
