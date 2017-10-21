package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class OAuthData implements Parcelable {
    public static final Creator<OAuthData> CREATOR = new 1();
    public String a;
    public String b;
    public long c;

    public OAuthData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readLong();
    }

    public OAuthData(String str, String str2, long j) {
        this.a = str;
        this.b = str2;
        this.c = j;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeLong(this.c);
    }
}
