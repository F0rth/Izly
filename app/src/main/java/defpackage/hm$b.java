package defpackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.P2PGet;

final class hm$b extends BaseAdapter {
    public int a = -1;
    final /* synthetic */ hm b;
    private LayoutInflater c;

    public hm$b(hm hmVar) {
        this.b = hmVar;
        this.c = hmVar.getActivity().getLayoutInflater();
    }

    public final int getCount() {
        return this.b.B.b.size();
    }

    public final Object getItem(int i) {
        return null;
    }

    public final long getItemId(int i) {
        return 0;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        hm$a hm_a;
        if (view == null) {
            view = this.c.inflate(R.layout.p2p_get_mult_confirm_recipient_item, null);
            hm$a hm_a2 = new hm$a(this.b, view);
            view.setTag(hm_a2);
            hm_a = hm_a2;
        } else {
            hm_a = (hm$a) view.getTag();
        }
        P2PGet p2PGet = (P2PGet) this.b.B.b.get(i);
        hm_a.a.setImageURI(null);
        hm_a.a.setImageResource(R.drawable.list_aiv_avatar_placeholder);
        if (p2PGet.d) {
            hm_a.c.setText(jf.a(p2PGet.f, p2PGet.g, p2PGet.c));
            hm_a.d.N.a.displayImage(jl.a(p2PGet.c), hm_a.a);
        } else {
            hm_a.c.setText(jf.a(p2PGet.c));
        }
        hm_a.b.setImageDrawable(null);
        switch (p2PGet.m) {
            case 0:
                hm_a.b.setImageResource(R.drawable.p2p_get_mult_iv_reicpient_status_wait);
                break;
            case 1:
                hm_a.b.setImageResource(R.drawable.p2p_get_mult_iv_reicpient_status_cancelled);
                break;
            case 2:
                hm_a.b.setImageResource(R.drawable.p2p_get_mult_iv_reicpient_status_refused);
                break;
            case 3:
                hm_a.b.setImageResource(R.drawable.p2p_get_mult_iv_reicpient_status_ok);
                break;
        }
        if (this.a == i) {
            view.setSelected(true);
        } else {
            view.setSelected(false);
        }
        return view;
    }
}
