package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.ui.widget.ToggleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class TabSwipeActivity extends SmoneyABSActivity {
    @Bind({2131755171})
    View accountBalanceView;
    private a b;
    ActionBar g;
    @Bind({2131755283})
    LinearLayout mTabLayout;
    @Bind({2131755925})
    public ViewPager mViewPager;

    final class a extends FragmentPagerAdapter implements OnPageChangeListener, TabListener {
        List<a> a = new ArrayList();
        List<ToggleView> b = new ArrayList();
        HashMap<a, ToggleView> c = new HashMap();
        final /* synthetic */ TabSwipeActivity d;

        final class AnonymousClass1 implements OnClickListener {
            final /* synthetic */ ToggleView a;
            final /* synthetic */ a b;

            AnonymousClass1(a aVar, ToggleView toggleView) {
                this.b = aVar;
                this.a = toggleView;
            }

            public final void onClick(View view) {
                for (ToggleView checked : this.b.b) {
                    checked.setChecked(false);
                }
                for (int i = 0; i < this.b.b.size(); i++) {
                    if (this.b.b.get(i) == this.a) {
                        this.b.d.mViewPager.setCurrentItem(i);
                        this.a.setChecked(true);
                    }
                }
            }
        }

        final class a {
            public final Class<? extends Fragment> a;
            public final Bundle b;
            final /* synthetic */ a c;

            public a(a aVar, Class<? extends Fragment> cls, Bundle bundle) {
                this.c = aVar;
                this.a = cls;
                this.b = bundle;
            }
        }

        public a(TabSwipeActivity tabSwipeActivity, AppCompatActivity appCompatActivity) {
            this.d = tabSwipeActivity;
            super(appCompatActivity.getSupportFragmentManager());
        }

        public final int getCount() {
            return this.a.size();
        }

        public final Fragment getItem(int i) {
            a aVar = (a) this.a.get(i);
            return Fragment.instantiate(this.d, aVar.a.getName(), aVar.b);
        }

        public final void onPageScrollStateChanged(int i) {
        }

        public final void onPageScrolled(int i, float f, int i2) {
        }

        public final void onPageSelected(int i) {
            for (ToggleView checked : this.b) {
                checked.setChecked(false);
            }
            ((ToggleView) this.b.get(i)).setChecked(true);
            this.d.d(i);
        }

        public final void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
        }

        public final void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
            for (ToggleView checked : this.b) {
                checked.setChecked(false);
            }
            a aVar = (a) tab.getTag();
            for (int i = 0; i < this.a.size(); i++) {
                if (this.a.get(i) == aVar) {
                    ((ToggleView) this.c.get(aVar)).setChecked(true);
                    this.d.mViewPager.setCurrentItem(i);
                }
            }
        }

        public final void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
        }
    }

    protected final void a(int i, Class<? extends Fragment> cls, Bundle bundle) {
        a aVar = this.b;
        CharSequence string = getString(i);
        a aVar2 = new a(aVar, cls, bundle);
        Tab newTab = aVar.d.g.newTab();
        newTab.setText(string);
        newTab.setTabListener(aVar);
        newTab.setTag(aVar2);
        View toggleView = new ToggleView(aVar.d.getBaseContext(), string, 0, 0);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.setMargins(0, 0, 0, 0);
        layoutParams.weight = 1.0f;
        aVar.a.add(aVar2);
        aVar.b.add(toggleView);
        aVar.c.put(aVar2, toggleView);
        aVar.d.g.addTab(newTab);
        toggleView.setOnClickListener(new AnonymousClass1(aVar, toggleView));
        aVar.d.mTabLayout.addView(toggleView, layoutParams);
        aVar.notifyDataSetChanged();
    }

    protected final void b(boolean z) {
        this.accountBalanceView.setVisibility(z ? 0 : 8);
    }

    protected void d(int i) {
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.tab_swipe_activity);
        ButterKnife.bind(this);
        this.g = getSupportActionBar();
        this.g.setNavigationMode(2);
        this.g.show();
        this.b = new a(this, this);
        this.mViewPager.setAdapter(this.b);
        this.mViewPager.setOnPageChangeListener(this.b);
        this.accountBalanceView.setVisibility(8);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                a();
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }
}
