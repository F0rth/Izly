package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyInCbAndPayConfirmData implements Parcelable {
    public static final Creator<MoneyInCbAndPayConfirmData> CREATOR = new Creator<MoneyInCbAndPayConfirmData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyInCbAndPayConfirmData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyInCbAndPayConfirmData[i];
        }
    };
    public P2PPay a;
    public MoneyInCb b;
    public boolean c;
    public CurrentUserSubscriptionPlan d;
    public CbUser e;
    public String f;
    public BalanceData g;

    public MoneyInCbAndPayConfirmData() {
        this.a = new P2PPay();
        this.b = new MoneyInCb();
        this.c = false;
        this.d = new CurrentUserSubscriptionPlan();
        this.e = new CbUser();
        this.g = new BalanceData();
    }

    private MoneyInCbAndPayConfirmData(Parcel parcel) {
        boolean z = true;
        this.a = new P2PPay(parcel);
        this.b = new MoneyInCb(parcel);
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.c = z;
        this.d = new CurrentUserSubscriptionPlan(parcel);
        this.e = new CbUser(parcel);
        this.g = new BalanceData(parcel);
        this.f = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        this.b.writeToParcel(parcel, i);
        parcel.writeInt(this.c ? 1 : 0);
        this.d.writeToParcel(parcel, i);
        this.e.writeToParcel(parcel, i);
        this.g.writeToParcel(parcel, i);
        parcel.writeString(this.f);
    }
}
