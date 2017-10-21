package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CardPaymentsData implements Parcelable {
    public static final Creator<CardPaymentsData> CREATOR = new Creator<CardPaymentsData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CardPaymentsData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CardPaymentsData[i];
        }
    };
    public String a;

    public CardPaymentsData(Parcel parcel) {
        this.a = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
    }
}
