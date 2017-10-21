package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class NameConstraints extends ASN1Object {
    private ASN1Sequence excluded;
    private ASN1Sequence permitted;

    public NameConstraints(Vector vector, Vector vector2) {
        if (vector != null) {
            this.permitted = createSequence(vector);
        }
        if (vector2 != null) {
            this.excluded = createSequence(vector2);
        }
    }

    private NameConstraints(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(objects.nextElement());
            switch (instance.getTagNo()) {
                case 0:
                    this.permitted = ASN1Sequence.getInstance(instance, false);
                    break;
                case 1:
                    this.excluded = ASN1Sequence.getInstance(instance, false);
                    break;
                default:
                    break;
            }
        }
    }

    private DERSequence createSequence(Vector vector) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            aSN1EncodableVector.add((GeneralSubtree) elements.nextElement());
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public static NameConstraints getInstance(Object obj) {
        return obj instanceof NameConstraints ? (NameConstraints) obj : obj != null ? new NameConstraints(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Sequence getExcludedSubtrees() {
        return this.excluded;
    }

    public ASN1Sequence getPermittedSubtrees() {
        return this.permitted;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.permitted != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.permitted));
        }
        if (this.excluded != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.excluded));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
