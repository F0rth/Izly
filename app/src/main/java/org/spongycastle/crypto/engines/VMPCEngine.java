package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.tls.CipherSuite;

public class VMPCEngine implements StreamCipher {
    protected byte[] P = null;
    protected byte n = (byte) 0;
    protected byte s = (byte) 0;
    protected byte[] workingIV;
    protected byte[] workingKey;

    public String getAlgorithmName() {
        return "VMPC";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
            if (parametersWithIV.getParameters() instanceof KeyParameter) {
                this.workingIV = parametersWithIV.getIV();
                if (this.workingIV == null || this.workingIV.length <= 0 || this.workingIV.length > 768) {
                    throw new IllegalArgumentException("VMPC requires 1 to 768 bytes of IV");
                }
                this.workingKey = keyParameter.getKey();
                initKey(this.workingKey, this.workingIV);
                return;
            }
            throw new IllegalArgumentException("VMPC init parameters must include a key");
        }
        throw new IllegalArgumentException("VMPC init parameters must include an IV");
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
        this.n = (byte) 0;
    }

    public void processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            for (int i4 = 0; i4 < i2; i4++) {
                this.s = this.P[(this.s + this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
                byte b = this.P[(this.P[this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
                byte b2 = this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
                this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
                this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = b2;
                this.n = (byte) ((this.n + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
                bArr2[i4 + i3] = (byte) (b ^ bArr[i4 + i]);
            }
        }
    }

    public void reset() {
        initKey(this.workingKey, this.workingIV);
    }

    public byte returnByte(byte b) {
        this.s = this.P[(this.s + this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        byte b2 = this.P[(this.P[this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        byte b3 = this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = b3;
        this.n = (byte) ((this.n + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        return (byte) (b2 ^ b);
    }
}
