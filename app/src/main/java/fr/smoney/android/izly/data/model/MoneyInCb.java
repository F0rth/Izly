package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyInCb implements Parcelable {
    public static final Creator<MoneyInCb> CREATOR = new Creator<MoneyInCb>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyInCb(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyInCb[i];
        }
    };
    public long a;
    public long b;
    public MoneyInCbCb c;
    public double d;
    public double e;
    public double f;

    public MoneyInCb() {
        this.c = new MoneyInCbCb();
    }

    public MoneyInCb(Parcel parcel) {
        this.a = parcel.readLong();
        this.b = parcel.readLong();
        this.c = new MoneyInCbCb(parcel);
        this.d = parcel.readDouble();
        this.e = parcel.readDouble();
        this.f = parcel.readDouble();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a);
        parcel.writeLong(this.b);
        this.c.writeToParcel(parcel, i);
        parcel.writeDouble(this.d);
        parcel.writeDouble(this.e);
        parcel.writeDouble(this.f);
    }
}
