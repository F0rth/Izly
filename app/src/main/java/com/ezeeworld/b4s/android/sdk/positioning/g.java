package com.ezeeworld.b4s.android.sdk.positioning;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.CalibrationConstants;
import com.ezeeworld.b4s.android.sdk.EventBus;
import com.ezeeworld.b4s.android.sdk.ibeacon.B4SBeacon;
import com.ezeeworld.b4s.android.sdk.ibeacon.Distance;

import java.util.Locale;

class g extends Thread {
    final /* synthetic */ PositioningService a;

    private g(PositioningService positioningService) {
        this.a = positioningService;
    }

    public void run() {
        while (PositioningService.a) {
            this.a.e.addSplit("handleScans");
            while (!this.a.b.isEmpty()) {
                i iVar = (i) this.a.b.poll();
                String b4SName = B4SBeacon.getB4SName(iVar.a);
                if (this.a.h != null && this.a.h.containsKey(b4SName)) {
                    B4SBeacon fromScanData = B4SBeacon.fromScanData(iVar.a, iVar.b, iVar.c, iVar.d);
                    if (fromScanData != null) {
                        if (!this.a.c.containsKey(b4SName)) {
                            this.a.c.put(b4SName, CalibrationConstants.get(this.a.g, fromScanData));
                        }
                        B4SLog.vs("IPST", String.format(Locale.US, "%1$s @ %2$.2fm", new Object[]{b4SName, Double.valueOf(Distance.calculateDistance((CalibrationConstants) this.a.c.get(b4SName), (double) iVar.d))}));
                        this.a.i.a(b4SName, r0);
                    }
                }
            }
            this.a.e.addSplit("feedDistances");
            PositioningUpdate a = this.a.i.a();
            this.a.e.addSplit("updatedPosition: " + a);
            if (a != null) {
                UserZoneChange a2 = this.a.j.a(a.position);
                a.zone = this.a.j.a();
                this.a.e.addSplit("updatedUserZone: " + a.zone);
                EventBus.get().post(a);
                if (a2 != null) {
                    EventBus.get().post(a2);
                }
                this.a.e.addSplit("posted");
            }
            try {
                Thread.sleep(333);
            } catch (InterruptedException e) {
            }
            this.a.e.addSplit("continuePositioning");
            this.a.e.dumpToLog();
            this.a.e.reset();
        }
    }
}
