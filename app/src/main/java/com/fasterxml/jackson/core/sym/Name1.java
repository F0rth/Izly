package com.fasterxml.jackson.core.sym;

public final class Name1 extends Name {
    private static final Name1 EMPTY = new Name1("", 0, 0);
    private final int q;

    Name1(String str, int i, int i2) {
        super(str, i);
        this.q = i2;
    }

    public static Name1 getEmptyName() {
        return EMPTY;
    }

    public final boolean equals(int i) {
        return i == this.q;
    }

    public final boolean equals(int i, int i2) {
        return i == this.q && i2 == 0;
    }

    public final boolean equals(int i, int i2, int i3) {
        return false;
    }

    public final boolean equals(int[] iArr, int i) {
        return i == 1 && iArr[0] == this.q;
    }
}
