package com.ad4screen.sdk.plugins;

import android.content.Context;

public interface PushPlugin extends BasePlugin {
    String register(Context context, String str);
}
