package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class P2PGetMultConfirmData implements Parcelable {
    public static final Creator<P2PGetMultConfirmData> CREATOR = new Creator<P2PGetMultConfirmData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PGetMultConfirmData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PGetMultConfirmData[i];
        }
    };
    public BalanceData a;
    public ArrayList<P2PGet> b;
    public long c;
    public long d;
    public double e;
    public int f;
    public String g;
    public double h;
    public String i;

    public P2PGetMultConfirmData() {
        this.b = new ArrayList();
        this.a = new BalanceData();
    }

    public P2PGetMultConfirmData(Parcel parcel) {
        this.a = new BalanceData(parcel);
        int readInt = parcel.readInt();
        this.b = new ArrayList();
        for (int i = 0; i < readInt; i++) {
            this.b.add((P2PGet) parcel.readParcelable(getClass().getClassLoader()));
        }
        this.c = parcel.readLong();
        this.d = parcel.readLong();
        this.e = parcel.readDouble();
        this.h = parcel.readDouble();
        this.f = parcel.readInt();
        this.g = parcel.readString();
        this.i = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        parcel.writeInt(this.b.size());
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            parcel.writeParcelable((Parcelable) this.b.get(i2), 0);
        }
        parcel.writeLong(this.c);
        parcel.writeLong(this.d);
        parcel.writeDouble(this.e);
        parcel.writeDouble(this.h);
        parcel.writeInt(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.i);
    }
}
