package com.google.zxing.pdf417.decoder;

final class Codeword {
    private static final int BARCODE_ROW_UNKNOWN = -1;
    private final int bucket;
    private final int endX;
    private int rowNumber = -1;
    private final int startX;
    private final int value;

    Codeword(int i, int i2, int i3, int i4) {
        this.startX = i;
        this.endX = i2;
        this.bucket = i3;
        this.value = i4;
    }

    final int getBucket() {
        return this.bucket;
    }

    final int getEndX() {
        return this.endX;
    }

    final int getRowNumber() {
        return this.rowNumber;
    }

    final int getStartX() {
        return this.startX;
    }

    final int getValue() {
        return this.value;
    }

    final int getWidth() {
        return this.endX - this.startX;
    }

    final boolean hasValidRowNumber() {
        return isValidRowNumber(this.rowNumber);
    }

    final boolean isValidRowNumber(int i) {
        return i != -1 && this.bucket == (i % 3) * 3;
    }

    final void setRowNumber(int i) {
        this.rowNumber = i;
    }

    final void setRowNumberAsRowIndicatorColumn() {
        this.rowNumber = ((this.value / 30) * 3) + (this.bucket / 3);
    }

    public final String toString() {
        return this.rowNumber + "|" + this.value;
    }
}
