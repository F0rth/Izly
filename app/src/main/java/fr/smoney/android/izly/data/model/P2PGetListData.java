package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class P2PGetListData implements Parcelable {
    public static final Creator<P2PGetListData> CREATOR = new Creator<P2PGetListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PGetListData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PGetListData[i];
        }
    };
    public ArrayList<P2PGetMult> a;
    public boolean b;
    public int c;
    public int d;
    public boolean e;
    public BalanceData f;

    public P2PGetListData() {
        this.a = new ArrayList();
        this.f = new BalanceData();
    }

    private P2PGetListData(Parcel parcel) {
        boolean z = true;
        this.a = new ArrayList();
        parcel.readTypedList(this.a, P2PGetMult.CREATOR);
        this.b = parcel.readInt() == 1;
        this.d = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.e = z;
        this.f = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeTypedList(this.a);
        parcel.writeInt(this.b ? 1 : 0);
        parcel.writeInt(this.d);
        if (!this.e) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        this.f.writeToParcel(parcel, i);
    }
}
