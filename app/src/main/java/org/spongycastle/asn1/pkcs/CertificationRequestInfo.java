package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509Name;

public class CertificationRequestInfo extends ASN1Object {
    ASN1Set attributes = null;
    X500Name subject;
    SubjectPublicKeyInfo subjectPKInfo;
    ASN1Integer version = new ASN1Integer(0);

    public CertificationRequestInfo(ASN1Sequence aSN1Sequence) {
        this.version = (ASN1Integer) aSN1Sequence.getObjectAt(0);
        this.subject = X500Name.getInstance(aSN1Sequence.getObjectAt(1));
        this.subjectPKInfo = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(2));
        if (aSN1Sequence.size() > 3) {
            this.attributes = ASN1Set.getInstance((DERTaggedObject) aSN1Sequence.getObjectAt(3), false);
        }
        if (this.subject == null || this.version == null || this.subjectPKInfo == null) {
            throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    public CertificationRequestInfo(X500Name x500Name, SubjectPublicKeyInfo subjectPublicKeyInfo, ASN1Set aSN1Set) {
        this.subject = x500Name;
        this.subjectPKInfo = subjectPublicKeyInfo;
        this.attributes = aSN1Set;
        if (x500Name == null || this.version == null || this.subjectPKInfo == null) {
            throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    public CertificationRequestInfo(X509Name x509Name, SubjectPublicKeyInfo subjectPublicKeyInfo, ASN1Set aSN1Set) {
        this.subject = X500Name.getInstance(x509Name.toASN1Primitive());
        this.subjectPKInfo = subjectPublicKeyInfo;
        this.attributes = aSN1Set;
        if (x509Name == null || this.version == null || this.subjectPKInfo == null) {
            throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    public static CertificationRequestInfo getInstance(Object obj) {
        return obj instanceof CertificationRequestInfo ? (CertificationRequestInfo) obj : obj != null ? new CertificationRequestInfo(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Set getAttributes() {
        return this.attributes;
    }

    public X500Name getSubject() {
        return this.subject;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPKInfo;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.subject);
        aSN1EncodableVector.add(this.subjectPKInfo);
        if (this.attributes != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.attributes));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
