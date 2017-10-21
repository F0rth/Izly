package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class IsEnrollmentOpenData implements Parcelable {
    public static final Creator<IsEnrollmentOpenData> CREATOR = new Creator<IsEnrollmentOpenData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new IsEnrollmentOpenData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new IsEnrollmentOpenData[i];
        }
    };
    public boolean a;

    public IsEnrollmentOpenData(Parcel parcel) {
        boolean z = true;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.a = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a ? 1 : 0);
    }
}
