package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class P2PPayData implements Parcelable {
    public static final Creator<P2PPayData> CREATOR = new Creator<P2PPayData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PPayData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PPayData[i];
        }
    };
    public P2PPay a;
    public boolean b;
    public ArrayList<MoneyInCbCb> c;
    public double[] d;
    public boolean e;
    public boolean f;
    public boolean g;
    public BalanceData h;

    public P2PPayData() {
        this.e = false;
        this.f = false;
        this.a = new P2PPay();
        this.b = false;
        this.c = new ArrayList();
        this.h = new BalanceData();
    }

    private P2PPayData(Parcel parcel) {
        int i;
        boolean z = true;
        this.e = false;
        this.f = false;
        this.a = new P2PPay(parcel);
        this.b = parcel.readInt() == 1;
        int readInt = parcel.readInt();
        this.c = new ArrayList();
        for (i = 0; i < readInt; i++) {
            this.c.add((MoneyInCbCb) parcel.readParcelable(getClass().getClassLoader()));
        }
        i = parcel.readInt();
        if (i >= 0) {
            this.d = new double[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.d[i2] = parcel.readDouble();
            }
        }
        this.h = new BalanceData(parcel);
        this.g = parcel.readInt() == 1;
        this.e = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.f = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        this.a.writeToParcel(parcel, i);
        parcel.writeInt(this.b ? 1 : 0);
        parcel.writeInt(this.c.size());
        for (int i3 = 0; i3 < this.c.size(); i3++) {
            parcel.writeParcelable((Parcelable) this.c.get(i3), 0);
        }
        parcel.writeInt(this.d == null ? -1 : this.d.length);
        if (this.d != null) {
            for (double writeDouble : this.d) {
                parcel.writeDouble(writeDouble);
            }
        }
        this.h.writeToParcel(parcel, i);
        parcel.writeInt(this.g ? 1 : 0);
        parcel.writeInt(this.e ? 1 : 0);
        if (!this.f) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
