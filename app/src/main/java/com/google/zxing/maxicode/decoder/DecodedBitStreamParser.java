package com.google.zxing.maxicode.decoder;

import com.google.zxing.common.DecoderResult;
import java.text.DecimalFormat;
import java.text.NumberFormat;

final class DecodedBitStreamParser {
    private static final char ECI = '￺';
    private static final char FS = '\u001c';
    private static final char GS = '\u001d';
    private static final char LATCHA = '￷';
    private static final char LATCHB = '￸';
    private static final char LOCK = '￹';
    private static final NumberFormat NINE_DIGITS = new DecimalFormat("000000000");
    private static final char NS = '￻';
    private static final char PAD = '￼';
    private static final char RS = '\u001e';
    private static final String[] SETS = new String[]{"\nABCDEFGHIJKLMNOPQRSTUVWXYZ￺\u001c\u001d\u001e￻ ￼\"#$%&'()*+,-./0123456789:￱￲￳￴￸", "`abcdefghijklmnopqrstuvwxyz￺\u001c\u001d\u001e￻{￼}~;<=>?[\\]^_ ,./:@!|￼￵￶￼￰￲￳￴￷", "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚ￺\u001c\u001d\u001eÛÜÝÞßª¬±²³µ¹º¼½¾￷ ￹￳￴￸", "àáâãäåæçèéêëìíîïðñòóôõö÷øùú￺\u001c\u001d\u001e￻ûüýþÿ¡¨«¯°´·¸»¿￷ ￲￹￴￸", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a￺￼￼\u001b￻\u001c\u001d\u001e\u001f ¢£¤¥¦§©­®¶￷ ￲￳￹￸", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?"};
    private static final char SHIFTA = '￰';
    private static final char SHIFTB = '￱';
    private static final char SHIFTC = '￲';
    private static final char SHIFTD = '￳';
    private static final char SHIFTE = '￴';
    private static final char THREESHIFTA = '￶';
    private static final NumberFormat THREE_DIGITS = new DecimalFormat("000");
    private static final char TWOSHIFTA = '￵';

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(byte[] bArr, int i) {
        StringBuilder stringBuilder = new StringBuilder(144);
        switch (i) {
            case 2:
            case 3:
                String format;
                if (i == 2) {
                    format = new DecimalFormat("0000000000".substring(0, getPostCode2Length(bArr))).format((long) getPostCode2(bArr));
                } else {
                    format = getPostCode3(bArr);
                }
                String format2 = THREE_DIGITS.format((long) getCountry(bArr));
                String format3 = THREE_DIGITS.format((long) getServiceClass(bArr));
                stringBuilder.append(getMessage(bArr, 10, 84));
                if (!stringBuilder.toString().startsWith("[)>\u001e01\u001d")) {
                    stringBuilder.insert(0, format + GS + format2 + GS + format3 + GS);
                    break;
                }
                stringBuilder.insert(9, format + GS + format2 + GS + format3 + GS);
                break;
            case 4:
                stringBuilder.append(getMessage(bArr, 1, 93));
                break;
            case 5:
                stringBuilder.append(getMessage(bArr, 1, 77));
                break;
        }
        return new DecoderResult(bArr, stringBuilder.toString(), null, String.valueOf(i));
    }

    private static int getBit(int i, byte[] bArr) {
        int i2 = i - 1;
        return (bArr[i2 / 6] & (1 << (5 - (i2 % 6)))) == 0 ? 0 : 1;
    }

    private static int getCountry(byte[] bArr) {
        return getInt(bArr, new byte[]{(byte) 53, (byte) 54, (byte) 43, (byte) 44, (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 37, (byte) 38});
    }

    private static int getInt(byte[] bArr, byte[] bArr2) {
        int i = 0;
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            i += getBit(bArr2[i2], bArr) << ((bArr2.length - i2) - 1);
        }
        return i;
    }

    private static String getMessage(byte[] bArr, int i, int i2) {
        StringBuilder stringBuilder = new StringBuilder();
        int i3 = 0;
        int i4 = 0;
        int i5 = -1;
        int i6 = i;
        while (i6 < i + i2) {
            char charAt = SETS[i4].charAt(bArr[i6]);
            int i7;
            switch (charAt) {
                case '￰':
                case '￱':
                case '￲':
                case '￳':
                case '￴':
                    i7 = i4;
                    i4 = charAt - 65520;
                    i5 = 1;
                    i3 = i7;
                    break;
                case '￵':
                    i7 = i4;
                    i4 = 0;
                    i5 = 2;
                    i3 = i7;
                    break;
                case '￶':
                    i7 = i4;
                    i4 = 0;
                    i5 = 3;
                    i3 = i7;
                    break;
                case '￷':
                    i4 = 0;
                    i5 = -1;
                    break;
                case '￸':
                    i4 = 1;
                    i5 = -1;
                    break;
                case '￹':
                    i5 = -1;
                    break;
                case '￻':
                    i6++;
                    byte b = bArr[i6];
                    i6++;
                    byte b2 = bArr[i6];
                    i6++;
                    byte b3 = bArr[i6];
                    i6++;
                    byte b4 = bArr[i6];
                    i6++;
                    stringBuilder.append(NINE_DIGITS.format((long) (((((b << 24) + (b2 << 18)) + (b3 << 12)) + (b4 << 6)) + bArr[i6])));
                    break;
                default:
                    stringBuilder.append(charAt);
                    break;
            }
            if (i5 == 0) {
                i4 = i3;
            }
            i5--;
            i6++;
        }
        while (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == PAD) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private static int getPostCode2(byte[] bArr) {
        return getInt(bArr, new byte[]{(byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 25, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 13, (byte) 14, (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 1, (byte) 2});
    }

    private static int getPostCode2Length(byte[] bArr) {
        return getInt(bArr, new byte[]{(byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 31, (byte) 32});
    }

    private static String getPostCode3(byte[] bArr) {
        return String.valueOf(new char[]{SETS[0].charAt(getInt(bArr, new byte[]{(byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 31, (byte) 32})), SETS[0].charAt(getInt(bArr, new byte[]{(byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 25, (byte) 26})), SETS[0].charAt(getInt(bArr, new byte[]{(byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 19, (byte) 20})), SETS[0].charAt(getInt(bArr, new byte[]{(byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 13, (byte) 14})), SETS[0].charAt(getInt(bArr, new byte[]{(byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 7, (byte) 8})), SETS[0].charAt(getInt(bArr, new byte[]{(byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 1, (byte) 2}))});
    }

    private static int getServiceClass(byte[] bArr) {
        return getInt(bArr, new byte[]{(byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 60, (byte) 49, (byte) 50, (byte) 51, (byte) 52});
    }
}
