package com.google.zxing.oned.rss;

public final class RSSUtils {
    private RSSUtils() {
    }

    private static int combins(int i, int i2) {
        int i3;
        int i4 = 1;
        if (i - i2 > i2) {
            i3 = i2;
            i2 = i - i2;
        } else {
            i3 = i - i2;
        }
        int i5 = 1;
        while (i > i2) {
            i4 *= i;
            if (i5 <= i3) {
                i4 /= i5;
                i5++;
            }
            i--;
        }
        int i6 = i4;
        i5 = i6;
        for (i4 = i5; i4 <= i3; i4++) {
            i5 /= i4;
        }
        return i5;
    }

    static int[] elements(int[] iArr, int i, int i2) {
        int[] iArr2 = new int[(iArr.length + 2)];
        int i3 = i2 << 1;
        iArr2[0] = 1;
        int i4 = 10;
        int i5 = 1;
        for (int i6 = 1; i6 < i3 - 2; i6 += 2) {
            iArr2[i6] = iArr[i6 - 1] - iArr2[i6 - 1];
            iArr2[i6 + 1] = iArr[i6] - iArr2[i6];
            i5 += iArr2[i6] + iArr2[i6 + 1];
            if (iArr2[i6] < i4) {
                i4 = iArr2[i6];
            }
        }
        iArr2[i3 - 1] = i - i5;
        i5 = iArr2[i3 + -1] < i4 ? iArr2[i3 - 1] : i4;
        if (i5 > 1) {
            for (i4 = 0; i4 < i3; i4 += 2) {
                iArr2[i4] = iArr2[i4] + (i5 - 1);
                int i7 = i4 + 1;
                iArr2[i7] = iArr2[i7] - (i5 - 1);
            }
        }
        return iArr2;
    }

    public static int getRSSvalue(int[] iArr, int i, boolean z) {
        int length = iArr.length;
        int length2 = iArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length2) {
            int i4 = iArr[i2];
            i2++;
            i3 += i4;
        }
        int i5 = 0;
        int i6 = i3;
        length2 = 0;
        i3 = 0;
        while (i5 < length - 1) {
            length2 = (1 << i5) | length2;
            i2 = i3;
            i3 = 1;
            while (i3 < iArr[i5]) {
                i4 = combins((i6 - i3) - 1, (length - i5) - 2);
                if (z && length2 == 0 && (i6 - i3) - ((length - i5) - 1) >= (length - i5) - 1) {
                    i4 -= combins((i6 - i3) - (length - i5), (length - i5) - 2);
                }
                if ((length - i5) - 1 > 1) {
                    int i7 = 0;
                    for (int i8 = (i6 - i3) - ((length - i5) - 2); i8 > i; i8--) {
                        i7 += combins(((i6 - i3) - i8) - 1, (length - i5) - 3);
                    }
                    i4 -= i7 * ((length - 1) - i5);
                } else if (i6 - i3 > i) {
                    i4--;
                }
                i2 += i4;
                i3++;
                length2 &= (1 << i5) ^ -1;
            }
            i5++;
            i6 -= i3;
            i3 = i2;
        }
        return i3;
    }

    static int[] getRSSwidths(int i, int i2, int i3, int i4, boolean z) {
        int[] iArr = new int[i3];
        int i5 = 0;
        int i6 = 0;
        int i7 = i;
        while (i6 < i3 - 1) {
            int combins;
            i5 |= 1 << i6;
            int i8 = 1;
            while (true) {
                combins = combins((i2 - i8) - 1, (i3 - i6) - 2);
                if (z && i5 == 0 && (i2 - i8) - ((i3 - i6) - 1) >= (i3 - i6) - 1) {
                    combins -= combins((i2 - i8) - (i3 - i6), (i3 - i6) - 2);
                }
                if ((i3 - i6) - 1 > 1) {
                    int i9 = 0;
                    for (int i10 = (i2 - i8) - ((i3 - i6) - 2); i10 > i4; i10--) {
                        i9 += combins(((i2 - i8) - i10) - 1, (i3 - i6) - 3);
                    }
                    combins -= i9 * ((i3 - 1) - i6);
                } else if (i2 - i8 > i4) {
                    combins--;
                }
                i7 -= combins;
                if (i7 < 0) {
                    break;
                }
                i8++;
                i5 &= (1 << i6) ^ -1;
            }
            i = i7 + combins;
            i2 -= i8;
            iArr[i6] = i8;
            i6++;
            i7 = i;
        }
        iArr[i6] = i2;
        return iArr;
    }
}
