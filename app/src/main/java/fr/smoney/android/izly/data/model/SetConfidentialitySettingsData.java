package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SetConfidentialitySettingsData implements Parcelable {
    public static final Creator<SetConfidentialitySettingsData> CREATOR = new Creator<SetConfidentialitySettingsData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new SetConfidentialitySettingsData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new SetConfidentialitySettingsData[i];
        }
    };
    public BalanceData a;

    public SetConfidentialitySettingsData() {
        this.a = new BalanceData();
    }

    public SetConfidentialitySettingsData(Parcel parcel) {
        this.a = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.a.writeToParcel(parcel, i);
    }
}
