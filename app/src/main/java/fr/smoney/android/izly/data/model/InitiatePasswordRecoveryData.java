package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class InitiatePasswordRecoveryData implements Parcelable {
    public static final Creator<InitiatePasswordRecoveryData> CREATOR = new Creator<InitiatePasswordRecoveryData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new InitiatePasswordRecoveryData((byte) 0);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new InitiatePasswordRecoveryData[i];
        }
    };

    public InitiatePasswordRecoveryData(byte b) {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }
}
