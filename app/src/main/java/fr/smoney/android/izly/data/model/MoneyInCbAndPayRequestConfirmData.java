package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyInCbAndPayRequestConfirmData implements Parcelable {
    public static final Creator<MoneyInCbAndPayRequestConfirmData> CREATOR = new Creator<MoneyInCbAndPayRequestConfirmData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyInCbAndPayRequestConfirmData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyInCbAndPayRequestConfirmData[i];
        }
    };
    public P2PPayRequest a;
    public MoneyInCb b;
    public boolean c;
    public CurrentUserSubscriptionPlan d;
    public CbUser e;
    public BalanceData f;
    public String g;

    public MoneyInCbAndPayRequestConfirmData() {
        this.a = new P2PPayRequest();
        this.b = new MoneyInCb();
        this.c = false;
        this.d = new CurrentUserSubscriptionPlan();
        this.e = new CbUser();
        this.f = new BalanceData();
    }

    private MoneyInCbAndPayRequestConfirmData(Parcel parcel) {
        boolean z = true;
        this.a = new P2PPayRequest(parcel);
        this.b = new MoneyInCb(parcel);
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.c = z;
        this.d = new CurrentUserSubscriptionPlan(parcel);
        this.e = new CbUser(parcel);
        this.f = new BalanceData(parcel);
        this.g = parcel.readString();
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
        this.f.writeToParcel(parcel, i);
        parcel.writeString(this.g);
    }
}
