package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class CheckMoneyDemandForProData implements Parcelable {
    public static final Creator<CheckMoneyDemandForProData> CREATOR = new Creator<CheckMoneyDemandForProData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CheckMoneyDemandForProData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CheckMoneyDemandForProData[i];
        }
    };
    public BalanceData a;
    public ArrayList<ProMoneyDemand> b;
    public long c;
    public String d;
    public long e;
    public float f;
    public float g;
    public float h;

    public CheckMoneyDemandForProData() {
        this.f = -1.0f;
        this.g = -1.0f;
        this.h = -1.0f;
        this.a = new BalanceData();
        this.b = new ArrayList();
    }

    public CheckMoneyDemandForProData(Parcel parcel) {
        this.f = -1.0f;
        this.g = -1.0f;
        this.h = -1.0f;
        this.a = new BalanceData(parcel);
        this.c = parcel.readLong();
        this.d = parcel.readString();
        this.e = parcel.readLong();
        this.f = parcel.readFloat();
        this.g = parcel.readFloat();
        this.h = parcel.readFloat();
        int readInt = parcel.readInt();
        this.b = new ArrayList();
        for (int i = 0; i < readInt; i++) {
            this.b.add((ProMoneyDemand) parcel.readParcelable(getClass().getClassLoader()));
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        parcel.writeLong(this.c);
        parcel.writeString(this.d);
        parcel.writeLong(this.e);
        parcel.writeFloat(this.f);
        parcel.writeFloat(this.g);
        parcel.writeFloat(this.h);
        int size = this.b.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeParcelable((Parcelable) this.b.get(i2), i);
        }
    }
}
