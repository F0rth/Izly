package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import fr.smoney.android.izly.data.model.NearPro.Tills;

import java.util.ArrayList;

public class TillList implements Parcelable {
    public static final Creator<TillList> CREATOR = new Creator<TillList>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new TillList(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new TillList[i];
        }
    };
    public ArrayList<Tills> a;

    public TillList() {
        this.a = new ArrayList();
    }

    public TillList(Parcel parcel) {
        this();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.a.add((Tills) parcel.readParcelable(getClass().getClassLoader()));
        }
    }

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
