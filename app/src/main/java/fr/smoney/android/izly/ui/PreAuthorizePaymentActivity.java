package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import defpackage.gd;
import defpackage.hu;
import defpackage.hw;
import defpackage.ie;
import defpackage.if$a;
import defpackage.is;
import defpackage.iu;
import defpackage.je;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData.PreAuthorization;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class PreAuthorizePaymentActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, SmoneyRequestManager$a, if$a {
    private cl b;
    private PreAuthorizationContainerData c;
    private PreAuthorization d;
    private ImageView e;
    private TextView f;
    private EditText g;
    private TextView h;
    private EditText i;
    private Button j;

    private void a(PreAuthorizationContainerData preAuthorizationContainerData) {
        int keyAt;
        SmoneyRequestManager j = j();
        String str = this.b.b.a;
        String str2 = this.b.b.c;
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 236 && intent.getStringExtra("fr.smoney.android.izly.extras.checkPreAuthorizationUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.checkPreAuthorizationSessionId").equals(str2) && intent.getParcelableExtra("fr.smoney.android.izly.extras.checkPreAuthorizationInfos").equals(preAuthorizationContainerData)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 236);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.checkPreAuthorizationUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.checkPreAuthorizationSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.checkPreAuthorizationInfos", preAuthorizationContainerData);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bL = null;
        j.f.bM = null;
        super.a(keyAt, 236, true);
    }

    private void a(PreAuthorizationContainerData preAuthorizationContainerData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (preAuthorizationContainerData == null) {
            a(hw.a(this, this));
        } else {
            Intent a = is.a(this, PreAuthorizePaymentConfirmActivity.class);
            a.putExtra("INTENT_EXTRA_PRE_AUTHORIZATION", preAuthorizationContainerData);
            startActivity(a);
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 236) {
                    a(this.c);
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
            if (i2 == 236) {
                a((PreAuthorizationContainerData) bundle.getParcelable("fr.smoney.android.izly.extras.RECEIVER_EXTRA_PRE_AUTHORIZATION_DATA"), serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
    }

    public final void b_(int i) {
        if (i == 236) {
            a(this.b.bL, this.b.bM);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onClick(View view) {
        if (view == this.j) {
            int i = (this.d == null || this.d.b < 0) ? 0 : 1;
            if (i != 0) {
                String obj = this.i.getText().toString();
                double d = 0.0d;
                try {
                    d = iu.a(obj);
                } catch (gd e) {
                }
                if (!iu.b(obj)) {
                    a(hu.a(getString(R.string.p2p_get_mult_dialog_error_invalid_amount_title), getString(R.string.p2p_get_mult_dialog_error_invalid_amount_message), getString(17039370)));
                    return;
                } else if (d > this.b.b.e) {
                    a(hu.a(getString(R.string.p2p_get_mult_dialog_error_max_reached_title), getString(R.string.p2p_get_mult_dialog_error_max_reached_message), getString(17039370)));
                    return;
                } else {
                    this.c.b = d;
                    this.c.d.a = this.g.getText().toString();
                    a(this.c);
                    return;
                }
            }
        }
        if (view == this.h && this.g.getVisibility() == 8) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.playTogether(ObjectAnimator.ofFloat(this.g, "alpha", 0.0f, 1.0f), ObjectAnimator.ofFloat(this.h, "alpha", 1.0f, 0.0f));
            animatorSet.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ PreAuthorizePaymentActivity a;

                {
                    this.a = r1;
                }

                public final void onAnimationEnd(Animator animator) {
                    this.a.h.setVisibility(8);
                }

                public final void onAnimationStart(Animator animator) {
                    this.a.g.setVisibility(0);
                    this.a.g.requestFocus();
                    je.b(this.a, this.a.g);
                }
            });
            animatorSet.start();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.pre_authorize);
        this.c = (PreAuthorizationContainerData) getIntent().getParcelableExtra("INTENT_EXTRA_PRE_AUTHORIZATION");
        this.d = this.c.d;
        this.b = i();
        if (bundle != null) {
            this.c = (PreAuthorizationContainerData) bundle.getParcelable("SAVED_STATE_PREAUTORIZE");
        }
        this.f = (TextView) findViewById(R.id.tv_pre_authorize_recipient);
        this.e = (ImageView) findViewById(R.id.maiv_pre_authorize_recipient_avatar);
        this.g = (EditText) findViewById(R.id.et_pre_authorize_identifier);
        this.h = (TextView) findViewById(R.id.tv_pre_authorize_identifier);
        this.i = (EditText) findViewById(R.id.et_pre_authorize_amount);
        this.j = (Button) findViewById(R.id.bt_pre_authorize_valid);
        this.j.setOnClickListener(this);
        this.h.setOnClickListener(this);
        if (this.c != null) {
            this.a.a.displayImage(jl.a(this.c.a.a), this.e);
            this.f.setText(this.c.a.b);
            if (this.b.b != null) {
                this.i.setText(String.format("%.2f", new Object[]{Double.valueOf(Math.min(r0.e, r0.B.a))}));
            }
            this.h.setText(this.d.a);
            this.g.setText(this.d.a);
            this.g.addTextChangedListener(this);
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("SAVED_STATE_PREAUTORIZE", this.c);
        super.onSaveInstanceState(bundle);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence.length() == 0 || charSequence.length() == 16) {
            if (charSequence.length() == 0) {
                this.c.d.b = 0;
            } else {
                this.c.d.b = 1;
            }
            this.j.setEnabled(true);
        } else if (charSequence.length() > 0 && charSequence.length() < 16) {
            this.j.setEnabled(false);
        }
    }
}
