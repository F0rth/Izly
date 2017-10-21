package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyInCbData implements Parcelable {
    public static final Creator<MoneyInCbData> CREATOR = new Creator<MoneyInCbData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyInCbData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyInCbData[i];
        }
    };
    public MoneyInCb a;
    public CbUser b;
    public BalanceData c;

    public MoneyInCbData() {
        this.a = new MoneyInCb();
        this.b = new CbUser();
        this.c = new BalanceData();
    }

    private MoneyInCbData(Parcel parcel) {
        this.a = new MoneyInCb(parcel);
        this.b = new CbUser(parcel);
        this.c = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        this.b.writeToParcel(parcel, i);
        this.c.writeToParcel(parcel, i);
    }
}
