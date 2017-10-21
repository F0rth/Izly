package com.google.zxing.common;

public final class BitMatrix {
    private final int[] bits;
    private final int height;
    private final int rowSize;
    private final int width;

    public BitMatrix(int i) {
        this(i, i);
    }

    public BitMatrix(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.width = i;
        this.height = i2;
        this.rowSize = (i + 31) >> 5;
        this.bits = new int[(this.rowSize * i2)];
    }

    public final void clear() {
        int length = this.bits.length;
        for (int i = 0; i < length; i++) {
            this.bits[i] = 0;
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof BitMatrix)) {
            return false;
        }
        BitMatrix bitMatrix = (BitMatrix) obj;
        if (this.width != bitMatrix.width || this.height != bitMatrix.height || this.rowSize != bitMatrix.rowSize || this.bits.length != bitMatrix.bits.length) {
            return false;
        }
        for (int i = 0; i < this.bits.length; i++) {
            if (this.bits[i] != bitMatrix.bits[i]) {
                return false;
            }
        }
        return true;
    }

    public final void flip(int i, int i2) {
        int i3 = (this.rowSize * i2) + (i >> 5);
        int[] iArr = this.bits;
        iArr[i3] = iArr[i3] ^ (1 << (i & 31));
    }

    public final boolean get(int i, int i2) {
        return ((this.bits[(this.rowSize * i2) + (i >> 5)] >>> (i & 31)) & 1) != 0;
    }

    public final int[] getBottomRightOnBit() {
        int length = this.bits.length - 1;
        while (length >= 0 && this.bits[length] == 0) {
            length--;
        }
        if (length < 0) {
            return null;
        }
        int i;
        int i2 = length / this.rowSize;
        int i3 = this.rowSize;
        for (i = 31; (this.bits[length] >>> i) == 0; i--) {
        }
        return new int[]{i + ((length % i3) << 5), i2};
    }

    public final int[] getEnclosingRectangle() {
        int i = -1;
        int i2 = this.width;
        int i3 = this.height;
        int i4 = -1;
        int i5 = 0;
        while (i5 < this.height) {
            int i6 = 0;
            while (i6 < this.rowSize) {
                int i7;
                int i8 = this.bits[(this.rowSize * i5) + i6];
                if (i8 != 0) {
                    int i9;
                    i7 = i5 < i3 ? i5 : i3;
                    if (i5 > i) {
                        i = i5;
                    }
                    if (i6 * 32 < i2) {
                        i3 = 0;
                        while ((i8 << (31 - i3)) == 0) {
                            i3++;
                        }
                        if ((i6 * 32) + i3 < i2) {
                            i3 += i6 * 32;
                            if ((i6 * 32) + 31 > i4) {
                                i2 = 31;
                                while ((i8 >>> i2) == 0) {
                                    i2--;
                                }
                                if ((i6 * 32) + i2 > i4) {
                                    i4 = (i6 * 32) + i2;
                                    i2 = i;
                                    i9 = i4;
                                    i4 = i7;
                                    i7 = i9;
                                }
                            }
                            i2 = i;
                            i9 = i4;
                            i4 = i7;
                            i7 = i9;
                        }
                    }
                    i3 = i2;
                    if ((i6 * 32) + 31 > i4) {
                        i2 = 31;
                        while ((i8 >>> i2) == 0) {
                            i2--;
                        }
                        if ((i6 * 32) + i2 > i4) {
                            i4 = (i6 * 32) + i2;
                            i2 = i;
                            i9 = i4;
                            i4 = i7;
                            i7 = i9;
                        }
                    }
                    i2 = i;
                    i9 = i4;
                    i4 = i7;
                    i7 = i9;
                } else {
                    i7 = i4;
                    i4 = i3;
                    i3 = i2;
                    i2 = i;
                }
                i6++;
                i = i2;
                i2 = i3;
                i3 = i4;
                i4 = i7;
            }
            i5++;
        }
        i5 = i - i3;
        if (i4 - i2 < 0 || i5 < 0) {
            return null;
        }
        return new int[]{i2, i3, i4, i5};
    }

    public final int getHeight() {
        return this.height;
    }

    public final BitArray getRow(int i, BitArray bitArray) {
        if (bitArray == null || bitArray.getSize() < this.width) {
            bitArray = new BitArray(this.width);
        }
        int i2 = this.rowSize;
        for (int i3 = 0; i3 < this.rowSize; i3++) {
            bitArray.setBulk(i3 << 5, this.bits[(i * i2) + i3]);
        }
        return bitArray;
    }

    public final int[] getTopLeftOnBit() {
        int i = 0;
        while (i < this.bits.length && this.bits[i] == 0) {
            i++;
        }
        if (i == this.bits.length) {
            return null;
        }
        int i2;
        int i3 = i / this.rowSize;
        int i4 = this.rowSize;
        for (i2 = 0; (this.bits[i] << (31 - i2)) == 0; i2++) {
        }
        return new int[]{((i % i4) << 5) + i2, i3};
    }

    public final int getWidth() {
        return this.width;
    }

    public final int hashCode() {
        int i = this.width;
        i = (((((i * 31) + this.width) * 31) + this.height) * 31) + this.rowSize;
        for (int i2 : this.bits) {
            i = (i * 31) + i2;
        }
        return i;
    }

    public final void set(int i, int i2) {
        int i3 = (this.rowSize * i2) + (i >> 5);
        int[] iArr = this.bits;
        iArr[i3] = iArr[i3] | (1 << (i & 31));
    }

    public final void setRegion(int i, int i2, int i3, int i4) {
        if (i2 < 0 || i < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        } else if (i4 <= 0 || i3 <= 0) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        } else {
            int i5 = i + i3;
            int i6 = i2 + i4;
            if (i6 > this.height || i5 > this.width) {
                throw new IllegalArgumentException("The region must fit inside the matrix");
            }
            while (i2 < i6) {
                int i7 = this.rowSize;
                for (int i8 = i; i8 < i5; i8++) {
                    int[] iArr = this.bits;
                    int i9 = (i8 >> 5) + (i2 * i7);
                    iArr[i9] = iArr[i9] | (1 << (i8 & 31));
                }
                i2++;
            }
        }
    }

    public final void setRow(int i, BitArray bitArray) {
        System.arraycopy(bitArray.getBitArray(), 0, this.bits, this.rowSize * i, this.rowSize);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.height * (this.width + 1));
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                stringBuilder.append(get(i2, i) ? "X " : "  ");
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
