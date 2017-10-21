package com.ezeeworld.b4s.android.sdk.monitor;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ezeeworld.b4s.android.sdk.AsyncExecutor;
import com.ezeeworld.b4s.android.sdk.AsyncExecutor.RunnableEx;
import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.BluetoothHelper;
import com.ezeeworld.b4s.android.sdk.ConnectivityHelper;
import com.ezeeworld.b4s.android.sdk.ConnectivityHelper.ConnectivityType;
import com.ezeeworld.b4s.android.sdk.ibeacon.Updater;
import com.ezeeworld.b4s.android.sdk.monitor.SessionManager.B4SSpeedIndices;
import com.ezeeworld.b4s.android.sdk.playservices.GoogleApiAvailability;
import com.ezeeworld.b4s.android.sdk.playservices.location.GeofencingEvent;
import com.ezeeworld.b4s.android.sdk.playservices.location.LocationResult;
import com.ezeeworld.b4s.android.sdk.playservices.location.LocationServices;
import com.ezeeworld.b4s.android.sdk.server.AppInfo;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.ManualTag;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InteractionService extends Service {
    private static final long[] a = new long[]{30000, 60000, 120000, 300000};
    private SessionManager b;
    private a c;
    private PendingIntent d;
    private PendingIntent e;
    private long f;
    private String g;
    private String h;
    private long i;
    private ScheduledExecutorService j = Executors.newScheduledThreadPool(1);

    class a {
        AppInfo a = InteractionsApi.get().getAppInfo(false);
        String b = "UNDEF";
        final /* synthetic */ InteractionService c;
        private boolean d = true;
        private int e = -1;
        private int f = 150000;

        public a(final InteractionService interactionService) {
            this.c = interactionService;
            B4SLog.i((Object) this, "[Thread-" + Thread.currentThread().getId() + "] B4S SessionsUpdateThread scheduler");
            interactionService.j.scheduleAtFixedRate(new Runnable(this) {
                final /* synthetic */ a b;

                public void run() {
                    if (this.b.a == null) {
                        this.b.a = InteractionsApi.get().getAppInfo(false);
                    } else {
                        if (!this.b.c.b.c().booleanValue()) {
                            this.b.c.b.a(this.b.a);
                        }
                        B4SLog.d((Object) this, "[InteractionService] SessionsUpdateThread");
                        this.b.c.b.a();
                        this.b.c.b.b();
                        long currentTimeMillis = System.currentTimeMillis() - this.b.c.f;
                        if (!this.b.c.b() || currentTimeMillis < ((long) this.b.f)) {
                            B4SLog.d((Object) this, "[" + this.b.b + "] = deltaTime:" + currentTimeMillis + " permissions=" + this.b.c.b());
                        } else {
                            this.b.c.f = System.currentTimeMillis();
                            int intExtra = B4SSettings.get().getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("level", -1);
                            ConnectivityType current = ConnectivityHelper.getCurrent(B4SSettings.get().getApplicationContext());
                            if (!(current == ConnectivityType.Wifi || current == ConnectivityType.Cellular)) {
                                B4SLog.d((Object) this, "[InteractionService] connection down");
                            }
                            Object obj = (this.b.c.b == null || !this.b.c.b.b(this.b.a)) ? null : 1;
                            Object obj2 = (this.b.c.b == null || !this.b.c.b.f()) ? null : 1;
                            boolean z = false;
                            if (obj2 != null) {
                                z = this.b.c.a().booleanValue();
                                if (this.b.e == -1) {
                                    this.b.c.i = System.currentTimeMillis() - 3600000;
                                    z = true;
                                    this.b.b = "STATIC";
                                    this.b.f = 150000;
                                }
                            }
                            boolean booleanValue = this.b.c.b.e().booleanValue();
                            B4SSpeedIndices d = this.b.c.b.d();
                            int ordinal = d.ordinal();
                            int i = booleanValue ? 10000 : 0;
                            int i2 = obj != null ? 1000 : 0;
                            int i3 = ((obj2 != null ? 10 : 0) + ((z ? 100 : 0) + (i2 + (i + (ordinal * 100000))))) + 0;
                            B4SLog.d((Object) this, "[Thread-" + Thread.currentThread().getId() + "] previousLocalisationRequestStatus=" + this.b.e + " vs " + i3 + " current trackingMode=" + this.b.b + " GSD=" + booleanValue + " WSD=" + z + " bLvl=" + intExtra + " threads=" + Thread.activeCount() + " contextEvaluationTimeout was:" + this.b.f);
                            if (i3 != this.b.e) {
                                B4SLog.d((Object) this, "[" + this.b.b + "] status change detected");
                                Intent intent = new Intent(this.b.c, InteractionService.class);
                                int i4;
                                if (obj2 == null) {
                                    this.b.f = 150000;
                                    if (booleanValue) {
                                        this.b.b = "NW_STATIC";
                                        if (this.b.c.d != null) {
                                            LocationServices.get().removeGeofences(this.b.c.d);
                                            LocationServices.get().removeLocationUpdates(this.b.c.d);
                                            this.b.c.d = null;
                                        }
                                        if (this.b.c.e == null) {
                                            this.b.c.e = PendingIntent.getService(this.b.c, 1339, intent, 0);
                                        } else {
                                            LocationServices.get().removeGeofences(this.b.c.e);
                                            LocationServices.get().removeLocationUpdates(this.b.c.e);
                                        }
                                        this.b.c.b.a(this.b.a);
                                        LocationServices.get().requestNetworkLocationUpdates(300000, this.b.c.e);
                                        B4SLog.d((Object) this, "[" + this.b.b + "] localisationRequest [W] 5 minutes");
                                    } else {
                                        this.b.b = "NW_MOVE";
                                        if (B4SSettings.get().locationTrackingEnabled()) {
                                            if (this.b.c.e != null) {
                                                LocationServices.get().removeGeofences(this.b.c.e);
                                                LocationServices.get().removeLocationUpdates(this.b.c.e);
                                                this.b.c.e = null;
                                            }
                                            if (this.b.c.d == null) {
                                                this.b.c.d = PendingIntent.getService(this.b.c, 1338, intent, 0);
                                            } else {
                                                LocationServices.get().removeGeofences(this.b.c.d);
                                                LocationServices.get().removeLocationUpdates(this.b.c.d);
                                            }
                                        } else {
                                            if (this.b.c.d != null) {
                                                LocationServices.get().removeGeofences(this.b.c.d);
                                                LocationServices.get().removeLocationUpdates(this.b.c.d);
                                                this.b.c.d = null;
                                            }
                                            if (this.b.c.e == null) {
                                                this.b.c.e = PendingIntent.getService(this.b.c, 1339, intent, 0);
                                            } else {
                                                LocationServices.get().removeGeofences(this.b.c.e);
                                                LocationServices.get().removeLocationUpdates(this.b.c.e);
                                            }
                                        }
                                        this.b.c.b.a(this.b.a);
                                        if (obj != null) {
                                            switch (B4SSpeedIndices.values()[d.ordinal()]) {
                                                case B4SSpeedIndices_UNKNOWN:
                                                    i4 = 60;
                                                    break;
                                                case B4SSpeedIndices_STATIC:
                                                    i4 = 180;
                                                    break;
                                                case B4SSpeedIndices_WALK:
                                                    i4 = 30;
                                                    break;
                                                case B4SSpeedIndices_CITY_DRIVE:
                                                    i4 = 17;
                                                    break;
                                                case B4SSpeedIndices_HIGHWAY_DRIVE:
                                                    i4 = 180;
                                                    break;
                                            }
                                        }
                                        i4 = 300;
                                        LocationServices.get().requestGpsLocationUpdates((long) (i4 * 1000), this.b.c.d);
                                        B4SLog.d((Object) this, "[" + this.b.b + "] localisationRequest [G] var seconds");
                                    }
                                } else {
                                    i4 = this.b.c.b.g();
                                    if (this.b.b == "STATIC" && i4 > 1 && z) {
                                        B4SLog.d((Object) this, "Still STATIC");
                                        this.b.f = 150000;
                                    } else {
                                        B4SLog.d((Object) this, "Location WFactor=" + i4);
                                        if (i4 > 1 && z) {
                                            this.b.b = "STATIC";
                                            if (this.b.c.d != null) {
                                                LocationServices.get().removeGeofences(this.b.c.d);
                                                LocationServices.get().removeLocationUpdates(this.b.c.d);
                                                this.b.c.d = null;
                                            }
                                            if (this.b.c.e == null) {
                                                this.b.c.e = PendingIntent.getService(this.b.c, 1339, intent, 0);
                                            } else {
                                                LocationServices.get().removeGeofences(this.b.c.e);
                                                LocationServices.get().removeLocationUpdates(this.b.c.e);
                                            }
                                            LocationServices.get().requestNetworkLocationUpdates(3600000, this.b.c.e);
                                            B4SLog.d((Object) this, "[" + this.b.b + "] localisationRequest [N] 60 minutes");
                                            this.b.f = 150000;
                                        } else if (i4 <= 1 || z) {
                                            if (B4SSettings.get().locationTrackingEnabled()) {
                                                this.b.b = "ROAD-s";
                                                if (this.b.c.e != null) {
                                                    LocationServices.get().removeGeofences(this.b.c.e);
                                                    LocationServices.get().removeLocationUpdates(this.b.c.e);
                                                    this.b.c.e = null;
                                                }
                                                if (this.b.c.d == null) {
                                                    this.b.c.d = PendingIntent.getService(this.b.c, 1338, intent, 0);
                                                } else {
                                                    LocationServices.get().removeGeofences(this.b.c.d);
                                                    LocationServices.get().removeLocationUpdates(this.b.c.d);
                                                }
                                            } else {
                                                this.b.b = "ROAD-c";
                                                if (this.b.c.d != null) {
                                                    LocationServices.get().removeGeofences(this.b.c.d);
                                                    LocationServices.get().removeLocationUpdates(this.b.c.d);
                                                    this.b.c.d = null;
                                                }
                                                if (this.b.c.e == null) {
                                                    this.b.c.e = PendingIntent.getService(this.b.c, 1339, intent, 0);
                                                } else {
                                                    LocationServices.get().removeGeofences(this.b.c.e);
                                                    LocationServices.get().removeLocationUpdates(this.b.c.e);
                                                }
                                            }
                                            this.b.c.b.a(this.b.a);
                                            i4 = 60;
                                            switch (B4SSpeedIndices.values()[d.ordinal()]) {
                                                case B4SSpeedIndices_UNKNOWN:
                                                    i4 = 60;
                                                    break;
                                                case B4SSpeedIndices_STATIC:
                                                    i4 = 180;
                                                    break;
                                                case B4SSpeedIndices_WALK:
                                                    i4 = 30;
                                                    break;
                                                case B4SSpeedIndices_CITY_DRIVE:
                                                    i4 = 17;
                                                    break;
                                                case B4SSpeedIndices_HIGHWAY_DRIVE:
                                                    i4 = 180;
                                                    break;
                                            }
                                            LocationServices.get().requestGpsLocationUpdates((long) (i4 * 1000), this.b.c.d);
                                            B4SLog.d((Object) this, "[" + this.b.b + "] localisationRequest [G] " + i4 + " minutes");
                                            this.b.f = 150000;
                                        } else {
                                            if (B4SSettings.get().locationTrackingEnabled()) {
                                                this.b.b = "CITY-s";
                                                if (this.b.c.e != null) {
                                                    LocationServices.get().removeGeofences(this.b.c.e);
                                                    LocationServices.get().removeLocationUpdates(this.b.c.e);
                                                    this.b.c.e = null;
                                                }
                                                if (this.b.c.d == null) {
                                                    this.b.c.d = PendingIntent.getService(this.b.c, 1338, intent, 0);
                                                } else {
                                                    LocationServices.get().removeGeofences(this.b.c.d);
                                                    LocationServices.get().removeLocationUpdates(this.b.c.d);
                                                }
                                            } else {
                                                this.b.b = "CITY-c";
                                                if (this.b.c.d != null) {
                                                    LocationServices.get().removeGeofences(this.b.c.d);
                                                    LocationServices.get().removeLocationUpdates(this.b.c.d);
                                                    this.b.c.d = null;
                                                }
                                                if (this.b.c.e == null) {
                                                    this.b.c.e = PendingIntent.getService(this.b.c, 1339, intent, 0);
                                                } else {
                                                    LocationServices.get().removeGeofences(this.b.c.e);
                                                    LocationServices.get().removeLocationUpdates(this.b.c.e);
                                                }
                                            }
                                            this.b.c.b.a(this.b.a);
                                            i4 = 60;
                                            switch (B4SSpeedIndices.values()[d.ordinal()]) {
                                                case B4SSpeedIndices_UNKNOWN:
                                                    i4 = 60;
                                                    break;
                                                case B4SSpeedIndices_STATIC:
                                                    i4 = 180;
                                                    break;
                                                case B4SSpeedIndices_WALK:
                                                    i4 = 30;
                                                    break;
                                                case B4SSpeedIndices_CITY_DRIVE:
                                                    i4 = 17;
                                                    break;
                                                case B4SSpeedIndices_HIGHWAY_DRIVE:
                                                    i4 = 180;
                                                    break;
                                            }
                                            LocationServices.get().requestGpsLocationUpdates((long) (i4 * 1000), this.b.c.d);
                                            B4SLog.d((Object) this, "[" + this.b.b + "] localisationRequest [G] " + i4 + " seconds");
                                            this.b.f = 150000;
                                        }
                                    }
                                }
                                this.b.e = i3;
                            } else {
                                B4SLog.d((Object) this, "[" + this.b.b + "] status did not change.");
                            }
                            B4SLog.d((Object) this, "[" + this.b.b + "] + deltaTime=" + currentTimeMillis);
                        }
                    }
                    B4SLog.v((Object) this, "[Thread-" + Thread.currentThread().getId() + "] loop needsUpdating=" + this.b.d);
                }
            }, 10, 92, TimeUnit.SECONDS);
        }

        void a() {
            B4SLog.e((Object) this, "[Thread-" + Thread.currentThread().getId() + "] cancel request");
            this.d = false;
            this.c.j.shutdown();
        }
    }

    private Boolean a() {
        List<ScanResult> h = this.b.h();
        if (h == null) {
            B4SLog.w((Object) this, "No WIDs");
            return Boolean.valueOf(false);
        }
        B4SLog.w((Object) this, h.size() + "x WIDs");
        ScanResult scanResult;
        if (this.g == null) {
            if (h.size() > 0) {
                scanResult = (ScanResult) h.get(0);
                B4SLog.w((Object) this, "Set WID (" + scanResult.BSSID + "/" + scanResult.level + ")");
                this.g = scanResult.BSSID;
                if (h.size() > 1) {
                    scanResult = (ScanResult) h.get(1);
                    this.h = scanResult.BSSID;
                    B4SLog.w((Object) this, "Set Alt WID (" + scanResult.BSSID + "/" + scanResult.level + ")");
                } else {
                    this.h = "";
                }
                this.i = System.currentTimeMillis();
            }
            return Boolean.valueOf(false);
        }
        for (ScanResult scanResult2 : h) {
            if (!scanResult2.BSSID.equalsIgnoreCase(this.g)) {
                if (scanResult2.BSSID.equalsIgnoreCase(this.h)) {
                }
            }
            this.g = scanResult2.BSSID;
            long currentTimeMillis = (System.currentTimeMillis() - this.i) / 1000;
            B4SLog.w((Object) this, "Got WID (" + scanResult2.BSSID + "/" + scanResult2.level + ") initialy seen:" + currentTimeMillis + "s ago");
            return currentTimeMillis > 240 ? Boolean.valueOf(true) : Boolean.valueOf(false);
        }
        if (h.size() > 0) {
            scanResult2 = (ScanResult) h.get(0);
            B4SLog.w((Object) this, "Updt WID (" + scanResult2.BSSID + "/" + scanResult2.level + ")");
            this.g = scanResult2.BSSID;
            if (h.size() > 1) {
                scanResult2 = (ScanResult) h.get(1);
                this.h = scanResult2.BSSID;
                B4SLog.w((Object) this, "Updt Alt WID (" + scanResult2.BSSID + "/" + scanResult2.level + ")");
            } else {
                this.h = "";
            }
            this.i = System.currentTimeMillis();
        }
        return Boolean.valueOf(false);
    }

    @TargetApi(23)
    private boolean b() {
        return VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    public static void fillSessionForTag(ManualTag manualTag) {
        if (B4SSettings.isInitialized()) {
            SessionManager.a(B4SSettings.get().getApplicationContext()).a(manualTag);
        }
    }

    public static void resetSessions() {
        if (B4SSettings.isInitialized()) {
            AsyncExecutor.get().execute(new RunnableEx() {
                public final void run() throws Exception {
                    SessionManager.a(B4SSettings.get().getApplicationContext()).i();
                }
            });
        }
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        if (BluetoothHelper.isAvailable(this)) {
            B4SLog.i((Object) this, "[Thread-" + Thread.currentThread().getId() + "] B4S InteractionService created");
            if (B4SSettings.isInitialized() && B4SSettings.get() != null) {
                if (!B4SSettings.get().areInteractionsEnabled()) {
                    B4SLog.c((Object) this, "[Thread-" + Thread.currentThread().getId() + "] No matching of interactions, as this is explicitly disabled by the application developer", MonitoringStatus.InteractionsDisabled);
                }
                if (!GoogleApiAvailability.get().hasPlayServices()) {
                    B4SLog.w(MonitoringManager.class.getSimpleName(), "[Thread-" + Thread.currentThread().getId() + "] Google Play Services are not installed on this device; continue but we can not use location services!");
                    MonitoringStatus.update(MonitoringStatus.NoPlayServicesInstalled);
                    return;
                }
                return;
            }
            return;
        }
        B4SLog.c((Object) this, "[Thread-" + Thread.currentThread().getId() + "] No matching of interactions, as Bluetooth LE is not supported on this device", MonitoringStatus.BluetoothIncompatible);
    }

    public void onDestroy() {
        B4SLog.i((Object) this, "[Thread-" + Thread.currentThread().getId() + "] B4S SessionManager onDestroy");
        if (this.c != null) {
            this.c.a();
            this.c = null;
        }
        if (b()) {
            if (this.e != null) {
                LocationServices.get().removeLocationUpdates(this.e);
            }
            if (this.d != null) {
                LocationServices.get().removeLocationUpdates(this.d);
            }
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (!BluetoothHelper.isAvailable(this)) {
            B4SLog.i((Object) this, "[Thread-" + Thread.currentThread().getId() + "] B4S InteractionService onStartCommand BLEHelper not available");
            return 2;
        } else if (intent == null || !B4SSettings.isInitialized() || !B4SSettings.get().areInteractionsEnabled()) {
            return 1;
        } else {
            if (this.b == null) {
                AsyncExecutor.get().execute(new RunnableEx(this) {
                    final /* synthetic */ InteractionService a;

                    {
                        this.a = r1;
                    }

                    public void run() throws Exception {
                        AppInfo appInfo = InteractionsApi.get().getAppInfo(false);
                        if (appInfo == null || appInfo.sdkEnabled) {
                            synchronized (appInfo) {
                                if (this.a.b == null) {
                                    B4SLog.i((Object) this, "[Thread-" + Thread.currentThread().getId() + "] B4S set SessionManager");
                                    this.a.b = SessionManager.a(this.a);
                                    this.a.c = new a(this.a);
                                }
                            }
                            return;
                        }
                        B4SLog.i((Object) this, "[Thread-" + Thread.currentThread().getId() + "] B4S can't init SessionManager, appInfo not available");
                    }
                });
                return 1;
            } else if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_PUSH_TRACKING_QUEUE")) {
                AsyncExecutor.get().execute(new RunnableEx(this) {
                    final /* synthetic */ InteractionService a;

                    {
                        this.a = r1;
                    }

                    public void run() throws Exception {
                        BufferedTracking.get().pushOrSchedule();
                    }
                });
                return 1;
            } else if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_PUSH_SESSION_QUEUE")) {
                AsyncExecutor.get().execute(new RunnableEx(this) {
                    final /* synthetic */ InteractionService a;

                    {
                        this.a = r1;
                    }

                    public void run() throws Exception {
                        BufferedSessionRegistration.get().pushOrSchedule();
                    }
                });
                return 1;
            } else if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_PUSH_LOCATION_QUEUE")) {
                AsyncExecutor.get().execute(new RunnableEx(this) {
                    final /* synthetic */ InteractionService a;

                    {
                        this.a = r1;
                    }

                    public void run() throws Exception {
                        B4SLog.w((Object) this, "Location tracking invoked from InteractionService");
                        BufferedLocationTracking.get().pushOrSchedule(InteractionsApi.get().getAppInfo(false));
                    }
                });
                return 1;
            } else if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_OBSERVATIONS")) {
                r1 = intent.getParcelableArrayListExtra("b4s_observations");
                if (r1 == null) {
                    return 1;
                }
                AsyncExecutor.get().execute(new RunnableEx(this) {
                    final /* synthetic */ InteractionService b;

                    public void run() throws Exception {
                        AppInfo appInfo = InteractionsApi.get().getAppInfo(false);
                        if (appInfo == null || appInfo.sdkEnabled) {
                            this.b.b.a(new ArrayList(r1), appInfo);
                            BufferedTracking.get().addObservations(r1);
                        }
                    }
                });
                return 1;
            } else if (B4SSettings.get().shouldDisableBluetoothScanning() || intent.getAction() == null || !intent.getAction().equals("com.ezeeworld.b4s.android.sdk.monitor.B4S_BEACONS")) {
                final LocationResult extractResult = LocationResult.extractResult(intent);
                if (extractResult.getLastLocation() != null) {
                    AsyncExecutor.get().execute(new RunnableEx(this) {
                        final /* synthetic */ InteractionService b;

                        public void run() throws Exception {
                            if (this.b.b != null) {
                                this.b.b.a(extractResult.getLastLocation());
                            }
                        }
                    });
                    return 1;
                }
                final GeofencingEvent fromIntent = GeofencingEvent.fromIntent(intent);
                if (fromIntent.hasError()) {
                    B4SLog.w((Object) this, "Geofencing error received: " + fromIntent.getErrorCode());
                    return 1;
                } else if (fromIntent.getTriggeringGeofences() == null || fromIntent.getTriggeringGeofences().isEmpty()) {
                    return 1;
                } else {
                    AsyncExecutor.get().execute(new RunnableEx(this) {
                        final /* synthetic */ InteractionService b;

                        public void run() throws Exception {
                            if (this.b.b != null) {
                                this.b.b.a(fromIntent);
                            }
                        }
                    });
                    return 1;
                }
            } else {
                r1 = intent.getParcelableArrayListExtra("b4s_beacons");
                if (r1 == null) {
                    return 1;
                }
                AsyncExecutor.get().execute(new RunnableEx(this) {
                    final /* synthetic */ InteractionService b;

                    public void run() throws Exception {
                        Updater.get().addObservations(r1);
                    }
                });
                return 1;
            }
        }
    }
}
