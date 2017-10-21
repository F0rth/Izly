package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class DigestInfo extends ASN1Object {
    private AlgorithmIdentifier algId;
    private byte[] digest;

    public DigestInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.algId = AlgorithmIdentifier.getInstance(objects.nextElement());
        this.digest = ASN1OctetString.getInstance(objects.nextElement()).getOctets();
    }

    public DigestInfo(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.digest = bArr;
        this.algId = algorithmIdentifier;
    }

    public static DigestInfo getInstance(Object obj) {
        return obj instanceof DigestInfo ? (DigestInfo) obj : obj != null ? new DigestInfo(ASN1Sequence.getInstance(obj)) : null;
    }

    public static DigestInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public byte[] getDigest() {
        return this.digest;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(new DEROctetString(this.digest));
        return new DERSequence(aSN1EncodableVector);
    }
}
