package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetActiveMandateData implements Parcelable {
    public static final Creator<GetActiveMandateData> CREATOR = new Creator<GetActiveMandateData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetActiveMandateData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetActiveMandateData[i];
        }
    };
    public BalanceData a;
    public double b;
    public double c;
    public int[] d;
    public Mandate e;

    public GetActiveMandateData(Parcel parcel) {
        this();
        this.a = new BalanceData(parcel);
        this.b = parcel.readDouble();
        this.c = parcel.readDouble();
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            this.d = new int[readInt];
            parcel.readIntArray(this.d);
        }
        this.e = (Mandate) parcel.readParcelable(getClass().getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
        parcel.writeDouble(this.b);
        parcel.writeDouble(this.c);
        parcel.writeInt(this.d == null ? -1 : this.d.length);
        parcel.writeIntArray(this.d);
        parcel.writeParcelable(this.e, 0);
    }
}
