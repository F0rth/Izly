package org.spongycastle.asn1.icao;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;

public class DataGroupHash extends ASN1Object {
    ASN1OctetString dataGroupHashValue;
    ASN1Integer dataGroupNumber;

    public DataGroupHash(int i, ASN1OctetString aSN1OctetString) {
        this.dataGroupNumber = new ASN1Integer(i);
        this.dataGroupHashValue = aSN1OctetString;
    }

    private DataGroupHash(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.dataGroupNumber = DERInteger.getInstance(objects.nextElement());
        this.dataGroupHashValue = ASN1OctetString.getInstance(objects.nextElement());
    }

    public static DataGroupHash getInstance(Object obj) {
        return obj instanceof DataGroupHash ? (DataGroupHash) obj : obj != null ? new DataGroupHash(ASN1Sequence.getInstance(obj)) : null;
    }

    public ASN1OctetString getDataGroupHashValue() {
        return this.dataGroupHashValue;
    }

    public int getDataGroupNumber() {
        return this.dataGroupNumber.getValue().intValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.dataGroupNumber);
        aSN1EncodableVector.add(this.dataGroupHashValue);
        return new DERSequence(aSN1EncodableVector);
    }
}
