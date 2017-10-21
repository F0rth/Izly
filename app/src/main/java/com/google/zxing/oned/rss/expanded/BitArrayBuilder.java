package com.google.zxing.oned.rss.expanded;

import com.google.zxing.common.BitArray;
import java.util.List;

final class BitArrayBuilder {
    private BitArrayBuilder() {
    }

    static BitArray buildBitArray(List<ExpandedPair> list) {
        int size = (list.size() << 1) - 1;
        BitArray bitArray = new BitArray((((ExpandedPair) list.get(list.size() + -1)).getRightChar() == null ? size - 1 : size) * 12);
        int value = ((ExpandedPair) list.get(0)).getRightChar().getValue();
        size = 11;
        int i = 0;
        while (size >= 0) {
            if (((1 << size) & value) != 0) {
                bitArray.set(i);
            }
            size--;
            i++;
        }
        int i2 = i;
        for (size = 1; size < list.size(); size++) {
            ExpandedPair expandedPair = (ExpandedPair) list.get(size);
            int value2 = expandedPair.getLeftChar().getValue();
            value = 11;
            while (value >= 0) {
                if (((1 << value) & value2) != 0) {
                    bitArray.set(i2);
                }
                value--;
                i2++;
            }
            if (expandedPair.getRightChar() != null) {
                value = expandedPair.getRightChar().getValue();
                i = 11;
                while (i >= 0) {
                    if (((1 << i) & value) != 0) {
                        bitArray.set(i2);
                    }
                    i--;
                    i2++;
                }
            }
        }
        return bitArray;
    }
}
