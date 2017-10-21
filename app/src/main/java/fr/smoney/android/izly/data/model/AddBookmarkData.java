package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class AddBookmarkData implements Parcelable {
    public static final Creator<AddBookmarkData> CREATOR = new Creator<AddBookmarkData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new AddBookmarkData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new AddBookmarkData[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public boolean d;

    public AddBookmarkData(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.d = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeInt(this.d ? 1 : 0);
    }
}
