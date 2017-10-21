package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class BlockAccountData implements Parcelable {
    public static final Creator<BlockAccountData> CREATOR = new Creator<BlockAccountData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new BlockAccountData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new BlockAccountData[i];
        }
    };
    public BalanceData a;

    public BlockAccountData() {
        this.a = new BalanceData();
    }

    public BlockAccountData(Parcel parcel) {
        this.a = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
    }
}
