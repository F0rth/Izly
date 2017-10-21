package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class BalanceData implements Parcelable {
    public static final Creator<BalanceData> CREATOR = new Creator<BalanceData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new BalanceData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new BalanceData[i];
        }
    };
    public double a;
    public double b;
    public long c;

    public BalanceData(Parcel parcel) {
        this.a = parcel.readDouble();
        this.b = parcel.readDouble();
        this.c = parcel.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.a);
        parcel.writeDouble(this.b);
        parcel.writeLong(this.c);
    }
}
