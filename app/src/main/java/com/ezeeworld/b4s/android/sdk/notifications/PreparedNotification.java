package com.ezeeworld.b4s.android.sdk.notifications;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

class PreparedNotification implements Parcelable {
    public static final Creator<PreparedNotification> CREATOR = new Creator<PreparedNotification>() {
        public final PreparedNotification a(Parcel parcel) {
            return new PreparedNotification(parcel);
        }

        public final PreparedNotification[] a(int i) {
            return new PreparedNotification[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public int a;
    public String b;
    public String c;
    public String d;
    public boolean e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public Intent l;
    public boolean m;

    private PreparedNotification(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readInt();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readInt() == 1;
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readString();
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.l = (Intent) parcel.readParcelable(Intent.class.getClassLoader());
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.m = z;
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
        parcel.writeInt(this.e ? 1 : 0);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeString(this.i);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeParcelable(this.l, i);
        if (!this.m) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
