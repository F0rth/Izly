package com.ad4screen.sdk.service.modules.k.c;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.Date;

public class e implements Parcelable {
    public static final Creator<e> CREATOR = new Creator<e>() {
        public final e a(Parcel parcel) {
            return new e(parcel);
        }

        public final e[] a(int i) {
            return new e[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    private String a;
    private String b;
    private Date c;
    private String d;

    private e(Parcel parcel) {
        this.a = parcel.readString();
        this.c = new Date(parcel.readLong());
        this.d = parcel.readString();
    }

    protected e(String str, String str2, Date date, String str3) {
        this.a = str;
        this.b = str2;
        this.d = str3;
        a(date);
    }

    public e(String str, Date date) {
        this.a = str;
        a(date);
    }

    protected e(String str, Date date, String str2) {
        this.a = str;
        a(date);
        this.d = str2;
    }

    private void a(Date date) {
        if (date == null) {
            date = new Date(0);
        }
        this.c = date;
    }

    public String a() {
        return this.d;
    }

    public String b() {
        return this.a;
    }

    public Date c() {
        return this.c;
    }

    public String d() {
        return this.b;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            e eVar = (e) obj;
            if (this.c == null) {
                if (eVar.c != null) {
                    return false;
                }
            } else if (!this.c.equals(eVar.c)) {
                return false;
            }
            if (this.a == null) {
                if (eVar.a != null) {
                    return false;
                }
            } else if (!this.a.equals(eVar.a)) {
                return false;
            }
            if (this.d == null) {
                if (eVar.d != null) {
                    return false;
                }
            } else if (!this.d.equals(eVar.d)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.c == null ? 0 : this.c.hashCode();
        int hashCode2 = this.a == null ? 0 : this.a.hashCode();
        if (this.d != null) {
            i = this.d.hashCode();
        }
        return ((((hashCode + 31) * 31) + hashCode2) * 31) + i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeLong(this.c.getTime());
        parcel.writeString(this.d);
    }
}
