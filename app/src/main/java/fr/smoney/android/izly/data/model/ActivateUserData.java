package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ActivateUserData implements Parcelable {
    public static final Creator<ActivateUserData> CREATOR = new Creator<ActivateUserData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ActivateUserData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ActivateUserData[i];
        }
    };
    public int a;
    public String b;
    public String c;

    public ActivateUserData(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readString();
        this.c = parcel.readString();
    }

    public ActivateUserData(String str, String str2, int i) {
        this.c = str;
        this.b = str2;
        this.a = i;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
    }
}
