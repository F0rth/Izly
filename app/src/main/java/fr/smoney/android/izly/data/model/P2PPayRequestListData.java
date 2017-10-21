package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class P2PPayRequestListData implements Parcelable {
    public static final Creator<P2PPayRequestListData> CREATOR = new Creator<P2PPayRequestListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PPayRequestListData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PPayRequestListData[i];
        }
    };
    public ArrayList<P2PPayRequest> a;
    public boolean b;
    public int c;
    public boolean d;
    public BalanceData e;

    public P2PPayRequestListData() {
        this.a = new ArrayList();
        this.e = new BalanceData();
    }

    private P2PPayRequestListData(Parcel parcel) {
        boolean z = true;
        this.a = new ArrayList();
        parcel.readTypedList(this.a, P2PPayRequest.CREATOR);
        this.b = parcel.readInt() == 1;
        this.c = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.d = z;
        this.e = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeTypedList(this.a);
        parcel.writeInt(this.b ? 1 : 0);
        parcel.writeInt(this.c);
        if (!this.d) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        this.e.writeToParcel(parcel, i);
    }
}
