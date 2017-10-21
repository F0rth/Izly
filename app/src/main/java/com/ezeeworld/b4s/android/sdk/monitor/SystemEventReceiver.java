package com.ezeeworld.b4s.android.sdk.monitor;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.BluetoothHelper;

@SuppressLint({"UnsafeProtectedBroadcastReceiver"})
public class SystemEventReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (BluetoothHelper.isAvailable(context)) {
            MonitoringManager.ensureMonitoringService(context);
        } else {
            B4SLog.w((Object) this, "Cannot start because Bluetooth LE is not supported on this device.");
        }
    }
}
