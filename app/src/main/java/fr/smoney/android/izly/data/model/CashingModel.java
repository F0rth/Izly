package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.io.Serializable;

public class CashingModel implements Parcelable, Serializable {
    public static final Creator<CashingModel> CREATOR = new Creator<CashingModel>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CashingModel(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CashingModel[i];
        }
    };
    public int a;
    public double b = -1.0d;
    public double c = -1.0d;
    public double d = -1.0d;
    public String e;
    public String f;
    public String g;
    public String h;
    public long i;
    public String j;
    public String k;
    public int l;

    public CashingModel(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readDouble();
        this.c = parcel.readDouble();
        this.d = parcel.readDouble();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readLong();
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.l = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeDouble(this.b);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeLong(this.i);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeInt(this.l);
    }
}
