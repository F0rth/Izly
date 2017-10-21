package fr.smoney.android.izly.data.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.Html;
import android.text.Spanned;

import fr.smoney.android.izly.R;

public class NewsFeedMoneyOperationFeedItem extends NewsFeedItem {
    public static final Creator<NewsFeedMoneyOperationFeedItem> CREATOR = new Creator<NewsFeedMoneyOperationFeedItem>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NewsFeedMoneyOperationFeedItem(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NewsFeedMoneyOperationFeedItem[i];
        }
    };
    public double a = -1.0d;

    public NewsFeedMoneyOperationFeedItem(Parcel parcel) {
        super(parcel);
    }

    protected final int a() {
        return 1;
    }

    public final Spanned a(Context context, String str) {
        String a = NewsFeedItem.a(context, str, this.a);
        int i = -1;
        switch (this.d) {
            case MoneyIn:
                i = R.string.home_news_feed_money_in;
                break;
            case MoneyOut:
                i = R.string.home_news_feed_money_out;
                break;
            case MoneyOutRefund:
                i = R.string.home_news_feed_money_refund;
                break;
            case MoneyInRefund:
                i = R.string.home_news_feed_money_in_refund;
                break;
            case PayInCard:
                i = R.string.home_news_feed_pay_in_card;
                break;
            case PayInCardRefund:
                i = R.string.home_news_feed_pay_in_card_refund;
                break;
            case MoneyInVme:
                i = R.string.home_news_feed_money_in_vme;
                break;
        }
        return Html.fromHtml(context.getString(i, new Object[]{a}));
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
