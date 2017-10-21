package com.ad4screen.sdk.common.b;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Binder;
import android.webkit.WebView;

import com.ad4screen.sdk.Log;

@TargetApi(18)
public final class d {
    @SuppressLint({"SetJavaScriptEnabled"})
    public static void a(WebView webView, boolean z) {
        webView.getSettings().setJavaScriptEnabled(z);
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
