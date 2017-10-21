package org.spongycastle.asn1.x509.qualified;

import com.thewingitapp.thirdparties.wingitlib.BuildConfig;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface ETSIQCObjectIdentifiers {
    public static final ASN1ObjectIdentifier id_etsi_qcs;
    public static final ASN1ObjectIdentifier id_etsi_qcs_LimiteValue = id_etsi_qcs.branch(BuildConfig.VERSION_NAME);
    public static final ASN1ObjectIdentifier id_etsi_qcs_QcCompliance;
    public static final ASN1ObjectIdentifier id_etsi_qcs_QcSSCD = id_etsi_qcs.branch("4");
    public static final ASN1ObjectIdentifier id_etsi_qcs_RetentionPeriod = id_etsi_qcs.branch("3");

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("0.4.0.1862.1");
        id_etsi_qcs = aSN1ObjectIdentifier;
        id_etsi_qcs_QcCompliance = aSN1ObjectIdentifier.branch("1");
    }
}
