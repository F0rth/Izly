package com.ad4screen.sdk.b;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.Date;
import java.util.HashMap;

public class c implements Parcelable {
    public static final Creator<c> CREATOR = new Creator<c>() {
        public final c a(Parcel parcel) {
            return new c(parcel);
        }

        public final c[] a(int i) {
            return new c[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public String a;
    public String b;
    public Date c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public a j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public boolean o;
    public String p;
    public a[] q;
    public HashMap<String, String> r;

    public enum a {
        System,
        Text,
        Push,
        Web,
        Event,
        Url,
        Close
    }

    public c() {
        this.q = new a[0];
    }

    private c(Parcel parcel) {
        int i = 0;
        this.q = new a[0];
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = new Date(Long.valueOf(parcel.readLong()).longValue());
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.j = a.valueOf(parcel.readString());
        this.i = parcel.readString();
        this.p = parcel.readString();
        boolean[] zArr = new boolean[5];
        parcel.readBooleanArray(zArr);
        this.n = zArr[0];
        this.k = zArr[1];
        this.l = zArr[2];
        this.m = zArr[3];
        this.o = zArr[4];
        Object readArray = parcel.readArray(getClass().getClassLoader());
        if (readArray != null) {
            this.q = new a[readArray.length];
            System.arraycopy(readArray, 0, this.q, 0, readArray.length);
        } else {
            this.q = new a[0];
        }
        this.r = new HashMap();
        int readInt = parcel.readInt();
        while (i < readInt) {
            this.r.put(parcel.readString(), parcel.readString());
            i++;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeLong(this.c.getTime());
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeString(this.j.name());
        parcel.writeString(this.i);
        parcel.writeString(this.p);
        parcel.writeBooleanArray(new boolean[]{this.n, this.k, this.l, this.m, this.o});
        parcel.writeArray(this.q);
        if (this.r != null) {
            parcel.writeInt(this.r.size());
            for (String str : this.r.keySet()) {
                parcel.writeString(str);
                parcel.writeString((String) this.r.get(str));
            }
            return;
        }
        parcel.writeInt(0);
    }
}
