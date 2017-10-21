package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.pdf417.PDF417Common;

public final class ModulusGF {
    public static final ModulusGF PDF417_GF = new ModulusGF(PDF417Common.NUMBER_OF_CODEWORDS, 3);
    private final int[] expTable;
    private final int[] logTable;
    private final int modulus;
    private final ModulusPoly one;
    private final ModulusPoly zero;

    private ModulusGF(int i, int i2) {
        this.modulus = i;
        this.expTable = new int[i];
        this.logTable = new int[i];
        int i3 = 1;
        for (int i4 = 0; i4 < i; i4++) {
            this.expTable[i4] = i3;
            i3 = (i3 * i2) % i;
        }
        for (i3 = 0; i3 < i - 1; i3++) {
            this.logTable[this.expTable[i3]] = i3;
        }
        this.zero = new ModulusPoly(this, new int[]{0});
        this.one = new ModulusPoly(this, new int[]{1});
    }

    final int add(int i, int i2) {
        return (i + i2) % this.modulus;
    }

    final ModulusPoly buildMonomial(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        } else if (i2 == 0) {
            return this.zero;
        } else {
            int[] iArr = new int[(i + 1)];
            iArr[0] = i2;
            return new ModulusPoly(this, iArr);
        }
    }

    final int exp(int i) {
        return this.expTable[i];
    }

    final ModulusPoly getOne() {
        return this.one;
    }

    final int getSize() {
        return this.modulus;
    }

    final ModulusPoly getZero() {
        return this.zero;
    }

    final int inverse(int i) {
        if (i != 0) {
            return this.expTable[(this.modulus - this.logTable[i]) - 1];
        }
        throw new ArithmeticException();
    }

    final int log(int i) {
        if (i != 0) {
            return this.logTable[i];
        }
        throw new IllegalArgumentException();
    }

    final int multiply(int i, int i2) {
        return (i == 0 || i2 == 0) ? 0 : this.expTable[(this.logTable[i] + this.logTable[i2]) % (this.modulus - 1)];
    }

    final int subtract(int i, int i2) {
        return ((this.modulus + i) - i2) % this.modulus;
    }
}
