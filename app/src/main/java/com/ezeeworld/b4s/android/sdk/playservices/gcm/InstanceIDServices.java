package com.ezeeworld.b4s.android.sdk.playservices.gcm;

import com.ezeeworld.b4s.android.sdk.playservices.GoogleApi;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public final class InstanceIDServices {
    private InstanceID instanceID;

    static class Holder {
        static final InstanceIDServices INSTANCE = new InstanceIDServices();

        private Holder() {
        }
    }

    private InstanceIDServices() {
        this.instanceID = InstanceID.getInstance(GoogleApi.get().getApplicationContext());
    }

    public static InstanceIDServices get() {
        return Holder.INSTANCE;
    }

    public final String getToken(String str) {
        try {
            return this.instanceID.getToken(str, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
        } catch (IOException e) {
            return null;
        }
    }
}
