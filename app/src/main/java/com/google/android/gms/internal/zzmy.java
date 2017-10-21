package com.google.android.gms.internal;

public final class zzmy {
    public static String zza(byte[] bArr, int i, int i2, boolean z) {
        if (bArr == null || bArr.length == 0 || i < 0 || i2 <= 0 || i + i2 > bArr.length) {
            return null;
        }
        int i3 = 57;
        if (z) {
            i3 = 75;
        }
        StringBuilder stringBuilder = new StringBuilder(i3 * (((i2 + 16) - 1) / 16));
        i3 = 0;
        int i4 = i2;
        int i5 = 0;
        int i6 = i;
        while (i4 > 0) {
            if (i5 == 0) {
                if (i2 < 65536) {
                    stringBuilder.append(String.format("%04X:", new Object[]{Integer.valueOf(i6)}));
                    i3 = i6;
                } else {
                    stringBuilder.append(String.format("%08X:", new Object[]{Integer.valueOf(i6)}));
                    i3 = i6;
                }
            } else if (i5 == 8) {
                stringBuilder.append(" -");
            }
            stringBuilder.append(String.format(" %02X", new Object[]{Integer.valueOf(bArr[i6] & 255)}));
            int i7 = i4 - 1;
            i5++;
            if (z && (i5 == 16 || i7 == 0)) {
                int i8 = 16 - i5;
                if (i8 > 0) {
                    for (i4 = 0; i4 < i8; i4++) {
                        stringBuilder.append("   ");
                    }
                }
                if (i8 >= 8) {
                    stringBuilder.append("  ");
                }
                stringBuilder.append("  ");
                for (i8 = 0; i8 < i5; i8++) {
                    char c = (char) bArr[i3 + i8];
                    if (c < ' ' || c > '~') {
                        c = '.';
                    }
                    stringBuilder.append(c);
                }
            }
            if (i5 == 16 || i7 == 0) {
                stringBuilder.append('\n');
                i4 = 0;
            } else {
                i4 = i5;
            }
            i6++;
            i5 = i4;
            i4 = i7;
        }
        return stringBuilder.toString();
    }
}
