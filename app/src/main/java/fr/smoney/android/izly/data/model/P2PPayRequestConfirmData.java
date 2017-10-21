package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class P2PPayRequestConfirmData implements Parcelable {
    public static final Creator<P2PPayRequestConfirmData> CREATOR = new Creator<P2PPayRequestConfirmData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PPayRequestConfirmData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PPayRequestConfirmData[i];
        }
    };
    public P2PPayRequest a;
    public BalanceData b;
    public String c;
    public String d;

    public P2PPayRequestConfirmData() {
        this.a = new P2PPayRequest();
        this.b = new BalanceData();
    }

    private P2PPayRequestConfirmData(Parcel parcel) {
        this.a = new P2PPayRequest(parcel);
        this.b = new BalanceData(parcel);
        this.c = parcel.readString();
        this.d = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        this.b.writeToParcel(parcel, i);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
    }
}
