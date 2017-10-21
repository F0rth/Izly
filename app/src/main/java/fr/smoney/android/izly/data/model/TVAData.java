package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TVAData implements Parcelable {
    public static final Creator<TVAData> CREATOR = new Creator<TVAData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new TVAData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new TVAData[i];
        }
    };
    public float a;
    public String b;

    public TVAData(Parcel parcel) {
        this.a = parcel.readFloat();
        this.b = parcel.readString();
    }

    public TVAData(String str) {
        this.b = str.concat(" %").replace('.', ',');
        if (this.b != null) {
            this.a = Float.parseFloat(str.trim());
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.a);
        parcel.writeString(this.b);
    }
}
