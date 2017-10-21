package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

final class BitMatrixParser {
    private final BitMatrix mappingBitMatrix;
    private final BitMatrix readMappingMatrix;
    private final Version version;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int height = bitMatrix.getHeight();
        if (height < 8 || height > 144 || (height & 1) != 0) {
            throw FormatException.getFormatInstance();
        }
        this.version = readVersion(bitMatrix);
        this.mappingBitMatrix = extractDataRegion(bitMatrix);
        this.readMappingMatrix = new BitMatrix(this.mappingBitMatrix.getWidth(), this.mappingBitMatrix.getHeight());
    }

    private static Version readVersion(BitMatrix bitMatrix) throws FormatException {
        return Version.getVersionForDimensions(bitMatrix.getHeight(), bitMatrix.getWidth());
    }

    final BitMatrix extractDataRegion(BitMatrix bitMatrix) {
        int symbolSizeRows = this.version.getSymbolSizeRows();
        int symbolSizeColumns = this.version.getSymbolSizeColumns();
        if (bitMatrix.getHeight() != symbolSizeRows) {
            throw new IllegalArgumentException("Dimension of bitMarix must match the version size");
        }
        int dataRegionSizeRows = this.version.getDataRegionSizeRows();
        int dataRegionSizeColumns = this.version.getDataRegionSizeColumns();
        int i = symbolSizeRows / dataRegionSizeRows;
        int i2 = symbolSizeColumns / dataRegionSizeColumns;
        BitMatrix bitMatrix2 = new BitMatrix(i2 * dataRegionSizeColumns, i * dataRegionSizeRows);
        for (int i3 = 0; i3 < i; i3++) {
            for (int i4 = 0; i4 < i2; i4++) {
                for (symbolSizeColumns = 0; symbolSizeColumns < dataRegionSizeRows; symbolSizeColumns++) {
                    for (symbolSizeRows = 0; symbolSizeRows < dataRegionSizeColumns; symbolSizeRows++) {
                        if (bitMatrix.get((((dataRegionSizeColumns + 2) * i4) + 1) + symbolSizeRows, (((dataRegionSizeRows + 2) * i3) + 1) + symbolSizeColumns)) {
                            bitMatrix2.set((i4 * dataRegionSizeColumns) + symbolSizeRows, (i3 * dataRegionSizeRows) + symbolSizeColumns);
                        }
                    }
                }
            }
        }
        return bitMatrix2;
    }

    final Version getVersion() {
        return this.version;
    }

    final byte[] readCodewords() throws FormatException {
        Object obj = null;
        byte[] bArr = new byte[this.version.getTotalCodewords()];
        int height = this.mappingBitMatrix.getHeight();
        int width = this.mappingBitMatrix.getWidth();
        Object obj2 = null;
        Object obj3 = null;
        Object obj4 = null;
        int i = 0;
        int i2 = 4;
        int i3 = 0;
        while (true) {
            Object obj5;
            int i4;
            Object obj6;
            int i5;
            Object obj7;
            int i6;
            Object obj8;
            Object obj9;
            int i7;
            if (i2 == height && i == 0 && obj4 == null) {
                bArr[i3] = (byte) readCorner1(height, width);
                i7 = i2 - 2;
                obj5 = 1;
                i4 = i3 + 1;
                obj6 = obj2;
                i5 = i + 2;
                obj7 = obj;
                i6 = i7;
                obj8 = obj3;
            } else if (i2 == height - 2 && i == 0 && (width & 3) != 0 && obj3 == null) {
                bArr[i3] = (byte) readCorner2(height, width);
                obj9 = obj4;
                i4 = i3 + 1;
                obj6 = obj2;
                i5 = i + 2;
                obj7 = obj;
                i6 = i2 - 2;
                i7 = 1;
                obj5 = obj9;
            } else if (i2 == height + 4 && i == 2 && (width & 7) == 0 && obj2 == null) {
                bArr[i3] = (byte) readCorner3(height, width);
                i5 = i + 2;
                obj7 = obj;
                i6 = i2 - 2;
                obj8 = obj3;
                int i8 = i3 + 1;
                i3 = 1;
                obj5 = obj4;
                i4 = i8;
            } else if (i2 == height - 2 && i == 0 && (width & 7) == 4 && obj == null) {
                bArr[i3] = (byte) readCorner4(height, width);
                i6 = i2 - 2;
                obj8 = obj3;
                obj9 = obj2;
                i5 = i + 2;
                i = 1;
                obj5 = obj4;
                i4 = i3 + 1;
                obj6 = obj9;
            } else {
                Object obj10;
                Object obj11;
                i7 = i;
                i = i2;
                i2 = i3;
                while (true) {
                    if (i >= height || i7 < 0 || this.readMappingMatrix.get(i7, i)) {
                        i3 = i2;
                    } else {
                        i3 = i2 + 1;
                        bArr[i2] = (byte) readUtah(i, i7, height, width);
                    }
                    i2 = i - 2;
                    i = i7 + 2;
                    if (i2 < 0 || i >= width) {
                        i7 = i + 3;
                        i = i3;
                        i3 = i2 + 1;
                    } else {
                        i7 = i;
                        i = i2;
                        i2 = i3;
                    }
                }
                i7 = i + 3;
                i = i3;
                i3 = i2 + 1;
                while (true) {
                    if (i3 < 0 || i7 >= width || this.readMappingMatrix.get(i7, i3)) {
                        i2 = i;
                    } else {
                        i2 = i + 1;
                        bArr[i] = (byte) readUtah(i3, i7, height, width);
                    }
                    i = i3 + 2;
                    i3 = i7 - 2;
                    if (i >= height || i3 < 0) {
                        obj8 = obj3;
                        obj9 = obj;
                        i6 = i + 3;
                        obj7 = obj9;
                        obj10 = obj4;
                        i4 = i2;
                        obj5 = obj10;
                        obj11 = obj2;
                        i5 = i3 + 1;
                        obj6 = obj11;
                    } else {
                        i7 = i3;
                        i3 = i;
                        i = i2;
                    }
                }
                obj8 = obj3;
                obj9 = obj;
                i6 = i + 3;
                obj7 = obj9;
                obj10 = obj4;
                i4 = i2;
                obj5 = obj10;
                obj11 = obj2;
                i5 = i3 + 1;
                obj6 = obj11;
            }
            if (i6 >= height && i5 >= width) {
                break;
            }
            obj3 = obj8;
            obj9 = obj7;
            i = i5;
            obj2 = obj6;
            i3 = i4;
            obj4 = obj5;
            i2 = i6;
            obj = obj9;
        }
        if (i4 == this.version.getTotalCodewords()) {
            return bArr;
        }
        throw FormatException.getFormatInstance();
    }

    final int readCorner1(int i, int i2) {
        int i3 = (readModule(i + -1, 0, i, i2) ? 1 : 0) << 1;
        if (readModule(i - 1, 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(i - 1, 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(1, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(2, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        return readModule(3, i2 + -1, i, i2) ? i3 | 1 : i3;
    }

    final int readCorner2(int i, int i2) {
        int i3 = (readModule(i + -3, 0, i, i2) ? 1 : 0) << 1;
        if (readModule(i - 2, 0, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(i - 1, 0, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 4, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 3, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        return readModule(1, i2 + -1, i, i2) ? i3 | 1 : i3;
    }

    final int readCorner3(int i, int i2) {
        int i3 = (readModule(i + -1, 0, i, i2) ? 1 : 0) << 1;
        if (readModule(i - 1, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 3, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(1, i2 - 3, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(1, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        return readModule(1, i2 + -1, i, i2) ? i3 | 1 : i3;
    }

    final int readCorner4(int i, int i2) {
        int i3 = (readModule(i + -3, 0, i, i2) ? 1 : 0) << 1;
        if (readModule(i - 2, 0, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(i - 1, 0, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 2, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(0, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(1, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        if (readModule(2, i2 - 1, i, i2)) {
            i3 |= 1;
        }
        i3 <<= 1;
        return readModule(3, i2 + -1, i, i2) ? i3 | 1 : i3;
    }

    final boolean readModule(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (i < 0) {
            i5 = i + i3;
            i6 = (4 - ((i3 + 4) & 7)) + i2;
        } else {
            i6 = i2;
            i5 = i;
        }
        if (i6 < 0) {
            i6 += i4;
            i5 += 4 - ((i4 + 4) & 7);
        }
        this.readMappingMatrix.set(i6, i5);
        return this.mappingBitMatrix.get(i6, i5);
    }

    final int readUtah(int i, int i2, int i3, int i4) {
        int i5 = 0;
        if (readModule(i - 2, i2 - 2, i3, i4)) {
            i5 = 1;
        }
        i5 <<= 1;
        if (readModule(i - 2, i2 - 1, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i - 1, i2 - 2, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i - 1, i2 - 1, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i - 1, i2, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i, i2 - 2, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        if (readModule(i, i2 - 1, i3, i4)) {
            i5 |= 1;
        }
        i5 <<= 1;
        return readModule(i, i2, i3, i4) ? i5 | 1 : i5;
    }
}
