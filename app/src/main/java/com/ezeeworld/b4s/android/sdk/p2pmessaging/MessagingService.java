package com.ezeeworld.b4s.android.sdk.p2pmessaging;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.BluetoothHelper;
import com.ezeeworld.b4s.android.sdk.p2pmessaging.MessagingException.ErrorCode;
import com.ezeeworld.b4s.android.sdk.server.Shop;

import de.greenrobot.event.EventBus;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

@TargetApi(18)
public class MessagingService extends Service {
    static final Charset a = Charset.forName("UTF-8");
    static final UUID b = UUID.fromString("38F87FEB-9D0D-4368-A837-7C854100647F");
    private static WeakReference<MessagingHandler> c;
    private Shop d;
    private BluetoothAdapter e;
    private Map<String, NearbyDevice> f;
    private Map<String, Long> g;
    private c h;
    private a i;
    private b j;
    private LeScanCallback k;

    public interface MessagingHandler {
        void deviceLeft(NearbyDevice nearbyDevice);

        void deviceNearby(NearbyDevice nearbyDevice);

        void messageReceived(NearbyDevice nearbyDevice, String str);

        void onMessagingException(MessagingException messagingException);
    }

    class a extends Thread {
        final /* synthetic */ MessagingService a;
        private BluetoothServerSocket b;
        private boolean c = true;

        public a(MessagingService messagingService) {
            this.a = messagingService;
        }

        private void b() {
            if (this.b != null) {
                try {
                    this.b.close();
                } catch (IOException e) {
                }
            }
            try {
                this.b = this.a.e.listenUsingInsecureRfcommWithServiceRecord(MessagingService.class.getSimpleName(), MessagingService.b);
            } catch (Throwable e2) {
                EventBus.getDefault().post(new MessagingException(ErrorCode.BluetoothUnavailable, "Bluetooth hardware not ready; will retry on shop re-entry", e2));
            }
        }

        public void a() {
            this.c = false;
            if (this.b != null) {
                try {
                    this.b.close();
                } catch (IOException e) {
                }
            }
        }

        public void run() {
            while (this.c) {
                try {
                    b();
                    if (this.b == null) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        BluetoothSocket accept = this.b.accept();
                        if (accept != null) {
                            BluetoothDevice remoteDevice = accept.getRemoteDevice();
                            B4SLog.v(this.a, "Device (Android) " + remoteDevice.getName() + " is nearby");
                            if (!this.a.f.containsKey(remoteDevice.getAddress())) {
                                NearbyDevice aVar = new a(remoteDevice, accept, this.a.d.sKey.getBytes(MessagingService.a));
                                this.a.f.put(remoteDevice.getAddress(), aVar);
                                EventBus.getDefault().post(new b(aVar));
                            }
                            b();
                        }
                    }
                } catch (Throwable e2) {
                    EventBus.getDefault().post(new MessagingException(ErrorCode.BluetoothUnavailable, "Bluetooth hardware couldn't accept device connection; reset and continue", e2));
                    b();
                }
            }
        }
    }

    class b extends Thread {
        final /* synthetic */ MessagingService a;
        private boolean b = true;

        public b(MessagingService messagingService) {
            this.a = messagingService;
        }

        public void a() {
            this.b = false;
        }

        public void run() {
            while (this.b && this.a.d.aMACs != null) {
                for (String str : this.a.d.aMACList) {
                    if (!this.b || this.a.f.containsKey(str)) {
                        break;
                    }
                    try {
                        BluetoothDevice remoteDevice = this.a.e.getRemoteDevice(str);
                        BluetoothSocket createInsecureRfcommSocketToServiceRecord = remoteDevice.createInsecureRfcommSocketToServiceRecord(MessagingService.b);
                        createInsecureRfcommSocketToServiceRecord.connect();
                        this.b = false;
                        NearbyDevice aVar = new a(remoteDevice, createInsecureRfcommSocketToServiceRecord, this.a.d.sKey.getBytes(MessagingService.a));
                        this.a.f.put(remoteDevice.getAddress(), aVar);
                        EventBus.getDefault().post(new b(aVar));
                        break;
                    } catch (IOException e) {
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e2) {
                }
            }
        }
    }

    class c extends Thread {
        final /* synthetic */ MessagingService a;
        private boolean b = true;

        public c(MessagingService messagingService) {
            this.a = messagingService;
        }

        public void a() {
            this.b = false;
        }

        public void run() {
            while (this.b) {
                for (Entry entry : this.a.g.entrySet()) {
                    if (((Long) entry.getValue()).longValue() < System.currentTimeMillis()) {
                        this.a.g.remove(entry.getKey());
                        EventBus.getDefault().post(new c((NearbyDevice) this.a.f.get(entry.getKey())));
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    static class d {
        private d() {
        }
    }

    private void a() {
        if (this.h != null) {
            this.h.a();
            this.h = null;
        }
        if (this.i != null) {
            this.i.a();
            this.i = null;
        }
        if (this.j != null) {
            this.j.a();
            this.j = null;
        }
        this.e.stopLeScan(this.k);
    }

    private void b() {
        if (this.j == null) {
            this.j = new b(this);
        }
        if (!this.j.isAlive()) {
            this.j.start();
        }
    }

    private void c() {
        if (this.i == null) {
            this.i = new a(this);
        }
        if (!this.i.isAlive()) {
            this.i.start();
        }
    }

    private void d() {
        this.k = new LeScanCallback(this) {
            final /* synthetic */ MessagingService a;

            {
                this.a = r1;
            }

            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                if (this.a.d != null && bluetoothDevice.getName() != null && bluetoothDevice.getName().startsWith(this.a.d.sSSID)) {
                    String address = bluetoothDevice.getAddress();
                    if (this.a.f.containsKey(address)) {
                        this.a.g.put(address, Long.valueOf(System.currentTimeMillis() + 10000));
                        return;
                    }
                    NearbyDevice dVar = new d(bluetoothDevice, this.a, this.a.d.sKey.getBytes(MessagingService.a));
                    this.a.f.put(address, dVar);
                    this.a.g.put(address, Long.valueOf(System.currentTimeMillis() + 10000));
                    EventBus.getDefault().post(new b(dVar));
                }
            }
        };
        this.e.startLeScan(this.k);
    }

    public static void enteredShop(Context context, Shop shop) {
        if (shop.sSSID != null && shop.aMACs != null) {
            context.startService(new Intent(context, MessagingService.class).putExtra("shop", shop));
        }
    }

    public static void exitedShop(Context context) {
        context.stopService(new Intent(context, MessagingService.class));
    }

    public static void registerMessagingHandler(MessagingHandler messagingHandler) {
        c = new WeakReference(messagingHandler);
        EventBus.getDefault().post(new d());
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        this.e = BluetoothHelper.getDefaultAdapter();
        EventBus.getDefault().register(this);
    }

    public void onDestroy() {
        B4SLog.d(MessagingService.class.getSimpleName(), "Messaging service ended");
        a();
        for (NearbyDevice a : this.f.values()) {
            a.a();
        }
        this.d = null;
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(MessagingException messagingException) {
        B4SLog.w(MessagingService.class.getSimpleName(), messagingException.getMessage());
        if (c != null && c.get() != null) {
            ((MessagingHandler) c.get()).onMessagingException(messagingException);
        }
    }

    public void onEventMainThread(d dVar) {
        if (c != null && c.get() != null && this.f != null) {
            for (NearbyDevice deviceNearby : this.f.values()) {
                ((MessagingHandler) c.get()).deviceNearby(deviceNearby);
            }
        }
    }

    public void onEventMainThread(PublishMessage publishMessage) {
        NearbyDevice nearbyDevice = publishMessage.getNearbyDevice();
        String message = publishMessage.getMessage();
        if (this.f.containsKey(nearbyDevice.getBluetoothDevice().getAddress())) {
            B4SLog.d((Object) this, "Sending message: " + message);
            nearbyDevice.a(ObjectType.Notification, message);
        } else if (c != null && c.get() != null) {
            EventBus.getDefault().post(new MessagingException(ErrorCode.DeviceNotNearby, "Device " + nearbyDevice.getBluetoothDevice().getName() + " is not nearby, so we cannot send message '" + message + "'", null));
        }
    }

    public void onEventMainThread(ReceivedMessage receivedMessage) {
        B4SLog.d((Object) this, "Received message from " + receivedMessage.getNearbyDevice().getBluetoothDevice().getName() + ": " + receivedMessage.getMessage());
        if (c != null && c.get() != null && !receivedMessage.a().isSystemMessage()) {
            ((MessagingHandler) c.get()).messageReceived(receivedMessage.getNearbyDevice(), receivedMessage.getMessage());
        }
    }

    public void onEventMainThread(b bVar) {
        NearbyDevice a = bVar.a();
        B4SLog.v((Object) this, "Device (" + (a instanceof a ? "Android" : "iOS") + ") " + a.getBluetoothDevice().getName() + " is nearby");
        if (c != null && c.get() != null) {
            ((MessagingHandler) c.get()).deviceNearby(a);
        }
    }

    public void onEventMainThread(c cVar) {
        NearbyDevice a = cVar.a();
        BluetoothDevice bluetoothDevice = a.getBluetoothDevice();
        this.f.remove(bluetoothDevice.getAddress());
        B4SLog.v((Object) this, "Device (" + (a instanceof a ? "Android" : "iOS") + ") " + bluetoothDevice.getName() + " is no longer nearby");
        if (c != null && c.get() != null) {
            ((MessagingHandler) c.get()).deviceLeft(a);
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null && intent.hasExtra("shop")) {
            Shop shop = (Shop) intent.getParcelableExtra("shop");
            if (this.d == null || !shop.sShopId.equals(this.d.sShopId)) {
                if (this.f != null) {
                    for (NearbyDevice a : this.f.values()) {
                        a.a();
                    }
                }
                this.d = shop;
                this.f = new HashMap();
                this.g = new HashMap();
            }
        }
        B4SLog.d(MessagingService.class.getSimpleName(), "Messaging service started for " + this.d.sName);
        a();
        this.h = new c(this);
        this.h.start();
        c();
        d();
        b();
        return 3;
    }
}
