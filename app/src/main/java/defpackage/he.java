package defpackage;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.Bookmark;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.GetBookmarksData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.ContactDetailsActivity;
import fr.smoney.android.izly.ui.ContactsActivity;
import java.util.List;

public final class he extends aa implements TabListener, OnItemClickListener, SmoneyRequestManager$a {
    private ListView e;
    private ListView f;
    private LinearLayout g;
    private LayoutInflater h;
    private EditText i;
    private gm j;
    private he$d k;
    private int l;
    private boolean m = false;
    private List<String> n;
    private View o;
    private cl p;

    public static he a(int i, boolean z) {
        he heVar = new he();
        Bundle bundle = new Bundle();
        bundle.putInt("fr.smoney.android.izly.argumentModeContactPicker", i);
        bundle.putBoolean("fr.smoney.android.izly.intentExtrasModeTypeContact", z);
        heVar.setArguments(bundle);
        return heVar;
    }

    private void a(GetBookmarksData getBookmarksData, ServerError serverError) {
        this.d.setSupportProgressBarIndeterminateVisibility(false);
        if (serverError != null) {
            a(serverError);
        } else if (getBookmarksData == null) {
            a(hw.a(this.d, this));
        } else {
            o();
        }
    }

    private void a(String str) {
        Intent intent = new Intent();
        intent.putExtra("resultIntentExtrasContactId", str);
        this.d.setResult(1, intent);
        this.d.finish();
    }

    private void b(int i) {
        Editor edit = this.d.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
        edit.putInt("sharedPrefMyAccountContactsActiveTab", i);
        edit.commit();
    }

    private void c(boolean z) {
        this.d.setSupportProgressBarIndeterminateVisibility(!z);
        super.a(j().a(this.p.b.a, this.p.b.c), 105, z);
    }

    private void n() {
        this.e.setVisibility(0);
        this.f.setVisibility(8);
        this.g.setVisibility(8);
        b(1);
    }

    private void o() {
        if (this.p.ao != null) {
            int size = this.p.ao.b.size();
            if (size > 0) {
                this.f.setVisibility(0);
                this.g.setVisibility(8);
                this.k.clear();
                for (int i = 0; i < size; i++) {
                    this.k.add((Bookmark) this.p.ao.b.get(i));
                }
                this.k.getCount();
                return;
            }
            this.f.setVisibility(8);
            this.g.setVisibility(0);
            return;
        }
        this.f.setVisibility(8);
        this.g.setVisibility(8);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (he$1.a[ieVar.ordinal()]) {
            case 1:
                if (h() == 105) {
                    c(true);
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case 2:
                a((String) this.n.get(bundle.getInt("Data.SelectItem")));
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 105) {
                a((GetBookmarksData) bundle.getParcelable("fr.smoney.android.izly.extras.getBookmarksData"), serverError);
            }
        }
    }

    protected final void b() {
        super.b();
        this.d.getSupportActionBar().setHomeButtonEnabled(true);
    }

    public final void b_(int i) {
        if (i == 105) {
            a(this.p.ao, this.p.ap);
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 105) {
            g();
            if (this.d instanceof ContactsActivity) {
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
        return getString(R.string.my_account_contacts_title);
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(false);
        b(true);
        this.p = i();
        this.e = (ListView) this.o.findViewById(R.id.lv_contacts);
        this.f = (ListView) this.o.findViewById(R.id.lv_favorites);
        this.f.setOnScrollListener(new PauseOnScrollListener(SmoneyApplication.d.a, true, false));
        this.g = (LinearLayout) this.o.findViewById(R.id.iv_favorites_empty);
        this.j = new gm(this.d);
        this.e.setAdapter(this.j);
        this.e.setFastScrollEnabled(true);
        this.e.setOnItemClickListener(this);
        this.k = new he$d(this, this.d);
        this.k.setNotifyOnChange(true);
        this.f.setAdapter(this.k);
        this.f.setOnItemClickListener(this);
        int i = this.d.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).getInt("sharedPrefMyAccountContactsActiveTab", 0);
        if (i == 0) {
            this.e.setVisibility(8);
            o();
            b(0);
        } else if (i == 1) {
            n();
        }
        this.h = this.d.getLayoutInflater();
        if (this.m) {
            new he$a().execute(new Void[0]);
        } else if (this.l == 1) {
            new he$b().execute(new Void[0]);
        } else {
            new he$a().execute(new Void[0]);
        }
        if (this.m) {
            n();
        } else if (this.p.ao == null) {
            c(true);
        } else {
            o();
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.l = arguments.getInt("fr.smoney.android.izly.argumentModeContactPicker");
        this.m = arguments.getBoolean("fr.smoney.android.izly.intentExtrasModeTypeContact", false);
    }

    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(R.string.contacts_search).setIcon(R.drawable.pict_search).setActionView(this.i).setShowAsAction(9);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.o = layoutInflater.inflate(R.layout.contacts, viewGroup, false);
        this.i = (EditText) layoutInflater.inflate(R.layout.layout_contacts_searchview, viewGroup, false);
        return this.o;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Bookmark bookmark;
        if (this.l == 0) {
            Intent intent;
            if (adapterView == this.f) {
                bookmark = (Bookmark) ((he$d) this.f.getAdapter()).getItem(i);
                intent = new Intent(this.d, ContactDetailsActivity.class);
                intent.putExtra("fr.smoney.android.izly.extras.contactId", bookmark);
                intent.putExtra("fr.smoney.android.izly.extras.mode", 0);
                startActivity(intent);
            } else if (adapterView == this.e) {
                Parcelable a = ((gm) this.e.getAdapter()).a(i);
                intent = new Intent(this.d, ContactDetailsActivity.class);
                intent.putExtra("fr.smoney.android.izly.extras.contactId", a);
                intent.putExtra("fr.smoney.android.izly.extras.mode", 1);
                startActivity(intent);
            }
        } else if (this.l != 1) {
        } else {
            if (adapterView == this.f) {
                bookmark = (Bookmark) ((he$d) this.f.getAdapter()).getItem(i);
                if (bookmark.d) {
                    a(hu.a(getString(R.string.my_account_contacts_dialog_contact_blocked_title), getString(R.string.my_account_contacts_dialog_contact_blocked_message), getString(17039370)));
                } else {
                    a(bookmark.a);
                }
            } else if (adapterView == this.e) {
                Contact a2 = ((gm) this.e.getAdapter()).a(i);
                new he$e(this, this.d).execute(new String[]{a2.a});
            }
        }
    }

    public final void onResume() {
        super.onResume();
        if (this.p.cw && !a(105)) {
            c(this.p.ao == null);
        }
    }

    public final void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public final void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public final void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
