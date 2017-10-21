package org.spongycastle.crypto.engines;

import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.tls.CipherSuite;

public class HC256Engine implements StreamCipher {
    private byte[] buf = new byte[4];
    private int cnt = 0;
    private int idx = 0;
    private boolean initialised;
    private byte[] iv;
    private byte[] key;
    private int[] p = new int[PKIFailureInfo.badRecipientNonce];
    private int[] q = new int[PKIFailureInfo.badRecipientNonce];

    private byte getByte() {
        if (this.idx == 0) {
            int step = step();
            this.buf[0] = (byte) (step & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            step >>= 8;
            this.buf[1] = (byte) (step & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            step >>= 8;
            this.buf[2] = (byte) (step & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            this.buf[3] = (byte) ((step >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        }
        byte b = this.buf[this.idx];
        this.idx = (this.idx + 1) & 3;
        return b;
    }

    private void init() {
        int i = 16;
        if (this.key.length != 32 && this.key.length != 16) {
            throw new IllegalArgumentException("The key must be 128/256 bits long");
        } else if (this.iv.length < 16) {
            throw new IllegalArgumentException("The IV must be at least 128 bits long");
        } else {
            Object obj;
            int i2;
            int i3;
            if (this.key.length != 32) {
                obj = new byte[32];
                System.arraycopy(this.key, 0, obj, 0, this.key.length);
                System.arraycopy(this.key, 0, obj, 16, this.key.length);
                this.key = obj;
            }
            if (this.iv.length < 32) {
                obj = new byte[32];
                System.arraycopy(this.iv, 0, obj, 0, this.iv.length);
                System.arraycopy(this.iv, 0, obj, this.iv.length, 32 - this.iv.length);
                this.iv = obj;
            }
            this.cnt = 0;
            Object obj2 = new int[2560];
            for (i2 = 0; i2 < 32; i2++) {
                i3 = i2 >> 2;
                obj2[i3] = obj2[i3] | ((this.key[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << ((i2 & 3) * 8));
            }
            for (i2 = 0; i2 < 32; i2++) {
                i3 = (i2 >> 2) + 8;
                obj2[i3] = obj2[i3] | ((this.iv[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << ((i2 & 3) * 8));
            }
            while (i < 2560) {
                i2 = obj2[i - 2];
                i3 = obj2[i - 15];
                obj2[i] = (((((rotateRight(i2, 19) ^ rotateRight(i2, 17)) ^ (i2 >>> 10)) + obj2[i - 7]) + ((rotateRight(i3, 18) ^ rotateRight(i3, 7)) ^ (i3 >>> 3))) + obj2[i - 16]) + i;
                i++;
            }
            System.arraycopy(obj2, 512, this.p, 0, PKIFailureInfo.badRecipientNonce);
            System.arraycopy(obj2, 1536, this.q, 0, PKIFailureInfo.badRecipientNonce);
            for (i = 0; i < PKIFailureInfo.certConfirmed; i++) {
                step();
            }
            this.cnt = 0;
        }
    }

    private static int rotateRight(int i, int i2) {
        return (i >>> i2) | (i << (-i2));
    }

    private int step() {
        int i = this.cnt & 1023;
        int i2;
        int i3;
        int[] iArr;
        int i4;
        int i5;
        if (this.cnt < PKIFailureInfo.badRecipientNonce) {
            i2 = this.p[(i - 3) & 1023];
            i3 = this.p[(i - 1023) & 1023];
            iArr = this.p;
            iArr[i] = (this.q[(i2 ^ i3) & 1023] + ((rotateRight(i2, 10) ^ rotateRight(i3, 23)) + this.p[(i - 10) & 1023])) + iArr[i];
            i2 = this.p[(i - 12) & 1023];
            i3 = this.q[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            i4 = this.q[((i2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 256];
            i5 = this.q[((i2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 512];
            i = this.p[i] ^ (this.q[((i2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 768] + ((i3 + i4) + i5));
        } else {
            i2 = this.q[(i - 3) & 1023];
            i3 = this.q[(i - 1023) & 1023];
            iArr = this.q;
            iArr[i] = (this.p[(i2 ^ i3) & 1023] + ((rotateRight(i2, 10) ^ rotateRight(i3, 23)) + this.q[(i - 10) & 1023])) + iArr[i];
            i2 = this.q[(i - 12) & 1023];
            i3 = this.p[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            i4 = this.p[((i2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 256];
            i5 = this.p[((i2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 512];
            i = this.q[i] ^ (this.p[((i2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 768] + ((i3 + i4) + i5));
        }
        this.cnt = (this.cnt + 1) & 2047;
        return i;
    }

    public String getAlgorithmName() {
        return "HC-256";
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        CipherParameters parameters;
        if (cipherParameters instanceof ParametersWithIV) {
            this.iv = ((ParametersWithIV) cipherParameters).getIV();
            parameters = ((ParametersWithIV) cipherParameters).getParameters();
        } else {
            this.iv = new byte[0];
            parameters = cipherParameters;
        }
        if (parameters instanceof KeyParameter) {
            this.key = ((KeyParameter) parameters).getKey();
            init();
            this.initialised = true;
            return;
        }
        throw new IllegalArgumentException("Invalid parameter passed to HC256 init - " + cipherParameters.getClass().getName());
    }

    public void processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
        if (!this.initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        } else if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            for (int i4 = 0; i4 < i2; i4++) {
                bArr2[i3 + i4] = (byte) (bArr[i + i4] ^ getByte());
            }
        }
    }

    public void reset() {
        this.idx = 0;
        init();
    }

    public byte returnByte(byte b) {
        return (byte) (getByte() ^ b);
    }
}
