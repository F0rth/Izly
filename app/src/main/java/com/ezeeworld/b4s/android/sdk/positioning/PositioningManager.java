package com.ezeeworld.b4s.android.sdk.positioning;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.text.TextUtils;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.BluetoothHelper;
import com.ezeeworld.b4s.android.sdk.EventBus;
import com.ezeeworld.b4s.android.sdk.positioning.server.IndoorMap;
import com.ezeeworld.b4s.android.sdk.server.DeviceSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class PositioningManager {
    private String a;
    private IndoorMap b;
    private List<DeviceSetting> c;
    private Map<String, Position> d;
    private List<Zone> e;
    private List<PositionListener> f;
    private h g;
    private boolean h;
    private ServiceConnection i;

    private PositioningManager() {
        this.f = new ArrayList();
        this.h = false;
        this.i = new a(this);
    }

    public static PositioningManager get() {
        if (!TextUtils.isEmpty(c.a.a)) {
            return c.a;
        }
        throw new RuntimeException("Please call through PositioningManager.init() before using the indoor positioning system!");
    }

    public static PositioningManager init(String str) {
        c.a.a = str;
        return c.a;
    }

    public final IndoorMap getMap() {
        return this.b;
    }

    public final void onEventMainThread(PositioningUpdate positioningUpdate) {
        for (PositionListener onPositionUpdate : this.f) {
            onPositionUpdate.onPositionUpdate(positioningUpdate);
        }
    }

    public final void onEventMainThread(UserZoneChange userZoneChange) {
        for (PositionListener onZoneUpdated : this.f) {
            onZoneUpdated.onZoneUpdated(userZoneChange);
        }
    }

    public final void register(PositionListener positionListener) {
        if (!this.h) {
            B4SLog.w((Object) this, "Registered, but the positioning service will not send updates until start() is called.");
        }
        this.f.add(positionListener);
    }

    public final void start(Context context) {
        if (BluetoothHelper.isAvailable(context)) {
            if (!this.h) {
                B4SLog.is((Object) this, "Starting positioning service");
                this.h = true;
                context.getApplicationContext().bindService(new Intent(context.getApplicationContext(), PositioningService.class), this.i, 1);
                EventBus.get().register(this);
            }
            new b(this).execute(new Void[0]);
            return;
        }
        B4SLog.e((Object) this, "Cannot start positioning service, as Bluetooth LE is unsupported on this device");
    }

    public final void stop(Context context) {
        if (this.h) {
            B4SLog.is((Object) this, "Stopping positioning service");
            if (!this.f.isEmpty()) {
                B4SLog.w((Object) this, "There are still listeners registered to the positioning service; they will no longer get updates.");
            }
            EventBus.get().unregister(this);
            context.getApplicationContext().unbindService(this.i);
            this.h = false;
        }
    }

    public final void unregister(PositionListener positionListener) {
        this.f.remove(positionListener);
    }
}
