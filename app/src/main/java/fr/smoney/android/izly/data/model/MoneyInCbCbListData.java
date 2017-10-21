package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class MoneyInCbCbListData implements Parcelable {
    public static final Creator<MoneyInCbCbListData> CREATOR = new Creator<MoneyInCbCbListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyInCbCbListData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyInCbCbListData[i];
        }
    };
    public ArrayList<MoneyInCbCb> a;
    public int[] b;
    public BalanceData c;

    public MoneyInCbCbListData() {
        this.a = new ArrayList();
        this.c = new BalanceData();
    }

    private MoneyInCbCbListData(Parcel parcel) {
        this.a = new ArrayList();
        parcel.readTypedList(this.a, MoneyInCbCb.CREATOR);
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            this.b = new int[readInt];
            parcel.readIntArray(this.b);
        }
        this.c = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.a);
        parcel.writeInt(this.b == null ? -1 : this.b.length);
        parcel.writeIntArray(this.b);
        this.c.writeToParcel(parcel, i);
    }
}
