package com.google.zxing.oned.rss.expanded;

import java.util.ArrayList;
import java.util.List;

final class ExpandedRow {
    private final List<ExpandedPair> pairs;
    private final int rowNumber;
    private final boolean wasReversed;

    ExpandedRow(List<ExpandedPair> list, int i, boolean z) {
        this.pairs = new ArrayList(list);
        this.rowNumber = i;
        this.wasReversed = z;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ExpandedRow) {
            ExpandedRow expandedRow = (ExpandedRow) obj;
            if (this.pairs.equals(expandedRow.getPairs()) && this.wasReversed == expandedRow.wasReversed) {
                return true;
            }
        }
        return false;
    }

    final List<ExpandedPair> getPairs() {
        return this.pairs;
    }

    final int getRowNumber() {
        return this.rowNumber;
    }

    public final int hashCode() {
        return this.pairs.hashCode() ^ Boolean.valueOf(this.wasReversed).hashCode();
    }

    final boolean isEquivalent(List<ExpandedPair> list) {
        return this.pairs.equals(list);
    }

    final boolean isReversed() {
        return this.wasReversed;
    }

    public final String toString() {
        return "{ " + this.pairs + " }";
    }
}
