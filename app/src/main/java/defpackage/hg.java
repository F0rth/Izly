package defpackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.Bookmark;
import fr.smoney.android.izly.data.model.GetBookmarksData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.ContactDetailsActivity;
import fr.smoney.android.izly.ui.ContactsActivity;

public class hg extends aa implements OnItemClickListener, SmoneyRequestManager$a {
    private ListView e;
    private ListView f;
    private View g;
    private LayoutInflater h;
    private EditText i;
    private gm j;
    private hg$b k;
    private int l;
    private View m;
    private cl n;

    private void a(GetBookmarksData getBookmarksData, ServerError serverError) {
        this.d.setSupportProgressBarIndeterminateVisibility(false);
        if (serverError != null) {
            a(serverError);
        } else if (getBookmarksData == null) {
            a(hw.a(this.d, this));
        } else {
            n();
        }
    }

    private void c(boolean z) {
        this.d.setSupportProgressBarIndeterminateVisibility(!z);
        super.a(j().a(this.n.b.a, this.n.b.c), 105, z);
    }

    private void n() {
        if (this.n.ao != null) {
            int size = this.n.ao.b.size();
            if (size > 0) {
                this.f.setVisibility(0);
                this.g.setVisibility(8);
                this.k.clear();
                for (int i = 0; i < size; i++) {
                    this.k.add((Bookmark) this.n.ao.b.get(i));
                }
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
        switch (hg$1.a[ieVar.ordinal()]) {
            case 1:
                if (h() == 105) {
                    c(true);
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
            a(this.n.ao, this.n.ap);
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

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(false);
        b(true);
        this.n = i();
        this.e = (ListView) this.m.findViewById(R.id.lv_contacts);
        this.f = (ListView) this.m.findViewById(R.id.lv_favorites);
        this.f.setOnScrollListener(new PauseOnScrollListener(SmoneyApplication.d.a, true, false));
        this.g = this.m.findViewById(R.id.iv_favorites_empty);
        this.j = new gm(this.d);
        this.e.setAdapter(this.j);
        this.e.setFastScrollEnabled(true);
        this.e.setOnItemClickListener(this);
        this.k = new hg$b(this, this.d);
        this.k.setNotifyOnChange(true);
        this.f.setAdapter(this.k);
        this.f.setOnItemClickListener(this);
        this.e.setVisibility(8);
        n();
        this.h = this.d.getLayoutInflater();
        if (this.n.ao == null) {
            c(true);
        } else {
            n();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.l = getArguments().getInt("fr.smoney.android.izly.argumentModeContactPicker");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.m = layoutInflater.inflate(R.layout.contacts, viewGroup, false);
        this.i = (EditText) layoutInflater.inflate(R.layout.layout_contacts_searchview, viewGroup, false);
        return this.m;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Bookmark bookmark;
        Intent intent;
        if (this.l == 0) {
            if (adapterView == this.f) {
                bookmark = (Bookmark) ((hg$b) this.f.getAdapter()).getItem(i);
                intent = new Intent(this.d, ContactDetailsActivity.class);
                intent.putExtra("fr.smoney.android.izly.extras.contactId", bookmark);
                intent.putExtra("fr.smoney.android.izly.extras.mode", 0);
                startActivity(intent);
            }
        } else if (this.l == 1 && adapterView == this.f) {
            bookmark = (Bookmark) ((hg$b) this.f.getAdapter()).getItem(i);
            if (bookmark.d) {
                a(hu.a(getString(R.string.my_account_contacts_dialog_contact_blocked_title), getString(R.string.my_account_contacts_dialog_contact_blocked_message), getString(17039370)));
                return;
            }
            String str = bookmark.a;
            intent = new Intent();
            intent.putExtra("resultIntentExtrasContactId", str);
            this.d.setResult(1, intent);
            if (this.d instanceof ContactsActivity) {
                this.d.finish();
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (this.n.cw && !a(105)) {
            c(this.n.ao == null);
        }
    }
}
