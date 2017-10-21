package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.io.NumberInput;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StdDateFormat extends DateFormat {
    protected static final String[] ALL_FORMATS = new String[]{DATE_FORMAT_STR_ISO8601, DATE_FORMAT_STR_ISO8601_Z, DATE_FORMAT_STR_RFC1123, DATE_FORMAT_STR_PLAIN};
    protected static final DateFormat DATE_FORMAT_ISO8601;
    protected static final DateFormat DATE_FORMAT_ISO8601_Z;
    protected static final DateFormat DATE_FORMAT_PLAIN;
    protected static final DateFormat DATE_FORMAT_RFC1123;
    public static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    protected static final String DATE_FORMAT_STR_ISO8601_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    protected static final String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";
    protected static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final Locale DEFAULT_LOCALE = Locale.US;
    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");
    public static final StdDateFormat instance = new StdDateFormat();
    protected transient DateFormat _formatISO8601;
    protected transient DateFormat _formatISO8601_z;
    protected transient DateFormat _formatPlain;
    protected transient DateFormat _formatRFC1123;
    protected Boolean _lenient;
    protected final Locale _locale;
    protected transient TimeZone _timezone;

    static {
        DateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STR_RFC1123, DEFAULT_LOCALE);
        DATE_FORMAT_RFC1123 = simpleDateFormat;
        simpleDateFormat.setTimeZone(DEFAULT_TIMEZONE);
        simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STR_ISO8601, DEFAULT_LOCALE);
        DATE_FORMAT_ISO8601 = simpleDateFormat;
        simpleDateFormat.setTimeZone(DEFAULT_TIMEZONE);
        simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STR_ISO8601_Z, DEFAULT_LOCALE);
        DATE_FORMAT_ISO8601_Z = simpleDateFormat;
        simpleDateFormat.setTimeZone(DEFAULT_TIMEZONE);
        simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_STR_PLAIN, DEFAULT_LOCALE);
        DATE_FORMAT_PLAIN = simpleDateFormat;
        simpleDateFormat.setTimeZone(DEFAULT_TIMEZONE);
    }

    public StdDateFormat() {
        this._locale = DEFAULT_LOCALE;
    }

    @Deprecated
    public StdDateFormat(TimeZone timeZone, Locale locale) {
        this._timezone = timeZone;
        this._locale = locale;
    }

    protected StdDateFormat(TimeZone timeZone, Locale locale, Boolean bool) {
        this._timezone = timeZone;
        this._locale = locale;
        this._lenient = bool;
    }

    private static final DateFormat _cloneFormat(DateFormat dateFormat, String str, TimeZone timeZone, Locale locale, Boolean bool) {
        DateFormat dateFormat2;
        if (locale.equals(DEFAULT_LOCALE)) {
            dateFormat2 = (DateFormat) dateFormat.clone();
            if (timeZone != null) {
                dateFormat2.setTimeZone(timeZone);
            }
        } else {
            dateFormat2 = new SimpleDateFormat(str, locale);
            if (timeZone == null) {
                timeZone = DEFAULT_TIMEZONE;
            }
            dateFormat2.setTimeZone(timeZone);
        }
        if (bool != null) {
            dateFormat2.setLenient(bool.booleanValue());
        }
        return dateFormat2;
    }

    public static TimeZone getDefaultTimeZone() {
        return DEFAULT_TIMEZONE;
    }

    @Deprecated
    public static DateFormat getISO8601Format(TimeZone timeZone) {
        return getISO8601Format(timeZone, DEFAULT_LOCALE);
    }

    public static DateFormat getISO8601Format(TimeZone timeZone, Locale locale) {
        return _cloneFormat(DATE_FORMAT_ISO8601, DATE_FORMAT_STR_ISO8601, timeZone, locale, null);
    }

    @Deprecated
    public static DateFormat getRFC1123Format(TimeZone timeZone) {
        return getRFC1123Format(timeZone, DEFAULT_LOCALE);
    }

    public static DateFormat getRFC1123Format(TimeZone timeZone, Locale locale) {
        return _cloneFormat(DATE_FORMAT_RFC1123, DATE_FORMAT_STR_RFC1123, timeZone, locale, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final boolean hasTimeZone(java.lang.String r4) {
        /*
        r3 = 45;
        r2 = 43;
        r0 = r4.length();
        r1 = 6;
        if (r0 < r1) goto L_0x002b;
    L_0x000b:
        r1 = r0 + -6;
        r1 = r4.charAt(r1);
        if (r1 == r2) goto L_0x0015;
    L_0x0013:
        if (r1 != r3) goto L_0x0017;
    L_0x0015:
        r0 = 1;
    L_0x0016:
        return r0;
    L_0x0017:
        r1 = r0 + -5;
        r1 = r4.charAt(r1);
        if (r1 == r2) goto L_0x0015;
    L_0x001f:
        if (r1 == r3) goto L_0x0015;
    L_0x0021:
        r0 = r0 + -3;
        r0 = r4.charAt(r0);
        if (r0 == r2) goto L_0x0015;
    L_0x0029:
        if (r0 == r3) goto L_0x0015;
    L_0x002b:
        r0 = 0;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.util.StdDateFormat.hasTimeZone(java.lang.String):boolean");
    }

    protected void _clearFormats() {
        this._formatRFC1123 = null;
        this._formatISO8601 = null;
        this._formatISO8601_z = null;
        this._formatPlain = null;
    }

    public StdDateFormat clone() {
        return new StdDateFormat(this._timezone, this._locale, this._lenient);
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (this._formatISO8601 == null) {
            this._formatISO8601 = _cloneFormat(DATE_FORMAT_ISO8601, DATE_FORMAT_STR_ISO8601, this._timezone, this._locale, this._lenient);
        }
        return this._formatISO8601.format(date, stringBuffer, fieldPosition);
    }

    public TimeZone getTimeZone() {
        return this._timezone;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public boolean isLenient() {
        return this._lenient == null ? true : this._lenient.booleanValue();
    }

    protected boolean looksLikeISO8601(String str) {
        return str.length() >= 5 && Character.isDigit(str.charAt(0)) && Character.isDigit(str.charAt(3)) && str.charAt(4) == '-';
    }

    public Date parse(String str) throws ParseException {
        Date parseAsISO8601;
        int length;
        String trim = str.trim();
        ParsePosition parsePosition = new ParsePosition(0);
        if (looksLikeISO8601(trim)) {
            parseAsISO8601 = parseAsISO8601(trim, parsePosition, true);
        } else {
            length = trim.length();
            while (true) {
                length--;
                if (length < 0) {
                    break;
                }
                char charAt = trim.charAt(length);
                if ((charAt < '0' || charAt > '9') && (length > 0 || charAt != '-')) {
                    break;
                }
            }
            parseAsISO8601 = (length >= 0 || !(trim.charAt(0) == '-' || NumberInput.inLongRange(trim, false))) ? parseAsRFC1123(trim, parsePosition) : new Date(Long.parseLong(trim));
        }
        if (parseAsISO8601 != null) {
            return parseAsISO8601;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str2 : ALL_FORMATS) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\", \"");
            } else {
                stringBuilder.append('\"');
            }
            stringBuilder.append(str2);
        }
        stringBuilder.append('\"');
        throw new ParseException(String.format("Can not parse date \"%s\": not compatible with any of standard forms (%s)", new Object[]{trim, stringBuilder.toString()}), parsePosition.getErrorIndex());
    }

    public Date parse(String str, ParsePosition parsePosition) {
        if (looksLikeISO8601(str)) {
            try {
                return parseAsISO8601(str, parsePosition, false);
            } catch (ParseException e) {
                return null;
            }
        }
        int length = str.length();
        while (true) {
            length--;
            if (length < 0) {
                break;
            }
            char charAt = str.charAt(length);
            if ((charAt < '0' || charAt > '9') && (length > 0 || charAt != '-')) {
                break;
            }
        }
        return (length >= 0 || !(str.charAt(0) == '-' || NumberInput.inLongRange(str, false))) ? parseAsRFC1123(str, parsePosition) : new Date(Long.parseLong(str));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected java.util.Date parseAsISO8601(java.lang.String r10, java.text.ParsePosition r11, boolean r12) throws java.text.ParseException {
        /*
        r9 = this;
        r7 = 90;
        r5 = 84;
        r6 = 58;
        r4 = 12;
        r3 = 48;
        r1 = r10.length();
        r0 = r1 + -1;
        r0 = r10.charAt(r0);
        r2 = 10;
        if (r1 > r2) goto L_0x0058;
    L_0x0018:
        r2 = java.lang.Character.isDigit(r0);
        if (r2 == 0) goto L_0x0058;
    L_0x001e:
        r1 = r9._formatPlain;
        r0 = "yyyy-MM-dd";
        if (r1 != 0) goto L_0x0034;
    L_0x0024:
        r1 = DATE_FORMAT_PLAIN;
        r2 = "yyyy-MM-dd";
        r3 = r9._timezone;
        r4 = r9._locale;
        r5 = r9._lenient;
        r1 = _cloneFormat(r1, r2, r3, r4, r5);
        r9._formatPlain = r1;
    L_0x0034:
        r1 = r1.parse(r10, r11);
        if (r1 != 0) goto L_0x0163;
    L_0x003a:
        r1 = new java.text.ParseException;
        r2 = "Can not parse date \"%s\": while it seems to fit format '%s', parsing fails (leniency? %s)";
        r3 = 3;
        r3 = new java.lang.Object[r3];
        r4 = 0;
        r3[r4] = r10;
        r4 = 1;
        r3[r4] = r0;
        r0 = 2;
        r4 = r9._lenient;
        r3[r0] = r4;
        r0 = java.lang.String.format(r2, r3);
        r2 = r11.getErrorIndex();
        r1.<init>(r0, r2);
        throw r1;
    L_0x0058:
        if (r0 != r7) goto L_0x008c;
    L_0x005a:
        r0 = r9._formatISO8601_z;
        if (r0 != 0) goto L_0x006e;
    L_0x005e:
        r0 = DATE_FORMAT_ISO8601_Z;
        r2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        r3 = r9._timezone;
        r4 = r9._locale;
        r5 = r9._lenient;
        r0 = _cloneFormat(r0, r2, r3, r4, r5);
        r9._formatISO8601_z = r0;
    L_0x006e:
        r2 = r1 + -4;
        r2 = r10.charAt(r2);
        if (r2 != r6) goto L_0x015c;
    L_0x0076:
        r2 = new java.lang.StringBuilder;
        r2.<init>(r10);
        r1 = r1 + -1;
        r3 = ".000";
        r2.insert(r1, r3);
        r10 = r2.toString();
        r1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x0034;
    L_0x008c:
        r0 = hasTimeZone(r10);
        if (r0 == 0) goto L_0x011d;
    L_0x0092:
        r0 = r1 + -3;
        r0 = r10.charAt(r0);
        if (r0 != r6) goto L_0x00e0;
    L_0x009a:
        r0 = new java.lang.StringBuilder;
        r0.<init>(r10);
        r2 = r1 + -3;
        r1 = r1 + -2;
        r0.delete(r2, r1);
        r10 = r0.toString();
    L_0x00aa:
        r0 = r10.length();
        r1 = r10.lastIndexOf(r5);
        r1 = r0 - r1;
        r1 = r1 + -6;
        if (r1 >= r4) goto L_0x00c6;
    L_0x00b8:
        r0 = r0 + -5;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r10);
        switch(r1) {
            case 5: goto L_0x0117;
            case 6: goto L_0x0112;
            case 7: goto L_0x00c2;
            case 8: goto L_0x010c;
            case 9: goto L_0x0106;
            case 10: goto L_0x0100;
            case 11: goto L_0x00fc;
            default: goto L_0x00c2;
        };
    L_0x00c2:
        r10 = r2.toString();
    L_0x00c6:
        r1 = r9._formatISO8601;
        r0 = r9._formatISO8601;
        if (r0 != 0) goto L_0x00dc;
    L_0x00cc:
        r0 = DATE_FORMAT_ISO8601;
        r1 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        r2 = r9._timezone;
        r3 = r9._locale;
        r4 = r9._lenient;
        r1 = _cloneFormat(r0, r1, r2, r3, r4);
        r9._formatISO8601 = r1;
    L_0x00dc:
        r0 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        goto L_0x0034;
    L_0x00e0:
        r1 = 43;
        if (r0 == r1) goto L_0x00e8;
    L_0x00e4:
        r1 = 45;
        if (r0 != r1) goto L_0x00aa;
    L_0x00e8:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r0 = r0.append(r10);
        r1 = "00";
        r0 = r0.append(r1);
        r10 = r0.toString();
        goto L_0x00aa;
    L_0x00fc:
        r2.insert(r0, r3);
        goto L_0x00c2;
    L_0x0100:
        r1 = "00";
        r2.insert(r0, r1);
        goto L_0x00c2;
    L_0x0106:
        r1 = "000";
        r2.insert(r0, r1);
        goto L_0x00c2;
    L_0x010c:
        r1 = ".000";
        r2.insert(r0, r1);
        goto L_0x00c2;
    L_0x0112:
        r1 = "00.000";
        r2.insert(r0, r1);
    L_0x0117:
        r1 = ":00.000";
        r2.insert(r0, r1);
        goto L_0x00c2;
    L_0x011d:
        r0 = new java.lang.StringBuilder;
        r0.<init>(r10);
        r2 = r10.lastIndexOf(r5);
        r1 = r1 - r2;
        r1 = r1 + -1;
        if (r1 >= r4) goto L_0x0133;
    L_0x012b:
        switch(r1) {
            case 9: goto L_0x0158;
            case 10: goto L_0x0155;
            case 11: goto L_0x0152;
            default: goto L_0x012e;
        };
    L_0x012e:
        r1 = ".000";
        r0.append(r1);
    L_0x0133:
        r0.append(r7);
        r10 = r0.toString();
        r1 = r9._formatISO8601_z;
        r0 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        if (r1 != 0) goto L_0x0034;
    L_0x0140:
        r1 = DATE_FORMAT_ISO8601_Z;
        r2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        r3 = r9._timezone;
        r4 = r9._locale;
        r5 = r9._lenient;
        r1 = _cloneFormat(r1, r2, r3, r4, r5);
        r9._formatISO8601_z = r1;
        goto L_0x0034;
    L_0x0152:
        r0.append(r3);
    L_0x0155:
        r0.append(r3);
    L_0x0158:
        r0.append(r3);
        goto L_0x0133;
    L_0x015c:
        r1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x0034;
    L_0x0163:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.util.StdDateFormat.parseAsISO8601(java.lang.String, java.text.ParsePosition, boolean):java.util.Date");
    }

    protected Date parseAsRFC1123(String str, ParsePosition parsePosition) {
        if (this._formatRFC1123 == null) {
            this._formatRFC1123 = _cloneFormat(DATE_FORMAT_RFC1123, DATE_FORMAT_STR_RFC1123, this._timezone, this._locale, this._lenient);
        }
        return this._formatRFC1123.parse(str, parsePosition);
    }

    public void setLenient(boolean z) {
        Boolean valueOf = Boolean.valueOf(z);
        if (this._lenient != valueOf) {
            this._lenient = valueOf;
            _clearFormats();
        }
    }

    public void setTimeZone(TimeZone timeZone) {
        if (!timeZone.equals(this._timezone)) {
            _clearFormats();
            this._timezone = timeZone;
        }
    }

    public String toString() {
        String str = "DateFormat " + getClass().getName();
        TimeZone timeZone = this._timezone;
        if (timeZone != null) {
            str = str + " (timezone: " + timeZone + ")";
        }
        return str + "(locale: " + this._locale + ")";
    }

    public StdDateFormat withLocale(Locale locale) {
        return locale.equals(this._locale) ? this : new StdDateFormat(this._timezone, locale, this._lenient);
    }

    public StdDateFormat withTimeZone(TimeZone timeZone) {
        if (timeZone == null) {
            timeZone = DEFAULT_TIMEZONE;
        }
        return (timeZone == this._timezone || timeZone.equals(this._timezone)) ? this : new StdDateFormat(timeZone, this._locale, this._lenient);
    }
}
