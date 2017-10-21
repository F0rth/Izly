package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CbChangeAliasData implements Parcelable {
    public static final Creator<CbChangeAliasData> CREATOR = new Creator<CbChangeAliasData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CbChangeAliasData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CbChangeAliasData[i];
        }
    };
    public MoneyInCbCb a;
    public BalanceData b;

    public CbChangeAliasData() {
        this.a = new MoneyInCbCb();
        this.b = new BalanceData();
    }

    private CbChangeAliasData(Parcel parcel) {
        this.a = (MoneyInCbCb) MoneyInCbCb.CREATOR.createFromParcel(parcel);
        this.b = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, 0);
        this.b.writeToParcel(parcel, i);
    }
}
