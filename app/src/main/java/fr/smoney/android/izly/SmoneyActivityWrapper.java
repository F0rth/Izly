package fr.smoney.android.izly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import defpackage.ie;
import defpackage.if;
import defpackage.if$a;
import defpackage.y;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

public class SmoneyActivityWrapper extends AppCompatActivity implements if$a {
    private y a = new y();

    protected final int a() {
        return this.a.n();
    }

    protected final void a(int i, int i2, boolean z) {
        this.a.a(i, 221, true);
    }

    public final void a(Intent intent, int i, boolean z) {
        if (intent != null) {
            intent.putExtra("INTENT_EXTRA_IS_MODAL", true);
        }
        super.startActivityForResult(intent, 1);
        this.a.a(intent, false);
    }

    public void a(ie ieVar, Bundle bundle) {
        this.a.a(ieVar, bundle);
    }

    protected final void a(if ifVar) {
        this.a.a(ifVar);
    }

    protected final boolean a(int i, int i2, int i3, Bundle bundle) {
        return this.a.a(i, i2, i3, bundle);
    }

    protected final boolean a(ServerError serverError) {
        return this.a.a(serverError);
    }

    protected final cl b() {
        return this.a.a;
    }

    public void b(ie ieVar) {
        this.a.b(ieVar);
    }

    protected final SmoneyRequestManager c() {
        return this.a.b;
    }

    public void c(ie ieVar) {
        this.a.c(ieVar);
    }

    public final void d(ie ieVar) {
        this.a.d(ieVar);
    }

    public void finish() {
        super.finish();
        this.a.j();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a.b(bundle, this, this, this instanceof SmoneyRequestManager$a ? (SmoneyRequestManager$a) this : null);
    }

    protected void onDestroy() {
        this.a.e();
        super.onDestroy();
    }

    protected void onPause() {
        this.a.b();
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        this.a.a();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.a.a(bundle);
    }

    protected void onStop() {
        this.a.c();
        super.onStop();
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.a.a(intent);
    }

    public void startActivityForResult(Intent intent, int i) {
        super.startActivityForResult(intent, i);
        this.a.a(intent);
    }
}
