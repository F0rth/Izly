package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;

import fr.smoney.android.izly.R;

import java.util.ArrayList;

public class Transaction extends Operation {
    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new Transaction(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Transaction[i];
        }
    };
    public static final SparseArray<a> a = new SparseArray();
    public ArrayList<TransactionMessage> A;
    public boolean B;
    public long b;
    public boolean c;
    public boolean d;
    public double e;
    public double f;
    public double g;
    public double h;
    public double i;
    public String j;
    public long k;
    public long l;
    public String m;
    public int n;
    public int o;
    public String p;
    public String q;
    public String r;
    public int s;
    public a t;
    public String u;
    public long v;
    public String w;
    public String x;
    public String y;
    public int z;

    public enum a {
        Unknown(-1),
        PerReload(0),
        PerDay(1),
        PerWeek(2),
        PerMonth(3),
        PerYear(4);

        int g;

        private a(int i) {
            this.g = 0;
            this.g = i;
        }
    }

    static {
        for (a aVar : a.values()) {
            a.put(aVar.g, aVar);
        }
    }

    public Transaction() {
        this.A = new ArrayList();
    }

    private Transaction(Parcel parcel) {
        boolean z = true;
        this.b = parcel.readLong();
        this.c = parcel.readInt() == 1;
        this.d = parcel.readInt() == 1;
        this.e = parcel.readDouble();
        this.f = parcel.readDouble();
        this.g = parcel.readDouble();
        this.h = parcel.readDouble();
        this.i = parcel.readDouble();
        this.j = parcel.readString();
        this.k = parcel.readLong();
        this.l = parcel.readLong();
        this.m = parcel.readString();
        this.n = parcel.readInt();
        this.p = parcel.readString();
        this.q = parcel.readString();
        this.r = parcel.readString();
        this.s = parcel.readInt();
        this.t = (a) parcel.readSerializable();
        this.u = parcel.readString();
        this.v = parcel.readLong();
        this.w = parcel.readString();
        this.x = parcel.readString();
        this.y = parcel.readString();
        this.z = parcel.readInt();
        this.A = new ArrayList();
        parcel.readTypedList(this.A, TransactionMessage.CREATOR);
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.B = z;
    }

    public final boolean a() {
        return (this.x == null || this.y == null) ? false : true;
    }

    public final int b() {
        switch (this.t) {
            case PerReload:
                return R.string.duration_type_per_reload;
            case PerDay:
                return R.string.duration_type_per_day;
            case PerWeek:
                return R.string.duration_type_per_week;
            case PerMonth:
                return R.string.duration_type_per_month;
            case PerYear:
                return R.string.duration_type_per_year;
            default:
                return R.string.duration_type_unknown;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeLong(this.b);
        parcel.writeInt(this.c ? 1 : 0);
        parcel.writeInt(this.d ? 1 : 0);
        parcel.writeDouble(this.e);
        parcel.writeDouble(this.f);
        parcel.writeDouble(this.g);
        parcel.writeDouble(this.h);
        parcel.writeDouble(this.i);
        parcel.writeString(this.j);
        parcel.writeLong(this.k);
        parcel.writeLong(this.l);
        parcel.writeString(this.m);
        parcel.writeInt(this.n);
        parcel.writeString(this.p);
        parcel.writeString(this.q);
        parcel.writeString(this.r);
        parcel.writeInt(this.s);
        parcel.writeSerializable(this.t);
        parcel.writeString(this.u);
        parcel.writeLong(this.v);
        parcel.writeString(this.w);
        parcel.writeString(this.x);
        parcel.writeString(this.y);
        parcel.writeInt(this.z);
        parcel.writeTypedList(this.A);
        if (!this.B) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
