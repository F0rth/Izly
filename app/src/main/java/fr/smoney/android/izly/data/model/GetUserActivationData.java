package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetUserActivationData implements Parcelable {
    public static final Creator<GetUserActivationData> CREATOR = new Creator<GetUserActivationData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetUserActivationData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetUserActivationData[i];
        }
    };
    public String a;
    public String b;
    public String c;

    public GetUserActivationData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
    }
}
