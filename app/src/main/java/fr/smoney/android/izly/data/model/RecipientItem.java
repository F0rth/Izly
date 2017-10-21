package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class RecipientItem implements Parcelable {
    public static final Creator<RecipientItem> CREATOR = new Creator<RecipientItem>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new RecipientItem(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new RecipientItem[i];
        }
    };
    public String a;
    public String b;
    public double c = -1.0d;
    public double d = -1.0d;
    public boolean e;

    public RecipientItem(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readString();
        this.c = parcel.readDouble();
        this.d = parcel.readDouble();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.e = z;
    }

    public RecipientItem(String str, boolean z) {
        this.a = str;
        this.b = str;
        this.e = false;
    }

    public final double a() {
        return this.d != -1.0d ? this.d : this.c != -1.0d ? this.c : -1.0d;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.d);
        parcel.writeInt(this.e ? 1 : 0);
    }
}
