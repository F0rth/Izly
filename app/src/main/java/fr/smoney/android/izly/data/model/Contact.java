package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Contact implements Parcelable, Comparable<Contact> {
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new Contact(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Contact[i];
        }
    };
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public b f;
    public int g = a.b;
    public int h;
    public boolean i = false;
    public String j;

    public enum a {
        ;

        static {
            a = 1;
            b = 2;
            c = 3;
            d = new int[]{a, b, c};
        }

        public static int[] a() {
            return (int[]) d.clone();
        }
    }

    public enum b {
        Unknown,
        SmoneyUserPart,
        SmoneyUserPro
    }

    public Contact(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = (b) parcel.readSerializable();
        this.i = parcel.readInt() == 1;
        this.j = parcel.readString();
        this.g = a.a()[parcel.readInt()];
        this.h = parcel.readInt();
    }

    public /* synthetic */ int compareTo(Object obj) {
        return this.b.compareToIgnoreCase(((Contact) obj).b);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeSerializable(this.f);
        parcel.writeInt(this.i ? 1 : 0);
        parcel.writeString(this.j);
        parcel.writeInt(this.g - 1);
        parcel.writeInt(this.h);
    }
}
