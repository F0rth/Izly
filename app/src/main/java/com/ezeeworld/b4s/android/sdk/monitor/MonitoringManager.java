package com.ezeeworld.b4s.android.sdk.monitor;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.BluetoothHelper;
import com.ezeeworld.b4s.android.sdk.playservices.GoogleApiAvailability;
import com.ezeeworld.b4s.android.sdk.push.PushApi;
import com.ezeeworld.b4s.android.sdk.server.TrackingUser.InstalledApplication;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class MonitoringManager extends IntentService {
    private static PendingIntent a;
    private static SortedMap<String, a> b;
    private static SortedMap<String, Boolean> c;

    static class a {
        public long a;
        public String b;
        public String c;
        public String d;

        private a() {
        }
    }

    public MonitoringManager() {
        super("B4S MonitoringManager");
    }

    private void a() {
        Entry entry;
        String str;
        Entry entry2 = null;
        for (Entry entry3 : b.entrySet()) {
            if (entry3.getValue() != null) {
                if (((a) entry3.getValue()).d == null || (entry2 != null && ((a) entry3.getValue()).a <= ((a) entry2.getValue()).a)) {
                    entry3 = entry2;
                }
                entry2 = entry3;
            } else {
                return;
            }
        }
        for (Entry entry32 : c.entrySet()) {
            if (entry32.getValue() != null) {
                if (((Boolean) entry32.getValue()).booleanValue()) {
                    str = (String) entry32.getKey();
                    break;
                }
            }
            B4SLog.d((Object) this, "SR No val");
            return;
        }
        str = null;
        if (str != null) {
            B4SLog.w((Object) this, "Legacy app " + str + " is still responsible for scanning");
            MonitoringStatus.update(MonitoringStatus.Waiting);
            a(false, false);
            return;
        }
        boolean z = entry2 == null || ((String) entry2.getKey()).equals(getPackageName());
        if (z) {
            B4SLog.d((Object) this, "We, " + getPackageName() + ", are responsible for scanning");
            startService(new Intent(this, ScanService.class));
        } else {
            B4SLog.d((Object) this, "App " + ((String) entry2.getKey()) + " is responsible for scanning");
            MonitoringStatus.update(MonitoringStatus.Waiting);
            if (ScanService.a) {
                B4SLog.d((Object) this, "  As we, " + getPackageName() + ", are no longer responsible, stop scanning");
            }
            stopService(new Intent(this, ScanService.class));
        }
        a(z, false);
    }

    private void a(boolean z, boolean z2) {
        if (B4SSettings.isInitialized()) {
            List list = null;
            if (z2) {
                InstalledApplication installedApplication = new InstalledApplication();
                installedApplication.packageName = getPackageName();
                installedApplication.sdkVersion = B4SSettings.getSdkVersionName();
                installedApplication.appId = B4SSettings.get().getAppId();
                installedApplication.appVersion = B4SSettings.get().getAppVersion();
                list = Collections.singletonList(installedApplication);
            } else if (z) {
                List arrayList = new ArrayList(c.size() + c.size());
                for (Entry entry : b.entrySet()) {
                    InstalledApplication installedApplication2 = new InstalledApplication();
                    installedApplication2.packageName = (String) entry.getKey();
                    installedApplication2.appId = ((a) entry.getValue()).d;
                    installedApplication2.appVersion = ((a) entry.getValue()).c;
                    installedApplication2.sdkVersion = ((a) entry.getValue()).b;
                    arrayList.add(installedApplication2);
                }
                for (Entry entry2 : c.entrySet()) {
                    InstalledApplication installedApplication3 = new InstalledApplication();
                    installedApplication3.packageName = (String) entry2.getKey();
                    arrayList.add(installedApplication3);
                }
                list = arrayList;
            }
            B4SSettings.get().setInstalledApplications(list);
        }
    }

    public static void ensureMonitoringService(Context context) {
        if (!B4SSettings.isInitialized()) {
            throw new RuntimeException("Please call through EZBeaconSettings.init() before using the SDK!");
        } else if (BluetoothHelper.isAvailable(context)) {
            MonitoringStatus.update(MonitoringStatus.Starting);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            if (a != null) {
                a.cancel();
            }
            a = PendingIntent.getService(context, 1337, new Intent(context, MonitoringManager.class).setAction("com.ezeeworld.b4s.android.sdk.monitor.B4S_ENSURE_SCANNING"), 268435456);
            alarmManager.setInexactRepeating(0, System.currentTimeMillis() + 900000, 900000, a);
            context.startService(new Intent(context, MonitoringManager.class).setAction("com.ezeeworld.b4s.android.sdk.monitor.B4S_ENSURE_SCANNING"));
        } else {
            B4SLog.c(MonitoringManager.class.getSimpleName(), "Cannot start because Bluetooth LE is not supported on this device.", MonitoringStatus.BluetoothIncompatible);
        }
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Intent intent2;
            Intent intent3;
            if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_ENSURE_SCANNING")) {
                if (B4SSettings.get() != null && GoogleApiAvailability.get().hasGcmServices()) {
                    PushApi.get().perhapsRegisterToken();
                }
                b = new TreeMap();
                c = new TreeMap();
                Intent intent4 = new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_QUERY_VERSION");
                intent2 = new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_QUERY_SCHEDULE");
                try {
                    List<ResolveInfo> queryIntentServices = getPackageManager().queryIntentServices(intent4, 0);
                    List<ResolveInfo> queryIntentServices2 = getPackageManager().queryIntentServices(intent2, 0);
                    if (queryIntentServices2.size() == 1) {
                        B4SLog.w((Object) this, "We, " + getPackageName() + ", are responsible for scanning (as we are the only B4S app)");
                        startService(new Intent(this, ScanService.class));
                        a(true, true);
                        return;
                    }
                    for (ResolveInfo resolveInfo : queryIntentServices) {
                        if (resolveInfo.serviceInfo.packageName.equals(getPackageName())) {
                            a aVar = new a();
                            aVar.a = B4SSettings.getSdkBuildTimestamp();
                            aVar.b = B4SSettings.getSdkVersionName();
                            if (B4SSettings.get() != null) {
                                aVar.c = B4SSettings.get().getAppVersion();
                                aVar.d = B4SSettings.get().getAppId();
                            }
                            b.put(resolveInfo.serviceInfo.packageName, aVar);
                        } else {
                            B4SLog.i((Object) this, "Ask " + resolveInfo.serviceInfo.packageName + " for its version");
                            b.put(resolveInfo.serviceInfo.packageName, null);
                            Intent intent5 = new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_QUERY_VERSION");
                            intent5.setComponent(new ComponentName(resolveInfo.serviceInfo.packageName, MonitoringManager.class.getName()));
                            intent5.putExtra("b4s_package_name", getPackageName());
                            try {
                                startService(intent5);
                            } catch (Exception e) {
                            }
                        }
                    }
                    for (ResolveInfo resolveInfo2 : queryIntentServices2) {
                        if (!(resolveInfo2.serviceInfo.packageName.equals(getPackageName()) || b.containsKey(resolveInfo2.serviceInfo.packageName))) {
                            B4SLog.i((Object) this, "Ask " + resolveInfo2.serviceInfo.packageName + " if it is scheduled");
                            c.put(resolveInfo2.serviceInfo.packageName, null);
                            intent3 = new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_QUERY_SCHEDULE");
                            intent3.setComponent(new ComponentName(resolveInfo2.serviceInfo.packageName, MonitoringManager.class.getName()));
                            intent3.putExtra("b4s_package_name", getPackageName());
                            try {
                                startService(intent3);
                            } catch (Exception e2) {
                            }
                        }
                    }
                } catch (Exception e3) {
                    B4SLog.w((Object) this, "We, " + getPackageName() + ", are responsible for scanning (as we cannot access to the full apps list)");
                    startService(new Intent(this, ScanService.class));
                    a(true, true);
                }
            } else if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_QUERY_VERSION")) {
                r1 = intent.getStringExtra("b4s_package_name");
                intent3 = new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_REPORT_VERSION");
                B4SLog.i((Object) this, "Version query from " + r1 + ": we are " + getPackageName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + (B4SSettings.get() == null ? "[NOT INITIALISED]" : B4SSettings.get().getAppVersion()) + " on SDK " + B4SSettings.getSdkVersionName() + " BuildTS=" + Long.valueOf(B4SSettings.getSdkBuildTimestamp()));
                intent3.setComponent(new ComponentName(r1, MonitoringManager.class.getName()));
                intent3.putExtra("b4s_package_name", getPackageName());
                intent3.putExtra("b4s_sdk_version_code", Long.valueOf(B4SSettings.getSdkBuildTimestamp()));
                intent3.putExtra("b4s_sdk_version_name", B4SSettings.getSdkVersionName());
                if (B4SSettings.get() != null) {
                    intent3.putExtra("b4s_app_version", B4SSettings.get().getAppVersion());
                    intent3.putExtra("b4s_app_id", B4SSettings.get().getAppId());
                }
                try {
                    startService(intent3);
                } catch (Exception e4) {
                }
            } else if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_QUERY_SCHEDULE")) {
                String stringExtra = intent.getStringExtra("b4s_package_name");
                intent2 = new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_SCHEDULE_RESULT");
                B4SLog.i((Object) this, "Legacy schedule query from " + stringExtra + ": tell yes to prevent it from scanning");
                intent2.setComponent(new ComponentName(stringExtra, MonitoringManager.class.getName()));
                intent2.putExtra("b4s_is_scheduled", true);
                intent2.putExtra("b4s_package_name", getPackageName());
                try {
                    startService(intent2);
                    startService(new Intent(this, MonitoringManager.class).setAction("com.ezeeworld.b4s.android.sdk.monitor.B4S_ENSURE_SCANNING"));
                } catch (Exception e5) {
                }
            } else if (intent.getAction() == null || !intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_REPORT_VERSION")) {
                if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_SCHEDULE_RESULT") && c != null) {
                    c.put(intent.getStringExtra("b4s_package_name"), Boolean.valueOf(intent.getBooleanExtra("b4s_is_scheduled", false)));
                    a();
                }
            } else if (b != null) {
                a aVar2 = new a();
                r1 = intent.getStringExtra("b4s_package_name");
                try {
                    aVar2.a = intent.getLongExtra("b4s_sdk_version_code", 0);
                } catch (Exception e6) {
                    aVar2.a = (long) intent.getIntExtra("b4s_sdk_version_code", 0);
                }
                aVar2.b = intent.getStringExtra("b4s_sdk_version_name");
                aVar2.c = intent.getStringExtra("b4s_app_version");
                aVar2.d = intent.getStringExtra("b4s_app_id");
                B4SLog.i((Object) this, "Version Report for:" + r1 + " AppId=" + aVar2.d + " SDKVer=" + aVar2.b + " TS=" + aVar2.a);
                b.put(r1, aVar2);
                a();
            }
        }
    }
}
