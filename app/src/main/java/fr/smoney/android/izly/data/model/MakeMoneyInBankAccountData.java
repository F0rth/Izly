package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MakeMoneyInBankAccountData implements Parcelable {
    public static final Creator<MakeMoneyInBankAccountData> CREATOR = new Creator<MakeMoneyInBankAccountData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MakeMoneyInBankAccountData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MakeMoneyInBankAccountData[i];
        }
    };
    public String a;
    public double b;

    public MakeMoneyInBankAccountData(Parcel parcel) {
        this();
        this.a = parcel.readString();
        this.b = parcel.readDouble();
    }

    public MakeMoneyInBankAccountData(String str, double d) {
        this.a = str;
        this.b = d;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeDouble(this.b);
    }
}
