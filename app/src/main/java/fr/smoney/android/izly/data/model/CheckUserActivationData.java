package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class CheckUserActivationData implements Parcelable {
    public static final Creator<CheckUserActivationData> CREATOR = new Creator<CheckUserActivationData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CheckUserActivationData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CheckUserActivationData[i];
        }
    };
    public ArrayList<String> a = new ArrayList();
    public String b;
    public ArrayList<String> c = new ArrayList();

    public CheckUserActivationData(Parcel parcel) {
        int i;
        int i2 = 0;
        int readInt = parcel.readInt();
        for (i = 0; i < readInt; i++) {
            this.a.add(parcel.readString());
        }
        this.b = parcel.readString();
        i = parcel.readInt();
        while (i2 < i) {
            this.c.add(parcel.readString());
            i2++;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2;
        int i3 = 0;
        int size = this.a.size();
        parcel.writeInt(size);
        for (i2 = 0; i2 < size; i2++) {
            parcel.writeString((String) this.a.get(i2));
        }
        parcel.writeString(this.b);
        i2 = this.c.size();
        parcel.writeInt(i2);
        while (i3 < i2) {
            parcel.writeString((String) this.c.get(i3));
            i3++;
        }
    }
}
