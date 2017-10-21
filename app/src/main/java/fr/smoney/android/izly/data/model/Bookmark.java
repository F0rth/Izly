package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import defpackage.jf;

public class Bookmark implements Parcelable, Comparable<Bookmark> {
    public static final Creator<Bookmark> CREATOR = new Creator<Bookmark>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new Bookmark(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Bookmark[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public boolean d;

    public Bookmark(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.d = z;
    }

    public /* synthetic */ int compareTo(Object obj) {
        Bookmark bookmark = (Bookmark) obj;
        return jf.a(this.c, this.b, this.a).compareToIgnoreCase(jf.a(bookmark.c, bookmark.b, bookmark.a));
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
