package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class Code128Writer extends OneDimensionalCodeWriter {
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_B = 100;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final char ESCAPE_FNC_1 = 'ñ';
    private static final char ESCAPE_FNC_2 = 'ò';
    private static final char ESCAPE_FNC_3 = 'ó';
    private static final char ESCAPE_FNC_4 = 'ô';

    private static boolean isDigits(CharSequence charSequence, int i, int i2) {
        int i3 = i + i2;
        int length = charSequence.length();
        while (i < i3 && i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < '0' || charAt > '9') {
                if (charAt != ESCAPE_FNC_1) {
                    return false;
                }
                i3++;
            }
            i++;
        }
        return i3 <= length;
    }

    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got " + barcodeFormat);
    }

    public final boolean[] encode(String str) {
        int i = 0;
        int length = str.length();
        if (length <= 0 || length > 80) {
            throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
        }
        int i2;
        for (i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt < ' ' || charAt > '~') {
                switch (charAt) {
                    case 'ñ':
                    case 'ò':
                    case 'ó':
                    case 'ô':
                        break;
                    default:
                        throw new IllegalArgumentException("Bad character in input: " + charAt);
                }
            }
        }
        Collection<int[]> arrayList = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        int i5 = 1;
        int i6 = 0;
        while (i4 < length) {
            int i7 = isDigits(str, i4, i6 == 99 ? 2 : 4) ? 99 : 100;
            if (i7 == i6) {
                if (i6 != 100) {
                    switch (str.charAt(i4)) {
                        case 'ñ':
                            i2 = 102;
                            i7 = i4 + 1;
                            break;
                        case 'ò':
                            i2 = 97;
                            i7 = i4 + 1;
                            break;
                        case 'ó':
                            i2 = 96;
                            i7 = i4 + 1;
                            break;
                        case 'ô':
                            i7 = i4 + 1;
                            i2 = 100;
                            break;
                        default:
                            i2 = Integer.parseInt(str.substring(i4, i4 + 2));
                            i7 = i4 + 2;
                            break;
                    }
                }
                i2 = str.charAt(i4) - 32;
                i7 = i4 + 1;
            } else {
                i2 = i6 == 0 ? i7 == 100 ? 104 : 105 : i7;
                i6 = i7;
                i7 = i4;
            }
            arrayList.add(Code128Reader.CODE_PATTERNS[i2]);
            i3 = (i2 * i5) + i3;
            i5 = i7 != 0 ? i5 + 1 : i5;
            i4 = i7;
        }
        arrayList.add(Code128Reader.CODE_PATTERNS[i3 % 103]);
        arrayList.add(Code128Reader.CODE_PATTERNS[106]);
        int i8 = 0;
        for (int[] iArr : arrayList) {
            i6 = 0;
            while (i6 < iArr.length) {
                i7 = iArr[i6] + i8;
                i6++;
                i8 = i7;
            }
        }
        boolean[] zArr = new boolean[i8];
        for (int[] iArr2 : arrayList) {
            i += OneDimensionalCodeWriter.appendPattern(zArr, i, iArr2, true);
        }
        return zArr;
    }
}
