package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

abstract class AI01weightDecoder extends AI01decoder {
    AI01weightDecoder(BitArray bitArray) {
        super(bitArray);
    }

    protected abstract void addWeightCode(StringBuilder stringBuilder, int i);

    protected abstract int checkWeight(int i);

    protected final void encodeCompressedWeight(StringBuilder stringBuilder, int i, int i2) {
        int extractNumericValueFromBitArray = getGeneralDecoder().extractNumericValueFromBitArray(i, i2);
        addWeightCode(stringBuilder, extractNumericValueFromBitArray);
        int checkWeight = checkWeight(extractNumericValueFromBitArray);
        extractNumericValueFromBitArray = 100000;
        for (int i3 = 0; i3 < 5; i3++) {
            if (checkWeight / extractNumericValueFromBitArray == 0) {
                stringBuilder.append('0');
            }
            extractNumericValueFromBitArray /= 10;
        }
        stringBuilder.append(checkWeight);
    }
}
