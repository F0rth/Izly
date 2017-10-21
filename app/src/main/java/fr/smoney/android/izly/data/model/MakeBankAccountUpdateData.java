package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MakeBankAccountUpdateData implements Parcelable {
    public static final Creator<MakeBankAccountUpdateData> CREATOR = new Creator<MakeBankAccountUpdateData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MakeBankAccountUpdateData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MakeBankAccountUpdateData[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public long g;
    public boolean h;
    public boolean i;
    public String j;
    public String k;

    private MakeBankAccountUpdateData(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.j = parcel.readString();
        this.g = parcel.readLong();
        this.h = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.i = z;
        this.k = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.j);
        parcel.writeLong(this.g);
        parcel.writeInt(this.h ? 1 : 0);
        if (!this.i) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeString(this.k);
    }
}
