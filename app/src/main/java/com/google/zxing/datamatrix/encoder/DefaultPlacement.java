package com.google.zxing.datamatrix.encoder;

import java.util.Arrays;

public class DefaultPlacement {
    private final byte[] bits;
    private final String codewords;
    private final int numcols;
    private final int numrows;

    public DefaultPlacement(String str, int i, int i2) {
        this.codewords = str;
        this.numcols = i;
        this.numrows = i2;
        this.bits = new byte[(i * i2)];
        Arrays.fill(this.bits, (byte) -1);
    }

    private void corner1(int i) {
        module(this.numrows - 1, 0, i, 1);
        module(this.numrows - 1, 1, i, 2);
        module(this.numrows - 1, 2, i, 3);
        module(0, this.numcols - 2, i, 4);
        module(0, this.numcols - 1, i, 5);
        module(1, this.numcols - 1, i, 6);
        module(2, this.numcols - 1, i, 7);
        module(3, this.numcols - 1, i, 8);
    }

    private void corner2(int i) {
        module(this.numrows - 3, 0, i, 1);
        module(this.numrows - 2, 0, i, 2);
        module(this.numrows - 1, 0, i, 3);
        module(0, this.numcols - 4, i, 4);
        module(0, this.numcols - 3, i, 5);
        module(0, this.numcols - 2, i, 6);
        module(0, this.numcols - 1, i, 7);
        module(1, this.numcols - 1, i, 8);
    }

    private void corner3(int i) {
        module(this.numrows - 3, 0, i, 1);
        module(this.numrows - 2, 0, i, 2);
        module(this.numrows - 1, 0, i, 3);
        module(0, this.numcols - 2, i, 4);
        module(0, this.numcols - 1, i, 5);
        module(1, this.numcols - 1, i, 6);
        module(2, this.numcols - 1, i, 7);
        module(3, this.numcols - 1, i, 8);
    }

    private void corner4(int i) {
        module(this.numrows - 1, 0, i, 1);
        module(this.numrows - 1, this.numcols - 1, i, 2);
        module(0, this.numcols - 3, i, 3);
        module(0, this.numcols - 2, i, 4);
        module(0, this.numcols - 1, i, 5);
        module(1, this.numcols - 3, i, 6);
        module(1, this.numcols - 2, i, 7);
        module(1, this.numcols - 1, i, 8);
    }

    private void module(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        boolean z = true;
        if (i < 0) {
            i5 = i + this.numrows;
            i6 = (4 - ((this.numrows + 4) % 8)) + i2;
        } else {
            i6 = i2;
            i5 = i;
        }
        if (i6 < 0) {
            i6 += this.numcols;
            i5 += 4 - ((this.numcols + 4) % 8);
        }
        if ((this.codewords.charAt(i3) & (1 << (8 - i4))) == 0) {
            z = false;
        }
        setBit(i6, i5, z);
    }

    private void utah(int i, int i2, int i3) {
        module(i - 2, i2 - 2, i3, 1);
        module(i - 2, i2 - 1, i3, 2);
        module(i - 1, i2 - 2, i3, 3);
        module(i - 1, i2 - 1, i3, 4);
        module(i - 1, i2, i3, 5);
        module(i, i2 - 2, i3, 6);
        module(i, i2 - 1, i3, 7);
        module(i, i2, i3, 8);
    }

    public final boolean getBit(int i, int i2) {
        return this.bits[(this.numcols * i2) + i] == (byte) 1;
    }

    final byte[] getBits() {
        return this.bits;
    }

    final int getNumcols() {
        return this.numcols;
    }

    final int getNumrows() {
        return this.numrows;
    }

    final boolean hasBit(int i, int i2) {
        return this.bits[(this.numcols * i2) + i] >= (byte) 0;
    }

    public final void place() {
        int i = 0;
        int i2 = 0;
        int i3 = 4;
        while (true) {
            if (i3 == this.numrows && r1 == 0) {
                corner1(i);
                i++;
            }
            if (i3 == this.numrows - 2 && r1 == 0 && this.numcols % 4 != 0) {
                corner2(i);
                i++;
            }
            if (i3 == this.numrows - 2 && r1 == 0 && this.numcols % 8 == 4) {
                corner3(i);
                i++;
            }
            if (i3 == this.numrows + 4 && r1 == 2 && this.numcols % 8 == 0) {
                corner4(i);
                i++;
            }
            do {
                if (i3 < this.numrows && i2 >= 0 && !hasBit(i2, i3)) {
                    utah(i3, i2, i);
                    i++;
                }
                i3 -= 2;
                i2 += 2;
                if (i3 < 0) {
                    break;
                }
            } while (i2 < this.numcols);
            i3++;
            int i4 = i2 + 3;
            i2 = i;
            while (true) {
                if (i3 < 0 || i4 >= this.numcols || hasBit(i4, i3)) {
                    i = i2;
                } else {
                    i = i2 + 1;
                    utah(i3, i4, i2);
                }
                i2 = i3 + 2;
                i4 -= 2;
                if (i2 >= this.numrows || i4 < 0) {
                    i3 = i2 + 3;
                    i2 = i4 + 1;
                } else {
                    i3 = i2;
                    i2 = i;
                }
            }
            i3 = i2 + 3;
            i2 = i4 + 1;
            if (i3 >= this.numrows && i2 >= this.numcols) {
                break;
            }
        }
        if (!hasBit(this.numcols - 1, this.numrows - 1)) {
            setBit(this.numcols - 1, this.numrows - 1, true);
            setBit(this.numcols - 2, this.numrows - 2, true);
        }
    }

    final void setBit(int i, int i2, boolean z) {
        this.bits[(this.numcols * i2) + i] = z ? (byte) 1 : (byte) 0;
    }
}
