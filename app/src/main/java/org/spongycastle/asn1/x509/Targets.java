package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class Targets extends ASN1Object {
    private ASN1Sequence targets;

    private Targets(ASN1Sequence aSN1Sequence) {
        this.targets = aSN1Sequence;
    }

    public Targets(Target[] targetArr) {
        this.targets = new DERSequence((ASN1Encodable[]) targetArr);
    }

    public static Targets getInstance(Object obj) {
        return obj instanceof Targets ? (Targets) obj : obj != null ? new Targets(ASN1Sequence.getInstance(obj)) : null;
    }

    public Target[] getTargets() {
        Target[] targetArr = new Target[this.targets.size()];
        int i = 0;
        Enumeration objects = this.targets.getObjects();
        while (objects.hasMoreElements()) {
            targetArr[i] = Target.getInstance(objects.nextElement());
            i++;
        }
        return targetArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.targets;
    }
}
