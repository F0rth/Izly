package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.location.internal.ParcelableGeofence;

public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;

    public static final class Builder {
        private String zzEY = null;
        private int zzaNC = 0;
        private long zzaND = Long.MIN_VALUE;
        private short zzaNE = (short) -1;
        private double zzaNF;
        private double zzaNG;
        private float zzaNH;
        private int zzaNI = 0;
        private int zzaNJ = -1;

        public final Geofence build() {
            if (this.zzEY == null) {
                throw new IllegalArgumentException("Request ID not set.");
            } else if (this.zzaNC == 0) {
                throw new IllegalArgumentException("Transitions types not set.");
            } else if ((this.zzaNC & 4) != 0 && this.zzaNJ < 0) {
                throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
            } else if (this.zzaND == Long.MIN_VALUE) {
                throw new IllegalArgumentException("Expiration not set.");
            } else if (this.zzaNE == (short) -1) {
                throw new IllegalArgumentException("Geofence region not set.");
            } else if (this.zzaNI >= 0) {
                return new ParcelableGeofence(this.zzEY, this.zzaNC, (short) 1, this.zzaNF, this.zzaNG, this.zzaNH, this.zzaND, this.zzaNI, this.zzaNJ);
            } else {
                throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
            }
        }

        public final Builder setCircularRegion(double d, double d2, float f) {
            this.zzaNE = (short) 1;
            this.zzaNF = d;
            this.zzaNG = d2;
            this.zzaNH = f;
            return this;
        }

        public final Builder setExpirationDuration(long j) {
            if (j < 0) {
                this.zzaND = -1;
            } else {
                this.zzaND = SystemClock.elapsedRealtime() + j;
            }
            return this;
        }

        public final Builder setLoiteringDelay(int i) {
            this.zzaNJ = i;
            return this;
        }

        public final Builder setNotificationResponsiveness(int i) {
            this.zzaNI = i;
            return this;
        }

        public final Builder setRequestId(String str) {
            this.zzEY = str;
            return this;
        }

        public final Builder setTransitionTypes(int i) {
            this.zzaNC = i;
            return this;
        }
    }

    String getRequestId();
}
