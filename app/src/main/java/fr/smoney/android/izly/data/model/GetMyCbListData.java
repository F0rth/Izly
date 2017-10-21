package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class GetMyCbListData implements Parcelable {
    public static final Creator<GetMyCbListData> CREATOR = new Creator<GetMyCbListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetMyCbListData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetMyCbListData[i];
        }
    };
    public ArrayList<MoneyInCbCb> a;
    public BalanceData b;

    public GetMyCbListData() {
        this.b = new BalanceData();
        this.a = new ArrayList();
    }

    public GetMyCbListData(Parcel parcel) {
        this.a = new ArrayList();
        parcel.readTypedList(this.a, MoneyInCbCb.CREATOR);
        this.b = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.a);
        this.b.writeToParcel(parcel, i);
    }
}
