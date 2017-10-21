package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import defpackage.jk;

public class Support implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new Support(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Support[i];
        }
    };
    public long a;
    public co b;
    public String c;
    public String d;
    public boolean e;
    public int f;
    private String g;
    private String h;

    public Support(long j, co coVar, String str, String str2, String str3, String str4, boolean z, int i) {
        this.a = j;
        this.h = str4;
        this.e = z;
        this.b = coVar;
        this.c = str;
        this.d = str2;
        this.g = str3;
        this.f = i;
    }

    public Support(Parcel parcel) {
        String[] strArr = new String[5];
        parcel.readStringArray(strArr);
        this.c = strArr[0];
        this.d = strArr[1];
        this.g = strArr[2];
        this.h = strArr[3];
        this.b = co.valueOf(strArr[4]);
        this.a = parcel.readLong();
        boolean[] zArr = new boolean[1];
        parcel.readBooleanArray(zArr);
        this.e = zArr[0];
        this.f = parcel.readInt();
    }

    public final String a() {
        return (this.g == null || this.g.length() == 0) ? null : jk.b(this.g);
    }

    public final String b() {
        return (this.h == null || this.h.length() == 0) ? null : jk.b(this.h);
    }

    public final boolean c() {
        return this.h != null && this.h.length() > 0;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "Support{id=" + this.a + ", type=" + this.b + ", crous='" + this.c + '\'' + ", label='" + this.d + '\'' + ", expiration='" + this.g + '\'' + ", opositeDate='" + this.h + '\'' + ", isOpositePermanent=" + this.e + '}';
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.c, this.d, this.g, this.h, this.b.name()});
        parcel.writeLong(this.a);
        parcel.writeBooleanArray(new boolean[]{this.e});
        parcel.writeInt(this.f);
    }
}
