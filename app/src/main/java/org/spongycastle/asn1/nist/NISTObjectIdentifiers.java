package org.spongycastle.asn1.nist;

import com.thewingitapp.thirdparties.wingitlib.BuildConfig;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface NISTObjectIdentifiers {
    public static final ASN1ObjectIdentifier aes;
    public static final ASN1ObjectIdentifier dsa_with_sha224;
    public static final ASN1ObjectIdentifier dsa_with_sha256 = id_dsa_with_sha2.branch(BuildConfig.VERSION_NAME);
    public static final ASN1ObjectIdentifier dsa_with_sha384 = id_dsa_with_sha2.branch("3");
    public static final ASN1ObjectIdentifier dsa_with_sha512 = id_dsa_with_sha2.branch("4");
    public static final ASN1ObjectIdentifier id_aes128_CBC = aes.branch(BuildConfig.VERSION_NAME);
    public static final ASN1ObjectIdentifier id_aes128_CCM = aes.branch("7");
    public static final ASN1ObjectIdentifier id_aes128_CFB = aes.branch("4");
    public static final ASN1ObjectIdentifier id_aes128_ECB;
    public static final ASN1ObjectIdentifier id_aes128_GCM = aes.branch("6");
    public static final ASN1ObjectIdentifier id_aes128_OFB = aes.branch("3");
    public static final ASN1ObjectIdentifier id_aes128_wrap = aes.branch("5");
    public static final ASN1ObjectIdentifier id_aes192_CBC = aes.branch("22");
    public static final ASN1ObjectIdentifier id_aes192_CCM = aes.branch("27");
    public static final ASN1ObjectIdentifier id_aes192_CFB = aes.branch("24");
    public static final ASN1ObjectIdentifier id_aes192_ECB = aes.branch("21");
    public static final ASN1ObjectIdentifier id_aes192_GCM = aes.branch("26");
    public static final ASN1ObjectIdentifier id_aes192_OFB = aes.branch("23");
    public static final ASN1ObjectIdentifier id_aes192_wrap = aes.branch("25");
    public static final ASN1ObjectIdentifier id_aes256_CBC = aes.branch("42");
    public static final ASN1ObjectIdentifier id_aes256_CCM = aes.branch("47");
    public static final ASN1ObjectIdentifier id_aes256_CFB = aes.branch("44");
    public static final ASN1ObjectIdentifier id_aes256_ECB = aes.branch("41");
    public static final ASN1ObjectIdentifier id_aes256_GCM = aes.branch("46");
    public static final ASN1ObjectIdentifier id_aes256_OFB = aes.branch("43");
    public static final ASN1ObjectIdentifier id_aes256_wrap = aes.branch("45");
    public static final ASN1ObjectIdentifier id_dsa_with_sha2;
    public static final ASN1ObjectIdentifier id_sha224 = nistAlgorithm.branch("2.4");
    public static final ASN1ObjectIdentifier id_sha256;
    public static final ASN1ObjectIdentifier id_sha384 = nistAlgorithm.branch("2.2");
    public static final ASN1ObjectIdentifier id_sha512 = nistAlgorithm.branch("2.3");
    public static final ASN1ObjectIdentifier nistAlgorithm;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.16.840.1.101.3.4");
        nistAlgorithm = aSN1ObjectIdentifier;
        id_sha256 = aSN1ObjectIdentifier.branch("2.1");
        aSN1ObjectIdentifier = nistAlgorithm.branch("1");
        aes = aSN1ObjectIdentifier;
        id_aes128_ECB = aSN1ObjectIdentifier.branch("1");
        aSN1ObjectIdentifier = nistAlgorithm.branch("3");
        id_dsa_with_sha2 = aSN1ObjectIdentifier;
        dsa_with_sha224 = aSN1ObjectIdentifier.branch("1");
    }
}
