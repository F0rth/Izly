package com.ezeeworld.b4s.android.sdk.playservices.gcm;

import android.os.Bundle;

public abstract class GcmListenerService extends com.google.android.gms.gcm.GcmListenerService {
    public abstract void onMessageReceived(String str, Bundle bundle);
}
