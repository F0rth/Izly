package defpackage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetAttachmentData;
import fr.smoney.android.izly.data.model.MakeMoneyDemandRelaunchData;
import fr.smoney.android.izly.data.model.MoneyDemandRelaunch;
import fr.smoney.android.izly.data.model.P2PGet;
import fr.smoney.android.izly.data.model.P2PGetMult;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.widget.DetailTwoText;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import org.spongycastle.crypto.tls.CipherSuite;

public final class hn extends aa implements OnClickListener, SmoneyRequestManager$a {
    private DetailTwoText e;
    private DetailTwoText f;
    private LinearLayout g;
    private ImageView h;
    private ImageView i;
    private TextView j;
    private TextView k;
    private Button l;
    private Button m;
    private Button n;
    private TextView o;
    private TextView p;
    private TextView q;
    private TextView r;
    private String s;
    private P2PGetMult t;
    private P2PGet u;
    private int v;
    private ge w;

    public static hn a(P2PGetMult p2PGetMult) {
        hn hnVar = new hn();
        Bundle bundle = new Bundle();
        bundle.putParcelable("fr.smoney.android.izly.extras.p2pGetMultDetailsData", p2PGetMult);
        hnVar.setArguments(bundle);
        return hnVar;
    }

    private String a(double d) {
        return String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.s});
    }

    private String a(ArrayList<MoneyDemandRelaunch> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            MoneyDemandRelaunch moneyDemandRelaunch = (MoneyDemandRelaunch) it.next();
            if (moneyDemandRelaunch.e) {
                stringBuilder.append(getString(R.string.p2p_get_mult_details_dialog_relaunch_result_message, new Object[]{moneyDemandRelaunch.b}));
                stringBuilder.append("\n\n");
            } else if (!(moneyDemandRelaunch.f == -1 || moneyDemandRelaunch.g == null)) {
                stringBuilder.append(getString(R.string.p2p_get_mult_details_dialog_relaunch_result_error_message, new Object[]{moneyDemandRelaunch.b, moneyDemandRelaunch.g}));
                stringBuilder.append("\n\n");
            }
        }
        return stringBuilder.toString();
    }

    private void a(long j, int i) {
        cl i2 = i();
        this.v = i;
        SmoneyRequestManager j2 = j();
        String str = i2.b.a;
        String str2 = i2.b.c;
        long j3 = this.t.c;
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            arrayList.add(Long.valueOf(j));
        } else {
            Iterator it = this.t.b.iterator();
            while (it.hasNext()) {
                P2PGet p2PGet = (P2PGet) it.next();
                if (p2PGet.m == 0 && p2PGet.p == null) {
                    arrayList.add(Long.valueOf(p2PGet.a));
                }
            }
        }
        int size = arrayList.size();
        long[] jArr = new long[size];
        for (int i3 = 0; i3 < size; i3++) {
            jArr[i3] = ((Long) arrayList.get(i3)).longValue();
        }
        super.a(j2.a(str, str2, j3, jArr), 219, true);
    }

    private void a(long j, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (j == 0) {
            a(hw.a(getActivity(), this));
        } else {
            this.v = -1;
            if (this.t.c == j) {
                Iterator it = this.t.b.iterator();
                while (it.hasNext()) {
                    P2PGet p2PGet = (P2PGet) it.next();
                    if (p2PGet.m == 0) {
                        p2PGet.m = 1;
                    }
                }
            } else {
                this.u.m = 1;
            }
            n();
        }
    }

    private void a(GetAttachmentData getAttachmentData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getAttachmentData == null) {
            a(hw.a(getActivity(), this));
        } else if (getAttachmentData.c) {
            a(hu.a(getString(R.string.dialog_error_title), getString(R.string.p2p_pay_request_attachement_invalid), getString(17039370)));
        } else {
            iw.b(getActivity());
        }
    }

    private void a(MakeMoneyDemandRelaunchData makeMoneyDemandRelaunchData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (makeMoneyDemandRelaunchData == null) {
            a(hw.a(getActivity(), this));
        } else {
            this.v = -1;
            ArrayList arrayList = new ArrayList(makeMoneyDemandRelaunchData.c);
            this.t.c = makeMoneyDemandRelaunchData.a;
            Iterator it = this.t.b.iterator();
            while (it.hasNext()) {
                P2PGet p2PGet = (P2PGet) it.next();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    MoneyDemandRelaunch moneyDemandRelaunch = (MoneyDemandRelaunch) it2.next();
                    if (moneyDemandRelaunch.b.equals(p2PGet.c)) {
                        p2PGet.a = moneyDemandRelaunch.a;
                        p2PGet.p = moneyDemandRelaunch.d;
                        arrayList.remove(moneyDemandRelaunch);
                        break;
                    }
                }
            }
            n();
            if (this.v == 0) {
                if (makeMoneyDemandRelaunchData.b) {
                    a(hu.a(getString(R.string.p2p_get_mult_details_dialog_relaunch_result_title), getString(R.string.p2p_get_mult_details_dialog_relaunch_result_message, new Object[]{jf.a(this.u.f, this.u.g, this.u.c)}), getString(17039370)));
                    return;
                }
                a(hu.a(getString(R.string.p2p_get_mult_details_dialog_relaunch_result_title), a(i().bk.c), getString(17039370)));
            } else if (this.v != 1) {
            } else {
                if (makeMoneyDemandRelaunchData.b) {
                    a(hu.a(getString(R.string.p2p_get_mult_details_dialog_relaunch_all_result_title), getString(R.string.p2p_get_mult_details_dialog_relaunch_all_result_message), getString(17039370)));
                } else {
                    a(hu.a(getString(R.string.p2p_get_mult_details_dialog_relaunch_all_result_title), a(i().bk.c), getString(17039370)));
                }
            }
        }
    }

    private void a(P2PGet p2PGet) {
        CharSequence charSequence;
        this.w.a.displayImage(jl.a(p2PGet.c), this.i);
        String a;
        Object obj;
        if (p2PGet.d) {
            a = jf.a(p2PGet.f, p2PGet.g, p2PGet.c);
            this.q.setText(jf.a(p2PGet.c));
            if (jf.a(p2PGet.f, p2PGet.g)) {
                this.q.setVisibility(0);
                obj = a;
            } else {
                this.q.setVisibility(8);
                charSequence = a;
            }
        } else {
            a = jf.a(p2PGet.c);
            this.q.setVisibility(4);
            obj = a;
        }
        this.p.setText(charSequence);
        ViewGroup viewGroup = (ViewGroup) this.r.getParent();
        int indexOfChild = viewGroup.indexOfChild(this.r);
        viewGroup.removeView(this.r);
        switch (p2PGet.m) {
            case 0:
                this.r = in.a(this.d, il.b);
                this.r.setText(R.string.p2p_get_status_tobepaid);
                break;
            case 1:
                this.r = in.a(this.d, il.d);
                this.r.setText(R.string.p2p_get_status_canceled);
                break;
            case 2:
                this.r = in.a(this.d, il.c);
                this.r.setText(R.string.p2p_get_status_refused);
                break;
            case 3:
                this.r = in.a(this.d, il.a);
                this.r.setText(getString(R.string.p2p_get_status_paid, new Object[]{charSequence, a(p2PGet.h)}));
                break;
        }
        viewGroup.addView(this.r, indexOfChild);
        switch (p2PGet.m) {
            case 0:
                this.m.setEnabled(true);
                if (p2PGet.p == null) {
                    this.l.setEnabled(true);
                    break;
                } else {
                    this.l.setEnabled(false);
                    break;
                }
            default:
                this.m.setVisibility(8);
                this.l.setVisibility(8);
                break;
        }
        if (p2PGet.m != 0 || p2PGet.p == null) {
            this.l.setText(getString(R.string.p2p_get_mult_details_b_relaunch_request));
            return;
        }
        this.l.setText(getString(R.string.p2p_get_mult_details_b_relaunch_hours_request, new Object[]{p2PGet.p}));
    }

    private void b(long j, int i) {
        cl i2 = i();
        this.v = i;
        super.a(j().a(i2.b.a, i2.b.c, j, i), 63, true);
    }

    private void n() {
        a(this.u);
    }

    private void o() {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, this.t.n), CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (hn$1.a[ieVar.ordinal()]) {
            case 1:
                int h = h();
                if (h == 219) {
                    if (this.v == 1) {
                        a(this.t.c, this.v);
                        return;
                    } else if (this.v == 0) {
                        a(this.u.a, this.v);
                        return;
                    } else {
                        return;
                    }
                } else if (h == 63) {
                    if (this.v == 1) {
                        b(this.t.c, this.v);
                        return;
                    } else if (this.v == 0) {
                        b(this.u.a, this.v);
                        return;
                    } else {
                        return;
                    }
                } else if (h == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
                    o();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case 2:
                b(this.u.a, 0);
                return;
            case 3:
                a(this.u.a, 0);
                return;
            case 4:
                b(this.t.c, 1);
                return;
            case 5:
                a(this.t.c, 1);
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 219) {
                a((MakeMoneyDemandRelaunchData) bundle.getParcelable("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunch"), serverError);
            } else if (i2 == 63) {
                a(bundle.getLong("fr.smoney.android.izly.extras.p2pGetCancelIdToCancel"), serverError);
            } else if (i2 == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
                a((GetAttachmentData) bundle.getParcelable("fr.smoney.android.izly.extras.getAttachementData"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        int h = h();
        switch (hn$1.a[ieVar.ordinal()]) {
            case 1:
                if (h == 227) {
                    getActivity().finish();
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
        if (i == 219) {
            a(i2.bk, i2.bl);
        } else if (i == 63) {
            a(i2.K, i2.L);
        } else if (i == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
            a(i2.aA, i2.aB);
        }
    }

    public final void c(ie ieVar) {
        int h = h();
        switch (hn$1.a[ieVar.ordinal()]) {
            case 6:
                if (h == 227) {
                    getActivity().finish();
                    return;
                }
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        int h = h();
        if (ieVar == ie.ProgressType && h == 227) {
            g();
            getActivity().finish();
            return;
        }
        super.d(ieVar);
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle != null) {
            P2PGetMult p2PGetMult = (P2PGetMult) bundle.getParcelable("savedStateP2PGetMult");
            if (p2PGetMult != null) {
                this.t = p2PGetMult;
            }
        }
        if (this.t != null) {
            this.s = Currency.getInstance(i().b.j).getSymbol();
            this.u = (P2PGet) this.t.b.get(0);
            if (this.t != null) {
                cl i = i();
                this.o.setText(R.string.p2p_get_mult_recipient);
                this.l.setOnClickListener(this);
                this.m.setOnClickListener(this);
                this.f.setRightText(jk.b(getActivity(), this.t.e));
                if (this.t.j == null || this.t.j.equals("")) {
                    this.g.setVisibility(8);
                } else {
                    this.j.setText(jk.b(getActivity(), new Date().getTime()));
                    this.k.setText(this.t.j);
                    this.w.a.displayImage(jl.a(i.b.a), this.h);
                    this.g.setVisibility(0);
                }
                if (TextUtils.isEmpty(this.t.n)) {
                    this.n.setVisibility(8);
                } else {
                    this.n.setOnClickListener(this);
                    this.n.setVisibility(0);
                    this.n.setText(this.t.o);
                }
                if (this.t.f > 0.0d) {
                    this.e.setRightText(a(this.t.f));
                } else {
                    this.e.setRightText((int) R.string.p2p_get_mult_tv_without_amount);
                }
                a(this.u);
            }
        }
    }

    public final void onClick(View view) {
        if (view == this.m) {
            a(ht.a(getString(R.string.p2p_get_mult_details_dialog_cancel_confirm_title), getString(R.string.p2p_get_mult_details_dialog_cancel_confirm_message, new Object[]{jf.a(this.u.f, this.u.g, this.u.c)}), getString(17039379), getString(17039369), this, ie.P2PGetSimpleCancelType));
        } else if (view == this.l) {
            a(ht.a(getString(R.string.p2p_get_mult_details_dialog_relaunch_confirm_title), getString(R.string.p2p_get_mult_details_dialog_relaunch_confirm_message, new Object[]{jf.a(this.u.f, this.u.g, this.u.c)}), getString(17039379), getString(17039369), this, ie.P2PGetSimpleRelaunchType));
        } else if (view == this.n) {
            o();
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.t = (P2PGetMult) getArguments().getParcelable("fr.smoney.android.izly.extras.p2pGetMultDetailsData");
        this.w = new ge(this.d);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.p2p_get_simple_details, null);
        this.o = (TextView) inflate.findViewById(R.id.tv_recipient_info);
        this.e = (DetailTwoText) inflate.findViewById(R.id.tv_amount);
        this.f = (DetailTwoText) inflate.findViewById(R.id.tv_date);
        this.g = (LinearLayout) inflate.findViewById(R.id.ll_message_me);
        this.h = (ImageView) inflate.findViewById(R.id.img_message_me);
        this.j = (TextView) inflate.findViewById(R.id.tv_message_date_me);
        this.k = (TextView) inflate.findViewById(R.id.tv_message_me);
        this.l = (Button) inflate.findViewById(R.id.b_relaunch_request);
        this.m = (Button) inflate.findViewById(R.id.b_cancel_request);
        this.i = (ImageView) inflate.findViewById(R.id.aiv_recipient_photo);
        this.p = (TextView) inflate.findViewById(R.id.tv_recipient_name);
        this.q = (TextView) inflate.findViewById(R.id.tv_recipient_id);
        this.n = (Button) inflate.findViewById(R.id.b_attachment);
        this.r = (TextView) inflate.findViewById(R.id.tv_p2p_get_status);
        return inflate;
    }

    public final void onSaveInstanceState(Bundle bundle) {
        if (this.t != null) {
            bundle.putParcelable("savedStateP2PGetMult", this.t);
        }
        super.onSaveInstanceState(bundle);
    }
}
