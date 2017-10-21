package org.spongycastle.asn1.crmf;

import com.thewingitapp.thirdparties.wingitlib.BuildConfig;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public interface CRMFObjectIdentifiers {
    public static final ASN1ObjectIdentifier id_ct_encKeyWithID = new ASN1ObjectIdentifier(PKCSObjectIdentifiers.id_ct + ".21");
    public static final ASN1ObjectIdentifier id_pkip;
    public static final ASN1ObjectIdentifier id_pkix;
    public static final ASN1ObjectIdentifier id_regCtrl;
    public static final ASN1ObjectIdentifier id_regCtrl_authenticator = id_regCtrl.branch(BuildConfig.VERSION_NAME);
    public static final ASN1ObjectIdentifier id_regCtrl_pkiArchiveOptions = id_regCtrl.branch("4");
    public static final ASN1ObjectIdentifier id_regCtrl_pkiPublicationInfo = id_regCtrl.branch("3");
    public static final ASN1ObjectIdentifier id_regCtrl_regToken;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.6.1.5.5.7");
        id_pkix = aSN1ObjectIdentifier;
        aSN1ObjectIdentifier = aSN1ObjectIdentifier.branch("5");
        id_pkip = aSN1ObjectIdentifier;
        aSN1ObjectIdentifier = aSN1ObjectIdentifier.branch("1");
        id_regCtrl = aSN1ObjectIdentifier;
        id_regCtrl_regToken = aSN1ObjectIdentifier.branch("1");
    }
}
