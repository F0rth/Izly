package com.ezeeworld.b4s.android.sdk.p2pmessaging;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.text.TextUtils;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.p2pmessaging.MessagingException.ErrorCode;

import de.greenrobot.event.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@TargetApi(18)
final class a extends NearbyDevice {
    private BluetoothSocket a;
    private a b;
    private b c;
    private boolean d = false;
    private ByteArrayOutputStream e = null;
    private boolean f = false;
    private byte[] g = null;
    private String h;

    class a extends Thread {
        final /* synthetic */ a a;
        private InputStream b = null;

        public a(a aVar) {
            this.a = aVar;
            aVar.d = true;
            aVar.e = new ByteArrayOutputStream();
            if (aVar.f) {
                a();
            }
        }

        private boolean a(byte[] bArr) {
            try {
                this.a.a.getOutputStream().write(bArr);
                this.a.f = false;
                return true;
            } catch (Exception e) {
                EventBus.getDefault().post(new PublishMessageException(ErrorCode.CommunicationFailed, "Error while sending message to " + this.a.getBluetoothDevice().getName() + "; reconnecting now", e, this.a.h));
                this.a.c();
                return false;
            }
        }

        public void a() {
            if (this.a.f && a(this.a.g)) {
                B4SLog.v(MessagingService.class.getSimpleName(), "Write success: " + new String(this.a.g, MessagingService.a));
            }
        }

        public void b() {
            this.a.d = false;
            try {
                this.b.close();
            } catch (IOException e) {
            }
        }

        public void run() {
            byte[] bArr = new byte[1024];
            while (this.a.d) {
                try {
                    this.b = this.a.a.getInputStream();
                    if (this.b == null) {
                        b();
                        this.a.c();
                    } else if (this.b.available() != 0) {
                        while (this.b.available() > 0) {
                            this.a.e.write(bArr, 0, this.b.read(bArr));
                        }
                        String str = new String(new Xtea(this.a.e.toByteArray(), this.a.encryptionPassword).decrypt(), MessagingService.a);
                        B4SLog.v(MessagingService.class.getSimpleName(), "Read success: " + str);
                        if (str.contains("END")) {
                            ObjectType fromObjectCode = ObjectType.fromObjectCode(str.substring(0, 3));
                            if (fromObjectCode == null) {
                                EventBus.getDefault().post(new MessagingException(ErrorCode.UnsupportedObject, "Received unsupported data stream '" + str + "'", null));
                            } else {
                                str = str.substring(3, str.lastIndexOf("END"));
                                if (fromObjectCode == ObjectType.Name) {
                                    this.a.receivedName = str;
                                }
                                EventBus.getDefault().post(new ReceivedMessage(this.a, fromObjectCode, str));
                            }
                            this.a.e.close();
                            this.a.e = new ByteArrayOutputStream();
                        }
                    }
                } catch (Throwable e) {
                    EventBus.getDefault().post(new MessagingException(ErrorCode.CommunicationFailed, "Error while receiving message from " + this.a.getBluetoothDevice().getName() + "; reconnecting now", e));
                    this.a.c();
                }
            }
        }
    }

    class b extends Thread {
        final /* synthetic */ a a;
        private boolean b = true;

        public b(a aVar) {
            this.a = aVar;
            try {
                if (aVar.a.isConnected()) {
                    aVar.a.close();
                }
            } catch (IOException e) {
            }
            EventBus.getDefault().post(new c(aVar));
        }

        public void a() {
            this.b = false;
        }

        public void run() {
            while (this.b) {
                try {
                    this.a.a = this.a.bluetoothDevice.createInsecureRfcommSocketToServiceRecord(MessagingService.b);
                    this.a.a.connect();
                    EventBus.getDefault().post(new b(this.a));
                    this.b = false;
                    this.a.b();
                } catch (Throwable e) {
                    EventBus.getDefault().post(new MessagingException(ErrorCode.BluetoothUnavailable, "Bluetooth connection unavailable; will retry soon", e));
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e2) {
                }
            }
        }
    }

    public a(BluetoothDevice bluetoothDevice, BluetoothSocket bluetoothSocket, byte[] bArr) {
        super(bluetoothDevice, bArr);
        this.a = bluetoothSocket;
        b();
    }

    private void b() {
        this.b = new a(this);
        this.b.start();
    }

    private void c() {
        if (this.b != null) {
            this.b.b();
            this.b = null;
        }
        this.c = new b(this);
        this.c.start();
    }

    final void a() {
        try {
            this.a.close();
        } catch (IOException e) {
        }
        try {
            this.e.close();
        } catch (IOException e2) {
        }
        if (this.b != null) {
            this.b.b();
            this.b = null;
        }
        if (this.c != null) {
            this.c.a();
            this.c = null;
        }
    }

    final void a(ObjectType objectType, String str) {
        if (this.f) {
            EventBus.getDefault().post(new PublishMessageException(ErrorCode.Busy, "Already sending data at the moment; only one message concurrently is supported", null, str));
        } else if (objectType == null || TextUtils.isEmpty(str)) {
            EventBus.getDefault().post(new PublishMessageException(ErrorCode.UnsupportedObject, "Cannot send empty messages to a device", null, str));
        } else {
            byte[] bytes = (objectType.getObjectCode() + str + "END").getBytes(MessagingService.a);
            this.f = true;
            this.g = new Xtea(bytes, this.encryptionPassword).encrypt();
            this.h = str;
            if (this.b != null) {
                this.b.a();
            } else {
                c();
            }
        }
    }
}
