package fr.smoney.android.izly.data.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import fr.smoney.android.izly.R;

public class MoneyInCbCb implements Parcelable {
    public static final Creator<MoneyInCbCb> CREATOR = new Creator<MoneyInCbCb>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new MoneyInCbCb(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MoneyInCbCb[i];
        }
    };
    public String a;
    public String b;
    public int c;
    public String d;
    public boolean e;
    public boolean f;

    public MoneyInCbCb(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readInt();
        this.d = parcel.readString();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.e = z;
    }

    public static String a(Context context, int i) {
        switch (i) {
            case 0:
                return context.getString(R.string.cb_list_network_mastercard);
            case 1:
                return context.getString(R.string.cb_list_network_visa);
            case 2:
                return context.getString(R.string.cb_list_network_cb);
            default:
                return "";
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e ? 1 : 0);
    }
}
