package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import fr.smoney.android.izly.data.model.NearPro.Tills;

import java.util.ArrayList;

public class GetContactDetailsData implements Parcelable {
    public static final Creator<GetContactDetailsData> CREATOR = new Creator<GetContactDetailsData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetContactDetailsData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetContactDetailsData[i];
        }
    };
    public String A;
    public String B;
    public int C;
    public long D;
    public String E;
    public boolean F;
    public boolean G;
    public ArrayList<Transaction> H;
    public ArrayList<Tills> I;
    public ProInfos J;
    public PreAuthorizationContainerData K;
    public String a;
    public String b;
    public BalanceData c;
    public String d;
    public String e;
    public String f;
    public boolean g;
    public String h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public String q;
    public String r;
    public String s;
    public int t;
    public long u;
    public int v;
    public String w;
    public String x;
    public String y;
    public String z;

    public GetContactDetailsData() {
        this.H = new ArrayList();
        this.I = new ArrayList();
        this.c = new BalanceData();
        this.J = new ProInfos();
    }

    public GetContactDetailsData(Parcel parcel) {
        int i;
        int i2 = 0;
        boolean z = true;
        this();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readInt() == 1;
        this.a = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readInt() == 1;
        this.b = parcel.readString();
        this.j = parcel.readInt() == 1;
        this.k = parcel.readInt() == 1;
        this.l = parcel.readInt() == 1;
        this.m = parcel.readInt() == 1;
        this.n = parcel.readInt() == 1;
        this.o = parcel.readInt() == 1;
        this.p = parcel.readInt() == 1;
        this.q = parcel.readString();
        this.r = parcel.readString();
        this.s = parcel.readString();
        this.t = parcel.readInt();
        this.u = parcel.readLong();
        this.v = parcel.readInt();
        this.w = parcel.readString();
        this.x = parcel.readString();
        this.y = parcel.readString();
        this.F = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.G = z;
        this.z = parcel.readString();
        this.B = parcel.readString();
        this.C = parcel.readInt();
        this.D = parcel.readLong();
        this.E = parcel.readString();
        this.A = parcel.readString();
        this.K = (PreAuthorizationContainerData) parcel.readParcelable(getClass().getClassLoader());
        int readInt = parcel.readInt();
        for (i = 0; i < readInt; i++) {
            this.I.add((Tills) parcel.readParcelable(getClass().getClassLoader()));
        }
        i = parcel.readInt();
        this.H = new ArrayList();
        while (i2 < i) {
            this.H.add((Transaction) parcel.readParcelable(getClass().getClassLoader()));
            i2++;
        }
        this.c = new BalanceData(parcel);
        this.J = new ProInfos(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        int i3 = 0;
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.g ? 1 : 0);
        parcel.writeString(this.a);
        parcel.writeString(this.h);
        parcel.writeInt(this.i ? 1 : 0);
        parcel.writeString(this.b);
        parcel.writeInt(this.j ? 1 : 0);
        parcel.writeInt(this.k ? 1 : 0);
        parcel.writeInt(this.l ? 1 : 0);
        parcel.writeInt(this.m ? 1 : 0);
        parcel.writeInt(this.n ? 1 : 0);
        parcel.writeInt(this.o ? 1 : 0);
        parcel.writeInt(this.p ? 1 : 0);
        parcel.writeString(this.q);
        parcel.writeString(this.r);
        parcel.writeString(this.s);
        parcel.writeInt(this.t);
        parcel.writeLong(this.u);
        parcel.writeInt(this.v);
        parcel.writeString(this.w);
        parcel.writeString(this.x);
        parcel.writeString(this.y);
        parcel.writeInt(this.F ? 1 : 0);
        if (!this.G) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeString(this.z);
        parcel.writeString(this.B);
        parcel.writeInt(this.C);
        parcel.writeLong(this.D);
        parcel.writeString(this.E);
        parcel.writeString(this.A);
        parcel.writeParcelable(this.K, i);
        int size = this.I.size();
        parcel.writeInt(size);
        for (i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.I.get(i2), 0);
        }
        i2 = this.H.size();
        parcel.writeInt(i2);
        while (i3 < i2) {
            parcel.writeParcelable((Parcelable) this.H.get(i3), i);
            i3++;
        }
        this.c.writeToParcel(parcel, i);
        if (this.J != null) {
            this.J.writeToParcel(parcel, i);
        }
    }
}
