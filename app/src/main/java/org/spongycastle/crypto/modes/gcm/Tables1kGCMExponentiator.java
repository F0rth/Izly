package org.spongycastle.crypto.modes.gcm;

import org.spongycastle.util.Arrays;

public class Tables1kGCMExponentiator implements GCMExponentiator {
    byte[][] lookupPowX2 = new byte[64][];

    public void exponentiateX(long j, byte[] bArr) {
        Object oneAsBytes = GCMUtil.oneAsBytes();
        int i = 1;
        while (j > 0) {
            if ((1 & j) != 0) {
                GCMUtil.multiply(oneAsBytes, this.lookupPowX2[i]);
            }
            i++;
            j >>>= 1;
        }
        System.arraycopy(oneAsBytes, 0, bArr, 0, 16);
    }

    public void init(byte[] bArr) {
        this.lookupPowX2[0] = GCMUtil.oneAsBytes();
        this.lookupPowX2[1] = Arrays.clone(bArr);
        for (int i = 2; i != 64; i++) {
            byte[] clone = Arrays.clone(this.lookupPowX2[i - 1]);
            GCMUtil.multiply(clone, clone);
            this.lookupPowX2[i] = clone;
        }
    }
}
