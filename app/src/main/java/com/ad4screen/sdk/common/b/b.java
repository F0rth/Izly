package com.ad4screen.sdk.common.b;

import android.annotation.TargetApi;
import android.net.TrafficStats;

@TargetApi(14)
public final class b {
    public static void a() {
        TrafficStats.clearThreadStatsTag();
    }

    public static void a(int i) {
        TrafficStats.setThreadStatsTag(i);
    }
}
