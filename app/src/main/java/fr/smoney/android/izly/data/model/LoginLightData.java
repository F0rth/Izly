package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LoginLightData implements Parcelable {
    public static final Creator<LoginLightData> CREATOR = new Creator<LoginLightData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new LoginLightData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new LoginLightData[i];
        }
    };
    public String a;
    public int b;
    public BalanceData c;

    public LoginLightData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
        this.c = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
        this.c.writeToParcel(parcel, i);
    }
}
