package fr.smoney.android.izly.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import defpackage.ht;
import defpackage.hu;
import defpackage.hv;
import defpackage.hw;
import defpackage.hy;
import defpackage.ie;
import defpackage.il;
import defpackage.in;
import defpackage.is;
import defpackage.iw;
import defpackage.jf;
import defpackage.jh;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetAttachmentData;
import fr.smoney.android.izly.data.model.GetNewsFeedDetailsData;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.P2PPayRequest;
import fr.smoney.android.izly.data.model.P2PPayRequestData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;

import org.spongycastle.crypto.tls.CipherSuite;

public class P2PPayRequestActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private P2PPayRequestData A;
    private boolean B;
    private ge C;
    private TextView b;
    private ImageView c;
    private DetailTwoText d;
    private DetailTwoText e;
    private DetailTwoText f;
    private LinearLayout g;
    private ImageView h;
    private ImageView i;
    private TextView j;
    private TextView k;
    private TextView l;
    private LinearLayout m;
    private EditText n;
    private LinearLayout o;
    private TextView p;
    private TextView q;
    private Button r;
    private LinearLayout s;
    private Button t;
    private Button u;
    private double v;
    private int w;
    private Button x;
    private P2PPayRequest y;
    private NewsFeedItem z;

    private void a(long j, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("ResultIntentOpIdToHide", j);
        setResult(5, intent);
        finish();
    }

    private void a(GetAttachmentData getAttachmentData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getAttachmentData == null) {
            a(hw.a(this, this));
        } else if (i().aA.c) {
            a(hu.a(getString(R.string.dialog_error_title), getString(R.string.p2p_pay_request_attachement_invalid), getString(17039370)));
        } else {
            iw.b((Context) this);
        }
    }

    private void a(GetNewsFeedDetailsData getNewsFeedDetailsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getNewsFeedDetailsData == null) {
            a(hw.a(this, this));
        } else {
            this.y = (P2PPayRequest) getNewsFeedDetailsData.a;
            k();
        }
    }

    private void a(NewsFeedItem newsFeedItem) {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, newsFeedItem), 227, true);
    }

    private void a(P2PPayRequest p2PPayRequest, int i) {
        Intent a = is.a(this, P2PPayRequestConfirmActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.p2pPayRequest", p2PPayRequest);
        a.putExtra("fr.smoney.android.izly.extras.responseStatus", i);
        startActivity(a);
    }

    private void a(P2PPayRequestData p2PPayRequestData) {
        if (p2PPayRequestData.b) {
            a(ht.a(getString(R.string.dialog_insufficient_found_title), getString(R.string.dialog_insufficient_found_message), getString(17039370), getString(17039360), this, ie.InsufficientFundType));
            return;
        }
        p2PPayRequestData.a.l = this.n.getText().toString();
        a(p2PPayRequestData.a, this.w);
    }

    private void a(P2PPayRequestData p2PPayRequestData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PPayRequestData == null) {
            a(hw.a(this, this));
        } else {
            this.A = p2PPayRequestData;
            this.A.a.p = this.y.p;
            this.A.a.o = this.y.o;
            this.A.a.v = this.y.v;
            this.y = this.A.a;
            i().b.B = p2PPayRequestData.g;
            if (this.w == 0) {
                a(this.A);
            }
        }
    }

    private void d(int i) {
        cl i2 = i();
        this.w = i;
        if (this.y.u) {
            if (i2.b.E <= 0) {
                a(ht.a(getString(R.string.p2p_pay_charity_dialog_title), getString(R.string.p2p_pay_charity_dialog_message_completion, new Object[]{this.y.f, this.y.f}), getString(17039370), getString(17039360), this, ie.PayToCharityNoAdressType));
                return;
            } else if (i2.b.D) {
                if (this.B) {
                    e(i);
                    return;
                }
                a(hv.a(getString(R.string.p2p_pay_charity_dialog_title), getString(R.string.p2p_pay_charity_dialog_message, new Object[]{this.y.f}), getString(17039370), this, ie.PayToCharityResult));
                return;
            } else if (this.B) {
                this.y.v = true;
            } else {
                a(ht.a(getString(R.string.p2p_pay_charity_dialog_title), getString(R.string.p2p_pay_charity_dialog_message_opt_in, new Object[]{this.y.f, this.y.f}), getString(17039370), getString(17039360), this, ie.PayToCharityNoOptInPartnersType));
                return;
            }
        }
        e(i);
    }

    private void e(int i) {
        cl i2 = i();
        double d = (i == 0 && this.y.g) ? this.v : this.y.h;
        super.a(j().a(i2.b.a, i2.b.c, this.y.a, i, this.n.getText().toString(), d), 73, true);
    }

    private void k() {
        cl i = i();
        String symbol = Currency.getInstance(i.b.j).getSymbol();
        String a = jf.a(this.y.e, this.y.f, this.y.d);
        if (jh.a(a)) {
            setTitle(getString(R.string.p2p_pay_request_title_format_with_quote, new Object[]{a}));
        } else {
            setTitle(getString(R.string.p2p_pay_request_title_format, new Object[]{a}));
        }
        if (this.y.u) {
            this.t.setText(R.string.confirm_result_b_donate);
        }
        ViewGroup viewGroup = (ViewGroup) this.b.getParent();
        int indexOfChild = viewGroup.indexOfChild(this.b);
        viewGroup.removeView(this.b);
        switch (this.y.n) {
            case 0:
                this.b = in.a(this, il.b);
                this.b.setText(getString(R.string.p2p_pay_request_tv_request_status_to_be_paid));
                break;
            case 1:
                this.b = in.a(this, il.d);
                this.b.setText(getString(R.string.p2p_pay_request_tv_request_status_canceled_format));
                break;
            case 2:
                this.b = in.a(this, il.c);
                this.b.setText(getString(R.string.p2p_pay_request_tv_request_status_refused_format));
                break;
            case 3:
                this.b = in.a(this, il.a);
                TextView textView = this.b;
                Object[] objArr = new Object[2];
                objArr[0] = a;
                objArr[1] = String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.y.h), symbol});
                textView.setText(getString(R.string.p2p_pay_request_tv_request_status_paid_format, objArr));
                break;
        }
        viewGroup.addView(this.b, indexOfChild);
        if (!(i == null || i.b == null)) {
            this.C.a.displayImage(jl.a(this.y.d), this.c);
        }
        ((TextView) findViewById(R.id.tv_recipient_name)).setText(a);
        ((TextView) findViewById(R.id.tv_recipient_info)).setText(getString(R.string.recipient));
        TextView textView2 = (TextView) findViewById(R.id.tv_recipient_id);
        if (jf.a(this.y.e, this.y.f)) {
            textView2.setText(jf.a(this.y.d));
        } else {
            textView2.setVisibility(8);
        }
        if (this.y.g) {
            this.e.setVisibility(0);
            this.d.setVisibility(8);
        } else {
            this.e.setVisibility(8);
            this.d.setVisibility(0);
            this.d.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.y.h), symbol}));
        }
        this.f.setRightText(jk.b(this, this.y.c));
        Object obj = null;
        if (this.y.k == null || TextUtils.isEmpty(this.y.k)) {
            this.g.setVisibility(8);
        } else {
            obj = 1;
            this.g.setVisibility(0);
            this.C.a.displayImage(jl.a(this.y.d), this.h);
            this.j.setText(a);
            this.k.setText(jk.a(this, this.y.c, true));
            this.l.setText(this.y.k);
        }
        if (this.y.n == 0) {
            obj = 1;
            this.o.setVisibility(8);
            this.m.setVisibility(0);
            this.r.setVisibility(8);
            this.s.setVisibility(0);
        } else {
            this.m.setVisibility(8);
            if (this.y.l == null || TextUtils.isEmpty(this.y.l)) {
                this.o.setVisibility(8);
            } else {
                obj = 1;
                this.o.setVisibility(0);
                this.p.setText(jk.a(this, this.y.m, true));
                this.q.setText(this.y.l);
            }
            this.r.setVisibility(0);
            this.s.setVisibility(8);
        }
        if (obj != null) {
            this.C.a.displayImage(jl.a(i.b.a), this.i);
        }
        if (TextUtils.isEmpty(this.y.p)) {
            this.x.setVisibility(8);
        } else {
            this.x.setOnClickListener(this);
            this.x.setVisibility(0);
            this.x.setText(this.y.o);
        }
        findViewById(R.id.ll_root).setVisibility(0);
    }

    private void l() {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, this.y.a), 72, true);
    }

    private void m() {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, this.y.p), (int) CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                int h = h();
                if (h == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
                    m();
                    return;
                } else if (h == 73) {
                    d(this.w);
                    return;
                } else if (h == 227) {
                    a(this.z);
                    return;
                } else if (h == 72) {
                    l();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case InputAmountType:
                this.v = bundle.getDouble("Data.Amount");
                d(0);
                return;
            case HideOperationType:
                l();
                return;
            case InsufficientFundType:
                cl i = i();
                i.U.f = this.y.v;
                Intent a;
                if (i.b == null || !i.b.a()) {
                    a = is.a(this, MoneyInCbAndPayActivity.class);
                    a.putExtra("fr.smoney.android.izly.extras.p2pPayRequestData", i.U);
                    startActivity(a);
                    return;
                }
                a = is.a(this, CompleteAccountWrapperActivity.class);
                a.putExtra("fr.smoney.android.izly.extras.activityToStart", 3);
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("fr.smoney.android.izly.extras.p2pPayRequestData", i.U);
                a.putExtra("fr.smoney.android.izly.extras.activityToStartDataBundle", bundle2);
                startActivity(a);
                return;
            case PayToCharityNoAdressType:
                Intent a2 = is.a(this, CompleteAccountWrapperActivity.class);
                a2.putExtra("fr.smoney.android.izly.extras.isADirectCallForAddress", true);
                startActivityForResult(a2, 2457);
                return;
            case PayToCharityNoOptInPartnersType:
                this.y.v = true;
                e(this.w);
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    protected final boolean a(ServerError serverError) {
        switch (serverError.b) {
            case 507:
                startActivityForResult(is.a(this, CompleteAccountWrapperActivity.class), 1287);
                return true;
            default:
                return super.a(serverError);
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
                a((GetAttachmentData) bundle.getParcelable("fr.smoney.android.izly.extras.getAttachementData"), serverError);
            } else if (i2 == 73) {
                a((P2PPayRequestData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayRequestData"), serverError);
            } else if (i2 == 72) {
                a(bundle.getLong("fr.smoney.android.izly.extras.p2pPayRequestHideIdToHide"), serverError);
            } else if (i2 == 227) {
                a((GetNewsFeedDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNewsFeedDetails"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        int h = h();
        switch (ieVar) {
            case ConnexionErrorType:
                if (h == 73 || h == 227) {
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
        if (i == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
            a(i2.aA, i2.aB);
        } else if (i == 73) {
            a(i2.U, i2.V);
        } else if (i == 72) {
            a(i2.S, i2.T);
        } else if (i == 227) {
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
            case PayToCharityResult:
                e(this.w);
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

    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 2457:
                if (i2 == -1) {
                    e(this.w);
                    return;
                }
                return;
            default:
                super.onActivityResult(i, i2, intent);
                return;
        }
    }

    public void onClick(View view) {
        if (view == this.r) {
            a(ht.a(getString(R.string.p2p_pay_request_dialog_hide_confirm_title), getString(R.string.p2p_pay_request_dialog_hide_confirm_message), getString(17039370), getString(17039360), this, ie.HideOperationType));
        } else if (view == this.t) {
            if (this.y.g) {
                String string;
                cl i = i();
                if (ad.b) {
                    string = getString(R.string.dialog_amount_entry_et_amount_hint, new Object[]{Double.valueOf(i.b.d), Double.valueOf(i.b.e)});
                } else {
                    string = getString(R.string.dialog_amount_entry_et_amount_hint_default);
                }
                a(hy.a(getString(R.string.dialog_amount_entry_title), string, null, this));
            } else if (this.A == null) {
                d(0);
            } else {
                this.w = 0;
                a(this.A);
            }
        } else if (view == this.u) {
            this.y.l = this.n.getText().toString();
            a(this.y, 1);
        } else if (view == this.x) {
            m();
        } else if (view == this.c) {
            Intent a = is.a(this, ContactDetailsActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.contactId", this.y);
            a.putExtra("fr.smoney.android.izly.extras.mode", 0);
            startActivity(a);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_pay_request);
        this.b = (TextView) findViewById(R.id.tv_request_status);
        this.c = (ImageView) findViewById(R.id.aiv_recipient_photo);
        this.c.setOnClickListener(this);
        this.d = (DetailTwoText) findViewById(R.id.tv_amount);
        this.e = (DetailTwoText) findViewById(R.id.tv_without_amount);
        this.f = (DetailTwoText) findViewById(R.id.tv_date);
        this.g = (LinearLayout) findViewById(R.id.ll_message_other);
        this.h = (ImageView) findViewById(R.id.aiv_message_avatar_other);
        this.i = (ImageView) findViewById(R.id.img_message_me);
        this.j = (TextView) findViewById(R.id.tv_message_name_other);
        this.k = (TextView) findViewById(R.id.tv_message_date_other);
        this.l = (TextView) findViewById(R.id.tv_message_other);
        this.o = (LinearLayout) findViewById(R.id.ll_message_me);
        this.p = (TextView) findViewById(R.id.tv_message_date_me);
        this.q = (TextView) findViewById(R.id.tv_message_me);
        this.m = (LinearLayout) findViewById(R.id.ll_message_me_edit);
        this.n = (EditText) findViewById(R.id.et_message_me);
        this.x = (Button) findViewById(R.id.b_attachment);
        this.r = (Button) findViewById(R.id.b_hide);
        this.r.setOnClickListener(this);
        this.s = (LinearLayout) findViewById(R.id.ll_pay_refuse);
        this.t = (Button) findViewById(R.id.b_pay);
        this.t.setOnClickListener(this);
        this.u = (Button) findViewById(R.id.b_refuse);
        this.u.setOnClickListener(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.C = new ge(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.y = (P2PPayRequest) intent.getParcelableExtra("fr.smoney.android.izly.extras.p2pPayRequest");
            this.z = (NewsFeedItem) intent.getParcelableExtra("fr.smoney.android.izly.extras.relatedFeedItem");
            if (bundle != null) {
                this.A = (P2PPayRequestData) bundle.getParcelable("savedStateRequestP2PPayRequestData");
                this.v = bundle.getDouble("savedStateCurrentAmount");
                this.w = bundle.getInt("savedStateCurrentResponseStatus");
                this.B = bundle.getBoolean("savedStatePopUpAlreadyShown");
                this.n.setText(bundle.getString("savedStateCurrentMessage"));
                P2PPayRequest p2PPayRequest = (P2PPayRequest) bundle.getParcelable("savedStateP2PayRequest");
                if (p2PPayRequest != null) {
                    this.y = p2PPayRequest;
                }
            }
            if (this.y != null || this.z != null) {
                if (this.y != null) {
                    k();
                } else {
                    a(this.z);
                }
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("savedStateRequestP2PPayRequestData", this.A);
        bundle.putDouble("savedStateCurrentAmount", this.v);
        bundle.putInt("savedStateCurrentResponseStatus", this.w);
        bundle.putString("savedStateCurrentMessage", this.n.getText().toString());
        bundle.putBoolean("savedStatePopUpAlreadyShown", this.B);
        if (this.y != null) {
            bundle.putParcelable("savedStateP2PayRequest", this.y);
        }
        super.onSaveInstanceState(bundle);
    }
}
