package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.tls.CipherSuite;

public class VMPCMac implements Mac {
    private byte[] P = null;
    private byte[] T;
    private byte g;
    private byte n = (byte) 0;
    private byte s = (byte) 0;
    private byte[] workingIV;
    private byte[] workingKey;
    private byte x1;
    private byte x2;
    private byte x3;
    private byte x4;

    private void initKey(byte[] bArr, byte[] bArr2) {
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

    public int doFinal(byte[] bArr, int i) throws DataLengthException, IllegalStateException {
        int i2;
        for (i2 = 1; i2 < 25; i2++) {
            this.s = this.P[(this.s + this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.x4 = this.P[((this.x4 + this.x3) + i2) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.x3 = this.P[((this.x3 + this.x2) + i2) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.x2 = this.P[((this.x2 + this.x1) + i2) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.x1 = this.P[((this.x1 + this.s) + i2) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.T[this.g & 31] = (byte) (this.T[this.g & 31] ^ this.x1);
            this.T[(this.g + 1) & 31] = (byte) (this.T[(this.g + 1) & 31] ^ this.x2);
            this.T[(this.g + 2) & 31] = (byte) (this.T[(this.g + 2) & 31] ^ this.x3);
            this.T[(this.g + 3) & 31] = (byte) (this.T[(this.g + 3) & 31] ^ this.x4);
            this.g = (byte) ((this.g + 4) & 31);
            byte b = this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = b;
            this.n = (byte) ((this.n + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        }
        for (i2 = 0; i2 < 768; i2++) {
            this.s = this.P[((this.s + this.P[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]) + this.T[i2 & 31]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            b = this.P[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = b;
        }
        Object obj = new byte[20];
        for (i2 = 0; i2 < 20; i2++) {
            this.s = this.P[(this.s + this.P[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            obj[i2] = this.P[(this.P[this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            byte b2 = this.P[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = b2;
        }
        System.arraycopy(obj, 0, bArr, i, 20);
        reset();
        return 20;
    }

    public String getAlgorithmName() {
        return "VMPC-MAC";
    }

    public int getMacSize() {
        return 20;
    }

    public void init(CipherParameters cipherParameters) throws IllegalArgumentException {
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
            if (parametersWithIV.getParameters() instanceof KeyParameter) {
                this.workingIV = parametersWithIV.getIV();
                if (this.workingIV == null || this.workingIV.length <= 0 || this.workingIV.length > 768) {
                    throw new IllegalArgumentException("VMPC-MAC requires 1 to 768 bytes of IV");
                }
                this.workingKey = keyParameter.getKey();
                reset();
                return;
            }
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include a key");
        }
        throw new IllegalArgumentException("VMPC-MAC Init parameters must include an IV");
    }

    public void reset() {
        initKey(this.workingKey, this.workingIV);
        this.n = (byte) 0;
        this.x4 = (byte) 0;
        this.x3 = (byte) 0;
        this.x2 = (byte) 0;
        this.x1 = (byte) 0;
        this.g = (byte) 0;
        this.T = new byte[32];
        for (int i = 0; i < 32; i++) {
            this.T[i] = (byte) 0;
        }
    }

    public void update(byte b) throws IllegalStateException {
        this.s = this.P[(this.s + this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        byte b2 = (byte) (this.P[(this.P[this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] ^ b);
        this.x4 = this.P[(this.x4 + this.x3) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        this.x3 = this.P[(this.x3 + this.x2) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        this.x2 = this.P[(this.x2 + this.x1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        this.x1 = this.P[(b2 + (this.x1 + this.s)) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        this.T[this.g & 31] = (byte) (this.T[this.g & 31] ^ this.x1);
        this.T[(this.g + 1) & 31] = (byte) (this.T[(this.g + 1) & 31] ^ this.x2);
        this.T[(this.g + 2) & 31] = (byte) (this.T[(this.g + 2) & 31] ^ this.x3);
        this.T[(this.g + 3) & 31] = (byte) (this.T[(this.g + 3) & 31] ^ this.x4);
        this.g = (byte) ((this.g + 4) & 31);
        b2 = this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        this.P[this.n & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
        this.P[this.s & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] = b2;
        this.n = (byte) ((this.n + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    public void update(byte[] bArr, int i, int i2) throws DataLengthException, IllegalStateException {
        if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        for (int i3 = 0; i3 < i2; i3++) {
            update(bArr[i3]);
        }
    }
}
