package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import defpackage.hz;
import defpackage.ie;
import defpackage.is;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class BlockAccountActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private EditText b;
    private Button c;
    private String d;

    private void b(ServerError serverError) {
        if (serverError != null) {
            a(serverError);
            return;
        }
        ((SmoneyApplication) getApplication()).a();
        startActivity(is.a(this, BlockAccountResultActivity.class));
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String obj = this.b.getText().toString();
        String str3 = this.d;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 181 && intent.getStringExtra("fr.smoney.android.izly.extras.blockAccountUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.blockAccountSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.blockAccountMessage").equals(obj) && intent.getStringExtra("fr.smoney.android.izly.extras.blockAccountPassword").equals(str3)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 181);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.blockAccountUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.blockAccountSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.blockAccountMessage", obj);
        intent2.putExtra("fr.smoney.android.izly.extras.blockAccountPassword", str3);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.aQ = null;
        j.f.aR = null;
        super.a(keyAt, 181, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 181) {
                    k();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case InputPasswordType:
                this.d = bundle.getString("Data.Password");
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
            if (i2 == 181) {
                bundle.getParcelable("fr.smoney.android.izly.extras.blockAccountData");
                b(serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 181) {
            b(i2.aR);
        }
    }

    public final void d(ie ieVar) {
        switch (ieVar) {
            case InputPasswordType:
                return;
            default:
                super.d(ieVar);
                return;
        }
    }

    public void onClick(View view) {
        if (view == this.c) {
            a(hz.a(this, this));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.block_account);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.b = (EditText) findViewById(R.id.et_explanation);
        this.c = (Button) findViewById(R.id.b_confirm);
        this.c.setOnClickListener(this);
        if (bundle != null) {
            this.d = bundle.getString("savedStateCurrentPassword");
        }
        jb.a(getApplicationContext(), R.string.screen_name_block_account_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("savedStateCurrentPassword", this.d);
    }
}
