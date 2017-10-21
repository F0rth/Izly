package defpackage;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.P2PGet;
import fr.smoney.android.izly.data.model.P2PGetListData;
import fr.smoney.android.izly.data.model.P2PGetMult;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.P2PGetDetailsActivity;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;

public class hl extends aa implements OnItemClickListener, OnRefreshListener<ListView>, SmoneyRequestManager$a, if$a {
    private LinearLayout e;
    private P2PGetMult f;
    private LayoutInflater g;
    private PullToRefreshListView h;
    private hl$a i;
    private int j;
    private boolean k = false;
    private boolean l = false;
    private String m;
    private String n;
    private MenuItem o;
    private View p;
    private boolean q;
    private final Time r = new Time();
    private cl s;
    private hl$d t;

    private void a(long j, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (j == 0) {
            a(hw.a(this.d, this));
        } else {
            this.i.setNotifyOnChange(false);
            int count = this.i.getCount();
            int i = 0;
            while (i < count) {
                P2PGetMult p2PGetMult = (P2PGetMult) this.i.getItem(i);
                if (j == p2PGetMult.c) {
                    if (p2PGetMult.p && i < count - 1) {
                        ((P2PGetMult) this.i.getItem(i + 1)).p = true;
                        ((P2PGetMult) this.s.G.a.get(i + 1)).p = true;
                    }
                    this.i.remove(p2PGetMult);
                    this.s.G.a.remove(i);
                    this.i.notifyDataSetChanged();
                    if (this.i.getCount() == 0) {
                        c();
                    }
                }
                i++;
            }
            this.i.notifyDataSetChanged();
            if (this.i.getCount() == 0) {
                c();
            }
        }
    }

    private void a(P2PGetListData p2PGetListData, ServerError serverError) {
        this.d.setSupportProgressBarIndeterminateVisibility(false);
        if (serverError != null) {
            this.k = true;
            a(serverError);
        } else if (p2PGetListData == null) {
            a(hw.a(this.d, this));
        } else {
            if (this.l) {
                this.s.cu = false;
            }
            c(true);
            this.e.setVisibility(0);
            a(p2PGetListData.a);
        }
        this.h.onRefreshComplete();
    }

    private void a(ArrayList<P2PGetMult> arrayList) {
        this.i.setNotifyOnChange(false);
        this.i.clear();
        this.m = null;
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                P2PGetMult p2PGetMult = (P2PGetMult) it.next();
                Time time = new Time();
                time.set(p2PGetMult.e);
                String a = jk.a(this.d, time);
                if (this.m == null || !this.m.equals(a)) {
                    p2PGetMult.p = true;
                    this.m = a;
                } else {
                    p2PGetMult.p = false;
                }
                this.i.add(p2PGetMult);
            }
        }
        this.i.notifyDataSetChanged();
        c();
    }

    private void a(boolean z, boolean z2, boolean z3) {
        int b;
        long j = -1;
        int i = -1;
        cl i2 = i();
        this.l = z;
        this.m = null;
        switch (this.j) {
            case 0:
                i = 0;
                break;
            case 1:
                i = 2;
                break;
            case 2:
                i = 1;
                break;
        }
        c(false);
        if (z2) {
            this.e.setVisibility(8);
        }
        this.d.setSupportProgressBarIndeterminateVisibility(z3);
        if (z) {
            b = j().b(i2.b.a, i2.b.c, -1, 0, i, z);
        } else {
            int count = this.i.getCount();
            if (count > 0) {
                j = ((P2PGetMult) this.i.getItem(0)).c;
            }
            b = j().b(i2.b.a, i2.b.c, j, count, i, z);
        }
        super.a(b, 61, z2);
    }

    static /* synthetic */ boolean a(hl hlVar, ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((P2PGet) it.next()).m == 0) {
                return false;
            }
        }
        return true;
    }

    private void c(boolean z) {
        this.q = z;
        this.d.supportInvalidateOptionsMenu();
    }

    private void n() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        long j2 = this.f.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 62 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetHideUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pGetHideSessionId").equals(str2) && ((long) intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetHideGetId", -1)) == j2 && intent.getIntExtra("fr.smoney.android.izly.extras.p2pGetHideGetType", -1) == 1) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 62);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetHideUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetHideSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetHideGetId", j2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pGetHideGetType", 1);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.I = -1;
        j.f.J = null;
        super.a(keyAt, 62, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        boolean z = false;
        switch (hl$1.a[ieVar.ordinal()]) {
            case 1:
                int h = h();
                if (h == 61) {
                    this.k = false;
                    boolean z2 = this.l;
                    boolean z3 = this.s.G == null;
                    if (this.s.G != null) {
                        z = true;
                    }
                    a(z2, z3, z);
                    return;
                } else if (h == 62) {
                    n();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case 2:
                this.j = bundle.getInt("Data.SelectItem");
                Editor edit = this.d.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
                edit.putInt("sharedPrefP2PGetListFilter", this.j);
                edit.commit();
                a(true, true, false);
                return;
            case 3:
                n();
                return;
            case 4:
                if (i().b != null) {
                    j().c(i().b.a, i().b.c);
                    return;
                }
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 61) {
                a((P2PGetListData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pGetListData"), serverError);
            } else if (i2 == 62) {
                a(bundle.getLong("fr.smoney.android.izly.extras.p2pGetHideIdToHide"), serverError);
            }
        }
    }

    protected final void b() {
        if (!ad.a) {
            super.b();
        }
    }

    public final void b(ie ieVar) {
        switch (hl$1.a[ieVar.ordinal()]) {
            case 1:
                if (h() == 61 && this.s.G == null) {
                    if (this.d instanceof ih) {
                        ((ih) this.d).e();
                        break;
                    }
                }
                super.b(ieVar);
                break;
                break;
            case 4:
                break;
            default:
                super.b(ieVar);
                return;
        }
        if (this.d instanceof ih) {
            ((ih) this.d).e();
        }
    }

    public final void b_(int i) {
        if (i == 61) {
            a(this.s.G, this.s.H);
        } else if (i == 62) {
            a(this.s.I, this.s.J);
        }
    }

    public final void d(ie ieVar) {
        switch (hl$1.a[ieVar.ordinal()]) {
            case 5:
                g();
                if (this.d instanceof ih) {
                    ((ih) this.d).e();
                    return;
                }
                return;
            default:
                super.d(ieVar);
                return;
        }
    }

    protected final String k() {
        return getString(R.string.my_request);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        b(true);
        this.s = i();
        this.g = this.d.getLayoutInflater();
        this.n = Currency.getInstance(i().b.j).getSymbol();
        if (bundle != null) {
            this.k = bundle.getBoolean("savedStateIsLastRequestInError");
            this.l = bundle.getBoolean("savedStateIsRequestARefresh");
            this.q = bundle.getBoolean("savedStatemDisplayItem");
        }
        this.i = new hl$a(this, this.d);
        ((ListView) this.h.getRefreshableView()).setAdapter(this.i);
        ((ListView) this.h.getRefreshableView()).setOnItemLongClickListener(this.i);
        this.j = this.d.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).getInt("sharedPrefP2PGetListFilter", 3);
        if (this.s.G != null) {
            c(true);
            a(this.s.G.a);
            return;
        }
        a(true, true, false);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.o = menu.add(R.string.menu_item_filter);
        this.o.setIcon(R.drawable.pict_filter);
        this.o.setShowAsAction(2);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.p = layoutInflater.inflate(R.layout.p2p_get_list, null);
        this.h = (PullToRefreshListView) this.p.findViewById(R.id.listP2PGet);
        this.h.setOnScrollListener(new hl$c(this, SmoneyApplication.d.a, true, false));
        this.h.setOnItemClickListener(this);
        this.h.setOnRefreshListener(this);
        this.e = (LinearLayout) this.p.findViewById(R.id.emptyP2PGet);
        this.h.setEmptyView(this.e);
        return this.p;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - ((ListView) this.h.getRefreshableView()).getHeaderViewsCount();
        if (headerViewsCount != -1) {
            P2PGetMult p2PGetMult = (P2PGetMult) this.i.getItem(headerViewsCount);
            Intent intent = new Intent(this.d, P2PGetDetailsActivity.class);
            intent.putExtra("fr.smoney.android.izly.extras.p2pGetMultDetailsData", p2PGetMult);
            startActivity(intent);
            ((P2PGetMult) this.i.getItem(headerViewsCount)).d = true;
            this.i.notifyDataSetChanged();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.o) {
            return super.onOptionsItemSelected(menuItem);
        }
        a(id.a(getString(R.string.dialog_operation_filter_title), getResources().getTextArray(R.array.p2p_get_list_filter), this.j, this, ie.OperationListFilterType));
        return true;
    }

    public void onPause() {
        this.d.unregisterReceiver(this.t);
        this.t = null;
        super.onPause();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        this.o.setVisible(this.q);
        super.onPrepareOptionsMenu(menu);
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        a(true, false, false);
    }

    public void onResume() {
        boolean z = false;
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.t == null) {
            this.t = new hl$d();
        }
        this.d.registerReceiver(this.t, intentFilter);
        if (this.s.cu && !a(61)) {
            boolean z2 = this.s.G == null;
            if (this.s.G != null) {
                z = true;
            }
            a(true, z2, z);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("savedStateIsRequestARefresh", this.l);
        bundle.putBoolean("savedStateIsLastRequestInError", this.k);
        bundle.putBoolean("savedStatemDisplayItem", this.q);
        super.onSaveInstanceState(bundle);
    }
}
