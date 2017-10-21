package com.ezeeworld.b4s.android.sdk.p2pmessaging;

import com.ezeeworld.b4s.android.sdk.B4SLog;

import java.util.Arrays;

public final class Xtea {
    private final byte[] a;
    private final byte[] b;

    public Xtea(byte[] bArr, byte[] bArr2) {
        this.a = Arrays.copyOf(bArr, ((bArr.length + 3) / 4) * 4);
        this.b = Arrays.copyOf(bArr2, 4);
    }

    private static int a(int i, byte[] bArr) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return ((((bArr[i2] & 255) << 16) | ((bArr[i] & 255) << 24)) | ((bArr[i3] & 255) << 8)) | (bArr[i3 + 1] & 255);
    }

    private static void a(int i, int i2, byte[] bArr) {
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i >> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i >> 16);
        bArr[i4] = (byte) (i >> 8);
        bArr[i4 + 1] = (byte) i;
    }

    private void a(int[] iArr) {
        long j = -957401312;
        for (int i = 0; i < 32; i++) {
            iArr[1] = (int) (((long) iArr[1]) - ((((long) this.b[(int) ((6451 & j) >>> 11)]) + j) ^ ((long) (iArr[0] + ((iArr[0] << 4) ^ (iArr[0] >>> 5))))));
            j += 1640531527;
            iArr[0] = (int) (((long) iArr[0]) - (((long) (((iArr[1] << 4) ^ (iArr[1] >>> 5)) + iArr[1])) ^ (((long) this.b[(int) (3 & j)]) + j)));
        }
    }

    private void b(int[] iArr) {
        long j = 0;
        for (int i = 0; i < 32; i++) {
            iArr[0] = (int) (((long) iArr[0]) + (((long) (((iArr[1] << 4) ^ (iArr[1] >>> 5)) + iArr[1])) ^ (((long) this.b[(int) (3 & j)]) + j)));
            j -= 1640531527;
            iArr[1] = (int) (((long) iArr[1]) + ((((long) this.b[(int) ((6451 & j) >>> 11)]) + j) ^ ((long) (iArr[0] + ((iArr[0] << 4) ^ (iArr[0] >>> 5))))));
        }
    }

    public final byte[] decrypt() {
        try {
            int length = this.a.length / 8;
            int[] iArr = new int[2];
            for (int i = 0; i < length; i++) {
                iArr[0] = a(i * 8, this.a);
                iArr[1] = a((i * 8) + 4, this.a);
                a(iArr);
                a(iArr[0], i * 8, this.a);
                a(iArr[1], (i * 8) + 4, this.a);
            }
            return this.a;
        } catch (Exception e) {
            B4SLog.e("MessagingEncryption", "Cannot decrypt: " + e.toString());
            return new byte[0];
        }
    }

    public final byte[] encrypt() {
        try {
            int length = this.a.length / 8;
            int[] iArr = new int[2];
            for (int i = 0; i < length; i++) {
                iArr[0] = a(i * 8, this.a);
                iArr[1] = a((i * 8) + 4, this.a);
                b(iArr);
                a(iArr[0], i * 8, this.a);
                a(iArr[1], (i * 8) + 4, this.a);
            }
            return this.a;
        } catch (Exception e) {
            B4SLog.e("MessagingEncryption", "Cannot encrypt: " + e.toString());
            return new byte[0];
        }
    }
}
