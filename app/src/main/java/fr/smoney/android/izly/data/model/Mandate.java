package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Mandate implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new Mandate(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Mandate[i];
        }
    };
    public String a;
    public String b;
    public String c;
    private String d;

    public Mandate(Parcel parcel) {
        this.a = parcel.readString();
        this.d = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
    }

    public Mandate(String str, String str2, String str3, String str4) {
        this.a = str;
        this.d = str2;
        this.b = str3;
        this.c = str4;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.d);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
    }
}
