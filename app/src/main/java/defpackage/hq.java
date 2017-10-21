package defpackage;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.CounterListData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.Transaction;
import fr.smoney.android.izly.data.model.TransactionListData;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.HistoryActivity;
import fr.smoney.android.izly.ui.TransactionListDetailsActivity;
import java.util.ArrayList;
import java.util.Iterator;

public class hq extends aa implements OnRefreshListener, OnClickListener, OnItemClickListener, SmoneyRequestManager$a {
    private LayoutInflater e;
    private boolean f = false;
    private boolean g = false;
    private boolean h = false;
    private int i;
    private ListView j;
    private SwipeRefreshLayout k;
    private hq$c l;
    private String m;
    private MenuItem n;
    private View o;
    private boolean p;
    private LinearLayout q;
    private View r;
    private Time s;
    private cl t;
    private hq$b u;
    private int v;
    private int w;

    private void a(CounterListData counterListData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (counterListData == null) {
            a(hw.a(this.d, this));
        } else {
            HistoryActivity historyActivity = (HistoryActivity) getActivity();
            ((aa) historyActivity.mViewPager.getAdapter().instantiateItem(historyActivity.mViewPager, historyActivity.b)).a(counterListData, serverError);
        }
    }

    private void a(TransactionListData transactionListData, ServerError serverError) {
        this.d.setSupportProgressBarIndeterminateVisibility(false);
        if (serverError != null) {
            this.f = true;
            a(serverError);
        } else if (transactionListData == null) {
            a(hw.a(this.d, this));
        } else {
            if (this.g) {
                this.t.ct = false;
            }
            if (transactionListData.c != null) {
                this.t.b.B = transactionListData.c;
                c();
            }
            a(transactionListData.b);
            super.a(j().e(this.t.b.a, this.t.b.c), 261, false);
        }
        this.k.setRefreshing(false);
        this.h = false;
    }

    private void a(ArrayList<Transaction> arrayList) {
        this.l.setNotifyOnChange(false);
        this.l.clear();
        this.m = null;
        a(this.r);
        if (arrayList.size() > 0) {
            this.q.setVisibility(8);
            c(true);
            this.s = new Time();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Transaction transaction = (Transaction) it.next();
                this.s.set(transaction.l);
                String a = jk.a(this.d, this.s);
                if (this.m == null || !this.m.equals(a)) {
                    transaction.B = true;
                    this.m = a;
                } else {
                    transaction.B = false;
                }
                this.l.add(transaction);
            }
        } else {
            c(false);
            this.q.setVisibility(0);
        }
        this.l.notifyDataSetChanged();
        c();
    }

    private void a(boolean z, boolean z2, boolean z3) {
        int a;
        long j = -1;
        int i = -1;
        this.h = true;
        this.g = z;
        switch (this.i) {
            case 1:
                i = 0;
                break;
            case 2:
                i = 1;
                break;
            case 3:
                i = 2;
                break;
            case 4:
                i = 3;
                break;
            case 7:
                i = 6;
                break;
            case 1000:
                i = 4;
                break;
            case 1001:
                i = 5;
                break;
            case 1002:
                i = 8;
                break;
        }
        c(false);
        if (z2) {
            this.q.setVisibility(8);
        }
        this.d.setSupportProgressBarIndeterminateVisibility(z3);
        if (z) {
            a = j().a(this.t.b.a, this.t.b.c, -1, 0, i, z);
        } else {
            int count = this.l.getCount();
            if (count > 0) {
                j = ((Transaction) this.l.getItem(0)).b;
            }
            a = j().a(this.t.b.a, this.t.b.c, j, count, i, z);
        }
        super.a(a, 21, z2);
    }

    private void c(boolean z) {
        this.p = z;
        this.d.supportInvalidateOptionsMenu();
    }

    private void n() {
        switch (this.i) {
            case 0:
                this.r.findViewById(R.id.ll_header_transaction_sort_by).setVisibility(8);
                return;
            default:
                String[] stringArray = getResources().getStringArray(R.array.transaction_list_filter);
                if (this.i > 0 && this.i < stringArray.length) {
                    ((TextView) this.r.findViewById(R.id.tv_header_transaction_sort_by_value)).setText(stringArray[this.i]);
                    this.r.findViewById(R.id.ll_header_transaction_sort_by).setVisibility(0);
                    return;
                }
                return;
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        boolean z = false;
        switch (hq$1.a[ieVar.ordinal()]) {
            case 1:
                this.i = bundle.getInt("Data.SelectItem");
                Editor edit = this.d.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
                edit.putInt("sharedPrefTransactionListFilter", this.i);
                edit.commit();
                n();
                if (!this.h) {
                    a(true, true, false);
                    return;
                }
                return;
            case 2:
                if (h() != 21 || this.h) {
                    super.a(ieVar, bundle);
                    return;
                }
                this.f = false;
                boolean z2 = this.g;
                boolean z3 = this.t.n == null;
                if (this.t.n != null) {
                    z = true;
                }
                a(z2, z3, z);
                return;
            case 3:
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
            if (i2 == 21) {
                a((TransactionListData) bundle.getParcelable("fr.smoney.android.izly.extras.transactionListData"), serverError);
            } else if (i2 == 261) {
                a((CounterListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMyCounterList"), serverError);
            }
        }
    }

    protected final void b() {
        if (!ad.a) {
            super.b();
        }
    }

    public final void b(ie ieVar) {
        switch (hq$1.a[ieVar.ordinal()]) {
            case 2:
                if (h() == 21 && this.t.n == null) {
                    if (this.d instanceof ih) {
                        ((ih) this.d).e();
                        break;
                    }
                }
                super.b(ieVar);
                break;
                break;
            case 3:
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
        if (i == 21) {
            a(this.t.n, this.t.o);
        } else if (i == 261) {
            a(this.t.bB, this.t.bC);
        }
    }

    public final void d(ie ieVar) {
        switch (hq$1.a[ieVar.ordinal()]) {
            case 4:
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
        return getString(R.string.transaction_list_title);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        b(true);
        this.t = i();
        this.l = new hq$c(this, this.d);
        ListAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(this.l);
        alphaInAnimationAdapter.setAbsListView(this.j);
        this.j.addHeaderView(this.r, null, false);
        this.j.setAdapter(alphaInAnimationAdapter);
        if (bundle != null) {
            this.f = bundle.getBoolean("savedStateIsLastRequestInError");
            this.g = bundle.getBoolean("savedStateIsRequestARefresh");
            this.p = bundle.getBoolean("savedStatemDisplayItem");
        }
        this.i = this.d.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).getInt("sharedPrefTransactionListFilter", 0);
        n();
        this.t.n = null;
        if (this.t.n != null) {
            c(true);
            a(this.t.n.b);
        } else if (!this.h) {
            a(true, true, false);
        }
    }

    public void onClick(View view) {
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.n = menu.add(R.string.menu_item_filter);
        this.n.setIcon(R.drawable.icon_filtres);
        this.n.setShowAsAction(0);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.o = layoutInflater.inflate(R.layout.transaction_list, null);
        this.j = (ListView) this.o.findViewById(R.id.listTransactions);
        this.q = (LinearLayout) this.o.findViewById(R.id.emptyTransactions);
        this.k = (SwipeRefreshLayout) this.o.findViewById(R.id.swl_listTransactions);
        this.j.setOnItemClickListener(this);
        this.j.setOnScrollListener(new hq$a(this, SmoneyApplication.d.a, true, false));
        this.k.setOnRefreshListener(this);
        this.k.setColorSchemeResources(new int[]{R.color.izly_blue_light});
        this.r = layoutInflater.inflate(R.layout.header_transaction_account_balance, null);
        Resources resources = getResources();
        this.v = resources.getColor(R.color.general_tv_green);
        this.w = resources.getColor(R.color.general_tv_red);
        return this.o;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.j.getHeaderViewsCount();
        if (headerViewsCount != -1) {
            Transaction transaction = (Transaction) this.l.getItem(headerViewsCount);
            if (transaction != null) {
                Intent intent = new Intent(this.d, TransactionListDetailsActivity.class);
                intent.putExtra("fr.smoney.android.izly.intentExtrasTransaction", transaction);
                startActivity(intent);
                ((Transaction) this.l.getItem(headerViewsCount)).d = true;
                this.l.notifyDataSetChanged();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.n) {
            return super.onOptionsItemSelected(menuItem);
        }
        a(id.a(getString(R.string.dialog_operation_filter_title), getResources().getTextArray(R.array.transaction_list_filter), this.i, this, ie.OperationListFilterType));
        return true;
    }

    public void onPause() {
        this.d.unregisterReceiver(this.u);
        this.u = null;
        super.onPause();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        this.n.setVisible(this.p);
        super.onPrepareOptionsMenu(menu);
    }

    public void onRefresh() {
        this.s = new Time();
        if (!this.h) {
            a(true, false, false);
        }
    }

    public void onResume() {
        boolean z = false;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.u == null) {
            this.u = new hq$b();
        }
        this.d.registerReceiver(this.u, intentFilter);
        super.onResume();
        if (this.t.ct && !a(21) && !this.h) {
            boolean z2 = this.t.n == null;
            if (this.t.n != null) {
                z = true;
            }
            a(true, z2, z);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("savedStateIsLastRequestInError", this.f);
        bundle.putBoolean("savedStateIsRequestARefresh", this.g);
        bundle.putBoolean("savedStatemDisplayItem", this.p);
    }
}
