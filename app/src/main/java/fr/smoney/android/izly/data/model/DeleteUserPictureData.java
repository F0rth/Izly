package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DeleteUserPictureData implements Parcelable {
    public static final Creator<DeleteUserPictureData> CREATOR = new Creator<DeleteUserPictureData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new DeleteUserPictureData();
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new DeleteUserPictureData[i];
        }
    };
    public BalanceData a = new BalanceData();

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
    }
}
