package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class P2PPayRequestData implements Parcelable {
    public static final Creator<P2PPayRequestData> CREATOR = new Creator<P2PPayRequestData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PPayRequestData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PPayRequestData[i];
        }
    };
    public P2PPayRequest a;
    public boolean b;
    public ArrayList<MoneyInCbCb> c;
    public double[] d;
    public boolean e;
    public boolean f;
    public BalanceData g;

    public P2PPayRequestData() {
        this.e = false;
        this.f = false;
        this.a = new P2PPayRequest();
        this.b = false;
        this.c = new ArrayList();
        this.g = new BalanceData();
    }

    private P2PPayRequestData(Parcel parcel) {
        boolean z = true;
        this.e = false;
        this.f = false;
        this.a = new P2PPayRequest(parcel);
        this.b = parcel.readInt() == 1;
        this.e = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.f = z;
        this.c = new ArrayList();
        parcel.readTypedList(this.c, MoneyInCbCb.CREATOR);
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            this.d = new double[readInt];
            parcel.readDoubleArray(this.d);
        }
        this.g = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        this.a.writeToParcel(parcel, i);
        parcel.writeInt(this.b ? 1 : 0);
        parcel.writeInt(this.e ? 1 : 0);
        if (!this.f) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeTypedList(this.c);
        parcel.writeInt(this.d == null ? -1 : this.d.length);
        parcel.writeDoubleArray(this.d);
        this.g.writeToParcel(parcel, i);
    }
}
