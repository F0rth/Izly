package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class TransactionListData implements Parcelable {
    public static final Creator<TransactionListData> CREATOR = new Creator<TransactionListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new TransactionListData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new TransactionListData[i];
        }
    };
    public boolean a;
    public ArrayList<Transaction> b;
    public BalanceData c;

    public TransactionListData() {
        this.c = new BalanceData();
    }

    private TransactionListData(Parcel parcel) {
        boolean z = true;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.a = z;
        this.b = new ArrayList();
        parcel.readTypedList(this.b, Transaction.CREATOR);
        this.c = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a ? 1 : 0);
        parcel.writeTypedList(this.b);
        this.c.writeToParcel(parcel, i);
    }
}
