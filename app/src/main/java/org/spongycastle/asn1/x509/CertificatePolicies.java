package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class CertificatePolicies extends ASN1Object {
    private final PolicyInformation[] policyInformation;

    private CertificatePolicies(ASN1Sequence aSN1Sequence) {
        this.policyInformation = new PolicyInformation[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            this.policyInformation[i] = PolicyInformation.getInstance(aSN1Sequence.getObjectAt(i));
        }
    }

    public CertificatePolicies(PolicyInformation policyInformation) {
        this.policyInformation = new PolicyInformation[]{policyInformation};
    }

    public CertificatePolicies(PolicyInformation[] policyInformationArr) {
        this.policyInformation = policyInformationArr;
    }

    public static CertificatePolicies getInstance(Object obj) {
        return obj instanceof CertificatePolicies ? (CertificatePolicies) obj : obj != null ? new CertificatePolicies(ASN1Sequence.getInstance(obj)) : null;
    }

    public static CertificatePolicies getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public PolicyInformation[] getPolicyInformation() {
        Object obj = new PolicyInformation[this.policyInformation.length];
        System.arraycopy(this.policyInformation, 0, obj, 0, this.policyInformation.length);
        return obj;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(this.policyInformation);
    }

    public String toString() {
        String str = null;
        for (Object obj : this.policyInformation) {
            if (str != null) {
                str = str + ", ";
            }
            str = str + obj;
        }
        return "CertificatePolicies: " + str;
    }
}
