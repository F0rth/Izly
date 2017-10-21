package com.google.zxing.client.result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CalendarParsedResult extends ParsedResult {
    private static final DateFormat DATE_FORMAT;
    private static final Pattern DATE_TIME = Pattern.compile("[0-9]{8}(T[0-9]{6}Z?)?");
    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH);
    private static final Pattern RFC2445_DURATION = Pattern.compile("P(?:(\\d+)W)?(?:(\\d+)D)?(?:T(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?)?");
    private static final long[] RFC2445_DURATION_FIELD_UNITS = new long[]{604800000, 86400000, 3600000, 60000, 1000};
    private final String[] attendees;
    private final String description;
    private final Date end;
    private final boolean endAllDay;
    private final double latitude;
    private final String location;
    private final double longitude;
    private final String organizer;
    private final Date start;
    private final boolean startAllDay;
    private final String summary;

    static {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        DATE_FORMAT = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public CalendarParsedResult(String str, String str2, String str3, String str4, String str5, String str6, String[] strArr, String str7, double d, double d2) {
        super(ParsedResultType.CALENDAR);
        this.summary = str;
        try {
            this.start = parseDate(str2);
            if (str3 == null) {
                long parseDurationMS = parseDurationMS(str4);
                this.end = parseDurationMS < 0 ? null : new Date(parseDurationMS + this.start.getTime());
            } else {
                try {
                    this.end = parseDate(str3);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e.toString());
                }
            }
            this.startAllDay = str2.length() == 8;
            boolean z = str3 != null && str3.length() == 8;
            this.endAllDay = z;
            this.location = str5;
            this.organizer = str6;
            this.attendees = strArr;
            this.description = str7;
            this.latitude = d;
            this.longitude = d2;
        } catch (ParseException e2) {
            throw new IllegalArgumentException(e2.toString());
        }
    }

    private static String format(boolean z, Date date) {
        if (date == null) {
            return null;
        }
        return (z ? DateFormat.getDateInstance(2) : DateFormat.getDateTimeInstance(2, 2)).format(date);
    }

    private static Date parseDate(String str) throws ParseException {
        if (!DATE_TIME.matcher(str).matches()) {
            throw new ParseException(str, 0);
        } else if (str.length() == 8) {
            return DATE_FORMAT.parse(str);
        } else {
            if (str.length() != 16 || str.charAt(15) != 'Z') {
                return DATE_TIME_FORMAT.parse(str);
            }
            Date parse = DATE_TIME_FORMAT.parse(str.substring(0, 15));
            Calendar gregorianCalendar = new GregorianCalendar();
            long time = parse.getTime() + ((long) gregorianCalendar.get(15));
            gregorianCalendar.setTime(new Date(time));
            return new Date(time + ((long) gregorianCalendar.get(16)));
        }
    }

    private static long parseDurationMS(CharSequence charSequence) {
        long j = -1;
        if (charSequence != null) {
            Matcher matcher = RFC2445_DURATION.matcher(charSequence);
            if (matcher.matches()) {
                j = 0;
                for (int i = 0; i < RFC2445_DURATION_FIELD_UNITS.length; i++) {
                    String group = matcher.group(i + 1);
                    if (group != null) {
                        j += ((long) Integer.parseInt(group)) * RFC2445_DURATION_FIELD_UNITS[i];
                    }
                }
            }
        }
        return j;
    }

    public final String[] getAttendees() {
        return this.attendees;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(100);
        ParsedResult.maybeAppend(this.summary, stringBuilder);
        ParsedResult.maybeAppend(format(this.startAllDay, this.start), stringBuilder);
        ParsedResult.maybeAppend(format(this.endAllDay, this.end), stringBuilder);
        ParsedResult.maybeAppend(this.location, stringBuilder);
        ParsedResult.maybeAppend(this.organizer, stringBuilder);
        ParsedResult.maybeAppend(this.attendees, stringBuilder);
        ParsedResult.maybeAppend(this.description, stringBuilder);
        return stringBuilder.toString();
    }

    public final Date getEnd() {
        return this.end;
    }

    public final double getLatitude() {
        return this.latitude;
    }

    public final String getLocation() {
        return this.location;
    }

    public final double getLongitude() {
        return this.longitude;
    }

    public final String getOrganizer() {
        return this.organizer;
    }

    public final Date getStart() {
        return this.start;
    }

    public final String getSummary() {
        return this.summary;
    }

    public final boolean isEndAllDay() {
        return this.endAllDay;
    }

    public final boolean isStartAllDay() {
        return this.startAllDay;
    }
}
