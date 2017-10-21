package fr.smoney.android.izly.ui.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import defpackage.ht;
import defpackage.hw;
import defpackage.hy;
import defpackage.id;
import defpackage.ie;
import defpackage.ih;
import defpackage.jf;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.P2PPayRequest;
import fr.smoney.android.izly.data.model.P2PPayRequestData;
import fr.smoney.android.izly.data.model.P2PPayRequestListData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.CompleteAccountWrapperActivity;
import fr.smoney.android.izly.ui.MoneyInCbAndPayActivity;
import fr.smoney.android.izly.ui.P2PPayRequestActivity;
import fr.smoney.android.izly.ui.P2PPayRequestConfirmActivity;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;

public class P2PPayRequestListFragment extends aa implements OnClickListener, OnItemClickListener, OnRefreshListener<ListView>, SmoneyRequestManager$a {
    private PullToRefreshListView e;
    private LinearLayout f;
    private P2PPayRequest g;
    private double h;
    private int i;
    private b j;
    private int k;
    private boolean l;
    private String m;
    private boolean n;
    private cl o;
    private String p;
    private MenuItem q;
    private boolean r;
    private View s;
    private d t;

    final class a extends PauseOnScrollListener {
        final /* synthetic */ P2PPayRequestListFragment a;

        public a(P2PPayRequestListFragment p2PPayRequestListFragment, ImageLoader imageLoader, boolean z, boolean z2) {
            this.a = p2PPayRequestListFragment;
            super(imageLoader, true, false);
        }

        public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
            super.onScroll(absListView, i, i2, i3);
            boolean z = i + i2 >= i3 + -2;
            if (this.a.o.Q == null) {
                return;
            }
            if (this.a.o.Q.b) {
                if (this.a.l && !this.a.f() && i3 != 0 && !z) {
                    this.a.l = false;
                }
            } else if (i3 != 0 && z && !this.a.a(71)) {
                this.a.a(false, false, true);
            }
        }
    }

    final class b extends ArrayAdapter<P2PPayRequest> implements OnItemLongClickListener {
        public int a = -1;
        final /* synthetic */ P2PPayRequestListFragment b;
        private LayoutInflater c;

        public b(P2PPayRequestListFragment p2PPayRequestListFragment, Context context, LayoutInflater layoutInflater) {
            this.b = p2PPayRequestListFragment;
            super(context, -1);
            this.c = layoutInflater;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            f fVar;
            int i2 = 1;
            if (view == null) {
                view = this.c.inflate(R.layout.list_item, null);
                f fVar2 = new f(this.b, view);
                view.setTag(fVar2);
                fVar = fVar2;
            } else {
                fVar = (f) view.getTag();
            }
            P2PPayRequest p2PPayRequest = (P2PPayRequest) getItem(i);
            if (p2PPayRequest != null) {
                if (p2PPayRequest.b) {
                    fVar.d.setVisibility(8);
                } else {
                    fVar.d.setVisibility(0);
                }
                fVar.k.setVisibility(8);
                Time time = new Time();
                time.set(p2PPayRequest.c);
                if (p2PPayRequest.s) {
                    fVar.a.setVisibility(0);
                    fVar.a.setText(jk.a(fVar.l.d, time));
                } else {
                    fVar.a.setVisibility(8);
                }
                fVar.e.setText(time.format("%H:%M"));
                if (p2PPayRequest.g) {
                    fVar.h.setText("");
                } else {
                    fVar.h.setText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(p2PPayRequest.h), fVar.l.p}));
                }
                fVar.c.setOnClickListener(fVar.l);
                fVar.c.setImageResource(R.drawable.list_aiv_avatar_placeholder);
                fVar.l.c.a.displayImage(jl.a(p2PPayRequest.d), fVar.c);
                fVar.b.setOnClickListener(fVar.l);
                fVar.b.setTag(Integer.valueOf(i));
                fVar.f.setText(fVar.l.getString(R.string.p2p_pay_request_list_tv_name_format, new Object[]{jf.a(p2PPayRequest.e, p2PPayRequest.f, p2PPayRequest.d)}));
                switch (p2PPayRequest.n) {
                    case 0:
                        fVar.i.setImageResource(R.drawable.p2p_get_list_iv_status_to_be_paid);
                        break;
                    case 1:
                        fVar.i.setImageResource(R.drawable.p2p_get_list_iv_status_cancelled);
                        break;
                    case 2:
                        fVar.i.setImageResource(R.drawable.p2p_get_list_iv_status_refused);
                        break;
                    case 3:
                        fVar.i.setImageResource(R.drawable.p2p_get_list_iv_status_paid);
                        break;
                }
                int i3 = (p2PPayRequest.k == null || TextUtils.isEmpty(p2PPayRequest.k)) ? 0 : 1;
                if (!(p2PPayRequest.l == null || TextUtils.isEmpty(p2PPayRequest.l))) {
                    i3++;
                }
                fVar.g.setVisibility(i3 > 0 ? 0 : 8);
                if (p2PPayRequest.o == null || p2PPayRequest.p == null) {
                    i2 = 0;
                }
                if (i2 != 0) {
                    fVar.j.setVisibility(0);
                } else {
                    fVar.j.setVisibility(8);
                }
            }
            return view;
        }

        @TargetApi(11)
        public final boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
            int headerViewsCount = i - ((ListView) this.b.e.getRefreshableView()).getHeaderViewsCount();
            P2PPayRequest p2PPayRequest = (P2PPayRequest) getItem(headerViewsCount);
            this.b.g = p2PPayRequest;
            if (p2PPayRequest.n == 0) {
                this.b.d.startSupportActionMode(new e(this.b, headerViewsCount));
                return true;
            } else if (p2PPayRequest.n != 2 && p2PPayRequest.n != 1 && p2PPayRequest.n != 3) {
                return false;
            } else {
                this.b.d.startSupportActionMode(new c(this.b, headerViewsCount));
                return true;
            }
        }
    }

    final class c implements Callback {
        final /* synthetic */ P2PPayRequestListFragment a;
        private int b;

        public c(P2PPayRequestListFragment p2PPayRequestListFragment, int i) {
            this.a = p2PPayRequestListFragment;
            this.b = i;
        }

        public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case 1:
                    this.a.a(ht.a(this.a.getString(R.string.p2p_pay_request_dialog_hide_confirm_title), this.a.getString(R.string.p2p_pay_request_dialog_hide_confirm_message), this.a.getString(17039370), this.a.getString(17039360), this.a, ie.HideOperationType));
                    break;
            }
            actionMode.finish();
            return true;
        }

        public final boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            menu.add(0, 1, 1, R.string.p2p_pay_request_list_quick_action_hide).setIcon(R.drawable.pict_delete).setShowAsAction(1);
            return true;
        }

        public final void onDestroyActionMode(ActionMode actionMode) {
            this.a.j.a = -1;
            this.a.j.notifyDataSetChanged();
        }

        public final boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            this.a.j.a = this.b;
            this.a.j.notifyDataSetChanged();
            return true;
        }
    }

    final class d extends BroadcastReceiver {
        final /* synthetic */ P2PPayRequestListFragment a;

        private d(P2PPayRequestListFragment p2PPayRequestListFragment) {
            this.a = p2PPayRequestListFragment;
        }

        public final void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("fr.smoney.android.izly.sessionState", -1);
            if (intExtra == 0) {
                this.a.j.clear();
                this.a.j.notifyDataSetChanged();
                this.a.d();
            } else if (intExtra == 1) {
                this.a.a(true, true, false);
            } else if (intExtra == 2) {
                this.a.a(hw.a(this.a.d, this.a, ie.ConnexionErrorDuringIsSessionValid));
            }
        }
    }

    final class e implements Callback {
        final /* synthetic */ P2PPayRequestListFragment a;
        private int b;

        public e(P2PPayRequestListFragment p2PPayRequestListFragment, int i) {
            this.a = p2PPayRequestListFragment;
            this.b = i;
        }

        public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case 1:
                    if (!this.a.g.g) {
                        this.a.b(0);
                        break;
                    }
                    this.a.a(hy.a(this.a.getString(R.string.dialog_amount_entry_title), this.a.getString(R.string.dialog_amount_entry_et_amount_hint, new Object[]{Double.valueOf(this.a.o.b.d), Double.valueOf(this.a.o.b.e)}), null, this.a));
                    break;
                case 2:
                    this.a.a(this.a.g, 1);
                    break;
            }
            actionMode.finish();
            return true;
        }

        public final boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            menu.add(0, 1, 1, R.string.p2p_pay_request_list_quick_action_pay).setIcon(R.drawable.pict_valid).setShowAsAction(1);
            menu.add(0, 2, 2, R.string.p2p_pay_request_list_quick_action_refuse).setIcon(R.drawable.pict_cancel).setShowAsAction(1);
            return true;
        }

        public final void onDestroyActionMode(ActionMode actionMode) {
            this.a.j.a = -1;
            this.a.j.notifyDataSetChanged();
        }

        public final boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            this.a.j.a = this.b;
            this.a.j.notifyDataSetChanged();
            return true;
        }
    }

    final class f {
        TextView a;
        FrameLayout b;
        ImageView c;
        View d;
        TextView e;
        TextView f;
        ImageView g;
        TextView h;
        ImageView i;
        ImageView j;
        View k;
        final /* synthetic */ P2PPayRequestListFragment l;

        public f(P2PPayRequestListFragment p2PPayRequestListFragment, View view) {
            this.l = p2PPayRequestListFragment;
            this.a = (TextView) view.findViewById(R.id.tv_date);
            this.b = (FrameLayout) view.findViewById(R.id.fl_avatar);
            this.c = (ImageView) view.findViewById(R.id.aiv_avatar);
            this.d = view.findViewById(R.id.v_unread);
            this.e = (TextView) view.findViewById(R.id.tv_time);
            this.k = view.findViewById(R.id.tv_operation_label);
            this.f = (TextView) view.findViewById(R.id.tv_name);
            this.g = (ImageView) view.findViewById(R.id.iv_message);
            this.h = (TextView) view.findViewById(R.id.tv_amount);
            this.i = (ImageView) view.findViewById(R.id.iv_status);
            this.j = (ImageView) view.findViewById(R.id.tv_attachment);
        }
    }

    private void a(long j) {
        this.j.setNotifyOnChange(false);
        this.j.notifyDataSetChanged();
        int count = this.j.getCount();
        int i = 0;
        while (i < count) {
            P2PPayRequest p2PPayRequest = (P2PPayRequest) this.j.getItem(i);
            if (j == p2PPayRequest.a) {
                if (p2PPayRequest.s && i < count - 1) {
                    ((P2PPayRequest) this.j.getItem(i + 1)).s = true;
                    ((P2PPayRequest) this.o.Q.a.get(i + 1)).s = true;
                }
                this.j.remove(p2PPayRequest);
                this.j.notifyDataSetChanged();
                if (this.j.getCount() == 0) {
                    c();
                }
            }
            i++;
        }
        this.j.notifyDataSetChanged();
        if (this.j.getCount() == 0) {
            c();
        }
    }

    private void a(long j, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else {
            a(j);
        }
    }

    private void a(P2PPayRequest p2PPayRequest, int i) {
        Intent intent = new Intent(this.d, P2PPayRequestConfirmActivity.class);
        intent.putExtra("fr.smoney.android.izly.extras.p2pPayRequest", p2PPayRequest);
        intent.putExtra("fr.smoney.android.izly.extras.responseStatus", i);
        startActivity(intent);
    }

    private void a(P2PPayRequestData p2PPayRequestData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PPayRequestData == null) {
            a(hw.a(this.d, this));
        } else {
            this.o.b.B = p2PPayRequestData.g;
            if (this.i == 0 && p2PPayRequestData.b) {
                a(ht.a(getString(R.string.dialog_insufficient_found_title), getString(R.string.dialog_insufficient_found_message), getString(17039370), getString(17039360), this, ie.InsufficientFundType));
            } else {
                a(p2PPayRequestData.a, this.i);
            }
        }
    }

    private void a(P2PPayRequestListData p2PPayRequestListData, ServerError serverError) {
        this.d.setSupportProgressBarIndeterminateVisibility(false);
        if (serverError != null) {
            this.l = true;
            a(serverError);
        } else if (p2PPayRequestListData == null) {
            a(hw.a(this.d, this));
        } else {
            this.o.b.B = p2PPayRequestListData.e;
            if (this.n) {
                this.o.cv = false;
            }
            c(true);
            this.f.setVisibility(0);
            a(p2PPayRequestListData.a);
            if (p2PPayRequestListData.d) {
                Toast.makeText(this.d, R.string.p2p_pay_request_list_toast_new_data_available, 1).show();
            }
        }
        this.e.onRefreshComplete();
    }

    private void a(ArrayList<P2PPayRequest> arrayList) {
        this.j.setNotifyOnChange(false);
        this.j.clear();
        this.m = null;
        if (arrayList.size() > 0) {
            Time time = new Time();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                P2PPayRequest p2PPayRequest = (P2PPayRequest) it.next();
                time.set(p2PPayRequest.c);
                String a = jk.a(this.d, time);
                if (this.m == null || !this.m.equals(a)) {
                    p2PPayRequest.s = true;
                    this.m = a;
                } else {
                    p2PPayRequest.s = false;
                }
                this.j.add(p2PPayRequest);
            }
        }
        this.j.notifyDataSetChanged();
        c();
    }

    private void a(boolean z, boolean z2, boolean z3) {
        int c;
        long j = -1;
        int i = -1;
        this.n = z;
        switch (this.k) {
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
            this.f.setVisibility(8);
        }
        this.d.setSupportProgressBarIndeterminateVisibility(z3);
        if (z) {
            c = j().c(this.o.b.a, this.o.b.c, -1, 0, i, z);
        } else {
            int count = this.j.getCount();
            if (count > 0) {
                j = ((P2PPayRequest) this.j.getItem(0)).a;
            }
            c = j().c(this.o.b.a, this.o.b.c, j, count, i, z);
        }
        super.a(c, 71, z2);
    }

    private void b(int i) {
        this.i = i;
        double d = (i == 0 && this.g.g) ? this.h : this.g.h;
        super.a(j().a(this.o.b.a, this.o.b.c, this.g.a, i, "", d), 73, true);
    }

    private void c(boolean z) {
        this.r = z;
        this.d.supportInvalidateOptionsMenu();
    }

    private void n() {
        super.a(j().a(this.o.b.a, this.o.b.c, this.g.a), 72, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        boolean z = false;
        switch (ieVar) {
            case ConnexionErrorType:
                int h = h();
                if (h == 71) {
                    this.l = false;
                    boolean z2 = this.n;
                    boolean z3 = this.o.Q == null;
                    if (this.o.Q != null) {
                        z = true;
                    }
                    a(z2, z3, z);
                    return;
                } else if (h == 72) {
                    n();
                    return;
                } else if (h == 73) {
                    b(this.i);
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case InputAmountType:
                this.h = bundle.getDouble("Data.Amount");
                b(0);
                return;
            case HideOperationType:
                n();
                return;
            case OperationListFilterType:
                this.k = bundle.getInt("Data.SelectItem");
                Editor edit = this.d.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
                edit.putInt("sharedPrefP2PPayRequestListFilter", this.k);
                edit.commit();
                a(true, true, false);
                return;
            case InsufficientFundType:
                Intent intent;
                if (this.o.b == null || !this.o.b.a()) {
                    intent = new Intent(this.d, MoneyInCbAndPayActivity.class);
                    intent.putExtra("fr.smoney.android.izly.extras.p2pPayRequestData", this.o.U);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(this.d, CompleteAccountWrapperActivity.class);
                intent.putExtra("fr.smoney.android.izly.extras.activityToStart", 3);
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("fr.smoney.android.izly.extras.p2pPayRequestData", this.o.U);
                intent.putExtra("fr.smoney.android.izly.extras.activityToStartDataBundle", bundle2);
                startActivity(intent);
                return;
            case ConnexionErrorDuringIsSessionValid:
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
            if (i2 == 71) {
                a((P2PPayRequestListData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayRequestListData"), serverError);
            } else if (i2 == 72) {
                a(bundle.getLong("fr.smoney.android.izly.extras.p2pPayRequestHideIdToHide"), serverError);
            } else if (i2 == 73) {
                a((P2PPayRequestData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayRequestData"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 71 && this.o.Q == null) {
                    if (this.d instanceof ih) {
                        ((ih) this.d).e();
                        break;
                    }
                }
                super.b(ieVar);
                break;
            break;
            case ConnexionErrorDuringIsSessionValid:
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
        if (i == 71) {
            a(this.o.Q, this.o.R);
        } else if (i == 72) {
            a(this.o.S, this.o.T);
        } else if (i == 73) {
            a(this.o.U, this.o.V);
        }
    }

    public final void d(ie ieVar) {
        switch (ieVar) {
            case ProgressType:
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
        this.o = i();
        this.p = Currency.getInstance(this.o.b.j).getSymbol();
        this.e = (PullToRefreshListView) this.s.findViewById(R.id.listPayRequest);
        this.f = (LinearLayout) this.s.findViewById(R.id.emptyPayRequest);
        this.e.setOnScrollListener(new a(this, SmoneyApplication.d.a, true, false));
        this.e.setOnItemClickListener(this);
        this.e.setOnRefreshListener(this);
        this.e.setEmptyView(this.f);
        this.j = new b(this, this.d, this.d.getLayoutInflater());
        ((ListView) this.e.getRefreshableView()).setAdapter(this.j);
        ((ListView) this.e.getRefreshableView()).setOnItemLongClickListener(this.j);
        if (bundle != null) {
            this.n = bundle.getBoolean("savedStateIsRequestARefresh");
            this.l = bundle.getBoolean("savedStateIsLastRequestInError");
            this.h = bundle.getDouble("savedStateCurrentAmount", -1.0d);
            this.i = bundle.getInt("savedStateCurrentResponseStatus", -1);
        }
        this.k = this.d.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).getInt("sharedPrefP2PPayRequestListFilter", 3);
        if (this.o.Q != null) {
            c(true);
            a(this.o.Q.a);
            return;
        }
        a(true, false, false);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 10 && i2 == 5) {
            long longExtra = intent.getLongExtra("ResultIntentOpIdToHide", -1);
            if (longExtra != -1) {
                a(longExtra);
                return;
            }
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @SuppressLint({"NewApi"})
    public void onClick(View view) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.q = menu.add(R.string.menu_item_filter);
        this.q.setIcon(R.drawable.pict_filter);
        this.q.setShowAsAction(2);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.s = layoutInflater.inflate(R.layout.p2p_pay_request_list, viewGroup, false);
        return this.s;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - ((ListView) this.e.getRefreshableView()).getHeaderViewsCount();
        if (headerViewsCount != -1) {
            P2PPayRequest p2PPayRequest = (P2PPayRequest) this.j.getItem(headerViewsCount);
            Intent intent = new Intent(this.d, P2PPayRequestActivity.class);
            intent.putExtra("fr.smoney.android.izly.extras.p2pPayRequest", p2PPayRequest);
            startActivityForResult(intent, 10);
            ((P2PPayRequest) this.j.getItem(headerViewsCount)).b = true;
            this.j.notifyDataSetChanged();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.q) {
            return super.onOptionsItemSelected(menuItem);
        }
        a(id.a(getString(R.string.dialog_operation_filter_title), getResources().getTextArray(R.array.p2p_pay_request_list_filter), this.k, this, ie.OperationListFilterType));
        return true;
    }

    public void onPause() {
        this.d.unregisterReceiver(this.t);
        this.t = null;
        super.onPause();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        this.q.setVisible(this.r);
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
            this.t = new d();
        }
        this.d.registerReceiver(this.t, intentFilter);
        if (this.o.cv && !a(71)) {
            boolean z2 = this.o.Q == null;
            if (this.o.Q != null) {
                z = true;
            }
            a(true, z2, z);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("savedStateIsRequestARefresh", this.n);
        bundle.putBoolean("savedStateIsLastRequestInError", this.l);
        if (this.h != -1.0d) {
            bundle.putDouble("savedStateCurrentAmount", this.h);
        }
        if (this.i != -1) {
            bundle.putInt("savedStateCurrentResponseStatus", this.i);
        }
    }
}
