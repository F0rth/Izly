package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FinderPatternFinder {
    private static final int CENTER_QUORUM = 2;
    private static final int INTEGER_MATH_SHIFT = 8;
    protected static final int MAX_MODULES = 57;
    protected static final int MIN_SKIP = 3;
    private final int[] crossCheckStateCount;
    private boolean hasSkipped;
    private final BitMatrix image;
    private final List<FinderPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;

    static final class CenterComparator implements Serializable, Comparator<FinderPattern> {
        private final float average;

        private CenterComparator(float f) {
            this.average = f;
        }

        public final int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            if (finderPattern2.getCount() != finderPattern.getCount()) {
                return finderPattern2.getCount() - finderPattern.getCount();
            }
            float abs = Math.abs(finderPattern2.getEstimatedModuleSize() - this.average);
            float abs2 = Math.abs(finderPattern.getEstimatedModuleSize() - this.average);
            return abs < abs2 ? 1 : abs == abs2 ? 0 : -1;
        }
    }

    static final class FurthestFromAverageComparator implements Serializable, Comparator<FinderPattern> {
        private final float average;

        private FurthestFromAverageComparator(float f) {
            this.average = f;
        }

        public final int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            float abs = Math.abs(finderPattern2.getEstimatedModuleSize() - this.average);
            float abs2 = Math.abs(finderPattern.getEstimatedModuleSize() - this.average);
            return abs < abs2 ? -1 : abs == abs2 ? 0 : 1;
        }
    }

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this(bitMatrix, null);
    }

    public FinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList();
        this.crossCheckStateCount = new int[5];
        this.resultPointCallback = resultPointCallback;
    }

    private static float centerFromEnd(int[] iArr, int i) {
        return ((float) ((i - iArr[4]) - iArr[3])) - (((float) iArr[2]) / 2.0f);
    }

    private float crossCheckHorizontal(int i, int i2, int i3, int i4) {
        BitMatrix bitMatrix = this.image;
        int width = bitMatrix.getWidth();
        int[] crossCheckStateCount = getCrossCheckStateCount();
        int i5 = i;
        while (i5 >= 0 && bitMatrix.get(i5, i2)) {
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i5--;
        }
        if (i5 >= 0) {
            while (i5 >= 0 && !bitMatrix.get(i5, i2) && crossCheckStateCount[1] <= i3) {
                crossCheckStateCount[1] = crossCheckStateCount[1] + 1;
                i5--;
            }
            if (i5 >= 0 && crossCheckStateCount[1] <= i3) {
                while (i5 >= 0 && bitMatrix.get(i5, i2) && crossCheckStateCount[0] <= i3) {
                    crossCheckStateCount[0] = crossCheckStateCount[0] + 1;
                    i5--;
                }
                if (crossCheckStateCount[0] <= i3) {
                    i5 = i + 1;
                    while (i5 < width && bitMatrix.get(i5, i2)) {
                        crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
                        i5++;
                    }
                    if (i5 != width) {
                        while (i5 < width && !bitMatrix.get(i5, i2) && crossCheckStateCount[3] < i3) {
                            crossCheckStateCount[3] = crossCheckStateCount[3] + 1;
                            i5++;
                        }
                        if (i5 != width && crossCheckStateCount[3] < i3) {
                            while (i5 < width && bitMatrix.get(i5, i2) && crossCheckStateCount[4] < i3) {
                                crossCheckStateCount[4] = crossCheckStateCount[4] + 1;
                                i5++;
                            }
                            if (crossCheckStateCount[4] < i3 && Math.abs(((((crossCheckStateCount[0] + crossCheckStateCount[1]) + crossCheckStateCount[2]) + crossCheckStateCount[3]) + crossCheckStateCount[4]) - i4) * 5 < i4 && foundPatternCross(crossCheckStateCount)) {
                                return centerFromEnd(crossCheckStateCount, i5);
                            }
                        }
                    }
                }
            }
        }
        return Float.NaN;
    }

    private float crossCheckVertical(int i, int i2, int i3, int i4) {
        BitMatrix bitMatrix = this.image;
        int height = bitMatrix.getHeight();
        int[] crossCheckStateCount = getCrossCheckStateCount();
        int i5 = i;
        while (i5 >= 0 && bitMatrix.get(i2, i5)) {
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i5--;
        }
        if (i5 >= 0) {
            while (i5 >= 0 && !bitMatrix.get(i2, i5) && crossCheckStateCount[1] <= i3) {
                crossCheckStateCount[1] = crossCheckStateCount[1] + 1;
                i5--;
            }
            if (i5 >= 0 && crossCheckStateCount[1] <= i3) {
                while (i5 >= 0 && bitMatrix.get(i2, i5) && crossCheckStateCount[0] <= i3) {
                    crossCheckStateCount[0] = crossCheckStateCount[0] + 1;
                    i5--;
                }
                if (crossCheckStateCount[0] <= i3) {
                    i5 = i + 1;
                    while (i5 < height && bitMatrix.get(i2, i5)) {
                        crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
                        i5++;
                    }
                    if (i5 != height) {
                        while (i5 < height && !bitMatrix.get(i2, i5) && crossCheckStateCount[3] < i3) {
                            crossCheckStateCount[3] = crossCheckStateCount[3] + 1;
                            i5++;
                        }
                        if (i5 != height && crossCheckStateCount[3] < i3) {
                            while (i5 < height && bitMatrix.get(i2, i5) && crossCheckStateCount[4] < i3) {
                                crossCheckStateCount[4] = crossCheckStateCount[4] + 1;
                                i5++;
                            }
                            if (crossCheckStateCount[4] < i3 && Math.abs(((((crossCheckStateCount[0] + crossCheckStateCount[1]) + crossCheckStateCount[2]) + crossCheckStateCount[3]) + crossCheckStateCount[4]) - i4) * 5 < i4 * 2 && foundPatternCross(crossCheckStateCount)) {
                                return centerFromEnd(crossCheckStateCount, i5);
                            }
                        }
                    }
                }
            }
        }
        return Float.NaN;
    }

    private int findRowSkip() {
        if (this.possibleCenters.size() <= 1) {
            return 0;
        }
        FinderPattern finderPattern = null;
        for (FinderPattern finderPattern2 : this.possibleCenters) {
            if (finderPattern2.getCount() >= 2) {
                if (finderPattern == null) {
                    finderPattern = finderPattern2;
                } else {
                    this.hasSkipped = true;
                    return ((int) (Math.abs(finderPattern.getX() - finderPattern2.getX()) - Math.abs(finderPattern.getY() - finderPattern2.getY()))) / 2;
                }
            }
        }
        return 0;
    }

    protected static boolean foundPatternCross(int[] iArr) {
        int i;
        int i2 = 0;
        for (i = 0; i < 5; i++) {
            int i3 = iArr[i];
            if (i3 == 0) {
                break;
            }
            i2 += i3;
        }
        if (i2 >= 7) {
            i2 = (i2 << 8) / 7;
            i = i2 / 2;
            if (Math.abs(i2 - (iArr[0] << 8)) < i && Math.abs(i2 - (iArr[1] << 8)) < i && Math.abs((i2 * 3) - (iArr[2] << 8)) < i * 3 && Math.abs(i2 - (iArr[3] << 8)) < i && Math.abs(i2 - (iArr[4] << 8)) < i) {
                return true;
            }
        }
        return false;
    }

    private int[] getCrossCheckStateCount() {
        this.crossCheckStateCount[0] = 0;
        this.crossCheckStateCount[1] = 0;
        this.crossCheckStateCount[2] = 0;
        this.crossCheckStateCount[3] = 0;
        this.crossCheckStateCount[4] = 0;
        return this.crossCheckStateCount;
    }

    private boolean haveMultiplyConfirmedCenters() {
        float f;
        float f2 = 0.0f;
        int size = this.possibleCenters.size();
        int i = 0;
        float f3 = 0.0f;
        for (FinderPattern finderPattern : this.possibleCenters) {
            int i2;
            if (finderPattern.getCount() >= 2) {
                f3 += finderPattern.getEstimatedModuleSize();
                i2 = i + 1;
                f = f3;
            } else {
                i2 = i;
                f = f3;
            }
            f3 = f;
            i = i2;
        }
        if (i < 3) {
            return false;
        }
        f = f3 / ((float) size);
        for (FinderPattern finderPattern2 : this.possibleCenters) {
            f2 += Math.abs(finderPattern2.getEstimatedModuleSize() - f);
        }
        return f2 <= 0.05f * f3;
    }

    private FinderPattern[] selectBestPatterns() throws NotFoundException {
        float f = 0.0f;
        int size = this.possibleCenters.size();
        if (size < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (size > 3) {
            float f2 = 0.0f;
            float f3 = 0.0f;
            for (FinderPattern estimatedModuleSize : this.possibleCenters) {
                float estimatedModuleSize2 = estimatedModuleSize.getEstimatedModuleSize();
                f3 = (estimatedModuleSize2 * estimatedModuleSize2) + f3;
                f2 += estimatedModuleSize2;
            }
            float f4 = f2 / ((float) size);
            float sqrt = (float) Math.sqrt((double) ((f3 / ((float) size)) - (f4 * f4)));
            Collections.sort(this.possibleCenters, new FurthestFromAverageComparator(f4));
            f3 = Math.max(0.2f * f4, sqrt);
            int i = 0;
            while (i < this.possibleCenters.size() && this.possibleCenters.size() > 3) {
                if (Math.abs(((FinderPattern) this.possibleCenters.get(i)).getEstimatedModuleSize() - f4) > f3) {
                    this.possibleCenters.remove(i);
                    i--;
                }
                i++;
            }
        }
        if (this.possibleCenters.size() > 3) {
            for (FinderPattern estimatedModuleSize3 : this.possibleCenters) {
                f += estimatedModuleSize3.getEstimatedModuleSize();
            }
            Collections.sort(this.possibleCenters, new CenterComparator(f / ((float) this.possibleCenters.size())));
            this.possibleCenters.subList(3, this.possibleCenters.size()).clear();
        }
        return new FinderPattern[]{(FinderPattern) this.possibleCenters.get(0), (FinderPattern) this.possibleCenters.get(1), (FinderPattern) this.possibleCenters.get(2)};
    }

    final FinderPatternInfo find(Map<DecodeHintType, ?> map) throws NotFoundException {
        Object obj = (map == null || !map.containsKey(DecodeHintType.TRY_HARDER)) ? null : 1;
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i = (height * 3) / 228;
        int i2 = (i < 3 || obj != null) ? 3 : i;
        boolean z = false;
        int[] iArr = new int[5];
        int i3 = i2 - 1;
        int i4 = i2;
        while (i3 < height && !r1) {
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            iArr[3] = 0;
            iArr[4] = 0;
            i2 = 0;
            int i5 = 0;
            while (i5 < width) {
                if (this.image.get(i5, i3)) {
                    if ((i2 & 1) == 1) {
                        i2++;
                    }
                    iArr[i2] = iArr[i2] + 1;
                } else if ((i2 & 1) != 0) {
                    iArr[i2] = iArr[i2] + 1;
                } else if (i2 != 4) {
                    i2++;
                    iArr[i2] = iArr[i2] + 1;
                } else if (!foundPatternCross(iArr)) {
                    iArr[0] = iArr[2];
                    iArr[1] = iArr[3];
                    iArr[2] = iArr[4];
                    iArr[3] = 1;
                    iArr[4] = 0;
                    i2 = 3;
                } else if (handlePossibleCenter(iArr, i3, i5)) {
                    boolean haveMultiplyConfirmedCenters;
                    boolean z2;
                    if (this.hasSkipped) {
                        haveMultiplyConfirmedCenters = haveMultiplyConfirmedCenters();
                        i = i3;
                        i3 = i5;
                    } else {
                        i2 = findRowSkip();
                        if (i2 > iArr[2]) {
                            i2 = ((i2 - iArr[2]) - 2) + i3;
                            i3 = width - 1;
                        } else {
                            i2 = i3;
                            i3 = i5;
                        }
                        z2 = z;
                        i = i2;
                        haveMultiplyConfirmedCenters = z2;
                    }
                    iArr[0] = 0;
                    iArr[1] = 0;
                    iArr[2] = 0;
                    iArr[3] = 0;
                    iArr[4] = 0;
                    i4 = 2;
                    z2 = haveMultiplyConfirmedCenters;
                    i2 = 0;
                    i5 = i3;
                    i3 = i;
                    z = z2;
                } else {
                    iArr[0] = iArr[2];
                    iArr[1] = iArr[3];
                    iArr[2] = iArr[4];
                    iArr[3] = 1;
                    iArr[4] = 0;
                    i2 = 3;
                }
                i5++;
            }
            if (foundPatternCross(iArr) && handlePossibleCenter(iArr, i3, width)) {
                i4 = iArr[0];
                if (this.hasSkipped) {
                    z = haveMultiplyConfirmedCenters();
                }
            }
            i3 += i4;
        }
        ResultPoint[] selectBestPatterns = selectBestPatterns();
        ResultPoint.orderBestPatterns(selectBestPatterns);
        return new FinderPatternInfo(selectBestPatterns);
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final List<FinderPattern> getPossibleCenters() {
        return this.possibleCenters;
    }

    protected final boolean handlePossibleCenter(int[] iArr, int i, int i2) {
        int i3 = 0;
        int i4 = (((iArr[0] + iArr[1]) + iArr[2]) + iArr[3]) + iArr[4];
        float centerFromEnd = centerFromEnd(iArr, i2);
        float crossCheckVertical = crossCheckVertical(i, (int) centerFromEnd, iArr[2], i4);
        if (!Float.isNaN(crossCheckVertical)) {
            float crossCheckHorizontal = crossCheckHorizontal((int) centerFromEnd, (int) crossCheckVertical, iArr[2], i4);
            if (!Float.isNaN(crossCheckHorizontal)) {
                float f = ((float) i4) / 7.0f;
                for (int i5 = 0; i5 < this.possibleCenters.size(); i5++) {
                    FinderPattern finderPattern = (FinderPattern) this.possibleCenters.get(i5);
                    if (finderPattern.aboutEquals(f, crossCheckVertical, crossCheckHorizontal)) {
                        this.possibleCenters.set(i5, finderPattern.combineEstimate(crossCheckVertical, crossCheckHorizontal, f));
                        i3 = 1;
                        break;
                    }
                }
                if (i3 == 0) {
                    ResultPoint finderPattern2 = new FinderPattern(crossCheckHorizontal, crossCheckVertical, f);
                    this.possibleCenters.add(finderPattern2);
                    if (this.resultPointCallback != null) {
                        this.resultPointCallback.foundPossibleResultPoint(finderPattern2);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
