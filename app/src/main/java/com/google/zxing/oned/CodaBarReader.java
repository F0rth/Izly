package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public final class CodaBarReader extends OneDReader {
    static final char[] ALPHABET = ALPHABET_STRING.toCharArray();
    private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";
    static final int[] CHARACTER_ENCODINGS = new int[]{3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
    private static final int MAX_ACCEPTABLE = 512;
    private static final int MIN_CHARACTER_LENGTH = 3;
    private static final int PADDING = 384;
    private static final char[] STARTEND_ENCODING = new char[]{'A', 'B', 'C', 'D'};
    private int counterLength = 0;
    private int[] counters = new int[80];
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    static boolean arrayContains(char[] cArr, char c) {
        if (cArr == null) {
            return false;
        }
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    private void counterAppend(int i) {
        this.counters[this.counterLength] = i;
        this.counterLength++;
        if (this.counterLength >= this.counters.length) {
            Object obj = new int[(this.counterLength * 2)];
            System.arraycopy(this.counters, 0, obj, 0, this.counterLength);
            this.counters = obj;
        }
    }

    private int findStartPattern() throws NotFoundException {
        int i = 1;
        while (i < this.counterLength) {
            int toNarrowWidePattern = toNarrowWidePattern(i);
            if (toNarrowWidePattern != -1 && arrayContains(STARTEND_ENCODING, ALPHABET[toNarrowWidePattern])) {
                toNarrowWidePattern = 0;
                for (int i2 = i; i2 < i + 7; i2++) {
                    toNarrowWidePattern += this.counters[i2];
                }
                if (i == 1 || this.counters[i - 1] >= toNarrowWidePattern / 2) {
                    return i;
                }
            }
            i += 2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void setCounters(BitArray bitArray) throws NotFoundException {
        this.counterLength = 0;
        int nextUnset = bitArray.getNextUnset(0);
        int size = bitArray.getSize();
        if (nextUnset >= size) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i = 0;
        nextUnset = 1;
        for (int i2 = nextUnset; i2 < size; i2++) {
            if ((bitArray.get(i2) ^ nextUnset) != 0) {
                i++;
            } else {
                counterAppend(i);
                nextUnset = nextUnset == 0 ? 1 : 0;
                i = 1;
            }
        }
        counterAppend(i);
    }

    private int toNarrowWidePattern(int i) {
        int i2 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int i3 = i + 7;
        if (i3 >= this.counterLength) {
            return -1;
        }
        int[] iArr = this.counters;
        int i4 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int i5 = i;
        int i6 = 0;
        while (i5 < i3) {
            int i7 = iArr[i5];
            if (i7 < i4) {
                i4 = i7;
            }
            if (i7 <= i6) {
                i7 = i6;
            }
            i5 += 2;
            i6 = i7;
        }
        i4 = (i4 + i6) / 2;
        i5 = i + 1;
        i6 = 0;
        while (i5 < i3) {
            i7 = iArr[i5];
            if (i7 < i2) {
                i2 = i7;
            }
            if (i7 <= i6) {
                i7 = i6;
            }
            i5 += 2;
            i6 = i7;
        }
        i6 = (i2 + i6) / 2;
        i5 = 128;
        i3 = 0;
        i2 = 0;
        while (i3 < 7) {
            i5 >>= 1;
            i7 = iArr[i + i3] > ((i3 & 1) == 0 ? i4 : i6) ? i2 | i5 : i2;
            i3++;
            i2 = i7;
        }
        for (i7 = 0; i7 < CHARACTER_ENCODINGS.length; i7++) {
            if (CHARACTER_ENCODINGS[i7] == i2) {
                return i7;
            }
        }
        return -1;
    }

    public final Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        int toNarrowWidePattern;
        int i2;
        Arrays.fill(this.counters, 0);
        setCounters(bitArray);
        int findStartPattern = findStartPattern();
        this.decodeRowResult.setLength(0);
        int i3 = findStartPattern;
        do {
            toNarrowWidePattern = toNarrowWidePattern(i3);
            if (toNarrowWidePattern != -1) {
                this.decodeRowResult.append((char) toNarrowWidePattern);
                i3 += 8;
                if (this.decodeRowResult.length() > 1 && arrayContains(STARTEND_ENCODING, ALPHABET[toNarrowWidePattern])) {
                    break;
                }
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } while (i3 < this.counterLength);
        int i4 = this.counters[i3 - 1];
        toNarrowWidePattern = 0;
        for (i2 = -8; i2 < -1; i2++) {
            toNarrowWidePattern += this.counters[i3 + i2];
        }
        if (i3 >= this.counterLength || i4 >= toNarrowWidePattern / 2) {
            validatePattern(findStartPattern);
            for (toNarrowWidePattern = 0; toNarrowWidePattern < this.decodeRowResult.length(); toNarrowWidePattern++) {
                this.decodeRowResult.setCharAt(toNarrowWidePattern, ALPHABET[this.decodeRowResult.charAt(toNarrowWidePattern)]);
            }
            if (arrayContains(STARTEND_ENCODING, this.decodeRowResult.charAt(0))) {
                if (!arrayContains(STARTEND_ENCODING, this.decodeRowResult.charAt(this.decodeRowResult.length() - 1))) {
                    throw NotFoundException.getNotFoundInstance();
                } else if (this.decodeRowResult.length() <= 3) {
                    throw NotFoundException.getNotFoundInstance();
                } else {
                    this.decodeRowResult.deleteCharAt(this.decodeRowResult.length() - 1);
                    this.decodeRowResult.deleteCharAt(0);
                    i2 = 0;
                    toNarrowWidePattern = 0;
                    while (i2 < findStartPattern) {
                        i4 = this.counters[i2];
                        i2++;
                        toNarrowWidePattern += i4;
                    }
                    float f = (float) toNarrowWidePattern;
                    int i5 = toNarrowWidePattern;
                    findStartPattern = i5;
                    for (toNarrowWidePattern = findStartPattern; toNarrowWidePattern < i3 - 1; toNarrowWidePattern++) {
                        findStartPattern += this.counters[toNarrowWidePattern];
                    }
                    float f2 = (float) findStartPattern;
                    String stringBuilder = this.decodeRowResult.toString();
                    ResultPoint resultPoint = new ResultPoint(f, (float) i);
                    ResultPoint resultPoint2 = new ResultPoint(f2, (float) i);
                    return new Result(stringBuilder, null, new ResultPoint[]{resultPoint, resultPoint2}, BarcodeFormat.CODABAR);
                }
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    final void validatePattern(int i) throws NotFoundException {
        int i2 = 0;
        int[] iArr = new int[]{0, 0, 0, 0};
        int[] iArr2 = new int[]{0, 0, 0, 0};
        int length = this.decodeRowResult.length() - 1;
        int i3 = i;
        int i4 = 0;
        while (true) {
            int i5 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(i4)];
            for (int i6 = 6; i6 >= 0; i6--) {
                int i7 = (i6 & 1) + ((i5 & 1) * 2);
                iArr[i7] = iArr[i7] + this.counters[i3 + i6];
                iArr2[i7] = iArr2[i7] + 1;
                i5 >>= 1;
            }
            if (i4 >= length) {
                break;
            }
            i3 += 8;
            i4++;
        }
        int[] iArr3 = new int[4];
        int[] iArr4 = new int[4];
        for (i3 = 0; i3 < 2; i3++) {
            iArr4[i3] = 0;
            iArr4[i3 + 2] = (((iArr[i3] << 8) / iArr2[i3]) + ((iArr[i3 + 2] << 8) / iArr2[i3 + 2])) >> 1;
            iArr3[i3] = iArr4[i3 + 2];
            iArr3[i3 + 2] = ((iArr[i3 + 2] * 512) + PADDING) / iArr2[i3 + 2];
        }
        loop3:
        while (true) {
            i3 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(i2)];
            i4 = 6;
            while (i4 >= 0) {
                int i8 = (i4 & 1) + ((i3 & 1) * 2);
                int i9 = this.counters[i + i4] << 8;
                if (i9 >= iArr4[i8] && i9 <= iArr3[i8]) {
                    i3 >>= 1;
                    i4--;
                }
            }
            if (i2 < length) {
                i += 8;
                i2++;
            } else {
                return;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
