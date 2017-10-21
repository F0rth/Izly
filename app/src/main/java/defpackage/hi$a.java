package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.NewsFeedChatItem;
import fr.smoney.android.izly.data.model.NewsFeedChatItem.a;
import fr.smoney.android.izly.data.model.NewsFeedCommissionOrPass;
import fr.smoney.android.izly.data.model.NewsFeedCommissionOrPass.AnonymousClass2;
import fr.smoney.android.izly.data.model.NewsFeedHeader;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedMoneyDemandFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedMoneyOperationFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedPaymentFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedPreAuthorization;
import fr.smoney.android.izly.data.model.NewsFeedPromoOfferFeedItem;
import fr.smoney.android.izly.data.model.PromotionalOffer;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

final class hi$a extends BaseAdapter implements OnItemClickListener {
    List<NewsFeedItem> a = new ArrayList();
    final /* synthetic */ hi b;
    private LayoutInflater c;
    private Context d;
    private Resources e;

    public hi$a(hi hiVar, Context context) {
        this.b = hiVar;
        this.d = context;
        this.c = (LayoutInflater) context.getSystemService("layout_inflater");
        this.e = this.d.getResources();
    }

    public final NewsFeedItem a(int i) {
        return (NewsFeedItem) this.a.get(i);
    }

    public final int getCount() {
        return this.a.size();
    }

    public final /* synthetic */ Object getItem(int i) {
        return a(i);
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final int getItemViewType(int i) {
        NewsFeedItem a = a(i);
        return a.b == 3 ? 0 : a.b != 2 ? 1 : 2;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        hi$c hi_c;
        String symbol;
        int itemViewType = getItemViewType(i);
        if (view == null) {
            hi$c hi_c2 = new hi$c(this.b);
            switch (itemViewType) {
                case 0:
                    view = this.c.inflate(R.layout.listitem_news_header, viewGroup, false);
                    hi_c2.c = (TextView) view.findViewById(R.id.tv_news_feed_date);
                    break;
                case 1:
                    view = this.c.inflate(R.layout.listitem_news_feed, viewGroup, false);
                    hi_c2.b = (TextView) view.findViewById(R.id.tv_news_feed_message);
                    hi_c2.a = (ImageView) view.findViewById(R.id.aiv_news_feed_recipient_photo);
                    hi_c2.a.setOnClickListener(this.b.M);
                    hi_c2.c = (TextView) view.findViewById(R.id.tv_news_feed_date);
                    break;
                case 2:
                    view = this.c.inflate(R.layout.listitem_news_promo, viewGroup, false);
                    hi_c2.a = (ImageView) view.findViewById(R.id.aiv_news_feed_recipient_photo);
                    hi_c2.a.setOnClickListener(this.b.M);
                    hi_c2.d = (TextView) view.findViewById(R.id.tv_news_feed_promo_title);
                    hi_c2.e = (TextView) view.findViewById(R.id.tv_news_feed_promo_activity);
                    hi_c2.f = (TextView) view.findViewById(R.id.tv_news_feed_promo_subtitle);
                    break;
            }
            view.setTag(hi_c2);
            hi_c = hi_c2;
        } else {
            hi_c = (hi$c) view.getTag();
        }
        NewsFeedItem a = a(i);
        if (itemViewType == 1) {
            hi_c.a.setImageResource(R.drawable.icon_home_placeholder);
            hi_c.a.setEnabled(a.e != null);
            hi_c.b.setText("");
            hi_c.c.setText("");
        } else if (itemViewType == 2) {
            hi_c.a.setImageResource(R.drawable.icon_home_placeholder);
            hi_c.a.setEnabled(a.e != null);
            hi_c.d.setText("");
            hi_c.f.setText("");
            hi_c.e.setText("");
        } else if (itemViewType == 0) {
            hi_c.c.setText("");
        }
        try {
            symbol = Currency.getInstance(this.b.B.b.j).getSymbol(Locale.FRANCE);
        } catch (NullPointerException e) {
            symbol = ";-";
        }
        if (a instanceof NewsFeedChatItem) {
            NewsFeedChatItem newsFeedChatItem = (NewsFeedChatItem) a;
            hi_c.a.setImageDrawable(this.e.getDrawable(R.drawable.icon_home_message));
            TextView textView = hi_c.b;
            Context context = this.d;
            String str = null;
            if (newsFeedChatItem.a == a.InDirection) {
                str = context.getString(R.string.home_news_feed_chat_in, new Object[]{newsFeedChatItem.e.b});
            } else if (newsFeedChatItem.a == a.OutDirection) {
                str = context.getString(R.string.home_news_feed_chat_out, new Object[]{newsFeedChatItem.e.b});
            }
            textView.setText(Html.fromHtml(str));
            hi_c.c.setText(jk.d(this.d, newsFeedChatItem.f));
        } else if (a instanceof NewsFeedMoneyDemandFeedItem) {
            NewsFeedMoneyDemandFeedItem newsFeedMoneyDemandFeedItem = (NewsFeedMoneyDemandFeedItem) a;
            if ((newsFeedMoneyDemandFeedItem.a > 1 ? 1 : null) != null) {
                hi_c.a.setImageDrawable(this.e.getDrawable(R.drawable.icon_home_demande));
            }
            hi_c.b.setText(newsFeedMoneyDemandFeedItem.a(this.d, symbol));
            hi_c.c.setText(jk.d(this.d, newsFeedMoneyDemandFeedItem.f));
        } else if (a instanceof NewsFeedPaymentFeedItem) {
            NewsFeedPaymentFeedItem newsFeedPaymentFeedItem = (NewsFeedPaymentFeedItem) a;
            if (newsFeedPaymentFeedItem.d == NewsFeedItem.a.PaymentDistributor) {
                hi_c.a.setImageDrawable(this.e.getDrawable(R.drawable.icon_home_distrib));
            }
            hi_c.b.setText(newsFeedPaymentFeedItem.a(this.d, symbol));
            hi_c.c.setText(jk.d(this.d, newsFeedPaymentFeedItem.f));
        } else if (a instanceof NewsFeedMoneyOperationFeedItem) {
            NewsFeedMoneyOperationFeedItem newsFeedMoneyOperationFeedItem = (NewsFeedMoneyOperationFeedItem) a;
            hi_c.b.setText(newsFeedMoneyOperationFeedItem.a(this.d, symbol));
            hi_c.c.setText(jk.d(this.d, newsFeedMoneyOperationFeedItem.f));
            if (newsFeedMoneyOperationFeedItem.d == NewsFeedItem.a.MoneyIn || newsFeedMoneyOperationFeedItem.d == NewsFeedItem.a.MoneyInCash) {
                hi_c.a.setImageDrawable(this.e.getDrawable(R.drawable.icon_home_moneyin));
            } else if (newsFeedMoneyOperationFeedItem.d == NewsFeedItem.a.MoneyOut) {
                hi_c.a.setImageDrawable(this.e.getDrawable(R.drawable.icon_home_moneyout));
            } else if (newsFeedMoneyOperationFeedItem.d == NewsFeedItem.a.MoneyOutRefund) {
                hi_c.a.setImageDrawable(this.e.getDrawable(R.drawable.list_aiv_avatar_smoney));
            }
        } else if (a instanceof NewsFeedCommissionOrPass) {
            NewsFeedCommissionOrPass newsFeedCommissionOrPass = (NewsFeedCommissionOrPass) a;
            TextView textView2 = hi_c.b;
            Context context2 = this.d;
            int i2 = -1;
            switch (AnonymousClass2.a[newsFeedCommissionOrPass.d.ordinal()]) {
                case 1:
                    i2 = R.string.home_news_feed_commission;
                    break;
                case 2:
                    i2 = R.string.home_news_feed_commission_refund;
                    break;
                case 3:
                    i2 = R.string.home_news_feed_achat_pass;
                    break;
            }
            Object[] objArr = new Object[1];
            objArr[0] = String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(newsFeedCommissionOrPass.a), symbol});
            textView2.setText(Html.fromHtml(context2.getString(i2, objArr)));
            hi_c.c.setText(jk.d(this.d, a.f));
        } else if (a instanceof NewsFeedPromoOfferFeedItem) {
            NewsFeedPromoOfferFeedItem newsFeedPromoOfferFeedItem = (NewsFeedPromoOfferFeedItem) a;
            hi_c.d.setText(newsFeedPromoOfferFeedItem.a);
            hi_c.e.setText(newsFeedPromoOfferFeedItem.g);
            hi_c.f.setText(newsFeedPromoOfferFeedItem.h);
        } else if (a instanceof NewsFeedPreAuthorization) {
            NewsFeedPreAuthorization newsFeedPreAuthorization = (NewsFeedPreAuthorization) a;
            hi_c.b.setText(Html.fromHtml(this.d.getString(R.string.home_news_feed_pre_authorization, new Object[]{newsFeedPreAuthorization.e.b})));
            hi_c.c.setText(jk.d(this.d, a.f));
        } else if (a instanceof NewsFeedHeader) {
            hi_c.c.setText(jk.a(this.d, ((NewsFeedHeader) a).f).toUpperCase(Locale.getDefault()));
        }
        return view;
    }

    public final int getViewTypeCount() {
        return 3;
    }

    public final boolean isEnabled(int i) {
        return getItemViewType(i) != 0;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        NewsFeedItem a = a(i - this.b.k.getHeaderViewsCount());
        if (!this.b.I) {
            if (a.c()) {
                hi.a(this.b, a);
            } else if (a.d()) {
                hi.b(this.b, a);
            } else if (a.e()) {
                hi.c(this.b, a);
            } else if (a.b()) {
                Parcelable parcelable = (NewsFeedPromoOfferFeedItem) a;
                if (parcelable.j == NewsFeedPromoOfferFeedItem.a.SmoneyPromo) {
                    hi.a(this.b, new PromotionalOffer((NewsFeedPromoOfferFeedItem) parcelable));
                    return;
                }
                hi.a(this.b, parcelable);
            } else if (a.f()) {
                hi.d(this.b, a);
            }
        }
    }
}
