package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DLSequence;

public class AuthenticatedSafe extends ASN1Object {
    private ContentInfo[] info;
    private boolean isBer = true;

    private AuthenticatedSafe(ASN1Sequence aSN1Sequence) {
        this.info = new ContentInfo[aSN1Sequence.size()];
        for (int i = 0; i != this.info.length; i++) {
            this.info[i] = ContentInfo.getInstance(aSN1Sequence.getObjectAt(i));
        }
        this.isBer = aSN1Sequence instanceof BERSequence;
    }

    public AuthenticatedSafe(ContentInfo[] contentInfoArr) {
        this.info = contentInfoArr;
    }

    public static AuthenticatedSafe getInstance(Object obj) {
        return obj instanceof AuthenticatedSafe ? (AuthenticatedSafe) obj : obj != null ? new AuthenticatedSafe(ASN1Sequence.getInstance(obj)) : null;
    }

    public ContentInfo[] getContentInfo() {
        return this.info;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != this.info.length; i++) {
            aSN1EncodableVector.add(this.info[i]);
        }
        return this.isBer ? new BERSequence(aSN1EncodableVector) : new DLSequence(aSN1EncodableVector);
    }
}
