package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MoneyDemandRelaunch implements Parcelable {
    public static final Creator<MoneyDemandRelaunch> CREATOR = new Creator<MoneyDemandRelaunch>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyDemandRelaunch(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyDemandRelaunch[i];
        }
    };
    public long a;
    public String b;
    public int c;
    public String d;
    public boolean e;
    public int f = -1;
    public String g;

    public MoneyDemandRelaunch(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readLong();
        this.b = parcel.readString();
        this.c = parcel.readInt();
        this.d = parcel.readString();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.e = z;
        this.f = parcel.readInt();
        this.g = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e ? 1 : 0);
        parcel.writeInt(this.f);
        parcel.writeString(this.g);
    }
}
