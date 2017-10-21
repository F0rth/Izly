package com.ezeeworld.b4s.android.sdk;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public final class Device {
    static String a() {
        return "Android";
    }

    @TargetApi(19)
    public static boolean areNotificationEnabled(Context context) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String packageName = context.getApplicationContext().getPackageName();
        int i = applicationInfo.uid;
        try {
            return ((Integer) Class.forName(AppOpsManager.class.getName()).getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(((Integer) Class.forName(AppOpsManager.class.getName()).getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), packageName})).intValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static String b() {
        return VERSION.RELEASE;
    }

    static String c() {
        return ((TelephonyManager) B4SSettings.get().getApplicationContext().getSystemService("phone")).getNetworkOperatorName();
    }

    static int d() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        if (VERSION.SDK_INT >= 18) {
            return (int) (statFs.getTotalBytes() / 1048576);
        }
        return (int) ((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1048576);
    }

    static int e() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        if (VERSION.SDK_INT >= 18) {
            return (int) (statFs.getAvailableBytes() / 1048576);
        }
        return (int) ((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1048576);
    }

    @TargetApi(9)
    public static String getDeviceId() {
        String string = Secure.getString(B4SSettings.get().getApplicationContext().getContentResolver(), "android_id");
        return (VERSION.SDK_INT < 9 || Build.SERIAL.equals(EnvironmentCompat.MEDIA_UNKNOWN)) ? string : string + Build.SERIAL;
    }

    public static String getDeviceModel() {
        return (Build.MODEL.startsWith(Build.MANUFACTURER) ? Build.MODEL : Build.MANUFACTURER + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Build.MODEL).replaceAll("[^\\x00-\\x7F]", "");
    }

    public static String getDeviceName() {
        return BluetoothHelper.getDefaultAdapter() == null ? getDeviceModel() : BluetoothHelper.getDefaultAdapter().getName();
    }
}
