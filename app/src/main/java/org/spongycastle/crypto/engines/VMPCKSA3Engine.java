package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.tls.CipherSuite;

public class VMPCKSA3Engine extends VMPCEngine {
    public String getAlgorithmName() {
        return "VMPC-KSA3";
    }

    protected void initKey(byte[] bArr, byte[] bArr2) {
        int i;
        this.s = (byte) 0;
        this.P = new byte[256];
        for (i = 0; i < 256; i++) {
            this.P[i] = (byte) i;
        }
        for (i = 0; i < 768; i++) {
            this.s = this.P[((this.s + this.P[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]) + bArr[i % bArr.length]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            byte b = this.P[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = b;
        }
        for (i = 0; i < 768; i++) {
            this.s = this.P[((this.s + this.P[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]) + bArr2[i % bArr2.length]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            b = this.P[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = b;
        }
        for (i = 0; i < 768; i++) {
            this.s = this.P[((this.s + this.P[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]) + bArr[i % bArr.length]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            b = this.P[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = b;
        }
        this.n = (byte) 0;
    }
}
