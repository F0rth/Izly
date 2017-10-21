package com.ad4screen.sdk.service.modules.inapp.a.a;

public enum f {
    UNKNOWN(0),
    SECOND(1000),
    MINUTE(60 * SECOND.a()),
    HOUR(60 * MINUTE.a()),
    DAY(24 * HOUR.a()),
    WEEK(7 * DAY.a()),
    MONTH(4 * WEEK.a());

    private long h;

    private f(long j) {
        this.h = j;
    }

    public final long a() {
        return this.h;
    }
}
