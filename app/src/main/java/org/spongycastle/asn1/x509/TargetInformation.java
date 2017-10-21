package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class TargetInformation extends ASN1Object {
    private ASN1Sequence targets;

    private TargetInformation(ASN1Sequence aSN1Sequence) {
        this.targets = aSN1Sequence;
    }

    public TargetInformation(Targets targets) {
        this.targets = new DERSequence((ASN1Encodable) targets);
    }

    public TargetInformation(Target[] targetArr) {
        this(new Targets(targetArr));
    }

    public static TargetInformation getInstance(Object obj) {
        return obj instanceof TargetInformation ? (TargetInformation) obj : obj != null ? new TargetInformation(ASN1Sequence.getInstance(obj)) : null;
    }

    public Targets[] getTargetsObjects() {
        Targets[] targetsArr = new Targets[this.targets.size()];
        int i = 0;
        Enumeration objects = this.targets.getObjects();
        while (objects.hasMoreElements()) {
            targetsArr[i] = Targets.getInstance(objects.nextElement());
            i++;
        }
        return targetsArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.targets;
    }
}
