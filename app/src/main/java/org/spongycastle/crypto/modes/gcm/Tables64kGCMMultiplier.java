package org.spongycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.crypto.util.Pack;

public class Tables64kGCMMultiplier implements GCMMultiplier {
    private final int[][][] M = ((int[][][]) Array.newInstance(int[].class, new int[]{16, 256}));

    public void init(byte[] bArr) {
        int i;
        this.M[0][0] = new int[4];
        this.M[0][128] = GCMUtil.asInts(bArr);
        for (i = 64; i > 0; i >>= 1) {
            Object obj = new int[4];
            System.arraycopy(this.M[0][i + i], 0, obj, 0, 4);
            GCMUtil.multiplyP(obj);
            this.M[0][i] = obj;
        }
        i = 0;
        while (true) {
            for (int i2 = 2; i2 < 256; i2 += i2) {
                int i3;
                for (i3 = 1; i3 < i2; i3++) {
                    int[] iArr = new int[4];
                    System.arraycopy(this.M[i][i2], 0, iArr, 0, 4);
                    GCMUtil.xor(iArr, this.M[i][i3]);
                    this.M[i][i2 + i3] = iArr;
                }
            }
            i++;
            if (i != 16) {
                this.M[i][0] = new int[4];
                for (i3 = 128; i3 > 0; i3 >>= 1) {
                    Object obj2 = new int[4];
                    System.arraycopy(this.M[i - 1][i3], 0, obj2, 0, 4);
                    GCMUtil.multiplyP8(obj2);
                    this.M[i][i3] = obj2;
                }
            } else {
                return;
            }
        }
    }

    public void multiplyH(byte[] bArr) {
        int[] iArr = new int[4];
        for (int i = 15; i >= 0; i--) {
            int[] iArr2 = this.M[i][bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV];
            iArr[0] = iArr[0] ^ iArr2[0];
            iArr[1] = iArr[1] ^ iArr2[1];
            iArr[2] = iArr[2] ^ iArr2[2];
            iArr[3] = iArr2[3] ^ iArr[3];
        }
        Pack.intToBigEndian(iArr, bArr, 0);
    }
}
