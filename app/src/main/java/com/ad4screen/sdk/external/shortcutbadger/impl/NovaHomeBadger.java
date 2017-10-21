package com.ad4screen.sdk.external.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.external.shortcutbadger.a;
import com.ad4screen.sdk.external.shortcutbadger.b;

import java.util.Arrays;
import java.util.List;

@API
public class NovaHomeBadger implements a {
    public void executeBadge(Context context, ComponentName componentName, int i) throws b {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tag", componentName.getPackageName() + "/" + componentName.getClassName());
            contentValues.put(NewHtcHomeBadger.COUNT, Integer.valueOf(i));
            context.getContentResolver().insert(Uri.parse("content://com.teslacoilsw.notifier/unread_count"), contentValues);
        } catch (IllegalArgumentException e) {
        } catch (Exception e2) {
            throw new b(e2.getMessage());
        }
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.teslacoilsw.launcher"});
    }
}
