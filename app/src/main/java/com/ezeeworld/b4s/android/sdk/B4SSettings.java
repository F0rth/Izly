package com.ezeeworld.b4s.android.sdk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.ezeeworld.b4s.android.sdk.ActivityTracker.ApplicationToForeground;
import com.ezeeworld.b4s.android.sdk.AsyncExecutor.RunnableEx;
import com.ezeeworld.b4s.android.sdk.B4SUserProperty.Gender;
import com.ezeeworld.b4s.android.sdk.monitor.MonitoringStatus;
import com.ezeeworld.b4s.android.sdk.playservices.GoogleApi;
import com.ezeeworld.b4s.android.sdk.playservices.location.LocationServices;
import com.ezeeworld.b4s.android.sdk.server.Api2;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.TrackingUser;
import com.ezeeworld.b4s.android.sdk.server.TrackingUser.InstalledApplication;

import de.greenrobot.event.util.ThrowableFailureEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class B4SSettings {
    private static B4SSettings a;
    private final Context b;
    private final SharedPreferences c;
    public String currentCountry = "";
    public String currentTimezone = "";
    private final String d;
    private final String e;
    private final String f;
    private final int g;
    private boolean h = true;
    private boolean i = false;
    private boolean j = true;
    private boolean k = false;
    private boolean l = false;
    private boolean m = false;
    private int n = -5592406;
    private boolean o = true;
    private boolean p = false;
    private int q = -1;
    private String r = null;
    private Integer s = null;
    private boolean t = true;
    private boolean u = false;
    private List<InstalledApplication> v;

    private B4SSettings(Context context, String str) {
        PackageInfo packageInfo;
        this.b = context.getApplicationContext();
        String format = new SimpleDateFormat("Z").format(Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault()).getTime());
        try {
            this.currentTimezone = "GMT" + format.substring(0, 1) + Integer.parseInt(format.substring(1, 3));
        } catch (Exception e) {
            e.printStackTrace();
            this.currentTimezone = "GMT+00";
        }
        System.out.println("B4S currentTimezone=" + this.currentTimezone);
        this.currentCountry = context.getResources().getConfiguration().locale.getCountry();
        System.out.println("B4S currentCountry=" + this.currentCountry);
        this.c = PreferenceManager.getDefaultSharedPreferences(context);
        this.d = str;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e2) {
            packageInfo = null;
        }
        if (packageInfo != null) {
            this.f = packageInfo.packageName;
            this.e = packageInfo.versionName;
            this.g = packageInfo.versionCode;
            return;
        }
        this.f = context.getPackageName();
        this.e = null;
        this.g = 0;
    }

    private void a() {
        if (this.v != null) {
            AsyncExecutor.get().execute(new RunnableEx(this) {
                final /* synthetic */ B4SSettings a;

                {
                    this.a = r1;
                }

                public void run() throws Exception {
                    TrackingUser trackingUser = new TrackingUser();
                    Location lastLocation = LocationServices.get().getLastLocation();
                    if (lastLocation != null) {
                        trackingUser.latitude = lastLocation.getLatitude();
                        trackingUser.longitude = lastLocation.getLongitude();
                    }
                    trackingUser.installedApplications = this.a.v;
                    InteractionsApi.get().trackDevice(trackingUser);
                    B4SUserProperty.get().uploadIfNeeded(this.a.b);
                }
            });
        }
    }

    public static B4SSettings get() {
        if (a == null || a.b == null || a.getAppId() == null) {
            B4SLog.c(B4SSettings.class.getSimpleName(), "B4S not yet initialized; call through B4SSettings.init() before using the SDK!", MonitoringStatus.NotYetInitialized);
        }
        return a;
    }

    public static long getSdkBuildTimestamp() {
        return BuildConfig.BUILD_TIMESTAMP;
    }

    public static int getSdkVersionCode() {
        return 242;
    }

    public static String getSdkVersionFullName() {
        return "2.0.13-242-master";
    }

    public static String getSdkVersionName() {
        return "2.0.13";
    }

    public static B4SSettings init(Application application, String str) {
        Log.i("B4S", "Started BeaconForStoreSDK\n\tVersion: 2.0.13\n\tBuild number: " + Integer.toString(242) + "\n\tGit branch: master" + "\n\tAppID: " + str);
        if (TextUtils.isEmpty(str) || str.equals("MY-APP-ID")) {
            B4SLog.e(B4SSettings.class.getSimpleName(), "Application ID can't be empty; use the Ezeeworld-assigned application-specific B4S app ID.");
        }
        if (!(a == null || a.getAppId() == null || a.getAppId().equals(str))) {
            InteractionsApi.get().clearCache();
        }
        B4SSettings b4SSettings = new B4SSettings(application.getApplicationContext(), str);
        a = b4SSettings;
        b4SSettings.setShouldLogScanning(a.shouldLogScanning());
        a.setShouldLogMatching(a.shouldLogMatching());
        B4SLog.installUncaughtExceptionHandler();
        ActivityTracker.a(application);
        EventBus.get().register(a);
        GoogleApi.init(application);
        B4SAlertBehaviours.get();
        return a;
    }

    public static boolean isInitialized() {
        return (a == null || a.getAppId() == null) ? false : true;
    }

    public boolean areInteractionsEnabled() {
        return this.j;
    }

    public void disableLocationTrackingLocally() {
        this.h = false;
    }

    public void disableNotificationSound() {
        this.o = false;
    }

    public void enableLocationTrackingLocally() {
        this.h = true;
    }

    public void enableNotificationSound() {
        this.o = true;
    }

    public int getAppCode() {
        return this.g;
    }

    public String getAppId() {
        return this.d;
    }

    public String getAppPackage() {
        return this.f;
    }

    public String getAppVersion() {
        return this.e;
    }

    public Context getApplicationContext() {
        return this.b;
    }

    public Integer getCustomNotificationIcon() {
        return this.s;
    }

    public String getCustomerAddress() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_ADDRESS);
    }

    public int getCustomerAge() {
        Integer num = (Integer) B4SUserProperty.get().get(B4SUserProperty.USER_AGE);
        return num == null ? 0 : num.intValue();
    }

    public String getCustomerCity() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_CITY);
    }

    public String getCustomerClientRef() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_CUSTOMER_REF);
    }

    public String getCustomerCountry() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_COUNTRY);
    }

    public String getCustomerEmail() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_EMAIL);
    }

    public String getCustomerFirstName() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_FIRST_NAME);
    }

    public int getCustomerGender() {
        Gender gender = (Gender) B4SUserProperty.get().get(B4SUserProperty.USER_GENDER);
        return gender == null ? Gender.Undefined.ordinal() : gender.ordinal();
    }

    public String getCustomerLastName() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_LAST_NAME);
    }

    public String getCustomerPhone() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_PHONE);
    }

    public String getCustomerUserId() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_ID);
    }

    public String getCustomerZipcode() {
        return (String) B4SUserProperty.get().get(B4SUserProperty.USER_ZIPCODE);
    }

    public int getNotificationBackgroundColor() {
        return this.n;
    }

    public SharedPreferences getPreferences() {
        return this.c;
    }

    public String getPushMessagingSenderId() {
        return this.r;
    }

    public boolean locationTrackingEnabled() {
        return this.h;
    }

    public boolean notificationSoundStatus() {
        return this.o;
    }

    public void onEventBackgroundThread(ApplicationToForeground applicationToForeground) {
        a();
    }

    public void onEventBackgroundThread(ThrowableFailureEvent throwableFailureEvent) {
        B4SLog.e("UncaughtException", throwableFailureEvent.getThrowable().toString());
    }

    public void setAreInteractionsEnabled(boolean z) {
        this.j = z;
    }

    public void setCustomNotificationIcon(int i) {
        this.s = Integer.valueOf(i);
    }

    public void setInstalledApplications(List<InstalledApplication> list) {
        if (this.v == null || !this.v.equals(list)) {
            this.v = list;
            a();
        }
    }

    public void setNotificationBackgroundColor(int i) {
        this.n = i;
    }

    public void setPushMessagingSenderId(String str) {
        this.r = str;
    }

    public void setShouldDisableBluetoothScanning(boolean z) {
        this.p = z;
    }

    public void setShouldEnforceBluetooth(boolean z) {
        this.i = z;
    }

    public void setShouldLogApi(boolean z) {
        Api2.setLogApi(z);
        this.m = z;
    }

    public void setShouldLogMatching(boolean z) {
        B4SLog.b = Boolean.valueOf(z);
        this.l = z;
    }

    public void setShouldLogScanning(boolean z) {
        B4SLog.a = Boolean.valueOf(z);
        this.k = z;
    }

    public void setShouldRingOnNotification(boolean z) {
        this.u = z;
    }

    public void setShouldVibrateOnNotification(boolean z) {
        this.t = z;
    }

    public boolean shouldDisableBluetoothScanning() {
        return this.p;
    }

    public boolean shouldEnforceBluetooth() {
        return this.i;
    }

    public boolean shouldLogApi() {
        return this.m;
    }

    public boolean shouldLogMatching() {
        if (B4SLog.b == null) {
            B4SLog.b = Boolean.valueOf(this.l);
        }
        return B4SLog.b.booleanValue();
    }

    public boolean shouldLogScanning() {
        if (B4SLog.a == null) {
            B4SLog.a = Boolean.valueOf(this.k);
        }
        return B4SLog.a.booleanValue();
    }

    public boolean shouldRingOnNotification() {
        return this.u;
    }

    public boolean shouldVibrateOnNotification() {
        return this.t;
    }

    @Deprecated
    public void storeCustomerFields(String str, String str2, int i, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i2) {
        B4SUserProperty b4SUserProperty = B4SUserProperty.get();
        b4SUserProperty.store(B4SUserProperty.USER_CUSTOMER_REF, str);
        b4SUserProperty.store(B4SUserProperty.USER_ID, str2);
        b4SUserProperty.store(B4SUserProperty.USER_GENDER, Gender.values()[i]);
        b4SUserProperty.store(B4SUserProperty.USER_LAST_NAME, str3);
        b4SUserProperty.store(B4SUserProperty.USER_FIRST_NAME, str4);
        b4SUserProperty.store(B4SUserProperty.USER_EMAIL, str5);
        b4SUserProperty.store(B4SUserProperty.USER_PHONE, str6);
        b4SUserProperty.store(B4SUserProperty.USER_ADDRESS, str7);
        b4SUserProperty.store(B4SUserProperty.USER_ZIPCODE, str10);
        b4SUserProperty.store(B4SUserProperty.USER_CITY, str8);
        b4SUserProperty.store(B4SUserProperty.USER_COUNTRY, str9);
        b4SUserProperty.store(B4SUserProperty.USER_AGE, Integer.valueOf(i2));
    }
}
