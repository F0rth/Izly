package com.ad4screen.sdk.common;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class g implements a {
    protected g() {
    }

    public static a e() {
        return new g();
    }

    public long a() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
    }

    public long b() {
        return System.currentTimeMillis();
    }

    public Date c() {
        return Calendar.getInstance().getTime();
    }

    public Calendar d() {
        return Calendar.getInstance();
    }
}
