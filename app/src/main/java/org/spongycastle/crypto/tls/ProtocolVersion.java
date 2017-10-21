package org.spongycastle.crypto.tls;

import java.io.IOException;

public class ProtocolVersion {
    public static final ProtocolVersion SSLv3 = new ProtocolVersion(768);
    public static final ProtocolVersion TLSv10 = new ProtocolVersion(769);
    public static final ProtocolVersion TLSv11 = new ProtocolVersion(770);
    public static final ProtocolVersion TLSv12 = new ProtocolVersion(771);
    private int version;

    private ProtocolVersion(int i) {
        this.version = 65535 & i;
    }

    public static ProtocolVersion get(int i, int i2) throws IOException {
        switch (i) {
            case 3:
                switch (i2) {
                    case 0:
                        return SSLv3;
                    case 1:
                        return TLSv10;
                    case 2:
                        return TLSv11;
                    case 3:
                        return TLSv12;
                    default:
                        break;
                }
        }
        throw new TlsFatalAlert((short) 47);
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public int getFullVersion() {
        return this.version;
    }

    public int getMajorVersion() {
        return this.version >> 8;
    }

    public int getMinorVersion() {
        return this.version & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
    }

    public int hashCode() {
        return this.version;
    }
}
