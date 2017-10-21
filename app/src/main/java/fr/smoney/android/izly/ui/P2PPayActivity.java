package fr.smoney.android.izly.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.IconTextView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import defpackage.gd;
import defpackage.ht;
import defpackage.hu;
import defpackage.hv;
import defpackage.hw;
import defpackage.ie;
import defpackage.if$a;
import defpackage.is;
import defpackage.iu;
import defpackage.iy;
import defpackage.ja;
import defpackage.jb;
import defpackage.je;
import defpackage.jj;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.Bookmark;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.GetBookmarksData;
import fr.smoney.android.izly.data.model.GetContactDetailsData;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.NearPro;
import fr.smoney.android.izly.data.model.NearPro.Tills;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.data.model.P2PPayData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.TillList;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.ContactAutoCompleteTextView;

import java.util.Currency;
import java.util.Iterator;
import java.util.List;

public class P2PPayActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, OnItemSelectedListener, SmoneyRequestManager$a, if$a {
    private P2PPayCommerceInfos A;
    private TextWatcher B = new TextWatcher(this) {
        final /* synthetic */ P2PPayActivity a;

        {
            this.a = r1;
        }

        public final void afterTextChanged(Editable editable) {
            ImageSpan[] imageSpanArr = (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class);
            if (imageSpanArr.length > 0 && imageSpanArr[0].getSource().length() != editable.length()) {
                this.a.d.setText(imageSpanArr[0].getSource());
            }
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    };
    private boolean b = false;
    private boolean c = false;
    private ContactAutoCompleteTextView d;
    private IconTextView e;
    private EditText f;
    private TextView g;
    private EditText h;
    private Button i;
    private Button j;
    private View k;
    private View l;
    private Spinner m;
    private boolean n = false;
    private View o;
    private View p;
    private View q;
    private TillList r = null;
    private P2PPayData s;
    private Location t;
    private boolean u = false;
    private boolean v = true;
    private int w = -1;
    private cl x;
    private Bundle y;
    private boolean z;

    final class a extends AsyncTask<Void, Void, List<Contact>> {
        final /* synthetic */ P2PPayActivity a;
        private List<Bookmark> b;

        public a(P2PPayActivity p2PPayActivity, List<Bookmark> list) {
            this.a = p2PPayActivity;
            this.b = list;
        }

        protected final /* synthetic */ Object doInBackground(Object[] objArr) {
            return iy.b(this.b);
        }

        protected final /* synthetic */ void onPostExecute(Object obj) {
            this.a.d.a((List) obj);
        }
    }

    final class b extends AsyncTask<Void, Void, List<Contact>> {
        final /* synthetic */ P2PPayActivity a;

        private b(P2PPayActivity p2PPayActivity) {
            this.a = p2PPayActivity;
        }

        protected final /* synthetic */ Object doInBackground(Object[] objArr) {
            return iy.a(this.a);
        }

        protected final /* synthetic */ void onPostExecute(Object obj) {
            this.a.d.a((List) obj);
            this.a.b = true;
        }
    }

    final class c extends ArrayAdapter<Tills> {
        final /* synthetic */ P2PPayActivity a;

        public c(P2PPayActivity p2PPayActivity, Context context, int i) {
            this.a = p2PPayActivity;
            super(context, R.layout.listitem_tills);
        }

        public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
            View inflate = view == null ? ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(R.layout.listitem_tills, viewGroup, false) : view;
            ((TextView) inflate).setText(((Tills) getItem(i)).b);
            return inflate;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = view == null ? ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(17367048, viewGroup, false) : view;
            ((TextView) inflate).setText(((Tills) getItem(i)).b);
            return inflate;
        }
    }

    private void a(Bundle bundle) {
        if (bundle != null) {
            this.s = (P2PPayData) bundle.getParcelable("saved_instance_state_must_add_fund");
            this.w = bundle.getInt("saved_instance_state_selected_till", -1);
        }
        if (this.A != null) {
            this.d.setText(this.A.a);
            double d = this.A.b;
            if (d > 0.0d) {
                this.f.setText(String.valueOf(d));
            }
            this.h.setText(this.A.j);
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CharSequence string = extras.getString("defaultRecipientId");
            if (string != null) {
                this.d.setText(string);
            }
            string = extras.getString("productAmount");
            if (string != null) {
                this.f.setText(string);
            }
            Parcelable parcelable = extras.getParcelable("nearProData");
            if (parcelable != null) {
                if (parcelable instanceof NearPro) {
                    NearPro nearPro = (NearPro) parcelable;
                    this.r = new TillList();
                    this.r.a = nearPro.q;
                    b(true);
                } else if (parcelable instanceof GetContactDetailsData) {
                    this.r = new TillList();
                    this.r.a = ((GetContactDetailsData) parcelable).I;
                    b(true);
                }
            }
        }
        this.g.setText(Currency.getInstance(this.x.b.j).getSymbol());
        this.x.b.n = 0;
        this.d.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ P2PPayActivity a;

            {
                this.a = r1;
            }

            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (!this.a.b && !this.a.c) {
                    this.a.c = true;
                    if (ContextCompat.checkSelfPermission(this.a, "android.permission.READ_CONTACTS") != 0) {
                        ActivityCompat.requestPermissions(this.a, new String[]{"android.permission.READ_CONTACTS"}, 118);
                        return;
                    }
                    new b().execute(new Void[0]);
                }
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        if (this.x.ao == null) {
            r();
        } else {
            new a(this, this.x.ao.b).execute(new Void[0]);
            findViewById(R.id.pb_recipient).setVisibility(8);
        }
        if (this.z && this.A != null && this.A.a()) {
            if (!this.A.n) {
                this.f.setEnabled(false);
            }
            if (!this.A.o) {
                this.j.setVisibility(8);
                this.e.setVisibility(8);
                this.d.setEnabled(false);
            }
            if (!this.A.p) {
                this.h.setEnabled(false);
            }
        }
    }

    private void a(GetBookmarksData getBookmarksData, ServerError serverError) {
        findViewById(R.id.pb_recipient).setVisibility(8);
        if (serverError == null && getBookmarksData != null) {
            new a(this, getBookmarksData.b).execute(new Void[0]);
        }
    }

    private void a(LoginData loginData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (loginData == null) {
            a(hw.a(this, this));
        } else {
            this.v = true;
            n();
            Bundle bundle = this.y;
            getIntent();
            a(bundle);
            this.y = null;
            if (this.A.a()) {
                m();
            } else {
                t();
            }
        }
    }

    private void a(P2PPayData p2PPayData) {
        if (this.z) {
            p2PPayData.g = true;
        }
        if (p2PPayData.b) {
            o();
            return;
        }
        Intent intent = new Intent(this, P2PPayConfirmActivity.class);
        intent.putExtra("fr.smoney.android.izly.extras.p2pPayData", p2PPayData);
        if (this.z) {
            intent.putExtra("startFromWebPayementObject", getIntent().getParcelableExtra("startFromWebPayementObject"));
        }
        startActivity(intent);
        l();
    }

    private void a(P2PPayData p2PPayData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PPayData == null) {
            a(hw.a(this, this));
        } else if (p2PPayData.f) {
            this.s = p2PPayData;
            if (this.x.b.E <= 0) {
                a(ht.a(getString(R.string.p2p_pay_charity_dialog_title), getString(R.string.p2p_pay_charity_dialog_message_completion, new Object[]{this.s.a.h, this.s.a.h}), getString(17039370), getString(17039360), this, ie.PayToCharityNoAdressType));
            } else if (p2PPayData.e) {
                if (this.n) {
                    a(this.s);
                    return;
                }
                a(hv.a(getString(R.string.p2p_pay_charity_dialog_title), getString(R.string.p2p_pay_charity_dialog_message, new Object[]{this.s.a.h}), getString(17039370), this, ie.PayToCharityResult));
            } else if (this.n) {
                this.s.e = true;
                a(this.s);
            } else {
                a(ht.a(getString(R.string.p2p_pay_charity_dialog_title), getString(R.string.p2p_pay_charity_dialog_message_opt_in, new Object[]{this.s.a.h, this.s.a.h}), getString(17039370), getString(17039360), this, ie.PayToCharityNoOptInPartnersType));
            }
        } else {
            a(p2PPayData);
        }
    }

    private boolean a(double d) {
        if (d <= 0.0d) {
            a(hu.a(getString(R.string.general_dialog_error_negative_amount_title), getString(R.string.general_dialog_error_negative_amount_message), getString(17039370)));
            return false;
        } else if (d < this.x.b.d) {
            a(hu.a(getString(R.string.p2p_get_mult_dialog_error_min_reached_title), getString(R.string.p2p_get_mult_dialog_error_min_reached_message), getString(17039370)));
            return false;
        } else if (d <= this.x.b.e) {
            return true;
        } else {
            a(hu.a(getString(R.string.p2p_get_mult_dialog_error_max_reached_title), getString(R.string.p2p_get_mult_dialog_error_max_reached_message), getString(17039370)));
            return false;
        }
    }

    private void b(boolean z) {
        int i = 0;
        if (this.r.a.size() > 1) {
            this.k.setVisibility(8);
            this.j.setVisibility(z ? 8 : 0);
            this.l.setVisibility(0);
            SpinnerAdapter cVar = new c(this, this, R.layout.listitem_tills);
            Iterator it = this.r.a.iterator();
            while (it.hasNext()) {
                cVar.add((Tills) it.next());
            }
            this.m.setAdapter(cVar);
            this.d.setText("");
        } else if (this.r.a.size() == 1) {
            this.d.setText(((Tills) this.r.a.get(0)).a);
            this.k.setVisibility(0);
            Button button = this.j;
            if (z) {
                i = 8;
            }
            button.setVisibility(i);
            this.l.setVisibility(8);
        }
    }

    private void k() {
        Intent intent = new Intent(this, P2PPayConfirmActivity.class);
        if (this.z) {
            this.s.g = true;
            intent.putExtra("startFromWebPayementObject", getIntent().getParcelableExtra("startFromWebPayementObject"));
        }
        intent.putExtra("fr.smoney.android.izly.extras.p2pPayData", this.s);
        startActivity(intent);
        l();
    }

    private void l() {
        if (this.z && this.A != null && !this.A.a()) {
            finish();
        }
    }

    private void m() {
        this.o.setVisibility(0);
        this.p.setVisibility(8);
    }

    private void n() {
        Button button = this.i;
        boolean z = this.v && iu.a(this.f.getText().toString(), this.x.b.d, this.x.b.e) && (this.w >= 0 || !TextUtils.isEmpty(this.d.getText().toString()));
        button.setEnabled(z);
    }

    private void o() {
        a(ht.a(getString(R.string.dialog_insufficient_found_title), getString(R.string.dialog_insufficient_found_message), getString(17039370), getString(17039360), this, ie.InsufficientFundType));
    }

    private double p() {
        try {
            return iu.a(this.f.getEditableText().toString());
        } catch (gd e) {
            a(hu.a(getString(R.string.general_dialog_error_amount_format_title), getString(R.string.general_dialog_error_amount_format_message), getString(17039370)));
            return -1.0d;
        }
    }

    private String q() {
        return (this.r == null || this.r.a.size() <= 0) ? this.d.getEditableText().toString() : this.m.getSelectedItemPosition() == -1 ? ((Tills) this.r.a.get(0)).a : ((Tills) this.r.a.get(this.m.getSelectedItemPosition())).a;
    }

    private void r() {
        super.a(j().a(this.x.b.a, this.x.b.c), 105, false);
    }

    private void s() {
        double p = p();
        if (a(p)) {
            super.a(j().a(this.x.b.a, q(), p, this.h.getEditableText().toString(), this.x.b.c), 11, true);
        }
    }

    private void t() {
        double p = p();
        if (a(p)) {
            int keyAt;
            String q = q();
            String obj = this.h.getEditableText().toString();
            SmoneyRequestManager j = j();
            String str = this.x.b.a;
            Parcelable parcelable = this.A;
            String str2 = this.x.b.c;
            int size = j.b.size();
            for (int i = 0; i < size; i++) {
                Intent intent = (Intent) j.b.valueAt(i);
                if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 234 && intent.getStringExtra("fr.smoney.android.izly.extras.checkEcommerceUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.checkEcommerceRecipient").equals(q) && intent.getDoubleExtra("fr.smoney.android.izly.extras.checkEcommerceAmount", -1.0d) == p && intent.getParcelableExtra("fr.smoney.android.izly.extras.checkEcommercePayInfos") == parcelable && intent.getStringExtra("fr.smoney.android.izly.extras.checkEcommerceMessage").equals(obj) && intent.getStringExtra("fr.smoney.android.izly.extras.checkEcommerceSessionId").equals(str2)) {
                    keyAt = j.b.keyAt(i);
                    break;
                }
            }
            keyAt = SmoneyRequestManager.a.nextInt(1000000);
            Intent intent2 = new Intent(j.c, SmoneyService.class);
            intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 234);
            intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
            intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
            intent2.putExtra("fr.smoney.android.izly.extras.checkEcommerceUserId", str);
            intent2.putExtra("fr.smoney.android.izly.extras.checkEcommerceRecipient", q);
            intent2.putExtra("fr.smoney.android.izly.extras.checkEcommerceAmount", p);
            intent2.putExtra("fr.smoney.android.izly.extras.checkEcommercePayInfos", parcelable);
            intent2.putExtra("fr.smoney.android.izly.extras.checkEcommerceMessage", obj);
            intent2.putExtra("fr.smoney.android.izly.extras.checkEcommerceSessionId", str2);
            j.c.startService(intent2);
            j.b.append(keyAt, intent2);
            j.f.h = null;
            j.f.i = null;
            super.a(keyAt, 234, true);
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        Intent a;
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 11) {
                    s();
                    return;
                } else if (h() == 234) {
                    t();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case InsufficientFundType:
                if (this.x.b.a()) {
                    a = is.a(this, CompleteAccountWrapperActivity.class);
                    a.putExtra("fr.smoney.android.izly.extras.activityToStart", 2);
                    Bundle bundle2 = new Bundle();
                    if (this.z) {
                        this.x.f.g = true;
                    }
                    bundle2.putParcelable("fr.smoney.android.izly.extras.p2pPayData", this.x.f);
                    a.putExtra("fr.smoney.android.izly.extras.activityToStartDataBundle", bundle2);
                    startActivity(a);
                    return;
                }
                a = is.a(this, MoneyInCbAndPayActivity.class);
                if (this.z) {
                    this.x.f.g = true;
                }
                a.putExtra("fr.smoney.android.izly.extras.p2pPayData", this.x.f);
                startActivity(a);
                return;
            case PayToCharityNoAdressType:
                a = is.a(this, CompleteAccountWrapperActivity.class);
                a.putExtra("fr.smoney.android.izly.extras.isADirectCallForAddress", true);
                startActivityForResult(a, 2);
                return;
            case PayToCharityNoOptInPartnersType:
                if (this.s.b) {
                    o();
                    return;
                }
                this.s.e = true;
                k();
                return;
            case LocationSettingsType:
                startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
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
            if (i2 == 11) {
                a((P2PPayData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayData"), serverError);
            } else if (i2 == 234) {
                a((P2PPayData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayData"), serverError);
            } else if (i2 == 105) {
                a((GetBookmarksData) bundle.getParcelable("fr.smoney.android.izly.extras.getBookmarksData"), serverError);
            } else if (i2 == 228) {
                a((LoginData) bundle.getParcelable("fr.smoney.android.izly.extras.GetLogonInfos"), serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        n();
    }

    public final void b(ie ieVar) {
        if (ieVar != ie.ConnexionErrorType) {
            super.b(ieVar);
        } else if (h() == 228) {
            a(false);
        } else {
            super.b(ieVar);
        }
    }

    public final void b_(int i) {
        if (i == 11) {
            a(this.x.f, this.x.g);
        } else if (i == 234) {
            a(this.x.f, this.x.g);
        } else if (i == 105) {
            a(this.x.ao, this.x.ap);
        } else if (i == 228) {
            a(this.x.b, this.x.d);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case PayToCharityResult:
                if (this.s.b) {
                    o();
                    return;
                } else {
                    k();
                    return;
                }
            case ErrorType:
                if (this.z) {
                    jj.a(this, this.A, false);
                    return;
                } else {
                    super.c(ieVar);
                    return;
                }
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && this.z) {
            g();
            jj.a(this, this.A, false);
            return;
        }
        super.d(ieVar);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 1:
                if (i2 == 1) {
                    this.d.setText(intent.getStringExtra("resultIntentExtrasContactId"));
                    return;
                }
                return;
            case 2:
                if (i2 == -1) {
                    if (this.z) {
                        this.s.g = true;
                    }
                    a(this.s);
                    return;
                }
                return;
            case 3:
                if (i2 == 1) {
                    NearPro nearPro = (NearPro) intent.getParcelableExtra("nearProData");
                    if (nearPro != null) {
                        if (nearPro.o != null) {
                            Intent a = is.a(this, PreAuthorizePaymentActivity.class);
                            a.putExtra("INTENT_EXTRA_PRE_AUTHORIZATION", nearPro.o);
                            startActivity(a);
                        }
                        if (nearPro.q == null || nearPro.q.size() <= 0) {
                            this.w = -1;
                            this.r = null;
                            this.k.setVisibility(0);
                            this.j.setVisibility(0);
                            this.l.setVisibility(8);
                            this.d.setText(intent.getStringExtra("resultIntentExtrasNearProId"));
                            return;
                        }
                        this.r = new TillList();
                        this.r.a = nearPro.q;
                        b(false);
                        return;
                    }
                    return;
                }
                return;
            default:
                super.onActivityResult(i, i2, intent);
                return;
        }
    }

    public void onClick(View view) {
        if (view == this.i) {
            if (this.z) {
                t();
            } else {
                s();
            }
        } else if (view == this.e) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CONTACTS"}, 119);
                return;
            }
            r0 = is.a(this, ContactsActivity.class);
            r0.putExtra("fr.smoney.android.izly.intentExtrasModeContactPicker", 1);
            startActivityForResult(r0, 1);
        } else if (view == this.j) {
            je.a(this, this.d);
            if (this.u) {
                r0 = is.a(this, NearProListActivity.class);
                r0.putExtra("fr.smoney.android.izly.extras.INTENT_EXTRA_LOCATION", this.t);
                r0.putExtra("fr.smoney.android.izly.intentExtrasDisplayMode", 1);
                startActivityForResult(r0, 3);
                return;
            }
            a(ht.a(getString(R.string.dialog_display_location_settings_title), getString(R.string.dialog_display_location_settings_message), getString(17039379), getString(17039369), this, ie.LocationSettingsType));
        }
    }

    protected void onCreate(Bundle bundle) {
        CharSequence string;
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_pay);
        this.x = i();
        ActionBar supportActionBar = getSupportActionBar();
        this.o = findViewById(R.id.sv_content_view_pay);
        this.p = findViewById(R.id.pb_view_pay);
        this.q = findViewById(R.id.ll_pay_area_recipient);
        this.d = (ContactAutoCompleteTextView) findViewById(R.id.atv_recipient);
        this.d.addTextChangedListener(this);
        this.e = (IconTextView) findViewById(R.id.iv_choose_contact);
        this.e.setOnClickListener(this);
        this.f = (EditText) findViewById(R.id.et_amount);
        this.f.addTextChangedListener(this);
        this.f.setKeyListener(new ja(true, 3, 2));
        EditText editText = this.f;
        if (ad.b) {
            string = getString(R.string.dialog_amount_entry_et_amount_hint_no_sign, new Object[]{Double.valueOf(this.x.b.d), Double.valueOf(this.x.b.e)});
        } else {
            string = getString(R.string.p2p_get_simple_tv_amount_hint);
        }
        editText.setHint(string);
        this.g = (TextView) findViewById(R.id.tv_amount_symbol);
        this.h = (EditText) findViewById(R.id.et_comment);
        this.h.addTextChangedListener(this);
        this.i = (Button) findViewById(R.id.b_submit);
        this.i.setOnClickListener(this);
        this.j = (Button) findViewById(R.id.btn_pick_near_pro);
        this.j.setOnClickListener(this);
        this.k = findViewById(R.id.ll_pay_recipient);
        this.l = findViewById(R.id.ll_pay_tills);
        this.m = (Spinner) findViewById(R.id.spi_tills_recipient);
        this.m.setOnItemSelectedListener(this);
        n();
        this.d.addTextChangedListener(this.B);
        Intent intent = getIntent();
        this.z = intent.getBooleanExtra("startFromWebPayement", false);
        this.A = (P2PPayCommerceInfos) intent.getParcelableExtra("startFromWebPayementObject");
        if (intent.getBooleanExtra("fr.smoney.android.izly.extras.LaunchForExit", false)) {
            jj.a(this, this.A, true);
        } else if (this.z) {
            if (this.x.b == null) {
                this.y = bundle;
                this.v = false;
                n();
                super.a(j().a(SmoneyApplication.c.f()), 228, true);
            } else {
                a(bundle);
                if (this.A.a()) {
                    m();
                } else {
                    t();
                }
            }
            this.f.requestFocus();
        } else {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            m();
            a(bundle);
        }
        jb.a(getApplicationContext(), R.string.screen_name_send_money_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        this.w = i;
        n();
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                if (this.z) {
                    jj.a(this, this.A, false);
                    break;
                }
                return super.onKeyUp(i, keyEvent);
        }
        return super.onKeyUp(i, keyEvent);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        switch (i) {
            case 118:
                if (iArr.length > 0 && iArr[0] == 0) {
                    new b().execute(new Void[0]);
                    return;
                }
                return;
            case 119:
                if (iArr.length > 0 && iArr[0] == 0) {
                    Intent a = is.a(this, ContactsActivity.class);
                    a.putExtra("fr.smoney.android.izly.intentExtrasModeContactPicker", 1);
                    startActivityForResult(a, 1);
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.x.cw && !c(105)) {
            r();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.s != null) {
            bundle.putParcelable("saved_instance_state_must_add_fund", this.s);
        }
        bundle.putInt("saved_instance_state_selected_till", this.w);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
