package com.ezeeworld.b4s.android.sdk.playservices.location;

import java.util.ArrayList;
import java.util.List;

public class Geofence {
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;
    private final com.google.android.gms.location.Geofence wrapped;

    public static class Builder implements com.google.android.gms.location.Geofence {
        private final com.google.android.gms.location.Geofence.Builder builder = new com.google.android.gms.location.Geofence.Builder();
        private String requestId;

        com.google.android.gms.location.Geofence build() {
            return this.builder.build();
        }

        public String getRequestId() {
            return this.requestId;
        }

        public Builder setCircularRegion(double d, double d2, float f) {
            this.builder.setCircularRegion(d, d2, f);
            return this;
        }

        public Builder setExpirationDuration(long j) {
            this.builder.setExpirationDuration(j);
            return this;
        }

        public Builder setNotificationResponsiveness(int i) {
            this.builder.setNotificationResponsiveness(i);
            return this;
        }

        public Builder setRequestId(String str) {
            this.requestId = str;
            this.builder.setRequestId(str);
            return this;
        }

        public Builder setTransitionTypes(int i) {
            this.builder.setTransitionTypes(i);
            return this;
        }
    }

    private Geofence(com.google.android.gms.location.Geofence geofence) {
        this.wrapped = geofence;
    }

    public static List<Geofence> wrapGeofences(List<com.google.android.gms.location.Geofence> list) {
        List<Geofence> arrayList = new ArrayList(list.size());
        for (com.google.android.gms.location.Geofence geofence : list) {
            arrayList.add(new Geofence(geofence));
        }
        return arrayList;
    }

    public String getRequestId() {
        return this.wrapped.getRequestId();
    }
}
