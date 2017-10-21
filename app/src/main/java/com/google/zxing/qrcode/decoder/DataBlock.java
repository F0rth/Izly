package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.Version.ECB;
import com.google.zxing.qrcode.decoder.Version.ECBlocks;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int i, byte[] bArr) {
        this.numDataCodewords = i;
        this.codewords = bArr;
    }

    static DataBlock[] getDataBlocks(byte[] bArr, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        if (bArr.length != version.getTotalCodewords()) {
            throw new IllegalArgumentException();
        }
        int i;
        int i2;
        ECBlocks eCBlocksForLevel = version.getECBlocksForLevel(errorCorrectionLevel);
        ECB[] eCBlocks = eCBlocksForLevel.getECBlocks();
        int i3 = 0;
        for (ECB count : eCBlocks) {
            i3 += count.getCount();
        }
        DataBlock[] dataBlockArr = new DataBlock[i3];
        int length = eCBlocks.length;
        int i4 = 0;
        i3 = 0;
        while (i4 < length) {
            ECB ecb = eCBlocks[i4];
            i = i3;
            i3 = 0;
            while (i3 < ecb.getCount()) {
                int dataCodewords = ecb.getDataCodewords();
                dataBlockArr[i] = new DataBlock(dataCodewords, new byte[(eCBlocksForLevel.getECCodewordsPerBlock() + dataCodewords)]);
                i3++;
                i++;
            }
            i4++;
            i3 = i;
        }
        i4 = dataBlockArr[0].codewords.length;
        i = dataBlockArr.length - 1;
        while (i >= 0 && dataBlockArr[i].codewords.length != i4) {
            i--;
        }
        length = i + 1;
        i4 -= eCBlocksForLevel.getECCodewordsPerBlock();
        int i5 = 0;
        i = 0;
        while (i5 < i4) {
            i2 = i;
            i = 0;
            while (i < i3) {
                dataBlockArr[i].codewords[i5] = bArr[i2];
                i++;
                i2++;
            }
            i5++;
            i = i2;
        }
        i2 = length;
        while (i2 < i3) {
            dataBlockArr[i2].codewords[i4] = bArr[i];
            i2++;
            i++;
        }
        int length2 = dataBlockArr[0].codewords.length;
        while (i4 < length2) {
            i2 = 0;
            i5 = i;
            while (i2 < i3) {
                dataBlockArr[i2].codewords[i2 < length ? i4 : i4 + 1] = bArr[i5];
                i5++;
                i2++;
            }
            i4++;
            i = i5;
        }
        return dataBlockArr;
    }

    final byte[] getCodewords() {
        return this.codewords;
    }

    final int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}
