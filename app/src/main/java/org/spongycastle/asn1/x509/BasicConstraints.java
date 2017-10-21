package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBoolean;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;

public class BasicConstraints extends ASN1Object {
    DERBoolean cA;
    ASN1Integer pathLenConstraint;

    public BasicConstraints(int i) {
        this.cA = new DERBoolean(false);
        this.pathLenConstraint = null;
        this.cA = new DERBoolean(true);
        this.pathLenConstraint = new ASN1Integer(i);
    }

    private BasicConstraints(ASN1Sequence aSN1Sequence) {
        this.cA = new DERBoolean(false);
        this.pathLenConstraint = null;
        if (aSN1Sequence.size() == 0) {
            this.cA = null;
            this.pathLenConstraint = null;
            return;
        }
        if (aSN1Sequence.getObjectAt(0) instanceof DERBoolean) {
            this.cA = DERBoolean.getInstance(aSN1Sequence.getObjectAt(0));
        } else {
            this.cA = null;
            this.pathLenConstraint = DERInteger.getInstance(aSN1Sequence.getObjectAt(0));
        }
        if (aSN1Sequence.size() <= 1) {
            return;
        }
        if (this.cA != null) {
            this.pathLenConstraint = DERInteger.getInstance(aSN1Sequence.getObjectAt(1));
            return;
        }
        throw new IllegalArgumentException("wrong sequence in constructor");
    }

    public BasicConstraints(boolean z) {
        this.cA = new DERBoolean(false);
        this.pathLenConstraint = null;
        if (z) {
            this.cA = new DERBoolean(true);
        } else {
            this.cA = null;
        }
        this.pathLenConstraint = null;
    }

    public static BasicConstraints getInstance(Object obj) {
        Object obj2 = obj;
        while (!(obj2 instanceof BasicConstraints)) {
            if (!(obj2 instanceof X509Extension)) {
                return obj2 != null ? new BasicConstraints(ASN1Sequence.getInstance(obj2)) : null;
            } else {
                obj2 = X509Extension.convertValueToObject((X509Extension) obj2);
            }
        }
        return (BasicConstraints) obj2;
    }

    public static BasicConstraints getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public BigInteger getPathLenConstraint() {
        return this.pathLenConstraint != null ? this.pathLenConstraint.getValue() : null;
    }

    public boolean isCA() {
        return this.cA != null && this.cA.isTrue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.cA != null) {
            aSN1EncodableVector.add(this.cA);
        }
        if (this.pathLenConstraint != null) {
            aSN1EncodableVector.add(this.pathLenConstraint);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        return this.pathLenConstraint == null ? this.cA == null ? "BasicConstraints: isCa(false)" : "BasicConstraints: isCa(" + isCA() + ")" : "BasicConstraints: isCa(" + isCA() + "), pathLenConstraint = " + this.pathLenConstraint.getValue();
    }
}
