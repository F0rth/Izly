package com.ezeeworld.b4s.android.sdk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;

import com.ezeeworld.b4s.android.sdk.ActivityTracker.ApplicationToForeground;
import com.ezeeworld.b4s.android.sdk.playservices.GoogleApiAvailability;

public class B4SAlertBehaviours {
    private final SharedPreferences a;
    private int b;
    private boolean c;
    private int d;
    private int e;
    private int f;
    private boolean g;
    private int h;
    private int i;
    private int j;
    private boolean k;
    private int l;
    private int m;
    private int n;

    static class a {
        static final B4SAlertBehaviours a = new B4SAlertBehaviours();
    }

    private B4SAlertBehaviours() {
        this.c = false;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = false;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.a = B4SSettings.get().getPreferences();
        this.b = this.a.getInt("b4s_behaviours_launches", 0);
        EventBus.get().register(this);
    }

    private void a() {
        this.b++;
        this.a.edit().putInt("b4s_behaviours_launches", this.b).apply();
    }

    private void a(String str) {
        this.a.edit().putInt("b4s_behaviours_shown_" + str, this.a.getInt("b4s_behaviours_shown_" + str, 0) + 1).apply();
    }

    private boolean a(Activity activity) {
        if (VERSION.SDK_INT < 23 || activity.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
            return false;
        }
        activity.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 7331);
        return true;
    }

    private boolean a(String str, int i, int i2, int i3) {
        return (i3 == 0 || i3 > this.a.getInt("b4s_behaviours_shown_" + str, 0)) ? (i == 0 || i < this.b) ? (i2 == 0 && this.b == 1) || (i2 > 0 && ((this.b - 1) - i) % i2 == 0) : false : false;
    }

    private boolean b(final Activity activity) {
        if (!this.k || !a("playservices", this.l, this.m, this.n) || GoogleApiAvailability.get().hasPlayServices()) {
            return false;
        }
        activity.runOnUiThread(new Runnable(this) {
            final /* synthetic */ B4SAlertBehaviours b;

            public void run() {
                GoogleApiAvailability.get().getErrorDialog(activity, 7333).show();
            }
        });
        a("playservices");
        return true;
    }

    private boolean c(Activity activity) {
        if (!this.c || !a("bluetooth", this.d, this.e, this.f) || B4SSettings.get().shouldEnforceBluetooth() || BluetoothHelper.isEnabled()) {
            return false;
        }
        activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 7334);
        a("bluetooth");
        return true;
    }

    private boolean d(Activity activity) {
        if (!this.g || !a("geolocation", this.h, this.i, this.j) || !GoogleApiAvailability.get().hasPlayServices()) {
            return false;
        }
        boolean showLocationServicesSettings = GoogleApiAvailability.get().showLocationServicesSettings(activity, 7332);
        if (!showLocationServicesSettings) {
            return showLocationServicesSettings;
        }
        a("geolocation");
        return showLocationServicesSettings;
    }

    public static B4SAlertBehaviours get() {
        return a.a;
    }

    public void onEventBackgroundThread(ApplicationToForeground applicationToForeground) {
        a();
        if (!a(applicationToForeground.getCauseActivity()) && !b(applicationToForeground.getCauseActivity()) && !c(applicationToForeground.getCauseActivity())) {
            d(applicationToForeground.getCauseActivity());
        }
    }

    public void warnForBluetooth(boolean z, int i, int i2, int i3) {
        this.c = z;
        this.d = i;
        this.e = i2;
        this.f = i3;
        if (B4SSettings.get().shouldEnforceBluetooth() && z) {
            B4SLog.w((Object) this, "Requested to show alerts when Bluetooth is disabled, but it is set to be forcefully enabled by the SDK using B4SSettings.setShouldEnforceBluetooth(true).");
        }
    }

    public void warnForGeolocation(boolean z, int i, int i2, int i3) {
        this.g = z;
        this.h = i;
        this.i = i2;
        this.j = i3;
    }

    public void warnForPlayServices(boolean z, int i, int i2, int i3) {
        this.k = z;
        this.l = i;
        this.m = i2;
        this.n = i3;
    }
}
