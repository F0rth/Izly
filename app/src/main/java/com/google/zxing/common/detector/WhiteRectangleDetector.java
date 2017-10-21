package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class WhiteRectangleDetector {
    private static final int CORR = 1;
    private static final int INIT_SIZE = 30;
    private final int downInit;
    private final int height;
    private final BitMatrix image;
    private final int leftInit;
    private final int rightInit;
    private final int upInit;
    private final int width;

    public WhiteRectangleDetector(BitMatrix bitMatrix) throws NotFoundException {
        this.image = bitMatrix;
        this.height = bitMatrix.getHeight();
        this.width = bitMatrix.getWidth();
        this.leftInit = (this.width - 30) >> 1;
        this.rightInit = (this.width + 30) >> 1;
        this.upInit = (this.height - 30) >> 1;
        this.downInit = (this.height + 30) >> 1;
        if (this.upInit < 0 || this.leftInit < 0 || this.downInit >= this.height || this.rightInit >= this.width) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    public WhiteRectangleDetector(BitMatrix bitMatrix, int i, int i2, int i3) throws NotFoundException {
        this.image = bitMatrix;
        this.height = bitMatrix.getHeight();
        this.width = bitMatrix.getWidth();
        int i4 = i >> 1;
        this.leftInit = i2 - i4;
        this.rightInit = i2 + i4;
        this.upInit = i3 - i4;
        this.downInit = i4 + i3;
        if (this.upInit < 0 || this.leftInit < 0 || this.downInit >= this.height || this.rightInit >= this.width) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private ResultPoint[] centerEdges(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        float x = resultPoint.getX();
        float y = resultPoint.getY();
        float x2 = resultPoint2.getX();
        float y2 = resultPoint2.getY();
        float x3 = resultPoint3.getX();
        float y3 = resultPoint3.getY();
        float x4 = resultPoint4.getX();
        float y4 = resultPoint4.getY();
        if (x < ((float) this.width) / 2.0f) {
            return new ResultPoint[]{new ResultPoint(x4 - 1.0f, y4 + 1.0f), new ResultPoint(x2 + 1.0f, y2 + 1.0f), new ResultPoint(x3 - 1.0f, y3 - 1.0f), new ResultPoint(x + 1.0f, y - 1.0f)};
        }
        return new ResultPoint[]{new ResultPoint(x4 + 1.0f, y4 + 1.0f), new ResultPoint(x2 + 1.0f, y2 - 1.0f), new ResultPoint(x3 - 1.0f, y3 + 1.0f), new ResultPoint(x - 1.0f, y - 1.0f)};
    }

    private boolean containsBlackPoint(int i, int i2, int i3, boolean z) {
        if (z) {
            while (i <= i2) {
                if (this.image.get(i, i3)) {
                    return true;
                }
                i++;
            }
        } else {
            while (i <= i2) {
                if (this.image.get(i3, i)) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    private ResultPoint getBlackPointOnSegment(float f, float f2, float f3, float f4) {
        int round = MathUtils.round(MathUtils.distance(f, f2, f3, f4));
        float f5 = (f3 - f) / ((float) round);
        float f6 = (f4 - f2) / ((float) round);
        for (int i = 0; i < round; i++) {
            int round2 = MathUtils.round((((float) i) * f5) + f);
            int round3 = MathUtils.round((((float) i) * f6) + f2);
            if (this.image.get(round2, round3)) {
                return new ResultPoint((float) round2, (float) round3);
            }
        }
        return null;
    }

    public final ResultPoint[] detect() throws NotFoundException {
        int i;
        ResultPoint resultPoint = null;
        int i2 = 1;
        int i3 = this.leftInit;
        int i4 = this.rightInit;
        int i5 = this.upInit;
        int i6 = this.downInit;
        int i7 = 1;
        boolean z = false;
        while (i7 != 0) {
            boolean z2 = true;
            boolean z3 = false;
            while (z2 && i4 < this.width) {
                z2 = containsBlackPoint(i5, i6, i4, false);
                if (z2) {
                    i4++;
                    z3 = true;
                }
            }
            if (i4 >= this.width) {
                i = i5;
                i7 = i6;
                i6 = i4;
                i5 = i3;
                i4 = 1;
                break;
            }
            z2 = true;
            while (z2 && i6 < this.height) {
                z2 = containsBlackPoint(i3, i4, i6, true);
                if (z2) {
                    i6++;
                    z3 = true;
                }
            }
            if (i6 >= this.height) {
                i = i5;
                i7 = i6;
                i6 = i4;
                i5 = i3;
                i4 = 1;
                break;
            }
            z2 = true;
            while (z2 && i3 >= 0) {
                z2 = containsBlackPoint(i5, i6, i3, false);
                if (z2) {
                    i3--;
                    z3 = true;
                }
            }
            if (i3 < 0) {
                i = i5;
                i7 = i6;
                i6 = i4;
                i5 = i3;
                i4 = 1;
                break;
            }
            i7 = z3;
            z3 = true;
            while (z3 && i5 >= 0) {
                z3 = containsBlackPoint(i3, i4, i5, true);
                if (z3) {
                    i5--;
                    i7 = 1;
                }
            }
            if (i5 < 0) {
                i = i5;
                i7 = i6;
                i6 = i4;
                i5 = i3;
                i4 = 1;
                break;
            } else if (i7 != 0) {
                z = true;
            }
        }
        i = i5;
        i7 = i6;
        i6 = i4;
        i5 = i3;
        boolean z4 = false;
        if (i4 == 0 && r9) {
            int i8;
            int i9 = i6 - i5;
            ResultPoint resultPoint2 = null;
            for (i8 = 1; i8 < i9; i8++) {
                resultPoint2 = getBlackPointOnSegment((float) i5, (float) (i7 - i8), (float) (i5 + i8), (float) i7);
                if (resultPoint2 != null) {
                    break;
                }
            }
            ResultPoint resultPoint3 = resultPoint2;
            if (resultPoint3 == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            resultPoint2 = null;
            for (i8 = 1; i8 < i9; i8++) {
                resultPoint2 = getBlackPointOnSegment((float) i5, (float) (i + i8), (float) (i5 + i8), (float) i);
                if (resultPoint2 != null) {
                    break;
                }
            }
            ResultPoint resultPoint4 = resultPoint2;
            if (resultPoint4 == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            resultPoint2 = null;
            for (i8 = 1; i8 < i9; i8++) {
                resultPoint2 = getBlackPointOnSegment((float) i6, (float) (i + i8), (float) (i6 - i8), (float) i);
                if (resultPoint2 != null) {
                    break;
                }
            }
            if (resultPoint2 == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            while (i2 < i9) {
                resultPoint = getBlackPointOnSegment((float) i6, (float) (i7 - i2), (float) (i6 - i2), (float) i7);
                if (resultPoint != null) {
                    break;
                }
                i2++;
            }
            if (resultPoint != null) {
                return centerEdges(resultPoint, resultPoint3, resultPoint2, resultPoint4);
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
