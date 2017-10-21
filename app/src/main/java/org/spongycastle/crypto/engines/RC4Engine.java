package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.tls.CipherSuite;

public class RC4Engine implements StreamCipher {
    private static final int STATE_LENGTH = 256;
    private byte[] engineState = null;
    private byte[] workingKey = null;
    private int x = 0;
    private int y = 0;

    private void setKey(byte[] bArr) {
        int i;
        int i2 = 0;
        this.workingKey = bArr;
        this.x = 0;
        this.y = 0;
        if (this.engineState == null) {
            this.engineState = new byte[256];
        }
        for (i = 0; i < 256; i++) {
            this.engineState[i] = (byte) i;
        }
        int i3 = 0;
        for (i = 0; i < 256; i++) {
            i2 = (i2 + ((bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + this.engineState[i])) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            byte b = this.engineState[i];
            this.engineState[i] = this.engineState[i2];
            this.engineState[i2] = b;
            i3 = (i3 + 1) % bArr.length;
        }
    }

    public String getAlgorithmName() {
        return "RC4";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.workingKey = ((KeyParameter) cipherParameters).getKey();
            setKey(this.workingKey);
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to RC4 init - " + cipherParameters.getClass().getName());
    }

    public void processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            for (int i4 = 0; i4 < i2; i4++) {
                this.x = (this.x + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                this.y = (this.engineState[this.x] + this.y) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                byte b = this.engineState[this.x];
                this.engineState[this.x] = this.engineState[this.y];
                this.engineState[this.y] = b;
                bArr2[i4 + i3] = (byte) (bArr[i4 + i] ^ this.engineState[(this.engineState[this.x] + this.engineState[this.y]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV]);
            }
        }
    }

    public void reset() {
        setKey(this.workingKey);
    }

    public byte returnByte(byte b) {
        this.x = (this.x + 1) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        this.y = (this.engineState[this.x] + this.y) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        byte b2 = this.engineState[this.x];
        this.engineState[this.x] = this.engineState[this.y];
        this.engineState[this.y] = b2;
        return (byte) (this.engineState[(this.engineState[this.x] + this.engineState[this.y]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] ^ b);
    }
}
