package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int[][] calculateBlackPoints(byte[] bArr, int i, int i2, int i3, int i4) {
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i2, i});
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i5 << 3;
            int i7 = i4 - 8;
            if (i6 <= i7) {
                i7 = i6;
            }
            int i8 = 0;
            while (i8 < i) {
                int i9 = i8 << 3;
                i6 = i3 - 8;
                if (i9 <= i6) {
                    i6 = i9;
                }
                int i10 = 0;
                int i11 = 255;
                i9 = 0;
                int i12 = 0;
                int i13 = (i7 * i3) + i6;
                while (i12 < 8) {
                    int i14 = 0;
                    i6 = i9;
                    while (i14 < 8) {
                        i9 = bArr[i13 + i14] & 255;
                        if (i9 < i11) {
                            i11 = i9;
                        }
                        if (i9 > i6) {
                            i6 = i9;
                        }
                        i14++;
                        i10 += i9;
                    }
                    if (i6 - i11 > 24) {
                        i9 = i12 + 1;
                        i12 = i10;
                        int i15 = i13 + i3;
                        i13 = i9;
                        i9 = i15;
                        while (i13 < 8) {
                            i10 = i12;
                            for (i12 = 0; i12 < 8; i12++) {
                                i10 += bArr[i9 + i12] & 255;
                            }
                            i13++;
                            i9 += i3;
                            i12 = i10;
                        }
                    } else {
                        i9 = i13;
                        i13 = i12;
                        i12 = i10;
                    }
                    i10 = i9 + i3;
                    i9 = i13 + 1;
                    i13 = i10;
                    i10 = i12;
                    i12 = i9;
                    i9 = i6;
                }
                i6 = i10 >> 6;
                if (i9 - i11 <= 24) {
                    if (i5 > 0 && i8 > 0) {
                        i6 = ((iArr[i5 - 1][i8] + (iArr[i5][i8 - 1] * 2)) + iArr[i5 - 1][i8 - 1]) >> 2;
                        if (i11 < i6) {
                        }
                    }
                    i6 = i11 >> 1;
                }
                iArr[i5][i8] = i6;
                i8++;
            }
        }
        return iArr;
    }

    private static void calculateThresholdForBlock(byte[] bArr, int i, int i2, int i3, int i4, int[][] iArr, BitMatrix bitMatrix) {
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i5 << 3;
            int i7 = i4 - 8;
            if (i6 <= i7) {
                i7 = i6;
            }
            for (int i8 = 0; i8 < i; i8++) {
                i6 = i8 << 3;
                int i9 = i3 - 8;
                if (i6 <= i9) {
                    i9 = i6;
                }
                int cap = cap(i8, 2, i - 3);
                int cap2 = cap(i5, 2, i2 - 3);
                i6 = 0;
                for (int i10 = -2; i10 <= 2; i10++) {
                    int[] iArr2 = iArr[cap2 + i10];
                    int i11 = iArr2[cap - 2];
                    int i12 = iArr2[cap - 1];
                    int i13 = iArr2[cap];
                    i6 += iArr2[cap + 2] + (((i11 + i12) + i13) + iArr2[cap + 1]);
                }
                thresholdBlock(bArr, i9, i7, i6 / 25, i3, bitMatrix);
            }
        }
    }

    private static int cap(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    private static void thresholdBlock(byte[] bArr, int i, int i2, int i3, int i4, BitMatrix bitMatrix) {
        int i5 = (i2 * i4) + i;
        for (int i6 = 0; i6 < 8; i6++) {
            for (int i7 = 0; i7 < 8; i7++) {
                if ((bArr[i5 + i7] & 255) <= i3) {
                    bitMatrix.set(i + i7, i2 + i6);
                }
            }
            i5 += i4;
        }
    }

    public final Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    public final BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.matrix != null) {
            return this.matrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < 40 || height < 40) {
            this.matrix = super.getBlackMatrix();
        } else {
            byte[] matrix = luminanceSource.getMatrix();
            int i = width >> 3;
            if ((width & 7) != 0) {
                i++;
            }
            int i2 = height >> 3;
            if ((height & 7) != 0) {
                i2++;
            }
            int[][] calculateBlackPoints = calculateBlackPoints(matrix, i, i2, width, height);
            BitMatrix bitMatrix = new BitMatrix(width, height);
            calculateThresholdForBlock(matrix, i, i2, width, height, calculateBlackPoints, bitMatrix);
            this.matrix = bitMatrix;
        }
        return this.matrix;
    }
}
