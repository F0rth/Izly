package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CounterFamily implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CounterFamily(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CounterFamily[i];
        }
    };
    public long a;
    public String b;
    public int c;

    public CounterFamily(long j, String str, int i) {
        this.a = j;
        this.b = str;
        this.c = i;
    }

    public CounterFamily(Parcel parcel) {
        this.a = parcel.readLong();
        this.b = parcel.readString();
        this.c = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c);
    }
}
