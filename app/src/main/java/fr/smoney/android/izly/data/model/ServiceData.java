package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ServiceData implements Parcelable {
    public static final Creator<ServiceData> CREATOR = new Creator<ServiceData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ServiceData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ServiceData[i];
        }
    };
    public int a;
    public boolean b;

    public ServiceData(int i, boolean z) {
        this.a = 1;
        this.b = false;
    }

    public ServiceData(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.b = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b ? 1 : 0);
    }
}
