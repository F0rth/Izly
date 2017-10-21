package com.google.zxing.pdf417.decoder;

import com.google.zxing.ResultPoint;

final class DetectionResultRowIndicatorColumn extends DetectionResultColumn {
    private final boolean isLeft;

    DetectionResultRowIndicatorColumn(BoundingBox boundingBox, boolean z) {
        super(boundingBox);
        this.isLeft = z;
    }

    private void removeIncorrectCodewords(Codeword[] codewordArr, BarcodeMetadata barcodeMetadata) {
        for (int i = 0; i < codewordArr.length; i++) {
            Codeword codeword = codewordArr[i];
            if (codewordArr[i] != null) {
                int value = codeword.getValue() % 30;
                int rowNumber = codeword.getRowNumber();
                if (rowNumber <= barcodeMetadata.getRowCount()) {
                    if (!this.isLeft) {
                        rowNumber += 2;
                    }
                    switch (rowNumber % 3) {
                        case 0:
                            if ((value * 3) + 1 == barcodeMetadata.getRowCountUpperPart()) {
                                break;
                            }
                            codewordArr[i] = null;
                            break;
                        case 1:
                            if (value / 3 != barcodeMetadata.getErrorCorrectionLevel() || value % 3 != barcodeMetadata.getRowCountLowerPart()) {
                                codewordArr[i] = null;
                                break;
                            }
                            break;
                        case 2:
                            if (value + 1 == barcodeMetadata.getColumnCount()) {
                                break;
                            }
                            codewordArr[i] = null;
                            break;
                        default:
                            break;
                    }
                }
                codewordArr[i] = null;
            }
        }
    }

    final int adjustCompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
        Codeword[] codewords = getCodewords();
        setRowNumbers();
        removeIncorrectCodewords(codewords, barcodeMetadata);
        BoundingBox boundingBox = getBoundingBox();
        ResultPoint topLeft = this.isLeft ? boundingBox.getTopLeft() : boundingBox.getTopRight();
        ResultPoint bottomLeft = this.isLeft ? boundingBox.getBottomLeft() : boundingBox.getBottomRight();
        int imageRowToCodewordIndex = imageRowToCodewordIndex((int) topLeft.getY());
        int imageRowToCodewordIndex2 = imageRowToCodewordIndex((int) bottomLeft.getY());
        float rowCount = ((float) (imageRowToCodewordIndex2 - imageRowToCodewordIndex)) / ((float) barcodeMetadata.getRowCount());
        int i = -1;
        int i2 = 0;
        int i3 = 1;
        int i4 = imageRowToCodewordIndex;
        while (i4 < imageRowToCodewordIndex2) {
            if (codewords[i4] != null) {
                Codeword codeword = codewords[i4];
                imageRowToCodewordIndex = codeword.getRowNumber() - i;
                int i5;
                if (imageRowToCodewordIndex == 0) {
                    i5 = i;
                    i = i3;
                    i3 = i2 + 1;
                    imageRowToCodewordIndex = i5;
                } else if (imageRowToCodewordIndex == 1) {
                    i = Math.max(i3, i2);
                    imageRowToCodewordIndex = codeword.getRowNumber();
                    i3 = 1;
                } else if (imageRowToCodewordIndex < 0) {
                    codewords[i4] = null;
                    imageRowToCodewordIndex = i;
                    i = i3;
                    i3 = i2;
                } else if (codeword.getRowNumber() >= barcodeMetadata.getRowCount()) {
                    codewords[i4] = null;
                    imageRowToCodewordIndex = i;
                    i = i3;
                    i3 = i2;
                } else if (imageRowToCodewordIndex > i4) {
                    codewords[i4] = null;
                    imageRowToCodewordIndex = i;
                    i = i3;
                    i3 = i2;
                } else {
                    int i6 = i3 > 2 ? imageRowToCodewordIndex * (i3 - 2) : imageRowToCodewordIndex;
                    Object obj = i6 >= i4 ? 1 : null;
                    for (int i7 = 1; i7 <= i6 && obj == null; i7++) {
                        obj = codewords[i4 - i7] != null ? 1 : null;
                    }
                    if (obj != null) {
                        codewords[i4] = null;
                        imageRowToCodewordIndex = i;
                        i = i3;
                        i3 = i2;
                    } else {
                        imageRowToCodewordIndex = codeword.getRowNumber();
                        i5 = i3;
                        i3 = 1;
                        i = i5;
                    }
                }
            } else {
                imageRowToCodewordIndex = i;
                i = i3;
                i3 = i2;
            }
            i4++;
            i2 = i3;
            i3 = i;
            i = imageRowToCodewordIndex;
        }
        return (int) (((double) rowCount) + 0.5d);
    }

    final int adjustIncompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
        BoundingBox boundingBox = getBoundingBox();
        ResultPoint topLeft = this.isLeft ? boundingBox.getTopLeft() : boundingBox.getTopRight();
        ResultPoint bottomLeft = this.isLeft ? boundingBox.getBottomLeft() : boundingBox.getBottomRight();
        int imageRowToCodewordIndex = imageRowToCodewordIndex((int) topLeft.getY());
        int imageRowToCodewordIndex2 = imageRowToCodewordIndex((int) bottomLeft.getY());
        float rowCount = ((float) (imageRowToCodewordIndex2 - imageRowToCodewordIndex)) / ((float) barcodeMetadata.getRowCount());
        Codeword[] codewords = getCodewords();
        int i = -1;
        int i2 = 0;
        imageRowToCodewordIndex = 1;
        for (int i3 = imageRowToCodewordIndex; i3 < imageRowToCodewordIndex2; i3++) {
            if (codewords[i3] != null) {
                Codeword codeword = codewords[i3];
                codeword.setRowNumberAsRowIndicatorColumn();
                int rowNumber = codeword.getRowNumber() - i;
                if (rowNumber == 0) {
                    i2++;
                } else if (rowNumber == 1) {
                    imageRowToCodewordIndex = Math.max(imageRowToCodewordIndex, i2);
                    i = codeword.getRowNumber();
                    i2 = 1;
                } else if (codeword.getRowNumber() >= barcodeMetadata.getRowCount()) {
                    codewords[i3] = null;
                } else {
                    i = codeword.getRowNumber();
                    i2 = 1;
                }
            }
        }
        return (int) (((double) rowCount) + 0.5d);
    }

    final BarcodeMetadata getBarcodeMetadata() {
        Codeword[] codewords = getCodewords();
        BarcodeValue barcodeValue = new BarcodeValue();
        BarcodeValue barcodeValue2 = new BarcodeValue();
        BarcodeValue barcodeValue3 = new BarcodeValue();
        BarcodeValue barcodeValue4 = new BarcodeValue();
        for (Codeword codeword : codewords) {
            if (codeword != null) {
                codeword.setRowNumberAsRowIndicatorColumn();
                int value = codeword.getValue() % 30;
                int rowNumber = codeword.getRowNumber();
                if (!this.isLeft) {
                    rowNumber += 2;
                }
                switch (rowNumber % 3) {
                    case 0:
                        barcodeValue2.setValue((value * 3) + 1);
                        break;
                    case 1:
                        barcodeValue4.setValue(value / 3);
                        barcodeValue3.setValue(value % 3);
                        break;
                    case 2:
                        barcodeValue.setValue(value + 1);
                        break;
                    default:
                        break;
                }
            }
        }
        if (barcodeValue.getValue().length == 0 || barcodeValue2.getValue().length == 0 || barcodeValue3.getValue().length == 0 || barcodeValue4.getValue().length == 0 || barcodeValue.getValue()[0] <= 0 || barcodeValue2.getValue()[0] + barcodeValue3.getValue()[0] < 3 || barcodeValue2.getValue()[0] + barcodeValue3.getValue()[0] > 90) {
            return null;
        }
        BarcodeMetadata barcodeMetadata = new BarcodeMetadata(barcodeValue.getValue()[0], barcodeValue2.getValue()[0], barcodeValue3.getValue()[0], barcodeValue4.getValue()[0]);
        removeIncorrectCodewords(codewords, barcodeMetadata);
        return barcodeMetadata;
    }

    final int[] getRowHeights() {
        BarcodeMetadata barcodeMetadata = getBarcodeMetadata();
        if (barcodeMetadata == null) {
            return null;
        }
        adjustIncompleteIndicatorColumnRowNumbers(barcodeMetadata);
        int[] iArr = new int[barcodeMetadata.getRowCount()];
        for (Codeword codeword : getCodewords()) {
            if (codeword != null) {
                int rowNumber = codeword.getRowNumber();
                iArr[rowNumber] = iArr[rowNumber] + 1;
            }
        }
        return iArr;
    }

    final boolean isLeft() {
        return this.isLeft;
    }

    final void setRowNumbers() {
        for (Codeword codeword : getCodewords()) {
            if (codeword != null) {
                codeword.setRowNumberAsRowIndicatorColumn();
            }
        }
    }

    public final String toString() {
        return "IsLeft: " + this.isLeft + '\n' + super.toString();
    }
}
