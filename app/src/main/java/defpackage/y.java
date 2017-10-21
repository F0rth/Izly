package defpackage;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.CounterFamily;
import fr.smoney.android.izly.data.model.CounterListData;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.LoginLightData;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.data.model.RequestData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.HomeActivity;
import fr.smoney.android.izly.ui.LoginActivity;
import fr.smoney.android.izly.ui.P2PPayActivity;
import fr.smoney.android.izly.ui.PhoneValidationActivity;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import org.spongycastle.crypto.tls.CipherSuite;

public class y {
    static FragmentManager n;
    private static final String o = y.class.getSimpleName();
    public cl a;
    public SmoneyRequestManager b;
    ArrayList<RequestData> c;
    protected AppCompatActivity d;
    public boolean e;
    protected View f;
    protected TextView g;
    protected TextView h;
    protected TextView i;
    protected TextView j;
    protected View k;
    protected RelativeLayout l;
    protected LinearLayout m;
    private boolean p = false;
    private RequestData q = null;
    private if$a r;
    private SmoneyRequestManager$a s;
    private ia t;
    private boolean u = true;
    private String v;
    private SimpleDateFormat w;

    private void a(LoginLightData loginLightData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (loginLightData == null) {
            a(hw.a(this.d, this.r, ie.ConnexionErrorDuringReloginType));
        } else {
            this.v = null;
            Intent intent = new Intent("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
            intent.putExtra("fr.smoney.android.izly.sessionState", 1);
            this.d.sendBroadcast(intent);
        }
    }

    public static void a(ie ieVar) {
        if ifVar = (if) n.findFragmentByTag("dialog");
        if (ifVar != null && ifVar.e == ieVar && ifVar.isAdded()) {
            ifVar.dismiss();
        }
    }

    private void a(String str) {
        int keyAt;
        SmoneyRequestManager smoneyRequestManager = this.b;
        String f = this.a.b != null ? this.a.b.a : SmoneyApplication.c.f();
        int size = smoneyRequestManager.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) smoneyRequestManager.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 232 && intent.getStringExtra("fr.smoney.android.izly.extras.loginLightUserId").equals(f) && intent.getStringExtra("fr.smoney.android.izly.extras.loginLightPassword").equals(str)) {
                keyAt = smoneyRequestManager.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(smoneyRequestManager.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 232);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", smoneyRequestManager.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.loginLightUserId", f);
        intent2.putExtra("fr.smoney.android.izly.extras.loginLightPassword", str);
        smoneyRequestManager.c.startService(intent2);
        smoneyRequestManager.b.append(keyAt, intent2);
        smoneyRequestManager.f.bJ = null;
        smoneyRequestManager.f.bK = null;
        a(keyAt, 232, true);
    }

    private static boolean a(ArrayList<RequestData> arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (((RequestData) arrayList.get(i)).c) {
                return true;
            }
        }
        return false;
    }

    private void b(ServerError serverError) {
        a(hv.a(serverError.d, serverError.c, this.d.getString(17039370), this.r, ie.ErrorType));
    }

    private void b(boolean z) {
        a(hv.a(this.d.getString(R.string.dialog_error_data_error_title), this.d.getString(R.string.dialog_error_data_error_message), this.d.getString(17039370), this.r, z ? ie.ErrorForNotMustBeRetryRequest : ie.ErrorType));
    }

    private void c(ServerError serverError) {
        Intent intent = new Intent(this.d, LoginActivity.class);
        intent.addFlags(335544320);
        intent.putExtra("fr.smoney.android.izly.extras.userId", this.a.a);
        intent.putExtra("fr.smoney.android.izly.extras.launchActivity", 4);
        intent.putExtra("fr.smoney.android.izly.intentExtra.sessionExpiredServerError", serverError);
        if (this.d instanceof P2PPayActivity) {
            P2PPayCommerceInfos p2PPayCommerceInfos = (P2PPayCommerceInfos) this.d.getIntent().getParcelableExtra("startFromWebPayementObject");
            if (p2PPayCommerceInfos != null) {
                intent.setData(Uri.parse("mv://SBSCA?action=pay&id=" + p2PPayCommerceInfos.a + "&amount=" + p2PPayCommerceInfos.b));
            }
        }
        this.d.startActivity(intent);
        if (this.d instanceof LoginActivity) {
            this.d.overridePendingTransition(0, 0);
        } else {
            this.d.overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
        }
        this.d.finish();
    }

    private ArrayList<RequestData> d() {
        SmoneyRequestManager smoneyRequestManager = this.b;
        ArrayList arrayList = this.c;
        ArrayList<RequestData> arrayList2 = new ArrayList();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (smoneyRequestManager.b.indexOfKey(((RequestData) arrayList.get(i)).a) >= 0) {
                arrayList2.add(arrayList.get(i));
            }
        }
        return arrayList2;
    }

    private static boolean d(int i) {
        return i == 14 || i == 76 || i == 33 || i == 53 || i == 67 || i == 12 || i == 74;
    }

    public void a() {
        n = this.d.getSupportFragmentManager();
        synchronized (this.c) {
            if (this.c.size() > 0) {
                ArrayList d = d();
                if (d.size() > 0) {
                    if (y.a(d)) {
                        g();
                    }
                    if (this.s != null) {
                        this.b.a(this.s);
                    }
                }
                synchronized (this.c) {
                    for (int i = 0; i < this.c.size(); i++) {
                        RequestData requestData = (RequestData) this.c.get(i);
                        if (!d.contains(requestData)) {
                            if (requestData.b == 232) {
                                a(this.a.bJ, this.a.bK);
                            } else if (this.s != null) {
                                this.s.b_(requestData.b);
                            }
                            this.c.remove(requestData);
                        }
                    }
                }
            }
        }
    }

    public final void a(int i) {
        b(y.d(i));
    }

    public final void a(int i, int i2, boolean z) {
        if (z) {
            g();
        }
        this.q = new RequestData(i, i2, z);
        synchronized (this.c) {
            if (!this.c.contains(this.q)) {
                this.c.add(this.q);
            }
        }
        if (this.s != null) {
            this.b.a(this.s);
        }
    }

    public final void a(int i, Fragment fragment) {
        FragmentManager supportFragmentManager = this.d.getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        Fragment findFragmentById = supportFragmentManager.findFragmentById(i);
        if (findFragmentById != null) {
            beginTransaction.remove(findFragmentById);
        }
        beginTransaction.setTransition(0);
        beginTransaction.replace(i, fragment, "fragment");
        beginTransaction.commit();
    }

    public final void a(Intent intent) {
        a(intent, false);
    }

    public final void a(Intent intent, boolean z) {
        if (intent != null) {
            ComponentName component = intent.getComponent();
            ComponentName componentName = new ComponentName(this.d, HomeActivity.class);
            if (component != null && component.equals(componentName) && (intent.getFlags() & 67108864) != 0) {
                this.d.overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
            } else if (z) {
                this.d.overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
            } else {
                this.d.overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
            }
        }
    }

    public final void a(Bundle bundle) {
        bundle.putParcelableArrayList("savedStateRequestData", this.c);
        bundle.putString("savedStateCurrentPassword", this.v);
        cl clVar = this.a;
        if (clVar.b != null) {
            bundle.putParcelable("loginDataKEy", clVar.b);
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (y$1.a[ieVar.ordinal()]) {
            case 1:
                if (this.a.b != null) {
                    SmoneyRequestManager smoneyRequestManager = this.b;
                    String str = this.a.b.a;
                    String str2 = this.a.b.c;
                    Intent intent = new Intent(smoneyRequestManager.c, SmoneyService.class);
                    intent.putExtra("com.foxykeep.datadroid.extras.workerType", 2);
                    intent.putExtra("com.foxykeep.datadroid.extras.receiver", smoneyRequestManager.e);
                    intent.putExtra("fr.smoney.android.izly.extras.logoutUserId", str);
                    intent.putExtra("fr.smoney.android.izly.extras.logoutSessionId", str2);
                    smoneyRequestManager.c.startService(intent);
                    smoneyRequestManager.f.b = null;
                    smoneyRequestManager.f.d = null;
                }
                SmoneyApplication.c.b();
                SmoneyApplication.c.d();
                ((SmoneyApplication) this.d.getApplication()).a();
                Intent intent2 = new Intent(this.d, LoginActivity.class);
                intent2.addFlags(335544320);
                intent2.putExtra("fr.smoney.android.izly.extras.launchActivity", 3);
                this.d.startActivity(intent2);
                this.d.overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
                this.d.finish();
                return;
            case 2:
                this.v = bundle.getString("Data.Password");
                a(this.v);
                return;
            case 3:
                if (n() == 232) {
                    a(this.v);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public final void a(if ifVar) {
        FragmentManager supportFragmentManager = this.d.getSupportFragmentManager();
        n = supportFragmentManager;
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        if ifVar2 = (if) n.findFragmentByTag("dialog");
        if (ifVar2 != null) {
            beginTransaction.remove(ifVar2);
        }
        try {
            ifVar.show(beginTransaction, "dialog");
        } catch (IllegalStateException e) {
        }
    }

    public final void a(boolean z) {
        if (z) {
            ((SmoneyApplication) this.d.getApplication()).a();
        }
        Intent intent = new Intent(this.d, LoginActivity.class);
        intent.addFlags(268468224);
        intent.putExtra("fr.smoney.android.izly.extras.launchActivity", 2);
        this.d.startActivity(intent);
        this.d.overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
    }

    public final boolean a(int i, int i2, int i3, Bundle bundle) {
        RequestData requestData;
        boolean z;
        int size = this.c.size();
        for (int i4 = 0; i4 < size; i4++) {
            requestData = (RequestData) this.c.get(i4);
            if (requestData.a == i) {
                break;
            }
        }
        requestData = null;
        if (requestData != null) {
            this.q = requestData;
            synchronized (this.c) {
                this.c.remove(requestData);
            }
            ArrayList d = d();
            if (d.size() == 0) {
                if (this.s != null) {
                    this.b.b(this.s);
                }
                h();
            } else if (!y.a(d)) {
                h();
            }
            if (i3 == -1) {
                if (y.d(i2)) {
                    b(true);
                    z = false;
                } else if (bundle != null) {
                    if (bundle.getInt("com.foxykeep.datadroid.extras.error", -1) == 2) {
                        b(false);
                        z = false;
                    } else if (this.d instanceof HomeActivity) {
                        z = true;
                    } else {
                        a(hw.a(this.d, this.r));
                        z = false;
                    }
                } else if (this.d instanceof HomeActivity) {
                    z = true;
                } else {
                    a(hw.a(this.d, this.r));
                    z = false;
                }
            } else if (i2 != 232) {
                return true;
            } else {
                a((LoginLightData) bundle.getParcelable("fr.smoney.android.izly.extras.LoginLight"), (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError"));
                return false;
            }
        }
        z = false;
        return z;
    }

    public final boolean a(ServerError serverError) {
        switch (serverError.b) {
            case 570:
            case 571:
                SmoneyApplication.c.b();
                SmoneyApplication.c.d();
                ((SmoneyApplication) this.d.getApplication()).a();
                c(serverError);
                return true;
            default:
                switch (serverError.e) {
                    case 0:
                        SmoneyApplication.c.b();
                        SmoneyApplication.c.g();
                        SmoneyApplication.c.d();
                        ((SmoneyApplication) this.d.getApplication()).a();
                        c(serverError);
                        return true;
                    case 1:
                        SmoneyApplication.c.b();
                        SmoneyApplication.c.d();
                        ((SmoneyApplication) this.d.getApplication()).a();
                        c(serverError);
                        return true;
                    case 2:
                        switch (serverError.b) {
                            case 105:
                                a(hv.a(serverError.d, serverError.c, this.d.getString(17039370), this.r, ie.ErrorPasswordType));
                                break;
                            case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA /*140*/:
                            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /*141*/:
                                this.a.a();
                                i();
                                break;
                            case 512:
                                a(hv.a(serverError.d, serverError.c, this.d.getString(17039370), this.r, ie.ErrorAuthentType));
                                break;
                            default:
                                b(serverError);
                                break;
                        }
                        return true;
                    case 3:
                        b(serverError);
                        return true;
                    default:
                        return false;
                }
        }
    }

    public final aa b(int i) {
        Fragment findFragmentById = this.d.getSupportFragmentManager().findFragmentById(i);
        return findFragmentById != null ? (aa) findFragmentById : null;
    }

    public void b() {
        synchronized (this.c) {
            if (this.c.size() > 0) {
                if (this.s != null) {
                    this.b.b(this.s);
                }
                h();
            }
        }
    }

    protected final void b(int i, Fragment fragment) {
        FragmentTransaction beginTransaction = this.d.getSupportFragmentManager().beginTransaction();
        beginTransaction.setTransition(0);
        beginTransaction.replace(i, fragment, "fragment");
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }

    public final void b(Bundle bundle, AppCompatActivity appCompatActivity, if$a if_a, SmoneyRequestManager$a smoneyRequestManager$a) {
        this.d = appCompatActivity;
        this.r = if_a;
        this.s = smoneyRequestManager$a;
        this.a = SmoneyApplication.a;
        this.b = SmoneyApplication.b;
        this.w = new SimpleDateFormat(appCompatActivity.getResources().getString(R.string.date_and_time_format), Locale.getDefault());
        if (bundle != null) {
            this.c = bundle.getParcelableArrayList("savedStateRequestData");
            this.v = bundle.getString("savedStateCurrentPassword");
            this.a.b = (LoginData) bundle.getParcelable("loginDataKEy");
        } else {
            this.c = new ArrayList();
        }
        Intent intent = this.d.getIntent();
        if (intent != null && intent.hasExtra("INTENT_EXTRA_IS_MODAL")) {
            this.p = intent.getBooleanExtra("INTENT_EXTRA_IS_MODAL", false);
        }
    }

    public final void b(ie ieVar) {
        switch (y$1.a[ieVar.ordinal()]) {
            case 3:
                if (n() != 232) {
                    return;
                }
                if (this.d instanceof ih) {
                    ((ih) this.d).e();
                    return;
                } else {
                    k();
                    return;
                }
            default:
                return;
        }
    }

    public void c() {
    }

    public final void c(ie ieVar) {
        switch (y$1.a[ieVar.ordinal()]) {
            case 4:
            case 5:
                if (!(this.d instanceof LoginActivity) && !(this.d instanceof PhoneValidationActivity)) {
                    a(hz.a(this.d, this.r));
                    return;
                }
                return;
            case 7:
                this.a.cs = true;
                if (this.d instanceof ih) {
                    ((ih) this.d).e();
                    return;
                } else {
                    k();
                    return;
                }
            default:
                return;
        }
    }

    public final boolean c(int i) {
        int size = this.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            RequestData requestData = (RequestData) this.c.get(i2);
            if (requestData.b == i) {
                if ((this.b.b.indexOfKey(requestData.a) >= 0 ? 1 : null) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void d(ie ieVar) {
        switch (y$1.a[ieVar.ordinal()]) {
            case 2:
                if (this.d instanceof ih) {
                    ((ih) this.d).e();
                    return;
                } else {
                    k();
                    return;
                }
            case 8:
            case 9:
                m();
                return;
            default:
                return;
        }
    }

    public final void e() {
        this.a = null;
        this.b = null;
        this.q = null;
        this.c = null;
        this.d = null;
        this.s = null;
        this.r = null;
        this.d = null;
    }

    public final void f() {
        if (this.g != null && this.h != null && this.i != null && this.f != null && this.k != null) {
            LoginData loginData = SmoneyApplication.a.b;
            if (loginData != null && loginData.B != null) {
                if (this.a.b == null || !this.a.b.F) {
                    this.g.setText(String.format("%1$.2f", new Object[]{Double.valueOf(loginData.B.a)}));
                    this.h.setText(Currency.getInstance(loginData.j).getSymbol());
                    this.i.setText(this.w.format(new Date(loginData.B.c)));
                } else {
                    this.f.setVisibility(8);
                }
                if (loginData.B.b <= 0.0d || this.j == null) {
                    this.k.setVisibility(8);
                } else {
                    this.j.setText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(loginData.B.b), Currency.getInstance(loginData.j).getSymbol()}));
                    this.k.setVisibility(0);
                }
                CounterListData counterListData = SmoneyApplication.a.bB;
                if (this.l != null) {
                    if (this.e) {
                        this.l.setVisibility(0);
                    }
                    if (counterListData != null) {
                        this.m.removeAllViews();
                        Iterator it = counterListData.a.iterator();
                        while (it.hasNext()) {
                            CounterFamily counterFamily = (CounterFamily) it.next();
                            View inflate = LayoutInflater.from(this.d).inflate(R.layout.listitem_counter_balance, this.m, false);
                            TextView textView = (TextView) inflate.findViewById(R.id.tv_counter_balance_value);
                            ((TextView) inflate.findViewById(R.id.tv_counter_balance_name)).setText(counterFamily.b);
                            if (counterFamily.a == 3) {
                                textView.setText(String.format("%1$.2f%2$s", new Object[]{new BigDecimal(counterFamily.c).divide(new BigDecimal(100)), Currency.getInstance(loginData.j).getSymbol()}));
                            } else {
                                textView.setText(counterFamily.c);
                            }
                            this.m.addView(inflate);
                        }
                    }
                }
            }
        }
    }

    public final void g() {
        if (this.u) {
            if (this.t == null) {
                this.t = ia.a(this.d.getString(R.string.progress_dialog_title), this.d.getString(R.string.progress_dialog_message), this.r);
            } else {
                this.t.f = this.r;
            }
            if (!this.t.isAdded()) {
                a(this.t);
            }
            this.u = false;
        }
    }

    public final void h() {
        y.a(ie.ProgressType);
        this.u = true;
    }

    public final void i() {
        a(hz.a(this.d, this.r));
    }

    public final void j() {
        boolean z = this.p;
        if (this.d.getClass() == HomeActivity.class) {
            return;
        }
        if (z) {
            this.d.overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
        } else {
            this.d.overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
        }
    }

    public final void k() {
        Intent intent = new Intent(this.d, HomeActivity.class);
        intent.addFlags(67108864);
        this.d.startActivity(intent);
        this.d.overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
    }

    public final void l() {
        this.d.getSupportFragmentManager().popBackStack();
    }

    public final void m() {
        if (this.s != null) {
            this.b.b(this.s);
        }
        this.q = null;
        this.c = new ArrayList();
    }

    public final int n() {
        return this.q == null ? -1 : this.q.b;
    }
}
