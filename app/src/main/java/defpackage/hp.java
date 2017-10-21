package defpackage;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.ui.CompleteAccountWrapperActivity;
import java.util.ArrayList;

public class hp extends aa {
    private static final String i = hp.class.getSimpleName();
    hp$a e;
    public hp$b f;
    public int g = 1;
    ArrayList<hp$c> h;
    private final String j = "savedStateSelectedItem";
    private ListView k;
    private TextView l;
    private cl m;
    private SmoneyRequestManager n;
    private hp$d o;
    private ge p;
    private boolean q;
    private boolean r;

    static /* synthetic */ void h(hp hpVar) {
        Intent intent = new Intent(hpVar.d, CompleteAccountWrapperActivity.class);
        intent.putExtra("fr.smoney.android.izly.extras.isADirectCall", true);
        hpVar.startActivity(intent);
    }

    public static hp n() {
        return new hp();
    }

    public final void l() {
        super.l();
        if (!this.q) {
            this.q = true;
        }
        this.r = true;
    }

    public final void m() {
        super.m();
        if (!this.q) {
            this.q = true;
        }
        this.r = false;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.n = j();
        this.m = i();
        this.p = new ge(this.d);
        this.h = new ArrayList();
        this.h.add(new hp$c(this, 0));
        this.h.add(new hp$c(this, 1, R.drawable.icon_accueil, R.string.menu_entries_home));
        this.h.add(new hp$c(this, 2, R.drawable.icon_historique, R.string.menu_entries_transctions_list));
        this.h.add(new hp$c(this, 3, R.drawable.icon_envoyer_demander, R.string.menu_send_ask_money));
        this.h.add(new hp$c(this, 4, R.drawable.icon_demandes, R.string.menu_pay_request_received));
        this.h.add(new hp$c(this, 5, R.drawable.icon_moneyout, R.string.menu_entries_money_out));
        this.h.add(new hp$c(this, 6, R.drawable.icon_pot_commun, R.string.menu_entries_pot_commun));
        this.h.add(new hp$c(this, 8, R.drawable.icon_moneyfriends, R.string.menu_entries_money_friends));
        this.h.add(new hp$c(this, 7, R.drawable.icon_wingit, R.string.menu_entries_wingit));
        this.h.add(new hp$c(this, 9, R.drawable.icon_contacts, R.string.menu_entries_contacts));
        this.h.add(new hp$c(this, 10, R.drawable.icon_parametres, R.string.menu_entries_header_params));
        this.h.add(new hp$c(this, 11, R.drawable.icon_aide, R.string.menu_entries_faq));
        this.h.add(new hp$c(this, 12, R.drawable.icon_deconnecter, R.string.menu_entries_disconnect));
        this.f = new hp$b(this, this.d);
        if (this.r && i().b != null) {
            if (i().b.a()) {
                if (i().b.E == 1) {
                    this.l.setText(Html.fromHtml(getString(R.string.menu_complete_subscription_cb)));
                } else {
                    this.l.setText(Html.fromHtml(getString(R.string.menu_complete_subscription)));
                }
                if (this.k.getHeaderViewsCount() == 0) {
                    this.k.addHeaderView(this.l);
                }
            } else if (this.k.getHeaderViewsCount() > 0) {
                this.k.removeHeaderView(this.l);
            }
        }
        this.k.setAdapter(this.f);
        this.k.setOnItemClickListener(this.f);
        try {
            this.e = (hp$a) this.d;
            if (bundle != null) {
                this.g = bundle.getInt("savedStateSelectedItem");
                hp$b hp_b = this.f;
                if (bundle != null) {
                    hp_b.a = bundle.getInt("fr.smoney.android.izly.SlidingMenuSelectedPosition", -1);
                    return;
                }
                return;
            }
            this.e.b(((hp$c) this.h.get(this.g)).d);
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement DisplayContentListener");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_sliding_menu, null);
        this.k = (ListView) inflate.findViewById(16908298);
        this.l = (TextView) layoutInflater.inflate(R.layout.sliding_menu_complete_subscription, null);
        return inflate;
    }

    public void onPause() {
        super.onPause();
        this.d.unregisterReceiver(this.o);
        this.o = null;
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_UPDATER");
        this.o = new hp$d();
        this.d.registerReceiver(this.o, intentFilter);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("savedStateSelectedItem", Integer.valueOf(this.g));
        hp$b hp_b = this.f;
        if (bundle != null) {
            bundle.putInt("fr.smoney.android.izly.SlidingMenuSelectedPosition", hp_b.a);
        }
    }
}
