package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify$IconValue;

import defpackage.ht;
import defpackage.hw;
import defpackage.ie;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.CbChangeAliasData;
import fr.smoney.android.izly.data.model.MoneyInCbCb;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class CbEditActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, SmoneyRequestManager$a {
    private DetailTwoText b;
    private DetailTwoText c;
    private EditText d;
    private Button e;
    private MoneyInCbCb f;
    private String g;
    private MenuItem h;

    private void a(CbChangeAliasData cbChangeAliasData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (cbChangeAliasData == null) {
            a(hw.a(this, this));
        } else {
            this.g = this.d.getText().toString();
            setResult(-1);
            finish();
        }
    }

    private void b(ServerError serverError) {
        if (serverError != null) {
            a(serverError);
            return;
        }
        setResult(-1);
        finish();
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = this.f.a;
        String obj = this.d.getText().toString();
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 81 && intent.getStringExtra("fr.smoney.android.izly.extras.cbChangeAliasUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.cbChangeAliasSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.cbChangeAliasCardId").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.cbChangeAliasNewAlias").equals(obj)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 81);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.cbChangeAliasUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.cbChangeAliasSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.cbChangeAliasCardId", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.cbChangeAliasNewAlias", obj);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.ac = null;
        j.f.ad = null;
        super.a(keyAt, 81, true);
    }

    private void l() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = this.f.a;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 82 && intent.getStringExtra("fr.smoney.android.izly.extras.cbDeleteUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.cbDeleteSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.cbDeletCardId").equals(str3)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 82);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.cbDeleteUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.cbDeleteSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.cbDeletCardId", str3);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.ae = null;
        j.f.af = null;
        super.a(keyAt, 82, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                int h = h();
                if (h == 81) {
                    k();
                    return;
                } else if (h == 82) {
                    l();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case CBDeleteType:
                l();
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 81) {
                a((CbChangeAliasData) bundle.getParcelable("fr.smoney.android.izly.extras.cbChangeAliasData"), serverError);
            } else if (i2 == 82) {
                bundle.getParcelable("fr.smoney.android.izly.extras.chooseDefaultIdData");
                b(serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        String obj = editable.toString();
        Button button = this.e;
        boolean z = !obj.equals(this.g) && obj.length() > 0;
        button.setEnabled(z);
        if (obj.equalsIgnoreCase("meufs")) {
            MediaPlayer.create(this, R.raw.sound).start();
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 81) {
            a(i2.ac, i2.ad);
        } else if (i == 82) {
            b(i2.af);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onClick(View view) {
        if (view == this.e) {
            k();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.cb_edit);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.b = (DetailTwoText) findViewById(R.id.tv_network);
        this.c = (DetailTwoText) findViewById(R.id.tv_hint);
        this.d = (EditText) findViewById(R.id.et_alias);
        this.d.addTextChangedListener(this);
        this.e = (Button) findViewById(R.id.b_submit);
        this.e.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.f = (MoneyInCbCb) intent.getParcelableExtra("intentExtraCb");
            this.g = this.f.b;
            setTitle(this.g);
        }
        this.b.setRightText(MoneyInCbCb.a(this, this.f.c));
        this.c.setRightText(this.f.d);
        this.d.setText(this.f.b);
        jb.a(getApplicationContext(), R.string.screen_name_credit_card_details_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.h = menu.add(R.string.menu_item_delete_cb);
        this.h.setIcon(new IconDrawable(this, Iconify$IconValue.fa_trash_o).colorRes(R.color.izly_blue).actionBarSize());
        this.h.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == this.h) {
            a(ht.a(getString(R.string.cb_edit_dialog_delete_title), getString(R.string.cb_edit_dialog_delete_message, new Object[]{this.g}), getString(R.string.cb_edit_dialog_delete_confirm), getString(17039360), this, ie.CBDeleteType));
            return true;
        } else if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            finish();
            return true;
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
