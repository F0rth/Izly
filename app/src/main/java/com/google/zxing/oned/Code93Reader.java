package com.google.zxing.oned;

import android.support.v4.media.TransportMediator;
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

public final class Code93Reader extends OneDReader {
    private static final char[] ALPHABET = ALPHABET_STRING.toCharArray();
    private static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
    private static final int ASTERISK_ENCODING;
    private static final int[] CHARACTER_ENCODINGS;
    private final int[] counters = new int[6];
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    static {
        int[] iArr = new int[]{276, 328, 324, 322, 296, 292, 290, 336, 274, 266, 424, 420, 418, 404, 402, 394, 360, 356, 354, 308, 282, 344, 332, 326, 300, 278, 436, 434, 428, 422, 406, 410, 364, 358, 310, 314, 302, 468, 466, 458, 366, 374, 430, 294, 474, 470, 306, 350};
        CHARACTER_ENCODINGS = iArr;
        ASTERISK_ENCODING = iArr[47];
    }

    private static void checkChecksums(CharSequence charSequence) throws ChecksumException {
        int length = charSequence.length();
        checkOneChecksum(charSequence, length - 2, 20);
        checkOneChecksum(charSequence, length - 1, 15);
    }

    private static void checkOneChecksum(CharSequence charSequence, int i, int i2) throws ChecksumException {
        int i3 = 0;
        int i4 = 1;
        int i5 = i - 1;
        while (i5 >= 0) {
            int indexOf = ALPHABET_STRING.indexOf(charSequence.charAt(i5));
            int i6 = i4 + 1;
            if (i6 > i2) {
                i6 = 1;
            }
            i5--;
            i3 += i4 * indexOf;
            i4 = i6;
        }
        if (charSequence.charAt(i) != ALPHABET[i3 % 47]) {
            throw ChecksumException.getChecksumInstance();
        }
    }

    private static String decodeExtended(CharSequence charSequence) throws FormatException {
        int length = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            int i2;
            char charAt = charSequence.charAt(i);
            if (charAt < 'a' || charAt > 'd') {
                stringBuilder.append(charAt);
                i2 = i;
            } else if (i >= length - 1) {
                throw FormatException.getFormatInstance();
            } else {
                char charAt2 = charSequence.charAt(i + 1);
                switch (charAt) {
                    case 'a':
                        if (charAt2 >= 'A' && charAt2 <= 'Z') {
                            charAt = (char) (charAt2 - 64);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                        break;
                    case 'b':
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
                    case 'c':
                        if (charAt2 >= 'A' && charAt2 <= 'O') {
                            charAt = (char) (charAt2 - 32);
                            break;
                        } else if (charAt2 == 'Z') {
                            charAt = ':';
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    case 'd':
                        if (charAt2 >= 'A' && charAt2 <= 'Z') {
                            charAt = (char) (charAt2 + 32);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                        break;
                    default:
                        charAt = '\u0000';
                        break;
                }
                stringBuilder.append(charAt);
                i2 = i + 1;
            }
            i = i2 + 1;
        }
        return stringBuilder.toString();
    }

    private int[] findAsteriskPattern(BitArray bitArray) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        Arrays.fill(this.counters, 0);
        Object obj = this.counters;
        int length = obj.length;
        int i = 0;
        int i2 = nextSet;
        nextSet = 0;
        for (int i3 = nextSet; i3 < size; i3++) {
            if ((bitArray.get(i3) ^ i) != 0) {
                obj[nextSet] = obj[nextSet] + 1;
            } else {
                if (nextSet != length - 1) {
                    nextSet++;
                } else if (toPattern(obj) == ASTERISK_ENCODING) {
                    return new int[]{i2, i3};
                } else {
                    i2 += obj[0] + obj[1];
                    System.arraycopy(obj, 2, obj, 0, length - 2);
                    obj[length - 2] = null;
                    obj[length - 1] = null;
                    nextSet--;
                }
                obj[nextSet] = 1;
                i = i == 0 ? 1 : 0;
            }
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

    private static int toPattern(int[] iArr) {
        int i;
        int length = iArr.length;
        int i2 = 0;
        for (int i3 : iArr) {
            i2 = i3 + i2;
        }
        int i4 = 0;
        for (i3 = 0; i3 < length; i3++) {
            int i5 = ((iArr[i3] << 8) * 9) / i2;
            int i6 = i5 >> 8;
            i5 = (i5 & 255) > TransportMediator.KEYCODE_MEDIA_PAUSE ? i6 + 1 : i6;
            if (i5 <= 0 || i5 > 4) {
                return -1;
            }
            if ((i3 & 1) == 0) {
                i6 = 0;
                while (i6 < i5) {
                    i6++;
                    i4 = (i4 << 1) | 1;
                }
            } else {
                i4 <<= i5;
            }
        }
        return i4;
    }

    public final Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int[] findAsteriskPattern = findAsteriskPattern(bitArray);
        int nextSet = bitArray.getNextSet(findAsteriskPattern[1]);
        int size = bitArray.getSize();
        int[] iArr = this.counters;
        Arrays.fill(iArr, 0);
        CharSequence charSequence = this.decodeRowResult;
        charSequence.setLength(0);
        while (true) {
            OneDReader.recordPattern(bitArray, nextSet, iArr);
            int toPattern = toPattern(iArr);
            if (toPattern < 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            char patternToChar = patternToChar(toPattern);
            charSequence.append(patternToChar);
            toPattern = nextSet;
            for (int i2 : iArr) {
                toPattern += i2;
            }
            toPattern = bitArray.getNextSet(toPattern);
            if (patternToChar == '*') {
                break;
            }
            nextSet = toPattern;
        }
        charSequence.deleteCharAt(charSequence.length() - 1);
        if (toPattern == size || !bitArray.get(toPattern)) {
            throw NotFoundException.getNotFoundInstance();
        } else if (charSequence.length() < 2) {
            throw NotFoundException.getNotFoundInstance();
        } else {
            checkChecksums(charSequence);
            charSequence.setLength(charSequence.length() - 2);
            String decodeExtended = decodeExtended(charSequence);
            float f = ((float) (nextSet + toPattern)) / 2.0f;
            ResultPoint resultPoint = new ResultPoint(((float) (findAsteriskPattern[0] + findAsteriskPattern[1])) / 2.0f, (float) i);
            ResultPoint resultPoint2 = new ResultPoint(f, (float) i);
            return new Result(decodeExtended, null, new ResultPoint[]{resultPoint, resultPoint2}, BarcodeFormat.CODE_93);
        }
    }
}
