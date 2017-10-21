package com.ad4screen.sdk.external.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.external.shortcutbadger.a;
import com.ad4screen.sdk.external.shortcutbadger.b;

import java.util.ArrayList;
import java.util.List;

@API
public class DefaultBadger implements a {
    public void executeBadge(Context context, ComponentName componentName, int i) throws b {
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", i);
        intent.putExtra("badge_count_package_name", componentName.getPackageName());
        intent.putExtra("badge_count_class_name", componentName.getClassName());
        context.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return new ArrayList(0);
    }
}
