package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyInCbConfirmData implements Parcelable {
    public static final Creator<MoneyInCbConfirmData> CREATOR = new Creator<MoneyInCbConfirmData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyInCbConfirmData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyInCbConfirmData[i];
        }
    };
    public MoneyInCb a;
    public boolean b;
    public CurrentUserSubscriptionPlan c;
    public CbUser d;
    public BalanceData e;
    public String f;

    public MoneyInCbConfirmData() {
        this.a = new MoneyInCb();
        this.b = false;
        this.c = new CurrentUserSubscriptionPlan();
        this.d = new CbUser();
        this.e = new BalanceData();
    }

    private MoneyInCbConfirmData(Parcel parcel) {
        boolean z = true;
        this.a = new MoneyInCb(parcel);
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.b = z;
        this.c = new CurrentUserSubscriptionPlan(parcel);
        this.d = new CbUser(parcel);
        this.e = new BalanceData(parcel);
        this.f = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        parcel.writeInt(this.b ? 1 : 0);
        this.c.writeToParcel(parcel, i);
        this.d.writeToParcel(parcel, i);
        this.e.writeToParcel(parcel, i);
        parcel.writeString(this.f);
    }
}
