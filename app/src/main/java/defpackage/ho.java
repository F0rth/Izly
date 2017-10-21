package defpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetBilendiBannerData;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.CompleteAccountWrapperActivity;
import fr.smoney.android.izly.ui.MoneyInActivity;
import fr.smoney.android.izly.ui.adapter.PaymentListAdapter;
import org.spongycastle.asn1.x509.DisplayText;

public final class ho extends aa implements OnItemClickListener, SmoneyRequestManager$a {
    private ListView e;
    private WebView f;
    private it g;
    private cl h;

    private void a(GetBilendiBannerData getBilendiBannerData) {
        if (getBilendiBannerData == null || getBilendiBannerData.b != DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE) {
            this.f.setVisibility(8);
            return;
        }
        this.f.loadData(getBilendiBannerData.a, "text/html; charset=utf-8", null);
        this.f.setWebViewClient(new ho$1(this));
    }

    static /* synthetic */ void a(ho hoVar) {
        int a = it.a(hoVar.d);
        int i = (int) (((float) a) / 6.4f);
        LayoutParams layoutParams = hoVar.f.getLayoutParams();
        hoVar.f.setScrollbarFadingEnabled(true);
        if (layoutParams != null) {
            layoutParams.width = a;
            layoutParams.height = i;
            hoVar.f.setLayoutParams(layoutParams);
        }
        hoVar.f.setOnTouchListener(new ho$2(hoVar));
        hoVar.f.setVisibility(0);
    }

    public static ho n() {
        return new ho();
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 273) {
                a((GetBilendiBannerData) bundle.getParcelable("fr.smoney.android.izly.extra.BilendiBannerData"));
            }
        }
    }

    public final void b_(int i) {
        if (i == 273) {
            a(this.h.ch);
        }
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.h = i();
        this.g = new it();
        this.f = (WebView) this.d.findViewById(R.id.bilendiAdserverView);
    }

    public final View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.payment_list_fragment, viewGroup, false);
        this.e = (ListView) inflate.findViewById(R.id.listView);
        this.e.setAdapter(new PaymentListAdapter(this.d));
        this.e.setOnItemClickListener(this);
        jb.a(this.d, R.string.screen_name_money_in_activity);
        return inflate;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Intent intent = null;
        ch chVar = ch.values()[i];
        if (i().b != null) {
            if (!i().b.a()) {
                Intent a = is.a(this.d, MoneyInActivity.class);
                switch (ho$3.a[chVar.ordinal()]) {
                    case 1:
                        a.putExtra("MONEY_IN_MODE", 1);
                        intent = a;
                        break;
                    case 2:
                        a.putExtra("MONEY_IN_MODE", 2);
                        intent = a;
                        break;
                    case 3:
                        a.putExtra("MONEY_IN_MODE", 3);
                        intent = a;
                        break;
                    case 4:
                        a.putExtra("MONEY_IN_MODE", 5);
                        intent = a;
                        break;
                    default:
                        break;
                }
            }
            intent = is.a(this.d, CompleteAccountWrapperActivity.class);
            intent.putExtra("fr.smoney.android.izly.extras.activityToStart", 1);
        }
        if (intent != null) {
            a(intent, true);
        }
    }

    public final void onPause() {
        super.onPause();
    }

    public final void onResume() {
        super.onResume();
        if (this.h.b != null && this.h.b.t != null && this.h.b.u != null && !a(273)) {
            super.a(j().a(String.valueOf(this.g.a(this.d, 320)), String.valueOf(this.g.a(this.d, 50)), getString(R.string.profilage_bancaire_reload), this.h.b.t, this.h.b.u, it.a(this.h.b.A, this.d)), 273, true);
        }
    }
}
