package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DeleteBankAccountData implements Parcelable {
    public static final Creator<DeleteBankAccountData> CREATOR = new Creator<DeleteBankAccountData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new DeleteBankAccountData();
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new DeleteBankAccountData[i];
        }
    };
    public BalanceData a = new BalanceData();

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
    }
}
