package org.spongycastle.asn1.icao;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;

public class LDSVersionInfo extends ASN1Object {
    private DERPrintableString ldsVersion;
    private DERPrintableString unicodeVersion;

    public LDSVersionInfo(String str, String str2) {
        this.ldsVersion = new DERPrintableString(str);
        this.unicodeVersion = new DERPrintableString(str2);
    }

    private LDSVersionInfo(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("sequence wrong size for LDSVersionInfo");
        }
        this.ldsVersion = DERPrintableString.getInstance(aSN1Sequence.getObjectAt(0));
        this.unicodeVersion = DERPrintableString.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static LDSVersionInfo getInstance(Object obj) {
        return obj instanceof LDSVersionInfo ? (LDSVersionInfo) obj : obj != null ? new LDSVersionInfo(ASN1Sequence.getInstance(obj)) : null;
    }

    public String getLdsVersion() {
        return this.ldsVersion.getString();
    }

    public String getUnicodeVersion() {
        return this.unicodeVersion.getString();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.ldsVersion);
        aSN1EncodableVector.add(this.unicodeVersion);
        return new DERSequence(aSN1EncodableVector);
    }
}
