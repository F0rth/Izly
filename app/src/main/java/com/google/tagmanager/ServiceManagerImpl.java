package com.google.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.google.android.gms.common.util.VisibleForTesting;

class ServiceManagerImpl extends ServiceManager {
    private static final int MSG_KEY = 1;
    private static final Object MSG_OBJECT = new Object();
    private static ServiceManagerImpl instance;
    private boolean connected = true;
    private Context ctx;
    private int dispatchPeriodInSeconds = 1800;
    private Handler handler;
    private boolean listenForNetwork = true;
    private HitStoreStateListener listener = new HitStoreStateListener() {
        public void reportStoreIsEmpty(boolean z) {
            ServiceManagerImpl.this.updatePowerSaveMode(z, ServiceManagerImpl.this.connected);
        }
    };
    private NetworkReceiver networkReceiver;
    private boolean pendingDispatch = true;
    private boolean readyToDispatch = false;
    private HitStore store;
    private boolean storeIsEmpty = false;
    private volatile HitSendingThread thread;

    private ServiceManagerImpl() {
    }

    @VisibleForTesting
    ServiceManagerImpl(Context context, HitSendingThread hitSendingThread, HitStore hitStore, boolean z) {
        this.store = hitStore;
        this.thread = hitSendingThread;
        this.listenForNetwork = z;
        initialize(context, hitSendingThread);
    }

    @VisibleForTesting
    static void clearInstance() {
        instance = null;
    }

    public static ServiceManagerImpl getInstance() {
        if (instance == null) {
            instance = new ServiceManagerImpl();
        }
        return instance;
    }

    private void initializeHandler() {
        this.handler = new Handler(this.ctx.getMainLooper(), new Callback() {
            public boolean handleMessage(Message message) {
                if (1 == message.what && ServiceManagerImpl.MSG_OBJECT.equals(message.obj)) {
                    ServiceManagerImpl.this.dispatch();
                    if (ServiceManagerImpl.this.dispatchPeriodInSeconds > 0 && !ServiceManagerImpl.this.storeIsEmpty) {
                        ServiceManagerImpl.this.handler.sendMessageDelayed(ServiceManagerImpl.this.handler.obtainMessage(1, ServiceManagerImpl.MSG_OBJECT), (long) (ServiceManagerImpl.this.dispatchPeriodInSeconds * 1000));
                    }
                }
                return true;
            }
        });
        if (this.dispatchPeriodInSeconds > 0) {
            this.handler.sendMessageDelayed(this.handler.obtainMessage(1, MSG_OBJECT), (long) (this.dispatchPeriodInSeconds * 1000));
        }
    }

    private void initializeNetworkReceiver() {
        this.networkReceiver = new NetworkReceiver(this);
        this.networkReceiver.register(this.ctx);
    }

    public void dispatch() {
        synchronized (this) {
            if (this.readyToDispatch) {
                this.thread.queueToThread(new Runnable() {
                    public void run() {
                        ServiceManagerImpl.this.store.dispatch();
                    }
                });
            } else {
                Log.v("Dispatch call queued. Dispatch will run once initialization is complete.");
                this.pendingDispatch = true;
            }
        }
    }

    @VisibleForTesting
    HitStoreStateListener getListener() {
        return this.listener;
    }

    HitStore getStore() {
        HitStore hitStore;
        synchronized (this) {
            if (this.store == null) {
                if (this.ctx == null) {
                    throw new IllegalStateException("Cant get a store unless we have a context");
                }
                this.store = new PersistentHitStore(this.listener, this.ctx);
            }
            if (this.handler == null) {
                initializeHandler();
            }
            this.readyToDispatch = true;
            if (this.pendingDispatch) {
                dispatch();
                this.pendingDispatch = false;
            }
            if (this.networkReceiver == null && this.listenForNetwork) {
                initializeNetworkReceiver();
            }
            hitStore = this.store;
        }
        return hitStore;
    }

    void initialize(Context context, HitSendingThread hitSendingThread) {
        synchronized (this) {
            if (this.ctx == null) {
                this.ctx = context.getApplicationContext();
                if (this.thread == null) {
                    this.thread = hitSendingThread;
                }
            }
        }
    }

    void onRadioPowered() {
        synchronized (this) {
            if (!this.storeIsEmpty && this.connected && this.dispatchPeriodInSeconds > 0) {
                this.handler.removeMessages(1, MSG_OBJECT);
                this.handler.sendMessage(this.handler.obtainMessage(1, MSG_OBJECT));
            }
        }
    }

    public void setDispatchPeriod(int i) {
        synchronized (this) {
            if (this.handler == null) {
                Log.v("Dispatch period set with null handler. Dispatch will run once initialization is complete.");
                this.dispatchPeriodInSeconds = i;
            } else {
                if (!this.storeIsEmpty && this.connected && this.dispatchPeriodInSeconds > 0) {
                    this.handler.removeMessages(1, MSG_OBJECT);
                }
                this.dispatchPeriodInSeconds = i;
                if (i > 0 && !this.storeIsEmpty && this.connected) {
                    this.handler.sendMessageDelayed(this.handler.obtainMessage(1, MSG_OBJECT), (long) (i * 1000));
                }
            }
        }
    }

    void updateConnectivityStatus(boolean z) {
        synchronized (this) {
            updatePowerSaveMode(this.storeIsEmpty, z);
        }
    }

    @VisibleForTesting
    void updatePowerSaveMode(boolean z, boolean z2) {
        synchronized (this) {
            if (!(this.storeIsEmpty == z && this.connected == z2)) {
                if (z || !z2) {
                    if (this.dispatchPeriodInSeconds > 0) {
                        this.handler.removeMessages(1, MSG_OBJECT);
                    }
                }
                if (!z && z2 && this.dispatchPeriodInSeconds > 0) {
                    this.handler.sendMessageDelayed(this.handler.obtainMessage(1, MSG_OBJECT), (long) (this.dispatchPeriodInSeconds * 1000));
                }
                StringBuilder stringBuilder = new StringBuilder("PowerSaveMode ");
                String str = (z || !z2) ? "initiated." : "terminated.";
                Log.v(stringBuilder.append(str).toString());
                this.storeIsEmpty = z;
                this.connected = z2;
            }
        }
    }
}
