package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;

public class CertRequest extends ASN1Object {
    private ASN1Integer certReqId;
    private CertTemplate certTemplate;
    private Controls controls;

    public CertRequest(int i, CertTemplate certTemplate, Controls controls) {
        this(new ASN1Integer(i), certTemplate, controls);
    }

    public CertRequest(ASN1Integer aSN1Integer, CertTemplate certTemplate, Controls controls) {
        this.certReqId = aSN1Integer;
        this.certTemplate = certTemplate;
        this.controls = controls;
    }

    private CertRequest(ASN1Sequence aSN1Sequence) {
        this.certReqId = new ASN1Integer(DERInteger.getInstance(aSN1Sequence.getObjectAt(0)).getValue());
        this.certTemplate = CertTemplate.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() > 2) {
            this.controls = Controls.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public static CertRequest getInstance(Object obj) {
        return obj instanceof CertRequest ? (CertRequest) obj : obj != null ? new CertRequest(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Integer getCertReqId() {
        return this.certReqId;
    }

    public CertTemplate getCertTemplate() {
        return this.certTemplate;
    }

    public Controls getControls() {
        return this.controls;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.certReqId);
        aSN1EncodableVector.add(this.certTemplate);
        if (this.controls != null) {
            aSN1EncodableVector.add(this.controls);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
