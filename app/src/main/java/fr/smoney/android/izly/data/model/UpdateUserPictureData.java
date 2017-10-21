package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UpdateUserPictureData implements Parcelable {
    public static final Creator<UpdateUserPictureData> CREATOR = new Creator<UpdateUserPictureData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new UpdateUserPictureData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new UpdateUserPictureData[i];
        }
    };
    public BalanceData a;

    public UpdateUserPictureData() {
        this.a = new BalanceData();
    }

    public UpdateUserPictureData(Parcel parcel) {
        this.a = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
    }
}
