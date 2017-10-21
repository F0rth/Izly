package defpackage;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.gcm.GCMNotificationInsideApplicationReceiver;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

public class x extends y {
    private static final String o = x.class.getSimpleName();
    private GCMNotificationInsideApplicationReceiver p;

    public final void a() {
        super.a();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_DISPATCHED");
        intentFilter.setPriority(100);
        this.p = new GCMNotificationInsideApplicationReceiver();
        this.p.a = new x$1(this);
        this.d.registerReceiver(this.p, intentFilter);
        f();
        SmoneyApplication smoneyApplication = (SmoneyApplication) this.d.getApplication();
        smoneyApplication.f = true;
        if (!smoneyApplication.g) {
            smoneyApplication.g = true;
            if (SmoneyApplication.a.b != null) {
                SmoneyApplication.b.c(SmoneyApplication.a.b.a, SmoneyApplication.a.b.c);
            }
        }
    }

    public final void a(Bundle bundle, AppCompatActivity appCompatActivity, if$a if_a, SmoneyRequestManager$a smoneyRequestManager$a) {
        super.b(bundle, appCompatActivity, if_a, smoneyRequestManager$a);
    }

    public final boolean a(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return false;
        }
        k();
        return true;
    }

    public final void b() {
        this.d.unregisterReceiver(this.p);
        this.p = null;
        ((SmoneyApplication) this.d.getApplication()).f = false;
        super.b();
    }

    public final void c() {
        SmoneyApplication smoneyApplication = (SmoneyApplication) this.d.getApplication();
        if (!smoneyApplication.f) {
            smoneyApplication.g = false;
        }
        super.c();
    }

    public final void d() {
        this.k = this.d.findViewById(R.id.cash_layout);
        this.f = this.d.findViewById(R.id.rl_account_balance_container);
        this.g = (TextView) this.d.findViewById(R.id.tv_account_balance_value);
        this.j = (TextView) this.d.findViewById(R.id.cash_value);
        this.h = (TextView) this.d.findViewById(R.id.tv_account_balance_currency);
        this.i = (TextView) this.d.findViewById(R.id.tv_account_balance_date);
    }
}
