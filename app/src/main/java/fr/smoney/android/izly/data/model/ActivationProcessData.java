package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ActivationProcessData implements Parcelable {
    public static final Creator<ActivationProcessData> CREATOR = new Creator<ActivationProcessData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ActivationProcessData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ActivationProcessData[i];
        }
    };
    public String a;
    public String b;
    public int c;
    public String d;
    public int e;

    public ActivationProcessData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readInt();
        this.d = parcel.readString();
        this.e = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
    }
}
