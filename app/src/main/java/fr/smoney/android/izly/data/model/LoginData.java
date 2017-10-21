package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;
import java.util.List;

public class LoginData implements Parcelable {
    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new LoginData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new LoginData[i];
        }
    };
    public String A;
    public BalanceData B;
    public boolean C;
    public boolean D;
    public int E;
    public boolean F;
    public boolean G;
    public boolean H;
    public String I;
    public List<ServiceData> J;
    public String a;
    public String b;
    public String c;
    public double d;
    public double e;
    public double f;
    public double g;
    public double h;
    public double i;
    public String j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public String p;
    public String q;
    public String r;
    public int s;
    public String t;
    public String u;
    public int v;
    public String w;
    public String x;
    public String y;
    public String z;

    public LoginData() {
        this.d = -1.0d;
        this.e = -1.0d;
        this.f = -1.0d;
        this.g = -1.0d;
        this.h = -1.0d;
        this.i = -1.0d;
        this.F = false;
        this.G = false;
        this.H = false;
        this.a = "";
        this.b = "";
        this.c = "";
        this.j = "";
        this.B = new BalanceData();
        this.J = new ArrayList();
        this.E = 0;
    }

    private LoginData(Parcel parcel) {
        boolean z = true;
        this();
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readDouble();
        this.e = parcel.readDouble();
        this.f = parcel.readDouble();
        this.g = parcel.readDouble();
        this.h = parcel.readDouble();
        this.i = parcel.readDouble();
        this.j = parcel.readString();
        this.k = parcel.readInt();
        this.l = parcel.readInt();
        this.m = parcel.readInt();
        this.n = parcel.readInt();
        this.o = parcel.readInt();
        this.I = parcel.readString();
        this.p = parcel.readString();
        this.q = parcel.readString();
        this.r = parcel.readString();
        this.s = parcel.readInt();
        this.t = parcel.readString();
        this.u = parcel.readString();
        this.v = parcel.readInt();
        this.w = parcel.readString();
        this.x = parcel.readString();
        this.y = parcel.readString();
        this.z = parcel.readString();
        this.A = parcel.readString();
        this.E = parcel.readInt();
        this.F = parcel.readInt() == 1;
        this.C = parcel.readInt() == 1;
        this.D = parcel.readInt() == 1;
        this.G = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.H = z;
        parcel.readTypedList(this.J, ServiceData.CREATOR);
        this.B = new BalanceData(parcel);
    }

    public final boolean a() {
        return this.E < 2;
    }

    public final boolean b() {
        return this.E == 3;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeDouble(this.d);
        parcel.writeDouble(this.e);
        parcel.writeDouble(this.f);
        parcel.writeDouble(this.g);
        parcel.writeDouble(this.h);
        parcel.writeDouble(this.i);
        parcel.writeString(this.j);
        parcel.writeInt(this.k);
        parcel.writeInt(this.l);
        parcel.writeInt(this.m);
        parcel.writeInt(this.n);
        parcel.writeInt(this.o);
        parcel.writeString(this.I);
        parcel.writeString(this.p);
        parcel.writeString(this.q);
        parcel.writeString(this.r);
        parcel.writeInt(this.s);
        parcel.writeString(this.t);
        parcel.writeString(this.u);
        parcel.writeInt(this.v);
        parcel.writeString(this.w);
        parcel.writeString(this.x);
        parcel.writeString(this.y);
        parcel.writeString(this.z);
        parcel.writeString(this.A);
        parcel.writeInt(this.E);
        parcel.writeInt(this.F ? 1 : 0);
        parcel.writeInt(this.C ? 1 : 0);
        parcel.writeInt(this.D ? 1 : 0);
        parcel.writeInt(this.G ? 1 : 0);
        if (!this.H) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeTypedList(this.J);
        this.B.writeToParcel(parcel, i);
    }
}
