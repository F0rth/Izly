package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

import fr.smoney.android.izly.data.model.NewsFeedItem.a;

public class NewsFeedCommissionOrPass extends NewsFeedItem {
    public static final Creator<NewsFeedCommissionOrPass> CREATOR = new Creator<NewsFeedCommissionOrPass>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NewsFeedCommissionOrPass(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NewsFeedCommissionOrPass[i];
        }
    };
    public double a;

    public static final /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] a = new int[a.values().length];

        static {
            try {
                a[a.Commission.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.CommissionRefund.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.AchatPass.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public NewsFeedCommissionOrPass(Parcel parcel) {
        super(parcel);
    }

    protected final int a() {
        return 1;
    }

    public final void a(Parcel parcel) {
        super.a(parcel);
        this.a = parcel.readDouble();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(this.a);
    }
}
