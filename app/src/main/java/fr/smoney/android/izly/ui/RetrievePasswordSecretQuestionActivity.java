package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import defpackage.ie;
import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.GetSecretQuestionData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

import org.spongycastle.asn1.eac.EACTags;

public class RetrievePasswordSecretQuestionActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, SmoneyRequestManager$a {
    private int b = 0;
    private TextView c;
    private EditText d;
    private Button e;
    private Button f;

    private void a(GetSecretQuestionData getSecretQuestionData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else {
            this.c.setText(getSecretQuestionData.a);
        }
    }

    private void b(ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else {
            startActivity(is.a(this, RetrievePasswordRequestActivity.class));
        }
    }

    private void k() {
        int keyAt;
        SmoneyRequestManager j = j();
        String f = SmoneyApplication.c.f();
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY && intent.getStringExtra("fr.smoney.android.izly.extras.getSecretQuestionUserId").equals(f)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.getSecretQuestionUserId", f);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.aq = null;
        j.f.ar = null;
        super.a(keyAt, (int) EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY, true);
    }

    private void l() {
        int keyAt;
        String obj = this.d.getText().toString();
        SmoneyRequestManager j = j();
        String f = SmoneyApplication.c.f();
        boolean z = this.b == 1;
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == EACTags.SECURITY_SUPPORT_TEMPLATE && intent.getStringExtra("fr.smoney.android.izly.extras.secretQuestionAnwserUserId").equals(f) && intent.getStringExtra("fr.smoney.android.izly.extras.secretQuestionAnwserSecretAnswer").equals(obj) && intent.getStringExtra("fr.smoney.android.izly.extras.secretQuestionAnwserUnlockAccount").equals(Boolean.valueOf(z))) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", EACTags.SECURITY_SUPPORT_TEMPLATE);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.secretQuestionAnwserUserId", f);
        intent2.putExtra("fr.smoney.android.izly.extras.secretQuestionAnwserSecretAnswer", obj);
        intent2.putExtra("fr.smoney.android.izly.extras.secretQuestionAnwserUnlockAccount", z);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.as = null;
        j.f.at = null;
        super.a(keyAt, (int) EACTags.SECURITY_SUPPORT_TEMPLATE, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY) {
                    k();
                    return;
                } else if (h() == EACTags.SECURITY_SUPPORT_TEMPLATE) {
                    l();
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
            if (i2 == EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY) {
                a((GetSecretQuestionData) bundle.getParcelable("fr.smoney.android.izly.extras.getSecretQuestionData"), serverError);
            } else if (i2 == EACTags.SECURITY_SUPPORT_TEMPLATE) {
                b(serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        if (editable.toString().length() > 0) {
            this.e.setEnabled(true);
        } else {
            this.e.setEnabled(false);
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY) {
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
        if (i == EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY) {
            a(i2.aq, i2.ar);
        } else if (i == EACTags.SECURITY_SUPPORT_TEMPLATE) {
            b(i2.at);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case ErrorType:
                if (h() == EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY) {
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
        if (ieVar == ie.ProgressType && h() == EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY) {
            g();
            finish();
            return;
        }
        super.d(ieVar);
    }

    public void onClick(View view) {
        if (view == this.e) {
            l();
        } else if (view == this.f) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("tel:" + getString(R.string.retrieve_password_secret_question_b_care_number))));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.retrieve_password_secret_question);
        this.c = (TextView) findViewById(R.id.tv_question);
        this.d = (EditText) findViewById(R.id.et_answer);
        this.d.addTextChangedListener(this);
        this.e = (Button) findViewById(R.id.b_submit);
        this.e.setOnClickListener(this);
        this.f = (Button) findViewById(R.id.b_call);
        this.f.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.b = extras.getInt("fr.smoney.android.izly.intentExtraMode");
        }
        k();
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
