package fr.smoney.android.izly.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetNearProListData;
import fr.smoney.android.izly.data.model.NearPro;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

import java.util.ArrayList;
import java.util.Iterator;

public class NearProCategoryActivity extends SmoneyABSActivity implements TabListener, OnItemClickListener, OnRefreshListener<ListView>, SmoneyRequestManager$a {
    private PullToRefreshListView b;
    private View c;
    private ListView d;
    private Location e;
    private a f;
    private c g;
    private GetNearProListData h;
    private MenuItem i;
    private boolean j;
    private ActionBar k;
    private Tab l;
    private Tab m;
    private ProgressDialog n;
    private int o = 0;
    private OnClickListener p = new OnClickListener(this) {
        final /* synthetic */ NearProCategoryActivity a;

        {
            this.a = r1;
        }

        public final void onClick(View view) {
            int positionForView = ((ListView) this.a.b.getRefreshableView()).getPositionForView(view) - 1;
            if (positionForView != -1) {
                Intent intent;
                NearPro nearPro = (NearPro) this.a.g.getItem(positionForView);
                Intent a;
                if (nearPro.o != null) {
                    a = is.a(this.a, PreAuthorizePaymentActivity.class);
                    a.putExtra("INTENT_EXTRA_PRE_AUTHORIZATION", nearPro.o);
                    intent = a;
                } else {
                    a = is.a(this.a, P2PPayActivity.class);
                    a.putExtra("defaultRecipientId", nearPro.b);
                    if (nearPro.q.size() > 0) {
                        a.putExtra("nearProData", nearPro);
                    }
                    intent = a;
                }
                this.a.startActivity(intent);
            }
        }
    };
    private OnClickListener q = new OnClickListener(this) {
        final /* synthetic */ NearProCategoryActivity a;

        {
            this.a = r1;
        }

        public final void onClick(View view) {
            int positionForView = ((ListView) this.a.b.getRefreshableView()).getPositionForView(view) - 1;
            if (positionForView != -1) {
                Parcelable getNearProListData = new GetNearProListData();
                getNearProListData.a = this.a.h.a;
                getNearProListData.b = new ArrayList(this.a.h.b.subList(positionForView, Math.min(this.a.h.b.size(), positionForView + 1)));
                Intent a = is.a(this.a, NearProMapActivity.class);
                a.putExtra("fr.smoney.android.izly.extras.nearProMapData", getNearProListData);
                this.a.startActivity(a);
            }
        }
    };

    final class a extends BaseAdapter {
        final /* synthetic */ NearProCategoryActivity a;
        private LayoutInflater b;

        final class a {
            ImageView a;
            TextView b;
            final /* synthetic */ a c;

            a(a aVar) {
                this.c = aVar;
            }
        }

        public a(NearProCategoryActivity nearProCategoryActivity, Context context) {
            this.a = nearProCategoryActivity;
            this.b = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        public static cn a(int i) {
            return cn.b(i);
        }

        public final int getCount() {
            return cn.a();
        }

        public final /* synthetic */ Object getItem(int i) {
            return cn.b(i);
        }

        public final long getItemId(int i) {
            return (long) i;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                a aVar2 = new a(this);
                view = this.b.inflate(R.layout.listitem_nearpro_category, null);
                aVar2.a = (ImageView) view.findViewById(R.id.nearpro_cat_picto);
                aVar2.b = (TextView) view.findViewById(R.id.nearpro_cat_text);
                view.setTag(aVar2);
                aVar = aVar2;
            } else {
                aVar = (a) view.getTag();
            }
            aVar.b.setText(cn.b(i).o);
            int i2 = cn.b(i).q;
            if (i2 == -1) {
                aVar.a.setVisibility(8);
            } else {
                aVar.a.setVisibility(0);
                aVar.a.setImageResource(i2);
            }
            return view;
        }
    }

    final class b {
        TextView a;
        TextView b;
        TextView c;
        TextView d;
        Button e;
        Button f;
        ImageView g;
        final /* synthetic */ NearProCategoryActivity h;

        public b(NearProCategoryActivity nearProCategoryActivity, View view) {
            this.h = nearProCategoryActivity;
            this.a = (TextView) view.findViewById(R.id.tv_pro_item_name);
            this.c = (TextView) view.findViewById(R.id.tv_pro_item_actvity);
            this.d = (TextView) view.findViewById(R.id.tv_pro_item_distance);
            this.g = (ImageView) view.findViewById(R.id.iv_pro_item_avatar);
        }
    }

    final class c extends ArrayAdapter<NearPro> {
        final /* synthetic */ NearProCategoryActivity a;
        private LayoutInflater b;

        public c(NearProCategoryActivity nearProCategoryActivity, Context context) {
            this.a = nearProCategoryActivity;
            super(context, -1);
            this.b = nearProCategoryActivity.getLayoutInflater();
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            b bVar;
            if (view == null) {
                view = this.b.inflate(R.layout.near_pro_view_item, null);
                b bVar2 = new b(this.a, view);
                view.setTag(bVar2);
                bVar = bVar2;
            } else {
                bVar = (b) view.getTag();
            }
            NearPro nearPro = (NearPro) getItem(i);
            StringBuilder stringBuilder = new StringBuilder();
            if (nearPro.a != null) {
                stringBuilder.append(nearPro.a);
            } else {
                stringBuilder.append(bVar.h.getString(R.string.near_pro_display_name_unkonwn));
            }
            bVar.a.setText(stringBuilder.toString());
            CharSequence b = nearPro.b();
            if (TextUtils.isEmpty(b)) {
                bVar.b.setText(bVar.h.getString(R.string.near_pro_address_unknown));
            } else {
                bVar.b.setText(b);
            }
            cn a = cn.a(nearPro.e);
            if (a != null) {
                bVar.c.setText(a.o);
            } else {
                bVar.c.setText(bVar.h.getString(R.string.near_pro_activity_unknown));
            }
            bVar.d.setText(nearPro.a(bVar.h.getApplicationContext()));
            bVar.g.setImageResource(R.drawable.list_aiv_avatar_pro);
            bVar.h.a.a.displayImage(jl.a(nearPro.b), bVar.g);
            bVar.e.setOnClickListener(bVar.h.p);
            bVar.f.setOnClickListener(bVar.h.q);
            if (nearPro.c) {
                bVar.f.setVisibility(8);
                bVar.d.setVisibility(8);
                bVar.e.setText(R.string.near_pro_list_donate_action);
            } else {
                bVar.f.setVisibility(0);
                bVar.f.setEnabled(nearPro.i);
                bVar.d.setVisibility(0);
                bVar.e.setText(R.string.near_pro_list_pay_action);
            }
            return view;
        }
    }

    private void a(Location location, int i, boolean z) {
        if (location != null) {
            b(false);
            this.c.setVisibility(8);
            cl i2 = i();
            setSupportProgressBarIndeterminateVisibility(!z);
            super.a(j().a(i2.b.a, i2.b.c, location.getLatitude(), location.getLongitude(), i, 0), 213, z);
        }
    }

    private void a(GetNearProListData getNearProListData, ServerError serverError) {
        setSupportProgressBarIndeterminateVisibility(false);
        if (this.k.getSelectedTab() == this.l) {
            if (serverError != null) {
                a(serverError);
            } else if (getNearProListData == null) {
                a(hw.a(this, this));
            } else {
                this.h = getNearProListData;
                this.c.setVisibility(0);
                b(true);
                a(this.h.b);
            }
        } else if (serverError == null && getNearProListData != null) {
            this.c.setVisibility(0);
            this.h = getNearProListData;
        }
        this.b.onRefreshComplete();
    }

    private void a(ArrayList<NearPro> arrayList) {
        this.g.setNotifyOnChange(false);
        this.g.clear();
        if (arrayList.size() >= 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.g.add((NearPro) it.next());
            }
        }
        this.g.notifyDataSetChanged();
    }

    private void b(boolean z) {
        this.j = z;
        supportInvalidateOptionsMenu();
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() != 213) {
                    super.a(ieVar, bundle);
                    return;
                } else if (this.e != null) {
                    a(this.e, cn.m.p, this.h == null);
                    return;
                } else {
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
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 213) {
                    finish();
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

    public final void b_(int i) {
        cl i2 = i();
        if (i == 213) {
            a(i2.aW, i2.aX);
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 213) {
            g();
            finish();
            return;
        }
        super.d(ieVar);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(5);
        setContentView((int) R.layout.near_pro_category);
        this.k = getSupportActionBar();
        this.k.setHomeButtonEnabled(true);
        this.k.setNavigationMode(2);
        this.l = this.k.newTab();
        this.l.setText(R.string.near_pro_segmented_b_all_pro);
        this.l.setTabListener(this);
        this.m = this.k.newTab();
        this.m.setText(R.string.near_pro_segmented_b_categories_pro);
        this.m.setTabListener(this);
        this.k.addTab(this.l, 0, false);
        this.k.addTab(this.m, 1, false);
        this.k.setHomeButtonEnabled(true);
        this.k.setDisplayHomeAsUpEnabled(true);
        this.b = (PullToRefreshListView) findViewById(R.id.lv_all_pros);
        this.c = findViewById(R.id.iv_no_pro);
        this.b.setOnItemClickListener(this);
        this.b.setOnRefreshListener(this);
        this.b.setEmptyView(this.c);
        this.g = new c(this, this);
        ((ListView) this.b.getRefreshableView()).setAdapter(this.g);
        this.d = (ListView) findViewById(R.id.lv_categories);
        this.d.setOnItemClickListener(this);
        if (bundle != null) {
            this.h = (GetNearProListData) bundle.getParcelable("getNearProListData");
            this.e = (Location) bundle.getParcelable("myLocation");
        }
        this.k.setSelectedNavigationItem(0);
        if (this.h != null) {
            return;
        }
        if (this.e != null) {
            a(this.e, cn.m.p, true);
            return;
        }
        b(false);
        if (this.n == null) {
            this.n = new ProgressDialog(this);
            this.n.setTitle(R.string.near_pro_list_locate_title);
            this.n.setMessage(getString(R.string.near_pro_list_locate_message));
            this.n.setIndeterminate(true);
            this.n.setCancelable(true);
            this.n.setCanceledOnTouchOutside(false);
            this.n.setOnCancelListener(new OnCancelListener(this) {
                final /* synthetic */ NearProCategoryActivity a;

                {
                    this.a = r1;
                }

                public final void onCancel(DialogInterface dialogInterface) {
                    this.a.finish();
                }
            });
        }
        this.n.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.i = menu.add(R.string.menu_item_map);
        this.i.setIcon(R.drawable.pict_place);
        this.i.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int i2;
        Intent a;
        if (adapterView == this.b.getRefreshableView()) {
            i2 = i - 1;
            if (i2 != -1) {
                a = is.a(this, ContactDetailsActivity.class);
                a.putExtra("fr.smoney.android.izly.extras.contactId", (Parcelable) this.g.getItem(i2));
                startActivity(a);
            }
        } else if (adapterView == this.d) {
            cn a2 = a.a(i);
            if (a2.equals(cn.n)) {
                Intent a3 = is.a(this, PromotionalOfferListActivity.class);
                a3.putExtra("fr.smoney.android.izly.extras.INTENT_EXTRA_LOCATION", this.e);
                startActivity(a3);
                return;
            }
            i2 = a2.p;
            a = is.a(this, NearProListActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.INTENT_EXTRA_CATEGORY", i2);
            a.putExtra("fr.smoney.android.izly.extras.INTENT_EXTRA_LOCATION", this.e);
            startActivity(a);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.i) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (this.h != null) {
            Intent a = is.a(this, NearProMapActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.nearProMapData", this.h);
            startActivity(a);
        } else {
            Toast.makeText(this, getString(R.string.location_not_found_toast), 0).show();
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        this.i.setVisible(this.j);
        return super.onPrepareOptionsMenu(menu);
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        this.o = 0;
        if (this.e != null) {
            a(this.e, cn.m.p, false);
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.h != null) {
            bundle.putParcelable("getNearProListData", this.h);
        }
        if (this.e != null) {
            bundle.putParcelable("myLocation", this.e);
        }
    }

    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
        if (tab == this.l) {
            this.d.setVisibility(8);
            this.b.setVisibility(0);
            if (this.h != null) {
                b(true);
                a(this.h.b);
            }
        } else if (tab == this.m) {
            b(false);
            setSupportProgressBarIndeterminateVisibility(false);
            this.b.setVisibility(8);
            this.d.setVisibility(0);
            if (this.f == null) {
                this.f = new a(this, this);
                this.d.setAdapter(this.f);
            }
        }
    }

    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
