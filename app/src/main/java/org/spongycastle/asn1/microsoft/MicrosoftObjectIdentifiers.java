package org.spongycastle.asn1.microsoft;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface MicrosoftObjectIdentifiers {
    public static final ASN1ObjectIdentifier microsoft;
    public static final ASN1ObjectIdentifier microsoftAppPolicies = microsoft.branch("21.10");
    public static final ASN1ObjectIdentifier microsoftCaVersion = microsoft.branch("21.1");
    public static final ASN1ObjectIdentifier microsoftCertTemplateV1;
    public static final ASN1ObjectIdentifier microsoftCertTemplateV2 = microsoft.branch("21.7");
    public static final ASN1ObjectIdentifier microsoftPrevCaCertHash = microsoft.branch("21.2");

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.6.1.4.1.311");
        microsoft = aSN1ObjectIdentifier;
        microsoftCertTemplateV1 = aSN1ObjectIdentifier.branch("20.2");
    }
}
