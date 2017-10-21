package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetNewEventNumberData implements Parcelable {
    public static final Creator<GetNewEventNumberData> CREATOR = new Creator<GetNewEventNumberData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetNewEventNumberData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetNewEventNumberData[i];
        }
    };
    public int a;
    public int b;
    public int c;
    public boolean d;

    public GetNewEventNumberData(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.d = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeInt(this.d ? 1 : 0);
    }
}
