package fr.smoney.android.izly.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
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
import fr.smoney.android.izly.data.model.PromotionalOffer;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

import java.util.ArrayList;
import java.util.Iterator;

public class PromotionalOfferListActivity extends SmoneyABSActivity implements OnItemClickListener, OnRefreshListener<ListView>, SmoneyRequestManager$a {
    private Location b;
    private PullToRefreshListView c;
    private a d;
    private GetNearProListData e;
    private MenuItem f;
    private boolean g;
    private ProgressDialog h;
    private View i;

    final class a extends BaseAdapter {
        LayoutInflater a;
        ArrayList<PromotionalOffer> b = new ArrayList();
        final /* synthetic */ PromotionalOfferListActivity c;

        public a(PromotionalOfferListActivity promotionalOfferListActivity, Context context) {
            this.c = promotionalOfferListActivity;
            this.a = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        public final PromotionalOffer a(int i) {
            return (PromotionalOffer) this.b.get(i);
        }

        public final int getCount() {
            return this.b.size();
        }

        public final /* synthetic */ Object getItem(int i) {
            return a(i);
        }

        public final long getItemId(int i) {
            return (long) i;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            b bVar;
            if (view == null) {
                b bVar2 = new b();
                view = this.a.inflate(R.layout.layout_promo_offer, null);
                bVar2.a = (TextView) view.findViewById(R.id.pr_title);
                bVar2.b = (TextView) view.findViewById(R.id.pr_description);
                bVar2.c = (TextView) view.findViewById(R.id.pr_activity);
                bVar2.d = (ImageView) view.findViewById(R.id.pr_aiv_avatar);
                bVar2.e = view.findViewById(R.id.v_unread);
                view.setTag(bVar2);
                bVar = bVar2;
            } else {
                bVar = (b) view.getTag();
            }
            PromotionalOffer a = a(i);
            if (a != null) {
                bVar.a.setText(a.c);
                bVar.b.setText(a.d);
                if (TextUtils.isEmpty(a.i)) {
                    bVar.c.setVisibility(4);
                } else {
                    bVar.c.setVisibility(0);
                    bVar.c.setText(a.i);
                }
                if (a.m == fr.smoney.android.izly.data.model.PromotionalOffer.a.GLOBAL) {
                    bVar.d.setImageResource(R.drawable.detail_aiv_avatar_smoney);
                } else {
                    bVar.d.setImageResource(R.drawable.detail_aiv_avatar_placeholder);
                }
                bVar.f.a.a.displayImage(jl.a(a.a), bVar.d);
                if (a.b) {
                    bVar.e.setVisibility(8);
                } else {
                    bVar.e.setVisibility(0);
                }
            }
            return view;
        }
    }

    final class b {
        TextView a;
        TextView b;
        TextView c;
        ImageView d;
        View e;
        final /* synthetic */ PromotionalOfferListActivity f;

        private b(PromotionalOfferListActivity promotionalOfferListActivity) {
            this.f = promotionalOfferListActivity;
        }
    }

    private void a(Location location, int i, boolean z) {
        b(false);
        this.i.setVisibility(8);
        cl i2 = i();
        setSupportProgressBarIndeterminateVisibility(!z);
        super.a(j().a(i2.b.a, i2.b.c, location.getLatitude(), location.getLongitude(), -1, 0), 213, z);
    }

    private void a(GetNearProListData getNearProListData) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getNearProListData.c);
        if (getNearProListData.b.size() > 0) {
            Iterator it = getNearProListData.b.iterator();
            while (it.hasNext()) {
                arrayList.addAll(((NearPro) it.next()).p);
            }
        }
        a aVar = this.d;
        aVar.b = arrayList;
        aVar.notifyDataSetChanged();
    }

    private void a(GetNearProListData getNearProListData, ServerError serverError) {
        setSupportProgressBarIndeterminateVisibility(false);
        if (serverError != null) {
            a(serverError);
        } else if (getNearProListData == null) {
            a(hw.a(this, this));
        } else {
            i().bo = 0;
            this.e = getNearProListData;
            this.i.setVisibility(0);
            b(true);
            a(this.e);
        }
        this.c.onRefreshComplete();
    }

    private void b(boolean z) {
        this.g = z;
        supportInvalidateOptionsMenu();
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 213) {
                    a(this.b, -1, this.e == null);
                    return;
                } else {
                    super.a(ieVar, bundle);
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

    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 0:
            case 2:
                if (i2 == -1) {
                    a(this.b, -1, true);
                    return;
                }
                return;
            default:
                super.onActivityResult(i, i2, intent);
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(5);
        setContentView((int) R.layout.promotional_offer_list);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.c = (PullToRefreshListView) findViewById(R.id.listViewNearPro);
        this.c.setOnItemClickListener(this);
        this.c.setOnRefreshListener(this);
        this.i = findViewById(R.id.nearpro_list_empty);
        this.c.setEmptyView(this.i);
        this.d = new a(this, this);
        ((ListView) this.c.getRefreshableView()).setAdapter(this.d);
        this.b = (Location) getIntent().getParcelableExtra("fr.smoney.android.izly.extras.INTENT_EXTRA_LOCATION");
        if (bundle != null) {
            this.e = (GetNearProListData) bundle.getParcelable("getNearProListData");
            this.b = (Location) bundle.getParcelable("myLocation");
        }
        if (this.e == null) {
            this.g = false;
            if (this.b != null) {
                a(this.b, -1, true);
                return;
            }
            b(false);
            if (this.h == null) {
                this.h = new ProgressDialog(this);
                this.h.setTitle(R.string.near_pro_list_locate_title);
                this.h.setMessage(getString(R.string.near_pro_list_locate_message));
                this.h.setIndeterminate(true);
                this.h.setCancelable(true);
                this.h.setCanceledOnTouchOutside(false);
                this.h.setOnCancelListener(new OnCancelListener(this) {
                    final /* synthetic */ PromotionalOfferListActivity a;

                    {
                        this.a = r1;
                    }

                    public final void onCancel(DialogInterface dialogInterface) {
                        this.a.finish();
                    }
                });
            }
            this.h.show();
            return;
        }
        b(true);
        a(this.e);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.f = menu.add(R.string.menu_item_map);
        this.f.setIcon(R.drawable.pict_place);
        this.f.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Parcelable a = this.d.a(i - 1);
        Intent a2 = is.a(this, PromotionalOfferDetails.class);
        a2.putExtra("fr.smoney.android.izly.promo", a);
        startActivity(a2);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.f) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (this.b == null || this.e == null) {
            Toast.makeText(this, getString(R.string.location_not_found_toast), 0).show();
        } else {
            Intent a = is.a(this, NearProMapActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.nearProMapData", this.e);
            startActivityForResult(a, 2);
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        this.f.setVisible(this.g);
        return super.onPrepareOptionsMenu(menu);
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        if (this.b != null) {
            a(this.b, -1, false);
        } else {
            Toast.makeText(this, getString(R.string.location_not_found_toast), 0).show();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.e != null) {
            bundle.putParcelable("getNearProListData", this.e);
        }
        if (this.b != null) {
            bundle.putParcelable("myLocation", this.b);
        }
    }
}
