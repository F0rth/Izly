package com.google.zxing.datamatrix.decoder;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int i, byte[] bArr) {
        this.numDataCodewords = i;
        this.codewords = bArr;
    }

    static DataBlock[] getDataBlocks(byte[] bArr, Version version) {
        int i;
        int i2;
        ECBlocks eCBlocks = version.getECBlocks();
        ECB[] eCBlocks2 = eCBlocks.getECBlocks();
        int i3 = 0;
        for (ECB count : eCBlocks2) {
            i3 += count.getCount();
        }
        DataBlock[] dataBlockArr = new DataBlock[i3];
        int length = eCBlocks2.length;
        int i4 = 0;
        i3 = 0;
        while (i4 < length) {
            ECB ecb = eCBlocks2[i4];
            i = i3;
            i3 = 0;
            while (i3 < ecb.getCount()) {
                int dataCodewords = ecb.getDataCodewords();
                dataBlockArr[i] = new DataBlock(dataCodewords, new byte[(eCBlocks.getECCodewords() + dataCodewords)]);
                i3++;
                i++;
            }
            i4++;
            i3 = i;
        }
        i4 = dataBlockArr[0].codewords.length - eCBlocks.getECCodewords();
        int i5 = 0;
        for (i2 = 0; i2 < i4 - 1; i2++) {
            i = 0;
            while (i < i3) {
                dataBlockArr[i].codewords[i2] = bArr[i5];
                i++;
                i5++;
            }
        }
        length = version.getVersionNumber() == 24 ? 1 : 0;
        i = length != 0 ? 8 : i3;
        i2 = 0;
        while (i2 < i) {
            dataBlockArr[i2].codewords[i4 - 1] = bArr[i5];
            i2++;
            i5++;
        }
        int length2 = dataBlockArr[0].codewords.length;
        i = i5;
        while (i4 < length2) {
            i2 = 0;
            i5 = i;
            while (i2 < i3) {
                i = (length == 0 || i2 <= 7) ? i4 : i4 - 1;
                dataBlockArr[i2].codewords[i] = bArr[i5];
                i5++;
                i2++;
            }
            i4++;
            i = i5;
        }
        if (i == bArr.length) {
            return dataBlockArr;
        }
        throw new IllegalArgumentException();
    }

    final byte[] getCodewords() {
        return this.codewords;
    }

    final int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}
