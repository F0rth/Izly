package org.spongycastle.math.ntru.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;

public class ArrayEncoder {
    private static final int[] BIT1_TABLE = new int[]{1, 1, 1, 0, 0, 0, 1, 0, 1};
    private static final int[] BIT2_TABLE = new int[]{1, 1, 1, 1, 0, 0, 0, 1, 0};
    private static final int[] BIT3_TABLE = new int[]{1, 0, 1, 0, 0, 1, 1, 1, 0};
    private static final int[] COEFF1_TABLE = new int[]{0, 0, 0, 1, 1, 1, -1, -1};
    private static final int[] COEFF2_TABLE = new int[]{0, 1, -1, 0, 1, -1, 0, 1};

    public static int[] decodeMod3Sves(byte[] bArr, int i) {
        int i2 = 0;
        int[] iArr = new int[i];
        int i3 = 0;
        while (i2 < bArr.length * 8) {
            int i4 = i2 + 1;
            int bit = getBit(bArr, i2);
            int i5 = i4 + 1;
            i2 = i5 + 1;
            i4 = ((getBit(bArr, i4) * 2) + (bit * 4)) + getBit(bArr, i5);
            bit = i3 + 1;
            iArr[i3] = COEFF1_TABLE[i4];
            i3 = bit + 1;
            iArr[bit] = COEFF2_TABLE[i4];
            if (i3 > i - 2) {
                break;
            }
        }
        return iArr;
    }

    public static int[] decodeMod3Tight(InputStream inputStream, int i) throws IOException {
        return decodeMod3Tight(Util.readFullLength(inputStream, (int) Math.ceil(((((double) i) * Math.log(3.0d)) / Math.log(2.0d)) / 8.0d)), i);
    }

    public static int[] decodeMod3Tight(byte[] bArr, int i) {
        BigInteger bigInteger = new BigInteger(1, bArr);
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = bigInteger.mod(BigInteger.valueOf(3)).intValue() - 1;
            if (iArr[i2] > 1) {
                iArr[i2] = iArr[i2] - 3;
            }
            bigInteger = bigInteger.divide(BigInteger.valueOf(3));
        }
        return iArr;
    }

    public static int[] decodeModQ(InputStream inputStream, int i, int i2) throws IOException {
        return decodeModQ(Util.readFullLength(inputStream, (((31 - Integer.numberOfLeadingZeros(i2)) * i) + 7) / 8), i, i2);
    }

    public static int[] decodeModQ(byte[] bArr, int i, int i2) {
        int i3 = 0;
        int[] iArr = new int[i];
        int numberOfLeadingZeros = 31 - Integer.numberOfLeadingZeros(i2);
        int i4 = 0;
        while (i4 < i * numberOfLeadingZeros) {
            if (i4 > 0 && i4 % numberOfLeadingZeros == 0) {
                i3++;
            }
            iArr[i3] = (getBit(bArr, i4) << (i4 % numberOfLeadingZeros)) + iArr[i3];
            i4++;
        }
        return iArr;
    }

    public static byte[] encodeMod3Sves(int[] iArr) {
        byte[] bArr = new byte[(((((iArr.length * 3) + 1) / 2) + 7) / 8)];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < (iArr.length / 2) * 2) {
            int i4 = i + 1;
            int i5 = iArr[i] + 1;
            i = i4 + 1;
            i4 = iArr[i4] + 1;
            if (i5 == 0 && i4 == 0) {
                throw new IllegalStateException("Illegal encoding!");
            }
            i4 += i5 * 3;
            i5 = BIT1_TABLE[i4];
            int i6 = BIT2_TABLE[i4];
            int i7 = BIT3_TABLE[i4];
            for (i4 = 0; i4 < 3; i4++) {
                bArr[i2] = (byte) (bArr[i2] | (new int[]{i5, i6, i7}[i4] << i3));
                if (i3 == 7) {
                    i2++;
                    i3 = 0;
                } else {
                    i3++;
                }
            }
        }
        return bArr;
    }

    public static byte[] encodeMod3Tight(int[] iArr) {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int length = iArr.length - 1; length >= 0; length--) {
            bigInteger = bigInteger.multiply(BigInteger.valueOf(3)).add(BigInteger.valueOf((long) (iArr[length] + 1)));
        }
        int bitLength = (BigInteger.valueOf(3).pow(iArr.length).bitLength() + 7) / 8;
        byte[] toByteArray = bigInteger.toByteArray();
        if (toByteArray.length >= bitLength) {
            return toByteArray.length > bitLength ? Arrays.copyOfRange(toByteArray, 1, toByteArray.length) : toByteArray;
        } else {
            Object obj = new byte[bitLength];
            System.arraycopy(toByteArray, 0, obj, bitLength - toByteArray.length, toByteArray.length);
            return obj;
        }
    }

    public static byte[] encodeModQ(int[] iArr, int i) {
        int numberOfLeadingZeros = 31 - Integer.numberOfLeadingZeros(i);
        byte[] bArr = new byte[(((iArr.length * numberOfLeadingZeros) + 7) / 8)];
        int i2 = 0;
        int i3 = 0;
        for (int i4 : iArr) {
            for (int i5 = 0; i5 < numberOfLeadingZeros; i5++) {
                bArr[i2] = (byte) ((((i4 >> i5) & 1) << i3) | bArr[i2]);
                if (i3 == 7) {
                    i2++;
                    i3 = 0;
                } else {
                    i3++;
                }
            }
        }
        return bArr;
    }

    private static int getBit(byte[] bArr, int i) {
        return ((bArr[i / 8] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) >> (i % 8)) & 1;
    }
}
