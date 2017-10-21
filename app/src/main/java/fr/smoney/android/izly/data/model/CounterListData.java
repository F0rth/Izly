package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class CounterListData implements Parcelable {
    public static final Creator<CounterListData> CREATOR = new Creator<CounterListData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CounterListData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CounterListData[i];
        }
    };
    public final ArrayList<CounterFamily> a;

    public CounterListData(Parcel parcel) {
        this.a = new ArrayList();
        parcel.readTypedList(this.a, CounterFamily.CREATOR);
    }

    public CounterListData(ArrayList<CounterFamily> arrayList) {
        this.a = arrayList;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.a);
    }
}
