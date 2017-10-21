package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class RemoveDeviceTokenData implements Parcelable {
    public static final Creator<RemoveDeviceTokenData> CREATOR = new Creator<RemoveDeviceTokenData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new RemoveDeviceTokenData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new RemoveDeviceTokenData[i];
        }
    };
    public BalanceData a;

    public RemoveDeviceTokenData() {
        this.a = new BalanceData();
    }

    public RemoveDeviceTokenData(Parcel parcel) {
        this.a = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
    }
}
