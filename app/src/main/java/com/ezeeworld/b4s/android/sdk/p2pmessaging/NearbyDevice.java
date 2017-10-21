package com.ezeeworld.b4s.android.sdk.p2pmessaging;

import android.bluetooth.BluetoothDevice;

import de.greenrobot.event.EventBus;

public abstract class NearbyDevice {
    protected BluetoothDevice bluetoothDevice;
    protected final byte[] encryptionPassword;
    protected String receivedName = null;

    NearbyDevice(BluetoothDevice bluetoothDevice, byte[] bArr) {
        this.bluetoothDevice = bluetoothDevice;
        this.encryptionPassword = bArr;
    }

    abstract void a();

    abstract void a(ObjectType objectType, String str);

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public String getReceivedName() {
        return this.receivedName;
    }

    public void sendMessage(String str) {
        EventBus.getDefault().post(new PublishMessage(this, str));
    }
}
