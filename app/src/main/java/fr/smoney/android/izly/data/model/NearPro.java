package fr.smoney.android.izly.data.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

import fr.smoney.android.izly.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NearPro implements Parcelable {
    public static final Creator<NearPro> CREATOR = new Creator<NearPro>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NearPro(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NearPro[i];
        }
    };
    public static int r = 0;
    public static int s = 1;
    public static int t = 2;
    public static int u = 3;
    private static final DecimalFormat v = new DecimalFormat("0.00");
    public String a;
    public String b;
    public boolean c;
    public int d;
    public int e;
    public double f;
    public double g;
    public double h;
    public boolean i;
    public int j;
    public String k;
    public String l;
    public String m;
    public String n;
    public PreAuthorizationContainerData o;
    public ArrayList<PromotionalOffer> p;
    public ArrayList<Tills> q;

    public static class Tills implements Parcelable {
        public static final Creator<Tills> CREATOR = new Creator<Tills>() {
            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new Tills(parcel);
            }

            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new Tills[i];
            }
        };
        public String a;
        public String b;
        public boolean c;

        public Tills(Parcel parcel) {
            boolean z = true;
            this.a = parcel.readString();
            this.b = parcel.readString();
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.c = z;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            parcel.writeString(this.b);
            parcel.writeInt(this.c ? 1 : 0);
        }
    }

    public NearPro() {
        this.f = -1.0d;
        this.g = -1.0d;
        this.i = false;
        this.q = new ArrayList();
        this.p = new ArrayList();
    }

    public NearPro(Parcel parcel) {
        int i;
        boolean z = true;
        int i2 = 0;
        this();
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readInt() == 1;
        this.e = parcel.readInt();
        this.f = parcel.readDouble();
        this.g = parcel.readDouble();
        this.h = parcel.readDouble();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.i = z;
        this.j = parcel.readInt();
        this.k = parcel.readString();
        this.l = parcel.readString();
        this.m = parcel.readString();
        this.n = parcel.readString();
        this.d = parcel.readInt();
        this.o = (PreAuthorizationContainerData) parcel.readParcelable(getClass().getClassLoader());
        int readInt = parcel.readInt();
        for (i = 0; i < readInt; i++) {
            this.q.add((Tills) parcel.readParcelable(getClass().getClassLoader()));
        }
        i = parcel.readInt();
        while (i2 < i) {
            this.p.add((PromotionalOffer) parcel.readParcelable(getClass().getClassLoader()));
            i2++;
        }
    }

    public final String a(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        double d = this.h / 1000.0d;
        if (d >= 1.0d) {
            stringBuilder.append(v.format(d)).append(context.getString(R.string.near_pro_distance_unit_km));
        } else {
            stringBuilder.append(v.format(this.h)).append(context.getString(R.string.near_pro_distance_unit_meter));
        }
        return stringBuilder.toString();
    }

    public final boolean a() {
        return this.p != null && this.p.size() > 0;
    }

    public final String b() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!(this.k == null || TextUtils.isEmpty(this.k))) {
            stringBuilder.append(this.k);
        }
        if (!(this.m == null || TextUtils.isEmpty(this.m))) {
            stringBuilder.append(", ");
            stringBuilder.append(this.m);
        }
        if (!(this.l == null || TextUtils.isEmpty(this.l))) {
            stringBuilder.append(", ");
            stringBuilder.append(this.l);
        }
        return stringBuilder.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c ? 1 : 0);
        parcel.writeInt(this.e);
        parcel.writeDouble(this.f);
        parcel.writeDouble(this.g);
        parcel.writeDouble(this.h);
        if (!this.i) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeString(this.m);
        parcel.writeString(this.n);
        parcel.writeInt(this.d);
        parcel.writeParcelable(this.o, i);
        int size = this.q.size();
        parcel.writeInt(size);
        for (i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.q.get(i2), 0);
        }
        size = this.p.size();
        parcel.writeInt(size);
        for (i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.p.get(i2), 0);
        }
    }
}
