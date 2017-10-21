package com.ad4screen.sdk.b;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.HashMap;

public class a implements Parcelable {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public final a a(Parcel parcel) {
            return new a(parcel);
        }

        public final a[] a(int i) {
            return new a[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public String a;
    public String b;
    public com.ad4screen.sdk.b.c.a c;
    public String d;
    public String e;
    public HashMap<String, String> f;

    private a(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = com.ad4screen.sdk.b.c.a.valueOf(parcel.readString());
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = new HashMap();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.f.put(parcel.readString(), parcel.readString());
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c.name());
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeInt(this.f.size());
        for (String str : this.f.keySet()) {
            parcel.writeString(str);
            parcel.writeString((String) this.f.get(str));
        }
    }
}
