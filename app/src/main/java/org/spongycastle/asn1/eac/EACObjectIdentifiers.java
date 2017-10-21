package org.spongycastle.asn1.eac;

import com.thewingitapp.thirdparties.wingitlib.BuildConfig;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface EACObjectIdentifiers {
    public static final ASN1ObjectIdentifier bsi_de;
    public static final ASN1ObjectIdentifier id_CA;
    public static final ASN1ObjectIdentifier id_CA_DH;
    public static final ASN1ObjectIdentifier id_CA_DH_3DES_CBC_CBC;
    public static final ASN1ObjectIdentifier id_CA_ECDH;
    public static final ASN1ObjectIdentifier id_CA_ECDH_3DES_CBC_CBC;
    public static final ASN1ObjectIdentifier id_EAC_ePassport = bsi_de.branch("3.1.2.1");
    public static final ASN1ObjectIdentifier id_PK;
    public static final ASN1ObjectIdentifier id_PK_DH;
    public static final ASN1ObjectIdentifier id_PK_ECDH = id_PK.branch(BuildConfig.VERSION_NAME);
    public static final ASN1ObjectIdentifier id_TA;
    public static final ASN1ObjectIdentifier id_TA_ECDSA;
    public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_1;
    public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_224 = id_TA_ECDSA.branch(BuildConfig.VERSION_NAME);
    public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_256 = id_TA_ECDSA.branch("3");
    public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_384 = id_TA_ECDSA.branch("4");
    public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_512 = id_TA_ECDSA.branch("5");
    public static final ASN1ObjectIdentifier id_TA_RSA;
    public static final ASN1ObjectIdentifier id_TA_RSA_PSS_SHA_1 = id_TA_RSA.branch("3");
    public static final ASN1ObjectIdentifier id_TA_RSA_PSS_SHA_256 = id_TA_RSA.branch("4");
    public static final ASN1ObjectIdentifier id_TA_RSA_PSS_SHA_512 = id_TA_RSA.branch("6");
    public static final ASN1ObjectIdentifier id_TA_RSA_v1_5_SHA_1;
    public static final ASN1ObjectIdentifier id_TA_RSA_v1_5_SHA_256 = id_TA_RSA.branch(BuildConfig.VERSION_NAME);
    public static final ASN1ObjectIdentifier id_TA_RSA_v1_5_SHA_512 = id_TA_RSA.branch("5");

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("0.4.0.127.0.7");
        bsi_de = aSN1ObjectIdentifier;
        aSN1ObjectIdentifier = aSN1ObjectIdentifier.branch("2.2.1");
        id_PK = aSN1ObjectIdentifier;
        id_PK_DH = aSN1ObjectIdentifier.branch("1");
        aSN1ObjectIdentifier = bsi_de.branch("2.2.3");
        id_CA = aSN1ObjectIdentifier;
        aSN1ObjectIdentifier = aSN1ObjectIdentifier.branch("1");
        id_CA_DH = aSN1ObjectIdentifier;
        id_CA_DH_3DES_CBC_CBC = aSN1ObjectIdentifier.branch("1");
        aSN1ObjectIdentifier = id_CA.branch(BuildConfig.VERSION_NAME);
        id_CA_ECDH = aSN1ObjectIdentifier;
        id_CA_ECDH_3DES_CBC_CBC = aSN1ObjectIdentifier.branch("1");
        aSN1ObjectIdentifier = bsi_de.branch("2.2.2");
        id_TA = aSN1ObjectIdentifier;
        aSN1ObjectIdentifier = aSN1ObjectIdentifier.branch("1");
        id_TA_RSA = aSN1ObjectIdentifier;
        id_TA_RSA_v1_5_SHA_1 = aSN1ObjectIdentifier.branch("1");
        aSN1ObjectIdentifier = id_TA.branch(BuildConfig.VERSION_NAME);
        id_TA_ECDSA = aSN1ObjectIdentifier;
        id_TA_ECDSA_SHA_1 = aSN1ObjectIdentifier.branch("1");
    }
}
