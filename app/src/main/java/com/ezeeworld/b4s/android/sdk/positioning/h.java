package com.ezeeworld.b4s.android.sdk.positioning;

import android.os.Binder;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.positioning.a.c;
import com.ezeeworld.b4s.android.sdk.server.DeviceSetting;

import java.util.List;
import java.util.Map;

class h extends Binder {
    final /* synthetic */ PositioningService a;

    h(PositioningService positioningService) {
        this.a = positioningService;
    }

    void a(List<DeviceSetting> list, Map<String, Position> map, List<Zone> list2) {
        if (this.a.f == null) {
            B4SLog.e(this.a, "Can't store the available beacons and their positions before the background service is bound and connected! Call this from the ServiceConnection onServiceConnected event listener instead.");
            return;
        }
        this.a.d.addSplit("init");
        this.a.i = new c();
        this.a.i.a(this.a, (Map) map);
        this.a.j = new j();
        this.a.j.a((List) list2);
        this.a.g = list;
        this.a.h = map;
        this.a.b();
    }
}
