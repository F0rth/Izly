package com.google.zxing.aztec.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Detector {
    private boolean compact;
    private final BitMatrix image;
    private int nbCenterLayers;
    private int nbDataBlocks;
    private int nbLayers;
    private int shift;

    static final class Point {
        private final int x;
        private final int y;

        Point(int i, int i2) {
            this.x = i;
            this.y = i2;
        }

        final int getX() {
            return this.x;
        }

        final int getY() {
            return this.y;
        }

        final ResultPoint toResultPoint() {
            return new ResultPoint((float) getX(), (float) getY());
        }
    }

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private static void correctParameterData(boolean[] zArr, boolean z) throws NotFoundException {
        int i;
        int i2;
        int i3;
        if (z) {
            i = 7;
            i2 = 2;
        } else {
            i = 10;
            i2 = 4;
        }
        int[] iArr = new int[i];
        for (i3 = 0; i3 < i; i3++) {
            int i4;
            int i5 = 1;
            for (i4 = 1; i4 <= 4; i4++) {
                if (zArr[((i3 * 4) + 4) - i4]) {
                    iArr[i3] = iArr[i3] + i5;
                }
                i5 <<= 1;
            }
        }
        try {
            new ReedSolomonDecoder(GenericGF.AZTEC_PARAM).decode(iArr, i - i2);
            for (i3 = 0; i3 < i2; i3++) {
                i5 = 1;
                i4 = 1;
                while (i4 <= 4) {
                    zArr[((i3 * 4) + 4) - i4] = (iArr[i3] & i5) == i5;
                    i4++;
                    i5 <<= 1;
                }
            }
        } catch (ReedSolomonException e) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private static float distance(Point point, Point point2) {
        return MathUtils.distance(point.getX(), point.getY(), point2.getX(), point2.getY());
    }

    private void extractParameters(Point[] pointArr) throws NotFoundException {
        boolean[] zArr;
        int i = 0;
        int i2 = this.nbCenterLayers * 2;
        boolean[] sampleLine = sampleLine(pointArr[0], pointArr[1], i2 + 1);
        boolean[] sampleLine2 = sampleLine(pointArr[1], pointArr[2], i2 + 1);
        boolean[] sampleLine3 = sampleLine(pointArr[2], pointArr[3], i2 + 1);
        boolean[] sampleLine4 = sampleLine(pointArr[3], pointArr[0], i2 + 1);
        if (sampleLine[0] && sampleLine[i2]) {
            this.shift = 0;
        } else if (sampleLine2[0] && sampleLine2[i2]) {
            this.shift = 1;
        } else if (sampleLine3[0] && sampleLine3[i2]) {
            this.shift = 2;
        } else if (sampleLine4[0] && sampleLine4[i2]) {
            this.shift = 3;
        } else {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean[] zArr2;
        if (this.compact) {
            zArr2 = new boolean[28];
            for (i2 = 0; i2 < 7; i2++) {
                zArr2[i2] = sampleLine[i2 + 2];
                zArr2[i2 + 7] = sampleLine2[i2 + 2];
                zArr2[i2 + 14] = sampleLine3[i2 + 2];
                zArr2[i2 + 21] = sampleLine4[i2 + 2];
            }
            zArr = new boolean[28];
            while (i < 28) {
                zArr[i] = zArr2[((this.shift * 7) + i) % 28];
                i++;
            }
        } else {
            zArr2 = new boolean[40];
            for (i2 = 0; i2 < 11; i2++) {
                if (i2 < 5) {
                    zArr2[i2] = sampleLine[i2 + 2];
                    zArr2[i2 + 10] = sampleLine2[i2 + 2];
                    zArr2[i2 + 20] = sampleLine3[i2 + 2];
                    zArr2[i2 + 30] = sampleLine4[i2 + 2];
                }
                if (i2 > 5) {
                    zArr2[i2 - 1] = sampleLine[i2 + 2];
                    zArr2[i2 + 9] = sampleLine2[i2 + 2];
                    zArr2[i2 + 19] = sampleLine3[i2 + 2];
                    zArr2[i2 + 29] = sampleLine4[i2 + 2];
                }
            }
            zArr = new boolean[40];
            while (i < 40) {
                zArr[i] = zArr2[((this.shift * 10) + i) % 40];
                i++;
            }
        }
        correctParameterData(zArr, this.compact);
        getParameters(zArr);
    }

    private Point[] getBullEyeCornerPoints(Point point) throws NotFoundException {
        this.nbCenterLayers = 1;
        Point point2 = point;
        Point point3 = point;
        boolean z = true;
        Point point4 = point;
        while (this.nbCenterLayers < 9) {
            Point firstDifferent = getFirstDifferent(point3, z, 1, -1);
            Point firstDifferent2 = getFirstDifferent(point4, z, 1, 1);
            Point firstDifferent3 = getFirstDifferent(point, z, -1, 1);
            Point firstDifferent4 = getFirstDifferent(point2, z, -1, -1);
            if (this.nbCenterLayers > 2) {
                float distance = (distance(firstDifferent4, firstDifferent) * ((float) this.nbCenterLayers)) / (distance(point2, point3) * ((float) (this.nbCenterLayers + 2)));
                if (((double) distance) >= 0.75d) {
                    if (((double) distance) <= 1.25d) {
                        if (!isWhiteOrBlackRectangle(firstDifferent, firstDifferent2, firstDifferent3, firstDifferent4)) {
                            break;
                        }
                    }
                    break;
                }
                break;
            }
            boolean z2 = !z;
            this.nbCenterLayers++;
            point = firstDifferent3;
            point3 = firstDifferent;
            z = z2;
            point4 = firstDifferent2;
            point2 = firstDifferent4;
        }
        if (this.nbCenterLayers == 5 || this.nbCenterLayers == 7) {
            this.compact = this.nbCenterLayers == 5;
            float f = 1.5f / ((float) ((this.nbCenterLayers * 2) - 3));
            int x = point3.getX() - point.getX();
            int y = point3.getY() - point.getY();
            int round = MathUtils.round(((float) point.getX()) - (((float) x) * f));
            int round2 = MathUtils.round(((float) point.getY()) - (((float) y) * f));
            x = MathUtils.round((((float) x) * f) + ((float) point3.getX()));
            int round3 = MathUtils.round(((float) point3.getY()) + (((float) y) * f));
            y = point4.getX() - point2.getX();
            int y2 = point4.getY() - point2.getY();
            int round4 = MathUtils.round(((float) point2.getX()) - (((float) y) * f));
            int round5 = MathUtils.round(((float) point2.getY()) - (((float) y2) * f));
            y = MathUtils.round((((float) y) * f) + ((float) point4.getX()));
            int round6 = MathUtils.round((f * ((float) y2)) + ((float) point4.getY()));
            if (isValid(x, round3) && isValid(y, round6) && isValid(round, round2) && isValid(round4, round5)) {
                return new Point[]{new Point(x, round3), new Point(y, round6), new Point(round, round2), new Point(round4, round5)};
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private int getColor(Point point, Point point2) {
        float distance = distance(point, point2);
        float x = ((float) (point2.getX() - point.getX())) / distance;
        float y = ((float) (point2.getY() - point.getY())) / distance;
        float x2 = (float) point.getX();
        float y2 = (float) point.getY();
        boolean z = this.image.get(point.getX(), point.getY());
        int i = 0;
        for (int i2 = 0; ((float) i2) < distance; i2++) {
            x2 += x;
            y2 += y;
            if (this.image.get(MathUtils.round(x2), MathUtils.round(y2)) != z) {
                i++;
            }
        }
        float f = ((float) i) / distance;
        if (f > 0.1f && f < 0.9f) {
            return 0;
        }
        return ((f > 0.1f ? 1 : (f == 0.1f ? 0 : -1)) <= 0) == z ? 1 : -1;
    }

    private Point getFirstDifferent(Point point, boolean z, int i, int i2) {
        int x = point.getX() + i;
        int y = point.getY() + i2;
        while (isValid(x, y) && this.image.get(x, y) == z) {
            x += i;
            y += i2;
        }
        x -= i;
        y -= i2;
        while (isValid(x, y) && this.image.get(x, y) == z) {
            x += i;
        }
        int i3 = x - i;
        x = y;
        while (isValid(i3, x) && this.image.get(i3, x) == z) {
            x += i2;
        }
        return new Point(i3, x - i2);
    }

    private Point getMatrixCenter() {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        ResultPoint resultPoint4;
        int width;
        int height;
        try {
            ResultPoint[] detect = new WhiteRectangleDetector(this.image).detect();
            resultPoint = detect[0];
            resultPoint2 = detect[1];
            resultPoint3 = detect[2];
            resultPoint4 = detect[3];
        } catch (NotFoundException e) {
            width = this.image.getWidth() / 2;
            height = this.image.getHeight() / 2;
            resultPoint = getFirstDifferent(new Point(width + 7, height - 7), false, 1, -1).toResultPoint();
            resultPoint2 = getFirstDifferent(new Point(width + 7, height + 7), false, 1, 1).toResultPoint();
            resultPoint3 = getFirstDifferent(new Point(width - 7, height + 7), false, -1, 1).toResultPoint();
            resultPoint4 = getFirstDifferent(new Point(width - 7, height - 7), false, -1, -1).toResultPoint();
        }
        height = MathUtils.round((((resultPoint.getX() + resultPoint4.getX()) + resultPoint2.getX()) + resultPoint3.getX()) / 4.0f);
        width = MathUtils.round((((resultPoint.getY() + resultPoint4.getY()) + resultPoint2.getY()) + resultPoint3.getY()) / 4.0f);
        try {
            detect = new WhiteRectangleDetector(this.image, 15, height, width).detect();
            resultPoint = detect[0];
            resultPoint2 = detect[1];
            resultPoint3 = detect[2];
            resultPoint4 = detect[3];
        } catch (NotFoundException e2) {
            resultPoint = getFirstDifferent(new Point(height + 7, width - 7), false, 1, -1).toResultPoint();
            resultPoint2 = getFirstDifferent(new Point(height + 7, width + 7), false, 1, 1).toResultPoint();
            resultPoint3 = getFirstDifferent(new Point(height - 7, width + 7), false, -1, 1).toResultPoint();
            resultPoint4 = getFirstDifferent(new Point(height - 7, width - 7), false, -1, -1).toResultPoint();
        }
        return new Point(MathUtils.round((((resultPoint.getX() + resultPoint4.getX()) + resultPoint2.getX()) + resultPoint3.getX()) / 4.0f), MathUtils.round((((resultPoint.getY() + resultPoint4.getY()) + resultPoint2.getY()) + resultPoint3.getY()) / 4.0f));
    }

    private ResultPoint[] getMatrixCornerPoints(Point[] pointArr) throws NotFoundException {
        int i = -1;
        float f = ((float) (((this.nbLayers > 4 ? 1 : 0) + (this.nbLayers * 2)) + ((this.nbLayers - 4) / 8))) / (2.0f * ((float) this.nbCenterLayers));
        int x = pointArr[0].getX() - pointArr[2].getX();
        x += x > 0 ? 1 : -1;
        int y = pointArr[0].getY() - pointArr[2].getY();
        int i2 = (y > 0 ? 1 : -1) + y;
        y = MathUtils.round(((float) pointArr[2].getX()) - (((float) x) * f));
        int round = MathUtils.round(((float) pointArr[2].getY()) - (((float) i2) * f));
        x = MathUtils.round((((float) x) * f) + ((float) pointArr[0].getX()));
        int round2 = MathUtils.round((((float) i2) * f) + ((float) pointArr[0].getY()));
        int x2 = pointArr[1].getX() - pointArr[3].getX();
        i2 = (x2 > 0 ? 1 : -1) + x2;
        x2 = pointArr[1].getY() - pointArr[3].getY();
        if (x2 > 0) {
            i = 1;
        }
        i += x2;
        x2 = MathUtils.round(((float) pointArr[3].getX()) - (((float) i2) * f));
        int round3 = MathUtils.round(((float) pointArr[3].getY()) - (((float) i) * f));
        i2 = MathUtils.round((((float) i2) * f) + ((float) pointArr[1].getX()));
        i = MathUtils.round((((float) i) * f) + ((float) pointArr[1].getY()));
        if (isValid(x, round2) && isValid(i2, i) && isValid(y, round) && isValid(x2, round3)) {
            return new ResultPoint[]{new ResultPoint((float) x, (float) round2), new ResultPoint((float) i2, (float) i), new ResultPoint((float) y, (float) round), new ResultPoint((float) x2, (float) round3)};
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void getParameters(boolean[] zArr) {
        int i;
        int i2;
        int i3;
        if (this.compact) {
            i = 2;
            i2 = 6;
        } else {
            i = 5;
            i2 = 11;
        }
        for (i3 = 0; i3 < i; i3++) {
            this.nbLayers <<= 1;
            if (zArr[i3]) {
                this.nbLayers++;
            }
        }
        for (i3 = i; i3 < i + i2; i3++) {
            this.nbDataBlocks <<= 1;
            if (zArr[i3]) {
                this.nbDataBlocks++;
            }
        }
        this.nbLayers++;
        this.nbDataBlocks++;
    }

    private boolean isValid(int i, int i2) {
        return i >= 0 && i < this.image.getWidth() && i2 > 0 && i2 < this.image.getHeight();
    }

    private boolean isWhiteOrBlackRectangle(Point point, Point point2, Point point3, Point point4) {
        Point point5 = new Point(point.getX() - 3, point.getY() + 3);
        Point point6 = new Point(point2.getX() - 3, point2.getY() - 3);
        Point point7 = new Point(point3.getX() + 3, point3.getY() - 3);
        Point point8 = new Point(point4.getX() + 3, point4.getY() + 3);
        int color = getColor(point8, point5);
        return color != 0 && getColor(point5, point6) == color && getColor(point6, point7) == color && getColor(point7, point8) == color;
    }

    private BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        int i = this.compact ? (this.nbLayers * 4) + 11 : this.nbLayers <= 4 ? (this.nbLayers * 4) + 15 : ((this.nbLayers * 4) + ((((this.nbLayers - 4) / 8) + 1) * 2)) + 15;
        return GridSampler.getInstance().sampleGrid(bitMatrix, i, i, 0.5f, 0.5f, ((float) i) - 0.5f, 0.5f, ((float) i) - 0.5f, ((float) i) - 0.5f, 0.5f, ((float) i) - 0.5f, resultPoint.getX(), resultPoint.getY(), resultPoint4.getX(), resultPoint4.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private boolean[] sampleLine(Point point, Point point2, int i) {
        boolean[] zArr = new boolean[i];
        float distance = distance(point, point2);
        float f = distance / ((float) (i - 1));
        float x = (((float) (point2.getX() - point.getX())) * f) / distance;
        float y = (f * ((float) (point2.getY() - point.getY()))) / distance;
        f = (float) point.getX();
        float y2 = (float) point.getY();
        for (int i2 = 0; i2 < i; i2++) {
            zArr[i2] = this.image.get(MathUtils.round(f), MathUtils.round(y2));
            f += x;
            y2 += y;
        }
        return zArr;
    }

    public final AztecDetectorResult detect() throws NotFoundException {
        Point[] bullEyeCornerPoints = getBullEyeCornerPoints(getMatrixCenter());
        extractParameters(bullEyeCornerPoints);
        ResultPoint[] matrixCornerPoints = getMatrixCornerPoints(bullEyeCornerPoints);
        return new AztecDetectorResult(sampleGrid(this.image, matrixCornerPoints[this.shift % 4], matrixCornerPoints[(this.shift + 3) % 4], matrixCornerPoints[(this.shift + 2) % 4], matrixCornerPoints[(this.shift + 1) % 4]), matrixCornerPoints, this.compact, this.nbDataBlocks, this.nbLayers);
    }
}
