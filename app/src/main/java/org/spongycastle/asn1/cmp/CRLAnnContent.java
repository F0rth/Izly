package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.CertificateList;

public class CRLAnnContent extends ASN1Object {
    private ASN1Sequence content;

    private CRLAnnContent(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public CRLAnnContent(CertificateList certificateList) {
        this.content = new DERSequence((ASN1Encodable) certificateList);
    }

    public static CRLAnnContent getInstance(Object obj) {
        return obj instanceof CRLAnnContent ? (CRLAnnContent) obj : obj != null ? new CRLAnnContent(ASN1Sequence.getInstance(obj)) : null;
    }

    public CertificateList[] getCertificateLists() {
        CertificateList[] certificateListArr = new CertificateList[this.content.size()];
        for (int i = 0; i != certificateListArr.length; i++) {
            certificateListArr[i] = CertificateList.getInstance(this.content.getObjectAt(i));
        }
        return certificateListArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }
}
