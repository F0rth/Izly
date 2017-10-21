package com.ezeeworld.b4s.android.sdk.monitor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.ConnectivityHelper;
import com.ezeeworld.b4s.android.sdk.ConnectivityHelper.ConnectivityType;
import com.ezeeworld.b4s.android.sdk.CupboardDbHelper;
import com.ezeeworld.b4s.android.sdk.server.AppInfo;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.TrackingLocation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.qbusict.cupboard.DatabaseCompartment;

public class BufferedLocationTracking {
    private final Context a;
    private final DatabaseCompartment b;
    private final Queue<TrackingLocation> c;
    private PendingIntent d;
    private Date e;

    static class a {
        static final BufferedLocationTracking a = new BufferedLocationTracking();
    }

    private BufferedLocationTracking() {
        B4SSettings b4SSettings = B4SSettings.get();
        this.a = b4SSettings.getApplicationContext();
        this.b = CupboardDbHelper.database(b4SSettings.getApplicationContext());
        this.c = new ConcurrentLinkedQueue();
        this.c.addAll(this.b.query(TrackingLocation.class).list());
    }

    public static BufferedLocationTracking get() {
        return a.a;
    }

    public void pushOrSchedule(AppInfo appInfo) {
        Object obj = null;
        synchronized (this) {
            Date date = new Date();
            long j = Long.MAX_VALUE;
            if (this.e != null) {
                j = (date.getTime() - this.e.getTime()) / 1000;
            }
            if (j < ((long) appInfo.locationTrackingUploadInterval)) {
                B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Send conditions not reached (" + j + "/" + appInfo.locationTrackingUploadInterval + ", " + this.c.size() + "/" + appInfo.locationTrackingMaxUploadSize + ")");
            } else {
                if (this.d != null) {
                    this.d.cancel();
                }
                if (!this.c.isEmpty()) {
                    B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Go locationTrackingMaxUploadSize=" + appInfo.locationTrackingMaxUploadSize + " locationTrackingMaxRecordsToKeep=" + appInfo.locationTrackingMaxRecordsToKeep);
                    B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "pluggedOnlyUpload=" + appInfo.pluggedOnlyUpload + " wifiOnlyUpload=" + appInfo.wifiOnlyUpload);
                    this.e = date;
                    ConnectivityType current = ConnectivityHelper.getCurrent(this.a);
                    if (!appInfo.wifiOnlyUpload || current == ConnectivityType.Wifi) {
                        if (appInfo.pluggedOnlyUpload) {
                            int intExtra = this.a.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("status", -1);
                            if (intExtra == 2 || intExtra == 5) {
                                obj = 1;
                            }
                            if (obj == null) {
                                B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Locations are only uploaded when device is plugged");
                            } else {
                                B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Plugged");
                            }
                        }
                        if (current == ConnectivityType.Wifi || current == ConnectivityType.Cellular) {
                            TrackingLocation trackingLocation;
                            B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Network UP !");
                            Iterator it = this.c.iterator();
                            List<TrackingLocation> arrayList = new ArrayList();
                            Date date2 = new Date(System.currentTimeMillis() - ((long) ((((appInfo.locationTrackingHistorySize * 1000) * 60) * 60) * 24)));
                            B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "oldestDate=" + date2);
                            while (it.hasNext() && arrayList.size() < appInfo.locationTrackingMaxUploadSize) {
                                trackingLocation = (TrackingLocation) it.next();
                                if (trackingLocation.date.after(date2)) {
                                    arrayList.add(trackingLocation);
                                    trackingLocation.toBesend = Boolean.valueOf(true);
                                    B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Adding id=" + trackingLocation._id + " to send queue");
                                } else {
                                    this.b.delete(trackingLocation);
                                    it.remove();
                                    B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Deleting OOT id=" + trackingLocation._id);
                                }
                            }
                            B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Table Size=" + ((long) this.b.query(TrackingLocation.class).list().size()));
                            B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Queue Size=" + this.c.size());
                            B4SLog.i(BufferedLocationTracking.class.getSimpleName(), arrayList.size() + "/" + this.c.size() + " locations to be sent");
                            if (InteractionsApi.get().trackingCoordinates(arrayList)) {
                                this.c.peek();
                                it = this.c.iterator();
                                while (it.hasNext()) {
                                    trackingLocation = (TrackingLocation) it.next();
                                    B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "remove test for location id:" + trackingLocation._id + " (" + trackingLocation.toBesend + ")");
                                    if (trackingLocation.toBesend.booleanValue()) {
                                        B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "remove location id:" + trackingLocation._id);
                                        it.remove();
                                        this.b.delete(trackingLocation);
                                    }
                                }
                                B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Sent records cleared q:" + this.c.size() + " remaining");
                                B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Sent records cleared t:" + ((long) this.b.query(TrackingLocation.class).list().size()) + " remaining");
                            } else {
                                B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Xmit failled");
                                for (TrackingLocation trackingLocation2 : arrayList) {
                                    trackingLocation2.toBesend = Boolean.valueOf(false);
                                }
                            }
                        } else {
                            B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "No network connection");
                        }
                        if (!this.c.isEmpty()) {
                            B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Some locations are still here (" + this.c.size() + ")");
                            try {
                                j = Math.max((((long) appInfo.locationTrackingUploadInterval) * 1000) + 1000, 5000);
                            } catch (Exception e) {
                                j = 120000;
                            }
                            AlarmManager alarmManager = (AlarmManager) this.a.getSystemService("alarm");
                            this.d = PendingIntent.getService(this.a, 1342, new Intent(this.a, InteractionService.class).setAction("com.ezeeworld.b4s.android.sdk.monitor.B4S_PUSH_LOCATION_QUEUE"), 0);
                            j += System.currentTimeMillis();
                            B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Buffered location to be sent at " + new Date(j));
                            if (VERSION.SDK_INT >= 19) {
                                alarmManager.setExact(0, j, this.d);
                            } else {
                                alarmManager.set(0, j, this.d);
                            }
                        }
                    } else {
                        B4SLog.i(BufferedLocationTracking.class.getSimpleName(), "Locations are only uploaded with Wifi connectivity");
                    }
                }
            }
        }
    }

    public void trackLocation(AppInfo appInfo, TrackingLocation trackingLocation) {
        if (this.c.size() < appInfo.locationTrackingMaxRecordsToKeep) {
            this.c.add(trackingLocation);
            this.b.put(trackingLocation);
            B4SLog.w(BufferedLocationTracking.class.getSimpleName(), "trackLocation updated (id=" + trackingLocation._id + ") queue size=" + this.c.size());
            pushOrSchedule(appInfo);
            return;
        }
        B4SLog.w(BufferedLocationTracking.class.getSimpleName(), "Max local history size reached. Erasing history...");
        this.c.clear();
        this.b.delete(TrackingLocation.class, null, new String[0]);
    }
}
