package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.text.format.Time;
import com.thewingitapp.thirdparties.wingitlib.util.WINGiTUtil;
import fr.smoney.android.izly.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class jk {
    private static Time a;
    private static Time b;
    private static SimpleDateFormat c = new SimpleDateFormat();

    public static long a(String str) {
        long j = -1;
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        Matcher matcher = Pattern.compile("^\\/Date\\((-?[0-9]*)?([\\+\\-]?[0-9]{4})\\)\\/$").matcher(str);
        int groupCount = matcher.groupCount();
        if (!matcher.matches() || groupCount <= 0) {
            return -1;
        }
        String group = matcher.group(1);
        if (group != null && group.length() > 0) {
            j = Long.parseLong(group);
        }
        long offset = (long) Calendar.getInstance().getTimeZone().getOffset(System.currentTimeMillis());
        if (groupCount < 2) {
            return j;
        }
        String group2 = matcher.group(2);
        if (group2 == null) {
            return j;
        }
        long parseInt = (((long) (Integer.parseInt(group2.substring(1)) / 100)) * 3600) * 1000;
        return group2.startsWith("+") ? j + (offset - parseInt) : group2.startsWith("-") ? j + (offset + parseInt) : j;
    }

    public static String a(long j) {
        Time time = new Time();
        time.set(j);
        return time.format("%d/%m/%G - %H:%M");
    }

    private static String a(Context context) {
        if (b == null) {
            b = new Time();
        }
        b.setToNow();
        b.hour = 0;
        b.minute = 0;
        b.second = 0;
        b.normalize(false);
        if (Time.compare(b, a) <= 0) {
            return context.getString(R.string.date_today);
        }
        Time time = b;
        time.monthDay--;
        b.normalize(false);
        return Time.compare(b, a) <= 0 ? context.getString(R.string.date_yesterday) : a.format(context.getResources().getString(R.string.date_format));
    }

    public static String a(Context context, long j) {
        if (a == null) {
            a = new Time();
        }
        a.set(j);
        return jk.a(context);
    }

    public static String a(Context context, long j, boolean z) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH'h'mm");
        Time time = new Time();
        time.setToNow();
        time.hour = 0;
        time.minute = 0;
        time.second = 0;
        time.normalize(false);
        Time time2 = new Time();
        time2.set(j);
        if (Time.compare(time, time2) <= 0) {
            return context.getString(R.string.date_today_with_hours_cap_format, new Object[]{simpleDateFormat2.format(Long.valueOf(j))});
        }
        time.monthDay--;
        time.normalize(false);
        if (Time.compare(time, time2) <= 0) {
            return context.getString(R.string.date_yesterday_with_hours_cap_format, new Object[]{simpleDateFormat2.format(Long.valueOf(j))});
        }
        return context.getString(R.string.date_short_with_hour_format, new Object[]{simpleDateFormat.format(Long.valueOf(j)), simpleDateFormat2.format(Long.valueOf(j))});
    }

    public static String a(Context context, long j, boolean z, boolean z2, boolean z3, boolean z4) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd MMMM");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH'h'mm");
        Time time = new Time();
        time.setToNow();
        time.hour = 0;
        time.minute = 0;
        time.second = 0;
        time.normalize(false);
        if (!z4) {
            Time time2 = new Time();
            time2.set(j);
            if (Time.compare(time, time2) <= 0) {
                return context.getString(R.string.date_today_with_hours_cap_format, new Object[]{simpleDateFormat2.format(Long.valueOf(j))});
            }
            time.monthDay--;
            time.normalize(false);
            if (Time.compare(time, time2) <= 0) {
                return context.getString(R.string.date_yesterday_with_hours_cap_format, new Object[]{simpleDateFormat2.format(Long.valueOf(j))});
            }
        }
        return context.getString(R.string.date_long_with_hours_format, new Object[]{simpleDateFormat.format(Long.valueOf(j)), simpleDateFormat2.format(Long.valueOf(j))});
    }

    public static String a(Context context, Time time) {
        if (a == null) {
            a = new Time(time);
        } else {
            a.set(time);
        }
        return jk.a(context);
    }

    public static String a(Time time) {
        StringBuilder stringBuilder = new StringBuilder();
        long j = time.gmtoff / 3600;
        stringBuilder.append(time.toMillis(false));
        if (j > 0) {
            if (j / 10 >= 1) {
                stringBuilder.append("+").append(j).append("00");
            } else {
                stringBuilder.append("+0").append(j).append("00");
            }
        }
        return stringBuilder.toString();
    }

    public static String a(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str2);
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date.setHours(date.getHours() + 1);
        return simpleDateFormat2.format(date);
    }

    private static boolean a(Time time, Time time2) {
        return time.year == time2.year && time.yearDay == time2.yearDay;
    }

    public static String b(long j) {
        StringBuilder stringBuilder = new StringBuilder("/Date(");
        stringBuilder.append(String.valueOf(j));
        stringBuilder.append("+0000");
        stringBuilder.append(")/");
        return stringBuilder.toString();
    }

    public static String b(Context context, long j) {
        return jk.a(context, j, true, true, false, false);
    }

    public static String b(String str) {
        return new SimpleDateFormat("dd/MM/yy").format(new Date(jk.a(str)));
    }

    public static long c(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        instance.add(10, instance.getTimeZone().getOffset(j) / 360000);
        return instance.getTimeInMillis();
    }

    public static String c(Context context, long j) {
        return jk.a(context, j, true, true, false, true);
    }

    public static String d(Context context, long j) {
        Resources resources = context.getResources();
        if (c == null) {
            c = new SimpleDateFormat();
        }
        if (b == null) {
            b = new Time();
        }
        b.setToNow();
        b.normalize(false);
        if (j >= 0) {
            if (a == null) {
                a = new Time();
            }
            a.set(j);
            a.normalize(true);
        }
        long toMillis = b.toMillis(false) - j;
        if (toMillis <= 0) {
            return resources.getString(R.string.home_news_feed_sameday_just_inthefuture);
        }
        if (toMillis < WINGiTUtil.ONE_MINUTE) {
            return resources.getString(R.string.home_news_feed_sameday_just_now);
        }
        if (toMillis < WINGiTUtil.ONE_HOUR) {
            return String.format(Locale.getDefault(), resources.getQuantityString(R.plurals.home_news_feed_sameday_minutes, (int) (toMillis / WINGiTUtil.ONE_MINUTE)), new Object[]{Integer.valueOf((int) (toMillis / WINGiTUtil.ONE_MINUTE))});
        } else if (jk.a(b, a)) {
            return String.format(Locale.getDefault(), resources.getQuantityString(R.plurals.home_news_feed_sameday_hour, Math.max(b.hour - a.hour, 0)), new Object[]{Integer.valueOf(Math.max(b.hour - a.hour, 0))});
        } else {
            Time time = b;
            Time time2 = a;
            time.monthDay--;
            time.normalize(true);
            if (jk.a(time, time2)) {
                return a.format(context.getString(R.string.home_news_feed_yesterday_php_strftime_format));
            }
            time = b;
            time2 = a;
            time.monthDay -= 7;
            time.normalize(true);
            if (time.before(time2)) {
                return a.format(context.getString(R.string.home_news_feed_within_week_php_strftime_format));
            }
            time = b;
            time2 = a;
            time.month--;
            time.normalize(true);
            if (time.before(time2)) {
                return a.format(context.getString(R.string.home_news_feed_within_a_month_php_strftime_format));
            }
            time = b;
            time2 = a;
            time.year--;
            time.normalize(true);
            return time.before(time2) ? a.format(context.getString(R.string.home_news_feed_within_a_year_php_strftime_format)) : a.format(context.getString(R.string.home_news_feed_other_day_format));
        }
    }
}
