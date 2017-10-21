package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import defpackage.hu;
import defpackage.hw;
import defpackage.ie;
import defpackage.ii;
import defpackage.ij;
import defpackage.is;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UpdatePasswordData;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.FormEditText;

import java.util.regex.Pattern;

public class ChangePasswordActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private FormEditText b;
    private FormEditText c;
    private FormEditText d;
    private Button e;
    private String f;
    private ii g;
    private ii h;
    private ii i;

    final class a implements TextWatcher {
        final /* synthetic */ ChangePasswordActivity a;
        private View b;

        public a(ChangePasswordActivity changePasswordActivity, View view) {
            this.a = changePasswordActivity;
            this.b = view;
        }

        public final void afterTextChanged(Editable editable) {
            if (this.b.equals(this.a.c)) {
                this.a.i.a(this.a.c.getText().toString());
            } else if (this.b.equals(this.a.b)) {
                this.a.g.a(this.a.b.getText().toString());
                this.a.h.a(this.a.b.getText().toString());
            }
            this.a.k();
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    private void a(UpdatePasswordData updatePasswordData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
            if (serverError.b == 169) {
                this.c.setText("");
                this.d.setText("");
            }
        } else if (updatePasswordData == null) {
            a(hw.a(this, this));
        } else {
            SmoneyApplication.c.b();
            SmoneyApplication.c.h();
            startActivity(is.a(this, ChangePasswordResultActivity.class));
        }
    }

    private void k() {
        if (this.b.c() && this.c.c() && this.d.c()) {
            this.e.setEnabled(true);
        } else {
            this.e.setEnabled(false);
        }
    }

    private void l() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String obj = this.c.getText().toString();
        String obj2 = this.b.getText().toString();
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 161 && intent.getStringExtra("fr.smoney.android.izly.extras.updatePasswordUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.updatePasswordSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.updatePasswordNewPassword").equals(obj) && intent.getStringExtra("fr.smoney.android.izly.extras.updatePasswordPassword").equals(obj2)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 161);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.updatePasswordUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.updatePasswordSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.updatePasswordNewPassword", obj);
        intent2.putExtra("fr.smoney.android.izly.extras.updatePasswordPassword", obj2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.aG = null;
        j.f.aH = null;
        super.a(keyAt, 161, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 161) {
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
            if (i2 == 161) {
                a((UpdatePasswordData) bundle.getParcelable("fr.smoney.android.izly.extras.updatePasswordData"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 161) {
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
        if (i == 161) {
            a(i2.aG, i2.aH);
        }
    }

    public void onClick(View view) {
        if (view != this.e) {
            return;
        }
        if (!this.c.getText().toString().equals(this.d.getText().toString())) {
            a(hu.a(getString(R.string.change_password_dialog_error_password_not_match_title), getString(R.string.change_password_dialog_error_password_not_match_message), getString(17039370)));
        } else if (this.c.getText().toString().equals(this.b.getText().toString())) {
            a(hu.a(getString(R.string.change_password_dialog_error_password_old_and_new_identical_title), getString(R.string.change_password_dialog_error_password_old_and_new_identical_message), getString(17039370)));
        } else {
            this.f = this.c.getText().toString();
            l();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.change_password);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.b = (FormEditText) findViewById(R.id.et_old_password);
        this.b.a(new ij(getString(R.string.subscribe_error_msg_for_password_less_than_six_numbers), Pattern.compile("[0-9]{6}$"), false));
        this.b.a(new ij(getString(R.string.subscribe_error_msg_for_password_identical_numbers_are_not_allowed), Pattern.compile("0{6}|1{6}|2{6}|3{6}|4{6}|5{6}|6{6}|7{6}|8{6}|9{6}"), true));
        this.b.a(new ij(getString(R.string.subscribe_error_msg_for_password_series_are_not_allowed), Pattern.compile("012345|123456|234567|345678|456789|567890|678901|789012|890123|901234|987654|876543|765432|654321|543210|432109|321098|210987|109876|098765"), true));
        this.b.addTextChangedListener(new a(this, this.b));
        this.c = (FormEditText) findViewById(R.id.et_new_password);
        this.c.a(new ij(getString(R.string.subscribe_error_msg_for_password_less_than_six_numbers), Pattern.compile("[0-9]{6}$"), false));
        this.c.a(new ij(getString(R.string.subscribe_error_msg_for_password_identical_numbers_are_not_allowed), Pattern.compile("0{6}|1{6}|2{6}|3{6}|4{6}|5{6}|6{6}|7{6}|8{6}|9{6}"), true));
        this.c.a(new ij(getString(R.string.subscribe_error_msg_for_password_series_are_not_allowed), Pattern.compile("012345|123456|234567|345678|456789|567890|678901|789012|890123|901234|987654|876543|765432|654321|543210|432109|321098|210987|109876|098765"), true));
        this.g = new ii(this.c, getString(R.string.change_password_error_msg_for_new_password_equal_to_old_password), this.b.getText().toString(), true);
        this.c.a(this.g);
        this.c.addTextChangedListener(new a(this, this.c));
        this.d = (FormEditText) findViewById(R.id.et_new_password_confirm);
        this.i = new ii(this.d, getString(R.string.subscribe_error_msg_for_password_not_equal_to_password), this.c.getText().toString(), false);
        this.d.a(this.i);
        this.h = new ii(this.d, getString(R.string.change_password_error_msg_for_new_password_equal_to_old_password), this.b.getText().toString(), true);
        this.d.a(this.h);
        this.d.a(new ij(getString(R.string.subscribe_error_msg_for_password_less_than_six_numbers), Pattern.compile("[0-9]{6}$"), false));
        this.d.a(new ij(getString(R.string.subscribe_error_msg_for_password_identical_numbers_are_not_allowed), Pattern.compile("0{6}|1{6}|2{6}|3{6}|4{6}|5{6}|6{6}|7{6}|8{6}|9{6}"), true));
        this.d.a(new ij(getString(R.string.subscribe_error_msg_for_password_series_are_not_allowed), Pattern.compile("012345|123456|234567|345678|456789|567890|678901|789012|890123|901234|987654|876543|765432|654321|543210|432109|321098|210987|109876|098765"), true));
        this.d.addTextChangedListener(new a(this, this.d));
        this.e = (Button) findViewById(R.id.b_confirm);
        this.e.setOnClickListener(this);
        if (bundle != null) {
            this.f = bundle.getString("savedStateCurrentPassword");
        }
        k();
        jb.a(getApplicationContext(), R.string.screen_name_modify_secret_code_activity);
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
        bundle.putString("savedStateCurrentPassword", this.f);
    }
}
