package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TransactionMessage implements Parcelable {
    public static final Creator<TransactionMessage> CREATOR = new Creator<TransactionMessage>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new TransactionMessage(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new TransactionMessage[i];
        }
    };
    public long a;
    public long b;
    public String c;
    public String d;

    public TransactionMessage(Parcel parcel) {
        this.a = parcel.readLong();
        this.b = parcel.readLong();
        this.c = parcel.readString();
        this.d = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.a);
        parcel.writeLong(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
    }
}
