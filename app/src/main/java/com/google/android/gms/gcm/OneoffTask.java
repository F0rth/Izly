package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;

public class OneoffTask extends Task {
    public static final Creator<OneoffTask> CREATOR = new Creator<OneoffTask>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzeI(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return zzhf(i);
        }

        public final OneoffTask zzeI(Parcel parcel) {
            return new OneoffTask(parcel);
        }

        public final OneoffTask[] zzhf(int i) {
            return new OneoffTask[i];
        }
    };
    private final long zzaLW;
    private final long zzaLX;

    public static class Builder extends com.google.android.gms.gcm.Task.Builder {
        private long zzaLY;
        private long zzaLZ;

        public Builder() {
            this.zzaLY = -1;
            this.zzaLZ = -1;
            this.isPersisted = false;
        }

        public OneoffTask build() {
            checkConditions();
            return new OneoffTask();
        }

        protected void checkConditions() {
            super.checkConditions();
            if (this.zzaLY == -1 || this.zzaLZ == -1) {
                throw new IllegalArgumentException("Must specify an execution window using setExecutionWindow.");
            } else if (this.zzaLY >= this.zzaLZ) {
                throw new IllegalArgumentException("Window start must be shorter than window end.");
            }
        }

        public Builder setExecutionWindow(long j, long j2) {
            this.zzaLY = j;
            this.zzaLZ = j2;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.extras = bundle;
            return this;
        }

        public Builder setPersisted(boolean z) {
            this.isPersisted = z;
            return this;
        }

        public Builder setRequiredNetwork(int i) {
            this.requiredNetworkState = i;
            return this;
        }

        public Builder setRequiresCharging(boolean z) {
            this.requiresCharging = z;
            return this;
        }

        public Builder setService(Class<? extends GcmTaskService> cls) {
            this.gcmTaskService = cls.getName();
            return this;
        }

        public Builder setTag(String str) {
            this.tag = str;
            return this;
        }

        public Builder setUpdateCurrent(boolean z) {
            this.updateCurrent = z;
            return this;
        }
    }

    @Deprecated
    private OneoffTask(Parcel parcel) {
        super(parcel);
        this.zzaLW = parcel.readLong();
        this.zzaLX = parcel.readLong();
    }

    private OneoffTask(Builder builder) {
        super((com.google.android.gms.gcm.Task.Builder) builder);
        this.zzaLW = builder.zzaLY;
        this.zzaLX = builder.zzaLZ;
    }

    public long getWindowEnd() {
        return this.zzaLX;
    }

    public long getWindowStart() {
        return this.zzaLW;
    }

    public void toBundle(Bundle bundle) {
        super.toBundle(bundle);
        bundle.putLong("window_start", this.zzaLW);
        bundle.putLong("window_end", this.zzaLX);
    }

    public String toString() {
        return super.toString() + " windowStart=" + getWindowStart() + " windowEnd=" + getWindowEnd();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeLong(this.zzaLW);
        parcel.writeLong(this.zzaLX);
    }
}
