package com.google.zxing.oned.rss.expanded.decoders;

final class DecodedNumeric extends DecodedObject {
    static final int FNC1 = 10;
    private final int firstDigit;
    private final int secondDigit;

    DecodedNumeric(int i, int i2, int i3) {
        super(i);
        this.firstDigit = i2;
        this.secondDigit = i3;
        if (this.firstDigit < 0 || this.firstDigit > 10) {
            throw new IllegalArgumentException("Invalid firstDigit: " + i2);
        } else if (this.secondDigit < 0 || this.secondDigit > 10) {
            throw new IllegalArgumentException("Invalid secondDigit: " + i3);
        }
    }

    final int getFirstDigit() {
        return this.firstDigit;
    }

    final int getSecondDigit() {
        return this.secondDigit;
    }

    final int getValue() {
        return (this.firstDigit * 10) + this.secondDigit;
    }

    final boolean isAnyFNC1() {
        return this.firstDigit == 10 || this.secondDigit == 10;
    }

    final boolean isFirstDigitFNC1() {
        return this.firstDigit == 10;
    }

    final boolean isSecondDigitFNC1() {
        return this.secondDigit == 10;
    }
}
