package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UserData implements Parcelable {
    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new UserData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new UserData[i];
        }
    };
    public String a;
    public String b;

    public UserData() {
        this.a = "";
        this.b = "";
    }

    private UserData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
    }
}
