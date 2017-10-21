package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import fr.smoney.android.izly.data.model.RequestData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.network.ConnectivityBroadcastReceiver;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager.a;
import java.util.ArrayList;

public class aa extends Fragment implements a, ig {
    protected z a = new z();
    protected boolean b;
    protected ge c;
    protected AppCompatActivity d;
    private ConnectivityBroadcastReceiver e;
    private IntentFilter f;

    protected final void a(int i, int i2, boolean z) {
        this.a.a(i, i2, z);
    }

    protected final void a(int i, Fragment fragment, boolean z) {
        this.a.b(2131755282, fragment);
    }

    public final void a(Intent intent, int i, boolean z) {
        if (intent != null) {
            intent.putExtra("INTENT_EXTRA_IS_MODAL", true);
        }
        super.startActivityForResult(intent, 10);
        this.a.a(intent, true);
    }

    public final void a(Intent intent, boolean z) {
        if (intent != null) {
            intent.putExtra("INTENT_EXTRA_IS_MODAL", true);
        }
        super.startActivity(intent);
        this.a.a(intent, true);
    }

    public void a(Parcelable parcelable, ServerError serverError) {
    }

    protected final void a(View view) {
        this.a.a(view);
    }

    public void a(ie ieVar, Bundle bundle) {
        this.a.a(ieVar, bundle);
    }

    protected final void a(if ifVar) {
        this.a.a(ifVar);
    }

    public boolean a() {
        return z.q();
    }

    protected final boolean a(int i) {
        return this.a.c(i);
    }

    protected final boolean a(int i, int i2, int i3, Bundle bundle) {
        return this.a.a(i, i2, i3, bundle);
    }

    protected final boolean a(ServerError serverError) {
        return a(serverError, true);
    }

    protected final boolean a(ServerError serverError, boolean z) {
        return this.a.a(serverError);
    }

    protected final void a_(boolean z) {
        this.a.a(false);
    }

    public void b() {
        this.a.r();
    }

    public void b(ie ieVar) {
        this.a.b(ieVar);
    }

    protected final void b(boolean z) {
        this.a.o = true;
    }

    protected final void c() {
        this.a.f();
    }

    public void c(ie ieVar) {
        this.a.c(ieVar);
    }

    protected final void d() {
        this.a.i();
    }

    public void d(ie ieVar) {
        this.a.d(ieVar);
    }

    protected final void e() {
        this.a.l();
    }

    protected final boolean f() {
        z zVar = this.a;
        SmoneyRequestManager smoneyRequestManager = zVar.b;
        ArrayList arrayList = zVar.c;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (smoneyRequestManager.b.indexOfKey(((RequestData) arrayList.get(i)).a) >= 0) {
                return true;
            }
        }
        return false;
    }

    protected final void g() {
        this.a.m();
    }

    protected final int h() {
        return this.a.n();
    }

    protected final cl i() {
        return this.a.a;
    }

    protected final SmoneyRequestManager j() {
        return this.a.b;
    }

    public String k() {
        return null;
    }

    public void l() {
        this.b = true;
    }

    public void m() {
        this.b = false;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        z.o();
        b();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.d = (AppCompatActivity) activity;
        z.d();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a.b(bundle, this.d, this, this instanceof a ? (a) this : null);
        this.c = new ge(this.d);
    }

    public void onDestroy() {
        this.a.e();
        super.onDestroy();
    }

    public void onDetach() {
        z.p();
        this.d = null;
        super.onDetach();
    }

    public void onPause() {
        this.a.b();
        this.d.unregisterReceiver(this.e);
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.a.a();
        if (this.a.o) {
            this.a.a(getActivity(), k());
        }
        this.a.f();
        this.f = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.e = new ConnectivityBroadcastReceiver(this);
        this.d.registerReceiver(this.e, this.f);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.a.a(bundle);
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
