package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyOutTransferAccountData implements Parcelable {
    public static final Creator<MoneyOutTransferAccountData> CREATOR = new Creator<MoneyOutTransferAccountData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyOutTransferAccountData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyOutTransferAccountData[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public float[] d;
    public BalanceData e;

    public MoneyOutTransferAccountData() {
        this.e = new BalanceData();
    }

    private MoneyOutTransferAccountData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            this.d = new float[readInt];
            parcel.readFloatArray(this.d);
        }
        this.e = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeInt(this.d == null ? -1 : this.d.length);
        parcel.writeFloatArray(this.d);
        this.e.writeToParcel(parcel, i);
    }
}
