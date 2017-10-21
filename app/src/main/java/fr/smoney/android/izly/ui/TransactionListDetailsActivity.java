package fr.smoney.android.izly.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import defpackage.hu;
import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import defpackage.iw;
import defpackage.je;
import defpackage.jf;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetAttachmentData;
import fr.smoney.android.izly.data.model.GetNewsFeedDetailsData;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.SendChatMessageData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.Transaction;
import fr.smoney.android.izly.data.model.TransactionMessage;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.spongycastle.crypto.tls.CipherSuite;

public class TransactionListDetailsActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, SmoneyRequestManager$a {
    private ImageView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private ImageView f;
    private ImageView g;
    private DetailTwoText h;
    private DetailTwoText i;
    private DetailTwoText j;
    private EditText k;
    private Button l;
    private LinearLayout m;
    private LinearLayout n;
    private Button o;
    private View p;
    private LayoutInflater q;
    private Transaction r;
    private NewsFeedItem s;
    private TextView t;
    private View u;
    private TextView v;
    private TextView w;
    private ge x;

    private static String a(Transaction transaction) {
        return transaction.c ? jf.a(transaction.q, transaction.r, transaction.p) : jf.a(transaction.p);
    }

    private void a(GetAttachmentData getAttachmentData, ServerError serverError) {
        if (serverError != null) {
            a(hw.a(this, this));
        } else if (getAttachmentData == null) {
            a(hw.a(this, this));
        } else if (getAttachmentData.c) {
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
            this.r = (Transaction) getNewsFeedDetailsData.a;
            k();
        }
    }

    private void a(NewsFeedItem newsFeedItem) {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, newsFeedItem), 227, true);
    }

    private void a(SendChatMessageData sendChatMessageData, ServerError serverError) {
        if (serverError != null) {
            a(hw.a(this, this));
        } else if (sendChatMessageData == null) {
            a(hw.a(this, this));
        } else {
            a(jk.a(this, new Date().getTime(), true), this.k.getText().toString());
            this.k.setText("");
            je.a(this, this.k);
        }
    }

    private void a(String str, String str2) {
        cl i = i();
        View inflate = this.q.inflate(R.layout.confirm_result_message_me, this.m, false);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_message_me);
        ((TextView) inflate.findViewById(R.id.tv_message_date_me)).setText(str);
        textView.setText(str2);
        this.f = (ImageView) inflate.findViewById(R.id.img_message_me);
        this.x.a.displayImage(jl.a(i.b.a), this.f);
        this.m.setVisibility(0);
        this.m.addView(inflate);
    }

    private void a(String str, String str2, String str3, String str4) {
        View inflate = this.q.inflate(R.layout.confirm_result_message_other, this.m, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.aiv_message_avatar_other);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_message_date_other);
        TextView textView2 = (TextView) inflate.findViewById(R.id.tv_message_other);
        ((TextView) inflate.findViewById(R.id.tv_message_name_other)).setText(str2);
        textView.setText(str3);
        textView2.setText(str4);
        this.x.a.displayImage(jl.a(str), imageView);
        this.m.setVisibility(0);
        this.m.addView(inflate);
    }

    private void k() {
        cl i = i();
        String symbol = Currency.getInstance(i.b.j).getSymbol();
        switch (this.r.n) {
            case 0:
            case 5:
                this.x.a.displayImage(jl.a(this.r.p), this.b);
                this.c.setText(getString(R.string.transaction_list_details_pay_in_title));
                if (this.r.c) {
                    this.d.setText(jf.a(this.r.q, this.r.r, this.r.p));
                    if (jf.a(this.r.q, this.r.r)) {
                        this.e.setText(jf.a(this.r.p));
                    } else {
                        this.e.setVisibility(8);
                    }
                } else {
                    this.e.setVisibility(8);
                    this.d.setText(jf.a(this.r.p));
                }
                switch (this.r.s) {
                    case 0:
                    case 5:
                        this.g.setImageResource(R.drawable.transaction_details_status_inprogress);
                        break;
                    case 1:
                    case 2:
                    case 4:
                        this.g.setImageResource(R.drawable.transaction_details_status_ko);
                        break;
                    case 3:
                        this.g.setImageResource(R.drawable.transaction_details_status_ok);
                        break;
                }
                this.n.setVisibility(0);
                this.k.setVisibility(0);
                this.l.setVisibility(0);
                break;
            case 1:
            case 4:
                this.x.a.displayImage(jl.a(this.r.p), this.b);
                this.c.setText(getString(R.string.transaction_list_details_pay_out_title));
                if (this.r.c) {
                    this.d.setText(jf.a(this.r.q, this.r.r, this.r.p));
                    if (jf.a(this.r.q, this.r.r)) {
                        this.e.setText(jf.a(this.r.p));
                    } else {
                        this.e.setVisibility(8);
                    }
                } else {
                    this.e.setVisibility(8);
                    this.d.setText(jf.a(this.r.p));
                }
                switch (this.r.s) {
                    case 0:
                    case 5:
                        this.g.setImageResource(R.drawable.transaction_details_status_inprogress);
                        break;
                    case 1:
                    case 2:
                    case 4:
                        this.g.setImageResource(R.drawable.transaction_details_status_ko);
                        break;
                    case 3:
                        this.g.setImageResource(R.drawable.transaction_details_status_ok);
                        break;
                }
                this.n.setVisibility(0);
                this.k.setVisibility(0);
                this.l.setVisibility(0);
                break;
            case 2:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_money_in);
                this.c.setText(getString(R.string.transaction_list_details_money_in_title));
                this.d.setText(jf.a(this.r.p));
                this.e.setVisibility(8);
                switch (this.r.s) {
                    case 0:
                        this.g.setImageResource(R.drawable.transaction_details_status_inprogress);
                        break;
                    case 1:
                        this.g.setImageResource(R.drawable.transaction_details_status_ok);
                        break;
                    case 2:
                        this.g.setImageResource(R.drawable.transaction_details_status_ko);
                        break;
                }
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 3:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_money_out);
                this.c.setText(getString(R.string.transaction_list_details_money_out_title));
                this.d.setText(jf.a(this.r.p));
                this.e.setVisibility(8);
                switch (this.r.s) {
                    case 0:
                        this.g.setImageResource(R.drawable.transaction_details_status_inprogress);
                        break;
                    case 1:
                        this.g.setImageResource(R.drawable.transaction_details_status_ok);
                        break;
                    case 2:
                        this.g.setImageResource(R.drawable.transaction_details_status_ko);
                        break;
                }
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 6:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_smoney);
                this.c.setText(getString(R.string.transaction_list_details_refund_title));
                this.d.setText(jf.a(this.r.p));
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 7:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_shopping_cart);
                this.c.setText(getString(R.string.transaction_list_details_ebusiness_title));
                this.d.setText(this.r.q + " " + this.r.r);
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 8:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_distrib);
                this.c.setText(getString(R.string.transaction_list_details_distributor_title));
                this.d.setText(jf.a(this.r.q, this.r.r, this.r.p));
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 9:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_smoney);
                this.c.setText(getString(R.string.transaction_list_details_commission_title));
                this.d.setText(jf.a(this.r.q, this.r.r, this.r.p));
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 10:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_money_in);
                this.c.setText(getString(R.string.transaction_list_details_money_in_refund_title));
                this.d.setText(jf.a(this.r.p));
                this.e.setVisibility(8);
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 11:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_money_out);
                this.c.setText(getString(R.string.transaction_list_details_money_out_refund_title));
                this.d.setText(jf.a(this.r.p));
                this.e.setVisibility(8);
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 14:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_shopping_cart);
                this.c.setText(getString(R.string.transaction_list_details_ebusiness_refund_title));
                this.d.setText(this.r.q + " " + this.r.r);
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 15:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_smoney);
                this.c.setText(getString(R.string.transaction_list_details_commission_refund_title));
                this.d.setText(jf.a(this.r.q, this.r.r, this.r.p));
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 17:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_smoney);
                this.c.setText(this.r.b());
                this.d.setText(jf.a(this.r.q, this.r.r, this.r.p));
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 18:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_placeholder);
                this.c.setText(R.string.transaction_list_details_pay_card_in_title);
                this.d.setText(jf.a(this.r.q, this.r.r, this.r.p));
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
            case 19:
                this.b.setImageResource(R.drawable.detail_aiv_avatar_smoney);
                this.c.setText(R.string.transaction_list_details_pay_in_refund_title);
                this.d.setText(jf.a(this.r.q, this.r.r, this.r.p));
                this.n.setVisibility(8);
                this.k.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                break;
        }
        Transaction transaction = this.r;
        if (transaction.g > 0.0d) {
            Object format = String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(transaction.g), symbol});
        } else {
            String format2 = String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(transaction.e), symbol});
        }
        switch (transaction.n) {
            case 0:
            case 5:
                this.t.setText(getString(R.string.historydetails_creditmessage_format, new Object[]{format, a(transaction)}));
                break;
            case 1:
            case 4:
                this.t.setText(getString(R.string.historydetails_debitmessage_format, new Object[]{format, a(transaction)}));
                break;
            case 2:
                this.t.setText(getString(R.string.historydetails_moneyinmessage_format, new Object[]{format}));
                break;
            case 3:
                this.t.setText(getString(R.string.historydetails_moneyoutmessage_format, new Object[]{format}));
                break;
            case 8:
                this.t.setText(getString(R.string.historydetails_distributomessage_format, new Object[]{format, a(transaction)}));
                break;
            case 18:
                this.t.setText(getString(R.string.historydetails_moneyin_thirdparty, new Object[]{format, a(transaction)}));
                break;
            default:
                this.t.setVisibility(8);
                break;
        }
        if (transaction.s == 5) {
            this.t.setVisibility(8);
            View findViewById = findViewById(R.id.tv_p2p_status_uncomplete);
            findViewById.setVisibility(0);
            long j = transaction.k / 1000;
            TextView textView = (TextView) findViewById.findViewById(R.id.tv_p2p_status_uncomplete_texttop);
            TextView textView2 = (TextView) findViewById.findViewById(R.id.tv_p2p_status_uncomplete_textbottom);
            textView.setText(getString(R.string.historydetails_noninscritmessageformat, new Object[]{format, a(transaction)}));
            textView2.setText(getString(R.string.historydetails_noninscritmessagetextformat, new Object[]{DateFormat.format("dd/MM", (j + 604800) * 1000)}));
        }
        if (this.r.g > 0.0d) {
            this.i.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.r.g), symbol}));
        } else {
            this.i.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.r.e), symbol}));
        }
        if (this.r.h > 0.0d) {
            this.v.setText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.r.h), symbol}));
        } else {
            this.v.setText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.r.f), symbol}));
        }
        if (this.r.j != null) {
            this.u.setVisibility(0);
            this.w.setText(this.r.j);
        }
        this.h.setRightText(jk.b(this, this.r.l));
        if (TextUtils.isEmpty(this.r.w)) {
            this.j.setVisibility(8);
        } else {
            this.j.setVisibility(0);
            this.j.setRightText(this.r.w);
        }
        if (!(TextUtils.isEmpty(this.r.u) && TextUtils.isEmpty(this.r.m) && this.r.A.size() <= 0)) {
            switch (this.r.n) {
                case 0:
                case 4:
                    if (!TextUtils.isEmpty(this.r.m)) {
                        a(this.r.p, jf.a(this.r.q, this.r.r, this.r.p), jk.a(this, this.r.k, true), this.r.m);
                    }
                    if (!TextUtils.isEmpty(this.r.u)) {
                        a(jk.a(this, this.r.v, true), this.r.u);
                        break;
                    }
                    break;
                case 1:
                case 5:
                    if (!TextUtils.isEmpty(this.r.m)) {
                        a(jk.a(this, this.r.k, true), this.r.m);
                    }
                    if (!TextUtils.isEmpty(this.r.u)) {
                        a(this.r.p, jf.a(this.r.q, this.r.r, this.r.p), jk.a(this, this.r.v, true), this.r.u);
                        break;
                    }
                    break;
                case 6:
                case 7:
                case 8:
                case 14:
                    a("", getString(R.string.transaction_list_details_smoney_name), jk.a(this, this.r.k, true), this.r.m);
                    break;
            }
            Iterator it = this.r.A.iterator();
            while (it.hasNext()) {
                TransactionMessage transactionMessage = (TransactionMessage) it.next();
                if (transactionMessage.c.equals(i.b.a)) {
                    a(jk.a(this, transactionMessage.b, true), transactionMessage.d);
                } else {
                    a(transactionMessage.c, jf.a(this.r.q, this.r.r, this.r.p), jk.a(this, transactionMessage.b, true), transactionMessage.d);
                }
            }
        }
        if (this.r.a()) {
            this.o.setOnClickListener(this);
            this.o.setVisibility(0);
            this.o.setText(this.r.x);
        }
        if (this.r.z == 1 || ad.a) {
            this.n.setVisibility(8);
        }
        findViewById(R.id.ll_root).setVisibility(0);
    }

    private void l() {
        if (this.k.getText().toString().length() > 0) {
            this.l.setEnabled(true);
        } else {
            this.l.setEnabled(false);
        }
    }

    private void m() {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, String.valueOf(this.r.y)), (int) CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, true);
    }

    private void n() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        long j2 = this.r.b;
        String obj = this.k.getText().toString();
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 22 && intent.getStringExtra("fr.smoney.android.izly.extras.sendChatMessageUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.sendChatMessageSessionId").equals(str2) && intent.getLongExtra("fr.smoney.android.izly.extras.sendChatMessageOperationId", -1) == j2 && intent.getStringExtra("fr.smoney.android.izly.extras.sendChatMessageText").equals(obj)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 22);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.sendChatMessageUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.sendChatMessageSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.sendChatMessageOperationId", j2);
        intent2.putExtra("fr.smoney.android.izly.extras.sendChatMessageText", obj);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.p = null;
        j.f.q = null;
        super.a(keyAt, 22, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        int h = h();
        switch (ieVar) {
            case ConnexionErrorType:
                if (h == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
                    m();
                    return;
                } else if (h == 22) {
                    n();
                    return;
                } else if (h == 227) {
                    a(this.s);
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
            if (i2 == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
                a((GetAttachmentData) bundle.getParcelable("fr.smoney.android.izly.extras.getAttachementData"), serverError);
            } else if (i2 == 22) {
                a((SendChatMessageData) bundle.getParcelable("fr.smoney.android.izly.extras.sendChatMessageData"), serverError);
            } else if (i2 == 227) {
                a((GetNewsFeedDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNewsFeedDetails"), serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        l();
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
        if (i == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
            a(i2.aA, i2.aB);
        } else if (i == 22) {
            a(i2.p, i2.q);
        } else if (i == 227) {
            a(i2.bx, i2.by);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
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
            m();
        } else if (view == this.l) {
            n();
        } else if (this.b == view) {
            Parcelable parcelable = this.r;
            switch (parcelable.n) {
                case 0:
                case 1:
                case 4:
                case 5:
                    Intent a = is.a(this, ContactDetailsActivity.class);
                    a.putExtra("fr.smoney.android.izly.extras.contactId", parcelable);
                    a.putExtra("fr.smoney.android.izly.extras.mode", 0);
                    startActivity(a);
                    return;
                default:
                    return;
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.transaction_list_details);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.q = getLayoutInflater();
        this.x = new ge(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.r = (Transaction) extras.getParcelable("fr.smoney.android.izly.intentExtrasTransaction");
            this.s = (NewsFeedItem) extras.getParcelable("fr.smoney.android.izly.extras.relatedFeedItem");
            if (bundle != null) {
                Transaction transaction = (Transaction) bundle.getParcelable("savedInstanceState.Transaction");
                if (transaction != null) {
                    this.r = transaction;
                }
            }
            if (this.r != null || this.s != null) {
                this.b = (ImageView) findViewById(R.id.aiv_recipient_photo);
                this.b.setOnClickListener(this);
                this.c = (TextView) findViewById(R.id.tv_recipient_info);
                this.c.setText("");
                this.d = (TextView) findViewById(R.id.tv_recipient_name);
                this.e = (TextView) findViewById(R.id.tv_recipient_id);
                this.g = (ImageView) findViewById(R.id.iv_status);
                this.p = findViewById(R.id.v_trans_details_bottom_view);
                this.h = (DetailTwoText) findViewById(R.id.tv_date);
                this.i = (DetailTwoText) findViewById(R.id.tv_amount);
                this.j = (DetailTwoText) findViewById(R.id.tv_info);
                this.m = (LinearLayout) findViewById(R.id.ll_messages);
                this.o = (Button) findViewById(R.id.b_attachment);
                this.n = (LinearLayout) findViewById(R.id.ll_chat_areabox);
                this.k = (EditText) findViewById(R.id.et_chat);
                this.k.addTextChangedListener(this);
                this.l = (Button) findViewById(R.id.b_chat);
                this.l.setOnClickListener(this);
                this.t = (TextView) findViewById(R.id.tv_p2p_status);
                this.u = findViewById(R.id.ll_commission_amount_infos);
                this.v = (TextView) findViewById(R.id.tv_commission_ht);
                this.w = (TextView) findViewById(R.id.tv_tax);
                l();
                if (this.r != null) {
                    k();
                } else {
                    a(this.s);
                }
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        if (this.r != null) {
            bundle.putParcelable("savedInstanceState.Transaction", this.r);
        }
        super.onSaveInstanceState(bundle);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
