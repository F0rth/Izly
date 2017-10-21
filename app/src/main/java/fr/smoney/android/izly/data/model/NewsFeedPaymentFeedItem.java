package fr.smoney.android.izly.data.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.Html;
import android.text.Spanned;

import fr.smoney.android.izly.R;

public class NewsFeedPaymentFeedItem extends NewsFeedItem {
    public static final Creator<NewsFeedPaymentFeedItem> CREATOR = new Creator<NewsFeedPaymentFeedItem>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new NewsFeedPaymentFeedItem(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new NewsFeedPaymentFeedItem[i];
        }
    };
    public double g = -1.0d;
    public boolean h;
    public boolean i;

    public NewsFeedPaymentFeedItem(Parcel parcel) {
        super(parcel);
    }

    protected final int a() {
        return 1;
    }

    public Spanned a(Context context, String str) {
        int i = R.string.home_news_feed_payment_debit;
        boolean z = this.e.d;
        String a = NewsFeedItem.a(context, str, this.g);
        switch (this.d) {
            case P2PCredit:
                i = R.string.home_news_feed_payment_credit;
                break;
            case PaymentDistributor:
                break;
            case P2PDebit:
                if (this.e.d) {
                    i = R.string.home_news_feed_payment_debit_pro;
                    break;
                }
                break;
            case ToPayRequest:
                if (a != null) {
                    i = R.string.home_news_feed_payment_request;
                    break;
                }
                i = R.string.home_news_feed_payment_request_without_amount;
                break;
            case PaymentRefund:
                i = R.string.home_news_feed_payment_refund;
                break;
            default:
                i = -1;
                break;
        }
        return Html.fromHtml(context.getString(i, new Object[]{this.e.b, a}));
    }

    public void a(Parcel parcel) {
        boolean z = true;
        super.a(parcel);
        this.g = parcel.readDouble();
        this.h = parcel.readInt() == 1;
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.i = z;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        super.writeToParcel(parcel, i);
        parcel.writeDouble(this.g);
        parcel.writeInt(this.h ? 1 : 0);
        if (!this.i) {
            i2 = 0;
        }
        parcel.writeInt(i2);
    }
}
