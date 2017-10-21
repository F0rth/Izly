package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.RC2Parameters;
import org.spongycastle.crypto.signers.PSSSigner;
import org.spongycastle.crypto.tls.CipherSuite;

public class RC2Engine implements BlockCipher {
    private static final int BLOCK_SIZE = 8;
    private static byte[] piTable = new byte[]{(byte) -39, (byte) 120, (byte) -7, (byte) -60, (byte) 25, (byte) -35, (byte) -75, (byte) -19, (byte) 40, (byte) -23, (byte) -3, (byte) 121, (byte) 74, (byte) -96, (byte) -40, (byte) -99, (byte) -58, (byte) 126, (byte) 55, (byte) -125, (byte) 43, (byte) 118, (byte) 83, (byte) -114, (byte) 98, (byte) 76, (byte) 100, (byte) -120, (byte) 68, (byte) -117, (byte) -5, (byte) -94, (byte) 23, (byte) -102, (byte) 89, (byte) -11, (byte) -121, (byte) -77, (byte) 79, (byte) 19, (byte) 97, (byte) 69, (byte) 109, (byte) -115, (byte) 9, (byte) -127, (byte) 125, (byte) 50, (byte) -67, (byte) -113, (byte) 64, (byte) -21, (byte) -122, (byte) -73, (byte) 123, (byte) 11, (byte) -16, (byte) -107, (byte) 33, (byte) 34, (byte) 92, (byte) 107, (byte) 78, (byte) -126, (byte) 84, (byte) -42, (byte) 101, (byte) -109, (byte) -50, (byte) 96, (byte) -78, (byte) 28, (byte) 115, (byte) 86, (byte) -64, (byte) 20, (byte) -89, (byte) -116, (byte) -15, (byte) -36, (byte) 18, (byte) 117, (byte) -54, (byte) 31, (byte) 59, (byte) -66, (byte) -28, (byte) -47, (byte) 66, (byte) 61, (byte) -44, (byte) 48, (byte) -93, (byte) 60, (byte) -74, (byte) 38, (byte) 111, (byte) -65, (byte) 14, (byte) -38, (byte) 70, (byte) 105, (byte) 7, (byte) 87, (byte) 39, (byte) -14, (byte) 29, (byte) -101, PSSSigner.TRAILER_IMPLICIT, (byte) -108, (byte) 67, (byte) 3, (byte) -8, (byte) 17, (byte) -57, (byte) -10, (byte) -112, (byte) -17, (byte) 62, (byte) -25, (byte) 6, (byte) -61, (byte) -43, (byte) 47, (byte) -56, (byte) 102, (byte) 30, (byte) -41, (byte) 8, (byte) -24, (byte) -22, (byte) -34, Byte.MIN_VALUE, (byte) 82, (byte) -18, (byte) -9, (byte) -124, (byte) -86, (byte) 114, (byte) -84, (byte) 53, (byte) 77, (byte) 106, (byte) 42, (byte) -106, (byte) 26, (byte) -46, (byte) 113, (byte) 90, (byte) 21, (byte) 73, (byte) 116, (byte) 75, (byte) -97, (byte) -48, (byte) 94, (byte) 4, (byte) 24, (byte) -92, (byte) -20, (byte) -62, (byte) -32, (byte) 65, (byte) 110, (byte) 15, (byte) 81, (byte) -53, (byte) -52, (byte) 36, (byte) -111, (byte) -81, (byte) 80, (byte) -95, (byte) -12, (byte) 112, (byte) 57, (byte) -103, (byte) 124, (byte) 58, (byte) -123, (byte) 35, (byte) -72, (byte) -76, (byte) 122, (byte) -4, (byte) 2, (byte) 54, (byte) 91, (byte) 37, (byte) 85, (byte) -105, (byte) 49, (byte) 45, (byte) 93, (byte) -6, (byte) -104, (byte) -29, (byte) -118, (byte) -110, (byte) -82, (byte) 5, (byte) -33, (byte) 41, Tnaf.POW_2_WIDTH, (byte) 103, (byte) 108, (byte) -70, (byte) -55, (byte) -45, (byte) 0, (byte) -26, (byte) -49, (byte) -31, (byte) -98, (byte) -88, (byte) 44, (byte) 99, (byte) 22, (byte) 1, (byte) 63, (byte) 88, (byte) -30, (byte) -119, (byte) -87, (byte) 13, (byte) 56, (byte) 52, (byte) 27, (byte) -85, (byte) 51, (byte) -1, (byte) -80, (byte) -69, (byte) 72, (byte) 12, (byte) 95, (byte) -71, (byte) -79, (byte) -51, (byte) 46, (byte) -59, (byte) -13, (byte) -37, (byte) 71, (byte) -27, (byte) -91, (byte) -100, (byte) 119, (byte) 10, (byte) -90, (byte) 32, (byte) 104, (byte) -2, Byte.MAX_VALUE, (byte) -63, (byte) -83};
    private boolean encrypting;
    private int[] workingKey;

    private void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3;
        int i4 = ((bArr[i + 7] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) + (bArr[i + 6] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        int i5 = ((bArr[i + 5] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) + (bArr[i + 4] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        int i6 = ((bArr[i + 3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) + (bArr[i + 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        int i7 = (bArr[i + 0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + ((bArr[i + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8);
        for (i3 = 60; i3 >= 44; i3 -= 4) {
            i4 = rotateWordLeft(i4, 11) - ((((i5 ^ -1) & i7) + (i6 & i5)) + this.workingKey[i3 + 3]);
            i5 = rotateWordLeft(i5, 13) - ((((i6 ^ -1) & i4) + (i7 & i6)) + this.workingKey[i3 + 2]);
            i6 = rotateWordLeft(i6, 14) - ((((i7 ^ -1) & i5) + (i4 & i7)) + this.workingKey[i3 + 1]);
            i7 = rotateWordLeft(i7, 15) - ((((i4 ^ -1) & i6) + (i5 & i4)) + this.workingKey[i3]);
        }
        i4 -= this.workingKey[i5 & 63];
        i5 -= this.workingKey[i6 & 63];
        i6 -= this.workingKey[i7 & 63];
        i7 -= this.workingKey[i4 & 63];
        for (i3 = 40; i3 >= 20; i3 -= 4) {
            i4 = rotateWordLeft(i4, 11) - ((((i5 ^ -1) & i7) + (i6 & i5)) + this.workingKey[i3 + 3]);
            i5 = rotateWordLeft(i5, 13) - ((((i6 ^ -1) & i4) + (i7 & i6)) + this.workingKey[i3 + 2]);
            i6 = rotateWordLeft(i6, 14) - ((((i7 ^ -1) & i5) + (i4 & i7)) + this.workingKey[i3 + 1]);
            i7 = rotateWordLeft(i7, 15) - ((((i4 ^ -1) & i6) + (i5 & i4)) + this.workingKey[i3]);
        }
        i4 -= this.workingKey[i5 & 63];
        i5 -= this.workingKey[i6 & 63];
        i6 -= this.workingKey[i7 & 63];
        i3 = i7 - this.workingKey[i4 & 63];
        for (i7 = 16; i7 >= 0; i7 -= 4) {
            i4 = rotateWordLeft(i4, 11) - ((((i5 ^ -1) & i3) + (i6 & i5)) + this.workingKey[i7 + 3]);
            i5 = rotateWordLeft(i5, 13) - ((((i6 ^ -1) & i4) + (i3 & i6)) + this.workingKey[i7 + 2]);
            i6 = rotateWordLeft(i6, 14) - ((((i3 ^ -1) & i5) + (i4 & i3)) + this.workingKey[i7 + 1]);
            i3 = rotateWordLeft(i3, 15) - ((((i4 ^ -1) & i6) + (i5 & i4)) + this.workingKey[i7]);
        }
        bArr2[i2 + 0] = (byte) i3;
        bArr2[i2 + 1] = (byte) (i3 >> 8);
        bArr2[i2 + 2] = (byte) i6;
        bArr2[i2 + 3] = (byte) (i6 >> 8);
        bArr2[i2 + 4] = (byte) i5;
        bArr2[i2 + 5] = (byte) (i5 >> 8);
        bArr2[i2 + 6] = (byte) i4;
        bArr2[i2 + 7] = (byte) (i4 >> 8);
    }

    private void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3;
        int i4 = ((bArr[i + 7] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) + (bArr[i + 6] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        int i5 = (bArr[i + 4] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + ((bArr[i + 5] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8);
        int i6 = (bArr[i + 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + ((bArr[i + 3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8);
        int i7 = ((bArr[i + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) + (bArr[i + 0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        for (i3 = 0; i3 <= 16; i3 += 4) {
            i7 = rotateWordLeft(((i7 + ((i4 ^ -1) & i6)) + (i5 & i4)) + this.workingKey[i3], 1);
            i6 = rotateWordLeft(((i6 + ((i7 ^ -1) & i5)) + (i4 & i7)) + this.workingKey[i3 + 1], 2);
            i5 = rotateWordLeft(((i5 + ((i6 ^ -1) & i4)) + (i7 & i6)) + this.workingKey[i3 + 2], 3);
            i4 = rotateWordLeft(((i4 + ((i5 ^ -1) & i7)) + (i6 & i5)) + this.workingKey[i3 + 3], 5);
        }
        i7 += this.workingKey[i4 & 63];
        i6 += this.workingKey[i7 & 63];
        i5 += this.workingKey[i6 & 63];
        i4 += this.workingKey[i5 & 63];
        for (i3 = 20; i3 <= 40; i3 += 4) {
            i7 = rotateWordLeft(((i7 + ((i4 ^ -1) & i6)) + (i5 & i4)) + this.workingKey[i3], 1);
            i6 = rotateWordLeft(((i6 + ((i7 ^ -1) & i5)) + (i4 & i7)) + this.workingKey[i3 + 1], 2);
            i5 = rotateWordLeft(((i5 + ((i6 ^ -1) & i4)) + (i7 & i6)) + this.workingKey[i3 + 2], 3);
            i4 = rotateWordLeft(((i4 + ((i5 ^ -1) & i7)) + (i6 & i5)) + this.workingKey[i3 + 3], 5);
        }
        i7 += this.workingKey[i4 & 63];
        i6 += this.workingKey[i7 & 63];
        i3 = this.workingKey[i6 & 63] + i5;
        i5 = this.workingKey[i3 & 63] + i4;
        for (i4 = 44; i4 < 64; i4 += 4) {
            i7 = rotateWordLeft(((i7 + ((i5 ^ -1) & i6)) + (i3 & i5)) + this.workingKey[i4], 1);
            i6 = rotateWordLeft(((i6 + ((i7 ^ -1) & i3)) + (i5 & i7)) + this.workingKey[i4 + 1], 2);
            i3 = rotateWordLeft(((i3 + ((i6 ^ -1) & i5)) + (i7 & i6)) + this.workingKey[i4 + 2], 3);
            i5 = rotateWordLeft(((i5 + ((i3 ^ -1) & i7)) + (i6 & i3)) + this.workingKey[i4 + 3], 5);
        }
        bArr2[i2 + 0] = (byte) i7;
        bArr2[i2 + 1] = (byte) (i7 >> 8);
        bArr2[i2 + 2] = (byte) i6;
        bArr2[i2 + 3] = (byte) (i6 >> 8);
        bArr2[i2 + 4] = (byte) i3;
        bArr2[i2 + 5] = (byte) (i3 >> 8);
        bArr2[i2 + 6] = (byte) i5;
        bArr2[i2 + 7] = (byte) (i5 >> 8);
    }

    private int[] generateWorkingKey(byte[] bArr, int i) {
        int i2;
        int i3;
        int i4 = 0;
        int[] iArr = new int[128];
        for (i2 = 0; i2 != bArr.length; i2++) {
            iArr[i2] = bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        }
        int length = bArr.length;
        if (length < 128) {
            i2 = iArr[length - 1];
            int i5 = 0;
            while (true) {
                i2 = piTable[(i2 + iArr[i5]) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                i3 = length + 1;
                iArr[length] = i2;
                if (i3 >= 128) {
                    break;
                }
                i5++;
                length = i3;
            }
        }
        i3 = (i + 7) >> 3;
        i2 = piTable[iArr[128 - i3] & (CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV >> ((-i) & 7))] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        iArr[128 - i3] = i2;
        for (length = (128 - i3) - 1; length >= 0; length--) {
            i2 = piTable[i2 ^ iArr[length + i3]] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            iArr[length] = i2;
        }
        int[] iArr2 = new int[64];
        while (i4 != 64) {
            iArr2[i4] = iArr[i4 * 2] + (iArr[(i4 * 2) + 1] << 8);
            i4++;
        }
        return iArr2;
    }

    private int rotateWordLeft(int i, int i2) {
        int i3 = 65535 & i;
        return (i3 << i2) | (i3 >> (16 - i2));
    }

    public String getAlgorithmName() {
        return "RC2";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.encrypting = z;
        if (cipherParameters instanceof RC2Parameters) {
            RC2Parameters rC2Parameters = (RC2Parameters) cipherParameters;
            this.workingKey = generateWorkingKey(rC2Parameters.getKey(), rC2Parameters.getEffectiveKeyBits());
        } else if (cipherParameters instanceof KeyParameter) {
            byte[] key = ((KeyParameter) cipherParameters).getKey();
            this.workingKey = generateWorkingKey(key, key.length * 8);
        } else {
            throw new IllegalArgumentException("invalid parameter passed to RC2 init - " + cipherParameters.getClass().getName());
        }
    }

    public final int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new IllegalStateException("RC2 engine not initialised");
        } else if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 8 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            if (this.encrypting) {
                encryptBlock(bArr, i, bArr2, i2);
            } else {
                decryptBlock(bArr, i, bArr2, i2);
            }
            return 8;
        }
    }

    public void reset() {
    }
}
