package defpackage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.astuetz.viewpagertabs.ViewPagerTabProvider;
import java.util.ArrayList;

final class hc$b extends PagerAdapter implements ViewPagerTabProvider {
    final /* synthetic */ hc a;
    private ArrayList<hc$a> b;

    public hc$b(hc hcVar, ArrayList<hc$a> arrayList) {
        this.a = hcVar;
        this.b = arrayList;
    }

    public final void destroyItem(View view, int i, Object obj) {
        ((ViewPager) view).removeView((View) obj);
    }

    public final int getCount() {
        return this.b.size();
    }

    public final String getTitle(int i) {
        return ((hc$a) this.b.get(i)).a;
    }

    public final Object instantiateItem(View view, int i) {
        View view2 = ((hc$a) this.b.get(i)).b;
        ((ViewPager) view).addView(view2, 0);
        return view2;
    }

    public final boolean isViewFromObject(View view, Object obj) {
        return view == ((View) obj);
    }
}
