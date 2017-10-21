package com.ezeeworld.b4s.android.sdk.ibeacon;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import com.ezeeworld.b4s.android.sdk.B4SLog;

import java.util.Locale;
import java.util.UUID;

@TargetApi(18)
final class a {
    private static final UUID a = UUID.fromString("0000FFFF-0000-1000-8000-00805F9B34FB");
    private static final UUID b = UUID.fromString("0000FEED-0000-1000-8000-00805F9B34FB");
    private static final UUID c = UUID.fromString("0000DEAD-0000-1000-8000-00805F9B34FB");
    private static final String d = a.class.getSimpleName();
    private final Context e;
    private final B4SBeacon f;
    private final BluetoothDevice g;
    private final b h;
    private final String i;
    private BluetoothGatt j;
    private BluetoothGattCharacteristic k;
    private BluetoothGattCharacteristic l;

    interface b {
        void a(boolean z);
    }

    static class a {
        private final Context a;
        private final B4SBeacon b;
        private final BluetoothDevice c;
        private b d;

        private a(Context context, B4SBeacon b4SBeacon) {
            this.a = context;
            this.b = b4SBeacon;
            this.c = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(b4SBeacon.getMacAddress());
        }

        public a a(b bVar) {
            this.d = bVar;
            return this;
        }

        public c a(int i, int i2) {
            Object[] objArr = new Object[1];
            objArr[0] = String.format(Locale.US, "ATA&W %1$3d %2$02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
            String format = String.format("%1$-20s", objArr);
            B4SLog.d(a.d, this.b.getInnerName() + " currently at " + this.b.getEmittingFrequency() + "ms@" + this.b.getEmittingLevel() + "; should be " + i + "ms@" + i2);
            return new c(this.a, this.b, this.c, this.d, format);
        }
    }

    static class c {
        private final Context a;
        private final B4SBeacon b;
        private final BluetoothDevice c;
        private final b d;
        private final String e;

        private c(Context context, B4SBeacon b4SBeacon, BluetoothDevice bluetoothDevice, b bVar, String str) {
            this.a = context;
            this.b = b4SBeacon;
            this.c = bluetoothDevice;
            this.d = bVar;
            this.e = str;
            B4SLog.d(a.d, "B4S command length=" + str.length());
        }

        public void a() {
            new a(this.a, this.b, this.c, this.d, this.e).e();
        }
    }

    private a(Context context, B4SBeacon b4SBeacon, BluetoothDevice bluetoothDevice, b bVar, String str) {
        this.e = context;
        this.f = b4SBeacon;
        this.g = bluetoothDevice;
        this.h = bVar;
        this.i = str;
    }

    public static a a(Context context, B4SBeacon b4SBeacon) {
        return new a(context, b4SBeacon);
    }

    private void a(byte[] bArr) {
        this.k.setValue(a(this.i, bArr, this.f.getMajor(), this.f.getMinor(), IBeaconID.getUdidAsBytes(this.f.getUuid())));
        B4SLog.d(d, this.f.getInnerName() + " write hashed command");
        this.j.writeCharacteristic(this.k);
    }

    private byte[] a(String str, byte[] bArr, int i, int i2, byte[] bArr2) {
        return new Secure().computeCommand(str, bArr, (char) i, (char) i2, bArr2);
    }

    private void e() {
        if (this.j == null || this.k == null || this.l == null) {
            this.j = this.g.connectGatt(this.e, true, new BluetoothGattCallback(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
                    if (i == 0) {
                        bluetoothGattCharacteristic.getStringValue(0);
                        B4SLog.v(a.d, this.a.f.getInnerName() + " read success");
                        if (bluetoothGattCharacteristic.getUuid().equals(a.c)) {
                            this.a.a(bluetoothGattCharacteristic.getValue());
                            return;
                        }
                        return;
                    }
                    B4SLog.v(a.d, this.a.f.getInnerName() + " read failure: " + i);
                    this.a.h.a(false);
                }

                public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
                    if (i == 0) {
                        bluetoothGattCharacteristic.getStringValue(0);
                        B4SLog.d(a.d, this.a.f.getInnerName() + " write success");
                        if (this.a.h != null) {
                            try {
                                Thread.sleep(5000);
                                this.a.j.disconnect();
                                this.a.j.close();
                            } catch (Exception e) {
                            }
                            this.a.h.a(true);
                        }
                    } else {
                        B4SLog.v(a.d, this.a.f.getInnerName() + " write failure: " + i);
                        try {
                            Thread.sleep(5000);
                            this.a.j.disconnect();
                            this.a.j.close();
                        } catch (Exception e2) {
                        }
                        this.a.h.a(false);
                    }
                    this.a.j.close();
                }

                public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
                    B4SLog.v(a.d, this.a.f.getInnerName() + (i2 == 2 ? " connected" : " disconnected"));
                    if (i2 == 2) {
                        this.a.j.discoverServices();
                    } else {
                        this.a.h.a(false);
                    }
                }

                public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                    B4SLog.v(a.d, this.a.f.getInnerName() + (i == 0 ? " services discovered" : " services not available"));
                    if (i == 0) {
                        BluetoothGattService service = this.a.j.getService(a.a);
                        if (service != null) {
                            this.a.k = service.getCharacteristic(a.b);
                            this.a.l = service.getCharacteristic(a.c);
                            this.a.f();
                            return;
                        }
                        return;
                    }
                    this.a.h.a(false);
                }
            });
        } else {
            f();
        }
    }

    private void f() {
        this.j.readCharacteristic(this.l);
    }
}
