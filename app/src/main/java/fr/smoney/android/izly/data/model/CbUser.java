package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CbUser implements Parcelable {
    public static final Creator<CbUser> CREATOR = new Creator<CbUser>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CbUser(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CbUser[i];
        }
    };
    public String a;
    public String b;

    public CbUser(Parcel parcel) {
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
