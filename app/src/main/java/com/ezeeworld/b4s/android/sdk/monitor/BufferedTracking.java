package com.ezeeworld.b4s.android.sdk.monitor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build.VERSION;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.ConnectivityHelper;
import com.ezeeworld.b4s.android.sdk.ConnectivityHelper.ConnectivityType;
import com.ezeeworld.b4s.android.sdk.CupboardDbHelper;
import com.ezeeworld.b4s.android.sdk.Device;
import com.ezeeworld.b4s.android.sdk.ibeacon.IBeaconData;
import com.ezeeworld.b4s.android.sdk.playservices.location.LocationServices;
import com.ezeeworld.b4s.android.sdk.server.AppInfo;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.TrackingBeacons;
import com.ezeeworld.b4s.android.sdk.server.TrackingBeacons.BeaconRssi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.qbusict.cupboard.DatabaseCompartment;

public class BufferedTracking {
    private final Context a;
    private final DatabaseCompartment b;
    private final Map<String, BeaconRssi> c;
    private final Queue<TrackingBeacons> d;
    private PendingIntent e;

    static class a {
        static final BufferedTracking a = new BufferedTracking();
    }

    private BufferedTracking() {
        B4SSettings b4SSettings = B4SSettings.get();
        this.a = b4SSettings.getApplicationContext();
        this.b = CupboardDbHelper.database(b4SSettings.getApplicationContext());
        this.c = new ConcurrentHashMap();
        this.d = new ConcurrentLinkedQueue();
        Collection<TrackingBeacons> list = this.b.query(TrackingBeacons.class).list();
        for (TrackingBeacons trackingBeacons : list) {
            ((TrackingBeacons) r2.next()).beaconsAround = this.b.query(BeaconRssi.class).withSelection("trackingId = ?", new String[]{trackingBeacons._id.toString()}).list();
        }
        this.d.addAll(list);
    }

    private void a(long j) {
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            TrackingBeacons trackingBeacons = (TrackingBeacons) it.next();
            if (trackingBeacons.timestamp >= (System.currentTimeMillis() - j) / 1000) {
                try {
                    if (InteractionsApi.get().trackBeacons(trackingBeacons)) {
                        it.remove();
                        for (BeaconRssi delete : trackingBeacons.beaconsAround) {
                            this.b.delete(delete);
                        }
                        this.b.delete(trackingBeacons);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void a(long j, String str, Location location, String str2, String str3, String str4, String str5, Double d, String str6, String str7) {
        TrackingBeacons trackingBeacons = new TrackingBeacons();
        trackingBeacons.timestamp = j / 1000;
        trackingBeacons.deviceId = str;
        if (location != null) {
            trackingBeacons.latitude = location.getLatitude();
            trackingBeacons.longitude = location.getLongitude();
        }
        trackingBeacons.shopSessionId = str2;
        trackingBeacons.interactionId = str3;
        trackingBeacons.categoryId = str4;
        trackingBeacons.shopId = str5;
        trackingBeacons.distance = d;
        trackingBeacons.beaconSessionId = str6;
        trackingBeacons.connectionType = str7;
        trackingBeacons.beaconsAround = new ArrayList();
        int i = 0;
        for (Entry entry : this.c.entrySet()) {
            if (i >= 10) {
                break;
            }
            trackingBeacons.beaconsAround.add(entry.getValue());
            i++;
        }
        this.d.add(trackingBeacons);
        this.b.put(trackingBeacons);
        for (BeaconRssi beaconRssi : trackingBeacons.beaconsAround) {
            beaconRssi.trackingId = trackingBeacons._id;
            this.b.put(beaconRssi);
        }
        this.c.clear();
    }

    public static BufferedTracking get() {
        return a.a;
    }

    public void addObservations(List<IBeaconData> list) {
        if (list != null) {
            for (IBeaconData iBeaconData : list) {
                BeaconRssi beaconRssi = (BeaconRssi) this.c.get(iBeaconData.getID().getInnerName());
                if (beaconRssi == null || beaconRssi.rssi < iBeaconData.getRssi()) {
                    beaconRssi = new BeaconRssi();
                    beaconRssi.beaconInnerName = iBeaconData.getID().getInnerName();
                    beaconRssi.rssi = iBeaconData.getRssi();
                    this.c.put(beaconRssi.beaconInnerName, beaconRssi);
                }
            }
        }
    }

    public void pushOrSchedule() {
        if (this.e != null) {
            this.e.cancel();
        }
        if (!this.d.isEmpty()) {
            int i;
            Long l;
            try {
                ConnectivityType current = ConnectivityHelper.getCurrent(this.a);
                AppInfo appInfo = InteractionsApi.get().getAppInfo(false);
                Long valueOf = Long.valueOf(Math.max((((long) appInfo.scanSampleRate) * 1000) + 1000, 5000));
                Long l2;
                AlarmManager alarmManager;
                long longValue;
                if ((appInfo.wifiOnlyUpload || current == ConnectivityType.NoConnection) && !(appInfo.wifiOnlyUpload && current == ConnectivityType.Wifi)) {
                    l2 = valueOf;
                    i = 1;
                    l = l2;
                    if (i != 0) {
                        alarmManager = (AlarmManager) this.a.getSystemService("alarm");
                        this.e = PendingIntent.getService(this.a, 1341, new Intent(this.a, InteractionService.class).setAction("com.ezeeworld.b4s.android.sdk.monitor.B4S_PUSH_TRACKING_QUEUE"), 0);
                        longValue = (l != null ? 30000 : l.longValue()) + System.currentTimeMillis();
                        B4SLog.i((Object) this, "Buffered tracking data to be send at " + new Date(longValue));
                        if (VERSION.SDK_INT < 19) {
                            alarmManager.setExact(0, longValue, this.e);
                        } else {
                            alarmManager.set(0, longValue, this.e);
                        }
                    }
                }
                a(((long) appInfo.nMaxSessionStorageDuration) * 1000);
                if (this.d.isEmpty()) {
                    l = valueOf;
                    i = 0;
                } else {
                    l2 = valueOf;
                    i = 1;
                    l = l2;
                }
                if (i != 0) {
                    alarmManager = (AlarmManager) this.a.getSystemService("alarm");
                    this.e = PendingIntent.getService(this.a, 1341, new Intent(this.a, InteractionService.class).setAction("com.ezeeworld.b4s.android.sdk.monitor.B4S_PUSH_TRACKING_QUEUE"), 0);
                    if (l != null) {
                    }
                    longValue = (l != null ? 30000 : l.longValue()) + System.currentTimeMillis();
                    B4SLog.i((Object) this, "Buffered tracking data to be send at " + new Date(longValue));
                    if (VERSION.SDK_INT < 19) {
                        alarmManager.set(0, longValue, this.e);
                    } else {
                        alarmManager.setExact(0, longValue, this.e);
                    }
                }
            } catch (Exception e) {
                i = 1;
                l = null;
            }
        }
    }

    public void track(e eVar) {
        if (!this.c.isEmpty()) {
            Location lastLocation = LocationServices.get().getLastLocation();
            ConnectivityType current = ConnectivityHelper.getCurrent(this.a);
            String str = current == ConnectivityType.Wifi ? InteractionsApi.B4SCONNECTION_WIFI : current == ConnectivityType.Cellular ? InteractionsApi.B4SCONNECTION_CELLULAR : InteractionsApi.B4SCONNECTION_NOCONNECTION;
            a(System.currentTimeMillis(), Device.getDeviceId(), lastLocation, eVar.i, eVar.d == null ? null : eVar.d.sInteractionId, eVar.e == null ? null : eVar.e.sGroupId, eVar.a.sShopId, eVar.f, eVar.e == null ? null : eVar.e.sInnerName, str);
            pushOrSchedule();
        }
    }
}
