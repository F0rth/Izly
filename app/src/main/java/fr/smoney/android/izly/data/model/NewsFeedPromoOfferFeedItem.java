package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;

public class NewsFeedPromoOfferFeedItem extends NewsFeedItem implements Parcelable {
    public static final Creator<NewsFeedPromoOfferFeedItem> CREATOR = new Creator<NewsFeedPromoOfferFeedItem>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NewsFeedPromoOfferFeedItem(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NewsFeedPromoOfferFeedItem[i];
        }
    };
    private static final SparseArray<a> k;
    public String a;
    public String g;
    public String h;
    public String i;
    public a j;

    public enum a {
        SmoneyPromo(0),
        LocalizedPromo(1);

        int c;

        private a(int i) {
            this.c = 0;
            this.c = i;
        }
    }

    static {
        SparseArray sparseArray = new SparseArray();
        for (a aVar : a.values()) {
            sparseArray.append(aVar.c, aVar);
        }
        k = sparseArray;
    }

    public NewsFeedPromoOfferFeedItem(Parcel parcel) {
        super(parcel);
    }

    public static a a(int i) {
        return (a) k.get(i);
    }

    protected final int a() {
        return 2;
    }

    public final void a(Parcel parcel) {
        super.a(parcel);
        this.a = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.a);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeString(this.i);
    }
}
