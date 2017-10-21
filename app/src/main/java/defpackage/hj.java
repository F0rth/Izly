package defpackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetNearProListData;
import fr.smoney.android.izly.data.model.NearPro;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.ContactDetailsActivity;
import fr.smoney.android.izly.ui.NearProActivity;
import java.util.ArrayList;
import java.util.Iterator;

public class hj extends aa implements OnRefreshListener, OnItemClickListener, SmoneyRequestManager$a, gf {
    private int e = 0;
    private ListView f;
    private SwipeRefreshLayout g;
    private GetNearProListData h;
    private Location i;
    private int j;
    private hj$b k;
    private boolean l;
    private int m;
    private ProgressDialog n;
    private View o;
    private cn p;
    private View q;
    private OnClickListener r = new hj$2(this);
    private OnClickListener s = new hj$3(this);

    private void a(Location location, int i, boolean z) {
        if (location != null) {
            c(false);
            this.g.setRefreshing(true);
            cl i2 = i();
            super.a(j().a(i2.b.a, i2.b.c, location.getLatitude(), location.getLongitude(), i, 0), 213, z);
        }
    }

    private void a(GetNearProListData getNearProListData, ServerError serverError) {
        int i = 0;
        this.g.setRefreshing(false);
        if (serverError != null) {
            a(serverError);
        } else if (getNearProListData == null) {
            a(hw.a(getActivity(), this));
        } else {
            this.h = getNearProListData;
            View view = this.o;
            if (this.h.b.size() != 0) {
                i = 8;
            }
            view.setVisibility(i);
            c(true);
            a(this.h.b);
            ((NearProActivity) getActivity()).d.b_();
        }
    }

    private void a(ArrayList<NearPro> arrayList) {
        this.k.setNotifyOnChange(false);
        this.k.clear();
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.k.add((NearPro) it.next());
            }
        }
        this.k.notifyDataSetChanged();
    }

    private void c(boolean z) {
        if (this.m != 0 || this.p == cn.l) {
            z = false;
        }
        this.l = z;
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (hj$4.a[ieVar.ordinal()]) {
            case 1:
                if (h() != 213 || this.i == null) {
                    super.a(ieVar, bundle);
                    return;
                } else {
                    a(this.i, this.j, this.h == null);
                    return;
                }
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 213) {
                a((GetNearProListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNearProList"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (hj$4.a[ieVar.ordinal()]) {
            case 1:
                if (h() == 213) {
                    getActivity().finish();
                    return;
                } else {
                    super.b(ieVar);
                    return;
                }
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_() {
        this.o.setVisibility(0);
        c(true);
        a(i().aW.b);
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 213) {
            a(i2.aW, i2.aX);
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 213) {
            g();
            getActivity().finish();
            return;
        }
        super.d(ieVar);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Intent intent = getActivity().getIntent();
        this.m = intent.getIntExtra("fr.smoney.android.izly.intentExtrasDisplayMode", 0);
        this.i = (Location) intent.getParcelableExtra("fr.smoney.android.izly.extras.INTENT_EXTRA_LOCATION");
        if (this.m == 0) {
            this.j = intent.getIntExtra("fr.smoney.android.izly.extras.INTENT_EXTRA_CATEGORY", 0);
            this.p = cn.a(this.j);
        } else {
            this.j = cn.m.p;
        }
        if (bundle != null) {
            this.h = (GetNearProListData) bundle.getParcelable("getNearProListData");
        }
        if (this.h != null) {
            c(true);
            a(this.h.b);
        } else if (this.i != null) {
            a(this.i, this.j, true);
        } else {
            if (this.n == null) {
                this.n = new ProgressDialog(getActivity());
                this.n.setTitle(R.string.near_pro_list_locate_title);
                this.n.setMessage(getString(R.string.near_pro_list_locate_message));
                this.n.setIndeterminate(true);
                this.n.setCancelable(true);
                this.n.setCanceledOnTouchOutside(false);
                this.n.setOnCancelListener(new hj$1(this));
            }
            this.n.show();
        }
        ((NearProActivity) getActivity()).e = this;
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.q = layoutInflater.inflate(R.layout.near_pro_list, null);
        this.f = (ListView) this.q.findViewById(R.id.listViewNearPro);
        this.f.setOnItemClickListener(this);
        this.g = (SwipeRefreshLayout) this.q.findViewById(R.id.swl_near_pro_list);
        this.g.setOnRefreshListener(this);
        this.g.setColorSchemeResources(new int[]{R.color.izly_blue_light});
        this.o = this.q.findViewById(R.id.nearpro_list_empty);
        this.k = new hj$b(this, getActivity());
        ListAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(this.k);
        alphaInAnimationAdapter.setAbsListView(this.f);
        this.f.setAdapter(alphaInAnimationAdapter);
        return this.q;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int i2 = i - 1;
        if (i2 != -1 && adapterView.equals(this.f)) {
            Intent a;
            if (this.m == 0) {
                a = is.a(getActivity(), ContactDetailsActivity.class);
                a.putExtra("fr.smoney.android.izly.extras.contactId", (Parcelable) this.k.getItem(i2));
                startActivityForResult(a, 1);
                return;
            }
            a = new Intent();
            a.putExtra("resultIntentExtrasNearProId", ((NearPro) this.k.getItem(i2)).b);
            a.putExtra("nearProData", (Parcelable) this.k.getItem(i2));
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onRefresh() {
        this.e = 0;
        if (this.i != null) {
            a(this.i, this.j, false);
        }
    }

    public void onResume() {
        super.onResume();
        this.p = cn.a(((NearProActivity) getActivity()).c);
        if (this.p == null) {
            this.j = cn.m.p;
        }
    }
}
