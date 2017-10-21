package fr.smoney.android.izly.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.gr;
import defpackage.hw;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.CounterFamily;
import fr.smoney.android.izly.data.model.CounterListData;
import fr.smoney.android.izly.data.model.LoginLightData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.CounterDetailActivity;

import java.util.List;

public class MyCountersFragment extends aa implements SmoneyRequestManager$a {
    gr e;
    private cl f;
    @Bind({2131755299})
    ListView myCounterFamilyList;

    private void a(CounterListData counterListData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (counterListData == null) {
            a(hw.a(this.d, this));
        } else {
            List list = counterListData.a;
            if (list != null && list.size() > 0) {
                this.e = new gr(getActivity(), list);
                this.myCounterFamilyList.setAdapter(this.e);
                this.myCounterFamilyList.setOnItemClickListener(new OnItemClickListener(this) {
                    final /* synthetic */ MyCountersFragment a;

                    {
                        this.a = r1;
                    }

                    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        CounterFamily a = this.a.e.a(i);
                        Intent intent = new Intent(this.a.getActivity(), CounterDetailActivity.class);
                        intent.putExtra("extra.counter.id", a.a);
                        intent.putExtra("extra.counter.name", a.b);
                        this.a.startActivity(intent);
                    }
                });
            }
        }
    }

    private void a(LoginLightData loginLightData, ServerError serverError) {
        if (serverError != null) {
            a(serverError, true);
        } else if (loginLightData == null) {
            a(hw.a(this.d, this));
        } else {
            super.a(j().e(this.f.b.a, this.f.b.c), 261, true);
        }
    }

    public final void a(Parcelable parcelable, ServerError serverError) {
        a((CounterListData) parcelable, serverError);
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        super.a(i, i2, i3, bundle);
        ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
        switch (i2) {
            case 232:
                a(this.f.bJ, this.f.bK);
                return;
            case 261:
                a((CounterListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMyCounterList"), serverError);
                return;
            default:
                return;
        }
    }

    public final void b_(int i) {
        switch (i) {
            case 232:
                a(this.f.bJ, this.f.bK);
                return;
            case 261:
                a(this.f.bB, this.f.bC);
                return;
            default:
                return;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f = i();
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_counter_list, viewGroup, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }
}
