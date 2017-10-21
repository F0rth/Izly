package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

final class BitMatrixParser {
    private final BitMatrix bitMatrix;
    private FormatInformation parsedFormatInfo;
    private Version parsedVersion;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int height = bitMatrix.getHeight();
        if (height < 21 || (height & 3) != 1) {
            throw FormatException.getFormatInstance();
        }
        this.bitMatrix = bitMatrix;
    }

    private int copyBit(int i, int i2, int i3) {
        return this.bitMatrix.get(i, i2) ? (i3 << 1) | 1 : i3 << 1;
    }

    final byte[] readCodewords() throws FormatException {
        FormatInformation readFormatInformation = readFormatInformation();
        Version readVersion = readVersion();
        DataMask forReference = DataMask.forReference(readFormatInformation.getDataMask());
        int height = this.bitMatrix.getHeight();
        forReference.unmaskBitMatrix(this.bitMatrix, height);
        BitMatrix buildFunctionPattern = readVersion.buildFunctionPattern();
        byte[] bArr = new byte[readVersion.getTotalCodewords()];
        int i = 0;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        int i5 = height - 1;
        while (i5 > 0) {
            if (i5 == 6) {
                i5--;
            }
            int i6 = 0;
            while (i6 < height) {
                int i7 = i2 != 0 ? (height - 1) - i6 : i6;
                for (int i8 = 0; i8 < 2; i8++) {
                    if (!buildFunctionPattern.get(i5 - i8, i7)) {
                        i4++;
                        i3 <<= 1;
                        if (this.bitMatrix.get(i5 - i8, i7)) {
                            i3 |= 1;
                        }
                        if (i4 == 8) {
                            bArr[i] = (byte) i3;
                            i++;
                            i4 = 0;
                            i3 = 0;
                        }
                    }
                }
                i6++;
            }
            i2 ^= 1;
            i5 -= 2;
        }
        if (i == readVersion.getTotalCodewords()) {
            return bArr;
        }
        throw FormatException.getFormatInstance();
    }

    final FormatInformation readFormatInformation() throws FormatException {
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        int i;
        int i2 = 0;
        for (i = 0; i < 6; i++) {
            i2 = copyBit(i, 8, i2);
        }
        i = 5;
        int copyBit = copyBit(8, 7, copyBit(8, 8, copyBit(7, 8, i2)));
        while (i >= 0) {
            i2 = copyBit(8, i, copyBit);
            i--;
            copyBit = i2;
        }
        int height = this.bitMatrix.getHeight();
        i2 = 0;
        int i3 = height - 1;
        while (i3 >= height - 7) {
            i = copyBit(8, i3, i2);
            i3--;
            i2 = i;
        }
        for (i3 = height - 8; i3 < height; i3++) {
            i2 = copyBit(i3, 8, i2);
        }
        this.parsedFormatInfo = FormatInformation.decodeFormatInformation(copyBit, i2);
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        throw FormatException.getFormatInstance();
    }

    final Version readVersion() throws FormatException {
        int i = 5;
        int i2 = 0;
        if (this.parsedVersion != null) {
            return this.parsedVersion;
        }
        int height = this.bitMatrix.getHeight();
        int i3 = (height - 17) >> 2;
        if (i3 <= 6) {
            return Version.getVersionForNumber(i3);
        }
        int i4 = height - 11;
        int i5 = 0;
        for (int i6 = 5; i6 >= 0; i6--) {
            for (i3 = height - 9; i3 >= i4; i3--) {
                i5 = copyBit(i3, i6, i5);
            }
        }
        Version decodeVersionInformation = Version.decodeVersionInformation(i5);
        if (decodeVersionInformation == null || decodeVersionInformation.getDimensionForVersion() != height) {
            while (i >= 0) {
                for (i3 = height - 9; i3 >= i4; i3--) {
                    i2 = copyBit(i, i3, i2);
                }
                i--;
            }
            decodeVersionInformation = Version.decodeVersionInformation(i2);
            if (decodeVersionInformation == null || decodeVersionInformation.getDimensionForVersion() != height) {
                throw FormatException.getFormatInstance();
            }
            this.parsedVersion = decodeVersionInformation;
            return decodeVersionInformation;
        }
        this.parsedVersion = decodeVersionInformation;
        return decodeVersionInformation;
    }
}
