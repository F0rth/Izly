package fr.smoney.android.izly.ui;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import defpackage.ht;
import defpackage.hw;
import defpackage.ie;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetMyCbListData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

import java.io.Serializable;

import org.apache.http.util.EncodingUtils;
import org.kxml2.wap.Wbxml;

import scanpay.it.SPCreditCard;
import scanpay.it.ScanPayActivity;

public class CbAddActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private static final String b = CbAddActivity.class.getSimpleName();
    private WebView c;
    private boolean d;
    private boolean e;
    private boolean f = false;
    private int g = 0;
    private int h = 0;
    private b i;
    private c j;
    private a k = a.InitStep;
    private MenuItem l;

    enum a {
        InitStep,
        VerificationStep
    }

    final class b extends WebViewClient {
        public boolean a;
        final /* synthetic */ CbAddActivity b;

        private b(CbAddActivity cbAddActivity) {
            this.b = cbAddActivity;
        }

        private static boolean a(String str) {
            return (str.indexOf("exec.cancel.a") == -1 && str.indexOf("exec.success.a") == -1 && str.indexOf("exec.refused.a") == -1 && str.indexOf("80.11.255.231") == -1) ? false : true;
        }

        public final void onLoadResource(WebView webView, String str) {
            boolean z = true;
            if (this.b.h != 0) {
                if (!(str.indexOf("exec.cancel.a") != -1)) {
                    if (!(str.indexOf("exec.refused.a") != -1)) {
                        if (str.indexOf("exec.success.a") == -1) {
                            z = false;
                        }
                        if (z) {
                            webView.stopLoading();
                            this.b.l();
                            return;
                        }
                        return;
                    }
                }
                this.b.setResult(0);
                this.b.finish();
            } else if (a(str)) {
                this.b.e = true;
                webView.stopLoading();
                this.b.l();
            }
        }

        public final void onPageFinished(WebView webView, String str) {
            if (str.indexOf("exec.paymentChoice.a") != -1) {
                this.a = true;
                this.b.supportInvalidateOptionsMenu();
            } else if (this.a) {
                this.a = false;
                this.b.supportInvalidateOptionsMenu();
            }
            if (!this.b.e && this.b.d) {
                this.b.d = false;
                this.b.d();
            }
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (a(str)) {
                this.b.e = true;
                webView.stopLoading();
                this.b.l();
            }
            if (!this.b.e && !this.b.d) {
                this.b.d = true;
                this.b.c();
            }
        }
    }

    final class c extends BroadcastReceiver {
        final /* synthetic */ CbAddActivity a;

        private c(CbAddActivity cbAddActivity) {
            this.a = cbAddActivity;
        }

        public final void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("fr.smoney.android.izly.sessionState", -1);
            if (intExtra == 0) {
                this.a.e();
            } else if (intExtra == 1) {
                this.a.l();
            } else if (intExtra == 2) {
                this.a.a(hw.a(this.a, this.a, ie.ConnexionErrorDuringIsSessionValid));
            }
        }
    }

    private void a(GetMyCbListData getMyCbListData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getMyCbListData == null) {
            a(hw.a(this, this));
        } else if (this.k == a.InitStep) {
            this.g = getMyCbListData.a.size();
            this.k = a.VerificationStep;
            k();
        } else if (this.k == a.VerificationStep && getMyCbListData != null) {
            if (getMyCbListData.a.size() > this.g) {
                Intent intent = new Intent();
                intent.putExtra("fr.smoney.android.izly.cbListExtra", getMyCbListData.a);
                setResult(-1, intent);
                finish();
                return;
            }
            a(ht.a(getString(R.string.dialog_error_during_adding_first_card_title), getString(R.string.dialog_error_during_adding_first_card_message), getString(17039379), getString(17039369), this, ie.NoCBType));
        }
    }

    private void k() {
        if (!this.f) {
            cl i = i();
            this.c.postUrl("https://mon-espace.izly.fr/tools/addCB", EncodingUtils.getBytes(getString(R.string.cb_add_url_parameters_format, new Object[]{"phoneNumber", i.b.a, "sessionId", i.b.c}), "BASE64"));
            this.f = true;
        }
    }

    private void l() {
        cl i = i();
        super.a(j().d(i.b.a, i.b.c), 230, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case NoCBType:
                this.f = false;
                k();
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 230) {
                a((GetMyCbListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMyCbList"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case NoCBType:
                setResult(0);
                finish();
                return;
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 230) {
            a(i2.bz, i2.s);
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType) {
            finish();
        } else {
            super.d(ieVar);
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 9736 && i2 == 1) {
            SPCreditCard sPCreditCard = (SPCreditCard) intent.getParcelableExtra("creditcard");
            this.c.loadUrl(String.format("javascript:(function() {document.forms['ref_form'].vads_card_number.value = '%s'; document.forms['ref_form'].vads_expiry_month.value = %s; document.forms['ref_form'].vads_expiry_year.value = 20%s; document.forms['ref_form'].vads_cvv.value = %s;})()", new Object[]{sPCreditCard.a, sPCreditCard.c, sPCreditCard.d, sPCreditCard.e}));
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = new WebView(this);
        this.c.getSettings().setJavaScriptEnabled(true);
        setContentView(this.c);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.i = new b();
        this.c.setWebViewClient(this.i);
        this.d = false;
        this.e = false;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.h = extras.getInt("fr.smoney.android.izly.intentExtraMode", 0);
        }
        if (bundle != null) {
            Serializable serializable = bundle.getSerializable("savedInstanceStateCheckStep");
            if (serializable != null && (serializable instanceof a)) {
                this.k = (a) serializable;
            }
            this.f = bundle.getBoolean("savedInstanceStateisDialogShown");
            this.g = bundle.getInt("savedInstanceStateCbCount", 0);
        }
        if (!this.f) {
            l();
        }
        jb.a(getApplicationContext(), R.string.screen_name_add_credit_card_details_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.l = menu.add("Scanner votre carte").setIcon(R.drawable.pict_scanpay);
        this.l.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4 || !this.f) {
            return super.onKeyUp(i, keyEvent);
        }
        l();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            l();
            return true;
        } else if (menuItem != this.l) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            Intent intent = new Intent(this, ScanPayActivity.class);
            try {
                intent.putExtra("sptoken", "87e057091488176384c1427ecc2e072fe500b039");
                startActivityForResult(intent, 9736);
                return true;
            } catch (Throwable e) {
                Toast.makeText(this, "Scanpay n'a pas pu se lancer", 0).show();
                Log.e(b, "Erreur scanpay", e);
                return true;
            }
        }
    }

    protected void onPause() {
        unregisterReceiver(this.j);
        this.j = null;
        super.onPause();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        this.l.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void onRestart() {
        super.onRestart();
        this.c.setWebViewClient(this.i);
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.c.restoreState(bundle);
    }

    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.j == null) {
            this.j = new c();
        }
        registerReceiver(this.j, intentFilter);
        this.c.requestFocus(Wbxml.EXT_T_2);
        this.c.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ CbAddActivity a;

            {
                this.a = r1;
            }

            public final boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                    case 1:
                        if (!view.hasFocus()) {
                            view.requestFocus();
                            break;
                        }
                        break;
                }
                return false;
            }
        });
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("savedInstanceStateCheckStep", this.k);
        bundle.putInt("savedInstanceStateCbCount", this.g);
        bundle.putBoolean("savedInstanceStateisDialogShown", this.f);
        this.c.saveState(bundle);
    }

    protected void onStop() {
        this.c.stopLoading();
        this.c.setWebViewClient(null);
        super.onStop();
    }
}
