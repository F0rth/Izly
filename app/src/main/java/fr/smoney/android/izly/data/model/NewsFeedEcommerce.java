package fr.smoney.android.izly.data.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.Html;
import android.text.Spanned;

import fr.smoney.android.izly.R;

public class NewsFeedEcommerce extends NewsFeedPaymentFeedItem {
    public static final Creator<NewsFeedPaymentFeedItem> CREATOR = new Creator<NewsFeedPaymentFeedItem>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NewsFeedEcommerce(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NewsFeedEcommerce[i];
        }
    };
    public String a;

    public NewsFeedEcommerce(Parcel parcel) {
        super(parcel);
    }

    public final Spanned a(Context context, String str) {
        boolean z = this.e.d;
        String a = NewsFeedItem.a(context, str, this.g);
        int i = -1;
        switch (this.d) {
            case PaymentEcommerce:
                i = R.string.home_news_feed_ecommerce_debit;
                break;
            case EcommerceRefund:
                i = R.string.home_news_feed_ecommerce_refund;
                break;
        }
        return Html.fromHtml(context.getString(i, new Object[]{this.e.b, a}));
    }

    public final void a(Parcel parcel) {
        super.a(parcel);
        this.a = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.a);
    }
}
