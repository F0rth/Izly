package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyInCbAndPayData implements Parcelable {
    public static final Creator<MoneyInCbAndPayData> CREATOR = new Creator<MoneyInCbAndPayData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyInCbAndPayData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyInCbAndPayData[i];
        }
    };
    public P2PPay a;
    public MoneyInCb b;
    public CbUser c;
    public BalanceData d;

    public MoneyInCbAndPayData() {
        this.a = new P2PPay();
        this.b = new MoneyInCb();
        this.c = new CbUser();
        this.d = new BalanceData();
    }

    private MoneyInCbAndPayData(Parcel parcel) {
        this.a = new P2PPay(parcel);
        this.b = new MoneyInCb(parcel);
        this.c = new CbUser(parcel);
        this.d = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        this.b.writeToParcel(parcel, i);
        this.c.writeToParcel(parcel, i);
        this.d.writeToParcel(parcel, i);
    }
}
