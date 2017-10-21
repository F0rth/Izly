package okhttp3.internal.http;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import okhttp3.internal.Util;

public final class HttpDate {
    private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS;
    private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
    public static final long MAX_DATE = 253402300799999L;
    private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        protected final DateFormat initialValue() {
            DateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            simpleDateFormat.setLenient(false);
            simpleDateFormat.setTimeZone(Util.UTC);
            return simpleDateFormat;
        }
    };

    static {
        String[] strArr = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
        BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = strArr;
        BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[strArr.length];
    }

    private HttpDate() {
    }

    public static String format(Date date) {
        return ((DateFormat) STANDARD_DATE_FORMAT.get()).format(date);
    }

    public static Date parse(String str) {
        Date date;
        int i = 0;
        if (str.length() == 0) {
            date = null;
        } else {
            ParsePosition parsePosition = new ParsePosition(0);
            date = ((DateFormat) STANDARD_DATE_FORMAT.get()).parse(str, parsePosition);
            if (parsePosition.getIndex() != str.length()) {
                synchronized (BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS) {
                    int length = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
                    while (i < length) {
                        DateFormat dateFormat = BROWSER_COMPATIBLE_DATE_FORMATS[i];
                        if (dateFormat == null) {
                            dateFormat = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[i], Locale.US);
                            dateFormat.setTimeZone(Util.UTC);
                            BROWSER_COMPATIBLE_DATE_FORMATS[i] = dateFormat;
                        }
                        parsePosition.setIndex(0);
                        date = dateFormat.parse(str, parsePosition);
                        if (parsePosition.getIndex() != 0) {
                            return date;
                        }
                        i++;
                    }
                    return null;
                }
            }
        }
        return date;
    }
}
