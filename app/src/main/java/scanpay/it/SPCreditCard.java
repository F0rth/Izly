package scanpay.it;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import defpackage.ou;
import defpackage.s;

public class SPCreditCard implements Parcelable {
    public static final Creator CREATOR = new s();
    public String a;
    public ou b;
    public String c;
    public String d;
    public String e;

    public SPCreditCard() {
        this.b = ou.UNKNOWN;
        this.a = "";
        this.c = "";
        this.d = "";
        this.e = "";
    }

    public SPCreditCard(Parcel parcel) {
        this.a = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.b = ou.valueOf(parcel.readString());
    }

    public final String a() {
        String str;
        int i = 0;
        if (this.b == ou.AMERICAN_EXPRESS) {
            str = "";
            while (i < this.a.length()) {
                if (i == 4 || i == 10) {
                    str = str + " ";
                }
                str = str + this.a.charAt(i);
                i++;
            }
        } else {
            str = "";
            while (i < this.a.length()) {
                if (i != 0 && i % 4 == 0) {
                    str = str + " ";
                }
                str = str + this.a.charAt(i);
                i++;
            }
        }
        return str;
    }

    public final void a(String str) {
        if (str.length() > 0) {
            switch (str.charAt(0)) {
                case '3':
                    this.b = ou.AMERICAN_EXPRESS;
                    break;
                case '4':
                    this.b = ou.VISA;
                    break;
                case '5':
                    this.b = ou.MASTERCARD;
                    break;
                default:
                    this.b = ou.UNKNOWN;
                    break;
            }
        }
        this.a = str.replaceAll(" ", "");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.b.toString());
    }
}
