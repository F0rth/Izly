package com.google.zxing.aztec.encoder;

import android.support.v4.media.TransportMediator;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import java.lang.reflect.Array;
import java.util.Arrays;

public final class Encoder {
    private static final int[][] CHAR_MAP = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{5, 256}));
    public static final int DEFAULT_EC_PERCENT = 33;
    private static final int[][] LATCH_TABLE = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{6, 6}));
    private static final int[] NB_BITS = new int[33];
    private static final int[] NB_BITS_COMPACT = new int[5];
    private static final int[][] SHIFT_TABLE = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{6, 6}));
    private static final int TABLE_BINARY = 5;
    private static final int TABLE_DIGIT = 2;
    private static final int TABLE_LOWER = 1;
    private static final int TABLE_MIXED = 3;
    private static final int TABLE_PUNCT = 4;
    private static final int TABLE_UPPER = 0;
    private static final int[] WORD_SIZE = new int[]{4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    static {
        int i;
        int i2 = 1;
        CHAR_MAP[0][32] = 1;
        for (i = 65; i <= 90; i++) {
            CHAR_MAP[0][i] = (i - 65) + 2;
        }
        CHAR_MAP[1][32] = 1;
        for (i = 97; i <= 122; i++) {
            CHAR_MAP[1][i] = (i - 97) + 2;
        }
        CHAR_MAP[2][32] = 1;
        for (i = 48; i <= 57; i++) {
            CHAR_MAP[2][i] = (i - 48) + 2;
        }
        CHAR_MAP[2][44] = 12;
        CHAR_MAP[2][46] = 13;
        for (i = 0; i < 28; i++) {
            CHAR_MAP[3][new int[]{0, 32, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 27, 28, 29, 30, 31, 64, 92, 94, 95, 96, 124, TransportMediator.KEYCODE_MEDIA_PLAY, TransportMediator.KEYCODE_MEDIA_PAUSE}[i]] = i;
        }
        int[] iArr = new int[]{0, 13, 0, 0, 0, 0, 33, 39, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 91, 93, 123, 125};
        for (i = 0; i < 31; i++) {
            if (iArr[i] > 0) {
                CHAR_MAP[4][iArr[i]] = i;
            }
        }
        for (int[] fill : SHIFT_TABLE) {
            Arrays.fill(fill, -1);
        }
        for (int[] fill2 : LATCH_TABLE) {
            Arrays.fill(fill2, -1);
        }
        SHIFT_TABLE[0][4] = 0;
        LATCH_TABLE[0][1] = 28;
        LATCH_TABLE[0][3] = 29;
        LATCH_TABLE[0][2] = 30;
        SHIFT_TABLE[0][5] = 31;
        SHIFT_TABLE[1][4] = 0;
        SHIFT_TABLE[1][0] = 28;
        LATCH_TABLE[1][3] = 29;
        LATCH_TABLE[1][2] = 30;
        SHIFT_TABLE[1][5] = 31;
        SHIFT_TABLE[3][4] = 0;
        LATCH_TABLE[3][1] = 28;
        LATCH_TABLE[3][0] = 29;
        LATCH_TABLE[3][4] = 30;
        SHIFT_TABLE[3][5] = 31;
        LATCH_TABLE[4][0] = 31;
        SHIFT_TABLE[2][4] = 0;
        LATCH_TABLE[2][0] = 30;
        SHIFT_TABLE[2][0] = 31;
        for (i = 1; i < NB_BITS_COMPACT.length; i++) {
            NB_BITS_COMPACT[i] = ((i * 16) + 88) * i;
        }
        while (i2 < NB_BITS.length) {
            NB_BITS[i2] = ((i2 * 16) + 112) * i2;
            i2++;
        }
    }

    private Encoder() {
    }

    static int[] bitsToWords(BitArray bitArray, int i, int i2) {
        int[] iArr = new int[i2];
        int size = bitArray.getSize() / i;
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = 0;
            int i5 = 0;
            while (i5 < i) {
                i5++;
                i4 = (bitArray.get((i3 * i) + i5) ? 1 << ((i - i5) - 1) : 0) | i4;
            }
            iArr[i3] = i4;
        }
        return iArr;
    }

    static void drawBullsEye(BitMatrix bitMatrix, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3 += 2) {
            for (int i4 = i - i3; i4 <= i + i3; i4++) {
                bitMatrix.set(i4, i - i3);
                bitMatrix.set(i4, i + i3);
                bitMatrix.set(i - i3, i4);
                bitMatrix.set(i + i3, i4);
            }
        }
        bitMatrix.set(i - i2, i - i2);
        bitMatrix.set((i - i2) + 1, i - i2);
        bitMatrix.set(i - i2, (i - i2) + 1);
        bitMatrix.set(i + i2, i - i2);
        bitMatrix.set(i + i2, (i - i2) + 1);
        bitMatrix.set(i + i2, (i + i2) - 1);
    }

    static void drawModeMessage(BitMatrix bitMatrix, boolean z, int i, BitArray bitArray) {
        int i2 = 0;
        if (z) {
            while (i2 < 7) {
                if (bitArray.get(i2)) {
                    bitMatrix.set(((i / 2) - 3) + i2, (i / 2) - 5);
                }
                if (bitArray.get(i2 + 7)) {
                    bitMatrix.set((i / 2) + 5, ((i / 2) - 3) + i2);
                }
                if (bitArray.get(20 - i2)) {
                    bitMatrix.set(((i / 2) - 3) + i2, (i / 2) + 5);
                }
                if (bitArray.get(27 - i2)) {
                    bitMatrix.set((i / 2) - 5, ((i / 2) - 3) + i2);
                }
                i2++;
            }
            return;
        }
        while (i2 < 10) {
            if (bitArray.get(i2)) {
                bitMatrix.set((((i / 2) - 5) + i2) + (i2 / 5), (i / 2) - 7);
            }
            if (bitArray.get(i2 + 10)) {
                bitMatrix.set((i / 2) + 7, (((i / 2) - 5) + i2) + (i2 / 5));
            }
            if (bitArray.get(29 - i2)) {
                bitMatrix.set((((i / 2) - 5) + i2) + (i2 / 5), (i / 2) + 7);
            }
            if (bitArray.get(39 - i2)) {
                bitMatrix.set((i / 2) - 7, (((i / 2) - 5) + i2) + (i2 / 5));
            }
            i2++;
        }
    }

    public static AztecCode encode(byte[] bArr) {
        return encode(bArr, 33);
    }

    public static AztecCode encode(byte[] bArr, int i) {
        BitArray bitArray;
        int i2;
        int i3;
        boolean z;
        BitArray highLevelEncode = highLevelEncode(bArr);
        int size = ((highLevelEncode.getSize() * i) / 100) + 11;
        int size2 = highLevelEncode.getSize() + size;
        int i4 = 0;
        BitArray bitArray2 = null;
        int i5 = 0;
        int i6 = 1;
        while (i6 < NB_BITS_COMPACT.length) {
            if (NB_BITS_COMPACT[i6] >= size2) {
                if (i5 != WORD_SIZE[i6]) {
                    i5 = WORD_SIZE[i6];
                    bitArray2 = stuffBits(highLevelEncode, i5);
                }
                i4 = NB_BITS_COMPACT[i6];
                if (bitArray2.getSize() + size <= NB_BITS_COMPACT[i6]) {
                    break;
                }
            }
            i6++;
        }
        if (i6 == NB_BITS_COMPACT.length) {
            bitArray = bitArray2;
            i2 = i5;
            i5 = 1;
            while (i5 < NB_BITS.length) {
                if (NB_BITS[i5] >= size2) {
                    if (i2 != WORD_SIZE[i5]) {
                        i2 = WORD_SIZE[i5];
                        bitArray = stuffBits(highLevelEncode, i2);
                    }
                    i4 = NB_BITS[i5];
                    if (bitArray.getSize() + size <= NB_BITS[i5]) {
                        break;
                    }
                }
                i5++;
            }
            i3 = i2;
            i2 = i4;
            i4 = i5;
            z = false;
        } else {
            BitArray bitArray3 = bitArray2;
            i2 = i4;
            i4 = i6;
            bitArray = bitArray3;
            i3 = i5;
            z = true;
        }
        if (i4 == NB_BITS.length) {
            throw new IllegalArgumentException("Data too large for an Aztec code");
        }
        int i7;
        int size3 = ((bitArray.getSize() + i3) - 1) / i3;
        ReedSolomonEncoder reedSolomonEncoder = new ReedSolomonEncoder(getGF(i3));
        size = i2 / i3;
        int[] bitsToWords = bitsToWords(bitArray, i3, size);
        reedSolomonEncoder.encode(bitsToWords, size - size3);
        BitArray bitArray4 = new BitArray();
        bitArray4.appendBits(0, i2 % i3);
        for (int size4 : bitsToWords) {
            bitArray4.appendBits(size4, i3);
        }
        BitArray generateModeMessage = generateModeMessage(z, i4, size3);
        i2 = z ? (i4 * 4) + 11 : (i4 * 4) + 14;
        int[] iArr = new int[i2];
        if (z) {
            for (i6 = 0; i6 < iArr.length; i6++) {
                iArr[i6] = i6;
            }
            i6 = i2;
        } else {
            i6 = (i2 + 1) + ((((i2 / 2) - 1) / 15) * 2);
            i7 = i2 / 2;
            size4 = i6 / 2;
            for (i3 = 0; i3 < i7; i3++) {
                size2 = (i3 / 15) + i3;
                iArr[(i7 - i3) - 1] = (size4 - size2) - 1;
                iArr[i7 + i3] = (size2 + size4) + 1;
            }
        }
        BitMatrix bitMatrix = new BitMatrix(i6);
        size2 = 0;
        for (int i8 = 0; i8 < i4; i8++) {
            i3 = z ? ((i4 - i8) * 4) + 9 : ((i4 - i8) * 4) + 12;
            for (size4 = 0; size4 < i3; size4++) {
                int i9 = size4 * 2;
                for (i7 = 0; i7 < 2; i7++) {
                    if (bitArray4.get((size2 + i9) + i7)) {
                        bitMatrix.set(iArr[(i8 * 2) + i7], iArr[(i8 * 2) + size4]);
                    }
                    if (bitArray4.get((((i3 * 2) + size2) + i9) + i7)) {
                        bitMatrix.set(iArr[(i8 * 2) + size4], iArr[((i2 - 1) - (i8 * 2)) - i7]);
                    }
                    if (bitArray4.get((((i3 * 4) + size2) + i9) + i7)) {
                        bitMatrix.set(iArr[((i2 - 1) - (i8 * 2)) - i7], iArr[((i2 - 1) - (i8 * 2)) - size4]);
                    }
                    if (bitArray4.get((((i3 * 6) + size2) + i9) + i7)) {
                        bitMatrix.set(iArr[((i2 - 1) - (i8 * 2)) - size4], iArr[(i8 * 2) + i7]);
                    }
                }
            }
            size2 = (i3 * 8) + size2;
        }
        drawModeMessage(bitMatrix, z, i6, generateModeMessage);
        if (z) {
            drawBullsEye(bitMatrix, i6 / 2, 5);
        } else {
            drawBullsEye(bitMatrix, i6 / 2, 7);
            i3 = 0;
            i7 = 0;
            while (i3 < (i2 / 2) - 1) {
                for (size4 = (i6 / 2) & 1; size4 < i6; size4 += 2) {
                    bitMatrix.set((i6 / 2) - i7, size4);
                    bitMatrix.set((i6 / 2) + i7, size4);
                    bitMatrix.set(size4, (i6 / 2) - i7);
                    bitMatrix.set(size4, (i6 / 2) + i7);
                }
                i3 += 15;
                i7 += 16;
            }
        }
        AztecCode aztecCode = new AztecCode();
        aztecCode.setCompact(z);
        aztecCode.setSize(i6);
        aztecCode.setLayers(i4);
        aztecCode.setCodeWords(size3);
        aztecCode.setMatrix(bitMatrix);
        return aztecCode;
    }

    static BitArray generateCheckWords(BitArray bitArray, int i, int i2) {
        int i3 = 0;
        int size = ((bitArray.getSize() + i2) - 1) / i2;
        for (int size2 = (size * i2) - bitArray.getSize(); size2 > 0; size2--) {
            bitArray.appendBit(true);
        }
        ReedSolomonEncoder reedSolomonEncoder = new ReedSolomonEncoder(getGF(i2));
        int i4 = i / i2;
        int[] bitsToWords = bitsToWords(bitArray, i2, i4);
        reedSolomonEncoder.encode(bitsToWords, i4 - size);
        BitArray bitArray2 = new BitArray();
        bitArray2.appendBits(0, i % i2);
        size = bitsToWords.length;
        while (i3 < size) {
            bitArray2.appendBits(bitsToWords[i3], i2);
            i3++;
        }
        return bitArray2;
    }

    static BitArray generateModeMessage(boolean z, int i, int i2) {
        BitArray bitArray = new BitArray();
        if (z) {
            bitArray.appendBits(i - 1, 2);
            bitArray.appendBits(i2 - 1, 6);
            return generateCheckWords(bitArray, 28, 4);
        }
        bitArray.appendBits(i - 1, 5);
        bitArray.appendBits(i2 - 1, 11);
        return generateCheckWords(bitArray, 40, 4);
    }

    static GenericGF getGF(int i) {
        switch (i) {
            case 4:
                return GenericGF.AZTEC_PARAM;
            case 6:
                return GenericGF.AZTEC_DATA_6;
            case 8:
                return GenericGF.AZTEC_DATA_8;
            case 10:
                return GenericGF.AZTEC_DATA_10;
            case 12:
                return GenericGF.AZTEC_DATA_12;
            default:
                return null;
        }
    }

    static BitArray highLevelEncode(byte[] bArr) {
        BitArray bitArray = new BitArray();
        int i = 0;
        int[] iArr = new int[5];
        int[] iArr2 = new int[5];
        int i2 = 0;
        while (i2 < bArr.length) {
            byte b;
            Object obj;
            int i3 = bArr[i2] & 255;
            int i4 = i2 < bArr.length + -1 ? bArr[i2 + 1] & 255 : 0;
            int i5 = 0;
            if (i3 == 13 && i4 == 10) {
                i5 = 2;
            } else if (i3 == 46 && i4 == 32) {
                i5 = 3;
            } else if (i3 == 44 && i4 == 32) {
                i5 = 4;
            } else if (i3 == 58 && i4 == 32) {
                i5 = 5;
            }
            if (i5 > 0) {
                if (i == 4) {
                    outputWord(bitArray, 4, i5);
                    i2++;
                } else if (SHIFT_TABLE[i][4] >= 0) {
                    outputWord(bitArray, i, SHIFT_TABLE[i][4]);
                    outputWord(bitArray, 4, i5);
                    i2++;
                } else if (LATCH_TABLE[i][4] >= 0) {
                    outputWord(bitArray, i, LATCH_TABLE[i][4]);
                    outputWord(bitArray, 4, i5);
                    i = 4;
                    i2++;
                }
                i2++;
            }
            int i6 = -1;
            int i7 = -1;
            int i8 = -1;
            int i9 = 0;
            while (i9 < 5) {
                iArr[i9] = CHAR_MAP[i9][i3];
                if (iArr[i9] > 0 && i6 < 0) {
                    i6 = i9;
                }
                i5 = (i7 >= 0 || iArr[i9] <= 0 || SHIFT_TABLE[i][i9] < 0) ? i7 : i9;
                iArr2[i9] = CHAR_MAP[i9][i4];
                i7 = (i8 >= 0 || iArr[i9] <= 0 || ((i4 != 0 && iArr2[i9] <= 0) || LATCH_TABLE[i][i9] < 0)) ? i8 : i9;
                i9++;
                i8 = i7;
                i7 = i5;
            }
            if (i7 < 0 && i8 < 0) {
                i5 = 0;
                while (i5 < 5) {
                    if (iArr[i5] <= 0 || LATCH_TABLE[i][i5] < 0) {
                        i5++;
                    } else {
                        if (iArr[i] > 0) {
                            outputWord(bitArray, i, iArr[i]);
                        } else if (i5 >= 0) {
                            outputWord(bitArray, i, LATCH_TABLE[i][i5]);
                            outputWord(bitArray, i5, iArr[i5]);
                            i = i5;
                        } else if (i7 < 0) {
                            outputWord(bitArray, i, SHIFT_TABLE[i][i7]);
                            outputWord(bitArray, i7, iArr[i7]);
                        } else {
                            if (i6 >= 0) {
                                if (i == 4) {
                                    outputWord(bitArray, 4, LATCH_TABLE[4][0]);
                                    i = 0;
                                } else if (i == 2) {
                                    outputWord(bitArray, 2, LATCH_TABLE[2][0]);
                                    i = 0;
                                }
                                i2--;
                            }
                            i9 = i2 + 1;
                            i5 = 0;
                            while (i9 < bArr.length) {
                                b = bArr[i9];
                                obj = 1;
                                i8 = 0;
                                while (i8 < 5) {
                                    if (CHAR_MAP[i8][b & 255] <= 0) {
                                        obj = null;
                                        if (obj == null) {
                                            i5 = 0;
                                        } else if (i5 <= 0) {
                                            i9 -= i5;
                                            i5 = i9 - i2;
                                            switch (i) {
                                                case 0:
                                                case 1:
                                                case 3:
                                                    outputWord(bitArray, i, SHIFT_TABLE[i][5]);
                                                    break;
                                                case 2:
                                                    outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                                    i = 0;
                                                    outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                                    break;
                                                case 4:
                                                    outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                                    i = 0;
                                                    outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                                    break;
                                            }
                                            if (i5 >= 32 && i5 < 63) {
                                                i5 = 31;
                                            }
                                            if (i5 > 542) {
                                                i5 = 542;
                                            }
                                            if (i5 >= 32) {
                                                bitArray.appendBits(i5, 5);
                                            } else {
                                                bitArray.appendBits(i5 - 31, 16);
                                            }
                                            while (i5 > 0) {
                                                bitArray.appendBits(bArr[i2], 8);
                                                i5--;
                                                i2++;
                                            }
                                            i2--;
                                        } else {
                                            i5++;
                                        }
                                        i9++;
                                    } else {
                                        i8++;
                                    }
                                }
                                if (obj == null) {
                                    i5 = 0;
                                } else if (i5 <= 0) {
                                    i5++;
                                } else {
                                    i9 -= i5;
                                    i5 = i9 - i2;
                                    switch (i) {
                                        case 0:
                                        case 1:
                                        case 3:
                                            outputWord(bitArray, i, SHIFT_TABLE[i][5]);
                                            break;
                                        case 2:
                                            outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                            i = 0;
                                            outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                            break;
                                        case 4:
                                            outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                            i = 0;
                                            outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                            break;
                                    }
                                    i5 = 31;
                                    if (i5 > 542) {
                                        i5 = 542;
                                    }
                                    if (i5 >= 32) {
                                        bitArray.appendBits(i5 - 31, 16);
                                    } else {
                                        bitArray.appendBits(i5, 5);
                                    }
                                    while (i5 > 0) {
                                        bitArray.appendBits(bArr[i2], 8);
                                        i5--;
                                        i2++;
                                    }
                                    i2--;
                                }
                                i9++;
                            }
                            i5 = i9 - i2;
                            switch (i) {
                                case 0:
                                case 1:
                                case 3:
                                    outputWord(bitArray, i, SHIFT_TABLE[i][5]);
                                    break;
                                case 2:
                                    outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                    i = 0;
                                    outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                    break;
                                case 4:
                                    outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                    i = 0;
                                    outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                    break;
                            }
                            i5 = 31;
                            if (i5 > 542) {
                                i5 = 542;
                            }
                            if (i5 >= 32) {
                                bitArray.appendBits(i5, 5);
                            } else {
                                bitArray.appendBits(i5 - 31, 16);
                            }
                            while (i5 > 0) {
                                bitArray.appendBits(bArr[i2], 8);
                                i5--;
                                i2++;
                            }
                            i2--;
                        }
                        i2++;
                    }
                }
            }
            i5 = i8;
            if (iArr[i] > 0) {
                outputWord(bitArray, i, iArr[i]);
            } else if (i5 >= 0) {
                outputWord(bitArray, i, LATCH_TABLE[i][i5]);
                outputWord(bitArray, i5, iArr[i5]);
                i = i5;
            } else if (i7 < 0) {
                if (i6 >= 0) {
                    if (i == 4) {
                        outputWord(bitArray, 4, LATCH_TABLE[4][0]);
                        i = 0;
                    } else if (i == 2) {
                        outputWord(bitArray, 2, LATCH_TABLE[2][0]);
                        i = 0;
                    }
                    i2--;
                }
                i9 = i2 + 1;
                i5 = 0;
                while (i9 < bArr.length) {
                    b = bArr[i9];
                    obj = 1;
                    i8 = 0;
                    while (i8 < 5) {
                        if (CHAR_MAP[i8][b & 255] <= 0) {
                            i8++;
                        } else {
                            obj = null;
                            if (obj == null) {
                                i5 = 0;
                            } else if (i5 <= 0) {
                                i9 -= i5;
                                i5 = i9 - i2;
                                switch (i) {
                                    case 0:
                                    case 1:
                                    case 3:
                                        outputWord(bitArray, i, SHIFT_TABLE[i][5]);
                                        break;
                                    case 2:
                                        outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                        i = 0;
                                        outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                        break;
                                    case 4:
                                        outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                        i = 0;
                                        outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                        break;
                                }
                                i5 = 31;
                                if (i5 > 542) {
                                    i5 = 542;
                                }
                                if (i5 >= 32) {
                                    bitArray.appendBits(i5 - 31, 16);
                                } else {
                                    bitArray.appendBits(i5, 5);
                                }
                                while (i5 > 0) {
                                    bitArray.appendBits(bArr[i2], 8);
                                    i5--;
                                    i2++;
                                }
                                i2--;
                            } else {
                                i5++;
                            }
                            i9++;
                        }
                    }
                    if (obj == null) {
                        i5 = 0;
                    } else if (i5 <= 0) {
                        i5++;
                    } else {
                        i9 -= i5;
                        i5 = i9 - i2;
                        switch (i) {
                            case 0:
                            case 1:
                            case 3:
                                outputWord(bitArray, i, SHIFT_TABLE[i][5]);
                                break;
                            case 2:
                                outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                i = 0;
                                outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                break;
                            case 4:
                                outputWord(bitArray, i, LATCH_TABLE[i][0]);
                                i = 0;
                                outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                                break;
                        }
                        i5 = 31;
                        if (i5 > 542) {
                            i5 = 542;
                        }
                        if (i5 >= 32) {
                            bitArray.appendBits(i5, 5);
                        } else {
                            bitArray.appendBits(i5 - 31, 16);
                        }
                        while (i5 > 0) {
                            bitArray.appendBits(bArr[i2], 8);
                            i5--;
                            i2++;
                        }
                        i2--;
                    }
                    i9++;
                }
                i5 = i9 - i2;
                switch (i) {
                    case 0:
                    case 1:
                    case 3:
                        outputWord(bitArray, i, SHIFT_TABLE[i][5]);
                        break;
                    case 2:
                        outputWord(bitArray, i, LATCH_TABLE[i][0]);
                        i = 0;
                        outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                        break;
                    case 4:
                        outputWord(bitArray, i, LATCH_TABLE[i][0]);
                        i = 0;
                        outputWord(bitArray, 0, SHIFT_TABLE[0][5]);
                        break;
                }
                i5 = 31;
                if (i5 > 542) {
                    i5 = 542;
                }
                if (i5 >= 32) {
                    bitArray.appendBits(i5 - 31, 16);
                } else {
                    bitArray.appendBits(i5, 5);
                }
                while (i5 > 0) {
                    bitArray.appendBits(bArr[i2], 8);
                    i5--;
                    i2++;
                }
                i2--;
            } else {
                outputWord(bitArray, i, SHIFT_TABLE[i][i7]);
                outputWord(bitArray, i7, iArr[i7]);
            }
            i2++;
        }
        return bitArray;
    }

    static void outputWord(BitArray bitArray, int i, int i2) {
        if (i == 2) {
            bitArray.appendBits(i2, 4);
        } else if (i < 5) {
            bitArray.appendBits(i2, 5);
        } else {
            bitArray.appendBits(i2, 8);
        }
    }

    static BitArray stuffBits(BitArray bitArray, int i) {
        BitArray bitArray2 = new BitArray();
        int size = bitArray.getSize();
        int i2 = (1 << i) - 2;
        int i3 = 0;
        while (i3 < size) {
            int i4 = 0;
            int i5 = 0;
            while (i5 < i) {
                if (i3 + i5 >= size || bitArray.get(i3 + i5)) {
                    i4 |= 1 << ((i - 1) - i5);
                }
                i5++;
            }
            if ((i4 & i2) == i2) {
                bitArray2.appendBits(i4 & i2, i);
                i4 = i3 - 1;
            } else if ((i4 & i2) == 0) {
                bitArray2.appendBits(i4 | 1, i);
                i4 = i3 - 1;
            } else {
                bitArray2.appendBits(i4, i);
                i4 = i3;
            }
            i3 = i4 + i;
        }
        return bitArray2;
    }
}
