package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetNewsFeedDetailsData;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedPromoOfferFeedItem;
import fr.smoney.android.izly.data.model.PromotionalOffer;
import fr.smoney.android.izly.data.model.PromotionalOffer.a;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PromotionalOfferDetails extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private static final String b = PromotionalOfferDetails.class.getSimpleName();
    private PromotionalOffer c;
    private WebView d;
    private ProgressBar e;
    private FrameLayout f;
    private boolean g = false;
    private View h;
    private TextView i;
    private TextView j;
    private TextView k;
    private TextView l;
    private TextView m;
    private ImageView n;
    private View o;
    private ImageView p;
    private TextView q;
    private TextView r;
    private TextView s;
    private NewsFeedItem t;

    private void a(GetNewsFeedDetailsData getNewsFeedDetailsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getNewsFeedDetailsData == null) {
            a(hw.a(this, this));
        } else {
            this.c = getNewsFeedDetailsData.b;
            if (this.c.k != null) {
                setTitle(this.c.k);
            } else {
                setTitle(this.c.c);
            }
            k();
        }
    }

    private void a(NewsFeedItem newsFeedItem) {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, newsFeedItem), 227, true);
    }

    private void k() {
        this.f.setVisibility(8);
        this.h.setVisibility(0);
        PromotionalOffer promotionalOffer = this.c;
        this.j.setText(promotionalOffer.k);
        this.k.setText(promotionalOffer.d);
        this.i.setText(promotionalOffer.i);
        this.l.setText(promotionalOffer.e);
        if (promotionalOffer.g <= 0 || promotionalOffer.h <= 0) {
            this.m.setVisibility(8);
        } else {
            String format;
            String format2;
            Calendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTimeInMillis(promotionalOffer.g);
            Calendar gregorianCalendar2 = new GregorianCalendar();
            gregorianCalendar2.setTimeInMillis(promotionalOffer.h);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            if (gregorianCalendar.get(1) == gregorianCalendar2.get(1)) {
                simpleDateFormat.applyPattern("dd MMMMM");
                format = simpleDateFormat.format(gregorianCalendar.getTime());
                simpleDateFormat.applyLocalizedPattern("dd MMMMM yyyy");
                format2 = simpleDateFormat.format(gregorianCalendar2.getTime());
            } else {
                simpleDateFormat.applyPattern("dd MMMMM yyyy");
                format = simpleDateFormat.format(gregorianCalendar.getTime());
                format2 = simpleDateFormat.format(gregorianCalendar2.getTime());
            }
            this.m.setText(getString(R.string.promotional_offers_details_validity, new Object[]{format, format2}));
        }
        this.a.a.displayImage(jl.a(promotionalOffer.j), this.p);
        this.q.setText(promotionalOffer.k);
        this.r.setText(promotionalOffer.i);
        if (promotionalOffer.l != -1.0d) {
            StringBuilder stringBuilder = new StringBuilder(PromotionalOffer.n.format(promotionalOffer.l / 1000.0d));
            stringBuilder.append(" ");
            stringBuilder.append(getString(R.string.contact_details_distance_unit));
            this.s.setText(stringBuilder.toString());
        } else {
            this.s.setVisibility(4);
        }
        this.a.a.displayImage(jl.b(promotionalOffer.a), this.n);
    }

    public final void a(ie ieVar, Bundle bundle) {
        int h = h();
        switch (ieVar) {
            case ConnexionErrorType:
                if (h == 227) {
                    a(this.t);
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 227) {
                a((GetNewsFeedDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNewsFeedDetails"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        int h = h();
        switch (ieVar) {
            case ConnexionErrorType:
                if (h == 227) {
                    finish();
                    return;
                } else {
                    super.b(ieVar);
                    return;
                }
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 227) {
            a(i2.bx, i2.by);
        }
    }

    public final void c(ie ieVar) {
        int h = h();
        switch (ieVar) {
            case ErrorType:
                if (h == 227) {
                    finish();
                    return;
                }
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        int h = h();
        if (ieVar == ie.ProgressType && h == 227) {
            g();
            finish();
            return;
        }
        super.d(ieVar);
    }

    public void onClick(View view) {
        if (view == this.o) {
            Intent a = is.a(this, ContactDetailsActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.contactId", this.c);
            startActivity(a);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.promotional_offers_details);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.d = (WebView) findViewById(R.id.wv_global_offer);
        this.e = (ProgressBar) findViewById(R.id.pb_global_offer);
        this.f = (FrameLayout) findViewById(R.id.fl_global_offer);
        this.h = findViewById(R.id.promotional_offer_detail_pro);
        this.n = (ImageView) findViewById(R.id.promo_detail_image);
        this.j = (TextView) findViewById(R.id.promo_detail_name);
        this.k = (TextView) findViewById(R.id.promo_detail_title);
        this.i = (TextView) findViewById(R.id.promo_detail_activity);
        this.l = (TextView) findViewById(R.id.promo_detail_description);
        this.m = (TextView) findViewById(R.id.promo_detail_validity);
        this.o = findViewById(R.id.ll_related_pro);
        this.o.setOnClickListener(this);
        this.p = (ImageView) findViewById(R.id.aiv_related_pro);
        this.q = (TextView) findViewById(R.id.tv_related_pro_name);
        this.r = (TextView) findViewById(R.id.tv_related_pro_activity);
        this.s = (TextView) findViewById(R.id.tv_related_pro_distance);
        Parcelable parcelableExtra = getIntent().getParcelableExtra("fr.smoney.android.izly.promo");
        if (parcelableExtra == null) {
            finish();
        } else if (parcelableExtra instanceof PromotionalOffer) {
            this.c = (PromotionalOffer) parcelableExtra;
            if (this.c.k != null) {
                setTitle(this.c.k);
            } else {
                setTitle(this.c.c);
            }
            if (this.c.m == a.GLOBAL) {
                this.d.setWebViewClient(new WebViewClient(this) {
                    final /* synthetic */ PromotionalOfferDetails a;

                    {
                        this.a = r1;
                    }

                    public final void onPageFinished(WebView webView, String str) {
                        super.onPageFinished(webView, str);
                        this.a.g = true;
                        this.a.e.setVisibility(8);
                    }

                    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
                        if (str.endsWith("/")) {
                            str = str.substring(0, str.length() - 1);
                        }
                        if (Uri.parse(str).equals(Uri.parse(this.a.c.f)) || !this.a.g) {
                            return false;
                        }
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setData(Uri.parse(str));
                        this.a.startActivity(intent);
                        return true;
                    }
                });
                this.d.loadUrl(this.c.f);
                this.d.setBackgroundColor(0);
                return;
            }
            k();
        } else if (parcelableExtra instanceof NewsFeedPromoOfferFeedItem) {
            this.t = (NewsFeedPromoOfferFeedItem) parcelableExtra;
            a(this.t);
        }
    }
}
