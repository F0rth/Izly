package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class NewsFeedContactLight implements Parcelable {
    public static final Creator<NewsFeedContactLight> CREATOR = new Creator<NewsFeedContactLight>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NewsFeedContactLight(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NewsFeedContactLight[i];
        }
    };
    public String a;
    public String b;
    public boolean c = false;
    public boolean d = false;

    public NewsFeedContactLight(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.d = z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c ? 1 : 0);
        if (!this.d) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
