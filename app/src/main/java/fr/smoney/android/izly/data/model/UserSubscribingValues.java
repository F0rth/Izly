package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class UserSubscribingValues implements Parcelable {
    public static final Creator<UserSubscribingValues> CREATOR = new Creator<UserSubscribingValues>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new UserSubscribingValues(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new UserSubscribingValues[i];
        }
    };
    public int a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public boolean k;
    public boolean l;
    public byte[] m;

    public UserSubscribingValues(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, boolean z, boolean z2, byte[] bArr) {
        this.a = i;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.f = str5;
        this.g = str6;
        this.h = str7;
        this.i = str8;
        this.j = str9;
        this.k = z;
        this.l = z2;
        this.m = bArr;
    }

    public UserSubscribingValues(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readInt();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readString();
        this.j = parcel.readString();
        this.k = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.l = z;
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.m = new byte[readInt];
            parcel.readByteArray(this.m);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeInt(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeString(this.i);
        parcel.writeString(this.j);
        parcel.writeInt(this.k ? 1 : 0);
        if (!this.l) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        if (this.m != null) {
            int length = this.m.length;
            parcel.writeInt(length);
            parcel.writeByteArray(this.m, 0, length);
            return;
        }
        parcel.writeInt(0);
    }
}
