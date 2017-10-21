package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class P2PPay implements Parcelable {
    public static final Creator<P2PPay> CREATOR = new Creator<P2PPay>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PPay(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PPay[i];
        }
    };
    public long a;
    public long b;
    public String c;
    public String d;
    public String e;
    public boolean f;
    public String g;
    public String h;
    public double i;
    public double j;
    public double k;
    public double l;
    public String m;
    public int n;
    public boolean o;

    public P2PPay(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readLong();
        this.b = parcel.readLong();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readInt() == 1;
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readDouble();
        this.j = parcel.readDouble();
        this.k = parcel.readDouble();
        this.l = parcel.readDouble();
        this.m = parcel.readString();
        this.n = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.o = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeLong(this.a);
        parcel.writeLong(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeInt(this.f ? 1 : 0);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeDouble(this.i);
        parcel.writeDouble(this.j);
        parcel.writeDouble(this.k);
        parcel.writeDouble(this.l);
        parcel.writeString(this.m);
        parcel.writeInt(this.n);
        if (!this.o) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
