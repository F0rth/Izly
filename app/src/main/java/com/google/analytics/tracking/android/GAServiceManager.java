package com.google.analytics.tracking.android;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.google.analytics.tracking.android.GAUsage.Field;
import com.google.android.gms.common.util.VisibleForTesting;

public class GAServiceManager extends ServiceManager {
    private static final int MSG_KEY = 1;
    private static final Object MSG_OBJECT = new Object();
    private static GAServiceManager instance;
    private boolean connected = true;
    private Context ctx;
    private int dispatchPeriodInSeconds = 1800;
    private Handler handler;
    private boolean listenForNetwork = true;
    private AnalyticsStoreStateListener listener = new AnalyticsStoreStateListener() {
        public void reportStoreIsEmpty(boolean z) {
            GAServiceManager.this.updatePowerSaveMode(z, GAServiceManager.this.connected);
        }
    };
    private GANetworkReceiver networkReceiver;
    private boolean pendingDispatch = true;
    private boolean pendingForceLocalDispatch;
    private String pendingHostOverride;
    private AnalyticsStore store;
    private boolean storeIsEmpty = false;
    private volatile AnalyticsThread thread;

    private GAServiceManager() {
    }

    @VisibleForTesting
    GAServiceManager(Context context, AnalyticsThread analyticsThread, AnalyticsStore analyticsStore, boolean z) {
        this.store = analyticsStore;
        this.thread = analyticsThread;
        this.listenForNetwork = z;
        initialize(context, analyticsThread);
    }

    @VisibleForTesting
    static void clearInstance() {
        instance = null;
    }

    public static GAServiceManager getInstance() {
        if (instance == null) {
            instance = new GAServiceManager();
        }
        return instance;
    }

    private void initializeHandler() {
        this.handler = new Handler(this.ctx.getMainLooper(), new Callback() {
            public boolean handleMessage(Message message) {
                if (1 == message.what && GAServiceManager.MSG_OBJECT.equals(message.obj)) {
                    GAUsage.getInstance().setDisableUsage(true);
                    GAServiceManager.this.dispatchLocalHits();
                    GAUsage.getInstance().setDisableUsage(false);
                    if (GAServiceManager.this.dispatchPeriodInSeconds > 0 && !GAServiceManager.this.storeIsEmpty) {
                        GAServiceManager.this.handler.sendMessageDelayed(GAServiceManager.this.handler.obtainMessage(1, GAServiceManager.MSG_OBJECT), (long) (GAServiceManager.this.dispatchPeriodInSeconds * 1000));
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
        this.networkReceiver = new GANetworkReceiver(this);
        this.networkReceiver.register(this.ctx);
    }

    @Deprecated
    public void dispatchLocalHits() {
        synchronized (this) {
            if (this.thread == null) {
                Log.v("Dispatch call queued. Dispatch will run once initialization is complete.");
                this.pendingDispatch = true;
            } else {
                GAUsage.getInstance().setUsage(Field.DISPATCH);
                this.thread.dispatch();
            }
        }
    }

    @VisibleForTesting
    AnalyticsStoreStateListener getListener() {
        return this.listener;
    }

    AnalyticsStore getStore() {
        AnalyticsStore analyticsStore;
        synchronized (this) {
            if (this.store == null) {
                if (this.ctx == null) {
                    throw new IllegalStateException("Cant get a store unless we have a context");
                }
                this.store = new PersistentAnalyticsStore(this.listener, this.ctx);
                if (this.pendingHostOverride != null) {
                    this.store.getDispatcher().overrideHostUrl(this.pendingHostOverride);
                    this.pendingHostOverride = null;
                }
            }
            if (this.handler == null) {
                initializeHandler();
            }
            if (this.networkReceiver == null && this.listenForNetwork) {
                initializeNetworkReceiver();
            }
            analyticsStore = this.store;
        }
        return analyticsStore;
    }

    void initialize(Context context, AnalyticsThread analyticsThread) {
        synchronized (this) {
            if (this.ctx == null) {
                this.ctx = context.getApplicationContext();
                if (this.thread == null) {
                    this.thread = analyticsThread;
                    if (this.pendingDispatch) {
                        dispatchLocalHits();
                        this.pendingDispatch = false;
                    }
                    if (this.pendingForceLocalDispatch) {
                        setForceLocalDispatch();
                        this.pendingForceLocalDispatch = false;
                    }
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

    @VisibleForTesting
    void overrideHostUrl(String str) {
        synchronized (this) {
            if (this.store == null) {
                this.pendingHostOverride = str;
            } else {
                this.store.getDispatcher().overrideHostUrl(str);
            }
        }
    }

    @Deprecated
    public void setForceLocalDispatch() {
        if (this.thread == null) {
            Log.v("setForceLocalDispatch() queued. It will be called once initialization is complete.");
            this.pendingForceLocalDispatch = true;
            return;
        }
        GAUsage.getInstance().setUsage(Field.SET_FORCE_LOCAL_DISPATCH);
        this.thread.setForceLocalDispatch();
    }

    @Deprecated
    public void setLocalDispatchPeriod(int i) {
        synchronized (this) {
            if (this.handler == null) {
                Log.v("Dispatch period set with null handler. Dispatch will run once initialization is complete.");
                this.dispatchPeriodInSeconds = i;
            } else {
                GAUsage.getInstance().setUsage(Field.SET_DISPATCH_PERIOD);
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
