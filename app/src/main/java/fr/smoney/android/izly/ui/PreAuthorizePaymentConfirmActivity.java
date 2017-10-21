package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import defpackage.hw;
import defpackage.hz;
import defpackage.ie;
import defpackage.if$a;
import defpackage.is;
import defpackage.jh;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class PreAuthorizePaymentConfirmActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a, if$a {
    private ImageView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;
    private Button j;
    private View k;
    private PreAuthorizationContainerData l;
    private cl m;
    private String n;
    private String o;
    private SimpleDateFormat p = new SimpleDateFormat("'Le' dd/MM/yyyy 'à' HH':'mm", Locale.getDefault());
    private ge q;

    private void a(PreAuthorizationContainerData preAuthorizationContainerData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (preAuthorizationContainerData == null) {
            a(hw.a(this, this));
        } else {
            Intent a = is.a(this, PreAuthorizePaymentResultActivity.class);
            a.putExtra("INTENT_EXTRA_PRE_AUTHORIZATION", preAuthorizationContainerData);
            startActivity(a);
        }
    }

    private void a(String str, PreAuthorizationContainerData preAuthorizationContainerData) {
        int keyAt;
        SmoneyRequestManager j = j();
        String str2 = this.m.b.a;
        String str3 = this.m.b.c;
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 237 && intent.getStringExtra("fr.smoney.android.izly.extras.makePreAuthorizationUserId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.makePreAuthorizationSessionId").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.makePreAuthorizationPassword").equals(str) && intent.getParcelableExtra("fr.smoney.android.izly.extras.makePreAuthorizationInfos").equals(preAuthorizationContainerData)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 237);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.makePreAuthorizationUserId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.makePreAuthorizationSessionId", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.makePreAuthorizationPassword", str);
        intent2.putExtra("fr.smoney.android.izly.extras.makePreAuthorizationInfos", preAuthorizationContainerData);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bN = null;
        j.f.bO = null;
        super.a(keyAt, 237, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case InputPasswordType:
                this.o = bundle.getString("Data.Password");
                a(this.o, this.l);
                return;
            case ConnexionErrorType:
                if (h() == 237) {
                    a(this.o, this.l);
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
            if (i2 == 237) {
                a((PreAuthorizationContainerData) bundle.getParcelable("fr.smoney.android.izly.extras.RECEIVER_EXTRA_PRE_AUTHORIZATION_DATA"), serverError);
            }
        }
    }

    public final void b_(int i) {
        if (i == 237) {
            a(this.m.bN, this.m.bO);
        }
    }

    public void onClick(View view) {
        if (view == this.j) {
            a(hz.a(this, this));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.pre_authorize_confirm);
        this.l = (PreAuthorizationContainerData) getIntent().getParcelableExtra("INTENT_EXTRA_PRE_AUTHORIZATION");
        this.n = Currency.getInstance(i().b.j).getSymbol();
        this.m = i();
        this.q = new ge(this);
        if (bundle != null) {
            this.o = bundle.getString("SAVED_STATE_INPUT_PASSWORD");
        }
        this.b = (ImageView) findViewById(R.id.aiv_recipient_photo);
        this.c = (TextView) findViewById(R.id.tv_recipient_info);
        this.d = (TextView) findViewById(R.id.tv_recipient_name);
        this.e = (TextView) findViewById(R.id.tv_recipient_id);
        this.g = (TextView) findViewById(R.id.tv_max_amount);
        this.h = (TextView) findViewById(R.id.tv_expiration_date);
        this.i = (TextView) findViewById(R.id.tv_card);
        this.j = (Button) findViewById(R.id.bt_pre_authorize_valid);
        this.k = findViewById(R.id.ll_pre_authorization_card_layout);
        this.j.setOnClickListener(this);
        this.c.setText(R.string.pre_authorize_recipient_payto);
        this.q.a.displayImage(jl.a(this.l.a.a), this.b);
        this.d.setText(this.l.a.b);
        this.e.setText(this.l.a.a);
        switch (this.l.a.f) {
            case SmoneyUserPart:
                this.f.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.confirm_result_tv_recipient_is_client_logo), null, null, null);
                this.f.setText(R.string.contact_details_tv_recipient_is_client_part);
                break;
            case SmoneyUserPro:
                this.f.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.confirm_result_tv_recipient_is_client_logo_pro), null, null, null);
                this.f.setText(R.string.contact_details_tv_recipient_is_client_pro);
                break;
            default:
                this.f.setVisibility(8);
                break;
        }
        TextView textView = this.g;
        double d = this.l.b;
        textView.setText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.n}));
        this.h.setText(this.p.format(new Date(this.l.c)));
        String str = this.l.d.a;
        this.i.setText(jh.c(str));
        if (str == null || str.length() == 0) {
            this.k.setVisibility(8);
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putString("SAVED_STATE_INPUT_PASSWORD", this.o);
        super.onSaveInstanceState(bundle);
    }
}
