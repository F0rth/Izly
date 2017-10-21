package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class P2PGetMult extends Operation {
    public static final Creator<P2PGetMult> CREATOR = new Creator<P2PGetMult>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PGetMult(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PGetMult[i];
        }
    };
    public BalanceData a;
    public ArrayList<P2PGet> b;
    public long c;
    public boolean d;
    public long e;
    public double f;
    public double g;
    public double h;
    public int i;
    public String j;
    public int k;
    public long l;
    public long m;
    public String n;
    public String o;
    public boolean p;

    public P2PGetMult() {
        this.b = new ArrayList();
        this.a = new BalanceData();
    }

    public P2PGetMult(Parcel parcel) {
        boolean z = true;
        this.a = new BalanceData(parcel);
        int readInt = parcel.readInt();
        this.b = new ArrayList();
        for (int i = 0; i < readInt; i++) {
            this.b.add((P2PGet) parcel.readParcelable(getClass().getClassLoader()));
        }
        this.c = parcel.readLong();
        this.d = parcel.readInt() == 1;
        this.e = parcel.readLong();
        this.f = parcel.readDouble();
        this.g = parcel.readDouble();
        this.h = parcel.readDouble();
        this.i = parcel.readInt();
        this.j = parcel.readString();
        this.k = parcel.readInt();
        this.l = parcel.readLong();
        this.m = parcel.readLong();
        this.n = parcel.readString();
        this.o = parcel.readString();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.p = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        this.a.writeToParcel(parcel, i);
        parcel.writeInt(this.b.size());
        for (int i3 = 0; i3 < this.b.size(); i3++) {
            parcel.writeParcelable((Parcelable) this.b.get(i3), 0);
        }
        parcel.writeLong(this.c);
        parcel.writeInt(this.d ? 1 : 0);
        parcel.writeLong(this.e);
        parcel.writeDouble(this.f);
        parcel.writeDouble(this.g);
        parcel.writeDouble(this.h);
        parcel.writeInt(this.i);
        parcel.writeString(this.j);
        parcel.writeInt(this.k);
        parcel.writeLong(this.l);
        parcel.writeLong(this.m);
        parcel.writeString(this.n);
        parcel.writeString(this.o);
        if (!this.p) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
