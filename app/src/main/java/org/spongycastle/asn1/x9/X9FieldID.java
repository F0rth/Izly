package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class X9FieldID extends ASN1Object implements X9ObjectIdentifiers {
    private ASN1ObjectIdentifier id;
    private ASN1Primitive parameters;

    public X9FieldID(int i, int i2, int i3, int i4) {
        this.id = characteristic_two_field;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(i));
        if (i3 == 0) {
            aSN1EncodableVector.add(tpBasis);
            aSN1EncodableVector.add(new ASN1Integer(i2));
        } else {
            aSN1EncodableVector.add(ppBasis);
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            aSN1EncodableVector2.add(new ASN1Integer(i2));
            aSN1EncodableVector2.add(new ASN1Integer(i3));
            aSN1EncodableVector2.add(new ASN1Integer(i4));
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        }
        this.parameters = new DERSequence(aSN1EncodableVector);
    }

    public X9FieldID(BigInteger bigInteger) {
        this.id = prime_field;
        this.parameters = new ASN1Integer(bigInteger);
    }

    public X9FieldID(ASN1Sequence aSN1Sequence) {
        this.id = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.parameters = (ASN1Primitive) aSN1Sequence.getObjectAt(1);
    }

    public ASN1ObjectIdentifier getIdentifier() {
        return this.id;
    }

    public ASN1Primitive getParameters() {
        return this.parameters;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.id);
        aSN1EncodableVector.add(this.parameters);
        return new DERSequence(aSN1EncodableVector);
    }
}
