package com.fasterxml.jackson.core.io;

import java.math.BigDecimal;

public final class NumberInput {
    static final long L_BILLION = 1000000000;
    static final String MAX_LONG_STR = "9223372036854775807";
    static final String MIN_LONG_STR_NO_SIGN = "-9223372036854775808".substring(1);
    public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";

    private static NumberFormatException _badBD(String str) {
        return new NumberFormatException("Value \"" + str + "\" can not be represented as BigDecimal");
    }

    public static boolean inLongRange(String str, boolean z) {
        String str2 = z ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int length = str2.length();
        int length2 = str.length();
        if (length2 < length) {
            return true;
        }
        if (length2 > length) {
            return false;
        }
        length2 = 0;
        while (length2 < length) {
            int charAt = str.charAt(length2) - str2.charAt(length2);
            if (charAt != 0) {
                return charAt < 0;
            } else {
                length2++;
            }
        }
        return true;
    }

    public static boolean inLongRange(char[] cArr, int i, int i2, boolean z) {
        String str = z ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
        int length = str.length();
        if (i2 < length) {
            return true;
        }
        if (i2 > length) {
            return false;
        }
        int i3 = 0;
        while (i3 < length) {
            int charAt = cArr[i + i3] - str.charAt(i3);
            if (charAt != 0) {
                return charAt < 0;
            } else {
                i3++;
            }
        }
        return true;
    }

    public static double parseAsDouble(String str, double d) {
        if (str != null) {
            String trim = str.trim();
            if (trim.length() != 0) {
                try {
                    d = parseDouble(trim);
                } catch (NumberFormatException e) {
                }
            }
        }
        return d;
    }

    public static int parseAsInt(String str, int i) {
        int i2 = 0;
        if (str == null) {
            return i;
        }
        String trim = str.trim();
        int length = trim.length();
        if (length == 0) {
            return i;
        }
        String substring;
        char charAt;
        if (length > 0) {
            char charAt2 = trim.charAt(0);
            if (charAt2 == '+') {
                substring = trim.substring(1);
                length = substring.length();
            } else if (charAt2 == '-') {
                i2 = 1;
                substring = trim;
            }
            while (i2 < length) {
                charAt = substring.charAt(i2);
                if (charAt <= '9' || charAt < '0') {
                    try {
                        return (int) parseDouble(substring);
                    } catch (NumberFormatException e) {
                        return i;
                    }
                }
                i2++;
            }
            return Integer.parseInt(substring);
        }
        substring = trim;
        while (i2 < length) {
            charAt = substring.charAt(i2);
            if (charAt <= '9') {
            }
            return (int) parseDouble(substring);
        }
        try {
            return Integer.parseInt(substring);
        } catch (NumberFormatException e2) {
            return i;
        }
    }

    public static long parseAsLong(String str, long j) {
        int i = 0;
        if (str == null) {
            return j;
        }
        String trim = str.trim();
        int length = trim.length();
        if (length == 0) {
            return j;
        }
        String substring;
        char charAt;
        if (length > 0) {
            char charAt2 = trim.charAt(0);
            if (charAt2 == '+') {
                substring = trim.substring(1);
                length = substring.length();
            } else if (charAt2 == '-') {
                i = 1;
                substring = trim;
            }
            while (i < length) {
                charAt = substring.charAt(i);
                if (charAt <= '9' || charAt < '0') {
                    try {
                        return (long) parseDouble(substring);
                    } catch (NumberFormatException e) {
                        return j;
                    }
                }
                i++;
            }
            return Long.parseLong(substring);
        }
        substring = trim;
        while (i < length) {
            charAt = substring.charAt(i);
            if (charAt <= '9') {
            }
            return (long) parseDouble(substring);
        }
        try {
            return Long.parseLong(substring);
        } catch (NumberFormatException e2) {
            return j;
        }
    }

    public static BigDecimal parseBigDecimal(String str) throws NumberFormatException {
        try {
            return new BigDecimal(str);
        } catch (NumberFormatException e) {
            throw _badBD(str);
        }
    }

    public static BigDecimal parseBigDecimal(char[] cArr) throws NumberFormatException {
        return parseBigDecimal(cArr, 0, cArr.length);
    }

    public static BigDecimal parseBigDecimal(char[] cArr, int i, int i2) throws NumberFormatException {
        try {
            return new BigDecimal(cArr, i, i2);
        } catch (NumberFormatException e) {
            throw _badBD(new String(cArr, i, i2));
        }
    }

    public static double parseDouble(String str) throws NumberFormatException {
        return NASTY_SMALL_DOUBLE.equals(str) ? Double.MIN_VALUE : Double.parseDouble(str);
    }

    public static int parseInt(String str) {
        int i;
        char charAt = str.charAt(0);
        int length = str.length();
        int i2 = charAt == '-' ? 1 : 0;
        if (i2 != 0) {
            if (length == 1 || length > 10) {
                return Integer.parseInt(str);
            }
            int charAt2 = str.charAt(1);
            i = 2;
        } else if (length > 9) {
            return Integer.parseInt(str);
        } else {
            char c = charAt;
            i = 1;
        }
        if (charAt2 > 57 || charAt2 < 48) {
            return Integer.parseInt(str);
        }
        charAt2 -= 48;
        if (i < length) {
            int i3 = i + 1;
            charAt = str.charAt(i);
            if (charAt > '9' || charAt < '0') {
                return Integer.parseInt(str);
            }
            i = (charAt - 48) + (charAt2 * 10);
            if (i3 < length) {
                charAt2 = i3 + 1;
                char charAt3 = str.charAt(i3);
                if (charAt3 > '9' || charAt3 < '0') {
                    return Integer.parseInt(str);
                }
                i = (i * 10) + (charAt3 - 48);
                if (charAt2 < length) {
                    i3 = i;
                    while (true) {
                        i = charAt2 + 1;
                        c = str.charAt(charAt2);
                        if (c <= '9' && c >= '0') {
                            charAt2 = (c - 48) + (i3 * 10);
                            if (i >= length) {
                                break;
                            }
                            i3 = charAt2;
                            charAt2 = i;
                        }
                    }
                    return Integer.parseInt(str);
                }
            }
            charAt2 = i;
        }
        return i2 != 0 ? -charAt2 : charAt2;
    }

    public static int parseInt(char[] cArr, int i, int i2) {
        int i3;
        int i4 = cArr[i] - 48;
        if (i2 > 4) {
            i3 = i + 1;
            char c = cArr[i3];
            i3++;
            char c2 = cArr[i3];
            i3++;
            i = i3 + 1;
            i4 = (((((((i4 * 10) + (c - 48)) * 10) + (c2 - 48)) * 10) + (cArr[i3] - 48)) * 10) + (cArr[i] - 48);
            i2 -= 4;
            if (i2 > 4) {
                i3 = i + 1;
                c = cArr[i3];
                i3++;
                c2 = cArr[i3];
                i3++;
                return (((((((i4 * 10) + (c - 48)) * 10) + (c2 - 48)) * 10) + (cArr[i3] - 48)) * 10) + (cArr[i3 + 1] - 48);
            }
        }
        if (i2 <= 1) {
            return i4;
        }
        i3 = i + 1;
        i4 = (i4 * 10) + (cArr[i3] - 48);
        if (i2 <= 2) {
            return i4;
        }
        i3++;
        i4 = (i4 * 10) + (cArr[i3] - 48);
        return i2 > 3 ? (i4 * 10) + (cArr[i3 + 1] - 48) : i4;
    }

    public static long parseLong(String str) {
        return str.length() <= 9 ? (long) parseInt(str) : Long.parseLong(str);
    }

    public static long parseLong(char[] cArr, int i, int i2) {
        int i3 = i2 - 9;
        return ((long) parseInt(cArr, i3 + i, 9)) + (((long) parseInt(cArr, i, i3)) * L_BILLION);
    }
}
