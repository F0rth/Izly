package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class P2PPayRequest extends Operation {
    public static final Creator<P2PPayRequest> CREATOR = new Creator<P2PPayRequest>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PPayRequest(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PPayRequest[i];
        }
    };
    public long a;
    public boolean b;
    public long c;
    public String d;
    public String e;
    public String f;
    public boolean g;
    public double h;
    public double i;
    public double j;
    public String k;
    public String l;
    public long m;
    public int n;
    public String o;
    public String p;
    public boolean q;
    public boolean r;
    public boolean s;
    public boolean t = false;
    public boolean u = false;
    public boolean v;

    public P2PPayRequest(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readLong();
        this.b = parcel.readInt() == 1;
        this.c = parcel.readLong();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.q = parcel.readInt() == 1;
        this.r = parcel.readInt() == 1;
        this.g = parcel.readInt() == 1;
        this.h = parcel.readDouble();
        this.i = parcel.readDouble();
        this.j = parcel.readDouble();
        this.k = parcel.readString();
        this.l = parcel.readString();
        this.m = parcel.readLong();
        this.n = parcel.readInt();
        this.o = parcel.readString();
        this.p = parcel.readString();
        this.t = parcel.readInt() == 1;
        this.u = parcel.readInt() == 1;
        this.v = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.s = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeLong(this.a);
        parcel.writeInt(this.b ? 1 : 0);
        parcel.writeLong(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.q ? 1 : 0);
        parcel.writeInt(this.r ? 1 : 0);
        parcel.writeInt(this.g ? 1 : 0);
        parcel.writeDouble(this.h);
        parcel.writeDouble(this.i);
        parcel.writeDouble(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeLong(this.m);
        parcel.writeInt(this.n);
        parcel.writeString(this.o);
        parcel.writeString(this.p);
        parcel.writeInt(this.t ? 1 : 0);
        parcel.writeInt(this.u ? 1 : 0);
        parcel.writeInt(this.v ? 1 : 0);
        if (!this.s) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
