package fr.smoney.android.izly.ui;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class TabSwipeActivity$$ViewBinder<T extends TabSwipeActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mViewPager = (ViewPager) finder.castView((View) finder.findRequiredView(obj, R.id.viewPager, "field 'mViewPager'"), R.id.viewPager, "field 'mViewPager'");
        t.mTabLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.tabsLayout, "field 'mTabLayout'"), R.id.tabsLayout, "field 'mTabLayout'");
        t.accountBalanceView = (View) finder.findRequiredView(obj, R.id.account_balance_view, "field 'accountBalanceView'");
    }

    public void unbind(T t) {
        t.mViewPager = null;
        t.mTabLayout = null;
        t.accountBalanceView = null;
    }
}
