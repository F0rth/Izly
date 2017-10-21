package com.ezeeworld.b4s.android.sdk.positioning;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

class a implements ServiceConnection {
    final /* synthetic */ PositioningManager a;

    a(PositioningManager positioningManager) {
        this.a = positioningManager;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.a.g = (h) iBinder;
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.a.g = null;
    }
}
