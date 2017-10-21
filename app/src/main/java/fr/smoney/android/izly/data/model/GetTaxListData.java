package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class GetTaxListData implements Parcelable {
    public static final Creator<GetTaxListData> CREATOR = new Creator<GetTaxListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetTaxListData();
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetTaxListData[i];
        }
    };
    public ArrayList<TVAData> a = new ArrayList();

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int size = this.a.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.a.get(i2), 0);
        }
    }
}
