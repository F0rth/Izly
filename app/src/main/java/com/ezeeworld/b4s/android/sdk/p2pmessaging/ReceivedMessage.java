package com.ezeeworld.b4s.android.sdk.p2pmessaging;

public class ReceivedMessage {
    private final NearbyDevice a;
    private final ObjectType b;
    private final String c;

    ReceivedMessage(NearbyDevice nearbyDevice, ObjectType objectType, String str) {
        this.a = nearbyDevice;
        this.b = objectType;
        this.c = str;
    }

    ObjectType a() {
        return this.b;
    }

    public String getMessage() {
        return this.c;
    }

    public NearbyDevice getNearbyDevice() {
        return this.a;
    }
}
