package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;

public class POPODecKeyRespContent extends ASN1Object {
    private ASN1Sequence content;

    private POPODecKeyRespContent(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public static POPODecKeyRespContent getInstance(Object obj) {
        return obj instanceof POPODecKeyRespContent ? (POPODecKeyRespContent) obj : obj != null ? new POPODecKeyRespContent(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Integer[] toASN1IntegerArray() {
        ASN1Integer[] aSN1IntegerArr = new ASN1Integer[this.content.size()];
        for (int i = 0; i != aSN1IntegerArr.length; i++) {
            aSN1IntegerArr[i] = DERInteger.getInstance(this.content.getObjectAt(i));
        }
        return aSN1IntegerArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }
}
