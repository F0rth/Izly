package com.google.zxing.datamatrix.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class Detector {
    private final BitMatrix image;
    private final WhiteRectangleDetector rectangleDetector;

    static final class ResultPointsAndTransitions {
        private final ResultPoint from;
        private final ResultPoint to;
        private final int transitions;

        private ResultPointsAndTransitions(ResultPoint resultPoint, ResultPoint resultPoint2, int i) {
            this.from = resultPoint;
            this.to = resultPoint2;
            this.transitions = i;
        }

        final ResultPoint getFrom() {
            return this.from;
        }

        final ResultPoint getTo() {
            return this.to;
        }

        public final int getTransitions() {
            return this.transitions;
        }

        public final String toString() {
            return this.from + "/" + this.to + '/' + this.transitions;
        }
    }

    static final class ResultPointsAndTransitionsComparator implements Serializable, Comparator<ResultPointsAndTransitions> {
        private ResultPointsAndTransitionsComparator() {
        }

        public final int compare(ResultPointsAndTransitions resultPointsAndTransitions, ResultPointsAndTransitions resultPointsAndTransitions2) {
            return resultPointsAndTransitions.getTransitions() - resultPointsAndTransitions2.getTransitions();
        }
    }

    public Detector(BitMatrix bitMatrix) throws NotFoundException {
        this.image = bitMatrix;
        this.rectangleDetector = new WhiteRectangleDetector(bitMatrix);
    }

    private ResultPoint correctTopRight(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i) {
        float distance = ((float) distance(resultPoint, resultPoint2)) / ((float) i);
        int distance2 = distance(resultPoint3, resultPoint4);
        ResultPoint resultPoint5 = new ResultPoint((((resultPoint4.getX() - resultPoint3.getX()) / ((float) distance2)) * distance) + resultPoint4.getX(), (distance * ((resultPoint4.getY() - resultPoint3.getY()) / ((float) distance2))) + resultPoint4.getY());
        float distance3 = ((float) distance(resultPoint, resultPoint3)) / ((float) i);
        int distance4 = distance(resultPoint2, resultPoint4);
        ResultPoint resultPoint6 = new ResultPoint((((resultPoint4.getX() - resultPoint2.getX()) / ((float) distance4)) * distance3) + resultPoint4.getX(), (distance3 * ((resultPoint4.getY() - resultPoint2.getY()) / ((float) distance4))) + resultPoint4.getY());
        return !isValid(resultPoint5) ? isValid(resultPoint6) ? resultPoint6 : null : !isValid(resultPoint6) ? resultPoint5 : Math.abs(transitionsBetween(resultPoint3, resultPoint5).getTransitions() - transitionsBetween(resultPoint2, resultPoint5).getTransitions()) <= Math.abs(transitionsBetween(resultPoint3, resultPoint6).getTransitions() - transitionsBetween(resultPoint2, resultPoint6).getTransitions()) ? resultPoint5 : resultPoint6;
    }

    private ResultPoint correctTopRightRectangular(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) {
        float distance = ((float) distance(resultPoint, resultPoint2)) / ((float) i);
        int distance2 = distance(resultPoint3, resultPoint4);
        ResultPoint resultPoint5 = new ResultPoint((((resultPoint4.getX() - resultPoint3.getX()) / ((float) distance2)) * distance) + resultPoint4.getX(), (distance * ((resultPoint4.getY() - resultPoint3.getY()) / ((float) distance2))) + resultPoint4.getY());
        float distance3 = ((float) distance(resultPoint, resultPoint3)) / ((float) i2);
        int distance4 = distance(resultPoint2, resultPoint4);
        ResultPoint resultPoint6 = new ResultPoint((((resultPoint4.getX() - resultPoint2.getX()) / ((float) distance4)) * distance3) + resultPoint4.getX(), (distance3 * ((resultPoint4.getY() - resultPoint2.getY()) / ((float) distance4))) + resultPoint4.getY());
        return !isValid(resultPoint5) ? isValid(resultPoint6) ? resultPoint6 : null : !isValid(resultPoint6) ? resultPoint5 : Math.abs(i - transitionsBetween(resultPoint3, resultPoint5).getTransitions()) + Math.abs(i2 - transitionsBetween(resultPoint2, resultPoint5).getTransitions()) <= Math.abs(i - transitionsBetween(resultPoint3, resultPoint6).getTransitions()) + Math.abs(i2 - transitionsBetween(resultPoint2, resultPoint6).getTransitions()) ? resultPoint5 : resultPoint6;
    }

    private static int distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2));
    }

    private static void increment(Map<ResultPoint, Integer> map, ResultPoint resultPoint) {
        Integer num = (Integer) map.get(resultPoint);
        map.put(resultPoint, Integer.valueOf(num == null ? 1 : num.intValue() + 1));
    }

    private boolean isValid(ResultPoint resultPoint) {
        return resultPoint.getX() >= 0.0f && resultPoint.getX() < ((float) this.image.getWidth()) && resultPoint.getY() > 0.0f && resultPoint.getY() < ((float) this.image.getHeight());
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(bitMatrix, i, i2, 0.5f, 0.5f, ((float) i) - 0.5f, 0.5f, ((float) i) - 0.5f, ((float) i2) - 0.5f, 0.5f, ((float) i2) - 0.5f, resultPoint.getX(), resultPoint.getY(), resultPoint4.getX(), resultPoint4.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private ResultPointsAndTransitions transitionsBetween(ResultPoint resultPoint, ResultPoint resultPoint2) {
        int x = (int) resultPoint.getX();
        int y = (int) resultPoint.getY();
        int x2 = (int) resultPoint2.getX();
        int y2 = (int) resultPoint2.getY();
        Object obj = Math.abs(y2 - y) > Math.abs(x2 - x) ? 1 : null;
        if (obj != null) {
            int i = y2;
            y2 = x2;
            x2 = i;
            int i2 = x;
            x = y;
            y = i2;
        }
        int abs = Math.abs(x2 - x);
        int abs2 = Math.abs(y2 - y);
        int i3 = -abs;
        int i4 = y < y2 ? 1 : -1;
        int i5 = x < x2 ? 1 : -1;
        int i6 = 0;
        boolean z = this.image.get(obj != null ? y : x, obj != null ? x : y);
        int i7 = i3 >> 1;
        int i8 = y;
        while (x != x2) {
            boolean z2 = this.image.get(obj != null ? i8 : x, obj != null ? x : i8);
            if (z2 != z) {
                i6++;
                z = z2;
            }
            y = i7 + abs2;
            if (y > 0) {
                if (i8 == y2) {
                    break;
                }
                i8 += i4;
                y -= abs;
            }
            x += i5;
            i7 = y;
        }
        return new ResultPointsAndTransitions(resultPoint, resultPoint2, i6);
    }

    public final DetectorResult detect() throws NotFoundException {
        AnonymousClass1 anonymousClass1 = null;
        ResultPoint[] detect = this.rectangleDetector.detect();
        ResultPoint resultPoint = detect[0];
        ResultPoint resultPoint2 = detect[1];
        ResultPoint resultPoint3 = detect[2];
        ResultPoint resultPoint4 = detect[3];
        List arrayList = new ArrayList(4);
        arrayList.add(transitionsBetween(resultPoint, resultPoint2));
        arrayList.add(transitionsBetween(resultPoint, resultPoint3));
        arrayList.add(transitionsBetween(resultPoint2, resultPoint4));
        arrayList.add(transitionsBetween(resultPoint3, resultPoint4));
        Collections.sort(arrayList, new ResultPointsAndTransitionsComparator());
        ResultPointsAndTransitions resultPointsAndTransitions = (ResultPointsAndTransitions) arrayList.get(0);
        ResultPointsAndTransitions resultPointsAndTransitions2 = (ResultPointsAndTransitions) arrayList.get(1);
        Map hashMap = new HashMap();
        increment(hashMap, resultPointsAndTransitions.getFrom());
        increment(hashMap, resultPointsAndTransitions.getTo());
        increment(hashMap, resultPointsAndTransitions2.getFrom());
        increment(hashMap, resultPointsAndTransitions2.getTo());
        ResultPoint resultPoint5 = null;
        ResultPoint resultPoint6 = null;
        for (Entry entry : hashMap.entrySet()) {
            ResultPoint resultPoint7 = (ResultPoint) entry.getKey();
            if (((Integer) entry.getValue()).intValue() == 2) {
                resultPoint5 = resultPoint7;
            } else if (resultPoint6 == null) {
                resultPoint6 = resultPoint7;
            } else {
                anonymousClass1 = resultPoint7;
            }
        }
        if (resultPoint6 == null || resultPoint5 == null || anonymousClass1 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        BitMatrix sampleGrid;
        detect = new ResultPoint[]{resultPoint6, resultPoint5, anonymousClass1};
        ResultPoint.orderBestPatterns(detect);
        ResultPoint resultPoint8 = detect[0];
        resultPoint7 = detect[1];
        resultPoint5 = detect[2];
        resultPoint6 = !hashMap.containsKey(resultPoint) ? resultPoint : !hashMap.containsKey(resultPoint2) ? resultPoint2 : !hashMap.containsKey(resultPoint3) ? resultPoint3 : resultPoint4;
        int transitions = transitionsBetween(resultPoint5, resultPoint6).getTransitions();
        int transitions2 = transitionsBetween(resultPoint8, resultPoint6).getTransitions();
        if ((transitions & 1) == 1) {
            transitions++;
        }
        transitions += 2;
        if ((transitions2 & 1) == 1) {
            transitions2++;
        }
        int i = transitions2 + 2;
        int transitions3;
        if (transitions * 4 >= i * 7 || i * 4 >= transitions * 7) {
            resultPoint4 = correctTopRightRectangular(resultPoint7, resultPoint8, resultPoint5, resultPoint6, transitions, i);
            if (resultPoint4 == null) {
                resultPoint4 = resultPoint6;
            }
            transitions3 = transitionsBetween(resultPoint5, resultPoint4).getTransitions();
            int transitions4 = transitionsBetween(resultPoint8, resultPoint4).getTransitions();
            if ((transitions3 & 1) == 1) {
                transitions3++;
            }
            if ((transitions4 & 1) == 1) {
                transitions4++;
            }
            sampleGrid = sampleGrid(this.image, resultPoint5, resultPoint7, resultPoint8, resultPoint4, transitions3, transitions4);
        } else {
            resultPoint4 = correctTopRight(resultPoint7, resultPoint8, resultPoint5, resultPoint6, Math.min(i, transitions));
            if (resultPoint4 == null) {
                resultPoint4 = resultPoint6;
            }
            transitions3 = Math.max(transitionsBetween(resultPoint5, resultPoint4).getTransitions(), transitionsBetween(resultPoint8, resultPoint4).getTransitions()) + 1;
            if ((transitions3 & 1) == 1) {
                transitions3++;
            }
            sampleGrid = sampleGrid(this.image, resultPoint5, resultPoint7, resultPoint8, resultPoint4, transitions3, transitions3);
        }
        return new DetectorResult(sampleGrid, new ResultPoint[]{resultPoint5, resultPoint7, resultPoint8, resultPoint4});
    }
}
