package com.ezeeworld.b4s.android.sdk.monitor;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.BluetoothHelper;
import com.ezeeworld.b4s.android.sdk.CalibrationConstants;
import com.ezeeworld.b4s.android.sdk.ibeacon.B4SBeacon;
import com.ezeeworld.b4s.android.sdk.ibeacon.Distance;
import com.ezeeworld.b4s.android.sdk.server.AppInfo;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ScanService extends Service {
    static boolean a = false;
    private boolean b = true;
    private ConcurrentLinkedQueue<c> c = new ConcurrentLinkedQueue();
    private final ConcurrentHashMap<B4SBeacon, Distance> d = new ConcurrentHashMap();
    private AppInfo e;
    private int f = 10000;
    private int g = 1000;
    private int h = CommonStatusCodes.AUTH_API_INVALID_CREDENTIALS;
    private long i;
    private boolean j;

    @TargetApi(18)
    class a extends Thread {
        final /* synthetic */ ScanService a;
        private final LeScanCallback b;

        private a(ScanService scanService) {
            this.a = scanService;
            this.b = new LeScanCallback(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                    if (bluetoothDevice.getName() != null && bluetoothDevice.getName().startsWith("B4S")) {
                        c cVar = new c();
                        cVar.a = bluetoothDevice.getName();
                        cVar.b = bluetoothDevice.getAddress();
                        cVar.c = bArr;
                        cVar.d = i;
                        this.a.a.c.add(cVar);
                    }
                }
            };
        }

        public void run() {
            while (this.a.b) {
                if (this.a.a()) {
                    try {
                        BluetoothAdapter.getDefaultAdapter().startLeScan(this.b);
                    } catch (Throwable th) {
                        B4SLog.c(this.a, "Bluetooth stack malfunctioning", MonitoringStatus.BluetoothCrashed);
                    }
                }
                try {
                    Thread.sleep((long) this.a.f);
                } catch (InterruptedException e) {
                }
                if (this.a.a()) {
                    try {
                        BluetoothAdapter.getDefaultAdapter().stopLeScan(this.b);
                    } catch (Throwable th2) {
                        B4SLog.c(this.a, "Bluetooth stack malfunctioning", MonitoringStatus.BluetoothCrashed);
                    }
                }
                try {
                    Thread.sleep((long) this.a.g);
                } catch (InterruptedException e2) {
                }
            }
        }
    }

    class b extends Thread {
        final /* synthetic */ ScanService a;

        private b(ScanService scanService) {
            this.a = scanService;
        }

        public void run() {
            while (this.a.b) {
                try {
                    Intent intent;
                    List<ResolveInfo> queryIntentServices = this.a.getPackageManager().queryIntentServices(new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_OBSERVATIONS"), 0);
                    List<ResolveInfo> queryIntentServices2 = this.a.getPackageManager().queryIntentServices(new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_BEACONS"), 0);
                    Object obj = this.a.e != null ? 1 : null;
                    if (B4SSettings.isInitialized()) {
                        this.a.e = InteractionsApi.get().getAppInfo(false);
                    }
                    while (!this.a.c.isEmpty()) {
                        c cVar = (c) this.a.c.poll();
                        B4SBeacon fromScanData = B4SBeacon.fromScanData(cVar.a, cVar.b, cVar.c, cVar.d);
                        if (fromScanData != null) {
                            Object obj2 = (Distance) this.a.d.get(fromScanData);
                            if (obj2 == null) {
                                obj2 = Distance.build(CalibrationConstants.get(this.a.e == null ? null : this.a.e.deviceSettings, fromScanData), (double) cVar.d);
                            } else if (obj != null || this.a.e == null) {
                                obj2.smooth((double) cVar.d);
                            } else {
                                obj2 = Distance.build(CalibrationConstants.get(this.a.e.deviceSettings, fromScanData), obj2.getDistanceEstimate());
                                obj2.smooth((double) cVar.d);
                            }
                            this.a.d.put(fromScanData, obj2);
                        }
                    }
                    if (this.a.d.size() > 0) {
                        this.a.i = System.currentTimeMillis();
                        if (!this.a.j) {
                            this.a.a(13);
                            this.a.j = true;
                        }
                    } else if (System.currentTimeMillis() - this.a.i < 600000) {
                        if (!this.a.j) {
                            this.a.a(13);
                            this.a.j = true;
                        }
                    } else if (this.a.j) {
                        this.a.a(12);
                        this.a.j = false;
                    }
                    Iterator it = this.a.d.entrySet().iterator();
                    ArrayList arrayList = new ArrayList(this.a.d.size());
                    while (it.hasNext()) {
                        Entry entry = (Entry) it.next();
                        if (((Distance) entry.getValue()).isFresh()) {
                            ((B4SBeacon) entry.getKey()).getData().setDistance(((Distance) entry.getValue()).getDistanceEstimate());
                            arrayList.add(((B4SBeacon) entry.getKey()).getData());
                        } else {
                            it.remove();
                        }
                    }
                    for (ResolveInfo resolveInfo : queryIntentServices) {
                        intent = new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_OBSERVATIONS");
                        intent.setComponent(new ComponentName(resolveInfo.serviceInfo.packageName, InteractionService.class.getName()));
                        intent.putExtra("b4s_has_observations", !this.a.d.isEmpty());
                        intent.putParcelableArrayListExtra("b4s_observations", arrayList);
                        try {
                            this.a.startService(intent);
                        } catch (Exception e) {
                        }
                    }
                    for (ResolveInfo resolveInfo2 : queryIntentServices2) {
                        intent = new Intent("com.ezeeworld.b4s.android.sdk.monitor.B4S_BEACONS");
                        intent.setComponent(new ComponentName(resolveInfo2.serviceInfo.packageName, InteractionService.class.getName()));
                        intent.putParcelableArrayListExtra("b4s_beacons", Collections.list(this.a.d.keys()));
                        try {
                            this.a.startService(intent);
                        } catch (Exception e2) {
                        }
                    }
                } catch (Exception e3) {
                }
                try {
                    Thread.sleep((long) this.a.h);
                } catch (InterruptedException e4) {
                }
            }
        }
    }

    static class c {
        public String a;
        public String b;
        public byte[] c;
        public int d;

        private c() {
        }
    }

    private void a(int i) {
        switch (i) {
            case 0:
                this.g = 1000;
                this.h = CommonStatusCodes.AUTH_API_INVALID_CREDENTIALS;
                this.f = 10000;
                break;
            case 1:
                this.g = CommonStatusCodes.AUTH_API_INVALID_CREDENTIALS;
                this.h = 3500;
                this.f = 10000;
                break;
            case 2:
                this.g = 10000;
                this.h = 3500;
                this.f = 7000;
                break;
            case 3:
                this.g = 14000;
                this.h = 3500;
                this.f = 7000;
                break;
            case 4:
                this.g = 18000;
                this.h = 3500;
                this.f = 7000;
                break;
            case 5:
                this.g = 30000;
                this.h = 3500;
                this.f = 7000;
                break;
            case 6:
                this.g = 18000;
                this.h = 3500;
                this.f = 10000;
                break;
            case 7:
                this.g = CommonStatusCodes.AUTH_API_INVALID_CREDENTIALS;
                this.h = 3500;
                this.f = 7000;
                break;
            case 8:
                this.g = 6000;
                this.h = 3500;
                this.f = 7000;
                break;
            case 9:
                this.g = 9000;
                this.h = 3500;
                this.f = 7000;
                break;
            case 10:
                this.g = 5000;
                this.h = 3500;
                this.f = CommonStatusCodes.AUTH_API_INVALID_CREDENTIALS;
                break;
            case 11:
                this.g = 8500;
                this.h = 3500;
                this.f = 5500;
                break;
            case 12:
                this.g = 8000;
                this.h = 3500;
                this.f = 6000;
                break;
            case 13:
                this.g = CommonStatusCodes.AUTH_API_INVALID_CREDENTIALS;
                this.h = 3500;
                this.f = 9000;
                break;
        }
        B4SLog.d((Object) this, "B4S timings : " + this.f + " / " + this.g + " / " + this.h);
    }

    private boolean a() {
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter == null) {
                return false;
            }
            if (!defaultAdapter.isEnabled() && (B4SSettings.get() == null || !B4SSettings.get().shouldEnforceBluetooth())) {
                return false;
            }
            try {
                if (!defaultAdapter.isEnabled()) {
                    try {
                        defaultAdapter.enable();
                        B4SLog.ws((Object) this, "Forcefully enable Bluetooth LE (as per the app setting)");
                    } catch (RuntimeException e) {
                        B4SLog.c((Object) this, "Error starting the Bluetooth LE service (probably because it is not supported): " + e.toString(), MonitoringStatus.BluetoothIncompatible);
                        return false;
                    }
                }
                return true;
            } catch (Exception e2) {
                B4SLog.c((Object) this, "Crash while starting bluetooth scan: " + e2.toString(), MonitoringStatus.UnexpectedError);
                return false;
            }
        } catch (SecurityException e3) {
            B4SLog.c((Object) this, "Bluetooth permission not granted: add the android.permission.BLUETOOTH and android.permission.BLUETOOTH_ADMIN permissions in your application's AndroidManifest.xml", MonitoringStatus.BluetoothPermissionsNotRequested);
            return false;
        } catch (Exception e22) {
            B4SLog.c((Object) this, "Bluetooth can not be used: " + e22, MonitoringStatus.BluetoothIncompatible);
            return false;
        }
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        if (BluetoothHelper.isAvailable(this)) {
            B4SLog.is((Object) this, "B4S ScanService created");
            this.j = false;
            a(12);
            a = true;
            new a().start();
            new b().start();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        a = false;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return !BluetoothHelper.isAvailable(this) ? 2 : 1;
    }
}
