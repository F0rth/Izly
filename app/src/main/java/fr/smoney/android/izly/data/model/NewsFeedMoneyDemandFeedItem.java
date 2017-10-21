package fr.smoney.android.izly.data.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.Html;
import android.text.Spanned;

import fr.smoney.android.izly.R;

public class NewsFeedMoneyDemandFeedItem extends NewsFeedPaymentFeedItem {
    public static final Creator<NewsFeedMoneyDemandFeedItem> CREATOR = new Creator<NewsFeedMoneyDemandFeedItem>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NewsFeedMoneyDemandFeedItem(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NewsFeedMoneyDemandFeedItem[i];
        }
    };
    public int a;

    public NewsFeedMoneyDemandFeedItem(Parcel parcel) {
        super(parcel);
    }

    public final Spanned a(Context context, String str) {
        boolean z = this.e.d;
        String a = NewsFeedItem.a(context, str, this.g);
        if (this.a == 1) {
            if (a == null) {
                return Html.fromHtml(context.getString(R.string.home_news_feed_payment_money_demand_without_amount, new Object[]{this.e.b, a}));
            }
            return Html.fromHtml(context.getString(R.string.home_news_feed_payment_money_demand, new Object[]{this.e.b, a}));
        } else if (a == null) {
            return Html.fromHtml(context.getResources().getQuantityString(R.plurals.home_news_feed_payment_money_demand_mult_without_amount, this.a - 1, new Object[]{this.e.b, Integer.valueOf(this.a - 1)}));
        } else {
            return Html.fromHtml(context.getResources().getQuantityString(R.plurals.home_news_feed_payment_money_demand_mult, this.a - 1, new Object[]{this.e.b, a, Integer.valueOf(this.a - 1)}));
        }
    }

    public final void a(Parcel parcel) {
        super.a(parcel);
        this.a = parcel.readInt();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.a);
    }
}
