package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class P2PPayConfirmData implements Parcelable {
    public static final Creator<P2PPayConfirmData> CREATOR = new Creator<P2PPayConfirmData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PPayConfirmData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PPayConfirmData[i];
        }
    };
    public P2PPay a;
    public BalanceData b;
    public String c;

    public P2PPayConfirmData() {
        this.a = new P2PPay();
        this.b = new BalanceData();
    }

    private P2PPayConfirmData(Parcel parcel) {
        this.a = new P2PPay(parcel);
        this.b = new BalanceData(parcel);
        this.c = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        this.b.writeToParcel(parcel, i);
        parcel.writeString(this.c);
    }
}
