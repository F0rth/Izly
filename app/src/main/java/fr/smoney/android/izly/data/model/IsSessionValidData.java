package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class IsSessionValidData implements Parcelable {
    public static final Creator<IsSessionValidData> CREATOR = new Creator<IsSessionValidData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new IsSessionValidData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new IsSessionValidData[i];
        }
    };
    public boolean a;
    public BalanceData b;

    public IsSessionValidData(Parcel parcel) {
        boolean z = true;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.a = z;
        this.b = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a ? 1 : 0);
        this.b.writeToParcel(parcel, i);
    }
}
