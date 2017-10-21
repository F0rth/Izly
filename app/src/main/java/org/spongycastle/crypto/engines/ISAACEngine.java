package org.spongycastle.crypto.engines;

import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.tls.CipherSuite;

public class ISAACEngine implements StreamCipher {
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int[] engineState = null;
    private int index = 0;
    private boolean initialised = false;
    private byte[] keyStream = new byte[PKIFailureInfo.badRecipientNonce];
    private int[] results = null;
    private final int sizeL = 8;
    private final int stateArraySize = 256;
    private byte[] workingKey = null;

    private int byteToIntLittle(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return ((((bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) | (bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV)) | ((bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | (bArr[i3 + 1] << 24);
    }

    private byte[] intToByteLittle(int i) {
        byte b = (byte) i;
        byte b2 = (byte) (i >>> 8);
        byte b3 = (byte) (i >>> 16);
        return new byte[]{(byte) (i >>> 24), b3, b2, b};
    }

    private byte[] intToByteLittle(int[] iArr) {
        Object obj = new byte[(iArr.length * 4)];
        int i = 0;
        int i2 = 0;
        while (i < iArr.length) {
            System.arraycopy(intToByteLittle(iArr[i]), 0, obj, i2, 4);
            i++;
            i2 += 4;
        }
        return obj;
    }

    private void isaac() {
        int i = this.b;
        int i2 = this.c + 1;
        this.c = i2;
        this.b = i + i2;
        for (i = 0; i < 256; i++) {
            i2 = this.engineState[i];
            switch (i & 3) {
                case 0:
                    this.a ^= this.a << 13;
                    break;
                case 1:
                    this.a ^= this.a >>> 6;
                    break;
                case 2:
                    this.a ^= this.a << 2;
                    break;
                case 3:
                    this.a ^= this.a >>> 16;
                    break;
                default:
                    break;
            }
            this.a += this.engineState[(i + 128) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            int i3 = (this.engineState[(i2 >>> 2) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] + this.a) + this.b;
            this.engineState[i] = i3;
            int[] iArr = this.results;
            i2 += this.engineState[(i3 >>> 10) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            this.b = i2;
            iArr[i] = i2;
        }
    }

    private void mix(int[] iArr) {
        iArr[0] = iArr[0] ^ (iArr[1] << 11);
        iArr[3] = iArr[3] + iArr[0];
        iArr[1] = iArr[1] + iArr[2];
        iArr[1] = iArr[1] ^ (iArr[2] >>> 2);
        iArr[4] = iArr[4] + iArr[1];
        iArr[2] = iArr[2] + iArr[3];
        iArr[2] = iArr[2] ^ (iArr[3] << 8);
        iArr[5] = iArr[5] + iArr[2];
        iArr[3] = iArr[3] + iArr[4];
        iArr[3] = iArr[3] ^ (iArr[4] >>> 16);
        iArr[6] = iArr[6] + iArr[3];
        iArr[4] = iArr[4] + iArr[5];
        iArr[4] = iArr[4] ^ (iArr[5] << 10);
        iArr[7] = iArr[7] + iArr[4];
        iArr[5] = iArr[5] + iArr[6];
        iArr[5] = iArr[5] ^ (iArr[6] >>> 4);
        iArr[0] = iArr[0] + iArr[5];
        iArr[6] = iArr[6] + iArr[7];
        iArr[6] = iArr[6] ^ (iArr[7] << 8);
        iArr[1] = iArr[1] + iArr[6];
        iArr[7] = iArr[7] + iArr[0];
        iArr[7] = iArr[7] ^ (iArr[0] >>> 9);
        iArr[2] = iArr[2] + iArr[7];
        iArr[0] = iArr[0] + iArr[1];
    }

    private void setKey(byte[] bArr) {
        int i;
        this.workingKey = bArr;
        if (this.engineState == null) {
            this.engineState = new int[256];
        }
        if (this.results == null) {
            this.results = new int[256];
        }
        for (i = 0; i < 256; i++) {
            int[] iArr = this.engineState;
            this.results[i] = 0;
            iArr[i] = 0;
        }
        this.c = 0;
        this.b = 0;
        this.a = 0;
        this.index = 0;
        Object obj = new byte[(bArr.length + (bArr.length & 3))];
        System.arraycopy(bArr, 0, obj, 0, bArr.length);
        for (i = 0; i < obj.length; i += 4) {
            this.results[i >> 2] = byteToIntLittle(obj, i);
        }
        int[] iArr2 = new int[8];
        for (i = 0; i < 8; i++) {
            iArr2[i] = -1640531527;
        }
        for (i = 0; i < 4; i++) {
            mix(iArr2);
        }
        int i2 = 0;
        while (i2 < 2) {
            for (int i3 = 0; i3 < 256; i3 += 8) {
                for (int i4 = 0; i4 < 8; i4++) {
                    iArr2[i4] = (i2 <= 0 ? this.results[i3 + i4] : this.engineState[i3 + i4]) + iArr2[i4];
                }
                mix(iArr2);
                for (i = 0; i < 8; i++) {
                    this.engineState[i3 + i] = iArr2[i];
                }
            }
            i2++;
        }
        isaac();
        this.initialised = true;
    }

    public String getAlgorithmName() {
        return "ISAAC";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            setKey(((KeyParameter) cipherParameters).getKey());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to ISAAC init - " + cipherParameters.getClass().getName());
    }

    public void processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (!this.initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        } else if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            for (int i4 = 0; i4 < i2; i4++) {
                if (this.index == 0) {
                    isaac();
                    this.keyStream = intToByteLittle(this.results);
                }
                bArr2[i4 + i3] = (byte) (this.keyStream[this.index] ^ bArr[i4 + i]);
                this.index = (this.index + 1) & 1023;
            }
        }
    }

    public void reset() {
        setKey(this.workingKey);
    }

    public byte returnByte(byte b) {
        if (this.index == 0) {
            isaac();
            this.keyStream = intToByteLittle(this.results);
        }
        byte b2 = (byte) (this.keyStream[this.index] ^ b);
        this.index = (this.index + 1) & 1023;
        return b2;
    }
}
