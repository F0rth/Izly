package com.ezeeworld.b4s.android.sdk.playservices;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;

public final class AdvertisingServices {
    private Info advertisingInfo;
    private final Object advertisingInfoLock;

    static class Holder {
        static final AdvertisingServices INSTANCE = new AdvertisingServices();

        private Holder() {
        }
    }

    private AdvertisingServices() {
        this.advertisingInfoLock = new Object();
    }

    private boolean connect() {
        synchronized (this.advertisingInfoLock) {
            if (this.advertisingInfo == null) {
                try {
                    this.advertisingInfo = AdvertisingIdClient.getAdvertisingIdInfo(GoogleApi.get().getApplicationContext());
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return this.advertisingInfo != null;
    }

    public static AdvertisingServices get() {
        return Holder.INSTANCE;
    }

    public final String getIdentifier() {
        return connect() ? this.advertisingInfo.getId() : null;
    }

    public final Boolean getTrackingEnabled() {
        return connect() ? Boolean.valueOf(this.advertisingInfo.isLimitAdTrackingEnabled()) : null;
    }
}
