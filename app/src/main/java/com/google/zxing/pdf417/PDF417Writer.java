package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.pdf417.encoder.PDF417;
import java.lang.reflect.Array;
import java.util.Map;

public final class PDF417Writer implements Writer {
    private static BitMatrix bitMatrixFromEncoder(PDF417 pdf417, String str, int i, int i2) throws WriterException {
        int i3;
        pdf417.generateBarcodeLogic(str, 2);
        byte[][] scaledMatrix = pdf417.getBarcodeMatrix().getScaledMatrix(2, 8);
        if (((i2 > i ? 1 : 0) ^ (scaledMatrix[0].length < scaledMatrix.length ? 1 : 0)) != 0) {
            scaledMatrix = rotateArray(scaledMatrix);
            i3 = 1;
        } else {
            i3 = 0;
        }
        int length = i / scaledMatrix[0].length;
        int length2 = i2 / scaledMatrix.length;
        if (length < length2) {
            length2 = length;
        }
        if (length2 <= 1) {
            return bitMatrixFrombitArray(scaledMatrix);
        }
        byte[][] scaledMatrix2 = pdf417.getBarcodeMatrix().getScaledMatrix(length2 * 2, (length2 * 4) * 2);
        return bitMatrixFrombitArray(i3 != 0 ? rotateArray(scaledMatrix2) : scaledMatrix2);
    }

    private static BitMatrix bitMatrixFrombitArray(byte[][] bArr) {
        BitMatrix bitMatrix = new BitMatrix(bArr[0].length + 60, bArr.length + 60);
        bitMatrix.clear();
        int height = bitMatrix.getHeight() - 30;
        int i = 0;
        while (i < bArr.length) {
            for (int i2 = 0; i2 < bArr[0].length; i2++) {
                if (bArr[i][i2] == (byte) 1) {
                    bitMatrix.set(i2 + 30, height);
                }
            }
            i++;
            height--;
        }
        return bitMatrix;
    }

    private static byte[][] rotateArray(byte[][] bArr) {
        int length = bArr[0].length;
        int length2 = bArr.length;
        byte[][] bArr2 = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{length, length2});
        for (length2 = 0; length2 < bArr.length; length2++) {
            int length3 = bArr.length;
            for (int i = 0; i < bArr[0].length; i++) {
                bArr2[i][(length3 - length2) - 1] = bArr[length2][i];
            }
        }
        return bArr2;
    }

    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2) throws WriterException {
        return encode(str, barcodeFormat, i, i2, null);
    }

    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.PDF_417) {
            throw new IllegalArgumentException("Can only encode PDF_417, but got " + barcodeFormat);
        }
        PDF417 pdf417 = new PDF417();
        if (map != null) {
            if (map.containsKey(EncodeHintType.PDF417_COMPACT)) {
                pdf417.setCompact(((Boolean) map.get(EncodeHintType.PDF417_COMPACT)).booleanValue());
            }
            if (map.containsKey(EncodeHintType.PDF417_COMPACTION)) {
                pdf417.setCompaction((Compaction) map.get(EncodeHintType.PDF417_COMPACTION));
            }
            if (map.containsKey(EncodeHintType.PDF417_DIMENSIONS)) {
                Dimensions dimensions = (Dimensions) map.get(EncodeHintType.PDF417_DIMENSIONS);
                pdf417.setDimensions(dimensions.getMaxCols(), dimensions.getMinCols(), dimensions.getMaxRows(), dimensions.getMinRows());
            }
        }
        return bitMatrixFromEncoder(pdf417, str, i, i2);
    }
}
