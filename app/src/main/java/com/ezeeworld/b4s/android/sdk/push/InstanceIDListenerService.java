package com.ezeeworld.b4s.android.sdk.push;

public class InstanceIDListenerService extends com.ezeeworld.b4s.android.sdk.playservices.gcm.InstanceIDListenerService {
    public void onTokenRefresh() {
        PushApi.get().renewTokenRegistration();
    }
}
