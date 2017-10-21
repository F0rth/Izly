package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import defpackage.ht;
import defpackage.hw;
import defpackage.ie;
import defpackage.if$a;
import defpackage.iv;
import defpackage.jh;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetNewsFeedDetailsData;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedPreAuthorization;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class PreAuthorizePaymentDetailsActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a, if$a {
    private View b;
    private View c;
    private ImageView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;
    private TextView j;
    private TextView k;
    private ImageView l;
    private TextView m;
    private Button n;
    private TextView o;
    private PreAuthorizationContainerData p;
    private NewsFeedPreAuthorization q;
    private String r;
    private SimpleDateFormat s = new SimpleDateFormat("'Le' dd/MM/yyyy 'à' HH':'mm", Locale.getDefault());
    private ge t;

    private void a(GetNewsFeedDetailsData getNewsFeedDetailsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getNewsFeedDetailsData == null) {
            a(hw.a(this, this));
        } else {
            this.p = getNewsFeedDetailsData.c;
            k();
        }
    }

    private void a(NewsFeedItem newsFeedItem) {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, newsFeedItem), 227, true);
    }

    private void b(ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else {
            finish();
        }
    }

    private void k() {
        this.b.setVisibility(0);
        this.e.setText(R.string.pre_authorize_recipient_payto);
        this.t.a.displayImage(jl.a(this.p.a.a), this.d);
        this.f.setText(this.p.a.b);
        this.g.setText(this.p.a.a);
        switch (this.p.a.f) {
            case SmoneyUserPart:
                this.h.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.confirm_result_tv_recipient_is_client_logo), null, null, null);
                this.h.setText(R.string.contact_details_tv_recipient_is_client_part);
                break;
            case SmoneyUserPro:
                this.h.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.confirm_result_tv_recipient_is_client_logo_pro), null, null, null);
                this.h.setText(R.string.contact_details_tv_recipient_is_client_pro);
                break;
            default:
                this.h.setVisibility(8);
                break;
        }
        TextView textView = this.i;
        double d = this.p.b;
        textView.setText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.r}));
        this.j.setText(this.s.format(new Date(this.p.c)));
        String str = this.p.d.a;
        this.k.setText(jh.c(str));
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        try {
            this.l.setImageBitmap(iv.a(str, displayMetrics.widthPixels, (int) (((double) displayMetrics.widthPixels) * 0.3d), -16777216, -1));
        } catch (WriterException e) {
        }
        this.o.setVisibility(0);
        switch (this.p.e) {
            case Canceled:
                this.o.setText(getString(R.string.pre_authorize_cancel_status));
                this.o.setBackground(getResources().getDrawable(R.drawable.bg_header_status_canceled));
                this.o.setCompoundDrawablesWithIntrinsicBounds(R.drawable.header_status_annule, 0, 0, 0);
                this.m.setVisibility(8);
                this.n.setVisibility(8);
                this.c.setVisibility(8);
                return;
            case Expired:
                this.o.setText(getString(R.string.pre_authorize_expiration_status));
                this.o.setBackground(getResources().getDrawable(R.drawable.bg_header_status_canceled));
                this.o.setCompoundDrawablesWithIntrinsicBounds(R.drawable.header_status_annule, 0, 0, 0);
                this.m.setVisibility(8);
                this.n.setVisibility(8);
                this.c.setVisibility(8);
                return;
            case Accepted:
                this.o.setText(getString(R.string.pre_authorize_validate_status));
                this.m.setVisibility(8);
                this.n.setVisibility(8);
                this.c.setVisibility(8);
                return;
            default:
                this.o.setVisibility(8);
                return;
        }
    }

    private void l() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        Parcelable parcelable = this.p;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 238 && intent.getStringExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationSessionId").equals(str2) && intent.getParcelableExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationInfos").equals(parcelable)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 238);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.cancelPreAuthorizationInfos", parcelable);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bP = null;
        j.f.bQ = null;
        super.a(keyAt, 238, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case CancelPreAuthorizationType:
                l();
                return;
            case ConnexionErrorType:
                if (h() == 238) {
                    l();
                    return;
                } else if (h() == 227) {
                    a(this.q);
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
            } else if (i2 == 238) {
                b(serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 227) {
            a(i2.bx, i2.by);
        } else if (i == 238) {
            b(i2.bQ);
        }
    }

    public void onClick(View view) {
        if (view == this.n) {
            a(ht.a(getString(R.string.pre_authorize_cancel_dialog_title), getString(R.string.pre_authorize_cancel_dialog_message), getString(17039379), getString(17039369), this, ie.CancelPreAuthorizationType));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.pre_authorize_details);
        this.t = new ge(this);
        this.p = (PreAuthorizationContainerData) getIntent().getParcelableExtra("INTENT_EXTRA_PRE_AUTHORIZATION");
        this.q = (NewsFeedPreAuthorization) getIntent().getParcelableExtra("INTENT_EXTRA_P2P_PAY_REQUEST_RELATED_FEED_ITEM");
        this.r = Currency.getInstance(i().b.j).getSymbol();
        if (bundle != null) {
            this.p = (PreAuthorizationContainerData) bundle.getParcelable("fr.smoney.android.izly.SAVED_PRE_AUTHORIZATION");
        }
        this.b = findViewById(R.id.container);
        this.d = (ImageView) findViewById(R.id.aiv_recipient_photo);
        this.e = (TextView) findViewById(R.id.tv_recipient_info);
        this.f = (TextView) findViewById(R.id.tv_recipient_name);
        this.g = (TextView) findViewById(R.id.tv_recipient_id);
        this.i = (TextView) findViewById(R.id.tv_max_amount);
        this.j = (TextView) findViewById(R.id.tv_expiration_date);
        this.k = (TextView) findViewById(R.id.tv_pre_authorization_card);
        this.m = (TextView) findViewById(R.id.tv_barcode_explanation);
        this.o = (TextView) findViewById(R.id.tv_pre_authorize_status);
        this.l = (ImageView) findViewById(R.id.iv_pre_authorization_card);
        this.c = findViewById(R.id.ll_pre_authorization_card);
        this.n = (Button) findViewById(R.id.bt_pre_authorize_cancel);
        this.n.setOnClickListener(this);
        if (this.p != null) {
            k();
        } else if (this.q != null) {
            a(this.q);
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("fr.smoney.android.izly.SAVED_PRE_AUTHORIZATION", this.p);
        super.onSaveInstanceState(bundle);
    }
}
