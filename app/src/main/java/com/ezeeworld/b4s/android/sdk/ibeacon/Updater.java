package com.ezeeworld.b4s.android.sdk.ibeacon;

import android.content.Context;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.server.Beacon;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.Spot;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public final class Updater {
    private final Map<B4SBeacon, Boolean> a;
    private b b;

    static class a {
        static final Updater a = new Updater();
    }

    class b extends Thread {
        final /* synthetic */ Updater a;

        private b(Updater updater) {
            this.a = updater;
        }

        public void run() {
            Context applicationContext = B4SSettings.get().getApplicationContext();
            Spot cachedInteractionsSpot = InteractionsApi.get().getCachedInteractionsSpot();
            if (cachedInteractionsSpot == null || cachedInteractionsSpot.beacons == null) {
                this.a.a.clear();
                return;
            }
            synchronized (this.a.a) {
                Object obj = null;
                for (Entry entry : this.a.a.entrySet()) {
                    if (!((Boolean) entry.getValue()).booleanValue()) {
                        Beacon beacon;
                        final B4SBeacon b4SBeacon = (B4SBeacon) entry.getKey();
                        entry.setValue(Boolean.TRUE);
                        for (Beacon beacon2 : cachedInteractionsSpot.beacons) {
                            if (beacon2.sInnerName.equals(b4SBeacon.getInnerName())) {
                                beacon = beacon2;
                                break;
                            }
                        }
                        beacon = null;
                        if (beacon == null) {
                            this.a.a.remove(b4SBeacon);
                        } else {
                            Object obj2 = (b4SBeacon.getEmittingFrequency() == null || b4SBeacon.getEmittingFrequency().equals(Integer.valueOf(beacon.nReqTxInterval))) ? 1 : null;
                            Object obj3 = (b4SBeacon.getEmittingLevel() == null || b4SBeacon.getEmittingLevel().equals(Integer.valueOf(beacon.nReqTxPower))) ? 1 : null;
                            if (obj2 == null || obj3 == null) {
                                B4SLog.d("BUPDT", " [" + beacon.sInnerName + "] Current Frq is:" + b4SBeacon.getEmittingFrequency() + " Updating to:" + beacon.nReqTxInterval + " sz=" + this.a.a.size());
                                a.a(applicationContext, b4SBeacon).a(new b(this) {
                                    final /* synthetic */ b b;

                                    public void a(boolean z) {
                                        this.b.a.a.remove(b4SBeacon);
                                        B4SLog.d("BUPDT", " done");
                                    }
                                }).a(beacon.nReqTxInterval, beacon.nReqTxPower).a();
                                obj = 1;
                            }
                        }
                    }
                }
                if (obj == null) {
                    this.a.a.clear();
                } else {
                    B4SLog.d("BUPDT", " beacons queue clearing delayed");
                }
            }
        }
    }

    private Updater() {
        this.a = new ConcurrentHashMap();
    }

    public static Updater get() {
        return a.a;
    }

    public final void addObservations(List<B4SBeacon> list) {
        if (list.size() != 0 && this.a.isEmpty()) {
            synchronized (this.a) {
                for (B4SBeacon b4SBeacon : list) {
                    if (!this.a.containsKey(b4SBeacon)) {
                        this.a.put(b4SBeacon, Boolean.FALSE);
                    }
                }
            }
            this.b = new b();
            this.b.start();
        }
    }
}
