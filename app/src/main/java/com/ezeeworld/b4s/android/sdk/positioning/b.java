package com.ezeeworld.b4s.android.sdk.positioning;

import android.os.AsyncTask;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.positioning.server.IndoorMap.BeaconPosition;
import com.ezeeworld.b4s.android.sdk.positioning.server.IndoorMap.IndoorArea;
import com.ezeeworld.b4s.android.sdk.positioning.server.a;

import java.util.ArrayList;
import java.util.HashMap;

class b extends AsyncTask<Void, Void, Boolean> {
    final /* synthetic */ PositioningManager a;

    b(PositioningManager positioningManager) {
        this.a = positioningManager;
    }

    protected Boolean a(Void... voidArr) {
        this.a.b = a.a().a(this.a.a);
        if (this.a.b == null) {
            return Boolean.valueOf(false);
        }
        this.a.c = this.a.b.calibration == null ? null : this.a.b.calibration.deviceSettings;
        this.a.d = new HashMap(this.a.b.beacons.size());
        for (BeaconPosition beaconPosition : this.a.b.beacons) {
            this.a.d.put(beaconPosition.sInnerName, new Position((double) beaconPosition.nX, (double) beaconPosition.nY));
        }
        this.a.e = new ArrayList(this.a.b.areas.size());
        for (IndoorArea indoorArea : this.a.b.areas) {
            this.a.e.add(new Zone(indoorArea.sName, (double) (indoorArea.nX + indoorArea.nW), (double) indoorArea.nX, (double) (indoorArea.nY + indoorArea.nH), (double) indoorArea.nY));
        }
        do {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
        } while (this.a.g == null);
        for (PositionListener onClearMap : this.a.f) {
            onClearMap.onClearMap();
        }
        this.a.g.a(this.a.c, this.a.d, this.a.e);
        return Boolean.valueOf(true);
    }

    protected void a(Boolean bool) {
        if (!bool.booleanValue()) {
            B4SLog.e(this.a, "Could not load map " + this.a.a + " (does not exist or no connection)");
        }
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        a((Boolean) obj);
    }
}
