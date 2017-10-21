package com.ezeeworld.b4s.android.sdk.monitor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;

import com.ezeeworld.b4s.android.sdk.ActivityTracker;
import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.ConnectivityHelper;
import com.ezeeworld.b4s.android.sdk.ConnectivityHelper.ConnectivityType;
import com.ezeeworld.b4s.android.sdk.CupboardDbHelper;
import com.ezeeworld.b4s.android.sdk.server.Beacon;
import com.ezeeworld.b4s.android.sdk.server.Interaction;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.SessionRegistration;
import com.ezeeworld.b4s.android.sdk.server.Shop;
import com.ezeeworld.b4s.android.sdk.server.Spot;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.qbusict.cupboard.DatabaseCompartment;

public class BufferedSessionRegistration {
    private final Context a;
    private final DatabaseCompartment b;
    private final Queue<SessionRegistration> c;
    private PendingIntent d;

    static class a {
        static final BufferedSessionRegistration a = new BufferedSessionRegistration();
    }

    private BufferedSessionRegistration() {
        B4SSettings b4SSettings = B4SSettings.get();
        this.a = b4SSettings.getApplicationContext();
        this.b = CupboardDbHelper.database(b4SSettings.getApplicationContext());
        this.c = new ConcurrentLinkedQueue();
        this.c.addAll(this.b.query(SessionRegistration.class).list());
        B4SLog.d("REC", " BufferedSessionRegistration QSZ:" + this.c.size());
    }

    private Beacon a(String str, Spot spot) {
        if (spot == null) {
            return null;
        }
        for (Beacon beacon : spot.beacons) {
            if (beacon.sInnerName.equals(str)) {
                return beacon;
            }
        }
        return null;
    }

    private Interaction a(String str, List<Interaction> list) {
        if (str == null) {
            return null;
        }
        for (Interaction interaction : list) {
            if (interaction.sInteractionId.equals(str)) {
                return interaction;
            }
        }
        return null;
    }

    private Shop b(String str, List<Shop> list) {
        if (list != null) {
            for (Shop shop : list) {
                if (shop.sShopId.equals(str)) {
                    return shop;
                }
            }
        }
        return null;
    }

    public static BufferedSessionRegistration get() {
        return a.a;
    }

    public void persistSession(SessionRegistration sessionRegistration) {
        B4SLog.d("REG", " Persist session:" + sessionRegistration.sSessionId + " shopId:" + sessionRegistration.sShopId + " id=" + this.b.put(sessionRegistration) + " ints=" + sessionRegistration.nInteractions);
    }

    public void pushOrSchedule() {
        if (this.d != null) {
            this.d.cancel();
        }
        if (!this.c.isEmpty()) {
            Iterator it = this.c.iterator();
            while (it.hasNext()) {
                SessionRegistration sessionRegistration = (SessionRegistration) it.next();
                if (sessionRegistration.bSessionClosed.booleanValue()) {
                    if (InteractionsApi.get().registerSession(sessionRegistration)) {
                        it.remove();
                        this.b.delete(sessionRegistration);
                    } else {
                        sessionRegistration.bImmediate = false;
                        this.b.put(sessionRegistration);
                    }
                }
            }
            if (!this.c.isEmpty()) {
                long max;
                try {
                    max = Math.max((((long) InteractionsApi.get().getAppInfo(false).scanSampleRate) * 1000) + 1000, 5000);
                } catch (Exception e) {
                    max = 30000;
                }
                AlarmManager alarmManager = (AlarmManager) this.a.getSystemService("alarm");
                this.d = PendingIntent.getService(this.a, 1342, new Intent(this.a, InteractionService.class).setAction("com.ezeeworld.b4s.android.sdk.monitor.B4S_PUSH_SESSION_QUEUE"), 0);
                max += System.currentTimeMillis();
                B4SLog.i(BufferedSessionRegistration.class.getSimpleName(), "Buffered new session creation to be send at " + new Date(max));
                if (VERSION.SDK_INT >= 19) {
                    alarmManager.setExact(0, max, this.d);
                } else {
                    alarmManager.set(0, max, this.d);
                }
            }
        }
    }

    public void recoverPersistedSessions(Spot spot, Context context, HashMap<String, e> hashMap) {
        if (!this.c.isEmpty()) {
            try {
                B4SLog.d("REC", " recoverPersistedSessions QSZ:" + this.c.size());
                Iterator it = this.c.iterator();
                while (it.hasNext()) {
                    SessionRegistration sessionRegistration = (SessionRegistration) it.next();
                    B4SLog.d("REC", " SR Shop session:" + sessionRegistration.sSessionId + " shop:" + sessionRegistration.sShopId + " closed=" + sessionRegistration.bSessionClosed + " sessionType=" + sessionRegistration.nSessionType + " date=" + sessionRegistration.dDate);
                    if (!sessionRegistration.bSessionClosed.booleanValue() && sessionRegistration.nSessionType == 0) {
                        Shop b = b(sessionRegistration.sShopId, spot.shops);
                        hashMap.put(b.sShopId, e.a(context, b, 3600000, sessionRegistration));
                        B4SLog.d("REC", " Shop session recreated shop:" + sessionRegistration.sShopId + " sessionId:" + sessionRegistration.sSessionId + " ints=" + sessionRegistration.nInteractions);
                        it.remove();
                    }
                }
                B4SLog.d("REC", "[recoverPersistedSessions] shopSessions:" + hashMap.size());
                Iterator it2 = this.c.iterator();
                while (it2.hasNext()) {
                    try {
                        SessionRegistration sessionRegistration2 = (SessionRegistration) it2.next();
                        B4SLog.d("REC", " SR Shop session:" + sessionRegistration2.sSessionId + " shop:" + sessionRegistration2.sShopId + " closed=" + sessionRegistration2.bSessionClosed + " sessionType=" + sessionRegistration2.nSessionType + " date=" + sessionRegistration2.dDate + " rangeEnteredTime=" + sessionRegistration2.nRangeEnteredTime + " ints=" + sessionRegistration2.nInteractions + " dur=" + sessionRegistration2.nDuration + " dist=" + sessionRegistration2.nDistance);
                        if (!(sessionRegistration2.bSessionClosed.booleanValue() || sessionRegistration2.nSessionType == 0)) {
                            switch (sessionRegistration2.nSessionType) {
                                case 2:
                                    e eVar = (e) hashMap.get(sessionRegistration2.sShopId);
                                    Beacon a = a(sessionRegistration2.sBeaconId, spot);
                                    B4SLog.d("REC", " Beacon session recreation beacon=" + a + " shopSession=" + eVar + " ints=" + sessionRegistration2.nInteractions);
                                    if (eVar != null && a != null) {
                                        eVar.b.put(a.sInnerName, a.a(context, eVar, a, null, null, spot.interactions, sessionRegistration2));
                                        B4SLog.d("REC", " Beacon session recreated:" + sessionRegistration2.sSessionId);
                                        break;
                                    }
                                    B4SLog.d("REC", " Beacon session recreation failled for:" + sessionRegistration2.sSessionId);
                                    break;
                                case 3:
                                    e eVar2 = (e) hashMap.get(sessionRegistration2.sShopId);
                                    Interaction a2 = a(sessionRegistration2.sInteractionId, eVar2.a.geofenceInteractionsList);
                                    B4SLog.d("REC", " Geofence session recreation interaction=" + a2.sInteractionId + " shopSession=" + sessionRegistration2.sSessionId + " ints=" + sessionRegistration2.nInteractions);
                                    if (eVar2 != null && a2 != null) {
                                        b a3 = b.a(context, eVar2, null, a2, sessionRegistration2);
                                        a3.d.c = sessionRegistration2.nRangeEnteredTime;
                                        a3.d.b = d.a(sessionRegistration2.nRangeState);
                                        eVar2.c.put(a2.sInteractionId, a3);
                                        B4SLog.d("REC", " Geofence session recreated:" + sessionRegistration2.sSessionId);
                                        break;
                                    }
                                    B4SLog.d("REC", " Geofence session recreation failled for:" + sessionRegistration2.sSessionId);
                                    break;
                                break;
                            }
                            it2.remove();
                        }
                    } catch (Exception e) {
                        B4SLog.e("REC", " Session recover exception:" + e.toString());
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void registerSession(SessionRegistration sessionRegistration) {
        ConnectivityType current = ConnectivityHelper.getCurrent(this.a);
        String str = current == ConnectivityType.Wifi ? InteractionsApi.B4SCONNECTION_WIFI : current == ConnectivityType.Cellular ? InteractionsApi.B4SCONNECTION_CELLULAR : InteractionsApi.B4SCONNECTION_NOCONNECTION;
        sessionRegistration.sNetwork = str;
        sessionRegistration.bImmediate = true;
        sessionRegistration.bAppActive = ActivityTracker.isAppInForeground();
        sessionRegistration.bSessionClosed = Boolean.valueOf(true);
        this.c.add(sessionRegistration);
        this.b.put(sessionRegistration);
        B4SLog.d("REG", " Session[" + sessionRegistration.sSessionId + "] location coordinates=" + sessionRegistration.fEntryLatitude + "," + sessionRegistration.fEntryLongitude + " ints=" + sessionRegistration.nInteractions);
        pushOrSchedule();
    }
}
