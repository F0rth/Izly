package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

abstract class AI01decoder extends AbstractExpandedDecoder {
    protected static final int GTIN_SIZE = 40;

    AI01decoder(BitArray bitArray) {
        super(bitArray);
    }

    private static void appendCheckDigit(StringBuilder stringBuilder, int i) {
        int charAt;
        int i2 = 0;
        int i3 = 0;
        while (i3 < 13) {
            charAt = stringBuilder.charAt(i3 + i) - 48;
            if ((i3 & 1) == 0) {
                charAt *= 3;
            }
            i3++;
            i2 = charAt + i2;
        }
        charAt = 10 - (i2 % 10);
        if (charAt == 10) {
            charAt = 0;
        }
        stringBuilder.append(charAt);
    }

    protected final void encodeCompressedGtin(StringBuilder stringBuilder, int i) {
        stringBuilder.append("(01)");
        int length = stringBuilder.length();
        stringBuilder.append('9');
        encodeCompressedGtinWithoutAI(stringBuilder, i, length);
    }

    protected final void encodeCompressedGtinWithoutAI(StringBuilder stringBuilder, int i, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            int extractNumericValueFromBitArray = getGeneralDecoder().extractNumericValueFromBitArray((i3 * 10) + i, 10);
            if (extractNumericValueFromBitArray / 100 == 0) {
                stringBuilder.append('0');
            }
            if (extractNumericValueFromBitArray / 10 == 0) {
                stringBuilder.append('0');
            }
            stringBuilder.append(extractNumericValueFromBitArray);
        }
        appendCheckDigit(stringBuilder, i2);
    }
}
