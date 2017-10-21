package com.ad4screen.sdk.external.shortcutbadger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.external.shortcutbadger.impl.AdwHomeBadger;
import com.ad4screen.sdk.external.shortcutbadger.impl.ApexHomeBadger;
import com.ad4screen.sdk.external.shortcutbadger.impl.AsusHomeLauncher;
import com.ad4screen.sdk.external.shortcutbadger.impl.DefaultBadger;
import com.ad4screen.sdk.external.shortcutbadger.impl.NewHtcHomeBadger;
import com.ad4screen.sdk.external.shortcutbadger.impl.NovaHomeBadger;
import com.ad4screen.sdk.external.shortcutbadger.impl.SolidHomeBadger;
import com.ad4screen.sdk.external.shortcutbadger.impl.SonyHomeBadger;
import com.ad4screen.sdk.external.shortcutbadger.impl.XiaomiHomeBadger;

import java.util.LinkedList;
import java.util.List;

@API
public final class ShortcutBadger {
    private static final String a = (ShortcutBadger.class.getSimpleName() + "|");
    private static final List<Class<? extends a>> b;
    private static a c;
    private static ComponentName d;

    static {
        List linkedList = new LinkedList();
        b = linkedList;
        linkedList.add(AdwHomeBadger.class);
        b.add(ApexHomeBadger.class);
        b.add(NewHtcHomeBadger.class);
        b.add(NovaHomeBadger.class);
        b.add(SolidHomeBadger.class);
        b.add(SonyHomeBadger.class);
        b.add(XiaomiHomeBadger.class);
        b.add(AsusHomeLauncher.class);
    }

    private ShortcutBadger() {
    }

    private static void a(Context context) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntentForPackage == null) {
            Log.warn(a + "Impossible to find badger");
            return;
        }
        d = launchIntentForPackage.getComponent();
        Log.debug(a + "Finding badger");
        try {
            launchIntentForPackage = new Intent("android.intent.action.MAIN");
            launchIntentForPackage.addCategory("android.intent.category.HOME");
            String str = context.getPackageManager().resolveActivity(launchIntentForPackage, 65536).activityInfo.packageName;
            for (Class newInstance : b) {
                a aVar = (a) newInstance.newInstance();
                if (aVar.getSupportLaunchers().contains(str)) {
                    c = aVar;
                    break;
                }
            }
            if (c == null && Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                c = new XiaomiHomeBadger();
                return;
            }
        } catch (Throwable e) {
            Log.error(a + e.getMessage(), e);
        }
        if (c == null) {
            c = new DefaultBadger();
        }
        Log.debug(a + "Current badger:" + c.getClass().getCanonicalName());
    }

    public static boolean applyCount(Context context, int i) {
        try {
            applyCountOrThrow(context, i);
            return true;
        } catch (b e) {
            Log.error(a + "Unable to execute badge:" + e.getMessage());
            return false;
        }
    }

    public static void applyCountOrThrow(Context context, int i) throws b {
        if (c == null) {
            a(context);
        }
        try {
            c.executeBadge(context, d, i);
        } catch (Throwable th) {
            b bVar = new b("Unable to execute badge:" + th.getMessage());
        }
    }

    public static boolean removeCount(Context context) {
        return applyCount(context, 0);
    }

    public static void removeCountOrThrow(Context context) throws b {
        applyCountOrThrow(context, 0);
    }
}
