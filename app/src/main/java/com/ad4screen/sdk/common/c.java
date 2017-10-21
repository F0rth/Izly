package com.ad4screen.sdk.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;

import com.ad4screen.sdk.Log;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.List;

public final class c {
    public static Bundle a(Intent intent) {
        return intent.getBundleExtra("al_applink_data");
    }

    public static String a(Context context) {
        Throwable e;
        String string;
        try {
            PackageManager packageManager = context.getPackageManager();
            Uri parse = packageManager.resolveContentProvider("com.facebook.katana.provider.AttributionIdProvider", 0) != null ? Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider") : packageManager.resolveContentProvider("com.facebook.wakizashi.provider.AttributionIdProvider", 0) != null ? Uri.parse("content://com.facebook.wakizashi.provider.AttributionIdProvider") : null;
            if (parse == null) {
                return null;
            }
            Cursor query = context.getContentResolver().query(parse, new String[]{"aid"}, null, null, null);
            if (query == null || !query.moveToFirst()) {
                return null;
            }
            string = query.getString(query.getColumnIndex("aid"));
            try {
                query.close();
                return string;
            } catch (Exception e2) {
                e = e2;
                Log.internal("FacebookUtil|Failed to retrieve attribution id with exception", e);
                return string;
            }
        } catch (Exception e3) {
            e = e3;
            string = null;
            Log.internal("FacebookUtil|Failed to retrieve attribution id with exception", e);
            return string;
        }
    }

    public static String a(Context context, String str) {
        String string = Secure.getString(context.getContentResolver(), "android_id");
        return string == null ? null : a(string + str);
    }

    private static String a(String str) {
        return a("SHA-1", str);
    }

    private static String a(String str, String str2) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(str2.getBytes());
            byte[] digest = instance.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                stringBuilder.append(Integer.toHexString((b >> 4) & 15));
                stringBuilder.append(Integer.toHexString((b >> 0) & 15));
            }
            return stringBuilder.toString();
        } catch (Throwable e) {
            Log.internal("FacebookUtil|hashWithAlgorithm failed", e);
            return null;
        }
    }

    public static void a(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("FacebookSource", 0);
        ComponentName callingActivity = activity.getCallingActivity();
        if (callingActivity != null) {
            String packageName = callingActivity.getPackageName();
            if (packageName.equals(activity.getPackageName())) {
                a(sharedPreferences);
                return;
            }
            sharedPreferences.edit().putString("source", packageName).commit();
        }
        Intent intent = activity.getIntent();
        boolean booleanExtra = intent.getBooleanExtra("_accfbSourceApplicationHasBeenSet", false);
        if (intent == null || booleanExtra) {
            a(sharedPreferences);
            return;
        }
        Bundle a = a(intent);
        if (a == null) {
            a(sharedPreferences);
            return;
        }
        sharedPreferences.edit().putBoolean("openedByAppLink", true).commit();
        a = a.getBundle("referer_app_link");
        if (a == null) {
            sharedPreferences.edit().remove("source").commit();
            return;
        }
        sharedPreferences.edit().putString("source", a.getString("package")).commit();
        intent.putExtra("_accfbSourceApplicationHasBeenSet", true);
    }

    private static void a(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().remove("openedByAppLink").remove("source").commit();
    }

    public static String b(Context context) {
        try {
            Class cls = Class.forName("com.facebook.Session");
            Object invoke = cls.getMethod("getActiveSession", new Class[0]).invoke(cls, new Object[0]);
            Method method = cls.getMethod("getApplicationId", new Class[0]);
            if (invoke != null) {
                return (String) method.invoke(invoke, new Object[0]);
            }
        } catch (Throwable e) {
            Log.internal("FacebookUtil|Impossible to get FacebookAppId", e);
        }
        return null;
    }

    public static String c(Context context) {
        Throwable th;
        String str = null;
        try {
            Class cls = Class.forName("com.facebook.Session");
            Object invoke = cls.getMethod("getActiveSession", new Class[0]).invoke(cls, new Object[0]);
            String str2 = invoke != null ? (String) cls.getMethod("getAccessToken", new Class[0]).invoke(invoke, new Object[0]) : null;
            if (str2 == null) {
                return str2;
            }
            try {
                return str2.length() < 2 ? null : str2;
            } catch (Throwable e) {
                Throwable th2 = e;
                str = str2;
                th = th2;
                Log.internal("FacebookUtil|Impossible to get FacebookToken", th);
                return str;
            }
        } catch (Exception e2) {
            th = e2;
            Log.internal("FacebookUtil|Impossible to get FacebookToken", th);
            return str;
        }
    }

    public static String[] d(Context context) {
        try {
            Class cls = Class.forName("com.facebook.Session");
            Object invoke = cls.getMethod("getActiveSession", new Class[0]).invoke(cls, new Object[0]);
            Method method = cls.getMethod("getPermissions", new Class[0]);
            if (invoke != null) {
                List list = (List) method.invoke(invoke, new Object[0]);
                return (String[]) list.toArray(new String[list.size()]);
            }
        } catch (Throwable e) {
            Log.internal("FacebookUtil|Impossible to get FacebookPermissions", e);
        }
        return null;
    }

    public static String e(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FacebookSource", 0);
        String str = "Unclassified";
        if (sharedPreferences.getBoolean("openedByAppLink", false)) {
            str = "Applink";
        }
        return sharedPreferences.getString("source", null) != null ? str + "(" + sharedPreferences.getString("source", "") + ")" : str;
    }
}
