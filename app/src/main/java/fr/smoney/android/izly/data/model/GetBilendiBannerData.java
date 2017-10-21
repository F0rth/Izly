package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetBilendiBannerData implements Parcelable {
    public static final Creator<GetBilendiBannerData> CREATOR = new Creator<GetBilendiBannerData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetBilendiBannerData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetBilendiBannerData[i];
        }
    };
    public String a;
    public int b;
    public boolean c = false;

    public GetBilendiBannerData(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
    }
}
