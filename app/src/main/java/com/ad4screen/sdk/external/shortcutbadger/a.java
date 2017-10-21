package com.ad4screen.sdk.external.shortcutbadger;

import android.content.ComponentName;
import android.content.Context;

import java.util.List;

public interface a {
    void executeBadge(Context context, ComponentName componentName, int i) throws b;

    List<String> getSupportLaunchers();
}
