package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AssociatePhoneNumberData implements Parcelable {
    public static final Creator<AssociatePhoneNumberData> CREATOR = new Creator<AssociatePhoneNumberData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new AssociatePhoneNumberData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new AssociatePhoneNumberData[i];
        }
    };
    public String a;
    public String b;

    public AssociatePhoneNumberData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
    }
}
