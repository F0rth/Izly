package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class GoodDealsArray implements Parcelable {
    public static final Creator<GoodDealsArray> CREATOR = new Creator<GoodDealsArray>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GoodDealsArray(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GoodDealsArray[i];
        }
    };
    public ArrayList<GoodDealsData> a;

    public GoodDealsArray(Parcel parcel) {
        this.a = new ArrayList();
        parcel.readTypedList(this.a, GoodDealsData.CREATOR);
    }

    public GoodDealsArray(ArrayList<GoodDealsData> arrayList) {
        this.a = arrayList;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.a);
    }
}
