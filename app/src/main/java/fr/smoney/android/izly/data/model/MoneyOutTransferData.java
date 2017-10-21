package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyOutTransferData implements Parcelable {
    public static final Creator<MoneyOutTransferData> CREATOR = new Creator<MoneyOutTransferData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyOutTransferData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyOutTransferData[i];
        }
    };
    public long a;
    public String b;
    public String c;
    public double d;
    public double e;
    public double f;
    public double g;
    public BalanceData h;

    public MoneyOutTransferData() {
        this.h = new BalanceData();
    }

    private MoneyOutTransferData(Parcel parcel) {
        this.a = parcel.readLong();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readDouble();
        this.e = parcel.readDouble();
        this.f = parcel.readDouble();
        this.g = parcel.readDouble();
        this.h = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeDouble(this.d);
        parcel.writeDouble(this.e);
        parcel.writeDouble(this.f);
        parcel.writeDouble(this.g);
        this.h.writeToParcel(parcel, i);
    }
}
