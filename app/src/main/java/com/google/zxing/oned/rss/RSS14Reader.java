package com.google.zxing.oned.rss;

import android.support.v4.media.TransportMediator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class RSS14Reader extends AbstractRSSReader {
    private static final int[][] FINDER_PATTERNS;
    private static final int[] INSIDE_GSUM = new int[]{0, 336, 1036, 1516};
    private static final int[] INSIDE_ODD_TOTAL_SUBSET = new int[]{4, 20, 48, 81};
    private static final int[] INSIDE_ODD_WIDEST = new int[]{2, 4, 6, 8};
    private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET = new int[]{1, 10, 34, 70, TransportMediator.KEYCODE_MEDIA_PLAY};
    private static final int[] OUTSIDE_GSUM = new int[]{0, 161, 961, 2015, 2715};
    private static final int[] OUTSIDE_ODD_WIDEST = new int[]{8, 6, 4, 3, 1};
    private final List<Pair> possibleLeftPairs = new ArrayList();
    private final List<Pair> possibleRightPairs = new ArrayList();

    static {
        int[] iArr = new int[]{1, 3, 9, 1};
        FINDER_PATTERNS = new int[][]{new int[]{3, 8, 2, 1}, new int[]{3, 5, 5, 1}, new int[]{3, 3, 7, 1}, new int[]{3, 1, 9, 1}, new int[]{2, 7, 4, 1}, new int[]{2, 5, 6, 1}, new int[]{2, 3, 8, 1}, new int[]{1, 5, 7, 1}, iArr};
    }

    private static void addOrTally(Collection<Pair> collection, Pair pair) {
        if (pair != null) {
            Object obj;
            for (Pair pair2 : collection) {
                if (pair2.getValue() == pair.getValue()) {
                    pair2.incrementCount();
                    obj = 1;
                    break;
                }
            }
            obj = null;
            if (obj == null) {
                collection.add(pair);
            }
        }
    }

    private void adjustOddEvenCounts(boolean z, int i) throws NotFoundException {
        Object obj;
        Object obj2;
        int i2;
        Object obj3;
        Object obj4;
        Object obj5;
        Object obj6 = 1;
        int count = AbstractRSSReader.count(getOddCounts());
        int count2 = AbstractRSSReader.count(getEvenCounts());
        int i3 = (count + count2) - i;
        Object obj7 = (count & 1) == (z ? 1 : 0) ? 1 : null;
        Object obj8 = (count2 & 1) == 1 ? 1 : null;
        if (z) {
            if (count > 12) {
                obj = 1;
                obj2 = null;
            } else if (count < 4) {
                obj = null;
                i2 = 1;
            } else {
                obj = null;
                obj2 = null;
            }
            if (count2 > 12) {
                obj3 = obj;
                obj = 1;
                obj4 = obj2;
                obj2 = null;
                obj5 = obj4;
            } else {
                if (count2 < 4) {
                    obj3 = obj;
                    obj = null;
                    obj5 = obj2;
                    i2 = 1;
                }
                obj3 = obj;
                obj = null;
                obj4 = obj2;
                obj2 = null;
                obj5 = obj4;
            }
        } else {
            if (count > 11) {
                obj = 1;
                obj2 = null;
            } else if (count < 5) {
                obj = null;
                i2 = 1;
            } else {
                obj = null;
                obj2 = null;
            }
            if (count2 > 10) {
                obj3 = obj;
                int i4 = 1;
                obj4 = obj2;
                obj2 = null;
                obj5 = obj4;
            } else {
                if (count2 < 4) {
                    obj3 = obj;
                    obj = null;
                    obj5 = obj2;
                    i2 = 1;
                }
                obj3 = obj;
                obj = null;
                obj4 = obj2;
                obj2 = null;
                obj5 = obj4;
            }
        }
        if (i3 == 1) {
            if (obj7 != null) {
                if (obj8 != null) {
                    throw NotFoundException.getNotFoundInstance();
                }
                obj4 = obj2;
                obj2 = obj5;
                obj5 = 1;
                obj6 = obj4;
            } else if (obj8 == null) {
                throw NotFoundException.getNotFoundInstance();
            } else {
                i4 = 1;
                obj6 = obj2;
                obj2 = obj5;
                obj5 = obj3;
            }
        } else if (i3 == -1) {
            if (obj7 != null) {
                if (obj8 != null) {
                    throw NotFoundException.getNotFoundInstance();
                }
                obj5 = obj3;
                obj6 = obj2;
                i2 = 1;
            } else if (obj8 == null) {
                throw NotFoundException.getNotFoundInstance();
            } else {
                obj2 = obj5;
                obj5 = obj3;
            }
        } else if (i3 != 0) {
            throw NotFoundException.getNotFoundInstance();
        } else if (obj7 != null) {
            if (obj8 == null) {
                throw NotFoundException.getNotFoundInstance();
            } else if (count < count2) {
                i4 = 1;
                obj5 = obj3;
                obj6 = obj2;
                i2 = 1;
            } else {
                obj2 = obj5;
                int i5 = 1;
            }
        } else if (obj8 != null) {
            throw NotFoundException.getNotFoundInstance();
        } else {
            obj6 = obj2;
            obj2 = obj5;
            obj5 = obj3;
        }
        if (obj2 != null) {
            if (obj5 != null) {
                throw NotFoundException.getNotFoundInstance();
            }
            AbstractRSSReader.increment(getOddCounts(), getOddRoundingErrors());
        }
        if (obj5 != null) {
            AbstractRSSReader.decrement(getOddCounts(), getOddRoundingErrors());
        }
        if (obj6 != null) {
            if (obj != null) {
                throw NotFoundException.getNotFoundInstance();
            }
            AbstractRSSReader.increment(getEvenCounts(), getOddRoundingErrors());
        }
        if (obj != null) {
            AbstractRSSReader.decrement(getEvenCounts(), getEvenRoundingErrors());
        }
    }

    private static boolean checkChecksum(Pair pair, Pair pair2) {
        int checksumPortion = pair.getChecksumPortion();
        int checksumPortion2 = pair2.getChecksumPortion();
        int value = (pair.getFinderPattern().getValue() * 9) + pair2.getFinderPattern().getValue();
        if (value > 72) {
            value--;
        }
        if (value > 8) {
            value--;
        }
        return (checksumPortion + (checksumPortion2 * 16)) % 79 == value;
    }

    private static Result constructResult(Pair pair, Pair pair2) {
        int length;
        String valueOf = String.valueOf((4537077 * ((long) pair.getValue())) + ((long) pair2.getValue()));
        StringBuilder stringBuilder = new StringBuilder(14);
        for (length = 13 - valueOf.length(); length > 0; length--) {
            stringBuilder.append('0');
        }
        stringBuilder.append(valueOf);
        int i = 0;
        int i2 = 0;
        while (i < 13) {
            length = stringBuilder.charAt(i) - 48;
            if ((i & 1) == 0) {
                length *= 3;
            }
            i++;
            i2 = length + i2;
        }
        length = 10 - (i2 % 10);
        if (length == 10) {
            length = 0;
        }
        stringBuilder.append(length);
        ResultPoint[] resultPoints = pair.getFinderPattern().getResultPoints();
        ResultPoint[] resultPoints2 = pair2.getFinderPattern().getResultPoints();
        String valueOf2 = String.valueOf(stringBuilder.toString());
        ResultPoint resultPoint = resultPoints[0];
        ResultPoint resultPoint2 = resultPoints[1];
        ResultPoint resultPoint3 = resultPoints2[0];
        ResultPoint resultPoint4 = resultPoints2[1];
        return new Result(valueOf2, null, new ResultPoint[]{resultPoint, resultPoint2, resultPoint3, resultPoint4}, BarcodeFormat.RSS_14);
    }

    private DataCharacter decodeDataCharacter(BitArray bitArray, FinderPattern finderPattern, boolean z) throws NotFoundException {
        int i;
        int length;
        int i2;
        int[] dataCharacterCounters = getDataCharacterCounters();
        dataCharacterCounters[0] = 0;
        dataCharacterCounters[1] = 0;
        dataCharacterCounters[2] = 0;
        dataCharacterCounters[3] = 0;
        dataCharacterCounters[4] = 0;
        dataCharacterCounters[5] = 0;
        dataCharacterCounters[6] = 0;
        dataCharacterCounters[7] = 0;
        if (z) {
            OneDReader.recordPatternInReverse(bitArray, finderPattern.getStartEnd()[0], dataCharacterCounters);
        } else {
            OneDReader.recordPattern(bitArray, finderPattern.getStartEnd()[1] + 1, dataCharacterCounters);
            i = 0;
            for (length = dataCharacterCounters.length - 1; i < length; length--) {
                i2 = dataCharacterCounters[i];
                dataCharacterCounters[i] = dataCharacterCounters[length];
                dataCharacterCounters[length] = i2;
                i++;
            }
        }
        i = z ? 16 : 15;
        float count = ((float) AbstractRSSReader.count(dataCharacterCounters)) / ((float) i);
        int[] oddCounts = getOddCounts();
        int[] evenCounts = getEvenCounts();
        float[] oddRoundingErrors = getOddRoundingErrors();
        float[] evenRoundingErrors = getEvenRoundingErrors();
        for (length = 0; length < dataCharacterCounters.length; length++) {
            float f = ((float) dataCharacterCounters[length]) / count;
            i2 = (int) (0.5f + f);
            if (i2 <= 0) {
                i2 = 1;
            } else if (i2 > 8) {
                i2 = 8;
            }
            int i3 = length >> 1;
            if ((length & 1) == 0) {
                oddCounts[i3] = i2;
                oddRoundingErrors[i3] = f - ((float) i2);
            } else {
                evenCounts[i3] = i2;
                evenRoundingErrors[i3] = f - ((float) i2);
            }
        }
        adjustOddEvenCounts(z, i);
        i = oddCounts.length - 1;
        int i4 = 0;
        int i5 = 0;
        while (i >= 0) {
            length = oddCounts[i];
            i2 = oddCounts[i];
            i--;
            i4 = length + (i4 * 9);
            i5 = i2 + i5;
        }
        i = 0;
        length = 0;
        for (i2 = evenCounts.length - 1; i2 >= 0; i2--) {
            i = (i * 9) + evenCounts[i2];
            length += evenCounts[i2];
        }
        i2 = (i * 3) + i4;
        if (z) {
            if ((i5 & 1) != 0 || i5 > 12 || i5 < 4) {
                throw NotFoundException.getNotFoundInstance();
            }
            length = (12 - i5) / 2;
            i = OUTSIDE_ODD_WIDEST[length];
            return new DataCharacter(OUTSIDE_GSUM[length] + ((RSSUtils.getRSSvalue(oddCounts, i, false) * OUTSIDE_EVEN_TOTAL_SUBSET[length]) + RSSUtils.getRSSvalue(evenCounts, 9 - i, true)), i2);
        } else if ((length & 1) != 0 || length > 10 || length < 4) {
            throw NotFoundException.getNotFoundInstance();
        } else {
            length = (10 - length) / 2;
            i4 = INSIDE_ODD_WIDEST[length];
            return new DataCharacter(INSIDE_GSUM[length] + ((RSSUtils.getRSSvalue(evenCounts, 9 - i4, false) * INSIDE_ODD_TOTAL_SUBSET[length]) + RSSUtils.getRSSvalue(oddCounts, i4, true)), i2);
        }
    }

    private Pair decodePair(BitArray bitArray, boolean z, int i, Map<DecodeHintType, ?> map) {
        try {
            int[] findFinderPattern = findFinderPattern(bitArray, 0, z);
            FinderPattern parseFoundFinderPattern = parseFoundFinderPattern(bitArray, i, z, findFinderPattern);
            ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (resultPointCallback != null) {
                float f = ((float) (findFinderPattern[0] + findFinderPattern[1])) / 2.0f;
                if (z) {
                    f = ((float) (bitArray.getSize() - 1)) - f;
                }
                resultPointCallback.foundPossibleResultPoint(new ResultPoint(f, (float) i));
            }
            DataCharacter decodeDataCharacter = decodeDataCharacter(bitArray, parseFoundFinderPattern, true);
            DataCharacter decodeDataCharacter2 = decodeDataCharacter(bitArray, parseFoundFinderPattern, false);
            return new Pair((decodeDataCharacter.getValue() * 1597) + decodeDataCharacter2.getValue(), decodeDataCharacter.getChecksumPortion() + (decodeDataCharacter2.getChecksumPortion() * 4), parseFoundFinderPattern);
        } catch (NotFoundException e) {
            return null;
        }
    }

    private int[] findFinderPattern(BitArray bitArray, int i, boolean z) throws NotFoundException {
        int[] decodeFinderCounters = getDecodeFinderCounters();
        decodeFinderCounters[0] = 0;
        decodeFinderCounters[1] = 0;
        decodeFinderCounters[2] = 0;
        decodeFinderCounters[3] = 0;
        int size = bitArray.getSize();
        boolean z2 = false;
        int i2 = i;
        while (i2 < size) {
            z2 = !bitArray.get(i2);
            if (z == z2) {
                break;
            }
            i2++;
        }
        int i3 = z2;
        int i4 = 0;
        for (int i5 = i2; i5 < size; i5++) {
            if ((bitArray.get(i5) ^ i3) != 0) {
                decodeFinderCounters[i4] = decodeFinderCounters[i4] + 1;
            } else {
                if (i4 != 3) {
                    i4++;
                } else if (AbstractRSSReader.isFinderPattern(decodeFinderCounters)) {
                    return new int[]{i2, i5};
                } else {
                    i2 += decodeFinderCounters[0] + decodeFinderCounters[1];
                    decodeFinderCounters[0] = decodeFinderCounters[2];
                    decodeFinderCounters[1] = decodeFinderCounters[3];
                    decodeFinderCounters[2] = 0;
                    decodeFinderCounters[3] = 0;
                    i4--;
                }
                decodeFinderCounters[i4] = 1;
                i3 = i3 == 0 ? 1 : 0;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitArray, int i, boolean z, int[] iArr) throws NotFoundException {
        int size;
        boolean z2 = bitArray.get(iArr[0]);
        int i2 = iArr[0] - 1;
        while (i2 >= 0 && (bitArray.get(i2) ^ z2) != 0) {
            i2--;
        }
        int i3 = i2 + 1;
        i2 = iArr[0];
        Object decodeFinderCounters = getDecodeFinderCounters();
        System.arraycopy(decodeFinderCounters, 0, decodeFinderCounters, 1, decodeFinderCounters.length - 1);
        decodeFinderCounters[0] = i2 - i3;
        int parseFinderValue = AbstractRSSReader.parseFinderValue(decodeFinderCounters, FINDER_PATTERNS);
        int i4 = iArr[1];
        if (z) {
            size = (bitArray.getSize() - 1) - i3;
            i4 = (bitArray.getSize() - 1) - i4;
        } else {
            size = i3;
        }
        return new FinderPattern(parseFinderValue, new int[]{i3, iArr[1]}, size, i4, i);
    }

    public final Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        addOrTally(this.possibleLeftPairs, decodePair(bitArray, false, i, map));
        bitArray.reverse();
        addOrTally(this.possibleRightPairs, decodePair(bitArray, true, i, map));
        bitArray.reverse();
        int size = this.possibleLeftPairs.size();
        for (int i2 = 0; i2 < size; i2++) {
            Pair pair = (Pair) this.possibleLeftPairs.get(i2);
            if (pair.getCount() > 1) {
                int size2 = this.possibleRightPairs.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    Pair pair2 = (Pair) this.possibleRightPairs.get(i3);
                    if (pair2.getCount() > 1 && checkChecksum(pair, pair2)) {
                        return constructResult(pair, pair2);
                    }
                }
                continue;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final void reset() {
        this.possibleLeftPairs.clear();
        this.possibleRightPairs.clear();
    }
}
