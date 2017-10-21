package org.spongycastle.asn1.icao;

import com.thewingitapp.thirdparties.wingitlib.BuildConfig;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface ICAOObjectIdentifiers {
    public static final ASN1ObjectIdentifier id_icao;
    public static final ASN1ObjectIdentifier id_icao_aaProtocolObject = id_icao_mrtd_security.branch("5");
    public static final ASN1ObjectIdentifier id_icao_cscaMasterList = id_icao_mrtd_security.branch(BuildConfig.VERSION_NAME);
    public static final ASN1ObjectIdentifier id_icao_cscaMasterListSigningKey = id_icao_mrtd_security.branch("3");
    public static final ASN1ObjectIdentifier id_icao_documentTypeList = id_icao_mrtd_security.branch("4");
    public static final ASN1ObjectIdentifier id_icao_extensions;
    public static final ASN1ObjectIdentifier id_icao_extensions_namechangekeyrollover;
    public static final ASN1ObjectIdentifier id_icao_ldsSecurityObject;
    public static final ASN1ObjectIdentifier id_icao_mrtd;
    public static final ASN1ObjectIdentifier id_icao_mrtd_security;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.23.136");
        id_icao = aSN1ObjectIdentifier;
        aSN1ObjectIdentifier = aSN1ObjectIdentifier.branch("1");
        id_icao_mrtd = aSN1ObjectIdentifier;
        aSN1ObjectIdentifier = aSN1ObjectIdentifier.branch("1");
        id_icao_mrtd_security = aSN1ObjectIdentifier;
        id_icao_ldsSecurityObject = aSN1ObjectIdentifier.branch("1");
        aSN1ObjectIdentifier = id_icao_mrtd_security.branch("6");
        id_icao_extensions = aSN1ObjectIdentifier;
        id_icao_extensions_namechangekeyrollover = aSN1ObjectIdentifier.branch("1");
    }
}
