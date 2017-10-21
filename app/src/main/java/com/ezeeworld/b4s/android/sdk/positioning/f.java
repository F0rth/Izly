package com.ezeeworld.b4s.android.sdk.positioning;

import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;

class f implements LeScanCallback {
    final /* synthetic */ e a;

    f(e eVar) {
        this.a = eVar;
    }

    public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        if (bluetoothDevice.getName() != null && bluetoothDevice.getName().startsWith("B4S")) {
            i iVar = new i();
            iVar.a = bluetoothDevice.getName();
            iVar.b = bluetoothDevice.getAddress();
            iVar.c = bArr;
            iVar.d = i;
            this.a.a.b.add(iVar);
        }
    }
}
