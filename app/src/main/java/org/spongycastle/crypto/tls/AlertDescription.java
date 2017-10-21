package org.spongycastle.crypto.tls;

public class AlertDescription {
    public static final short access_denied = (short) 49;
    public static final short bad_certificate = (short) 42;
    public static final short bad_certificate_hash_value = (short) 114;
    public static final short bad_certificate_status_response = (short) 113;
    public static final short bad_record_mac = (short) 20;
    public static final short certificate_expired = (short) 45;
    public static final short certificate_revoked = (short) 44;
    public static final short certificate_unknown = (short) 46;
    public static final short certificate_unobtainable = (short) 111;
    public static final short close_notify = (short) 0;
    public static final short decode_error = (short) 50;
    public static final short decompression_failure = (short) 30;
    public static final short decrypt_error = (short) 51;
    public static final short decryption_failed = (short) 21;
    public static final short export_restriction = (short) 60;
    public static final short handshake_failure = (short) 40;
    public static final short illegal_parameter = (short) 47;
    public static final short insufficient_security = (short) 71;
    public static final short internal_error = (short) 80;
    public static final short no_certificate = (short) 41;
    public static final short no_renegotiation = (short) 100;
    public static final short protocol_version = (short) 70;
    public static final short record_overflow = (short) 22;
    public static final short unexpected_message = (short) 10;
    public static final short unknown_ca = (short) 48;
    public static final short unknown_psk_identity = (short) 115;
    public static final short unrecognized_name = (short) 112;
    public static final short unsupported_certificate = (short) 43;
    public static final short unsupported_extension = (short) 110;
    public static final short user_canceled = (short) 90;
}
