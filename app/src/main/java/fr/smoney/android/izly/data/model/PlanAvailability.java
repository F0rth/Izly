package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PlanAvailability implements Parcelable {
    public static final Creator<PlanAvailability> CREATOR = new Creator<PlanAvailability>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new PlanAvailability(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new PlanAvailability[i];
        }
    };
    public long a;
    public float b;
    public int c;
    public float d;

    public PlanAvailability(Parcel parcel) {
        this.a = parcel.readLong();
        this.b = parcel.readFloat();
        this.c = parcel.readInt();
        this.d = parcel.readFloat();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a);
        parcel.writeFloat(this.b);
        parcel.writeInt(this.c);
        parcel.writeFloat(this.d);
    }
}
