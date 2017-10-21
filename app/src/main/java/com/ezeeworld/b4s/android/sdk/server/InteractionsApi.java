package com.ezeeworld.b4s.android.sdk.server;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Build.VERSION;

import com.ezeeworld.b4s.android.sdk.ActivityTracker.ApplicationToForeground;
import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.BluetoothHelper;
import com.ezeeworld.b4s.android.sdk.Device;
import com.ezeeworld.b4s.android.sdk.EventBus;
import com.ezeeworld.b4s.android.sdk.monitor.InteractionService;
import com.ezeeworld.b4s.android.sdk.playservices.AdvertisingServices;
import com.ezeeworld.b4s.android.sdk.playservices.GoogleApiAvailability;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class InteractionsApi {
    public static final String B4SCAMPAIGN_TYPE_GLOBAL = "global";
    public static final String B4SCAMPAIGN_TYPE_PROXIMITY = "proximity";
    public static final String B4SCAMPAIGN_TYPE_ZONE = "zone";
    public static final int B4SCONFIGUPDT_MOVE = 1;
    public static final int B4SCONFIGUPDT_REGIONIN = 4;
    public static final int B4SCONFIGUPDT_REGIONOUT = 5;
    public static final String B4SCONNECTION_CELLULAR = "C";
    public static final String B4SCONNECTION_NOCONNECTION = "N";
    public static final String B4SCONNECTION_WIFI = "W";
    public static final int B4SDISTMODEL_GEO_INCOMING = 2;
    public static final int B4SDISTMODEL_GEO_OUTGOING = 3;
    public static final int B4SDISTMODEL_LONGRANGE = 1;
    public static final int B4SDISTMODEL_STANDARD = 0;
    @Deprecated
    public static final int B4SGENDER_FEMALE = 1;
    @Deprecated
    public static final int B4SGENDER_MALE = 2;
    @Deprecated
    public static final int B4SGENDER_UNDEFINED = 0;
    public static final int B4SLOGGINGMEDIUM_ALL = 0;
    public static final int B4SLOGGINGMEDIUM_LOCAL_ONLY = 3;
    public static final int B4SLOGGINGMEDIUM_WIFIONLY = 1;
    public static final int B4SLOGGINGMEDIUM_WWAN_ONLY = 2;
    public static final String B4SNOTIFICATION_TYPE_DEEPLINK = "deeplink";
    public static final String B4SNOTIFICATION_TYPE_PASSIVE = "passive";
    public static final String B4SNOTIFICATION_TYPE_RICH = "rich";
    public static final String B4SNOTIFICATION_TYPE_SIMPLE = "simple";
    public static final String B4SSATUS_ACCEPTED = "A";
    public static final String B4SSATUS_OPENED = "O";
    public static final String B4SSATUS_REJECTED = "R";
    public static final String B4SSATUS_SENT = "S";
    public static final int B4SSESSION_END_END = 5;
    public static final int B4SSESSION_END_FORCED = 3;
    public static final int B4SSESSION_END_MAXCOUNT = 4;
    public static final int B4SSESSION_END_NOBEACONS = 2;
    public static final int B4SSESSION_END_RANGEOUT = 0;
    public static final int B4SSESSION_END_SWAP = 1;
    public static final int B4SSESSION_TYPE_BEACON = 2;
    public static final int B4SSESSION_TYPE_GEOFENCE = 3;
    public static final int B4SSESSION_TYPE_INTERACTION = 5;
    public static final int B4SSESSION_TYPE_SHOP = 0;
    public static final String B4SSUPPORTED_TYPE_CUSTJOURNEY = "customerJourney";
    public static final String B4SSUPPORTED_TYPE_ORCONDITION = "orCondition";
    public static final String B4SSUPPORTED_TYPE_PASSIVE = "passive";
    private final SharedPreferences a;
    private final Api2 b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;
    private final int g;
    private Boolean h;
    private AppInfo i;
    private long j;
    private final Object k;
    private EnvironmentInfo l;
    private long m;
    private final Object n;
    private long o;
    private final Object p;
    public Spot spot;

    static class a {
        static final InteractionsApi a = new InteractionsApi();
    }

    private InteractionsApi() {
        this.k = new Object();
        this.n = new Object();
        this.p = new Object();
        B4SSettings b4SSettings = B4SSettings.get();
        b4SSettings.getApplicationContext();
        this.a = b4SSettings.getPreferences();
        this.b = Api2.get();
        EventBus.get().register(this);
        this.h = Boolean.valueOf(false);
        this.c = Device.getDeviceId();
        this.d = Device.getDeviceModel();
        this.e = Device.getDeviceName();
        String identifier = AdvertisingServices.get().getIdentifier();
        Boolean trackingEnabled = AdvertisingServices.get().getTrackingEnabled();
        this.f = identifier == null ? "0" : identifier;
        int i = trackingEnabled == null ? 2 : trackingEnabled.booleanValue() ? 0 : 1;
        this.g = i;
        if (identifier == null) {
            B4SLog.w((Object) this, "Advertising id could not be retrieved from the device");
        }
    }

    private <T> long a(T t) {
        long j;
        if (t instanceof AppInfo) {
            j = (long) ((AppInfo) t).beaconsCacheValidity;
        } else {
            if (this.i == null) {
                this.i = (AppInfo) e(AppInfo.class);
            }
            j = this.i == null ? 0 : (long) this.i.beaconsCacheValidity;
        }
        return ((j * 60) * 1000) + System.currentTimeMillis();
    }

    private <T> long a(T t, String str) {
        Class cls = t.getClass();
        String str2 = "b4s_cachevalidity_" + cls.getSimpleName();
        String str3 = "b4s_cachedata_" + cls.getSimpleName();
        String str4 = "b4s_cachemd5_" + cls.getSimpleName();
        long a = a((Object) t);
        Editor edit = this.a.edit();
        try {
            edit.putString(str3, Api2.get().getJackson().writeValueAsString(t));
            edit.putString(str4, str);
            edit.putLong(str2, a);
        } catch (JsonProcessingException e) {
            edit.remove(str3);
            edit.remove(str3);
            edit.remove(str2);
        }
        edit.apply();
        return a;
    }

    private <T> T a(Response<?> response, T t, Class<T> cls) {
        if (response == null || (response.code() != 204 && response.code() != 304)) {
            return (response == null || response.code() < 200 || response.code() > 300) ? t == null ? e(cls) : t : null;
        } else {
            if (t == null) {
                t = e(cls);
            }
            if (t == null) {
                return t;
            }
            this.a.edit().putLong("b4s_cachevalidity_" + cls.getSimpleName(), a((Object) cls)).apply();
            return t;
        }
    }

    private String a(Class<?> cls) {
        return this.a.getString("b4s_cachemd5_" + cls.getSimpleName(), null);
    }

    private long b(Class<?> cls) {
        return this.a.getLong("b4s_cachevalidity_" + cls.getSimpleName(), 0);
    }

    private boolean c(Class<?> cls) {
        return this.a.contains("b4s_cachedata_" + cls.getSimpleName());
    }

    private boolean d(Class<?> cls) {
        String str = "b4s_cachevalidity_" + cls.getSimpleName();
        return this.a.contains(str) && this.a.contains("b4s_cachedata_" + cls.getSimpleName()) && this.a.contains("b4s_cachemd5_" + cls.getSimpleName()) && this.a.getLong(str, 0) >= System.currentTimeMillis();
    }

    private <T> T e(Class<T> cls) {
        try {
            return Api2.get().getJackson().readValue(this.a.getString("b4s_cachedata_" + cls.getSimpleName(), ""), (Class) cls);
        } catch (Exception e) {
            B4SLog.e((Object) this, "Tried to rely on " + cls.getSimpleName() + " pref cache, but we have none!");
            this.a.edit().remove("b4s_cachevalidity_" + cls.getSimpleName()).remove("b4s_cachedata_" + cls.getSimpleName()).remove("b4s_cachemd5_" + cls.getSimpleName()).apply();
            return null;
        }
    }

    public static InteractionsApi get() {
        return a.a;
    }

    public void clearCache() {
        synchronized (this.k) {
            synchronized (this.n) {
                synchronized (this.p) {
                    B4SLog.i(InteractionsApi.class.getSimpleName(), "Clearing entirely our local cache data!");
                    this.i = null;
                    this.j = 0;
                    this.l = null;
                    this.m = 0;
                    this.spot = null;
                    this.o = 0;
                    Editor edit = this.a.edit();
                    edit.remove("b4s_cachevalidity_" + AppInfo.class.getSimpleName());
                    edit.remove("b4s_cachevalidity_" + EnvironmentInfo.class.getSimpleName());
                    edit.remove("b4s_cachevalidity_" + Spot.class.getSimpleName());
                    edit.remove("b4s_cachedata_" + AppInfo.class.getSimpleName());
                    edit.remove("b4s_cachedata_" + EnvironmentInfo.class.getSimpleName());
                    edit.remove("b4s_cachedata_" + Spot.class.getSimpleName());
                    edit.remove("b4s_cachemd5_" + AppInfo.class.getSimpleName());
                    edit.remove("b4s_cachemd5_" + EnvironmentInfo.class.getSimpleName());
                    edit.remove("b4s_cachemd5_" + Spot.class.getSimpleName());
                    edit.apply();
                }
            }
        }
    }

    public AppInfo getAppInfo(boolean z) {
        AppInfo appInfo;
        synchronized (this.k) {
            if (this.i == null || z || this.j <= System.currentTimeMillis()) {
                if (d(AppInfo.class) && !z) {
                    this.i = (AppInfo) e(AppInfo.class);
                    this.j = b(AppInfo.class);
                    if (this.i != null) {
                        appInfo = this.i;
                    }
                }
                Request request = new Request();
                request.sAppId = B4SSettings.get().getAppId();
                request.sDeviceOs = "ANDROID";
                request.sDeviceUdid = this.c;
                request.sDeviceModel = this.d;
                request.sDeviceName = this.e;
                request.sOsVersion = VERSION.RELEASE;
                request.sAdvertisingIdentifier = this.f;
                request.nAdvertisingTrackingEnabled = this.g;
                request.bBluetooth = BluetoothHelper.isEnabled();
                request.md5 = a(AppInfo.class);
                Response response = null;
                try {
                    response = ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).getAppInfos(request).execute();
                } catch (Exception e) {
                    B4SLog.e((Object) this, "Error loading " + AppInfo.class + ": " + e.toString());
                }
                appInfo = (AppInfo) a(response, this.i, AppInfo.class);
                if (appInfo != null) {
                    this.i = appInfo;
                    this.j = b(AppInfo.class);
                } else if (!(response == null || response.body() == null)) {
                    this.i = ((AppInfoWrapper) response.body()).app;
                    this.j = a(this.i, this.i.md5);
                }
                if (this.i != null) {
                    B4SLog.setRemotePriority(this.i);
                }
                appInfo = this.i;
            } else {
                appInfo = this.i;
            }
        }
        return appInfo;
    }

    public Spot getCachedInteractionsSpot() {
        if (this.spot != null) {
            return this.spot;
        }
        if (c(Spot.class)) {
            this.spot = (Spot) e(Spot.class);
            this.o = b(Spot.class);
        }
        return this.spot;
    }

    public AppInfo getCachedOnlyAppInfo() {
        synchronized (this.k) {
            if (this.i != null) {
                AppInfo appInfo = this.i;
                return appInfo;
            }
            if (d(AppInfo.class)) {
                this.i = (AppInfo) e(AppInfo.class);
                this.j = b(AppInfo.class);
                if (this.i != null) {
                    appInfo = this.i;
                    return appInfo;
                }
            }
            return null;
        }
    }

    public EnvironmentInfo getEnvironmentInfo() {
        Response response = null;
        synchronized (this.n) {
            if (this.i == null) {
                return response;
            } else if (this.l == null || this.m <= System.currentTimeMillis()) {
                if (d(EnvironmentInfo.class)) {
                    this.l = (EnvironmentInfo) e(EnvironmentInfo.class);
                    this.m = b(EnvironmentInfo.class);
                    if (this.l != null) {
                        r0 = this.l;
                        return r0;
                    }
                }
                Request request = new Request();
                request.sAppId = this.i.appId;
                request.sClientId = this.i.clientId;
                request.md5 = a(EnvironmentInfo.class);
                try {
                    response = ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).getEnvironmentInfo(request).execute();
                } catch (Exception e) {
                    B4SLog.e((Object) this, "Error loading " + EnvironmentInfo.class + ": " + e.toString());
                }
                r0 = (EnvironmentInfo) a(response, this.l, EnvironmentInfo.class);
                if (r0 != null) {
                    this.l = r0;
                    this.m = b(EnvironmentInfo.class);
                } else if (!(response == null || response.body() == null)) {
                    this.l = (EnvironmentInfo) response.body();
                    this.m = a(this.l, this.l.md5);
                }
                r0 = this.l;
                return r0;
            } else {
                r0 = this.l;
                return r0;
            }
        }
    }

    public Spot getInteractionsSpot(boolean z, Location location, int i, String str, Double d) {
        Response response = null;
        boolean z2 = false;
        if (location == null && str == null) {
            B4SLog.d((Object) this, "[getInteractionsSpot] location reference is missing");
            return response;
        }
        synchronized (this.p) {
            if (this.i == null) {
                B4SLog.d((Object) this, "[getInteractionsSpot] AppInfo null");
                return response;
            } else if (this.spot == null || z || this.o <= System.currentTimeMillis()) {
                if (d(Spot.class) && !z) {
                    B4SLog.d((Object) this, "[getInteractionsSpot] cached spot exists");
                    this.spot = (Spot) e(Spot.class);
                    this.o = b(Spot.class);
                    if (this.spot != null) {
                        B4SLog.d((Object) this, "[getInteractionsSpot] spot retrieved from cache");
                        this.spot.wasRefreshed = false;
                        virtualBeaconShopsPreset(this.spot);
                        r0 = this.spot;
                        return r0;
                    }
                }
                int i2 = this.i.radius;
                if (str != null && location == null) {
                    z2 = true;
                }
                int i3 = (location != null || str == null) ? i2 : ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                new SupportedFeature().type = "passive";
                new SupportedFeature().type = B4SSUPPORTED_TYPE_CUSTJOURNEY;
                new SupportedFeature().type = B4SSUPPORTED_TYPE_ORCONDITION;
                Request request = new Request();
                request.aSupported = new SupportedFeature[]{r0, r5, r6};
                request.sAppId = this.i.appId;
                request.sClientId = this.i.clientId;
                request.nLatitude = location == null ? response : Double.valueOf(location.getLatitude());
                request.nLongitude = location == null ? response : Double.valueOf(location.getLongitude());
                request.nCause = i;
                request.sDeviceUdid = this.c;
                request.sInnerName = str;
                request.nRadius = i3;
                request.sDemoMode = z2;
                request.bBluetooth = BluetoothHelper.isEnabled();
                request.nDistance = d;
                request.sAdvertisingIdentifier = this.f;
                request.nAdvertisingTrackingEnabled = this.g;
                request.sDataMD5 = a(Spot.class);
                try {
                    B4SLog.d((Object) this, "[getInteractionsSpot] querying context");
                    response = ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).getInteractionsSpot(request).execute();
                } catch (Exception e) {
                    B4SLog.e((Object) this, "[getInteractionsSpot] Error loading " + Spot.class + ": " + e.toString());
                }
                r0 = (Spot) a(response, this.spot, Spot.class);
                if (r0 != null) {
                    this.spot = r0;
                    this.spot.wasRefreshed = false;
                    this.o = b(Spot.class);
                    if (!this.h.booleanValue()) {
                        virtualBeaconShopsPreset(this.spot);
                    }
                } else if (!(response == null || response.body() == null)) {
                    this.spot = ((SpotWrapper) response.body()).res;
                    this.spot.wasRefreshed = true;
                    this.o = a(this.spot, this.spot.sDataMD5);
                    B4SLog.d((Object) this, "[getInteractionsSpot] spot downloaded from back");
                    virtualBeaconShopsPreset(this.spot);
                }
                r0 = this.spot;
                return r0;
            } else {
                this.spot.wasRefreshed = false;
                B4SLog.d((Object) this, "[getInteractionsSpot] cache still valid");
                r0 = this.spot;
                return r0;
            }
        }
    }

    public List<NearbyShop> getNearbyShops(Double d, Double d2) {
        synchronized (this.k) {
            getAppInfo(false);
            if (this.i == null) {
                return null;
            }
            Request request = new Request();
            request.sClientId = this.i.clientId;
            request.nLongitude = d;
            request.nLatitude = d2;
            request.nRadius = this.i.geofencingRadius;
            try {
                List<NearbyShop> list = (List) ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).getNearbyShops(request).execute().body();
                return list;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public void invalidateCache(boolean z) {
        synchronized (this.k) {
            B4SLog.i(InteractionsApi.class.getSimpleName(), "Resetting our local cache data expirations and any interaction lock/counters!");
            this.j = 0;
            this.m = 0;
            this.o = 0;
            Editor edit = this.a.edit();
            edit.remove("b4s_cachevalidity_" + AppInfo.class.getSimpleName());
            edit.remove("b4s_cachevalidity_" + EnvironmentInfo.class.getSimpleName());
            edit.remove("b4s_cachevalidity_" + Spot.class.getSimpleName());
            edit.apply();
            if (z) {
                InteractionService.resetSessions();
            }
        }
    }

    public void onEventBackgroundThread(ApplicationToForeground applicationToForeground) {
        invalidateCache(false);
    }

    public boolean registerProperties(Properties properties) {
        getAppInfo(false);
        if (this.i == null) {
            return false;
        }
        properties.sDeviceUdid = this.c;
        properties.sAppId = this.i.appId;
        properties.sClientId = this.i.clientId;
        try {
            Response execute = ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).registerProperties(properties).execute();
            boolean z = execute.code() >= 200 && execute.code() < 300;
            B4SLog.i((Object) this, "[registerProperties] ret:" + z);
            return z;
        } catch (Exception e) {
            B4SLog.e((Object) this, "[registerProperties] send failled:" + e.toString());
            return false;
        }
    }

    public boolean registerSession(SessionRegistration sessionRegistration) {
        getAppInfo(false);
        if (this.i == null) {
            return false;
        }
        B4SSettings b4SSettings = B4SSettings.get();
        sessionRegistration.sAppId = this.i.appId;
        sessionRegistration.sClientId = this.i.clientId;
        sessionRegistration.sCustomerId = this.c;
        sessionRegistration.sCustomerRef = b4SSettings.getCustomerClientRef();
        sessionRegistration.sOS = "ANDROID";
        sessionRegistration.sAdvertisingIdentifier = this.f;
        sessionRegistration.nAdvertisingTrackingEnabled = this.g;
        sessionRegistration.nVersion = 2;
        B4SLog.i(InteractionsApi.class.getSimpleName(), "Send Session type=" + sessionRegistration.nSessionType + " id=" + sessionRegistration.sSessionId + " entry=" + sessionRegistration.fEntryLatitude + "," + sessionRegistration.fEntryLongitude + " exit=" + sessionRegistration.fExitLatitude + "," + sessionRegistration.fExitLongitude + " dur=" + sessionRegistration.nDuration);
        try {
            return ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).registerSession(sessionRegistration).execute().code() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registerSessionStatus(String str, String str2, String str3) {
        getAppInfo(false);
        if (this.i == null) {
            return false;
        }
        SessionUpdate sessionUpdate = new SessionUpdate();
        sessionUpdate.sSessionId = str;
        sessionUpdate.sInnerName = str2;
        sessionUpdate.sStatus = str3;
        sessionUpdate.sClientId = this.i.clientId;
        sessionUpdate.sOS = "ANDROID";
        try {
            return ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).updateSession(sessionUpdate).execute().code() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendManualTag(ManualTag manualTag) {
        getAppInfo(false);
        if (this.i == null) {
            return false;
        }
        manualTag.applicationId = this.i.appId;
        manualTag.clientId = this.i.clientId;
        manualTag.timestamp = Long.valueOf(System.currentTimeMillis() / 1000);
        manualTag.deviceId = this.c;
        manualTag.advertisingIdentifier = this.f;
        manualTag.advertisingTrackingEnabled = this.g;
        try {
            return ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).manualTag(manualTag).execute().code() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendRemoteLog(RemoteLog remoteLog) {
        getAppInfo(false);
        if (this.i == null) {
            return false;
        }
        remoteLog.applicationId = this.i.appId;
        remoteLog.clientId = this.i.clientId;
        remoteLog.deviceId = this.c;
        String str = (remoteLog.priority == 6 || remoteLog.priority == 7) ? "logError" : remoteLog.priority == 5 ? "logWarn" : remoteLog.priority == 4 ? "logInfo" : "logDebug";
        try {
            return ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).remoteLog(str, remoteLog).execute().code() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean trackBeacons(TrackingBeacons trackingBeacons) {
        getAppInfo(false);
        if (this.i == null) {
            return false;
        }
        trackingBeacons.applicationId = this.i.appId;
        trackingBeacons.clientId = this.i.clientId;
        trackingBeacons.version = 1;
        trackingBeacons.advertisingIdentifier = this.f;
        trackingBeacons.advertisingTrackingEnabled = this.g;
        try {
            return ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).trackBeacons(trackingBeacons).execute().code() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean trackDevice(TrackingUser trackingUser) {
        getAppInfo(false);
        if (this.i == null) {
            return false;
        }
        B4SSettings b4SSettings = B4SSettings.get();
        trackingUser.applicationId = this.i.appId;
        trackingUser.clientId = this.i.clientId;
        trackingUser.deviceId = this.c;
        trackingUser.advertisingIdentifier = this.f;
        trackingUser.advertisingTrackingEnabled = this.g;
        trackingUser.custumerReference = b4SSettings.getCustomerClientRef();
        trackingUser.userId = b4SSettings.getCustomerUserId();
        trackingUser.gender = b4SSettings.getCustomerGender();
        trackingUser.firstName = b4SSettings.getCustomerFirstName();
        trackingUser.lastName = b4SSettings.getCustomerLastName();
        trackingUser.email = b4SSettings.getCustomerEmail();
        trackingUser.phone = b4SSettings.getCustomerPhone();
        trackingUser.address = b4SSettings.getCustomerAddress();
        trackingUser.city = b4SSettings.getCustomerCity();
        trackingUser.country = b4SSettings.getCustomerCountry();
        trackingUser.zipCode = b4SSettings.getCustomerZipcode();
        trackingUser.age = Integer.valueOf(b4SSettings.getCustomerAge());
        trackingUser.language = Locale.getDefault().getLanguage();
        trackingUser.deviceOs = "ANDROID";
        trackingUser.deviceOsVersion = VERSION.RELEASE;
        trackingUser.deviceModel = Device.getDeviceModel();
        trackingUser.sdkVersion = "2.0.13";
        trackingUser.appVersion = b4SSettings.getAppVersion();
        trackingUser.appPackage = b4SSettings.getAppPackage();
        trackingUser.bluetoothEnabled = BluetoothHelper.isEnabled();
        trackingUser.locationEnabled = GoogleApiAvailability.get().hasLocationServicesEnabled();
        trackingUser.playServicesInstalled = GoogleApiAvailability.get().hasPlayServices();
        try {
            return ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).trackDevice(trackingUser).execute().code() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean trackingCoordinates(List<TrackingLocation> list) {
        if (this.i == null) {
            B4SLog.w((Object) this, "[trackingCoordinates] appInfo is missing");
            return false;
        }
        TrackingCoordinates trackingCoordinates = new TrackingCoordinates(this.i);
        trackingCoordinates.advertisingIdentifier = this.f;
        trackingCoordinates.deviceId = this.c;
        trackingCoordinates.locations = list;
        for (TrackingLocation trackingLocation : list) {
            B4SLog.d((Object) this, "[trackingCoordinates:" + trackingLocation.date + "] Sending location:" + trackingLocation.latitude + "," + trackingLocation.longitude);
        }
        try {
            Response execute = ((InteractionsRoutes) this.b.getRoutes(InteractionsRoutes.class)).sendCoordinates(trackingCoordinates).execute();
            if (execute.code() != 204) {
                B4SLog.e((Object) this, "[trackingCoordinates] xmit failled:" + execute.toString());
            } else {
                B4SLog.d((Object) this, "[trackingCoordinates] xmit ok");
            }
            return execute.code() == 204;
        } catch (Exception e) {
            B4SLog.e((Object) this, "[trackingCoordinates] xmit failled:" + e.toString());
            return false;
        }
    }

    protected void virtualBeaconShopsPreset(Spot spot) {
        if (spot.interactions == null) {
            B4SLog.w((Object) this, "[virtualBeaconShopsPreset] no interactions");
            return;
        }
        B4SLog.d((Object) this, "[virtualBeaconShopsPreset] interactions=" + spot.interactions.size() + " shops=" + spot.shops.size());
        spot.init();
        for (Interaction interaction : spot.interactions) {
            B4SLog.d((Object) this, "[virtualBeaconShopsPreset] interaction:" + interaction.sName + " distModel=" + interaction.nDistModel + " notType=" + interaction.sNotificationType);
            if (interaction.sCampaignType.equals(B4SCAMPAIGN_TYPE_ZONE)) {
                for (String findShop : interaction.aShopIds) {
                    Shop findShop2 = spot.findShop(findShop);
                    findShop2.interactionRadius = (float) (interaction.nRangeMax / 100);
                    findShop2.addInteraction(interaction);
                }
            }
        }
        this.h = Boolean.valueOf(true);
    }
}
