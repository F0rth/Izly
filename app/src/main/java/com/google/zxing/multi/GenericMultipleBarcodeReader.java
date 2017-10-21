package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.delegate = reader;
    }

    private void doDecodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int i, int i2, int i3) {
        int i4 = i2;
        while (i3 <= 4) {
            try {
                Object obj;
                Result decode = this.delegate.decode(binaryBitmap, map);
                for (Result text : list) {
                    if (text.getText().equals(decode.getText())) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    list.add(translateResultPoints(decode, i, i4));
                }
                ResultPoint[] resultPoints = decode.getResultPoints();
                if (resultPoints != null && resultPoints.length != 0) {
                    int width = binaryBitmap.getWidth();
                    int height = binaryBitmap.getHeight();
                    float f = (float) width;
                    float f2 = (float) height;
                    float f3 = 0.0f;
                    int length = resultPoints.length;
                    float f4 = f2;
                    float f5 = 0.0f;
                    int i5 = 0;
                    while (i5 < length) {
                        ResultPoint resultPoint = resultPoints[i5];
                        float x = resultPoint.getX();
                        float y = resultPoint.getY();
                        float f6 = x < f ? x : f;
                        f = y < f4 ? y : f4;
                        if (x <= f5) {
                            x = f5;
                        }
                        if (y <= f3) {
                            y = f3;
                        }
                        i5++;
                        f4 = f;
                        f5 = x;
                        f3 = y;
                        f = f6;
                    }
                    if (f > 100.0f) {
                        doDecodeMultiple(binaryBitmap.crop(0, 0, (int) f, height), map, list, i, i4, i3 + 1);
                    }
                    if (f4 > 100.0f) {
                        doDecodeMultiple(binaryBitmap.crop(0, 0, width, (int) f4), map, list, i, i4, i3 + 1);
                    }
                    if (f5 < ((float) (width - 100))) {
                        doDecodeMultiple(binaryBitmap.crop((int) f5, 0, width - ((int) f5), height), map, list, i + ((int) f5), i4, i3 + 1);
                    }
                    if (f3 < ((float) (height - 100))) {
                        binaryBitmap = binaryBitmap.crop(0, (int) f3, width, height - ((int) f3));
                        i4 += (int) f3;
                        i3++;
                    } else {
                        return;
                    }
                }
                return;
            } catch (ReaderException e) {
                return;
            }
        }
    }

    private static Result translateResultPoints(Result result, int i, int i2) {
        ResultPoint[] resultPoints = result.getResultPoints();
        if (resultPoints == null) {
            return result;
        }
        ResultPoint[] resultPointArr = new ResultPoint[resultPoints.length];
        for (int i3 = 0; i3 < resultPoints.length; i3++) {
            ResultPoint resultPoint = resultPoints[i3];
            resultPointArr[i3] = new ResultPoint(resultPoint.getX() + ((float) i), resultPoint.getY() + ((float) i2));
        }
        Result result2 = new Result(result.getText(), result.getRawBytes(), resultPointArr, result.getBarcodeFormat());
        result2.putAllMetadata(result.getResultMetadata());
        return result2;
    }

    public final Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    public final Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        List arrayList = new ArrayList();
        doDecodeMultiple(binaryBitmap, map, arrayList, 0, 0, 0);
        if (!arrayList.isEmpty()) {
            return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
