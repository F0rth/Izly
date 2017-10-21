package com.google.zxing.oned.rss;

final class Pair extends DataCharacter {
    private int count;
    private final FinderPattern finderPattern;

    Pair(int i, int i2, FinderPattern finderPattern) {
        super(i, i2);
        this.finderPattern = finderPattern;
    }

    final int getCount() {
        return this.count;
    }

    final FinderPattern getFinderPattern() {
        return this.finderPattern;
    }

    final void incrementCount() {
        this.count++;
    }
}
