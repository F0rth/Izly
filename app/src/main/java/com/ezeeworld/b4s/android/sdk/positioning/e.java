package com.ezeeworld.b4s.android.sdk.positioning;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.monitor.MonitoringStatus;

@TargetApi(18)
class e extends Thread {
    final /* synthetic */ PositioningService a;
    private final LeScanCallback b;

    private e(PositioningService positioningService) {
        this.a = positioningService;
        this.b = new f(this);
    }

    public void run() {
        while (PositioningService.a) {
            this.a.d.addSplit("scanInBurst");
            if (this.a.c()) {
                try {
                    BluetoothAdapter.getDefaultAdapter().startLeScan(this.b);
                } catch (Throwable th) {
                    B4SLog.c(this.a, "Bluetooth stack malfunctioning", MonitoringStatus.BluetoothCrashed);
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
            this.a.d.addSplit("stopScanBurst");
            if (this.a.c()) {
                try {
                    BluetoothAdapter.getDefaultAdapter().stopLeScan(this.b);
                } catch (Throwable th2) {
                    B4SLog.c(this.a, "Bluetooth stack malfunctioning", MonitoringStatus.BluetoothCrashed);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e2) {
            }
            this.a.d.addSplit("continueScanning");
            this.a.d.dumpToLog();
            this.a.d.reset();
        }
    }
}
