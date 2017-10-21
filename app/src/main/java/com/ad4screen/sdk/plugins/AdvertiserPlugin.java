package com.ad4screen.sdk.plugins;

import android.content.Context;

public interface AdvertiserPlugin extends BasePlugin {
    String getId(Context context);

    boolean isLimitAdTrackingEnabled(Context context);
}
