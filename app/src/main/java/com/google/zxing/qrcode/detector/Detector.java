package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

public class Detector {
    private final BitMatrix image;
    private ResultPointCallback resultPointCallback;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private float calculateModuleSizeOneWay(ResultPoint resultPoint, ResultPoint resultPoint2) {
        float sizeOfBlackWhiteBlackRunBothWays = sizeOfBlackWhiteBlackRunBothWays((int) resultPoint.getX(), (int) resultPoint.getY(), (int) resultPoint2.getX(), (int) resultPoint2.getY());
        float sizeOfBlackWhiteBlackRunBothWays2 = sizeOfBlackWhiteBlackRunBothWays((int) resultPoint2.getX(), (int) resultPoint2.getY(), (int) resultPoint.getX(), (int) resultPoint.getY());
        return Float.isNaN(sizeOfBlackWhiteBlackRunBothWays) ? sizeOfBlackWhiteBlackRunBothWays2 / 7.0f : Float.isNaN(sizeOfBlackWhiteBlackRunBothWays2) ? sizeOfBlackWhiteBlackRunBothWays / 7.0f : (sizeOfBlackWhiteBlackRunBothWays + sizeOfBlackWhiteBlackRunBothWays2) / 14.0f;
    }

    private static int computeDimension(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, float f) throws NotFoundException {
        int round = ((MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2) / f) + MathUtils.round(ResultPoint.distance(resultPoint, resultPoint3) / f)) >> 1) + 7;
        switch (round & 3) {
            case 0:
                return round + 1;
            case 2:
                return round - 1;
            case 3:
                throw NotFoundException.getNotFoundInstance();
            default:
                return round;
        }
    }

    private static PerspectiveTransform createTransform(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i) {
        float x;
        float y;
        float f;
        float f2;
        float f3 = ((float) i) - 3.5f;
        if (resultPoint4 != null) {
            x = resultPoint4.getX();
            y = resultPoint4.getY();
            f = f3 - 3.0f;
            f2 = f;
        } else {
            x = (resultPoint2.getX() - resultPoint.getX()) + resultPoint3.getX();
            y = (resultPoint2.getY() - resultPoint.getY()) + resultPoint3.getY();
            f = f3;
            f2 = f3;
        }
        return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f3, 3.5f, f2, f, 3.5f, f3, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), x, y, resultPoint3.getX(), resultPoint3.getY());
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, PerspectiveTransform perspectiveTransform, int i) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(bitMatrix, i, i, perspectiveTransform);
    }

    private float sizeOfBlackWhiteBlackRun(int i, int i2, int i3, int i4) {
        Object obj = Math.abs(i4 - i2) > Math.abs(i3 - i) ? 1 : null;
        if (obj == null) {
            int i5 = i3;
            i3 = i4;
            i4 = i5;
            int i6 = i;
            i = i2;
            i2 = i6;
        }
        int abs = Math.abs(i4 - i2);
        int abs2 = Math.abs(i3 - i);
        int i7 = -abs;
        int i8 = i2 < i4 ? 1 : -1;
        int i9 = i < i3 ? 1 : -1;
        int i10 = 0;
        int i11 = i7 >> 1;
        int i12 = i2;
        int i13 = i;
        while (i12 != i4 + i8) {
            if ((i10 == 1) == this.image.get(obj != null ? i13 : i12, obj != null ? i12 : i13)) {
                if (i10 == 2) {
                    return MathUtils.distance(i12, i13, i2, i);
                }
                i10++;
            }
            i7 = i11 + abs2;
            if (i7 > 0) {
                if (i13 == i3) {
                    i9 = i10;
                    break;
                }
                i13 += i9;
                i7 -= abs;
            }
            i12 += i8;
            i11 = i7;
        }
        i9 = i10;
        return i9 == 2 ? MathUtils.distance(i4 + i8, i3, i2, i) : Float.NaN;
    }

    private float sizeOfBlackWhiteBlackRunBothWays(int i, int i2, int i3, int i4) {
        float f;
        int i5;
        int i6 = 0;
        float sizeOfBlackWhiteBlackRun = sizeOfBlackWhiteBlackRun(i, i2, i3, i4);
        int i7 = i - (i3 - i);
        if (i7 < 0) {
            f = ((float) i) / ((float) (i - i7));
            i5 = 0;
        } else if (i7 >= this.image.getWidth()) {
            f = ((float) ((this.image.getWidth() - 1) - i)) / ((float) (i7 - i));
            i5 = this.image.getWidth() - 1;
        } else {
            i5 = i7;
            f = 1.0f;
        }
        i7 = (int) (((float) i2) - (f * ((float) (i4 - i2))));
        if (i7 < 0) {
            f = ((float) i2) / ((float) (i2 - i7));
        } else if (i7 >= this.image.getHeight()) {
            f = ((float) ((this.image.getHeight() - 1) - i2)) / ((float) (i7 - i2));
            i6 = this.image.getHeight() - 1;
        } else {
            i6 = i7;
            f = 1.0f;
        }
        return (sizeOfBlackWhiteBlackRun(i, i2, (int) ((f * ((float) (i5 - i))) + ((float) i)), i6) + sizeOfBlackWhiteBlackRun) - 1.0f;
    }

    protected final float calculateModuleSize(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        return (calculateModuleSizeOneWay(resultPoint, resultPoint2) + calculateModuleSizeOneWay(resultPoint, resultPoint3)) / 2.0f;
    }

    public DetectorResult detect() throws NotFoundException, FormatException {
        return detect(null);
    }

    public final DetectorResult detect(Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        this.resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        return processFinderPatternInfo(new FinderPatternFinder(this.image, this.resultPointCallback).find(map));
    }

    protected final AlignmentPattern findAlignmentInRegion(float f, int i, int i2, float f2) throws NotFoundException {
        int i3 = (int) (f2 * f);
        int max = Math.max(0, i - i3);
        int min = Math.min(this.image.getWidth() - 1, i + i3);
        if (((float) (min - max)) < f * 3.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int max2 = Math.max(0, i2 - i3);
        int min2 = Math.min(this.image.getHeight() - 1, i3 + i2);
        if (((float) (min2 - max2)) < f * 3.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        return new AlignmentPatternFinder(this.image, max, max2, min - max, min2 - max2, f, this.resultPointCallback).find();
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final ResultPointCallback getResultPointCallback() {
        return this.resultPointCallback;
    }

    protected final DetectorResult processFinderPatternInfo(FinderPatternInfo finderPatternInfo) throws NotFoundException, FormatException {
        ResultPoint topLeft = finderPatternInfo.getTopLeft();
        ResultPoint topRight = finderPatternInfo.getTopRight();
        ResultPoint bottomLeft = finderPatternInfo.getBottomLeft();
        float calculateModuleSize = calculateModuleSize(topLeft, topRight, bottomLeft);
        if (calculateModuleSize < 1.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        ResultPoint[] resultPointArr;
        int computeDimension = computeDimension(topLeft, topRight, bottomLeft, calculateModuleSize);
        Version provisionalVersionForDimension = Version.getProvisionalVersionForDimension(computeDimension);
        int dimensionForVersion = provisionalVersionForDimension.getDimensionForVersion();
        ResultPoint resultPoint = null;
        if (provisionalVersionForDimension.getAlignmentPatternCenters().length > 0) {
            float x = topRight.getX();
            float x2 = topLeft.getX();
            float x3 = bottomLeft.getX();
            float y = topRight.getY();
            float y2 = topLeft.getY();
            float y3 = bottomLeft.getY();
            float f = 1.0f - (3.0f / ((float) (dimensionForVersion - 7)));
            int x4 = (int) (((((x - x2) + x3) - topLeft.getX()) * f) + topLeft.getX());
            dimensionForVersion = (int) (topLeft.getY() + (f * (((y - y2) + y3) - topLeft.getY())));
            int i = 4;
            while (i <= 16) {
                try {
                    resultPoint = findAlignmentInRegion(calculateModuleSize, x4, dimensionForVersion, (float) i);
                    break;
                } catch (NotFoundException e) {
                    i <<= 1;
                }
            }
        }
        BitMatrix sampleGrid = sampleGrid(this.image, createTransform(topLeft, topRight, bottomLeft, resultPoint, computeDimension), computeDimension);
        if (resultPoint == null) {
            resultPointArr = new ResultPoint[]{bottomLeft, topLeft, topRight};
        } else {
            resultPointArr = new ResultPoint[]{bottomLeft, topLeft, topRight, resultPoint};
        }
        return new DetectorResult(sampleGrid, resultPointArr);
    }
}
