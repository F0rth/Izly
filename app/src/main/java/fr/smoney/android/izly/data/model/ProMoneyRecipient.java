package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ProMoneyRecipient implements Parcelable {
    public static final Creator<ProMoneyRecipient> CREATOR = new Creator<ProMoneyRecipient>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ProMoneyRecipient(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ProMoneyRecipient[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public float d;

    public ProMoneyRecipient(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readFloat();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeFloat(this.d);
    }
}
