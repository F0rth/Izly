package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;

public class OriginatorIdentifierOrKey extends ASN1Object implements ASN1Choice {
    private ASN1Encodable id;

    public OriginatorIdentifierOrKey(ASN1OctetString aSN1OctetString) {
        this(new SubjectKeyIdentifier(aSN1OctetString.getOctets()));
    }

    public OriginatorIdentifierOrKey(ASN1Primitive aSN1Primitive) {
        this.id = aSN1Primitive;
    }

    public OriginatorIdentifierOrKey(IssuerAndSerialNumber issuerAndSerialNumber) {
        this.id = issuerAndSerialNumber;
    }

    public OriginatorIdentifierOrKey(OriginatorPublicKey originatorPublicKey) {
        this.id = new DERTaggedObject(false, 1, originatorPublicKey);
    }

    public OriginatorIdentifierOrKey(SubjectKeyIdentifier subjectKeyIdentifier) {
        this.id = new DERTaggedObject(false, 0, subjectKeyIdentifier);
    }

    public static OriginatorIdentifierOrKey getInstance(Object obj) {
        if (obj == null || (obj instanceof OriginatorIdentifierOrKey)) {
            return (OriginatorIdentifierOrKey) obj;
        }
        if (obj instanceof IssuerAndSerialNumber) {
            return new OriginatorIdentifierOrKey((IssuerAndSerialNumber) obj);
        }
        if (obj instanceof SubjectKeyIdentifier) {
            return new OriginatorIdentifierOrKey((SubjectKeyIdentifier) obj);
        }
        if (obj instanceof OriginatorPublicKey) {
            return new OriginatorIdentifierOrKey((OriginatorPublicKey) obj);
        }
        if (obj instanceof ASN1TaggedObject) {
            return new OriginatorIdentifierOrKey((ASN1TaggedObject) obj);
        }
        throw new IllegalArgumentException("Invalid OriginatorIdentifierOrKey: " + obj.getClass().getName());
    }

    public static OriginatorIdentifierOrKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            return getInstance(aSN1TaggedObject.getObject());
        }
        throw new IllegalArgumentException("Can't implicitly tag OriginatorIdentifierOrKey");
    }

    public ASN1Encodable getId() {
        return this.id;
    }

    public IssuerAndSerialNumber getIssuerAndSerialNumber() {
        return this.id instanceof IssuerAndSerialNumber ? (IssuerAndSerialNumber) this.id : null;
    }

    public OriginatorPublicKey getOriginatorKey() {
        return ((this.id instanceof ASN1TaggedObject) && ((ASN1TaggedObject) this.id).getTagNo() == 1) ? OriginatorPublicKey.getInstance((ASN1TaggedObject) this.id, false) : null;
    }

    public SubjectKeyIdentifier getSubjectKeyIdentifier() {
        return ((this.id instanceof ASN1TaggedObject) && ((ASN1TaggedObject) this.id).getTagNo() == 0) ? SubjectKeyIdentifier.getInstance((ASN1TaggedObject) this.id, false) : null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.id.toASN1Primitive();
    }
}
