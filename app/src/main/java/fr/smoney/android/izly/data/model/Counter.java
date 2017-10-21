package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Counter implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new Counter(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Counter[i];
        }
    };
    public final long a;
    public final int b;
    public final String c;
    private final long d;

    public Counter(long j, long j2, int i, String str) {
        this.d = j;
        this.a = j2;
        this.b = i;
        this.c = str;
    }

    public Counter(Parcel parcel) {
        this.d = parcel.readLong();
        this.c = parcel.readString();
        this.a = parcel.readLong();
        this.b = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.d);
        parcel.writeString(this.c);
        parcel.writeLong(this.a);
        parcel.writeInt(this.b);
    }
}
