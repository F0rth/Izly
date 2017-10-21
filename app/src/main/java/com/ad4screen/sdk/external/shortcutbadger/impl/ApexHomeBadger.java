package com.ad4screen.sdk.external.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.external.shortcutbadger.a;
import com.ad4screen.sdk.external.shortcutbadger.b;

import java.util.Arrays;
import java.util.List;

@API
public class ApexHomeBadger implements a {
    public void executeBadge(Context context, ComponentName componentName, int i) throws b {
        Intent intent = new Intent("com.anddoes.launcher.COUNTER_CHANGED");
        intent.putExtra("package", componentName.getPackageName());
        intent.putExtra(NewHtcHomeBadger.COUNT, i);
        intent.putExtra("class", componentName.getClassName());
        context.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.anddoes.launcher"});
    }
}
