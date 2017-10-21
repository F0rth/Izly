package com.thewingitapp.thirdparties.wingitlib.util;

import android.text.format.DateUtils;
import java.util.Date;

public class WINGiTUtil {
    public static final long ONE_DAY = 86400000;
    public static final long ONE_HOUR = 3600000;
    public static final long ONE_MINUTE = 60000;

    public static boolean isTomorrow(Date date) {
        return DateUtils.isToday(date.getTime() - ONE_DAY);
    }

    public static boolean isYesterday(Date date) {
        return DateUtils.isToday(date.getTime() + ONE_DAY);
    }

    public static double metersToYards(double d) {
        return Math.ceil(1.09361d * d);
    }

    public static double yardsToMiles(double d) {
        return Math.ceil(5.6818E-4d * d);
    }
}
