package fr.smoney.android.izly.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.is;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.P2PGetMultActivity;
import fr.smoney.android.izly.ui.P2PGetSimpleActivity;
import fr.smoney.android.izly.ui.P2PPayActivity;
import fr.smoney.android.izly.ui.adapter.IzlyListAdapterHelper;

public class AskSendMoneyFragment extends aa implements OnItemClickListener {
    @Bind({2131755299})
    ListView mListView;

    static final /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[ci.values().length];

        static {
            try {
                a[ci.c.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[ci.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[ci.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static AskSendMoneyFragment n() {
        return new AskSendMoneyFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.ask_send_money_fragment, viewGroup, false);
        ButterKnife.bind(this, inflate);
        this.mListView.setAdapter(new IzlyListAdapterHelper(this.d, ci.values()));
        this.mListView.setOnItemClickListener(this);
        jb.a(getActivity(), R.string.screen_name_ask_send_money_activity);
        return inflate;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Intent intent = null;
        switch (AnonymousClass1.a[ci.values()[i].ordinal()]) {
            case 1:
                intent = is.a(this.d, P2PGetMultActivity.class);
                break;
            case 2:
                intent = is.a(this.d, P2PGetSimpleActivity.class);
                break;
            case 3:
                intent = is.a(this.d, P2PPayActivity.class);
                break;
        }
        if (intent != null) {
            a(intent, true);
        }
    }
}
