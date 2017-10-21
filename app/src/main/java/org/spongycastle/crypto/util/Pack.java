package org.spongycastle.crypto.util;

import org.spongycastle.crypto.tls.CipherSuite;

public abstract class Pack {
    public static int bigEndianToInt(byte[] bArr, int i) {
        byte b = bArr[i];
        int i2 = i + 1;
        byte b2 = bArr[i2];
        i2++;
        return (((b << 24) | ((b2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | ((bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | (bArr[i2 + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    public static void bigEndianToInt(byte[] bArr, int i, int[] iArr) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = bigEndianToInt(bArr, i);
            i += 4;
        }
    }

    public static long bigEndianToLong(byte[] bArr, int i) {
        return (((long) bigEndianToInt(bArr, i + 4)) & 4294967295L) | ((((long) bigEndianToInt(bArr, i)) & 4294967295L) << 32);
    }

    public static void intToBigEndian(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 24);
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 16);
        i3++;
        bArr[i3] = (byte) (i >>> 8);
        bArr[i3 + 1] = (byte) i;
    }

    public static void intToBigEndian(int[] iArr, byte[] bArr, int i) {
        for (int intToBigEndian : iArr) {
            intToBigEndian(intToBigEndian, bArr, i);
            i += 4;
        }
    }

    public static void intToLittleEndian(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 8);
        i3++;
        bArr[i3] = (byte) (i >>> 16);
        bArr[i3 + 1] = (byte) (i >>> 24);
    }

    public static void intToLittleEndian(int[] iArr, byte[] bArr, int i) {
        for (int intToLittleEndian : iArr) {
            intToLittleEndian(intToLittleEndian, bArr, i);
            i += 4;
        }
    }

    public static int littleEndianToInt(byte[] bArr, int i) {
        byte b = bArr[i];
        int i2 = i + 1;
        byte b2 = bArr[i2];
        i2++;
        return (((b & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) | ((b2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | ((bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | (bArr[i2 + 1] << 24);
    }

    public static void littleEndianToInt(byte[] bArr, int i, int[] iArr) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = littleEndianToInt(bArr, i);
            i += 4;
        }
    }

    public static long littleEndianToLong(byte[] bArr, int i) {
        return (((long) littleEndianToInt(bArr, i)) & 4294967295L) | ((((long) littleEndianToInt(bArr, i + 4)) & 4294967295L) << 32);
    }

    public static void longToBigEndian(long j, byte[] bArr, int i) {
        intToBigEndian((int) (j >>> 32), bArr, i);
        intToBigEndian((int) (4294967295L & j), bArr, i + 4);
    }

    public static void longToLittleEndian(long j, byte[] bArr, int i) {
        intToLittleEndian((int) (4294967295L & j), bArr, i);
        intToLittleEndian((int) (j >>> 32), bArr, i + 4);
    }
}
