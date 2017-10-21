package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UpdatePasswordData implements Parcelable {
    public static final Creator<UpdatePasswordData> CREATOR = new Creator<UpdatePasswordData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new UpdatePasswordData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new UpdatePasswordData[i];
        }
    };
    public String a;

    public UpdatePasswordData(Parcel parcel) {
        this.a = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
    }
}
