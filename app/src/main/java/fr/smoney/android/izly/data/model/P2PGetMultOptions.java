package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class P2PGetMultOptions implements Parcelable {
    public static final Creator<P2PGetMultOptions> CREATOR = new Creator<P2PGetMultOptions>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PGetMultOptions(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PGetMultOptions[i];
        }
    };
    public double a = 0.0d;
    public boolean b = false;
    public boolean c = true;
    public int d = 0;
    public double e = 0.0d;

    public P2PGetMultOptions(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readDouble();
        this.e = parcel.readDouble();
        this.b = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.c = z;
        this.d = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeDouble(this.a);
        parcel.writeDouble(this.e);
        parcel.writeInt(this.b ? 1 : 0);
        if (!this.c) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.d);
    }
}
