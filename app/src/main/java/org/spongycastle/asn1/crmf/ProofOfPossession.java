package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERTaggedObject;

public class ProofOfPossession extends ASN1Object implements ASN1Choice {
    public static final int TYPE_KEY_AGREEMENT = 3;
    public static final int TYPE_KEY_ENCIPHERMENT = 2;
    public static final int TYPE_RA_VERIFIED = 0;
    public static final int TYPE_SIGNING_KEY = 1;
    private ASN1Encodable obj;
    private int tagNo;

    public ProofOfPossession() {
        this.tagNo = 0;
        this.obj = DERNull.INSTANCE;
    }

    public ProofOfPossession(int i, POPOPrivKey pOPOPrivKey) {
        this.tagNo = i;
        this.obj = pOPOPrivKey;
    }

    private ProofOfPossession(ASN1TaggedObject aSN1TaggedObject) {
        this.tagNo = aSN1TaggedObject.getTagNo();
        switch (this.tagNo) {
            case 0:
                this.obj = DERNull.INSTANCE;
                return;
            case 1:
                this.obj = POPOSigningKey.getInstance(aSN1TaggedObject, false);
                return;
            case 2:
            case 3:
                this.obj = POPOPrivKey.getInstance(aSN1TaggedObject, false);
                return;
            default:
                throw new IllegalArgumentException("unknown tag: " + this.tagNo);
        }
    }

    public ProofOfPossession(POPOSigningKey pOPOSigningKey) {
        this.tagNo = 1;
        this.obj = pOPOSigningKey;
    }

    public static ProofOfPossession getInstance(Object obj) {
        if (obj == null || (obj instanceof ProofOfPossession)) {
            return (ProofOfPossession) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new ProofOfPossession((ASN1TaggedObject) obj);
        }
        throw new IllegalArgumentException("Invalid object: " + obj.getClass().getName());
    }

    public ASN1Encodable getObject() {
        return this.obj;
    }

    public int getType() {
        return this.tagNo;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(false, this.tagNo, this.obj);
    }
}
