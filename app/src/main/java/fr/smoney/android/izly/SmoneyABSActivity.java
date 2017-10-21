package fr.smoney.android.izly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.ad4screen.sdk.A4S;

import defpackage.ie;
import defpackage.if;
import defpackage.if$a;
import defpackage.x;
import defpackage.y;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

public class SmoneyABSActivity extends AppCompatActivity implements if$a {
    protected ge a;
    private x b = new x();

    protected final void a() {
        this.b.k();
    }

    protected final void a(int i, int i2, boolean z) {
        this.b.a(i, i2, z);
    }

    protected final void a(int i, Fragment fragment) {
        this.b.a(i, fragment);
    }

    public final void a(Intent intent, int i, boolean z) {
        intent.putExtra("INTENT_EXTRA_IS_MODAL", true);
        super.startActivityForResult(intent, i);
        this.b.a(intent, false);
    }

    public final void a(Intent intent, boolean z) {
        if (intent != null) {
            intent.putExtra("INTENT_EXTRA_IS_MODAL", true);
        }
        super.startActivity(intent);
        this.b.a(intent, true);
    }

    protected final void a(ie ieVar) {
        y.a(ieVar);
    }

    public void a(ie ieVar, Bundle bundle) {
        this.b.a(ieVar, bundle);
    }

    protected final void a(if ifVar) {
        this.b.a(ifVar);
    }

    protected final void a(boolean z) {
        this.b.a(z);
    }

    protected final boolean a(int i, int i2, int i3, Bundle bundle) {
        return this.b.a(i, i2, i3, bundle);
    }

    public boolean a(ServerError serverError) {
        return a(serverError, true);
    }

    protected final boolean a(ServerError serverError, boolean z) {
        return this.b.a(serverError);
    }

    protected final void a_(int i) {
        this.b.a(i);
    }

    protected final aa b(int i) {
        return this.b.b((int) R.id.content_fragment);
    }

    protected final void b() {
        this.b.f();
    }

    public void b(ie ieVar) {
        this.b.b(ieVar);
    }

    protected final void c() {
        this.b.g();
    }

    public void c(ie ieVar) {
        this.b.c(ieVar);
    }

    protected final boolean c(int i) {
        return this.b.c(105);
    }

    protected final void d() {
        this.b.h();
    }

    public void d(ie ieVar) {
        this.b.d(ieVar);
    }

    protected final void e() {
        this.b.i();
    }

    protected final void f() {
        this.b.l();
    }

    public void finish() {
        super.finish();
        this.b.j();
    }

    protected final void g() {
        this.b.m();
    }

    protected final int h() {
        return this.b.n();
    }

    protected final cl i() {
        return this.b.a;
    }

    protected final SmoneyRequestManager j() {
        return this.b.b;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = new ge(this);
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
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.b.a();
        A4S.get(this).startActivity(this);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.b.a(bundle);
    }

    public void onStop() {
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
        if (intent != null) {
            intent.putExtra("INTENT_EXTRA_IS_MODAL", false);
        }
        super.startActivity(intent);
        this.b.a(intent);
    }

    public void startActivityForResult(Intent intent, int i) {
        super.startActivityForResult(intent, i);
        this.b.a(intent);
    }
}
