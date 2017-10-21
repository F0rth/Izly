package fr.smoney.android.izly;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.ad4screen.sdk.A4S;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import defpackage.ie;
import defpackage.if;
import defpackage.if$a;
import defpackage.ig;
import defpackage.x;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.network.ConnectivityBroadcastReceiver;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

public class SmoneySlidingActivity extends SlidingFragmentActivity implements if$a, ig {
    protected boolean a;
    private x b = new x();
    private ConnectivityBroadcastReceiver c;
    private IntentFilter d;

    protected final aa a(int i) {
        return this.b.b((int) R.id.content_fragment);
    }

    protected final void a() {
        this.b.m();
    }

    protected final void a(int i, int i2, boolean z) {
        this.b.a(i, 228, true);
    }

    protected final void a(int i, Fragment fragment) {
        this.b.a((int) R.id.content_fragment, fragment);
    }

    public final void a(Intent intent, boolean z) {
        if (intent != null) {
            intent.putExtra("INTENT_EXTRA_IS_MODAL", true);
        }
        super.startActivity(intent);
        this.b.a(intent, true);
    }

    public void a(ie ieVar, Bundle bundle) {
        this.b.a(ieVar, bundle);
    }

    protected final void a(if ifVar) {
        this.b.a(ifVar);
    }

    protected final void a(boolean z) {
        this.b.a(false);
    }

    protected final boolean a(int i, int i2, int i3, Bundle bundle) {
        return this.b.a(i, i2, i3, bundle);
    }

    protected final boolean a(ServerError serverError) {
        return this.b.a(serverError);
    }

    protected final int b() {
        return this.b.n();
    }

    public void b(ie ieVar) {
        this.b.b(ieVar);
    }

    protected final cl c() {
        return this.b.a;
    }

    public void c(ie ieVar) {
        this.b.c(ieVar);
    }

    protected final SmoneyRequestManager d() {
        return this.b.b;
    }

    public void d(ie ieVar) {
        this.b.d(ieVar);
    }

    public void finish() {
        super.finish();
        this.b.j();
    }

    public void l() {
        this.a = true;
    }

    public void m() {
        this.a = false;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b.a(bundle, this, this, this instanceof SmoneyRequestManager$a ? (SmoneyRequestManager$a) this : null);
    }

    protected void onDestroy() {
        this.b.e();
        super.onDestroy();
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        A4S.get(this).setIntent(intent);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return this.b.a(menuItem);
    }

    public void onPause() {
        this.b.b();
        A4S.get(this).stopActivity(this);
        unregisterReceiver(this.c);
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.b.a();
        this.d = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.c = new ConnectivityBroadcastReceiver(this);
        A4S.get(this).startActivity(this);
        registerReceiver(this.c, this.d);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.b.a(bundle);
    }

    protected void onStop() {
        this.b.c();
        super.onStop();
    }

    public void setContentView(int i) {
        super.setContentView(i);
        this.b.d();
    }

    public void setContentView(View view) {
        super.setContentView(view);
        this.b.d();
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        super.setContentView(view, layoutParams);
        this.b.d();
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.b.a(intent);
    }

    public void startActivityForResult(Intent intent, int i) {
        super.startActivityForResult(intent, i);
        this.b.a(intent);
    }
}
