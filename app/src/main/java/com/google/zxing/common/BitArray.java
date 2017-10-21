package com.google.zxing.common;

public final class BitArray {
    private int[] bits;
    private int size;

    public BitArray() {
        this.size = 0;
        this.bits = new int[1];
    }

    public BitArray(int i) {
        this.size = i;
        this.bits = makeArray(i);
    }

    private void ensureCapacity(int i) {
        if (i > (this.bits.length << 5)) {
            Object makeArray = makeArray(i);
            System.arraycopy(this.bits, 0, makeArray, 0, this.bits.length);
            this.bits = makeArray;
        }
    }

    private static int[] makeArray(int i) {
        return new int[((i + 31) >> 5)];
    }

    public final void appendBit(boolean z) {
        ensureCapacity(this.size + 1);
        if (z) {
            int[] iArr = this.bits;
            int i = this.size >> 5;
            iArr[i] = iArr[i] | (1 << (this.size & 31));
        }
        this.size++;
    }

    public final void appendBitArray(BitArray bitArray) {
        int i = bitArray.size;
        ensureCapacity(this.size + i);
        for (int i2 = 0; i2 < i; i2++) {
            appendBit(bitArray.get(i2));
        }
    }

    public final void appendBits(int i, int i2) {
        if (i2 < 0 || i2 > 32) {
            throw new IllegalArgumentException("Num bits must be between 0 and 32");
        }
        ensureCapacity(this.size + i2);
        while (i2 > 0) {
            appendBit(((i >> (i2 + -1)) & 1) == 1);
            i2--;
        }
    }

    public final void clear() {
        int length = this.bits.length;
        for (int i = 0; i < length; i++) {
            this.bits[i] = 0;
        }
    }

    public final void flip(int i) {
        int[] iArr = this.bits;
        int i2 = i >> 5;
        iArr[i2] = iArr[i2] ^ (1 << (i & 31));
    }

    public final boolean get(int i) {
        return (this.bits[i >> 5] & (1 << (i & 31))) != 0;
    }

    public final int[] getBitArray() {
        return this.bits;
    }

    public final int getNextSet(int i) {
        if (i >= this.size) {
            return this.size;
        }
        int i2 = i >> 5;
        int i3 = this.bits[i2] & (((1 << (i & 31)) - 1) ^ -1);
        while (i3 == 0) {
            i2++;
            if (i2 == this.bits.length) {
                return this.size;
            }
            i3 = this.bits[i2];
        }
        i2 = (i2 << 5) + Integer.numberOfTrailingZeros(i3);
        return i2 > this.size ? this.size : i2;
    }

    public final int getNextUnset(int i) {
        if (i >= this.size) {
            return this.size;
        }
        int i2 = i >> 5;
        int i3 = (this.bits[i2] ^ -1) & (((1 << (i & 31)) - 1) ^ -1);
        while (i3 == 0) {
            i2++;
            if (i2 == this.bits.length) {
                return this.size;
            }
            i3 = this.bits[i2] ^ -1;
        }
        i2 = (i2 << 5) + Integer.numberOfTrailingZeros(i3);
        return i2 > this.size ? this.size : i2;
    }

    public final int getSize() {
        return this.size;
    }

    public final int getSizeInBytes() {
        return (this.size + 7) >> 3;
    }

    public final boolean isRange(int i, int i2, boolean z) {
        if (i2 < i) {
            throw new IllegalArgumentException();
        } else if (i2 == i) {
            return true;
        } else {
            int i3 = i2 - 1;
            int i4 = i >> 5;
            int i5 = i3 >> 5;
            int i6 = i4;
            while (i6 <= i5) {
                int i7 = i6 > i4 ? 0 : i & 31;
                int i8 = i6 < i5 ? 31 : i3 & 31;
                if (i7 == 0 && i8 == 31) {
                    i7 = -1;
                } else {
                    int i9 = i7;
                    i7 = 0;
                    while (i9 <= i8) {
                        int i10 = (1 << i9) | i7;
                        i9++;
                        i7 = i10;
                    }
                }
                if ((i7 & this.bits[i6]) != (z ? i7 : 0)) {
                    return false;
                }
                i6++;
            }
            return true;
        }
    }

    public final void reverse() {
        int[] iArr = new int[this.bits.length];
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (get((i - i2) - 1)) {
                int i3 = i2 >> 5;
                iArr[i3] = iArr[i3] | (1 << (i2 & 31));
            }
        }
        this.bits = iArr;
    }

    public final void set(int i) {
        int[] iArr = this.bits;
        int i2 = i >> 5;
        iArr[i2] = iArr[i2] | (1 << (i & 31));
    }

    public final void setBulk(int i, int i2) {
        this.bits[i >> 5] = i2;
    }

    public final void setRange(int i, int i2) {
        if (i2 < i) {
            throw new IllegalArgumentException();
        } else if (i2 != i) {
            int i3 = i2 - 1;
            int i4 = i >> 5;
            int i5 = i3 >> 5;
            int i6 = i4;
            while (i6 <= i5) {
                int i7 = i6 > i4 ? 0 : i & 31;
                int i8 = i6 < i5 ? 31 : i3 & 31;
                if (i7 == 0 && i8 == 31) {
                    i7 = -1;
                } else {
                    int i9 = i7;
                    i7 = 0;
                    while (i9 <= i8) {
                        int i10 = (1 << i9) | i7;
                        i9++;
                        i7 = i10;
                    }
                }
                int[] iArr = this.bits;
                iArr[i6] = i7 | iArr[i6];
                i6++;
            }
        }
    }

    public final void toBytes(int i, byte[] bArr, int i2, int i3) {
        int i4 = 0;
        int i5 = i;
        while (i4 < i3) {
            int i6 = i5;
            i5 = 0;
            for (int i7 = 0; i7 < 8; i7++) {
                if (get(i6)) {
                    i5 |= 1 << (7 - i7);
                }
                i6++;
            }
            bArr[i2 + i4] = (byte) i5;
            i4++;
            i5 = i6;
        }
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.size);
        for (int i = 0; i < this.size; i++) {
            if ((i & 7) == 0) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(get(i) ? 'X' : '.');
        }
        return stringBuilder.toString();
    }

    public final void xor(BitArray bitArray) {
        if (this.bits.length != bitArray.bits.length) {
            throw new IllegalArgumentException("Sizes don't match");
        }
        for (int i = 0; i < this.bits.length; i++) {
            int[] iArr = this.bits;
            iArr[i] = iArr[i] ^ bitArray.bits[i];
        }
    }
}
