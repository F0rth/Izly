package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ChooseDefaultCbData implements Parcelable {
    public static final Creator<ChooseDefaultCbData> CREATOR = new Creator<ChooseDefaultCbData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ChooseDefaultCbData((byte) 0);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ChooseDefaultCbData[i];
        }
    };

    public ChooseDefaultCbData(byte b) {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }
}
