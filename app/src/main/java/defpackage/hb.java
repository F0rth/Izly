package defpackage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.ui.ContactDetailsActivity;
import fr.smoney.android.izly.ui.ContactsActivity;
import java.util.List;

public class hb extends aa implements OnItemClickListener, if$a {
    private ListView e;
    private ListView f;
    private View g;
    private LayoutInflater h;
    private EditText i;
    private gm j;
    private int k;
    private boolean l = false;
    private List<String> m;
    private View n;

    private void a(String str) {
        Intent intent = new Intent();
        intent.putExtra("resultIntentExtrasContactId", str);
        this.d.setResult(1, intent);
        if (this.d instanceof ContactsActivity) {
            this.d.finish();
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (hb$1.a[ieVar.ordinal()]) {
            case 1:
                super.a(ieVar, bundle);
                return;
            case 2:
                a((String) this.m.get(bundle.getInt("Data.SelectItem")));
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    protected final void b() {
        super.b();
        this.d.getSupportActionBar().setHomeButtonEnabled(true);
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
        this.e = (ListView) this.n.findViewById(R.id.lv_contacts);
        this.f = (ListView) this.n.findViewById(R.id.lv_favorites);
        this.f.setOnScrollListener(new PauseOnScrollListener(SmoneyApplication.d.a, true, false));
        this.g = this.n.findViewById(R.id.iv_favorites_empty);
        this.j = new gm(this.d);
        this.e.setAdapter(this.j);
        this.e.setFastScrollEnabled(true);
        this.e.setOnItemClickListener(this);
        this.e.setVisibility(0);
        this.f.setVisibility(8);
        this.g.setVisibility(8);
        this.h = this.d.getLayoutInflater();
        new hb$a().execute(new Void[0]);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.k = arguments.getInt("fr.smoney.android.izly.argumentModeContactPicker");
        this.l = arguments.getBoolean("fr.smoney.android.izly.intentExtrasModeTypeContact", false);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(R.string.contacts_search).setIcon(R.drawable.pict_search).setActionView(this.i).setShowAsAction(9);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.n = layoutInflater.inflate(R.layout.contacts, viewGroup, false);
        this.i = (EditText) layoutInflater.inflate(R.layout.layout_contacts_searchview, viewGroup, false);
        return this.n;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.k == 0) {
            if (adapterView == this.e) {
                Parcelable a = ((gm) this.e.getAdapter()).a(i);
                Intent intent = new Intent(this.d, ContactDetailsActivity.class);
                intent.putExtra("fr.smoney.android.izly.extras.contactId", a);
                intent.putExtra("fr.smoney.android.izly.extras.mode", 1);
                startActivity(intent);
            }
        } else if (this.k == 1 && adapterView == this.e) {
            Contact a2 = ((gm) this.e.getAdapter()).a(i);
            new hb$b(this, this.d).execute(new String[]{a2.a});
        }
    }

    public void onResume() {
        super.onResume();
    }
}
