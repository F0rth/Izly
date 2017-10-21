package fr.smoney.android.izly.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;

import defpackage.ht;
import defpackage.hu;
import defpackage.hv;
import defpackage.hw;
import defpackage.ie;
import defpackage.ir;
import defpackage.is;
import defpackage.jb;
import defpackage.jf;
import defpackage.jh;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.IsEnrollmentOpenData;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserData;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.CGUActivity.a;

import java.io.IOException;

public class LoginActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, OnEditorActionListener, SmoneyRequestManager$a {
    private EditText b;
    private EditText c;
    private Button d;
    private Button e;
    private TextView f;
    private Button g;
    private String h;
    private String i;
    private String j;
    private TextView k;
    private LoginData l;
    private ImageView m;
    private boolean n;
    private boolean o;
    private TextWatcher p = new TextWatcher(this) {
        final /* synthetic */ LoginActivity a;

        {
            this.a = r1;
        }

        public final void afterTextChanged(Editable editable) {
            String obj = editable.toString();
            if (!TextUtils.isEmpty(this.a.b.getText().toString()) && obj.length() >= 6) {
                this.a.s();
            }
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    };

    private void a(IsEnrollmentOpenData isEnrollmentOpenData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (isEnrollmentOpenData == null) {
        } else {
            if (isEnrollmentOpenData.a) {
                startActivity(is.a(this, SubscribeActivity.class));
            } else {
                startActivity(is.a(this, InviteRequestActivity.class));
            }
        }
    }

    private void a(LoginData loginData, UserData userData, ServerError serverError) {
        if (serverError != null) {
            l();
            if (serverError.b == 120) {
                a(hu.a(serverError.d, serverError.c, getString(17039370)));
            } else {
                a(serverError);
            }
        } else if (loginData == null && userData == null) {
            a(hw.a(this, this));
        } else if (loginData != null) {
            this.l = loginData;
            ir.a(this, this.l);
            Intent intent;
            if (loginData.H) {
                if (loginData.F) {
                    a(hv.a(getString(R.string.login_dialog_cgurefused_title), getString(R.string.login_dialog_cgucashier_message), getString(17039370), this, ie.CGUNotAcceptedType));
                    return;
                }
                intent = new Intent(this, CGUActivity.class);
                intent.putExtra("fr.smoney.android.izly.extras.displayCase", a.CGU_CHANGED);
                startActivityForResult(intent, 20);
            } else if (loginData.G) {
                p();
            } else if (SmoneyApplication.c.f() != null && SmoneyApplication.c.a() != null) {
                if (getIntent().getBooleanExtra("startFromWebPayement", false)) {
                    intent = new Intent(this, P2PPayActivity.class);
                    intent.putExtras(getIntent());
                    startActivity(intent);
                    finish();
                } else if (this.n) {
                    n();
                } else {
                    m();
                }
            }
        } else {
            SmoneyApplication.c.b(userData.a);
            startActivity(is.a(this, PhoneNonValidatedActivity.class));
            finish();
        }
    }

    public static boolean a(int i, int i2) {
        return (i & i2) == i2;
    }

    private void b(boolean z) {
        String obj = this.b.getEditableText().toString();
        i().a = obj;
        super.a(j().a(obj, this.h, z, jf.a(), false), 1, true);
    }

    private void k() {
        if (SmoneyApplication.c.e()) {
            findViewById(R.id.ll_login_infos_number).setVisibility(8);
            this.b.setText("");
            return;
        }
        String f = SmoneyApplication.c.f();
        this.b.setText(f);
        this.k.setText(jf.a(f, true));
        this.b.setVisibility(8);
        this.g.setVisibility(8);
    }

    private void l() {
        this.c.setText("");
    }

    private void m() {
        startActivityForResult(is.a(this, HomeActivity.class), 30);
    }

    private void n() {
        Intent a = is.a(this, P2PPayActivity.class);
        a.putExtra("startFromWebPayement", true);
        Intent intent = getIntent();
        if ("android.intent.action.VIEW".equals(intent.getAction())) {
            Uri parse = Uri.parse(intent.getData().toString().toLowerCase());
            if (parse != null) {
                String queryParameter = parse.getQueryParameter("amount");
                String queryParameter2 = parse.getQueryParameter("appid");
                String queryParameter3 = parse.getQueryParameter("receiver");
                String queryParameter4 = parse.getQueryParameter("transactionid");
                String queryParameter5 = parse.getQueryParameter("message");
                String queryParameter6 = parse.getQueryParameter("editablefields");
                String queryParameter7 = parse.getQueryParameter("signature");
                String queryParameter8 = parse.getQueryParameter("callback");
                Parcelable p2PPayCommerceInfos = new P2PPayCommerceInfos();
                try {
                    double parseDouble = Double.parseDouble(queryParameter) / 100.0d;
                    if (parseDouble >= 0.0d) {
                        p2PPayCommerceInfos.b = parseDouble;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, R.string.invalid_amount_number, 1).show();
                }
                p2PPayCommerceInfos.d = queryParameter2;
                p2PPayCommerceInfos.a = queryParameter3;
                p2PPayCommerceInfos.e = queryParameter4;
                p2PPayCommerceInfos.g = queryParameter7;
                p2PPayCommerceInfos.j = queryParameter5;
                p2PPayCommerceInfos.r = Boolean.parseBoolean(queryParameter8);
                if (queryParameter6 != null && queryParameter6.length() > 0) {
                    int parseInt = Integer.parseInt(queryParameter6);
                    p2PPayCommerceInfos.q = parseInt;
                    p2PPayCommerceInfos.o = a(parseInt, 1);
                    p2PPayCommerceInfos.n = a(parseInt, 2);
                    p2PPayCommerceInfos.p = a(parseInt, 4);
                }
                a.putExtra("startFromWebPayementObject", p2PPayCommerceInfos);
                startActivity(a);
                finish();
            }
        }
    }

    private void o() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://mon-espace.izly.fr/Home/Mobile"));
        startActivity(intent);
    }

    private void p() {
        a(ht.a(getString(R.string.login_dialog_update_title), getString(R.string.login_dialog_update_message), getString(R.string.login_dialog_b_update), getString(R.string.login_dialog_b_continue), this, ie.AppNewVersionType));
    }

    private void q() {
        int keyAt;
        SmoneyRequestManager j = j();
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            if (((Intent) j.b.valueAt(i)).getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 218) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent = new Intent(j.c, SmoneyService.class);
        intent.putExtra("com.foxykeep.datadroid.extras.workerType", 218);
        intent.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        j.c.startService(intent);
        j.b.append(keyAt, intent);
        j.f.bi = null;
        j.f.bj = null;
        super.a(keyAt, 218, true);
    }

    private void r() {
        jb.a(getApplicationContext(), R.string.screen_name_activation_activity);
        startActivity(is.a(this, ActivationActivity.class));
    }

    private void s() {
        this.j = this.b.getText().toString();
        if (SmoneyApplication.c.e()) {
            this.h = this.c.getEditableText().toString();
            b(false);
        } else if (SmoneyApplication.c.a(this.b.getEditableText().toString())) {
            this.h = this.c.getEditableText().toString();
            b(true);
        } else {
            a(ht.a(getString(R.string.login_dialog_overwrite_user_title), getString(R.string.login_dialog_overwrite_user_message), getString(17039370), getString(17039360), this, ie.OverWriteUserType));
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                int h = h();
                if (h == 1) {
                    b(SmoneyApplication.c.a(this.b.getText().toString()));
                    return;
                } else if (h == 218) {
                    q();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case OverWriteUserType:
                SmoneyApplication.c.g();
                SmoneyApplication.c.b();
                b(false);
                return;
            case RetrievePasswordType:
                Intent intent = new Intent(this, RetrievePasswordInitiatePasswordRecoveryActivity.class);
                intent.putExtra("fr.smoney.android.izly.ui.intentExtraMode", 0);
                intent.putExtra("fr.smoney.android.izly.ui.intentUserId", this.b.getText().toString());
                startActivity(intent);
                return;
            case AccountBlockedType:
                Intent intent2 = new Intent(this, RetrievePasswordInitiatePasswordRecoveryActivity.class);
                intent2.putExtra("fr.smoney.android.izly.ui.intentExtraMode", 1);
                intent2.putExtra("fr.smoney.android.izly.ui.intentUserId", this.b.getText().toString().equalsIgnoreCase("") ? this.j : this.b.getText().toString());
                startActivity(intent2);
                return;
            case AppOutdatedType:
                o();
                return;
            case AppNewVersionType:
                o();
                return;
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
            } else if (i2 == 218) {
                a((IsEnrollmentOpenData) bundle.getParcelable("fr.smoney.android.izly.extras.IsEnrollementOpen"), serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        CharSequence obj = this.c.getText().toString();
        Button button = this.d;
        boolean z = (TextUtils.isEmpty(this.b.getText().toString()) || TextUtils.isEmpty(obj)) ? false : true;
        button.setEnabled(z);
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case AccountBlockedType:
                finish();
                return;
            case AppNewVersionType:
                if (this.n) {
                    n();
                    return;
                } else {
                    m();
                    return;
                }
            default:
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 1) {
            a(i2.b, i2.c, i2.d);
        } else if (i == 218) {
            a(i2.bi, i2.bj);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case PlayStoreInvalidStatusType:
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

    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 10:
                if (i2 != -1) {
                    finish();
                    return;
                }
                return;
            case 20:
                if (i2 == -1) {
                    if (!this.l.G) {
                        if (!this.n) {
                            m();
                            break;
                        } else {
                            n();
                            break;
                        }
                    }
                    new Handler().post(new Runnable(this) {
                        final /* synthetic */ LoginActivity a;

                        {
                            this.a = r1;
                        }

                        public final void run() {
                            this.a.p();
                        }
                    });
                    break;
                }
                SmoneyApplication.c.b();
                if (i2 == 2) {
                    new Handler().post(new Runnable(this) {
                        final /* synthetic */ LoginActivity a;

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
            case 40:
                if (i2 == -1) {
                    r();
                    return;
                }
                return;
        }
        super.onActivityResult(i, i2, intent);
    }

    public void onClick(View view) {
        if (view == this.d) {
            s();
        } else if (view == this.e) {
            q();
        } else if (view == this.g) {
            r();
        } else if (view == this.m) {
            startActivityForResult(new Intent(this, WelcomeActivity.class), 40);
        } else if (view != this.f) {
        } else {
            if (!SmoneyApplication.c.e() || (this.b.getText().length() > 0 && jf.b(this.b.getText().toString()))) {
                a(ht.a(getString(2131230841), getString(R.string.login_dialog_forgotten_secret_code), getString(R.string.dialog_button_continue), getString(17039360), this, ie.RetrievePasswordType));
            } else {
                Toast.makeText(this, getString(R.string.login_toast_check_for_retrieve_password_error), 1).show();
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        boolean z = true;
        super.onCreate(bundle);
        setContentView((int) R.layout.login);
        this.k = (TextView) findViewById(R.id.tv_login_connected_with);
        this.b = (EditText) findViewById(R.id.et_login);
        this.b.addTextChangedListener(this);
        this.c = (EditText) findViewById(R.id.et_password);
        this.c.addTextChangedListener(this);
        this.c.addTextChangedListener(this.p);
        this.c.setOnEditorActionListener(this);
        this.d = (Button) findViewById(R.id.b_submit);
        this.d.setOnClickListener(this);
        this.e = (Button) findViewById(R.id.b_subscribe);
        this.e.setOnClickListener(this);
        this.f = (TextView) findViewById(R.id.ll_forgotten_password);
        this.f.setText(Html.fromHtml(getString(R.string.login_b_forgotten_password)));
        this.f.setOnClickListener(this);
        this.g = (Button) findViewById(R.id.activation);
        this.g.setOnClickListener(this);
        this.m = (ImageView) findViewById(R.id.iv_tuto_login);
        this.m.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("fr.smoney.android.izly.config.sp", 0);
        if (sharedPreferences.getBoolean("sharedPrefAppFolderRightModified", true)) {
            try {
                Runtime.getRuntime().exec("/system/bin/chmod 751 " + getFilesDir().getParent());
                Editor edit = sharedPreferences.edit();
                edit.putBoolean("sharedPrefAppFolderRightModified", false);
                edit.commit();
            } catch (IOException e) {
            }
        }
        if (bundle != null) {
            this.h = bundle.getString("savedStateCurrentPassword");
        }
        Intent intent = getIntent();
        int i;
        if (intent != null) {
            int intExtra = intent.getIntExtra("fr.smoney.android.izly.extras.launchActivity", -1);
            Intent a;
            switch (intExtra) {
                case 1:
                    this.i = intent.getStringExtra("fr.smoney.android.izly.intentExtra.notifMessage");
                    i = intExtra;
                    break;
                case 2:
                    finish();
                    i = intExtra;
                    break;
                case 3:
                    k();
                    i = intExtra;
                    break;
                case 4:
                    String string;
                    String str;
                    k();
                    ServerError serverError = (ServerError) intent.getParcelableExtra("fr.smoney.android.izly.intentExtra.sessionExpiredServerError");
                    switch (serverError.b) {
                        case 102:
                        case 506:
                            a(ht.a(serverError.d, serverError.c, getString(R.string.login_dialog_unblock_account_unblock_button), getString(17039360), this, ie.AccountBlockedType));
                            i = intExtra;
                            break;
                        case 324:
                            a(ht.a(serverError.d, serverError.c, getString(R.string.login_dialog_b_update), getString(17039360), this, ie.AppOutdatedType));
                            i = intExtra;
                            break;
                        case 500:
                            a(ht.a(serverError.d, serverError.c, getString(R.string.login_dialog_unblock_account_raise_opposition_button), getString(17039360), this, ie.AccountBlockedType));
                            i = intExtra;
                            break;
                        case 570:
                        case 571:
                        case 573:
                        case 576:
                            string = !TextUtils.isEmpty(serverError.d) ? serverError.d : getString(R.string.dialog_error_data_error_title);
                            str = serverError.c;
                            break;
                        default:
                            string = !TextUtils.isEmpty(serverError.d) ? serverError.d : getString(R.string.dialog_error_data_error_title);
                            if (!TextUtils.isEmpty(serverError.c)) {
                                str = serverError.c;
                                break;
                            } else {
                                str = getString(R.string.dialog_error_data_error_message);
                                break;
                            }
                    }
                    a(hu.a(string, str, getString(17039370)));
                    i = intExtra;
                    break;
                case 5:
                    a = is.a(this, PhoneValidationActivity.class);
                    a.setData(intent.getData());
                    a.putExtra("fr.smoney.android.izly.extras.startByBrowser", getIntent().getExtras().getBoolean("fr.smoney.android.izly.extras.startByBrowser"));
                    a.setFlags(335544320);
                    startActivity(a);
                    finish();
                    i = intExtra;
                    break;
                default:
                    Uri data = intent.getData();
                    boolean z2 = data != null && ((data.getHost().equals(getString(R.string.payment_host_web)) || data.getHost().equals(getString(R.string.payment_host_normal))) && intent.getBooleanExtra("fr.smoney.android.izly.extras.startByBrowser", true));
                    this.n = z2;
                    data = intent.getData();
                    if (data == null || !data.getHost().equals("MKG")) {
                        z = false;
                    }
                    this.o = z;
                    if (SmoneyApplication.c.f() != null && SmoneyApplication.c.a() != null) {
                        if (!intent.getBooleanExtra("startFromWebPayement", false)) {
                            if (!this.n) {
                                if (!this.o) {
                                    m();
                                    i = intExtra;
                                    break;
                                }
                                a = is.a(this, HomeActivity.class);
                                a.setData(intent.getData());
                                startActivityForResult(a, 30);
                                this.o = false;
                                i = intExtra;
                                break;
                            }
                            n();
                            i = intExtra;
                            break;
                        }
                        a = new Intent(this, P2PPayActivity.class);
                        a.putExtras(intent);
                        startActivity(a);
                        finish();
                        i = intExtra;
                        break;
                    }
                    k();
                    i = intExtra;
                    break;
                break;
            }
        }
        i = -1;
        if (SmoneyApplication.c.e() && r0 != 4 && SmoneyApplication.c.c() == null) {
            startActivityForResult(new Intent(this, WelcomeActivity.class), 40);
        }
        jb.a(getApplicationContext(), R.string.screen_name_login_activity);
        jh.a();
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        CharSequence obj = this.c.getText().toString();
        if (!(TextUtils.isEmpty(this.b.getText().toString()) || TextUtils.isEmpty(obj))) {
            s();
        }
        return false;
    }

    protected void onResume() {
        Object obj = 1;
        super.onResume();
        l();
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isGooglePlayServicesAvailable != 0) {
            if (isGooglePlayServicesAvailable == 1 || isGooglePlayServicesAvailable == 2 || isGooglePlayServicesAvailable == 3) {
                GooglePlayServicesUtil.getErrorDialog(isGooglePlayServicesAvailable, this, 10, new OnCancelListener(this) {
                    final /* synthetic */ LoginActivity a;

                    {
                        this.a = r1;
                    }

                    public final void onCancel(DialogInterface dialogInterface) {
                        this.a.finish();
                    }
                }).show();
            } else if (isGooglePlayServicesAvailable == 9) {
                a(hv.a(getString(R.string.dialog_error_title), getString(R.string.dialog_error_playstore_status_invalid_message), getString(17039370), this, ie.PlayStoreInvalidStatusType));
            }
            obj = null;
        }
        if (obj != null && this.i != null) {
            a(hu.a(getString(2131230841), this.i, getString(R.string.dialog_button_close)));
            this.i = null;
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("savedStateCurrentPassword", this.h);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
