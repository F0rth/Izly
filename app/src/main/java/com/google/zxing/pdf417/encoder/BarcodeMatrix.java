package com.google.zxing.pdf417.encoder;

import java.lang.reflect.Array;

public final class BarcodeMatrix {
    private int currentRow;
    private final int height;
    private final BarcodeRow[] matrix;
    private final int width;

    BarcodeMatrix(int i, int i2) {
        this.matrix = new BarcodeRow[(i + 2)];
        int length = this.matrix.length;
        for (int i3 = 0; i3 < length; i3++) {
            this.matrix[i3] = new BarcodeRow(((i2 + 4) * 17) + 1);
        }
        this.width = i2 * 17;
        this.height = i + 2;
        this.currentRow = 0;
    }

    final BarcodeRow getCurrentRow() {
        return this.matrix[this.currentRow];
    }

    public final byte[][] getMatrix() {
        return getScaledMatrix(1, 1);
    }

    public final byte[][] getScaledMatrix(int i) {
        return getScaledMatrix(i, i);
    }

    public final byte[][] getScaledMatrix(int i, int i2) {
        int i3 = 0;
        int i4 = this.height;
        int i5 = this.width;
        byte[][] bArr = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{i4 * i2, i5 * i});
        i5 = this.height * i2;
        while (i3 < i5) {
            bArr[(i5 - i3) - 1] = this.matrix[i3 / i2].getScaledRow(i);
            i3++;
        }
        return bArr;
    }

    final void set(int i, int i2, byte b) {
        this.matrix[i2].set(i, b);
    }

    final void setMatrix(int i, int i2, boolean z) {
        set(i, i2, (byte) (z ? 1 : 0));
    }

    final void startRow() {
        this.currentRow++;
    }
}
