package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public abstract class OneDReader implements Reader {
    protected static final int INTEGER_MATH_SHIFT = 8;
    protected static final int PATTERN_MATCH_RESULT_SCALE_FACTOR = 256;

    private Result doDecode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        Object obj;
        int max;
        int i;
        Map map2;
        int i2;
        int i3;
        int i4;
        int i5;
        Result decodeRow;
        ResultPoint[] resultPoints;
        int width = binaryBitmap.getWidth();
        int height = binaryBitmap.getHeight();
        BitArray bitArray = new BitArray(width);
        if (map != null) {
            if (map.containsKey(DecodeHintType.TRY_HARDER)) {
                obj = 1;
                max = Math.max(1, height >> (obj == null ? 8 : 5));
                i = obj == null ? height : 15;
                map2 = map;
                for (i2 = 0; i2 < i; i2++) {
                    i3 = (i2 + 1) >> 1;
                    if (((i2 & 1) != 0 ? 1 : null) == null) {
                        i3 = -i3;
                    }
                    i4 = (height >> 1) + (i3 * max);
                    if (i4 < 0 || i4 >= height) {
                        break;
                    }
                    try {
                        bitArray = binaryBitmap.getBlackRow(i4, bitArray);
                        i5 = 0;
                        while (i5 < 2) {
                            if (i5 == 1) {
                                bitArray.reverse();
                                if (map2 != null && map2.containsKey(DecodeHintType.NEED_RESULT_POINT_CALLBACK)) {
                                    Map enumMap = new EnumMap(DecodeHintType.class);
                                    enumMap.putAll(map2);
                                    enumMap.remove(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
                                    map2 = enumMap;
                                }
                            }
                            try {
                                decodeRow = decodeRow(i4, bitArray, map2);
                                if (i5 == 1) {
                                    decodeRow.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(180));
                                    resultPoints = decodeRow.getResultPoints();
                                    if (resultPoints != null) {
                                        resultPoints[0] = new ResultPoint((((float) width) - resultPoints[0].getX()) - 1.0f, resultPoints[0].getY());
                                        resultPoints[1] = new ResultPoint((((float) width) - resultPoints[1].getX()) - 1.0f, resultPoints[1].getY());
                                    }
                                }
                                return decodeRow;
                            } catch (ReaderException e) {
                                i5++;
                            }
                        }
                        continue;
                    } catch (NotFoundException e2) {
                    }
                }
                throw NotFoundException.getNotFoundInstance();
            }
        }
        obj = null;
        if (obj == null) {
        }
        max = Math.max(1, height >> (obj == null ? 8 : 5));
        if (obj == null) {
        }
        map2 = map;
        for (i2 = 0; i2 < i; i2++) {
            i3 = (i2 + 1) >> 1;
            if ((i2 & 1) != 0) {
            }
            if (((i2 & 1) != 0 ? 1 : null) == null) {
                i3 = -i3;
            }
            i4 = (height >> 1) + (i3 * max);
            bitArray = binaryBitmap.getBlackRow(i4, bitArray);
            i5 = 0;
            while (i5 < 2) {
                if (i5 == 1) {
                    bitArray.reverse();
                    Map enumMap2 = new EnumMap(DecodeHintType.class);
                    enumMap2.putAll(map2);
                    enumMap2.remove(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
                    map2 = enumMap2;
                }
                decodeRow = decodeRow(i4, bitArray, map2);
                if (i5 == 1) {
                    decodeRow.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(180));
                    resultPoints = decodeRow.getResultPoints();
                    if (resultPoints != null) {
                        resultPoints[0] = new ResultPoint((((float) width) - resultPoints[0].getX()) - 1.0f, resultPoints[0].getY());
                        resultPoints[1] = new ResultPoint((((float) width) - resultPoints[1].getX()) - 1.0f, resultPoints[1].getY());
                    }
                }
                return decodeRow;
            }
            continue;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected static int patternMatchVariance(int[] iArr, int[] iArr2, int i) {
        int i2;
        int length = iArr.length;
        int i3 = 0;
        int i4 = 0;
        for (i2 = 0; i2 < length; i2++) {
            i4 += iArr[i2];
            i3 += iArr2[i2];
        }
        if (i4 >= i3) {
            int i5 = (i4 << 8) / i3;
            i3 = 0;
            i2 = 0;
            while (i2 < length) {
                int i6 = iArr[i2] << 8;
                int i7 = iArr2[i2] * i5;
                i6 = i6 > i7 ? i6 - i7 : i7 - i6;
                if (i6 <= ((i * i5) >> 8)) {
                    i2++;
                    i3 = i6 + i3;
                }
            }
            return i3 / i4;
        }
        return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    protected static void recordPattern(BitArray bitArray, int i, int[] iArr) throws NotFoundException {
        int length = iArr.length;
        Arrays.fill(iArr, 0, length, 0);
        int size = bitArray.getSize();
        if (i >= size) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i2 = !bitArray.get(i) ? 1 : 0;
        int i3 = 0;
        while (i < size) {
            if ((bitArray.get(i) ^ i2) == 0) {
                i3++;
                if (i3 == length) {
                    break;
                }
                iArr[i3] = 1;
                i2 = i2 == 0 ? 1 : 0;
            } else {
                iArr[i3] = iArr[i3] + 1;
            }
            i++;
        }
        if (i3 == length) {
            return;
        }
        if (i3 != length - 1 || i != size) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    protected static void recordPatternInReverse(BitArray bitArray, int i, int[] iArr) throws NotFoundException {
        int length = iArr.length;
        boolean z = bitArray.get(i);
        while (i > 0 && length >= 0) {
            i--;
            if (bitArray.get(i) != z) {
                length--;
                z = !z;
            }
        }
        if (length >= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        recordPattern(bitArray, i + 1, iArr);
    }

    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return decode(binaryBitmap, null);
    }

    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        try {
            return doDecode(binaryBitmap, map);
        } catch (NotFoundException e) {
            Object obj = (map == null || !map.containsKey(DecodeHintType.TRY_HARDER)) ? null : 1;
            if (obj == null || !binaryBitmap.isRotateSupported()) {
                throw e;
            }
            BinaryBitmap rotateCounterClockwise = binaryBitmap.rotateCounterClockwise();
            Result doDecode = doDecode(rotateCounterClockwise, map);
            Map resultMetadata = doDecode.getResultMetadata();
            int i = 270;
            if (resultMetadata != null && resultMetadata.containsKey(ResultMetadataType.ORIENTATION)) {
                i = (((Integer) resultMetadata.get(ResultMetadataType.ORIENTATION)).intValue() + 270) % 360;
            }
            doDecode.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(i));
            ResultPoint[] resultPoints = doDecode.getResultPoints();
            if (resultPoints != null) {
                int height = rotateCounterClockwise.getHeight();
                for (i = 0; i < resultPoints.length; i++) {
                    resultPoints[i] = new ResultPoint((((float) height) - resultPoints[i].getY()) - 1.0f, resultPoints[i].getX());
                }
            }
            return doDecode;
        }
    }

    public abstract Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException;

    public void reset() {
    }
}
