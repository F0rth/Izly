package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SetDeviceTokenData implements Parcelable {
    public static final Creator<SetDeviceTokenData> CREATOR = new Creator<SetDeviceTokenData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new SetDeviceTokenData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new SetDeviceTokenData[i];
        }
    };
    public String a;
    public BalanceData b;

    public SetDeviceTokenData() {
        this.b = new BalanceData();
    }

    public SetDeviceTokenData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        this.b.writeToParcel(parcel, i);
    }
}
