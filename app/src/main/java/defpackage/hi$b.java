package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.PromotionalOffer;
import fr.smoney.android.izly.data.model.PromotionalOffer.a;
import java.util.ArrayList;

final class hi$b extends BaseAdapter implements OnItemClickListener {
    LayoutInflater a;
    ArrayList<PromotionalOffer> b = new ArrayList();
    final /* synthetic */ hi c;

    public hi$b(hi hiVar, Context context) {
        this.c = hiVar;
        this.a = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    private PromotionalOffer a(int i) {
        return (PromotionalOffer) this.b.get(i);
    }

    public final int getCount() {
        return this.b.size();
    }

    public final /* synthetic */ Object getItem(int i) {
        return a(i);
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        hi$d hi_d;
        if (view == null) {
            hi$d hi_d2 = new hi$d(this.c);
            view = this.a.inflate(R.layout.listitem_home_promo_offer, viewGroup, false);
            hi_d2.a = (TextView) view.findViewById(R.id.pr_title);
            hi_d2.b = (TextView) view.findViewById(R.id.pr_description);
            hi_d2.c = (TextView) view.findViewById(R.id.pr_activity);
            hi_d2.d = (ImageView) view.findViewById(R.id.pr_aiv_avatar);
            hi_d2.e = view.findViewById(R.id.v_unread);
            view.setTag(hi_d2);
            hi_d = hi_d2;
        } else {
            hi_d = (hi$d) view.getTag();
        }
        PromotionalOffer a = a(i);
        if (a != null) {
            hi_d.a.setText(a.c);
            hi_d.b.setText(a.d);
            if (TextUtils.isEmpty(a.i)) {
                hi_d.c.setVisibility(4);
            } else {
                hi_d.c.setVisibility(0);
                hi_d.c.setText(a.i);
            }
            if (a.m == a.GLOBAL) {
                hi_d.d.setImageResource(R.drawable.detail_aiv_avatar_smoney);
            } else {
                hi_d.d.setImageResource(R.drawable.detail_aiv_avatar_placeholder);
            }
            if (a.b) {
                hi_d.e.setVisibility(8);
            } else {
                hi_d.e.setVisibility(0);
            }
        }
        return view;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        hi.a(this.c, this.c.D.a(i - this.c.u.getHeaderViewsCount()));
    }
}
