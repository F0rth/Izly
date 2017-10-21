package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.text.DecimalFormat;

public class PromotionalOffer implements Parcelable {
    public static final Creator<PromotionalOffer> CREATOR = new Creator<PromotionalOffer>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new PromotionalOffer(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new PromotionalOffer[i];
        }
    };
    public static final DecimalFormat n = new DecimalFormat("0.00");
    public int a;
    public boolean b;
    public String c;
    public String d;
    public String e;
    public String f;
    public long g;
    public long h;
    public String i;
    public String j = null;
    public String k = null;
    public double l = -1.0d;
    public a m = a.GEOLOCALIZED;

    public enum a {
        GLOBAL,
        GEOLOCALIZED
    }

    public PromotionalOffer(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.b = z;
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readLong();
        this.h = parcel.readLong();
        this.i = parcel.readString();
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.l = parcel.readDouble();
        this.m = (a) parcel.readSerializable();
    }

    public PromotionalOffer(NewsFeedPromoOfferFeedItem newsFeedPromoOfferFeedItem) {
        this.c = newsFeedPromoOfferFeedItem.a;
        this.f = newsFeedPromoOfferFeedItem.i;
        this.m = a.GLOBAL;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b ? 1 : 0);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeLong(this.g);
        parcel.writeLong(this.h);
        parcel.writeString(this.i);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeDouble(this.l);
        parcel.writeSerializable(this.m);
    }
}
