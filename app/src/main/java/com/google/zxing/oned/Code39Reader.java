package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public final class Code39Reader extends OneDReader {
    private static final char[] ALPHABET = ALPHABET_STRING.toCharArray();
    static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";
    private static final int ASTERISK_ENCODING;
    static final int[] CHARACTER_ENCODINGS;
    private final int[] counters;
    private final StringBuilder decodeRowResult;
    private final boolean extendedMode;
    private final boolean usingCheckDigit;

    static {
        int[] iArr = new int[]{52, 289, 97, 352, 49, 304, 112, 37, 292, 100, 265, 73, 328, 25, 280, 88, 13, 268, 76, 28, 259, 67, 322, 19, 274, 82, 7, 262, 70, 22, 385, 193, 448, 145, 400, 208, 133, 388, 196, 148, 168, 162, 138, 42};
        CHARACTER_ENCODINGS = iArr;
        ASTERISK_ENCODING = iArr[39];
    }

    public Code39Reader() {
        this(false);
    }

    public Code39Reader(boolean z) {
        this(z, false);
    }

    public Code39Reader(boolean z, boolean z2) {
        this.usingCheckDigit = z;
        this.extendedMode = z2;
        this.decodeRowResult = new StringBuilder(20);
        this.counters = new int[9];
    }

    private static String decodeExtended(CharSequence charSequence) throws FormatException {
        int length = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            int i2;
            char charAt = charSequence.charAt(i);
            if (charAt == '+' || charAt == '$' || charAt == '%' || charAt == '/') {
                char charAt2 = charSequence.charAt(i + 1);
                switch (charAt) {
                    case '$':
                        if (charAt2 >= 'A' && charAt2 <= 'Z') {
                            charAt = (char) (charAt2 - 64);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                        break;
                    case '%':
                        if (charAt2 < 'A' || charAt2 > 'E') {
                            if (charAt2 >= 'F' && charAt2 <= 'W') {
                                charAt = (char) (charAt2 - 11);
                                break;
                            }
                            throw FormatException.getFormatInstance();
                        }
                        charAt = (char) (charAt2 - 38);
                        break;
                        break;
                    case '+':
                        if (charAt2 >= 'A' && charAt2 <= 'Z') {
                            charAt = (char) (charAt2 + 32);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                        break;
                    case '/':
                        if (charAt2 >= 'A' && charAt2 <= 'O') {
                            charAt = (char) (charAt2 - 32);
                            break;
                        } else if (charAt2 == 'Z') {
                            charAt = ':';
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    default:
                        charAt = '\u0000';
                        break;
                }
                stringBuilder.append(charAt);
                i2 = i + 1;
            } else {
                stringBuilder.append(charAt);
                i2 = i;
            }
            i = i2 + 1;
        }
        return stringBuilder.toString();
    }

    private static int[] findAsteriskPattern(BitArray bitArray, int[] iArr) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        int length = iArr.length;
        int i = 0;
        int i2 = nextSet;
        int i3 = nextSet;
        nextSet = 0;
        while (i2 < size) {
            if ((bitArray.get(i2) ^ i) != 0) {
                iArr[nextSet] = iArr[nextSet] + 1;
            } else {
                if (nextSet != length - 1) {
                    nextSet++;
                } else if (toNarrowWidePattern(iArr) == ASTERISK_ENCODING && bitArray.isRange(Math.max(0, i3 - ((i2 - i3) >> 1)), i3, false)) {
                    return new int[]{i3, i2};
                } else {
                    i3 += iArr[0] + iArr[1];
                    System.arraycopy(iArr, 2, iArr, 0, length - 2);
                    iArr[length - 2] = 0;
                    iArr[length - 1] = 0;
                    nextSet--;
                }
                iArr[nextSet] = 1;
                i = i == 0 ? 1 : 0;
            }
            i2++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static char patternToChar(int i) throws NotFoundException {
        for (int i2 = 0; i2 < CHARACTER_ENCODINGS.length; i2++) {
            if (CHARACTER_ENCODINGS[i2] == i) {
                return ALPHABET[i2];
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int toNarrowWidePattern(int[] iArr) {
        int i;
        int length = iArr.length;
        int i2 = 0;
        while (true) {
            int i3;
            int i4 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            for (int i5 : iArr) {
                if (i5 < i4 && i5 > r0) {
                    i4 = i5;
                }
            }
            int i52 = 0;
            i = 0;
            i2 = 0;
            for (i3 = 0; i3 < length; i3++) {
                int i6 = iArr[i3];
                if (i6 > i4) {
                    i2 |= 1 << ((length - 1) - i3);
                    i++;
                    i52 += i6;
                }
            }
            if (i == 3) {
                break;
            } else if (i <= 3) {
                return -1;
            } else {
                i2 = i4;
            }
        }
        int i7 = i;
        int i8 = i7;
        for (i = 0; i < length && i8 > 0; i++) {
            i3 = iArr[i];
            if (i3 > i4) {
                i8--;
                if ((i3 << 1) >= i52) {
                    return -1;
                }
            }
        }
        return i2;
    }

    public final Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int i2;
        int[] iArr = this.counters;
        Arrays.fill(iArr, 0);
        Object obj = this.decodeRowResult;
        obj.setLength(0);
        int[] findAsteriskPattern = findAsteriskPattern(bitArray, iArr);
        int nextSet = bitArray.getNextSet(findAsteriskPattern[1]);
        int size = bitArray.getSize();
        while (true) {
            OneDReader.recordPattern(bitArray, nextSet, iArr);
            int toNarrowWidePattern = toNarrowWidePattern(iArr);
            if (toNarrowWidePattern < 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            char patternToChar = patternToChar(toNarrowWidePattern);
            obj.append(patternToChar);
            toNarrowWidePattern = nextSet;
            for (int i3 : iArr) {
                toNarrowWidePattern += i3;
            }
            toNarrowWidePattern = bitArray.getNextSet(toNarrowWidePattern);
            if (patternToChar == '*') {
                break;
            }
            nextSet = toNarrowWidePattern;
        }
        obj.setLength(obj.length() - 1);
        int i4 = 0;
        for (int i32 : iArr) {
            i4 += i32;
        }
        if (toNarrowWidePattern == size || (((toNarrowWidePattern - nextSet) - i4) >> 1) >= i4) {
            if (this.usingCheckDigit) {
                int length = obj.length() - 1;
                i4 = 0;
                for (i2 = 0; i2 < length; i2++) {
                    i4 += ALPHABET_STRING.indexOf(this.decodeRowResult.charAt(i2));
                }
                if (obj.charAt(length) != ALPHABET[i4 % 43]) {
                    throw ChecksumException.getChecksumInstance();
                }
                obj.setLength(length);
            }
            if (obj.length() == 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            String decodeExtended = this.extendedMode ? decodeExtended(obj) : obj.toString();
            float f = ((float) (nextSet + toNarrowWidePattern)) / 2.0f;
            ResultPoint resultPoint = new ResultPoint(((float) (findAsteriskPattern[1] + findAsteriskPattern[0])) / 2.0f, (float) i);
            ResultPoint resultPoint2 = new ResultPoint(f, (float) i);
            return new Result(decodeExtended, null, new ResultPoint[]{resultPoint, resultPoint2}, BarcodeFormat.CODE_39);
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
