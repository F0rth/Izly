package com.ad4screen.sdk.plugins;

import android.content.Context;

public interface ADMPlugin extends PushPlugin {
    boolean isSupported(Context context);

    boolean unregister(Context context);
}
