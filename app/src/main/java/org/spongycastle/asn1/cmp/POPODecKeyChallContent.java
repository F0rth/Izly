package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class POPODecKeyChallContent extends ASN1Object {
    private ASN1Sequence content;

    private POPODecKeyChallContent(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public static POPODecKeyChallContent getInstance(Object obj) {
        return obj instanceof POPODecKeyChallContent ? (POPODecKeyChallContent) obj : obj != null ? new POPODecKeyChallContent(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }

    public Challenge[] toChallengeArray() {
        Challenge[] challengeArr = new Challenge[this.content.size()];
        for (int i = 0; i != challengeArr.length; i++) {
            challengeArr[i] = Challenge.getInstance(this.content.getObjectAt(i));
        }
        return challengeArr;
    }
}
