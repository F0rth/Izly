package com.fasterxml.jackson.core.io;

public final class NumberOutput {
    private static int BILLION = 1000000000;
    private static final char[] FULL_3 = new char[4000];
    private static final byte[] FULL_TRIPLETS_B = new byte[4000];
    private static final char[] LEAD_3 = new char[4000];
    private static long MAX_INT_AS_LONG = 2147483647L;
    private static int MILLION = 1000000;
    private static long MIN_INT_AS_LONG = -2147483648L;
    private static final char NC = '\u0000';
    static final String SMALLEST_LONG = "-9223372036854775808";
    private static long TEN_BILLION_L = 10000000000L;
    private static long THOUSAND_L = 1000;
    private static final String[] sSmallIntStrs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final String[] sSmallIntStrs2 = new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};

    static {
        int i = 0;
        int i2 = 0;
        while (i < 10) {
            char c = (char) (i + 48);
            char c2 = i == 0 ? '\u0000' : c;
            int i3 = 0;
            while (i3 < 10) {
                char c3 = (char) (i3 + 48);
                char c4 = (i == 0 && i3 == 0) ? '\u0000' : c3;
                int i4 = i2;
                for (i2 = 0; i2 < 10; i2++) {
                    char c5 = (char) (i2 + 48);
                    LEAD_3[i4] = c2;
                    LEAD_3[i4 + 1] = c4;
                    LEAD_3[i4 + 2] = c5;
                    FULL_3[i4] = c;
                    FULL_3[i4 + 1] = c3;
                    FULL_3[i4 + 2] = c5;
                    i4 += 4;
                }
                i3++;
                i2 = i4;
            }
            i++;
        }
        for (int i5 = 0; i5 < 4000; i5++) {
            FULL_TRIPLETS_B[i5] = (byte) FULL_3[i5];
        }
    }

    private static int calcLongStrLength(long j) {
        int i = 10;
        for (long j2 = TEN_BILLION_L; j >= j2 && i != 19; j2 = (j2 << 3) + (j2 << 1)) {
            i++;
        }
        return i;
    }

    private static int full3(int i, byte[] bArr, int i2) {
        int i3 = i << 2;
        int i4 = i2 + 1;
        int i5 = i3 + 1;
        bArr[i2] = FULL_TRIPLETS_B[i3];
        i3 = i4 + 1;
        bArr[i4] = FULL_TRIPLETS_B[i5];
        bArr[i3] = FULL_TRIPLETS_B[i5 + 1];
        return i3 + 1;
    }

    private static int full3(int i, char[] cArr, int i2) {
        int i3 = i << 2;
        int i4 = i2 + 1;
        int i5 = i3 + 1;
        cArr[i2] = FULL_3[i3];
        i3 = i4 + 1;
        cArr[i4] = FULL_3[i5];
        cArr[i3] = FULL_3[i5 + 1];
        return i3 + 1;
    }

    private static int leading3(int i, byte[] bArr, int i2) {
        int i3 = i << 2;
        int i4 = i3 + 1;
        char c = LEAD_3[i3];
        if (c != '\u0000') {
            bArr[i2] = (byte) c;
            i2++;
        }
        c = LEAD_3[i4];
        if (c != '\u0000') {
            bArr[i2] = (byte) c;
            i2++;
        }
        bArr[i2] = (byte) LEAD_3[i4 + 1];
        return i2 + 1;
    }

    private static int leading3(int i, char[] cArr, int i2) {
        int i3 = i << 2;
        int i4 = i3 + 1;
        char c = LEAD_3[i3];
        if (c != '\u0000') {
            cArr[i2] = c;
            i2++;
        }
        c = LEAD_3[i4];
        if (c != '\u0000') {
            cArr[i2] = c;
            i2++;
        }
        cArr[i2] = LEAD_3[i4 + 1];
        return i2 + 1;
    }

    public static int outputInt(int i, byte[] bArr, int i2) {
        if (i < 0) {
            if (i == Integer.MIN_VALUE) {
                return outputLong((long) i, bArr, i2);
            }
            bArr[i2] = (byte) 45;
            i = -i;
            i2++;
        }
        if (i >= MILLION) {
            Object obj = i >= BILLION ? 1 : null;
            if (obj != null) {
                i -= BILLION;
                if (i >= BILLION) {
                    i -= BILLION;
                    bArr[i2] = (byte) 50;
                    i2++;
                } else {
                    bArr[i2] = (byte) 49;
                    i2++;
                }
            }
            int i3 = i / 1000;
            int i4 = i3 / 1000;
            return full3(i - (i3 * 1000), bArr, full3(i3 - (i4 * 1000), bArr, obj != null ? full3(i4, bArr, i2) : leading3(i4, bArr, i2)));
        } else if (i >= 1000) {
            int i5 = i / 1000;
            return full3(i - (i5 * 1000), bArr, leading3(i5, bArr, i2));
        } else if (i >= 10) {
            return leading3(i, bArr, i2);
        } else {
            bArr[i2] = (byte) (i + 48);
            return i2 + 1;
        }
    }

    public static int outputInt(int i, char[] cArr, int i2) {
        if (i < 0) {
            if (i == Integer.MIN_VALUE) {
                return outputLong((long) i, cArr, i2);
            }
            cArr[i2] = '-';
            i = -i;
            i2++;
        }
        if (i >= MILLION) {
            Object obj = i >= BILLION ? 1 : null;
            if (obj != null) {
                i -= BILLION;
                if (i >= BILLION) {
                    i -= BILLION;
                    cArr[i2] = '2';
                    i2++;
                } else {
                    cArr[i2] = '1';
                    i2++;
                }
            }
            int i3 = i / 1000;
            int i4 = i3 / 1000;
            return full3(i - (i3 * 1000), cArr, full3(i3 - (i4 * 1000), cArr, obj != null ? full3(i4, cArr, i2) : leading3(i4, cArr, i2)));
        } else if (i >= 1000) {
            int i5 = i / 1000;
            return full3(i - (i5 * 1000), cArr, leading3(i5, cArr, i2));
        } else if (i >= 10) {
            return leading3(i, cArr, i2);
        } else {
            cArr[i2] = (char) (i + 48);
            return i2 + 1;
        }
    }

    public static int outputLong(long j, byte[] bArr, int i) {
        int length;
        int i2;
        int i3;
        if (j < 0) {
            if (j > MIN_INT_AS_LONG) {
                return outputInt((int) j, bArr, i);
            }
            if (j == Long.MIN_VALUE) {
                length = SMALLEST_LONG.length();
                i2 = 0;
                i3 = i;
                while (i2 < length) {
                    bArr[i3] = (byte) SMALLEST_LONG.charAt(i2);
                    i2++;
                    i3++;
                }
                return i3;
            }
            bArr[i] = (byte) 45;
            j = -j;
            i++;
        } else if (j <= MAX_INT_AS_LONG) {
            return outputInt((int) j, bArr, i);
        }
        length = i + calcLongStrLength(j);
        i3 = length;
        while (j > MAX_INT_AS_LONG) {
            int i4 = i3 - 3;
            long j2 = j / THOUSAND_L;
            full3((int) (j - (THOUSAND_L * j2)), bArr, i4);
            j = j2;
            i3 = i4;
        }
        i2 = (int) j;
        while (i2 >= 1000) {
            i3 -= 3;
            i4 = i2 / 1000;
            full3(i2 - (i4 * 1000), bArr, i3);
            i2 = i4;
        }
        leading3(i2, bArr, i);
        return length;
    }

    public static int outputLong(long j, char[] cArr, int i) {
        int length;
        if (j < 0) {
            if (j > MIN_INT_AS_LONG) {
                return outputInt((int) j, cArr, i);
            }
            if (j == Long.MIN_VALUE) {
                length = SMALLEST_LONG.length();
                SMALLEST_LONG.getChars(0, length, cArr, i);
                return i + length;
            }
            cArr[i] = '-';
            j = -j;
            i++;
        } else if (j <= MAX_INT_AS_LONG) {
            return outputInt((int) j, cArr, i);
        }
        int calcLongStrLength = i + calcLongStrLength(j);
        length = calcLongStrLength;
        while (j > MAX_INT_AS_LONG) {
            int i2 = length - 3;
            long j2 = j / THOUSAND_L;
            full3((int) (j - (THOUSAND_L * j2)), cArr, i2);
            j = j2;
            length = i2;
        }
        int i3 = (int) j;
        while (i3 >= 1000) {
            length -= 3;
            i2 = i3 / 1000;
            full3(i3 - (i2 * 1000), cArr, length);
            i3 = i2;
        }
        leading3(i3, cArr, i);
        return calcLongStrLength;
    }

    public static String toString(double d) {
        return Double.toString(d);
    }

    public static String toString(float f) {
        return Float.toString(f);
    }

    public static String toString(int i) {
        if (i < sSmallIntStrs.length) {
            if (i >= 0) {
                return sSmallIntStrs[i];
            }
            int i2 = (-i) - 1;
            if (i2 < sSmallIntStrs2.length) {
                return sSmallIntStrs2[i2];
            }
        }
        return Integer.toString(i);
    }

    public static String toString(long j) {
        return (j > 2147483647L || j < -2147483648L) ? Long.toString(j) : toString((int) j);
    }
}
