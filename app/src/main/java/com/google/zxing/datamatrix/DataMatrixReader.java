package com.google.zxing.datamatrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.datamatrix.decoder.Decoder;
import com.google.zxing.datamatrix.detector.Detector;
import java.util.List;
import java.util.Map;

public final class DataMatrixReader implements Reader {
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        int[] topLeftOnBit = bitMatrix.getTopLeftOnBit();
        int[] bottomRightOnBit = bitMatrix.getBottomRightOnBit();
        if (topLeftOnBit == null || bottomRightOnBit == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        int moduleSize = moduleSize(topLeftOnBit, bitMatrix);
        int i = topLeftOnBit[1];
        int i2 = bottomRightOnBit[1];
        int i3 = topLeftOnBit[0];
        int i4 = ((bottomRightOnBit[0] - i3) + 1) / moduleSize;
        i2 = ((i2 - i) + 1) / moduleSize;
        if (i4 <= 0 || i2 <= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i5 = moduleSize >> 1;
        BitMatrix bitMatrix2 = new BitMatrix(i4, i2);
        for (int i6 = 0; i6 < i2; i6++) {
            for (int i7 = 0; i7 < i4; i7++) {
                if (bitMatrix.get((i7 * moduleSize) + (i3 + i5), (i + i5) + (i6 * moduleSize))) {
                    bitMatrix2.set(i7, i6);
                }
            }
        }
        return bitMatrix2;
    }

    private static int moduleSize(int[] iArr, BitMatrix bitMatrix) throws NotFoundException {
        int width = bitMatrix.getWidth();
        int i = iArr[0];
        int i2 = iArr[1];
        while (i < width && bitMatrix.get(i, i2)) {
            i++;
        }
        if (i == width) {
            throw NotFoundException.getNotFoundInstance();
        }
        i -= iArr[0];
        if (i != 0) {
            return i;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return decode(binaryBitmap, null);
    }

    public final Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        DecoderResult decode;
        ResultPoint[] points;
        if (map == null || !map.containsKey(DecodeHintType.PURE_BARCODE)) {
            DetectorResult detect = new Detector(binaryBitmap.getBlackMatrix()).detect();
            decode = this.decoder.decode(detect.getBits());
            points = detect.getPoints();
        } else {
            decode = this.decoder.decode(extractPureBits(binaryBitmap.getBlackMatrix()));
            points = NO_POINTS;
        }
        Result result = new Result(decode.getText(), decode.getRawBytes(), points, BarcodeFormat.DATA_MATRIX);
        List byteSegments = decode.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String eCLevel = decode.getECLevel();
        if (eCLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, eCLevel);
        }
        return result;
    }

    public final void reset() {
    }
}
