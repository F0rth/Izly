package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyOutTransferConfirmData implements Parcelable {
    public static final Creator<MoneyOutTransferConfirmData> CREATOR = new Creator<MoneyOutTransferConfirmData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyOutTransferConfirmData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyOutTransferConfirmData[i];
        }
    };
    public long a;
    public long b;
    public String c;
    public String d;
    public double e;
    public double f;
    public double g;
    public double h;
    public BalanceData i;
    public String j;

    public MoneyOutTransferConfirmData() {
        this.i = new BalanceData();
    }

    private MoneyOutTransferConfirmData(Parcel parcel) {
        this.a = parcel.readLong();
        this.b = parcel.readLong();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readDouble();
        this.f = parcel.readDouble();
        this.g = parcel.readDouble();
        this.h = parcel.readDouble();
        this.i = new BalanceData(parcel);
        this.j = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a);
        parcel.writeLong(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeDouble(this.e);
        parcel.writeDouble(this.f);
        parcel.writeDouble(this.g);
        parcel.writeDouble(this.h);
        this.i.writeToParcel(parcel, i);
        parcel.writeString(this.j);
    }
}
