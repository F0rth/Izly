package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import defpackage.iv;
import defpackage.jh;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class PreAuthorizePaymentResultActivity extends SmoneyABSActivity {
    private View b;
    private ImageView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;
    private TextView j;
    private ImageView k;
    private PreAuthorizationContainerData l;
    private String m;
    private SimpleDateFormat n = new SimpleDateFormat("'Le' dd/MM/yyyy 'à' HH':'mm", Locale.getDefault());
    private ge o;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.pre_authorize_result);
        this.l = (PreAuthorizationContainerData) getIntent().getParcelableExtra("INTENT_EXTRA_PRE_AUTHORIZATION");
        this.m = Currency.getInstance(i().b.j).getSymbol();
        this.o = new ge(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.b = findViewById(R.id.container);
        this.c = (ImageView) findViewById(R.id.aiv_recipient_photo);
        this.d = (TextView) findViewById(R.id.tv_recipient_info);
        this.e = (TextView) findViewById(R.id.tv_recipient_name);
        this.f = (TextView) findViewById(R.id.tv_recipient_id);
        this.h = (TextView) findViewById(R.id.tv_max_amount);
        this.i = (TextView) findViewById(R.id.tv_expiration_date);
        this.j = (TextView) findViewById(R.id.tv_pre_authorization_card);
        this.k = (ImageView) findViewById(R.id.iv_pre_authorization_card);
        if (this.l != null) {
            this.b.setVisibility(0);
            this.d.setText(R.string.pre_authorize_recipient_payto);
            this.o.a.displayImage(jl.a(this.l.a.a), this.c);
            this.e.setText(this.l.a.b);
            this.f.setText(this.l.a.a);
            switch (this.l.a.f) {
                case SmoneyUserPart:
                    this.g.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.confirm_result_tv_recipient_is_client_logo), null, null, null);
                    this.g.setText(R.string.contact_details_tv_recipient_is_client_part);
                    break;
                case SmoneyUserPro:
                    this.g.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.confirm_result_tv_recipient_is_client_logo_pro), null, null, null);
                    this.g.setText(R.string.contact_details_tv_recipient_is_client_pro);
                    break;
                default:
                    this.g.setVisibility(8);
                    break;
            }
            TextView textView = this.h;
            double d = this.l.b;
            textView.setText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.m}));
            this.i.setText(this.n.format(new Date(this.l.c)));
            String str = this.l.d.a;
            this.j.setText(jh.c(str));
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            try {
                this.k.setImageBitmap(iv.a(str, displayMetrics.widthPixels, (int) (((double) displayMetrics.widthPixels) * 0.3d), -16777216, -1));
            } catch (WriterException e) {
            }
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                a();
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }
}
