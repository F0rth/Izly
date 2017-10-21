package defpackage;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetBankAccountData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.AddTransferAccountActivity;

public final class hr extends aa implements OnClickListener, SmoneyRequestManager$a {
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private Button i;
    private View j;
    private GetBankAccountData k;
    private hr$a l;

    public static hr a(GetBankAccountData getBankAccountData) {
        hr hrVar = new hr();
        Bundle bundle = new Bundle();
        bundle.putParcelable("fr.smoney.android.izly.extras.argumentBankAccount", null);
        hrVar.setArguments(bundle);
        return hrVar;
    }

    private void a(GetBankAccountData getBankAccountData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getBankAccountData == null) {
            a(hw.a(this.d, this));
        } else {
            this.k = getBankAccountData;
            n();
        }
    }

    private void n() {
        this.g.setText(this.k.d);
        this.h.setText(this.k.f);
        this.f.setText(this.k.b);
        if (!this.k.h) {
            this.i.setEnabled(false);
        }
        CharSequence charSequence = this.k.i;
        if (charSequence != null && charSequence.length() > 0) {
            this.e.setText(charSequence);
        }
    }

    private void o() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 225 && intent.getStringExtra("fr.smoney.android.izly.extras.GetBankAccountUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.GetBankAccountSessionId").equals(str2)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 225);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetBankAccountUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.GetBankAccountSessionId", str2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bt = null;
        j.f.bu = null;
        super.a(keyAt, 225, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (hr$1.a[ieVar.ordinal()]) {
            case 1:
                if (h() == 225) {
                    o();
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
            if (i2 == 225) {
                a((GetBankAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.getBankAccountData"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (hr$1.a[ieVar.ordinal()]) {
            case 1:
                if (h() != 225) {
                    super.b(ieVar);
                    return;
                } else if (this.d instanceof ih) {
                    ((ih) this.d).e();
                    return;
                } else {
                    return;
                }
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 225) {
            a(i2.bt, i2.bu);
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 225) {
            g();
            if (this.d instanceof ih) {
                ((ih) this.d).e();
                return;
            }
            return;
        }
        super.d(ieVar);
    }

    protected final String k() {
        return getString(R.string.transfer_account_detail_title);
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.e = (TextView) this.j.findViewById(R.id.t_add_informations);
        this.f = (TextView) this.j.findViewById(R.id.tv_alias);
        this.g = (TextView) this.j.findViewById(R.id.tv_iban);
        this.h = (TextView) this.j.findViewById(R.id.tv_bic);
        this.i = (Button) this.j.findViewById(R.id.b_modify_iban);
        this.i.setOnClickListener(this);
        b(true);
        if (this.k != null) {
            n();
        } else {
            o();
        }
    }

    public final void onClick(View view) {
        if (view == this.i) {
            Intent intent = new Intent(this.d, AddTransferAccountActivity.class);
            intent.putExtra("fr.smoney.android.izly.intentExtrasMode", 1);
            startActivity(intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.k = (GetBankAccountData) getArguments().getParcelable("fr.smoney.android.izly.extras.argumentBankAccount");
    }

    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.j = layoutInflater.inflate(R.layout.transfer_account_detail, viewGroup, false);
        jb.a(getActivity(), R.string.screen_name_bank_account_activity);
        return this.j;
    }

    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                e();
                break;
        }
        return true;
    }

    public final void onPause() {
        this.d.unregisterReceiver(this.l);
        this.l = null;
        super.onPause();
    }

    public final void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.l == null) {
            this.l = new hr$a();
        }
        this.d.registerReceiver(this.l, intentFilter);
        this.d.getSupportActionBar().setTitle(R.string.transfer_account_detail_title);
    }
}
