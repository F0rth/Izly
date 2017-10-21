package org.spongycastle.asn1.misc;

import com.thewingitapp.thirdparties.wingitlib.BuildConfig;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface MiscObjectIdentifiers {
    public static final ASN1ObjectIdentifier entrust;
    public static final ASN1ObjectIdentifier entrustVersionExtension;
    public static final ASN1ObjectIdentifier netscape;
    public static final ASN1ObjectIdentifier netscapeBaseURL = netscape.branch(BuildConfig.VERSION_NAME);
    public static final ASN1ObjectIdentifier netscapeCARevocationURL = netscape.branch("4");
    public static final ASN1ObjectIdentifier netscapeCApolicyURL = netscape.branch("8");
    public static final ASN1ObjectIdentifier netscapeCertComment = netscape.branch("13");
    public static final ASN1ObjectIdentifier netscapeCertType;
    public static final ASN1ObjectIdentifier netscapeRenewalURL = netscape.branch("7");
    public static final ASN1ObjectIdentifier netscapeRevocationURL = netscape.branch("3");
    public static final ASN1ObjectIdentifier netscapeSSLServerName = netscape.branch("12");
    public static final ASN1ObjectIdentifier novell;
    public static final ASN1ObjectIdentifier novellSecurityAttribs;
    public static final ASN1ObjectIdentifier verisign;
    public static final ASN1ObjectIdentifier verisignCzagExtension;
    public static final ASN1ObjectIdentifier verisignDnbDunsNumber = verisign.branch("6.15");

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.16.840.1.113730.1");
        netscape = aSN1ObjectIdentifier;
        netscapeCertType = aSN1ObjectIdentifier.branch("1");
        aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.16.840.1.113733.1");
        verisign = aSN1ObjectIdentifier;
        verisignCzagExtension = aSN1ObjectIdentifier.branch("6.3");
        aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.16.840.1.113719");
        novell = aSN1ObjectIdentifier;
        novellSecurityAttribs = aSN1ObjectIdentifier.branch("1.9.4.1");
        aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.2.840.113533.7");
        entrust = aSN1ObjectIdentifier;
        entrustVersionExtension = aSN1ObjectIdentifier.branch("65.0");
    }
}
