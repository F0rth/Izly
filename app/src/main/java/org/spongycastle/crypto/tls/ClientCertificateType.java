package org.spongycastle.crypto.tls;

public class ClientCertificateType {
    public static final short dss_fixed_dh = (short) 4;
    public static final short dss_sign = (short) 2;
    public static final short ecdsa_fixed_ecdh = (short) 66;
    public static final short ecdsa_sign = (short) 64;
    public static final short rsa_fixed_dh = (short) 3;
    public static final short rsa_fixed_ecdh = (short) 65;
    public static final short rsa_sign = (short) 1;
}
