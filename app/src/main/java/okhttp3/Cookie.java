package okhttp3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;

public final class Cookie {
    private static final Pattern DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");
    private static final Pattern MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
    private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
    private final String domain;
    private final long expiresAt;
    private final boolean hostOnly;
    private final boolean httpOnly;
    private final String name;
    private final String path;
    private final boolean persistent;
    private final boolean secure;
    private final String value;

    public static final class Builder {
        String domain;
        long expiresAt = HttpDate.MAX_DATE;
        boolean hostOnly;
        boolean httpOnly;
        String name;
        String path = "/";
        boolean persistent;
        boolean secure;
        String value;

        private Builder domain(String str, boolean z) {
            if (str == null) {
                throw new NullPointerException("domain == null");
            }
            String domainToAscii = Util.domainToAscii(str);
            if (domainToAscii == null) {
                throw new IllegalArgumentException("unexpected domain: " + str);
            }
            this.domain = domainToAscii;
            this.hostOnly = z;
            return this;
        }

        public final Cookie build() {
            return new Cookie();
        }

        public final Builder domain(String str) {
            return domain(str, false);
        }

        public final Builder expiresAt(long j) {
            long j2 = HttpDate.MAX_DATE;
            long j3 = j <= 0 ? Long.MIN_VALUE : j;
            if (j3 <= HttpDate.MAX_DATE) {
                j2 = j3;
            }
            this.expiresAt = j2;
            this.persistent = true;
            return this;
        }

        public final Builder hostOnlyDomain(String str) {
            return domain(str, true);
        }

        public final Builder httpOnly() {
            this.httpOnly = true;
            return this;
        }

        public final Builder name(String str) {
            if (str == null) {
                throw new NullPointerException("name == null");
            } else if (str.trim().equals(str)) {
                this.name = str;
                return this;
            } else {
                throw new IllegalArgumentException("name is not trimmed");
            }
        }

        public final Builder path(String str) {
            if (str.startsWith("/")) {
                this.path = str;
                return this;
            }
            throw new IllegalArgumentException("path must start with '/'");
        }

        public final Builder secure() {
            this.secure = true;
            return this;
        }

        public final Builder value(String str) {
            if (str == null) {
                throw new NullPointerException("value == null");
            } else if (str.trim().equals(str)) {
                this.value = str;
                return this;
            } else {
                throw new IllegalArgumentException("value is not trimmed");
            }
        }
    }

    private Cookie(String str, String str2, long j, String str3, String str4, boolean z, boolean z2, boolean z3, boolean z4) {
        this.name = str;
        this.value = str2;
        this.expiresAt = j;
        this.domain = str3;
        this.path = str4;
        this.secure = z;
        this.httpOnly = z2;
        this.hostOnly = z3;
        this.persistent = z4;
    }

    private Cookie(Builder builder) {
        if (builder.name == null) {
            throw new NullPointerException("builder.name == null");
        } else if (builder.value == null) {
            throw new NullPointerException("builder.value == null");
        } else if (builder.domain == null) {
            throw new NullPointerException("builder.domain == null");
        } else {
            this.name = builder.name;
            this.value = builder.value;
            this.expiresAt = builder.expiresAt;
            this.domain = builder.domain;
            this.path = builder.path;
            this.secure = builder.secure;
            this.httpOnly = builder.httpOnly;
            this.persistent = builder.persistent;
            this.hostOnly = builder.hostOnly;
        }
    }

    private static int dateCharacterOffset(String str, int i, int i2, boolean z) {
        for (int i3 = i; i3 < i2; i3++) {
            char charAt = str.charAt(i3);
            Object obj = ((charAt >= ' ' || charAt == '\t') && charAt < '' && ((charAt < '0' || charAt > '9') && ((charAt < 'a' || charAt > 'z') && ((charAt < 'A' || charAt > 'Z') && charAt != ':')))) ? null : 1;
            if (obj == (!z ? 1 : null)) {
                return i3;
            }
        }
        return i2;
    }

    private static boolean domainMatch(HttpUrl httpUrl, String str) {
        String host = httpUrl.host();
        return host.equals(str) || (host.endsWith(str) && host.charAt((host.length() - str.length()) - 1) == '.' && !Util.verifyAsIpAddress(host));
    }

    static Cookie parse(long j, HttpUrl httpUrl, String str) {
        int length = str.length();
        int delimiterOffset = Util.delimiterOffset(str, 0, length, ';');
        int delimiterOffset2 = Util.delimiterOffset(str, 0, delimiterOffset, '=');
        if (delimiterOffset2 == delimiterOffset) {
            return null;
        }
        String trimSubstring = Util.trimSubstring(str, 0, delimiterOffset2);
        if (trimSubstring.isEmpty()) {
            return null;
        }
        long j2;
        String host;
        String substring;
        String trimSubstring2 = Util.trimSubstring(str, delimiterOffset2 + 1, delimiterOffset);
        long j3 = HttpDate.MAX_DATE;
        long j4 = -1;
        String str2 = null;
        String str3 = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = true;
        boolean z4 = false;
        delimiterOffset++;
        while (delimiterOffset < length) {
            long j5;
            int delimiterOffset3 = Util.delimiterOffset(str, delimiterOffset, length, ';');
            int delimiterOffset4 = Util.delimiterOffset(str, delimiterOffset, delimiterOffset3, '=');
            String trimSubstring3 = Util.trimSubstring(str, delimiterOffset, delimiterOffset4);
            String trimSubstring4 = delimiterOffset4 < delimiterOffset3 ? Util.trimSubstring(str, delimiterOffset4 + 1, delimiterOffset3) : "";
            if (trimSubstring3.equalsIgnoreCase("expires")) {
                try {
                    z4 = true;
                    j5 = j4;
                    j4 = parseExpires(trimSubstring4, 0, trimSubstring4.length());
                    j2 = j5;
                } catch (IllegalArgumentException e) {
                    j2 = j4;
                    j4 = j3;
                }
            } else {
                if (trimSubstring3.equalsIgnoreCase("max-age")) {
                    try {
                        j2 = parseMaxAge(trimSubstring4);
                        z4 = true;
                        j4 = j3;
                    } catch (NumberFormatException e2) {
                        j2 = j4;
                        j4 = j3;
                    }
                } else {
                    if (trimSubstring3.equalsIgnoreCase("domain")) {
                        try {
                            str2 = parseDomain(trimSubstring4);
                            z3 = false;
                            j2 = j4;
                            j4 = j3;
                        } catch (IllegalArgumentException e3) {
                            j2 = j4;
                            j4 = j3;
                        }
                    } else {
                        if (trimSubstring3.equalsIgnoreCase("path")) {
                            str3 = trimSubstring4;
                            j2 = j4;
                            j4 = j3;
                        } else {
                            if (trimSubstring3.equalsIgnoreCase("secure")) {
                                z = true;
                                j2 = j4;
                                j4 = j3;
                            } else {
                                if (trimSubstring3.equalsIgnoreCase("httponly")) {
                                    z2 = true;
                                    j2 = j4;
                                    j4 = j3;
                                } else {
                                    j2 = j4;
                                    j4 = j3;
                                }
                            }
                        }
                    }
                }
            }
            j5 = j2;
            delimiterOffset = delimiterOffset3 + 1;
            j3 = j4;
            j4 = j5;
        }
        if (j4 == Long.MIN_VALUE) {
            j2 = Long.MIN_VALUE;
        } else if (j4 != -1) {
            j2 = (j4 <= 9223372036854775L ? 1000 * j4 : Long.MAX_VALUE) + j;
            if (j2 < j || j2 > HttpDate.MAX_DATE) {
                j2 = HttpDate.MAX_DATE;
            }
        } else {
            j2 = j3;
        }
        if (str2 == null) {
            host = httpUrl.host();
        } else if (!domainMatch(httpUrl, str2)) {
            return null;
        } else {
            host = str2;
        }
        if (str3 == null || !str3.startsWith("/")) {
            str2 = httpUrl.encodedPath();
            int lastIndexOf = str2.lastIndexOf(47);
            substring = lastIndexOf != 0 ? str2.substring(0, lastIndexOf) : "/";
        } else {
            substring = str3;
        }
        return new Cookie(trimSubstring, trimSubstring2, j2, host, substring, z, z2, z3, z4);
    }

    public static Cookie parse(HttpUrl httpUrl, String str) {
        return parse(System.currentTimeMillis(), httpUrl, str);
    }

    public static List<Cookie> parseAll(HttpUrl httpUrl, Headers headers) {
        List values = headers.values("Set-Cookie");
        List list = null;
        int size = values.size();
        int i = 0;
        while (i < size) {
            List arrayList;
            Cookie parse = parse(httpUrl, (String) values.get(i));
            if (parse != null) {
                arrayList = list == null ? new ArrayList() : list;
                arrayList.add(parse);
            } else {
                arrayList = list;
            }
            i++;
            list = arrayList;
        }
        return list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
    }

    private static String parseDomain(String str) {
        if (str.endsWith(".")) {
            throw new IllegalArgumentException();
        }
        if (str.startsWith(".")) {
            str = str.substring(1);
        }
        String domainToAscii = Util.domainToAscii(str);
        if (domainToAscii != null) {
            return domainToAscii;
        }
        throw new IllegalArgumentException();
    }

    private static long parseExpires(String str, int i, int i2) {
        int dateCharacterOffset = dateCharacterOffset(str, i, i2, false);
        int i3 = -1;
        int i4 = -1;
        Matcher matcher = TIME_PATTERN.matcher(str);
        int i5 = dateCharacterOffset;
        dateCharacterOffset = -1;
        int i6 = -1;
        int i7 = -1;
        int i8 = -1;
        int i9 = i5;
        while (i9 < i2) {
            int dateCharacterOffset2 = dateCharacterOffset(str, i9 + 1, i2, true);
            matcher.region(i9, dateCharacterOffset2);
            if (i7 == -1 && matcher.usePattern(TIME_PATTERN).matches()) {
                i7 = Integer.parseInt(matcher.group(1));
                i3 = Integer.parseInt(matcher.group(2));
                dateCharacterOffset = Integer.parseInt(matcher.group(3));
            } else if (i6 == -1 && matcher.usePattern(DAY_OF_MONTH_PATTERN).matches()) {
                i6 = Integer.parseInt(matcher.group(1));
            } else if (i4 == -1 && matcher.usePattern(MONTH_PATTERN).matches()) {
                i4 = MONTH_PATTERN.pattern().indexOf(matcher.group(1).toLowerCase(Locale.US)) / 4;
            } else if (i8 == -1 && matcher.usePattern(YEAR_PATTERN).matches()) {
                i8 = Integer.parseInt(matcher.group(1));
            }
            i9 = dateCharacterOffset(str, dateCharacterOffset2 + 1, i2, false);
        }
        if (i8 >= 70 && i8 <= 99) {
            i8 += 1900;
        }
        if (i8 >= 0 && i8 <= 69) {
            i8 += 2000;
        }
        if (i8 < 1601) {
            throw new IllegalArgumentException();
        } else if (i4 == -1) {
            throw new IllegalArgumentException();
        } else if (i6 <= 0 || i6 > 31) {
            throw new IllegalArgumentException();
        } else if (i7 < 0 || i7 > 23) {
            throw new IllegalArgumentException();
        } else if (i3 < 0 || i3 > 59) {
            throw new IllegalArgumentException();
        } else if (dateCharacterOffset < 0 || dateCharacterOffset > 59) {
            throw new IllegalArgumentException();
        } else {
            Calendar gregorianCalendar = new GregorianCalendar(Util.UTC);
            gregorianCalendar.setLenient(false);
            gregorianCalendar.set(1, i8);
            gregorianCalendar.set(2, i4 - 1);
            gregorianCalendar.set(5, i6);
            gregorianCalendar.set(11, i7);
            gregorianCalendar.set(12, i3);
            gregorianCalendar.set(13, dateCharacterOffset);
            gregorianCalendar.set(14, 0);
            return gregorianCalendar.getTimeInMillis();
        }
    }

    private static long parseMaxAge(String str) {
        try {
            long parseLong = Long.parseLong(str);
            if (parseLong > 0) {
                return parseLong;
            }
        } catch (NumberFormatException e) {
            if (!str.matches("-?\\d+")) {
                throw e;
            } else if (!str.startsWith("-")) {
                return Long.MAX_VALUE;
            }
        }
        return Long.MIN_VALUE;
    }

    private static boolean pathMatch(HttpUrl httpUrl, String str) {
        String encodedPath = httpUrl.encodedPath();
        return encodedPath.equals(str) || (encodedPath.startsWith(str) && (str.endsWith("/") || encodedPath.charAt(str.length()) == '/'));
    }

    public final String domain() {
        return this.domain;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Cookie) {
            Cookie cookie = (Cookie) obj;
            if (cookie.name.equals(this.name) && cookie.value.equals(this.value) && cookie.domain.equals(this.domain) && cookie.path.equals(this.path) && cookie.expiresAt == this.expiresAt && cookie.secure == this.secure && cookie.httpOnly == this.httpOnly && cookie.persistent == this.persistent && cookie.hostOnly == this.hostOnly) {
                return true;
            }
        }
        return false;
    }

    public final long expiresAt() {
        return this.expiresAt;
    }

    public final int hashCode() {
        int i = 1;
        int hashCode = this.name.hashCode();
        int hashCode2 = this.value.hashCode();
        int hashCode3 = this.domain.hashCode();
        int hashCode4 = this.path.hashCode();
        int i2 = (int) (this.expiresAt ^ (this.expiresAt >>> 32));
        int i3 = this.secure ? 0 : 1;
        int i4 = this.httpOnly ? 0 : 1;
        int i5 = this.persistent ? 0 : 1;
        if (this.hostOnly) {
            i = 0;
        }
        return ((((((i3 + ((((((((((hashCode + 527) * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i2) * 31)) * 31) + i4) * 31) + i5) * 31) + i;
    }

    public final boolean hostOnly() {
        return this.hostOnly;
    }

    public final boolean httpOnly() {
        return this.httpOnly;
    }

    public final boolean matches(HttpUrl httpUrl) {
        return (this.hostOnly ? httpUrl.host().equals(this.domain) : domainMatch(httpUrl, this.domain)) && pathMatch(httpUrl, this.path) && (!this.secure || httpUrl.isHttps());
    }

    public final String name() {
        return this.name;
    }

    public final String path() {
        return this.path;
    }

    public final boolean persistent() {
        return this.persistent;
    }

    public final boolean secure() {
        return this.secure;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append('=');
        stringBuilder.append(this.value);
        if (this.persistent) {
            if (this.expiresAt == Long.MIN_VALUE) {
                stringBuilder.append("; max-age=0");
            } else {
                stringBuilder.append("; expires=").append(HttpDate.format(new Date(this.expiresAt)));
            }
        }
        if (!this.hostOnly) {
            stringBuilder.append("; domain=").append(this.domain);
        }
        stringBuilder.append("; path=").append(this.path);
        if (this.secure) {
            stringBuilder.append("; secure");
        }
        if (this.httpOnly) {
            stringBuilder.append("; httponly");
        }
        return stringBuilder.toString();
    }

    public final String value() {
        return this.value;
    }
}
