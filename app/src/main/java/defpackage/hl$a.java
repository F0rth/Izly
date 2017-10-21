package defpackage;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.P2PGet;
import fr.smoney.android.izly.data.model.P2PGetMult;

final class hl$a extends ArrayAdapter<P2PGetMult> implements OnItemLongClickListener {
    public int a = -1;
    final /* synthetic */ hl b;

    public hl$a(hl hlVar, Context context) {
        this.b = hlVar;
        super(context, -1);
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        hl$e hl_e;
        if (view == null) {
            if (this.b.g == null) {
                this.b.g = this.b.d.getLayoutInflater();
            }
            view = this.b.g.inflate(R.layout.p2p_get_list_item, null);
            hl$e hl_e2 = new hl$e(this.b, view);
            view.setTag(hl_e2);
            hl_e = hl_e2;
        } else {
            hl_e = (hl$e) view.getTag();
        }
        P2PGetMult p2PGetMult = (P2PGetMult) getItem(i);
        if (p2PGetMult != null) {
            int i2;
            if (p2PGetMult.d) {
                hl_e.e.setVisibility(8);
            } else {
                hl_e.e.setVisibility(0);
            }
            hl_e.o.r.setToNow();
            hl_e.o.r.set(p2PGetMult.e);
            String a = jk.a(hl_e.o.d, hl_e.o.r);
            if (p2PGetMult.p) {
                hl_e.a.setVisibility(0);
                hl_e.o.m = a;
                hl_e.a.setText(hl_e.o.m);
            } else {
                hl_e.a.setVisibility(8);
            }
            hl_e.b.setText(hl_e.o.r.format("%H:%M"));
            if (p2PGetMult.f > 0.0d) {
                hl_e.c.setVisibility(0);
                hl_e.c.setText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(p2PGetMult.f), hl_e.o.n}));
            } else {
                hl_e.c.setVisibility(8);
                hl_e.c.setText("");
            }
            hl_e.l.setVisibility(8);
            hl_e.g.setVisibility(0);
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            while (i6 < p2PGetMult.b.size()) {
                switch (((P2PGet) p2PGetMult.b.get(i6)).m) {
                    case 0:
                        i5++;
                        i2 = i7;
                        break;
                    case 1:
                        i4++;
                        i2 = i7;
                        break;
                    case 2:
                        i2 = i7 + 1;
                        break;
                    case 3:
                        i3++;
                        i2 = i7;
                        break;
                    default:
                        i2 = i7;
                        break;
                }
                i6++;
                i7 = i2;
            }
            i2 = ((i4 + i3) + i7) + i5;
            if (i3 > 0) {
                if (i2 > 1) {
                    hl_e.h.setText(String.valueOf(i3));
                } else {
                    hl_e.h.setText("");
                }
                hl_e.h.setVisibility(0);
            } else {
                hl_e.h.setVisibility(8);
            }
            if (i5 > 0) {
                if (i2 > 1) {
                    hl_e.j.setText(String.valueOf(i5));
                } else {
                    hl_e.j.setText("");
                }
                hl_e.j.setVisibility(0);
            } else {
                hl_e.j.setVisibility(8);
            }
            if (i7 > 0) {
                if (i2 > 1) {
                    hl_e.k.setText(String.valueOf(i7));
                } else {
                    hl_e.k.setText("");
                }
                hl_e.k.setVisibility(0);
            } else {
                hl_e.k.setVisibility(8);
            }
            if (i4 > 0) {
                if (i2 > 1) {
                    hl_e.i.setText(String.valueOf(i4));
                } else {
                    hl_e.i.setText("");
                }
                hl_e.i.setVisibility(0);
            } else {
                hl_e.i.setVisibility(8);
            }
            i6 = 0;
            for (i7 = 0; i7 < p2PGetMult.b.size(); i7++) {
                if (!TextUtils.isEmpty(((P2PGet) p2PGetMult.b.get(i7)).j)) {
                    i6++;
                }
            }
            if (!TextUtils.isEmpty(p2PGetMult.j)) {
                i6++;
            }
            hl_e.m.setVisibility(i6 > 0 ? 0 : 8);
            Object obj = (p2PGetMult.o == null || p2PGetMult.n == null) ? null : 1;
            if (obj != null) {
                hl_e.n.setVisibility(0);
            } else {
                hl_e.n.setVisibility(8);
            }
            P2PGet p2PGet = (P2PGet) p2PGetMult.b.get(0);
            String a2 = jf.a(p2PGet.f, p2PGet.g, p2PGet.c);
            if (!p2PGet.d) {
                a2 = jf.a(p2PGet.c);
            }
            if (p2PGetMult.b.size() == 1) {
                hl_e.f.setText(hl_e.o.getString(R.string.p2p_get_list_to, new Object[]{a2}));
            } else if (p2PGetMult.b.size() > 1) {
                hl_e.f.setText(hl_e.o.getResources().getQuantityString(R.plurals.p2p_get_list_to_and_others, p2PGetMult.b.size() - 1, new Object[]{a2, Integer.valueOf(p2PGetMult.b.size() - 1)}));
            }
            if (p2PGetMult.b.size() > 1) {
                hl_e.d.setImageResource(R.drawable.list_aiv_avatar_mult);
            } else {
                hl_e.d.setImageResource(R.drawable.list_aiv_avatar_placeholder);
                if (p2PGet.d) {
                    hl_e.d.setImageResource(R.drawable.list_aiv_avatar_placeholder);
                    hl_e.o.c.a.displayImage(jl.a(p2PGet.c), hl_e.d);
                }
            }
        }
        return view;
    }

    @TargetApi(11)
    public final boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - ((ListView) this.b.h.getRefreshableView()).getHeaderViewsCount();
        this.b.f = (P2PGetMult) getItem(headerViewsCount);
        this.b.d.startSupportActionMode(new hl$b(this.b, headerViewsCount));
        return true;
    }
}
