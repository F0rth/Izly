package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DealsData implements Parcelable {
    public static final Creator<DealsData> CREATOR = new Creator<DealsData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new DealsData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new DealsData[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;

    public DealsData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
    }

    public DealsData(String str, String str2, String str3, String str4, String str5, String str6) {
        this.a = str;
        this.b = str3;
        this.c = str2;
        this.d = str4;
        this.e = str5;
        this.f = str6;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
    }
}
