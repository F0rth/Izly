package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class P2PPayCommerceInfos implements Parcelable {
    public static final Creator<P2PPayCommerceInfos> CREATOR = new Creator<P2PPayCommerceInfos>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PPayCommerceInfos(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PPayCommerceInfos[i];
        }
    };
    public String a;
    public double b;
    public String c = "";
    public String d = "";
    public String e = "";
    public long f;
    public String g = "";
    public String h = "";
    public String i = "";
    public String j = "";
    public String k = "";
    public String l = "";
    public String m = "";
    public boolean n;
    public boolean o;
    public boolean p;
    public int q;
    public boolean r;

    public P2PPayCommerceInfos(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readString();
        this.b = parcel.readDouble();
        this.e = parcel.readString();
        this.d = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readString();
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.l = parcel.readString();
        this.m = parcel.readString();
        this.n = parcel.readInt() == 1;
        this.o = parcel.readInt() == 1;
        this.p = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.r = z;
        this.q = parcel.readInt();
        this.f = parcel.readLong();
    }

    public final boolean a() {
        return this.n || this.o || this.p;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.a);
        parcel.writeDouble(this.b);
        parcel.writeString(this.e);
        parcel.writeString(this.d);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeString(this.i);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeString(this.m);
        parcel.writeInt(this.n ? 1 : 0);
        parcel.writeInt(this.o ? 1 : 0);
        parcel.writeInt(this.p ? 1 : 0);
        if (!this.r) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.q);
        parcel.writeLong(this.f);
    }
}
