package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.GeneralName;

public class EncKeyWithID extends ASN1Object {
    private final ASN1Encodable identifier;
    private final PrivateKeyInfo privKeyInfo;

    private EncKeyWithID(ASN1Sequence aSN1Sequence) {
        this.privKeyInfo = PrivateKeyInfo.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() <= 1) {
            this.identifier = null;
        } else if (aSN1Sequence.getObjectAt(1) instanceof DERUTF8String) {
            this.identifier = aSN1Sequence.getObjectAt(1);
        } else {
            this.identifier = GeneralName.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public EncKeyWithID(PrivateKeyInfo privateKeyInfo) {
        this.privKeyInfo = privateKeyInfo;
        this.identifier = null;
    }

    public EncKeyWithID(PrivateKeyInfo privateKeyInfo, DERUTF8String dERUTF8String) {
        this.privKeyInfo = privateKeyInfo;
        this.identifier = dERUTF8String;
    }

    public EncKeyWithID(PrivateKeyInfo privateKeyInfo, GeneralName generalName) {
        this.privKeyInfo = privateKeyInfo;
        this.identifier = generalName;
    }

    public static EncKeyWithID getInstance(Object obj) {
        return obj instanceof EncKeyWithID ? (EncKeyWithID) obj : obj != null ? new EncKeyWithID(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1Encodable getIdentifier() {
        return this.identifier;
    }

    public PrivateKeyInfo getPrivateKey() {
        return this.privKeyInfo;
    }

    public boolean hasIdentifier() {
        return this.identifier != null;
    }

    public boolean isIdentifierUTF8String() {
        return this.identifier instanceof DERUTF8String;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.privKeyInfo);
        if (this.identifier != null) {
            aSN1EncodableVector.add(this.identifier);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
