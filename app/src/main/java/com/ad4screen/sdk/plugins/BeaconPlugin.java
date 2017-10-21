package com.ad4screen.sdk.plugins;

import android.content.Context;

public interface BeaconPlugin extends BasePlugin {
    boolean isBeaconServiceDeclared(Context context);
}
