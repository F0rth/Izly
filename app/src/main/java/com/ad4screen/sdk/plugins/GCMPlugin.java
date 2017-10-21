package com.ad4screen.sdk.plugins;

import android.content.Context;

public interface GCMPlugin extends PushPlugin {
    boolean unregister(Context context, String str);
}
