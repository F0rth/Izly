package defpackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GoodDealsArray;
import fr.smoney.android.izly.data.model.GoodDealsData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.DealsActivity;
import java.util.ArrayList;

public class hh extends aa implements OnItemClickListener, SmoneyRequestManager$a {
    private String e;
    private ListView f;
    private cl g;
    private View h;
    private View i;
    private View j;

    private void a(GoodDealsArray goodDealsArray, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
            this.i.setVisibility(0);
            this.j.setVisibility(0);
            this.h.setVisibility(0);
        } else if (goodDealsArray == null) {
            this.i.setVisibility(0);
            this.j.setVisibility(0);
            this.h.setVisibility(0);
        } else {
            this.i.setVisibility(8);
            ArrayList arrayList = goodDealsArray.a;
            if (arrayList != null && arrayList.size() > 0) {
                this.f.setAdapter(new gv(getActivity(), arrayList));
                this.f.setOnItemClickListener(this);
            }
        }
        getActivity().findViewById(R.id.loadingPanelListGoodDeals).setVisibility(8);
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            a((GoodDealsArray) bundle.getParcelable("fr.smoney.android.izly.extras.GoodDealsData"), (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError"));
        }
    }

    public final void b_(int i) {
        a(this.g.cd, this.g.ce);
    }

    public void onActivityCreated(Bundle bundle) {
        int keyAt;
        super.onActivityCreated(bundle);
        this.g = i();
        SmoneyRequestManager j = j();
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            if (((Intent) j.b.valueAt(i)).getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 270) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent = new Intent(j.c, SmoneyService.class);
        intent.putExtra("com.foxykeep.datadroid.extras.workerType", 270);
        intent.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        j.c.startService(intent);
        j.b.append(keyAt, intent);
        j.f.cc = null;
        super.a(keyAt, 270, false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.list_fragment_good_deals, viewGroup, false);
        this.f = (ListView) inflate.findViewById(R.id.list_view_good_deals);
        this.h = inflate.findViewById(R.id.iv_place_holder_no_news);
        this.i = inflate.findViewById(R.id.list_empty_view);
        this.j = inflate.findViewById(R.id.tv_list_empty_view);
        return inflate;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.e = "http://izly.maximiles.com/api/deals/" + ((GoodDealsData) this.g.cd.a.get(i)).a;
        Intent intent = new Intent(getActivity(), DealsActivity.class);
        intent.putExtra(getString(R.string.services_bons_plans_extra_url), this.e);
        startActivity(intent);
    }
}
