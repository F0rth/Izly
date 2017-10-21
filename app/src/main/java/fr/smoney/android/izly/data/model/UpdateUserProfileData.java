package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UpdateUserProfileData implements Parcelable {
    public static final Creator<UpdateUserProfileData> CREATOR = new Creator<UpdateUserProfileData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new UpdateUserProfileData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new UpdateUserProfileData[i];
        }
    };
    public BalanceData a;
    public int b;

    public UpdateUserProfileData() {
        this.a = new BalanceData();
    }

    public UpdateUserProfileData(Parcel parcel) {
        this.a = new BalanceData(parcel);
        this.b = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        parcel.writeInt(this.b);
    }
}
