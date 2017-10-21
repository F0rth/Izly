package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.ArrayList;

public class P2PGetMultData implements Parcelable {
    public static final Creator<P2PGetMultData> CREATOR = new Creator<P2PGetMultData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PGetMultData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PGetMultData[i];
        }
    };
    public BalanceData a;
    public ArrayList<P2PGet> b;
    public double c;
    public double d;
    public int e;
    public String f;
    public boolean g;

    public P2PGetMultData() {
        this.g = true;
        this.a = new BalanceData();
        this.b = new ArrayList();
    }

    public P2PGetMultData(Parcel parcel) {
        this.g = true;
        this.a = new BalanceData(parcel);
        int readInt = parcel.readInt();
        this.b = new ArrayList();
        for (int i = 0; i < readInt; i++) {
            this.b.add((P2PGet) parcel.readParcelable(getClass().getClassLoader()));
        }
        this.c = parcel.readDouble();
        this.d = parcel.readDouble();
        this.e = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readInt() == 1;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 0;
        this.a.writeToParcel(parcel, i);
        parcel.writeInt(this.b.size());
        for (int i3 = 0; i3 < this.b.size(); i3++) {
            parcel.writeParcelable((Parcelable) this.b.get(i3), 0);
        }
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.d);
        parcel.writeInt(this.e);
        parcel.writeString(this.f);
        if (this.g) {
            i2 = 1;
        }
        parcel.writeInt(i2);
    }
}
