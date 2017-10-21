package com.ad4screen.sdk.common.b;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Binder;
import android.webkit.WebView;

import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.i;

@TargetApi(19)
public final class e {
    public static int a(Context context, String str) {
        int identifier;
        if (str != null) {
            identifier = context.getResources().getIdentifier(str, "drawable", context.getPackageName());
            return identifier > 0 ? identifier : -1;
        } else {
            String a = i.a(context, "com.ad4screen.notifications.icon", A4SService.class);
            if (a == null) {
                return -1;
            }
            identifier = context.getResources().getIdentifier(a, "drawable", context.getPackageName());
            return identifier > 0 ? identifier : -1;
        }
    }

    public static void a() {
        WebView.setWebContentsDebuggingEnabled(true);
    }

    public static void a(AlarmManager alarmManager, int i, long j, PendingIntent pendingIntent) {
        alarmManager.setExact(i, j, pendingIntent);
    }

    public static boolean a(Context context) {
        try {
            Object systemService = context.getSystemService("appops");
            return ((Integer) systemService.getClass().getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(systemService, new Object[]{Integer.valueOf(11), Integer.valueOf(Binder.getCallingUid()), context.getPackageName()})).intValue() != 0;
        } catch (Throwable e) {
            Log.internal("Compatibility|isNotificationDisplayDisabled InvocationTargetException: ", e);
            return false;
        } catch (Throwable e2) {
            Log.internal("Compatibility|isNotificationDisplayDisabled NoSuchMethodException: ", e2);
            return false;
        } catch (Throwable e22) {
            Log.internal("Compatibility|isNotificationDisplayDisabled IllegalAccessException: ", e22);
            return false;
        }
    }
}
