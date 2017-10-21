package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;

import defpackage.gt;
import defpackage.hu;
import defpackage.hv;
import defpackage.ie;
import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.AddressValues;
import fr.smoney.android.izly.data.model.AddressValues.a;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

import java.util.EnumSet;
import java.util.Iterator;

public class CompleteMyAddressActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, SmoneyRequestManager$a {
    private EditText b;
    private EditText c;
    private EditText d;
    private View e;
    private Spinner f;
    private gt g;
    private AddressValues h;
    private boolean i;
    private MenuItem j;

    private void a(AddressValues addressValues) {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = addressValues.a;
        String str4 = addressValues.b;
        String str5 = addressValues.c;
        String valueOf = String.valueOf(addressValues.d.F);
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 220 && intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressWay").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressCode").equals(str4) && intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressCity").equals(str5) && intent.getStringExtra("fr.smoney.android.izly.extras.updateMyAddressCountry").equals(valueOf)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 220);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.updateMyAddressUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.updateMyAddressSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.updateMyAddressWay", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.updateMyAddressCode", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.updateMyAddressCity", str5);
        intent2.putExtra("fr.smoney.android.izly.extras.updateMyAddressCountry", valueOf);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bm = null;
        super.a(keyAt, 220, true);
    }

    private void b(ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (this.i) {
            setResult(-1);
            finish();
        } else {
            a(hv.a(getString(R.string.complete_my_address_dialog_title_success), getString(R.string.complete_my_address_dialog_message_success), getString(17039370), this, ie.AdressCompletedType));
        }
    }

    private void k() {
        if (this.d.getText().length() == 0 || this.c.getText().length() == 0 || this.b.getText().length() == 0) {
            this.e.setEnabled(false);
        } else {
            this.e.setEnabled(true);
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 220) {
                    a(this.h);
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
            if (i2 == 220) {
                b(serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        k();
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 220) {
            b(i2.bm);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case AdressCompletedType:
                setResult(-1);
                finish();
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public void onClick(View view) {
        Object obj;
        this.h = new AddressValues(this.b.getText().toString(), this.c.getText().toString(), this.d.getText().toString(), this.g.getCount() > 0 ? (a) this.g.getItem(this.f.getSelectedItemPosition()) : null);
        if (this.h.d.equals(a.Other)) {
            a(hu.a(getString(R.string.complete_my_address_error_title), getString(R.string.complete_my_address_country_value_other_not_allowed), getString(17039370)));
            obj = null;
        } else {
            obj = 1;
        }
        if (obj != null) {
            a(this.h);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.complete_address);
        this.i = getIntent().getBooleanExtra("fr.smoney.android.izly.extras.isADirectCallForAddress", false);
        this.b = (EditText) findViewById(R.id.complete_address_et_street);
        this.c = (EditText) findViewById(R.id.complete_address_et_postal_code);
        this.d = (EditText) findViewById(R.id.complete_address_et_city);
        this.e = findViewById(R.id.complete_address_b_update);
        this.f = (Spinner) findViewById(R.id.sp_country);
        this.g = new gt(this);
        Iterator it = EnumSet.allOf(a.class).iterator();
        while (it.hasNext()) {
            this.g.add((a) it.next());
        }
        this.f.setAdapter(this.g);
        this.e.setOnClickListener(this);
        this.b.addTextChangedListener(this);
        this.c.addTextChangedListener(this);
        this.d.addTextChangedListener(this);
        k();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.j = menu.add(R.string.menu_item_help);
        this.j.setIcon(R.drawable.pict_ab_help);
        this.j.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.j) {
            return super.onOptionsItemSelected(menuItem);
        }
        Intent a = is.a(this, WebViewHelperActivity.class);
        a.putExtra("webview_url", "https://mon-espace.izly.fr/Home/Help");
        a.putExtra("title_tag", R.string.subscribe_help_title);
        startActivity(a);
        return true;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
