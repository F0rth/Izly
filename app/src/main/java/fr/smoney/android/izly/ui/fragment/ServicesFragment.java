package fr.smoney.android.izly.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import defpackage.gx;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.ServicesMoneyFriendsActivity;
import fr.smoney.android.izly.ui.ServicesPotCommunActivity;
import fr.smoney.android.izly.ui.ServicesSynintraActivity;
import fr.smoney.android.izly.ui.ServicesWingitActivity;

import java.util.ArrayList;

public class ServicesFragment extends aa implements OnItemClickListener {
    private ListView e;

    public final class a {
        public String a = "";
        public String b = "";
        public String c = "";
        final /* synthetic */ ServicesFragment d;

        public a(ServicesFragment servicesFragment) {
            this.d = servicesFragment;
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ArrayList arrayList = new ArrayList();
        a aVar = new a(this);
        aVar.a = getString(R.string.services_pot_title);
        aVar.b = getString(R.string.services_pot_text_title);
        aVar.c = getString(R.string.services_pot_text_title_bis);
        arrayList.add(aVar);
        aVar = new a(this);
        aVar.a = getString(R.string.services_synintra_title);
        aVar.c = getString(R.string.services_synintra_text_title_bis);
        arrayList.add(aVar);
        aVar = new a(this);
        aVar.a = getString(R.string.services_money_title);
        aVar.b = getString(R.string.services_money_text);
        aVar.c = getString(R.string.services_money_text1);
        arrayList.add(aVar);
        aVar = new a(this);
        aVar.a = getString(R.string.services_wingit_title);
        aVar.c = getString(R.string.services_wingit_text);
        arrayList.add(aVar);
        this.e.setAdapter(new gx(getActivity(), arrayList));
        this.e.setOnItemClickListener(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.list_fragment_services, viewGroup, false);
        this.e = (ListView) inflate.findViewById(R.id.list_services);
        return inflate;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        switch (i) {
            case 0:
                jb.a(this.d, R.string.tracking_category_services, R.string.tracking_action_offers, R.string.tracking_label_lpc);
                startActivity(new Intent(getActivity(), ServicesPotCommunActivity.class));
                return;
            case 1:
                jb.a(this.d, R.string.tracking_category_services, R.string.tracking_action_offers, R.string.tracking_label_synintra);
                startActivity(new Intent(getActivity(), ServicesSynintraActivity.class));
                return;
            case 2:
                jb.a(this.d, R.string.tracking_category_services, R.string.tracking_action_offers, R.string.tracking_label_moneyfriends);
                startActivity(new Intent(getActivity(), ServicesMoneyFriendsActivity.class));
                return;
            case 3:
                jb.a(this.d, R.string.tracking_category_services, R.string.tracking_action_offers, R.string.tracking_label_wingit);
                startActivity(new Intent(getActivity(), ServicesWingitActivity.class));
                return;
            default:
                return;
        }
    }
}
