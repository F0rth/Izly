package com.ezeeworld.b4s.android.sdk;

import android.util.Log;

import com.ezeeworld.b4s.android.sdk.AsyncExecutor.RunnableEx;
import com.ezeeworld.b4s.android.sdk.monitor.MonitoringStatus;
import com.ezeeworld.b4s.android.sdk.server.AppInfo;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.RemoteLog;

import java.lang.Thread.UncaughtExceptionHandler;

public final class B4SLog {
    static Boolean a = Boolean.valueOf(false);
    static Boolean b = Boolean.valueOf(false);
    private static Integer c;
    private static Integer d;
    private static Boolean e = Boolean.valueOf(false);

    private static void a(final int i, final String str, final String str2) {
        if (c != null && B4SSettings.isInitialized() && e.booleanValue()) {
            AsyncExecutor.get().execute(new RunnableEx() {
                public final void run() throws Exception {
                    if (B4SLog.c != null && B4SLog.c.intValue() >= i && B4SLog.c()) {
                        try {
                            RemoteLog remoteLog = new RemoteLog();
                            remoteLog.priority = i;
                            remoteLog.methodName = str;
                            remoteLog.message = str2;
                            if (B4SSettings.isInitialized()) {
                                InteractionsApi.get().sendRemoteLog(remoteLog);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            });
        }
    }

    public static void c(Object obj, String str, MonitoringStatus monitoringStatus) {
        c(obj.getClass().getSimpleName(), str, monitoringStatus);
    }

    public static void c(String str, String str2, MonitoringStatus monitoringStatus) {
        Log.e("B4S", str + ": " + str2);
        a(6, str, str2);
        MonitoringStatus.update(monitoringStatus, str2);
    }

    private static boolean c() {
        switch (ConnectivityHelper.getCurrent(B4SSettings.get().getApplicationContext())) {
            case Cellular:
                if (d.intValue() == 0) {
                    return true;
                }
                if (d.intValue() == 2) {
                    return true;
                }
                break;
            case Wifi:
                if (d.intValue() == 0) {
                    return true;
                }
                if (d.intValue() == 1) {
                    return true;
                }
                break;
        }
        return false;
    }

    public static void cs(Object obj, String str, MonitoringStatus monitoringStatus) {
        c(obj.getClass().getSimpleName(), str, monitoringStatus);
    }

    public static void d(Object obj, String str) {
        d(obj.getClass().getSimpleName(), str);
    }

    public static void d(String str, String str2) {
        if (b.booleanValue()) {
            Log.d("B4S", str + ": " + str2);
        }
        a(3, str, str2);
    }

    public static void ds(Object obj, String str) {
        ds(obj.getClass().getSimpleName(), str);
    }

    public static void ds(String str, String str2) {
        if (a.booleanValue()) {
            Log.d("B4SSCN", str + ": " + str2);
        }
        a(3, str, str2);
    }

    public static void e(Object obj, String str) {
        e(obj.getClass().getSimpleName(), str);
    }

    public static void e(String str, String str2) {
        Log.e("B4S", str + ": " + str2);
        a(6, str, str2);
    }

    public static void es(Object obj, String str) {
        e(obj.getClass().getSimpleName(), str);
    }

    public static void i(Object obj, String str) {
        i(obj.getClass().getSimpleName(), str);
    }

    public static void i(String str, String str2) {
        if (b.booleanValue()) {
            Log.i("B4S", str + ": " + str2);
        }
        a(4, str, str2);
    }

    public static void installUncaughtExceptionHandler() {
        final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public final void uncaughtException(Thread thread, Throwable th) {
                int i = 0;
                if (th.getStackTrace() == null || th.getStackTrace().length <= 0 || !th.getStackTrace()[0].getClassName().startsWith("com.ezeeworld.b4s")) {
                    defaultUncaughtExceptionHandler.uncaughtException(thread, th);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(th);
                StackTraceElement[] stackTrace = th.getStackTrace();
                int length = stackTrace.length;
                while (i < length) {
                    Object obj = stackTrace[i];
                    stringBuilder.append("\n  ");
                    stringBuilder.append(obj);
                    i++;
                }
                B4SLog.e("UncaughtException", stringBuilder.toString());
                th.printStackTrace();
            }
        });
    }

    public static void is(Object obj, String str) {
        is(obj.getClass().getSimpleName(), str);
    }

    public static void is(String str, String str2) {
        if (a.booleanValue()) {
            Log.i("B4SSCN", str + ": " + str2);
        }
        a(4, str, str2);
    }

    public static void setRemotePriority(AppInfo appInfo) {
        e = Boolean.valueOf(appInfo.logging);
        if (appInfo.loggingLevel > 0) {
            if (appInfo.loggingLevel == 1) {
                c = Integer.valueOf(6);
            } else if (appInfo.loggingLevel == 2) {
                c = Integer.valueOf(5);
            } else if (appInfo.loggingLevel == 3) {
                c = Integer.valueOf(4);
            } else {
                c = Integer.valueOf(2);
            }
        }
        d = Integer.valueOf(appInfo.loggingMedium);
    }

    public static void v(Object obj, String str) {
        v(obj.getClass().getSimpleName(), str);
    }

    public static void v(String str, String str2) {
        if (b.booleanValue()) {
            Log.v("B4S", str + ": " + str2);
        }
        a(2, str, str2);
    }

    public static void vs(Object obj, String str) {
        vs(obj.getClass().getSimpleName(), str);
    }

    public static void vs(String str, String str2) {
        if (a.booleanValue()) {
            Log.v("B4SSCN", str + ": " + str2);
        }
        a(2, str, str2);
    }

    public static void w(Object obj, String str) {
        w(obj.getClass().getSimpleName(), str);
    }

    public static void w(String str, String str2) {
        if (b.booleanValue()) {
            Log.w("B4S", str + ": " + str2);
        }
        a(5, str, str2);
    }

    public static void ws(Object obj, String str) {
        ws(obj.getClass().getSimpleName(), str);
    }

    public static void ws(String str, String str2) {
        if (a.booleanValue()) {
            Log.w("B4SSCN", str + ": " + str2);
        }
        a(5, str, str2);
    }
}
