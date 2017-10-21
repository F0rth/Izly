package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class BinderWrapper implements Parcelable {
    public static final Creator<BinderWrapper> CREATOR = new Creator<BinderWrapper>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzan(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return zzbQ(i);
        }

        public final BinderWrapper zzan(Parcel parcel) {
            return new BinderWrapper(parcel);
        }

        public final BinderWrapper[] zzbQ(int i) {
            return new BinderWrapper[i];
        }
    };
    private IBinder zzakD;

    public BinderWrapper() {
        this.zzakD = null;
    }

    public BinderWrapper(IBinder iBinder) {
        this.zzakD = null;
        this.zzakD = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.zzakD = null;
        this.zzakD = parcel.readStrongBinder();
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.zzakD);
    }
}
