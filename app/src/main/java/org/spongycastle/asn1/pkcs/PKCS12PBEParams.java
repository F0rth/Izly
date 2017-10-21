package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class PKCS12PBEParams extends ASN1Object {
    ASN1Integer iterations;
    ASN1OctetString iv;

    private PKCS12PBEParams(ASN1Sequence aSN1Sequence) {
        this.iv = (ASN1OctetString) aSN1Sequence.getObjectAt(0);
        this.iterations = DERInteger.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public PKCS12PBEParams(byte[] bArr, int i) {
        this.iv = new DEROctetString(bArr);
        this.iterations = new ASN1Integer(i);
    }

    public static PKCS12PBEParams getInstance(Object obj) {
        return obj instanceof PKCS12PBEParams ? (PKCS12PBEParams) obj : obj != null ? new PKCS12PBEParams(ASN1Sequence.getInstance(obj)) : null;
    }

    public byte[] getIV() {
        return this.iv.getOctets();
    }

    public BigInteger getIterations() {
        return this.iterations.getValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.iv);
        aSN1EncodableVector.add(this.iterations);
        return new DERSequence(aSN1EncodableVector);
    }
}
