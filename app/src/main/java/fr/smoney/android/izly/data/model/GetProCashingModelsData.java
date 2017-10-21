package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.io.Serializable;
import java.util.ArrayList;

public class GetProCashingModelsData implements Parcelable, Serializable {
    public static final Creator<GetProCashingModelsData> CREATOR = new Creator<GetProCashingModelsData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetProCashingModelsData();
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetProCashingModelsData[i];
        }
    };
    public ArrayList<CashingModel> a = new ArrayList();
    public int b;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int size = this.a.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.a.get(i2), 0);
        }
        parcel.writeInt(this.b);
    }
}
