package defpackage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.PromotionalOffer;
import fr.smoney.android.izly.data.model.PromotionalOffer.a;
import java.util.ArrayList;

public final class hk$a extends PagerAdapter {
    ArrayList<PromotionalOffer> a = new ArrayList();
    final /* synthetic */ hk b;
    private LayoutInflater c;

    public hk$a(hk hkVar) {
        this.b = hkVar;
        this.c = (LayoutInflater) hkVar.getActivity().getSystemService("layout_inflater");
    }

    public final PromotionalOffer a(int i) {
        return (PromotionalOffer) this.a.get(i);
    }

    public final void destroyItem(View view, int i, Object obj) {
        ((ViewPager) view).removeView((View) obj);
    }

    public final int getCount() {
        return this.a.size();
    }

    public final Object instantiateItem(ViewGroup viewGroup, int i) {
        hk$a$a hk_a_a = new hk$a$a(this);
        View inflate = this.c.inflate(R.layout.layout_promo_offer, null);
        hk_a_a.a = (TextView) inflate.findViewById(R.id.pr_title);
        hk_a_a.b = (TextView) inflate.findViewById(R.id.pr_activity);
        hk_a_a.c = (TextView) inflate.findViewById(R.id.pr_description);
        hk_a_a.d = (ImageView) inflate.findViewById(R.id.pr_aiv_avatar);
        PromotionalOffer a = a(i);
        if (a != null) {
            hk_a_a.a.setText(a.c);
            hk_a_a.c.setText(a.d);
            hk_a_a.b.setText(a.i);
            if (a.m == a.GLOBAL) {
                hk_a_a.d.setImageResource(R.drawable.list_aiv_avatar_smoney);
            } else {
                hk_a_a.d.setImageResource(R.drawable.list_aiv_avatar_placeholder);
            }
            hk_a_a.e.b.c.a.displayImage(jl.a(a.a), hk_a_a.d);
        }
        inflate.setOnClickListener(this.b);
        ((ViewPager) viewGroup).addView(inflate, 0);
        return inflate;
    }

    public final boolean isViewFromObject(View view, Object obj) {
        return view == ((View) obj);
    }
}
