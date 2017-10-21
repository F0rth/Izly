package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class P2PGet implements Parcelable {
    public static final Creator<P2PGet> CREATOR = new Creator<P2PGet>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PGet(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PGet[i];
        }
    };
    public long a;
    public long b;
    public String c;
    public boolean d;
    public boolean e = false;
    public String f;
    public String g;
    public double h;
    public double i;
    public String j;
    public String k;
    public long l;
    public int m;
    public int n;
    public long o;
    public String p;
    public int q;
    public String r;
    public int s;

    public P2PGet(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readLong();
        this.b = parcel.readLong();
        this.c = parcel.readString();
        this.d = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.e = z;
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readDouble();
        this.i = parcel.readDouble();
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.l = parcel.readLong();
        this.m = parcel.readInt();
        this.n = parcel.readInt();
        this.o = parcel.readLong();
        this.p = parcel.readString();
        this.q = parcel.readInt();
        this.r = parcel.readString();
        this.s = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeLong(this.a);
        parcel.writeLong(this.b);
        parcel.writeString(this.c);
        parcel.writeInt(this.d ? 1 : 0);
        if (!this.e) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeDouble(this.h);
        parcel.writeDouble(this.i);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeLong(this.l);
        parcel.writeInt(this.m);
        parcel.writeInt(this.n);
        parcel.writeLong(this.o);
        parcel.writeString(this.p);
        parcel.writeInt(this.q);
        parcel.writeString(this.r);
        parcel.writeInt(this.s);
    }
}
