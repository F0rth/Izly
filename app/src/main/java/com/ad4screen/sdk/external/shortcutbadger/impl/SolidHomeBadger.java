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
public class SolidHomeBadger implements a {
    public void executeBadge(Context context, ComponentName componentName, int i) throws b {
        Intent intent = new Intent("com.majeur.launcher.intent.action.UPDATE_BADGE");
        intent.putExtra("com.majeur.launcher.intent.extra.BADGE_PACKAGE", componentName.getPackageName());
        intent.putExtra("com.majeur.launcher.intent.extra.BADGE_COUNT", i);
        intent.putExtra("com.majeur.launcher.intent.extra.BADGE_CLASS", componentName.getClassName());
        context.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.majeur.launcher"});
    }
}
