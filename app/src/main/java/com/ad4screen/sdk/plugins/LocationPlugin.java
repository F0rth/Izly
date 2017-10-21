package com.ad4screen.sdk.plugins;

import android.content.Context;
import android.location.Location;

import com.ad4screen.sdk.A4S.Callback;

public interface LocationPlugin extends BasePlugin {
    boolean connect(Context context, long j, long j2, Callback<Location> callback);

    void disconnect();
}
