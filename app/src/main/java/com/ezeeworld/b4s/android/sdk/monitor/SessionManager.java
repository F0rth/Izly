package com.ezeeworld.b4s.android.sdk.monitor;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.ibeacon.IBeaconData;
import com.ezeeworld.b4s.android.sdk.monitor.InteractionHistory.EventType;
import com.ezeeworld.b4s.android.sdk.playservices.location.Geofence;
import com.ezeeworld.b4s.android.sdk.playservices.location.Geofence.Builder;
import com.ezeeworld.b4s.android.sdk.playservices.location.GeofencingEvent;
import com.ezeeworld.b4s.android.sdk.playservices.location.LocationServices;
import com.ezeeworld.b4s.android.sdk.server.AppInfo;
import com.ezeeworld.b4s.android.sdk.server.Beacon;
import com.ezeeworld.b4s.android.sdk.server.EnvironmentInfo;
import com.ezeeworld.b4s.android.sdk.server.Group;
import com.ezeeworld.b4s.android.sdk.server.Interaction;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.ManualTag;
import com.ezeeworld.b4s.android.sdk.server.Shop;
import com.ezeeworld.b4s.android.sdk.server.Spot;
import com.ezeeworld.b4s.android.sdk.server.TrackingLocation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

final class SessionManager {
    private static SessionManager a;
    private long A;
    private Location B;
    private long C;
    private final Context b;
    private final SharedPreferences c;
    private HashMap<String, e> d;
    private ConcurrentHashMap<String, String> e;
    private List<Builder> f;
    private List<Shop> g;
    private Spot h;
    private Location i;
    private Location j;
    private double k;
    private Long l;
    private IBeaconData m;
    private PendingIntent n;
    private Boolean o = Boolean.valueOf(false);
    private double p;
    private double q;
    private double r;
    private B4SSpeedIndices s = B4SSpeedIndices.B4SSpeedIndices_UNKNOWN;
    private String t;
    private WifiManager u;
    private Boolean v = Boolean.valueOf(false);
    private Date w;
    private Location x;
    private String y;
    private String z;

    public enum B4SSpeedIndices {
        B4SSpeedIndices_UNKNOWN,
        B4SSpeedIndices_STATIC,
        B4SSpeedIndices_WALK,
        B4SSpeedIndices_CITY_DRIVE,
        B4SSpeedIndices_HIGHWAY_DRIVE
    }

    private SessionManager(Context context) {
        this.b = context;
        this.c = PreferenceManager.getDefaultSharedPreferences(context);
        this.d = new HashMap();
        this.f = new ArrayList();
        this.g = new ArrayList();
        this.e = k();
        this.z = "";
        this.A = System.currentTimeMillis();
        this.u = (WifiManager) context.getSystemService("wifi");
        try {
            this.i = LocationServices.get().getLastLocation();
            B4SLog.d("SessionManager", "SessionManager lastSpotLocation=" + this.i);
            AppInfo appInfo = InteractionsApi.get().getAppInfo(false);
            if (appInfo != null) {
                this.l = Long.valueOf(((long) appInfo.scanSampleRate) * 1000);
                BufferedLocationTracking.get().pushOrSchedule(appInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IBeaconData a(String str, List<IBeaconData> list) {
        for (IBeaconData iBeaconData : list) {
            if (iBeaconData.getID() != null && str.equals(iBeaconData.getID().getInnerName())) {
                return iBeaconData;
            }
        }
        return null;
    }

    static SessionManager a(Context context) {
        synchronized (SessionManager.class) {
            boolean z;
            try {
                SessionManager sessionManager;
                if (a == null) {
                    B4SLog.d("SessionManager", "SessionManager");
                    AppInfo appInfo = InteractionsApi.get().getAppInfo(false);
                    InteractionsApi.get().getEnvironmentInfo();
                    LocationManager locationManager = (LocationManager) context.getSystemService("location");
                    B4SLog.d("SessionManager", "SessionManager gps provider enabled=" + locationManager.isProviderEnabled("gps"));
                    B4SLog.d("SessionManager", "SessionManager network provider enabled=" + locationManager.isProviderEnabled("network"));
                    B4SLog.d("SessionManager", "SessionManager passive provider enabled=" + locationManager.isProviderEnabled("passive"));
                    sessionManager = new SessionManager(context);
                    a = sessionManager;
                    if (sessionManager.i != null) {
                        z = false;
                        a.h = InteractionsApi.get().getInteractionsSpot(false, a.i, 4, null, null);
                        a.l();
                        a.a(appInfo);
                    } else {
                        z = "No user location available: wait until we see a beacon to load interactions";
                        MonitoringStatus.update(MonitoringStatus.NoUserLocation, z);
                    }
                }
                sessionManager = a;
                return sessionManager;
            } finally {
                z = SessionManager.class;
            }
        }
    }

    private a a(e eVar, Beacon beacon) {
        return (eVar == null || beacon == null) ? null : (a) eVar.b.get(beacon.sInnerName);
    }

    private b a(e eVar, String str) {
        return eVar == null ? null : (b) eVar.c.get(str);
    }

    private e a(Shop shop) {
        if (shop == null) {
            B4SLog.d("SessionManager", "[findShopSession] no shop specified");
            return null;
        }
        B4SLog.d("SessionManager", "[findShopSession] shopId=" + shop.sShopId + " inside " + this.d.size() + " shop sessions");
        return (e) this.d.get(shop.sShopId);
    }

    private Beacon a(IBeaconData iBeaconData, Spot spot) {
        if (spot == null) {
            return null;
        }
        for (Beacon beacon : spot.beacons) {
            if (beacon.sInnerName.equals(iBeaconData.getID().getInnerName())) {
                return beacon;
            }
        }
        return null;
    }

    private Group a(Beacon beacon, List<Group> list) {
        return beacon == null ? null : c(beacon.sGroupId, (List) list);
    }

    private Group a(Shop shop, List<Group> list) {
        return shop == null ? null : c(shop.sGroupId, (List) list);
    }

    private Interaction a(String str, Spot spot) {
        if (spot == null || spot.interactions == null) {
            return null;
        }
        for (Interaction interaction : spot.interactions) {
            if (interaction.sInteractionId.equals(str)) {
                return interaction;
            }
        }
        return null;
    }

    private Shop a(Beacon beacon, Spot spot) {
        return (spot == null || beacon == null) ? null : b(beacon.sShopId, spot.shops);
    }

    private List<IBeaconData> a(List<IBeaconData> list, Spot spot, boolean z) {
        Iterator it = list.iterator();
        List arrayList = new ArrayList(list.size());
        while (it.hasNext()) {
            Object obj;
            IBeaconData iBeaconData = (IBeaconData) it.next();
            if (!(spot == null || spot.beacons == null)) {
                for (Beacon beacon : spot.beacons) {
                    if (iBeaconData.getID().getInnerName().equals(beacon.sInnerName)) {
                        obj = 1;
                        break;
                    }
                }
            }
            obj = null;
            if (obj == null) {
                arrayList.add(iBeaconData);
                it.remove();
            }
        }
        MonitoringStatus.visibleBeacons(list, arrayList, z);
        return list;
    }

    private void a(Spot spot, Spot spot2) {
        if (this.d != null && spot != null && spot2 != null && spot2.wasRefreshed && spot.interactions != null && spot2.interactions != null) {
            for (Interaction interaction : spot2.interactions) {
                Interaction a = a(interaction.sInteractionId, spot);
                if (a == null || !(a.sChecksum == null || a.sChecksum.equals(interaction.sChecksum))) {
                    for (e eVar : this.d.values()) {
                        for (a a2 : eVar.b.values()) {
                            a2.a(interaction);
                        }
                        for (b a3 : eVar.c.values()) {
                            a3.a(interaction);
                        }
                    }
                }
            }
            for (Interaction interaction2 : spot.interactions) {
                if (a(interaction2.sInteractionId, spot2) == null) {
                    for (e eVar2 : this.d.values()) {
                        for (a a22 : eVar2.b.values()) {
                            a22.b(interaction2);
                        }
                        if (eVar2.c.containsKey(interaction2.sInteractionId)) {
                            eVar2.c.remove(interaction2.sInteractionId);
                        }
                    }
                }
            }
        }
    }

    private void a(Serializable serializable) {
        try {
            B4SLog.d("SessionManager", "[saveNotifiedList] elements=" + this.e.size());
            OutputStream openFileOutput = this.b.openFileOutput("notifiedShops.dat", 0);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(openFileOutput);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            openFileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(String str, int i, Location location) {
        synchronized (this) {
            try {
                B4SLog.d("SessionManager", "[notifyCompositeIndex] " + str + " notified");
                this.t = str;
                EnvironmentInfo environmentInfo = InteractionsApi.get().getEnvironmentInfo();
                if (!(this.h == null || environmentInfo == null || this.d == null)) {
                    List<Shop> virtualBeaconsAtCompositeIndex = this.h.virtualBeaconsAtCompositeIndex(str);
                    B4SLog.d("SessionManager", "[notifyCompositeIndex] region=" + str + " shopsOnRegion=" + virtualBeaconsAtCompositeIndex.size() + " shopSessions=" + this.d.size());
                    for (Shop shop : virtualBeaconsAtCompositeIndex) {
                        e a = a(shop);
                        if (a != null) {
                            B4SLog.d("SessionManager", "[notifyCompositeIndex] shop:" + shop.sName + " interactions=" + shop.geofenceInteractionsList.size() + " shopSession=" + a.toString());
                        } else {
                            B4SLog.d("SessionManager", "[notifyCompositeIndex] shop:" + shop.sName + " interactions=" + shop.geofenceInteractionsList.size());
                        }
                        if (i == 2 && a == null) {
                            B4SLog.d("SessionManager", "[notifyCompositeIndex] Exiting shop:" + shop.sName + " without previous entry (no existing shop session)");
                        } else {
                            Group a2 = a(shop, environmentInfo.groups);
                            e eVar = a;
                            for (Interaction interaction : shop.geofenceInteractionsList) {
                                B4SLog.d("SessionManager", "[notifyCompositeIndex] index=" + str + " shop=" + shop.sName + " interaction=" + interaction.sName);
                                b a3 = a(eVar, interaction.sInteractionId);
                                if (eVar == null) {
                                    eVar = e.a(this.b, shop, 3600000, null);
                                    eVar.a(location, shop);
                                    this.d.put(shop.sShopId, eVar);
                                    B4SLog.d("SessionManager", "[notifyCompositeIndex] record shopsession on shopId=" + shop.sShopId + " for:" + shop.sName);
                                }
                                if (a3 == null) {
                                    a3 = b.a(this.b, eVar, a2, interaction, null);
                                    eVar.c.put(interaction.sInteractionId, a3);
                                    B4SLog.d("SessionManager", "[notifyCompositeIndex] record geofenceSession on interactionId=" + interaction.sInteractionId);
                                }
                                if (i == 1) {
                                    if (a3.a(location, shop)) {
                                        B4SLog.d("SessionManager", "[notifyCompositeIndex] Entry index=" + str + " shop=" + shop.sName + " interaction=" + interaction.sName);
                                        InteractionsApi.get().invalidateCache(false);
                                    } else if (interaction.nNotificationPresenceDuration > 0) {
                                        B4SLog.d("SessionManager", "[notifyCompositeIndex] Presence duration index=" + str + " shop=" + shop.sName + " interaction=" + interaction.sName);
                                        a3.b(location, shop);
                                    }
                                } else if (i == 2) {
                                    if (a3.a(location, shop, Boolean.valueOf(false))) {
                                        InteractionsApi.get().invalidateCache(false);
                                    }
                                    eVar.c.remove(interaction.sInteractionId);
                                    B4SLog.d("SessionManager", "[notifyCompositeIndex] > shopSessions=" + this.d.size());
                                    eVar.a(location, shop, Boolean.valueOf(false));
                                    this.d.remove(shop.sShopId);
                                    B4SLog.d("SessionManager", "[notifyCompositeIndex] < shopSessions=" + this.d.size());
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Shop b(String str, List<Shop> list) {
        for (Shop shop : list) {
            if (shop.sShopId.equals(str)) {
                return shop;
            }
        }
        return null;
    }

    private void b(Location location) {
        synchronized (this) {
            synchronized (this.e) {
                B4SLog.d("SessionManager", "[clearOutOfSightNotifiedRegions] notifiedShopsList=" + this.e.size());
                Iterator it = this.e.values().iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    Shop oneBeaconAtCompositeIndex = this.h.getOneBeaconAtCompositeIndex(str);
                    if (oneBeaconAtCompositeIndex == null) {
                        it.remove();
                        B4SLog.d("SessionManager", "[clearOutOfSightNotifiedRegions] " + oneBeaconAtCompositeIndex.sName + " removed (" + str + ") unknown index");
                    } else {
                        float distanceTo = oneBeaconAtCompositeIndex.location().distanceTo(location);
                        float f = oneBeaconAtCompositeIndex.interactionRadius + 200.0f;
                        B4SLog.d("SessionManager", "[clearOutOfSightNotifiedRegions] shop location=" + oneBeaconAtCompositeIndex.location() + " last location=" + location + " accuracy=" + location.getAccuracy());
                        B4SLog.d("SessionManager", "[clearOutOfSightNotifiedRegions] remove test on:" + oneBeaconAtCompositeIndex.sName + " distance=" + distanceTo + " vs " + f);
                        if (distanceTo > f) {
                            a(str, 2, location);
                            B4SLog.d("SessionManager", "[clearOutOfSightNotifiedRegions] " + oneBeaconAtCompositeIndex.sName + " removed (" + str + ")");
                            it.remove();
                        }
                    }
                }
                B4SLog.d("SessionManager", "[clearOutOfSightNotifiedRegions] still cached notifiedShopsList=" + this.e.size());
            }
        }
    }

    private boolean b(AppInfo appInfo, Location location) {
        int i = 1;
        try {
            String a = a(appInfo, location);
            Date date = new Date();
            Boolean valueOf = Boolean.valueOf(false);
            if (!a.equals(this.y) || this.w == null || this.y == null || this.x == null) {
                B4SLog.d("SessionManager", "[locationTracking] new box:" + location);
                valueOf = Boolean.valueOf(true);
            } else {
                double distanceTo = (double) location.distanceTo(this.x);
                long time = (date.getTime() - this.w.getTime()) / 1000;
                B4SLog.d("SessionManager", "[locationTracking] Test new location distance=" + distanceTo + " duration=" + time + " vs " + appInfo.minTimeBetweenUserCoords);
                if (distanceTo > ((double) appInfo.minDistanceBetweenUserCoords) || time > ((long) appInfo.minTimeBetweenUserCoords)) {
                    valueOf = Boolean.valueOf(true);
                }
            }
            if (valueOf.booleanValue()) {
                B4SLog.d("SessionManager", "[locationTracking] record");
                TrackingLocation trackingLocation = new TrackingLocation();
                trackingLocation.date = new Date();
                trackingLocation.latitude = location.getLatitude();
                trackingLocation.longitude = location.getLongitude();
                trackingLocation.precision = (int) location.getAccuracy();
                trackingLocation.toBesend = Boolean.valueOf(false);
                trackingLocation.averageSpeed = this.r;
                trackingLocation.speedIndex = this.s.ordinal();
                if (this.u != null) {
                    if (!this.u.isWifiEnabled()) {
                        i = 0;
                    }
                    trackingLocation.wifiStatus = i;
                } else {
                    trackingLocation.wifiStatus = 2;
                }
                BufferedLocationTracking.get().trackLocation(appInfo, trackingLocation);
                this.w = date;
                this.y = a;
                this.x = location;
            }
        } catch (Exception e) {
            B4SLog.e("SessionManager", "[locationTracking] exc:" + e.toString());
            e.printStackTrace();
        }
        return false;
    }

    private Group c(String str, List<Group> list) {
        for (Group group : list) {
            if (group.sGroupId.equals(str)) {
                return group;
            }
        }
        return null;
    }

    private void c(Location location) {
        B4SLog.d("SessionManager", "[testPresenceInsideGeofence] shopSessions:" + this.d.size());
        if (this.g == null) {
            B4SLog.d("SessionManager", "[testPresenceInsideGeofence] no virtual beacons nearby");
        } else if (location.getAccuracy() > 500.0f) {
            B4SLog.w("SessionManager", "[testPresenceInsideGeofence] Do not notify since accuracy is far too low (" + location.getAccuracy() + "m)");
        } else {
            for (Shop shop : this.g) {
                float distanceTo = location.distanceTo(shop.location());
                B4SLog.d("SessionManager", "[testPresenceInsideGeofence] vBeacon:" + shop.sName + " index=" + shop.compositeCoordinateIndex + " dist test:" + distanceTo + " vs " + shop.interactionRadius);
                if (distanceTo <= shop.interactionRadius + 50.0f) {
                    synchronized (this.e) {
                        if (this.e.containsKey(shop.compositeCoordinateIndex)) {
                            B4SLog.d("SessionManager", "[testPresenceInsideGeofence] " + shop.sName + " (" + shop.compositeCoordinateIndex + ") was already notified");
                        } else {
                            this.e.put(shop.compositeCoordinateIndex, shop.compositeCoordinateIndex);
                            a(shop.compositeCoordinateIndex, 1, location);
                            B4SLog.d("SessionManager", "[testPresenceInsideGeofence] notify " + shop.sName + " (" + shop.compositeCoordinateIndex + ") size=" + this.e.size());
                        }
                    }
                }
            }
        }
    }

    private void c(AppInfo appInfo, Location location) {
        String a = a(appInfo, location);
        long currentTimeMillis = (System.currentTimeMillis() - this.C) / 1000;
        if (this.B == null) {
            B4SLog.w("SessionManager", "[contextEvolutionManagement] speedComputationUsedLastLocation is null");
            this.B = location;
            this.C = System.currentTimeMillis();
            this.r = 0.0d;
        } else if (currentTimeMillis > 20) {
            float distanceTo = location.distanceTo(this.B);
            double d = (double) ((3.6f * distanceTo) / ((float) currentTimeMillis));
            this.r = d;
            this.C = System.currentTimeMillis();
            this.B = location;
            B4SLog.w("SessionManager", "[contextEvolutionManagement] distance=" + distanceTo + " duration=" + currentTimeMillis + " speed=" + d);
        } else {
            B4SLog.w("SessionManager", "[contextEvolutionManagement] location updated less than 20s ago.");
        }
        if (a.equalsIgnoreCase(this.z)) {
            B4SLog.w("SessionManager", "[contextEvolutionManagement] Same GContext:" + a);
        } else {
            this.A = System.currentTimeMillis();
            this.z = a;
            B4SLog.w("SessionManager", "[contextEvolutionManagement] GContext changed:" + a);
        }
        if ((System.currentTimeMillis() - this.A) / 1000 >= 300 || this.r == 0.0d) {
            if (this.s != B4SSpeedIndices.B4SSpeedIndices_STATIC) {
                this.s = B4SSpeedIndices.B4SSpeedIndices_STATIC;
                B4SLog.w("SessionManager", "[tuneLocationManager] STATIC 1 (" + this.s.ordinal() + ")");
            }
        } else if (this.r < 8.0d) {
            if (this.s != B4SSpeedIndices.B4SSpeedIndices_WALK) {
                this.s = B4SSpeedIndices.B4SSpeedIndices_WALK;
                B4SLog.w("SessionManager", "[tuneLocationManager] WALK deviceMeanSpeed=" + this.r + "(" + this.s.ordinal() + ")");
            }
        } else if (this.r <= 60.0d) {
            if (this.s != B4SSpeedIndices.B4SSpeedIndices_CITY_DRIVE) {
                this.s = B4SSpeedIndices.B4SSpeedIndices_CITY_DRIVE;
                B4SLog.w("SessionManager", "[tuneLocationManager] CITY deviceMeanSpeed=" + this.r + "(" + this.s.ordinal() + ")");
            }
        } else if (this.s != B4SSpeedIndices.B4SSpeedIndices_HIGHWAY_DRIVE) {
            this.s = B4SSpeedIndices.B4SSpeedIndices_HIGHWAY_DRIVE;
            B4SLog.w("SessionManager", "[tuneLocationManager] SPEED deviceMeanSpeed=" + this.r + "(" + this.s.ordinal() + ")");
        }
    }

    private void j() {
        synchronized (this) {
            try {
                B4SLog.d("SessionManager", "[testDelayedGeofencesNotifications] region=" + this.t + " shopSessions=" + this.d.size());
                EnvironmentInfo environmentInfo = InteractionsApi.get().getEnvironmentInfo();
                if (!(this.h == null || environmentInfo == null || this.d == null)) {
                    for (e eVar : this.d.values()) {
                        Shop shop = eVar.a;
                        if (shop == null) {
                            B4SLog.d("SessionManager", "[testDelayedGeofencesNotifications] No shop on session:" + eVar.toString());
                        } else if (shop.geofenceInteractionsList != null) {
                            B4SLog.d("SessionManager", "[testDelayedGeofencesNotifications] shop:" + shop.sName + " dist=" + LocationServices.get().getLastLocation().distanceTo(shop.location()) + " dur=" + eVar.d());
                            if (eVar != null) {
                                if (shop.geofenceInteractionsList != null) {
                                    B4SLog.d("SessionManager", "[testDelayedGeofencesNotifications] shop:" + shop.sName + " interactions=" + shop.geofenceInteractionsList.size() + " shopSession=" + eVar.i);
                                } else {
                                    B4SLog.d("SessionManager", "[testDelayedGeofencesNotifications] shop:" + shop.sName + " shopSession=" + eVar.toString());
                                }
                                for (Interaction interaction : shop.geofenceInteractionsList) {
                                    B4SLog.d("SessionManager", "[testDelayedGeofencesNotifications] index=" + this.t + " shop=" + shop.sName + " interaction=" + interaction.sName);
                                    b a = a(eVar, interaction.sInteractionId);
                                    float distanceTo = 100.0f * shop.location().distanceTo(LocationServices.get().getLastLocation());
                                    B4SLog.d("SessionManager", "[notifyCompositeIndex] Presence duration test index=" + this.t + " shop=" + shop.sName + " interaction=" + interaction.sName + " distance=" + distanceTo + " nNotificationPresenceDuration=" + interaction.nNotificationPresenceDuration);
                                    if (interaction.nNotificationPresenceDuration > 0 && distanceTo < ((float) interaction.nRangeMax)) {
                                        a.b(LocationServices.get().getLastLocation(), shop);
                                    }
                                }
                            } else {
                                B4SLog.d("SessionManager", "[testDelayedGeofencesNotifications] No session for shop:" + shop.sName);
                            }
                        } else {
                            continue;
                        }
                    }
                }
            } catch (Exception e) {
                B4SLog.d("SessionManager", "[testDelayedGeofencesNotifications] exc:" + e.toString());
                e.printStackTrace();
            }
        }
    }

    private ConcurrentHashMap k() {
        try {
            InputStream openFileInput = this.b.openFileInput("notifiedShops.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(openFileInput);
            this.e = (ConcurrentHashMap) objectInputStream.readObject();
            objectInputStream.close();
            openFileInput.close();
        } catch (Exception e) {
            this.e = new ConcurrentHashMap();
        }
        B4SLog.d("SessionManager", "[loadNotifiedList]  elements=" + this.e.size());
        return this.e;
    }

    private void l() {
        synchronized (this) {
            B4SLog.d("SessionManager", "[recoverSessionState]");
            B4SLog.d("SessionManager", "[recoverSessionState] shopSessions:" + this.d.size());
            BufferedSessionRegistration.get().recoverPersistedSessions(this.h, this.b, this.d);
            B4SLog.d("SessionManager", "[recoverSessionState] shopSessions:" + this.d.size());
            for (e eVar : this.d.values()) {
                B4SLog.d("SessionManager", "[recoverSessionState] shopSession:" + eVar.i);
                B4SLog.d("SessionManager", "[recoverSessionState] beaconSessions:" + eVar.b.size());
                B4SLog.d("SessionManager", "[recoverSessionState] geofenceSession:" + eVar.c.size());
            }
            this.o = Boolean.valueOf(true);
        }
    }

    private void m() {
        synchronized (this) {
            B4SLog.d("SessionManager", "[persistSessionState]");
            B4SLog.d("SessionManager", "[persistSessionState] shopSessions:" + this.d.values().size());
            for (e eVar : this.d.values()) {
                B4SLog.d("SessionManager", "[persistSessionState] shopSession:" + eVar.i);
                BufferedSessionRegistration.get().persistSession(eVar.m);
                B4SLog.d("SessionManager", "[persistSessionState] beaconSessions:" + eVar.b.size());
                for (a aVar : eVar.b.values()) {
                    B4SLog.d("SessionManager", " [persistSessionState] beaconSession:" + aVar.i);
                    BufferedSessionRegistration.get().persistSession(aVar.g);
                }
                B4SLog.d("SessionManager", "[persistSessionState] geofenceSession:" + eVar.c.size());
                for (b bVar : eVar.c.values()) {
                    B4SLog.d("SessionManager", " [persistSessionState] geofenceSession:" + bVar.i);
                    bVar.f.nRangeEnteredTime = bVar.d.c;
                    BufferedSessionRegistration.get().persistSession(bVar.f);
                }
            }
        }
    }

    private void n() {
        B4SLog.d("SessionManager", "[resetGeofenceSessions]");
        if (this.d != null && this.n != null) {
            this.f.clear();
            LocationServices.get().removeGeofences(this.n);
            for (e eVar : this.d.values()) {
                eVar.c.clear();
            }
        }
    }

    public final String a(AppInfo appInfo, Location location) {
        int i = appInfo.boundingBoxSideLength / 10;
        float latitude = (((float) ((int) ((location.getLatitude() * 10000.0d) / ((double) i)))) * ((float) i)) / 10000.0f;
        float longitude = (((float) i) * ((float) ((int) ((location.getLongitude() * 10000.0d) / ((double) i))))) / 10000.0f;
        return "VB4S-" + String.format("%1.4f", new Object[]{Float.valueOf(latitude)}) + "," + String.format("%1.4f", new Object[]{Float.valueOf(longitude)});
    }

    public final void a() {
        synchronized (this) {
            Iterator it = this.d.entrySet().iterator();
            Location lastLocation = LocationServices.get().getLastLocation();
            if (this.i == null) {
                this.h = null;
                this.h = InteractionsApi.get().getInteractionsSpot(true, lastLocation, 1, "", Double.valueOf(0.0d));
            } else {
                this.i = lastLocation;
                while (it.hasNext()) {
                    e eVar = (e) ((Entry) it.next()).getValue();
                    B4SLog.d("SessionManager", "[updateSessions] Test shop=" + eVar.a.sName + " dur=" + eVar.d() + "s, " + eVar.j + ", " + (eVar.l + eVar.j) + ", " + System.currentTimeMillis());
                    Iterator it2;
                    if (eVar.b()) {
                        B4SLog.d("SessionManager", "[updateSessions] Exiting shop=" + eVar.a.sName);
                        for (b b : eVar.c.values()) {
                            b.b(this.i);
                        }
                        eVar.a(this.i, eVar.a, Boolean.valueOf(true));
                        Iterator it3 = eVar.b.entrySet().iterator();
                        while (it3.hasNext()) {
                            ((a) ((Entry) it3.next()).getValue()).a(this.i, null, Boolean.valueOf(true));
                            it3.remove();
                        }
                        it.remove();
                    } else {
                        it2 = eVar.b.entrySet().iterator();
                        while (it2.hasNext()) {
                            a aVar = (a) ((Entry) it2.next()).getValue();
                            if (aVar.b()) {
                                aVar.a(this.i, eVar.a, Boolean.valueOf(true));
                                it2.remove();
                            }
                        }
                    }
                }
                B4SLog.d("SessionManager", "[updateSessions] shops=" + this.d.size() + " at:" + this.i);
                j();
                m();
            }
        }
    }

    public final void a(Location location) {
        Double d = null;
        synchronized (this) {
            try {
                B4SLog.d("SessionManager", "[handleLocationUpdate] lastSpotLocation=" + this.i + " newLocation=" + location + " hasSpeed:" + location.hasSpeed());
                AppInfo appInfo = InteractionsApi.get().getAppInfo(false);
                if (appInfo == null) {
                    B4SLog.d("SessionManager", "[handleLocationUpdate] AppInfo is missing");
                } else {
                    if (!this.o.booleanValue()) {
                        l();
                    }
                    float accuracy = location.getAccuracy();
                    if (accuracy > 1300.0f) {
                        B4SLog.d("SessionManager", "[handleLocationUpdate] locationAccuracy is too low for geofences management (" + accuracy + "m)");
                        c(appInfo, location);
                    } else {
                        if (appInfo != null) {
                            B4SLog.v("SessionManager", "app.radius=" + appInfo.radius);
                        }
                        this.t = a(appInfo, location);
                        B4SLog.v("SessionManager", "[handleLocationUpdate] newLocation=" + location);
                        B4SLog.v("SessionManager", "[handleLocationUpdate] lastSpotLocation=" + this.i);
                        float f = 0.0f;
                        if (!(location == null || location == this.i || this.i == null)) {
                            f = location.distanceTo(this.i);
                            B4SLog.v("SessionManager", "[handleLocationUpdate] distance=" + f + "/" + appInfo.geofencingRadius);
                        }
                        if (appInfo != null && appInfo.geofencingRadius > 0 && (this.i == null || f > ((float) appInfo.geofencingRadius))) {
                            if (this.i != null) {
                                B4SLog.v("SessionManager", String.format(Locale.US, "[handleLocationUpdate] Leaved Config Context. User moved %1$.2fm, which causes the spot (beacons/interactions/geofences) to update", new Object[]{Float.valueOf(f)}));
                            } else {
                                B4SLog.v("SessionManager", "[handleLocationUpdate] No previous lastSpotLocation");
                            }
                            this.i = location;
                            this.h = null;
                            InteractionsApi interactionsApi = InteractionsApi.get();
                            String innerName = this.m == null ? null : this.m.getID().getInnerName();
                            if (this.m != null) {
                                d = Double.valueOf(this.m.getDistance());
                            }
                            this.h = interactionsApi.getInteractionsSpot(true, location, 1, innerName, d);
                            a(appInfo);
                        } else if (this.j != null) {
                            B4SLog.v("SessionManager", "currentNBGCRLocation=" + this.j);
                            if (((double) this.j.distanceTo(location)) >= this.k) {
                                a(appInfo);
                            } else {
                                B4SLog.v("SessionManager", " distance from currentNBGCRLocation=" + this.j.distanceTo(location) + " vs:" + this.k);
                            }
                        } else {
                            B4SLog.v("SessionManager", "no currentNBGCRLocation");
                        }
                        if (this.d != null) {
                            for (e eVar : this.d.values()) {
                                eVar.c();
                                B4SLog.v("SessionManager", "shopSession shop=" + eVar.a.sName + " duration:" + eVar.d());
                                for (b bVar : eVar.c.values()) {
                                    bVar.c();
                                    bVar.a(location);
                                }
                            }
                        }
                        if (accuracy > 500.0f) {
                            B4SLog.d("SessionManager", "[handleLocationUpdate] locationAccuracy is too low (" + accuracy + "m)");
                            if (this.j != null) {
                                B4SLog.d("SessionManager", "[handleLocationUpdate] ensure geofences dist=" + this.j.distanceTo(LocationServices.get().getLastLocation()) + " vs " + this.k);
                                if (((double) this.j.distanceTo(LocationServices.get().getLastLocation())) >= this.k) {
                                    a(appInfo);
                                }
                            }
                            if (accuracy > 1300.0f && location.getSpeed() > BitmapDescriptorFactory.HUE_ORANGE) {
                                B4SLog.d("SessionManager", "[handleLocationUpdate] High Speed location tracking (" + location.hasSpeed() + ")");
                                if (appInfo.isCountingActive && B4SSettings.get().locationTrackingEnabled()) {
                                    b(appInfo, location);
                                }
                            }
                            c(appInfo, location);
                        } else {
                            c(appInfo, location);
                            if (this.j == null) {
                                B4SLog.d("SessionManager", "[handleLocationUpdate] no NBGCR");
                            } else if (((double) this.j.distanceTo(LocationServices.get().getLastLocation())) >= this.k) {
                                a(appInfo);
                            } else {
                                B4SLog.d("SessionManager", "[handleLocationUpdate] Still inside NBGCR region. Do not update.");
                                c(location);
                            }
                            b(location);
                            if (appInfo.isCountingActive && B4SSettings.get().locationTrackingEnabled()) {
                                b(appInfo, location);
                            }
                            a(this.e);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void a(GeofencingEvent geofencingEvent) {
        synchronized (this) {
            if (geofencingEvent.getTriggeringLocation().getLatitude() == this.p && geofencingEvent.getTriggeringLocation().getLongitude() == this.q) {
                B4SLog.d("SessionManager", "[handleTriggeredGeofences] location already triggered:" + geofencingEvent.getTriggeringLocation());
            } else {
                try {
                    this.p = geofencingEvent.getTriggeringLocation().getLatitude();
                    this.q = geofencingEvent.getTriggeringLocation().getLongitude();
                    EnvironmentInfo environmentInfo = InteractionsApi.get().getEnvironmentInfo();
                    if (!(this.h == null || environmentInfo == null || geofencingEvent == null || geofencingEvent.getTriggeringGeofences() == null || this.d == null)) {
                        AppInfo appInfo = InteractionsApi.get().getAppInfo(false);
                        float accuracy = geofencingEvent.getTriggeringLocation().getAccuracy();
                        if (accuracy > 500.0f) {
                            Location triggeringLocation = geofencingEvent.getTriggeringLocation();
                            B4SLog.d("SessionManager", "[handleTriggeredGeofences] locationAccuracy is too low (" + accuracy + "m) @" + geofencingEvent.getTriggeringLocation() + " hasSpeed:" + triggeringLocation.hasSpeed());
                            if (accuracy > 1300.0f && triggeringLocation.getSpeed() > BitmapDescriptorFactory.HUE_ORANGE) {
                                B4SLog.d("SessionManager", "[handleTriggeredGeofences] High Speed location tracking (" + triggeringLocation.hasSpeed() + ")");
                                if (appInfo.isCountingActive && B4SSettings.get().locationTrackingEnabled()) {
                                    b(appInfo, triggeringLocation);
                                }
                            }
                            c(appInfo, geofencingEvent.getTriggeringLocation());
                        } else {
                            c(appInfo, geofencingEvent.getTriggeringLocation());
                            Boolean valueOf = Boolean.valueOf(false);
                            Boolean valueOf2 = Boolean.valueOf(false);
                            if (this.i != null) {
                                B4SLog.v("SessionManager", "[handleTriggeredGeofences] config context distance=" + geofencingEvent.getTriggeringLocation().distanceTo(this.i) + "/" + appInfo.geofencingRadius + " threads=" + Thread.activeCount());
                            }
                            for (Geofence geofence : geofencingEvent.getTriggeringGeofences()) {
                                B4SLog.d("SessionManager", "[handleTriggeredGeofences] regionId=" + geofence.getRequestId() + " location=" + geofencingEvent.getTriggeringLocation() + " OUT=" + (geofencingEvent.getGeofenceTransition() == 2) + " IN=" + (geofencingEvent.getGeofenceTransition() == 1));
                                String[] split = geofence.getRequestId().split("_");
                                String str = split[0];
                                if (str.equals("NBCR")) {
                                    valueOf = Boolean.valueOf(true);
                                } else {
                                    Boolean bool;
                                    if (str.equals("VB4S")) {
                                        String str2 = split[2];
                                        synchronized (this.e) {
                                            if (this.e.containsKey(str2)) {
                                                B4SLog.d("SessionManager", "[handleTriggeredGeofences] " + str2 + " was already notified");
                                            } else {
                                                this.e.put(str2, str2);
                                                a(str2, geofencingEvent.getGeofenceTransition(), geofencingEvent.getTriggeringLocation());
                                                B4SLog.d("SessionManager", "[handleTriggeredGeofences] notify " + str2 + " size=" + this.e.size());
                                            }
                                        }
                                        bool = valueOf2;
                                    } else if (str.equals("NBGCR")) {
                                        valueOf = geofencingEvent.getGeofenceTransition() == 2 ? Boolean.valueOf(true) : valueOf;
                                    } else {
                                        if (str.equals("CCR")) {
                                            B4SLog.d("SessionManager", "[handleTriggeredGeofences] Leaved Config Context");
                                            if (appInfo != null && appInfo.geofencingRadius > 0) {
                                                this.i = geofencingEvent.getTriggeringLocation();
                                                this.h = null;
                                                this.h = InteractionsApi.get().getInteractionsSpot(true, this.i, 1, "", Double.valueOf(0.0d));
                                                a(appInfo);
                                                bool = Boolean.valueOf(true);
                                            }
                                        }
                                        bool = valueOf2;
                                    }
                                    valueOf2 = bool;
                                }
                            }
                            if (appInfo != null) {
                                if (!valueOf2.booleanValue() && (this.i == null || geofencingEvent.getTriggeringLocation().distanceTo(this.i) > ((float) appInfo.geofencingRadius))) {
                                    if (this.i != null) {
                                        B4SLog.v("SessionManager", String.format(Locale.US, "[handleTriggeredGeofences] Leaved Config Context. User moved %1$.2fm, which causes the spot (beacons/interactions/geofences) to update", new Object[]{Float.valueOf(geofencingEvent.getTriggeringLocation().distanceTo(this.i))}));
                                    } else {
                                        B4SLog.v("SessionManager", "[handleTriggeredGeofences] No previous lastSpotLocation");
                                    }
                                    this.i = geofencingEvent.getTriggeringLocation();
                                    this.h = null;
                                    this.h = InteractionsApi.get().getInteractionsSpot(true, geofencingEvent.getTriggeringLocation(), 1, this.m == null ? null : this.m.getID().getInnerName(), this.m == null ? null : Double.valueOf(this.m.getDistance()));
                                    a(appInfo);
                                }
                                B4SLog.d("SessionManager", "[handleTriggeredGeofences] isGeofenceResetNeeded=" + valueOf);
                                if (valueOf.booleanValue()) {
                                    a(appInfo);
                                } else if (this.j == null) {
                                    B4SLog.d("SessionManager", "[handleTriggeredGeofences] no NBGCR");
                                } else if (((double) this.j.distanceTo(LocationServices.get().getLastLocation())) >= this.k) {
                                    B4SLog.d("SessionManager", "[handleTriggeredGeofences] NBGCR leaved");
                                    a(appInfo);
                                } else {
                                    B4SLog.d("SessionManager", "[handleTriggeredGeofences] still inside NBGCR");
                                    c(geofencingEvent.getTriggeringLocation());
                                }
                                if (appInfo.isCountingActive && B4SSettings.get().locationTrackingEnabled()) {
                                    b(appInfo, geofencingEvent.getTriggeringLocation());
                                }
                                b(geofencingEvent.getTriggeringLocation());
                                a(this.e);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void a(AppInfo appInfo) {
        synchronized (this) {
            B4SLog.d("SessionManager", "[ensureInteractionGeofences]");
            EnvironmentInfo environmentInfo = InteractionsApi.get().getEnvironmentInfo();
            Location lastLocation = LocationServices.get().getLastLocation();
            B4SLog.d("SessionManager", "[ensureInteractionGeofences] location=" + lastLocation);
            if (!(appInfo == null || this.h == null || environmentInfo == null || lastLocation == null)) {
                B4SLog.d("SessionManager", "[ensureInteractionGeofences] get nearest");
                this.g = this.h.virtualBeaconsNear(lastLocation);
                HashMap hashMap = new HashMap();
                HashMap hashMap2 = new HashMap();
                if (this.g == null) {
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] no virtual beacons nearby");
                } else {
                    double d;
                    ArrayList arrayList;
                    String[] split;
                    String str;
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] sort nearest in:" + this.g.size() + " shops");
                    Collections.sort(this.g, new Comparator<Shop>(this) {
                        final /* synthetic */ SessionManager a;

                        {
                            this.a = r1;
                        }

                        public int a(Shop shop, Shop shop2) {
                            return shop.lastComputedNotificationDistance.compareTo(shop2.lastComputedNotificationDistance);
                        }

                        public /* synthetic */ int compare(Object obj, Object obj2) {
                            return a((Shop) obj, (Shop) obj2);
                        }
                    });
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] sorted nearest:" + this.g.size());
                    if (this.g.size() > 0) {
                        B4SLog.d("SessionManager", "[ensureInteractionGeofences] first:" + ((Shop) this.g.get(0)).sName + " (" + ((Shop) this.g.get(0)).lastComputedNotificationDistance + ") " + ((Shop) this.g.get(0)).nLatitude + "," + ((Shop) this.g.get(0)).nLongitude);
                        B4SLog.d("SessionManager", "[ensureInteractionGeofences] last:" + ((Shop) this.g.get(this.g.size() - 1)).sName + " (" + ((Shop) this.g.get(this.g.size() - 1)).lastComputedNotificationDistance + ") " + ((Shop) this.g.get(this.g.size() - 1)).nLatitude + "," + ((Shop) this.g.get(this.g.size() - 1)).nLongitude);
                    }
                    double d2 = (double) appInfo.geofencingRadius;
                    int i = 0;
                    for (Shop shop : this.g) {
                        if (hashMap.size() >= 69) {
                            B4SLog.i("SessionManager", "[ensureInteractionGeofences] regions set regions:" + hashMap.size() + " shops=" + i + " maxRadius=" + d2);
                            break;
                        }
                        String str2 = shop.compositeCoordinateIndex;
                        if (shop.lastComputedNotificationDistance.doubleValue() >= 0.0d) {
                            d2 = shop.lastComputedNotificationDistance.doubleValue();
                            hashMap.put(str2, shop);
                            B4SLog.i("SessionManager", "[ensureInteractionGeofences] region:" + shop.compositeCoordinateIndex + " dist=" + shop.lastComputedNotificationDistance);
                        } else {
                            B4SLog.i("SessionManager", "[ensureInteractionGeofences] subRegion:" + shop.compositeCoordinateIndex + " dist=" + shop.lastComputedNotificationDistance);
                            hashMap2.put(shop.compositeCoordinateIndex, shop);
                        }
                        i++;
                    }
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] regionsToBeMonitored:" + hashMap.size());
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] subRegionBeacons:" + hashMap2.size());
                    double d3 = d2 > ((double) appInfo.geofencingRadius) ? (double) appInfo.geofencingRadius : d2;
                    if (hashMap.size() > 0) {
                        if (d3 > 500.0d) {
                            d = d3 - 300.0d;
                        } else {
                            d3 /= 2.0d;
                            if (d3 < 85.0d) {
                                d = 85.0d;
                            }
                        }
                        B4SLog.i("SessionManager", "[ensureInteractionGeofences] final maxRadius=" + d);
                        hashMap.size();
                        B4SLog.d("SessionManager", "[ensureInteractionGeofences] clear uneeded regions");
                        arrayList = new ArrayList();
                        for (Builder requestId : this.f) {
                            split = requestId.getRequestId().split("_");
                            if (split[0].equals("VB4S")) {
                                str = split[2];
                                if (!(hashMap.containsKey(str) || hashMap2.containsKey(str))) {
                                    synchronized (this.e) {
                                        if (this.e.containsKey(str)) {
                                            B4SLog.d("SessionManager", "[ensureInteractionGeofences] " + str + " not previously notified");
                                        } else {
                                            a(str, 2, lastLocation);
                                            this.e.remove(str);
                                            B4SLog.d("SessionManager", "[ensureInteractionGeofences] " + str + " was previouly notified");
                                        }
                                    }
                                }
                            }
                        }
                        B4SLog.d("SessionManager", "[ensureInteractionGeofences] set geofences I");
                        this.f.clear();
                        B4SLog.d("SessionManager", "[ensureInteractionGeofences] set geofences II");
                        for (Shop shop2 : hashMap.values()) {
                            B4SLog.d("SessionManager", "Setting up interaction around " + shop2.sName + " (at " + shop2.lastComputedNotificationDistance + "m) " + shop2.nLatitude + "," + shop2.nLongitude + " index=" + shop2.compositeCoordinateIndex);
                            if (shop2.interactionRadius <= 0.0f) {
                                this.f.add(new Builder().setRequestId("VB4S_" + shop2.sShopId + "_" + shop2.compositeCoordinateIndex).setCircularRegion(shop2.nLatitude, shop2.nLongitude, 50.0f + shop2.interactionRadius).setTransitionTypes(3).setExpirationDuration(-1).setNotificationResponsiveness(1000));
                            } else {
                                B4SLog.e("SessionManager", "Can't create geofence for " + shop2.sName + " (at " + shop2.lastComputedNotificationDistance + "m / " + shop2.interactionRadius + "m)");
                            }
                        }
                        this.f.add(new Builder().setRequestId("NBGCR_" + System.currentTimeMillis()).setCircularRegion(lastLocation.getLatitude(), lastLocation.getLongitude(), (float) d).setTransitionTypes(2).setExpirationDuration(-1).setNotificationResponsiveness(2000));
                        if (this.i != null) {
                            B4SLog.d("SessionManager", "[ensureInteractionGeofences] update config context");
                            this.f.add(new Builder().setRequestId("CCR_" + System.currentTimeMillis()).setCircularRegion(this.i.getLatitude(), this.i.getLongitude(), (float) appInfo.geofencingRadius).setTransitionTypes(2).setExpirationDuration(-1).setNotificationResponsiveness(20000));
                        }
                        this.k = d;
                    }
                    d = d3;
                    B4SLog.i("SessionManager", "[ensureInteractionGeofences] final maxRadius=" + d);
                    hashMap.size();
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] clear uneeded regions");
                    arrayList = new ArrayList();
                    while (r1.hasNext()) {
                        split = requestId.getRequestId().split("_");
                        if (split[0].equals("VB4S")) {
                            str = split[2];
                            synchronized (this.e) {
                                if (this.e.containsKey(str)) {
                                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] " + str + " not previously notified");
                                } else {
                                    a(str, 2, lastLocation);
                                    this.e.remove(str);
                                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] " + str + " was previouly notified");
                                }
                            }
                        }
                    }
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] set geofences I");
                    this.f.clear();
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] set geofences II");
                    for (Shop shop22 : hashMap.values()) {
                        B4SLog.d("SessionManager", "Setting up interaction around " + shop22.sName + " (at " + shop22.lastComputedNotificationDistance + "m) " + shop22.nLatitude + "," + shop22.nLongitude + " index=" + shop22.compositeCoordinateIndex);
                        if (shop22.interactionRadius <= 0.0f) {
                            B4SLog.e("SessionManager", "Can't create geofence for " + shop22.sName + " (at " + shop22.lastComputedNotificationDistance + "m / " + shop22.interactionRadius + "m)");
                        } else {
                            this.f.add(new Builder().setRequestId("VB4S_" + shop22.sShopId + "_" + shop22.compositeCoordinateIndex).setCircularRegion(shop22.nLatitude, shop22.nLongitude, 50.0f + shop22.interactionRadius).setTransitionTypes(3).setExpirationDuration(-1).setNotificationResponsiveness(1000));
                        }
                    }
                    this.f.add(new Builder().setRequestId("NBGCR_" + System.currentTimeMillis()).setCircularRegion(lastLocation.getLatitude(), lastLocation.getLongitude(), (float) d).setTransitionTypes(2).setExpirationDuration(-1).setNotificationResponsiveness(2000));
                    if (this.i != null) {
                        B4SLog.d("SessionManager", "[ensureInteractionGeofences] update config context");
                        this.f.add(new Builder().setRequestId("CCR_" + System.currentTimeMillis()).setCircularRegion(this.i.getLatitude(), this.i.getLongitude(), (float) appInfo.geofencingRadius).setTransitionTypes(2).setExpirationDuration(-1).setNotificationResponsiveness(20000));
                    }
                    this.k = d;
                }
                B4SLog.d("SessionManager", "[ensureInteractionGeofences] currentNBGCRRadius=" + this.k);
                if (hashMap.size() > 0) {
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] regionsToBeMonitored=" + hashMap.size() + " sub region=" + hashMap2.size());
                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] notify local");
                    if (lastLocation.getAccuracy() > 500.0f) {
                        B4SLog.w("SessionManager", "[ensureInteractionGeofences] Do not notify since accuracy is far too low (" + lastLocation.getAccuracy() + "m)");
                    } else {
                        for (Shop shop222 : hashMap2.values()) {
                            B4SLog.d("SessionManager", "[ensureInteractionGeofences] SUB vBeacon:" + shop222.sName + " index=" + shop222.compositeCoordinateIndex);
                            synchronized (this.e) {
                                if (this.e.containsKey(shop222.compositeCoordinateIndex)) {
                                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] " + shop222.compositeCoordinateIndex + " was already notified");
                                } else {
                                    this.e.put(shop222.compositeCoordinateIndex, shop222.compositeCoordinateIndex);
                                    a(shop222.compositeCoordinateIndex, 1, lastLocation);
                                    B4SLog.d("SessionManager", "[ensureInteractionGeofences] notify " + shop222.sName + " (" + shop222.compositeCoordinateIndex + ") size=" + this.e.size());
                                }
                            }
                        }
                    }
                } else {
                    B4SLog.i("SessionManager", "[initVirtualBeaconsSet] no regionsToBeMonitored");
                }
                B4SLog.i("SessionManager", "[initVirtualBeaconsSet] geofences=" + this.f.size());
                if (this.n != null) {
                    LocationServices.get().removeGeofences(this.n);
                    this.n.cancel();
                }
                this.n = PendingIntent.getService(this.b, 1340, new Intent(this.b, InteractionService.class), 268435456);
                if (this.f.size() > 0) {
                    LocationServices.get().addGeofences(this.f, this.n);
                }
                a(this.e);
                this.v = Boolean.valueOf(true);
                this.j = lastLocation;
            }
        }
    }

    public final void a(ManualTag manualTag) {
        if (this.d != null && !this.d.isEmpty()) {
            e eVar = (e) this.d.values().iterator().next();
            manualTag.sessionId = eVar.i;
            manualTag.shopId = eVar.a.sShopId;
        }
    }

    public final void a(List<IBeaconData> list, AppInfo appInfo) {
        synchronized (this) {
            try {
                if (!B4SSettings.get().shouldDisableBluetoothScanning()) {
                    EnvironmentInfo environmentInfo = InteractionsApi.get().getEnvironmentInfo();
                    if (appInfo == null || environmentInfo == null) {
                        B4SLog.c("SessionManager", "No interaction matching possible: empty app or client info", MonitoringStatus.AppInfoNotLoaded);
                    } else {
                        e a;
                        a a2;
                        this.l = Long.valueOf(((long) appInfo.scanSampleRate) * 1000);
                        Collections.sort(list, IBeaconData.DISTANCE_COMPARATOR);
                        IBeaconData iBeaconData = list.isEmpty() ? null : (IBeaconData) list.get(0);
                        String innerName = iBeaconData == null ? null : iBeaconData.getID().getInnerName();
                        Double valueOf = iBeaconData == null ? null : Double.valueOf(iBeaconData.getDistance());
                        Spot cachedInteractionsSpot = InteractionsApi.get().getCachedInteractionsSpot();
                        if (this.h == null) {
                            this.i = LocationServices.get().getLastLocation();
                        }
                        InteractionsApi interactionsApi = InteractionsApi.get();
                        boolean z = this.h == null || (this.m == null && list.size() != 0);
                        this.h = interactionsApi.getInteractionsSpot(z, this.i, 4, innerName, valueOf);
                        a(cachedInteractionsSpot, this.h);
                        if (iBeaconData != null) {
                            this.m = iBeaconData;
                        }
                        Location lastLocation = LocationServices.get().getLastLocation();
                        List<IBeaconData> a3 = a((List) list, this.h, appInfo.isDeviceCalibrated);
                        for (IBeaconData iBeaconData2 : a3) {
                            InteractionHistory.get().registerLatestEvent(EventType.BeaconObserved, iBeaconData2.getID().getInnerName(), System.currentTimeMillis());
                            Beacon a4 = a(iBeaconData2, this.h);
                            B4SLog.d("SessionManager", " beacon " + a4.sInnerName);
                            Shop a5 = a(a4, this.h);
                            Group a6 = a(a4, environmentInfo.groups);
                            if (!(a4 == null || a5 == null || a6 == null)) {
                                a = a(a5);
                                a2 = a(a, a4);
                                if (a == null) {
                                    a = e.a(this.b, a5, 3600000, null);
                                    a.a(lastLocation, a5);
                                } else {
                                    a.a(iBeaconData2.getDistance());
                                }
                                B4SLog.d("SessionManager", " shopSession min dist=" + a.f);
                                this.d.put(a5.sShopId, a);
                                if (a2 == null) {
                                    a2 = a.a(this.b, a, a4, a6, iBeaconData2, this.h.interactions, null);
                                    a2.a(lastLocation, a5);
                                } else {
                                    a2.a(iBeaconData2.getDistance());
                                }
                                B4SLog.d("SessionManager", " beaconSession interactions=" + a2.e.size());
                                a.b.put(a4.sInnerName, a2);
                            }
                        }
                        int i = 0;
                        for (e a7 : this.d.values()) {
                            for (a a22 : a7.b.values()) {
                                i |= a22.a(a(a22.a.sInnerName, (List) a3), a7.a, lastLocation);
                            }
                        }
                        if (i != 0) {
                            InteractionsApi.get().invalidateCache(false);
                        }
                        if (i == 0) {
                            MonitoringStatus.update(MonitoringStatus.NoInteractionsMatched);
                        } else if (!a3.isEmpty()) {
                            MonitoringStatus.update(MonitoringStatus.MatchingBeacons);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void b() {
        synchronized (this) {
            long j = this.c.getLong("b4s_tracking_time", -1);
            if (this.l != null && this.l.longValue() > 0 && j < System.currentTimeMillis() - this.l.longValue() && this.d.size() > 0) {
                BufferedTracking.get().track((e) this.d.values().iterator().next());
                this.c.edit().putLong("b4s_tracking_time", System.currentTimeMillis()).apply();
            }
        }
    }

    public final boolean b(AppInfo appInfo) {
        return (appInfo.isCountingActive && B4SSettings.get().locationTrackingEnabled()) || !(this.d == null || this.f == null || this.f.size() <= 2);
    }

    public final Boolean c() {
        return this.i == null ? Boolean.valueOf(false) : this.v;
    }

    public final B4SSpeedIndices d() {
        return this.s;
    }

    public final Boolean e() {
        long currentTimeMillis = (System.currentTimeMillis() - this.A) / 1000;
        B4SLog.w("SessionManager", "[currentLocationIsStatic] GContext:" + this.z + " (" + currentTimeMillis + ")");
        return currentTimeMillis > 480 ? Boolean.valueOf(true) : Boolean.valueOf(false);
    }

    public final boolean f() {
        return this.u != null ? this.u.isWifiEnabled() : false;
    }

    public final int g() {
        if (this.u != null) {
            List scanResults = this.u.getScanResults();
            if (scanResults != null) {
                return scanResults.size();
            }
        }
        return 0;
    }

    public final List<ScanResult> h() {
        return this.u != null ? this.u.getScanResults() : null;
    }

    public final void i() {
        B4SLog.d("SessionManager", "[resetSessions]");
        n();
        InteractionHistory.get().clearAllEvents();
        this.d = new HashMap();
        AppInfo appInfo = InteractionsApi.get().getAppInfo(false);
        if (appInfo != null) {
            a(appInfo);
        }
    }
}
