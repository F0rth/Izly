package defpackage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.devsmart.android.ui.HorizontalListView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetAttachmentData;
import fr.smoney.android.izly.data.model.MakeMoneyDemandRelaunchData;
import fr.smoney.android.izly.data.model.MoneyDemandRelaunch;
import fr.smoney.android.izly.data.model.P2PGet;
import fr.smoney.android.izly.data.model.P2PGetMult;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import org.spongycastle.crypto.tls.CipherSuite;

public final class hm extends aa implements OnClickListener, OnItemClickListener, SmoneyRequestManager$a {
    private String A;
    private P2PGetMult B;
    private P2PGet C;
    private hm$b D;
    private int E;
    private TextView F;
    private View G;
    private View H;
    private View I;
    private View J;
    private View K;
    private ImageView L;
    private View M;
    private ge N;
    private HorizontalListView e;
    private TextView f;
    private TextView g;
    private View h;
    private LinearLayout i;
    private ImageView j;
    private TextView k;
    private TextView l;
    private TextView m;
    private TextView n;
    private TextView o;
    private TextView p;
    private TextView q;
    private Button r;
    private Button s;
    private Button t;
    private Button u;
    private Button v;
    private TextView w;
    private TextView x;
    private TextView y;
    private ImageView z;

    public static hm a(P2PGetMult p2PGetMult) {
        hm hmVar = new hm();
        Bundle bundle = new Bundle();
        bundle.putParcelable("fr.smoney.android.izly.extras.p2pGetMultDetailsData", p2PGetMult);
        hmVar.setArguments(bundle);
        return hmVar;
    }

    private String a(double d) {
        return String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.A});
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
        this.E = i;
        SmoneyRequestManager j2 = j();
        String str = i2.b.a;
        String str2 = i2.b.c;
        long j3 = this.B.c;
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            arrayList.add(Long.valueOf(j));
        } else {
            Iterator it = this.B.b.iterator();
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
            this.E = -1;
            if (this.B.c == j) {
                Iterator it = this.B.b.iterator();
                while (it.hasNext()) {
                    P2PGet p2PGet = (P2PGet) it.next();
                    if (p2PGet.m == 0) {
                        p2PGet.m = 1;
                    }
                }
            } else {
                this.C.m = 1;
            }
            o();
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
            this.E = -1;
            ArrayList arrayList = new ArrayList(makeMoneyDemandRelaunchData.c);
            this.B.c = makeMoneyDemandRelaunchData.a;
            Iterator it = this.B.b.iterator();
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
            o();
            if (this.E == 0) {
                if (makeMoneyDemandRelaunchData.b) {
                    a(hu.a(getString(R.string.p2p_get_mult_details_dialog_relaunch_result_title), getString(R.string.p2p_get_mult_details_dialog_relaunch_result_message, new Object[]{jf.a(this.C.f, this.C.g, this.C.c)}), getString(17039370)));
                    return;
                }
                a(hu.a(getString(R.string.p2p_get_mult_details_dialog_relaunch_result_title), a(i().bk.c), getString(17039370)));
            } else if (this.E != 1) {
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
        boolean z = this.B.f <= 0.0d;
        this.N.a.displayImage(jl.a(p2PGet.c), this.L);
        if (p2PGet.d) {
            this.x.setText(jf.a(p2PGet.f, p2PGet.g, p2PGet.c));
            this.y.setText(jf.a(p2PGet.c));
            if (jf.a(p2PGet.f, p2PGet.g)) {
                this.y.setVisibility(0);
            } else {
                this.y.setVisibility(8);
            }
        } else {
            this.x.setText(jf.a(p2PGet.c));
            this.y.setVisibility(4);
        }
        if (p2PGet.h > 0.0d) {
            this.F.setText(a(p2PGet.h));
        } else if (z) {
            this.F.setText(R.string.p2p_get_mult_tv_without_amount);
        } else {
            this.F.setText(R.string.p2p_get_mult_tv_no_amount);
        }
        switch (p2PGet.m) {
            case 0:
                this.z.setImageResource(R.drawable.p2p_get_list_iv_status_to_be_paid);
                this.s.setEnabled(true);
                if (p2PGet.p == null) {
                    this.r.setEnabled(true);
                    break;
                } else {
                    this.r.setEnabled(false);
                    break;
                }
            case 1:
                this.z.setImageResource(R.drawable.p2p_get_list_iv_status_cancelled);
                this.s.setEnabled(false);
                this.r.setEnabled(false);
                break;
            case 2:
                this.z.setImageResource(R.drawable.p2p_get_list_iv_status_refused);
                this.s.setEnabled(false);
                this.r.setEnabled(false);
                break;
            case 3:
                this.z.setImageResource(R.drawable.p2p_get_list_iv_status_paid);
                this.s.setEnabled(false);
                this.r.setEnabled(false);
                break;
        }
        if (p2PGet.m != 0 || p2PGet.p == null) {
            this.r.setText(getString(R.string.p2p_get_mult_details_b_relaunch_request));
        } else {
            this.r.setText(getString(R.string.p2p_get_mult_details_b_relaunch_hours_request, new Object[]{p2PGet.p}));
        }
    }

    private void b(long j, int i) {
        cl i2 = i();
        this.E = i;
        super.a(j().a(i2.b.a, i2.b.c, j, i), 63, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void n() {
        /*
        r14 = this;
        r11 = 1;
        r7 = 0;
        r0 = r14.B;
        r0 = r0.b;
        r8 = r0.size();
        r6 = r7;
        r1 = r7;
        r5 = r7;
        r3 = r7;
        r4 = r7;
        r2 = r7;
    L_0x0010:
        if (r6 >= r8) goto L_0x005b;
    L_0x0012:
        r0 = r14.B;
        r0 = r0.b;
        r0 = r0.get(r6);
        r0 = (fr.smoney.android.izly.data.model.P2PGet) r0;
        r9 = r0.m;
        switch(r9) {
            case 0: goto L_0x0031;
            case 1: goto L_0x0049;
            case 2: goto L_0x0041;
            case 3: goto L_0x0053;
            default: goto L_0x0021;
        };
    L_0x0021:
        r0 = r1;
        r1 = r3;
        r3 = r0;
        r0 = r2;
        r2 = r4;
        r4 = r5;
    L_0x0027:
        r5 = r6 + 1;
        r6 = r5;
        r5 = r4;
        r4 = r2;
        r2 = r0;
        r12 = r3;
        r3 = r1;
        r1 = r12;
        goto L_0x0010;
    L_0x0031:
        r1 = r1 + 1;
        r0 = r0.p;
        if (r0 != 0) goto L_0x0021;
    L_0x0037:
        r0 = r5 + 1;
        r12 = r2;
        r2 = r4;
        r4 = r0;
        r0 = r12;
        r13 = r1;
        r1 = r3;
        r3 = r13;
        goto L_0x0027;
    L_0x0041:
        r0 = r3 + 1;
        r3 = r1;
        r1 = r0;
        r0 = r2;
        r2 = r4;
        r4 = r5;
        goto L_0x0027;
    L_0x0049:
        r0 = r4 + 1;
        r4 = r5;
        r12 = r3;
        r3 = r1;
        r1 = r12;
        r13 = r0;
        r0 = r2;
        r2 = r13;
        goto L_0x0027;
    L_0x0053:
        r0 = r2 + 1;
        r2 = r4;
        r4 = r5;
        r12 = r1;
        r1 = r3;
        r3 = r12;
        goto L_0x0027;
    L_0x005b:
        r0 = r14.n;
        r6 = r14.getResources();
        r8 = 2131361799; // 0x7f0a0007 float:1.834336E38 double:1.0530326437E-314;
        r9 = new java.lang.Object[r11];
        r10 = java.lang.Integer.valueOf(r2);
        r9[r7] = r10;
        r2 = r6.getQuantityString(r8, r2, r9);
        r0.setText(r2);
        r0 = r14.o;
        r2 = r14.getResources();
        r6 = 2131361798; // 0x7f0a0006 float:1.8343359E38 double:1.0530326433E-314;
        r8 = new java.lang.Object[r11];
        r9 = java.lang.Integer.valueOf(r4);
        r8[r7] = r9;
        r2 = r2.getQuantityString(r6, r4, r8);
        r0.setText(r2);
        r0 = r14.p;
        r2 = 2131231505; // 0x7f080311 float:1.8079093E38 double:1.05296827E-314;
        r6 = new java.lang.Object[r11];
        r8 = java.lang.Integer.valueOf(r1);
        r6[r7] = r8;
        r2 = r14.getString(r2, r6);
        r0.setText(r2);
        r0 = r14.q;
        r2 = r14.getResources();
        r6 = 2131361800; // 0x7f0a0008 float:1.8343363E38 double:1.053032644E-314;
        r8 = new java.lang.Object[r11];
        r3 = java.lang.Integer.valueOf(r3);
        r8[r7] = r3;
        r2 = r2.getQuantityString(r6, r4, r8);
        r0.setText(r2);
        if (r5 <= 0) goto L_0x00c6;
    L_0x00b9:
        r0 = r14.t;
        r0.setEnabled(r11);
    L_0x00be:
        if (r1 <= 0) goto L_0x00cc;
    L_0x00c0:
        r0 = r14.u;
        r0.setEnabled(r11);
    L_0x00c5:
        return;
    L_0x00c6:
        r0 = r14.t;
        r0.setEnabled(r7);
        goto L_0x00be;
    L_0x00cc:
        r0 = r14.u;
        r0.setEnabled(r7);
        goto L_0x00c5;
        */
        throw new UnsupportedOperationException("Method not decompiled: hm.n():void");
    }

    private void o() {
        n();
        if (this.C != null) {
            a(this.C);
        }
        this.D.notifyDataSetChanged();
    }

    private void p() {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, this.B.n), CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (hm$1.a[ieVar.ordinal()]) {
            case 1:
                int h = h();
                if (h == 219) {
                    if (this.E == 1) {
                        a(this.B.c, this.E);
                        return;
                    } else if (this.E == 0) {
                        a(this.C.a, this.E);
                        return;
                    } else {
                        return;
                    }
                } else if (h == 63) {
                    if (this.E == 1) {
                        b(this.B.c, this.E);
                        return;
                    } else if (this.E == 0) {
                        b(this.C.a, this.E);
                        return;
                    } else {
                        return;
                    }
                } else if (h == CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA) {
                    p();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case 2:
                b(this.C.a, 0);
                return;
            case 3:
                a(this.C.a, 0);
                return;
            case 4:
                b(this.B.c, 1);
                return;
            case 5:
                a(this.B.c, 1);
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
        switch (hm$1.a[ieVar.ordinal()]) {
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
        switch (hm$1.a[ieVar.ordinal()]) {
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
        double d = 0.0d;
        super.onActivityCreated(bundle);
        if (bundle != null) {
            P2PGetMult p2PGetMult = (P2PGetMult) bundle.getParcelable("savedStateP2PGetMult");
            if (p2PGetMult != null) {
                this.B = p2PGetMult;
            }
        }
        if (this.B != null) {
            this.A = Currency.getInstance(i().b.j).getSymbol();
            if (this.B != null) {
                cl i = i();
                this.w.setText(R.string.p2p_get_mult_recipient);
                this.D = new hm$b(this);
                this.e.setAdapter(this.D);
                this.e.setOnItemClickListener(this);
                this.r.setOnClickListener(this);
                this.s.setOnClickListener(this);
                if (this.B.b.size() > 1) {
                    this.t.setOnClickListener(this);
                    this.u.setOnClickListener(this);
                } else {
                    this.t.setVisibility(8);
                    this.u.setVisibility(8);
                }
                this.g.setText(jk.b(getActivity(), this.B.e));
                if (this.B.f > 0.0d) {
                    this.f.setText(a(this.B.f));
                    int size = this.B.b.size();
                    int i2 = 0;
                    while (i2 < size) {
                        P2PGet p2PGet = (P2PGet) this.B.b.get(i2);
                        i2++;
                        d = p2PGet.m == 3 ? p2PGet.h + d : d;
                    }
                    this.m.setText(a(d));
                } else {
                    this.m.setText(getString(R.string.p2p_get_mult_tv_without_amount));
                    this.M.setVisibility(8);
                }
                if (this.B.j == null || this.B.j.equals("")) {
                    this.h.setVisibility(8);
                    this.i.setVisibility(8);
                } else {
                    this.k.setText(jk.b(getActivity(), new Date().getTime()));
                    this.l.setText(this.B.j);
                    this.N.a.displayImage(jl.a(i.b.a), this.j);
                    this.h.setVisibility(0);
                    this.i.setVisibility(0);
                }
                if (TextUtils.isEmpty(this.B.n)) {
                    this.v.setVisibility(8);
                } else {
                    this.v.setOnClickListener(this);
                    this.v.setVisibility(0);
                    this.v.setText(this.B.o);
                }
                n();
            }
        }
    }

    public final void onClick(View view) {
        if (view == this.s) {
            a(ht.a(getString(R.string.p2p_get_mult_details_dialog_cancel_confirm_title), getString(R.string.p2p_get_mult_details_dialog_cancel_confirm_message, new Object[]{jf.a(this.C.f, this.C.g, this.C.c)}), getString(17039379), getString(17039369), this, ie.P2PGetSimpleCancelType));
        } else if (view == this.u) {
            a(ht.a(getString(R.string.p2p_get_mult_details_dialog_cancel_confirm_title), getString(R.string.p2p_get_mult_details_dialog_cancel_all_confirm_message), getString(17039379), getString(17039369), this, ie.P2PGetMultipleCancelType));
        } else if (view == this.r) {
            a(ht.a(getString(R.string.p2p_get_mult_details_dialog_relaunch_confirm_title), getString(R.string.p2p_get_mult_details_dialog_relaunch_confirm_message, new Object[]{jf.a(this.C.f, this.C.g, this.C.c)}), getString(17039379), getString(17039369), this, ie.P2PGetSimpleRelaunchType));
        } else if (view == this.t) {
            a(ht.a(getString(R.string.p2p_get_mult_details_dialog_relaunch_confirm_title), getString(R.string.p2p_get_mult_details_dialog_relaunch_confirm_message_multiple), getString(17039379), getString(17039369), this, ie.P2PGetMultipleRelaunchType));
        } else if (view == this.v) {
            p();
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.B = (P2PGetMult) getArguments().getParcelable("fr.smoney.android.izly.extras.p2pGetMultDetailsData");
        this.N = new ge(this.d);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.p2p_get_mult_details, null);
        this.J = inflate.findViewById(R.id.block_button_all_recipients);
        this.K = inflate.findViewById(R.id.block_button_recipient_specific);
        this.I = inflate.findViewById(R.id.ll_recipient_infos);
        this.G = inflate.findViewById(R.id.ll_recipient_amount_block);
        this.H = inflate.findViewById(R.id.ll_amount_block);
        this.w = (TextView) inflate.findViewById(R.id.tv_recipient_info);
        this.e = (HorizontalListView) inflate.findViewById(R.id.riv_recipients);
        this.f = (TextView) inflate.findViewById(R.id.tv_amount);
        this.g = (TextView) inflate.findViewById(R.id.tv_date);
        this.h = inflate.findViewById(R.id.v_message_separator);
        this.i = (LinearLayout) inflate.findViewById(R.id.ll_message_me);
        this.j = (ImageView) inflate.findViewById(R.id.img_message_me);
        this.k = (TextView) inflate.findViewById(R.id.tv_message_date_me);
        this.l = (TextView) inflate.findViewById(R.id.tv_message_me);
        this.m = (TextView) inflate.findViewById(R.id.tv_already_payed);
        this.n = (TextView) inflate.findViewById(R.id.tv_number_of_paid);
        this.o = (TextView) inflate.findViewById(R.id.tv_number_of_cancelled);
        this.p = (TextView) inflate.findViewById(R.id.tv_number_of_to_be_paid);
        this.q = (TextView) inflate.findViewById(R.id.tv_number_of_refused);
        this.r = (Button) inflate.findViewById(R.id.b_relaunch_request);
        this.s = (Button) inflate.findViewById(R.id.b_cancel_request);
        this.t = (Button) inflate.findViewById(R.id.b_relaunch_all);
        this.u = (Button) inflate.findViewById(R.id.b_cancel_all);
        this.L = (ImageView) inflate.findViewById(R.id.aiv_recipient_photo);
        this.x = (TextView) inflate.findViewById(R.id.tv_recipient_name);
        this.y = (TextView) inflate.findViewById(R.id.tv_recipient_id);
        this.z = (ImageView) inflate.findViewById(R.id.tv_recipient_status);
        this.F = (TextView) inflate.findViewById(R.id.tv_recipient_amount);
        this.v = (Button) inflate.findViewById(R.id.b_attachment);
        this.M = inflate.findViewById(R.id.tv_amount_bar);
        return inflate;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        hm$b hm_b = (hm$b) adapterView.getAdapter();
        if (hm_b.a != i) {
            hm_b.a = i;
            P2PGet p2PGet = (P2PGet) this.B.b.get(i);
            this.C = p2PGet;
            a(p2PGet);
            this.H.setVisibility(8);
            this.J.setVisibility(8);
            this.G.setVisibility(0);
            this.K.setVisibility(0);
            this.I.setVisibility(0);
        } else {
            ((hm$b) adapterView.getAdapter()).a = -1;
            this.C = null;
            this.G.setVisibility(8);
            this.K.setVisibility(8);
            this.I.setVisibility(8);
            this.H.setVisibility(0);
            this.J.setVisibility(0);
        }
        hm_b.notifyDataSetChanged();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        if (this.B != null) {
            bundle.putParcelable("savedStateP2PGetMult", this.B);
        }
        super.onSaveInstanceState(bundle);
    }
}
