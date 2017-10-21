package com.google.zxing.qrcode.encoder;

final class MaskUtil {
    private static final int N1 = 3;
    private static final int N2 = 3;
    private static final int N3 = 40;
    private static final int N4 = 10;

    private MaskUtil() {
    }

    static int applyMaskPenaltyRule1(ByteMatrix byteMatrix) {
        return applyMaskPenaltyRule1Internal(byteMatrix, true) + applyMaskPenaltyRule1Internal(byteMatrix, false);
    }

    private static int applyMaskPenaltyRule1Internal(ByteMatrix byteMatrix, boolean z) {
        int height = z ? byteMatrix.getHeight() : byteMatrix.getWidth();
        int width = z ? byteMatrix.getWidth() : byteMatrix.getHeight();
        byte[][] array = byteMatrix.getArray();
        int i = 0;
        int i2 = 0;
        while (i < height) {
            byte b = (byte) -1;
            int i3 = 0;
            int i4 = 0;
            while (i4 < width) {
                int i5;
                byte b2 = z ? array[i][i4] : array[i4][i];
                if (b2 == b) {
                    i3++;
                    i5 = i2;
                } else {
                    int i6 = i3 >= 5 ? i2 + ((i3 - 5) + 3) : i2;
                    i3 = 1;
                    byte b3 = b2;
                    i5 = i6;
                    b = b3;
                }
                i4++;
                i2 = i5;
            }
            if (i3 >= 5) {
                i2 += (i3 - 5) + 3;
            }
            i++;
        }
        return i2;
    }

    static int applyMaskPenaltyRule2(ByteMatrix byteMatrix) {
        byte[][] array = byteMatrix.getArray();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        int i = 0;
        int i2 = 0;
        while (i2 < height - 1) {
            int i3 = 0;
            while (i3 < width - 1) {
                byte b = array[i2][i3];
                if (b == array[i2][i3 + 1] && b == array[i2 + 1][i3] && b == array[i2 + 1][i3 + 1]) {
                    i++;
                }
                i3++;
            }
            i2++;
        }
        return i * 3;
    }

    static int applyMaskPenaltyRule3(ByteMatrix byteMatrix) {
        byte[][] array = byteMatrix.getArray();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        int i = 0;
        int i2 = 0;
        while (i < height) {
            int i3 = 0;
            while (i3 < width) {
                if (i3 + 6 < width && array[i][i3] == (byte) 1 && array[i][i3 + 1] == (byte) 0 && array[i][i3 + 2] == (byte) 1 && array[i][i3 + 3] == (byte) 1 && array[i][i3 + 4] == (byte) 1 && array[i][i3 + 5] == (byte) 0 && array[i][i3 + 6] == (byte) 1 && ((i3 + 10 < width && array[i][i3 + 7] == (byte) 0 && array[i][i3 + 8] == (byte) 0 && array[i][i3 + 9] == (byte) 0 && array[i][i3 + 10] == (byte) 0) || (i3 - 4 >= 0 && array[i][i3 - 1] == (byte) 0 && array[i][i3 - 2] == (byte) 0 && array[i][i3 - 3] == (byte) 0 && array[i][i3 - 4] == (byte) 0))) {
                    i2 += 40;
                }
                if (i + 6 < height && array[i][i3] == (byte) 1 && array[i + 1][i3] == (byte) 0 && array[i + 2][i3] == (byte) 1 && array[i + 3][i3] == (byte) 1 && array[i + 4][i3] == (byte) 1 && array[i + 5][i3] == (byte) 0 && array[i + 6][i3] == (byte) 1 && ((i + 10 < height && array[i + 7][i3] == (byte) 0 && array[i + 8][i3] == (byte) 0 && array[i + 9][i3] == (byte) 0 && array[i + 10][i3] == (byte) 0) || (i - 4 >= 0 && array[i - 1][i3] == (byte) 0 && array[i - 2][i3] == (byte) 0 && array[i - 3][i3] == (byte) 0 && array[i - 4][i3] == (byte) 0))) {
                    i2 += 40;
                }
                i3++;
            }
            i++;
        }
        return i2;
    }

    static int applyMaskPenaltyRule4(ByteMatrix byteMatrix) {
        byte[][] array = byteMatrix.getArray();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        int i = 0;
        for (int i2 = 0; i2 < height; i2++) {
            byte[] bArr = array[i2];
            for (int i3 = 0; i3 < width; i3++) {
                if (bArr[i3] == (byte) 1) {
                    i++;
                }
            }
        }
        return ((int) (Math.abs((((double) i) / ((double) (byteMatrix.getHeight() * byteMatrix.getWidth()))) - 0.5d) * 20.0d)) * 10;
    }

    static boolean getDataMaskBit(int i, int i2, int i3) {
        int i4;
        switch (i) {
            case 0:
                i4 = (i3 + i2) & 1;
                break;
            case 1:
                i4 = i3 & 1;
                break;
            case 2:
                i4 = i2 % 3;
                break;
            case 3:
                i4 = (i3 + i2) % 3;
                break;
            case 4:
                i4 = ((i3 >>> 1) + (i2 / 3)) & 1;
                break;
            case 5:
                i4 = i3 * i2;
                i4 = (i4 & 1) + (i4 % 3);
                break;
            case 6:
                i4 = i3 * i2;
                i4 = ((i4 & 1) + (i4 % 3)) & 1;
                break;
            case 7:
                i4 = (((i3 * i2) % 3) + ((i3 + i2) & 1)) & 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid mask pattern: " + i);
        }
        return i4 == 0;
    }
}
