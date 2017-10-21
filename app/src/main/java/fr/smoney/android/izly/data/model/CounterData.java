package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class CounterData implements Parcelable {
    public static final Creator<CounterData> CREATOR = new Creator<CounterData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CounterData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CounterData[i];
        }
    };
    public final ArrayList<Counter> a;

    public CounterData(Parcel parcel) {
        this.a = new ArrayList();
        parcel.readTypedList(this.a, Counter.CREATOR);
    }

    public CounterData(ArrayList<Counter> arrayList) {
        this.a = arrayList;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.a);
    }
}
