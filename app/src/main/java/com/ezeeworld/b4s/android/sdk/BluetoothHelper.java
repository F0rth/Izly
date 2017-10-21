package com.ezeeworld.b4s.android.sdk;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build.VERSION;

public class BluetoothHelper {
    public static BluetoothAdapter getDefaultAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    @TargetApi(18)
    public static boolean isAvailable(Context context) {
        return VERSION.SDK_INT >= 18 && context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public static boolean isEnabled() {
        try {
            return getDefaultAdapter().isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}
