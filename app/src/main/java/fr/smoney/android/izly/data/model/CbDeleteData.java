package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CbDeleteData implements Parcelable {
    public static final Creator<CbDeleteData> CREATOR = new Creator<CbDeleteData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CbDeleteData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CbDeleteData[i];
        }
    };
    public String a;
    public BalanceData b;

    public CbDeleteData() {
        this.b = new BalanceData();
    }

    private CbDeleteData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        this.b.writeToParcel(parcel, i);
    }
}
