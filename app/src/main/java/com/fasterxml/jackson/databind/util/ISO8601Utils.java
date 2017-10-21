package com.fasterxml.jackson.databind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {
    @Deprecated
    private static final String GMT_ID = "GMT";
    @Deprecated
    private static final TimeZone TIMEZONE_GMT = TimeZone.getTimeZone(GMT_ID);
    private static final TimeZone TIMEZONE_UTC;
    private static final TimeZone TIMEZONE_Z;
    private static final String UTC_ID = "UTC";

    static {
        TimeZone timeZone = TimeZone.getTimeZone(UTC_ID);
        TIMEZONE_UTC = timeZone;
        TIMEZONE_Z = timeZone;
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        Calendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder stringBuilder = new StringBuilder(((z ? 4 : 0) + 19) + (timeZone.getRawOffset() == 0 ? 1 : 6));
        padInt(stringBuilder, gregorianCalendar.get(1), 4);
        stringBuilder.append('-');
        padInt(stringBuilder, gregorianCalendar.get(2) + 1, 2);
        stringBuilder.append('-');
        padInt(stringBuilder, gregorianCalendar.get(5), 2);
        stringBuilder.append('T');
        padInt(stringBuilder, gregorianCalendar.get(11), 2);
        stringBuilder.append(':');
        padInt(stringBuilder, gregorianCalendar.get(12), 2);
        stringBuilder.append(':');
        padInt(stringBuilder, gregorianCalendar.get(13), 2);
        if (z) {
            stringBuilder.append('.');
            padInt(stringBuilder, gregorianCalendar.get(14), 3);
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int abs = Math.abs((offset / 60000) / 60);
            int abs2 = Math.abs((offset / 60000) % 60);
            stringBuilder.append(offset < 0 ? '-' : '+');
            padInt(stringBuilder, abs, 2);
            stringBuilder.append(':');
            padInt(stringBuilder, abs2, 2);
        } else {
            stringBuilder.append('Z');
        }
        return stringBuilder.toString();
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }

    public static void main(String[] strArr) {
        while (true) {
            long currentTimeMillis = System.currentTimeMillis();
            System.out.println("Pow (" + test1(250000, 3) + ") -> " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
            currentTimeMillis = System.currentTimeMillis();
            System.out.println("Iter (" + test2(250000, 3) + ") -> " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
        }
    }

    private static void padInt(StringBuilder stringBuilder, int i, int i2) {
        String num = Integer.toString(i);
        for (int length = i2 - num.length(); length > 0; length--) {
            stringBuilder.append('0');
        }
        stringBuilder.append(num);
    }

    public static Date parse(String str, ParsePosition parsePosition) throws ParseException {
        String substring;
        Throwable th;
        String message;
        ParseException parseException;
        int i = 0;
        try {
            int index = parsePosition.getIndex();
            int i2 = index + 4;
            int parseInt = parseInt(str, index, i2);
            if (checkOffset(str, i2, '-')) {
                i2++;
            }
            index = i2 + 2;
            int parseInt2 = parseInt(str, i2, index);
            i2 = checkOffset(str, index, '-') ? index + 1 : index;
            index = i2 + 2;
            int parseInt3 = parseInt(str, i2, index);
            boolean checkOffset = checkOffset(str, index, 'T');
            if (checkOffset || str.length() > index) {
                int i3;
                int parseInt4;
                char charAt;
                int i4;
                if (checkOffset) {
                    index++;
                    i2 = index + 2;
                    index = parseInt(str, index, i2);
                    if (checkOffset(str, i2, ':')) {
                        i2++;
                    }
                    i3 = i2 + 2;
                    parseInt4 = parseInt(str, i2, i3);
                    if (checkOffset(str, i3, ':')) {
                        i3++;
                    }
                    if (str.length() > i3) {
                        charAt = str.charAt(i3);
                        if (!(charAt == 'Z' || charAt == '+' || charAt == '-')) {
                            i2 = i3 + 2;
                            i3 = parseInt(str, i3, i2);
                            if (i3 > 59 && i3 < 63) {
                                i3 = 59;
                            }
                            if (checkOffset(str, i2, '.')) {
                                i4 = i2 + 1;
                                i = indexOfNonDigit(str, i4 + 1);
                                int min = Math.min(i, i4 + 3);
                                i2 = parseInt(str, i4, min);
                                switch (min - i4) {
                                    case 1:
                                        i2 *= 100;
                                        break;
                                    case 2:
                                        i2 *= 10;
                                        break;
                                }
                                i4 = i2;
                                int i5 = index;
                                index = i;
                                i = i5;
                            } else {
                                i4 = 0;
                                i = index;
                                index = i2;
                            }
                        }
                    }
                    i4 = 0;
                    i = index;
                    index = i3;
                    i3 = 0;
                } else {
                    parseInt4 = 0;
                    i3 = 0;
                    i4 = 0;
                }
                if (str.length() <= index) {
                    throw new IllegalArgumentException("No time zone indicator");
                }
                TimeZone timeZone;
                charAt = str.charAt(index);
                if (charAt == 'Z') {
                    timeZone = TIMEZONE_Z;
                    index++;
                } else if (charAt == '+' || charAt == '-') {
                    substring = str.substring(index);
                    index += substring.length();
                    if ("+0000".equals(substring) || "+00:00".equals(substring)) {
                        timeZone = TIMEZONE_Z;
                    } else {
                        String stringBuilder = new StringBuilder(GMT_ID).append(substring).toString();
                        timeZone = TimeZone.getTimeZone(stringBuilder);
                        String id = timeZone.getID();
                        if (!(id.equals(stringBuilder) || id.replace(":", "").equals(stringBuilder))) {
                            throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + stringBuilder + " given, resolves to " + timeZone.getID());
                        }
                    }
                } else {
                    throw new IndexOutOfBoundsException("Invalid time zone indicator '" + charAt + "'");
                }
                Calendar gregorianCalendar = new GregorianCalendar(timeZone);
                gregorianCalendar.setLenient(false);
                gregorianCalendar.set(1, parseInt);
                gregorianCalendar.set(2, parseInt2 - 1);
                gregorianCalendar.set(5, parseInt3);
                gregorianCalendar.set(11, i);
                gregorianCalendar.set(12, parseInt4);
                gregorianCalendar.set(13, i3);
                gregorianCalendar.set(14, i4);
                parsePosition.setIndex(index);
                return gregorianCalendar.getTime();
            }
            Calendar gregorianCalendar2 = new GregorianCalendar(parseInt, parseInt2 - 1, parseInt3);
            parsePosition.setIndex(index);
            return gregorianCalendar2.getTime();
        } catch (Throwable e) {
            th = e;
            substring = str == null ? null : "\"" + str + "'";
            message = th.getMessage();
            if (message == null || message.isEmpty()) {
                message = "(" + th.getClass().getName() + ")";
            }
            parseException = new ParseException("Failed to parse date [" + substring + "]: " + message, parsePosition.getIndex());
            parseException.initCause(th);
            throw parseException;
        } catch (Throwable e2) {
            th = e2;
            if (str == null) {
            }
            message = th.getMessage();
            message = "(" + th.getClass().getName() + ")";
            parseException = new ParseException("Failed to parse date [" + substring + "]: " + message, parsePosition.getIndex());
            parseException.initCause(th);
            throw parseException;
        } catch (Throwable e22) {
            th = e22;
            if (str == null) {
            }
            message = th.getMessage();
            message = "(" + th.getClass().getName() + ")";
            parseException = new ParseException("Failed to parse date [" + substring + "]: " + message, parsePosition.getIndex());
            parseException.initCause(th);
            throw parseException;
        }
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        int i3;
        int digit;
        if (i < i2) {
            i3 = i + 1;
            digit = Character.digit(str.charAt(i), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            digit = -digit;
        } else {
            digit = 0;
            i3 = i;
        }
        while (i3 < i2) {
            int digit2 = Character.digit(str.charAt(i3), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            digit = (digit * 10) - digit2;
            i3++;
        }
        return -digit;
    }

    static int test1(int i, int i2) {
        int i3 = 3;
        while (true) {
            i--;
            if (i < 0) {
                return i3;
            }
            i3 = (int) Math.pow(10.0d, (double) i2);
        }
    }

    static int test2(int i, int i2) {
        int i3 = 3;
        while (true) {
            i--;
            if (i < 0) {
                return i3;
            }
            i3 = 10;
            int i4 = i2;
            while (true) {
                i4--;
                if (i4 > 0) {
                    i3 *= 10;
                }
            }
        }
    }

    @Deprecated
    public static TimeZone timeZoneGMT() {
        return TIMEZONE_GMT;
    }
}
