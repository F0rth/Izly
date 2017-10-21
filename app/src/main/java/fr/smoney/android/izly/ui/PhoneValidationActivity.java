package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import defpackage.ht;
import defpackage.hu;
import defpackage.hv;
import defpackage.hw;
import defpackage.ie;
import defpackage.ir;
import defpackage.is;
import defpackage.ix;
import defpackage.jf;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserData;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.ActivityLauncherService;
import fr.smoney.android.izly.ui.CGUActivity.a;

import java.util.List;

public class PhoneValidationActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, OnEditorActionListener, SmoneyRequestManager$a {
    public static final String b = PhoneValidationActivity.class.getSimpleName();
    private Button c;
    private TextView d;
    private EditText e;
    private ImageView f;
    private String g;
    private String h;
    private LoginData i;

    private void a(LoginData loginData, UserData userData, ServerError serverError) {
        if (serverError != null) {
            m();
            if (serverError.b == 120) {
                a(hu.a(serverError.d, serverError.c, getString(17039370)));
            } else {
                a(serverError);
            }
        } else if (loginData == null && userData == null) {
            a(hw.a(this, this));
        } else if (loginData != null) {
            ir.a(this, loginData);
            this.i = loginData;
            if (loginData.H) {
                if (loginData.F) {
                    a(hv.a(getString(R.string.login_dialog_cgurefused_title), getString(R.string.login_dialog_cgucashier_message), getString(17039370), this, ie.CGUNotAcceptedType));
                    return;
                }
                Intent intent = new Intent(this, CGUActivity.class);
                intent.putExtra("fr.smoney.android.izly.extras.displayCase", a.CGU_CHANGED);
                startActivityForResult(intent, 20);
            } else if (loginData.G) {
                n();
            } else {
                startActivity(loginData.a() ? is.a(this, CompleteSubscriptionActivity.class) : is.a(this, HomeActivity.class));
                finish();
            }
        } else {
            startActivity(is.a(this, PhoneNonValidatedActivity.class));
            finish();
        }
    }

    private void b(boolean z) {
        super.a(j().a(this.g, z ? null : this.e.getEditableText().toString(), true, jf.a(), z), 1, true);
    }

    private void k() {
        Object obj = this.e.getText().toString();
        this.c.setEnabled(!TextUtils.isEmpty(obj));
        if (obj.length() >= 6) {
            b(false);
        }
    }

    private void l() {
        a(hv.a(getString(R.string.phone_validation_dialog_error_bad_scheme_title), getString(R.string.phone_validation_dialog_error_bad_scheme_message), getString(17039370), this, ie.BadSchemeType));
    }

    private void m() {
        if (this.e != null) {
            this.e.setText("");
        }
    }

    private void n() {
        a(ht.a(getString(R.string.login_dialog_update_title), getString(R.string.login_dialog_update_message), getString(R.string.login_dialog_b_update), getString(R.string.login_dialog_b_continue), this, ie.AppNewVersionType));
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 1) {
                    b(false);
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
            if (i2 == 1) {
                a((LoginData) bundle.getParcelable("fr.smoney.android.izly.extras.loginData"), (UserData) bundle.getParcelable("fr.smoney.android.izly.extras.userData"), serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        k();
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 1) {
            a(i2.b, i2.c, i2.d);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case ErrorType:
                SmoneyApplication.c.h();
                this.e.setText("");
                return;
            case BadSchemeType:
                finish();
                return;
            case CGUNotAcceptedType:
                SmoneyApplication.c.b();
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType) {
            g();
            SmoneyApplication.c.h();
            finish();
            return;
        }
        super.d(ieVar);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 20:
                if (i2 == -1) {
                    if (!this.i.G) {
                        startActivity(this.i.a() ? is.a(this, CompleteSubscriptionActivity.class) : is.a(this, HomeActivity.class));
                        finish();
                        break;
                    }
                    new Handler().post(new Runnable(this) {
                        final /* synthetic */ PhoneValidationActivity a;

                        {
                            this.a = r1;
                        }

                        public final void run() {
                            this.a.n();
                        }
                    });
                    break;
                }
                SmoneyApplication.c.b();
                if (i2 == 2) {
                    new Handler().post(new Runnable(this) {
                        final /* synthetic */ PhoneValidationActivity a;

                        {
                            this.a = r1;
                        }

                        public final void run() {
                            this.a.a(hv.a(this.a.getString(R.string.login_dialog_cgurefused_title), this.a.getString(R.string.login_dialog_cgurefused_message), this.a.getString(17039370), this.a, ie.CGUNotAcceptedType));
                        }
                    });
                    break;
                }
                break;
            case 30:
                if (i2 != -1) {
                    finish();
                    return;
                }
                return;
        }
        super.onActivityResult(i, i2, intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        finish();
        startActivity(intent);
    }

    public void onClick(View view) {
        if (view == this.c) {
            b(false);
        } else if (view == this.f) {
            startActivityForResult(new Intent(this, WelcomeActivity.class), 40);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Uri data = intent.getData();
        boolean booleanExtra = (data == null || !data.getHost().equals("SBSCR")) ? false : intent.getBooleanExtra("fr.smoney.android.izly.extras.startByBrowser", true);
        if (booleanExtra) {
            finish();
            Intent intent2 = new Intent(this, ActivityLauncherService.class);
            intent2.putExtra("fr.smoney.android.izly.extras.serviceActivityLauncher", getClass().getName());
            intent2.setData(intent.getData());
            startService(intent2);
        } else if (SmoneyApplication.c.f() == null || SmoneyApplication.c.a() == null) {
            int i;
            setContentView((int) R.layout.login);
            this.c = (Button) findViewById(R.id.b_submit);
            this.c.setOnClickListener(this);
            findViewById(R.id.et_login).setVisibility(8);
            this.d = (TextView) findViewById(R.id.tv_login_connected_with);
            this.e = (EditText) findViewById(R.id.et_password);
            this.e.addTextChangedListener(this);
            this.e.setOnEditorActionListener(this);
            this.f = (ImageView) findViewById(R.id.iv_tuto_login);
            this.f.setOnClickListener(this);
            data = getIntent().getData();
            if (data == null) {
                l();
                i = 0;
            } else {
                List pathSegments = data.getPathSegments();
                if (pathSegments.size() != 2) {
                    l();
                    i = 0;
                } else {
                    this.g = (String) pathSegments.get(0);
                    this.h = (String) pathSegments.get(1);
                    SmoneyApplication.c.b(this.g);
                    ac acVar = SmoneyApplication.c;
                    String str = this.h;
                    Editor edit = acVar.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
                    edit.putString("sharedPrefHotpActivationCode", str);
                    edit.putString("sharedPrefHotpCounter", Base64.encodeToString(ix.a(0), 8));
                    edit.commit();
                    booleanExtra = true;
                }
            }
            if (i != 0) {
                this.d.setText(jf.a(this.g));
                findViewById(R.id.activation).setVisibility(8);
                this.e.requestFocus();
                if (SmoneyApplication.c.c() != null) {
                    SmoneyApplication.c.d();
                }
                b(true);
            }
            k();
        } else {
            startActivity(is.a(this, HomeActivity.class));
            finish();
        }
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (this.c.isEnabled()) {
            b(false);
        }
        return false;
    }

    protected void onResume() {
        super.onResume();
        m();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
