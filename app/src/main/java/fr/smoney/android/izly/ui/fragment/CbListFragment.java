package fr.smoney.android.izly.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify$IconValue;

import defpackage.ht;
import defpackage.hw;
import defpackage.ib;
import defpackage.ie;
import defpackage.ih;
import defpackage.is;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.ChooseDefaultCbData;
import fr.smoney.android.izly.data.model.GetMyCbListData;
import fr.smoney.android.izly.data.model.MoneyInCbCb;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.CbAddActivity;
import fr.smoney.android.izly.ui.CbEditActivity;
import fr.smoney.android.izly.ui.CbListActivity;
import fr.smoney.android.izly.ui.CompleteAccountWrapperActivity;

import java.util.ArrayList;
import java.util.Iterator;

public class CbListFragment extends aa implements OnClickListener, OnItemClickListener, SmoneyRequestManager$a {
    private ListView e;
    private View f;
    private Button g;
    private GetMyCbListData h;
    private MenuItem i;
    private b j;
    private View k;
    private c l;

    final class a {
        TextView a;
        TextView b;
        TextView c;
        final /* synthetic */ CbListFragment d;

        public a(CbListFragment cbListFragment, View view) {
            this.d = cbListFragment;
            this.a = (TextView) view.findViewById(R.id.tv_alias);
            this.b = (TextView) view.findViewById(R.id.tv_network_hint);
            this.c = (TextView) view.findViewById(R.id.iv_default);
        }
    }

    final class b extends BaseAdapter {
        ArrayList<MoneyInCbCb> a = new ArrayList();
        final /* synthetic */ CbListFragment b;
        private LayoutInflater c;

        public b(CbListFragment cbListFragment) {
            this.b = cbListFragment;
            this.c = cbListFragment.d.getLayoutInflater();
        }

        public final MoneyInCbCb a(int i) {
            return (MoneyInCbCb) this.a.get(i);
        }

        public final int getCount() {
            return this.a.size();
        }

        public final /* synthetic */ Object getItem(int i) {
            return a(i);
        }

        public final long getItemId(int i) {
            return (long) i;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = this.c.inflate(R.layout.cb_list_item, null);
                aVar = new a(this.b, view);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            aVar = aVar;
            MoneyInCbCb a = a(i);
            if (a != null) {
                aVar.a.setText(a.b);
                aVar.b.setText(aVar.d.getString(R.string.cb_list_tv_network_hint_format, new Object[]{MoneyInCbCb.a(aVar.d.d, a.c), a.d}));
                if (a.e) {
                    aVar.c.setVisibility(0);
                } else {
                    aVar.c.setVisibility(8);
                }
            }
            return view;
        }
    }

    final class c extends BroadcastReceiver {
        final /* synthetic */ CbListFragment a;

        private c(CbListFragment cbListFragment) {
            this.a = cbListFragment;
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("fr.smoney.android.izly.sessionState", -1) == 1) {
                this.a.p();
            }
        }
    }

    private void a(ChooseDefaultCbData chooseDefaultCbData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (chooseDefaultCbData == null) {
            a(hw.a(getActivity(), this));
        } else {
            p();
            Toast.makeText(getActivity(), R.string.cb_edit_toast_default_cb_success, 1).show();
        }
    }

    private void a(GetMyCbListData getMyCbListData) {
        this.j.a.clear();
        int size = getMyCbListData.a.size();
        if (size > 0) {
            Iterator it = getMyCbListData.a.iterator();
            while (it.hasNext()) {
                this.j.a.add((MoneyInCbCb) it.next());
            }
        }
        if (size <= 1) {
            this.g.setVisibility(8);
        } else {
            this.g.setVisibility(0);
        }
        this.j.notifyDataSetChanged();
    }

    private void a(GetMyCbListData getMyCbListData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getMyCbListData == null) {
            a(hw.a(this.d, this));
        } else {
            this.h = getMyCbListData;
            this.f.setVisibility(0);
            a(getMyCbListData);
        }
    }

    public static CbListFragment n() {
        return new CbListFragment();
    }

    private void o() {
        startActivityForResult(new Intent(this.d, CbAddActivity.class), 2);
    }

    private void p() {
        this.f.setVisibility(8);
        cl i = i();
        super.a(j().d(i.b.a, i.b.c), 230, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        Intent a;
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 211) {
                    p();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case CompleteSubscriptionInfoType:
                a = is.a(this.d, CompleteAccountWrapperActivity.class);
                a.putExtra("fr.smoney.android.izly.extras.isADirectCall", true);
                startActivityForResult(a, 401);
                return;
            case SelectDefaultCbType:
                int keyAt;
                String str = ((MoneyInCbCb) this.h.a.get(bundle.getInt("Data.SelectItem"))).a;
                cl i = i();
                SmoneyRequestManager j = j();
                String str2 = i.b.a;
                String str3 = i.b.c;
                String valueOf = String.valueOf(str);
                int size = j.b.size();
                for (int i2 = 0; i2 < size; i2++) {
                    a = (Intent) j.b.valueAt(i2);
                    if (a.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 211 && a.getStringExtra("fr.smoney.android.izly.extras.chooseDefaultCbUserId").equals(str2) && a.getStringExtra("fr.smoney.android.izly.extras.chooseDefaultCbSessionId").equals(str3) && a.getStringExtra("fr.smoney.android.izly.extras.chooseDefaultCbCardId").equals(valueOf)) {
                        keyAt = j.b.keyAt(i2);
                        super.a(keyAt, 211, true);
                        return;
                    }
                }
                keyAt = SmoneyRequestManager.a.nextInt(1000000);
                Intent intent = new Intent(j.c, SmoneyService.class);
                intent.putExtra("com.foxykeep.datadroid.extras.workerType", 211);
                intent.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
                intent.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
                intent.putExtra("fr.smoney.android.izly.extras.chooseDefaultCbUserId", str2);
                intent.putExtra("fr.smoney.android.izly.extras.chooseDefaultCbSessionId", str3);
                intent.putExtra("fr.smoney.android.izly.extras.chooseDefaultCbCardId", valueOf);
                j.c.startService(intent);
                j.b.append(keyAt, intent);
                j.f.aU = null;
                j.f.aV = null;
                super.a(keyAt, 211, true);
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 230) {
                a((GetMyCbListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMyCbList"), serverError);
            } else if (i2 == 211) {
                a((ChooseDefaultCbData) bundle.getParcelable("fr.smoney.android.izly.extras.chooseDefaultIdData"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() != 211) {
                    super.b(ieVar);
                    return;
                } else if (this.d instanceof CbListActivity) {
                    this.d.finish();
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
        if (i == 230) {
            a(i2.bz, i2.bA);
        } else if (i == 211) {
            a(i2.aU, i2.aV);
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType) {
            g();
            if (this.d instanceof CbListActivity) {
                this.d.finish();
                return;
            } else if (this.d instanceof ih) {
                ((ih) this.d).e();
                return;
            } else {
                return;
            }
        }
        super.d(ieVar);
    }

    protected final String k() {
        return getString(R.string.cb_list_title);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        b(true);
        this.e = (ListView) this.k.findViewById(R.id.lv_cb);
        this.f = this.k.findViewById(R.id.iv_no_cb);
        this.e.setOnItemClickListener(this);
        this.e.setEmptyView(this.f);
        this.j = new b(this);
        this.e.setAdapter(this.j);
        this.g = (Button) this.k.findViewById(R.id.b_default);
        this.g.setOnClickListener(this);
        if (bundle != null) {
            this.h = (GetMyCbListData) bundle.getParcelable("GetMyCbListData");
        }
        if (this.h != null) {
            a(this.h);
        } else {
            p();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 1:
                if (i2 == -1) {
                    p();
                    return;
                }
                return;
            case 2:
                if (i2 == -1) {
                    GetMyCbListData getMyCbListData = (GetMyCbListData) intent.getParcelableExtra("fr.smoney.android.izly.cbListExtra");
                    if (getMyCbListData != null) {
                        this.h = getMyCbListData;
                        a(getMyCbListData);
                        return;
                    }
                    p();
                    return;
                }
                p();
                return;
            case 401:
                if (i2 == -1) {
                    o();
                    return;
                }
                return;
            default:
                super.onActivityResult(i, i2, intent);
                return;
        }
    }

    public void onClick(View view) {
        if (view == this.g) {
            int size = this.h.a.size();
            CharSequence[] charSequenceArr = new String[size];
            for (int i = 0; i < size; i++) {
                charSequenceArr[i] = ((MoneyInCbCb) this.h.a.get(i)).b;
            }
            a(ib.a(getString(R.string.cb_list_b_default), charSequenceArr, this, ie.SelectDefaultCbType));
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.i = menu.add(R.string.menu_item_add_cb);
        this.i.setIcon(new IconDrawable(this.d, Iconify$IconValue.fa_plus_circle).colorRes(R.color.izly_blue).actionBarSize());
        this.i.setShowAsAction(2);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.k = layoutInflater.inflate(R.layout.cb_list, viewGroup, false);
        jb.a(getActivity(), R.string.screen_name_list_credit_card_activity);
        return this.k;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Parcelable a = this.j.a(i);
        Intent intent = new Intent(this.d, CbEditActivity.class);
        intent.putExtra("intentExtraCb", a);
        startActivityForResult(intent, 1);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == this.i) {
            if (i().b.E == 0) {
                a(ht.a(getString(R.string.dialog_complete_subscription_info_title), getString(R.string.dialog_complete_subscription_address_msg), getString(R.string.dialog_complete_subscription_info_complete_btn), getString(17039360), this, ie.CompleteSubscriptionInfoType));
            } else if (i().b.a()) {
                Intent a = is.a(this.d, CompleteAccountWrapperActivity.class);
                a.putExtra("fr.smoney.android.izly.extras.isADirectCall", true);
                startActivityForResult(a, 2);
            } else {
                o();
            }
            return true;
        } else if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            e();
            return true;
        }
    }

    public void onPause() {
        this.d.unregisterReceiver(this.l);
        this.l = null;
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.l == null) {
            this.l = new c();
        }
        this.d.registerReceiver(this.l, intentFilter);
        this.d.getSupportActionBar().setTitle(R.string.cb_list_title);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.h != null) {
            bundle.putParcelable("GetMyCbListData", this.h);
        }
    }
}
