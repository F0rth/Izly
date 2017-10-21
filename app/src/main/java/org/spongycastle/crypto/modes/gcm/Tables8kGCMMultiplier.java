package org.spongycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.spongycastle.crypto.util.Pack;

public class Tables8kGCMMultiplier implements GCMMultiplier {
    private final int[][][] M = ((int[][][]) Array.newInstance(int[].class, new int[]{32, 16}));

    public void init(byte[] bArr) {
        int i;
        this.M[0][0] = new int[4];
        this.M[1][0] = new int[4];
        this.M[1][8] = GCMUtil.asInts(bArr);
        for (i = 4; i > 0; i >>= 1) {
            Object obj = new int[4];
            System.arraycopy(this.M[1][i + i], 0, obj, 0, 4);
            GCMUtil.multiplyP(obj);
            this.M[1][i] = obj;
        }
        Object obj2 = new int[4];
        System.arraycopy(this.M[1][1], 0, obj2, 0, 4);
        GCMUtil.multiplyP(obj2);
        this.M[0][8] = obj2;
        for (i = 4; i > 0; i >>= 1) {
            obj = new int[4];
            System.arraycopy(this.M[0][i + i], 0, obj, 0, 4);
            GCMUtil.multiplyP(obj);
            this.M[0][i] = obj;
        }
        i = 0;
        while (true) {
            int i2;
            for (int i3 = 2; i3 < 16; i3 += i3) {
                for (i2 = 1; i2 < i3; i2++) {
                    int[] iArr = new int[4];
                    System.arraycopy(this.M[i][i3], 0, iArr, 0, 4);
                    GCMUtil.xor(iArr, this.M[i][i2]);
                    this.M[i][i3 + i2] = iArr;
                }
            }
            i++;
            if (i != 32) {
                if (i > 1) {
                    this.M[i][0] = new int[4];
                    for (i2 = 8; i2 > 0; i2 >>= 1) {
                        Object obj3 = new int[4];
                        System.arraycopy(this.M[i - 2][i2], 0, obj3, 0, 4);
                        GCMUtil.multiplyP8(obj3);
                        this.M[i][i2] = obj3;
                    }
                }
            } else {
                return;
            }
        }
    }

    public void multiplyH(byte[] bArr) {
        int[] iArr = new int[4];
        for (int i = 15; i >= 0; i--) {
            int[] iArr2 = this.M[i + i][bArr[i] & 15];
            iArr[0] = iArr[0] ^ iArr2[0];
            iArr[1] = iArr[1] ^ iArr2[1];
            iArr[2] = iArr[2] ^ iArr2[2];
            iArr[3] = iArr2[3] ^ iArr[3];
            iArr2 = this.M[(i + i) + 1][(bArr[i] & 240) >>> 4];
            iArr[0] = iArr[0] ^ iArr2[0];
            iArr[1] = iArr[1] ^ iArr2[1];
            iArr[2] = iArr[2] ^ iArr2[2];
            iArr[3] = iArr2[3] ^ iArr[3];
        }
        Pack.intToBigEndian(iArr, bArr, 0);
    }
}
