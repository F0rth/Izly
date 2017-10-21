package com.ezeeworld.b4s.android.sdk.p2pmessaging;

public class PublishMessage {
    private final NearbyDevice a;
    private final String b;

    PublishMessage(NearbyDevice nearbyDevice, String str) {
        this.a = nearbyDevice;
        this.b = str;
    }

    public String getMessage() {
        return this.b;
    }

    public NearbyDevice getNearbyDevice() {
        return this.a;
    }
}
