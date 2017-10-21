package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;

public class GlobalHistogramBinarizer extends Binarizer {
    private static final byte[] EMPTY = new byte[0];
    private static final int LUMINANCE_BITS = 5;
    private static final int LUMINANCE_BUCKETS = 32;
    private static final int LUMINANCE_SHIFT = 3;
    private final int[] buckets = new int[32];
    private byte[] luminances = EMPTY;

    public GlobalHistogramBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int estimateBlackPoint(int[] iArr) throws NotFoundException {
        int i;
        int i2;
        int i3 = 0;
        int length = iArr.length;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (i = 0; i < length; i++) {
            if (iArr[i] > i5) {
                i5 = iArr[i];
                i6 = i;
            }
            if (iArr[i] > i4) {
                i4 = iArr[i];
            }
        }
        i = 0;
        i5 = 0;
        while (i < length) {
            int i7 = i - i6;
            i7 *= iArr[i] * i7;
            if (i7 > i3) {
                i5 = i;
            } else {
                i7 = i3;
            }
            i++;
            i3 = i7;
        }
        if (i6 > i5) {
            i3 = i5;
            i2 = i6;
        } else {
            i3 = i6;
            i2 = i5;
        }
        if (i2 - i3 <= (length >> 4)) {
            throw NotFoundException.getNotFoundInstance();
        }
        i5 = i2 - 1;
        i7 = -1;
        i6 = i2 - 1;
        while (i6 > i3) {
            i = i6 - i3;
            i = ((i * i) * (i2 - i6)) * (i4 - iArr[i6]);
            if (i > i7) {
                i5 = i6;
            } else {
                i = i7;
            }
            i6--;
            i7 = i;
        }
        return i5 << 3;
    }

    private void initArrays(int i) {
        if (this.luminances.length < i) {
            this.luminances = new byte[i];
        }
        for (int i2 = 0; i2 < 32; i2++) {
            this.buckets[i2] = 0;
        }
    }

    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new GlobalHistogramBinarizer(luminanceSource);
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        int i;
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        BitMatrix bitMatrix = new BitMatrix(width, height);
        initArrays(width);
        int[] iArr = this.buckets;
        for (i = 1; i < 5; i++) {
            int i2;
            byte[] row = luminanceSource.getRow((height * i) / 5, this.luminances);
            int i3 = (width << 2) / 5;
            for (i2 = width / 5; i2 < i3; i2++) {
                int i4 = (row[i2] & 255) >> 3;
                iArr[i4] = iArr[i4] + 1;
            }
        }
        int estimateBlackPoint = estimateBlackPoint(iArr);
        byte[] matrix = luminanceSource.getMatrix();
        for (i = 0; i < height; i++) {
            for (i2 = 0; i2 < width; i2++) {
                if ((matrix[(i * width) + i2] & 255) < estimateBlackPoint) {
                    bitMatrix.set(i2, i);
                }
            }
        }
        return bitMatrix;
    }

    public BitArray getBlackRow(int i, BitArray bitArray) throws NotFoundException {
        int i2;
        int i3;
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        if (bitArray == null || bitArray.getSize() < width) {
            bitArray = new BitArray(width);
        } else {
            bitArray.clear();
        }
        initArrays(width);
        byte[] row = luminanceSource.getRow(i, this.luminances);
        int[] iArr = this.buckets;
        for (i2 = 0; i2 < width; i2++) {
            i3 = (row[i2] & 255) >> 3;
            iArr[i3] = iArr[i3] + 1;
        }
        i3 = estimateBlackPoint(iArr);
        i2 = row[0] & 255;
        int i4 = row[1] & 255;
        int i5 = 1;
        while (i5 < width - 1) {
            int i6 = row[i5 + 1] & 255;
            if (((((i4 << 2) - i2) - i6) >> 1) < i3) {
                bitArray.set(i5);
            }
            i5++;
            i2 = i4;
            i4 = i6;
        }
        return bitArray;
    }
}
