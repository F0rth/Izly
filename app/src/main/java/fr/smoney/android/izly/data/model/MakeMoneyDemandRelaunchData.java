package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class MakeMoneyDemandRelaunchData implements Parcelable {
    public static final Creator<MakeMoneyDemandRelaunchData> CREATOR = new Creator<MakeMoneyDemandRelaunchData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MakeMoneyDemandRelaunchData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MakeMoneyDemandRelaunchData[i];
        }
    };
    public long a;
    public boolean b;
    public ArrayList<MoneyDemandRelaunch> c;

    public MakeMoneyDemandRelaunchData() {
        this.c = new ArrayList();
    }

    public MakeMoneyDemandRelaunchData(Parcel parcel) {
        boolean z = true;
        int i = 0;
        this.a = parcel.readLong();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.b = z;
        int readInt = parcel.readInt();
        this.c = new ArrayList();
        while (i < readInt) {
            this.c.add((MoneyDemandRelaunch) parcel.readParcelable(getClass().getClassLoader()));
            i++;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 0;
        parcel.writeLong(this.a);
        parcel.writeInt(this.b ? 1 : 0);
        int size = this.c.size();
        parcel.writeInt(size);
        while (i2 < size) {
            parcel.writeParcelable((Parcelable) this.c.get(i2), i);
            i2++;
        }
    }
}
