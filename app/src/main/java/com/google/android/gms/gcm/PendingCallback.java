package com.google.android.gms.gcm;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PendingCallback implements Parcelable {
    public static final Creator<PendingCallback> CREATOR = new Creator<PendingCallback>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzeJ(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return zzhg(i);
        }

        public final PendingCallback zzeJ(Parcel parcel) {
            return new PendingCallback(parcel);
        }

        public final PendingCallback[] zzhg(int i) {
            return new PendingCallback[i];
        }
    };
    final IBinder zzakD;

    public PendingCallback(Parcel parcel) {
        this.zzakD = parcel.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public IBinder getIBinder() {
        return this.zzakD;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.zzakD);
    }
}
