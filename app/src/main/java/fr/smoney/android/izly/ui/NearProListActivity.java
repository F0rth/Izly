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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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

public class NearProListActivity extends SmoneyABSActivity implements OnItemClickListener, OnRefreshListener<ListView>, SmoneyRequestManager$a {
    private int b = 0;
    private PullToRefreshListView c;
    private GetNearProListData d;
    private Location e;
    private int f;
    private b g;
    private MenuItem h;
    private boolean i;
    private int j;
    private ProgressDialog k;
    private View l;
    private cn m;
    private OnClickListener n = new OnClickListener(this) {
        final /* synthetic */ NearProListActivity a;

        {
            this.a = r1;
        }

        public final void onClick(View view) {
            int positionForView = ((ListView) this.a.c.getRefreshableView()).getPositionForView(view) - 1;
            if (positionForView != -1) {
                NearPro nearPro = (NearPro) this.a.g.getItem(positionForView);
                Intent a;
                if (nearPro.o != null) {
                    a = is.a(this.a, PreAuthorizePaymentActivity.class);
                    a.putExtra("INTENT_EXTRA_PRE_AUTHORIZATION", nearPro.o);
                    this.a.startActivity(a);
                    return;
                }
                a = is.a(this.a, P2PPayActivity.class);
                a.putExtra("defaultRecipientId", nearPro.b);
                a.putExtra("nearProData", nearPro);
                this.a.startActivity(a);
            }
        }
    };
    private OnClickListener o = new OnClickListener(this) {
        final /* synthetic */ NearProListActivity a;

        {
            this.a = r1;
        }

        public final void onClick(View view) {
            int positionForView = ((ListView) this.a.c.getRefreshableView()).getPositionForView(view) - 1;
            if (positionForView != -1) {
                Parcelable getNearProListData = new GetNearProListData();
                getNearProListData.a = this.a.d.a;
                getNearProListData.b = new ArrayList(this.a.d.b.subList(positionForView, Math.min(this.a.d.b.size(), positionForView + 1)));
                Intent a = is.a(this.a, NearProMapActivity.class);
                a.putExtra("fr.smoney.android.izly.extras.nearProMapData", getNearProListData);
                this.a.startActivityForResult(a, 2);
            }
        }
    };

    final class a {
        TextView a;
        TextView b;
        TextView c;
        TextView d;
        Button e;
        Button f;
        ImageView g;
        final /* synthetic */ NearProListActivity h;

        public a(NearProListActivity nearProListActivity, View view) {
            this.h = nearProListActivity;
            this.a = (TextView) view.findViewById(R.id.tv_pro_item_name);
            this.c = (TextView) view.findViewById(R.id.tv_pro_item_actvity);
            this.d = (TextView) view.findViewById(R.id.tv_pro_item_distance);
            this.g = (ImageView) view.findViewById(R.id.iv_pro_item_avatar);
            if (nearProListActivity.j == 0) {
                this.e.setOnClickListener(nearProListActivity.n);
                this.f.setOnClickListener(nearProListActivity.o);
                return;
            }
            this.e.setVisibility(8);
            this.f.setVisibility(8);
        }
    }

    final class b extends ArrayAdapter<NearPro> {
        final /* synthetic */ NearProListActivity a;
        private LayoutInflater b;

        public b(NearProListActivity nearProListActivity, Context context) {
            this.a = nearProListActivity;
            super(context, -1);
            this.b = nearProListActivity.getLayoutInflater();
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = this.b.inflate(R.layout.near_pro_view_item, null);
                a aVar2 = new a(this.a, view);
                view.setTag(aVar2);
                aVar = aVar2;
            } else {
                aVar = (a) view.getTag();
            }
            NearPro nearPro = (NearPro) getItem(i);
            StringBuilder stringBuilder = new StringBuilder();
            if (nearPro.a != null) {
                stringBuilder.append(nearPro.a);
            } else {
                stringBuilder.append(aVar.h.getString(R.string.near_pro_display_name_unkonwn));
            }
            aVar.a.setText(stringBuilder.toString());
            CharSequence b = nearPro.b();
            if (TextUtils.isEmpty(b)) {
                aVar.b.setText(aVar.h.getString(R.string.near_pro_address_unknown));
            } else {
                aVar.b.setText(b);
            }
            cn a = cn.a(nearPro.e);
            if (a != null) {
                aVar.c.setText(a.o);
            } else {
                aVar.c.setText(aVar.h.getString(R.string.near_pro_activity_unknown));
            }
            aVar.d.setText(nearPro.a(aVar.h.getApplicationContext()));
            aVar.g.setImageResource(R.drawable.list_aiv_avatar_pro);
            aVar.h.a.a.displayImage(jl.a(nearPro.b), aVar.g);
            if (aVar.h.j == 0) {
                if (nearPro.c) {
                    aVar.f.setVisibility(8);
                    aVar.d.setVisibility(8);
                    aVar.e.setText(R.string.near_pro_list_donate_action);
                } else {
                    aVar.f.setVisibility(0);
                    aVar.f.setEnabled(nearPro.i);
                    aVar.d.setVisibility(0);
                    aVar.e.setText(R.string.near_pro_list_pay_action);
                }
            }
            return view;
        }
    }

    private void a(Location location, int i, boolean z) {
        if (location != null) {
            b(false);
            this.l.setVisibility(8);
            cl i2 = i();
            setSupportProgressBarIndeterminateVisibility(!z);
            super.a(j().a(i2.b.a, i2.b.c, location.getLatitude(), location.getLongitude(), i, 0), 213, z);
        }
    }

    private void a(GetNearProListData getNearProListData, ServerError serverError) {
        setSupportProgressBarIndeterminateVisibility(false);
        if (serverError != null) {
            a(serverError);
        } else if (getNearProListData == null) {
            a(hw.a(this, this));
        } else {
            this.d = getNearProListData;
            this.l.setVisibility(0);
            b(true);
            a(this.d.b);
        }
        this.c.onRefreshComplete();
    }

    private void a(ArrayList<NearPro> arrayList) {
        this.g.setNotifyOnChange(false);
        this.g.clear();
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.g.add((NearPro) it.next());
            }
        }
        this.g.notifyDataSetChanged();
    }

    private void b(boolean z) {
        if (this.j != 0 || this.m == cn.l) {
            z = false;
        }
        this.i = z;
        supportInvalidateOptionsMenu();
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() != 213 || this.e == null) {
                    super.a(ieVar, bundle);
                    return;
                } else {
                    a(this.e, this.f, this.d == null);
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
        setContentView((int) R.layout.near_pro_list);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.c = (PullToRefreshListView) findViewById(R.id.listViewNearPro);
        this.c.setOnItemClickListener(this);
        this.c.setOnRefreshListener(this);
        this.l = findViewById(R.id.nearpro_list_empty);
        this.c.setEmptyView(this.l);
        this.g = new b(this, this);
        ((ListView) this.c.getRefreshableView()).setAdapter(this.g);
        Intent intent = getIntent();
        this.j = intent.getIntExtra("fr.smoney.android.izly.intentExtrasDisplayMode", 0);
        this.e = (Location) intent.getParcelableExtra("fr.smoney.android.izly.extras.INTENT_EXTRA_LOCATION");
        if (this.j == 0) {
            this.f = intent.getIntExtra("fr.smoney.android.izly.extras.INTENT_EXTRA_CATEGORY", 0);
            this.m = cn.a(this.f);
            if (this.m != null) {
                setTitle(this.m.o);
            }
        } else {
            setTitle(R.string.near_pro_pick_title);
            this.f = cn.m.p;
        }
        if (bundle != null) {
            this.d = (GetNearProListData) bundle.getParcelable("getNearProListData");
        }
        if (this.d != null) {
            b(true);
            a(this.d.b);
        } else if (this.e != null) {
            a(this.e, this.f, true);
        } else {
            if (this.k == null) {
                this.k = new ProgressDialog(this);
                this.k.setTitle(R.string.near_pro_list_locate_title);
                this.k.setMessage(getString(R.string.near_pro_list_locate_message));
                this.k.setIndeterminate(true);
                this.k.setCancelable(true);
                this.k.setCanceledOnTouchOutside(false);
                this.k.setOnCancelListener(new OnCancelListener(this) {
                    final /* synthetic */ NearProListActivity a;

                    {
                        this.a = r1;
                    }

                    public final void onCancel(DialogInterface dialogInterface) {
                        this.a.finish();
                    }
                });
            }
            this.k.show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.h = menu.add(R.string.menu_item_map);
        this.h.setIcon(R.drawable.pict_place);
        this.h.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int i2 = i - 1;
        if (i2 != -1 && adapterView.equals(this.c.getRefreshableView())) {
            Intent a;
            if (this.j == 0) {
                a = is.a(this, ContactDetailsActivity.class);
                a.putExtra("fr.smoney.android.izly.extras.contactId", (Parcelable) this.g.getItem(i2));
                startActivityForResult(a, 1);
                return;
            }
            a = new Intent();
            a.putExtra("resultIntentExtrasNearProId", ((NearPro) this.g.getItem(i2)).b);
            a.putExtra("nearProData", (Parcelable) this.g.getItem(i2));
            setResult(1, a);
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.h) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (this.e == null || this.d == null) {
            Toast.makeText(this, getString(R.string.location_not_found_toast), 0).show();
        } else {
            Intent a = is.a(this, NearProMapActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.nearProMapData", this.d);
            startActivityForResult(a, 2);
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        this.h.setVisible(this.i);
        return super.onPrepareOptionsMenu(menu);
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        this.b = 0;
        if (this.e != null) {
            a(this.e, this.f, false);
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.d != null) {
            bundle.putParcelable("getNearProListData", this.d);
        }
    }
}
