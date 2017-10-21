package com.google.zxing.pdf417.decoder;

import android.support.v4.widget.AutoScrollHelper;
import com.google.zxing.pdf417.PDF417Common;
import java.lang.reflect.Array;

final class PDF417CodewordDecoder {
    private static final float[][] RATIOS_TABLE;

    static {
        int length = PDF417Common.SYMBOL_TABLE.length;
        RATIOS_TABLE = (float[][]) Array.newInstance(Float.TYPE, new int[]{length, 8});
        for (length = 0; length < PDF417Common.SYMBOL_TABLE.length; length++) {
            int i = PDF417Common.SYMBOL_TABLE[length];
            int i2 = i & 1;
            for (int i3 = 0; i3 < 8; i3++) {
                float f = 0.0f;
                while ((i & 1) == i2) {
                    f += 1.0f;
                    i >>= 1;
                }
                i2 = i & 1;
                RATIOS_TABLE[length][(8 - i3) - 1] = f / 17.0f;
            }
        }
    }

    private PDF417CodewordDecoder() {
    }

    private static int getBitValue(int[] iArr) {
        long j = 0;
        for (int i = 0; i < iArr.length; i++) {
            int i2 = 0;
            while (i2 < iArr[i]) {
                i2++;
                j = (j << 1) | ((long) (i % 2 == 0 ? 1 : 0));
            }
        }
        return (int) j;
    }

    private static int getClosestDecodedValue(int[] iArr) {
        int i;
        int bitCountSum = PDF417Common.getBitCountSum(iArr);
        float[] fArr = new float[8];
        for (i = 0; i < 8; i++) {
            fArr[i] = ((float) iArr[i]) / ((float) bitCountSum);
        }
        i = -1;
        float f = AutoScrollHelper.NO_MAX;
        for (bitCountSum = 0; bitCountSum < RATIOS_TABLE.length; bitCountSum++) {
            float f2 = 0.0f;
            int i2 = 0;
            while (i2 < 8) {
                float f3 = RATIOS_TABLE[bitCountSum][i2] - fArr[i2];
                i2++;
                f2 += f3 * f3;
            }
            if (f2 < f) {
                i = PDF417Common.SYMBOL_TABLE[bitCountSum];
                f = f2;
            }
        }
        return i;
    }

    private static int getDecodedCodewordValue(int[] iArr) {
        int bitValue = getBitValue(iArr);
        return PDF417Common.getCodeword((long) bitValue) == -1 ? -1 : bitValue;
    }

    static int getDecodedValue(int[] iArr) {
        int decodedCodewordValue = getDecodedCodewordValue(sampleBitCounts(iArr));
        return decodedCodewordValue != -1 ? decodedCodewordValue : getClosestDecodedValue(iArr);
    }

    private static int[] sampleBitCounts(int[] iArr) {
        int i = 0;
        float bitCountSum = (float) PDF417Common.getBitCountSum(iArr);
        int[] iArr2 = new int[8];
        int i2 = 0;
        for (int i3 = 0; i3 < 17; i3++) {
            if (((float) (iArr[i2] + i)) <= (bitCountSum / 34.0f) + ((((float) i3) * bitCountSum) / 17.0f)) {
                i += iArr[i2];
                i2++;
            }
            iArr2[i2] = iArr2[i2] + 1;
        }
        return iArr2;
    }
}
