package com.ad4screen.sdk;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.widget.FrameLayout.LayoutParams;

import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.plugins.model.Beacon;

import java.util.ArrayList;
import java.util.List;

@API
public abstract class A4S {
    private static boolean a = false;
    private static Context b;
    private static Runnable c = new Runnable() {
        public final void run() {
            A4S.f = A4S.b(A4S.b);
        }
    };
    private static Handler d = new Handler(Looper.getMainLooper());
    private static final Object e = new Object();
    private static A4S f;

    @API
    public interface Callback<T> {
        void onError(int i, String str);

        void onResult(T t);
    }

    @API
    public interface MessageCallback {
        void onError(int i, String str);

        void onResult(Message message, int i);
    }

    A4S() {
    }

    private static A4S b(Context context) {
        if (i.a(context, "com.ad4screen.idsprovider", A4SService.class) != null) {
            b a = b.a(context, true);
            if (a.l() == null) {
                a = false;
                Log.setEnabled(true);
                Log.error("A4S|PartnerID needs to be properly setup in your AndroidManifest.xml file or by your provider");
                Log.error("A4S|Accengage SDK will retry to start in 10 sec");
                if (d != null) {
                    d.postDelayed(c, 10000);
                }
                return new b();
            } else if (a.m() == null) {
                a = false;
                Log.setEnabled(true);
                Log.error("A4S|PrivateKey needs to be properly setup in your AndroidManifest.xml file or by your provider");
                Log.error("A4S|Accengage SDK will retry to start in 10 sec");
                if (d != null) {
                    d.postDelayed(c, 10000);
                }
                return new b();
            } else {
                c = null;
                d = null;
            }
        }
        a = true;
        if (isTrackingEnabled(context)) {
            try {
                if (VERSION.class.getDeclaredField("SDK_INT").getInt(null) >= 4) {
                    return new a(context, true);
                }
            } catch (Throwable e) {
                Log.error("A4S|Could not check android Build.VERSION.SDK_INT field, cannot start A4SSDK", e);
            } catch (Throwable e2) {
                Log.error("A4S|Could not check android Build.VERSION.SDK_INT field, cannot start A4SSDK", e2);
            } catch (Throwable e22) {
                Log.error("A4S|Could not check android Build.VERSION.SDK_INT field, cannot start A4SSDK", e22);
            }
        }
        a aVar = new a(context, false);
        return new b();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void b(android.content.Context r4, boolean r5, boolean r6) {
        /*
        r1 = e;
        monitor-enter(r1);
        r0 = "com.ad4screen.sdk.A4S";
        r2 = 0;
        r0 = r4.getSharedPreferences(r0, r2);	 Catch:{ all -> 0x0037 }
        r2 = "com.ad4screen.sdk.A4S.doNotTrack";
        r3 = 0;
        r2 = r0.getBoolean(r2, r3);	 Catch:{ all -> 0x0037 }
        if (r2 != r5) goto L_0x0015;
    L_0x0013:
        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
    L_0x0014:
        return;
    L_0x0015:
        r0 = r0.edit();	 Catch:{ all -> 0x0037 }
        r2 = "com.ad4screen.sdk.A4S.doNotTrack";
        r0.putBoolean(r2, r5);	 Catch:{ all -> 0x0037 }
        r0.commit();	 Catch:{ all -> 0x0037 }
        r0 = f;	 Catch:{ all -> 0x0037 }
        if (r0 == 0) goto L_0x0035;
    L_0x0025:
        r0 = f;	 Catch:{ all -> 0x0037 }
        r0.a(r4, r5, r6);	 Catch:{ all -> 0x0037 }
        r0 = b;	 Catch:{ all -> 0x0037 }
        r0 = b(r0);	 Catch:{ all -> 0x0037 }
        f = r0;	 Catch:{ all -> 0x0037 }
        r0.a(r4, r5, r6);	 Catch:{ all -> 0x0037 }
    L_0x0035:
        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
        goto L_0x0014;
    L_0x0037:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ad4screen.sdk.A4S.b(android.content.Context, boolean, boolean):void");
    }

    public static void disableTracking(Context context, boolean z) {
        b(context, true, z);
    }

    public static void enableTracking(Context context) {
        b(context, false, false);
    }

    public static A4S get(Context context) {
        A4S a4s;
        synchronized (A4S.class) {
            try {
                b = context;
                if (f == null || !a) {
                    if (f != null) {
                        d.removeCallbacks(c);
                    }
                    f = b(context);
                }
                a4s = f;
            } catch (Throwable th) {
                Class cls = A4S.class;
            }
        }
        return a4s;
    }

    @Deprecated
    public static boolean isDoNotTrackEnabled(Context context) {
        return !isTrackingEnabled(context);
    }

    public static boolean isInA4SProcess(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4);
            if (packageInfo.services != null) {
                List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
                if (runningAppProcesses == null) {
                    return false;
                }
                ServiceInfo[] serviceInfoArr = packageInfo.services;
                int length = serviceInfoArr.length;
                int i = 0;
                while (i < length) {
                    ServiceInfo serviceInfo = serviceInfoArr[i];
                    if (!"com.ad4screen.sdk.A4SService".equals(serviceInfo.name)) {
                        i++;
                    } else if (serviceInfo.processName == null || serviceInfo.processName.equals(packageInfo.applicationInfo.processName)) {
                        return false;
                    } else {
                        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                            if (runningAppProcessInfo.pid == Process.myPid()) {
                                return runningAppProcessInfo.processName.equals(serviceInfo.processName);
                            }
                        }
                        return false;
                    }
                }
            }
        } catch (Throwable e) {
            Log.error("A4S|Error while checking if current process is dedicated to A4SSDK", e);
        }
        return false;
    }

    public static boolean isTrackingEnabled(Context context) {
        boolean z = false;
        synchronized (e) {
            if (!context.getSharedPreferences("com.ad4screen.sdk.A4S", 0).getBoolean("com.ad4screen.sdk.A4S.doNotTrack", false)) {
                z = true;
            }
        }
        return z;
    }

    @Deprecated
    public static void setDoNotTrackEnabled(Context context, boolean z) {
        b(context, z, false);
    }

    protected abstract void a();

    protected abstract void a(int i, Callback<Inbox> callback, boolean z);

    protected abstract void a(Context context, boolean z, boolean z2);

    protected abstract void a(String str);

    protected abstract void a(String str, Callback<Message> callback);

    protected abstract void a(String str, String str2, String[] strArr);

    protected abstract void b();

    protected abstract void b(String str);

    protected abstract void c();

    protected abstract void c(String str);

    protected abstract void d(String str);

    public abstract void displayInApp(String str);

    public abstract void getA4SId(Callback<String> callback);

    public abstract String getAndroidId();

    @API
    protected abstract ArrayList<Beacon> getBeacons();

    public abstract void getIDFV(Callback<String> callback);

    public abstract void getInbox(Callback<Inbox> callback);

    public abstract void getListOfSubscriptions(Callback<List<StaticList>> callback);

    @API
    public abstract void getPushToken(Callback<String> callback);

    public abstract void getSubscriptionStatusForLists(List<StaticList> list, Callback<List<StaticList>> callback);

    @API
    protected abstract void handleGeofencingMessage(Bundle bundle);

    @API
    protected abstract void handlePushMessage(Bundle bundle);

    @Deprecated
    public abstract void isGCMEnabled(Callback<Boolean> callback);

    public abstract void isInAppDisplayLocked(Callback<Boolean> callback);

    public abstract void isPushEnabled(Callback<Boolean> callback);

    public abstract boolean isPushNotificationLocked();

    public abstract void isRestrictedConnection(Callback<Boolean> callback);

    public abstract void putState(String str, String str2);

    @API
    public abstract void refreshPushToken();

    public abstract void resetOverlayPosition();

    @Deprecated
    public abstract void sendGCMToken(String str);

    public abstract void sendPushToken(String str);

    @Deprecated
    public abstract void setGCMEnabled(boolean z);

    public abstract void setInAppClickedCallback(Callback<InApp> callback, int... iArr);

    public abstract void setInAppClosedCallback(Callback<InApp> callback, int... iArr);

    public abstract void setInAppDisplayLocked(boolean z);

    public abstract void setInAppDisplayedCallback(Callback<InApp> callback, int... iArr);

    public abstract void setInAppReadyCallback(boolean z, Callback<InApp> callback, int... iArr);

    public abstract void setIntent(Intent intent);

    public abstract void setOverlayPosition(LayoutParams layoutParams);

    public abstract void setPushEnabled(boolean z);

    public abstract void setPushNotificationLocked(boolean z);

    public abstract void setRestrictedConnection(boolean z);

    public abstract void setView(String str);

    public abstract void startActivity(Activity activity);

    public abstract void stopActivity(Activity activity);

    public abstract void subscribeToLists(List<StaticList> list);

    public abstract void subscribeToLists(StaticList... staticListArr);

    public abstract void trackAddToCart(Cart cart);

    public abstract void trackEvent(long j, String str, String... strArr);

    public abstract void trackLead(Lead lead);

    public abstract void trackPurchase(Purchase purchase);

    @API
    protected abstract void triggerBeacons(Bundle bundle);

    public abstract void unsubscribeFromLists(List<StaticList> list);

    public abstract void unsubscribeFromLists(StaticList... staticListArr);

    public abstract void updateDeviceInfo(Bundle bundle);

    public abstract void updateGeolocation(Location location);

    public abstract void updateMessages(Inbox inbox);

    @API
    protected abstract void updatePushRegistration(Bundle bundle);
}
