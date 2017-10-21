package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProInfos implements Parcelable {
    public static final Creator<ProInfos> CREATOR = new Creator<ProInfos>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ProInfos(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ProInfos[i];
        }
    };
    public static final DecimalFormat r = new DecimalFormat("0.00");
    public static final DecimalFormat s = new DecimalFormat("0");
    public boolean a;
    public String b;
    public String c;
    public double d;
    public double e;
    public double f;
    public boolean g;
    public boolean h;
    public int i;
    public boolean j;
    public boolean k;
    public int l;
    public String m;
    public String n;
    public ArrayList<PromotionalOffer> o;
    public ArrayList<ProProduct> p;
    public ArrayList<String> q;

    public ProInfos() {
        this.d = -1.0d;
        this.e = -1.0d;
        this.f = -1.0d;
        this.o = new ArrayList();
        this.p = new ArrayList();
        this.q = new ArrayList();
    }

    public ProInfos(Parcel parcel) {
        int i;
        int i2 = 0;
        boolean z = true;
        this.d = -1.0d;
        this.e = -1.0d;
        this.f = -1.0d;
        this.a = parcel.readInt() == 1;
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readDouble();
        this.e = parcel.readDouble();
        this.f = parcel.readDouble();
        this.g = parcel.readInt() == 1;
        this.h = parcel.readInt() == 1;
        this.i = parcel.readInt();
        this.j = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.k = z;
        this.l = parcel.readInt();
        this.m = parcel.readString();
        this.n = parcel.readString();
        int readInt = parcel.readInt();
        this.o = new ArrayList();
        for (i = 0; i < readInt; i++) {
            this.o.add((PromotionalOffer) parcel.readParcelable(getClass().getClassLoader()));
        }
        readInt = parcel.readInt();
        this.p = new ArrayList();
        for (i = 0; i < readInt; i++) {
            this.p.add((ProProduct) parcel.readParcelable(getClass().getClassLoader()));
        }
        int readInt2 = parcel.readInt();
        this.q = new ArrayList();
        while (i2 < readInt2) {
            this.q.add(parcel.readString());
            i2++;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        int i3 = 0;
        parcel.writeInt(this.a ? 1 : 0);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeDouble(this.d);
        parcel.writeDouble(this.e);
        parcel.writeDouble(this.f);
        parcel.writeInt(this.g ? 1 : 0);
        parcel.writeInt(this.h ? 1 : 0);
        parcel.writeInt(this.i);
        parcel.writeInt(this.j ? 1 : 0);
        if (!this.k) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.l);
        parcel.writeString(this.m);
        parcel.writeString(this.n);
        int size = this.o.size();
        parcel.writeInt(size);
        for (i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.o.get(i2), i);
        }
        size = this.p.size();
        parcel.writeInt(size);
        for (i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.p.get(i2), i);
        }
        i2 = this.q.size();
        parcel.writeInt(i2);
        while (i3 < i2) {
            parcel.writeString((String) this.q.get(i3));
            i3++;
        }
    }
}
