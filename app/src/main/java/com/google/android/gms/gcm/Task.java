package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.zzx;

public abstract class Task implements Parcelable {
    public static final int EXTRAS_LIMIT_BYTES = 10240;
    public static final int NETWORK_STATE_ANY = 2;
    public static final int NETWORK_STATE_CONNECTED = 0;
    public static final int NETWORK_STATE_UNMETERED = 1;
    protected static final long UNINITIALIZED = -1;
    private final Bundle mExtras;
    private final String mTag;
    private final String zzaMh;
    private final boolean zzaMi;
    private final boolean zzaMj;
    private final int zzaMk;
    private final boolean zzaMl;
    private final zzd zzaMm;

    public static abstract class Builder {
        protected Bundle extras;
        protected String gcmTaskService;
        protected boolean isPersisted;
        protected int requiredNetworkState;
        protected boolean requiresCharging;
        protected String tag;
        protected boolean updateCurrent;
        protected zzd zzaMn = zzd.zzaMc;

        public abstract Task build();

        protected void checkConditions() {
            zzx.zzb(this.gcmTaskService != null, (Object) "Must provide an endpoint for this task by calling setService(ComponentName).");
            GcmNetworkManager.zzdT(this.tag);
            Task.zza(this.zzaMn);
            if (this.isPersisted) {
                Task.zzG(this.extras);
            }
        }

        public abstract Builder setExtras(Bundle bundle);

        public abstract Builder setPersisted(boolean z);

        public abstract Builder setRequiredNetwork(int i);

        public abstract Builder setRequiresCharging(boolean z);

        public abstract Builder setService(Class<? extends GcmTaskService> cls);

        public abstract Builder setTag(String str);

        public abstract Builder setUpdateCurrent(boolean z);
    }

    @Deprecated
    Task(Parcel parcel) {
        boolean z = true;
        Log.e("Task", "Constructing a Task object using a parcel.");
        this.zzaMh = parcel.readString();
        this.mTag = parcel.readString();
        this.zzaMi = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.zzaMj = z;
        this.zzaMk = 2;
        this.zzaMl = false;
        this.zzaMm = zzd.zzaMc;
        this.mExtras = null;
    }

    Task(Builder builder) {
        this.zzaMh = builder.gcmTaskService;
        this.mTag = builder.tag;
        this.zzaMi = builder.updateCurrent;
        this.zzaMj = builder.isPersisted;
        this.zzaMk = builder.requiredNetworkState;
        this.zzaMl = builder.requiresCharging;
        this.zzaMm = builder.zzaMn;
        this.mExtras = builder.extras;
    }

    private static boolean zzD(Object obj) {
        return (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof String) || (obj instanceof Boolean);
    }

    public static void zzG(Bundle bundle) {
        if (bundle != null) {
            Parcel obtain = Parcel.obtain();
            bundle.writeToParcel(obtain, 0);
            int dataSize = obtain.dataSize();
            if (dataSize > EXTRAS_LIMIT_BYTES) {
                obtain.recycle();
                throw new IllegalArgumentException("Extras exceeding maximum size(10240 bytes): " + dataSize);
            }
            obtain.recycle();
            for (String str : bundle.keySet()) {
                if (!zzD(bundle.get(str))) {
                    throw new IllegalArgumentException("Only the following extra parameter types are supported: Integer, Long, Double, String, and Boolean. ");
                }
            }
        }
    }

    public static void zza(zzd com_google_android_gms_gcm_zzd) {
        if (com_google_android_gms_gcm_zzd != null) {
            int zzym = com_google_android_gms_gcm_zzd.zzym();
            if (zzym == 1 || zzym == 0) {
                int zzyn = com_google_android_gms_gcm_zzd.zzyn();
                int zzyo = com_google_android_gms_gcm_zzd.zzyo();
                if (zzym == 0 && zzyn < 0) {
                    throw new IllegalArgumentException("InitialBackoffSeconds can't be negative: " + zzyn);
                } else if (zzym == 1 && zzyn < 10) {
                    throw new IllegalArgumentException("RETRY_POLICY_LINEAR must have an initial backoff at least 10 seconds.");
                } else if (zzyo < zzyn) {
                    throw new IllegalArgumentException("MaximumBackoffSeconds must be greater than InitialBackoffSeconds: " + com_google_android_gms_gcm_zzd.zzyo());
                } else {
                    return;
                }
            }
            throw new IllegalArgumentException("Must provide a valid RetryPolicy: " + zzym);
        }
    }

    public int describeContents() {
        return 0;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getRequiredNetwork() {
        return this.zzaMk;
    }

    public boolean getRequiresCharging() {
        return this.zzaMl;
    }

    public String getServiceName() {
        return this.zzaMh;
    }

    public String getTag() {
        return this.mTag;
    }

    public boolean isPersisted() {
        return this.zzaMj;
    }

    public boolean isUpdateCurrent() {
        return this.zzaMi;
    }

    public void toBundle(Bundle bundle) {
        bundle.putString("tag", this.mTag);
        bundle.putBoolean("update_current", this.zzaMi);
        bundle.putBoolean("persisted", this.zzaMj);
        bundle.putString("service", this.zzaMh);
        bundle.putInt("requiredNetwork", this.zzaMk);
        bundle.putBoolean("requiresCharging", this.zzaMl);
        bundle.putBundle("retryStrategy", this.zzaMm.zzF(new Bundle()));
        bundle.putBundle("extras", this.mExtras);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.zzaMh);
        parcel.writeString(this.mTag);
        parcel.writeInt(this.zzaMi ? 1 : 0);
        if (!this.zzaMj) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
