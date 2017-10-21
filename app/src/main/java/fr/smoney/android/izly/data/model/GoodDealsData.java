package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GoodDealsData implements Parcelable {
    public static final Creator<GoodDealsData> CREATOR = new Creator<GoodDealsData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GoodDealsData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GoodDealsData[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public String d;

    public GoodDealsData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
    }

    public GoodDealsData(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
    }
}
