package org.spongycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class AdmissionSyntax extends ASN1Object {
    private GeneralName admissionAuthority;
    private ASN1Sequence contentsOfAdmissions;

    private AdmissionSyntax(ASN1Sequence aSN1Sequence) {
        switch (aSN1Sequence.size()) {
            case 1:
                this.contentsOfAdmissions = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(0));
                return;
            case 2:
                this.admissionAuthority = GeneralName.getInstance(aSN1Sequence.getObjectAt(0));
                this.contentsOfAdmissions = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1));
                return;
            default:
                throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
    }

    public AdmissionSyntax(GeneralName generalName, ASN1Sequence aSN1Sequence) {
        this.admissionAuthority = generalName;
        this.contentsOfAdmissions = aSN1Sequence;
    }

    public static AdmissionSyntax getInstance(Object obj) {
        if (obj == null || (obj instanceof AdmissionSyntax)) {
            return (AdmissionSyntax) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new AdmissionSyntax((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public GeneralName getAdmissionAuthority() {
        return this.admissionAuthority;
    }

    public Admissions[] getContentsOfAdmissions() {
        Admissions[] admissionsArr = new Admissions[this.contentsOfAdmissions.size()];
        int i = 0;
        Enumeration objects = this.contentsOfAdmissions.getObjects();
        while (objects.hasMoreElements()) {
            admissionsArr[i] = Admissions.getInstance(objects.nextElement());
            i++;
        }
        return admissionsArr;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.admissionAuthority != null) {
            aSN1EncodableVector.add(this.admissionAuthority);
        }
        aSN1EncodableVector.add(this.contentsOfAdmissions);
        return new DERSequence(aSN1EncodableVector);
    }
}
