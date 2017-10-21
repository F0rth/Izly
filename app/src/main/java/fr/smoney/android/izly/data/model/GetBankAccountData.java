package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetBankAccountData implements Parcelable {
    public static final Creator<GetBankAccountData> CREATOR = new Creator<GetBankAccountData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetBankAccountData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetBankAccountData[i];
        }
    };
    public long a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public boolean g;
    public boolean h = true;
    public String i;

    public GetBankAccountData(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readLong();
        this.b = parcel.readString();
        this.f = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.c = parcel.readString();
        this.g = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.h = z;
        this.i = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeLong(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.f);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.c);
        parcel.writeInt(this.g ? 1 : 0);
        if (!this.h) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeString(this.i);
    }
}
