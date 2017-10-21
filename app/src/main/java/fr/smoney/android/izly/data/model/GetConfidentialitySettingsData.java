package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GetConfidentialitySettingsData implements Parcelable {
    public static final Creator<GetConfidentialitySettingsData> CREATOR = new Creator<GetConfidentialitySettingsData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GetConfidentialitySettingsData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GetConfidentialitySettingsData[i];
        }
    };
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public BalanceData g;

    public GetConfidentialitySettingsData() {
        this.g = new BalanceData();
    }

    public GetConfidentialitySettingsData(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        this.d = parcel.readInt();
        this.e = parcel.readInt();
        this.f = parcel.readInt();
        this.g = new BalanceData(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeInt(this.d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
        this.g.writeToParcel(parcel, i);
    }
}
