package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzk implements Callback {
    private final Handler mHandler;
    private final zza zzalQ;
    private final ArrayList<ConnectionCallbacks> zzalR = new ArrayList();
    final ArrayList<ConnectionCallbacks> zzalS = new ArrayList();
    private final ArrayList<OnConnectionFailedListener> zzalT = new ArrayList();
    private volatile boolean zzalU = false;
    private final AtomicInteger zzalV = new AtomicInteger(0);
    private boolean zzalW = false;
    private final Object zzpV = new Object();

    public interface zza {
        boolean isConnected();

        Bundle zzoi();
    }

    public zzk(Looper looper, zza com_google_android_gms_common_internal_zzk_zza) {
        this.zzalQ = com_google_android_gms_common_internal_zzk_zza;
        this.mHandler = new Handler(looper, this);
    }

    public final boolean handleMessage(Message message) {
        if (message.what == 1) {
            ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) message.obj;
            synchronized (this.zzpV) {
                if (this.zzalU && this.zzalQ.isConnected() && this.zzalR.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(this.zzalQ.zzoi());
                }
            }
            return true;
        }
        Log.wtf("GmsClientEvents", "Don't know how to handle message: " + message.what, new Exception());
        return false;
    }

    public final boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks) {
        boolean contains;
        zzx.zzz(connectionCallbacks);
        synchronized (this.zzpV) {
            contains = this.zzalR.contains(connectionCallbacks);
        }
        return contains;
    }

    public final boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener) {
        boolean contains;
        zzx.zzz(onConnectionFailedListener);
        synchronized (this.zzpV) {
            contains = this.zzalT.contains(onConnectionFailedListener);
        }
        return contains;
    }

    public final void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        zzx.zzz(connectionCallbacks);
        synchronized (this.zzpV) {
            if (this.zzalR.contains(connectionCallbacks)) {
                Log.w("GmsClientEvents", "registerConnectionCallbacks(): listener " + connectionCallbacks + " is already registered");
            } else {
                this.zzalR.add(connectionCallbacks);
            }
        }
        if (this.zzalQ.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, connectionCallbacks));
        }
    }

    public final void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        zzx.zzz(onConnectionFailedListener);
        synchronized (this.zzpV) {
            if (this.zzalT.contains(onConnectionFailedListener)) {
                Log.w("GmsClientEvents", "registerConnectionFailedListener(): listener " + onConnectionFailedListener + " is already registered");
            } else {
                this.zzalT.add(onConnectionFailedListener);
            }
        }
    }

    public final void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        zzx.zzz(connectionCallbacks);
        synchronized (this.zzpV) {
            if (!this.zzalR.remove(connectionCallbacks)) {
                Log.w("GmsClientEvents", "unregisterConnectionCallbacks(): listener " + connectionCallbacks + " not found");
            } else if (this.zzalW) {
                this.zzalS.add(connectionCallbacks);
            }
        }
    }

    public final void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        zzx.zzz(onConnectionFailedListener);
        synchronized (this.zzpV) {
            if (!this.zzalT.remove(onConnectionFailedListener)) {
                Log.w("GmsClientEvents", "unregisterConnectionFailedListener(): listener " + onConnectionFailedListener + " not found");
            }
        }
    }

    public final void zzbT(int i) {
        boolean z = false;
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            z = true;
        }
        zzx.zza(z, (Object) "onUnintentionalDisconnection must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.zzpV) {
            this.zzalW = true;
            ArrayList arrayList = new ArrayList(this.zzalR);
            int i2 = this.zzalV.get();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) it.next();
                if (this.zzalU && this.zzalV.get() == i2) {
                    if (this.zzalR.contains(connectionCallbacks)) {
                        connectionCallbacks.onConnectionSuspended(i);
                    }
                }
            }
            this.zzalS.clear();
            this.zzalW = false;
        }
    }

    public final void zzk(Bundle bundle) {
        boolean z = true;
        zzx.zza(Looper.myLooper() == this.mHandler.getLooper(), (Object) "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.zzpV) {
            zzx.zzab(!this.zzalW);
            this.mHandler.removeMessages(1);
            this.zzalW = true;
            if (this.zzalS.size() != 0) {
                z = false;
            }
            zzx.zzab(z);
            ArrayList arrayList = new ArrayList(this.zzalR);
            int i = this.zzalV.get();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) it.next();
                if (this.zzalU && this.zzalQ.isConnected() && this.zzalV.get() == i) {
                    if (!this.zzalS.contains(connectionCallbacks)) {
                        connectionCallbacks.onConnected(bundle);
                    }
                }
            }
            this.zzalS.clear();
            this.zzalW = false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzk(com.google.android.gms.common.ConnectionResult r6) {
        /*
        r5 = this;
        r1 = 1;
        r0 = android.os.Looper.myLooper();
        r2 = r5.mHandler;
        r2 = r2.getLooper();
        if (r0 != r2) goto L_0x0057;
    L_0x000d:
        r0 = r1;
    L_0x000e:
        r2 = "onConnectionFailure must only be called on the Handler thread";
        com.google.android.gms.common.internal.zzx.zza(r0, r2);
        r0 = r5.mHandler;
        r0.removeMessages(r1);
        r1 = r5.zzpV;
        monitor-enter(r1);
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0052 }
        r2 = r5.zzalT;	 Catch:{ all -> 0x0052 }
        r0.<init>(r2);	 Catch:{ all -> 0x0052 }
        r2 = r5.zzalV;	 Catch:{ all -> 0x0052 }
        r2 = r2.get();	 Catch:{ all -> 0x0052 }
        r3 = r0.iterator();	 Catch:{ all -> 0x0052 }
    L_0x002c:
        r0 = r3.hasNext();	 Catch:{ all -> 0x0052 }
        if (r0 == 0) goto L_0x0055;
    L_0x0032:
        r0 = r3.next();	 Catch:{ all -> 0x0052 }
        r0 = (com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener) r0;	 Catch:{ all -> 0x0052 }
        r4 = r5.zzalU;	 Catch:{ all -> 0x0052 }
        if (r4 == 0) goto L_0x0044;
    L_0x003c:
        r4 = r5.zzalV;	 Catch:{ all -> 0x0052 }
        r4 = r4.get();	 Catch:{ all -> 0x0052 }
        if (r4 == r2) goto L_0x0046;
    L_0x0044:
        monitor-exit(r1);	 Catch:{ all -> 0x0052 }
    L_0x0045:
        return;
    L_0x0046:
        r4 = r5.zzalT;	 Catch:{ all -> 0x0052 }
        r4 = r4.contains(r0);	 Catch:{ all -> 0x0052 }
        if (r4 == 0) goto L_0x002c;
    L_0x004e:
        r0.onConnectionFailed(r6);	 Catch:{ all -> 0x0052 }
        goto L_0x002c;
    L_0x0052:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0052 }
        throw r0;
    L_0x0055:
        monitor-exit(r1);	 Catch:{ all -> 0x0052 }
        goto L_0x0045;
    L_0x0057:
        r0 = 0;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzk.zzk(com.google.android.gms.common.ConnectionResult):void");
    }

    public final void zzqQ() {
        this.zzalU = false;
        this.zzalV.incrementAndGet();
    }

    public final void zzqR() {
        this.zzalU = true;
    }
}
