package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class GeneralNames extends ASN1Object {
    private final GeneralName[] names;

    private GeneralNames(ASN1Sequence aSN1Sequence) {
        this.names = new GeneralName[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            this.names[i] = GeneralName.getInstance(aSN1Sequence.getObjectAt(i));
        }
    }

    public GeneralNames(GeneralName generalName) {
        this.names = new GeneralName[]{generalName};
    }

    public GeneralNames(GeneralName[] generalNameArr) {
        this.names = generalNameArr;
    }

    public static GeneralNames getInstance(Object obj) {
        return obj instanceof GeneralNames ? (GeneralNames) obj : obj != null ? new GeneralNames(ASN1Sequence.getInstance(obj)) : null;
    }

    public static GeneralNames getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public GeneralName[] getNames() {
        Object obj = new GeneralName[this.names.length];
        System.arraycopy(this.names, 0, obj, 0, this.names.length);
        return obj;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(this.names);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("GeneralNames:");
        stringBuffer.append(property);
        for (int i = 0; i != this.names.length; i++) {
            stringBuffer.append("    ");
            stringBuffer.append(this.names[i]);
            stringBuffer.append(property);
        }
        return stringBuffer.toString();
    }
}
