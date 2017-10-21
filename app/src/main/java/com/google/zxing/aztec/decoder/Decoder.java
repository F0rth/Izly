package com.google.zxing.aztec.decoder;

import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.location.places.Place;
import com.google.zxing.FormatException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Decoder {
    private static final String[] DIGIT_TABLE = new String[]{"CTRL_PS", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ",", ".", "CTRL_UL", "CTRL_US"};
    private static final String[] LOWER_TABLE = new String[]{"CTRL_PS", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
    private static final String[] MIXED_TABLE = new String[]{"CTRL_PS", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b", "\t", "\n", "\u000b", "\f", "\r", "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@", "\\", "^", "_", "`", "|", "~", "", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"};
    private static final int[] NB_BITS = new int[]{0, 128, 288, 480, 704, 960, 1248, 1568, 1920, 2304, 2720, 3168, 3648, 4160, 4704, 5280, 5888, 6528, 7200, 7904, 8640, 9408, 10208, 11040, 11904, 12800, 13728, 14688, 15680, 16704, 17760, 18848, 19968};
    private static final int[] NB_BITS_COMPACT = new int[]{0, 104, 240, 408, 608};
    private static final int[] NB_DATABLOCK = new int[]{0, 21, 48, 60, 88, 120, 156, 196, 240, 230, 272, 316, 364, 416, 470, 528, 588, 652, 720, 790, 864, 940, Place.TYPE_ROUTE, 920, 992, 1066, 1144, 1224, 1306, 1392, 1480, 1570, 1664};
    private static final int[] NB_DATABLOCK_COMPACT = new int[]{0, 17, 40, 51, 76};
    private static final String[] PUNCT_TABLE = new String[]{"", "\r", "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL"};
    private static final String[] UPPER_TABLE = new String[]{"CTRL_PS", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, InteractionsApi.B4SSATUS_ACCEPTED, "B", InteractionsApi.B4SCONNECTION_CELLULAR, "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", InteractionsApi.B4SCONNECTION_NOCONNECTION, InteractionsApi.B4SSATUS_OPENED, "P", "Q", InteractionsApi.B4SSATUS_REJECTED, InteractionsApi.B4SSATUS_SENT, "T", "U", "V", InteractionsApi.B4SCONNECTION_WIFI, "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
    private int codewordSize;
    private AztecDetectorResult ddata;
    private int invertedBitCount;
    private int numCodewords;

    enum Table {
        UPPER,
        LOWER,
        MIXED,
        DIGIT,
        PUNCT,
        BINARY
    }

    private boolean[] correctBits(boolean[] zArr) throws FormatException {
        GenericGF genericGF;
        int i;
        int i2;
        int i3;
        if (this.ddata.getNbLayers() <= 2) {
            this.codewordSize = 6;
            genericGF = GenericGF.AZTEC_DATA_6;
        } else if (this.ddata.getNbLayers() <= 8) {
            this.codewordSize = 8;
            genericGF = GenericGF.AZTEC_DATA_8;
        } else if (this.ddata.getNbLayers() <= 22) {
            this.codewordSize = 10;
            genericGF = GenericGF.AZTEC_DATA_10;
        } else {
            this.codewordSize = 12;
            genericGF = GenericGF.AZTEC_DATA_12;
        }
        int nbDatablocks = this.ddata.getNbDatablocks();
        if (this.ddata.isCompact()) {
            i = NB_BITS_COMPACT[this.ddata.getNbLayers()] - (this.numCodewords * this.codewordSize);
            i2 = NB_DATABLOCK_COMPACT[this.ddata.getNbLayers()] - nbDatablocks;
        } else {
            i = NB_BITS[this.ddata.getNbLayers()] - (this.numCodewords * this.codewordSize);
            i2 = NB_DATABLOCK[this.ddata.getNbLayers()] - nbDatablocks;
        }
        int[] iArr = new int[this.numCodewords];
        for (i3 = 0; i3 < this.numCodewords; i3++) {
            int i4 = 1;
            for (int i5 = 1; i5 <= this.codewordSize; i5++) {
                if (zArr[(((this.codewordSize * i3) + this.codewordSize) - i5) + i]) {
                    iArr[i3] = iArr[i3] + i4;
                }
                i4 <<= 1;
            }
        }
        try {
            new ReedSolomonDecoder(genericGF).decode(iArr, i2);
            this.invertedBitCount = 0;
            boolean[] zArr2 = new boolean[(this.codewordSize * nbDatablocks)];
            int i6 = 0;
            i2 = 0;
            while (i6 < nbDatablocks) {
                i3 = 0;
                i4 = i2;
                i2 = 1 << (this.codewordSize - 1);
                int i7 = 0;
                for (i = 0; i < this.codewordSize; i++) {
                    boolean z = (iArr[i6] & i2) == i2;
                    if (i3 != this.codewordSize - 1) {
                        if (i7 == z) {
                            i3++;
                        } else {
                            boolean z2 = z;
                            i3 = 1;
                        }
                        zArr2[((this.codewordSize * i6) + i) - i4] = z;
                    } else if (z == i7) {
                        throw FormatException.getFormatInstance();
                    } else {
                        i4++;
                        this.invertedBitCount++;
                        i7 = 0;
                        i3 = 0;
                    }
                    i2 >>>= 1;
                }
                i6++;
                i2 = i4;
            }
            return zArr2;
        } catch (ReedSolomonException e) {
            throw FormatException.getFormatInstance();
        }
    }

    private boolean[] extractBits(BitMatrix bitMatrix) throws FormatException {
        boolean[] zArr;
        if (this.ddata.isCompact()) {
            if (this.ddata.getNbLayers() > NB_BITS_COMPACT.length) {
                throw FormatException.getFormatInstance();
            }
            zArr = new boolean[NB_BITS_COMPACT[this.ddata.getNbLayers()]];
            this.numCodewords = NB_DATABLOCK_COMPACT[this.ddata.getNbLayers()];
        } else if (this.ddata.getNbLayers() > NB_BITS.length) {
            throw FormatException.getFormatInstance();
        } else {
            zArr = new boolean[NB_BITS[this.ddata.getNbLayers()]];
            this.numCodewords = NB_DATABLOCK[this.ddata.getNbLayers()];
        }
        int i = 0;
        int nbLayers = this.ddata.getNbLayers();
        int i2 = 0;
        int height = bitMatrix.getHeight();
        while (nbLayers != 0) {
            int i3;
            int i4 = 0;
            for (i3 = 0; i3 < (height * 2) - 4; i3++) {
                zArr[i2 + i3] = bitMatrix.get(i + i4, (i3 / 2) + i);
                zArr[(((height * 2) + i2) - 4) + i3] = bitMatrix.get((i3 / 2) + i, ((i + height) - 1) - i4);
                i4 = (i4 + 1) % 2;
            }
            i4 = 0;
            for (i3 = (height * 2) + 1; i3 > 5; i3--) {
                zArr[((((height * 4) + i2) - 8) + ((height * 2) - i3)) + 1] = bitMatrix.get(((i + height) - 1) - i4, ((i3 / 2) + i) - 1);
                zArr[((((height * 6) + i2) - 12) + ((height * 2) - i3)) + 1] = bitMatrix.get(((i3 / 2) + i) - 1, i + i4);
                i4 = (i4 + 1) % 2;
            }
            nbLayers--;
            i2 = ((height * 8) - 16) + i2;
            height -= 4;
            i += 2;
        }
        return zArr;
    }

    private static String getCharacter(Table table, int i) {
        switch (table) {
            case UPPER:
                return UPPER_TABLE[i];
            case LOWER:
                return LOWER_TABLE[i];
            case MIXED:
                return MIXED_TABLE[i];
            case PUNCT:
                return PUNCT_TABLE[i];
            case DIGIT:
                return DIGIT_TABLE[i];
            default:
                return "";
        }
    }

    private String getEncodedData(boolean[] zArr) throws FormatException {
        int nbDatablocks = (this.codewordSize * this.ddata.getNbDatablocks()) - this.invertedBitCount;
        if (nbDatablocks > zArr.length) {
            throw FormatException.getFormatInstance();
        }
        Table table = Table.UPPER;
        Table table2 = Table.UPPER;
        StringBuilder stringBuilder = new StringBuilder(20);
        Object obj = null;
        Object obj2 = null;
        Object obj3 = null;
        int i = 0;
        Object obj4 = null;
        Table table3 = table;
        while (obj == null) {
            Object obj5;
            Object obj6;
            int i2;
            if (obj4 != null) {
                table = table3;
                obj5 = 1;
            } else {
                table = table2;
                obj5 = obj2;
            }
            int i3;
            int i4;
            if (obj3 == null) {
                if (table2 == Table.BINARY) {
                    if (nbDatablocks - i < 8) {
                        break;
                    }
                    stringBuilder.append((char) readCode(zArr, i, 8));
                    Table table4 = table2;
                    obj6 = obj4;
                    i2 = i + 8;
                    table3 = table4;
                } else {
                    i3 = 5;
                    if (table2 == Table.DIGIT) {
                        i3 = 4;
                    }
                    if (nbDatablocks - i < i3) {
                        break;
                    }
                    i4 = i + i3;
                    String character = getCharacter(table2, readCode(zArr, i, i3));
                    if (character.startsWith("CTRL_")) {
                        table3 = getTable(character.charAt(5));
                        if (character.charAt(6) == 'S') {
                            obj6 = 1;
                            if (character.charAt(5) == 'B') {
                                obj3 = 1;
                                i2 = i4;
                            } else {
                                i2 = i4;
                            }
                        }
                    } else {
                        stringBuilder.append(character);
                        table3 = table2;
                    }
                    obj6 = obj4;
                    i2 = i4;
                }
            } else if (nbDatablocks - i < 5) {
                break;
            } else {
                Object obj7;
                i3 = readCode(zArr, i, 5);
                int i5 = i + 5;
                if (i3 == 0) {
                    if (nbDatablocks - i5 < 11) {
                        break;
                    }
                    i3 = readCode(zArr, i5, 11) + 31;
                    i5 += 11;
                }
                i4 = i5;
                for (i5 = 0; i5 < i3; i5++) {
                    if (nbDatablocks - i4 < 8) {
                        obj7 = 1;
                        break;
                    }
                    stringBuilder.append((char) readCode(zArr, i4, 8));
                    i4 += 8;
                }
                obj7 = obj;
                obj3 = null;
                obj = obj7;
                table3 = table2;
                obj6 = obj4;
                i2 = i4;
            }
            if (obj5 != null) {
                obj2 = null;
                table3 = table;
                i = i2;
                obj4 = null;
                table2 = table;
            } else {
                i = i2;
                obj2 = obj5;
                obj4 = obj6;
                table2 = table3;
                table3 = table;
            }
        }
        return stringBuilder.toString();
    }

    private static Table getTable(char c) {
        switch (c) {
            case 'B':
                return Table.BINARY;
            case 'D':
                return Table.DIGIT;
            case 'L':
                return Table.LOWER;
            case 'M':
                return Table.MIXED;
            case 'P':
                return Table.PUNCT;
            default:
                return Table.UPPER;
        }
    }

    private static int readCode(boolean[] zArr, int i, int i2) {
        int i3 = 0;
        for (int i4 = i; i4 < i + i2; i4++) {
            i3 <<= 1;
            if (zArr[i4]) {
                i3++;
            }
        }
        return i3;
    }

    private static BitMatrix removeDashedLines(BitMatrix bitMatrix) {
        int width = ((((bitMatrix.getWidth() - 1) / 2) / 16) * 2) + 1;
        BitMatrix bitMatrix2 = new BitMatrix(bitMatrix.getWidth() - width, bitMatrix.getHeight() - width);
        width = 0;
        for (int i = 0; i < bitMatrix.getWidth(); i++) {
            if (((bitMatrix.getWidth() / 2) - i) % 16 != 0) {
                int i2 = 0;
                for (int i3 = 0; i3 < bitMatrix.getHeight(); i3++) {
                    if (((bitMatrix.getWidth() / 2) - i3) % 16 != 0) {
                        if (bitMatrix.get(i, i3)) {
                            bitMatrix2.set(width, i2);
                        }
                        i2++;
                    }
                }
                width++;
            }
        }
        return bitMatrix2;
    }

    public final DecoderResult decode(AztecDetectorResult aztecDetectorResult) throws FormatException {
        this.ddata = aztecDetectorResult;
        BitMatrix bits = aztecDetectorResult.getBits();
        if (!this.ddata.isCompact()) {
            bits = removeDashedLines(this.ddata.getBits());
        }
        return new DecoderResult(null, getEncodedData(correctBits(extractBits(bits))), null, null);
    }
}
