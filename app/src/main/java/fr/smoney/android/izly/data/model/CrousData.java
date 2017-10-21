package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CrousData implements Parcelable {
    public static final Creator<CrousData> CREATOR = new Creator<CrousData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CrousData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CrousData[i];
        }
    };
    public String a;
    public String b;
    public boolean c;

    private CrousData(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readString();
        this.b = parcel.readString();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.c = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c ? 1 : 0);
    }
}
