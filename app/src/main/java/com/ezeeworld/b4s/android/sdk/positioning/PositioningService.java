package com.ezeeworld.b4s.android.sdk.positioning;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.BluetoothHelper;
import com.ezeeworld.b4s.android.sdk.CalibrationConstants;
import com.ezeeworld.b4s.android.sdk.monitor.MonitoringStatus;
import com.ezeeworld.b4s.android.sdk.positioning.a.c;
import com.ezeeworld.b4s.android.sdk.server.DeviceSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class PositioningService extends Service {
    private static boolean a = false;
    private ConcurrentLinkedQueue<i> b = new ConcurrentLinkedQueue();
    private Map<String, CalibrationConstants> c = new HashMap();
    private TimingLogger d;
    private TimingLogger e;
    private IBinder f;
    private List<DeviceSetting> g;
    private Map<String, Position> h;
    private c i;
    private j j;

    private void b() {
        if (BluetoothHelper.isAvailable(this)) {
            this.d.addSplit("startScan");
            a = true;
            new e().start();
            new g().start();
        }
    }

    private boolean c() {
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            return defaultAdapter != null && defaultAdapter.isEnabled();
        } catch (SecurityException e) {
            B4SLog.c((Object) this, "Bluetooth permission not granted: add the android.permission.BLUETOOTH and android.permission.BLUETOOTH_ADMIN permissions in your application's AndroidManifest.xml", MonitoringStatus.BluetoothPermissionsNotRequested);
            return false;
        } catch (Exception e2) {
            B4SLog.c((Object) this, "Bluetooth can not be used: " + e2, MonitoringStatus.BluetoothIncompatible);
            return false;
        }
    }

    public final IBinder onBind(Intent intent) {
        IBinder hVar = new h(this);
        this.f = hVar;
        return hVar;
    }

    public final void onCreate() {
        if (BluetoothHelper.isAvailable(this)) {
            this.d = new TimingLogger("IPST", "timingsScanning");
            this.e = new TimingLogger("IPST", "timingsPositioning");
        }
    }

    public final boolean onUnbind(Intent intent) {
        a = false;
        if (this.i != null) {
            this.i.b();
        }
        return false;
    }
}
