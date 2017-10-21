package com.ezeeworld.b4s.android.sdk.p2pmessaging;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.text.TextUtils;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.p2pmessaging.MessagingException.ErrorCode;

import de.greenrobot.event.EventBus;

import java.util.Arrays;
import java.util.UUID;

@TargetApi(18)
final class d extends NearbyDevice {
    private static final UUID a = UUID.fromString("A27FDB17-EC1E-4506-BFDA-79FB7882BA33");
    private static final UUID b = UUID.fromString("53A7C23B-DAFE-47B8-A404-EA22BA5C0835");
    private static final UUID c = UUID.fromString("9E6C762F-B8EA-4599-895A-B0B4DABE7B21");
    private static final UUID d = UUID.fromString("A043A760-C76F-44DF-94B2-72B8F05323EC");
    private static final UUID e = UUID.fromString("1E4697AF-A4A3-4753-AADA-5539482A286F");
    private static final UUID f = UUID.fromString("651EE962-1C1C-4298-AE70-B23176691174");
    private final Context g;
    private BluetoothGatt h;
    private BluetoothGattCharacteristic i;
    private boolean j = false;
    private StringBuilder k = null;
    private boolean l = false;
    private byte[] m = null;

    d(BluetoothDevice bluetoothDevice, Context context, byte[] bArr) {
        super(bluetoothDevice, bArr);
        this.g = context;
        e();
    }

    private void e() {
        B4SLog.v(MessagingService.class.getSimpleName(), "Connect to GATT service of " + this.bluetoothDevice.getName());
        this.h = this.bluetoothDevice.connectGatt(this.g, true, new BluetoothGattCallback(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
                if (i == 0) {
                    String stringValue = bluetoothGattCharacteristic.getStringValue(0);
                    B4SLog.v(MessagingService.class.getSimpleName(), "Read success: " + stringValue);
                    if (this.a.j) {
                        this.a.k.append(stringValue);
                    } else {
                        this.a.j = true;
                        this.a.k = new StringBuilder(stringValue);
                    }
                    stringValue = new String(new Xtea(this.a.k.toString().getBytes(MessagingService.a), this.a.encryptionPassword).decrypt(), MessagingService.a);
                    if (stringValue.contains("END")) {
                        this.a.j = false;
                        ObjectType fromObjectCode = ObjectType.fromObjectCode(stringValue.substring(0, 3));
                        if (fromObjectCode == null) {
                            EventBus.getDefault().post(new MessagingException(ErrorCode.UnsupportedObject, "Received unsupported data stream '" + stringValue + "'", null));
                            return;
                        }
                        stringValue = stringValue.substring(3, stringValue.lastIndexOf("END"));
                        if (fromObjectCode == ObjectType.Name) {
                            this.a.receivedName = stringValue;
                        }
                        EventBus.getDefault().post(new ReceivedMessage(this.a, fromObjectCode, stringValue));
                    }
                }
            }

            public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
                if (i == 0) {
                    B4SLog.v(MessagingService.class.getSimpleName(), "Write success: " + bluetoothGattCharacteristic.getStringValue(0));
                    if (this.a.l) {
                        this.a.b();
                    }
                }
            }

            public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
                if (i2 == 2) {
                    this.a.h.discoverServices();
                }
            }

            public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                if (i == 0) {
                    BluetoothGattService service = this.a.h.getService(MessagingService.b);
                    if (service != null) {
                        B4SLog.v(MessagingService.class.getSimpleName(), "Fetched GATT services for " + this.a.bluetoothDevice.getName());
                        this.a.i = service.getCharacteristic(d.e);
                        if (this.a.receivedName == null) {
                            BluetoothGattCharacteristic characteristic = service.getCharacteristic(d.d);
                            if (characteristic != null) {
                                this.a.h.readCharacteristic(characteristic);
                            }
                        }
                        if (this.a.l) {
                            this.a.b();
                        }
                    }
                }
            }
        });
    }

    final void a() {
        this.h.close();
    }

    final void a(ObjectType objectType, String str) {
        if (this.l) {
            EventBus.getDefault().post(new PublishMessageException(ErrorCode.Busy, "Already sending data at the moment; only one message concurrently is supported", null, str));
        } else if (objectType == null || TextUtils.isEmpty(str)) {
            EventBus.getDefault().post(new PublishMessageException(ErrorCode.UnsupportedObject, "Cannot send empty messages to a device", null, str));
        } else {
            byte[] bytes = (objectType.getObjectCode() + str + "END").getBytes(MessagingService.a);
            this.l = true;
            this.m = new Xtea(bytes, this.encryptionPassword).encrypt();
            if (this.h == null || this.h.getServices() == null || this.i == null) {
                e();
            } else {
                b();
            }
        }
    }

    final void b() {
        int min = Math.min(this.m.length, 20);
        byte[] copyOfRange = Arrays.copyOfRange(this.m, 0, min);
        this.i.setValue(copyOfRange);
        this.h.writeCharacteristic(this.i);
        B4SLog.v(MessagingService.class.getSimpleName(), "Write success: " + new String(copyOfRange, MessagingService.a));
        if (this.m.length - min > 0) {
            this.m = Arrays.copyOfRange(this.m, min, this.m.length);
        } else {
            this.l = false;
        }
    }
}
