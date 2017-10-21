package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ProMoneyDemand implements Parcelable {
    public static final Creator<ProMoneyDemand> CREATOR = new Creator<ProMoneyDemand>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ProMoneyDemand(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ProMoneyDemand[i];
        }
    };
    public String a;
    public String b;
    public boolean c;
    public boolean d;
    public boolean e;
    public boolean f;
    public float g = -1.0f;
    public float h = -1.0f;
    public float i = -1.0f;
    public int j = 0;
    public String k;
    public int l;

    public ProMoneyDemand(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readInt() == 1;
        this.d = parcel.readInt() == 1;
        this.e = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.f = z;
        this.g = parcel.readFloat();
        this.h = parcel.readFloat();
        this.i = parcel.readFloat();
        this.j = parcel.readInt();
        this.k = parcel.readString();
        this.l = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c ? 1 : 0);
        parcel.writeInt(this.d ? 1 : 0);
        parcel.writeInt(this.e ? 1 : 0);
        if (!this.f) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeFloat(this.g);
        parcel.writeFloat(this.h);
        parcel.writeFloat(this.i);
        parcel.writeInt(this.j);
        parcel.writeString(this.k);
        parcel.writeInt(this.l);
    }
}
