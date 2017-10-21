package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x500.DirectoryString;

public class AdditionalInformationSyntax extends ASN1Object {
    private DirectoryString information;

    public AdditionalInformationSyntax(String str) {
        this(new DirectoryString(str));
    }

    private AdditionalInformationSyntax(DirectoryString directoryString) {
        this.information = directoryString;
    }

    public static AdditionalInformationSyntax getInstance(Object obj) {
        return obj instanceof AdditionalInformationSyntax ? (AdditionalInformationSyntax) obj : obj != null ? new AdditionalInformationSyntax(DirectoryString.getInstance(obj)) : null;
    }

    public DirectoryString getInformation() {
        return this.information;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.information.toASN1Primitive();
    }
}
