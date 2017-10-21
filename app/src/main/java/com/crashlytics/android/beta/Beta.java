package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import defpackage.js;
import defpackage.jy;
import defpackage.mo;
import java.util.HashMap;
import java.util.Map;

public class Beta extends jy<Boolean> implements kt {
    private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private static final String CRASHLYTICS_BUILD_PROPERTIES = "crashlytics-build.properties";
    static final String NO_DEVICE_TOKEN = "";
    public static final String TAG = "Beta";
    private final ke<String> deviceTokenCache = new ke();
    private final DeviceTokenLoader deviceTokenLoader = new DeviceTokenLoader();
    private UpdatesController updatesController;

    private String getBetaDeviceToken(Context context, String str) {
        Object obj = null;
        try {
            String str2 = (String) this.deviceTokenCache.a(context, this.deviceTokenLoader);
            if (!"".equals(str2)) {
                String str3 = str2;
            }
        } catch (Throwable e) {
            js.a().c(TAG, "Failed to load the Beta device token", e);
        }
        js.a().a(TAG, "Beta device token present: " + (!TextUtils.isEmpty(obj)));
        return obj;
    }

    private mu getBetaSettingsData() {
        ni a = a.a().a();
        return a != null ? a.f : null;
    }

    public static Beta getInstance() {
        return (Beta) js.a(Beta.class);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.crashlytics.android.beta.BuildProperties loadBuildProperties(android.content.Context r8) {
        /*
        r7 = this;
        r2 = 0;
        r0 = r8.getAssets();	 Catch:{ Exception -> 0x0061, all -> 0x00a8 }
        r1 = "crashlytics-build.properties";
        r1 = r0.open(r1);	 Catch:{ Exception -> 0x0061, all -> 0x00a8 }
        if (r1 == 0) goto L_0x00a3;
    L_0x000d:
        r2 = com.crashlytics.android.beta.BuildProperties.fromPropertiesStream(r1);	 Catch:{ Exception -> 0x0097, all -> 0x0082 }
        r0 = defpackage.js.a();	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r3 = "Beta";
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r4.<init>();	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r5 = r2.packageName;	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r5 = " build properties: ";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r5 = r2.versionName;	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r5 = " (";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r5 = r2.versionCode;	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r5 = ") - ";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r5 = r2.buildId;	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r4 = r4.toString();	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r0.a(r3, r4);	 Catch:{ Exception -> 0x009d, all -> 0x0082 }
        r0 = r2;
    L_0x004e:
        if (r1 == 0) goto L_0x0053;
    L_0x0050:
        r1.close();	 Catch:{ IOException -> 0x0054 }
    L_0x0053:
        return r0;
    L_0x0054:
        r1 = move-exception;
        r2 = defpackage.js.a();
        r3 = "Beta";
        r4 = "Error closing Beta build properties asset";
        r2.c(r3, r4, r1);
        goto L_0x0053;
    L_0x0061:
        r0 = move-exception;
        r1 = r0;
        r0 = r2;
    L_0x0064:
        r3 = defpackage.js.a();	 Catch:{ all -> 0x00a5 }
        r4 = "Beta";
        r5 = "Error reading Beta build properties";
        r3.c(r4, r5, r1);	 Catch:{ all -> 0x00a5 }
        if (r2 == 0) goto L_0x0053;
    L_0x0071:
        r2.close();	 Catch:{ IOException -> 0x0075 }
        goto L_0x0053;
    L_0x0075:
        r1 = move-exception;
        r2 = defpackage.js.a();
        r3 = "Beta";
        r4 = "Error closing Beta build properties asset";
        r2.c(r3, r4, r1);
        goto L_0x0053;
    L_0x0082:
        r0 = move-exception;
    L_0x0083:
        r2 = r1;
    L_0x0084:
        if (r2 == 0) goto L_0x0089;
    L_0x0086:
        r2.close();	 Catch:{ IOException -> 0x008a }
    L_0x0089:
        throw r0;
    L_0x008a:
        r1 = move-exception;
        r2 = defpackage.js.a();
        r3 = "Beta";
        r4 = "Error closing Beta build properties asset";
        r2.c(r3, r4, r1);
        goto L_0x0089;
    L_0x0097:
        r0 = move-exception;
        r6 = r0;
        r0 = r2;
        r2 = r1;
        r1 = r6;
        goto L_0x0064;
    L_0x009d:
        r0 = move-exception;
        r6 = r0;
        r0 = r2;
        r2 = r1;
        r1 = r6;
        goto L_0x0064;
    L_0x00a3:
        r0 = r2;
        goto L_0x004e;
    L_0x00a5:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0083;
    L_0x00a8:
        r0 = move-exception;
        goto L_0x0084;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.beta.Beta.loadBuildProperties(android.content.Context):com.crashlytics.android.beta.BuildProperties");
    }

    boolean canCheckForUpdates(mu muVar, BuildProperties buildProperties) {
        return (muVar == null || TextUtils.isEmpty(muVar.a) || buildProperties == null) ? false : true;
    }

    @TargetApi(14)
    UpdatesController createUpdatesController(int i, Application application) {
        return i >= 14 ? new ActivityLifecycleCheckForUpdatesController(getFabric().d, getFabric().c) : new ImmediateCheckForUpdatesController();
    }

    protected Boolean doInBackground() {
        js.a().a(TAG, "Beta kit initializing...");
        Context context = getContext();
        kw idManager = getIdManager();
        if (TextUtils.isEmpty(getBetaDeviceToken(context, idManager.d()))) {
            js.a().a(TAG, "A Beta device token was not found for this app");
            return Boolean.valueOf(false);
        }
        js.a().a(TAG, "Beta device token is present, checking for app updates.");
        mu betaSettingsData = getBetaSettingsData();
        BuildProperties loadBuildProperties = loadBuildProperties(context);
        if (canCheckForUpdates(betaSettingsData, loadBuildProperties)) {
            this.updatesController.initialize(context, this, idManager, betaSettingsData, loadBuildProperties, new mo(this), new la(), new me(js.a()));
        }
        return Boolean.valueOf(true);
    }

    public Map<a, String> getDeviceIdentifiers() {
        CharSequence betaDeviceToken = getBetaDeviceToken(getContext(), getIdManager().d());
        Map<a, String> hashMap = new HashMap();
        if (!TextUtils.isEmpty(betaDeviceToken)) {
            hashMap.put(a.c, betaDeviceToken);
        }
        return hashMap;
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:beta";
    }

    String getOverridenSpiEndpoint() {
        return kp.c(getContext(), CRASHLYTICS_API_ENDPOINT);
    }

    public String getVersion() {
        return "1.2.6.18";
    }

    @TargetApi(14)
    protected boolean onPreExecute() {
        this.updatesController = createUpdatesController(VERSION.SDK_INT, (Application) getContext().getApplicationContext());
        return true;
    }
}
